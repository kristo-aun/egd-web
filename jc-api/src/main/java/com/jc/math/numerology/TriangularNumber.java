package com.jc.math.numerology;

public final class TriangularNumber {
	public static boolean isTriangle(int num) {

		double check = -0.5 + Math.sqrt(0.25 + 2 * num);
		if (check == (int) check) {
			return true;
		}

		return false;
	}

	public static boolean isTriangle(long num) {

		double check = -0.5 + Math.sqrt(0.25 + 2 * num);
		if (check == (long) check) {
			return true;
		}

		return false;
	}

	public static int getTriangle(int num) {
		return (num * (num + 1)) / 2;
	}
}
