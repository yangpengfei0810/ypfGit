package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynCostDetailDataInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDynCostDetailDataInfo()
    {
        this("id");
    }
    protected AbstractDynCostDetailDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��̬�ɱ���ϸ���ݴ洢 's ��ͷ property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostDetailInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostDetailInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.DynCostDetailInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ��ܺ�ԼIDproperty 
     */
    public String getProgrammingContractID()
    {
        return getString("programmingContractID");
    }
    public void setProgrammingContractID(String item)
    {
        setString("programmingContractID", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ��ͬIDproperty 
     */
    public String getContractID()
    {
        return getString("contractID");
    }
    public void setContractID(String item)
    {
        setString("contractID", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's �滮���property 
     */
    public java.math.BigDecimal getPromAmount()
    {
        return getBigDecimal("promAmount");
    }
    public void setPromAmount(java.math.BigDecimal item)
    {
        setBigDecimal("promAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ״̬property 
     */
    public boolean isState()
    {
        return getBoolean("state");
    }
    public void setState(boolean item)
    {
        setBoolean("state", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ǩԼ���property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ʵ�ʳа����property 
     */
    public java.math.BigDecimal getActualContractingAmount()
    {
        return getBigDecimal("actualContractingAmount");
    }
    public void setActualContractingAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actualContractingAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ���н��property 
     */
    public java.math.BigDecimal getTempAmount()
    {
        return getBigDecimal("tempAmount");
    }
    public void setTempAmount(java.math.BigDecimal item)
    {
        setBigDecimal("tempAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ������property 
     */
    public java.math.BigDecimal getChangeAmount()
    {
        return getBigDecimal("changeAmount");
    }
    public void setChangeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ������property 
     */
    public java.math.BigDecimal getBalanceAmount()
    {
        return getBigDecimal("balanceAmount");
    }
    public void setBalanceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("balanceAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's �������property 
     */
    public java.math.BigDecimal getLatestCost()
    {
        return getBigDecimal("latestCost");
    }
    public void setLatestCost(java.math.BigDecimal item)
    {
        setBigDecimal("latestCost", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's �滮���property 
     */
    public java.math.BigDecimal getPromBalance()
    {
        return getBigDecimal("promBalance");
    }
    public void setPromBalance(java.math.BigDecimal item)
    {
        setBigDecimal("promBalance", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ����֧��property 
     */
    public java.math.BigDecimal getNeedSpendingAmount()
    {
        return getBigDecimal("needSpendingAmount");
    }
    public void setNeedSpendingAmount(java.math.BigDecimal item)
    {
        setBigDecimal("needSpendingAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ��̬�ɱ�С�ƣ��������property 
     */
    public java.math.BigDecimal getDynamicCostOneAmount()
    {
        return getBigDecimal("dynamicCostOneAmount");
    }
    public void setDynamicCostOneAmount(java.math.BigDecimal item)
    {
        setBigDecimal("dynamicCostOneAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's �ɱ����������property 
     */
    public java.math.BigDecimal getCostDiffOneAmount()
    {
        return getBigDecimal("costDiffOneAmount");
    }
    public void setCostDiffOneAmount(java.math.BigDecimal item)
    {
        setBigDecimal("costDiffOneAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's Ŀ��ɱ�ƫ����property 
     */
    public java.math.BigDecimal getTargetCostDiffOneRate()
    {
        return getBigDecimal("targetCostDiffOneRate");
    }
    public void setTargetCostDiffOneRate(java.math.BigDecimal item)
    {
        setBigDecimal("targetCostDiffOneRate", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ����property 
     */
    public java.math.BigDecimal getAdjustmentDiffAmount()
    {
        return getBigDecimal("adjustmentDiffAmount");
    }
    public void setAdjustmentDiffAmount(java.math.BigDecimal item)
    {
        setBigDecimal("adjustmentDiffAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ��̬�ɱ�С�ƣ������property 
     */
    public java.math.BigDecimal getDynamicCostTwoAmount()
    {
        return getBigDecimal("dynamicCostTwoAmount");
    }
    public void setDynamicCostTwoAmount(java.math.BigDecimal item)
    {
        setBigDecimal("dynamicCostTwoAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's �ɱ��������property 
     */
    public java.math.BigDecimal getCostDiffTwoAmount()
    {
        return getBigDecimal("costDiffTwoAmount");
    }
    public void setCostDiffTwoAmount(java.math.BigDecimal item)
    {
        setBigDecimal("costDiffTwoAmount", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's Ŀ��ɱ�ƫ���ʣ������property 
     */
    public java.math.BigDecimal getTargetCostDiffTwoRate()
    {
        return getBigDecimal("targetCostDiffTwoRate");
    }
    public void setTargetCostDiffTwoRate(java.math.BigDecimal item)
    {
        setBigDecimal("targetCostDiffTwoRate", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ����property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's �ۼ����property 
     */
    public java.math.BigDecimal getCumulativeCashOut()
    {
        return getBigDecimal("cumulativeCashOut");
    }
    public void setCumulativeCashOut(java.math.BigDecimal item)
    {
        setBigDecimal("cumulativeCashOut", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's �ۼ�ʵ��property 
     */
    public java.math.BigDecimal getCumulativeActuallyPaid()
    {
        return getBigDecimal("cumulativeActuallyPaid");
    }
    public void setCumulativeActuallyPaid(java.math.BigDecimal item)
    {
        setBigDecimal("cumulativeActuallyPaid", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ��ͬ�ҷ�property 
     */
    public String getContractParty()
    {
        return getString("contractParty");
    }
    public void setContractParty(String item)
    {
        setString("contractParty", item);
    }
    /**
     * Object:��̬�ɱ���ϸ���ݴ洢's ��ͬ����property 
     */
    public String getContractPartyC()
    {
        return getString("contractPartyC");
    }
    public void setContractPartyC(String item)
    {
        setString("contractPartyC", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("832495AA");
    }
}