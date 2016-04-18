package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOtherPaymentInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractOtherPaymentInfo()
    {
        this("id");
    }
    protected AbstractOtherPaymentInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:其它付款单's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: 其它付款单 's 组织 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getOrganize()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("organize");
    }
    public void setOrganize(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("organize", item);
    }
    /**
     * Object: 其它付款单 's 工程项目 property 
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
     * Object: 其它付款单 's 用款部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getWithMSector()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("withMSector");
    }
    public void setWithMSector(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("withMSector", item);
    }
    /**
     * Object: 其它付款单 's 申请期间 property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getApplyPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("applyPeriod");
    }
    public void setApplyPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("applyPeriod", item);
    }
    /**
     * Object:其它付款单's 付款日期property 
     */
    public java.util.Date getPaymentDate()
    {
        return getDate("paymentDate");
    }
    public void setPaymentDate(java.util.Date item)
    {
        setDate("paymentDate", item);
    }
    /**
     * Object: 其它付款单 's 收款单位全称 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getPayee()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("payee");
    }
    public void setPayee(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("payee", item);
    }
    /**
     * Object:其它付款单's 收款银行property 
     */
    public String getReceiptBank()
    {
        return getString("receiptBank");
    }
    public void setReceiptBank(String item)
    {
        setString("receiptBank", item);
    }
    /**
     * Object: 其它付款单 's 付款类型 property 
     */
    public com.kingdee.eas.fdc.basedata.PaymentTypeInfo getPayType()
    {
        return (com.kingdee.eas.fdc.basedata.PaymentTypeInfo)get("payType");
    }
    public void setPayType(com.kingdee.eas.fdc.basedata.PaymentTypeInfo item)
    {
        put("payType", item);
    }
    /**
     * Object: 其它付款单 's 实际收款单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getRealityPayee()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("realityPayee");
    }
    public void setRealityPayee(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("realityPayee", item);
    }
    /**
     * Object:其它付款单's 收款帐号property 
     */
    public String getReceiptNumber()
    {
        return getString("receiptNumber");
    }
    public void setReceiptNumber(String item)
    {
        setString("receiptNumber", item);
    }
    /**
     * Object:其它付款单's 申请金额（原币）property 
     */
    public java.math.BigDecimal getApplyAMT()
    {
        return getBigDecimal("applyAMT");
    }
    public void setApplyAMT(java.math.BigDecimal item)
    {
        setBigDecimal("applyAMT", item);
    }
    /**
     * Object:其它付款单's 申请金额（本币）property 
     */
    public java.math.BigDecimal getApplyAMTHC()
    {
        return getBigDecimal("applyAMTHC");
    }
    public void setApplyAMTHC(java.math.BigDecimal item)
    {
        setBigDecimal("applyAMTHC", item);
    }
    /**
     * Object: 其它付款单 's 币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("Currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("Currency", item);
    }
    /**
     * Object:其它付款单's 汇率property 
     */
    public java.math.BigDecimal getParities()
    {
        return getBigDecimal("parities");
    }
    public void setParities(java.math.BigDecimal item)
    {
        setBigDecimal("parities", item);
    }
    /**
     * Object:其它付款单's 发票号property 
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
     * Object:其它付款单's 发票金额property 
     */
    public java.math.BigDecimal getInvoiceAMT()
    {
        return getBigDecimal("invoiceAMT");
    }
    public void setInvoiceAMT(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAMT", item);
    }
    /**
     * Object:其它付款单's 备注property 
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
     * Object:其它付款单's 进度款付款比例property 
     */
    public java.math.BigDecimal getPaymentProportion()
    {
        return getBigDecimal("PaymentProportion");
    }
    public void setPaymentProportion(java.math.BigDecimal item)
    {
        setBigDecimal("PaymentProportion", item);
    }
    /**
     * Object:其它付款单's 本期完工工程量property 
     */
    public java.math.BigDecimal getCompletedQuantities()
    {
        return getBigDecimal("completedQuantities");
    }
    public void setCompletedQuantities(java.math.BigDecimal item)
    {
        setBigDecimal("completedQuantities", item);
    }
    /**
     * Object: 其它付款单 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: 其它付款单 's 付款申请单 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getPayRequest()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("payRequest");
    }
    public void setPayRequest(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("payRequest", item);
    }
    /**
     * Object:其它付款单's 单据名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:其它付款单's 单据状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getBillstates()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("billstates"));
    }
    public void setBillstates(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("billstates", item.getValue());
		}
    }
    /**
     * Object:其它付款单's 审核日期property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:其它付款单's 是否走OA审批property 
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
     * Object:其它付款单's OA流程模版IDproperty 
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
     * Object:其它付款单's OA单据IDproperty 
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
        return new BOSObjectType("B58F9F65");
    }
}