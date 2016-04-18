package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSBank extends WSBean { 
    private com.kingdee.eas.basedata.assistant.app.WSBank parentInGroup ;

    private String openDate ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit relatedCompany ;

    private boolean used ;

    private String longNumberInGroup ;

    private String settleDate ;

    private String bankAreaType ;

    private String simpleName ;

    private String deletedStatus ;

    private String address ;

    private String createTime ;

    private String description ;

    private String phone ;

    private String name ;

    private String fax ;

    private String longNumber ;

    private String displayName ;

    private String linkman ;

    private boolean inGroup ;

    private String number ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private boolean isBank ;

    private String id ;

    private boolean isLeaf ;

    private int level ;

    private com.kingdee.eas.basedata.assistant.app.WSBank parent ;

    public com.kingdee.eas.basedata.assistant.app.WSBank getParentInGroup() {
        return this.parentInGroup;
    }

    public void setParentInGroup( com.kingdee.eas.basedata.assistant.app.WSBank parentInGroup) {
        this.parentInGroup = parentInGroup;
    }

    public String getOpenDate() {
        return this.openDate;
    }

    public void setOpenDate( String openDate) {
        this.openDate = openDate;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getRelatedCompany() {
        return this.relatedCompany;
    }

    public void setRelatedCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit relatedCompany) {
        this.relatedCompany = relatedCompany;
    }

    public boolean getUsed() {
        return this.used;
    }

    public void setUsed( boolean used) {
        this.used = used;
    }

    public String getLongNumberInGroup() {
        return this.longNumberInGroup;
    }

    public void setLongNumberInGroup( String longNumberInGroup) {
        this.longNumberInGroup = longNumberInGroup;
    }

    public String getSettleDate() {
        return this.settleDate;
    }

    public void setSettleDate( String settleDate) {
        this.settleDate = settleDate;
    }

    public String getBankAreaType() {
        return this.bankAreaType;
    }

    public void setBankAreaType( String bankAreaType) {
        this.bankAreaType = bankAreaType;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public String getDeletedStatus() {
        return this.deletedStatus;
    }

    public void setDeletedStatus( String deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress( String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone( String phone) {
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax( String fax) {
        this.fax = fax;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
    }

    public String getLinkman() {
        return this.linkman;
    }

    public void setLinkman( String linkman) {
        this.linkman = linkman;
    }

    public boolean getInGroup() {
        return this.inGroup;
    }

    public void setInGroup( boolean inGroup) {
        this.inGroup = inGroup;
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

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public boolean getIsBank() {
        return this.isBank;
    }

    public void setIsBank( boolean isBank) {
        this.isBank = isBank;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public com.kingdee.eas.basedata.assistant.app.WSBank getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.assistant.app.WSBank parent) {
        this.parent = parent;
    }

}