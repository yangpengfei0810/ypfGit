package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import chrriis.common.Filter;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.UrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitFactory;
import com.kingdee.eas.fdc.finance.OtherPaymentCollection;
import com.kingdee.eas.fdc.finance.OtherPaymentFactory;
import com.kingdee.eas.fdc.finance.OtherPaymentInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.st.common.app.STServerUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class PaymentRelateContractUI extends AbstractPaymentRelateContractUI
{
    private static final Logger logger = CoreUIObject.getLogger(PaymentRelateContractUI.class);
    
    public PaymentRelateContractUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	Object objval = this.getUIContext().get("curProjectid");
    	Object number = this.getUIContext().get("number");
    	if(number != null){
    		this.txtnumber.setText(number.toString());
    	}
    	this.kdcboPayCon.setSelected(true);
    	kdcboPayCon_stateChanged(null);
    	
    }
    
    //确认,生成付款申请单
    protected void kDButton1_actionPerformed(ActionEvent e) throws Exception {
    	Object contval = this.pmtContract.getValue();//合同
    	Object billidval = this.getUIContext().get("billid");
    	
    	Object payRequestid = this.getUIContext().get("payRequestid");
    	if(payRequestid != null ){
    		String payid = payRequestid.toString();
    		PayRequestBillInfo requestBill = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(payid));
    		if(requestBill.getState().equals(FDCBillStateEnum.SUBMITTED)){
    			PayRequestBillFactory.getRemoteInstance().delete(new ObjectUuidPK(payid));//如果已生成付款申请单先删除原来的，再生成新的付款申请单。
    		}else{
    			MsgBox.showWarning("已生成付款申请单，且付款申请单已不是提交状态，不能继续生成！");
    			SysUtil.abort();
    		}
    	}
	    	if( contval != null && billidval != null){
	    		ContractBillInfo billInfo = (ContractBillInfo) contval;
	    		OtherPaymentInfo otherPaymentInfo = OtherPaymentFactory.getRemoteInstance().getOtherPaymentInfo(new ObjectUuidPK(billidval.toString()));
	    		
	    		//检验
	    		boolean ischeck = false;
	    		ischeck = checkContractSplitState(otherPaymentInfo);
//	    		boolean checkTempSmallerThanZero = checkTempSmallerThanZero(otherPaymentInfo);
//	    		if(!checkTempSmallerThanZero){
//	    			boolean checkContractSplitState = checkContractSplitState(otherPaymentInfo);
//	    			ischeck = checkContractSplitState;
//	    		}else{
//	    			ischeck = true;
//	    		}
//	    		if(!ischeck){
//	    			boolean checkSubmit = checkSubmit(billInfo,otherPaymentInfo);
//	    			ischeck = checkSubmit;
//	    		}
//	    		if(!ischeck){
//	    			boolean planAcctCtrl = planAcctCtrl(otherPaymentInfo);
//	    			ischeck = planAcctCtrl;
//	    		}
		    	if(!ischeck){
		    		//生成付款申请单
		    		PayRequestBillInfo payReqbillInfo = new PayRequestBillInfo();
		    		getReqNumber("NONE", payReqbillInfo);//根据编码规则生成编码
		    		payReqbillInfo.setAmount(otherPaymentInfo.getApplyAMT());//金额
		    		payReqbillInfo.setState(FDCBillStateEnum.SUBMITTED);
		    		
		    		if(otherPaymentInfo.getOrganize() != null){
		    			FullOrgUnitInfo fullOrgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(otherPaymentInfo.getOrganize().getId()));
		    			payReqbillInfo.setOrgUnit(fullOrgUnitInfo);//组织
		    		}
		    		
		    		payReqbillInfo.setUseDepartment(otherPaymentInfo.getWithMSector());//用款部门
		    		payReqbillInfo.setPayDate(otherPaymentInfo.getPaymentDate());//付款日期
		    		payReqbillInfo.setContractNo(billInfo.getNumber());//合同编码
		    		payReqbillInfo.setAttachment(0);//附件  后续新增一条关联关系到付款申请单
		    		payReqbillInfo.setExchangeRate(otherPaymentInfo.getParities());//汇率
		    		payReqbillInfo.setCurrency(otherPaymentInfo.getCurrency());//币别
		    		payReqbillInfo.setSupplier(otherPaymentInfo.getPayee());//收款单位
		    		payReqbillInfo.setCurProject(otherPaymentInfo.getCurProject());//工程项目
		    		payReqbillInfo.setContractName(billInfo.getName());//合同名称
		    		payReqbillInfo.setContractPrice(billInfo.getCeremonyb());//合同金额
		    		payReqbillInfo.setLatestPrice(billInfo.getCeremonyb());//最新造价
		    		payReqbillInfo.setProjectPriceInContract(otherPaymentInfo.getApplyAMT());//合同内工程款（本期发生）
		    		payReqbillInfo.setUrgentDegree(UrgentDegreeEnum.NORMAL);//紧急程度
		    		payReqbillInfo.setPaymentType(otherPaymentInfo.getPayType());//付款类型
		    		payReqbillInfo.setCurPaid(otherPaymentInfo.getApplyAMT());//实付款本期发生额
		    		payReqbillInfo.setPrjAllReqAmt(otherPaymentInfo.getApplyAMT());//合同内工程累计申请本币
		    		payReqbillInfo.setContractId(billInfo.getId().toString());//合同
		    		payReqbillInfo.setRealSupplier(otherPaymentInfo.getRealityPayee());//实付收款单位
		    		payReqbillInfo.setRecBank(otherPaymentInfo.getReceiptBank());//银行
		    		payReqbillInfo.setRecAccount(otherPaymentInfo.getReceiptNumber());//银行帐号
		    		if(otherPaymentInfo.getPaymentProportion() != null){
		    			payReqbillInfo.setPaymentProportion(otherPaymentInfo.getPaymentProportion());//付款比例
		    		}else{
		    			payReqbillInfo.setPaymentProportion(new BigDecimal(0));//付款比例
		    		}
		    		payReqbillInfo.setCompletePrjAmt(otherPaymentInfo.getCompletedQuantities());//本期成本金额（本期完工工程量）
		    		
		    		payReqbillInfo.setPeriod(otherPaymentInfo.getApplyPeriod());//期间
		    		payReqbillInfo.setOriginalAmount(otherPaymentInfo.getApplyAMT());//原币
		    		payReqbillInfo.setSourceType(SourceTypeEnum.ADDNEW);//单据来源方式
		    		payReqbillInfo.setProjectPriceInContractOri(otherPaymentInfo.getApplyAMT());//合同内工程款
		    		payReqbillInfo.setSource("0D6DD1F4");//来源bostype
		    		payReqbillInfo.setContractBase(billInfo.getContractBaseData());//合同基本信息
		    		payReqbillInfo.setLocalCurrency(otherPaymentInfo.getCurrency());//本位币币别
		    		payReqbillInfo.setInvoiceDate(new Date());//发票日期
		    		payReqbillInfo.setIsOtherPay(true);
		    		
		    		payReqbillInfo.setIsPay(false);//add by ypf on 20140126  解决其他付款单生成的付款单，审核后的状态不是已付款的问题
		    		
		    		IObjectPK addnew = PayRequestBillFactory.getRemoteInstance().addnew(payReqbillInfo);//保存付款申请单
		    		
		    		
		    		//附件
		    		SelectorItemCollection sicc = new SelectorItemCollection();
		    		sicc.add(new SelectorItemInfo("id"));
		    		sicc.add(new SelectorItemInfo("attachment.id"));
		    		sicc.add(new SelectorItemInfo("attachment.name"));
		            FilterInfo filter = new FilterInfo();
		            filter.getFilterItems().add(new FilterItemInfo("boID", billidval.toString()));
		            EntityViewInfo evi = new EntityViewInfo();
		            evi.setFilter(filter);
		            evi.setSelector(sicc);
		            BoAttchAssoCollection cols = null;
		            cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
		            for (int i = 0; i < cols.size(); i++) {
		            	BoAttchAssoInfo attchAssoInfo = cols.get(i);
		            	BoAttchAssoInfo assoInfo  = new BoAttchAssoInfo();
		            	assoInfo.setBoID(addnew.toString());
		            	assoInfo.setAssoType(attchAssoInfo.getAssoType());
		            	assoInfo.setAttachment(attchAssoInfo.getAttachment());
		            	assoInfo.setAssoBusObjType(attchAssoInfo.getAssoBusObjType());
		            	assoInfo.setSourceBillID(attchAssoInfo.getSourceBillID());
		            	assoInfo.setSaveTime(attchAssoInfo.getSaveTime());
		            	BoAttchAssoFactory.getRemoteInstance().addnew(assoInfo);
					}
		            
		    		
		    		SelectorItemCollection sic = new SelectorItemCollection();
		    		sic.add("contract");
		    		sic.add("payRequest");
		    		otherPaymentInfo.setContract(billInfo);
		    		PayRequestBillInfo requestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(addnew);
		    		otherPaymentInfo.setPayRequest(requestBillInfo);
		    		OtherPaymentFactory.getRemoteInstance().updatePartial(otherPaymentInfo,sic);
		    		
		    		this.uiWindow.close();
		    		MsgBox.showInfo("已关联合同并生成付款申请单！");
		    	}
	    	}else{
	    		MsgBox.showInfo("参数合同或其他付款单ID为空，请检查。");
	    }
    }
    
    
    /**
     * 获取付款申请单的编码
     * @param orgType
     * @param reqinfo
     * @throws Exception
     */
    private void getReqNumber(String orgType,PayRequestBillInfo reqinfo) throws Exception{
    	String companyID = null;
		if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
			companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
		}
		else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
			companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
    	}
		com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		reqinfo.setNumber(iCodingRuleManager.getNumber(reqinfo,companyID));
    }
    
    /**
     * 所有合同
     */
    protected void kdcboAllCon_stateChanged(ChangeEvent e) throws Exception {
    	this.kdcboPayCon.setSelected(false);
    	Object objval = this.getUIContext().get("curProjectid");
    	if(objval != null ){
    		String  curProjectid = objval.toString();
    		EntityViewInfo viewInfo = new EntityViewInfo();
    		FilterInfo filterInfo = new FilterInfo();
    		filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id",curProjectid));
    		filterInfo.getFilterItems().add(new FilterItemInfo("hasSettled",false));
    		viewInfo.setFilter(filterInfo);
    		this.pmtContract.setEntityViewInfo(viewInfo);
    	}
    }
    
    /**
     * 收款方合同
     */
    protected void kdcboPayCon_stateChanged(ChangeEvent e) throws Exception {
    	this.kdcboAllCon.setSelected(false);
    	Object billidval = this.getUIContext().get("billid");
    	Object objval = this.getUIContext().get("curProjectid");
    	if(objval != null && billidval != null){
    		OtherPaymentInfo otherPaymentInfo = OtherPaymentFactory.getRemoteInstance().getOtherPaymentInfo(new ObjectUuidPK(billidval.toString()));
    		String  curProjectid = objval.toString();
    		EntityViewInfo viewInfo = new EntityViewInfo();
    		FilterInfo filterInfo = new FilterInfo();
    		filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id",curProjectid));
    		filterInfo.getFilterItems().add(new FilterItemInfo("hasSettled",false));
    		if(otherPaymentInfo.getPayee() != null){
	    		SupplierInfo supplierInfo = SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(otherPaymentInfo.getPayee().getId().toString()));
    			filterInfo.getFilterItems().add(new FilterItemInfo("landdeveloper.number",supplierInfo.getNumber()));
    			filterInfo.getFilterItems().add(new FilterItemInfo("partB.number",supplierInfo.getNumber()));
    			filterInfo.getFilterItems().add(new FilterItemInfo("partC.number",supplierInfo.getNumber()));
    			filterInfo.setMaskString("#0 and #1 and( #2 or #3 or #4)");
    		}
    		viewInfo.setFilter(filterInfo);
    		this.pmtContract.setEntityViewInfo(viewInfo);
    	}
    	
    }
    
    /**
     * 暂估款类型的付款申请单的累计发票金额不能小于0，请修正后提交
     * @param editData
     * @throws Exception
     */
    private boolean checkTempSmallerThanZero(OtherPaymentInfo editData)
    throws Exception
	    {
	    	Object contval = this.pmtContract.getValue();//合同
	    if(contval == null)
	        return false;
	    ContractBillInfo contractBill =(ContractBillInfo) contval;
	    Object o = editData.getPayType();//付款类型
	    if(o == null || !(o instanceof OtherPaymentInfo) || !"Bd2bh+CHRDenvdQS3D72ouwp3Sw=".equals(((OtherPaymentInfo)o).getPayType().getId().toString()))
	        return false;
	    BigDecimal totalInvoiceAmt = FDCHelper.ZERO;
	    String contractId = contractBill.getId().toString();
	    EntityViewInfo view = new EntityViewInfo();
	    FilterInfo filter = new FilterInfo();
	    SelectorItemCollection selector = new SelectorItemCollection();
	    selector.add("invoiceAmt");
	    filter.getFilterItems().add(new FilterItemInfo("contract", contractId));
	    filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", "Bd2bh+CHRDenvdQS3D72ouwp3Sw="));
	    view.setFilter(filter);
	    view.setSelector(selector);
	    OtherPaymentCollection payReqColl = OtherPaymentFactory.getRemoteInstance().getOtherPaymentCollection(view);
	    for(Iterator iter = payReqColl.iterator(); iter.hasNext();)
	    {
	        PayRequestBillInfo payRequest = (PayRequestBillInfo)iter.next();
	        totalInvoiceAmt = FDCHelper.add(totalInvoiceAmt, payRequest.getInvoiceAmt());
	    }
	
	    if(editData.getId() != null && ("\u4FDD\u5B58".equals(editData.getBillstates().toString()) || "\u5DF2\u63D0\u4EA4".equals(editData.getBillstates().toString())))
	    {
	        PayRequestBillInfo billInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(editData.getId()));
	        totalInvoiceAmt = FDCHelper.subtract(totalInvoiceAmt, billInfo.getInvoiceAmt());
	        totalInvoiceAmt = FDCHelper.add(totalInvoiceAmt, editData.getInvoiceAMT());
	    }
	    if(FDCHelper.ZERO.compareTo(totalInvoiceAmt) == 1)
	    {
	    	//暂估款类型的付款申请单的累计发票金额不能小于0，请修正后提交！
	        FDCMsgBox.showWarning("\u6682\u4F30\u6B3E\u7C7B\u578B\u7684\u4ED8\u6B3E\u7533\u8BF7\u5355\u7684\u7D2F\u8BA1\u53D1\u7968\u91D1\u989D\u4E0D\u80FD\u5C0F\u4E8E0\uFF0C\u8BF7\u4FEE\u6B63\u540E\u63D0\u4EA4\uFF01");
	        return true;
	    }
	    return false;
	 }
    
    /**
     * 累计付款金额不能大于合同金额
     * @param editData
     * @return
     * @throws Exception 
     */
    private boolean checkContractSplitState(OtherPaymentInfo editData) throws Exception
    {
    	Object contval = this.pmtContract.getValue();//合同
        if(contval != null )
        {
      	    ContractBillInfo contractBill =(ContractBillInfo) contval;
      	    BigDecimal camount = contractBill.getAmount();//合同金额
      	    
      	    EntityViewInfo viewInfo = new EntityViewInfo();
      	    FilterInfo filterInfo = new FilterInfo();
      	    filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id",editData.getCurProject().getId().toString()));
      	    filterInfo.getFilterItems().add(new FilterItemInfo("contractid",contractBill.getId().toString()));
      	    filterInfo.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
      	    filterInfo.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.REJECT_VALUE));
      	    filterInfo.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.SUBMITTED_VALUE));
      	    filterInfo.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTING_VALUE));
      	    filterInfo.setMaskString("#0 and #1 and (#2 or #3 or #4 or #5)");
      	    viewInfo.setFilter(filterInfo);
      	    PayRequestBillCollection payRequestBillCollection = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(viewInfo);
      	    
      	    BigDecimal amt = BigDecimal.ZERO;
      	    for (int i = 0; i < payRequestBillCollection.size(); i++) {
      	    	 PayRequestBillInfo payRequestBillInfo = payRequestBillCollection.get(i);
      	    	 BigDecimal localAmt = payRequestBillInfo.getAmount();
      	    	 amt = amt.add(localAmt);
			}
      	  BigDecimal amtval = amt.add(editData.getApplyAMTHC());
      	  if(amtval.compareTo(camount) > 0){
      		  MsgBox.showWarning("累计付款金额不能大于合同金额");
      		return true;
      	  }
