package com.jc.structure.grid;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @param <KeyType>
 * @param <ValueType>
 */
final class _BidirectionalMap<KeyType, ValueType> implements Map<KeyType,ValueType>, Serializable {

	private static final long serialVersionUID = 6560212098310408819L;
	private final Map<KeyType, ValueType> keyToValueMap = new LinkedHashMap<>();

	@Override
	public Collection<ValueType> values() {
		return keyToValueMap.values();
	}

	@Override
	public Set<Entry<KeyType, ValueType>> entrySet() {
		return keyToValueMap.entrySet();
	}

	@Override
	public Set<KeyType> keySet() {
		return keyToValueMap.keySet();
	}

	@Override
	public ValueType put(KeyType key, ValueType value) throws IllegalStateException {
		if (value == null) throw new IllegalArgumentException("BidirectionalMap.put: value == null");

        if (keyToValueMap.containsKey(key)) {
			throw new IllegalStateException("BidirectionalMap.put: topelt key ei ole lubatud: key=" + key + ", kvalue=" + keyToValueMap.get(key) + ", value=" + value);
		}
		if (keyToValueMap.containsValue(value)) {
			StringBuilder msg = new StringBuilder("BidirectionalMap.put: topelt value ei ole lubatud: ");

			msg.append("sisse tahetakse panna=").append(value);
			msg.append(", võti, kus objekt asub=").append(key);

			for (ValueType p : keyToValueMap.values()) {
				if (p.equals(value)) {
					msg.append(", juba mapis olev objekt=").append(value);
					break;
				}
			}

			throw new IllegalStateException(msg.toString());
		}
		keyToValueMap.put(key, value);
		return value;
	}

	@Override
	public void putAll(Map<? extends KeyType, ? extends ValueType> m) {
		keyToValueMap.putAll(m);
	}

	@Override
	public ValueType remove(Object key) {
		return keyToValueMap.remove(key);
	}

	public KeyType removeByValue(ValueType value) {
		KeyType key = null;
		for (Entry<KeyType, ValueType> p : keyToValueMap.entrySet()) {
			if (p.getValue().equals(value)) key = p.getKey();
		}
		return keyToValueMap.remove(key) != null ? key : null;
	}

	@Override
	public boolean containsKey(Object key) {
		return keyToValueMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return keyToValueMap.containsValue(value);
	}

	public KeyType getKey(ValueType value) throws NullPointerException {
		for (Entry<KeyType, ValueType> p : keyToValueMap.entrySet()) {
			if (p.getValue().equals(value)) return p.getKey();
		}
		throw new NullPointerException("BidirectionalMap.getKey");
	}

	@Override
	public ValueType get(Object key) {
		return keyToValueMap.get(key);
	}

	@Override
	public int size() {
		return keyToValueMap.size();
	}

	@Override
	public boolean isEmpty() {
		return keyToValueMap.isEmpty();
	}

	@Override
	public void clear() {
		keyToValueMap.clear();
	}

	public String toString() {
		return "BidirectionalMap{" +
				"keyToValueMap=" + keyToValueMap +
				'}';
	}
}