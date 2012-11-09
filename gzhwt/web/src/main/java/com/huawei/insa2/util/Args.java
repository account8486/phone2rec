package com.huawei.insa2.util;

import java.util.HashMap;
import java.util.Map;

public class Args {

	public Args() {
		this(((Map) (new HashMap())));
	}

	public Args(Map theArgs) {
		if (theArgs == null) {
			throw new NullPointerException("argument is null");
		} else {
			args = theArgs;
			return;
		}
	}

	public String get(String key, String def) {
		try {
			String s = args.get(key).toString();
			return s;
		} catch (Exception ex) {
			String s1 = def;
			return s1;
		}
	}

	public int get(String key, int def) {
		try {
			int i = Integer.parseInt(args.get(key).toString());
			return i;
		} catch (Exception ex) {
			int j = def;
			return j;
		}
	}

	public long get(String key, long def) {
		try {
			long l = Long.parseLong(args.get(key).toString());
			return l;
		} catch (Exception ex) {
			long l1 = def;
			return l1;
		}
	}

	public float get(String key, float def) {
		try {
			float f = Float.parseFloat(args.get(key).toString());
			return f;
		} catch (Exception ex) {
			float f1 = def;
			return f1;
		}
	}

	public boolean get(String key, boolean def) {
		try {
			boolean flag = "true".equals(args.get(key));
			return flag;
		} catch (Exception ex) {
			boolean flag1 = def;
			return flag1;
		}
	}

	public Object get(String key, Object def) {
		Object obj3;
		try {
			Object obj = args.get(key);
			if (obj == null) {
				Object obj1 = def;
				return obj1;
			} else {
				Object obj2 = obj;
				return obj2;
			}
		} catch (Exception ex) {
			obj3 = def;
		}
		return obj3;
	}

	public Args set(String key, Object value) {
		if (locked) {
			throw new UnsupportedOperationException(
					"Args have locked,can modify");
		} else {
			args.put(key, value);
			return this;
		}
	}

	public Args set(String key, int value) {
		if (locked) {
			throw new UnsupportedOperationException(
					"Args have locked,can modify");
		} else {
			args.put(key, new Integer(value));
			return this;
		}
	}

	public Args set(String key, boolean value) {
		if (locked) {
			throw new UnsupportedOperationException(
					"Args have locked,can modify");
		} else {
			args.put(key, new Boolean(value));
			return this;
		}
	}

	public Args set(String key, long value) {
		if (locked) {
			throw new UnsupportedOperationException(
					"Args have locked,can modify");
		} else {
			args.put(key, new Long(value));
			return this;
		}
	}

	public Args set(String key, float value) {
		if (locked) {
			throw new UnsupportedOperationException(
					"Args have locked,can modify");
		} else {
			args.put(key, new Float(value));
			return this;
		}
	}

	public Args set(String key, double value) {
		if (locked) {
			throw new UnsupportedOperationException(
					"Args have locked,can modify");
		} else {
			args.put(key, new Double(value));
			return this;
		}
	}

	public Args lock() {
		locked = true;
		return this;
	}

	public String toString() {
		return args.toString();
	}

	public static final Args EMPTY = (new Args()).lock();

	boolean locked;

	Map args;

}
