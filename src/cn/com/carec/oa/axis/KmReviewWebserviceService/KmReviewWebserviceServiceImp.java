/**
 * KmReviewWebserviceServiceImp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package cn.com.carec.oa.axis.KmReviewWebserviceService;

public interface KmReviewWebserviceServiceImp extends java.rmi.Remote {
    public java.lang.Object[] getTemplate(java.lang.String key) throws java.rmi.RemoteException;
    public int delTasksByID(java.lang.String id) throws java.rmi.RemoteException;
    public java.lang.String addReview(KmReviewWebserviceService.KmReviewParamterForm webForm) throws java.rmi.RemoteException;
    public java.lang.String addReviewNoatt(KmReviewWebserviceService.KmReviewParamterNoattForm webForm) throws java.rmi.RemoteException;
}
