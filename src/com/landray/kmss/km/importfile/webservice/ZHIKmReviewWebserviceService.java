/**
 * ZHIKmReviewWebserviceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.landray.kmss.km.importfile.webservice;

public interface ZHIKmReviewWebserviceService extends java.rmi.Remote {
    public java.lang.String addReview(com.landray.kmss.km.importfile.webservice.KmReviewParamterForm arg0) throws java.rmi.RemoteException, com.landray.kmss.km.importfile.webservice.Exception;
    public java.lang.String getTemplate(java.lang.String arg0) throws java.rmi.RemoteException, com.landray.kmss.km.importfile.webservice.Exception;
    public int delTasksByID(java.lang.String arg0) throws java.rmi.RemoteException, com.landray.kmss.km.importfile.webservice.Exception;
    public java.lang.String addReviewNoatt(com.landray.kmss.km.importfile.webservice.KmReviewParamterNoattForm arg0) throws java.rmi.RemoteException, com.landray.kmss.km.importfile.webservice.Exception;
}
