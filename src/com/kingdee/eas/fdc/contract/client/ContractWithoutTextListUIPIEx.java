package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class ContractWithoutTextListUIPIEx extends ContractWithoutTextListUI {

	public ContractWithoutTextListUIPIEx() throws Exception {
		super();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
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

	//add by ypf on 20121022  修改editui数据改变后  listui数据没有修改的bug
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		//传入listui对象
		super.prepareUIContext(uiContext, e);
		uiContext.put("Owner", this);
		uiContext.put("isRefresh", "true");
	}
	
	public void actionAudit_actionPerformed(ActionEvent arg0) throws Exception {
		checkSelected();
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
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
		checkSelected();
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
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
		checkSelected();
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
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
		super.actionEdit_actionPerformed(e);
	}
}
