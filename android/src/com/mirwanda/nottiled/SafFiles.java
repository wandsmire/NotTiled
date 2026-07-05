package com.mirwanda.nottiled;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;

/**
 * Wraps the platform Files implementation so that absolute paths under
 * {@link SafFileHandle#ROOT} resolve to SAF-backed handles. Installed over
 * Gdx.files after initialize(); everything else delegates untouched.
 */
public class SafFiles implements Files {

	private final Files wrapped;
	private final SafDocumentStore store;

	public SafFiles(Files wrapped, SafDocumentStore store) {
		this.wrapped = wrapped;
		this.store = store;
	}

	@Override
	public FileHandle getFileHandle(String path, FileType type) {
		if (type == FileType.Absolute && SafFileHandle.isSafPath(path)) {
			return new SafFileHandle(store, path);
		}
		return wrapped.getFileHandle(path, type);
	}

	@Override
	public FileHandle classpath(String path) {
		return wrapped.classpath(path);
	}

	@Override
	public FileHandle internal(String path) {
		return wrapped.internal(path);
	}

	@Override
	public FileHandle external(String path) {
		return wrapped.external(path);
	}

	@Override
	public FileHandle absolute(String path) {
		if (SafFileHandle.isSafPath(path)) {
			return new SafFileHandle(store, path);
		}
		return wrapped.absolute(path);
	}

	@Override
	public FileHandle local(String path) {
		return wrapped.local(path);
	}

	@Override
	public String getExternalStoragePath() {
		return wrapped.getExternalStoragePath();
	}

	@Override
	public boolean isExternalStorageAvailable() {
		return wrapped.isExternalStorageAvailable();
	}

	@Override
	public String getLocalStoragePath() {
		return wrapped.getLocalStoragePath();
	}

	@Override
	public boolean isLocalStorageAvailable() {
		return wrapped.isLocalStorageAvailable();
	}
}
