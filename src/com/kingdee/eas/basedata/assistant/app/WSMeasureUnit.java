package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSMeasureUnit extends WSBean { 
    private String disabledDate ;

    private boolean isBaseUnit ;

    private String id ;

    private com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup measureUnitGroup ;

    private String simpleName ;

    private int qtyPrecision ;

    private String lastUpdateTime ;

    private String name ;

    private String number ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String description ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private boolean isDisabled ;

    private String createTime ;

    private double coefficient ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    public String getDisabledDate() {
        return this.disabledDate;
    }

    public void setDisabledDate( String disabledDate) {
        this.disabledDate = disabledDate;
    }

    public boolean getIsBaseUnit() {
        return this.isBaseUnit;
    }

    public void setIsBaseUnit( boolean isBaseUnit) {
        this.isBaseUnit = isBaseUnit;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup getMeasureUnitGroup() {
        return this.measureUnitGroup;
    }

    public void setMeasureUnitGroup( com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup measureUnitGroup) {
        this.measureUnitGroup = measureUnitGroup;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public int getQtyPrecision() {
        return this.qtyPrecision;
    }

    public void setQtyPrecision( int qtyPrecision) {
        this.qtyPrecision = qtyPrecision;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public boolean getIsDisabled() {
        return this.isDisabled;
    }

    public void setIsDisabled( boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public double getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient( double coefficient) {
        this.coefficient = coefficient;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

}