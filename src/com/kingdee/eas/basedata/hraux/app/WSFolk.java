package com.kingdee.eas.basedata.hraux.app;

import com.kingdee.bos.webservice.WSBean;

public class WSFolk extends WSBean { 
    private int index ;

    private String name ;

    private String simpleName ;

    private String number ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String description ;

    private String createTime ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String id ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    public int getIndex() {
        return this.index;
    }

    public void setIndex( int index) {
        this.index = index;
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

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
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

}