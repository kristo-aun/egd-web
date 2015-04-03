package ee.esutoniagodesu.util.lang.alphab;

public enum EstAlphabet {
	a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, pq, r, s, š, z, ž, t, u, v, w, õ, ä, ö, ü, x, y;

	public static int indexOf(EstAlphabet a) {
		int i = 0;
		for (EstAlphabet item : EstAlphabet.values()) {
			if (a.equals(item)) {
				return i;
			}
			i++;
		}

		return -1;
	}

	public static boolean hasEst(String value) {
		String lower = value.toLowerCase();
		for (EstAlphabet p : values()) {
			if (lower.contains(p.name())) return true;
		}
		return false;
	}

	public static boolean isEst(char c) {
		String arr[] = new String[]{
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
				"o", "p", "q", "r", "s", "š", "z", "ž", "t", "u", "v", "w", "õ", "ä", "ö", "ü", "x", "y",
				"\t", "\n", " ", ",", ";", ".", "_", "-",
				"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
		};

		for (String p : arr) {
			if (String.valueOf(c).toLowerCase().equals(p)) return true;
		}
		return false;
	}
}
