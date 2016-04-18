package com.kingdee.eas.basedata.master.account.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAccountType extends WSBean { 
    private com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTableID ;

    private int level ;

    private String lastUpdateTime ;

    private String property ;

    private String longNumber ;

    private boolean isLeaf ;

    private String displayName ;

    private String number ;

    private String id ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String simpleName ;

    private String name ;

    private String description ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountType parent ;

    private String createTime ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    public com.kingdee.eas.basedata.master.account.app.WSAccountTable getAccountTableID() {
        return this.accountTableID;
    }

    public void setAccountTableID( com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTableID) {
        this.accountTableID = accountTableID;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty( String property) {
        this.property = property;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
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

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
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

    public com.kingdee.eas.basedata.master.account.app.WSAccountType getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.master.account.app.WSAccountType parent) {
        this.parent = parent;
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

}