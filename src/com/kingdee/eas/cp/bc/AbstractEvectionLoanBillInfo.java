package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvectionLoanBillInfo extends com.kingdee.eas.cp.bc.LoanBillInfo implements Serializable 
{
    public AbstractEvectionLoanBillInfo()
    {
        this("id");
    }
    protected AbstractEvectionLoanBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.cp.bc.EvectionLoanBillEntryCollection());
    }
    /**
     * Object: 出差借款单 's 分录 property 
     */
    public com.kingdee.eas.cp.bc.EvectionLoanBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.EvectionLoanBillEntryCollection)get("entries");
    }
    /**
     * Object:出差借款单's 开户银行property 
     */
    public String getBankNo()
    {
        return getString("BankNo");
    }
    public void setBankNo(String item)
    {
        setString("BankNo", item);
    }
    /**
     * Object:出差借款单's 付款单位全称property 
     */
    public String getPayUnitName()
    {
        return getString("PayUnitName");
    }
    public void setPayUnitName(String item)
    {
        setString("PayUnitName", item);
    }
    /**
     * Object:出差借款单's 帐号property 
     */
    public String getAccountNo()
    {
        return getString("AccountNo");
    }
    public void setAccountNo(String item)
    {
        setString("AccountNo", item);
    }
    /**
     * Object:出差借款单's 领款人property 
     */
    public String getLeadPerson()
    {
        return getString("LeadPerson");
    }
    public void setLeadPerson(String item)
    {
        setString("LeadPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7AE53B38");
    }
}