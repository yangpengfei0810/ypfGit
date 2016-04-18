package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSCurrency extends WSBean { 
    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String createTime ;

    private String lastUpdateTime ;

    private int precision ;

    private String number ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String name ;

    private String sign ;

    private String deletedStatus ;

    private String id ;

    private String simpleName ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String isoCode ;

    private String baseUnit ;

    private String description ;

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision( int precision) {
        this.precision = precision;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
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

    public String getSign() {
        return this.sign;
    }

    public void setSign( String sign) {
        this.sign = sign;
    }

    public String getDeletedStatus() {
        return this.deletedStatus;
    }

    public void setDeletedStatus( String deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
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

    public String getIsoCode() {
        return this.isoCode;
    }

    public void setIsoCode( String isoCode) {
        this.isoCode = isoCode;
    }

    public String getBaseUnit() {
        return this.baseUnit;
    }

    public void setBaseUnit( String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

}