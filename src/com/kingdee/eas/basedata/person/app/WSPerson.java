package com.kingdee.eas.basedata.person.app;

import com.kingdee.bos.webservice.WSBean;

public class WSPerson extends WSBean { 
    private String lastUpdateTime ;

    private com.kingdee.eas.hr.base.app.WSEmployeeType employeeType ;

    private com.kingdee.eas.basedata.hraux.app.WSFolk folk ;

    private String number ;

    private String fullNamePingYin ;

    private String nativePlace ;

    private String backupEMail ;

    private String passportNO ;

    private String description ;

    private com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit ;

    private String id ;

    private String checkState ;

    private boolean isStandbyCadre ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private String oldName ;

    private String idCardNO ;

    private com.kingdee.eas.fi.lcm.app.WSLCMRationLevel lCMRationLevel ;

    private com.kingdee.eas.hr.base.app.WSCertifiedCompetency highestCompetency ;

    private String homePhone ;

    private com.kingdee.eas.basedata.hraux.app.WSBirth birth ;

    private com.kingdee.eas.basedata.hraux.app.WSWed wed ;

    private String backupCell ;

    private com.kingdee.eas.hr.base.app.WSTechnicalPost highestTechPost ;

    private String officePhone ;

    private com.kingdee.eas.basedata.hraux.app.WSHealth health ;

    private com.kingdee.eas.basedata.hraux.app.WSDiploma highestDegree ;

    private com.kingdee.eas.basedata.assistant.app.WSKAClassfication kaClassfication ;

    private String cell ;

    private com.kingdee.eas.hr.base.app.WSEmployeeClassify employeeClassify ;

    private boolean isStandby ;

    private String rtx ;

    private String addressTX ;

    private String simpleName ;

    private String bloodType ;

    private int lenOfActualService ;

    private int indexOf ;

    private String idNum ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private com.kingdee.eas.basedata.hraux.app.WSPoliticalFace politicalFace ;

    private String name ;

    private com.kingdee.eas.basedata.hraux.app.WSDegree highestSubDegree ;

    private String email ;

    private String createTime ;

    private String simpleNamePingYin ;

    private com.kingdee.eas.hr.base.app.WSTechnicalPost employTechPost ;

    private com.kingdee.eas.basedata.hraux.app.WSNationality nationality ;

    private String state ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private String gender ;

    private String birthday ;

    private int height ;

    private com.kingdee.eas.basedata.hraux.app.WSStanding standing ;

    private String deletedStatus ;

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public com.kingdee.eas.hr.base.app.WSEmployeeType getEmployeeType() {
        return this.employeeType;
    }

