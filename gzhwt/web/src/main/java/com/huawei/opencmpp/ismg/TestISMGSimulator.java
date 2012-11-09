package com.huawei.opencmpp.ismg;

import java.util.Calendar;

public class TestISMGSimulator{

	public static void main(String[] args) throws Exception {
		TestISMGSimulator test = new TestISMGSimulator();
		test.test_start();
	}

	public void test_start() throws Exception {
		CMPPISMGService service = new CMPPISMGService();
		service.start();

		for (;;) {
			Thread.sleep(100);
		}
	}

	public void test_gen_submit_resp_id() throws Exception {
		long id = 1;

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);

		System.out.println(month + "-" + day + " " + hour + ":" + minute + ":"
				+ second);

		int ismg_Id = Integer.MAX_VALUE;
		int seq_id = 7;

		// 截去前10位
		ismg_Id &= (int) Math.pow(2, 22) - 1;

		// bits.get(bitIndex)
		// 0000 00001 00001 000000 000000 0000100001000010000100
		// 0000100001000010
		// 月 日 时 分 秒 网关ID 序列号
		// 4位 5位 5位 6位 6位 22位 16位

		for (int i = 63; i >= 0; --i) {
			if (i % 8 == 7) {
				System.out.print(' ');
			}
			int item = get(ismg_Id, i) ? 1 : 0;
			System.out.print(item);
		}
		System.out.println();
		System.out.println();

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println();

		id |= ((long) month) << 60;
		id |= ((long) day) << 55;
		id |= ((long) hour) << 50;
		id |= ((long) minute) << 44;
		id |= ((long) second) << 38;
		id |= ((long) ismg_Id) << 16;
		id = seq_id;

		for (int i = 63; i >= 0; --i) {
			if (i == 59 || i == 54 || i == 49 || i == 43 || i == 37 || i == 15) {
				System.out.print(' ');
			}
			int item = get(id, i) ? 1 : 0;
			System.out.print(item);
		}

		System.out.println();

	}

	public void test_bit() throws Exception {
		int ismg_Id = Integer.MAX_VALUE;

		for (int i = 31; i >= 0; --i) {
			if (i % 8 == 7) {
				System.out.print(' ');
			}
			int item = get(ismg_Id, i) ? 1 : 0;
			System.out.print(item);
		}
		System.out.println();

		// for (int i = 31; i >= 22; --i) {
		// ismg_Id &= ~bit(i);
		// }

		int v = (int) Math.pow(2, 22) - 1;
		System.out.println("v:" + v);
		ismg_Id &= (int) Math.pow(2, 22) + 1;

		System.out.println(ismg_Id);

		for (int i = 31; i >= 0; --i) {
			if (i % 8 == 7) {
				System.out.print(' ');
			}
			int item = get(ismg_Id, i) ? 1 : 0;
			System.out.print(item);
		}
		System.out.println();
	}

	public static boolean get(long bits, int bitIndex) {
		if (bitIndex < 0)
			throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);

		boolean result = false;
		result = ((bits & bit(bitIndex)) != 0);

		return result;
	}

	private static long bit(int bitIndex) {
		return 1L << (bitIndex & BIT_INDEX_MASK);
	}

	private static int unitIndex(int bitIndex) {
		return bitIndex >> ADDRESS_BITS_PER_UNIT;
	}

	private final static int ADDRESS_BITS_PER_UNIT = 6;

	private final static int BITS_PER_UNIT = 1 << ADDRESS_BITS_PER_UNIT;

	private final static int BIT_INDEX_MASK = BITS_PER_UNIT - 1;
}
