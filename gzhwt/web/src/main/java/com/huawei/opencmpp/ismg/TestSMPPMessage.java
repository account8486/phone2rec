package com.huawei.opencmpp.ismg;

import com.huawei.insa2.comm.smpp.message.SMPPLoginMessage;
import com.huawei.insa2.comm.smpp.message.SMPPSubmitMessage;

public class TestSMPPMessage {
	public void test_bind() throws Exception {
		SMPPLoginMessage bind = new SMPPLoginMessage(1, "systemId", "password",
				"system_type", (byte) 3, (byte) 4, (byte) 5, "address_range");

		System.out.println("systemId : " + bind.getSystemId());
		System.out.println("password : " + bind.getPassword());
		System.out.println("interface_version : " + bind.getIntefaceVersion());
		System.out.println("addr_ton : " + bind.getAddrTon());
		System.out.println("addr_npi : " + bind.getAddrNpi());
		System.out.println("system_type : " + bind.getSystemType());
		System.out.println("address_range : " + bind.getAddressRange());

	}

	public void test_submit() throws Exception {
		String serviceType = "srvT";

		byte sourceAddrTon = 1;

		byte sourceAddrNpi = 1;

		String sourceAddr = "sourceAddr";

		byte destAddrTon = 1;

		byte destAddrNpi = 1;

		String destinationAddr = "destionationAddr";

		byte esmClass = 1;

		byte protocolId = 1;

		byte priorityFlag = 1;

		String scheduleDeliveryTime = "2005-06-12 10:11";

		String validityPeriod = "2004-03-11 11:12";

		byte registeredDelivery = 1;

		byte replaceIfPresentFlag = 1;

		byte dataCoding = 1;

		byte smDefaultMsgId = 1;

		byte smLength = 8;

		String shortMessage = "中文信息";

		SMPPSubmitMessage submit = new SMPPSubmitMessage(serviceType,
				sourceAddrTon, sourceAddrNpi, sourceAddr, destAddrTon,
				destAddrNpi, destinationAddr, esmClass, protocolId,
				priorityFlag, scheduleDeliveryTime, validityPeriod,
				registeredDelivery, replaceIfPresentFlag, dataCoding,
				smDefaultMsgId, smLength, shortMessage);

		byte[] bytes = submit.getBytes();

		SMPPSubmitMessage new_submit = new SMPPSubmitMessage(bytes);
		
		System.out.println("destAddrTon : " + new_submit.getDestAddress());
		System.out.println("scheduleDeliveryTime : " + new_submit.getScheduleDeliveryTime());
		System.out.println("validityPeriod : " + new_submit.getValidityPeriod());
		System.out.println("shortMessage : " + new_submit.getShortMessage());
	}
}
