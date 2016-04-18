package com.kingdee.eas.base.permission.app;

import com.kingdee.bos.webservice.WSBean;

public class WSUser extends WSBean { 
    private String loginAuthorWay ;

    private com.kingdee.eas.base.permission.app.WSOrgRangeIncludeSubOrg[] orgRangeIncludeSubOrg ;

    private String lockedTime ;

    private String createTime ;

    private String referId ;

    private com.kingdee.eas.base.permission.app.WSRole mainRole ;

    private String password ;

    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit defOrgUnit ;

    private com.kingdee.eas.base.permission.app.WSOrgRange[] orgRange ;

    private boolean bizAdmin ;

    private com.kingdee.eas.base.permission.app.WSSecurity security ;

    private String lastUpdateTime ;

    private boolean isLocked ;

    private boolean isForbidden ;

    private String supplierID ;

    private String type ;

    private String cell ;

    private String effectiveDate ;

    private boolean agentUser ;

    private String officePhone ;

    private String pwdHisStr ;

    private String description ;

    private com.kingdee.eas.basedata.person.app.WSPerson person ;

    private String name ;

    private boolean isRegister ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String customerID ;

    private int errCount ;

    private String PWEffectiveDate ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String defLocale ;

    private String email ;

    private String backupEMail ;

    private String number ;

    private com.kingdee.eas.base.permission.app.WSUserGroup group ;

    private String id ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String invalidationDate ;

    private String homePhone ;

    private boolean changedPW ;

    private boolean isDelete ;

    public String getLoginAuthorWay() {
        return this.loginAuthorWay;
    }

    public void setLoginAuthorWay( String loginAuthorWay) {
        this.loginAuthorWay = loginAuthorWay;
    }

    public com.kingdee.eas.base.permission.app.WSOrgRangeIncludeSubOrg[] getOrgRangeIncludeSubOrg() {
        return this.orgRangeIncludeSubOrg;
    }

    public void setOrgRangeIncludeSubOrg( com.kingdee.eas.base.permission.app.WSOrgRangeIncludeSubOrg[] orgRangeIncludeSubOrg) {
        this.orgRangeIncludeSubOrg = orgRangeIncludeSubOrg;
    }

    public String getLockedTime() {
        return this.lockedTime;
    }

    public void setLockedTime( String lockedTime) {
        this.lockedTime = lockedTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getReferId() {
        return this.referId;
    }

    public void setReferId( String referId) {
        this.referId = referId;
    }

    public com.kingdee.eas.base.permission.app.WSRole getMainRole() {
        return this.mainRole;
    }

    public void setMainRole( com.kingdee.eas.base.permission.app.WSRole mainRole) {
        this.mainRole = mainRole;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public com.kingdee.eas.basedata.org.app.WSFullOrgUnit getDefOrgUnit() {
        return this.defOrgUnit;
    }

    public void setDefOrgUnit( com.kingdee.eas.basedata.org.app.WSFullOrgUnit defOrgUnit) {
        this.defOrgUnit = defOrgUnit;
    }

    public com.kingdee.eas.base.permission.app.WSOrgRange[] getOrgRange() {
        return this.orgRange;
    }

    public void setOrgRange( com.kingdee.eas.base.permission.app.WSOrgRange[] orgRange) {
        this.orgRange = orgRange;
    }

    public boolean getBizAdmin() {
        return this.bizAdmin;
    }

    public void setBizAdmin( boolean bizAdmin) {
        this.bizAdmin = bizAdmin;
    }

    public com.kingdee.eas.base.permission.app.WSSecurity getSecurity() {
        return this.security;
    }

    public void setSecurity( com.kingdee.eas.base.permission.app.WSSecurity security) {
        this.security = security;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public boolean getIsLocked() {
        return this.isLocked;
    }

    public void setIsLocked( boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean getIsForbidden() {
        return this.isForbidden;
    }

    public void setIsForbidden( boolean isForbidden) {
        this.isForbidden = isForbidden;
    }

    public String getSupplierID() {
        return this.supplierID;
    }

    public void setSupplierID( String supplierID) {
        this.supplierID = supplierID;
    }

    public String getType() {
        return this.type;
    }

    public void setType( String type) {
        this.type = type;
    }

    public String getCell() {
        return this.cell;
    }

    public void setCell( String cell) {
        this.cell = cell;
    }

    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate( String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public boolean getAgentUser() {
        return this.agentUser;
    }

    public void setAgentUser( boolean agentUser) {
        this.agentUser = agentUser;
    }

    public String getOfficePhone() {
        return this.officePhone;
    }

    public void setOfficePhone( String officePhone) {
        this.officePhone = officePhone;
    }

    public String getPwdHisStr() {
        return this.pwdHisStr;
    }

    public void setPwdHisStr( String pwdHisStr) {
        this.pwdHisStr = pwdHisStr;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public com.kingdee.eas.basedata.person.app.WSPerson getPerson() {
        return this.person;
    }

    public void setPerson( com.kingdee.eas.basedata.person.app.WSPerson person) {
        this.person = person;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public boolean getIsRegister() {
        return this.isRegister;
    }

    public void setIsRegister( boolean isRegister) {
        this.isRegister = isRegister;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID( String customerID) {
        this.customerID = customerID;
    }

    public int getErrCount() {
        return this.errCount;
    }

    public void setErrCount( int errCount) {
        this.errCount = errCount;
    }

    public String getPWEffectiveDate() {
        return this.PWEffectiveDate;
    }

    public void setPWEffectiveDate( String PWEffectiveDate) {
        this.PWEffectiveDate = PWEffectiveDate;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getDefLocale() {
        return this.defLocale;
    }

    public void setDefLocale( String defLocale) {
        this.defLocale = defLocale;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail( String email) {
        this.email = email;
    }

    public String getBackupEMail() {
        return this.backupEMail;
    }

    public void setBackupEMail( String backupEMail) {
        this.backupEMail = backupEMail;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public com.kingdee.eas.base.permission.app.WSUserGroup getGroup() {
        return this.group;
    }

    public void setGroup( com.kingdee.eas.base.permission.app.WSUserGroup group) {
        this.group = group;
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

    public String getInvalidationDate() {
        return this.invalidationDate;
    }

    public void setInvalidationDate( String invalidationDate) {
        this.invalidationDate = invalidationDate;
    }

    public String getHomePhone() {
        return this.homePhone;
    }

    public void setHomePhone( String homePhone) {
        this.homePhone = homePhone;
    }

    public boolean getChangedPW() {
        return this.changedPW;
    }

    public void setChangedPW( boolean changedPW) {
        this.changedPW = changedPW;
    }

    public boolean getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete( boolean isDelete) {
        this.isDelete = isDelete;
    }

}