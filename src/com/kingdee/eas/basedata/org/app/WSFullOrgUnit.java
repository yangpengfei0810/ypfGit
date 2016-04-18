package com.kingdee.eas.basedata.org.app;

import com.kingdee.bos.webservice.WSBean;

public class WSFullOrgUnit extends WSBean { 
    private com.kingdee.eas.basedata.org.app.WSOUPartQuality partQuality ;

    private boolean isUnion ;

    private boolean isCompanyOrgUnit ;

    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit mainOrg ;

    private com.kingdee.eas.basedata.org.app.WSOUPartProfitCenter partProfitCenter ;

    private String invalidDate ;

    private String longNumber ;

    private String id ;

    private com.kingdee.eas.basedata.org.app.WSOUPartCostCenter partCostCenter ;

    private com.kingdee.eas.basedata.org.app.WSOUPartCtrl partCtrl ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private com.kingdee.eas.basedata.org.app.WSOUPartHR partHR ;

    private String createTime ;

    private boolean isCU ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private boolean isGrouping ;

    private boolean isLeaf ;

    private boolean isAdminOrgUnit ;

    private boolean isAssistantOrg ;

    private boolean isCostOrgUnit ;

    private String versionNumber ;

    private boolean isSaleOrgUnit ;

    private com.kingdee.eas.basedata.org.app.WSOUPartStorage partStorage ;

    private String effectDate ;

    private com.kingdee.eas.basedata.org.app.WSOUPartUnionGroup partUnionGroup ;

    private com.kingdee.eas.basedata.org.app.WSOUPartAdmin partAdmin ;

    private boolean isStorageOrgUnit ;

    private boolean isTransportOrgUnit ;

    private String simpleName ;

    private boolean isFreeze ;

    private String description ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit maintainCU ;

    private String displayName ;

    private String name ;

    private com.kingdee.eas.basedata.org.app.WSFullOrgUnit parent ;

    private boolean isStart ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String code ;

    private boolean isQualityOrgUnit ;

    private boolean isOUSealUp ;

    private boolean isPurchaseOrgUnit ;

    private com.kingdee.eas.basedata.org.app.WSOUPartSale partSale ;

    private com.kingdee.eas.basedata.org.app.WSOUPartPurchase partPurchase ;

    private int level ;

    private String orgTypeStr ;

    private String englishName ;

    private boolean isProfitOrgUnit ;

    private com.kingdee.eas.basedata.org.app.WSOUPartFI partFI ;

    private boolean isHROrgUnit ;

    private com.kingdee.eas.basedata.org.app.WSOUPartTransport partTransport ;

    private String number ;

    public com.kingdee.eas.basedata.org.app.WSOUPartQuality getPartQuality() {
        return this.partQuality;
    }

    public void setPartQuality( com.kingdee.eas.basedata.org.app.WSOUPartQuality partQuality) {
        this.partQuality = partQuality;
    }

    public boolean getIsUnion() {
        return this.isUnion;
    }

    public void setIsUnion( boolean isUnion) {
        this.isUnion = isUnion;
    }

    public boolean getIsCompanyOrgUnit() {
        return this.isCompanyOrgUnit;
    }

    public void setIsCompanyOrgUnit( boolean isCompanyOrgUnit) {
        this.isCompanyOrgUnit = isCompanyOrgUnit;
    }

    public com.kingdee.eas.basedata.org.app.WSFullOrgUnit getMainOrg() {
        return this.mainOrg;
    }

