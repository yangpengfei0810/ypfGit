package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPublicSetSplitInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPublicSetSplitInfo()
    {
        this("id");
    }
    protected AbstractPublicSetSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.PublicSetSplitEntryCollection());
    }
    /**
     * Object:���������ѷ����ɱ���̯'s ��ǰ������ĿIDproperty 
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
     * Object: ���������ѷ����ɱ���̯ 's �������׵Ĳ�Ʒ���� property 
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
     * Object:���������ѷ����ɱ���̯'s �ѷ����ɱ�property 
     */
    public java.math.BigDecimal getHappenedAmt()
    {
        return getBigDecimal("happenedAmt");
    }
    public void setHappenedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("happenedAmt", item);
    }
    /**
     * Object:���������ѷ����ɱ���̯'s ��̯��ʽ���ϣ�property 
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
     * Object: ���������ѷ����ɱ���̯ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.PublicSetSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.PublicSetSplitEntryCollection)get("entrys");
    }
    /**
     * Object: ���������ѷ����ɱ���̯ 's ��̯��ʽ property 
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
        return new BOSObjectType("5B781E78");
    }
}