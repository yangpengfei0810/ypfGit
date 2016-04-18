package com.kingdee.eas.hr.base.app;

import com.kingdee.bos.webservice.WSBean;

public class WSCertifiedCompetencyType extends WSBean { 
    private String name ;

    private String description ;

    private com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit ;

    private com.kingdee.eas.hr.base.app.WSCertifiedCompetencyType parent ;

    private int level ;

    private String number ;

    private boolean isLeaf ;

    private String createTime ;

    private String simpleName ;

    private String id ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private int isStandard ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String lastUpdateTime ;

    private String displayName ;

    private String longNumber ;

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

    public com.kingdee.eas.basedata.org.app.WSHROrgUnit getHrOrgUnit() {
        return this.hrOrgUnit;
    }

    public void setHrOrgUnit( com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit) {
        this.hrOrgUnit = hrOrgUnit;
    }

    public com.kingdee.eas.hr.base.app.WSCertifiedCompetencyType getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.hr.base.app.WSCertifiedCompetencyType parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
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

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public int getIsStandard() {
        return this.isStandard;
    }

    public void setIsStandard( int isStandard) {
        this.isStandard = isStandard;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

}