package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.List;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.dao.query.server.SQLDataAccessFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.WriteBackBillStatusFacadeFactory;
import com.kingdee.eas.fdc.contract.app.WriteBackBillStatusFacadeControllerBean;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractBillListUIPIEx extends ContractBillListUI {

	public ContractBillListUIPIEx() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		btnAddContent.setEnabled(false);

		// add by ypf on 20131224 新增解除框架合约
		this.btnUnProgram.setEnabled(true);
	}

	// add by ypf on 20121022 修改editui数据改变后 listui数据没有修改的bug
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		// super.prepareUIContext(uiContext, e);
		com.kingdee.bos.ui.face.ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			BOSUuid projId = ((CurProjectInfo) getProjSelectedTreeNode()
					.getUserObject()).getId();
			BOSUuid typeId = null;
			if (getTypeSelectedTreeNode() != null
					&& getTypeSelectedTreeNode().isLeaf()
					&& (getTypeSelectedTreeNode().getUserObject() instanceof ContractTypeInfo))
				typeId = ((ContractTypeInfo) getTypeSelectedTreeNode()
						.getUserObject()).getId();
			uiContext.put("projectId", projId);
			uiContext.put("contractTypeId", typeId);
		}
		// 传入listui对象
		uiContext.put("Owner", this);
		uiContext.put("isRefresh", "true");
	}

	public void onShow() throws Exception {
		super.onShow();
		btnAddContent.setEnabled(false);
	}

	// private Map contentMap = new HashMap();
	// private Map attachMap = new HashMap();

	// super不能注释，不注释又报错
	public void onGetRowSet(IRowSet rowSet) {
		// super.onGetRowSet(rowSet);
		// try {
		// rowSet.beforeFirst();
		//
		// Set contractIds = new HashSet();
		// while (rowSet.next()) {
		// String id = rowSet.getString("id");
		// contractIds.add(id);
		// }
		// Map retValue = ContractBillFactory.getRemoteInstance()
		// .getOtherInfo(contractIds);
		// contentMap = (Map) retValue.get("contentMap");
		// attachMap = (Map) retValue.get("attachMap");
		// auditMap = (Map) retValue.get("auditMap");
		// } catch (Exception e) {
		// this.handUIException(e);
		// } finally {
		// try {
		// rowSet.beforeFirst();
		// } catch (SQLException e) {
		// this.handUIException(e);
		// }
		// }
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblMain);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
		// 取列值

		String state = (row.getCell("state").getValue() != null) ? row.getCell(
				"state").getValue().toString() : "";
		String isOAAudit = (row.getCell("isOAAudit").getValue() != null) ? row
				.getCell("isOAAudit").getValue().toString() : "";

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

	// -----------杨人代码 begin-------------------------------------------
	private ContractBillCollection getConCol(String idKey) throws Exception {
		ContractBillCollection collection = null;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("programmingContract.*"));
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", idKey));
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.setFilter(filterInfo);
		viewInfo.setSelector(sic);
		collection = ContractBillFactory.getRemoteInstance()
				.getContractBillCollection(viewInfo);
		return collection;
	}

	// ------end-----------------------------------------------------------

	public void actionAudit_actionPerformed(ActionEvent arg0) throws Exception {
		// -----------杨人代码 begin-------------------------------------------
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		// ------end-----------------------------------------------------------

		checkSelected(tblMain);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("state").getValue() != null) ? row.getCell(
				"state").getValue().toString() : "";
		String isOAAudit = (row.getCell("isOAAudit").getValue() != null) ? row
				.getCell("isOAAudit").getValue().toString() : "";

		System.out.println("----------------------------------------------"
				+ state + "-----" + isOAAudit);
		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经走OA流程审批，不能在EAS审批");
			SysUtil.abort();
		} else {
			super.actionAudit_actionPerformed(arg0);

			// -----------杨人代码 begin-------------------------------------------
			for (int i = 0; i < idList.size(); i++) {
				String idKey = idList.get(i).toString();
				ContractBillCollection coll = getConCol(idKey);
				for (int j = 0; j < coll.size(); j++) {
					ContractBillInfo contract = coll.get(j);
					if (contract.getProgrammingContract() != null) {
						ProgrammingContractInfo pcInfo = contract
								.getProgrammingContract();

						BigDecimal totalSplitAmount = ContractBillFacadeFactory
								.getRemoteInstance().getTotalSplitAmount(
										pcInfo.getId().toString());
						// 累计分配=累计分配+本次分配；
						BigDecimal calTotalSplitAmount = FDCHelper.add(
								totalSplitAmount, contract.getSplitAmt());
						ContractBillFacadeFactory.getRemoteInstance()
								.updateTotalSplitAmount(
										pcInfo.getId().toString(),
										calTotalSplitAmount);
					}
				}
			}
			//------end----------------------------------------------------------
			// -
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// -----------杨人代码 begin-------------------------------------------
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		// ------end-----------------------------------------------------------

		checkSelected(tblMain);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("state").getValue() != null) ? row.getCell(
				"state").getValue().toString() : "";
		String isOAAudit = (row.getCell("isOAAudit").getValue() != null) ? row
				.getCell("isOAAudit").getValue().toString() : "";

		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经通过OA审批，不能进行反审批");
			SysUtil.abort();
		} else {
			super.actionUnAudit_actionPerformed(e);

			// -----------杨人代码 begin-------------------------------------------
			for (int i = 0; i < idList.size(); i++) {
				String idKey = idList.get(i).toString();
				ContractBillCollection coll = getConCol(idKey);
				for (int j = 0; j < coll.size(); j++) {
					ContractBillInfo contract = coll.get(j);
					if (contract.getProgrammingContract() != null) {
						ProgrammingContractInfo pcInfo = contract
								.getProgrammingContract();

						BigDecimal totalSplitAmount = ContractBillFacadeFactory
								.getRemoteInstance().getTotalSplitAmount(
										pcInfo.getId().toString());
						// 累计分配=累计分配-本次分配；
						BigDecimal calTotalSplitAmount = FDCHelper.subtract(
								totalSplitAmount, contract.getSplitAmt());
						ContractBillFacadeFactory.getRemoteInstance()
								.updateTotalSplitAmount(
										pcInfo.getId().toString(),
										calTotalSplitAmount);
					}
				}
			}
			//------end----------------------------------------------------------
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// modify by ll 2012-10-16 修改在editUI提交单据并走OA流程，但ListUI单据状态没有改变仍然可以修改

		checkSelected(tblMain);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("state").getValue() != null) ? row.getCell(
				"state").getValue().toString() : "";
		String isOAAudit = (row.getCell("isOAAudit").getValue() != null) ? row
				.getCell("isOAAudit").getValue().toString() : "";

		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经走OA流程审批，不能进行修改");
			SysUtil.abort();
		} else {
			super.actionEdit_actionPerformed(e);
		}
	}

	// add by ypf on 20131224 新增解除框架合约
	public void actionUnProgram_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(tblMain);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
		// 取列值

		String id = (row.getCell("id").getValue() != null) ? row.getCell("id").getValue().toString() : "";

		ContractBillInfo info = new ContractBillInfo();
		IObjectPK pk = new ObjectUuidPK();
		pk.setKeyValue("id", id);
		info = ContractBillFactory.getRemoteInstance().getContractBillInfo(pk);
		ProgrammingContractInfo programmingContract = info.getProgrammingContract();
		if (info != null && programmingContract==null) {
			MsgBox.showInfo("该合同没有关联框架合约，不需要解除");
			SysUtil.abort();
		}

		String sql = "select count(t2.fid) rowNumber                                      "
				+ "from T_CON_ContractCostSplit t1                                        "
				+ "     left join T_CON_ContractCostSplitEntry t2 on t2.fparentid=t1.fid  "
				+ "where t1.fcontractbillid='" + id + "'                ";

		IRowSet rowSet = SQLDataAccessFactory.getRemoteInstance().getRowSet(sql);
		if (rowSet != null && rowSet.size() > 0) {
			if (rowSet.next()) {
				int rowNumber = rowSet.getInt("rowNumber");//检查是否有拆分记录，rowNumber
															// 为0时说明没有拆分
				if (rowNumber > 0) {
					MsgBox.showWarning("解除框架合约前必须先删除合同拆分记录");
				} else {
					if (MsgBox.showConfirm2("确定要解除合同框架合约吗?") == 0) {
						SelectorItemCollection sic = new SelectorItemCollection();
						sic.add("programmingContract");
						System.out.println("-------更新-----getProgrammingContract().getId():"+ info.getProgrammingContract().getId());
						info.setProgrammingContract(null);
						ContractBillFactory.getRemoteInstance().updatePartial(info, sic);
						
						MsgBox.showInfo("解除成功");
					}
				}
			}
		}
	}

}
