package com.huawei.insa2.util;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

public class Debug {

	private Debug() {
	}

	public static final void myAssert(boolean condition) {
		if (!condition)
			throw new AssertFailed();
		else
			return;
	}

	public static void setDumpStream(OutputStream os) {
		out = new PrintWriter(os);
	}

	public static void setDumpStream(Writer w) {
		out = new PrintWriter(w);
	}

	public static void setDumpIndent(String indent) {
		indentString = indent;
	}

	public static String getDumpIndent() {
		return indentString;
	}

	public static final void dump() {
		out.println(dumpHead());
		out.flush();
	}

	public static final void dump(int i) {
		out.println(String.valueOf(dumpHead()) + String.valueOf(i));
		out.flush();
	}

	public static final void dump(long l) {
		out.println(String.valueOf(dumpHead()) + String.valueOf(l));
		out.flush();
	}

	public static final void dump(float f) {
		out.println(String.valueOf(dumpHead()) + String.valueOf(f));
		out.flush();
	}

	public static final void dump(double d) {
		out.println(String.valueOf(dumpHead()) + String.valueOf(d));
		out.flush();
	}

	public static final void dump(boolean b) {
		out.println(String.valueOf(dumpHead()) + String.valueOf(b));
		out.flush();
	}

	public static final void dump(char ch) {
		out.println(String.valueOf(dumpHead()) + String.valueOf(ch));
		out.flush();
	}

	public static final void dump(byte data[], int offset, int length) {
		dump(dumpHead(), data, offset, length);
	}

	public static final void dump(byte data[]) {
		dump(dumpHead(), data);
	}

	public static final void dump(Object obj) {
		dump(dumpHead(), 3, new Vector(), obj);
	}

	public static final void dump(Object obj, String prefix) {
		dump(String.valueOf(prefix) + String.valueOf(dumpHead()), 3,
				new Vector(), obj);
	}

	public static final void dump(Object obj, int depth) {
		dump(dumpHead(), depth, new Vector(), obj);
	}

	private static final void dump(String prefix, int depth,
			Vector checkCircuit, Vector v) {
		if (v == null) {
			dump(prefix, "null");
			return;
		}
		dumpBegin(prefix, checkCircuit, v);
		for (int i = 0; i < v.size(); i++) {
			Object item = v.elementAt(i);
			StringBuffer itemPrefix = new StringBuffer();
			itemPrefix.append(indent(prefix));
			itemPrefix.append('[');
			itemPrefix.append(i);
			itemPrefix.append("] ");
			itemPrefix.append(formatClassName(item.getClass(), item));
			itemPrefix.append(" @");
			itemPrefix.append(System.identityHashCode(item));
			dump(itemPrefix.toString(), depth, checkCircuit, item);
		}

		dumpEnd(prefix, checkCircuit, v);
	}

	private static final void dumpServletRequest(String prefix, Object request) {
		try {
			if (request == null) {
				dump(prefix, "null");
				return;
			}
			dumpBegin(prefix, new Vector(), request);
			Class c = request.getClass();
			Method m1 = null;
			m1 = c.getMethod("getParameterNames", new Class[0]);
			String name;
			StringBuffer sb;
			for (Enumeration e = (Enumeration) m1
					.invoke(request, new Object[0]); e.hasMoreElements(); dump(
					indent(prefix), String
							.valueOf(String.valueOf((new StringBuffer(String
									.valueOf(String.valueOf(name)))).append(
									" = ").append(sb))))) {
				name = e.nextElement().toString();
				Method m2 = c.getMethod("getParameterValues",
						new Class[] { java.lang.String.class });
				String values[] = (String[]) m2.invoke(request,
						new Object[] { name });
				sb = new StringBuffer();
				for (int i = 0; i < values.length; i++) {
					sb.append(values[i]);
					if (i != values.length - 1)
						sb.append(" ; ");
				}

			}

			dumpEnd(prefix, new Vector(), request);
		} catch (Exception ex) {
			ex.printStackTrace(out);
		}
	}

	private static final void dump(String prefix, int depth,
			Vector checkCircuit, Enumeration e) {
		if (e == null) {
			dump(prefix, "null");
			return;
		}
		dumpBegin(prefix, checkCircuit, e);
		int i = 0;
		for (; e.hasMoreElements(); dump(String.valueOf(String
				.valueOf((new StringBuffer(String.valueOf(String
						.valueOf(indent(prefix))))).append('[').append(i++)
						.append("] "))), depth, checkCircuit, e.nextElement()))
			;
		dumpEnd(prefix, checkCircuit, e);
	}

	private static final void dump(String prefix, Throwable t) {
		if (t == null) {
			dump(prefix, "null");
			return;
		} else {
			dumpBegin(prefix, new Vector(), t);
			t.printStackTrace(out);
			dumpEnd(prefix, new Vector(), t);
			return;
		}
	}

