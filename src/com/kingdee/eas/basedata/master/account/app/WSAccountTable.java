package com.kingdee.eas.basedata.master.account.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAccountTable extends WSBean { 
    private boolean isControlNumber ;

    private boolean isUserLevel ;

    private String number ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountUserLevel[] userLevel ;

    private boolean isUseSeparator ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company ;

    private String createTime ;

    private com.kingdee.eas.basedata.master.account.app.WSAccount_Control[] control ;

    private String separator ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String simpleName ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String id ;

    private String description ;

    private String name ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    public boolean getIsControlNumber() {
        return this.isControlNumber;
    }

    public void setIsControlNumber( boolean isControlNumber) {
        this.isControlNumber = isControlNumber;
    }

    public boolean getIsUserLevel() {
        return this.isUserLevel;
    }

    public void setIsUserLevel( boolean isUserLevel) {
        this.isUserLevel = isUserLevel;
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

    public com.kingdee.eas.basedata.master.account.app.WSAccountUserLevel[] getUserLevel() {
        return this.userLevel;
    }

    public void setUserLevel( com.kingdee.eas.basedata.master.account.app.WSAccountUserLevel[] userLevel) {
        this.userLevel = userLevel;
    }

    public boolean getIsUseSeparator() {
        return this.isUseSeparator;
    }

    public void setIsUseSeparator( boolean isUseSeparator) {
        this.isUseSeparator = isUseSeparator;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getCompany() {
        return this.company;
    }

    public void setCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company) {
        this.company = company;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccount_Control[] getControl() {
        return this.control;
    }

    public void setControl( com.kingdee.eas.basedata.master.account.app.WSAccount_Control[] control) {
        this.control = control;
    }

    public String getSeparator() {
        return this.separator;
    }

    public void setSeparator( String separator) {
        this.separator = separator;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
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

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
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

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

}