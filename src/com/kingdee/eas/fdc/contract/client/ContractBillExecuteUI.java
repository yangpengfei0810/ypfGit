/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.server.SQLDataAccessFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillExecuteDataHander;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractExecuteData;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentBillEditUI;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����:��ִͬ�����һ����
 * @author jackwang  date:2007-5-22 <p>
 * @version EAS5.3
 */
public class ContractBillExecuteUI extends AbstractContractBillExecuteUI {
	private static final Logger logger = CoreUIObject.getLogger(ContractBillExecuteUI.class);
	
	//�Ƿ�ֻ��ʾ���ı���ͬ
	private boolean isDisplayConNoText = true;
	//�Ƿ�ֻ��ʾ��ͬ
	private boolean isDisplayContract = true;
	
	
	private boolean isMoreSett = false;	//���ö�ν���
	private boolean allNotPaidParam = false; //�Ƿ����ù�ʽ ���깤δ���� �� �ۼ��깤������-�Ѹ�� Added By Owen_wen 2010-07-28
	private boolean isDisplayPlan = false; // �Ƿ���ʾ

	/**
	 * output class constructor
	 */
	public ContractBillExecuteUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.btnQueryScheme.setVisible(false);
		getSytemParm();
	}

	/**
	 * ���ñ������
	 */
	protected void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		table.setRefresh(false);
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		table.getColumn("contractBill.amt").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		table.getColumn("contractBill.oriAmt").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));			
		table.getColumn("contractBill.oriAmt").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		
		table.getColumn("payPlanAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("payRealAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("contractBillLastAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("notPayed").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("contractBill.amt").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
						
		table.getColumn("payPlanAmt").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("payRealAmt").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("contractBillLastAmt").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("notPayed").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("changeAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("changeAmt").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("totalSettPrice").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("totalSettPrice").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("projectPriceInContract").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("projectPriceInContract").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);		
		table.getColumn("allNotPaid").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("allNotPaid").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("completePrjAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("completePrjAmt").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("allFinvoiceAmt").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("allFinvoiceAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("allFinvoiceOriAmt").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("allFinvoiceOriAmt").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("adjustAmount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("adjustAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.setColumnMoveable(true);
//		FDCTableHelper.setColumnMoveable(table, true);
		try {
			isDisplayPlan = isDisplayPlan();
		} catch (Exception e) {
			handUIException(e);
		}
		table.getColumn("payPlanDate").getStyleAttributes().setHided(!isDisplayPlan);
		table.getColumn("payPlanAmt").getStyleAttributes().setHided(!isDisplayPlan);
		table.getColumn("payPlanSrcAmt").getStyleAttributes().setHided(!isDisplayPlan);
		actionViewPayPlan.setVisible(isDisplayPlan);
		actionViewPayPlan.setEnabled(isDisplayPlan);
		table.getTreeColumn().setDepth(2);
		this.menuBiz.add(actionDisplayAll);
		this.menuBiz.add(actionDisplayContract);
		this.menuBiz.add(actionDisplayConNoText);
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.actionImportData.setVisible(false);
//		this.menuBiz.setVisible(false);
//		this.menuBiz.setEnabled(false);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
//		this.actionQuery.setVisible(false);
//		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		//BT484834��ť����ʾ  by hpw 2010.11.16
//		this.menuView.setVisible(false);
		this.menuItemPreVersion.setVisible(false);
		this.menuItemNextVersion.setVisible(false);
		this.menuItemFirstVersion.setVisible(false);
		this.menuItemLastVersion.setVisible(false);
		this.menuItemPreVersion.setEnabled(false);
		this.menuItemNextVersion.setEnabled(false);
		this.menuItemFirstVersion.setEnabled(false);
		this.menuItemLastVersion.setEnabled(false);
		
		this.menuItemSubmit.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuItemRecense.setVisible(false);
		actionExpand.setEnabled(true);
		actionExpand.setVisible(true);
		actionShorten.setEnabled(true);
		actionShorten.setVisible(true);
		actionShorten.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_controlsinglerange"));
		actionExpand.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_uniterange"));
		actionViewContract.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_assistantlistaccount"));
		actionViewPayment.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_assistantgeneralledger"));
		actionViewPayPlan.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showlist"));
		actionDisplayAll.putValue(Action.SMALL_ICON,EASResource.getIcon("imgTbtn_assistantlistaccount"));
		actionDisplayContract.putValue(Action.SMALL_ICON,EASResource.getIcon("imgTbtn_assistantlistaccount"));
		actionDisplayConNoText.putValue(Action.SMALL_ICON,EASResource.getIcon("imgTbtn_assistantlistaccount"));
	}
	
	protected void fillTable() throws Exception {
		tblMain.removeRows(false);
		tblMain.setUserObject(null);
		tblMain.getTreeColumn().setDepth(2);
		final Set projectIds = getSelectObjLeafIds(true);
		if (projectIds != null && !projectIds.isEmpty()) {
			final EntityViewInfo view = (EntityViewInfo) mainQuery.clone();
	
			LongTimeDialog dialog = UITools.getDialog(this);
			if (dialog == null)
				return;
			dialog.setLongTimeTask(new ILongTimeTask() {
				public Object exec() throws Exception {
					return getExecuteDatas(projectIds, view);
				}

				public void afterExec(Object result) throws Exception {
					fillTable((List) result);
				}
			});
			dialog.show();
		}
	}
	private List getExecuteDatas(Set projectIds, EntityViewInfo oldView) throws Exception {
		Map params = new HashMap();
		params.put("isDisplayPlan", Boolean.valueOf(isDisplayPlan));
		params.put("isMoreSett", Boolean.valueOf(isMoreSett));
		params.put("allNotPaidParam", Boolean.valueOf(allNotPaidParam));
		params.put("EntityViewInfo", oldView);
		return ContractBillExecuteDataHander.getContractExeData(projectIds, params, isDisplayContract, isDisplayConNoText);
	}

	private void fillRowByContractExeData(IRow row, ContractExecuteData data) {
		ContractBillInfo contract = data.getContract();
		row.getCell("project.name").setValue(contract.getCurProject().getName());
		row.getCell("contractBill.number").setValue(contract.getNumber());
		row.getCell("contractBill.name").setValue(contract.getName());
		// by tim_gao ���Ӻ�ͬ����
		row.getCell("contractType").setValue(contract.getContractType().getName());
		//by biaobiao ���Ӻ�ͬ״̬
		row.getCell("contractBill.status").setValue(contract.getState());
		
		//add by ypf on 20141214 ���Ӻ�ͬ�������ڡ�OA����������
		row.getCell("OAWorkFlowNumber").setValue(contract.getOAWorkFlowNumber()!=null?contract.getOAWorkFlowNumber():"");//
		
		Date dateTime = contract.getAuditTime()!=null?contract.getAuditTime():null;
		if(dateTime==null && contract.getSignDate()!=null)
		{
			dateTime = contract.getSignDate()!=null?contract.getSignDate():null;
		}
		
		row.getCell("auditDate").setValue(dateTime!=null?dateTime:"");
		
		row.getCell("currency.name").setValue(contract.getCurrency().getName());
		row.getCell("contractBill.oriAmt").setValue(contract.getOriginalAmount());
		row.getCell("contractBill.amt").setValue(contract.getAmount());
		row.getCell("contractLastSrcAmt").setValue(data.getContractLastSrcAmount());
		row.getCell("contractBillLastAmt").setValue(data.getContractLastAmount());
		row.getCell("contractBill.hasSettled").setValue(Boolean.valueOf(contract.isHasSettled()));
		row.getCell("payPlanAmt").setValue(data.getPlanPayAmount());
		row.getCell("payPlanSrcAmt").setValue(data.getPlanPaySrcAmount());
		row.getCell("notPayed").setValue(data.getNotPay());
		row.getCell("contract.id").setValue(contract.getId().toString());
		if (contract.getPartB() != null) {
			row.getCell("partB").setValue(contract.getPartB().getName());
		}
		if (contract.getRespPerson() != null) {
			row.getCell("respPerson").setValue(contract.getRespPerson().getName());
		}
		row.getCell("changeAmt").setValue(data.getChangeAmount());
		row.getCell("totalSettPrice").setValue(data.getTotalSettPrice());
		row.getCell("projectPriceInContract").setValue(data.getProjectPriceInContract());//-biaobiao
		row.getCell("allNotPaid").setValue(data.getCompleteNotPay());
		row.getCell("completePrjAmt").setValue(data.getCompleteProjectAmount());
//		if(contract.getNumber().equals("HY01.01.JA.016")){
//			System.out.println(data.getRealPayAmount());
//		}
		row.getCell("payRealAmt").setValue(data.getRealPayAmount());//-biaobiao
		row.getCell("payRealSrcAmt").setValue(data.getRealPaySrcAmount());//-biaobiao
//		row.getCell("payRealAmt").setValue(data.getProjectPriceInContract());//-biaobiao
//		row.getCell("payRealSrcAmt").setValue(data.getProjectPriceInContract());//-biaobiao
		
		row.getCell("notSrcPayed").setValue(FDCHelper.subtract(data.getContractLastSrcAmount(), data.getRealPaySrcAmount()));
		if(null!=data.getPayAmountMap()){
			row.getCell("allFinvoiceAmt").setValue(data.getPayAmountMap().get(data.getContract().getId().toString()));
		}
		if(null!=data.getPayAmountOriMap()){
			row.getCell("allFinvoiceOriAmt").setValue(data.getPayAmountOriMap().get(data.getContract().getId().toString()));
		}
		
		
	}

	private void fillRowByNotTextContractExeData(IRow row, ContractExecuteData data) {
		ContractWithoutTextInfo contract = data.getNoTextContract();
		row.getCell("project.name").setValue(contract.getCurProject().getName());
		row.getCell("contractBill.number").setValue(contract.getNumber());
		row.getCell("contractBill.name").setValue(contract.getName());
		//by biaobiao ���Ӻ�ͬ״̬
		row.getCell("contractBill.status").setValue(contract.getState());
		
		//add by ypf on 20141214 ���Ӻ�ͬ�������ڡ�OA����������
		row.getCell("OAWorkFlowNumber").setValue(contract.getOAWorkFlowNumber()!=null?contract.getOAWorkFlowNumber():"");//
		
		Date dateTime = contract.getAuditTime()!=null?contract.getAuditTime():null;
		if(dateTime==null && contract.getSignDate()!=null)
		{
			dateTime = contract.getSignDate()!=null?contract.getSignDate():null;
		}
		
		row.getCell("auditDate").setValue(dateTime!=null?dateTime:"");
		
		row.getCell("currency.name").setValue(contract.getCurrency().getName());
		row.getCell("contractBill.oriAmt").setValue(contract.getOriginalAmount());
		row.getCell("contractBill.amt").setValue(contract.getAmount());
		row.getCell("payRealAmt").setValue(data.getRealPayAmount());//-biaobiao
		row.getCell("contract.id").setValue(contract.getId().toString());
		row.getCell("partB").setValue(data.getPartB());
		// row.getCell("projectPriceInContract").setValue(data.getProjectPriceInContract());
		row.getCell("allNotPaid").setValue(data.getCompleteNotPay());
		row.getCell("completePrjAmt").setValue(data.getCompleteProjectAmount());
	}
	
	//���������ϸ���˴��������Ӷ�����ϸ���ֶ�
	private void fillPayDatas(IRow row, ContractExecuteData child ,ContractExecuteData data) {
		BigDecimal adjustAllAmt = FDCConstants.ZERO;
		row.getCell("payPlan.id").setValue(child.getPlanPayId());
		row.getCell("payPlanDate").setValue(child.getPlanPayDate());
		row.getCell("payPlanSrcAmt").setValue(child.getPlanPaySrcAmount());
		row.getCell("payPlanAmt").setValue(child.getPlanPayAmount());
		row.getCell("paymentBill.id").setValue(child.getRealPayId());
//		System.out.println("-----------���id��"+child.getRealPayId());
		//R101221-207��ִͬ��������ŵ�ʵ�ʸ������ڸ�Ϊ����ϵ�ҵ������ by zhiyuan_tang
//		row.getCell("payRealDate").setValue(child.getRealPayDate());
		row.getCell("payRealDate").setValue(child.getPayBizDate());
		
		//add by ypf on 20141215,��ʾ�����Ӧƾ֤��ƾ֤��
		if(child.getRealPayId()!=null && !child.getRealPayId().equals(""))
		{
			try {
				PaymentBillInfo paymentBillInfo = PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(" where id='"+child.getRealPayId()+"'");
				if(paymentBillInfo.getVoucher()!=null && paymentBillInfo.getVoucher().getId()!=null){
					String sql = "select fnumber from T_GL_Voucher where fid ='"+paymentBillInfo.getVoucher().getId()+"'";
					IRowSet rs = SQLDataAccessFactory.getRemoteInstance().getRowSet(sql);
					String voucherNumber = "";
					if(rs!=null && rs.size()>0)
					{
						while(rs.next())
						{
							voucherNumber = rs.getString("fnumber");
						}
					}
					row.getCell("voucherNumber").setValue(voucherNumber);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else{
			System.out.println("�п�����");
		}
		
		row.getCell("payRealAmt").setValue(child.getRealPayAmount());//-biaobiao
		row.getCell("payRealSrcAmt").setValue(child.getRealPaySrcAmount());//-biaobiao
//		row.getCell("payRealAmt").setValue(child.getProjectPriceInContract());//-biaobiao
//		row.getCell("payRealSrcAmt").setValue(child.getProjectPriceInContract());//-biaobiao
		row.getCell("projectPriceInContract").setValue(child.getProjectPriceInContract());//-biaobiao
		
		String payMentID = child.getRealPayId() != null ? child.getRealPayId().toString() : ""; // �е����Ǹ���ƻ���������û�и��ID��
		if(payMentID!=null&&!"".equals(payMentID)){
			tblMain.checkParsed();
			if(data.getPayAmountMap()!=null&&!data.getPayAmountMap().isEmpty()){
				if(data.getPayAmountMap().get(payMentID)!=null){
					if(null!=row.getCell("allFinvoiceAmt")){
						row.getCell("allFinvoiceAmt").setValue(FDCHelper.toBigDecimal(data.getPayAmountMap().get(payMentID)));
					}
				}
			}
			if(data.getPayAmountOriMap()!=null&&!data.getPayAmountOriMap().isEmpty()){
				if(data.getPayAmountOriMap().get(payMentID)!=null){
					if(null!=row.getCell("allFinvoiceOriAmt")){
						row.getCell("allFinvoiceOriAmt").setValue(FDCHelper.toBigDecimal(data.getPayAmountOriMap().get(payMentID)));
					}
				}
			}
		}
		//��������  by duyu at 2011-8-17
		int level = row.getTreeLevel()+1;
		if(data.getAdjustSumMap() != null && !data.getAdjustSumMap().isEmpty()){
			Map leafMap = data.getAdjustSumMap();
			if(null!=leafMap.get(child.getRealPayId())){
				Map lastMap = (Map) leafMap.get(child.getRealPayId());
				Set keySet = lastMap.keySet() ;
				Iterator it = keySet.iterator();
				while(it.hasNext()){
					String adjustSum = (String) it.next();
					BigDecimal adjustAmt = (BigDecimal) lastMap.get(adjustSum);
					adjustAllAmt = FDCHelper.add(adjustAllAmt, adjustAmt);
					IRow row2 = tblMain.addRow();
					row2.setTreeLevel(level);
					row2.getCell("adjustSum").setValue(adjustSum);
					row2.getCell("adjustAmount").setValue(adjustAmt);
					row2.getStyleAttributes().setHided(true);
					int dep =tblMain.getTreeColumn().getDepth();
					if(dep < level+1){
						tblMain.getTreeColumn().setDepth(level+1);
					}
				}
			}
		}
		row.setCollapse(true);
		//��������ϼ�
		if(adjustAllAmt.compareTo(FDCHelper.ZERO)==1){
			row.getCell("adjustAmount").setValue(adjustAllAmt);
		}
	}
	private void fillTable(List datas) {
		if (datas != null && !datas.isEmpty()) {
			tblMain.getTreeColumn().setDepth(2);
			BigDecimal amt = FDCConstants.ZERO;
			BigDecimal contractBillLastAmt = FDCConstants.ZERO;
			BigDecimal planAmt = FDCConstants.ZERO;
			BigDecimal realAmt = FDCConstants.ZERO;
			BigDecimal changeAmt = FDCConstants.ZERO;
			BigDecimal totalSettPrice = FDCConstants.ZERO;
			BigDecimal projectPriceInContract = FDCConstants.ZERO;
			BigDecimal allNotPaid = FDCConstants.ZERO;
			BigDecimal noPayAmt = FDCConstants.ZERO;
			BigDecimal allFinvoiceAmt = FDCConstants.ZERO;
			for (int i = 0; i < datas.size(); ++i) {
				ContractExecuteData data = (ContractExecuteData) datas.get(i);
				IRow row = tblMain.addRow();
				if (data.getContract() != null || data.getNoTextContract() != null) {
					row.setTreeLevel(0);
					if (data.getContract() != null) {
						fillRowByContractExeData(row, data);
					} else {
						fillRowByNotTextContractExeData(row, data);
					}
					amt = FDCHelper.add(amt, row.getCell("contractBill.amt").getValue());
					contractBillLastAmt = FDCHelper.add(contractBillLastAmt, row.getCell("contractBillLastAmt").getValue());
					planAmt = FDCHelper.add(planAmt, row.getCell("payPlanAmt").getValue());
					realAmt = FDCHelper.add(realAmt, row.getCell("payRealAmt").getValue());
					changeAmt = FDCHelper.add(changeAmt, row.getCell("changeAmt").getValue());
					totalSettPrice = FDCHelper.add(totalSettPrice, row.getCell("totalSettPrice").getValue());
					projectPriceInContract = FDCHelper.add(projectPriceInContract, row.getCell("projectPriceInContract").getValue());
					allNotPaid = FDCHelper.add(allNotPaid, row.getCell("allNotPaid").getValue());
					noPayAmt = FDCHelper.add(noPayAmt, row.getCell("notPayed").getValue());
					allFinvoiceAmt = FDCHelper.add(allFinvoiceAmt, row.getCell("allFinvoiceAmt").getValue());
				}
				BigDecimal invoiceAmt = FDCConstants.ZERO;
				BigDecimal invoiceOriAmt = FDCConstants.ZERO;
				if(data.getChildren() != null && !data.getChildren().isEmpty()){
					for(int j = 0; j < data.getChildren().size(); ++j){
						ContractExecuteData child = (ContractExecuteData) data.getChildren().get(j);
						IRow row1 = tblMain.addRow();
						row1.setTreeLevel(1);
						fillPayDatas(row1, child,data);
						invoiceAmt = FDCHelper.add(invoiceAmt, row1.getCell("allFinvoiceAmt").getValue()==null?FDCHelper.ZERO:row1.getCell("allFinvoiceAmt").getValue());
						invoiceOriAmt = FDCHelper.add(invoiceOriAmt, row1.getCell("allFinvoiceOriAmt").getValue()==null?FDCHelper.ZERO:row1.getCell("allFinvoiceOriAmt").getValue());
					}
				}
				row.getCell("allFinvoiceAmt").setValue(FDCHelper.toBigDecimal(invoiceAmt));
				row.getCell("allFinvoiceOriAmt").setValue(FDCHelper.toBigDecimal(invoiceOriAmt));
			}
			IRow row = this.tblMain.addRow();
			row.setTreeLevel(0);
			row.getCell("project.name").setValue("�ϼ�");
			row.getCell("contractBill.amt").setValue(amt);
			row.getCell("contractBillLastAmt").setValue(contractBillLastAmt);
			row.getCell("payPlanAmt").setValue(planAmt);
			if (realAmt == null) {
				realAmt = FDCHelper.ZERO;
			}
			row.getCell("payRealAmt").setValue(realAmt);//biaobiao
			row.getCell("notPayed").setValue(noPayAmt);
			// �ϼƱ����� ��ʵ�ֲ�ֵ �깤δ����
			row.getCell("changeAmt").setValue(changeAmt);
			row.getCell("totalSettPrice").setValue(totalSettPrice);
			row.getCell("projectPriceInContract").setValue(projectPriceInContract);
			row.getCell("allNotPaid").setValue(allNotPaid);
			row.getCell("allFinvoiceAmt").setValue(allFinvoiceAmt);
			row.getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
		}
	}
	
	/**
	 * �������ϵͳ����<p>
	 * 1. �Ƿ����ö�ν��㣻2. �Ƿ����ù�ʽ ���깤δ���� �� �ۼ��깤������-�Ѹ���
	 * @author owen_wen 2010-8-13
	 *
	 */
	private void getSytemParm() {
		try {
			isMoreSett = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MORESETTER);
			allNotPaidParam = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MORESETTER_ALLNOTPAID);
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
	}
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		IContractBill contract = ContractBillFactory.getRemoteInstance();
		if (e != null && e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			this.setCursorOfWair();
			try {
				// modify to view when doubleClick row by Jacky 2005-1-7
				if (e.getType() == 0) {
					return;
				}
				int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
				if (tblMain.getRow(rowIndex).getCell("contract.id").getValue() != null) {
//					view(null, tblMain, "contract.id", ContractBillEditUI.class.getName());
					String id = (String)tblMain.getRow(rowIndex).getCell("contract.id").getValue();
					//�жϸü�¼Ϊ��ͬ�������ı���ͬ
					if(contract.exists(new ObjectUuidPK(BOSUuid.read(id)))){
						view(null, tblMain, "contract.id", ContractBillEditUI.class.getName());
					}else{
						view(null,tblMain,"contract.id",ContractWithoutTextEditUI.class.getName());
					}
					
				} else if (tblMain.getRow(rowIndex).getCell("paymentBill.id").getValue() != null) {
					view(null, tblMain, "paymentBill.id", PaymentBillEditUI.class.getName());
				} else if(tblMain.getRow(rowIndex).getCell("payPlan.id").getValue() != null){
					view(null,tblMain,"payPlan.id",ContractPayPlanEditUI.class.getName());
				}
			} finally {
				this.setCursorOfDefault();
			}
		}
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}
	/**
	 * ��ȡquery�е����������ƣ����ع��༭/ɾ��ʱ��ȡ�����ã�Ĭ��ֵΪ"id"���̳����������
	 * 
	 */
	protected String getKeyFieldName() {
		return "contractBill.number";
	}
	
	protected FilterInfo getDefaultFilterForQuery() {
		return new FilterInfo();
	}

	protected void tblMain_doRequestRowSetForHasQueryPK(RequestRowSetEvent e) {

	}

	protected String getEditUIName() {
		return null;
	}
	//���ز鿴����
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		
	}
	
	protected boolean isCanOrderTable() {
		return false;
	}
	
    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    	IContractBill contract = ContractBillFactory.getRemoteInstance();
    	checkSelected(tblMain) ;
    	checkSelected(tblMain,"contract.id");
     	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		String id = (String)tblMain.getRow(rowIndex).getCell("contract.id").getValue();
		//�жϸü�¼Ϊ��ͬ�������ı���ͬ
		if(contract.exists(new ObjectUuidPK(BOSUuid.read(id)))){
			view(null, tblMain, "contract.id", ContractBillEditUI.class.getName());
		}else{
			view(null,tblMain,"contract.id",ContractWithoutTextEditUI.class.getName());
		}
//    	view(e,tblMain,"contract.id",ContractBillEditUI.class.getName());		
    }

    /**
     * output actionViewPayment_actionPerformed method
     */
    public void actionViewPayment_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(tblMain) ;
    	checkSelected(tblMain,"paymentBill.id"); 
    	
    	view(e,tblMain,"paymentBill.id",PaymentBillEditUI.class.getName());
    }
    public void actionViewPayPlan_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected(tblMain) ;
    	checkSelected(tblMain,"payPlan.id"); 
    	
    	view(e,tblMain,"payPlan.id",ContractPayPlanEditUI.class.getName());
    }
	
    public void view(ActionEvent e,KDTable table,String keyField,String uiName) throws Exception
    {		
    	UIContext uiContext = new UIContext(this);
		IRow row =table.getRow(table.getSelectManager().getActiveRowIndex());
		if(row!=null){
			String id = (String)row.getCell(keyField).getValue();
			uiContext.put(UIContext.ID, id);
			
	        IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(uiName, uiContext, null,
	                    OprtState.VIEW);		        

	        uiWindow.show();
		}	
    }
	protected void execQuery() {
		try {
			fillTable();
		} catch (Exception e) {
			handUIException(e);
		}
	}
	private CustomerQueryPanel filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}
	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ContractBillExecFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	
	/**
	 * 
	 * ��������鵱ǰ���ݵ�Table�Ƿ�ѡ����
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected(KDTable table) {
		if (table.getRowCount() == 0 || table.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}
	
	public void checkSelected(KDTable table,String keyField) {		
    	int rowIndex = table.getSelectManager().getActiveRowIndex();
		if (table.getRow(rowIndex).getCell(keyField).getValue()==null) {
			MsgBox.showWarning(this,"��ѡ����ȷ����");
	        SysUtil.abort();
	    }		
	}
	//�Ƿ���ʾ��ִͬ�������ļƻ���
	private boolean isDisplayPlan() throws Exception{
		return this.isParamUse(FDCConstants.FDC_PARAM_CONTRACTEXEC);
	}
	
	public void actionExpand_actionPerformed(ActionEvent e) throws Exception {
		for(int i=0;i<tblMain.getRowCount();i++){
			IRow row=tblMain.getRow(i);
			if(row.getTreeLevel()==1){
				tblMain.getRow(i-1).setCollapse(false);
				row.getStyleAttributes().setHided(false);
			}
		}
	}
	
	public void actionShorten_actionPerformed(ActionEvent e) throws Exception {
		for(int i=0;i<tblMain.getRowCount();i++){
			IRow row=tblMain.getRow(i);
			if(row.getTreeLevel()==1){
				tblMain.getRow(i-1).setCollapse(true);
				row.getStyleAttributes().setHided(true);
			}
		}
	}
	/**
	 * ȫ����ʾ
	 */
	public void actionDisplayAll_actionPerformed(ActionEvent e) throws Exception {
		this.isDisplayConNoText = true;
		this.isDisplayContract = true;
		fillTable();
	}
	/**
	 * ֻ��ʾ���ı���ͬ
	 */
	public void actionDisplayConNoText_actionPerformed(ActionEvent e) throws Exception {
		this.isDisplayConNoText = true;
		this.isDisplayContract = false;
		fillTable();
	}
	/**
	 * ֻ��ʾ��ͬ
	 */
	public void actionDisplayContract_actionPerformed(ActionEvent e) throws Exception {
		this.isDisplayConNoText = false;
		this.isDisplayContract = true;
		fillTable();
	}
			
}