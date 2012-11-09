package com.huawei.opencmpp.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class TestConsole{
	public void test_0() throws Exception {
		//System.in
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		for (;;) {
			String command = reader.readLine();
			
			if ("exit".equalsIgnoreCase(command)) {
				break;
			}
			
			System.out.println(command);
		}
	}
}
