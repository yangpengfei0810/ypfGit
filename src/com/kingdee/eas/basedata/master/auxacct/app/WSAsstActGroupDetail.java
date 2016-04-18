package com.kingdee.eas.basedata.master.auxacct.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAsstActGroupDetail extends WSBean { 
    private String id ;

    private String number ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String lastUpdateTime ;

    private int seq ;

    private String description ;

    private com.kingdee.eas.basedata.master.auxacct.app.WSAsstActType asstActType ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String simpleName ;

    private String createTime ;

    private String name ;

    private com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount asstAccount ;

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq( int seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public com.kingdee.eas.basedata.master.auxacct.app.WSAsstActType getAsstActType() {
        return this.asstActType;
    }

    public void setAsstActType( com.kingdee.eas.basedata.master.auxacct.app.WSAsstActType asstActType) {
        this.asstActType = asstActType;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount getAsstAccount() {
        return this.asstAccount;
    }

    public void setAsstAccount( com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount asstAccount) {
        this.asstAccount = asstAccount;
    }

}