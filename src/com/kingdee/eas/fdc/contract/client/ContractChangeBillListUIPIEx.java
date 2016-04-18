package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeVisaFacadeFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class ContractChangeBillListUIPIEx extends ContractChangeBillListUI {

	public ContractChangeBillListUIPIEx() throws Exception {
		super();
	}
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		btnAudit.setVisible(true);
		btnAudit.setEnabled(true);
		btnUnAudit.setVisible(true);
		btnUnAudit.setEnabled(true);
		
		//add by ypf on 2015年7月20日
		kDContainer1.setTitle("现场签证单列表");
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
	 * 描述：生成获取单据的Selector
	 * @return
	 * @author:liupd
	 * 创建时间：2006-8-3 <p>
	 */
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("state");
		selectors.add("number");
		selectors.add("name");
		selectors.add("bookedDate");
		selectors.add("amount");
		selectors.add("originalAmount");
		selectors.add("createTime");
		selectors.add("balanceAmount");
		selectors.add("hasSettled");
		selectors.add("settleTime");
		selectors.add("forSettAfterSign");
		selectors.add("settAuditAmt");
		selectors.add("settAuditExRate");
		
		selectors.add("changeType.name");
		selectors.add("budgetPerson.name");
		selectors.add("conductDept.name");
		selectors.add("auditor.name");
		selectors.add("changeReason.name");
		selectors.add("mainSupp.name");
		selectors.add("changeAudit.createTime");
		selectors.add("changeAudit.number");
		selectors.add("changeAudit.suppEntry.reckonor.name");
		
		selectors.add("period.number");
		selectors.add("period.periodNumber");
		selectors.add("period.periodYear");
		selectors.add("isOAAudit");
				
		return selectors;
	}
	
	protected void displayBillByContract(EntityViewInfo view)
			throws BOSException {
//		super.displayBillByContract(view);
		ContractChangeBillCollection contractChangeBillCollection = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);
    	for (Iterator iter = contractChangeBillCollection.iterator(); iter.hasNext();) {
			ContractChangeBillInfo element = (ContractChangeBillInfo) iter.next();
			IRow row = getBillListTable().addRow();
			
			row.getCell("isOAAudit").setValue(element.isIsOAAudit());
			row.getCell("bookedDate").setValue(element.getBookedDate());
			row.getCell("period").setValue(element.getPeriod());
			
			row.getCell(ContractChangeBillContants.COL_ID).setValue(element.getId().toString());
			row.getCell(ContractChangeBillContants.COL_STATE).setValue(element.getState());
			
			//序时簿上添加一列    by Cassiel_peng  2009-8-20
			row.getCell(ContractChangeBillContants.COL_AFTERSIGN_STATE).setValue(element.getForSettAfterSign());
			
			if(element.getChangeAudit()!=null&&element.getChangeAudit().getNumber()!=null){
				row.getCell(ContractChangeBillContants.COL_CHANGEAUDIT).setValue(element.getChangeAudit().getNumber());
			}
			row.getCell(ContractChangeBillContants.COL_CHANGETYPE).setValue(element.getChangeType());
			row.getCell(ContractChangeBillContants.COL_NUMBER).setValue(element.getNumber());
			row.getCell(ContractChangeBillContants.COL_BILLNAME).setValue(element.getName());
			row.getCell(ContractChangeBillContants.COL_AMOUNT).setValue(element.getAmount());
			if(element.getChangeAudit()!=null){
				EntityViewInfo v = new EntityViewInfo();
				FilterInfo filter  = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("contractChange.id", element.getId().toString(), CompareType.INCLUDE));
				v.setFilter(filter);
				v.getSelector().add(new SelectorItemInfo("reckonor.name"));
				ChangeSupplierEntryCollection info = ChangeSupplierEntryFactory.getRemoteInstance().getChangeSupplierEntryCollection(v);
				if(info.iterator().hasNext()){
					ChangeSupplierEntryInfo test = (ChangeSupplierEntryInfo)info.iterator().next();
					if(test!=null&&test.getReckonor()!=null&&test.getReckonor().getName()!=null)
						row.getCell(ContractChangeBillContants.COL_BUDGETPERSON).setValue(test.getReckonor().getName());
				}
			}
			if(element.getChangeAudit()!=null)
				row.getCell(ContractChangeBillContants.COL_CONDUCTTIME).setValue(element.getChangeAudit().getCreateTime());
			else
				row.getCell(ContractChangeBillContants.COL_CONDUCTTIME).setValue(element.getCreateTime());
			row.getCell(ContractChangeBillContants.COL_CONDUCTDEPT).setValue(element.getConductDept());
			
			if(FDCUtils.isRunningWorkflow(element.getId().toString())){
				row.getCell(ContractChangeBillContants.COL_SETTLEAMOUNT).setValue(FDCHelper.multiply(element.getSettAuditAmt(), element.getSettAuditExRate()));
			}else{
				row.getCell(ContractChangeBillContants.COL_SETTLEAMOUNT).setValue(element.getBalanceAmount());
			}
			row.getCell(ContractChangeBillContants.COL_HASSETTLED).setValue(Boolean.valueOf(element.isHasSettled()));
			if(element.isHasSettled()){
				row.getCell(ContractChangeBillContants.COL_SETTLEDTIME).setValue(element.getSettleTime());
			}
			String auditor = element.getAuditor() == null ? "" : element.getAuditor().getName();
			row.getCell(ContractChangeBillContants.COL_AUDITOR).setValue(auditor);
			if(element.getMainSupp()!=null)
				row.getCell(ContractChangeBillContants.COL_MAINSUPP).setValue(element.getMainSupp().getName());
			if(element.getChangeAudit()!=null&&element.getChangeAudit().getId()!=null){
				row.getCell(ContractChangeBillContants.COL_CHANGEAUDIT_ID).setValue(element.getChangeAudit().getId().toString());
			}
		}
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblChangeList);
		int rowIndex = tblChangeList.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblChangeList.getRow(rowIndex);
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
		checkSelected(tblChangeList);
		int rowIndex = tblChangeList.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblChangeList.getRow(rowIndex);
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
		
		//add by ypf on 20150723 变更签证确认单审批时，将签证金额更新到变更后金额字段
		String id = getSelectedKeyValue();
		ContractChangeVisaFacadeFactory.getRemoteInstance().saveVisaAmountByChngAuditBillID(id);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblChangeList);
		int rowIndex = tblChangeList.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblChangeList.getRow(rowIndex);
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
		checkSelected(tblChangeList);
		int rowIndex = tblChangeList.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblChangeList.getRow(rowIndex);
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
