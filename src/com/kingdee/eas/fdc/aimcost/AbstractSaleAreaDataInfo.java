package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSaleAreaDataInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSaleAreaDataInfo()
    {
        this("id");
    }
    protected AbstractSaleAreaDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���۵������ܱ� 's  property 
     */
    public com.kingdee.eas.fdc.aimcost.MeasureCostInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.MeasureCostInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.MeasureCostInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ���۵������ܱ� 's �ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:���۵������ܱ�'s ������Ʒproperty 
     */
    public String getProductID()
    {
        return getString("productID");
    }
    public void setProductID(String item)
    {
        setString("productID", item);
    }
    /**
     * Object:���۵������ܱ�'s �ܳɱ�property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AFE41215");
    }
}