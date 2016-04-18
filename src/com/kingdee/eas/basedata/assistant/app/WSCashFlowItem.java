package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSCashFlowItem extends WSBean { 
    private boolean isEnable ;

    private boolean isProfit ;

    private int level ;

    private String name ;

    private boolean isFreeze ;

    private boolean isRunItem ;

    private String number ;

    private String fullName ;

    private String direction ;

    private String displayName ;

    private String longNumber ;

    private boolean isDealActivity ;

    private String simpleName ;

    private com.kingdee.eas.basedata.assistant.app.WSCashFlowItem parent ;

    private String createTime ;

    private String lastUpdateTime ;

    private String type ;

    private com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount asstAccount ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String id ;

    private boolean isExChange ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private boolean isLeaf ;

    private String description ;

    public boolean getIsEnable() {
        return this.isEnable;
    }

    public void setIsEnable( boolean isEnable) {
        this.isEnable = isEnable;
    }

    public boolean getIsProfit() {
        return this.isProfit;
    }

    public void setIsProfit( boolean isProfit) {
        this.isProfit = isProfit;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public boolean getIsFreeze() {
        return this.isFreeze;
    }

    public void setIsFreeze( boolean isFreeze) {
        this.isFreeze = isFreeze;
    }

    public boolean getIsRunItem() {
        return this.isRunItem;
    }

    public void setIsRunItem( boolean isRunItem) {
        this.isRunItem = isRunItem;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName( String fullName) {
        this.fullName = fullName;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection( String direction) {
        this.direction = direction;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName( String displayName) {
        this.displayName = displayName;
    }

    public String getLongNumber() {
        return this.longNumber;
    }

    public void setLongNumber( String longNumber) {
        this.longNumber = longNumber;
    }

    public boolean getIsDealActivity() {
        return this.isDealActivity;
    }

    public void setIsDealActivity( boolean isDealActivity) {
        this.isDealActivity = isDealActivity;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCashFlowItem getParent() {
        return this.parent;
    }

    public void setParent( com.kingdee.eas.basedata.assistant.app.WSCashFlowItem parent) {
        this.parent = parent;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getType() {
        return this.type;
    }

    public void setType( String type) {
        this.type = type;
    }

    public com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount getAsstAccount() {
        return this.asstAccount;
    }

    public void setAsstAccount( com.kingdee.eas.basedata.master.auxacct.app.WSAsstAccount asstAccount) {
        this.asstAccount = asstAccount;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public boolean getIsExChange() {
        return this.isExChange;
    }

    public void setIsExChange( boolean isExChange) {
        this.isExChange = isExChange;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf( boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

}