	private static final void dump(String prefix, byte data[], int offset,
			int length) {
		if (data == null) {
			dump(prefix, "null");
			return;
		}
		if (offset < 0 || data.length < offset + length) {
			dump(prefix, String.valueOf(String.valueOf((new StringBuffer(
					"IndexOutOfBounds:data.length=")).append(data.length)
					.append(" offset=").append(offset).append(" length=")
					.append(length))));
			return;
		}
		dumpBegin(prefix, new Vector(), data);
		int end = offset + length;
		dump(indent(prefix),
				"[HEX]  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f | 0123456789abcdef");
		dump(indent(prefix),
				"------------------------------------------------------------------------");
		for (int i = offset; i < end; i += 16) {
			byte row[] = { 48, 48, 48, 48, 58, 32, 48, 48, 32, 48, 48, 32, 48,
					48, 32, 48, 48, 32, 48, 48, 32, 48, 48, 32, 48, 48, 32, 48,
					48, 32, 48, 48, 32, 48, 48, 32, 48, 48, 32, 48, 48, 32, 48,
					48, 32, 48, 48, 32, 48, 48, 32, 48, 48, 32, 124, 32, 46,
					46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46 };
			setHex(row, 3, i);
			for (int j = i; j < i + 16; j++)
				if (j < end) {
					int b = data[j];
					if (b < 0)
						b += 256;
					setHex(row, 7 + (j - i) * 3, b);
					if (b >= 32 && b < 127)
						row[(56 + j) - i] = (byte) b;
				} else {
					row[6 + (j - i) * 3] = 32;
					row[7 + (j - i) * 3] = 32;
					row[56 + (j - i)] = 32;
				}

			dump(indent(prefix), new String(row));
		}

		dumpEnd(prefix, new Vector(), data);
	}

	private static final void dump(String prefix, byte b[]) {
		dump(prefix, b, 0, b.length <= 512 ? b.length : 512);
	}

	private static final void dump(String prefix, int depth,
			Vector checkCircuit, Map map) {
		if (map == null)
			dump(prefix, "null");
		dumpBegin(prefix, checkCircuit, map);
		for (Iterator i = map.keySet().iterator(); i.hasNext();) {
			Object key = i.next();
			Object value = map.get(key);
			if (value instanceof String)
				dump(indent(prefix), String.valueOf(String
						.valueOf((new StringBuffer(String.valueOf(String
								.valueOf(key.toString())))).append(" = ")
								.append(value))));
			else
				dump(String.valueOf(String.valueOf((new StringBuffer(String
						.valueOf(String.valueOf(indent(prefix))))).append(
						key.toString()).append(" = "))), depth, checkCircuit,
						map.get(key));
		}

		dumpEnd(prefix, checkCircuit, map);
	}

	private static final void dump(String prefix, String str) {
		out.println(String.valueOf(prefix) + String.valueOf(str));
		out.flush();
	}

	private static final void dump(String prefix, int depth,
			Vector checkCircuit, Object objs[]) {
		if (objs == null) {
			dump(prefix, "null");
			return;
		}
		dumpBegin(prefix, checkCircuit, ((Object) (objs)));
		for (int i = 0; i < objs.length; i++)
			dump(String.valueOf(String.valueOf((new StringBuffer(String
					.valueOf(String.valueOf(indent(prefix))))).append('[')
					.append(i).append("] "))), depth, checkCircuit, objs[i]);

		dumpEnd(prefix, checkCircuit, ((Object) (objs)));
	}

