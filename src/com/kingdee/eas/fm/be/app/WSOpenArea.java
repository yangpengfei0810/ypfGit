package com.kingdee.eas.fm.be.app;

import com.kingdee.bos.webservice.WSBean;

public class WSOpenArea extends WSBean { 
    private String bankInterfaceType ;

    private String createTime ;

    private String id ;

    private String deptID ;

    private String number ;

    private String simpleName ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String province ;

    private String name ;

    private String description ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String unionCode ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private boolean difBank ;

    private String lastUpdateTime ;

    public String getBankInterfaceType() {
        return this.bankInterfaceType;
    }

    public void setBankInterfaceType( String bankInterfaceType) {
        this.bankInterfaceType = bankInterfaceType;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getDeptID() {
        return this.deptID;
    }

    public void setDeptID( String deptID) {
        this.deptID = deptID;
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

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince( String province) {
        this.province = province;
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

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getUnionCode() {
        return this.unionCode;
    }

    public void setUnionCode( String unionCode) {
        this.unionCode = unionCode;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public boolean getDifBank() {
        return this.difBank;
    }

    public void setDifBank( boolean difBank) {
        this.difBank = difBank;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

}