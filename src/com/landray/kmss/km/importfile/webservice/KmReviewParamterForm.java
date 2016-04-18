/**
 * KmReviewParamterForm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.landray.kmss.km.importfile.webservice;

public class KmReviewParamterForm  implements java.io.Serializable {
    private com.landray.kmss.km.importfile.webservice.AttachmentForm[] attachmentForms;

    private java.lang.String docCreator;

    private java.lang.String docId;

    private java.lang.String docSubject;

    private java.lang.String easHtml;

    private java.lang.String fdTemplateId;

    private java.lang.String formValues;

    public KmReviewParamterForm() {
    }

    public KmReviewParamterForm(
           com.landray.kmss.km.importfile.webservice.AttachmentForm[] attachmentForms,
           java.lang.String docCreator,
           java.lang.String docId,
           java.lang.String docSubject,
           java.lang.String easHtml,
           java.lang.String fdTemplateId,
           java.lang.String formValues) {
           this.attachmentForms = attachmentForms;
           this.docCreator = docCreator;
           this.docId = docId;
           this.docSubject = docSubject;
           this.easHtml = easHtml;
           this.fdTemplateId = fdTemplateId;
           this.formValues = formValues;
    }


    /**
     * Gets the attachmentForms value for this KmReviewParamterForm.
     * 
     * @return attachmentForms
     */
    public com.landray.kmss.km.importfile.webservice.AttachmentForm[] getAttachmentForms() {
        return attachmentForms;
    }


    /**
     * Sets the attachmentForms value for this KmReviewParamterForm.
     * 
     * @param attachmentForms
     */
    public void setAttachmentForms(com.landray.kmss.km.importfile.webservice.AttachmentForm[] attachmentForms) {
        this.attachmentForms = attachmentForms;
    }

    public com.landray.kmss.km.importfile.webservice.AttachmentForm getAttachmentForms(int i) {
        return this.attachmentForms[i];
    }

    public void setAttachmentForms(int i, com.landray.kmss.km.importfile.webservice.AttachmentForm _value) {
        this.attachmentForms[i] = _value;
    }


    /**
     * Gets the docCreator value for this KmReviewParamterForm.
     * 
     * @return docCreator
     */
    public java.lang.String getDocCreator() {
        return docCreator;
    }


    /**
     * Sets the docCreator value for this KmReviewParamterForm.
     * 
     * @param docCreator
     */
    public void setDocCreator(java.lang.String docCreator) {
        this.docCreator = docCreator;
    }


    /**
     * Gets the docId value for this KmReviewParamterForm.
     * 
     * @return docId
     */
    public java.lang.String getDocId() {
        return docId;
    }


    /**
     * Sets the docId value for this KmReviewParamterForm.
     * 
     * @param docId
     */
    public void setDocId(java.lang.String docId) {
        this.docId = docId;
    }


    /**
     * Gets the docSubject value for this KmReviewParamterForm.
     * 
     * @return docSubject
     */
    public java.lang.String getDocSubject() {
        return docSubject;
    }


    /**
     * Sets the docSubject value for this KmReviewParamterForm.
     * 
     * @param docSubject
     */
    public void setDocSubject(java.lang.String docSubject) {
        this.docSubject = docSubject;
    }


    /**
     * Gets the easHtml value for this KmReviewParamterForm.
     * 
     * @return easHtml
     */
    public java.lang.String getEasHtml() {
        return easHtml;
    }


    /**
     * Sets the easHtml value for this KmReviewParamterForm.
     * 
     * @param easHtml
     */
    public void setEasHtml(java.lang.String easHtml) {
        this.easHtml = easHtml;
    }


    /**
     * Gets the fdTemplateId value for this KmReviewParamterForm.
     * 
     * @return fdTemplateId
     */
    public java.lang.String getFdTemplateId() {
        return fdTemplateId;
    }


    /**
     * Sets the fdTemplateId value for this KmReviewParamterForm.
     * 
     * @param fdTemplateId
     */
    public void setFdTemplateId(java.lang.String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }


    /**
     * Gets the formValues value for this KmReviewParamterForm.
     * 
     * @return formValues
     */
    public java.lang.String getFormValues() {
        return formValues;
    }


    /**
     * Sets the formValues value for this KmReviewParamterForm.
     * 
     * @param formValues
     */
    public void setFormValues(java.lang.String formValues) {
        this.formValues = formValues;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof KmReviewParamterForm)) return false;
        KmReviewParamterForm other = (KmReviewParamterForm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attachmentForms==null && other.getAttachmentForms()==null) || 
             (this.attachmentForms!=null &&
              java.util.Arrays.equals(this.attachmentForms, other.getAttachmentForms()))) &&
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
        if (getAttachmentForms() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttachmentForms());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttachmentForms(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
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
        new org.apache.axis.description.TypeDesc(KmReviewParamterForm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservice.importfile.km.kmss.landray.com/", "kmReviewParamterForm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachmentForms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attachmentForms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservice.importfile.km.kmss.landray.com/", "attachmentForm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
