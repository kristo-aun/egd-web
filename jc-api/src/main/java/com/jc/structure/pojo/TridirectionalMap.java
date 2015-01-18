package com.jc.structure.pojo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class TridirectionalMap<X, Y, Z> {

	private final BidirectionalMap<X, Y> xy = new BidirectionalMap<>();
	private final BidirectionalMap<X, Z> xz = new BidirectionalMap<>();
	private final BidirectionalMap<Y, Z> yz = new BidirectionalMap<>();

	public Collection<X> getX() {
		return xy.keys();
	}

	public Collection<Y> getY() {
		return xy.values();
	}

	public Collection<Z> getZ() {
		return xz.values();
	}

	public void put(X x, Y y, Z z) {
		xy.put(x, y);
		xz.put(x, z);
		yz.put(y, z);
	}

	public Map<Y, Z> removeByX(X x) {

		Y removedY = xy.removeByKey(x);
		Z removedZ = xz.removeByKey(x);
		yz.removeByKey(removedY);

		Map<Y, Z> result = new HashMap<>();
		result.put(removedY, removedZ);
		return result;
	}

	public Map<X, Z> removeByY(Y y) {

		X removedX = xy.removeByValue(y);
		Z removedZ = yz.removeByKey(y);
		xz.removeByKey(removedX);

		Map<X, Z> result = new HashMap<>();
		result.put(removedX, removedZ);
		return result;
	}

	public Map<X, Y> removeByZ(Z z) {

		X removedX = xz.removeByValue(z);
		Y removedY = yz.removeByValue(z);
		xy.removeByKey(removedX);

		Map<X, Y> result = new HashMap<>();
		result.put(removedX, removedY);
		return result;
	}

	public boolean containsX(X x) {
		return xy.containsKey(x);
	}

	public boolean containsY(Y y) {
		return xy.containsValue(y);
	}

	public X getXbyY(Y y) {
		return xy.getKey(y);
	}

	public X getXbyZ(Z z) {
		return xz.getKey(z);
	}

	public Y getYbyX(X x) {
		return xy.get(x);
	}

	public Y getYbyZ(Z z) {
		return yz.getKey(z);
	}

	public Z getZbyX(X x) {
		return xz.get(x);
	}

	public Z getZbyY(Y y) {
		return yz.get(y);
	}
}