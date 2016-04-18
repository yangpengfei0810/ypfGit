package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSOUPartCtrl extends WSBean { 
    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit ;

    private String id ;

    private String versionNumber ;

    public com.kingdee.eas.basedata.org.app.WSFullOrgUnit getUnit() {
        return this.unit;
    }

    public void setUnit( com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit) {
        this.unit = unit;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getVersionNumber() {
        return this.versionNumber;
    }

    public void setVersionNumber( String versionNumber) {
        this.versionNumber = versionNumber;
    }

}