package com.mirwanda.nottiled;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * FileHandle backed by the granted SAF document tree instead of the filesystem.
 * Addressed by virtual absolute paths: "/saf/<tree-relative-path>". Everything
 * in libGDX that consumes FileHandles via streams (Texture, Pixmap, readBytes,
 * writeString, copyTo, ...) works transparently; file() is unsupported.
 */
public class SafFileHandle extends FileHandle {

	/** Virtual path prefix. A path is SAF-backed iff it equals ROOT or starts with ROOT + "/". */
	public static final String ROOT = "/saf";

	private final SafDocumentStore store;

	public SafFileHandle(SafDocumentStore store, String virtualPath) {
		super(normalizeVirtual(virtualPath), FileType.Absolute);
		this.store = store;
	}

	public static boolean isSafPath(String path) {
		if (path == null) return false;
		String p = path.replace('\\', '/');
		return p.equals(ROOT) || p.startsWith(ROOT + "/");
	}

	private static String normalizeVirtual(String path) {
		String p = path.replace('\\', '/');
		// Collapse duplicate slashes and trailing slash (keep the leading one)
		while (p.contains("//")) p = p.replace("//", "/");
		if (p.length() > 1 && p.endsWith("/")) p = p.substring(0, p.length() - 1);
		if (!isSafPath(p)) throw new GdxRuntimeException("Not a SAF virtual path: " + path);
		return p;
	}

	/** Tree-relative path ("" = tree root). */
	public String relPath() {
		String p = path();
		if (p.equals(ROOT)) return "";
		return p.substring(ROOT.length() + 1);
	}

	// ─── Streams ─────────────────────────────────────────────────────────────

	@Override
	public InputStream read() {
		try {
			return store.openIn(relPath());
		} catch (Exception e) {
			throw new GdxRuntimeException("Error reading SAF file: " + path(), e);
		}
	}

	@Override
	public OutputStream write(boolean append) {
		try {
			return store.openOut(relPath(), append);
		} catch (Exception e) {
			throw new GdxRuntimeException("Error writing SAF file: " + path(), e);
		}
	}

	// Base writer() opens a FileOutputStream on file() directly — route through write().
	@Override
	public java.io.Writer writer(boolean append) {
		return writer(append, null);
	}

	@Override
	public java.io.Writer writer(boolean append, String charset) {
		try {
			OutputStream output = write(append);
			return charset == null
					? new java.io.OutputStreamWriter(output)
					: new java.io.OutputStreamWriter(output, charset);
		} catch (Exception e) {
			throw new GdxRuntimeException("Error writing SAF file: " + path(), e);
		}
	}

	// Base moveTo() tries file().renameTo() first — go straight to copy + delete.
	@Override
	public void moveTo(FileHandle dest) {
		copyTo(dest);
		delete();
		if (exists() && isDirectory()) deleteDirectory();
	}

	// ─── Traversal ───────────────────────────────────────────────────────────

	@Override
	public FileHandle child(String name) {
		return new SafFileHandle(store, path() + "/" + name);
	}

	@Override
	public FileHandle sibling(String name) {
		return parent().child(name);
	}

	@Override
	public FileHandle parent() {
		String p = path();
		if (p.equals(ROOT)) return this; // clamp at the tree root
		int slash = p.lastIndexOf('/');
		String parentPath = p.substring(0, slash);
		if (parentPath.length() < ROOT.length()) parentPath = ROOT;
		return new SafFileHandle(store, parentPath);
	}

	@Override
	public FileHandle[] list() {
		List<String> names = store.list(relPath());
		FileHandle[] handles = new FileHandle[names.size()];
		for (int i = 0; i < names.size(); i++) {
			handles[i] = child(names.get(i));
		}
		return handles;
	}

	@Override
	public FileHandle[] list(String suffix) {
		List<String> names = store.list(relPath());
		java.util.ArrayList<FileHandle> handles = new java.util.ArrayList<FileHandle>();
		for (String name : names) {
			if (name.endsWith(suffix)) handles.add(child(name));
		}
		return handles.toArray(new FileHandle[0]);
	}

	@Override
	public FileHandle[] list(FileFilter filter) {
		// FileFilter needs java.io.File; not supported for SAF-backed handles.
		return list();
	}

	@Override
	public FileHandle[] list(FilenameFilter filter) {
		List<String> names = store.list(relPath());
		java.util.ArrayList<FileHandle> handles = new java.util.ArrayList<FileHandle>();
		File fakeDir = new File(path());
		for (String name : names) {
			if (filter == null || filter.accept(fakeDir, name)) handles.add(child(name));
		}
		return handles.toArray(new FileHandle[0]);
	}

	// ─── Metadata ────────────────────────────────────────────────────────────

	@Override
	public boolean exists() {
		return store.exists(relPath());
	}

	@Override
	public boolean isDirectory() {
		return store.isDirectory(relPath());
	}

	@Override
	public long length() {
		return store.length(relPath());
	}

	@Override
	public long lastModified() {
		return store.lastModified(relPath());
	}

	// ─── Mutation ────────────────────────────────────────────────────────────

	@Override
	public void mkdirs() {
		store.createDirAt(relPath());
	}

	@Override
	public boolean delete() {
		return store.delete(relPath());
	}

	@Override
	public boolean deleteDirectory() {
		// SAF deleteDocument is recursive for directories.
		return store.delete(relPath());
	}

	@Override
	public void emptyDirectory() {
		for (FileHandle child : list()) {
			child.deleteDirectory();
		}
	}

	@Override
	public void emptyDirectory(boolean preserveTree) {
		emptyDirectory();
	}

	// ─── Unsupported ─────────────────────────────────────────────────────────

	@Override
	public File file() {
		// Never return a bogus java.io.File — callers must use streams.
		throw new GdxRuntimeException("SAF file handle has no java.io.File: " + path());
	}

	@Override
	public java.nio.ByteBuffer map(java.nio.channels.FileChannel.MapMode mode) {
		throw new GdxRuntimeException("Cannot map a SAF file handle: " + path());
	}
}
