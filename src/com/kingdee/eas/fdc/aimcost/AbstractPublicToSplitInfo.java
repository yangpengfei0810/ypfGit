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
     * Object:�������״�������̯'s ��ǰ��Ŀ����IDproperty 
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
     * Object: �������״�������̯ 's �������׵Ĳ�Ʒ���� property 
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
     * Object:�������״�������̯'s ����֧��property 
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
     * Object:�������״�������̯'s ����property 
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
     * Object:�������״�������̯'s ��̯��ʽproperty 
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
     * Object: �������״�������̯ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.PublicToSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.PublicToSplitEntryCollection)get("entrys");
    }
    /**
     * Object: �������״�������̯ 's ��̯��ʽF7 property 
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