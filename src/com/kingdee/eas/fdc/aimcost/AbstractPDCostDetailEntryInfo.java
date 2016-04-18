package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPDCostDetailEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPDCostDetailEntryInfo()
    {
        this("id");
    }
    protected AbstractPDCostDetailEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 各产品类型动态成本明细表数据存储分录 's  property 
     */
    public com.kingdee.eas.fdc.aimcost.PDCostDetailInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.PDCostDetailInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.PDCostDetailInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:各产品类型动态成本明细表数据存储分录's 成本科目IDproperty 
     */
    public String getCostAccountID()
    {
        return getString("costAccountID");
    }
    public void setCostAccountID(String item)
    {
        setString("costAccountID", item);
    }
    /**
     * Object:各产品类型动态成本明细表数据存储分录's 所属产品类型IDproperty 
     */
    public String getProductTypeID()
    {
        return getString("productTypeID");
    }
    public void setProductTypeID(String item)
    {
        setString("productTypeID", item);
    }
    /**
     * Object:各产品类型动态成本明细表数据存储分录's 目标成本合计property 
     */
    public java.math.BigDecimal getAimCostTotal()
    {
        return getBigDecimal("aimCostTotal");
    }
    public void setAimCostTotal(java.math.BigDecimal item)
    {
        setBigDecimal("aimCostTotal", item);
    }
    /**
     * Object:各产品类型动态成本明细表数据存储分录's 动态成本合计property 
     */
    public java.math.BigDecimal getDynamicCostTotal()
    {
        return getBigDecimal("dynamicCostTotal");
    }
    public void setDynamicCostTotal(java.math.BigDecimal item)
    {
        setBigDecimal("dynamicCostTotal", item);
    }
    /**
     * Object:各产品类型动态成本明细表数据存储分录's 目标可售单方property 
     */
    public java.math.BigDecimal getAimSale()
    {
        return getBigDecimal("aimSale");
    }
    public void setAimSale(java.math.BigDecimal item)
    {
        setBigDecimal("aimSale", item);
    }
    /**
     * Object:各产品类型动态成本明细表数据存储分录's 目标成本property 
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
     * Object:各产品类型动态成本明细表数据存储分录's 动态可售单方property 
     */
    public java.math.BigDecimal getDynamicSale()
    {
        return getBigDecimal("dynamicSale");
    }
    public void setDynamicSale(java.math.BigDecimal item)
    {
        setBigDecimal("dynamicSale", item);
    }
    /**
     * Object:各产品类型动态成本明细表数据存储分录's 动态成本property 
     */
    public java.math.BigDecimal getDynamicCost()
    {
        return getBigDecimal("dynamicCost");
    }
    public void setDynamicCost(java.math.BigDecimal item)
    {
        setBigDecimal("dynamicCost", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4B0A07A9");
    }
}