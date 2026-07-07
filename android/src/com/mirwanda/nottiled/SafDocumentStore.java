package com.mirwanda.nottiled;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Owns the persisted SAF folder-tree grant and an index of its contents.
 * All tree access (SafFileHandle, MainActivity) goes through here.
 *
 * Paths are tree-relative, '/'-separated, no leading slash ("Maps/town.tmx").
 * The empty string "" is the tree root.
 */
public class SafDocumentStore {

	/** One indexed document. */
	static class Entry {
		final Uri uri;
		final boolean isDir;
		final long length;
		final long lastModified;

		Entry(Uri uri, boolean isDir, long length, long lastModified) {
			this.uri = uri;
			this.isDir = isDir;
			this.length = length;
			this.lastModified = lastModified;
		}
	}

	private final Context context;
	private volatile Uri treeUri = null;
	// relPath -> Entry. Guarded by (index). Contains files AND directories.
	private final HashMap<String, Entry> index = new HashMap<String, Entry>();
	private volatile boolean indexReady = false;

	public SafDocumentStore(Context context) {
		this.context = context;
	}

	private ContentResolver cr() {
		return context.getContentResolver();
	}

	// ─── Tree grant lifecycle ────────────────────────────────────────────────

	public boolean hasTree() {
		return treeUri != null;
	}

	public Uri getTreeUri() {
		return treeUri;
	}

	/** Adopt a freshly-picked tree URI (permission already granted by the picker). */
	public void setTree(Uri uri) {
		treeUri = uri;
	}

