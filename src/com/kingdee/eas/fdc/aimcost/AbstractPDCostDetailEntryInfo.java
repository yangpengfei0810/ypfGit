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
     * Object: ����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢��¼ 's  property 
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
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢��¼'s �ɱ���ĿIDproperty 
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
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢��¼'s ������Ʒ����IDproperty 
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
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢��¼'s Ŀ��ɱ��ϼ�property 
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
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢��¼'s ��̬�ɱ��ϼ�property 
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
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢��¼'s Ŀ����۵���property 
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
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢��¼'s Ŀ��ɱ�property 
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
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢��¼'s ��̬���۵���property 
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
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢��¼'s ��̬�ɱ�property 
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