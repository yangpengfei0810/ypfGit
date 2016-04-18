package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSKAccountItem extends WSBean { 
    private String description ;

    private String createTime ;

    private String name ;

    private String id ;

    private com.kingdee.eas.basedata.assistant.app.WSKACLlassficationType FKAccClftype ;

    private com.kingdee.eas.basedata.assistant.app.WSKObjectType objectType ;

    private boolean isSysCreate ;

    private String number ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String simpleName ;

    private String lastUpdateTime ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

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

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.assistant.app.WSKACLlassficationType getFKAccClftype() {
        return this.FKAccClftype;
    }

    public void setFKAccClftype( com.kingdee.eas.basedata.assistant.app.WSKACLlassficationType FKAccClftype) {
        this.FKAccClftype = FKAccClftype;
    }

    public com.kingdee.eas.basedata.assistant.app.WSKObjectType getObjectType() {
        return this.objectType;
    }

    public void setObjectType( com.kingdee.eas.basedata.assistant.app.WSKObjectType objectType) {
        this.objectType = objectType;
    }

    public boolean getIsSysCreate() {
        return this.isSysCreate;
    }

    public void setIsSysCreate( boolean isSysCreate) {
        this.isSysCreate = isSysCreate;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
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

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

}