package com.kingdee.eas.base.permission.app;

import com.kingdee.bos.webservice.WSBean;

public class WSSecurity extends WSBean { 
    private int repeatTimes ;

    private com.kingdee.eas.base.permission.app.WSUser creator ;

    private boolean complex ;

    private int passwordLength ;

    private String createTime ;

    private String number ;

    private boolean requireChgPW ;

    private String alias ;

    private int lockCount ;

    private int passwordCycle ;

    private String id ;

    private boolean enableRepeatPw ;

    private String lastUpdateTime ;

    private com.kingdee.eas.basedata.org.app.WSCtrlUnit CU ;

    private boolean passwordForever ;

    private boolean expiredPassCanChg ;

    private boolean passwordCanChg ;

    private boolean needLocked ;

    private boolean requireSpecialChar ;

    private com.kingdee.eas.base.permission.app.WSUser lastUpdateUser ;

    private int forewarnDay ;

    private String descreption ;

    public int getRepeatTimes() {
        return this.repeatTimes;
    }

    public void setRepeatTimes( int repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public com.kingdee.eas.base.permission.app.WSUser getCreator() {
        return this.creator;
    }

    public void setCreator( com.kingdee.eas.base.permission.app.WSUser creator) {
        this.creator = creator;
    }

    public boolean getComplex() {
        return this.complex;
    }

    public void setComplex( boolean complex) {
        this.complex = complex;
    }

    public int getPasswordLength() {
        return this.passwordLength;
    }

    public void setPasswordLength( int passwordLength) {
        this.passwordLength = passwordLength;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public boolean getRequireChgPW() {
        return this.requireChgPW;
    }

    public void setRequireChgPW( boolean requireChgPW) {
        this.requireChgPW = requireChgPW;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias( String alias) {
        this.alias = alias;
    }

    public int getLockCount() {
        return this.lockCount;
    }

    public void setLockCount( int lockCount) {
        this.lockCount = lockCount;
    }

    public int getPasswordCycle() {
        return this.passwordCycle;
    }

    public void setPasswordCycle( int passwordCycle) {
        this.passwordCycle = passwordCycle;
    }

    public String getId() {
        return this.id;
    }

    public void setId( String id) {
        this.id = id;
    }

    public boolean getEnableRepeatPw() {
        return this.enableRepeatPw;
    }

    public void setEnableRepeatPw( boolean enableRepeatPw) {
        this.enableRepeatPw = enableRepeatPw;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime( String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public com.kingdee.eas.basedata.org.app.WSCtrlUnit getCU() {
        return this.CU;
    }

    public void setCU( com.kingdee.eas.basedata.org.app.WSCtrlUnit CU) {
        this.CU = CU;
    }

    public boolean getPasswordForever() {
        return this.passwordForever;
    }

    public void setPasswordForever( boolean passwordForever) {
        this.passwordForever = passwordForever;
    }

    public boolean getExpiredPassCanChg() {
        return this.expiredPassCanChg;
    }

    public void setExpiredPassCanChg( boolean expiredPassCanChg) {
        this.expiredPassCanChg = expiredPassCanChg;
    }

    public boolean getPasswordCanChg() {
        return this.passwordCanChg;
    }

    public void setPasswordCanChg( boolean passwordCanChg) {
        this.passwordCanChg = passwordCanChg;
    }

    public boolean getNeedLocked() {
        return this.needLocked;
    }

    public void setNeedLocked( boolean needLocked) {
        this.needLocked = needLocked;
    }

    public boolean getRequireSpecialChar() {
        return this.requireSpecialChar;
    }

    public void setRequireSpecialChar( boolean requireSpecialChar) {
        this.requireSpecialChar = requireSpecialChar;
    }

    public com.kingdee.eas.base.permission.app.WSUser getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public void setLastUpdateUser( com.kingdee.eas.base.permission.app.WSUser lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public int getForewarnDay() {
        return this.forewarnDay;
    }

    public void setForewarnDay( int forewarnDay) {
        this.forewarnDay = forewarnDay;
    }

    public String getDescreption() {
        return this.descreption;
    }

    public void setDescreption( String descreption) {
        this.descreption = descreption;
    }

}