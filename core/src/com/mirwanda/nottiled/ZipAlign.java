package com.mirwanda.nottiled;

import java.io.*;
import java.nio.*;
import java.util.*;

public class ZipAlign {

    private static class Alignment {
        long extraFieldLenOffset;
        short extraFieldLenValue;
        int extraFieldExtensionOffset;
        int alignAmount;

        Alignment(long extraFieldLenOffset, short extraFieldLenValue, int extraFieldExtensionOffset, int alignAmount) {
            this.extraFieldLenOffset = extraFieldLenOffset;
            this.extraFieldLenValue = extraFieldLenValue;
            this.extraFieldExtensionOffset = extraFieldExtensionOffset;
            this.alignAmount = alignAmount;
        }
    }

    private static class FileOffsetShift {
        long eocdhPosition;
        long shiftedFileOffset;

        FileOffsetShift(long eocdhPosition, long shiftedFileOffset) {
            this.eocdhPosition = eocdhPosition;
            this.shiftedFileOffset = shiftedFileOffset;
        }
    }

    private static void passBytes(RandomAccessFile in, OutputStream out, long count) throws IOException {
        byte[] buf = new byte[8192];
        long total = 0;
        while (total < count) {
            int toRead = (int) Math.min(buf.length, count - total);
            int read = in.read(buf, 0, toRead);
            if (read == -1) {
                throw new EOFException("Reached EOF while copying " + count + " bytes");
            }
            out.write(buf, 0, read);
            total += read;
        }
    }

    public static void alignZip(RandomAccessFile zipFile, OutputStream zipOut) throws IOException {
        alignZip(zipFile, zipOut, 4, 16384);
    }

    public static void alignZip(RandomAccessFile zipFile, OutputStream zipOut, boolean alignSo) throws IOException {
        alignZip(zipFile, zipOut, 4, alignSo ? 16384 : 0);
    }

    public static void alignZip(RandomAccessFile zipFile, OutputStream zipOut, int alignment, boolean alignSo) throws IOException {
        alignZip(zipFile, zipOut, alignment, alignSo ? 16384 : 0);
    }

