package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSKObjectType extends WSBean { 
    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String objectTypeAlias ;

    private String number ;

    private String simpleName ;

    private String id ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String objectTypeID ;

    private String description ;

    private String createTime ;

    private String objectTypeName ;

    private String name ;

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

    public String getObjectTypeAlias() {
        return this.objectTypeAlias;
    }

    public void setObjectTypeAlias( String objectTypeAlias) {
        this.objectTypeAlias = objectTypeAlias;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
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

    public String getObjectTypeID() {
        return this.objectTypeID;
    }

    public void setObjectTypeID( String objectTypeID) {
        this.objectTypeID = objectTypeID;
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

    public String getObjectTypeName() {
        return this.objectTypeName;
    }

    public void setObjectTypeName( String objectTypeName) {
        this.objectTypeName = objectTypeName;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

}