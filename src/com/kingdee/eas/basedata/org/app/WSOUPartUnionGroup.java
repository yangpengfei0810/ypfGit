package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSOUPartUnionGroup extends WSBean { 
    private String id ;

    private String versionNumber ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit stockControlCompany ;

    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit ;

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

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getStockControlCompany() {
        return this.stockControlCompany;
    }

    public void setStockControlCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit stockControlCompany) {
        this.stockControlCompany = stockControlCompany;
    }

    public com.kingdee.eas.basedata.org.app.WSFullOrgUnit getUnit() {
        return this.unit;
    }

    public void setUnit( com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit) {
        this.unit = unit;
    }

}