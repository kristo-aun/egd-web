package ee.esutoniagodesu.util.lang.alphab;

public final class JCCharacter {

	public static boolean isKanji(char ch) {
		return getUnicodeBlock(ch).equals(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
	}

	public static boolean isHiragana(char ch) {
		return getUnicodeBlock(ch).equals(Character.UnicodeBlock.HIRAGANA);
	}

	public static boolean isKatakana(char ch) {
		return getUnicodeBlock(ch).equals(Character.UnicodeBlock.KATAKANA);
	}

	public static boolean isLatin(char ch) {
		return getUnicodeBlock(ch).equals(Character.UnicodeBlock.BASIC_LATIN);
	}

	public static Character.UnicodeBlock getUnicodeBlock(char ch) {
		return Character.UnicodeBlock.of(ch);
	}
}
