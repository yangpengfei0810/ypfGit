package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAdminOrgUnit extends WSBean { 
    private String invalidDate ;

    private String setupDate ;

    private boolean isChurchyard ;

    private String economicType ;

    private com.kingdee.eas.basedata.org.app.WSPosition responPosition ;

    private String taxNumber ;

    private String registeredCode ;

    private boolean isEntity ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private int index ;

    private String simpleName ;

    private com.kingdee.eas.basedata.assistant.app.WSIndustry industry ;

    private boolean isAdminOrgUnit ;

    private String propertySealUpDate ;

    private boolean isLeaf ;

    private String id ;

    private boolean isGrouping ;

    private String description ;

    private String versionNumber ;

    private String zipCode ;

    private com.kingdee.eas.basedata.person.app.WSPerson juridicalPerson ;

    private com.kingdee.eas.basedata.assistant.app.WSAddress address ;

    private boolean isPurchaseOrgUnit ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String name ;

    private String number ;

    private String orgTypeStr ;

    private String phoneNumber ;

    private com.kingdee.eas.basedata.person.app.WSPerson principal ;

    private boolean isSealUp ;

    private double registeredCapital ;

    private String adminAddress ;

    private boolean isCostOrgUnit ;

    private boolean isHROrgUnit ;

    private boolean isTransportOrgUnit ;

    private boolean isCompanyOrgUnit ;

    private boolean isSaleOrgUnit ;

    private String fax ;

    private boolean isStorageOrgUnit ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.basedata.org.app.WSAdminOrgUnit parent ;

    private String territory ;

    private String effectDate ;

    private String longNumber ;

    private boolean isVirtual ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.org.app.WSOrgUnitLayer unitLayer ;

    private boolean isOUSealUp ;

    private boolean isFreeze ;

    private boolean isStart ;

    private String displayName ;

    private boolean isIndependence ;

    private String endupDate ;

    private String sortCode ;

    private String createTime ;

    private int level ;

    private boolean isUnion ;

    private com.kingdee.eas.basedata.org.app.WSJobSystem jobSystem ;

    private com.kingdee.eas.basedata.org.app.WSOrgUnitLayerType unitLayerType ;

    private boolean isCU ;

    private String code ;

    private boolean isJuridicalCompany ;

    private boolean isQualityOrgUnit ;

    private String baseDuty ;

    private boolean isProfitOrgUnit ;

    public String getInvalidDate() {
        return this.invalidDate;
    }

    public void setInvalidDate( String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getSetupDate() {
        return this.setupDate;
    }

    public void setSetupDate( String setupDate) {
        this.setupDate = setupDate;
    }

    public boolean getIsChurchyard() {
        return this.isChurchyard;
    }

    public void setIsChurchyard( boolean isChurchyard) {
        this.isChurchyard = isChurchyard;
    }

    public String getEconomicType() {
        return this.economicType;
    }

    public void setEconomicType( String economicType) {
        this.economicType = economicType;
    }

    public com.kingdee.eas.basedata.org.app.WSPosition getResponPosition() {
        return this.responPosition;
    }

    public void setResponPosition( com.kingdee.eas.basedata.org.app.WSPosition responPosition) {
        this.responPosition = responPosition;
    }

    public String getTaxNumber() {
        return this.taxNumber;
    }

    public void setTaxNumber( String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getRegisteredCode() {
        return this.registeredCode;
    }

    public void setRegisteredCode( String registeredCode) {
        this.registeredCode = registeredCode;
    }

    public boolean getIsEntity() {
        return this.isEntity;
    }

    public void setIsEntity( boolean isEntity) {
        this.isEntity = isEntity;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex( int index) {
        this.index = index;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public com.kingdee.eas.basedata.assistant.app.WSIndustry getIndustry() {
        return this.industry;
    }

    public void setIndustry( com.kingdee.eas.basedata.assistant.app.WSIndustry industry) {
        this.industry = industry;
    }

    public boolean getIsAdminOrgUnit() {
        return this.isAdminOrgUnit;
    }

    public void setIsAdminOrgUnit( boolean isAdminOrgUnit) {
        this.isAdminOrgUnit = isAdminOrgUnit;
    }

    public String getPropertySealUpDate() {
        return this.propertySealUpDate;
    }

    public void setPropertySealUpDate( String propertySealUpDate) {
        this.propertySealUpDate = propertySealUpDate;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public boolean getIsGrouping() {
        return this.isGrouping;
    }

    public void setIsGrouping( boolean isGrouping) {
        this.isGrouping = isGrouping;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public String getVersionNumber() {
        return this.versionNumber;
    }

    public void setVersionNumber( String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode( String zipCode) {
        this.zipCode = zipCode;
    }

    public com.kingdee.eas.basedata.person.app.WSPerson getJuridicalPerson() {
        return this.juridicalPerson;
    }

    public void setJuridicalPerson( com.kingdee.eas.basedata.person.app.WSPerson juridicalPerson) {
        this.juridicalPerson = juridicalPerson;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAddress getAddress() {
        return this.address;
    }

    public void setAddress( com.kingdee.eas.basedata.assistant.app.WSAddress address) {
        this.address = address;
    }

    public boolean getIsPurchaseOrgUnit() {
        return this.isPurchaseOrgUnit;
    }

    public void setIsPurchaseOrgUnit( boolean isPurchaseOrgUnit) {
        this.isPurchaseOrgUnit = isPurchaseOrgUnit;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public String getOrgTypeStr() {
        return this.orgTypeStr;
    }

    public void setOrgTypeStr( String orgTypeStr) {
        this.orgTypeStr = orgTypeStr;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public com.kingdee.eas.basedata.person.app.WSPerson getPrincipal() {
        return this.principal;
    }

    public void setPrincipal( com.kingdee.eas.basedata.person.app.WSPerson principal) {
        this.principal = principal;
    }

    public boolean getIsSealUp() {
        return this.isSealUp;
    }

    public void setIsSealUp( boolean isSealUp) {
        this.isSealUp = isSealUp;
    }

    public double getRegisteredCapital() {
        return this.registeredCapital;
    }

    public void setRegisteredCapital( double registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getAdminAddress() {
        return this.adminAddress;
    }

    public void setAdminAddress( String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public boolean getIsCostOrgUnit() {
        return this.isCostOrgUnit;
    }

    public void setIsCostOrgUnit( boolean isCostOrgUnit) {
        this.isCostOrgUnit = isCostOrgUnit;
    }

    public boolean getIsHROrgUnit() {
        return this.isHROrgUnit;
    }

    public void setIsHROrgUnit( boolean isHROrgUnit) {
        this.isHROrgUnit = isHROrgUnit;
    }

    public boolean getIsTransportOrgUnit() {
        return this.isTransportOrgUnit;
    }

    public void setIsTransportOrgUnit( boolean isTransportOrgUnit) {
        this.isTransportOrgUnit = isTransportOrgUnit;
    }

    public boolean getIsCompanyOrgUnit() {
        return this.isCompanyOrgUnit;
    }

    public void setIsCompanyOrgUnit( boolean isCompanyOrgUnit) {
        this.isCompanyOrgUnit = isCompanyOrgUnit;
    }

    public boolean getIsSaleOrgUnit() {
        return this.isSaleOrgUnit;
    }

    public void setIsSaleOrgUnit( boolean isSaleOrgUnit) {
        this.isSaleOrgUnit = isSaleOrgUnit;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax( String fax) {
        this.fax = fax;
    }

    public boolean getIsStorageOrgUnit() {
        return this.isStorageOrgUnit;
    }

    public void setIsStorageOrgUnit( boolean isStorageOrgUnit) {
        this.isStorageOrgUnit = isStorageOrgUnit;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public com.kingdee.eas.basedata.org.app.WSAdminOrgUnit getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.org.app.WSAdminOrgUnit parent) {
        this.parent = parent;
    }

    public String getTerritory() {
        return this.territory;
    }

    public void setTerritory( String territory) {
        this.territory = territory;
    }

    public String getEffectDate() {
        return this.effectDate;
    }

    public void setEffectDate( String effectDate) {
        this.effectDate = effectDate;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public boolean getIsVirtual() {
        return this.isVirtual;
    }

    public void setIsVirtual( boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public com.kingdee.eas.basedata.org.app.WSOrgUnitLayer getUnitLayer() {
        return this.unitLayer;
    }

    public void setUnitLayer( com.kingdee.eas.basedata.org.app.WSOrgUnitLayer unitLayer) {
        this.unitLayer = unitLayer;
    }

    public boolean getIsOUSealUp() {
        return this.isOUSealUp;
    }

    public void setIsOUSealUp( boolean isOUSealUp) {
        this.isOUSealUp = isOUSealUp;
    }

    public boolean getIsFreeze() {
        return this.isFreeze;
    }

    public void setIsFreeze( boolean isFreeze) {
        this.isFreeze = isFreeze;
    }

    public boolean getIsStart() {
        return this.isStart;
    }

    public void setIsStart( boolean isStart) {
        this.isStart = isStart;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
    }

    public boolean getIsIndependence() {
        return this.isIndependence;
    }

    public void setIsIndependence( boolean isIndependence) {
        this.isIndependence = isIndependence;
    }

    public String getEndupDate() {
        return this.endupDate;
    }

    public void setEndupDate( String endupDate) {
        this.endupDate = endupDate;
    }

    public String getSortCode() {
        return this.sortCode;
    }

    public void setSortCode( String sortCode) {
        this.sortCode = sortCode;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public boolean getIsUnion() {
        return this.isUnion;
    }

    public void setIsUnion( boolean isUnion) {
        this.isUnion = isUnion;
    }

    public com.kingdee.eas.basedata.org.app.WSJobSystem getJobSystem() {
        return this.jobSystem;
    }

    public void setJobSystem( com.kingdee.eas.basedata.org.app.WSJobSystem jobSystem) {
        this.jobSystem = jobSystem;
    }

    public com.kingdee.eas.basedata.org.app.WSOrgUnitLayerType getUnitLayerType() {
        return this.unitLayerType;
    }

    public void setUnitLayerType( com.kingdee.eas.basedata.org.app.WSOrgUnitLayerType unitLayerType) {
        this.unitLayerType = unitLayerType;
    }

    public boolean getIsCU() {
        return this.isCU;
    }

    public void setIsCU( boolean isCU) {
        this.isCU = isCU;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode( String code) {
        this.code = code;
    }

    public boolean getIsJuridicalCompany() {
        return this.isJuridicalCompany;
    }

    public void setIsJuridicalCompany( boolean isJuridicalCompany) {
        this.isJuridicalCompany = isJuridicalCompany;
    }

    public boolean getIsQualityOrgUnit() {
        return this.isQualityOrgUnit;
    }

    public void setIsQualityOrgUnit( boolean isQualityOrgUnit) {
        this.isQualityOrgUnit = isQualityOrgUnit;
    }

    public String getBaseDuty() {
        return this.baseDuty;
    }

    public void setBaseDuty( String baseDuty) {
        this.baseDuty = baseDuty;
    }

    public boolean getIsProfitOrgUnit() {
        return this.isProfitOrgUnit;
    }

    public void setIsProfitOrgUnit( boolean isProfitOrgUnit) {
        this.isProfitOrgUnit = isProfitOrgUnit;
    }

}