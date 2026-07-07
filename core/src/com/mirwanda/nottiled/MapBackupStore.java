package com.mirwanda.nottiled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Debounced rolling TMX backups under NotTiled/Backups/&lt;map-key&gt;/.
 */
public final class MapBackupStore {

    private static final String BACKUPS_DIR = "NotTiled/Backups";
    private static final String PREF_LAST_BACKUP_PREFIX = "lastBackup_";

    private MapBackupStore() {
    }

    public static String mapKey(String absolutePath) {
        return Integer.toHexString(absolutePath.hashCode());
    }

    public static FileHandle backupDir(String basepath, String absoluteMapPath) {
        FileHandle root = Gdx.files.absolute(basepath + BACKUPS_DIR);
        if (!root.exists())
            root.mkdirs();
        FileHandle dir = Gdx.files.absolute(basepath + BACKUPS_DIR + "/" + mapKey(absoluteMapPath));
        if (!dir.exists())
            dir.mkdirs();
        return dir;
    }

    /**
     * Writes temp file over destination (same directory rename when possible).
     *
     * Local destinations get delete + rename (atomic on the same filesystem).
     * SAF destinations are overwritten in place via a truncating stream copy —
     * never deleted first, so a crash mid-commit can't erase the user's file
     * and the provider document identity survives every save.
     */
    public static boolean commitAtomic(FileHandle tmp, FileHandle dest) {
        if (tmp == null || !tmp.exists() || dest == null)
            return false;
        FileHandle parent = dest.parent();
        if (parent != null && !parent.exists())
            parent.mkdirs();

        boolean destFileBacked;
        try {
            dest.file();
            destFileBacked = true;
        } catch (Exception safHandle) {
            destFileBacked = false;
        }

        if (destFileBacked && (!dest.exists() || dest.delete())) {
            try {
                if (tmp.file().renameTo(dest.file()))
                    return dest.exists() && dest.length() > 0;
            } catch (Exception ignored) {
            }
        }
        try {
            tmp.copyTo(dest);
            tmp.delete();
            return dest.exists() && dest.length() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean looksLikeTmx(FileHandle fh, XmlPullParserFactory factory) {
        if (fh == null || !fh.exists() || fh.length() < 16)
            return false;
        FileInputStreamHolder stream = null;
        try {
            XmlPullParserFactory f = factory != null ? factory : XmlPullParserFactory.newInstance();
            XmlPullParser parser = f.newPullParser();
            stream = new FileInputStreamHolder(fh);
            parser.setInput(stream.stream, "UTF-8");
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG && "map".equals(parser.getName()))
                    return true;
                event = parser.next();
            }
        } catch (Exception ignored) {
        } finally {
            if (stream != null)
                stream.closeQuietly();
        }
        return false;
    }

    public static void maybeBackup(String basepath, String absoluteMapPath, FileHandle savedTmx,
            int maxBackups, int minIntervalMinutes, boolean enabled, Preferences prefs) {
        if (!enabled || savedTmx == null || !savedTmx.exists())
            return;
        if (!"tmx".equalsIgnoreCase(savedTmx.extension()))
            return;
        String key = mapKey(absoluteMapPath);
        long now = System.currentTimeMillis();
        long minGap = Math.max(1, minIntervalMinutes) * 60L * 1000L;
        if (prefs != null) {
            long last = prefs.getLong(PREF_LAST_BACKUP_PREFIX + key, 0L);
            if (now - last < minGap)
                return;
        }
        FileHandle dir = backupDir(basepath, absoluteMapPath);
        String stamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US).format(new Date(now));
        FileHandle dest = Gdx.files.absolute(dir.path() + "/" + stamp + ".tmx");
        try {
            savedTmx.copyTo(dest);
        } catch (Exception e) {
            return;
        }
        if (prefs != null)
            prefs.putLong(PREF_LAST_BACKUP_PREFIX + key, now);
        prune(dir, maxBackups);
    }

    private static void prune(FileHandle dir, int maxBackups) {
        if (maxBackups < 1)
            maxBackups = 1;
        FileHandle[] files = dir.list(".tmx");
        if (files == null || files.length <= maxBackups)
            return;
        List<FileHandle> list = new ArrayList<>();
        Collections.addAll(list, files);
        Collections.sort(list, new Comparator<FileHandle>() {
            @Override
            public int compare(FileHandle a, FileHandle b) {
                return Long.compare(a.lastModified(), b.lastModified());
            }
        });
        int remove = list.size() - maxBackups;
        for (int i = 0; i < remove; i++) {
            // "original.tmx" is the pristine pre-NotTiled copy kept by the
            // compatibility guard — it must survive the rolling window.
            if ("original.tmx".equals(list.get(i).name()))
                continue;
            list.get(i).delete();
        }
    }

    public static List<FileHandle> listBackups(String basepath, String absoluteMapPath) {
        FileHandle dir = backupDir(basepath, absoluteMapPath);
        FileHandle[] files = dir.list(".tmx");
        List<FileHandle> list = new ArrayList<>();
        if (files != null)
            Collections.addAll(list, files);
        Collections.sort(list, new Comparator<FileHandle>() {
            @Override
            public int compare(FileHandle a, FileHandle b) {
                return Long.compare(b.lastModified(), a.lastModified());
            }
        });
        return list;
    }

    public static boolean restoreTo(FileHandle backup, FileHandle destination) {
        if (backup == null || !backup.exists() || destination == null)
            return false;
        FileHandle tmp = Gdx.files.absolute(destination.parent().path() + "/"
                + destination.nameWithoutExtension() + ".nottiled.tmp");
        if (tmp.exists())
            tmp.delete();
        try {
            backup.copyTo(tmp);
        } catch (Exception e) {
            return false;
        }
        return commitAtomic(tmp, destination);
    }

    private static final class FileInputStreamHolder {
        final java.io.InputStream stream;

        FileInputStreamHolder(FileHandle fh) throws java.io.IOException {
            stream = fh.read();
        }

        void closeQuietly() {
            try {
                stream.close();
            } catch (Exception ignored) {
            }
        }
    }
}
