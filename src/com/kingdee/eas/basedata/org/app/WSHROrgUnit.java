package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSHROrgUnit extends WSBean { 
    private boolean isCompanyOrgUnit ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String displayName ;

    private String invalidDate ;

    private boolean isPurchaseOrgUnit ;

    private com.kingdee.eas.basedata.org.app.WSHROrgUnit parent ;

    private boolean isBizUnit ;

    private boolean isSaleOrgUnit ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private boolean isProfitOrgUnit ;

    private boolean isCostOrgUnit ;

    private String longNumber ;

    private boolean isOUSealUp ;

    private String simpleName ;

    private boolean isGrouping ;

    private String orgTypeStr ;

    private boolean isSealUp ;

    private String code ;

    private String number ;

    private String lastUpdateTime ;

    private boolean isStart ;

    private String effectDate ;

    private int level ;

    private boolean isStorageOrgUnit ;

    private boolean isTransportOrgUnit ;

    private boolean isAdminOrgUnit ;

    private String description ;

    private boolean isFreeze ;

    private boolean isLeaf ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String propertySealUpDate ;

    private boolean isUnion ;

    private boolean isQualityOrgUnit ;

    private String createTime ;

    private String name ;

    private boolean isHROrgUnit ;

    private String id ;

    private String versionNumber ;

    private boolean isCU ;

    public boolean getIsCompanyOrgUnit() {
        return this.isCompanyOrgUnit;
    }

    public void setIsCompanyOrgUnit( boolean isCompanyOrgUnit) {
        this.isCompanyOrgUnit = isCompanyOrgUnit;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
    }

    public String getInvalidDate() {
        return this.invalidDate;
    }

    public void setInvalidDate( String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public boolean getIsPurchaseOrgUnit() {
        return this.isPurchaseOrgUnit;
    }

    public void setIsPurchaseOrgUnit( boolean isPurchaseOrgUnit) {
        this.isPurchaseOrgUnit = isPurchaseOrgUnit;
    }

    public com.kingdee.eas.basedata.org.app.WSHROrgUnit getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.org.app.WSHROrgUnit parent) {
        this.parent = parent;
    }

    public boolean getIsBizUnit() {
        return this.isBizUnit;
    }

    public void setIsBizUnit( boolean isBizUnit) {
        this.isBizUnit = isBizUnit;
    }

    public boolean getIsSaleOrgUnit() {
        return this.isSaleOrgUnit;
    }

    public void setIsSaleOrgUnit( boolean isSaleOrgUnit) {
        this.isSaleOrgUnit = isSaleOrgUnit;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public boolean getIsProfitOrgUnit() {
        return this.isProfitOrgUnit;
    }

    public void setIsProfitOrgUnit( boolean isProfitOrgUnit) {
        this.isProfitOrgUnit = isProfitOrgUnit;
    }

    public boolean getIsCostOrgUnit() {
        return this.isCostOrgUnit;
    }

    public void setIsCostOrgUnit( boolean isCostOrgUnit) {
        this.isCostOrgUnit = isCostOrgUnit;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public boolean getIsOUSealUp() {
        return this.isOUSealUp;
    }

    public void setIsOUSealUp( boolean isOUSealUp) {
        this.isOUSealUp = isOUSealUp;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public boolean getIsGrouping() {
        return this.isGrouping;
    }

    public void setIsGrouping( boolean isGrouping) {
        this.isGrouping = isGrouping;
    }

    public String getOrgTypeStr() {
        return this.orgTypeStr;
    }

    public void setOrgTypeStr( String orgTypeStr) {
        this.orgTypeStr = orgTypeStr;
    }

    public boolean getIsSealUp() {
        return this.isSealUp;
    }

    public void setIsSealUp( boolean isSealUp) {
        this.isSealUp = isSealUp;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode( String code) {
        this.code = code;
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

    public boolean getIsStart() {
        return this.isStart;
    }

    public void setIsStart( boolean isStart) {
        this.isStart = isStart;
    }

    public String getEffectDate() {
        return this.effectDate;
    }

    public void setEffectDate( String effectDate) {
        this.effectDate = effectDate;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public boolean getIsStorageOrgUnit() {
        return this.isStorageOrgUnit;
    }

    public void setIsStorageOrgUnit( boolean isStorageOrgUnit) {
        this.isStorageOrgUnit = isStorageOrgUnit;
    }

    public boolean getIsTransportOrgUnit() {
        return this.isTransportOrgUnit;
    }

    public void setIsTransportOrgUnit( boolean isTransportOrgUnit) {
        this.isTransportOrgUnit = isTransportOrgUnit;
    }

    public boolean getIsAdminOrgUnit() {
        return this.isAdminOrgUnit;
    }

    public void setIsAdminOrgUnit( boolean isAdminOrgUnit) {
        this.isAdminOrgUnit = isAdminOrgUnit;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public boolean getIsFreeze() {
        return this.isFreeze;
    }

    public void setIsFreeze( boolean isFreeze) {
        this.isFreeze = isFreeze;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getPropertySealUpDate() {
        return this.propertySealUpDate;
    }

    public void setPropertySealUpDate( String propertySealUpDate) {
        this.propertySealUpDate = propertySealUpDate;
    }

    public boolean getIsUnion() {
        return this.isUnion;
    }

    public void setIsUnion( boolean isUnion) {
        this.isUnion = isUnion;
    }

    public boolean getIsQualityOrgUnit() {
        return this.isQualityOrgUnit;
    }

    public void setIsQualityOrgUnit( boolean isQualityOrgUnit) {
        this.isQualityOrgUnit = isQualityOrgUnit;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public boolean getIsHROrgUnit() {
        return this.isHROrgUnit;
    }

    public void setIsHROrgUnit( boolean isHROrgUnit) {
        this.isHROrgUnit = isHROrgUnit;
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

    public boolean getIsCU() {
        return this.isCU;
    }

    public void setIsCU( boolean isCU) {
        this.isCU = isCU;
    }

}