package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPublicToSplitInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPublicToSplitInfo()
    {
        this("id");
    }
    protected AbstractPublicToSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.PublicToSplitEntryCollection());
    }
    /**
     * Object:公共配套待发生分摊's 当前项目工程IDproperty 
     */
    public String getCurProjectID()
    {
        return getString("curProjectID");
    }
    public void setCurProjectID(String item)
    {
        setString("curProjectID", item);
    }
    /**
     * Object: 公共配套待发生分摊 's 公共配套的产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object:公共配套待发生分摊's 尚需支出property 
     */
    public java.math.BigDecimal getNeedSpendingAmt()
    {
        return getBigDecimal("needSpendingAmt");
    }
    public void setNeedSpendingAmt(java.math.BigDecimal item)
    {
        setBigDecimal("needSpendingAmt", item);
    }
    /**
     * Object:公共配套待发生分摊's 调差property 
     */
    public java.math.BigDecimal getAdjustmentDiffAmt()
    {
        return getBigDecimal("adjustmentDiffAmt");
    }
    public void setAdjustmentDiffAmt(java.math.BigDecimal item)
    {
        setBigDecimal("adjustmentDiffAmt", item);
    }
    /**
     * Object:公共配套待发生分摊's 分摊方式property 
     */
    public com.kingdee.eas.fdc.aimcost.ApportionTypesEnum getApportionType()
    {
        return com.kingdee.eas.fdc.aimcost.ApportionTypesEnum.getEnum(getString("apportionType"));
    }
    public void setApportionType(com.kingdee.eas.fdc.aimcost.ApportionTypesEnum item)
    {
		if (item != null) {
        setString("apportionType", item.getValue());
		}
    }
    /**
     * Object: 公共配套待发生分摊 's 分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.PublicToSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.PublicToSplitEntryCollection)get("entrys");
    }
    /**
     * Object: 公共配套待发生分摊 's 分摊方式F7 property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getApportionTypeF7()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("apportionTypeF7");
    }
    public void setApportionTypeF7(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("apportionTypeF7", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A1FCDABF");
    }
}