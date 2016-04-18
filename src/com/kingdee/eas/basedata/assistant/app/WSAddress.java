package com.kingdee.eas.basedata.assistant.app;

import com.kingdee.bos.webservice.WSBean;

public class WSAddress extends WSBean { 
    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private com.kingdee.eas.basedata.assistant.app.WSLanguage languageId ;

    private String linkMan ;

    private String name ;

    private String simpleName ;

    private com.kingdee.eas.basedata.assistant.app.WSCity cityId ;

    private com.kingdee.eas.basedata.assistant.app.WSCountry countryId ;

    private com.kingdee.eas.basedata.assistant.app.WSRegion districtId ;

    private String availTime ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private String detailAddress ;

    private String fax ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private com.kingdee.eas.basedata.assistant.app.WSProvince provinceId ;

    private String lastUpdateTime ;

    private String createTime ;

    private String postalAddress ;

    private com.kingdee.eas.basedata.assistant.app.WSAddressClass classId ;

    private String emailAddress ;

    private String id ;

    private String phone ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit adminCU ;

    private String number ;

    private String postalCode ;

    private String description ;

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public com.kingdee.eas.basedata.assistant.app.WSLanguage getLanguageId() {
        return this.languageId;
    }

    public void setLanguageId( com.kingdee.eas.basedata.assistant.app.WSLanguage languageId) {
        this.languageId = languageId;
    }

    public String getLinkMan() {
        return this.linkMan;
    }

    public void setLinkMan( String linkMan) {
        this.linkMan = linkMan;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCity getCityId() {
        return this.cityId;
    }

    public void setCityId( com.kingdee.eas.basedata.assistant.app.WSCity cityId) {
        this.cityId = cityId;
    }

    public com.kingdee.eas.basedata.assistant.app.WSCountry getCountryId() {
        return this.countryId;
    }

    public void setCountryId( com.kingdee.eas.basedata.assistant.app.WSCountry countryId) {
        this.countryId = countryId;
    }

    public com.kingdee.eas.basedata.assistant.app.WSRegion getDistrictId() {
        return this.districtId;
    }

    public void setDistrictId( com.kingdee.eas.basedata.assistant.app.WSRegion districtId) {
        this.districtId = districtId;
    }

    public String getAvailTime() {
        return this.availTime;
    }

    public void setAvailTime( String availTime) {
        this.availTime = availTime;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getDetailAddress() {
        return this.detailAddress;
    }

    public void setDetailAddress( String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax( String fax) {
        this.fax = fax;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public com.kingdee.eas.basedata.assistant.app.WSProvince getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId( com.kingdee.eas.basedata.assistant.app.WSProvince provinceId) {
        this.provinceId = provinceId;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getPostalAddress() {
        return this.postalAddress;
    }

    public void setPostalAddress( String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public com.kingdee.eas.basedata.assistant.app.WSAddressClass getClassId() {
        return this.classId;
    }

    public void setClassId( com.kingdee.eas.basedata.assistant.app.WSAddressClass classId) {
        this.classId = classId;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress( String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone( String phone) {
        this.phone = phone;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getAdminCU() {
        return this.adminCU;
    }

    public void setAdminCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit adminCU) {
        this.adminCU = adminCU;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode( String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

}