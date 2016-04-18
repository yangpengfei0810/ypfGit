package com.kingdee.eas.base.permission.app;

import com.kingdee.bos.webservice.WSBean;

public class WSOrgRangeIncludeSubOrg extends WSBean { 
    private String type ;

    private com.kingdee.eas.base.permission.app.WSUser user ;

    private String id ;

    private boolean isIncludeSubOrg ;

    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit org ;

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

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public boolean getIsIncludeSubOrg() {
        return this.isIncludeSubOrg;
    }

    public void setIsIncludeSubOrg( boolean isIncludeSubOrg) {
        this.isIncludeSubOrg = isIncludeSubOrg;
    }

    public com.kingdee.eas.basedata.org.app.WSFullOrgUnit getOrg() {
        return this.org;
    }

    public void setOrg( com.kingdee.eas.basedata.org.app.WSFullOrgUnit org) {
        this.org = org;
    }

}