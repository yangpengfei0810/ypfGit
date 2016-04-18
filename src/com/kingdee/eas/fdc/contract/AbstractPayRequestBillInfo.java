package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayRequestBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPayRequestBillInfo()
    {
        this("id");
    }
    protected AbstractPayRequestBillInfo(String pkField)
    {
        super(pkField);
        put("confirmEntry", new com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection());
        put("acctPays", new com.kingdee.eas.fdc.finance.PayRequestAcctPayCollection());
        put("entrys", new com.kingdee.eas.fdc.contract.PayRequestBillEntryCollection());
    }
    /**
     * Object: 付款申请单 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillEntryCollection)get("entrys");
    }
    /**
     * Object: 付款申请单 's 工程项目 property 
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
     * Object: 付款申请单 's 用款部门 property 
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
     * Object:付款申请单's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("fivouchered", item);
    }
    /**
     * Object:付款申请单's 付款日期property 
     */
    public java.util.Date getPayDate()
    {
        return getDate("payDate");
    }
    public void setPayDate(java.util.Date item)
    {
        setDate("payDate", item);
    }
    /**
     * Object:付款申请单's 款项说明property 
     */
    public String getMoneyDesc()
    {
        return getString("moneyDesc");
    }
    public void setMoneyDesc(String item)
    {
        setString("moneyDesc", item);
    }
    /**
     * Object: 付款申请单 's 收款单位全称 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: 付款申请单 's 实付收款单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getRealSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("realSupplier");
    }
    public void setRealSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("realSupplier", item);
    }
    /**
     * Object:付款申请单's 收款银行property 
     */
    public String getRecBank()
    {
        return getString("recBank");
    }
    public void setRecBank(String item)
    {
        setString("recBank", item);
    }
    /**
     * Object:付款申请单's 收款账号property 
     */
    public String getRecAccount()
    {
        return getString("recAccount");
    }
    public void setRecAccount(String item)
    {
        setString("recAccount", item);
    }
    /**
     * Object:付款申请单's 合同编号property 
     */
    public String getContractNo()
    {
        return getString("contractNo");
    }
    public void setContractNo(String item)
    {
        setString("contractNo", item);
    }
    /**
     * Object:付款申请单's 附件property 
     */
    public int getAttachment()
    {
        return getInt("attachment");
    }
    public void setAttachment(int item)
    {
        setInt("attachment", item);
    }
    /**
     * Object: 付款申请单 's 付款类型 property 
     */
    public com.kingdee.eas.fdc.basedata.PaymentTypeInfo getPaymentType()
    {
        return (com.kingdee.eas.fdc.basedata.PaymentTypeInfo)get("paymentType");
    }
    public void setPaymentType(com.kingdee.eas.fdc.basedata.PaymentTypeInfo item)
    {
        put("paymentType", item);
    }
    /**
     * Object: 付款申请单 's 原币币别 property 
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
     * Object: 付款申请单 's 本位币币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getLocalCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("localCurrency");
    }
    public void setLocalCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("localCurrency", item);
    }
    /**
     * Object:付款申请单's 汇率property 
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
     * Object: 付款申请单 's 结算方式 property 
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
     * Object:付款申请单's 合同名称property 
     */
    public String getContractName()
    {
        return getString("contractName");
    }
    public void setContractName(String item)
    {
        setString("contractName", item);
    }
    /**
     * Object:付款申请单's 合同造价property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("contractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractPrice", item);
    }
    /**
     * Object:付款申请单's 最新造价property 
     */
    public java.math.BigDecimal getLatestPrice()
    {
        return getBigDecimal("latestPrice");
    }
    public void setLatestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("latestPrice", item);
    }
    /**
     * Object:付款申请单's 增加工程款（本期发生）property 
     */
    public java.math.BigDecimal getAddProjectAmt()
    {
        return getBigDecimal("addProjectAmt");
    }
    public void setAddProjectAmt(java.math.BigDecimal item)
    {
        setBigDecimal("addProjectAmt", item);
    }
    /**
     * Object:付款申请单's 变更签证金额property 
     */
    public java.math.BigDecimal getChangeAmt()
    {
        return getBigDecimal("changeAmt");
    }
    public void setChangeAmt(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmt", item);
    }
    /**
     * Object:付款申请单's 本申请单已付金额property 
     */
    public java.math.BigDecimal getPayedAmt()
    {
        return getBigDecimal("payedAmt");
    }
    public void setPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payedAmt", item);
    }
    /**
     * Object:付款申请单's 应扣甲供材料款（本期发生）property 
     */
    public java.math.BigDecimal getPayPartAMatlAmt()
    {
        return getBigDecimal("payPartAMatlAmt");
    }
    public void setPayPartAMatlAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payPartAMatlAmt", item);
    }
    /**
     * Object:付款申请单's 付款次数property 
     */
    public int getPayTimes()
    {
        return getInt("payTimes");
    }
    public void setPayTimes(int item)
    {
        setInt("payTimes", item);
    }
    /**
     * Object:付款申请单's 合同内工程款（本期发生）property 
     */
    public java.math.BigDecimal getProjectPriceInContract()
    {
        return getBigDecimal("projectPriceInContract");
    }
    public void setProjectPriceInContract(java.math.BigDecimal item)
    {
        setBigDecimal("projectPriceInContract", item);
    }
    /**
     * Object:付款申请单's 进度款property 
     */
    public java.math.BigDecimal getScheduleAmt()
    {
        return getBigDecimal("scheduleAmt");
    }
    public void setScheduleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("scheduleAmt", item);
    }
    /**
     * Object:付款申请单's 结算金额property 
     */
    public java.math.BigDecimal getSettleAmt()
    {
        return getBigDecimal("settleAmt");
    }
    public void setSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmt", item);
    }
    /**
     * Object:付款申请单's 紧急程度property 
     */
    public com.kingdee.eas.fdc.contract.UrgentDegreeEnum getUrgentDegree()
    {
        return com.kingdee.eas.fdc.contract.UrgentDegreeEnum.getEnum(getInt("urgentDegree"));
    }
    public void setUrgentDegree(com.kingdee.eas.fdc.contract.UrgentDegreeEnum item)
    {
		if (item != null) {
        setInt("urgentDegree", item.getValue());
		}
    }
    /**
     * Object:付款申请单's 大写金额property 
     */
    public String getCapitalAmount()
    {
        return getString("capitalAmount");
    }
    public void setCapitalAmount(String item)
    {
        setString("capitalAmount", item);
    }
    /**
     * Object:付款申请单's 本期计划付款property 
     */
    public java.math.BigDecimal getCurPlannedPayment()
    {
        return getBigDecimal("curPlannedPayment");
    }
    public void setCurPlannedPayment(java.math.BigDecimal item)
    {
        setBigDecimal("curPlannedPayment", item);
    }
    /**
     * Object:付款申请单's 本期欠付款property 
     */
    public java.math.BigDecimal getCurBackPay()
    {
        return getBigDecimal("curBackPay");
    }
    public void setCurBackPay(java.math.BigDecimal item)
    {
        setBigDecimal("curBackPay", item);
    }
    /**
     * Object:付款申请单's 付款计划property 
     */
    public java.math.BigDecimal getPaymentPlan()
    {
        return getBigDecimal("paymentPlan");
    }
    public void setPaymentPlan(java.math.BigDecimal item)
    {
        setBigDecimal("paymentPlan", item);
    }
    /**
     * Object:付款申请单's 本次申请%property 
     */
    public java.math.BigDecimal getCurReqPercent()
    {
        return getBigDecimal("curReqPercent");
    }
    public void setCurReqPercent(java.math.BigDecimal item)
    {
        setBigDecimal("curReqPercent", item);
    }
    /**
     * Object:付款申请单's 累计申请%property 
     */
    public java.math.BigDecimal getAllReqPercent()
    {
        return getBigDecimal("allReqPercent");
    }
    public void setAllReqPercent(java.math.BigDecimal item)
    {
        setBigDecimal("allReqPercent", item);
    }
    /**
     * Object:付款申请单's 形象进度property 
     */
    public java.math.BigDecimal getImageSchedule()
    {
        return getBigDecimal("imageSchedule");
    }
    public void setImageSchedule(java.math.BigDecimal item)
    {
        setBigDecimal("imageSchedule", item);
    }
    /**
     * Object:付款申请单's 实付款本期发生额(本期申请原币)property 
     */
    public java.math.BigDecimal getCurPaid()
    {
        return getBigDecimal("curPaid");
    }
    public void setCurPaid(java.math.BigDecimal item)
    {
        setBigDecimal("curPaid", item);
    }
    /**
     * Object:付款申请单's 合同内工程累计申请本币property 
     */
    public java.math.BigDecimal getPrjAllReqAmt()
    {
        return getBigDecimal("prjAllReqAmt");
    }
    public void setPrjAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("prjAllReqAmt", item);
    }
    /**
     * Object:付款申请单's 增加工程款累计申请property 
     */
    public java.math.BigDecimal getAddPrjAllReqAmt()
    {
        return getBigDecimal("addPrjAllReqAmt");
    }
    public void setAddPrjAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("addPrjAllReqAmt", item);
    }
    /**
     * Object:付款申请单's 甲供材累计申请款property 
     */
    public java.math.BigDecimal getPayPartAMatlAllReqAmt()
    {
        return getBigDecimal("payPartAMatlAllReqAmt");
    }
    public void setPayPartAMatlAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payPartAMatlAllReqAmt", item);
    }
    /**
     * Object:付款申请单's 合同内工程款上期累计申请property 
     */
    public java.math.BigDecimal getLstPrjAllReqAmt()
    {
        return getBigDecimal("lstPrjAllReqAmt");
    }
    public void setLstPrjAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstPrjAllReqAmt", item);
    }
    /**
     * Object:付款申请单's 增加工程款上期累计property 
     */
    public java.math.BigDecimal getLstAddPrjAllReqAmt()
    {
        return getBigDecimal("lstAddPrjAllReqAmt");
    }
    public void setLstAddPrjAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstAddPrjAllReqAmt", item);
    }
    /**
     * Object:付款申请单's 甲供材上期累计申请property 
     */
    public java.math.BigDecimal getLstAMatlAllReqAmt()
    {
        return getBigDecimal("lstAMatlAllReqAmt");
    }
    public void setLstAMatlAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstAMatlAllReqAmt", item);
    }
    /**
     * Object:付款申请单's 合同Idproperty 
     */
    public String getContractId()
    {
        return getString("contractId");
    }
    public void setContractId(String item)
    {
        setString("contractId", item);
    }
    /**
     * Object:付款申请单's 是否付清property 
     */
    public boolean isHasPayoff()
    {
        return getBoolean("hasPayoff");
    }
    public void setHasPayoff(boolean item)
    {
        setBoolean("hasPayoff", item);
    }
    /**
     * Object:付款申请单's 已关闭property 
     */
    public boolean isHasClosed()
    {
        return getBoolean("hasClosed");
    }
    public void setHasClosed(boolean item)
    {
        setBoolean("hasClosed", item);
    }
    /**
     * Object:付款申请单's 上期合同内累计实付property 
     */
    public java.math.BigDecimal getLstPrjAllPaidAmt()
    {
        return getBigDecimal("lstPrjAllPaidAmt");
    }
    public void setLstPrjAllPaidAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstPrjAllPaidAmt", item);
    }
    /**
     * Object:付款申请单's 增加工程款上期累计实付property 
     */
    public java.math.BigDecimal getLstAddPrjAllPaidAmt()
    {
        return getBigDecimal("lstAddPrjAllPaidAmt");
    }
    public void setLstAddPrjAllPaidAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstAddPrjAllPaidAmt", item);
    }
    /**
     * Object:付款申请单's 甲供材上期累计实付property 
     */
    public java.math.BigDecimal getLstAMatlAllPaidAmt()
    {
        return getBigDecimal("lstAMatlAllPaidAmt");
    }
    public void setLstAMatlAllPaidAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstAMatlAllPaidAmt", item);
    }
    /**
     * Object:付款申请单's 付款比例property 
     */
    public java.math.BigDecimal getPaymentProportion()
    {
        return getBigDecimal("paymentProportion");
    }
    public void setPaymentProportion(java.math.BigDecimal item)
    {
        setBigDecimal("paymentProportion", item);
    }
    /**
     * Object:付款申请单's 本期成本金额property 
     */
    public java.math.BigDecimal getCompletePrjAmt()
    {
        return getBigDecimal("completePrjAmt");
    }
    public void setCompletePrjAmt(java.math.BigDecimal item)
    {
        setBigDecimal("completePrjAmt", item);
    }
    /**
     * Object:付款申请单's 已完工工程量金额property 
     */
    public java.math.BigDecimal getCostAmount()
    {
        return getBigDecimal("costAmount");
    }
    public void setCostAmount(java.math.BigDecimal item)
    {
        setBigDecimal("costAmount", item);
    }
    /**
     * Object:付款申请单's 保修金金额property 
     */
    public java.math.BigDecimal getGrtAmount()
    {
        return getBigDecimal("grtAmount");
    }
    public void setGrtAmount(java.math.BigDecimal item)
    {
        setBigDecimal("grtAmount", item);
    }
    /**
     * Object:付款申请单's 是否提交付款property 
     */
    public boolean isIsPay()
    {
        return getBoolean("isPay");
    }
    public void setIsPay(boolean item)
    {
        setBoolean("isPay", item);
    }
    /**
     * Object:付款申请单's 单据来源方式property 
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
     * Object:付款申请单's 同城异地property 
     */
    public com.kingdee.eas.fi.cas.DifPlaceEnum getIsDifferPlace()
    {
        return com.kingdee.eas.fi.cas.DifPlaceEnum.getEnum(getInt("isDifferPlace"));
    }
    public void setIsDifferPlace(com.kingdee.eas.fi.cas.DifPlaceEnum item)
    {
		if (item != null) {
        setInt("isDifferPlace", item.getValue());
		}
    }
    /**
     * Object:付款申请单's 用途property 
     */
    public String getUsage()
    {
        return getString("usage");
    }
    public void setUsage(String item)
    {
        setString("usage", item);
    }
    /**
     * Object: 付款申请单 's 付款申请单拆分 property 
     */
    public com.kingdee.eas.fdc.finance.PayRequestSplitInfo getPayRequestSplit()
    {
        return (com.kingdee.eas.fdc.finance.PayRequestSplitInfo)get("payRequestSplit");
    }
    public void setPayRequestSplit(com.kingdee.eas.fdc.finance.PayRequestSplitInfo item)
    {
        put("payRequestSplit", item);
    }
    /**
     * Object:付款申请单's 累计结算款property 
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
     * Object:付款申请单's 实际付款数原币property 
     */
    public java.math.BigDecimal getActPaiedAmount()
    {
        return getBigDecimal("actPaiedAmount");
    }
    public void setActPaiedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actPaiedAmount", item);
    }
    /**
     * Object: 付款申请单 's 合同付款计划 property 
     */
    public com.kingdee.eas.fdc.finance.ContractPayPlanInfo getConPayplan()
    {
        return (com.kingdee.eas.fdc.finance.ContractPayPlanInfo)get("conPayplan");
    }
    public void setConPayplan(com.kingdee.eas.fdc.finance.ContractPayPlanInfo item)
    {
        put("conPayplan", item);
    }
    /**
     * Object:付款申请单's 来源对象property 
     */
    public String getSource()
    {
        return getString("source");
    }
    public void setSource(String item)
    {
        setString("source", item);
    }
    /**
     * Object: 付款申请单 's 成本科目付款分录 property 
     */
    public com.kingdee.eas.fdc.finance.PayRequestAcctPayCollection getAcctPays()
    {
        return (com.kingdee.eas.fdc.finance.PayRequestAcctPayCollection)get("acctPays");
    }
    /**
     * Object:付款申请单's 合同内工程款（本期发生原币）property 
     */
    public java.math.BigDecimal getProjectPriceInContractOri()
    {
        return getBigDecimal("projectPriceInContractOri");
    }
    public void setProjectPriceInContractOri(java.math.BigDecimal item)
    {
        setBigDecimal("projectPriceInContractOri", item);
    }
    /**
     * Object:付款申请单's 应扣甲供材料款（本期发生原币） property 
     */
    public java.math.BigDecimal getPayPartAMatlOriAmt()
    {
        return getBigDecimal("payPartAMatlOriAmt");
    }
    public void setPayPartAMatlOriAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payPartAMatlOriAmt", item);
    }
    /**
     * Object:付款申请单's 奖励原币property 
     */
    public java.math.BigDecimal getGuerdonOriginalAmt()
    {
        return getBigDecimal("guerdonOriginalAmt");
    }
    public void setGuerdonOriginalAmt(java.math.BigDecimal item)
    {
        setBigDecimal("guerdonOriginalAmt", item);
    }
    /**
     * Object:付款申请单's 奖励原币property 
     */
    public java.math.BigDecimal getCompensationOriginalAmt()
    {
        return getBigDecimal("compensationOriginalAmt");
    }
    public void setCompensationOriginalAmt(java.math.BigDecimal item)
    {
        setBigDecimal("compensationOriginalAmt", item);
    }
    /**
     * Object:付款申请单's 实付款本期发生额原币property 
     */
    public java.math.BigDecimal getCurPaidOriginal()
    {
        return getBigDecimal("curPaidOriginal");
    }
    public void setCurPaidOriginal(java.math.BigDecimal item)
    {
        setBigDecimal("curPaidOriginal", item);
    }
    /**
     * Object: 付款申请单 's 合同基本信息 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBaseDataInfo getContractBase()
    {
        return (com.kingdee.eas.fdc.contract.ContractBaseDataInfo)get("contractBase");
    }
    public void setContractBase(com.kingdee.eas.fdc.contract.ContractBaseDataInfo item)
    {
        put("contractBase", item);
    }
    /**
     * Object:付款申请单's 发票号 property 
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
     * Object:付款申请单's 发票金额property 
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
     * Object:付款申请单's 发票金额原币property 
     */
    public java.math.BigDecimal getInvoiceOriAmt()
    {
        return getBigDecimal("invoiceOriAmt");
    }
    public void setInvoiceOriAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceOriAmt", item);
    }
    /**
     * Object:付款申请单's 累计发票金额property 
     */
    public java.math.BigDecimal getAllInvoiceAmt()
    {
        return getBigDecimal("allInvoiceAmt");
    }
    public void setAllInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("allInvoiceAmt", item);
    }
    /**
     * Object:付款申请单's 累计发票金额原币property 
     */
    public java.math.BigDecimal getAllInvoiceOriAmt()
    {
        return getBigDecimal("allInvoiceOriAmt");
    }
    public void setAllInvoiceOriAmt(java.math.BigDecimal item)
    {
        setBigDecimal("allInvoiceOriAmt", item);
    }
    /**
     * Object:付款申请单's 发票日期property 
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
     * Object:付款申请单's 实际付款数本币property 
     */
    public java.math.BigDecimal getActPaiedLocAmount()
    {
        return getBigDecimal("actPaiedLocAmount");
    }
    public void setActPaiedLocAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actPaiedLocAmount", item);
    }
    /**
     * Object: 付款申请单 's 工程付款情况表 property 
     */
    public com.kingdee.eas.fdc.contract.PayReqPrjPayEntryInfo getPrjPayEntry()
    {
        return (com.kingdee.eas.fdc.contract.PayReqPrjPayEntryInfo)get("prjPayEntry");
    }
    public void setPrjPayEntry(com.kingdee.eas.fdc.contract.PayReqPrjPayEntryInfo item)
    {
        put("prjPayEntry", item);
    }
    /**
     * Object:付款申请单's 形象进度property 
     */
    public String getProcess()
    {
        return getString("process");
    }
    public void setProcess(String item)
    {
        setString("process", item);
    }
    /**
     * Object: 付款申请单 's 材料确认单分录 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection getConfirmEntry()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection)get("confirmEntry");
    }
    /**
     * Object: 付款申请单 's 计划项目，存在 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo getPlanHasCon()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo)get("planHasCon");
    }
    public void setPlanHasCon(com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo item)
    {
        put("planHasCon", item);
    }
    /**
     * Object: 付款申请单 's 计划项目，不存在 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo getPlanUnCon()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo)get("planUnCon");
    }
    public void setPlanUnCon(com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo item)
    {
        put("planUnCon", item);
    }
    /**
     * Object:付款申请单's 是否走OA审批property 
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
     * Object:付款申请单's oa单据模板idproperty 
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
     * Object:付款申请单's oa单据idproperty 
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
     * Object:付款申请单's 其它付款单property 
     */
    public boolean isIsOtherPay()
    {
        return getBoolean("isOtherPay");
    }
    public void setIsOtherPay(boolean item)
    {
        setBoolean("isOtherPay", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C9A5A869");
    }
}