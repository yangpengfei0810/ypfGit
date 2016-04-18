package com.kingdee.eas.basedata.master.account.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAccountView extends WSBean { 
    private boolean isCFreeze ;

    private boolean AC ;

    private String displayName ;

    private com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup measureUnitGroupID ;

    private com.kingdee.eas.basedata.assistant.app.WSCurrency currencyID ;

    private com.kingdee.eas.basedata.assistant.app.WSCashFlowItem borrowerAttCashFlowItem ;

    private com.kingdee.eas.basedata.assistant.app.WSCashFlowItem lenderMainCashFlowItem ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private boolean isCountAccrual ;

    private com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit companyID ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountView upper ;

    private String PLType ;

    private String helpCode ;

    private String number ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountView parent ;

    private boolean isGFreeze ;

    private String lastUpdateTime ;

    private int level ;

    private String control ;

    private int gLevel ;

    private com.kingdee.eas.basedata.assistant.app.WSCashFlowItem lenderAttCashFlowItem ;

    private String simpleName ;

    private boolean isUpperAllowCA ;

    private com.kingdee.eas.basedata.assistant.app.WSCashFlowItem mainCashFlowItem ;

    private String id ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTableID ;

    private com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount GAA ;

    private boolean isParentFreeze ;

    private boolean isAllowCA ;

    private String longName ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountCurrency[] accountCurrency ;

    private String longNumber ;

    private boolean isLeaf ;

    private boolean isBank ;

    private String refId ;

    private com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount CAA ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountType accountTypeID ;

    private boolean isCash ;

    private boolean ACNotice ;

    private String name ;

    private boolean hasUserProperty ;

    private boolean isCashEquivalent ;

    private float accrualPer ;

    private boolean isChangeCurrency ;

    private String description ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private boolean bw ;

    private String createTime ;

    private boolean isSelfFreeze ;

    private com.kingdee.eas.basedata.master.account.app.WSAccountView account ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private boolean isQty ;

    private int controlLevel ;

    private String DC ;

    private com.kingdee.eas.basedata.assistant.app.WSMeasureUnit measureUnitID ;

    private String accountingcurrency ;

    private boolean isOutDailyAccount ;

    private com.kingdee.eas.basedata.assistant.app.WSCashFlowItem borrowerMainCashFlowItem ;

    private com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount parentAA ;

    private com.kingdee.eas.basedata.assistant.app.WSCashFlowItem attCashFlowItem ;

    public boolean getIsCFreeze() {
        return this.isCFreeze;
    }

    public void setIsCFreeze( boolean isCFreeze) {
        this.isCFreeze = isCFreeze;
    }

    public boolean getAC() {
        return this.AC;
    }

    public void setAC( boolean AC) {
        this.AC = AC;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
    }

    public com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup getMeasureUnitGroupID() {
        return this.measureUnitGroupID;
    }

    public void setMeasureUnitGroupID( com.kingdee.eas.basedata.assistant.app.WSMeasureUnitGroup measureUnitGroupID) {
        this.measureUnitGroupID = measureUnitGroupID;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCurrency getCurrencyID() {
        return this.currencyID;
    }

    public void setCurrencyID( com.kingdee.eas.basedata.assistant.app.WSCurrency currencyID) {
        this.currencyID = currencyID;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCashFlowItem getBorrowerAttCashFlowItem() {
        return this.borrowerAttCashFlowItem;
    }

    public void setBorrowerAttCashFlowItem( com.kingdee.eas.basedata.assistant.app.WSCashFlowItem borrowerAttCashFlowItem) {
        this.borrowerAttCashFlowItem = borrowerAttCashFlowItem;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCashFlowItem getLenderMainCashFlowItem() {
        return this.lenderMainCashFlowItem;
    }

    public void setLenderMainCashFlowItem( com.kingdee.eas.basedata.assistant.app.WSCashFlowItem lenderMainCashFlowItem) {
        this.lenderMainCashFlowItem = lenderMainCashFlowItem;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public boolean getIsCountAccrual() {
        return this.isCountAccrual;
    }

    public void setIsCountAccrual( boolean isCountAccrual) {
        this.isCountAccrual = isCountAccrual;
    }

    public com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit getCompanyID() {
        return this.companyID;
    }

    public void setCompanyID( com.kingdee.eas.basedata.org.app.WSCompanyOrgUnit companyID) {
        this.companyID = companyID;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountView getUpper() {
        return this.upper;
    }

    public void setUpper( com.kingdee.eas.basedata.master.account.app.WSAccountView upper) {
        this.upper = upper;
    }

    public String getPLType() {
        return this.PLType;
    }

    public void setPLType( String PLType) {
        this.PLType = PLType;
    }

    public String getHelpCode() {
        return this.helpCode;
    }

    public void setHelpCode( String helpCode) {
        this.helpCode = helpCode;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountView getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.master.account.app.WSAccountView parent) {
        this.parent = parent;
    }

    public boolean getIsGFreeze() {
        return this.isGFreeze;
    }

    public void setIsGFreeze( boolean isGFreeze) {
        this.isGFreeze = isGFreeze;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public String getControl() {
        return this.control;
    }

    public void setControl( String control) {
        this.control = control;
    }

    public int getGLevel() {
        return this.gLevel;
    }

    public void setGLevel( int gLevel) {
        this.gLevel = gLevel;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCashFlowItem getLenderAttCashFlowItem() {
        return this.lenderAttCashFlowItem;
    }

    public void setLenderAttCashFlowItem( com.kingdee.eas.basedata.assistant.app.WSCashFlowItem lenderAttCashFlowItem) {
        this.lenderAttCashFlowItem = lenderAttCashFlowItem;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public boolean getIsUpperAllowCA() {
        return this.isUpperAllowCA;
    }

    public void setIsUpperAllowCA( boolean isUpperAllowCA) {
        this.isUpperAllowCA = isUpperAllowCA;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCashFlowItem getMainCashFlowItem() {
        return this.mainCashFlowItem;
    }

    public void setMainCashFlowItem( com.kingdee.eas.basedata.assistant.app.WSCashFlowItem mainCashFlowItem) {
        this.mainCashFlowItem = mainCashFlowItem;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountTable getAccountTableID() {
        return this.accountTableID;
    }

    public void setAccountTableID( com.kingdee.eas.basedata.master.account.app.WSAccountTable accountTableID) {
        this.accountTableID = accountTableID;
    }

    public com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount getGAA() {
        return this.GAA;
    }

    public void setGAA( com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount GAA) {
        this.GAA = GAA;
    }

    public boolean getIsParentFreeze() {
        return this.isParentFreeze;
    }

    public void setIsParentFreeze( boolean isParentFreeze) {
        this.isParentFreeze = isParentFreeze;
    }

    public boolean getIsAllowCA() {
        return this.isAllowCA;
    }

    public void setIsAllowCA( boolean isAllowCA) {
        this.isAllowCA = isAllowCA;
    }

    public String getLongName() {
        return this.longName;
    }

    public void setLongName( String longName) {
        this.longName = longName;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountCurrency[] getAccountCurrency() {
        return this.accountCurrency;
    }

    public void setAccountCurrency( com.kingdee.eas.basedata.master.account.app.WSAccountCurrency[] accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public boolean getIsBank() {
        return this.isBank;
    }

    public void setIsBank( boolean isBank) {
        this.isBank = isBank;
    }

    public String getRefId() {
        return this.refId;
    }

    public void setRefId( String refId) {
        this.refId = refId;
    }

    public com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount getCAA() {
        return this.CAA;
    }

    public void setCAA( com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount CAA) {
        this.CAA = CAA;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountType getAccountTypeID() {
        return this.accountTypeID;
    }

    public void setAccountTypeID( com.kingdee.eas.basedata.master.account.app.WSAccountType accountTypeID) {
        this.accountTypeID = accountTypeID;
    }

    public boolean getIsCash() {
        return this.isCash;
    }

    public void setIsCash( boolean isCash) {
        this.isCash = isCash;
    }

    public boolean getACNotice() {
        return this.ACNotice;
    }

    public void setACNotice( boolean ACNotice) {
        this.ACNotice = ACNotice;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public boolean getHasUserProperty() {
        return this.hasUserProperty;
    }

    public void setHasUserProperty( boolean hasUserProperty) {
        this.hasUserProperty = hasUserProperty;
    }

    public boolean getIsCashEquivalent() {
        return this.isCashEquivalent;
    }

    public void setIsCashEquivalent( boolean isCashEquivalent) {
        this.isCashEquivalent = isCashEquivalent;
    }

    public float getAccrualPer() {
        return this.accrualPer;
    }

    public void setAccrualPer( float accrualPer) {
        this.accrualPer = accrualPer;
    }

    public boolean getIsChangeCurrency() {
        return this.isChangeCurrency;
    }

    public void setIsChangeCurrency( boolean isChangeCurrency) {
        this.isChangeCurrency = isChangeCurrency;
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

    public boolean getBw() {
        return this.bw;
    }

    public void setBw( boolean bw) {
        this.bw = bw;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public boolean getIsSelfFreeze() {
        return this.isSelfFreeze;
    }

    public void setIsSelfFreeze( boolean isSelfFreeze) {
        this.isSelfFreeze = isSelfFreeze;
    }

    public com.kingdee.eas.basedata.master.account.app.WSAccountView getAccount() {
        return this.account;
    }

    public void setAccount( com.kingdee.eas.basedata.master.account.app.WSAccountView account) {
        this.account = account;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public boolean getIsQty() {
        return this.isQty;
    }

    public void setIsQty( boolean isQty) {
        this.isQty = isQty;
    }

    public int getControlLevel() {
        return this.controlLevel;
    }

    public void setControlLevel( int controlLevel) {
        this.controlLevel = controlLevel;
    }

    public String getDC() {
        return this.DC;
    }

    public void setDC( String DC) {
        this.DC = DC;
    }

    public com.kingdee.eas.basedata.assistant.app.WSMeasureUnit getMeasureUnitID() {
        return this.measureUnitID;
    }

    public void setMeasureUnitID( com.kingdee.eas.basedata.assistant.app.WSMeasureUnit measureUnitID) {
        this.measureUnitID = measureUnitID;
    }

    public String getAccountingcurrency() {
        return this.accountingcurrency;
    }

    public void setAccountingcurrency( String accountingcurrency) {
        this.accountingcurrency = accountingcurrency;
    }

    public boolean getIsOutDailyAccount() {
        return this.isOutDailyAccount;
    }

    public void setIsOutDailyAccount( boolean isOutDailyAccount) {
        this.isOutDailyAccount = isOutDailyAccount;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCashFlowItem getBorrowerMainCashFlowItem() {
        return this.borrowerMainCashFlowItem;
    }

    public void setBorrowerMainCashFlowItem( com.kingdee.eas.basedata.assistant.app.WSCashFlowItem borrowerMainCashFlowItem) {
        this.borrowerMainCashFlowItem = borrowerMainCashFlowItem;
    }

    public com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount getParentAA() {
        return this.parentAA;
    }

    public void setParentAA( com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount parentAA) {
        this.parentAA = parentAA;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCashFlowItem getAttCashFlowItem() {
        return this.attCashFlowItem;
    }

    public void setAttCashFlowItem( com.kingdee.eas.basedata.assistant.app.WSCashFlowItem attCashFlowItem) {
        this.attCashFlowItem = attCashFlowItem;
    }

}