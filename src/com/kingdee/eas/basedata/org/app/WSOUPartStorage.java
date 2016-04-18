package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSOUPartStorage extends WSBean { 
    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit unit ;

    private String id ;

    private com.kingdee.eas.basedata.person.app.WSPerson leader ;

    private String type ;

    private com.kingdee.eas.basedata.assistant.app.WSAddress address ;

    private String status ;

    private String location ;

    private String versionNumber ;

    private boolean isSealUp ;

    private String propertySealUpDate ;

    private boolean isBizUnit ;

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

    public com.kingdee.eas.basedata.person.app.WSPerson getLeader() {
        return this.leader;
    }

    public void setLeader( com.kingdee.eas.basedata.person.app.WSPerson leader) {
        this.leader = leader;
    }

    public String getType() {
        return this.type;
    }

    public void setType( String type) {
        this.type = type;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAddress getAddress() {
        return this.address;
    }

    public void setAddress( com.kingdee.eas.basedata.assistant.app.WSAddress address) {
        this.address = address;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus( String status) {
        this.status = status;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation( String location) {
        this.location = location;
    }

    public String getVersionNumber() {
        return this.versionNumber;
    }

    public void setVersionNumber( String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public boolean getIsSealUp() {
        return this.isSealUp;
    }

    public void setIsSealUp( boolean isSealUp) {
        this.isSealUp = isSealUp;
    }

    public String getPropertySealUpDate() {
        return this.propertySealUpDate;
    }

    public void setPropertySealUpDate( String propertySealUpDate) {
        this.propertySealUpDate = propertySealUpDate;
    }

    public boolean getIsBizUnit() {
        return this.isBizUnit;
    }

    public void setIsBizUnit( boolean isBizUnit) {
        this.isBizUnit = isBizUnit;
    }

}