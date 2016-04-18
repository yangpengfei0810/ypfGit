package com.kingdee.eas.basedata.master.auxacct.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAsstActType extends WSBean { 
    private String assistantType ;

    private String simpleName ;

    private String number ;

    private boolean isfreeze ;

    private boolean isQty ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.basedata.master.auxacct.app.WSAsstActGroupDetail[] asstActGpDt ;

    private boolean isMultilevel ;

    private String asstHGAttribute ;

    private String id ;

    private boolean isSystemAsstActItem ;

    private String groupTableName ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company ;

    private com.kingdee.eas.basedata.assistant.app.WSMeasureUnit measureUnit ;

    private boolean isSelfAsstActaiatem ;

    private boolean isForCompany ;

    private String mappingFieldName ;

    private String defaultQueryName ;

    private boolean useLongNumber ;

    private String permissionItem ;

    private String groupMappingFieldName ;

    private String description ;

    private String realDataObjAlias ;

    private String createTime ;

    private String realtionDataObject ;

    private String defaultF7UI ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String name ;

    private com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup measureUnitGroup ;

    private com.kingdee.eas.basedata.master.auxacct.app.WSGeneralAsstActTypeGroup glAsstActTypeGrp ;

    public String getAssistantType() {
        return this.assistantType;
    }

    public void setAssistantType( String assistantType) {
        this.assistantType = assistantType;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public boolean getIsfreeze() {
        return this.isfreeze;
    }

    public void setIsfreeze( boolean isfreeze) {
        this.isfreeze = isfreeze;
    }

    public boolean getIsQty() {
        return this.isQty;
    }

    public void setIsQty( boolean isQty) {
        this.isQty = isQty;
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

    public com.kingdee.eas.basedata.master.auxacct.app.WSAsstActGroupDetail[] getAsstActGpDt() {
        return this.asstActGpDt;
    }

    public void setAsstActGpDt( com.kingdee.eas.basedata.master.auxacct.app.WSAsstActGroupDetail[] asstActGpDt) {
        this.asstActGpDt = asstActGpDt;
    }

    public boolean getIsMultilevel() {
        return this.isMultilevel;
    }

    public void setIsMultilevel( boolean isMultilevel) {
        this.isMultilevel = isMultilevel;
    }

    public String getAsstHGAttribute() {
        return this.asstHGAttribute;
    }

    public void setAsstHGAttribute( String asstHGAttribute) {
        this.asstHGAttribute = asstHGAttribute;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public boolean getIsSystemAsstActItem() {
        return this.isSystemAsstActItem;
    }

    public void setIsSystemAsstActItem( boolean isSystemAsstActItem) {
        this.isSystemAsstActItem = isSystemAsstActItem;
    }

    public String getGroupTableName() {
        return this.groupTableName;
    }

    public void setGroupTableName( String groupTableName) {
        this.groupTableName = groupTableName;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getCompany() {
        return this.company;
    }

    public void setCompany( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit company) {
        this.company = company;
    }

    public com.kingdee.eas.basedata.assistant.app.WSMeasureUnit getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit( com.kingdee.eas.basedata.assistant.app.WSMeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    public boolean getIsSelfAsstActaiatem() {
        return this.isSelfAsstActaiatem;
    }

    public void setIsSelfAsstActaiatem( boolean isSelfAsstActaiatem) {
        this.isSelfAsstActaiatem = isSelfAsstActaiatem;
    }

    public boolean getIsForCompany() {
        return this.isForCompany;
    }

    public void setIsForCompany( boolean isForCompany) {
        this.isForCompany = isForCompany;
    }

    public String getMappingFieldName() {
        return this.mappingFieldName;
    }

    public void setMappingFieldName( String mappingFieldName) {
        this.mappingFieldName = mappingFieldName;
    }

    public String getDefaultQueryName() {
        return this.defaultQueryName;
    }

    public void setDefaultQueryName( String defaultQueryName) {
        this.defaultQueryName = defaultQueryName;
    }

    public boolean getUseLongNumber() {
        return this.useLongNumber;
    }

    public void setUseLongNumber( boolean useLongNumber) {
        this.useLongNumber = useLongNumber;
    }

    public String getPermissionItem() {
        return this.permissionItem;
    }

    public void setPermissionItem( String permissionItem) {
        this.permissionItem = permissionItem;
    }

    public String getGroupMappingFieldName() {
        return this.groupMappingFieldName;
    }

    public void setGroupMappingFieldName( String groupMappingFieldName) {
        this.groupMappingFieldName = groupMappingFieldName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public String getRealDataObjAlias() {
        return this.realDataObjAlias;
    }

    public void setRealDataObjAlias( String realDataObjAlias) {
        this.realDataObjAlias = realDataObjAlias;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getRealtionDataObject() {
        return this.realtionDataObject;
    }

    public void setRealtionDataObject( String realtionDataObject) {
        this.realtionDataObject = realtionDataObject;
    }

    public String getDefaultF7UI() {
        return this.defaultF7UI;
    }

    public void setDefaultF7UI( String defaultF7UI) {
        this.defaultF7UI = defaultF7UI;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup getMeasureUnitGroup() {
        return this.measureUnitGroup;
    }

    public void setMeasureUnitGroup( com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup measureUnitGroup) {
        this.measureUnitGroup = measureUnitGroup;
    }

    public com.kingdee.eas.basedata.master.auxacct.app.WSGeneralAsstActTypeGroup getGlAsstActTypeGrp() {
        return this.glAsstActTypeGrp;
    }

    public void setGlAsstActTypeGrp( com.kingdee.eas.basedata.master.auxacct.app.WSGeneralAsstActTypeGroup glAsstActTypeGrp) {
        this.glAsstActTypeGrp = glAsstActTypeGrp;
    }

}