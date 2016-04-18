package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSExchangeTable extends WSBean { 
    private String name ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String lastUpdateTime ;

    private boolean isReversible ;

    private String simpleName ;

    private com.kingdee.eas.basedata.assistant.app.WSCurrency baseCurrency ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String description ;

    private String createTime ;

    private String id ;

    private boolean isMulExchange ;

    private String number ;

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
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

    public boolean getIsReversible() {
        return this.isReversible;
    }

    public void setIsReversible( boolean isReversible) {
        this.isReversible = isReversible;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCurrency getBaseCurrency() {
        return this.baseCurrency;
    }

    public void setBaseCurrency( com.kingdee.eas.basedata.assistant.app.WSCurrency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public boolean getIsMulExchange() {
        return this.isMulExchange;
    }

    public void setIsMulExchange( boolean isMulExchange) {
        this.isMulExchange = isMulExchange;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

}