package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSCity extends WSBean { 
    private com.kingdee.eas.basedata.assistant.app.WSProvince province ;

    private String createTime ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String deletedStatus ;

    private String simpleName ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private boolean isDirCity ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String name ;

    private String description ;

    private String lastUpdateTime ;

    private String cityNumber ;

    private String id ;

    private String number ;

    public com.kingdee.eas.basedata.assistant.app.WSProvince getProvince() {
        return this.province;
    }

    public void setProvince( com.kingdee.eas.basedata.assistant.app.WSProvince province) {
        this.province = province;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getDeletedStatus() {
        return this.deletedStatus;
    }

    public void setDeletedStatus( String deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public boolean getIsDirCity() {
        return this.isDirCity;
    }

    public void setIsDirCity( boolean isDirCity) {
        this.isDirCity = isDirCity;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCityNumber() {
        return this.cityNumber;
    }

    public void setCityNumber( String cityNumber) {
        this.cityNumber = cityNumber;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

}