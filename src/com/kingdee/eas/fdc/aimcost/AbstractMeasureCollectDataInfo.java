package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasureCollectDataInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractMeasureCollectDataInfo()
    {
        this("id");
    }
    protected AbstractMeasureCollectDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 建筑单方汇总表 's  property 
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
     * Object: 建筑单方汇总表 's 成本科目 property 
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
     * Object:建筑单方汇总表's 所属产品property 
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
     * Object:建筑单方汇总表's 总成本property 
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
        return new BOSObjectType("B571FD4D");
    }
}