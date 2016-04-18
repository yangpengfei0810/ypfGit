package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPublicToSplitEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPublicToSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractPublicToSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �������״�������̯��¼ 's ��Ʒ���� property 
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
     * Object: �������״�������̯��¼ 's �� property 
     */
    public com.kingdee.eas.fdc.aimcost.PublicToSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.PublicToSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.PublicToSplitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�������״�������̯��¼'s ����̯�Ĳ�Ʒ����IDproperty 
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
     * Object:�������״�������̯��¼'s ��̯��������֧��property 
     */
    public java.math.BigDecimal getSplitNeedAmt()
    {
        return getBigDecimal("splitNeedAmt");
    }
    public void setSplitNeedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitNeedAmt", item);
    }
    /**
     * Object:�������״�������̯��¼'s ����̯���ĵ���property 
     */
    public java.math.BigDecimal getSplitDiffAmt()
    {
        return getBigDecimal("splitDiffAmt");
    }
    public void setSplitDiffAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitDiffAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("85E91CF3");
    }
}