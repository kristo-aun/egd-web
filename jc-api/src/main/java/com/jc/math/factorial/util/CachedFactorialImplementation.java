package com.jc.math.factorial.util;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author wwoody
 */
public class CachedFactorialImplementation implements FactorialAlgorithm {

	public static HashMap<Integer, BigInteger> cache = new HashMap<>();

	@Override
	public BigInteger factorial(int n) {
		BigInteger ret;

		if (n == 0) {
			return BigInteger.ONE;
		}
		if (null != (ret = cache.get(n))) {
			return ret;
		}
		ret = BigInteger.valueOf(n).multiply(factorial(n - 1));
		cache.put(n, ret);
		return ret;
	}
}
