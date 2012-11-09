package com.huawei.opencmpp.util;

import java.io.Serializable;

public class Pair<K, V> implements Serializable {
	private static final long serialVersionUID = 1L;

	private K first;

	private V second;

	public Pair() {
	}

	public Pair(K first, V second) {
		this.first = first;
		this.second = second;
	}

	public K getFirst() {
		return first;
	}

	public void setFirst(K first) {
		this.first = first;
	}

	public V getSecond() {
		return second;
	}

	public void setSecond(V second) {
		this.second = second;
	}

	public int hashCode() {
		long v = first == null ? 0L : first.hashCode();
		v += second == null ? 0L : second.hashCode();
		return (new Long(v)).hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof Pair) {
			Pair other = (Pair) o;
			if (first == null) {
				if (other.first != null)
					return false;
			} else if (first != other.first)
				return false;
			if (second == null) {
				if (other.second != null)
					return false;
			} else if (second != other.second)
				return false;
		}
		return false;
	}

}