    public void setEmployeeType( com.kingdee.eas.hr.base.app.WSEmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public com.kingdee.eas.basedata.hraux.app.WSFolk getFolk() {
        return this.folk;
    }

    public void setFolk( com.kingdee.eas.basedata.hraux.app.WSFolk folk) {
        this.folk = folk;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public String getFullNamePingYin() {
        return this.fullNamePingYin;
    }

    public void setFullNamePingYin( String fullNamePingYin) {
        this.fullNamePingYin = fullNamePingYin;
    }

    public String getNativePlace() {
        return this.nativePlace;
    }

    public void setNativePlace( String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getBackupEMail() {
        return this.backupEMail;
    }

    public void setBackupEMail( String backupEMail) {
        this.backupEMail = backupEMail;
    }

    public String getPassportNO() {
        return this.passportNO;
    }

    public void setPassportNO( String passportNO) {
        this.passportNO = passportNO;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public com.kingdee.eas.basedata.org.app.WSHROrgUnit getHrOrgUnit() {
        return this.hrOrgUnit;
    }

    public void setHrOrgUnit( com.kingdee.eas.basedata.org.app.WSHROrgUnit hrOrgUnit) {
        this.hrOrgUnit = hrOrgUnit;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public String getCheckState() {
        return this.checkState;
    }

    public void setCheckState( String checkState) {
        this.checkState = checkState;
    }

    public boolean getIsStandbyCadre() {
        return this.isStandbyCadre;
    }

    public void setIsStandbyCadre( boolean isStandbyCadre) {
        this.isStandbyCadre = isStandbyCadre;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public String getOldName() {
        return this.oldName;
    }

    public void setOldName( String oldName) {
        this.oldName = oldName;
    }

    public String getIdCardNO() {
        return this.idCardNO;
    }

    public void setIdCardNO( String idCardNO) {
        this.idCardNO = idCardNO;
    }

    public com.kingdee.eas.fi.lcm.app.WSLCMRationLevel getLCMRationLevel() {
        return this.lCMRationLevel;
    }

    public void setLCMRationLevel( com.kingdee.eas.fi.lcm.app.WSLCMRationLevel lCMRationLevel) {
        this.lCMRationLevel = lCMRationLevel;
    }

    public com.kingdee.eas.hr.base.app.WSCertifiedCompetency getHighestCompetency() {
        return this.highestCompetency;
    }

    public void setHighestCompetency( com.kingdee.eas.hr.base.app.WSCertifiedCompetency highestCompetency) {
        this.highestCompetency = highestCompetency;
    }

    public String getHomePhone() {
        return this.homePhone;
    }

    public void setHomePhone( String homePhone) {
        this.homePhone = homePhone;
    }

    public com.kingdee.eas.basedata.hraux.app.WSBirth getBirth() {
        return this.birth;
    }

    public void setBirth( com.kingdee.eas.basedata.hraux.app.WSBirth birth) {
        this.birth = birth;
    }

    public com.kingdee.eas.basedata.hraux.app.WSWed getWed() {
        return this.wed;
    }

    public void setWed( com.kingdee.eas.basedata.hraux.app.WSWed wed) {
        this.wed = wed;
    }

    public String getBackupCell() {
        return this.backupCell;
    }

    public void setBackupCell( String backupCell) {
        this.backupCell = backupCell;
    }

    public com.kingdee.eas.hr.base.app.WSTechnicalPost getHighestTechPost() {
        return this.highestTechPost;
    }

    public void setHighestTechPost( com.kingdee.eas.hr.base.app.WSTechnicalPost highestTechPost) {
        this.highestTechPost = highestTechPost;
    }

    public String getOfficePhone() {
        return this.officePhone;
    }

    public void setOfficePhone( String officePhone) {
        this.officePhone = officePhone;
    }

    public com.kingdee.eas.basedata.hraux.app.WSHealth getHealth() {
        return this.health;
    }

    public void setHealth( com.kingdee.eas.basedata.hraux.app.WSHealth health) {
        this.health = health;
    }

    public com.kingdee.eas.basedata.hraux.app.WSDiploma getHighestDegree() {
        return this.highestDegree;
    }

    public void setHighestDegree( com.kingdee.eas.basedata.hraux.app.WSDiploma highestDegree) {
        this.highestDegree = highestDegree;
    }

    public com.kingdee.eas.basedata.assistant.app.WSKAClassfication getKaClassfication() {
        return this.kaClassfication;
    }

    public void setKaClassfication( com.kingdee.eas.basedata.assistant.app.WSKAClassfication kaClassfication) {
        this.kaClassfication = kaClassfication;
    }

    public String getCell() {
        return this.cell;
    }

    public void setCell( String cell) {
        this.cell = cell;
    }

    public com.kingdee.eas.hr.base.app.WSEmployeeClassify getEmployeeClassify() {
        return this.employeeClassify;
    }

    public void setEmployeeClassify( com.kingdee.eas.hr.base.app.WSEmployeeClassify employeeClassify) {
        this.employeeClassify = employeeClassify;
    }

    public boolean getIsStandby() {
        return this.isStandby;
    }

    public void setIsStandby( boolean isStandby) {
        this.isStandby = isStandby;
    }

    public String getRtx() {
        return this.rtx;
    }

    public void setRtx( String rtx) {
        this.rtx = rtx;
    }

    public String getAddressTX() {
        return this.addressTX;
    }

    public void setAddressTX( String addressTX) {
        this.addressTX = addressTX;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName( String simpleName) {
        this.simpleName = simpleName;
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public void setBloodType( String bloodType) {
        this.bloodType = bloodType;
    }

    public int getLenOfActualService() {
        return this.lenOfActualService;
    }

    public void setLenOfActualService( int lenOfActualService) {
        this.lenOfActualService = lenOfActualService;
    }

    public int getIndexOf() {
        return this.indexOf;
    }

    public void setIndexOf( int indexOf) {
        this.indexOf = indexOf;
    }

    public String getIdNum() {
        return this.idNum;
    }

    public void setIdNum( String idNum) {
        this.idNum = idNum;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public com.kingdee.eas.basedata.hraux.app.WSPoliticalFace getPoliticalFace() {
        return this.politicalFace;
    }

    public void setPoliticalFace( com.kingdee.eas.basedata.hraux.app.WSPoliticalFace politicalFace) {
        this.politicalFace = politicalFace;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public com.kingdee.eas.basedata.hraux.app.WSDegree getHighestSubDegree() {
        return this.highestSubDegree;
    }

    public void setHighestSubDegree( com.kingdee.eas.basedata.hraux.app.WSDegree highestSubDegree) {
        this.highestSubDegree = highestSubDegree;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail( String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getSimpleNamePingYin() {
        return this.simpleNamePingYin;
    }

    public void setSimpleNamePingYin( String simpleNamePingYin) {
        this.simpleNamePingYin = simpleNamePingYin;
    }

    public com.kingdee.eas.hr.base.app.WSTechnicalPost getEmployTechPost() {
        return this.employTechPost;
    }

    public void setEmployTechPost( com.kingdee.eas.hr.base.app.WSTechnicalPost employTechPost) {
        this.employTechPost = employTechPost;
    }

    public com.kingdee.eas.basedata.hraux.app.WSNationality getNationality() {
        return this.nationality;
    }

    public void setNationality( com.kingdee.eas.basedata.hraux.app.WSNationality nationality) {
        this.nationality = nationality;
    }

    public String getState() {
        return this.state;
    }

    public void setState( String state) {
        this.state = state;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender( String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday( String birthday) {
        this.birthday = birthday;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight( int height) {
        this.height = height;
    }

    public com.kingdee.eas.basedata.hraux.app.WSStanding getStanding() {
        return this.standing;
    }

    public void setStanding( com.kingdee.eas.basedata.hraux.app.WSStanding standing) {
        this.standing = standing;
    }

    public String getDeletedStatus() {
        return this.deletedStatus;
    }

    public void setDeletedStatus( String deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

}