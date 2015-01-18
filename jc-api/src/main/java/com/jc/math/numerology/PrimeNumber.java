package com.jc.math.numerology;

import java.util.ArrayList;
import java.util.List;

public final class PrimeNumber {
	/**
	 * Lars Vogel Version 0.3 Copyright © 2009 Lars Vogel 08.09.2009
	 */
	public static List<Integer> primeFactors(int number) {
		int n = number;
		List<Integer> factors = new ArrayList<>();
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
				factors.add(i);
				n /= i;
			}
		}
		return factors;
	}

	public static boolean isPrime(long n) {
		boolean prime = true;
		for (long i = 3; i <= Math.sqrt(n); i += 2)
			if (n % i == 0) {
				prime = false;
				break;
			}
		if ((n % 2 != 0 && prime && n > 2) || n == 2) {
			return true;
		} else {
			return false;
		}
	}
}
