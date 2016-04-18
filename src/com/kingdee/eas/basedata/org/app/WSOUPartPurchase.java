package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSOUPartPurchase extends WSBean { 
    private String id ;

    private com.kingdee.eas.basedata.person.app.WSPerson leader ;

    private com.kingdee.eas.basedata.assistant.app.WSAddress address ;

    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit ;

    private boolean isBizUnit ;

    private boolean isSealUp ;

    private String versionNumber ;

    private String propertySealUpDate ;

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.person.app.WSPerson getLeader() {
        return this.leader;
    }

    public void setLeader( com.kingdee.eas.basedata.person.app.WSPerson leader) {
        this.leader = leader;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAddress getAddress() {
        return this.address;
    }

    public void setAddress( com.kingdee.eas.basedata.assistant.app.WSAddress address) {
        this.address = address;
    }

    public com.kingdee.eas.basedata.org.app.WSFullOrgUnit getUnit() {
        return this.unit;
    }

    public void setUnit( com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit) {
        this.unit = unit;
    }

    public boolean getIsBizUnit() {
        return this.isBizUnit;
    }

    public void setIsBizUnit( boolean isBizUnit) {
        this.isBizUnit = isBizUnit;
    }

    public boolean getIsSealUp() {
        return this.isSealUp;
    }

    public void setIsSealUp( boolean isSealUp) {
        this.isSealUp = isSealUp;
    }

    public String getVersionNumber() {
        return this.versionNumber;
    }

    public void setVersionNumber( String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getPropertySealUpDate() {
        return this.propertySealUpDate;
    }

    public void setPropertySealUpDate( String propertySealUpDate) {
        this.propertySealUpDate = propertySealUpDate;
    }

}