package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractWithoutTextInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractWithoutTextInfo()
    {
        this("id");
    }
    protected AbstractContractWithoutTextInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ı���ͬ 's ������Ŀ property 
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
     * Object:���ı���ͬ's ǩԼ����property 
     */
    public java.util.Date getSignDate()
    {
        return getDate("signDate");
    }
    public void setSignDate(java.util.Date item)
    {
        setDate("signDate", item);
    }
    /**
     * Object: ���ı���ͬ 's �տλ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getReceiveUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("receiveUnit");
    }
    public void setReceiveUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("receiveUnit", item);
    }
    /**
     * Object: ���ı���ͬ 's ���㷽ʽ property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object: ���ı���ͬ 's �ұ� property 
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
     * Object:���ı���ͬ's ��������property 
     */
    public String getFiSettleNo()
    {
        return getString("fiSettleNo");
    }
    public void setFiSettleNo(String item)
    {
        setString("fiSettleNo", item);
    }
    /**
     * Object:���ı���ͬ's ���ɸ���ƻ�property 
     */
    public boolean isGenPaymentPlan()
    {
        return getBoolean("genPaymentPlan");
    }
    public void setGenPaymentPlan(boolean item)
    {
        setBoolean("genPaymentPlan", item);
    }
    /**
     * Object:���ı���ͬ's ���ɸ������뵥property 
     */
    public boolean isGenPaymentReque()
    {
        return getBoolean("genPaymentReque");
    }
    public void setGenPaymentReque(boolean item)
    {
        setBoolean("genPaymentReque", item);
    }
    /**
     * Object:���ı���ͬ's ���ɸ��property 
     */
    public boolean isGenPaymentBill()
    {
        return getBoolean("genPaymentBill");
    }
    public void setGenPaymentBill(boolean item)
    {
        setBoolean("genPaymentBill", item);
    }
    /**
     * Object:���ı���ͬ's ��������property 
     */
    public String getBank()
    {
        return getString("bank");
    }
    public void setBank(String item)
    {
        setString("bank", item);
    }
    /**
     * Object:���ı���ͬ's �����˺�property 
     */
    public String getBankAcct()
    {
        return getString("bankAcct");
    }
    public void setBankAcct(String item)
    {
        setString("bankAcct", item);
    }
    /**
     * Object:���ı���ͬ's �Ƿ�ɱ����property 
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
     * Object:���ı���ͬ's ������״̬property 
     */
    public com.kingdee.eas.fdc.contract.ConSplitExecStateEnum getConSplitExecState()
    {
        return com.kingdee.eas.fdc.contract.ConSplitExecStateEnum.getEnum(getString("conSplitExecState"));
    }
    public void setConSplitExecState(com.kingdee.eas.fdc.contract.ConSplitExecStateEnum item)
    {
		if (item != null) {
        setString("conSplitExecState", item.getValue());
		}
    }
    /**
     * Object:���ı���ͬ's �Ƿ���Ҫ����property 
     */
    public boolean isIsNeedPaid()
    {
        return getBoolean("isNeedPaid");
    }
    public void setIsNeedPaid(boolean item)
    {
        setBoolean("isNeedPaid", item);
    }
    /**
     * Object:���ı���ͬ's ���踶��ԭ��property 
     */
    public String getNoPaidReason()
    {
        return getString("noPaidReason");
    }
    public void setNoPaidReason(String item)
    {
        setString("noPaidReason", item);
    }
    /**
     * Object:���ı���ͬ's ������Դ��ʽproperty 
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
     * Object: ���ı���ͬ 's ������Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("account");
    }
    public void setAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("account", item);
    }
    /**
     * Object:���ı���ͬ's ���״̬property 
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
     * Object: ���ı���ͬ 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object: ���ı���ͬ 's ��ͬ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo getConChargeType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo)get("conChargeType");
    }
    public void setConChargeType(com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo item)
    {
        put("conChargeType", item);
    }
    /**
     * Object: ���ı���ͬ 's ��ͬ������Ϣ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBaseDataInfo getContractBaseData()
    {
        return (com.kingdee.eas.fdc.contract.ContractBaseDataInfo)get("contractBaseData");
    }
    public void setContractBaseData(com.kingdee.eas.fdc.contract.ContractBaseDataInfo item)
    {
        put("contractBaseData", item);
    }
    /**
     * Object:���ı���ͬ's ��Ʊ��property 
     */
    public String getInvoiceNumber()
    {
        return getString("invoiceNumber");
    }
    public void setInvoiceNumber(String item)
    {
        setString("invoiceNumber", item);
    }
    /**
     * Object:���ı���ͬ's ��Ʊ���property 
     */
    public java.math.BigDecimal getInvoiceAmt()
    {
        return getBigDecimal("invoiceAmt");
    }
    public void setInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmt", item);
    }
    /**
     * Object:���ı���ͬ's �ۼƷ�Ʊ���property 
     */
    public double getAllInvoiceAmt()
    {
        return getDouble("allInvoiceAmt");
    }
    public void setAllInvoiceAmt(double item)
    {
        setDouble("allInvoiceAmt", item);
    }
    /**
     * Object:���ı���ͬ's ��Ʊ����property 
     */
    public java.util.Date getInvoiceDate()
    {
        return getDate("invoiceDate");
    }
    public void setInvoiceDate(java.util.Date item)
    {
        setDate("invoiceDate", item);
    }
    /**
     * Object: ���ı���ͬ 's �ÿ�� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseDepartment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useDepartment");
    }
    public void setUseDepartment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useDepartment", item);
    }
    /**
     * Object: ���ı���ͬ 's ��ܺ�Լ property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgrammingContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programmingContract");
    }
    public void setProgrammingContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programmingContract", item);
    }
    /**
     * Object: ���ı���ͬ 's ��ͬ�¶ȹ����ƻ��޺�ͬ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo getFdcDepConPlan()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo)get("fdcDepConPlan");
    }
    public void setFdcDepConPlan(com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo item)
    {
        put("fdcDepConPlan", item);
    }
    /**
     * Object:���ı���ͬ's �Ƿ���OA����property 
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
     * Object:���ı���ͬ's oa����ģ��idproperty 
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
     * Object:���ı���ͬ's oa����idproperty 
     */
    public String getOABillID()
    {
        return getString("OABillID");
    }
    public void setOABillID(String item)
    {
        setString("OABillID", item);
    }
    /**
     * Object:���ı���ͬ's oa�������̱��property 
     */
    public String getOAWorkFlowNumber()
    {
        return getString("OAWorkFlowNumber");
    }
    public void setOAWorkFlowNumber(String item)
    {
        setString("OAWorkFlowNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3D9A5388");
    }
}