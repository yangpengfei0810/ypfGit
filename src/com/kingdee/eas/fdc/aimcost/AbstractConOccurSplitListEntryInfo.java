package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConOccurSplitListEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractConOccurSplitListEntryInfo()
    {
        this("id");
    }
    protected AbstractConOccurSplitListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合约待发生拆分单分录 's  property 
     */
    public com.kingdee.eas.fdc.aimcost.ConOccSplitListInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.ConOccSplitListInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.ConOccSplitListInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:合约待发生拆分单分录's 归属成本科目property 
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
     * Object:合约待发生拆分单分录's 所属产品类型property 
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
     * Object:合约待发生拆分单分录's 目标成本property 
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
     * Object:合约待发生拆分单分录's 已发生成本property 
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
     * Object:合约待发生拆分单分录's 尚需支出归属金额property 
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
     * Object:合约待发生拆分单分录's 调差归属金额property 
     */
    public java.math.BigDecimal getAdjAmt()
    {
        return getBigDecimal("adjAmt");
    }
    public void setAdjAmt(java.math.BigDecimal item)
    {
        setBigDecimal("adjAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C88C50BB");
    }
}