//      	    
//        	if(!ContractClientUtils.getContractSplitState(contractBill.getId().toString())){
//	            FDCMsgBox.showWarning(this, FDCSplitClientHelper.getRes("conNotSplited"));
//	            return true;
//        	}
        }
        return false;
    }
    
    
    /**
     * 合同实付款不能大于合同结算价
     * @param e
     * @param editData
     * @throws Exception
     */
    private boolean checkSubmit(ContractBillInfo contractBill,OtherPaymentInfo editData) throws Exception{
    	boolean isCanPass = true;
   	 	Set payReqSet = new HashSet();
   	 	payReqSet.add(editData);
        BigDecimal uiAmt = editData.getApplyAMTHC();
        if(uiAmt == null)
            uiAmt = FDCHelper.ZERO;
        OtherPaymentInfo payReqBill = null;
        BigDecimal settlePrice = FDCHelper.ZERO;
        BigDecimal thisTimePayAmt = FDCHelper.ZERO;
        BigDecimal prjPriceInCon = FDCHelper.ZERO;
        if(payReqSet != null && payReqSet.size() > 0)
        {
            payReqBill = (OtherPaymentInfo)payReqSet.iterator().next();
            String contractId = contractBill.getId().toString();
            boolean isContractWithTxt = FDCUtils.isContractBill(null, contractId);
            if(isContractWithTxt)
            {
                ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
                if(!contract.isHasSettled())
                	isCanPass = true;
                settlePrice = FDCHelper.toBigDecimal(contract.getSettleAmt(), 2);
                prjPriceInCon = FDCHelper.toBigDecimal(ContractClientUtils.getPayAmt(contractId), 2);
                if(payReqSet.size() == 1 && payReqBill.getId() != null)
                {
                    SelectorItemCollection selector = new SelectorItemCollection();
                    selector.add("applyAMTHC");
                    OtherPaymentInfo _tempPayRequest = OtherPaymentFactory.getRemoteInstance().getOtherPaymentInfo(new ObjectUuidPK(payReqBill.getId()), selector);
                    thisTimePayAmt = FDCHelper.toBigDecimal(_tempPayRequest.getApplyAMTHC(), 2);
                    prjPriceInCon = FDCHelper.toBigDecimal(FDCHelper.add(FDCHelper.subtract(prjPriceInCon, thisTimePayAmt), uiAmt), 2);
                }
                if(settlePrice != null && settlePrice.compareTo(prjPriceInCon) == -1)
                	isCanPass = false;
            }
        }
        
//        boolean isCanPass = PayReqUtils.checkProjectPriceInContract(payReqBillSet, editData.getApplyAMTHC());
        if(!isCanPass)
        {
        	//合同实付款不能大于合同结算价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】
            FDCMsgBox.showError("\u5408\u540C\u5B9E\u4ED8\u6B3E\u4E0D\u80FD\u5927\u4E8E\u5408\u540C\u7ED3\u7B97\u4EF7\u3010\u5408\u540C\u5B9E\u4ED8\u6B3E =\u5DF2\u5173\u95ED\u7684\u4ED8\u6B3E\u7533\u8BF7\u5355\u5BF9\u5E94\u7684\u4ED8\u6B3E\u5355\u5408\u540C\u5185\u5DE5\u7A0B\u6B3E\u5408\u8BA1 + \u672A\u5173\u95ED\u7684\u4ED8\u6B3E\u7533\u8BF7\u5355\u5408\u540C\u5185\u5DE5\u7A0B\u6B3E\u5408\u8BA1\u3011");
            return true;
        }
        return false;
   }
    

    protected boolean planAcctCtrl(OtherPaymentInfo editData)
        throws Exception
    {
    	
    	label0:
        {
            boolean hasUsed = FDCUtils.getDefaultFDCParamByKey(null, null, "FDC5001001_ACCTBUDGET");
            if(!hasUsed)
                break label0;
            Map costAcctPlan = ConPayPlanSplitFactory.getRemoteInstance().getCostAcctPlan(editData.getCurProject().getId().toString(), editData.getPaymentDate());
            Map planSplitMap = (Map)costAcctPlan.get("planSplitMap");
            Map reqSplitMap = (Map)costAcctPlan.get("reqSplitMap");
            Map allPlanSplitMap = (Map)costAcctPlan.get("allPlanSplitMap");
            Map allReqSplitMap = (Map)costAcctPlan.get("allReqSplitMap");
            if(planSplitMap == null)
                planSplitMap = new HashMap();
            if(reqSplitMap == null)
                reqSplitMap = new HashMap();
            if(allPlanSplitMap == null)
                allPlanSplitMap = new HashMap();
            if(allReqSplitMap == null)
                allReqSplitMap = new HashMap();
            Iterator iter = planSplitMap.keySet().iterator();
            String key;
            BigDecimal planAmt;
            BigDecimal reqAmt;
            do
            {
                if(!iter.hasNext())
                    break label0;
                key = (String)iter.next();
                planAmt = (BigDecimal)planSplitMap.get(key);
                reqAmt = (BigDecimal)reqSplitMap.get(key);
            } while(FDCHelper.toBigDecimal(FDCNumberHelper.subtract(planAmt, reqAmt)).signum() >= 0);
            String acctId = key.substring(0, 44);
            CostAccountInfo costAccountInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(acctId));
            FDCMsgBox.showWarning(this, "'" + costAccountInfo.getName() + "' \u79D1\u76EE\u4E0A\u5DF2\u7ECF\u8D85\u4ED8");
            return true;
        }
    	return false;
    }
    
}