package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSPosition extends WSBean { 
    private com.kingdee.eas.hr.base.app.WSPositionTypeLdap pubProperLdap ;

    private String effectDate ;

    private int fluCheckTime ;

    private com.kingdee.eas.basedata.hraux.app.WSPositionType PositionType ;

    private String number ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String simpleName ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String createTime ;

    private String deletedStatus ;

    private boolean isRespPosition ;

    private int index ;

    private com.kingdee.eas.basedata.org.app.WSJob job ;

    private com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit ;

    private String valiDate ;

    private String name ;

    private com.kingdee.eas.basedata.org.app.WSAdminOrgUnit adminOrgUnit ;

    private String id ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String description ;

    public com.kingdee.eas.hr.base.app.WSPositionTypeLdap getPubProperLdap() {
        return this.pubProperLdap;
    }

    public void setPubProperLdap( com.kingdee.eas.hr.base.app.WSPositionTypeLdap pubProperLdap) {
        this.pubProperLdap = pubProperLdap;
    }

    public String getEffectDate() {
        return this.effectDate;
    }

    public void setEffectDate( String effectDate) {
        this.effectDate = effectDate;
    }

    public int getFluCheckTime() {
        return this.fluCheckTime;
    }

    public void setFluCheckTime( int fluCheckTime) {
        this.fluCheckTime = fluCheckTime;
    }

    public com.kingdee.eas.basedata.hraux.app.WSPositionType getPositionType() {
        return this.PositionType;
    }

    public void setPositionType( com.kingdee.eas.basedata.hraux.app.WSPositionType PositionType) {
        this.PositionType = PositionType;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
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

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getDeletedStatus() {
        return this.deletedStatus;
    }

    public void setDeletedStatus( String deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public boolean getIsRespPosition() {
        return this.isRespPosition;
    }

    public void setIsRespPosition( boolean isRespPosition) {
        this.isRespPosition = isRespPosition;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex( int index) {
        this.index = index;
    }

    public com.kingdee.eas.basedata.org.app.WSJob getJob() {
        return this.job;
    }

    public void setJob( com.kingdee.eas.basedata.org.app.WSJob job) {
        this.job = job;
    }

    public com.kingdee.eas.basedata.org.app.WSHROrgUnit getHrOrgUnit() {
        return this.hrOrgUnit;
    }

    public void setHrOrgUnit( com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit) {
        this.hrOrgUnit = hrOrgUnit;
    }

    public String getValiDate() {
        return this.valiDate;
    }

    public void setValiDate( String valiDate) {
        this.valiDate = valiDate;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public com.kingdee.eas.basedata.org.app.WSAdminOrgUnit getAdminOrgUnit() {
        return this.adminOrgUnit;
    }

    public void setAdminOrgUnit( com.kingdee.eas.basedata.org.app.WSAdminOrgUnit adminOrgUnit) {
        this.adminOrgUnit = adminOrgUnit;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

}