    public static void alignZip(RandomAccessFile zipFile, OutputStream zipOut, int alignment, int soAlignment) throws IOException {
        long length = zipFile.length();
        long searchStart = length - 65557;
        if (searchStart < 0) searchStart = 0;
        int searchLen = (int) (length - searchStart);
        byte[] buf = new byte[searchLen];
        zipFile.seek(searchStart);
        zipFile.readFully(buf);

        long eocdOffset = -1;
        for (int i = searchLen - 22; i >= 0; i--) {
            if (buf[i] == 0x50 && buf[i+1] == 0x4b && buf[i+2] == 0x05 && buf[i+3] == 0x06) {
                eocdOffset = searchStart + i;
                break;
            }
        }
        if (eocdOffset == -1) {
            throw new IOException("No end-of-central-directory found");
        }

        // Read EOCD fields
        zipFile.seek(eocdOffset + 10);
        byte[] eocdData = new byte[10];
        zipFile.readFully(eocdData);
        ByteBuffer eocdBB = ByteBuffer.wrap(eocdData).order(ByteOrder.LITTLE_ENDIAN);
        int numEntries = eocdBB.getShort() & 0xFFFF;
        long cdSize = eocdBB.getInt() & 0xFFFFFFFFL;
        long cdOffset = eocdBB.getInt() & 0xFFFFFFFFL;

        ArrayList<Alignment> alignments = new ArrayList<>();
        ArrayList<FileOffsetShift> shifts = new ArrayList<>();
        int cumulativeShift = 0;

        zipFile.seek(cdOffset);
        byte[] cdHeader = new byte[46];

        for (int i = 0; i < numEntries; i++) {
            long entryPos = zipFile.getFilePointer();
            zipFile.readFully(cdHeader);
            ByteBuffer cdBB = ByteBuffer.wrap(cdHeader).order(ByteOrder.LITTLE_ENDIAN);
            int sig = cdBB.getInt();
            if (sig != 0x02014b50) {
                throw new IOException("Invalid central directory entry signature at offset " + entryPos);
            }

            cdBB.position(10);
            int compressionMethod = cdBB.getShort() & 0xFFFF;

            cdBB.position(28);
            int filenameLen = cdBB.getShort() & 0xFFFF;
            int extraLen = cdBB.getShort() & 0xFFFF;
            int commentLen = cdBB.getShort() & 0xFFFF;

            cdBB.position(42);
            long localHeaderOffset = cdBB.getInt() & 0xFFFFFFFFL;

            // Read filename to check .so and track position
            byte[] nameBuf = new byte[filenameLen];
            zipFile.readFully(nameBuf);
            String filename = new String(nameBuf, "UTF-8");

            // Seek past extra field and comment
            zipFile.seek(entryPos + 46 + filenameLen + extraLen + commentLen);

            // Save central directory shift
            if (cumulativeShift != 0 || localHeaderOffset != 0) {
                shifts.add(new FileOffsetShift(entryPos + 42, localHeaderOffset + cumulativeShift));
            }

            // Read Local File Header (LFH) metadata
            long savedPos = zipFile.getFilePointer();
            zipFile.seek(localHeaderOffset + 26);
            byte[] lfhSizeBuf = new byte[4];
            zipFile.readFully(lfhSizeBuf);
            ByteBuffer lfhSizeBB = ByteBuffer.wrap(lfhSizeBuf).order(ByteOrder.LITTLE_ENDIAN);
            int lfhFilenameLen = lfhSizeBB.getShort() & 0xFFFF;
            int lfhExtraLen = lfhSizeBB.getShort() & 0xFFFF;
            zipFile.seek(savedPos);

            long dataOffset = localHeaderOffset + 30 + lfhFilenameLen + lfhExtraLen + cumulativeShift;

            int alignmentValue = 4;
            boolean needsAlignment = false;
            if (soAlignment != 0 && filename.endsWith(".so")) {
                alignmentValue = soAlignment;
                needsAlignment = true;
            } else if (compressionMethod == 0) {
                needsAlignment = true;
            }

            int padding = 0;
            if (needsAlignment) {
                long remainder = dataOffset % alignmentValue;
                if (remainder != 0) {
                    padding = (int) (alignmentValue - remainder);
                }
            }

            if (padding > 0) {
                alignments.add(new Alignment(
                    localHeaderOffset + 28,
                    (short) (lfhExtraLen + padding),
                    lfhFilenameLen + lfhExtraLen,
                    padding
                ));
                cumulativeShift += padding;
            }
        }

        // Write the aligned zip file
        zipFile.seek(0);

        if (alignments.isEmpty()) {
            // Write everything as-is if no alignments needed
            passBytes(zipFile, zipOut, length);
            return;
        }

        // Write with alignments
        for (Alignment align : alignments) {
            // Copy up to the extraFieldLenOffset
            passBytes(zipFile, zipOut, align.extraFieldLenOffset - zipFile.getFilePointer());

            // Write new extraFieldLenValue (2 bytes)
            ByteBuffer shortBB = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
            shortBB.putShort(align.extraFieldLenValue);
            zipOut.write(shortBB.array());

            // Skip the old 2 bytes in the input file
            zipFile.readShort();

            // Copy the filename and old extra field
            passBytes(zipFile, zipOut, align.extraFieldExtensionOffset);

            // Write padding bytes
            byte[] paddingBytes = new byte[align.alignAmount];
            zipOut.write(paddingBytes);
        }

        // Copy up to the central directory
        passBytes(zipFile, zipOut, cdOffset - zipFile.getFilePointer());

        // Process shifts in the Central Directory
        for (FileOffsetShift shift : shifts) {
            // Copy up to the shifted file offset field
            passBytes(zipFile, zipOut, shift.eocdhPosition - zipFile.getFilePointer());

            // Write new shifted file offset (4 bytes)
            ByteBuffer intBB = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
            intBB.putInt((int) shift.shiftedFileOffset);
            zipOut.write(intBB.array());

            // Skip old 4 bytes
            zipFile.readInt();
        }

        // Copy up to EOCD central directory offset field (EOCD offset + 16)
        passBytes(zipFile, zipOut, (eocdOffset + 16) - zipFile.getFilePointer());

        // Write new central directory offset (4 bytes)
        ByteBuffer intBB = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        intBB.putInt((int) (cdOffset + cumulativeShift));
        zipOut.write(intBB.array());

        // Skip old 4 bytes
        zipFile.readInt();

        // Copy the remaining bytes of EOCD
        passBytes(zipFile, zipOut, length - zipFile.getFilePointer());
    }
}