    public void setMainOrg( com.kingdee.eas.basedata.org.app.WSFullOrgUnit mainOrg) {
        this.mainOrg = mainOrg;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartProfitCenter getPartProfitCenter() {
        return this.partProfitCenter;
    }

    public void setPartProfitCenter( com.kingdee.eas.basedata.org.app.WSOUPartProfitCenter partProfitCenter) {
        this.partProfitCenter = partProfitCenter;
    }

    public String getInvalidDate() {
        return this.invalidDate;
    }

    public void setInvalidDate( String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartCostCenter getPartCostCenter() {
        return this.partCostCenter;
    }

    public void setPartCostCenter( com.kingdee.eas.basedata.org.app.WSOUPartCostCenter partCostCenter) {
        this.partCostCenter = partCostCenter;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartCtrl getPartCtrl() {
        return this.partCtrl;
    }

    public void setPartCtrl( com.kingdee.eas.basedata.org.app.WSOUPartCtrl partCtrl) {
        this.partCtrl = partCtrl;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartHR getPartHR() {
        return this.partHR;
    }

    public void setPartHR( com.kingdee.eas.basedata.org.app.WSOUPartHR partHR) {
        this.partHR = partHR;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public boolean getIsCU() {
        return this.isCU;
    }

    public void setIsCU( boolean isCU) {
        this.isCU = isCU;
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

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public boolean getIsAdminOrgUnit() {
        return this.isAdminOrgUnit;
    }

    public void setIsAdminOrgUnit( boolean isAdminOrgUnit) {
        this.isAdminOrgUnit = isAdminOrgUnit;
    }

    public boolean getIsAssistantOrg() {
        return this.isAssistantOrg;
    }

    public void setIsAssistantOrg( boolean isAssistantOrg) {
        this.isAssistantOrg = isAssistantOrg;
    }

    public boolean getIsCostOrgUnit() {
        return this.isCostOrgUnit;
    }

    public void setIsCostOrgUnit( boolean isCostOrgUnit) {
        this.isCostOrgUnit = isCostOrgUnit;
    }

    public String getVersionNumber() {
        return this.versionNumber;
    }

    public void setVersionNumber( String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public boolean getIsSaleOrgUnit() {
        return this.isSaleOrgUnit;
    }

    public void setIsSaleOrgUnit( boolean isSaleOrgUnit) {
        this.isSaleOrgUnit = isSaleOrgUnit;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartStorage getPartStorage() {
        return this.partStorage;
    }

    public void setPartStorage( com.kingdee.eas.basedata.org.app.WSOUPartStorage partStorage) {
        this.partStorage = partStorage;
    }

    public String getEffectDate() {
        return this.effectDate;
    }

    public void setEffectDate( String effectDate) {
        this.effectDate = effectDate;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartUnionGroup getPartUnionGroup() {
        return this.partUnionGroup;
    }

    public void setPartUnionGroup( com.kingdee.eas.basedata.org.app.WSOUPartUnionGroup partUnionGroup) {
        this.partUnionGroup = partUnionGroup;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartAdmin getPartAdmin() {
        return this.partAdmin;
    }

    public void setPartAdmin( com.kingdee.eas.basedata.org.app.WSOUPartAdmin partAdmin) {
        this.partAdmin = partAdmin;
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

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public boolean getIsFreeze() {
        return this.isFreeze;
    }

    public void setIsFreeze( boolean isFreeze) {
        this.isFreeze = isFreeze;
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

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getMaintainCU() {
        return this.maintainCU;
    }

    public void setMaintainCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit maintainCU) {
        this.maintainCU = maintainCU;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public com.kingdee.eas.basedata.org.app.WSFullOrgUnit getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.org.app.WSFullOrgUnit parent) {
        this.parent = parent;
    }

    public boolean getIsStart() {
        return this.isStart;
    }

    public void setIsStart( boolean isStart) {
        this.isStart = isStart;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode( String code) {
        this.code = code;
    }

    public boolean getIsQualityOrgUnit() {
        return this.isQualityOrgUnit;
    }

    public void setIsQualityOrgUnit( boolean isQualityOrgUnit) {
        this.isQualityOrgUnit = isQualityOrgUnit;
    }

    public boolean getIsOUSealUp() {
        return this.isOUSealUp;
    }

    public void setIsOUSealUp( boolean isOUSealUp) {
        this.isOUSealUp = isOUSealUp;
    }

    public boolean getIsPurchaseOrgUnit() {
        return this.isPurchaseOrgUnit;
    }

    public void setIsPurchaseOrgUnit( boolean isPurchaseOrgUnit) {
        this.isPurchaseOrgUnit = isPurchaseOrgUnit;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartSale getPartSale() {
        return this.partSale;
    }

    public void setPartSale( com.kingdee.eas.basedata.org.app.WSOUPartSale partSale) {
        this.partSale = partSale;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartPurchase getPartPurchase() {
        return this.partPurchase;
    }

    public void setPartPurchase( com.kingdee.eas.basedata.org.app.WSOUPartPurchase partPurchase) {
        this.partPurchase = partPurchase;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public String getOrgTypeStr() {
        return this.orgTypeStr;
    }

    public void setOrgTypeStr( String orgTypeStr) {
        this.orgTypeStr = orgTypeStr;
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public void setEnglishName( String englishName) {
        this.englishName = englishName;
    }

    public boolean getIsProfitOrgUnit() {
        return this.isProfitOrgUnit;
    }

    public void setIsProfitOrgUnit( boolean isProfitOrgUnit) {
        this.isProfitOrgUnit = isProfitOrgUnit;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartFI getPartFI() {
        return this.partFI;
    }

    public void setPartFI( com.kingdee.eas.basedata.org.app.WSOUPartFI partFI) {
        this.partFI = partFI;
    }

    public boolean getIsHROrgUnit() {
        return this.isHROrgUnit;
    }

    public void setIsHROrgUnit( boolean isHROrgUnit) {
        this.isHROrgUnit = isHROrgUnit;
    }

    public com.kingdee.eas.basedata.org.app.WSOUPartTransport getPartTransport() {
        return this.partTransport;
    }

    public void setPartTransport( com.kingdee.eas.basedata.org.app.WSOUPartTransport partTransport) {
        this.partTransport = partTransport;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

}