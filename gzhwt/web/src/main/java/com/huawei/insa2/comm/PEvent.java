package com.huawei.insa2.comm;

import java.lang.reflect.Field;
import java.util.HashMap;

public class PEvent {

	public PEvent(int type, PLayer source, Object data) {
		this.type = type;
		this.source = source;
		this.data = data;
	}

	public PLayer getSource() {
		return source;
	}

	public int getType() {
		return type;
	}

	public Object getData() {
		return data;
	}

	public String toString() {
		return "PEvent:source=" + source + ",type="
				+ names.get(new Integer(type)) + ",data=" + data;
	}

	public static final int CREATED = 1;

	public static final int CHILD_CREATED = 2;

	public static final int DELETED = 4;

	public static final int MESSAGE_SEND_SUCCESS = 8;

	public static final int MESSAGE_SEND_FAIL = 16;

	public static final int MESSAGE_DISPATCH_SUCCESS = 32;

	public static final int MESSAGE_DISPATCH_FAIL = 64;

	static final HashMap names;

	protected PLayer source;

	protected int type;

	protected Object data;

	static {
		names = new HashMap();
		try {
			Field f[] = (com.huawei.insa2.comm.PEvent.class).getFields();
			for (int i = 0; i < f.length; i++) {
				String name = f[i].getName();
				Object id = f[i].get(null);
				names.put(id, name);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
