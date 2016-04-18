/**
 * AttachmentForm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package KmReviewWebserviceService;

public class AttachmentForm  implements java.io.Serializable {
    private byte[] fdAttachment;
    private java.lang.String fdFileName;
    private java.lang.String fdKey;

    public AttachmentForm() {
    }

    public byte[] getFdAttachment() {
        return fdAttachment;
    }

    public void setFdAttachment(byte[] fdAttachment) {
        this.fdAttachment = fdAttachment;
    }

    public java.lang.String getFdFileName() {
        return fdFileName;
    }

    public void setFdFileName(java.lang.String fdFileName) {
        this.fdFileName = fdFileName;
    }

    public java.lang.String getFdKey() {
        return fdKey;
    }

    public void setFdKey(java.lang.String fdKey) {
        this.fdKey = fdKey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttachmentForm)) return false;
        AttachmentForm other = (AttachmentForm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fdAttachment==null && other.getFdAttachment()==null) || 
             (this.fdAttachment!=null &&
              java.util.Arrays.equals(this.fdAttachment, other.getFdAttachment()))) &&
            ((this.fdFileName==null && other.getFdFileName()==null) || 
             (this.fdFileName!=null &&
              this.fdFileName.equals(other.getFdFileName()))) &&
            ((this.fdKey==null && other.getFdKey()==null) || 
             (this.fdKey!=null &&
              this.fdKey.equals(other.getFdKey())));
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
        if (getFdAttachment() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFdAttachment());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFdAttachment(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFdFileName() != null) {
            _hashCode += getFdFileName().hashCode();
        }
        if (getFdKey() != null) {
            _hashCode += getFdKey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AttachmentForm.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:KmReviewWebserviceService", "AttachmentForm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fdAttachment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fdAttachment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "base64"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fdFileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fdFileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fdKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fdKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
