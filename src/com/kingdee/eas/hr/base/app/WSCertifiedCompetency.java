package com.kingdee.eas.hr.base.app;

import com.kingdee.bos.webservice.WSBean;

public class WSCertifiedCompetency extends WSBean { 
    private String description ;

    private String number ;

    private String id ;

    private com.kingdee.eas.hr.base.app.WSCertifyCompetencyLevel level ;

    private int isStandard ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String name ;

    private String simpleName ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String createTime ;

    private com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit ;

    private com.kingdee.eas.hr.base.app.WSCertifiedCompetencyType category ;

    private String lastUpdateTime ;

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
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

    public com.kingdee.eas.hr.base.app.WSCertifyCompetencyLevel getLevel() {
        return this.level;
    }

    public void setLevel( com.kingdee.eas.hr.base.app.WSCertifyCompetencyLevel level) {
        this.level = level;
    }

    public int getIsStandard() {
        return this.isStandard;
    }

    public void setIsStandard( int isStandard) {
        this.isStandard = isStandard;
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

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
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

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public com.kingdee.eas.basedata.org.app.WSHROrgUnit getHrOrgUnit() {
        return this.hrOrgUnit;
    }

    public void setHrOrgUnit( com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit) {
        this.hrOrgUnit = hrOrgUnit;
    }

    public com.kingdee.eas.hr.base.app.WSCertifiedCompetencyType getCategory() {
        return this.category;
    }

    public void setCategory( com.kingdee.eas.hr.base.app.WSCertifiedCompetencyType category) {
        this.category = category;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

}