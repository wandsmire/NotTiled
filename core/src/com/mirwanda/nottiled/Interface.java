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

	/** Parse TMX content, resolve referenced images from the persistent folder tree,
	 *  and copy only the needed files into the app's Temp directory.
	 *  On completion, getStatus() returns "OK", "no_tree" (no folder access granted yet),
	 *  or "error". getFilename() returns the TMX's tree-relative path used in Temp. */
	public void fetchTmxAssets(String tmxContent, String tmxUri);

	/** Returns true if a persistent folder-tree URI is saved and the index is ready. */
	public boolean hasTreeAccess();

	/** Write data back to a file in the saved folder tree at the given relative path.
	 *  Returns true on success. Creates intermediate directories if needed. */
	public boolean saveFileToTree(String relPath, byte[] data);

	/** Copy a file from the folder tree to a local absolute path.
	 *  Returns true on success. */
	public boolean copyTreeFileToLocal(String relPath, String localPath);

	/** Return a list of file paths in the tree matching the given extensions (e.g. ".png").
	 *  Extensions should include the dot. Returns empty list if no tree access. */
	public java.util.List<String> listTreeFiles(String[] extensions);

}