	private static void dump(String prefix, int depth, Vector checkCircuit,
			Object obj) {
		if (obj == null) {
			dump(prefix, "null");
			return;
		}
		try {
			if ((obj instanceof String) || (obj instanceof Number)
					|| (obj instanceof Character) || (obj instanceof Boolean)) {
				dump(prefix, obj.toString());
				return;
			}
			if (checkCircuit
					.contains(new Integer(System.identityHashCode(obj)))) {
				StringBuffer sb = new StringBuffer();
				sb.append(formatClassName(obj.getClass(), obj));
				sb.append(" @");
				sb.append(System.identityHashCode(obj));
				sb.append(' ');
				dump(prefix, " {Circle recursion!}");
				return;
			}
			if (getDepth(prefix) > depth) {
				String str = String.valueOf(String.valueOf((new StringBuffer(
						String.valueOf(String.valueOf(formatClassName(obj
								.getClass(), obj))))).append(" @").append(
						System.identityHashCode(obj))));
				if (prefix.trim().endsWith(str.trim()))
					str = "";
				String toStr;
				try {
					toStr = obj.toString();
					if (toStr.indexOf('@') > 0)
						toStr = " {Stack overflow!}";
				} catch (StackOverflowError t) {
					toStr = " {Stack overflow!}";
				}
				dump(prefix, String.valueOf(str) + String.valueOf(toStr));
				return;
			}
			if (obj instanceof Vector) {
				dump(prefix, depth, checkCircuit, (Vector) obj);
				return;
			}
			if (obj instanceof Map) {
				dump(prefix, depth, checkCircuit, (Map) obj);
				return;
			}
			if (obj instanceof Enumeration) {
				dump(prefix, depth, checkCircuit, (Enumeration) obj);
				return;
			}
			if (obj instanceof Object[]) {
				dump(prefix, depth, checkCircuit, (Object[]) obj);
				return;
			}
			if (obj instanceof Throwable) {
				dump(prefix, (Throwable) obj);
				return;
			}
			if (obj instanceof byte[]) {
				dump(prefix, (byte[]) obj);
				return;
			}
			if (obj.getClass().isArray()) {
				int len = Array.getLength(obj);
				dumpBegin(prefix, checkCircuit, obj);
				StringBuffer content = new StringBuffer();
				for (int i = 0; i < len; i++) {
					content.append(fixLength(Array.get(obj, i).toString(), 4));
					if (i % 8 == 7 && i < len - 1)
						content.append(String.valueOf(lineSeparator)
								+ String.valueOf(indent(prefix)));
				}

				dump(indent(prefix), content.toString());
				dumpEnd(prefix, checkCircuit, obj);
				return;
			}
			if (Class.forName("javax.servlet.ServletRequest").isInstance(obj)) {
				dumpServletRequest(prefix, obj);
				return;
			}
		} catch (ClassNotFoundException classnotfoundexception) {
		}
		dumpBegin(prefix, checkCircuit, obj);
		for (Class c = obj.getClass(); c != null; c = c.getSuperclass()) {
			Field f[];
			try {
				f = c.getDeclaredFields();
			} catch (SecurityException ex2) {
				dump(indent(prefix),
						"Can't dump object member for security reason.");
				return;
			}
			for (int i = 0; i < f.length; i++) {
				String m = Modifier.toString(f[i].getModifiers());
				if (m.indexOf("static") > 0)
					continue;
				String n = f[i].getName();
				Object v = "[unkonwn]";
				try {
					f[i].setAccessible(true);
				} catch (SecurityException securityexception) {
				}
				try {
					v = f[i].get(obj);
					if (v != null)
						if (v instanceof String)
							v = String.valueOf(String
									.valueOf((new StringBuffer("\"")).append(v)
											.append('"')));
						else if (v instanceof Character) {
							char cv = ((Character) v).charValue();
							if (cv < ' ') {
								StringBuffer sbv = new StringBuffer();
								sbv.append("\\u");
								sbv.append(Integer.toHexString(cv));
								for (; sbv.length() < 6; sbv.insert(2, '0'))
									;
								v = sbv;
							}
							v = String.valueOf(String
									.valueOf((new StringBuffer("'")).append(v)
											.append('\'')));
						}
				} catch (Exception exception) {
				}
				Class ct = f[i].getType();
				String t = formatClassName(ct, v);
				dump(String.valueOf(String.valueOf((new StringBuffer(String
						.valueOf(String.valueOf(indent(prefix))))).append(
						String.valueOf(
								String.valueOf((new StringBuffer(String
										.valueOf(String.valueOf(m)))).append(
										' ').append(t).append(' ').append(n)))
								.trim()).append(" = "))), depth, checkCircuit,
						v);
			}

		}

		dumpEnd(prefix, checkCircuit, obj);
	}

	private static void dumpBegin(String prefix, Vector checkCircuit, Object obj) {
		String className = formatClassName(obj.getClass(), obj);
		int address = System.identityHashCode(obj);
		checkCircuit.addElement(new Integer(address));
		if (obj instanceof Array)
			className = String.valueOf(String.valueOf((new StringBuffer(String
					.valueOf(String.valueOf(className.substring(2))))).append(
					'[').append(Array.getLength(obj)).append("] ")));
		if (className.startsWith("java.lang."))
			className = className.substring(10);
		if (prefix.trim().endsWith(
				"@".concat(String.valueOf(String.valueOf(address)))))
			out.println(String.valueOf(String.valueOf(prefix)).concat(" {"));
		else
			out.println(String.valueOf(String.valueOf((new StringBuffer(String
					.valueOf(String.valueOf(prefix)))).append(className)
					.append(" @").append(address).append(" {"))));
	}

