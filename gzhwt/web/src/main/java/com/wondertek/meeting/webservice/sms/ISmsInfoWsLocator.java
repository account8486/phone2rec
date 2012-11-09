/**
 * ISmsInfoWsLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wondertek.meeting.webservice.sms;

public class ISmsInfoWsLocator extends org.apache.axis.client.Service implements com.wondertek.meeting.webservice.sms.ISmsInfoWs {

    public ISmsInfoWsLocator() {
    }


    public ISmsInfoWsLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ISmsInfoWsLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ISmsInfoWsHttpPort
    private java.lang.String ISmsInfoWsHttpPort_address = "http://61.132.241.75:8093/sms/smsInfows";

    public java.lang.String getISmsInfoWsHttpPortAddress() {
        return ISmsInfoWsHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ISmsInfoWsHttpPortWSDDServiceName = "ISmsInfoWsHttpPort";

    public java.lang.String getISmsInfoWsHttpPortWSDDServiceName() {
        return ISmsInfoWsHttpPortWSDDServiceName;
    }

    public void setISmsInfoWsHttpPortWSDDServiceName(java.lang.String name) {
        ISmsInfoWsHttpPortWSDDServiceName = name;
    }

    public com.wondertek.meeting.webservice.sms.ISmsInfoWsPortType getISmsInfoWsHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ISmsInfoWsHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getISmsInfoWsHttpPort(endpoint);
    }

    public com.wondertek.meeting.webservice.sms.ISmsInfoWsPortType getISmsInfoWsHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.wondertek.meeting.webservice.sms.ISmsInfoWsHttpBindingStub _stub = new com.wondertek.meeting.webservice.sms.ISmsInfoWsHttpBindingStub(portAddress, this);
            _stub.setPortName(getISmsInfoWsHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setISmsInfoWsHttpPortEndpointAddress(java.lang.String address) {
        ISmsInfoWsHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.wondertek.meeting.webservice.sms.ISmsInfoWsPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.wondertek.meeting.webservice.sms.ISmsInfoWsHttpBindingStub _stub = new com.wondertek.meeting.webservice.sms.ISmsInfoWsHttpBindingStub(new java.net.URL(ISmsInfoWsHttpPort_address), this);
                _stub.setPortName(getISmsInfoWsHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ISmsInfoWsHttpPort".equals(inputPortName)) {
            return getISmsInfoWsHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.ideal.com", "ISmsInfoWs");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.ideal.com", "ISmsInfoWsHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ISmsInfoWsHttpPort".equals(portName)) {
            setISmsInfoWsHttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
