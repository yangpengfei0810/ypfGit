package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanIndexEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPlanIndexEntryInfo()
    {
        this("id");
    }
    protected AbstractPlanIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ָ���¼'s �Ƿ��ǲ�Ʒ����property 
     */
    public boolean isIsProduct()
    {
        return getBoolean("isProduct");
    }
    public void setIsProduct(boolean item)
    {
        setBoolean("isProduct", item);
    }
    /**
     * Object:����ָ���¼'s ��Ʒ����property 
     */
    public String getName()
    {
        return getName((Locale)null);
    }
    public void setName(String item)
    {
		setName(item,(Locale)null);
    }
    public String getName(Locale local)
    {
        return TypeConversionUtils.objToString(get("name", local));
    }
    public void setName(String item, Locale local)
    {
        put("name", item, local);
    }
    /**
     * Object:����ָ���¼'s ռ�����property 
     */
    public java.math.BigDecimal getContainArea()
    {
        return getBigDecimal("containArea");
    }
    public void setContainArea(java.math.BigDecimal item)
    {
        setBigDecimal("containArea", item);
    }
    /**
     * Object:����ָ���¼'s �ݻ��ʻ�λ��property 
     */
    public java.math.BigDecimal getCubageRate()
    {
        return getBigDecimal("cubageRate");
    }
    public void setCubageRate(java.math.BigDecimal item)
    {
        setBigDecimal("cubageRate", item);
    }
    /**
     * Object:����ָ���¼'s �������property 
     */
    public java.math.BigDecimal getBuildArea()
    {
        return getBigDecimal("buildArea");
    }
    public void setBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildArea", item);
    }
    /**
     * Object:����ָ���¼'s �������property 
     */
    public java.math.BigDecimal getSellArea()
    {
        return getBigDecimal("sellArea");
    }
    public void setSellArea(java.math.BigDecimal item)
    {
        setBigDecimal("sellArea", item);
    }
    /**
     * Object:����ָ���¼'s ��Ʒ����property 
     */
    public java.math.BigDecimal getProductRate()
    {
        return getBigDecimal("productRate");
    }
    public void setProductRate(java.math.BigDecimal item)
    {
        setBigDecimal("productRate", item);
    }
    /**
     * Object:����ָ���¼'s ƽ��ÿ�����property 
     */
    public java.math.BigDecimal getUnitArea()
    {
        return getBigDecimal("unitArea");
    }
    public void setUnitArea(java.math.BigDecimal item)
    {
        setBigDecimal("unitArea", item);
    }
    /**
     * Object:����ָ���¼'s ��Ԫ��property 
     */
    public java.math.BigDecimal getUnits()
    {
        return getBigDecimal("units");
    }
    public void setUnits(java.math.BigDecimal item)
    {
        setBigDecimal("units", item);
    }
    /**
     * Object:����ָ���¼'s ���� property 
     */
    public java.math.BigDecimal getDoors()
    {
        return getBigDecimal("doors");
    }
    public void setDoors(java.math.BigDecimal item)
    {
        setBigDecimal("doors", item);
    }
    /**
     * Object:����ָ���¼'s ����property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum getType()
    {
        return com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:����ָ���¼'s ����property 
     */
    public int getIndex()
    {
        return getInt("index");
    }
    public void setIndex(int item)
    {
        setInt("index", item);
    }
    /**
     * Object: ����ָ���¼ 's ��Ʒ property 
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
     * Object: ����ָ���¼ 's �� property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.PlanIndexInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.PlanIndexInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:����ָ���¼'s �Ƿ��̯property 
     */
    public boolean isIsSplit()
    {
        return getBoolean("isSplit");
    }
    public void setIsSplit(boolean item)
    {
        setBoolean("isSplit", item);
    }
    /**
     * Object:����ָ���¼'s �Ƿ��в�Ȩproperty 
     */
    public boolean isIsHasPropertyRight()
    {
        return getBoolean("isHasPropertyRight");
    }
    public void setIsHasPropertyRight(boolean item)
    {
        setBoolean("isHasPropertyRight", item);
    }
    /**
     * Object:����ָ���¼'s ��עproperty 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:����ָ���¼'s ʵ�ʽ������property 
     */
    public java.math.BigDecimal getConstructArea()
    {
        return getBigDecimal("constructArea");
    }
    public void setConstructArea(java.math.BigDecimal item)
    {
        setBigDecimal("constructArea", item);
    }
    /**
     * Object:����ָ���¼'s ������property 
     */
    public int getElevators()
    {
        return getInt("elevators");
    }
    public void setElevators(int item)
    {
        setInt("elevators", item);
    }
    /**
     * Object:����ָ���¼'s ����property 
     */
    public int getFloors()
    {
        return getInt("floors");
    }
    public void setFloors(int item)
    {
        setInt("floors", item);
    }
    /**
     * Object:����ָ���¼'s ���property 
     */
    public java.math.BigDecimal getFloorHeight()
    {
        return getBigDecimal("floorHeight");
    }
    public void setFloorHeight(java.math.BigDecimal item)
    {
        setBigDecimal("floorHeight", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DCB88FE0");
    }
}