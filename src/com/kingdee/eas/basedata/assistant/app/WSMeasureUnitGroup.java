package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSMeasureUnitGroup extends WSBean { 
    private String displayName ;

    private String createTime ;

    private String disableDate ;

    private com.kingdee.eas.basedata.assistant.app.WSMeasureUnit defaultUnit ;

    private boolean isLeaf ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String name ;

    private String number ;

    private String simpleName ;

    private String id ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String longNumber ;

    private String lastUpdateTime ;

    private int level ;

    private com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup parent ;

    private String description ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getDisableDate() {
        return this.disableDate;
    }

    public void setDisableDate( String disableDate) {
        this.disableDate = disableDate;
    }

    public com.kingdee.eas.basedata.assistant.app.WSMeasureUnit getDefaultUnit() {
        return this.defaultUnit;
    }

    public void setDefaultUnit( com.kingdee.eas.basedata.assistant.app.WSMeasureUnit defaultUnit) {
        this.defaultUnit = defaultUnit;
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

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

}