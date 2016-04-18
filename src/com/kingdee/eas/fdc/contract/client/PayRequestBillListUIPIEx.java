package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank;
import com.kingdee.eas.basedata.master.cssp.ISupplierCompanyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.WriteBackBillStatusFacade;
import com.kingdee.eas.fdc.contract.WriteBackBillStatusFacadeFactory;
import com.kingdee.eas.fdc.finance.client.PaymentBillContants;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class PayRequestBillListUIPIEx extends PayRequestBillListUI {

	public PayRequestBillListUIPIEx() throws Exception {
		super();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		//传入listui对象
		super.prepareUIContext(uiContext, e);
		uiContext.put("Owner", this);
		uiContext.put("isRefresh", "true");
		
		//add by ypf on 20121031 OA主题需要用到合同名称  
		//20140213 改    
		//编辑界面已在工程付款情况表里取到合同名称，这里代码可以去除。
//		uiContext.put("contractName", tblMain.getCell(tblMain.getSelectManagger().getActiveRowIndex(), "contractName").getValue());
	}
	
	
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selector = new SelectorItemCollection();
		Field[] fields = PayRequestBillContants.class.getFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().startsWith("COL_")) {
				try {
					selector.add(fields[i].get(null).toString());
				} catch (Exception e) {
					super.handUIException(e);
				}
			}
		}

		selector.add("usage");

		selector.add("period.number");
		selector.add("period.periodNumber");
		selector.add("period.periodYear");
		selector.add("bookedDate");
		selector.add("currency.name");

		selector.add("paymentType.name");
		selector.add("currency.precision");
		selector.add("originalAmount");
		selector.add("actPaiedAmount");
		selector.add("actPaiedLocAmount");
		selector.add("isRespite");
		selector.add("isOAAudit");
		selector.add("isOtherPay");
		selector.add("projectPriceInContractOri");//biaobiao  合同内工程款（本期发生原币）
		selector.add("projectPriceInContract");//biaobiao 合同内工程款（本期发生）
		return selector;
	}

	protected void displayBillByContract(EntityViewInfo view)
			throws BOSException {
		// 合计行本币
		BigDecimal localAmtAll = FDCHelper.ZERO;
		BigDecimal projectPriceInContractOri = FDCHelper.ZERO;
		BigDecimal projectPriceInContract = FDCHelper.ZERO;
		BigDecimal priceInContractOriVal = FDCHelper.ZERO;
		BigDecimal priceInContractVal = FDCHelper.ZERO;

		if (view == null) {
			return;
		}

		PayRequestBillCollection payRequestBillCollection = PayRequestBillFactory
				.getRemoteInstance().getPayRequestBillCollection(view);

		if (this.getBOTPViewStatus() == 1) {
			Map botpCtx = (Map) this.getUIContext().get("BTPEDITPARAMETER");
			treeProject.setEnabled(false);
			treeContractType.setEnabled(false);
			final StyleAttributes sa = tblMain.getStyleAttributes();
			sa.setLocked(true);
			tblMain.setEnabled(false);
			// contContrList.setEnabled(false);
			if (botpCtx != null) {
				String id = botpCtx.get("contractbillid").toString();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
				filter.getFilterItems().add(
						new FilterItemInfo("hasClosed", Boolean.FALSE));
				filter.getFilterItems().add(
						new FilterItemInfo("contractId", id));
				// filter.getFilterItems().add(new
				// FilterItemInfo("hasPayoff",Boolean
				// .FALSE,CompareType.EQUALS));
				EntityViewInfo eview = new EntityViewInfo();
				eview.setFilter(filter);
				SelectorItemCollection selectors = genBillQuerySelector();
				if (selectors != null && selectors.size() > 0) {
					for (Iterator iter = selectors.iterator(); iter.hasNext();) {
						SelectorItemInfo element = (SelectorItemInfo) iter
								.next();
						eview.getSelector().add(element);

					}
				}
				PayRequestBillCollection pRequestBillCollection = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillCollection(eview);
				for (Iterator iter = pRequestBillCollection.iterator(); iter
						.hasNext();) {
					PayRequestBillInfo element = (PayRequestBillInfo) iter
							.next();

					int pre = element.getCurrency().getPrecision();
					String curFormat = FDCClientHelper.getNumberFormat(pre,
							true);
					IRow row = getBillListTable().addRow();
					
					try {
						priceInContractOriVal=element.getProjectPriceInContractOri();
						priceInContractVal = element.getProjectPriceInContract();
						PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(element.getId()));
						String contractId =payRequestBillInfo.getContractId();
						if(contractId != null){
							BOSUuid read = BOSUuid.read(contractId);
							BOSObjectType type = read.getType();
							ContractWithoutTextInfo withoutTextinfo = new ContractWithoutTextInfo();
							BOSObjectType objectType = withoutTextinfo.getBOSType();
							if((type.toString()).equals(objectType.toString())){
								priceInContractOriVal=element.getOriginalAmount();
								priceInContractVal = element.getAmount();
								row.getCell("projectPriceInContractOri").setValue(priceInContractOriVal);//biaobiao 合同内工程款（本期发生原币） 2013-09-24
								row.getCell("projectPriceInContract").setValue(priceInContractVal);// biaobiao 合同内工程款（本期发生） 2013-09-24
							}else{
								row.getCell("projectPriceInContractOri").setValue(priceInContractOriVal);//biaobiao 合同内工程款（本期发生原币） 2013-09-24
								row.getCell("projectPriceInContract").setValue(priceInContractVal);// biaobiao 合同内工程款（本期发生） 2013-09-24
							}
						}else{
							row.getCell("projectPriceInContractOri").setValue(priceInContractOriVal);//biaobiao 合同内工程款（本期发生原币） 2013-09-24
							row.getCell("projectPriceInContract").setValue(priceInContractVal);// biaobiao 合同内工程款（本期发生） 2013-09-24
						}
					
					} catch (EASBizException e) {
						e.printStackTrace();
					}
					

					row.getCell("isOtherPay").setValue(element.isIsOtherPay());
					row.getCell("isOAAudit").setValue(element.isIsOAAudit());
					row.getCell("originalAmount").getStyleAttributes()
							.setNumberFormat(curFormat);
					row.getCell("actPaidLocAmount").getStyleAttributes()
							.setNumberFormat(curFormat);
					row.getCell(PayRequestBillContants.COL_ID).setValue(
							element.getId().toString());
					row.getCell(PayRequestBillContants.COL_STATE).setValue(
							element.getState());
					row.getCell(PayRequestBillContants.COL_NUMBER).setValue(
							element.getNumber());
					row.getCell(PayRequestBillContants.COL_AMOUNT).setValue(
							element.getAmount());
					row.getCell(PayRequestBillContants.COL_ACTPAYAMOUNT)
							.setValue(element.getActPaiedAmount());
					row.getCell(PayRequestBillContants.COL_ACTPAYLOCAMOUNT)
							.setValue(element.getActPaiedLocAmount());

					row.getCell(PayRequestBillContants.COL_PAYDATE).setValue(
							element.getPayDate());
					if (element.getSupplier() != null) {
						row.getCell(PayRequestBillContants.COL_SUPPLIER)
								.setValue(element.getSupplier().getName());
					}
					if (element.getCreator() != null) {
						row.getCell(PayRequestBillContants.COL_CREATER)
								.setValue(element.getCreator().getName());
					}
					row.getCell(PayRequestBillContants.COL_CREATETIME)
							.setValue(element.getCreateTime());
					row.getCell(PayRequestBillContants.COL_MONEYDESC).setValue(
							element.getUsage());
					row.getCell(PayRequestBillContants.COL_RECBANK).setValue(
							element.getRecBank());
					row.getCell(PayRequestBillContants.COL_RECACCOUNT)
							.setValue(element.getRecAccount());
					if (element.getAuditor() != null) {
						row.getCell(PayRequestBillContants.COL_AUDITOR)
								.setValue(element.getAuditor().getName());
					}
					row.getCell(PayRequestBillContants.COL_AUDITDATE).setValue(
							element.getAuditTime());
					row.getCell(PayRequestBillContants.COL_DESC).setValue(
							element.getDescription());
					row.getCell(PayRequestBillContants.COL_ATTACHMENT)
							.setValue(new Integer(element.getAttachment()));

					row.getCell(COL_DATE).setValue(element.getBookedDate());
					row.getCell(COL_PERIOD).setValue(element.getPeriod());

					row.getCell("currency").setValue(
							element.getCurrency().getName());
					row.getCell("originalAmount").setValue(
							element.getOriginalAmount());
					row.getCell(PayRequestBillContants.COL_ISRESPITE).setValue(
							new Boolean(element.isIsRespite()));
					if (element.getPaymentType() != null) {
						row.getCell("paymentType").setValue(
								element.getPaymentType().getName());
					}
					// 求合计行本币值
					localAmtAll = FDCHelper.add(localAmtAll, element.getAmount());
					
					projectPriceInContractOri = FDCHelper.add(projectPriceInContractOri, element.getProjectPriceInContractOri());
					projectPriceInContract = FDCHelper.add(projectPriceInContract, element.getProjectPriceInContract());
					
					
					tblPayRequestBill.getColumn(
							PayRequestBillContants.COL_ATTACHMENT)
							.getStyleAttributes().setHided(true);
				}
			} else {
				EntityViewInfo boCtx = (EntityViewInfo) this.getUIContext()
						.get("BOTPFilter");
				SelectorItemCollection selectors = genBillQuerySelector();
				if (selectors != null && selectors.size() > 0) {
					for (Iterator iter = selectors.iterator(); iter.hasNext();) {
						SelectorItemInfo element = (SelectorItemInfo) iter
								.next();
						boCtx.getSelector().add(element);

					}
				}
				PayRequestBillCollection pRequestBillCollection = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillCollection(boCtx);
				for (Iterator iter = pRequestBillCollection.iterator(); iter
						.hasNext();) {
					PayRequestBillInfo element = (PayRequestBillInfo) iter
							.next();
					int pre = element.getCurrency().getPrecision();
					String curFormat = FDCClientHelper.getNumberFormat(pre,
							true);
					IRow row = getBillListTable().addRow();
					row.getCell("isOAAudit").setValue(element.isIsOAAudit());
					row.getCell("isOtherPay").setValue(element.isIsOtherPay());
					
					try {
						priceInContractOriVal=element.getProjectPriceInContractOri();
						priceInContractVal = element.getProjectPriceInContract();
						PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(element.getId()));
						String contractId =payRequestBillInfo.getContractId();
						if(contractId != null){
							BOSUuid read = BOSUuid.read(contractId);
							BOSObjectType type = read.getType();
							ContractWithoutTextInfo withoutTextinfo = new ContractWithoutTextInfo();
							BOSObjectType objectType = withoutTextinfo.getBOSType();
							if((type.toString()).equals(objectType.toString())){
								priceInContractOriVal=element.getOriginalAmount();
								priceInContractVal = element.getAmount();
								row.getCell("projectPriceInContractOri").setValue(priceInContractOriVal);//biaobiao 合同内工程款（本期发生原币） 2013-09-24
								row.getCell("projectPriceInContract").setValue(priceInContractVal);// biaobiao 合同内工程款（本期发生） 2013-09-24
							}else{
								row.getCell("projectPriceInContractOri").setValue(priceInContractOriVal);//biaobiao 合同内工程款（本期发生原币） 2013-09-24
								row.getCell("projectPriceInContract").setValue(priceInContractVal);// biaobiao 合同内工程款（本期发生） 2013-09-24
							}
						}else{
							row.getCell("projectPriceInContractOri").setValue(priceInContractOriVal);//biaobiao 合同内工程款（本期发生原币） 2013-09-24
							row.getCell("projectPriceInContract").setValue(priceInContractVal);// biaobiao 合同内工程款（本期发生） 2013-09-24
						}
					} catch (EASBizException e) {
						e.printStackTrace();
					}
					
					row.getCell("originalAmount").getStyleAttributes()
							.setNumberFormat(curFormat);
					row.getCell("actPaidLocAmount").getStyleAttributes()
							.setNumberFormat(curFormat);
					row.getCell(PayRequestBillContants.COL_ID).setValue(
							element.getId().toString());
					row.getCell(PayRequestBillContants.COL_STATE).setValue(
							element.getState());
					row.getCell(PayRequestBillContants.COL_NUMBER).setValue(
							element.getNumber());
					row.getCell(PayRequestBillContants.COL_AMOUNT).setValue(
							element.getAmount());
					row.getCell(PayRequestBillContants.COL_ACTPAYAMOUNT)
							.setValue(element.getActPaiedAmount());
					row.getCell(PayRequestBillContants.COL_ACTPAYLOCAMOUNT)
							.setValue(element.getActPaiedLocAmount());
					row.getCell(PayRequestBillContants.COL_PAYDATE).setValue(
							element.getPayDate());
					if (element.getSupplier() != null) {
						row.getCell(PayRequestBillContants.COL_SUPPLIER)
								.setValue(element.getSupplier().getName());
					}
					if (element.getCreator() != null) {
						row.getCell(PayRequestBillContants.COL_CREATER)
								.setValue(element.getCreator().getName());
					}
					row.getCell(PayRequestBillContants.COL_CREATETIME)
							.setValue(element.getCreateTime());
					row.getCell(PayRequestBillContants.COL_MONEYDESC).setValue(
							element.getUsage());
					row.getCell(PayRequestBillContants.COL_RECBANK).setValue(
							element.getRecBank());
					row.getCell(PayRequestBillContants.COL_RECACCOUNT)
							.setValue(element.getRecAccount());
					if (element.getAuditor() != null) {
						row.getCell(PayRequestBillContants.COL_AUDITOR)
								.setValue(element.getAuditor().getName());
					}
					row.getCell(PayRequestBillContants.COL_AUDITDATE).setValue(
							element.getAuditTime());
					row.getCell(PayRequestBillContants.COL_DESC).setValue(
							element.getDescription());
					row.getCell(PayRequestBillContants.COL_ATTACHMENT)
							.setValue(new Integer(element.getAttachment()));

					row.getCell(COL_DATE).setValue(element.getBookedDate());
					row.getCell(COL_PERIOD).setValue(element.getPeriod());

					row.getCell("currency").setValue(
							element.getCurrency().getName());
					row.getCell("originalAmount").setValue(
							element.getOriginalAmount());
					if (element.getPaymentType() != null) {
						row.getCell("paymentType").setValue(
								element.getPaymentType().getName());
					}
					row.getCell(PayRequestBillContants.COL_ISRESPITE).setValue(
							new Boolean(element.isIsRespite()));
					// 求合计行本币值
					localAmtAll = FDCHelper.add(localAmtAll, element.getAmount());
					
					projectPriceInContractOri = FDCHelper.add(projectPriceInContractOri, element.getProjectPriceInContractOri());
					projectPriceInContract = FDCHelper.add(projectPriceInContract, element.getProjectPriceInContract());
					
					tblPayRequestBill.getColumn(
							PayRequestBillContants.COL_ATTACHMENT)
							.getStyleAttributes().setHided(true);
				}
			}

		} else {
			for (Iterator iter = payRequestBillCollection.iterator(); iter
					.hasNext();) {
				PayRequestBillInfo element = (PayRequestBillInfo) iter.next();

				int pre = element.getCurrency().getPrecision();
				String curFormat = FDCClientHelper.getNumberFormat(pre, true);
				IRow row = getBillListTable().addRow();
				row.getCell("isOAAudit").setValue(element.isIsOAAudit());
				
				row.getCell("isOtherPay").setValue(element.isIsOtherPay());
				
				try {
					priceInContractOriVal=element.getProjectPriceInContractOri();
					priceInContractVal = element.getProjectPriceInContract();
					PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(element.getId()));
					String contractId =payRequestBillInfo.getContractId();
					if(contractId != null){
						BOSUuid read = BOSUuid.read(contractId);
						BOSObjectType type = read.getType();
						ContractWithoutTextInfo withoutTextinfo = new ContractWithoutTextInfo();
						BOSObjectType objectType = withoutTextinfo.getBOSType();
						if((type.toString()).equals(objectType.toString())){
							priceInContractOriVal=element.getOriginalAmount();
							priceInContractVal = element.getAmount();
							row.getCell("projectPriceInContractOri").setValue(priceInContractOriVal);//biaobiao 合同内工程款（本期发生原币） 2013-09-24
							row.getCell("projectPriceInContract").setValue(priceInContractVal);// biaobiao 合同内工程款（本期发生） 2013-09-24
						}else{
							row.getCell("projectPriceInContractOri").setValue(priceInContractOriVal);//biaobiao 合同内工程款（本期发生原币） 2013-09-24
							row.getCell("projectPriceInContract").setValue(priceInContractVal);// biaobiao 合同内工程款（本期发生） 2013-09-24
						}
					}else{
						row.getCell("projectPriceInContractOri").setValue(priceInContractOriVal);//biaobiao 合同内工程款（本期发生原币） 2013-09-24
						row.getCell("projectPriceInContract").setValue(priceInContractVal);// biaobiao 合同内工程款（本期发生） 2013-09-24
					}
				
				} catch (EASBizException e) {
					e.printStackTrace();
				}
				
				
				row.getCell("originalAmount").getStyleAttributes()
						.setNumberFormat(curFormat);
				row.getCell("actPaidLocAmount").getStyleAttributes()
						.setNumberFormat(curFormat);
				row.getCell("actPaidLocAmount").getStyleAttributes()
						.setHorizontalAlign(HorizontalAlignment.RIGHT);
				row.getCell(PayRequestBillContants.COL_ID).setValue(
						element.getId().toString());
				row.getCell(PayRequestBillContants.COL_STATE).setValue(
						element.getState());
				row.getCell(PayRequestBillContants.COL_NUMBER).setValue(
						element.getNumber());
				row.getCell(PayRequestBillContants.COL_AMOUNT).setValue(
						element.getAmount());
				row.getCell(PayRequestBillContants.COL_ACTPAYAMOUNT).setValue(
						element.getActPaiedAmount());
				row.getCell(PayRequestBillContants.COL_ACTPAYLOCAMOUNT)
						.setValue(element.getActPaiedLocAmount());
				row.getCell(PayRequestBillContants.COL_PAYDATE).setValue(
						element.getPayDate());
				if (element.getSupplier() != null) {
					row.getCell(PayRequestBillContants.COL_SUPPLIER).setValue(
							element.getSupplier().getName());
				}
				if (element.getCreator() != null) {
					row.getCell(PayRequestBillContants.COL_CREATER).setValue(
							element.getCreator().getName());
				}
				row.getCell(PayRequestBillContants.COL_CREATETIME).setValue(
						element.getCreateTime());
				row.getCell(PayRequestBillContants.COL_MONEYDESC).setValue(
						element.getUsage());
				row.getCell(PayRequestBillContants.COL_RECBANK).setValue(
						element.getRecBank());
				row.getCell(PayRequestBillContants.COL_RECACCOUNT).setValue(
						element.getRecAccount());
				if (element.getAuditor() != null) {
					row.getCell(PayRequestBillContants.COL_AUDITOR).setValue(
							element.getAuditor().getName());
				}
				row.getCell(PayRequestBillContants.COL_AUDITDATE).setValue(
						element.getAuditTime());
				row.getCell(PayRequestBillContants.COL_DESC).setValue(
						element.getDescription());
				row.getCell(PayRequestBillContants.COL_ATTACHMENT).setValue(
						new Integer(element.getAttachment()));
				row.getCell(PayRequestBillContants.COL_ATTACHMENT)
						.getStyleAttributes().setHided(true);

				row.getCell(COL_DATE).setValue(element.getBookedDate());
				row.getCell(COL_PERIOD).setValue(element.getPeriod());

				row.getCell("currency").setValue(
						element.getCurrency().getName());
				row.getCell("originalAmount").setValue(
						element.getOriginalAmount());
				if (element.getPaymentType() != null) {
					row.getCell("paymentType").setValue(
							element.getPaymentType().getName());
				}
				row.getCell(PayRequestBillContants.COL_ISRESPITE).setValue(
						new Boolean(element.isIsRespite()));
				// 求合计行本币值
				localAmtAll = FDCHelper.add(localAmtAll, element.getAmount());
				
				projectPriceInContractOri = FDCHelper.add(projectPriceInContractOri, priceInContractOriVal);
				projectPriceInContract = FDCHelper.add(projectPriceInContract,priceInContractVal);
				
				tblPayRequestBill.getColumn(
						PayRequestBillContants.COL_ATTACHMENT)
						.getStyleAttributes().setHided(true);
			}
		}
		KDTFootManager footRowManager = tblPayRequestBill.getFootManager();
		IRow footRow = null;
		if (footRowManager == null) {
			footRowManager = new KDTFootManager(tblPayRequestBill);
			footRowManager.addFootView();
			tblPayRequestBill.setFootManager(footRowManager);
			footRow = tblPayRequestBill.addFootRow(0);
			footRow.getCell(PaymentBillContants.COL_AMOUNT)
					.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.RIGHT);
			tblPayRequestBill.getIndexColumn().setWidthAdjustMode(
					KDTIndexColumn.WIDTH_MANUAL);
			tblPayRequestBill.getIndexColumn().setWidth(60);
			footRow.getCell(PaymentBillContants.COL_AMOUNT).setValue(localAmtAll);
			
			footRow.getCell("projectPriceInContractOri").setValue(projectPriceInContractOri);
			footRow.getCell("projectPriceInContract").setValue(projectPriceInContract);
			
			footRow.getStyleAttributes().setNumberFormat(
					FDCHelper.strDataFormat);
			footRow.getStyleAttributes().setBackground(
					FDCTableHelper.totalColor);
			footRowManager.addIndexText(0, "合计");
		} else {
			footRow = tblPayRequestBill.getFootRow(0);
			footRow.getCell(PaymentBillContants.COL_AMOUNT).setValue(localAmtAll);
			
			footRow.getCell("projectPriceInContractOri").setValue(projectPriceInContractOri);
			footRow.getCell("projectPriceInContract").setValue(projectPriceInContract);
			
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblPayRequestBill);
		int rowIndex = tblPayRequestBill.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblPayRequestBill.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("state").getValue()!=null)?row.getCell("state").getValue().toString():"";
		String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";

		
		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经发起OA流程审批，不能进行删除");
			SysUtil.abort();
		} else if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经通过OA审批，不能进行删除");
			SysUtil.abort();
		} else {
			super.actionRemove_actionPerformed(e);
			
		}
	}

	public String auditForOA(String id)
	{
		try {
			WriteBackBillStatusFacadeFactory.getRemoteInstance().createPayBill(id);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void actionAudit_actionPerformed(ActionEvent arg0) throws Exception {
		checkSelected(tblPayRequestBill);
		int rowIndex = tblPayRequestBill.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblPayRequestBill.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("state").getValue()!=null)?row.getCell("state").getValue().toString():"";
		String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";

		String bid = (row.getCell("id").getValue()!=null)?row.getCell("id").getValue().toString():"";
		
		
		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经走OA流程审批，不能在EAS审批");
			SysUtil.abort();
		} else {
			super.actionAudit_actionPerformed(arg0);
			//更新供应商银行帐号
			if(bid != null && !bid.equals("")){
				IPayRequestBill remoteInstance = PayRequestBillFactory.getRemoteInstance();
				PayRequestBillInfo payRequestBillInfo = remoteInstance.getPayRequestBillInfo(new ObjectUuidPK(bid));
				upSupplierBank(payRequestBillInfo.getRecAccount(), payRequestBillInfo.getRealSupplier());
			}
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblPayRequestBill);
		int rowIndex = tblPayRequestBill.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblPayRequestBill.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("state").getValue()!=null)?row.getCell("state").getValue().toString():"";
		String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";

		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经通过OA审批，不能进行反审批");
			SysUtil.abort();
		} else {
			super.actionUnAudit_actionPerformed(e);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblPayRequestBill);
		int rowIndex = tblPayRequestBill.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblPayRequestBill.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("state").getValue()!=null)?row.getCell("state").getValue().toString():"";
		String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";
		String isOtherPay = (row.getCell("isOtherPay").getValue()!=null)?row.getCell("isOtherPay").getValue().toString():"";

		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经走OA流程审批，不能进行修改");
			SysUtil.abort();
		} else if(isOtherPay.equals("true")){
			MsgBox.showInfo("单据为其它付款单关联生成，不能进行修改");
			SysUtil.abort();
		}else {
			super.actionEdit_actionPerformed(e);
		}
	}
	
	/**
	 * 更新供应商银行帐号
	 * biaobiao 2014-01-22
	 * @throws Exception 
	 */
	private void upSupplierBank(String recAccount,Object trealSupplier) throws Exception {
			if(recAccount != null && trealSupplier != null && trealSupplier instanceof SupplierInfo && recAccount instanceof String){
				String bankNum = recAccount.toString();
				SupplierInfo supplierInfo = (SupplierInfo) trealSupplier;
				ISupplierCompanyInfo remoteInstance = SupplierCompanyInfoFactory.getRemoteInstance();//供应商财务信息
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("supplier",supplierInfo.getId()));
				viewInfo.setFilter(filterInfo);
				//查询供应商财务信息
				SupplierCompanyInfoCollection companyInfoCollection = remoteInstance.getSupplierCompanyInfoCollection(viewInfo);
				for (int i = 0; i < companyInfoCollection.size(); i++) {
					SupplierCompanyInfoInfo companyInfoInfo = companyInfoCollection.get(i);
					
					//财务信息的银行信息
					ISupplierCompanyBank companyBank = SupplierCompanyBankFactory.getRemoteInstance();//供应商财务银行
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("SupplierCompanyInfo",companyInfoInfo.getId()));
					filter.getFilterItems().add(new FilterItemInfo("bankAccount",bankNum));
					view.setFilter(filter);
					SupplierCompanyBankCollection companyBankCollection = companyBank.getSupplierCompanyBankCollection(view);
					if(!(companyBankCollection.size() > 0)){
						SupplierCompanyBankInfo companyBankInfo =  new SupplierCompanyBankInfo();
						companyBankInfo.setBankAccount(bankNum);
						companyBankInfo.setSupplierCompanyInfo(companyInfoInfo);
						SupplierCompanyBankFactory.getRemoteInstance().addnew(companyBankInfo);
					}
				}
				
			}
		}
	
}
