package com.kingdee.eas.basedata.master.account.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAccount_Control extends WSBean { 
    private int isQty ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit destCu ;

    private String id ;

    private int measureUnitGroupID ;

    private int plType ;

    private String createTime ;

    private int isChangeCurrency ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit destCompany ;

    private int dc ;

    private int measureUnitID ;

    private int isCountAccrual ;

    private int control ;

    private String lastUpdateTime ;

    private int isCashEquivalent ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit srcCu ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private int currencyID ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTableID ;

    private int bw ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private int accrualPer ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit srcCompany ;

    private boolean isDefaultForRefer ;

    private int helpCode ;

    private int ac ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private int acNotice ;

    private int isCash ;

    public int getIsQty() {
        return this.isQty;
    }

    public void setIsQty( int isQty) {
        this.isQty = isQty;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getDestCu() {
        return this.destCu;
    }

    public void setDestCu( com.kingdee.eas.basedata.org.app.WSCtrlUnit destCu) {
        this.destCu = destCu;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public int getMeasureUnitGroupID() {
        return this.measureUnitGroupID;
    }

    public void setMeasureUnitGroupID( int measureUnitGroupID) {
        this.measureUnitGroupID = measureUnitGroupID;
    }

    public int getPlType() {
        return this.plType;
    }

    public void setPlType( int plType) {
        this.plType = plType;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public int getIsChangeCurrency() {
        return this.isChangeCurrency;
    }

    public void setIsChangeCurrency( int isChangeCurrency) {
        this.isChangeCurrency = isChangeCurrency;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getDestCompany() {
        return this.destCompany;
    }

    public void setDestCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit destCompany) {
        this.destCompany = destCompany;
    }

    public int getDc() {
        return this.dc;
    }

    public void setDc( int dc) {
        this.dc = dc;
    }

    public int getMeasureUnitID() {
        return this.measureUnitID;
    }

    public void setMeasureUnitID( int measureUnitID) {
        this.measureUnitID = measureUnitID;
    }

    public int getIsCountAccrual() {
        return this.isCountAccrual;
    }

    public void setIsCountAccrual( int isCountAccrual) {
        this.isCountAccrual = isCountAccrual;
    }

    public int getControl() {
        return this.control;
    }

    public void setControl( int control) {
        this.control = control;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getIsCashEquivalent() {
        return this.isCashEquivalent;
    }

    public void setIsCashEquivalent( int isCashEquivalent) {
        this.isCashEquivalent = isCashEquivalent;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getSrcCu() {
        return this.srcCu;
    }

    public void setSrcCu( com.kingdee.eas.basedata.org.app.WSCtrlUnit srcCu) {
        this.srcCu = srcCu;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public int getCurrencyID() {
        return this.currencyID;
    }

    public void setCurrencyID( int currencyID) {
        this.currencyID = currencyID;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountTable getAccountTableID() {
        return this.accountTableID;
    }

    public void setAccountTableID( com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTableID) {
        this.accountTableID = accountTableID;
    }

    public int getBw() {
        return this.bw;
    }

    public void setBw( int bw) {
        this.bw = bw;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public int getAccrualPer() {
        return this.accrualPer;
    }

    public void setAccrualPer( int accrualPer) {
        this.accrualPer = accrualPer;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getSrcCompany() {
        return this.srcCompany;
    }

    public void setSrcCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit srcCompany) {
        this.srcCompany = srcCompany;
    }

    public boolean getIsDefaultForRefer() {
        return this.isDefaultForRefer;
    }

    public void setIsDefaultForRefer( boolean isDefaultForRefer) {
        this.isDefaultForRefer = isDefaultForRefer;
    }

    public int getHelpCode() {
        return this.helpCode;
    }

    public void setHelpCode( int helpCode) {
        this.helpCode = helpCode;
    }

    public int getAc() {
        return this.ac;
    }

    public void setAc( int ac) {
        this.ac = ac;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public int getAcNotice() {
        return this.acNotice;
    }

    public void setAcNotice( int acNotice) {
        this.acNotice = acNotice;
    }

    public int getIsCash() {
        return this.isCash;
    }

    public void setIsCash( int isCash) {
        this.isCash = isCash;
    }

}