package com.kingdee.eas.hr.base.app;

import com.kingdee.bos.webservice.WSBean;

public class WSEmployeeType extends WSBean { 
    private int index ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String createTime ;

    private String description ;

    private String number ;

    private String lastUpdateTime ;

    private int inService ;

    private boolean isInner ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private boolean isOnTheStrength ;

    private com.kingdee.eas.hr.base.app.WSEmployeeFenLei employeeFenLei ;

    private String name ;

    private String simpleName ;

    private String id ;

    public int getIndex() {
        return this.index;
    }

    public void setIndex( int index) {
        this.index = index;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

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

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getInService() {
        return this.inService;
    }

    public void setInService( int inService) {
        this.inService = inService;
    }

    public boolean getIsInner() {
        return this.isInner;
    }

    public void setIsInner( boolean isInner) {
        this.isInner = isInner;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public boolean getIsOnTheStrength() {
        return this.isOnTheStrength;
    }

    public void setIsOnTheStrength( boolean isOnTheStrength) {
        this.isOnTheStrength = isOnTheStrength;
    }

    public com.kingdee.eas.hr.base.app.WSEmployeeFenLei getEmployeeFenLei() {
        return this.employeeFenLei;
    }

    public void setEmployeeFenLei( com.kingdee.eas.hr.base.app.WSEmployeeFenLei employeeFenLei) {
        this.employeeFenLei = employeeFenLei;
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

}