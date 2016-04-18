/**
 * KmReviewParamterNoattForm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.landray.kmss.km.importfile.webservice;

public class KmReviewParamterNoattForm  implements java.io.Serializable {
    private java.lang.String docCreator;

    private java.lang.String docId;

    private java.lang.String docSubject;

    private java.lang.String easHtml;

    private java.lang.String fdTemplateId;

    private java.lang.String formValues;

    public KmReviewParamterNoattForm() {
    }

    public KmReviewParamterNoattForm(
           java.lang.String docCreator,
           java.lang.String docId,
           java.lang.String docSubject,
           java.lang.String easHtml,
           java.lang.String fdTemplateId,
           java.lang.String formValues) {
           this.docCreator = docCreator;
           this.docId = docId;
           this.docSubject = docSubject;
           this.easHtml = easHtml;
           this.fdTemplateId = fdTemplateId;
           this.formValues = formValues;
    }


    /**
     * Gets the docCreator value for this KmReviewParamterNoattForm.
     * 
     * @return docCreator
     */
    public java.lang.String getDocCreator() {
        return docCreator;
    }


    /**
     * Sets the docCreator value for this KmReviewParamterNoattForm.
     * 
     * @param docCreator
     */
    public void setDocCreator(java.lang.String docCreator) {
        this.docCreator = docCreator;
    }


    /**
     * Gets the docId value for this KmReviewParamterNoattForm.
     * 
     * @return docId
     */
    public java.lang.String getDocId() {
        return docId;
    }


    /**
     * Sets the docId value for this KmReviewParamterNoattForm.
     * 
     * @param docId
     */
    public void setDocId(java.lang.String docId) {
        this.docId = docId;
    }


    /**
     * Gets the docSubject value for this KmReviewParamterNoattForm.
     * 
     * @return docSubject
     */
    public java.lang.String getDocSubject() {
        return docSubject;
    }


    /**
     * Sets the docSubject value for this KmReviewParamterNoattForm.
     * 
     * @param docSubject
     */
    public void setDocSubject(java.lang.String docSubject) {
        this.docSubject = docSubject;
    }


    /**
     * Gets the easHtml value for this KmReviewParamterNoattForm.
     * 
     * @return easHtml
     */
    public java.lang.String getEasHtml() {
        return easHtml;
    }


    /**
     * Sets the easHtml value for this KmReviewParamterNoattForm.
     * 
     * @param easHtml
     */
    public void setEasHtml(java.lang.String easHtml) {
        this.easHtml = easHtml;
    }


    /**
     * Gets the fdTemplateId value for this KmReviewParamterNoattForm.
     * 
     * @return fdTemplateId
     */
    public java.lang.String getFdTemplateId() {
        return fdTemplateId;
    }


    /**
     * Sets the fdTemplateId value for this KmReviewParamterNoattForm.
     * 
     * @param fdTemplateId
     */
    public void setFdTemplateId(java.lang.String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }


    /**
     * Gets the formValues value for this KmReviewParamterNoattForm.
     * 
     * @return formValues
     */
    public java.lang.String getFormValues() {
        return formValues;
    }


    /**
     * Sets the formValues value for this KmReviewParamterNoattForm.
     * 
     * @param formValues
     */
    public void setFormValues(java.lang.String formValues) {
        this.formValues = formValues;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof KmReviewParamterNoattForm)) return false;
        KmReviewParamterNoattForm other = (KmReviewParamterNoattForm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docCreator==null && other.getDocCreator()==null) || 
             (this.docCreator!=null &&
              this.docCreator.equals(other.getDocCreator()))) &&
            ((this.docId==null && other.getDocId()==null) || 
             (this.docId!=null &&
              this.docId.equals(other.getDocId()))) &&
            ((this.docSubject==null && other.getDocSubject()==null) || 
             (this.docSubject!=null &&
              this.docSubject.equals(other.getDocSubject()))) &&
            ((this.easHtml==null && other.getEasHtml()==null) || 
             (this.easHtml!=null &&
              this.easHtml.equals(other.getEasHtml()))) &&
            ((this.fdTemplateId==null && other.getFdTemplateId()==null) || 
             (this.fdTemplateId!=null &&
              this.fdTemplateId.equals(other.getFdTemplateId()))) &&
            ((this.formValues==null && other.getFormValues()==null) || 
             (this.formValues!=null &&
              this.formValues.equals(other.getFormValues())));
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
        if (getDocCreator() != null) {
            _hashCode += getDocCreator().hashCode();
        }
        if (getDocId() != null) {
            _hashCode += getDocId().hashCode();
        }
        if (getDocSubject() != null) {
            _hashCode += getDocSubject().hashCode();
        }
        if (getEasHtml() != null) {
            _hashCode += getEasHtml().hashCode();
        }
        if (getFdTemplateId() != null) {
            _hashCode += getFdTemplateId().hashCode();
        }
        if (getFormValues() != null) {
            _hashCode += getFormValues().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(KmReviewParamterNoattForm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservice.importfile.km.kmss.landray.com/", "kmReviewParamterNoattForm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docCreator");
        elemField.setXmlName(new javax.xml.namespace.QName("", "docCreator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "docId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docSubject");
        elemField.setXmlName(new javax.xml.namespace.QName("", "docSubject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("easHtml");
        elemField.setXmlName(new javax.xml.namespace.QName("", "easHtml"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fdTemplateId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fdTemplateId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formValues");
        elemField.setXmlName(new javax.xml.namespace.QName("", "formValues"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
