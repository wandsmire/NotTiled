package com.mirwanda.nottiled;

import java.io.InputStream;

public interface Interface
{
	public void showbanner(boolean show);
	public void showinterstitial();
	public boolean buyadfree();
	public boolean ispro();
	public String getVersione();
	public byte[] getData();
	public java.util.List<byte[]> getDatas();
	public java.util.List<String> getFilenames();
	public String getFilename();
	public String getUri();
	public String getStatus();
	public void speak(final String s);
	public void changelanguage(final String lang);
	public void saveFile(String data);
	public void saveasFile(String data, String suggestedfilename);
	public void saveasFile(byte[] data, String suggestedfilename);
	public void openFile();
	public void newFile();
	public void selectFolder();
	public String getdatafromURI(String URI);
}
