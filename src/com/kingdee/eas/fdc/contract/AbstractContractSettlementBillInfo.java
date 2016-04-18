package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractSettlementBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractSettlementBillInfo()
    {
        this("id");
    }
    protected AbstractContractSettlementBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ͬ���㵥's �Ƿ�����ƾ֤property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object:��ͬ���㵥's �������property 
     */
    public java.math.BigDecimal getSettlePrice()
    {
        return getBigDecimal("settlePrice");
    }
    public void setSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("settlePrice", item);
    }
    /**
     * Object:��ͬ���㵥's �������property 
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
     * Object:��ͬ���㵥's ȡ�ѱ�׼property 
     */
    public String getGetFeeCriteria()
    {
        return getString("getFeeCriteria");
    }
    public void setGetFeeCriteria(String item)
    {
        setString("getFeeCriteria", item);
    }
    /**
     * Object:��ͬ���㵥's ��λ���property 
     */
    public java.math.BigDecimal getUnitPrice()
    {
        return getBigDecimal("unitPrice");
    }
    public void setUnitPrice(java.math.BigDecimal item)
    {
        setBigDecimal("unitPrice", item);
    }
    /**
     * Object:��ͬ���㵥's ��Ϣ��property 
     */
    public String getInfoPrice()
    {
        return getString("infoPrice");
    }
    public void setInfoPrice(String item)
    {
        setString("infoPrice", item);
    }
    /**
     * Object:��ͬ���㵥's �ʱ���property 
     */
    public java.math.BigDecimal getQualityGuarante()
    {
        return getBigDecimal("qualityGuarante");
    }
    public void setQualityGuarante(java.math.BigDecimal item)
    {
        setBigDecimal("qualityGuarante", item);
    }
    /**
     * Object:��ͬ���㵥's �Ƿ����ս���property 
     */
    public com.kingdee.eas.base.commonquery.BooleanEnum getIsFinalSettle()
    {
        return com.kingdee.eas.base.commonquery.BooleanEnum.getEnum(getInt("isFinalSettle"));
    }
    public void setIsFinalSettle(com.kingdee.eas.base.commonquery.BooleanEnum item)
    {
		if (item != null) {
        setInt("isFinalSettle", item.getValue());
		}
    }
    /**
     * Object: ��ͬ���㵥 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object: ��ͬ���㵥 's ƾ֤ property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("voucher");
    }
    public void setVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("voucher", item);
    }
    /**
     * Object:��ͬ���㵥's ��ͬ����property 
     */
    public String getContractBillNumber()
    {
        return getString("contractBillNumber");
    }
    public void setContractBillNumber(String item)
    {
        setString("contractBillNumber", item);
    }
    /**
     * Object:��ͬ���㵥's ���޽����property 
     */
    public java.math.BigDecimal getQualityGuaranteRate()
    {
        return getBigDecimal("qualityGuaranteRate");
    }
    public void setQualityGuaranteRate(java.math.BigDecimal item)
    {
        setBigDecimal("qualityGuaranteRate", item);
    }
    /**
     * Object:��ͬ���㵥's ��������property 
     */
    public String getQualityTime()
    {
        return getString("qualityTime");
    }
    public void setQualityTime(String item)
    {
        setString("qualityTime", item);
    }
    /**
     * Object: ��ͬ���㵥 's  property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: ��ͬ���㵥 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:��ͬ���㵥's ����property 
     */
    public java.math.BigDecimal getExchangeRate()
    {
        return getBigDecimal("exchangeRate");
    }
    public void setExchangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("exchangeRate", item);
    }
    /**
     * Object:��ͬ���㵥's ������Դ��ʽproperty 
     */
    public com.kingdee.eas.fdc.basedata.SourceTypeEnum getSourceType()
    {
        return com.kingdee.eas.fdc.basedata.SourceTypeEnum.getEnum(getInt("sourceType"));
    }
    public void setSourceType(com.kingdee.eas.fdc.basedata.SourceTypeEnum item)
    {
		if (item != null) {
        setInt("sourceType", item.getValue());
		}
    }
    /**
     * Object:��ͬ���㵥's nullproperty 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitStateEnum getSplitState()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitStateEnum.getEnum(getString("splitState"));
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
		if (item != null) {
        setString("splitState", item.getValue());
		}
    }
    /**
     * Object:��ͬ���㵥's �Ƿ���붯̬�ɱ�property 
     */
    public boolean isIsCostSplit()
    {
        return getBoolean("isCostSplit");
    }
    public void setIsCostSplit(boolean item)
    {
        setBoolean("isCostSplit", item);
    }
    /**
     * Object:��ͬ���㵥's �ۼƽ���ԭ��property 
     */
    public java.math.BigDecimal getTotalOriginalAmount()
    {
        return getBigDecimal("totalOriginalAmount");
    }
    public void setTotalOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalOriginalAmount", item);
    }
    /**
     * Object:��ͬ���㵥's �ۼƽ��㱾λ��property 
     */
    public java.math.BigDecimal getTotalSettlePrice()
    {
        return getBigDecimal("totalSettlePrice");
    }
    public void setTotalSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("totalSettlePrice", item);
    }
    /**
     * Object:��ͬ���㵥's ��ǰ�����property 
     */
    public java.math.BigDecimal getCurOriginalAmount()
    {
        return getBigDecimal("curOriginalAmount");
    }
    public void setCurOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("curOriginalAmount", item);
    }
    /**
     * Object:��ͬ���㵥's ��ǰ�����property 
     */
    public java.math.BigDecimal getCurSettlePrice()
    {
        return getBigDecimal("curSettlePrice");
    }
    public void setCurSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("curSettlePrice", item);
    }
    /**
     * Object:��ͬ���㵥's ��ͬ�ѽ���property 
     */
    public boolean isIsSettled()
    {
        return getBoolean("isSettled");
    }
    public void setIsSettled(boolean item)
    {
        setBoolean("isSettled", item);
    }
    /**
     * Object:��ͬ���㵥's ���޽�property 
     */
    public java.math.BigDecimal getGuaranteAmt()
    {
        return getBigDecimal("guaranteAmt");
    }
    public void setGuaranteAmt(java.math.BigDecimal item)
    {
        setBigDecimal("guaranteAmt", item);
    }
    /**
     * Object:��ͬ���㵥's ʩ���������property 
     */
    public java.math.BigDecimal getConstructPrice()
    {
        return getBigDecimal("constructPrice");
    }
    public void setConstructPrice(java.math.BigDecimal item)
    {
        setBigDecimal("constructPrice", item);
    }
    /**
     * Object:��ͬ���㵥's �鵵��property 
     */
    public String getOwnID()
    {
        return getString("ownID");
    }
    public void setOwnID(String item)
    {
        setString("ownID", item);
    }
    /**
     * Object:��ͬ���㵥's �Ƿ���OA����property 
     */
    public boolean isIsOAAudit()
    {
        return getBoolean("isOAAudit");
    }
    public void setIsOAAudit(boolean item)
    {
        setBoolean("isOAAudit", item);
    }
    /**
     * Object:��ͬ���㵥's oa����ģ��idproperty 
     */
    public String getBillTempletID()
    {
        return getString("billTempletID");
    }
    public void setBillTempletID(String item)
    {
        setString("billTempletID", item);
    }
    /**
     * Object:��ͬ���㵥's oa����idproperty 
     */
    public String getOABillID()
    {
        return getString("OABillID");
    }
    public void setOABillID(String item)
    {
        setString("OABillID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1D604E7D");
    }
}