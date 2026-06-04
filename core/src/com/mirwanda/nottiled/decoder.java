package com.mirwanda.nottiled;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

public class decoder {

	public static void writeLayerGidsLittleEndian(List<Long> gids, byte[] out, int offset) {
		ByteBuffer buf = ByteBuffer.wrap(out, offset, gids.size() * 4);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < gids.size(); i++)
			buf.putInt((int) (gids.get(i) & 0xffffffffL));
	}

	public java.util.List<Long> decodeZlib(String base64, int size) {
		try {
			byte[] data = Base64.decode(base64, Base64.NO_WRAP);

			Inflater decompresser = new Inflater();
			decompresser.setInput(data);
			byte[] result = new byte[size];
			decompresser.inflate(result);
			decompresser.end();
			java.util.List<Long> lint = new ArrayList<Long>();

			for (int i = 0; i + 3 < result.length; i += 4) {
				byte[] wrap = { result[i], result[i + 1], result[i + 2], result[i + 3] };
				ByteBuffer wrapped = ByteBuffer.wrap(wrap);
				wrapped.order(ByteOrder.LITTLE_ENDIAN);
				long num = getUnsignedInt(wrapped.getInt());
				lint.add(num);
			}
			return lint;
		} catch (Exception e) {
			return null;
		}
	}

	public String savebase64zlib(int index, java.util.List<layer> layers) {
		try {
			layer l = layers.get(index);
			int n = l.getStr().size();
			byte[] b = new byte[n * 4];
			writeLayerGidsLittleEndian(l.getStr(), b, 0);
			byte[] output = new byte[n * 4];

			Deflater compresser = new Deflater();
			compresser.setInput(b);
			compresser.finish();
			int compressedDataLength = compresser.deflate(output);
			compresser.end();
			byte[] outputcut = new byte[compressedDataLength];
			System.arraycopy(output, 0, outputcut, 0, compressedDataLength);
			return Base64.encodeToString(outputcut, Base64.NO_WRAP);
		} catch (Exception e) {
			return "";
		}
	}

	public java.util.List<Long> decodeGzip(String base64) {
		try {
			byte[] data = Base64.decode(base64, Base64.NO_WRAP);

			java.io.ByteArrayInputStream bytein = new java.io.ByteArrayInputStream(data);
			java.util.zip.GZIPInputStream gzin = new java.util.zip.GZIPInputStream(bytein);
			java.io.ByteArrayOutputStream byteout = new java.io.ByteArrayOutputStream();

			int res = 0;
			byte buf[] = new byte[1024];
			while (res >= 0) {
				res = gzin.read(buf, 0, buf.length);
				if (res > 0) {
					byteout.write(buf, 0, res);
				}
			}
			byte result[] = byteout.toByteArray();

			java.util.List<Long> lint = new ArrayList<Long>();

			for (int i = 0; i + 3 < result.length; i += 4) {
				byte[] wrap = { result[i], result[i + 1], result[i + 2], result[i + 3] };
				ByteBuffer wrapped = ByteBuffer.wrap(wrap);
				wrapped.order(ByteOrder.LITTLE_ENDIAN);
				long num = getUnsignedInt(wrapped.getInt());

				lint.add(num);
			}
			return lint;
		} catch (Exception e) {
			return null;
		}
	}

	public String savebase64gzip(int index, java.util.List<layer> layers) {
		try {
			layer l = layers.get(index);
			int n = l.getStr().size();
			byte[] b = new byte[n * 4];
			writeLayerGidsLittleEndian(l.getStr(), b, 0);

			ByteArrayOutputStream bos = new ByteArrayOutputStream(n * 4);
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(b);
			gzip.close();
			byte[] compressed = bos.toByteArray();
			bos.close();

			return Base64.encodeToString(compressed, Base64.NO_WRAP);
		} catch (Exception e) {
			return "";
		}
	}

	public java.util.List<Long> decode(String base64) {
		try {
			byte[] data = Base64.decode(base64, Base64.NO_WRAP);

			java.util.List<Long> lint = new ArrayList<Long>();

			for (int i = 0; i + 3 < data.length; i += 4) {
				byte[] wrap = { data[i], data[i + 1], data[i + 2], data[i + 3] };
				ByteBuffer wrapped = ByteBuffer.wrap(wrap);
				wrapped.order(ByteOrder.LITTLE_ENDIAN);
				long num = getUnsignedInt(wrapped.getInt());
				lint.add(num);
			}
			return lint;
		} catch (Exception e) {
			return null;
		}
	}

	public static long getUnsignedInt(int x) {
		return x & 0x00000000ffffffffL;
	}

	public String savebase64(int index, java.util.List<layer> layers) {
		try {
			layer l = layers.get(index);
			int n = l.getStr().size();
			byte[] b = new byte[n * 4];
			writeLayerGidsLittleEndian(l.getStr(), b, 0);
			return Base64.encodeToString(b, Base64.NO_WRAP);
		} catch (Exception e) {
			return "";
		}
	}
}
