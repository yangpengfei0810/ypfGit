package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSKAClassfication extends WSBean { 
    private String id ;

    private com.kingdee.eas.basedata.assistant.app.WSKACLlassficationType typelink ;

    private String simpleName ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String description ;

    private String createTime ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit currencyCompany ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String name ;

    private com.kingdee.eas.basedata.assistant.app.WSKAClassficationDetail[] detailCollection ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String number ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTableId ;

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.assistant.app.WSKACLlassficationType getTypelink() {
        return this.typelink;
    }

    public void setTypelink( com.kingdee.eas.basedata.assistant.app.WSKACLlassficationType typelink) {
        this.typelink = typelink;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
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

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getCurrencyCompany() {
        return this.currencyCompany;
    }

    public void setCurrencyCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit currencyCompany) {
        this.currencyCompany = currencyCompany;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public com.kingdee.eas.basedata.assistant.app.WSKAClassficationDetail[] getDetailCollection() {
        return this.detailCollection;
    }

    public void setDetailCollection( com.kingdee.eas.basedata.assistant.app.WSKAClassficationDetail[] detailCollection) {
        this.detailCollection = detailCollection;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountTable getAccountTableId() {
        return this.accountTableId;
    }

    public void setAccountTableId( com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTableId) {
        this.accountTableId = accountTableId;
    }

}