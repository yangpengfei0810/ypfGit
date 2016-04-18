package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSCompanyOrgUnit extends WSBean { 
    private String territory ;

    private com.kingdee.eas.basedata.assistant.app.WSIndustry industry ;

    private com.kingdee.eas.fi.books.app.WSAcountScheme accountScheme ;

    private boolean isHROrgUnit ;

    private boolean isStart ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountBank accountBank ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit parent ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.basedata.assistant.app.WSAddress address ;

    private boolean isSaleOrgUnit ;

    private boolean isLeaf ;

    private int level ;

    private boolean isBizUnit ;

    private String registeredCode ;

    private boolean isChurchyard ;

    private boolean isUnion ;

    private String code ;

    private com.kingdee.eas.basedata.assistant.app.WSExchangeTable adjustExchangeTable ;

    private String id ;

    private com.kingdee.eas.basedata.assistant.app.WSExchangeTable reportExchangeTable ;

    private boolean isCU ;

    private boolean isAssistantOrg ;

    private String description ;

    private String propertySealUpDate ;

    private boolean isCompanyOrgUnit ;

    private String orgTypeStr ;

    private boolean isStorageOrgUnit ;

    private com.kingdee.eas.basedata.assistant.app.WSExchangeTable baseExchangeTable ;

    private String effectDate ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private double registeredCapital ;

    private boolean isTransportOrgUnit ;

    private boolean isSealUp ;

    private boolean isQualityOrgUnit ;

    private String name ;

    private String economicType ;

    private String reportConvertMode ;

    private com.kingdee.eas.basedata.assistant.app.WSCurrency baseCurrency ;

    private boolean isGrouping ;

    private String createTime ;

    private String number ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTable ;

    private boolean isOnlyUnion ;

    private com.kingdee.eas.basedata.assistant.app.WSCurrency reportCurrency ;

    private com.kingdee.eas.basedata.person.app.WSPerson juridicalPerson ;

    private String lastUpdateTime ;

    private boolean isCostOrgUnit ;

    private String versionNumber ;

    private String simpleName ;

    private boolean isProfitOrgUnit ;

    private boolean isGroup ;

    private String longNumber ;

    private String endupDate ;

    private boolean isOUSealUp ;

    private String displayName ;

    private String setupDate ;

    private boolean isFreeze ;

    private com.kingdee.eas.basedata.assistant.app.WSPeriodType accountPeriodType ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private boolean isAdminOrgUnit ;

    private String invalidDate ;

    private boolean isPurchaseOrgUnit ;

    private String taxNumber ;

    private com.kingdee.eas.basedata.assistant.app.WSRegion region ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit mainOrg ;

    public String getTerritory() {
        return this.territory;
    }

    public void setTerritory( String territory) {
        this.territory = territory;
    }

    public com.kingdee.eas.basedata.assistant.app.WSIndustry getIndustry() {
        return this.industry;
    }

    public void setIndustry( com.kingdee.eas.basedata.assistant.app.WSIndustry industry) {
        this.industry = industry;
    }

    public com.kingdee.eas.fi.books.app.WSAcountScheme getAccountScheme() {
        return this.accountScheme;
    }

    public void setAccountScheme( com.kingdee.eas.fi.books.app.WSAcountScheme accountScheme) {
        this.accountScheme = accountScheme;
    }

    public boolean getIsHROrgUnit() {
        return this.isHROrgUnit;
    }

    public void setIsHROrgUnit( boolean isHROrgUnit) {
        this.isHROrgUnit = isHROrgUnit;
    }

    public boolean getIsStart() {
        return this.isStart;
    }

    public void setIsStart( boolean isStart) {
        this.isStart = isStart;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountBank getAccountBank() {
        return this.accountBank;
    }

    public void setAccountBank( com.kingdee.eas.basedata.assistant.app.WSAccountBank accountBank) {
        this.accountBank = accountBank;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit parent) {
        this.parent = parent;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAddress getAddress() {
        return this.address;
    }

    public void setAddress( com.kingdee.eas.basedata.assistant.app.WSAddress address) {
        this.address = address;
    }

    public boolean getIsSaleOrgUnit() {
        return this.isSaleOrgUnit;
    }

    public void setIsSaleOrgUnit( boolean isSaleOrgUnit) {
        this.isSaleOrgUnit = isSaleOrgUnit;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public boolean getIsBizUnit() {
        return this.isBizUnit;
    }

    public void setIsBizUnit( boolean isBizUnit) {
        this.isBizUnit = isBizUnit;
    }

    public String getRegisteredCode() {
        return this.registeredCode;
    }

    public void setRegisteredCode( String registeredCode) {
        this.registeredCode = registeredCode;
    }

    public boolean getIsChurchyard() {
        return this.isChurchyard;
    }

    public void setIsChurchyard( boolean isChurchyard) {
        this.isChurchyard = isChurchyard;
    }

    public boolean getIsUnion() {
        return this.isUnion;
    }

    public void setIsUnion( boolean isUnion) {
        this.isUnion = isUnion;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode( String code) {
        this.code = code;
    }

    public com.kingdee.eas.basedata.assistant.app.WSExchangeTable getAdjustExchangeTable() {
        return this.adjustExchangeTable;
    }

    public void setAdjustExchangeTable( com.kingdee.eas.basedata.assistant.app.WSExchangeTable adjustExchangeTable) {
        this.adjustExchangeTable = adjustExchangeTable;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.assistant.app.WSExchangeTable getReportExchangeTable() {
        return this.reportExchangeTable;
    }

    public void setReportExchangeTable( com.kingdee.eas.basedata.assistant.app.WSExchangeTable reportExchangeTable) {
        this.reportExchangeTable = reportExchangeTable;
    }

    public boolean getIsCU() {
        return this.isCU;
    }

    public void setIsCU( boolean isCU) {
        this.isCU = isCU;
    }

    public boolean getIsAssistantOrg() {
        return this.isAssistantOrg;
    }

    public void setIsAssistantOrg( boolean isAssistantOrg) {
        this.isAssistantOrg = isAssistantOrg;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public String getPropertySealUpDate() {
        return this.propertySealUpDate;
    }

    public void setPropertySealUpDate( String propertySealUpDate) {
        this.propertySealUpDate = propertySealUpDate;
    }

    public boolean getIsCompanyOrgUnit() {
        return this.isCompanyOrgUnit;
    }

    public void setIsCompanyOrgUnit( boolean isCompanyOrgUnit) {
        this.isCompanyOrgUnit = isCompanyOrgUnit;
    }

    public String getOrgTypeStr() {
        return this.orgTypeStr;
    }

    public void setOrgTypeStr( String orgTypeStr) {
        this.orgTypeStr = orgTypeStr;
    }

    public boolean getIsStorageOrgUnit() {
        return this.isStorageOrgUnit;
    }

    public void setIsStorageOrgUnit( boolean isStorageOrgUnit) {
        this.isStorageOrgUnit = isStorageOrgUnit;
    }

    public com.kingdee.eas.basedata.assistant.app.WSExchangeTable getBaseExchangeTable() {
        return this.baseExchangeTable;
    }

    public void setBaseExchangeTable( com.kingdee.eas.basedata.assistant.app.WSExchangeTable baseExchangeTable) {
        this.baseExchangeTable = baseExchangeTable;
    }

    public String getEffectDate() {
        return this.effectDate;
    }

    public void setEffectDate( String effectDate) {
        this.effectDate = effectDate;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public double getRegisteredCapital() {
        return this.registeredCapital;
    }

    public void setRegisteredCapital( double registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public boolean getIsTransportOrgUnit() {
        return this.isTransportOrgUnit;
    }

    public void setIsTransportOrgUnit( boolean isTransportOrgUnit) {
        this.isTransportOrgUnit = isTransportOrgUnit;
    }

    public boolean getIsSealUp() {
        return this.isSealUp;
    }

    public void setIsSealUp( boolean isSealUp) {
        this.isSealUp = isSealUp;
    }

    public boolean getIsQualityOrgUnit() {
        return this.isQualityOrgUnit;
    }

    public void setIsQualityOrgUnit( boolean isQualityOrgUnit) {
        this.isQualityOrgUnit = isQualityOrgUnit;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getEconomicType() {
        return this.economicType;
    }

    public void setEconomicType( String economicType) {
        this.economicType = economicType;
    }

    public String getReportConvertMode() {
        return this.reportConvertMode;
    }

    public void setReportConvertMode( String reportConvertMode) {
        this.reportConvertMode = reportConvertMode;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCurrency getBaseCurrency() {
        return this.baseCurrency;
    }

    public void setBaseCurrency( com.kingdee.eas.basedata.assistant.app.WSCurrency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public boolean getIsGrouping() {
        return this.isGrouping;
    }

    public void setIsGrouping( boolean isGrouping) {
        this.isGrouping = isGrouping;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountTable getAccountTable() {
        return this.accountTable;
    }

    public void setAccountTable( com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTable) {
        this.accountTable = accountTable;
    }

    public boolean getIsOnlyUnion() {
        return this.isOnlyUnion;
    }

    public void setIsOnlyUnion( boolean isOnlyUnion) {
        this.isOnlyUnion = isOnlyUnion;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCurrency getReportCurrency() {
        return this.reportCurrency;
    }

    public void setReportCurrency( com.kingdee.eas.basedata.assistant.app.WSCurrency reportCurrency) {
        this.reportCurrency = reportCurrency;
    }

    public com.kingdee.eas.basedata.person.app.WSPerson getJuridicalPerson() {
        return this.juridicalPerson;
    }

    public void setJuridicalPerson( com.kingdee.eas.basedata.person.app.WSPerson juridicalPerson) {
        this.juridicalPerson = juridicalPerson;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public boolean getIsCostOrgUnit() {
        return this.isCostOrgUnit;
    }

    public void setIsCostOrgUnit( boolean isCostOrgUnit) {
        this.isCostOrgUnit = isCostOrgUnit;
    }

    public String getVersionNumber() {
        return this.versionNumber;
    }

    public void setVersionNumber( String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public boolean getIsProfitOrgUnit() {
        return this.isProfitOrgUnit;
    }

    public void setIsProfitOrgUnit( boolean isProfitOrgUnit) {
        this.isProfitOrgUnit = isProfitOrgUnit;
    }

    public boolean getIsGroup() {
        return this.isGroup;
    }

    public void setIsGroup( boolean isGroup) {
        this.isGroup = isGroup;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public String getEndupDate() {
        return this.endupDate;
    }

    public void setEndupDate( String endupDate) {
        this.endupDate = endupDate;
    }

    public boolean getIsOUSealUp() {
        return this.isOUSealUp;
    }

    public void setIsOUSealUp( boolean isOUSealUp) {
        this.isOUSealUp = isOUSealUp;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
    }

    public String getSetupDate() {
        return this.setupDate;
    }

    public void setSetupDate( String setupDate) {
        this.setupDate = setupDate;
    }

    public boolean getIsFreeze() {
        return this.isFreeze;
    }

    public void setIsFreeze( boolean isFreeze) {
        this.isFreeze = isFreeze;
    }

    public com.kingdee.eas.basedata.assistant.app.WSPeriodType getAccountPeriodType() {
        return this.accountPeriodType;
    }

    public void setAccountPeriodType( com.kingdee.eas.basedata.assistant.app.WSPeriodType accountPeriodType) {
        this.accountPeriodType = accountPeriodType;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public boolean getIsAdminOrgUnit() {
        return this.isAdminOrgUnit;
    }

    public void setIsAdminOrgUnit( boolean isAdminOrgUnit) {
        this.isAdminOrgUnit = isAdminOrgUnit;
    }

    public String getInvalidDate() {
        return this.invalidDate;
    }

    public void setInvalidDate( String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public boolean getIsPurchaseOrgUnit() {
        return this.isPurchaseOrgUnit;
    }

    public void setIsPurchaseOrgUnit( boolean isPurchaseOrgUnit) {
        this.isPurchaseOrgUnit = isPurchaseOrgUnit;
    }

    public String getTaxNumber() {
        return this.taxNumber;
    }

    public void setTaxNumber( String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public com.kingdee.eas.basedata.assistant.app.WSRegion getRegion() {
        return this.region;
    }

    public void setRegion( com.kingdee.eas.basedata.assistant.app.WSRegion region) {
        this.region = region;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getMainOrg() {
        return this.mainOrg;
    }

    public void setMainOrg( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit mainOrg) {
        this.mainOrg = mainOrg;
    }

}