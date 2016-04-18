package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSJobCategory extends WSBean { 
    private String simpleName ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private boolean isLeaf ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String id ;

    private int level ;

    private com.kingdee.eas.basedata.org.app.WSJobSystem jobSystem ;

    private String description ;

    private com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit ;

    private String name ;

    private String number ;

    private String lastUpdateTime ;

    private String longNumber ;

    private com.kingdee.eas.basedata.org.app.WSJobCategory parent ;

    private String createTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

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

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public com.kingdee.eas.basedata.org.app.WSJobSystem getJobSystem() {
        return this.jobSystem;
    }

    public void setJobSystem( com.kingdee.eas.basedata.org.app.WSJobSystem jobSystem) {
        this.jobSystem = jobSystem;
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

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public com.kingdee.eas.basedata.org.app.WSJobCategory getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.org.app.WSJobCategory parent) {
        this.parent = parent;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

}