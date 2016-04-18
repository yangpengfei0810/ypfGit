package com.kingdee.eas.basedata.master.auxacct.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAsstAccount extends WSBean { 
    private String name ;

    private String lastUpdateTime ;

    private String simpleName ;

    private boolean isActCussent ;

    private com.kingdee.eas.basedata.master.auxacct.app.WSAsstActGroupDetail[] asstActGpDt ;

    private boolean isQty ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String description ;

    private boolean isCommon ;

    private boolean isCompany ;

    private com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup measureUnitGroup ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String createTime ;

    private String id ;

    private String number ;

    private int count ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company ;

    private com.kingdee.eas.basedata.assistant.app.WSMeasureUnit measureUnit ;

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
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

    public boolean getIsActCussent() {
        return this.isActCussent;
    }

    public void setIsActCussent( boolean isActCussent) {
        this.isActCussent = isActCussent;
    }

    public com.kingdee.eas.basedata.master.auxacct.app.WSAsstActGroupDetail[] getAsstActGpDt() {
        return this.asstActGpDt;
    }

    public void setAsstActGpDt( com.kingdee.eas.basedata.master.auxacct.app.WSAsstActGroupDetail[] asstActGpDt) {
        this.asstActGpDt = asstActGpDt;
    }

    public boolean getIsQty() {
        return this.isQty;
    }

    public void setIsQty( boolean isQty) {
        this.isQty = isQty;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public boolean getIsCommon() {
        return this.isCommon;
    }

    public void setIsCommon( boolean isCommon) {
        this.isCommon = isCommon;
    }

    public boolean getIsCompany() {
        return this.isCompany;
    }

    public void setIsCompany( boolean isCompany) {
        this.isCompany = isCompany;
    }

    public com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup getMeasureUnitGroup() {
        return this.measureUnitGroup;
    }

    public void setMeasureUnitGroup( com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup measureUnitGroup) {
        this.measureUnitGroup = measureUnitGroup;
    }

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

    public int getCount() {
        return this.count;
    }

    public void setCount( int count) {
        this.count = count;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getCompany() {
        return this.company;
    }

    public void setCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company) {
        this.company = company;
    }

    public com.kingdee.eas.basedata.assistant.app.WSMeasureUnit getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit( com.kingdee.eas.basedata.assistant.app.WSMeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

}