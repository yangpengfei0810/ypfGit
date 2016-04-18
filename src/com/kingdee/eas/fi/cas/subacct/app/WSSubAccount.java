package com.kingdee.eas.fi.cas.subacct.app;

import com.kingdee.bos.webservice.WSBean;

public class WSSubAccount extends WSBean { 
    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String createTime ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit relationalUnit ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountBank accountBank ;

    private String number ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountView accountView ;

    private com.kingdee.eas.basedata.assistant.app.WSBank bank ;

    private String deletedStatus ;

    private String description ;

    private com.kingdee.eas.fm.fs.app.WSInnerAccount innerAccount ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String simpleName ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountMgrStrategy ctrlStrategy ;

    private String name ;

    private String accountNumber ;

    private String id ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company ;

    private boolean isClosed ;

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getRelationalUnit() {
        return this.relationalUnit;
    }

    public void setRelationalUnit( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit relationalUnit) {
        this.relationalUnit = relationalUnit;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountBank getAccountBank() {
        return this.accountBank;
    }

    public void setAccountBank( com.kingdee.eas.basedata.assistant.app.WSAccountBank accountBank) {
        this.accountBank = accountBank;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountView getAccountView() {
        return this.accountView;
    }

    public void setAccountView( com.kingdee.eas.basedata.master.account.app.WSAccountView accountView) {
        this.accountView = accountView;
    }

    public com.kingdee.eas.basedata.assistant.app.WSBank getBank() {
        return this.bank;
    }

    public void setBank( com.kingdee.eas.basedata.assistant.app.WSBank bank) {
        this.bank = bank;
    }

    public String getDeletedStatus() {
        return this.deletedStatus;
    }

    public void setDeletedStatus( String deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public com.kingdee.eas.fm.fs.app.WSInnerAccount getInnerAccount() {
        return this.innerAccount;
    }

    public void setInnerAccount( com.kingdee.eas.fm.fs.app.WSInnerAccount innerAccount) {
        this.innerAccount = innerAccount;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountMgrStrategy getCtrlStrategy() {
        return this.ctrlStrategy;
    }

    public void setCtrlStrategy( com.kingdee.eas.basedata.assistant.app.WSAccountMgrStrategy ctrlStrategy) {
        this.ctrlStrategy = ctrlStrategy;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber( String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getCompany() {
        return this.company;
    }

    public void setCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company) {
        this.company = company;
    }

    public boolean getIsClosed() {
        return this.isClosed;
    }

    public void setIsClosed( boolean isClosed) {
        this.isClosed = isClosed;
    }

}