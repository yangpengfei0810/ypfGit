package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTravelAccountBillInfo extends com.kingdee.eas.cp.bc.ExpenseAccountBillInfo implements Serializable 
{
    public AbstractTravelAccountBillInfo()
    {
        this("id");
    }
    protected AbstractTravelAccountBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection());
    }
    /**
     * Object: 差旅费报销单 's 分录 property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection)get("entries");
    }
    /**
     * Object:差旅费报销单's 开户银行property 
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
     * Object:差旅费报销单's 付款单位全称property 
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
     * Object:差旅费报销单's 帐号property 
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
     * Object:差旅费报销单's 领款人property 
     */
    public String getLeadPerson()
    {
        return getString("LeadPerson");
    }
    public void setLeadPerson(String item)
    {
        setString("LeadPerson", item);
    }
    /**
     * Object:差旅费报销单's 出差申请单号property 
     */
    public String getOutAppBillNo()
    {
        return getString("outAppBillNo");
    }
    public void setOutAppBillNo(String item)
    {
        setString("outAppBillNo", item);
    }
    /**
     * Object:差旅费报销单's 是否培训property 
     */
    public com.kingdee.eas.cp.bc.EducateEnum getIsEducate()
    {
        return com.kingdee.eas.cp.bc.EducateEnum.getEnum(getString("isEducate"));
    }
    public void setIsEducate(com.kingdee.eas.cp.bc.EducateEnum item)
    {
		if (item != null) {
        setString("isEducate", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C57003BC");
    }
}