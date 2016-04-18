package com.kingdee.eas.base.attachment.app;

import com.kingdee.bos.webservice.WSBean;

public class WSBoAttchAsso extends WSBean { 
    private String assoType ;

    private String boID ;

    private String saveTime ;

    private String assoBusObjType ;

    private com.kingdee.eas.base.attachment.app.WSAttachment attachment ;

    private String id ;

    private String sourceBillID ;

    public String getAssoType() {
        return this.assoType;
    }

    public void setAssoType( String assoType) {
        this.assoType = assoType;
    }

    public String getBoID() {
        return this.boID;
    }

    public void setBoID( String boID) {
        this.boID = boID;
    }

    public String getSaveTime() {
        return this.saveTime;
    }

    public void setSaveTime( String saveTime) {
        this.saveTime = saveTime;
    }

    public String getAssoBusObjType() {
        return this.assoBusObjType;
    }

    public void setAssoBusObjType( String assoBusObjType) {
        this.assoBusObjType = assoBusObjType;
    }

    public com.kingdee.eas.base.attachment.app.WSAttachment getAttachment() {
        return this.attachment;
    }

    public void setAttachment( com.kingdee.eas.base.attachment.app.WSAttachment attachment) {
        this.attachment = attachment;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getSourceBillID() {
        return this.sourceBillID;
    }

    public void setSourceBillID( String sourceBillID) {
        this.sourceBillID = sourceBillID;
    }

}