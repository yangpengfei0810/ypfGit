package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAccountMgrStrategy extends WSBean { 
    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private boolean ctrlSettleSave ;

    private String simpleName ;

    private int transUpDate ;

    private String transUpPeriod ;

    private String description ;

    private double transUpNormAmt ;

    private String transUpMode ;

    private String name ;

    private int maxActiveTimes ;

    private double transDownFixedAmt ;

    private double transDownNormAmt ;

    private double lowestBalance ;

    private boolean remind ;

    private double overDraftAmount ;

    private double transUpMinAmt ;

    private String transDownMode ;

    private String overdraftCtrl ;

    private double transUpBaseAmt ;

    private boolean ctrlSettlePast ;

    private String id ;

    private String createTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String lastUpdateTime ;

    private double lowestDrawAmt ;

    private String number ;

    private double startAmount ;

    private boolean isFromBC ;

    private boolean ctrlRedBill ;

    private boolean ctrlSettleSubm ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private double transDownMinAmt ;

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public boolean getCtrlSettleSave() {
        return this.ctrlSettleSave;
    }

    public void setCtrlSettleSave( boolean ctrlSettleSave) {
        this.ctrlSettleSave = ctrlSettleSave;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public int getTransUpDate() {
        return this.transUpDate;
    }

    public void setTransUpDate( int transUpDate) {
        this.transUpDate = transUpDate;
    }

    public String getTransUpPeriod() {
        return this.transUpPeriod;
    }

    public void setTransUpPeriod( String transUpPeriod) {
        this.transUpPeriod = transUpPeriod;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public double getTransUpNormAmt() {
        return this.transUpNormAmt;
    }

    public void setTransUpNormAmt( double transUpNormAmt) {
        this.transUpNormAmt = transUpNormAmt;
    }

    public String getTransUpMode() {
        return this.transUpMode;
    }

    public void setTransUpMode( String transUpMode) {
        this.transUpMode = transUpMode;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public int getMaxActiveTimes() {
        return this.maxActiveTimes;
    }

    public void setMaxActiveTimes( int maxActiveTimes) {
        this.maxActiveTimes = maxActiveTimes;
    }

    public double getTransDownFixedAmt() {
        return this.transDownFixedAmt;
    }

    public void setTransDownFixedAmt( double transDownFixedAmt) {
        this.transDownFixedAmt = transDownFixedAmt;
    }

    public double getTransDownNormAmt() {
        return this.transDownNormAmt;
    }

    public void setTransDownNormAmt( double transDownNormAmt) {
        this.transDownNormAmt = transDownNormAmt;
    }

    public double getLowestBalance() {
        return this.lowestBalance;
    }

    public void setLowestBalance( double lowestBalance) {
        this.lowestBalance = lowestBalance;
    }

    public boolean getRemind() {
        return this.remind;
    }

    public void setRemind( boolean remind) {
        this.remind = remind;
    }

    public double getOverDraftAmount() {
        return this.overDraftAmount;
    }

    public void setOverDraftAmount( double overDraftAmount) {
        this.overDraftAmount = overDraftAmount;
    }

    public double getTransUpMinAmt() {
        return this.transUpMinAmt;
    }

    public void setTransUpMinAmt( double transUpMinAmt) {
        this.transUpMinAmt = transUpMinAmt;
    }

    public String getTransDownMode() {
        return this.transDownMode;
    }

    public void setTransDownMode( String transDownMode) {
        this.transDownMode = transDownMode;
    }

    public String getOverdraftCtrl() {
        return this.overdraftCtrl;
    }

    public void setOverdraftCtrl( String overdraftCtrl) {
        this.overdraftCtrl = overdraftCtrl;
    }

    public double getTransUpBaseAmt() {
        return this.transUpBaseAmt;
    }

    public void setTransUpBaseAmt( double transUpBaseAmt) {
        this.transUpBaseAmt = transUpBaseAmt;
    }

    public boolean getCtrlSettlePast() {
        return this.ctrlSettlePast;
    }

    public void setCtrlSettlePast( boolean ctrlSettlePast) {
        this.ctrlSettlePast = ctrlSettlePast;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public double getLowestDrawAmt() {
        return this.lowestDrawAmt;
    }

    public void setLowestDrawAmt( double lowestDrawAmt) {
        this.lowestDrawAmt = lowestDrawAmt;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public double getStartAmount() {
        return this.startAmount;
    }

    public void setStartAmount( double startAmount) {
        this.startAmount = startAmount;
    }

    public boolean getIsFromBC() {
        return this.isFromBC;
    }

    public void setIsFromBC( boolean isFromBC) {
        this.isFromBC = isFromBC;
    }

    public boolean getCtrlRedBill() {
        return this.ctrlRedBill;
    }

    public void setCtrlRedBill( boolean ctrlRedBill) {
        this.ctrlRedBill = ctrlRedBill;
    }

    public boolean getCtrlSettleSubm() {
        return this.ctrlSettleSubm;
    }

    public void setCtrlSettleSubm( boolean ctrlSettleSubm) {
        this.ctrlSettleSubm = ctrlSettleSubm;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public double getTransDownMinAmt() {
        return this.transDownMinAmt;
    }

    public void setTransDownMinAmt( double transDownMinAmt) {
        this.transDownMinAmt = transDownMinAmt;
    }

}