	private static void dumpEnd(String prefix, Vector checkCircuit, Object obj) {
		checkCircuit.removeElement(new Integer(System.identityHashCode(obj)));
		int p = prefix.lastIndexOf(indentString);
		if (p > 0)
			prefix = String.valueOf(prefix.substring(0, p))
					+ String.valueOf(indentString);
		int i = 0;
		do {
			if (i >= prefix.length())
				break;
			char c = prefix.charAt(i);
			if (c != '\t' && c != ' ')
				break;
			out.print(c);
			i++;
		} while (true);
		out.println("}");
		out.flush();
	}

	public static String locate(String esc) {
		StringWriter sw = new StringWriter();
		(new Exception()).printStackTrace(new PrintWriter(sw));
		StringTokenizer st = new StringTokenizer(sw.toString(), "\n");
		do {
			if (!st.hasMoreTokens())
				break;
			String str = st.nextToken();
			if (str.indexOf("Exception") != -1
					|| str.indexOf((com.huawei.insa2.util.Debug.class)
							.getName()) != -1 || esc != null
					&& str.indexOf(esc) != -1)
				continue;
			if (esc == "!@*#~^?'/\"")
				return str;
			int i = str.indexOf('(');
			int j = str.indexOf(')');
			if (i != -1 && j != -1)
				return str.substring(i, j + 1);
			break;
		} while (true);
		return "";
	}

	private static void setHex(byte src[], int lowByte, int value) {
		int i = 0;
		do {
			if (i >= 8)
				break;
			src[lowByte - i] = hexNumber[value & 0xf];
			value >>>= 4;
			if (value == 0)
				break;
			i++;
		} while (true);
	}

	private static String indent(String prefix) {
		int p = prefix.lastIndexOf(indentString);
		if (p > 0)
			prefix = String.valueOf(prefix.substring(0, p))
					+ String.valueOf(indentString);
		StringBuffer sb = new StringBuffer();
		int i = 0;
		do {
			if (i >= prefix.length())
				break;
			char c = prefix.charAt(i);
			if (c != '\t' && c != ' ')
				break;
			sb.append(c);
			i++;
		} while (true);
		sb.append(indentString);
		return sb.toString();
	}

	private static String formatClassName(Class c, Object obj) {
		String t = c.getName();
		if (t.charAt(t.length() - 1) == ';')
			t = t.substring(0, t.length() - 1);
		boolean isArray = false;
		boolean firstDimension = true;
		while (t.startsWith("[")) {
			isArray = true;
			if (firstDimension && obj != null) {
				t = String.valueOf(String.valueOf((new StringBuffer(String
						.valueOf(String.valueOf(t.substring(1))))).append('[')
						.append(Array.getLength(obj)).append(']')));
				firstDimension = false;
			} else {
				t = String.valueOf(String.valueOf(t.substring(1))).concat("[]");
			}
		}
		if (isArray) {
			char ch = t.charAt(0);
			t = t.substring(1);
			switch (ch) {
			case 66: // 'B'
				t = "byte".concat(String.valueOf(String.valueOf(t)));
				break;

			case 67: // 'C'
				t = "char".concat(String.valueOf(String.valueOf(t)));
				break;

			case 70: // 'F'
				t = "float".concat(String.valueOf(String.valueOf(t)));
				break;

			case 73: // 'I'
				t = "int".concat(String.valueOf(String.valueOf(t)));
				break;

			case 74: // 'J'
				t = "long".concat(String.valueOf(String.valueOf(t)));
				break;

			case 83: // 'S'
				t = "short".concat(String.valueOf(String.valueOf(t)));
				break;

			case 90: // 'Z'
				t = "boolean".concat(String.valueOf(String.valueOf(t)));
				break;
			}
		}
		if (t.startsWith("java.lang."))
			t = t.substring(10);
		else if (t.startsWith("class "))
			t = t.substring(7);
		return t;
	}

	private static String fixLength(String str, int len) {
		StringBuffer sb = new StringBuffer(len);
		sb.append(str);
		int n = len - str.length() % len;
		for (int i = 0; i < n; i++)
			sb.append(' ');

		return sb.toString();
	}

	private static String dumpHead() {
		StringBuffer sb = new StringBuffer();
		sb.append(sdf.format(new Date()));
		sb.append(locate(null));
		sb.append(' ');
		return sb.toString();
	}

	private static int getDepth(String prefix) {
		int count = 0;
		int indentLen = indentString.length();
		int i = -indentLen;
		do {
			count++;
			i = prefix.indexOf(indentString, i + indentLen);
		} while (i >= 0);
		return count;
	}

	private static String indentString = "    ";

	private static final String lineSeparator = System
			.getProperty("line.separator");

	public static PrintWriter out;

	private static final byte hexNumber[] = { 48, 49, 50, 51, 52, 53, 54, 55,
			56, 57, 97, 98, 99, 100, 101, 102 };

	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

	public static final String fullInfo = "!@*#~^?'/\"";

	static {
		out = new PrintWriter(System.out);
	}
}
