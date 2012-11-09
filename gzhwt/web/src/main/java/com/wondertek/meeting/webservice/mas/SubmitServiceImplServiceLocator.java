/**
 * SubmitServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wondertek.meeting.webservice.mas;

public class SubmitServiceImplServiceLocator extends org.apache.axis.client.Service implements com.wondertek.meeting.webservice.mas.SubmitServiceImplService {

    public SubmitServiceImplServiceLocator() {
    }


    public SubmitServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SubmitServiceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SubmitServiceImplPort
    private java.lang.String SubmitServiceImplPort_address = "http://218.201.202.146:20/msgService/services/submitService";

    public java.lang.String getSubmitServiceImplPortAddress() {
        return SubmitServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SubmitServiceImplPortWSDDServiceName = "SubmitServiceImplPort";

    public java.lang.String getSubmitServiceImplPortWSDDServiceName() {
        return SubmitServiceImplPortWSDDServiceName;
    }

    public void setSubmitServiceImplPortWSDDServiceName(java.lang.String name) {
        SubmitServiceImplPortWSDDServiceName = name;
    }

    public com.wondertek.meeting.webservice.mas.SubmitService getSubmitServiceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SubmitServiceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSubmitServiceImplPort(endpoint);
    }

    public com.wondertek.meeting.webservice.mas.SubmitService getSubmitServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.wondertek.meeting.webservice.mas.SubmitServiceImplServiceSoapBindingStub _stub = new com.wondertek.meeting.webservice.mas.SubmitServiceImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getSubmitServiceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSubmitServiceImplPortEndpointAddress(java.lang.String address) {
        SubmitServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.wondertek.meeting.webservice.mas.SubmitService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.wondertek.meeting.webservice.mas.SubmitServiceImplServiceSoapBindingStub _stub = new com.wondertek.meeting.webservice.mas.SubmitServiceImplServiceSoapBindingStub(new java.net.URL(SubmitServiceImplPort_address), this);
                _stub.setPortName(getSubmitServiceImplPortWSDDServiceName());
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
        if ("SubmitServiceImplPort".equals(inputPortName)) {
            return getSubmitServiceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.msg.mas.com/", "SubmitServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.msg.mas.com/", "SubmitServiceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SubmitServiceImplPort".equals(portName)) {
            setSubmitServiceImplPortEndpointAddress(address);
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
