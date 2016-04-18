package com.kingdee.eas.base.attachment.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAttachment extends WSBean { 
    private boolean isShared ;

    private String name ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String sharedDesc ;

    private String attachID ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private int sizeInByte ;

    private String description ;

    private com.kingdee.eas.base.attachment.app.WSBoAttchAsso[] boAttchAsso ;

    private String beizhu ;

    private String permission ;

    private String simpleName ;

    private String type ;

    private byte[] file ;

    private String createTime ;

    private String size ;

    private String shareRange ;

    private String number ;

    private String id ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String lastUpdateTime ;

    public boolean getIsShared() {
        return this.isShared;
    }

    public void setIsShared( boolean isShared) {
        this.isShared = isShared;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getSharedDesc() {
        return this.sharedDesc;
    }

    public void setSharedDesc( String sharedDesc) {
        this.sharedDesc = sharedDesc;
    }

    public String getAttachID() {
        return this.attachID;
    }

    public void setAttachID( String attachID) {
        this.attachID = attachID;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public int getSizeInByte() {
        return this.sizeInByte;
    }

    public void setSizeInByte( int sizeInByte) {
        this.sizeInByte = sizeInByte;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public com.kingdee.eas.base.attachment.app.WSBoAttchAsso[] getBoAttchAsso() {
        return this.boAttchAsso;
    }

    public void setBoAttchAsso( com.kingdee.eas.base.attachment.app.WSBoAttchAsso[] boAttchAsso) {
        this.boAttchAsso = boAttchAsso;
    }

    public String getBeizhu() {
        return this.beizhu;
    }

    public void setBeizhu( String beizhu) {
        this.beizhu = beizhu;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission( String permission) {
        this.permission = permission;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public String getType() {
        return this.type;
    }

    public void setType( String type) {
        this.type = type;
    }

    public byte[] getFile() {
        return this.file;
    }

    public void setFile( byte[] file) {
        this.file = file;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize( String size) {
        this.size = size;
    }

    public String getShareRange() {
        return this.shareRange;
    }

    public void setShareRange( String shareRange) {
        this.shareRange = shareRange;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
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

}