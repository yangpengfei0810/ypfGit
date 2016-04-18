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
     * Object: 动态成本明细数据存储 's 表头 property 
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
     * Object:动态成本明细数据存储's 框架合约IDproperty 
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
     * Object:动态成本明细数据存储's 合同IDproperty 
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
     * Object:动态成本明细数据存储's 规划金额property 
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
     * Object:动态成本明细数据存储's 状态property 
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
     * Object:动态成本明细数据存储's 签约金额property 
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
     * Object:动态成本明细数据存储's 实际承包金额property 
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
     * Object:动态成本明细数据存储's 暂列金额property 
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
     * Object:动态成本明细数据存储's 变更金额property 
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
     * Object:动态成本明细数据存储's 结算金额property 
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
     * Object:动态成本明细数据存储's 最新造价property 
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
     * Object:动态成本明细数据存储's 规划余额property 
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
     * Object:动态成本明细数据存储's 尚需支出property 
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
     * Object:动态成本明细数据存储's 动态成本小计（不含调差）property 
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
     * Object:动态成本明细数据存储's 成本差额（不含调差）property 
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
     * Object:动态成本明细数据存储's 目标成本偏差率property 
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
     * Object:动态成本明细数据存储's 调差property 
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
     * Object:动态成本明细数据存储's 动态成本小计（含调差）property 
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
     * Object:动态成本明细数据存储's 成本差额（含调差）property 
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
     * Object:动态成本明细数据存储's 目标成本偏差率（含调差）property 
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
     * Object:动态成本明细数据存储's 备注property 
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
     * Object:动态成本明细数据存储's 级次property 
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
     * Object:动态成本明细数据存储's 累计请款property 
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
     * Object:动态成本明细数据存储's 累计实付property 
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
     * Object:动态成本明细数据存储's 合同乙方property 
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
     * Object:动态成本明细数据存储's 合同丙方property 
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