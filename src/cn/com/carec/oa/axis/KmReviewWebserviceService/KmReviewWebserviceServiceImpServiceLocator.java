/**
 * KmReviewWebserviceServiceImpServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package cn.com.carec.oa.axis.KmReviewWebserviceService;

public class KmReviewWebserviceServiceImpServiceLocator extends org.apache.axis.client.Service implements cn.com.carec.oa.axis.KmReviewWebserviceService.KmReviewWebserviceServiceImpService {

    // Use to get a proxy class for KmReviewWebserviceService
    private final java.lang.String KmReviewWebserviceService_address = "http://oa.carec.com.cn/axis/KmReviewWebserviceService";

    public java.lang.String getKmReviewWebserviceServiceAddress() {
        return KmReviewWebserviceService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String KmReviewWebserviceServiceWSDDServiceName = "KmReviewWebserviceService";

    public java.lang.String getKmReviewWebserviceServiceWSDDServiceName() {
        return KmReviewWebserviceServiceWSDDServiceName;
    }

    public void setKmReviewWebserviceServiceWSDDServiceName(java.lang.String name) {
        KmReviewWebserviceServiceWSDDServiceName = name;
    }

    public cn.com.carec.oa.axis.KmReviewWebserviceService.KmReviewWebserviceServiceImp getKmReviewWebserviceService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(KmReviewWebserviceService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getKmReviewWebserviceService(endpoint);
    }

    public cn.com.carec.oa.axis.KmReviewWebserviceService.KmReviewWebserviceServiceImp getKmReviewWebserviceService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.com.carec.oa.axis.KmReviewWebserviceService.KmReviewWebserviceServiceSoapBindingStub _stub = new cn.com.carec.oa.axis.KmReviewWebserviceService.KmReviewWebserviceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getKmReviewWebserviceServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cn.com.carec.oa.axis.KmReviewWebserviceService.KmReviewWebserviceServiceImp.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.com.carec.oa.axis.KmReviewWebserviceService.KmReviewWebserviceServiceSoapBindingStub _stub = new cn.com.carec.oa.axis.KmReviewWebserviceService.KmReviewWebserviceServiceSoapBindingStub(new java.net.URL(KmReviewWebserviceService_address), this);
                _stub.setPortName(getKmReviewWebserviceServiceWSDDServiceName());
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
        String inputPortName = portName.getLocalPart();
        if ("KmReviewWebserviceService".equals(inputPortName)) {
            return getKmReviewWebserviceService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://oa.carec.com.cn/axis/KmReviewWebserviceService", "KmReviewWebserviceServiceImpService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("KmReviewWebserviceService"));
        }
        return ports.iterator();
    }

}
