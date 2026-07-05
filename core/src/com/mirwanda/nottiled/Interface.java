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
	public String getOS();
	public void speak(final String s);
	public void changelanguage(final String lang);
	public void saveFile(String data);
	public void saveasFile(String data, String suggestedfilename);
	public void saveasFile(byte[] data, String suggestedfilename);
	public void openFile();
	public void newFile();
	public void selectFolder();
	public String getdatafromURI(String URI);
	public void setOrientation(int ori);
	public String getFilesDirPath();
	public String getApkPath();
	public boolean isAccessAllFilesGranted();
	public String getExternalStoragePath();
	public void editInNot2Pix(String absolutePath);
	public boolean isNot2PixInstalled();

	/** Virtual-path root under which the granted SAF folder tree is mounted
	 *  (e.g. "/saf/"). Empty string on platforms without SAF; there the plain
	 *  filesystem is used directly. Paths under this root resolve through the
	 *  SAF-backed FileHandle shim installed over Gdx.files. */
	public String getSafRoot();

	/** Tree-relative path of the document behind a picked content URI, or null
	 *  if it lives outside the granted folder tree (or no tree is granted). */
	public String resolveUriToTreePath(String uri);

	/** Returns true if a persistent folder-tree URI is saved and the index is ready. */
	public boolean hasTreeAccess();

}
