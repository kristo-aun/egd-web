package com.jc.math.numerology;

public final class OddCompositeNumber {
	public static boolean isOddCompositeNumber(long number) {
		return number % 2 != 0 && !PrimeNumber.isPrime(number);
	}
}