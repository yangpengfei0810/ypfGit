package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTargetCostSplitInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTargetCostSplitInfo()
    {
        this("id");
    }
    protected AbstractTargetCostSplitInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 公共配套目标成本分摊 's  property 
     */
    public com.kingdee.eas.fdc.aimcost.MeasureCostInfo getFParent()
    {
        return (com.kingdee.eas.fdc.aimcost.MeasureCostInfo)get("FParent");
    }
    public void setFParent(com.kingdee.eas.fdc.aimcost.MeasureCostInfo item)
    {
        put("FParent", item);
    }
    /**
     * Object: 公共配套目标成本分摊 's 公共配套 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getPublicProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("publicProduct");
    }
    public void setPublicProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("publicProduct", item);
    }
    /**
     * Object: 公共配套目标成本分摊 's 所属产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:公共配套目标成本分摊's 分摊方式(废)property 
     */
    public com.kingdee.eas.fdc.aimcost.ApportionTypesEnum getSplitType()
    {
        return com.kingdee.eas.fdc.aimcost.ApportionTypesEnum.getEnum(getString("splitType"));
    }
    public void setSplitType(com.kingdee.eas.fdc.aimcost.ApportionTypesEnum item)
    {
		if (item != null) {
        setString("splitType", item.getValue());
		}
    }
    /**
     * Object:公共配套目标成本分摊's 金额-分摊后的结果property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: 公共配套目标成本分摊 's 分摊方式F7 property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getSplitTypeF7()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("splitTypeF7");
    }
    public void setSplitTypeF7(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("splitTypeF7", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("49033285");
    }
}