package com.kingdee.eas.fm.fs.app;

import com.kingdee.bos.webservice.WSBean;

public class WSInnerAccount extends WSBean { 
    private String closingDate ;

    private String simpleCode ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountBank deFaultAcctBank ;

    private String acctNumber ;

    private String description ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountProperty type ;

    private com.kingdee.eas.basedata.assistant.app.WSBank clearingHouse ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String openDepositDate ;

    private com.kingdee.eas.base.permission.app.WSUser shutter ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company ;

    private boolean isCollect ;

    private boolean isUsed ;

    private boolean isReckoning ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private boolean closed ;

    private String name ;

    private boolean isDefaultReck ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountView acctView ;

    private com.kingdee.eas.basedata.assistant.app.WSAccountMgrStrategy ctrlParam ;

    private com.kingdee.eas.basedata.assistant.app.WSKAClassfication clhClassification ;

    private String applyBillId ;

    private String setUsedDate ;

    private String number ;

    private String id ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit applyUnit ;

    private boolean linkIntObject ;

    private String createTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String lastUpdateTime ;

    private String simpleName ;

    public String getClosingDate() {
        return this.closingDate;
    }

    public void setClosingDate( String closingDate) {
        this.closingDate = closingDate;
    }

    public String getSimpleCode() {
        return this.simpleCode;
    }

    public void setSimpleCode( String simpleCode) {
        this.simpleCode = simpleCode;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountBank getDeFaultAcctBank() {
        return this.deFaultAcctBank;
    }

    public void setDeFaultAcctBank( com.kingdee.eas.basedata.assistant.app.WSAccountBank deFaultAcctBank) {
        this.deFaultAcctBank = deFaultAcctBank;
    }

    public String getAcctNumber() {
        return this.acctNumber;
    }

    public void setAcctNumber( String acctNumber) {
        this.acctNumber = acctNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountProperty getType() {
        return this.type;
    }

    public void setType( com.kingdee.eas.basedata.assistant.app.WSAccountProperty type) {
        this.type = type;
    }

    public com.kingdee.eas.basedata.assistant.app.WSBank getClearingHouse() {
        return this.clearingHouse;
    }

    public void setClearingHouse( com.kingdee.eas.basedata.assistant.app.WSBank clearingHouse) {
        this.clearingHouse = clearingHouse;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getOpenDepositDate() {
        return this.openDepositDate;
    }

    public void setOpenDepositDate( String openDepositDate) {
        this.openDepositDate = openDepositDate;
    }

    public com.kingdee.eas.base.permission.app.WSUser getShutter() {
        return this.shutter;
    }

    public void setShutter( com.kingdee.eas.base.permission.app.WSUser shutter) {
        this.shutter = shutter;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getCompany() {
        return this.company;
    }

    public void setCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company) {
        this.company = company;
    }

    public boolean getIsCollect() {
        return this.isCollect;
    }

    public void setIsCollect( boolean isCollect) {
        this.isCollect = isCollect;
    }

    public boolean getIsUsed() {
        return this.isUsed;
    }

    public void setIsUsed( boolean isUsed) {
        this.isUsed = isUsed;
    }

    public boolean getIsReckoning() {
        return this.isReckoning;
    }

    public void setIsReckoning( boolean isReckoning) {
        this.isReckoning = isReckoning;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public boolean getClosed() {
        return this.closed;
    }

    public void setClosed( boolean closed) {
        this.closed = closed;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public boolean getIsDefaultReck() {
        return this.isDefaultReck;
    }

    public void setIsDefaultReck( boolean isDefaultReck) {
        this.isDefaultReck = isDefaultReck;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountView getAcctView() {
        return this.acctView;
    }

    public void setAcctView( com.kingdee.eas.basedata.master.account.app.WSAccountView acctView) {
        this.acctView = acctView;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAccountMgrStrategy getCtrlParam() {
        return this.ctrlParam;
    }

    public void setCtrlParam( com.kingdee.eas.basedata.assistant.app.WSAccountMgrStrategy ctrlParam) {
        this.ctrlParam = ctrlParam;
    }

    public com.kingdee.eas.basedata.assistant.app.WSKAClassfication getClhClassification() {
        return this.clhClassification;
    }

    public void setClhClassification( com.kingdee.eas.basedata.assistant.app.WSKAClassfication clhClassification) {
        this.clhClassification = clhClassification;
    }

    public String getApplyBillId() {
        return this.applyBillId;
    }

    public void setApplyBillId( String applyBillId) {
        this.applyBillId = applyBillId;
    }

    public String getSetUsedDate() {
        return this.setUsedDate;
    }

    public void setSetUsedDate( String setUsedDate) {
        this.setUsedDate = setUsedDate;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getApplyUnit() {
        return this.applyUnit;
    }

    public void setApplyUnit( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit applyUnit) {
        this.applyUnit = applyUnit;
    }

    public boolean getLinkIntObject() {
        return this.linkIntObject;
    }

    public void setLinkIntObject( boolean linkIntObject) {
        this.linkIntObject = linkIntObject;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

}