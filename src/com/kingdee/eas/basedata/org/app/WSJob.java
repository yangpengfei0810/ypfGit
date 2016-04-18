package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSJob extends WSBean { 
    private com.kingdee.eas.basedata.org.app.WSCtrlUnit adminCU ;

    private com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit ;

    private com.kingdee.eas.basedata.org.app.WSJobCategory jobCategory ;

    private String number ;

    private String createTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private com.kingdee.eas.basedata.org.app.WSJobType jobType ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String description ;

    private String name ;

    private String simpleName ;

    private String id ;

    private String lastUpdateTime ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getAdminCU() {
        return this.adminCU;
    }

    public void setAdminCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit adminCU) {
        this.adminCU = adminCU;
    }

    public com.kingdee.eas.basedata.org.app.WSHROrgUnit getHrOrgUnit() {
        return this.hrOrgUnit;
    }

    public void setHrOrgUnit( com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit) {
        this.hrOrgUnit = hrOrgUnit;
    }

    public com.kingdee.eas.basedata.org.app.WSJobCategory getJobCategory() {
        return this.jobCategory;
    }

    public void setJobCategory( com.kingdee.eas.basedata.org.app.WSJobCategory jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
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

    public com.kingdee.eas.basedata.org.app.WSJobType getJobType() {
        return this.jobType;
    }

    public void setJobType( com.kingdee.eas.basedata.org.app.WSJobType jobType) {
        this.jobType = jobType;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
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

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

}