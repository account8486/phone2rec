/**
 * RespHeader.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wondertek.meeting.webservice.mas;

public class RespHeader  implements java.io.Serializable {
    private java.lang.String reqno;

    private java.lang.String respno;

    private java.lang.String resultCode;

    public RespHeader() {
    }

    public RespHeader(
           java.lang.String reqno,
           java.lang.String respno,
           java.lang.String resultCode) {
           this.reqno = reqno;
           this.respno = respno;
           this.resultCode = resultCode;
    }


    /**
     * Gets the reqno value for this RespHeader.
     * 
     * @return reqno
     */
    public java.lang.String getReqno() {
        return reqno;
    }


    /**
     * Sets the reqno value for this RespHeader.
     * 
     * @param reqno
     */
    public void setReqno(java.lang.String reqno) {
        this.reqno = reqno;
    }


    /**
     * Gets the respno value for this RespHeader.
     * 
     * @return respno
     */
    public java.lang.String getRespno() {
        return respno;
    }


    /**
     * Sets the respno value for this RespHeader.
     * 
     * @param respno
     */
    public void setRespno(java.lang.String respno) {
        this.respno = respno;
    }


    /**
     * Gets the resultCode value for this RespHeader.
     * 
     * @return resultCode
     */
    public java.lang.String getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this RespHeader.
     * 
     * @param resultCode
     */
    public void setResultCode(java.lang.String resultCode) {
        this.resultCode = resultCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespHeader)) return false;
        RespHeader other = (RespHeader) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reqno==null && other.getReqno()==null) || 
             (this.reqno!=null &&
              this.reqno.equals(other.getReqno()))) &&
            ((this.respno==null && other.getRespno()==null) || 
             (this.respno!=null &&
              this.respno.equals(other.getRespno()))) &&
            ((this.resultCode==null && other.getResultCode()==null) || 
             (this.resultCode!=null &&
              this.resultCode.equals(other.getResultCode())));
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
        if (getReqno() != null) {
            _hashCode += getReqno().hashCode();
        }
        if (getRespno() != null) {
            _hashCode += getRespno().hashCode();
        }
        if (getResultCode() != null) {
            _hashCode += getResultCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespHeader.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://resp.msg.mas.com", "RespHeader"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reqno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://resp.msg.mas.com", "reqno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("respno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://resp.msg.mas.com", "respno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://resp.msg.mas.com", "resultCode"));
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
