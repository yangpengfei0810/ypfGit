package com.kingdee.eas.basedata.master.auxacct.app;

import com.kingdee.bos.webservice.WSBean;

public class WSGeneralAsstActTypeGroup extends WSBean { 
    private String id ;

    private String description ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String customField1 ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String number ;

    private String name ;

    private String lastUpdateTime ;

    private String createTime ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String simpleName ;

    private String shareTactic ;

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

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getCustomField1() {
        return this.customField1;
    }

    public void setCustomField1( String customField1) {
        this.customField1 = customField1;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public String getShareTactic() {
        return this.shareTactic;
    }

    public void setShareTactic( String shareTactic) {
        this.shareTactic = shareTactic;
    }

}