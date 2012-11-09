/**
 * MmsSubmit.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wondertek.meeting.webservice.mas;

public class MmsSubmit  implements java.io.Serializable {
    private com.wondertek.meeting.webservice.mas.CPMMS cpmms;

    private java.lang.String[] dest;

    private com.wondertek.meeting.webservice.mas.Param[] param;

    private java.lang.String sourceAddr;

    public MmsSubmit() {
    }

    public MmsSubmit(
           com.wondertek.meeting.webservice.mas.CPMMS cpmms,
           java.lang.String[] dest,
           com.wondertek.meeting.webservice.mas.Param[] param,
           java.lang.String sourceAddr) {
           this.cpmms = cpmms;
           this.dest = dest;
           this.param = param;
           this.sourceAddr = sourceAddr;
    }


    /**
     * Gets the cpmms value for this MmsSubmit.
     * 
     * @return cpmms
     */
    public com.wondertek.meeting.webservice.mas.CPMMS getCpmms() {
        return cpmms;
    }


    /**
     * Sets the cpmms value for this MmsSubmit.
     * 
     * @param cpmms
     */
    public void setCpmms(com.wondertek.meeting.webservice.mas.CPMMS cpmms) {
        this.cpmms = cpmms;
    }


    /**
     * Gets the dest value for this MmsSubmit.
     * 
     * @return dest
     */
    public java.lang.String[] getDest() {
        return dest;
    }


    /**
     * Sets the dest value for this MmsSubmit.
     * 
     * @param dest
     */
    public void setDest(java.lang.String[] dest) {
        this.dest = dest;
    }


    /**
     * Gets the param value for this MmsSubmit.
     * 
     * @return param
     */
    public com.wondertek.meeting.webservice.mas.Param[] getParam() {
        return param;
    }


    /**
     * Sets the param value for this MmsSubmit.
     * 
     * @param param
     */
    public void setParam(com.wondertek.meeting.webservice.mas.Param[] param) {
        this.param = param;
    }


    /**
     * Gets the sourceAddr value for this MmsSubmit.
     * 
     * @return sourceAddr
     */
    public java.lang.String getSourceAddr() {
        return sourceAddr;
    }


    /**
     * Sets the sourceAddr value for this MmsSubmit.
     * 
     * @param sourceAddr
     */
    public void setSourceAddr(java.lang.String sourceAddr) {
        this.sourceAddr = sourceAddr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MmsSubmit)) return false;
        MmsSubmit other = (MmsSubmit) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cpmms==null && other.getCpmms()==null) || 
             (this.cpmms!=null &&
              this.cpmms.equals(other.getCpmms()))) &&
            ((this.dest==null && other.getDest()==null) || 
             (this.dest!=null &&
              java.util.Arrays.equals(this.dest, other.getDest()))) &&
            ((this.param==null && other.getParam()==null) || 
             (this.param!=null &&
              java.util.Arrays.equals(this.param, other.getParam()))) &&
            ((this.sourceAddr==null && other.getSourceAddr()==null) || 
             (this.sourceAddr!=null &&
              this.sourceAddr.equals(other.getSourceAddr())));
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
        if (getCpmms() != null) {
            _hashCode += getCpmms().hashCode();
        }
        if (getDest() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDest());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDest(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getParam() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParam());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParam(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSourceAddr() != null) {
            _hashCode += getSourceAddr().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MmsSubmit.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://data.msg.mas.com", "MmsSubmit"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cpmms");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.msg.mas.com", "cpmms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://data.msg.mas.com", "CPMMS"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.msg.mas.com", "dest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://service.msg.mas.com/", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("param");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.msg.mas.com", "param"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://data.msg.mas.com", "Param"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://data.msg.mas.com", "Param"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceAddr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://data.msg.mas.com", "sourceAddr"));
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
