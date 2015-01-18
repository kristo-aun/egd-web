package com.jc.math.factorial;

import com.jc.math.factorial.util.FactorialUtil;

import java.math.BigInteger;

/**
 * some uuseful static operations regarding factorials
 */
public final class JCFactorial {

	/**
	 * from n with k, how many combinations?
	 */
	public static BigInteger kcombinations(int n, int k) {
		BigInteger result = null;

		if (n >= 0 && k >= 0 && n >= k) {
			BigInteger a = FactorialUtil.factorial(n);
			BigInteger b = FactorialUtil.factorial(k);
			BigInteger c = FactorialUtil.factorial(n - k);

			result = a.divide(b.multiply(c));
		}

		return result;
	}

}
