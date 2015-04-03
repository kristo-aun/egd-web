package ee.esutoniagodesu.util.lang.alphab;

public enum LatinAlphabet {
	a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;

	public static int indexOf(LatinAlphabet a) {
		int i = 0;
		for (LatinAlphabet item : LatinAlphabet.values()) {
			if (a.equals(item)) {
				return i;
			}
			i++;
		}

		return -1;
	}

	public static boolean hasLatin(String value) {
		String lower = value.toLowerCase();
		for (LatinAlphabet p : values()) {
			if (lower.contains(p.name())) return true;
		}
		return false;
	}
}
