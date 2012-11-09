/**
 * SubmitService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wondertek.meeting.webservice.mas;

public interface SubmitService extends java.rmi.Remote {
    public com.wondertek.meeting.webservice.mas.RespHeader smsSubmit(com.wondertek.meeting.webservice.mas.ReqHeader arg0, com.wondertek.meeting.webservice.mas.SmsSubmit arg1, com.wondertek.meeting.webservice.mas.Param[] arg2) throws java.rmi.RemoteException;
    public com.wondertek.meeting.webservice.mas.RespHeader mmsSubmit(com.wondertek.meeting.webservice.mas.ReqHeader arg0, com.wondertek.meeting.webservice.mas.MmsSubmit arg1, com.wondertek.meeting.webservice.mas.Param[] arg2) throws java.rmi.RemoteException;
}
