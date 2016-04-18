package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSOUPartFI extends WSBean { 
    private com.kingdee.eas.basedata.assistant.app.WSRegion region ;

    private com.kingdee.eas.basedata.person.app.WSPerson juridicalPerson ;

    private com.kingdee.eas.basedata.assistant.app.WSCurrency baseCurrency ;

    private boolean isOnlyUnion ;

    private double registeredCapital ;

    private com.kingdee.eas.basedata.assistant.app.WSExchangeTable adjustExchangeTable ;

    private com.kingdee.eas.basedata.assistant.app.WSPeriodType accountPeriodType ;

    private String id ;

    private String versionNumber ;

    private boolean isGroup ;

    private com.kingdee.eas.basedata.assistant.app.WSExchangeTable reportExchangeTable ;

    private String economicType ;

    private com.kingdee.eas.fi.books.app.WSAcountScheme accountScheme ;

    private boolean isChurchyard ;

    private boolean isBizUnit ;

    private String companyDescription ;

    private boolean isSealUp ;

    private com.kingdee.eas.basedata.assistant.app.WSIndustry industry ;

    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountBank accountBank ;

    private String territory ;

    private String setupDate ;

    private com.kingdee.eas.basedata.assistant.app.WSExchangeTable baseExchangeTable ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTable ;

    private String propertySealUpDate ;

    private com.kingdee.eas.basedata.assistant.app.WSAddress address ;

    private com.kingdee.eas.basedata.assistant.app.WSCurrency reportCurrency ;

    private String registeredCode ;

    private String reportConvertMode ;

    private String endupDate ;

    private String taxNumber ;

    public com.kingdee.eas.basedata.assistant.app.WSRegion getRegion() {
        return this.region;
    }

    public void setRegion( com.kingdee.eas.basedata.assistant.app.WSRegion region) {
        this.region = region;
    }

    public com.kingdee.eas.basedata.person.app.WSPerson getJuridicalPerson() {
        return this.juridicalPerson;
    }

    public void setJuridicalPerson( com.kingdee.eas.basedata.person.app.WSPerson juridicalPerson) {
        this.juridicalPerson = juridicalPerson;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCurrency getBaseCurrency() {
        return this.baseCurrency;
    }

    public void setBaseCurrency( com.kingdee.eas.basedata.assistant.app.WSCurrency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public boolean getIsOnlyUnion() {
        return this.isOnlyUnion;
    }

    public void setIsOnlyUnion( boolean isOnlyUnion) {
        this.isOnlyUnion = isOnlyUnion;
    }

    public double getRegisteredCapital() {
        return this.registeredCapital;
    }

    public void setRegisteredCapital( double registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public com.kingdee.eas.basedata.assistant.app.WSExchangeTable getAdjustExchangeTable() {
        return this.adjustExchangeTable;
    }

    public void setAdjustExchangeTable( com.kingdee.eas.basedata.assistant.app.WSExchangeTable adjustExchangeTable) {
        this.adjustExchangeTable = adjustExchangeTable;
    }

    public com.kingdee.eas.basedata.assistant.app.WSPeriodType getAccountPeriodType() {
        return this.accountPeriodType;
    }

    public void setAccountPeriodType( com.kingdee.eas.basedata.assistant.app.WSPeriodType accountPeriodType) {
        this.accountPeriodType = accountPeriodType;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getVersionNumber() {
        return this.versionNumber;
    }

    public void setVersionNumber( String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public boolean getIsGroup() {
        return this.isGroup;
    }

    public void setIsGroup( boolean isGroup) {
        this.isGroup = isGroup;
    }

    public com.kingdee.eas.basedata.assistant.app.WSExchangeTable getReportExchangeTable() {
        return this.reportExchangeTable;
    }

    public void setReportExchangeTable( com.kingdee.eas.basedata.assistant.app.WSExchangeTable reportExchangeTable) {
        this.reportExchangeTable = reportExchangeTable;
    }

    public String getEconomicType() {
        return this.economicType;
    }

    public void setEconomicType( String economicType) {
        this.economicType = economicType;
    }

    public com.kingdee.eas.fi.books.app.WSAcountScheme getAccountScheme() {
        return this.accountScheme;
    }

    public void setAccountScheme( com.kingdee.eas.fi.books.app.WSAcountScheme accountScheme) {
        this.accountScheme = accountScheme;
    }

    public boolean getIsChurchyard() {
        return this.isChurchyard;
    }

    public void setIsChurchyard( boolean isChurchyard) {
        this.isChurchyard = isChurchyard;
    }

    public boolean getIsBizUnit() {
        return this.isBizUnit;
    }

    public void setIsBizUnit( boolean isBizUnit) {
        this.isBizUnit = isBizUnit;
    }

    public String getCompanyDescription() {
        return this.companyDescription;
    }

    public void setCompanyDescription( String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public boolean getIsSealUp() {
        return this.isSealUp;
    }

    public void setIsSealUp( boolean isSealUp) {
        this.isSealUp = isSealUp;
    }

    public com.kingdee.eas.basedata.assistant.app.WSIndustry getIndustry() {
        return this.industry;
    }

    public void setIndustry( com.kingdee.eas.basedata.assistant.app.WSIndustry industry) {
        this.industry = industry;
    }

    public com.kingdee.eas.basedata.org.app.WSFullOrgUnit getUnit() {
        return this.unit;
    }

    public void setUnit( com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit) {
        this.unit = unit;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountBank getAccountBank() {
        return this.accountBank;
    }

    public void setAccountBank( com.kingdee.eas.basedata.assistant.app.WSAccountBank accountBank) {
        this.accountBank = accountBank;
    }

    public String getTerritory() {
        return this.territory;
    }

    public void setTerritory( String territory) {
        this.territory = territory;
    }

    public String getSetupDate() {
        return this.setupDate;
    }

    public void setSetupDate( String setupDate) {
        this.setupDate = setupDate;
    }

    public com.kingdee.eas.basedata.assistant.app.WSExchangeTable getBaseExchangeTable() {
        return this.baseExchangeTable;
    }

    public void setBaseExchangeTable( com.kingdee.eas.basedata.assistant.app.WSExchangeTable baseExchangeTable) {
        this.baseExchangeTable = baseExchangeTable;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountTable getAccountTable() {
        return this.accountTable;
    }

    public void setAccountTable( com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTable) {
        this.accountTable = accountTable;
    }

    public String getPropertySealUpDate() {
        return this.propertySealUpDate;
    }

    public void setPropertySealUpDate( String propertySealUpDate) {
        this.propertySealUpDate = propertySealUpDate;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAddress getAddress() {
        return this.address;
    }

    public void setAddress( com.kingdee.eas.basedata.assistant.app.WSAddress address) {
        this.address = address;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCurrency getReportCurrency() {
        return this.reportCurrency;
    }

    public void setReportCurrency( com.kingdee.eas.basedata.assistant.app.WSCurrency reportCurrency) {
        this.reportCurrency = reportCurrency;
    }

    public String getRegisteredCode() {
        return this.registeredCode;
    }

    public void setRegisteredCode( String registeredCode) {
        this.registeredCode = registeredCode;
    }

    public String getReportConvertMode() {
        return this.reportConvertMode;
    }

    public void setReportConvertMode( String reportConvertMode) {
        this.reportConvertMode = reportConvertMode;
    }

    public String getEndupDate() {
        return this.endupDate;
    }

    public void setEndupDate( String endupDate) {
        this.endupDate = endupDate;
    }

    public String getTaxNumber() {
        return this.taxNumber;
    }

    public void setTaxNumber( String taxNumber) {
        this.taxNumber = taxNumber;
    }

}