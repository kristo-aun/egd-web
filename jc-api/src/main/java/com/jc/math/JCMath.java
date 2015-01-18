package com.jc.math;

import java.math.BigDecimal;

public final class JCMath {

	/**
	 * true if lower < value < upper
	 */
	public static boolean isBetween(double lower, double value, double upper) {
		boolean result = false;
		if (lower < value && value < upper) {
			result = true;
		}
		return result;
	}

	/**
	 * Sums up all values that are not null. If all parameters are null, returns 0
	 */
	public static int intSum(Integer... a) {
		int result = 0;
		for (Integer item : a) {
			if (item != null) {
				result += item;
			}
		}
		return result;
	}

	/**
	 * Sums up all values that are not null. Returns null if all parameters are null.
	 */
	public static Integer integerSum(Integer... a) {
		Integer result = null;
		for (Integer item : a) {
			if (item != null) {
				if (result == null) result = 0;
				result += item;
			}
		}
		return result;
	}

	public static BigDecimal bigDecimalSum(BigDecimal... a) {
		BigDecimal result = null;
		for (BigDecimal item : a) {
			if (item != null) {
				if (result == null) result = BigDecimal.ZERO;
				result = result.add(item);
			}
		}
		return result;
	}

	public static Integer negate(Integer num) {
		return num != null ? -num : null;
	}
}