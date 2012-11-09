/**
 * ReqHeader.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wondertek.meeting.webservice.mas;

public class ReqHeader  implements java.io.Serializable {
    private java.lang.String authCode;

    private java.lang.String reqno;

    private java.lang.String sysid;

    public ReqHeader() {
    }

    public ReqHeader(
           java.lang.String authCode,
           java.lang.String reqno,
           java.lang.String sysid) {
           this.authCode = authCode;
           this.reqno = reqno;
           this.sysid = sysid;
    }


    /**
     * Gets the authCode value for this ReqHeader.
     * 
     * @return authCode
     */
    public java.lang.String getAuthCode() {
        return authCode;
    }


    /**
     * Sets the authCode value for this ReqHeader.
     * 
     * @param authCode
     */
    public void setAuthCode(java.lang.String authCode) {
        this.authCode = authCode;
    }


    /**
     * Gets the reqno value for this ReqHeader.
     * 
     * @return reqno
     */
    public java.lang.String getReqno() {
        return reqno;
    }


    /**
     * Sets the reqno value for this ReqHeader.
     * 
     * @param reqno
     */
    public void setReqno(java.lang.String reqno) {
        this.reqno = reqno;
    }


    /**
     * Gets the sysid value for this ReqHeader.
     * 
     * @return sysid
     */
    public java.lang.String getSysid() {
        return sysid;
    }


    /**
     * Sets the sysid value for this ReqHeader.
     * 
     * @param sysid
     */
    public void setSysid(java.lang.String sysid) {
        this.sysid = sysid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqHeader)) return false;
        ReqHeader other = (ReqHeader) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.authCode==null && other.getAuthCode()==null) || 
             (this.authCode!=null &&
              this.authCode.equals(other.getAuthCode()))) &&
            ((this.reqno==null && other.getReqno()==null) || 
             (this.reqno!=null &&
              this.reqno.equals(other.getReqno()))) &&
            ((this.sysid==null && other.getSysid()==null) || 
             (this.sysid!=null &&
              this.sysid.equals(other.getSysid())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAuthCode() != null) {
            _hashCode += getAuthCode().hashCode();
        }
        if (getReqno() != null) {
            _hashCode += getReqno().hashCode();
        }
        if (getSysid() != null) {
            _hashCode += getSysid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqHeader.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.msg.mas.com", "ReqHeader"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://req.msg.mas.com", "authCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reqno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://req.msg.mas.com", "reqno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sysid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://req.msg.mas.com", "sysid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
