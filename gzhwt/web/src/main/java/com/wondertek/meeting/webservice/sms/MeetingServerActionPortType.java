/**
 * MeetingServerActionPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wondertek.meeting.webservice.sms;

public interface MeetingServerActionPortType extends java.rmi.Remote {
    public java.lang.String updateExtNum(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String sendTextSms(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String deleteExtNum(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String addExtNum(java.lang.String in0) throws java.rmi.RemoteException;
}
