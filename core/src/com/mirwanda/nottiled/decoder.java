package com.mirwanda.nottiled;

import java.io.*;
import java.nio.*;
import java.util.*;
import java.util.zip.*;

import android.util.Base64;

public class decoder
{
	
	public static void copyFile(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}
	public String encode(String text)
	{
		try
		{
			byte[] data = text.getBytes("UTF-8");
			String base64 = Base64.encodeToString(data, Base64.NO_WRAP);
			return base64;
		}
		catch (Exception e)
		{
			return null;
		}

	}
// Receiving side
	public java.util.List<Long> decodeZlib(String base64,int size)
	{
		try
		{
			byte[] data = Base64.decode(base64, Base64.NO_WRAP);

			Inflater decompresser = new Inflater();
			decompresser.setInput(data);
			byte[] result = new byte[size];
			int resultLength = decompresser.inflate(result);
			decompresser.end();
			java.util.List<Long> lint = new ArrayList<Long>();

			for (int i = 0; i < result.length; i += 4)
			{ 
				//Integer inet = Byte.toUnsignedInt();
				byte[] wrap= {result[i],result[i + 1],result[i + 2],result[i + 3]};
				ByteBuffer wrapped = ByteBuffer.wrap(wrap); 
				wrapped.order(ByteOrder.LITTLE_ENDIAN);
				// big-endian by default
				long num = getUnsignedInt(wrapped.getInt());
				lint.add(num);
			} 
			return lint;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	public String savebase64zlib(int index,java.util.List<layer> layers)
	{
		try
		{
			layer l = layers.get(index);
			byte[] b = new byte[l.getStr().size()*4];
			//byte[] b = new byte[l.getStr().size()];
			for (int i = 0; i < l.getStr().size(); i++)
			{ 
			 	String s = "00000000"+Long.toHexString(l.getStr().get(i));
				s=s.substring(s.length()-8);

				int ig[]= new int[4];
				ig[0]=(Integer.decode("0x"+s.substring(0,2)));
				ig[1]=(Integer.decode("0x"+s.substring(2,4)));
				ig[2]=(Integer.decode("0x"+s.substring(4,6)));
				ig[3]=(Integer.decode("0x"+s.substring(6,8)));

				b[i*4+0]=(byte) ig[3];
				b[i*4+1]=(byte) ig[2];
				b[i*4+2]=(byte) ig[1];
				b[i*4+3]=(byte) ig[0];
			} 
			//byte[] output= new byte[l.getStr().size()];
			byte[] output= new byte[l.getStr().size()*4];
			
			Deflater compresser = new Deflater();
			compresser.setInput(b);
			compresser.finish();
			int compressedDataLength = compresser.deflate(output);
			compresser.end();
			byte[] outputcut = new byte[compressedDataLength];
			for (int x=0;x<compressedDataLength;x++)
			{
				outputcut[x]=output[x];
			}
			String nyoy = Base64.encodeToString(outputcut,Base64.NO_WRAP);
			return nyoy;
		}
		catch (Exception e)
		{
			return "";
		}
	}
	public java.util.List<Long> decodeGzip(String base64)
	{
		try
		{
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

			for (int i = 0; i < result.length; i += 4)
			{ 
				//Integer inet = Byte.toUnsignedInt();
				byte[] wrap= {result[i],result[i + 1],result[i + 2],result[i + 3]};
				ByteBuffer wrapped = ByteBuffer.wrap(wrap); 
				wrapped.order(ByteOrder.LITTLE_ENDIAN);
				// big-endian by default
				long num = getUnsignedInt(wrapped.getInt());
				
				lint.add(num);
			} 
			return lint;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	public String savebase64gzip(int index,java.util.List<layer> layers)
	{
		try
		{
			layer l = layers.get(index);
			byte[] b = new byte[l.getStr().size()*4];
			for (int i = 0; i < l.getStr().size(); i++)
			{ 
			 	String s = "00000000"+Long.toHexString(l.getStr().get(i));
				s=s.substring(s.length()-8);

				int ig[]= new int[4];
				ig[0]=(Integer.decode("0x"+s.substring(0,2)));
				ig[1]=(Integer.decode("0x"+s.substring(2,4)));
				ig[2]=(Integer.decode("0x"+s.substring(4,6)));
				ig[3]=(Integer.decode("0x"+s.substring(6,8)));

				b[i*4+0]=(byte) ig[3];
				b[i*4+1]=(byte) ig[2];
				b[i*4+2]=(byte) ig[1];
				b[i*4+3]=(byte) ig[0];
			} 

			ByteArrayOutputStream bos = new ByteArrayOutputStream(l.getStr().size()*4);
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(b);
			gzip.close();
			byte[] compressed = bos.toByteArray();
			bos.close();

			String nyoy = Base64.encodeToString(compressed,Base64.NO_WRAP);
			return nyoy;
		}
		catch (Exception e)
		{
			return "";
		}
	}




	public java.util.List<Long> decode(String base64)
	{
		try
		{
			byte[] data = Base64.decode(base64, Base64.NO_WRAP);

			java.util.List<Long> lint = new ArrayList<Long>();

			for (int i = 0; i < data.length; i += 4)
			{ 
				//Integer inet = Byte.toUnsignedInt();
				byte[] wrap= {data[i],data[i + 1],data[i + 2],data[i + 3]};
				ByteBuffer wrapped = ByteBuffer.wrap(wrap); 
				wrapped.order(ByteOrder.LITTLE_ENDIAN);
				// big-endian by default
				long num = getUnsignedInt(wrapped.getInt());
				lint.add(num);
			} 
			return lint;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static long getUnsignedInt(int x) {
		return x & 0x00000000ffffffffL;
	}

	public String savebase64(int index,java.util.List<layer> layers)
	{
		try
		{
			layer l = layers.get(index);
			byte[] b = new byte[l.getStr().size()*4];
			for (int i = 0; i < l.getStr().size(); i++)
			{ 
			 	String s = "00000000"+Long.toHexString(l.getStr().get(i));
				s=s.substring(s.length()-8);

				int ig[]= new int[4];
				ig[0]=(Integer.decode("0x"+s.substring(0,2)));
				ig[1]=(Integer.decode("0x"+s.substring(2,4)));
				ig[2]=(Integer.decode("0x"+s.substring(4,6)));
				ig[3]=(Integer.decode("0x"+s.substring(6,8)));

				b[i*4+0]=(byte) ig[3];
				b[i*4+1]=(byte) ig[2];
				b[i*4+2]=(byte) ig[1];
				b[i*4+3]=(byte) ig[0];
			} 
			String nyoy = Base64.encodeToString(b,Base64.NO_WRAP);
			return nyoy;
		}
		catch (Exception e)
		{
			return "";
		}
	}
}
