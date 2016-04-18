package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConOccSplitListEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractConOccSplitListEntryInfo()
    {
        this("id");
    }
    protected AbstractConOccSplitListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:ConOccSplitListEntry's nullproperty 
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
     * Object:ConOccSplitListEntry's aimCostproperty 
     */
    public java.math.BigDecimal getAimCost()
    {
        return getBigDecimal("aimCost");
    }
    public void setAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("aimCost", item);
    }
    /**
     * Object:ConOccSplitListEntry's hapCostproperty 
     */
    public java.math.BigDecimal getHapCost()
    {
        return getBigDecimal("hapCost");
    }
    public void setHapCost(java.math.BigDecimal item)
    {
        setBigDecimal("hapCost", item);
    }
    /**
     * Object:ConOccSplitListEntry's needAmtproperty 
     */
    public java.math.BigDecimal getNeedAmt()
    {
        return getBigDecimal("needAmt");
    }
    public void setNeedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("needAmt", item);
    }
    /**
     * Object:ConOccSplitListEntry's adjAmtproperty 
     */
    public java.math.BigDecimal getAdjAmt()
    {
        return getBigDecimal("adjAmt");
    }
    public void setAdjAmt(java.math.BigDecimal item)
    {
        setBigDecimal("adjAmt", item);
    }
    /**
     * Object: ConOccSplitListEntry 's parent property 
     */
    public com.kingdee.eas.fdc.aimcost.ConOccSplitListInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.ConOccSplitListInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.ConOccSplitListInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("88ABD9DE");
    }
}