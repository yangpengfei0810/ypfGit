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
     * Object:出差申请单's 总人数property 
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
     * Object:出差申请单's 预计天数property 
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
     * Object: 出差申请单 's 分录 property 
     */
    public com.kingdee.eas.cp.bc.EvectionReqBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.EvectionReqBillEntryCollection)get("entries");
    }
    /**
     * Object:出差申请单's 是否需要借款property 
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
     * Object:出差申请单's 是否需要一同审批property 
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
     * Object: 出差申请单 's 借款单 property 
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
     * Object:出差申请单's 是否派车property 
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