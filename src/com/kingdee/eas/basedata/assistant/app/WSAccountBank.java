package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAccountBank extends WSBean { 
    private String phoneNumber ;

    private String bankAccountNumber ;

    private com.kingdee.eas.basedata.assistant.app.WSBank bank ;

    private com.kingdee.eas.basedata.assistant.app.WSCurrency currency ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountProperty property ;

    private boolean isMotherAccount ;

    private double maxPayAmount ;

    private boolean isUseGroupPayment ;

    private com.kingdee.eas.basedata.assistant.app.WSKAClassfication classificatio ;

    private boolean isCash ;

    private com.kingdee.eas.fi.cas.subacct.app.WSSubAccount subAccount ;

    private boolean isSetBankInterface ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String applyBillId ;

    private String createTime ;

    private boolean isOnlyRead ;

    private String simpleCode ;

    private String id ;

    private com.kingdee.eas.fm.fs.app.WSInnerAccount InnerAcct ;

    private boolean isClosed ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountMgrStrategy ctrlStrategy ;

    private String closeDate ;

    private boolean notOutPay ;

    private String accountType ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private boolean isReckoning ;

    private boolean isByCurrency ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountView account ;

    private String reference ;

    private boolean isBank ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit agencyCompany ;

    private String acctName ;

    private String simpleName ;

    private com.kingdee.eas.basedata.assistant.app.WSAcctBankTrusterEntries[] trusterEntries ;

    private String bankInterfaceType ;

    private com.kingdee.eas.fm.be.app.WSOpenArea openArea ;

    private String description ;

    private String bankCert ;

    private boolean isDefaultReck ;

    private boolean isDCPay ;

    private String number ;

    private String openDate ;

    private String name ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountBank releMotherAcct ;

    private String bankVersion ;

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBankAccountNumber() {
        return this.bankAccountNumber;
    }

    public void setBankAccountNumber( String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public com.kingdee.eas.basedata.assistant.app.WSBank getBank() {
        return this.bank;
    }

    public void setBank( com.kingdee.eas.basedata.assistant.app.WSBank bank) {
        this.bank = bank;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCurrency getCurrency() {
        return this.currency;
    }

    public void setCurrency( com.kingdee.eas.basedata.assistant.app.WSCurrency currency) {
        this.currency = currency;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountProperty getProperty() {
        return this.property;
    }

    public void setProperty( com.kingdee.eas.basedata.assistant.app.WSAccountProperty property) {
        this.property = property;
    }

    public boolean getIsMotherAccount() {
        return this.isMotherAccount;
    }

    public void setIsMotherAccount( boolean isMotherAccount) {
        this.isMotherAccount = isMotherAccount;
    }

    public double getMaxPayAmount() {
        return this.maxPayAmount;
    }

    public void setMaxPayAmount( double maxPayAmount) {
        this.maxPayAmount = maxPayAmount;
    }

    public boolean getIsUseGroupPayment() {
        return this.isUseGroupPayment;
    }

    public void setIsUseGroupPayment( boolean isUseGroupPayment) {
        this.isUseGroupPayment = isUseGroupPayment;
    }

    public com.kingdee.eas.basedata.assistant.app.WSKAClassfication getClassificatio() {
        return this.classificatio;
    }

    public void setClassificatio( com.kingdee.eas.basedata.assistant.app.WSKAClassfication classificatio) {
        this.classificatio = classificatio;
    }

    public boolean getIsCash() {
        return this.isCash;
    }

    public void setIsCash( boolean isCash) {
        this.isCash = isCash;
    }

    public com.kingdee.eas.fi.cas.subacct.app.WSSubAccount getSubAccount() {
        return this.subAccount;
    }

    public void setSubAccount( com.kingdee.eas.fi.cas.subacct.app.WSSubAccount subAccount) {
        this.subAccount = subAccount;
    }

    public boolean getIsSetBankInterface() {
        return this.isSetBankInterface;
    }

    public void setIsSetBankInterface( boolean isSetBankInterface) {
        this.isSetBankInterface = isSetBankInterface;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getApplyBillId() {
        return this.applyBillId;
    }

    public void setApplyBillId( String applyBillId) {
        this.applyBillId = applyBillId;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public boolean getIsOnlyRead() {
        return this.isOnlyRead;
    }

    public void setIsOnlyRead( boolean isOnlyRead) {
        this.isOnlyRead = isOnlyRead;
    }

    public String getSimpleCode() {
        return this.simpleCode;
    }

    public void setSimpleCode( String simpleCode) {
        this.simpleCode = simpleCode;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.fm.fs.app.WSInnerAccount getInnerAcct() {
        return this.InnerAcct;
    }

    public void setInnerAcct( com.kingdee.eas.fm.fs.app.WSInnerAccount InnerAcct) {
        this.InnerAcct = InnerAcct;
    }

    public boolean getIsClosed() {
        return this.isClosed;
    }

    public void setIsClosed( boolean isClosed) {
        this.isClosed = isClosed;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getCompany() {
        return this.company;
    }

    public void setCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company) {
        this.company = company;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountMgrStrategy getCtrlStrategy() {
        return this.ctrlStrategy;
    }

    public void setCtrlStrategy( com.kingdee.eas.basedata.assistant.app.WSAccountMgrStrategy ctrlStrategy) {
        this.ctrlStrategy = ctrlStrategy;
    }

    public String getCloseDate() {
        return this.closeDate;
    }

    public void setCloseDate( String closeDate) {
        this.closeDate = closeDate;
    }

    public boolean getNotOutPay() {
        return this.notOutPay;
    }

    public void setNotOutPay( boolean notOutPay) {
        this.notOutPay = notOutPay;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType( String accountType) {
        this.accountType = accountType;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public boolean getIsReckoning() {
        return this.isReckoning;
    }

    public void setIsReckoning( boolean isReckoning) {
        this.isReckoning = isReckoning;
    }

    public boolean getIsByCurrency() {
        return this.isByCurrency;
    }

    public void setIsByCurrency( boolean isByCurrency) {
        this.isByCurrency = isByCurrency;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountView getAccount() {
        return this.account;
    }

    public void setAccount( com.kingdee.eas.basedata.master.account.app.WSAccountView account) {
        this.account = account;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference( String reference) {
        this.reference = reference;
    }

    public boolean getIsBank() {
        return this.isBank;
    }

    public void setIsBank( boolean isBank) {
        this.isBank = isBank;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getAgencyCompany() {
        return this.agencyCompany;
    }

    public void setAgencyCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit agencyCompany) {
        this.agencyCompany = agencyCompany;
    }

    public String getAcctName() {
        return this.acctName;
    }

    public void setAcctName( String acctName) {
        this.acctName = acctName;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAcctBankTrusterEntries[] getTrusterEntries() {
        return this.trusterEntries;
    }

    public void setTrusterEntries( com.kingdee.eas.basedata.assistant.app.WSAcctBankTrusterEntries[] trusterEntries) {
        this.trusterEntries = trusterEntries;
    }

    public String getBankInterfaceType() {
        return this.bankInterfaceType;
    }

    public void setBankInterfaceType( String bankInterfaceType) {
        this.bankInterfaceType = bankInterfaceType;
    }

    public com.kingdee.eas.fm.be.app.WSOpenArea getOpenArea() {
        return this.openArea;
    }

    public void setOpenArea( com.kingdee.eas.fm.be.app.WSOpenArea openArea) {
        this.openArea = openArea;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public String getBankCert() {
        return this.bankCert;
    }

    public void setBankCert( String bankCert) {
        this.bankCert = bankCert;
    }

    public boolean getIsDefaultReck() {
        return this.isDefaultReck;
    }

    public void setIsDefaultReck( boolean isDefaultReck) {
        this.isDefaultReck = isDefaultReck;
    }

    public boolean getIsDCPay() {
        return this.isDCPay;
    }

    public void setIsDCPay( boolean isDCPay) {
        this.isDCPay = isDCPay;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public String getOpenDate() {
        return this.openDate;
    }

    public void setOpenDate( String openDate) {
        this.openDate = openDate;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountBank getReleMotherAcct() {
        return this.releMotherAcct;
    }

    public void setReleMotherAcct( com.kingdee.eas.basedata.assistant.app.WSAccountBank releMotherAcct) {
        this.releMotherAcct = releMotherAcct;
    }

    public String getBankVersion() {
        return this.bankVersion;
    }

    public void setBankVersion( String bankVersion) {
        this.bankVersion = bankVersion;
    }

}