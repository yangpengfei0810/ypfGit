package com.kingdee.eas.fdc.finance.client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.FDCPaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public class PaymentBillListUIPIEx extends PaymentBillListUI {

	private static final Logger logger = CoreUIObject
	.getLogger(PaymentBillListUI.class);
	
	public PaymentBillListUIPIEx() throws Exception {
		super();
	}

	protected void displayBillByContract(EntityViewInfo view)
			throws BOSException {
		// 合计行本币
		BigDecimal localAmtAll = FDCHelper.ZERO;
		BigDecimal projectPriceInContractOri = FDCHelper.ZERO;
		BigDecimal projectPriceInContract = FDCHelper.ZERO;
		BigDecimal priceInContractOriVal = FDCHelper.ZERO;
		BigDecimal priceInContractVal = FDCHelper.ZERO;

		getBillListTable().getColumn("period").getStyleAttributes().setHided(
				true);
		getBillListTable().getColumn(PaymentBillContants.COL_EXCHANGERATE)
				.getStyleAttributes().setHided(true);

		// biaobiao
		getBillListTable().getColumn("projectPriceInContractOri")
				.getStyleAttributes().setHided(false);
		getBillListTable().getColumn("projectPriceInContract")
				.getStyleAttributes().setHided(false);

		PaymentBillCollection paymentBillCollection = PaymentBillFactory
				.getRemoteInstance().getPaymentBillCollection(view);

		// 通过ID获取房地产付款单中间表的isRespite字段
		// TODO
		HashSet idSet = new HashSet();
		HashMap isRespiteMap = null;
		for (Iterator iter = paymentBillCollection.iterator(); iter.hasNext();) {
			PaymentBillInfo element = (PaymentBillInfo) iter.next();
			if (element.getContractBillId() != null) {
				idSet.add(element.getId().toString());
			}
		}

		try {
			isRespiteMap = FDCPaymentBillFactory.getRemoteInstance()
					.getIsRespiteByPaymentBillIds(idSet);
		} catch (EASBizException e) {
			logger.error("根据付款单ID集合获取isRespite字段失败：" + e.getMessage(), e);
		}

		if (isRespiteMap == null) {
			isRespiteMap = new HashMap();
		}

		for (Iterator iter = paymentBillCollection.iterator(); iter.hasNext();) {
			PaymentBillInfo element = (PaymentBillInfo) iter.next();
			if (element.getContractBillId() != null) {
				IRow row = getBillListTable().addRow();

				try {
					priceInContractOriVal=element.getProjectPriceInContract();
					priceInContractVal = element.getProjectPriceInContract();
					PaymentBillInfo PaymentBillBillInfo = PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(element.getId()));
					String contractId =PaymentBillBillInfo.getContractBillId();
					if(contractId != null){
						BOSUuid read = BOSUuid.read(contractId);
						BOSObjectType type = read.getType();
						ContractWithoutTextInfo withoutTextinfo = new ContractWithoutTextInfo();
						BOSObjectType objectType = withoutTextinfo.getBOSType();
						if((type.toString()).equals(objectType.toString())){
							priceInContractOriVal=element.getLocalAmt();
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
				
				
//				row.getCell("projectPriceInContractOri").setValue(element.getProjectPriceInContract());// biaobiao 合同内工程款（本期发生原币 ） 2013-09-24
//				row.getCell("projectPriceInContract").setValue(element.getProjectPriceInContract());// biaobiao 合同内工程款（本期发生） 2013-09-24

				// id
				row.getCell(PaymentBillContants.COL_ID).setValue(
						element.getId().toString());
				// billStatus
				row.getCell(PaymentBillContants.COL_STATE).setValue(
						element.getBillStatus());
				row.getCell("settlementStatus").setValue(
						element.getSettlementStatus());
				row.getCell("fiVouchered").setValue(
						new Boolean(element.isFiVouchered()));
				/*
				 * row.getCell("voucherNumber").setValue(element.getVoucherNumber
				 * ());
				 * row.getCell("voucherType").setValue(element.getVoucherType
				 * ().getName());
				 */
				if (element.getFdcPayType() != null) {
					// row.getCell("paymentType").setValue(new
					// Boolean(element.getFdcPayType().getName()));
					// 改为显示名字 by hpw 2009-07-17
					row.getCell("paymentType").setValue(
							element.getFdcPayType().getName());
				}

				row.getCell(PaymentBillContants.COL_NUMBER).setValue(
						element.getNumber());
				if (element.getFdcPayReqNumber() != null)
					row.getCell(PaymentBillContants.COL_REQNUM).setValue(
							element.getFdcPayReqNumber().toString());
				row.getCell(PaymentBillContants.COL_CURRENCY).setValue(
						element.getCurrency());

				int pre = element.getCurrency().getPrecision();
				String curFormat = FDCClientHelper.getNumberFormat(pre, true);
				row.getCell(PaymentBillContants.COL_AMOUNT)
						.getStyleAttributes().setNumberFormat(curFormat);
				row.getCell(PaymentBillContants.COL_LOCALAMT)
						.getStyleAttributes().setNumberFormat(curFormat);
				row.getCell(PaymentBillContants.COL_AMOUNT).setValue(
						element.getAmount());
				row.getCell(PaymentBillContants.COL_EXCHANGERATE).setValue(
						element.getExchangeRate());
				row.getCell(PaymentBillContants.COL_LOCALAMT).setValue(
						element.getLocalAmt());
				row.getCell(PaymentBillContants.COL_PAYDATE).setValue(
						element.getPayDate());
				row.getCell(PaymentBillContants.COL_PAYEENAME).setValue(
						element.getFdcPayeeName());// .getName());
				row.getCell(PaymentBillContants.COL_ACTPAYEENAME).setValue(
						element.getActFdcPayeeName());
				if (element.getCreator() != null) {
					row.getCell(PaymentBillContants.COL_CREATER).setValue(
							element.getCreator().getName());// .getCreator());
				}
				row.getCell(PaymentBillContants.COL_CREATETIME).setValue(
						element.getCreateTime());
				row.getCell(PaymentBillContants.COL_SUMMARY).setValue(
						element.getUsage());
				row.getCell("descritpion").setValue(element.getDescription());

				row.getCell(PaymentBillContants.COL_PAYEEBANK).setValue(
						element.getPayeeBank());
				row.getCell(PaymentBillContants.COL_PAYEEACCOUNTBANK).setValue(
						element.getPayeeAccountBank());
				if (element.getAuditor() != null)
					row.getCell(PaymentBillContants.COL_AUDITOR).setValue(
							element.getAuditor().getName());
				row.getCell(PaymentBillContants.COL_AUDITDATE).setValue(
						element.getAuditDate());
				row.getCell("contractId").setValue(element.getContractBillId());

				row.getCell(PaymentBillContants.COL_ISRESPITE).setValue(
						(Boolean) isRespiteMap.get(element.getId().toString()));
				// 求合计行本币值
				localAmtAll = FDCHelper.add(localAmtAll, element.getLocalAmt());
				//合同内工程款（原币、本币)
				projectPriceInContractOri = FDCHelper.add(projectPriceInContractOri, priceInContractOriVal);
				projectPriceInContract = FDCHelper.add(projectPriceInContract, priceInContractVal);
			}
		}
		KDTFootManager footRowManager = tblPaymentBill.getFootManager();
		IRow footRow = null;
		if (footRowManager == null) {
			footRowManager = new KDTFootManager(tblPaymentBill);
			footRowManager.addFootView();
			tblPaymentBill.setFootManager(footRowManager);
			footRow = tblPaymentBill.addFootRow(0);
			footRow.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.getAlignment("right"));
			tblPaymentBill.getIndexColumn().setWidthAdjustMode(
					KDTIndexColumn.WIDTH_MANUAL);
			tblPaymentBill.getIndexColumn().setWidth(60);
			footRow.getCell(PaymentBillContants.COL_LOCALAMT).setValue(localAmtAll);
			
			//合同内工程款（原币、本币)
			footRow.getCell("projectPriceInContractOri").setValue(projectPriceInContractOri);
			footRow.getCell("projectPriceInContract").setValue(projectPriceInContract);
			
			footRow.getStyleAttributes().setNumberFormat(
					FDCHelper.strDataFormat);
			footRow.getStyleAttributes().setBackground(
					FDCTableHelper.totalColor);
			footRowManager.addIndexText(0, "合计");
		} else {
			footRow = tblPaymentBill.getFootRow(0);
			footRow.getCell(PaymentBillContants.COL_LOCALAMT).setValue(localAmtAll);
			
			//合同内工程款（原币、本币)
			footRow.getCell("projectPriceInContractOri").setValue(projectPriceInContractOri);
			footRow.getCell("projectPriceInContract").setValue(projectPriceInContract);
		}
	}
	
	/*
	 * 描述：生成获取单据的Selector
	 */
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		
		//contractBillId
		selectors.add("contractBillId");
		selectors.add("billStatus");
		selectors.add("number");
		selectors.add("fdcPayReqNumber");
		selectors.add("settlementStatus");
		selectors.add("fiVouchered");
		selectors.add("amount");
		selectors.add("exchangeRate");
		selectors.add("payDate");
		selectors.add("localAmt");
		selectors.add("createTime");
		selectors.add("summary");
		selectors.add("usage");
		selectors.add("description");
		selectors.add("payeeBank");
		selectors.add("payeeAccountBank");
		selectors.add("auditDate");
		//fdcPayType
		selectors.add("fdcPayType.name");
		selectors.add("creator.name");
		selectors.add("auditor.number");
		selectors.add("auditor.name");
		selectors.add("fdcPayeeName.name");
		selectors.add("actFdcPayeeName.name");
		//怎么会有个.name? 屏蔽掉
//		selectors.add(".name");
		selectors.add("currency.name");
		selectors.add("currency.precision");
		/*售前临时内容
		selectors.add("voucherNumber");
		selectors.add("voucherType.name");
		*/
		
		selectors.add("projectPriceInContract");//biaobiao
		return selectors;
	}
	

}
