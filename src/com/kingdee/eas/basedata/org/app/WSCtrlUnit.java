package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSCtrlUnit extends WSBean { 
    private boolean isHROrgUnit ;

    private boolean isStorageOrgUnit ;

    private String effectDate ;

    private boolean isCU ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private boolean isLeaf ;

    private boolean isOUSealUp ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit parent ;

    private boolean isTransportOrgUnit ;

    private boolean isStart ;

    private String number ;

    private boolean isSaleOrgUnit ;

    private boolean isAdminOrgUnit ;

    private String id ;

    private String code ;

    private String createTime ;

    private boolean isProfitOrgUnit ;

    private String orgTypeStr ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private boolean isGrouping ;

    private boolean isFreeze ;

    private boolean isUnion ;

    private boolean isPurchaseOrgUnit ;

    private String displayName ;

    private boolean isQualityOrgUnit ;

    private String longNumber ;

    private String simpleName ;

    private String versionNumber ;

    private String description ;

    private String lastUpdateTime ;

    private String invalidDate ;

    private String name ;

    private int level ;

    private boolean isCompanyOrgUnit ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private boolean isCostOrgUnit ;

    public boolean getIsHROrgUnit() {
        return this.isHROrgUnit;
    }

    public void setIsHROrgUnit( boolean isHROrgUnit) {
        this.isHROrgUnit = isHROrgUnit;
    }

    public boolean getIsStorageOrgUnit() {
        return this.isStorageOrgUnit;
    }

    public void setIsStorageOrgUnit( boolean isStorageOrgUnit) {
        this.isStorageOrgUnit = isStorageOrgUnit;
    }

    public String getEffectDate() {
        return this.effectDate;
    }

    public void setEffectDate( String effectDate) {
        this.effectDate = effectDate;
    }

    public boolean getIsCU() {
        return this.isCU;
    }

    public void setIsCU( boolean isCU) {
        this.isCU = isCU;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public boolean getIsOUSealUp() {
        return this.isOUSealUp;
    }

    public void setIsOUSealUp( boolean isOUSealUp) {
        this.isOUSealUp = isOUSealUp;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.org.app.WSCtrlUnit parent) {
        this.parent = parent;
    }

    public boolean getIsTransportOrgUnit() {
        return this.isTransportOrgUnit;
    }

    public void setIsTransportOrgUnit( boolean isTransportOrgUnit) {
        this.isTransportOrgUnit = isTransportOrgUnit;
    }

    public boolean getIsStart() {
        return this.isStart;
    }

    public void setIsStart( boolean isStart) {
        this.isStart = isStart;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public boolean getIsSaleOrgUnit() {
        return this.isSaleOrgUnit;
    }

    public void setIsSaleOrgUnit( boolean isSaleOrgUnit) {
        this.isSaleOrgUnit = isSaleOrgUnit;
    }

    public boolean getIsAdminOrgUnit() {
        return this.isAdminOrgUnit;
    }

    public void setIsAdminOrgUnit( boolean isAdminOrgUnit) {
        this.isAdminOrgUnit = isAdminOrgUnit;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode( String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public boolean getIsProfitOrgUnit() {
        return this.isProfitOrgUnit;
    }

    public void setIsProfitOrgUnit( boolean isProfitOrgUnit) {
        this.isProfitOrgUnit = isProfitOrgUnit;
    }

    public String getOrgTypeStr() {
        return this.orgTypeStr;
    }

    public void setOrgTypeStr( String orgTypeStr) {
        this.orgTypeStr = orgTypeStr;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public boolean getIsGrouping() {
        return this.isGrouping;
    }

    public void setIsGrouping( boolean isGrouping) {
        this.isGrouping = isGrouping;
    }

    public boolean getIsFreeze() {
        return this.isFreeze;
    }

    public void setIsFreeze( boolean isFreeze) {
        this.isFreeze = isFreeze;
    }

    public boolean getIsUnion() {
        return this.isUnion;
    }

    public void setIsUnion( boolean isUnion) {
        this.isUnion = isUnion;
    }

    public boolean getIsPurchaseOrgUnit() {
        return this.isPurchaseOrgUnit;
    }

    public void setIsPurchaseOrgUnit( boolean isPurchaseOrgUnit) {
        this.isPurchaseOrgUnit = isPurchaseOrgUnit;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
    }

    public boolean getIsQualityOrgUnit() {
        return this.isQualityOrgUnit;
    }

    public void setIsQualityOrgUnit( boolean isQualityOrgUnit) {
        this.isQualityOrgUnit = isQualityOrgUnit;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public String getVersionNumber() {
        return this.versionNumber;
    }

    public void setVersionNumber( String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getInvalidDate() {
        return this.invalidDate;
    }

    public void setInvalidDate( String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public boolean getIsCompanyOrgUnit() {
        return this.isCompanyOrgUnit;
    }

    public void setIsCompanyOrgUnit( boolean isCompanyOrgUnit) {
        this.isCompanyOrgUnit = isCompanyOrgUnit;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public boolean getIsCostOrgUnit() {
        return this.isCostOrgUnit;
    }

    public void setIsCostOrgUnit( boolean isCostOrgUnit) {
        this.isCostOrgUnit = isCostOrgUnit;
    }

}