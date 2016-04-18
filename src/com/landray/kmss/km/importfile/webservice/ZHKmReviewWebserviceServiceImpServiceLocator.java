/**
 * ZHKmReviewWebserviceServiceImpServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.landray.kmss.km.importfile.webservice;

public class ZHKmReviewWebserviceServiceImpServiceLocator extends org.apache.axis.client.Service implements com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpService {

    public ZHKmReviewWebserviceServiceImpServiceLocator() {
    }


    public ZHKmReviewWebserviceServiceImpServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ZHKmReviewWebserviceServiceImpServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ZHKmReviewWebserviceServiceImpPort
    private java.lang.String ZHKmReviewWebserviceServiceImpPort_address = "http://oa.avicred.com/sys/webservice/zHKmReviewWebserviceService";

    public java.lang.String getZHKmReviewWebserviceServiceImpPortAddress() {
        return ZHKmReviewWebserviceServiceImpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ZHKmReviewWebserviceServiceImpPortWSDDServiceName = "ZHKmReviewWebserviceServiceImpPort";

    public java.lang.String getZHKmReviewWebserviceServiceImpPortWSDDServiceName() {
        return ZHKmReviewWebserviceServiceImpPortWSDDServiceName;
    }

    public void setZHKmReviewWebserviceServiceImpPortWSDDServiceName(java.lang.String name) {
        ZHKmReviewWebserviceServiceImpPortWSDDServiceName = name;
    }

    public com.landray.kmss.km.importfile.webservice.ZHIKmReviewWebserviceService getZHKmReviewWebserviceServiceImpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ZHKmReviewWebserviceServiceImpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getZHKmReviewWebserviceServiceImpPort(endpoint);
    }

    public com.landray.kmss.km.importfile.webservice.ZHIKmReviewWebserviceService getZHKmReviewWebserviceServiceImpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpServiceSoapBindingStub _stub = new com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getZHKmReviewWebserviceServiceImpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setZHKmReviewWebserviceServiceImpPortEndpointAddress(java.lang.String address) {
        ZHKmReviewWebserviceServiceImpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.landray.kmss.km.importfile.webservice.ZHIKmReviewWebserviceService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpServiceSoapBindingStub _stub = new com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpServiceSoapBindingStub(new java.net.URL(ZHKmReviewWebserviceServiceImpPort_address), this);
                _stub.setPortName(getZHKmReviewWebserviceServiceImpPortWSDDServiceName());
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
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ZHKmReviewWebserviceServiceImpPort".equals(inputPortName)) {
            return getZHKmReviewWebserviceServiceImpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.importfile.km.kmss.landray.com/", "ZHKmReviewWebserviceServiceImpService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.importfile.km.kmss.landray.com/", "ZHKmReviewWebserviceServiceImpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ZHKmReviewWebserviceServiceImpPort".equals(portName)) {
            setZHKmReviewWebserviceServiceImpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
