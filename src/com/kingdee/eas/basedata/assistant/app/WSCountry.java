package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSCountry extends WSBean { 
    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String description ;

    private String ISOCode ;

    private String number ;

    private String id ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String simpleName ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String lastUpdateTime ;

    private String createTime ;

    private String name ;

    private String deletedStatus ;

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public String getISOCode() {
        return this.ISOCode;
    }

    public void setISOCode( String ISOCode) {
        this.ISOCode = ISOCode;
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

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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

    public String getDeletedStatus() {
        return this.deletedStatus;
    }

    public void setDeletedStatus( String deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

}