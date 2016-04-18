package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConOccSplitListInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractConOccSplitListInfo()
    {
        this("id");
    }
    protected AbstractConOccSplitListInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.aimcost.ConOccurSplitListEntryCollection());
    }
    /**
     * Object:��Լ��������ֵ�'s ������Ŀproperty 
     */
    public String getProjectID()
    {
        return getString("projectID");
    }
    public void setProjectID(String item)
    {
        setString("projectID", item);
    }
    /**
     * Object:��Լ��������ֵ�'s ��ܺ�ԼIDproperty 
     */
    public String getProgrammingContractID()
    {
        return getString("programmingContractID");
    }
    public void setProgrammingContractID(String item)
    {
        setString("programmingContractID", item);
    }
    /**
     * Object:��Լ��������ֵ�'s Ŀǰ�ѷ���property 
     */
    public java.math.BigDecimal getCurHappend()
    {
        return getBigDecimal("curHappend");
    }
    public void setCurHappend(java.math.BigDecimal item)
    {
        setBigDecimal("curHappend", item);
    }
    /**
     * Object:��Լ��������ֵ�'s ����֧��property 
     */
    public java.math.BigDecimal getNeedAmount()
    {
        return getBigDecimal("needAmount");
    }
    public void setNeedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("needAmount", item);
    }
    /**
     * Object:��Լ��������ֵ�'s ����property 
     */
    public java.math.BigDecimal getAdjAmount()
    {
        return getBigDecimal("adjAmount");
    }
    public void setAdjAmount(java.math.BigDecimal item)
    {
        setBigDecimal("adjAmount", item);
    }
    /**
     * Object: ��Լ��������ֵ� 's entry property 
     */
    public com.kingdee.eas.fdc.aimcost.ConOccurSplitListEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.ConOccurSplitListEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FF0C76F4");
    }
}