	/** Restore a persisted tree URI; returns false if the grant is gone. */
	public boolean restoreTree(String uriString) {
		if (uriString == null || uriString.isEmpty()) return false;
		try {
			Uri uri = Uri.parse(uriString);
			cr().takePersistableUriPermission(uri,
					Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
			treeUri = uri;
			return true;
		} catch (Exception e) {
			treeUri = null;
			return false;
		}
	}

	// ─── Index ───────────────────────────────────────────────────────────────

	public boolean isIndexReady() {
		return indexReady;
	}

	public void rebuildIndexAsync() {
		final Uri tree = treeUri;
		if (tree == null) return;
		new Thread(new Runnable() {
			@Override
			public void run() {
				rebuildIndex();
			}
		}, "SAF-index").start();
	}

	/** Synchronous full rescan of the tree. */
	public void rebuildIndex() {
		Uri tree = treeUri;
		if (tree == null) return;
		HashMap<String, Entry> fresh = new HashMap<String, Entry>();
		try {
			String rootDocId = DocumentsContract.getTreeDocumentId(tree);
			walk(tree, rootDocId, "", fresh);
		} catch (Exception e) {
			e.printStackTrace();
		}
		synchronized (index) {
			index.clear();
			index.putAll(fresh);
		}
		indexReady = true;
	}

	private void walk(Uri tree, String parentDocId, String parentRelPath, Map<String, Entry> out) {
		Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(tree, parentDocId);
		Cursor cursor = null;
		try {
			cursor = cr().query(childrenUri, new String[]{
					DocumentsContract.Document.COLUMN_DOCUMENT_ID,
					DocumentsContract.Document.COLUMN_MIME_TYPE,
					DocumentsContract.Document.COLUMN_DISPLAY_NAME,
					DocumentsContract.Document.COLUMN_SIZE,
					DocumentsContract.Document.COLUMN_LAST_MODIFIED
			}, null, null, null);
			if (cursor == null) return;
			while (cursor.moveToNext()) {
				String docId = cursor.getString(0);
				String mime = cursor.getString(1);
				String name = cursor.getString(2);
				long size = cursor.isNull(3) ? 0 : cursor.getLong(3);
				long modified = cursor.isNull(4) ? 0 : cursor.getLong(4);
				String relPath = parentRelPath.isEmpty() ? name : parentRelPath + "/" + name;
				boolean isDir = DocumentsContract.Document.MIME_TYPE_DIR.equals(mime);
				Uri docUri = DocumentsContract.buildDocumentUriUsingTree(tree, docId);
				out.put(relPath, new Entry(docUri, isDir, size, modified));
				if (isDir) {
					walk(tree, docId, relPath, out);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) cursor.close();
		}
	}

	// ─── Resolution ──────────────────────────────────────────────────────────

	private static String normalize(String relPath) {
		if (relPath == null) return "";
		String norm = relPath.replace('\\', '/');
		while (norm.startsWith("/")) norm = norm.substring(1);
		while (norm.endsWith("/")) norm = norm.substring(0, norm.length() - 1);
		return norm;
	}

	/**
	 * Resolve a tree-relative path to its Entry. Checks the index first, then
	 * live-walks the provider segment by segment (handles files created after
	 * the last index build) and caches the result.
	 */
	public Entry resolve(String relPath) {
		Uri tree = treeUri;
		if (tree == null) return null;
		String norm = normalize(relPath);
		if (norm.isEmpty()) {
			String rootDocId = DocumentsContract.getTreeDocumentId(tree);
			return new Entry(DocumentsContract.buildDocumentUriUsingTree(tree, rootDocId), true, 0, 0);
		}
		synchronized (index) {
			Entry e = index.get(norm);
			if (e != null) return e;
			// Case-insensitive index match
			for (Map.Entry<String, Entry> en : index.entrySet()) {
				if (en.getKey().equalsIgnoreCase(norm)) return en.getValue();
			}
		}
		// Live walk (also serves before the index finishes building)
		Entry live = liveResolve(tree, norm);
		if (live != null) {
			synchronized (index) {
				index.put(norm, live);
			}
		}
		return live;
	}

	private Entry liveResolve(Uri tree, String norm) {
		try {
			String parentDocId = DocumentsContract.getTreeDocumentId(tree);
			String[] parts = norm.split("/");
			Entry found = null;
			for (int i = 0; i < parts.length; i++) {
				found = queryChild(tree, parentDocId, parts[i]);
				if (found == null) return null;
				if (i < parts.length - 1) {
					if (!found.isDir) return null;
					parentDocId = DocumentsContract.getDocumentId(found.uri);
				}
			}
			return found;
		} catch (Exception e) {
			return null;
		}
	}

	private Entry queryChild(Uri tree, String parentDocId, String childName) {
		Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(tree, parentDocId);
		Cursor cursor = null;
		try {
			cursor = cr().query(childrenUri, new String[]{
					DocumentsContract.Document.COLUMN_DOCUMENT_ID,
					DocumentsContract.Document.COLUMN_MIME_TYPE,
					DocumentsContract.Document.COLUMN_DISPLAY_NAME,
					DocumentsContract.Document.COLUMN_SIZE,
					DocumentsContract.Document.COLUMN_LAST_MODIFIED
			}, null, null, null);
			if (cursor == null) return null;
			while (cursor.moveToNext()) {
				String name = cursor.getString(2);
				if (childName.equalsIgnoreCase(name)) {
					String docId = cursor.getString(0);
					boolean isDir = DocumentsContract.Document.MIME_TYPE_DIR.equals(cursor.getString(1));
					long size = cursor.isNull(3) ? 0 : cursor.getLong(3);
					long modified = cursor.isNull(4) ? 0 : cursor.getLong(4);
					return new Entry(DocumentsContract.buildDocumentUriUsingTree(tree, docId), isDir, size, modified);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) cursor.close();
		}
		return null;
	}

	/** Read-only convenience: find a file by bare filename anywhere in the tree.
	 *  Never use for writes — duplicate names in different folders are ambiguous. */
	public String findByFilename(String filename) {
		if (filename == null || filename.isEmpty()) return null;
		synchronized (index) {
			for (Map.Entry<String, Entry> e : index.entrySet()) {
				if (e.getValue().isDir) continue;
				String key = e.getKey();
				String keyName = key.contains("/") ? key.substring(key.lastIndexOf('/') + 1) : key;
				if (keyName.equalsIgnoreCase(filename)) return key;
			}
		}
		return null;
	}

	/**
	 * Tree-relative path of the document behind a picked content URI, or null if
	 * it lives outside the granted tree. Matches by document-ID path component,
	 * falling back to a filename match against the index.
	 */
	public String relPathForUri(String uriString) {
		Uri tree = treeUri;
		if (tree == null || uriString == null || uriString.isEmpty()) return null;
		try {
			Uri u = Uri.parse(uriString);
			String docId = DocumentsContract.getDocumentId(u);
			if (docId == null) return null;
			// docId for external storage looks like "primary:Maps/town.tmx"
			String treeDocId = DocumentsContract.getTreeDocumentId(tree);
			String docPath = docId.contains(":") ? docId.substring(docId.indexOf(':') + 1) : docId;
			String treePath = treeDocId.contains(":") ? treeDocId.substring(treeDocId.indexOf(':') + 1) : treeDocId;
			String docVol = docId.contains(":") ? docId.substring(0, docId.indexOf(':')) : "";
			String treeVol = treeDocId.contains(":") ? treeDocId.substring(0, treeDocId.indexOf(':')) : "";
			if (docVol.equals(treeVol)) {
				docPath = docPath.replace('\\', '/');
				treePath = treePath.replace('\\', '/');
				if (treePath.isEmpty()) return docPath;
				if (docPath.equalsIgnoreCase(treePath)) return "";
				if (docPath.length() > treePath.length() + 1
						&& docPath.substring(0, treePath.length()).equalsIgnoreCase(treePath)
						&& docPath.charAt(treePath.length()) == '/') {
					return docPath.substring(treePath.length() + 1);
				}
			}
			// Different provider or volume (e.g. Drive): match by filename in the index
			String name = docPath.contains("/") ? docPath.substring(docPath.lastIndexOf('/') + 1) : docPath;
			return findByFilename(name);
		} catch (Exception e) {
			return null;
		}
	}

	// ─── I/O ─────────────────────────────────────────────────────────────────

	public InputStream openIn(String relPath) throws IOException {
		Entry e = resolve(relPath);
		if (e == null || e.isDir) throw new IOException("SAF: not found: " + relPath);
		InputStream in = cr().openInputStream(e.uri);
		if (in == null) throw new IOException("SAF: cannot open stream: " + relPath);
		return in;
	}

	/** Open for writing, creating the document (and parent dirs) if needed.
	 *  Truncates unless append. */
	public OutputStream openOut(String relPath, boolean append) throws IOException {
		String norm = normalize(relPath);
		Entry e = resolve(norm);
		if (e == null) {
			Uri created = createFileAt(norm);
			if (created == null) throw new IOException("SAF: cannot create: " + relPath);
			e = resolve(norm);
			if (e == null) throw new IOException("SAF: created but unresolvable: " + relPath);
		}
		if (e.isDir) throw new IOException("SAF: is a directory: " + relPath);
		// "rwt" truncates reliably; plain "w" leaves stale trailing bytes on some providers.
		ParcelFileDescriptor pfd = cr().openFileDescriptor(e.uri, append ? "wa" : "rwt");
		if (pfd == null) throw new IOException("SAF: cannot open descriptor: " + relPath);
		return new PfdOutputStream(pfd);
	}

	/** FileOutputStream that also closes its ParcelFileDescriptor. */
	private static class PfdOutputStream extends FileOutputStream {
		private final ParcelFileDescriptor pfd;

		PfdOutputStream(ParcelFileDescriptor pfd) {
			super(pfd.getFileDescriptor());
			this.pfd = pfd;
		}

		@Override
		public void close() throws IOException {
			try {
				super.close();
			} finally {
				try { pfd.close(); } catch (IOException ignored) {}
			}
		}
	}

	// ─── Creation / deletion ─────────────────────────────────────────────────

	/** Create (or find) the directory at relPath, creating parents as needed.
	 *  Returns its Uri or null. */
	public Uri createDirAt(String relPath) {
		Uri tree = treeUri;
		if (tree == null) return null;
		String norm = normalize(relPath);
		if (norm.isEmpty()) return resolve("").uri;
		try {
			String parentDocId = DocumentsContract.getTreeDocumentId(tree);
			String[] parts = norm.split("/");
			String pathSoFar = "";
			Uri dirUri = null;
			for (String part : parts) {
				pathSoFar = pathSoFar.isEmpty() ? part : pathSoFar + "/" + part;
				Entry child = queryChild(tree, parentDocId, part);
				if (child == null) {
					Uri parentUri = DocumentsContract.buildDocumentUriUsingTree(tree, parentDocId);
					dirUri = DocumentsContract.createDocument(cr(), parentUri,
							DocumentsContract.Document.MIME_TYPE_DIR, part);
					if (dirUri == null) return null;
					child = new Entry(dirUri, true, 0, System.currentTimeMillis());
					synchronized (index) {
						index.put(pathSoFar, child);
					}
				} else if (!child.isDir) {
					return null;
				} else {
					dirUri = child.uri;
				}
				parentDocId = DocumentsContract.getDocumentId(child.uri);
			}
			return dirUri;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** Create an empty file at relPath (parents included). Returns its Uri or null. */
	public Uri createFileAt(String relPath) {
		Uri tree = treeUri;
		if (tree == null) return null;
		String norm = normalize(relPath);
		if (norm.isEmpty()) return null;
		try {
			String parentRel = norm.contains("/") ? norm.substring(0, norm.lastIndexOf('/')) : "";
			String fileName = norm.contains("/") ? norm.substring(norm.lastIndexOf('/') + 1) : norm;
			Uri parentUri;
			if (parentRel.isEmpty()) {
				parentUri = DocumentsContract.buildDocumentUriUsingTree(tree,
						DocumentsContract.getTreeDocumentId(tree));
			} else {
				parentUri = createDirAt(parentRel);
				if (parentUri == null) return null;
			}
			// Keep the exact filename. Android's FileSystemProvider rewrites the
			// name when the supplied extension isn't canonical for the MIME type
			// (e.g. "text/xml" turns "map.tmx" into "map.tmx.xml"). ".tmx"/".tsx"
			// aren't registered extensions, so octet-stream leaves them untouched.
			String mimeType = "application/octet-stream";
			String lower = fileName.toLowerCase();
			if (lower.endsWith(".json")) mimeType = "application/json";
			else if (lower.endsWith(".png")) mimeType = "image/png";
			Uri newFile = DocumentsContract.createDocument(cr(), parentUri, mimeType, fileName);
			if (newFile == null) return null;
			// Some providers still rewrite the extension; rename back if so.
			// renameDocument needs API 24; below that the rewritten name stays.
			String actualName = queryDisplayName(newFile);
			if (actualName != null && !actualName.equals(fileName)
					&& android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
				try {
					Uri renamed = DocumentsContract.renameDocument(cr(), newFile, fileName);
					if (renamed != null) newFile = renamed;
				} catch (Exception re) {
					re.printStackTrace();
				}
			}
			synchronized (index) {
				index.put(norm, new Entry(newFile, false, 0, System.currentTimeMillis()));
			}
			return newFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String queryDisplayName(Uri uri) {
		Cursor cursor = null;
		try {
			cursor = cr().query(uri, new String[]{DocumentsContract.Document.COLUMN_DISPLAY_NAME},
					null, null, null);
			if (cursor != null && cursor.moveToFirst()) return cursor.getString(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) cursor.close();
		}
		return null;
	}

	/** Delete the document at relPath (recursive for directories, per SAF semantics). */
	public boolean delete(String relPath) {
		String norm = normalize(relPath);
		Entry e = resolve(norm);
		if (e == null) return false;
		try {
			boolean ok = DocumentsContract.deleteDocument(cr(), e.uri);
			if (ok) {
				synchronized (index) {
					index.remove(norm);
					// Drop any indexed descendants too
					List<String> stale = new ArrayList<String>();
					String prefix = norm + "/";
					for (String key : index.keySet()) {
						if (key.startsWith(prefix)) stale.add(key);
					}
					for (String key : stale) index.remove(key);
				}
			}
			return ok;
		} catch (Exception ex) {
			return false;
		}
	}

	// ─── Metadata / listing ──────────────────────────────────────────────────

	public boolean exists(String relPath) {
		return resolve(relPath) != null;
	}

	public boolean isDirectory(String relPath) {
		Entry e = resolve(relPath);
		return e != null && e.isDir;
	}

	public long length(String relPath) {
		Entry e = resolve(relPath);
		if (e == null) return 0;
		// Index metadata goes stale the moment a document is written — query live.
		long live = queryLong(e.uri, DocumentsContract.Document.COLUMN_SIZE);
		return live >= 0 ? live : e.length;
	}

	public long lastModified(String relPath) {
		Entry e = resolve(relPath);
		if (e == null) return 0;
		long live = queryLong(e.uri, DocumentsContract.Document.COLUMN_LAST_MODIFIED);
		return live >= 0 ? live : e.lastModified;
	}

	/** Live single-column query; -1 if unavailable. */
	private long queryLong(Uri uri, String column) {
		Cursor cursor = null;
		try {
			cursor = cr().query(uri, new String[]{column}, null, null, null);
			if (cursor != null && cursor.moveToFirst() && !cursor.isNull(0)) {
				return cursor.getLong(0);
			}
		} catch (Exception e) {
			// fall through
		} finally {
			if (cursor != null) cursor.close();
		}
		return -1;
	}

	/** Names of the direct children of the directory at relDir ("" = root). */
	public List<String> list(String relDir) {
		List<String> names = new ArrayList<String>();
		Uri tree = treeUri;
		if (tree == null) return names;
		String norm = normalize(relDir);
		Entry dir = resolve(norm);
		if (dir == null || !dir.isDir) return names;
		Cursor cursor = null;
		try {
			String parentDocId = DocumentsContract.getDocumentId(dir.uri);
			Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(tree, parentDocId);
			cursor = cr().query(childrenUri, new String[]{
					DocumentsContract.Document.COLUMN_DOCUMENT_ID,
					DocumentsContract.Document.COLUMN_MIME_TYPE,
					DocumentsContract.Document.COLUMN_DISPLAY_NAME,
					DocumentsContract.Document.COLUMN_SIZE,
					DocumentsContract.Document.COLUMN_LAST_MODIFIED
			}, null, null, null);
			if (cursor == null) return names;
			while (cursor.moveToNext()) {
				String docId = cursor.getString(0);
				boolean isDir = DocumentsContract.Document.MIME_TYPE_DIR.equals(cursor.getString(1));
				String name = cursor.getString(2);
				long size = cursor.isNull(3) ? 0 : cursor.getLong(3);
				long modified = cursor.isNull(4) ? 0 : cursor.getLong(4);
				names.add(name);
				String childRel = norm.isEmpty() ? name : norm + "/" + name;
				synchronized (index) {
					index.put(childRel, new Entry(
							DocumentsContract.buildDocumentUriUsingTree(tree, docId), isDir, size, modified));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) cursor.close();
		}
		return names;
	}

	/** All indexed file paths matching the given dot-extensions. */
	public List<String> listFilesByExtension(String[] extensions) {
		List<String> result = new ArrayList<String>();
		if (extensions == null) return result;
		synchronized (index) {
			for (Map.Entry<String, Entry> e : index.entrySet()) {
				if (e.getValue().isDir) continue;
				String lower = e.getKey().toLowerCase();
				for (String ext : extensions) {
					if (lower.endsWith(ext.toLowerCase())) {
						result.add(e.getKey());
						break;
					}
				}
			}
		}
		java.util.Collections.sort(result);
		return result;
	}
}
