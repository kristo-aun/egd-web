package com.jc.math.numerology;

/**
 * Pentagonal Pn=n(3n−1)/2 | 1, 5, 12, 22, 35, ...
 *
 * @author scylla
 */
public final class PentagonalNumber {
	public static boolean isPentagonal(int num) {
		double check = (Math.sqrt((24 * num) + 1) + 1) / 6;
		return check == (int) check;
	}

	public static boolean isPentagonal(long num) {
		double check = (Math.sqrt((24 * num) + 1) + 1) / 6;
		return check == (long) check;
	}

	public static int getPentagonal(int num) {
		return (num * ((3 * num) - 1)) / 2;
	}
}