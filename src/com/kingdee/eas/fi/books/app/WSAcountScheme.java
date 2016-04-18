package com.kingdee.eas.fi.books.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAcountScheme extends WSBean { 
    private String number ;

    private boolean isSyncCard ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String description ;

    private com.kingdee.eas.basedata.assistant.app.WSExchangeTable rptRateTable ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String lastUpdateTime ;

    private String name ;

    private com.kingdee.eas.basedata.assistant.app.WSPeriodType periodType ;

    private com.kingdee.eas.basedata.assistant.app.WSExchangeTable adjRateTable ;

    private String id ;

    private com.kingdee.eas.basedata.assistant.app.WSCurrency currency ;

    private String type ;

    private String createTime ;

    private com.kingdee.eas.basedata.assistant.app.WSExchangeTable rateTable ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTable ;

    private String rule ;

    private boolean isMaster ;

    private String simpleName ;

    private com.kingdee.eas.basedata.assistant.app.WSCurrency reportCurrency ;

    private boolean isLock ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String bookType ;

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public boolean getIsSyncCard() {
        return this.isSyncCard;
    }

    public void setIsSyncCard( boolean isSyncCard) {
        this.isSyncCard = isSyncCard;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public com.kingdee.eas.basedata.assistant.app.WSExchangeTable getRptRateTable() {
        return this.rptRateTable;
    }

    public void setRptRateTable( com.kingdee.eas.basedata.assistant.app.WSExchangeTable rptRateTable) {
        this.rptRateTable = rptRateTable;
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

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public com.kingdee.eas.basedata.assistant.app.WSPeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType( com.kingdee.eas.basedata.assistant.app.WSPeriodType periodType) {
        this.periodType = periodType;
    }

    public com.kingdee.eas.basedata.assistant.app.WSExchangeTable getAdjRateTable() {
        return this.adjRateTable;
    }

    public void setAdjRateTable( com.kingdee.eas.basedata.assistant.app.WSExchangeTable adjRateTable) {
        this.adjRateTable = adjRateTable;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCurrency getCurrency() {
        return this.currency;
    }

    public void setCurrency( com.kingdee.eas.basedata.assistant.app.WSCurrency currency) {
        this.currency = currency;
    }

    public String getType() {
        return this.type;
    }

    public void setType( String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public com.kingdee.eas.basedata.assistant.app.WSExchangeTable getRateTable() {
        return this.rateTable;
    }

    public void setRateTable( com.kingdee.eas.basedata.assistant.app.WSExchangeTable rateTable) {
        this.rateTable = rateTable;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountTable getAccountTable() {
        return this.accountTable;
    }

    public void setAccountTable( com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTable) {
        this.accountTable = accountTable;
    }

    public String getRule() {
        return this.rule;
    }

    public void setRule( String rule) {
        this.rule = rule;
    }

    public boolean getIsMaster() {
        return this.isMaster;
    }

    public void setIsMaster( boolean isMaster) {
        this.isMaster = isMaster;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCurrency getReportCurrency() {
        return this.reportCurrency;
    }

    public void setReportCurrency( com.kingdee.eas.basedata.assistant.app.WSCurrency reportCurrency) {
        this.reportCurrency = reportCurrency;
    }

    public boolean getIsLock() {
        return this.isLock;
    }

    public void setIsLock( boolean isLock) {
        this.isLock = isLock;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getBookType() {
        return this.bookType;
    }

    public void setBookType( String bookType) {
        this.bookType = bookType;
    }

}