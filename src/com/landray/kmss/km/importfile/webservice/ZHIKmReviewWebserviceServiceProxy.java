package com.landray.kmss.km.importfile.webservice;

public class ZHIKmReviewWebserviceServiceProxy implements com.landray.kmss.km.importfile.webservice.ZHIKmReviewWebserviceService {
  private String _endpoint = null;
  private com.landray.kmss.km.importfile.webservice.ZHIKmReviewWebserviceService zHIKmReviewWebserviceService = null;
  
  public ZHIKmReviewWebserviceServiceProxy() {
    _initZHIKmReviewWebserviceServiceProxy();
  }
  
  public ZHIKmReviewWebserviceServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initZHIKmReviewWebserviceServiceProxy();
  }
  
  private void _initZHIKmReviewWebserviceServiceProxy() {
    try {
      zHIKmReviewWebserviceService = (new com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpServiceLocator()).getZHKmReviewWebserviceServiceImpPort();
      if (zHIKmReviewWebserviceService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)zHIKmReviewWebserviceService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)zHIKmReviewWebserviceService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (zHIKmReviewWebserviceService != null)
      ((javax.xml.rpc.Stub)zHIKmReviewWebserviceService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.landray.kmss.km.importfile.webservice.ZHIKmReviewWebserviceService getZHIKmReviewWebserviceService() {
    if (zHIKmReviewWebserviceService == null)
      _initZHIKmReviewWebserviceServiceProxy();
    return zHIKmReviewWebserviceService;
  }
  
  public java.lang.String addReview(com.landray.kmss.km.importfile.webservice.KmReviewParamterForm arg0) throws java.rmi.RemoteException, com.landray.kmss.km.importfile.webservice.Exception{
    if (zHIKmReviewWebserviceService == null)
      _initZHIKmReviewWebserviceServiceProxy();
    return zHIKmReviewWebserviceService.addReview(arg0);
  }
  
  public java.lang.String getTemplate(java.lang.String arg0) throws java.rmi.RemoteException, com.landray.kmss.km.importfile.webservice.Exception{
    if (zHIKmReviewWebserviceService == null)
      _initZHIKmReviewWebserviceServiceProxy();
    return zHIKmReviewWebserviceService.getTemplate(arg0);
  }
  
  public int delTasksByID(java.lang.String arg0) throws java.rmi.RemoteException, com.landray.kmss.km.importfile.webservice.Exception{
    if (zHIKmReviewWebserviceService == null)
      _initZHIKmReviewWebserviceServiceProxy();
    return zHIKmReviewWebserviceService.delTasksByID(arg0);
  }
  
  public java.lang.String addReviewNoatt(com.landray.kmss.km.importfile.webservice.KmReviewParamterNoattForm arg0) throws java.rmi.RemoteException, com.landray.kmss.km.importfile.webservice.Exception{
    if (zHIKmReviewWebserviceService == null)
      _initZHIKmReviewWebserviceServiceProxy();
    return zHIKmReviewWebserviceService.addReviewNoatt(arg0);
  }
  
  
}