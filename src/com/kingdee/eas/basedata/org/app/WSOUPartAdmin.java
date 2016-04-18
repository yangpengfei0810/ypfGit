package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSOUPartAdmin extends WSBean { 
    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit ;

    private String registeredCode ;

    private double registeredCapital ;

    private String id ;

    private String phoneNumber ;

    private com.kingdee.eas.basedata.person.app.WSPerson juridicalPerson ;

    private boolean isJuridicalCompany ;

    private String baseDuty ;

    private String adminAddress ;

    private boolean isVirtual ;

    private String versionNumber ;

    private com.kingdee.eas.basedata.org.app.WSPosition responPosition ;

    private String propertySealUpDate ;

    private String taxNumber ;

    private com.kingdee.eas.basedata.assistant.app.WSAddress address ;

    private com.kingdee.eas.basedata.org.app.WSJobSystem jobSystem ;

    private boolean isEntity ;

    private String setupDate ;

    private boolean isIndependence ;

    private String economicType ;

    private int index ;

    private boolean isChurchyard ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit controlUnit ;

    private com.kingdee.eas.basedata.person.app.WSPerson principal ;

    private String territory ;

    private boolean isSealUp ;

    private String fax ;

    private com.kingdee.eas.basedata.assistant.app.WSIndustry industry ;

    private com.kingdee.eas.basedata.org.app.WSOrgUnitLayer unitLayer ;

    private String zipCode ;

    private String endupDate ;

    private com.kingdee.eas.basedata.org.app.WSOrgUnitLayerType unitLayerType ;

    public com.kingdee.eas.basedata.org.app.WSFullOrgUnit getUnit() {
        return this.unit;
    }

    public void setUnit( com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit) {
        this.unit = unit;
    }

    public String getRegisteredCode() {
        return this.registeredCode;
    }

    public void setRegisteredCode( String registeredCode) {
        this.registeredCode = registeredCode;
    }

    public double getRegisteredCapital() {
        return this.registeredCapital;
    }

    public void setRegisteredCapital( double registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public com.kingdee.eas.basedata.person.app.WSPerson getJuridicalPerson() {
        return this.juridicalPerson;
    }

    public void setJuridicalPerson( com.kingdee.eas.basedata.person.app.WSPerson juridicalPerson) {
        this.juridicalPerson = juridicalPerson;
    }

    public boolean getIsJuridicalCompany() {
        return this.isJuridicalCompany;
    }

    public void setIsJuridicalCompany( boolean isJuridicalCompany) {
        this.isJuridicalCompany = isJuridicalCompany;
    }

    public String getBaseDuty() {
        return this.baseDuty;
    }

    public void setBaseDuty( String baseDuty) {
        this.baseDuty = baseDuty;
    }

    public String getAdminAddress() {
        return this.adminAddress;
    }

    public void setAdminAddress( String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public boolean getIsVirtual() {
        return this.isVirtual;
    }

    public void setIsVirtual( boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getVersionNumber() {
        return this.versionNumber;
    }

    public void setVersionNumber( String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public com.kingdee.eas.basedata.org.app.WSPosition getResponPosition() {
        return this.responPosition;
    }

    public void setResponPosition( com.kingdee.eas.basedata.org.app.WSPosition responPosition) {
        this.responPosition = responPosition;
    }

    public String getPropertySealUpDate() {
        return this.propertySealUpDate;
    }

    public void setPropertySealUpDate( String propertySealUpDate) {
        this.propertySealUpDate = propertySealUpDate;
    }

    public String getTaxNumber() {
        return this.taxNumber;
    }

    public void setTaxNumber( String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAddress getAddress() {
        return this.address;
    }

    public void setAddress( com.kingdee.eas.basedata.assistant.app.WSAddress address) {
        this.address = address;
    }

    public com.kingdee.eas.basedata.org.app.WSJobSystem getJobSystem() {
        return this.jobSystem;
    }

    public void setJobSystem( com.kingdee.eas.basedata.org.app.WSJobSystem jobSystem) {
        this.jobSystem = jobSystem;
    }

    public boolean getIsEntity() {
        return this.isEntity;
    }

    public void setIsEntity( boolean isEntity) {
        this.isEntity = isEntity;
    }

    public String getSetupDate() {
        return this.setupDate;
    }

    public void setSetupDate( String setupDate) {
        this.setupDate = setupDate;
    }

    public boolean getIsIndependence() {
        return this.isIndependence;
    }

    public void setIsIndependence( boolean isIndependence) {
        this.isIndependence = isIndependence;
    }

    public String getEconomicType() {
        return this.economicType;
    }

    public void setEconomicType( String economicType) {
        this.economicType = economicType;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex( int index) {
        this.index = index;
    }

    public boolean getIsChurchyard() {
        return this.isChurchyard;
    }

    public void setIsChurchyard( boolean isChurchyard) {
        this.isChurchyard = isChurchyard;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getControlUnit() {
        return this.controlUnit;
    }

    public void setControlUnit( com.kingdee.eas.basedata.org.app.WSCtrlUnit controlUnit) {
        this.controlUnit = controlUnit;
    }

    public com.kingdee.eas.basedata.person.app.WSPerson getPrincipal() {
        return this.principal;
    }

    public void setPrincipal( com.kingdee.eas.basedata.person.app.WSPerson principal) {
        this.principal = principal;
    }

    public String getTerritory() {
        return this.territory;
    }

    public void setTerritory( String territory) {
        this.territory = territory;
    }

    public boolean getIsSealUp() {
        return this.isSealUp;
    }

    public void setIsSealUp( boolean isSealUp) {
        this.isSealUp = isSealUp;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax( String fax) {
        this.fax = fax;
    }

    public com.kingdee.eas.basedata.assistant.app.WSIndustry getIndustry() {
        return this.industry;
    }

    public void setIndustry( com.kingdee.eas.basedata.assistant.app.WSIndustry industry) {
        this.industry = industry;
    }

    public com.kingdee.eas.basedata.org.app.WSOrgUnitLayer getUnitLayer() {
        return this.unitLayer;
    }

    public void setUnitLayer( com.kingdee.eas.basedata.org.app.WSOrgUnitLayer unitLayer) {
        this.unitLayer = unitLayer;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode( String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEndupDate() {
        return this.endupDate;
    }

    public void setEndupDate( String endupDate) {
        this.endupDate = endupDate;
    }

    public com.kingdee.eas.basedata.org.app.WSOrgUnitLayerType getUnitLayerType() {
        return this.unitLayerType;
    }

    public void setUnitLayerType( com.kingdee.eas.basedata.org.app.WSOrgUnitLayerType unitLayerType) {
        this.unitLayerType = unitLayerType;
    }

}