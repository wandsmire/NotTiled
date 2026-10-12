package com.mirwanda.nottiled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Per terrain-corner stacks of the terrain ids a stamp covered, so erasing that stamp restores them.
 * Each entry is {under, over}: the id that was there and the id written on top of it.
 * Keys are (tileKey << 2 | corner), tileKey being a gid (macro editor) or a grid index (tileset editor).
 */
public class TerrainUnderlay {
	private static final int MAX_DEPTH = 32;
	private final HashMap<Long, ArrayList<int[]>> stacks = new HashMap<Long, ArrayList<int[]>>();

	public interface CornerLookup {
		int valueAt(long key);
	}

	public static long key(long tileKey, int corner) {
		return (tileKey << 2) | corner;
	}

	public void clear() {
		stacks.clear();
	}

	public boolean isEmpty() {
		return stacks.isEmpty();
	}

	/** Records that {@code over} is being written on top of {@code current}. */
	public void push(long key, int current, int over) {
		ArrayList<int[]> st = stacks.get(key);
		if (st != null && !st.isEmpty() && st.get(st.size() - 1)[1] != current) {
			stacks.remove(key);
			st = null;
		}
		if (current == -1)
			return;
		if (st == null) {
			st = new ArrayList<int[]>();
			stacks.put(key, st);
		}
		st.add(new int[] { current, over });
		if (st.size() > MAX_DEPTH)
			st.remove(0);
	}

	/** Returns the id to restore when erasing {@code current}, or -1; stale stacks are dropped. */
	public int peel(long key, int current) {
		ArrayList<int[]> st = stacks.get(key);
		if (st == null || st.isEmpty()) {
			stacks.remove(key);
			return -1;
		}
		int[] top = st.get(st.size() - 1);
		if (top[1] != current) {
			stacks.remove(key);
			return -1;
		}
		st.remove(st.size() - 1);
		if (st.isEmpty())
			stacks.remove(key);
		return top[0];
	}

	public void prune(CornerLookup lookup) {
		Iterator<Map.Entry<Long, ArrayList<int[]>>> it = stacks.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Long, ArrayList<int[]>> e = it.next();
			ArrayList<int[]> st = e.getValue();
			if (st.isEmpty() || st.get(st.size() - 1)[1] != lookup.valueAt(e.getKey()))
				it.remove();
		}
	}

	/** Renumbers ids after terrain {@code deletedIdx} was removed; entries touching it are dropped. */
	public void remapAfterDelete(int deletedIdx) {
		Iterator<Map.Entry<Long, ArrayList<int[]>>> it = stacks.entrySet().iterator();
		while (it.hasNext()) {
			ArrayList<int[]> st = it.next().getValue();
			for (int i = st.size() - 1; i >= 0; i--) {
				int[] en = st.get(i);
				if (en[0] == deletedIdx || en[1] == deletedIdx) {
					st.remove(i);
					continue;
				}
				if (en[0] > deletedIdx)
					en[0]--;
				if (en[1] > deletedIdx)
					en[1]--;
			}
			if (st.isEmpty())
				it.remove();
		}
	}

	public TerrainUnderlay copy() {
		TerrainUnderlay c = new TerrainUnderlay();
		for (Map.Entry<Long, ArrayList<int[]>> e : stacks.entrySet()) {
			ArrayList<int[]> st = new ArrayList<int[]>();
			for (int[] en : e.getValue())
				st.add(new int[] { en[0], en[1] });
			c.stacks.put(e.getKey(), st);
		}
		return c;
	}

	/** Entries as "tileKey:corner=under>over,under>over". */
	public List<String> toStrings() {
		List<String> out = new ArrayList<String>();
		for (Map.Entry<Long, ArrayList<int[]>> e : stacks.entrySet()) {
			if (e.getValue().isEmpty())
				continue;
			StringBuilder sb = new StringBuilder();
			long k = e.getKey();
			sb.append(k >> 2).append(':').append(k & 3).append('=');
			ArrayList<int[]> st = e.getValue();
			for (int i = 0; i < st.size(); i++) {
				if (i > 0)
					sb.append(',');
				sb.append(st.get(i)[0]).append('>').append(st.get(i)[1]);
			}
			out.add(sb.toString());
		}
		return out;
	}

	public void fromStrings(List<String> in) {
		clear();
		if (in == null)
			return;
		for (String s : in) {
			try {
				int eq = s.indexOf('=');
				int colon = s.indexOf(':');
				if (eq < 0 || colon < 0 || colon > eq)
					continue;
				long tileKey = Long.parseLong(s.substring(0, colon).trim());
				int corner = Integer.parseInt(s.substring(colon + 1, eq).trim());
				if (corner < 0 || corner > 3)
					continue;
				ArrayList<int[]> st = new ArrayList<int[]>();
				for (String part : s.substring(eq + 1).split(",")) {
					int gt = part.indexOf('>');
					if (gt < 0)
						continue;
					int u = Integer.parseInt(part.substring(0, gt).trim());
					int o = Integer.parseInt(part.substring(gt + 1).trim());
					if (u >= 0 && o >= 0)
						st.add(new int[] { u, o });
				}
				if (!st.isEmpty())
					stacks.put(key(tileKey, corner), st);
			} catch (NumberFormatException ignored) {
			}
		}
	}
}
