package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public abstract class AbstractLoanBillInfo extends com.kingdee.eas.cp.bc.BizCollBillBaseInfo implements Serializable 
{
    public AbstractLoanBillInfo()
    {
        this("id");
    }
    protected AbstractLoanBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �� 's ������Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountCredited()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountCredited");
    }
    public void setAccountCredited(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountCredited", item);
    }
    /**
     * Object: �� 's �跽��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountDebited()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountDebited");
    }
    public void setAccountDebited(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountDebited", item);
    }
    /**
     * Object:��'s Ԥ�ƻ�������property 
     */
    public java.util.Date getForeseeDate()
    {
        return getDate("foreseeDate");
    }
    public void setForeseeDate(java.util.Date item)
    {
        setDate("foreseeDate", item);
    }
    /**
     * Object: �� 's ֧����ʽ property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getPayMode()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("payMode");
    }
    public void setPayMode(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("payMode", item);
    }
    /**
     * Object:��'s ����״̬property 
     */
    public String getLoanState()
    {
        return getString("loanState");
    }
    public void setLoanState(String item)
    {
        setString("loanState", item);
    }
    /**
     * Object:��'s ��������property 
     */
    public java.util.Date getReturnDate()
    {
        return getDate("returnDate");
    }
    public void setReturnDate(java.util.Date item)
    {
        setDate("returnDate", item);
    }
    /**
     * Object:��'s ������property 
     */
    public java.math.BigDecimal getReturnAmount()
    {
        return getBigDecimal("returnAmount");
    }
    public void setReturnAmount(java.math.BigDecimal item)
    {
        setBigDecimal("returnAmount", item);
    }
    /**
     * Object:��'s Ԥ������property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    /**
     * Object:��'s �Ƿ��Ѿ�����ƾ֤property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object:��'s �Ƿ����property 
     */
    public String getActionFlag()
    {
        return getString("actionFlag");
    }
    public void setActionFlag(String item)
    {
        setString("actionFlag", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A8808375");
    }
}