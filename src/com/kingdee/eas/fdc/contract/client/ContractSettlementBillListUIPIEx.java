package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class ContractSettlementBillListUIPIEx extends
		ContractSettlementBillListUI {

	//合同可进行多次结算
	private boolean canSetterMore = false;
	
	public ContractSettlementBillListUIPIEx() throws Exception {
		super();
	}
	
	//add by ypf on 20121022  修改editui数据改变后  listui数据没有修改的bug
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		//传入listui对象
		super.prepareUIContext(uiContext, e);
		uiContext.put("Owner", this);
		uiContext.put("isRefresh", "true");
	}
	
	/**
	 * 
	 * 描述：根据选择的合同显示单据列表
	 * 
	 * @param e
	 * @throws BOSException
	 * @author:Jelon 创建时间：2006-8-18
	 *               <p>
	 */
	
	protected boolean  displayBillByContract(KDTSelectEvent e,EntityViewInfo view) throws BOSException {
//		return false;
//	}	
//	
//	protected void displayBillByContract(EntityViewInfo view)
//			throws BOSException {
		if(view==null){
			return false;
		}

		int pre = getPre(e);
		
		if(canSetterMore){
			getBillListTable().getColumn(ContractSettlementBillContants.COL_ISFINALSETTLE).getStyleAttributes().setHided(false);
		}else{
			getBillListTable().getColumn(ContractSettlementBillContants.COL_ISFINALSETTLE).getStyleAttributes().setHided(true);
			getBillListTable().getColumn("totalSettlePrice").getStyleAttributes().setHided(true);
			getBillListTable().getColumn("totalOriginalAmount").getStyleAttributes().setHided(true);
		}

		//设置精度
		String oriFormat = FDCClientHelper.getNumberFormat(pre,true);
		getBillListTable().getColumn("originalAmount").getStyleAttributes().setNumberFormat(oriFormat);
		getBillListTable().getColumn("totalOriginalAmount").getStyleAttributes().setNumberFormat(oriFormat);
		
		ContractSettlementBillCollection contractSettlementBillCollection = ContractSettlementBillFactory
				.getRemoteInstance().getContractSettlementBillCollection(view);
		for (Iterator iter = contractSettlementBillCollection.iterator(); iter
				.hasNext();) {
			ContractSettlementBillInfo element = (ContractSettlementBillInfo) iter
					.next();

			String contractId = element.getContractBill().getId().toString();
			EntityViewInfo conView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			conView.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("contract.Id", contractId));
			IRow row = getBillListTable().addRow();

			row.getCell("isOAAudit").setValue(element.isIsOAAudit());
			row.getCell("bookedDate").setValue(element.getBookedDate());
			row.getCell("period").setValue(element.getPeriod());
			
			row.getCell(ContractSettlementBillContants.COL_ID).setValue(
					element.getId().toString());
			row.getCell(ContractSettlementBillContants.COL_STATE).setValue(
					element.getState());
			row.getCell(ContractSettlementBillContants.COL_NUMBER).setValue(
					element.getNumber());
			row.getCell(ContractSettlementBillContants.COL_BILLNAME).setValue(
					element.getName());

//			row.getCell(ContractSettlementBillContants.COL_CONTRACTNUMBER)
//					.setValue(element.getContractBill().getNumber());
//			row.getCell(ContractSettlementBillContants.COL_CONTRACTNAME)
//					.setValue(element.getContractBill().getName());
			/**
			 * 按照周鹏的说法，如果以下金额列为0就应该显示0.00,而不是像以下代码未修改之前那样如果为0直接啥都不显示  by Cassiel_peng 2009-9-9
			 */
			if (element.getCurOriginalAmount() != null
					/*&& FDCHelper.ZERO.compareTo(element.getCurOriginalAmount()) != 0*/) {
				row.getCell("originalAmount")
						.setValue(FDCHelper.toBigDecimal(element.getCurOriginalAmount(),2));
			}
			
			if (element.getCurSettlePrice() != null
					/*&& FDCHelper.ZERO.compareTo(element.getCurSettlePrice()) != 0*/) {
				row.getCell(ContractSettlementBillContants.COL_SETTLEPRICE)
						.setValue(element.getCurSettlePrice());
			}
			
			if (element.getTotalOriginalAmount() != null
					/*&& FDCHelper.ZERO.compareTo(element.getTotalOriginalAmount()) != 0*/) {
				row.getCell("totalOriginalAmount")
						.setValue(element.getTotalOriginalAmount());
			}
			
			
			if (element.getTotalSettlePrice() != null
					/*&& FDCHelper.ZERO.compareTo(element.getTotalSettlePrice()) != 0*/) {
				row.getCell("totalSettlePrice")
						.setValue(element.getTotalSettlePrice());
			}

			if (element.getQualityGuarante() != null
					/*&& FDCHelper.ZERO.compareTo(element.getQualityGuarante()) != 0*/) {
				row.getCell(ContractSettlementBillContants.COL_QUALITYGUARANTE)
						.setValue(element.getQualityGuarante());
			}
			if (element.getBuildArea() != null
					&& FDCHelper.ZERO.compareTo(element.getBuildArea()) != 0) {
				row.getCell(ContractSettlementBillContants.COL_BUILDAREA)
						.setValue(element.getBuildArea());
			}
			if (element.getUnitPrice() != null
					/*&& FDCHelper.ZERO.compareTo(element.getUnitPrice()) != 0*/) {
				row.getCell(ContractSettlementBillContants.COL_UNITPRICE)
						.setValue(element.getUnitPrice());
			}
			row.getCell(ContractSettlementBillContants.COL_INFOPRICE).setValue(
					element.getInfoPrice());

			row.getCell(ContractSettlementBillContants.COL_GETFEECRITERIA)
					.setValue(element.getGetFeeCriteria());
			row.getCell(ContractSettlementBillContants.COL_ISFINALSETTLE)
					.setValue(element.getIsFinalSettle());

			row.getCell(ContractSettlementBillContants.COL_CREATOR).setValue(
					element.getCreator().getName());
			row.getCell(ContractSettlementBillContants.COL_CREATETIME)
					.setValue(element.getCreateTime());

			if (element.getAuditor() != null)
				row.getCell(ContractSettlementBillContants.COL_AUDITOR)
						.setValue(element.getAuditor().getName());
			row.getCell(ContractSettlementBillContants.COL_AUDITTIME).setValue(
					element.getAuditTime());

			row.getCell(ContractSettlementBillContants.COL_DESC).setValue(
					element.getDescription());

			if (element.getVoucher() != null)
				row.getCell(ContractSettlementBillContants.COL_VOUCHERNUMBER)
						.setValue(element.getVoucher().getNumber());
			
			row.getCell(COL_DATE).setValue(element.getBookedDate());
			row.getCell(COL_PERIOD).setValue(element.getPeriod());
			row.getCell(ContractSettlementBillContants.COL_ISRESPITE)
					.setValue(new Boolean(element.isIsRespite()));
		}

		return true;
	}

	protected void fetchInitData() throws Exception {
		Map param = new HashMap();
		Map initData = ((IFDCBill)getRemoteInterface()).fetchInitData(param);
		
		//获得当前组织
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		
		try {
			//合同可进行多次结算
			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			if(paramItem.get(FDCConstants.FDC_PARAM_MORESETTER)!=null){
				canSetterMore = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_MORESETTER).toString()).booleanValue();
			}
			
			//启用成本财务一体化
			if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
				isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
			}
		}catch (Exception e) {
			handUIException(e);
		}
	}
	
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblSettlementList);
		int rowIndex = tblSettlementList.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblSettlementList.getRow(rowIndex);
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

	public void actionAudit_actionPerformed(ActionEvent arg0) throws Exception {
		checkSelected(tblSettlementList);
		int rowIndex = tblSettlementList.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblSettlementList.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("state").getValue()!=null)?row.getCell("state").getValue().toString():"";
		String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";

		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经走OA流程审批，不能在EAS审批");
			SysUtil.abort();
		} else {
			super.actionAudit_actionPerformed(arg0);
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblSettlementList);
		int rowIndex = tblSettlementList.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblSettlementList.getRow(rowIndex);
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
		checkSelected(tblSettlementList);
		int rowIndex = tblSettlementList.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblSettlementList.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("state").getValue()!=null)?row.getCell("state").getValue().toString():"";
		String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";

		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经走OA流程审批，不能进行修改");
			SysUtil.abort();
		} else {
			super.actionEdit_actionPerformed(e);
		}
	}
}
