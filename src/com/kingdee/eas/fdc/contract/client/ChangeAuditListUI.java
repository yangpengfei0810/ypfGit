/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   ChangeAuditListUI.java
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditBillCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeVisaFacadeFactory;
import com.kingdee.eas.fdc.contract.CopySupplierEntryCollection;
import com.kingdee.eas.fdc.contract.CopySupplierEntryFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IChangeAuditBill;
import com.kingdee.eas.fdc.contract.SupplierContentEntryCollection;
import com.kingdee.eas.fdc.contract.SupplierContentEntryFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class ChangeAuditListUI extends AbstractChangeAuditListUI {
	private static final Logger logger = CoreUIObject.getLogger(com.kingdee.eas.fdc.contract.client.ChangeAuditListUI.class);
	private ChangeAuditListFilterUI filterUI;
	private CommonQueryDialog commonQueryDialog;

	public ChangeAuditListUI() throws Exception {
		filterUI = null;
		commonQueryDialog = null;
		actionRemove.setBindWorkFlow(false);
		actionAheadDisPatch.setBindWorkFlow(false);
	}

	protected String[] getLocateNames() {
		String locateNames[] = new String[5];
		locateNames[0] = "number";
		locateNames[1] = "name";
		locateNames[2] = "changeReason.name";
		locateNames[3] = "auditType.name";
		locateNames[4] = "specialtyType.name";
		return locateNames;
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return ChangeAuditBillFactory.getRemoteInstance();
	}

	public void actionRemove_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		SelectorItemCollection itemCollection = new SelectorItemCollection();
		itemCollection.add("suppEntry");
		itemCollection.add("suppEntry.contractChange.contractBill.isCoseSplit");
		ChangeAuditBillInfo info = ChangeAuditBillFactory.getRemoteInstance()
				.getChangeAuditBillInfo(new ObjectUuidPK(getSelectedKeyValue()), itemCollection);
		if (info != null) {
			boolean isCostSplit = false;
			for (int i = 0; i < info.getSuppEntry().size(); i++) {
				ContractChangeBillInfo entryInfo = info.getSuppEntry().get(i)
						.getContractChange();
				if (entryInfo != null && entryInfo.getContractBill() != null
						&& entryInfo.getContractBill().isIsCoseSplit()) {
					isCostSplit = FDCSplitClientHelper.isBillSplited(entryInfo
							.getId().toString(), "t_con_conchangesplit",
							"FContractChangeID");
					if (isCostSplit)
						break;
				}
				if (entryInfo == null || entryInfo.getContractBill() == null
						|| entryInfo.getContractBill().isIsCoseSplit())
					continue;
				isCostSplit = FDCSplitClientHelper.isBillSplited(entryInfo
						.getId().toString(), "T_CON_ConChangeNoCostSplit",
						"FContractChangeID");
				if (isCostSplit)
					break;
			}

			if (isCostSplit) {
				MsgBox.showWarning("\u6B64\u53D8\u66F4\u5BA1\u6279\u5355\u751F\u6210\u7684\u6307\u4EE4\u5355\u5DF2\u7ECF\u62C6\u5206\uFF0C\u4E0D\u80FD\u5220\u9664\uFF01");
				SysUtil.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	protected void audit(List ids) throws Exception {
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();
		if (ids != null)
			bill.audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();
		if (ids != null)
			bill.unAudit(ids);
	}

	protected String getEditUIName() {
		return (com.kingdee.eas.fdc.contract.client.ChangeAuditEditUI.class)
				.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ChangeAuditBillFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		ChangeAuditBillInfo objectValue = new ChangeAuditBillInfo();
		return objectValue;
	}

	protected void freezeTableColumn() {
	}

	public void onShow() throws Exception {
		super.onShow();
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);
		actionAheadDisPatch.putValue("SmallIcon", EASResource.getIcon("imgTbtn_emend"));
		actionAheadDisPatch.setEnabled(true);
		actionDisPatch.setVisible(false);
		actionQuery.setVisible(true);
		
//		tblMain.getColumn("changeBefAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
//		tblMain.getColumn("changeAftAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
//		loadChangeAmt();
	}

	protected void checkBillState(String state, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());
		java.util.Set idSet = ContractClientUtils.listToSet(idList);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("changeState");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);
		Iterator iter = coll.iterator();
		do {
			if (!iter.hasNext())
				break;
			CoreBaseInfo element = (CoreBaseInfo) iter.next();
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());
			if (!element.getString(getBillStatePropertyName()).equals(state)) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}
		} while (true);
	}

	protected String getBillStatePropertyName() {
		return "changeState";
	}

	protected String getStateForAudit() {
		return "3Submit";
	}

	protected String getStateForUnAudit() {
		return "5Audit";
	}

	protected String[] getBillStateForEditOrRemove() {
		return (new String[] { "1Saved", "3Submit", "2Register" });
	}

	public void actionDisPatch_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		checkSelected();
		super.actionDisPatch_actionPerformed(e);
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();
		if (selectedIdValues != null) {
			bill.disPatch(FDCHelper.list2Set(selectedIdValues));
			showOprtOKMsgAndRefresh();
		}
	}

	private void checkContractSplitIsAuditAndShowMsg(List idList)
			throws BOSException, EASBizException {
		for (int i = 0; i < idList.size(); i++) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("parent.id", idList.get(i)));
			view.setFilter(filter);
			view.getSelector().add(new SelectorItemInfo("contractBill.id"));
			view.getSelector().add(
					new SelectorItemInfo("contractBill.isCoseSplit"));
			ChangeSupplierEntryCollection cse = ChangeSupplierEntryFactory
					.getRemoteInstance().getChangeSupplierEntryCollection(view);
			for (int j = 0; j < cse.size(); j++) {
				boolean conSplitIsAudited = FDCSplitClientHelper
						.checkContractSplitIsAudited(cse.get(j)
								.getContractBill().getId().toString(), cse.get(
								j).getContractBill().isIsCoseSplit());
				if (conSplitIsAudited)
					FDCSplitClientHelper.getParamValueAndShowMsg(
							"conSplitNotAudit", this);
			}

		}

	}

	public void actionAudit_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		if (FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext
				.getSysContext().getCurrentOrgUnit().getId().toString(),
				"FDC018_IMPORTCONSPLIT"))
			checkContractSplitIsAuditAndShowMsg(idList);
		boolean hasMutex = false;
		try {
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			super.actionAudit_actionPerformed(e);
		} catch (Throwable e1) {
			handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex)
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					handUIException(e1);
				}
		}
		
	}

	public void actionQuery_actionPerformed(java.awt.event.ActionEvent e)throws Exception {
		getFilterUI();
		super.actionQuery_actionPerformed(e);
	}

	protected void checkBeforeRemove() throws Exception {
		super.checkBeforeRemove();
	}

	protected boolean confirmRemove()
    {
        EntityViewInfo view;
        List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
        java.util.Set idSet = ContractClientUtils.listToSet(idList);
        view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
        view.setFilter(filter);
        view.getSelector().add("id");
        view.getSelector().add(getBillStatePropertyName());
        CoreBaseCollection coll = null;
		try {
			coll = getRemoteInterface().getCollection(view);
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
        Iterator iter = coll.iterator();
        if(!iter.hasNext()){
	        CoreBillBaseInfo element = (CoreBillBaseInfo)iter.next();
	        String billState = element.getString(getBillStatePropertyName());
	        if(!billState.equals("3Submit")){
	        int isYes = MsgBox.showConfirm2(this, ChangeAuditUtil.getRes("hasChange"));
	        if(MsgBox.isYes(isYes)){
	            return true;
	        }
	        try
	        {
	            return false;
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
        }
        }
		
		return super.confirmRemove();//mod by ypf on 20150613
    }

        public void actionAheadDisPatch_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		checkSelected();
		checkBeforAhead();
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();
		java.util.Set idSet = ContractClientUtils.listToSet(selectedIdValues);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("changeState");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);
		IUIWindow uiWin;
		for (Iterator iter = coll.iterator(); iter.hasNext(); uiWin.show()) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", element.getId());
			uiWin = UIFactory
					.createUIFactory(UIFactoryName.MODEL)
					.create(
							(com.kingdee.eas.fdc.contract.client.ChangeAuditAheadEditUI.class)
									.getName(), uiContext, null, null);
		}

		refreshList();
		super.actionAheadDisPatch_actionPerformed(e);
	}

	private void checkBeforAhead() throws Exception {
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();
		java.util.Set idSet = ContractClientUtils.listToSet(selectedIdValues);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("changeState");
		view.getSelector().add("entrys.*");
		view.getSelector().add("suppEntry.mainSupp.*");
		view.getSelector().add("suppEntry.contractBill.*");
		view.getSelector().add("suppEntry.*");
		view.getSelector().add("suppEntry.entry.*");
		view.getSelector().add("suppEntry.copySupp.*");
		view.getSelector().add("suppEntry.reckonor.id");
		ChangeAuditBillCollection coll = bill
				.getChangeAuditBillCollection(view);
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ChangeAuditBillInfo element = (ChangeAuditBillInfo) iter.next();
			if (element.getChangeState().equals(ChangeBillStateEnum.Saved)
					|| element.getChangeState().equals(
							ChangeBillStateEnum.Register)
					|| element.getChangeState().equals(
							ChangeBillStateEnum.Submit)
					|| element.getChangeState().equals(
							ChangeBillStateEnum.Auditting)) {
				if (element.getEntrys().size() == 0
						|| element.getSuppEntry().size() == 0) {
					MsgBox
							.showWarning(this, ChangeAuditUtil
									.getRes("notAhead"));
					SysUtil.abort();
				} else {
					ChangeSupplierEntryCollection collSupp = element
							.getSuppEntry();
					int count = collSupp.size();
					int i = 0;
					while (i < count) {
						ChangeSupplierEntryInfo info = collSupp.get(i);
						if (info.getMainSupp() == null) {
							MsgBox.showWarning(this, ChangeAuditUtil
									.getRes("partAhead"));
							SysUtil.abort();
						}
						if (info.getContractBill() == null) {
							MsgBox.showWarning(this, ChangeAuditUtil
									.getRes("partAhead"));
							SysUtil.abort();
						}
						if (info.getCostAmount() == null
								|| info.getCostAmount().equals(FDCHelper.ZERO)) {
							MsgBox.showWarning(this, ChangeAuditUtil
									.getRes("costAhead"));
							SysUtil.abort();
						}
						if (info.getReckonor() == null) {
							MsgBox.showWarning(this, ChangeAuditUtil
									.getRes("costAhead"));
							SysUtil.abort();
						}
						if (info.getId() != null) {
							EntityViewInfo vi = new EntityViewInfo();
							FilterInfo fi = new FilterInfo();
							FilterItemCollection it = fi.getFilterItems();
							if (info.getId() != null)
								it
										.add(new FilterItemInfo("parent.id",
												info.getId().toString(),
												CompareType.EQUALS));
							vi.setFilter(fi);
							vi.getSelector().add("content.*");
							SupplierContentEntryCollection c = SupplierContentEntryFactory
									.getRemoteInstance()
									.getSupplierContentEntryCollection(vi);
							int entryNum = c.size();
							if (entryNum == 0) {
								MsgBox.showWarning(this, ChangeAuditUtil
										.getRes("partAhead"));
								SysUtil.abort();
							}
							EntityViewInfo vie = new EntityViewInfo();
							FilterInfo fil = new FilterInfo();
							FilterItemCollection itl = fil.getFilterItems();
							if (info.getId() != null)
								itl
										.add(new FilterItemInfo("parent.id",
												info.getId().toString(),
												CompareType.EQUALS));
							vie.setFilter(fil);
							vie.getSelector().add("copySupp.*");
							CopySupplierEntryCollection collCopy = CopySupplierEntryFactory
									.getRemoteInstance()
									.getCopySupplierEntryCollection(vie);
							int copyNum = collCopy.size();
							if (copyNum == 0) {
								MsgBox.showWarning(this, ChangeAuditUtil
										.getRes("partAhead"));
								SysUtil.abort();
							}
						}
						i++;
					}
				}
			} else {
				MsgBox.showWarning(this, ChangeAuditUtil.getRes("notAhead"));
				SysUtil.abort();
			}
		}

	}

	public void onLoad() throws Exception {
		super.onLoad();
		actionQuery.setEnabled(true);
		actionQuery.setVisible(true);
		boolean isDispatch = FDCUtils.getDefaultFDCParamByKey(null, null,
				"FDC220_ALLOWDISPATCH");
		if (!isDispatch) {
			actionAheadDisPatch.setVisible(false);
			actionAheadDisPatch.setEnabled(false);
		}
		
		//add by ypf on 20150623
		tblMain.getColumn("changeBefAmt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("changeAftAmt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("changeBefAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("changeAftAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		
		//add by ypf on 20150623
		loadChangeAmt();//在ui的父类模板里面有加载
		tblMain.getColumn("changeBefAmt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("changeAftAmt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("changeBefAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("changeAftAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	}

	protected void updateButtonStatus() {
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		actionRemove.setEnabled(true);
		if (tblMain.getSelectManager().getActiveRowIndex() != -1)
			if (tblMain.getSelectManager().getBlocks().size() > 1
					|| e.getSelectBlock().getBottom()
							- e.getSelectBlock().getTop() > 0) {
				actionSetRespite.setEnabled(true);
				actionCancelRespite.setEnabled(true);
			} else {
				IRow row = tblMain.getRow(tblMain.getSelectManager()
						.getActiveRowIndex());
				if (Boolean.TRUE.equals(row.getCell("isRespite").getValue())) {
					actionSetRespite.setEnabled(false);
					actionCancelRespite.setEnabled(true);
				} else {
					actionSetRespite.setEnabled(true);
					actionCancelRespite.setEnabled(false);
				}
				if (row.getCell("changeState") != null
						&& "5Audit".equalsIgnoreCase(((BizEnumValueInfo) row
								.getCell("changeState").getValue()).getValue()
								.toString())
						&& tblMain.getSelectManager().getBlocks().size() == 1) {
					actionSetRespite.setEnabled(false);
					actionCancelRespite.setEnabled(true);
				}
			}
	}

	public void actionAddNew_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		
		//add by ypf on 20150712
		Object changeTypeObj = getChangeTypeSelectedTreeNode().getUserObject();
		if(changeTypeObj == null || changeTypeObj.equals("") || changeTypeObj.equals("所有类型")){
			MsgBox.showWarning("请选择相应的变更类型");
            SysUtil.abort();
		}
		
		super.actionAddNew_actionPerformed(e);
	}

	public void actionUnAudit_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		checkSelected();
		FDCClientHelper.checkAuditor(getSelectedIdValues(),
				"T_CON_ChangeAuditBill");
		super.actionUnAudit_actionPerformed(e);
	}

	public void actionSetRespite_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(
				getBillListTable(), getKeyFieldName(), false);
		if (idList.size() != 0 && idList.get(0) != null) {
			((IFDCBill) getRemoteInterface()).setRespite(idList, true);
			MsgBox.showWarning("\u64CD\u4F5C\u6210\u529F\uFF01\u5DF2\u5BA1\u6279\u72B6\u6001\u7684\u5355\u636E\u4E0D\u4F1A\u542F\u7528\u6682\u7F13");
			refreshList();
		}
	}

	public void actionCancelRespite_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(
				getBillListTable(), getKeyFieldName(), true);
		if (idList.get(0) != null) {
			((IFDCBill) getRemoteInterface()).setRespite(idList, false);
			showOprtOKMsgAndRefresh();
		}
	}

	protected void execQuery() {
		super.execQuery();
		getMainQuery().setFilter(new FilterInfo());
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog == null) {
			commonQueryDialog = super.initCommonQueryDialog();
			commonQueryDialog.setWidth(400);
		}
		commonQueryDialog.addUserPanel(getFilterUI());
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (filterUI == null)
			try {
				filterUI = new ChangeAuditListFilterUI(this, actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		filterUI.setAuthorizedOrgs(authorizedOrgs);
		filterUI.setCompany(null);
		filterUI.setProject(null);
		DefaultKingdeeTreeNode projectNode = getProjSelectedTreeNode();
		if (projectNode != null && projectNode.getUserObject() != null
				&& (projectNode.getUserObject() instanceof CoreBaseInfo)) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode
					.getUserObject();
			if ((projTreeNodeInfo instanceof OrgStructureInfo)
					|| (projTreeNodeInfo instanceof FullOrgUnitInfo)) {
				FullOrgUnitInfo company = null;
				if (projTreeNodeInfo instanceof OrgStructureInfo)
					company = ((OrgStructureInfo) projTreeNodeInfo).getUnit();
				else
					company = (FullOrgUnitInfo) projTreeNodeInfo;
				filterUI.setCompany(company);
			} else if (projTreeNodeInfo instanceof CurProjectInfo)
				filterUI.setProject((CurProjectInfo) projTreeNodeInfo);
		}
		return filterUI;
	}

	//add by ypf on 20150623
	public void loadChangeAmt()
	{
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			int rowIndex = i;
			Object id = tblMain.getCell(rowIndex, "id").getValue();
			System.out.println("----rowIndex:"+rowIndex +"    id:"+id);
			if(id != null){
				try {
					IRowSet rs = ContractChangeVisaFacadeFactory.getRemoteInstance().getCostAmountByChngAuditBillID(id.toString(), null);
					if(rs!=null && rs.size()>0)
		    		{
		    			while(rs.next())
		    			{
		    				String chgBefAmt = rs.getString("chgBefAmt");//预估金额
		    				String chgAftAmt = rs.getString("chgAftAmt");//签证金额
		    				System.out.println("--chgBefAmt:"+chgBefAmt+"   chgAftAmt:"+chgAftAmt);
		    				
		    				tblMain.getCell(rowIndex, "changeBefAmt").setValue(chgBefAmt);
		    				tblMain.getCell(rowIndex, "changeAftAmt").setValue(chgAftAmt);
		    			}
		    		}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
