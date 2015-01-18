package com.jc.math.statistics.combinations;

import java.math.BigInteger;

/**
 * frame for k-combination classes. uses CombinationGenerator for engine
 */
public abstract class KCombs {

	protected final CombinationGenerator cg;

	protected KCombs(int n, int k) {
		cg = new CombinationGenerator(n, k);
	}

	public abstract Object getNext();

	public void reset() {
		cg.reset();
	}

	public BigInteger getNumLeft() {
		return cg.getNumLeft();
	}

	public boolean hasMore() {
		return getNumLeft().compareTo(BigInteger.ZERO) == 1;
	}

	public BigInteger getTotal() {
		return cg.getTotal();
	}
}
