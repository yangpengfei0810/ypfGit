package com.kingdee.eas.base.permission.app;

import com.kingdee.bos.webservice.WSBean;

public class WSOrgRange extends WSBean { 
    private String type ;

    private com.kingdee.eas.base.permission.app.WSUser user ;

    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit org ;

    private String id ;

    public String getType() {
        return this.type;
    }

    public void setType( String type) {
        this.type = type;
    }

    public com.kingdee.eas.base.permission.app.WSUser getUser() {
        return this.user;
    }

    public void setUser( com.kingdee.eas.base.permission.app.WSUser user) {
        this.user = user;
    }

    public com.kingdee.eas.basedata.org.app.WSFullOrgUnit getOrg() {
        return this.org;
    }

    public void setOrg( com.kingdee.eas.basedata.org.app.WSFullOrgUnit org) {
        this.org = org;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

}