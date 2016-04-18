package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSKAClassficationDetail extends WSBean { 
    private String number ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountView accountlink ;

    private String createTime ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private com.kingdee.eas.basedata.assistant.app.WSKAClassfication kaclassficlink ;

    private String lastUpdateTime ;

    private String id ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String name ;

    private String description ;

    private String simpleName ;

    private com.kingdee.eas.basedata.assistant.app.WSKAccountItem accountitemlink ;

    private com.kingdee.eas.basedata.assistant.app.WSKACLlassficationType kaclassficType ;

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountView getAccountlink() {
        return this.accountlink;
    }

    public void setAccountlink( com.kingdee.eas.basedata.master.account.app.WSAccountView accountlink) {
        this.accountlink = accountlink;
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

    public com.kingdee.eas.basedata.assistant.app.WSKAClassfication getKaclassficlink() {
        return this.kaclassficlink;
    }

    public void setKaclassficlink( com.kingdee.eas.basedata.assistant.app.WSKAClassfication kaclassficlink) {
        this.kaclassficlink = kaclassficlink;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
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

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public com.kingdee.eas.basedata.assistant.app.WSKAccountItem getAccountitemlink() {
        return this.accountitemlink;
    }

    public void setAccountitemlink( com.kingdee.eas.basedata.assistant.app.WSKAccountItem accountitemlink) {
        this.accountitemlink = accountitemlink;
    }

    public com.kingdee.eas.basedata.assistant.app.WSKACLlassficationType getKaclassficType() {
        return this.kaclassficType;
    }

    public void setKaclassficType( com.kingdee.eas.basedata.assistant.app.WSKACLlassficationType kaclassficType) {
        this.kaclassficType = kaclassficType;
    }

}