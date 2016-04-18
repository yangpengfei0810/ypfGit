package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBizAccountBillInfo extends com.kingdee.eas.cp.bc.ExpenseAccountBillInfo implements Serializable 
{
    public AbstractBizAccountBillInfo()
    {
        this("id");
    }
    protected AbstractBizAccountBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.cp.bc.BizAccountBillEntryCollection());
    }
    /**
     * Object: ���ñ����� 's ��¼ property 
     */
    public com.kingdee.eas.cp.bc.BizAccountBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.cp.bc.BizAccountBillEntryCollection)get("entries");
    }
    /**
     * Object:���ñ�����'s ��������property 
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
     * Object:���ñ�����'s ���λȫ��property 
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
     * Object:���ñ�����'s �ʺ�property 
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
     * Object:���ñ�����'s �����property 
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
        return new BOSObjectType("4A44F49F");
    }
}