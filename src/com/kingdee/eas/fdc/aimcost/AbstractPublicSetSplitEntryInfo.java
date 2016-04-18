package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPublicSetSplitEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPublicSetSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractPublicSetSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 公共配套已发生成本分摊分录 's 父 property 
     */
    public com.kingdee.eas.fdc.aimcost.PublicSetSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.PublicSetSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.PublicSetSplitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 公共配套已发生成本分摊分录 's 产品类型 property 
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
     * Object:公共配套已发生成本分摊分录's 被分摊的产品类型property 
     */
    public String getSplitProductID()
    {
        return getString("splitProductID");
    }
    public void setSplitProductID(String item)
    {
        setString("splitProductID", item);
    }
    /**
     * Object:公共配套已发生成本分摊分录's 分摊到的金额property 
     */
    public java.math.BigDecimal getAmt()
    {
        return getBigDecimal("amt");
    }
    public void setAmt(java.math.BigDecimal item)
    {
        setBigDecimal("amt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("11B044DA");
    }
}