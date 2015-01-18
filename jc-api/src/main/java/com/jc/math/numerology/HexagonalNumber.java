package com.jc.math.numerology;

public final class HexagonalNumber {
	public static boolean isHexagonal(int num) {
		double check = (Math.sqrt((8 * num) + 1) + 1) / 4;
		return check == (int) check;
	}

	public static boolean isHexagonal(long num) {
		double check = (Math.sqrt((8 * num) + 1) + 1) / 4;
		return check == (long) check;
	}

	public static int getHexagonal(int num) {
		return (num * ((2 * num) - 1));
	}
}