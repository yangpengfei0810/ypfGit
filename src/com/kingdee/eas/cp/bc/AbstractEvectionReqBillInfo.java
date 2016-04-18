package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvectionReqBillInfo extends com.kingdee.eas.cp.bc.BizCollBillBaseInfo implements Serializable 
{
    public AbstractEvectionReqBillInfo()
    {
        this("id");
    }
    protected AbstractEvectionReqBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.cp.bc.EvectionReqBillEntryCollection());
    }
    /**
     * Object:�������뵥's ������property 
     */
    public int getTotalPeople()
    {
        return getInt("totalPeople");
    }
    public void setTotalPeople(int item)
    {
        setInt("totalPeople", item);
    }
    /**
     * Object:�������뵥's Ԥ������property 
     */
    public java.math.BigDecimal getIntendingDays()
    {
        return getBigDecimal("intendingDays");
    }
    public void setIntendingDays(java.math.BigDecimal item)
    {
        setBigDecimal("intendingDays", item);
    }
    /**
     * Object: �������뵥 's ��¼ property 
     */
    public com.kingdee.eas.cp.bc.EvectionReqBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.EvectionReqBillEntryCollection)get("entries");
    }
    /**
     * Object:�������뵥's �Ƿ���Ҫ���property 
     */
    public boolean isIsNeedLoan()
    {
        return getBoolean("isNeedLoan");
    }
    public void setIsNeedLoan(boolean item)
    {
        setBoolean("isNeedLoan", item);
    }
    /**
     * Object:�������뵥's �Ƿ���Ҫһͬ����property 
     */
    public boolean isIsDyncCheck()
    {
        return getBoolean("isDyncCheck");
    }
    public void setIsDyncCheck(boolean item)
    {
        setBoolean("isDyncCheck", item);
    }
    /**
     * Object: �������뵥 's �� property 
     */
    public com.kingdee.eas.cp.bc.EvectionLoanBillInfo getLoanBillID()
    {
        return (com.kingdee.eas.cp.bc.EvectionLoanBillInfo)get("loanBillID");
    }
    public void setLoanBillID(com.kingdee.eas.cp.bc.EvectionLoanBillInfo item)
    {
        put("loanBillID", item);
    }
    /**
     * Object:�������뵥's �Ƿ��ɳ�property 
     */
    public boolean isIsDispatch()
    {
        return getBoolean("isDispatch");
    }
    public void setIsDispatch(boolean item)
    {
        setBoolean("isDispatch", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DE853384");
    }
}