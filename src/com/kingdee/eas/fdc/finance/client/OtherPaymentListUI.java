/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.OtherPaymentFactory;
import com.kingdee.eas.fdc.finance.OtherPaymentInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.app.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class OtherPaymentListUI extends AbstractOtherPaymentListUI
{
    private static final Logger logger = CoreUIObject.getLogger(OtherPaymentListUI.class);
    
    private Object treeVal = null;
    
    public OtherPaymentListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	initTree();
    	this.btnRContract.setEnabled(true);
    	this.btnRemRelation.setEnabled(true);
    	this.btnaudit.setEnabled(true);
    	this.btnunaudit.setEnabled(true);
    	
    	tblMain.getColumn("applyPeriod.periodNumber").getStyleAttributes().setHided(true);
    	tblMain.getColumn("paymentDate").getStyleAttributes().setHided(true);
    	tblMain.getColumn("receiptBank").getStyleAttributes().setHided(true);
    	tblMain.getColumn("payType.name").getStyleAttributes().setHided(true);
    	tblMain.getColumn("receiptNumber").getStyleAttributes().setHided(true);
    	tblMain.getColumn("Currency.name").getStyleAttributes().setHided(true);
    	tblMain.getColumn("parities").getStyleAttributes().setHided(true);
    	tblMain.getColumn("invoiceNumber").getStyleAttributes().setHided(true);
    	tblMain.getColumn("invoiceAMT").getStyleAttributes().setHided(true);
    	tblMain.getColumn("remark").getStyleAttributes().setHided(true);
    	tblMain.getColumn("PaymentProportion").getStyleAttributes().setHided(true);
    	tblMain.getColumn("completedQuantities").getStyleAttributes().setHided(true);
    	tblMain.getColumn("payRequest.name").getStyleAttributes().setHided(true);
    	tblMain.getColumn("auditor.name").getStyleAttributes().setHided(true);
    }
    
    public void storeFields()
    {
        super.storeFields();
    }

    //加载树结构
    protected void initTree() throws Exception
	{
//	    AdminOrgUnitInfo currentAdminUnit = SysContext.getSysContext().getCurrentAdminUnit();//当前组织
//	    DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode(currentAdminUnit.getName());
//	    EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit",currentAdminUnit.getId()));
//		view.setFilter(filter);
//		SorterItemCollection sor = new SorterItemCollection();
//		SorterItemInfo sorterItem = new SorterItemInfo("Number");
//		sorterItem.setSortType(SortType.DESCEND);
//		sor.add(sorterItem);
//		view.setSorter(sor);
//	    CurProjectCollection curProjectCollection = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);//项目
//	    Map map = new HashMap();
//	    for (int i = 0; i <curProjectCollection.size() ; i++) {
//	    	CurProjectInfo curProjectInfo = curProjectCollection.get(i);
//	    	DefaultKingdeeTreeNode root1 = null;
//	    	if(!curProjectInfo.isIsLeaf()){
//	    		root1 = new DefaultKingdeeTreeNode(curProjectInfo.getName());
//	    		root.add(root1);
//	    		EntityViewInfo viewInfo = new EntityViewInfo();
//	    		FilterInfo filterInfo = new FilterInfo();
//	    		filterInfo.getFilterItems().add(new FilterItemInfo("parent.id",curProjectInfo.getId()));
//	    		filterInfo.getFilterItems().add(new FilterItemInfo("IsLeaf",true));
//	    		filterInfo.getFilterItems().add(new FilterItemInfo("isenabled",true));
//	    		SorterItemCollection sorinfo = new SorterItemCollection();
//	    		SorterItemInfo sorterItemInfo = new SorterItemInfo("Number");
//	    		sorterItemInfo.setSortType(SortType.ASCEND);
//	    		sorinfo.add(sorterItemInfo);
//	    		viewInfo.setSorter(sorinfo);
//	    		viewInfo.setFilter(filterInfo);
//	    		
//	    		CurProjectCollection projectCollection = CurProjectFactory.getRemoteInstance().getCurProjectCollection(viewInfo);//子项目
//	    		for (int j = 0; j < projectCollection.size(); j++) {
//	    			CurProjectInfo projectInfo = projectCollection.get(j);
//	    			DefaultKingdeeTreeNode root2 = new DefaultKingdeeTreeNode(projectInfo.getName());
//		    		root1.add(root2);
//		    		root2.setUserObject(projectInfo);
//				}
//	    		root1.setUserObject(curProjectInfo);
//	    	}
//		}
//	    root.setUserObject(currentAdminUnit);
//	    
	    ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
	    
	    
//	    this.treeMain.setModel(new KingdeeTreeModel(root));
//	    this.treeMain.getSelectionModel().setSelectionMode(TreeBuilderFactory.DEFAULT_EXPAND_LEVEL);
//	    this.treeMain.setSelectionRow(0);
	    treeMain.addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent e) {
				selectChanged(e);
			}
	    });
	}

    /**
     * 选择树节点
     * @param e
     */
    private void selectChanged(TreeSelectionEvent e){
    	if (e.getNewLeadSelectionPath() != null) {
			DefaultKingdeeTreeNode selectedNode = (DefaultKingdeeTreeNode) e.getNewLeadSelectionPath().getLastPathComponent();
			Object object = selectedNode.getUserObject();
			if(object != null){
				treeVal =  object; 
				if(object instanceof CurProjectInfo ){
					CurProjectInfo projectInfo = (CurProjectInfo) object;
					FilterInfo filter  = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber",projectInfo.getLongNumber()+"%",CompareType.LIKE));
					this.mainQuery.setFilter(filter);
					this.tblMain.removeRows();
					this.tblMain.refresh();
				}else if(object instanceof OrgStructureInfo ){
					OrgStructureInfo orgUnitInfo = (OrgStructureInfo) object;
					FilterInfo filter  = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("organize.id",orgUnitInfo.getUnit().getId()));
					this.mainQuery.setFilter(filter);
					this.tblMain.removeRows();
					this.tblMain.refresh();
				}
			}
		}
    }
    
    /**
     * 审核
     */
    public void actionaudit_actionPerformed(ActionEvent e) throws Exception {
    	//mod by ypf on 20140125 
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	if(rowIndex > -1){
			// 取到行
			IRow row = this.tblMain.getRow(rowIndex);
			// 取列值
			String state = (row.getCell("billstates").getValue()!=null)?row.getCell("billstates").getValue().toString():"";
			String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";
			System.out.println("billstates:" + state + "   isOAAudit:" + isOAAudit);
	
			if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
					&& isOAAudit.equals("true")) {
				MsgBox.showInfo("单据已经发起OA流程审批，不能进行重复审批");
				SysUtil.abort();
			} else if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
					&& isOAAudit.equals("true")) {
				MsgBox.showInfo("单据已经通过OA审批，不能进行重复审批");
				SysUtil.abort();
			} 
    	
//    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		
			Object contVal =  this.tblMain.getCell(rowIndex, "billstates").getValue();
			Object idVal =  this.tblMain.getCell(rowIndex, "id").getValue();
			if(contVal != null && !contVal.toString().equals("已提交") ){
				MsgBox.showWarning("只有已提交的单据才能审批！");
				abort();
			}
			if(idVal != null && !idVal.equals("")){
				OtherPaymentInfo paymentInfo = OtherPaymentFactory.getRemoteInstance().getOtherPaymentInfo(new ObjectUuidPK(idVal.toString()));
				paymentInfo.setBillstates(FDCBillStateEnum.AUDITTED);
				SelectorItemCollection sicc = new SelectorItemCollection();
	    		sicc.add(new SelectorItemInfo("billstates"));
				OtherPaymentFactory.getRemoteInstance().updatePartial(paymentInfo,sicc);
				this.tblMain.refresh();
				MsgBox.showInfo("审核成功");
				abort();
			}
		}else{
			MsgBox.showWarning("请选择行！");
			abort();
		}
    }
    
    /**
     * 反审核
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	//mod by ypf on 20140125 
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	if(rowIndex > -1){
			// 取到行
			IRow row = this.tblMain.getRow(rowIndex);
			// 取列值
			String state = (row.getCell("billstates").getValue()!=null)?row.getCell("billstates").getValue().toString():"";
			String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";
			System.out.println("billstates:" + state + "   isOAAudit:" + isOAAudit);
	
			if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
					&& isOAAudit.equals("true")) {
				MsgBox.showInfo("单据已经发起OA流程审批，不能进行反审批");
				SysUtil.abort();
			} else if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
					&& isOAAudit.equals("true")) {
				MsgBox.showInfo("单据已经通过OA审批，不能进行反审批");
				SysUtil.abort();
			} 
    	
//    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		
			Object contVal =  this.tblMain.getCell(rowIndex, "billstates").getValue();
			Object idVal =  this.tblMain.getCell(rowIndex, "id").getValue();
			if(contVal != null && !contVal.toString().equals("已审批") ){
				MsgBox.showWarning("只有已审核的单据才能反审批！");
				this.tblMain.refresh();
				abort();
			}
			if(idVal != null && !idVal.equals("")){
				OtherPaymentInfo paymentInfo = OtherPaymentFactory.getRemoteInstance().getOtherPaymentInfo(new ObjectUuidPK(idVal.toString()));
				PayRequestBillInfo payRequest = paymentInfo.getPayRequest();
				
				if(payRequest != null){
					IPayRequestBill remoteInstance = PayRequestBillFactory.getRemoteInstance();
					PayRequestBillCollection billCollection = remoteInstance.getPayRequestBillCollection("where id = '"+payRequest.getId().toString()+"'");
					if(billCollection.size() > 0){
						PayRequestBillInfo requestBillInfo =billCollection.get(0);
						
						//add by ypf on 20140126  这种情况如果业务需要的话可以放开
						//下游单据付款申请单已经走OA审批，其他付款单不能进行反审批
						/*if(requestBillInfo.isIsOAAudit() && (requestBillInfo.getState().equals(FDCBillStateEnum.SUBMITTED) || requestBillInfo.getState().equals(FDCBillStateEnum.AUDITTED))){
							MsgBox.showWarning("下游单据付款申请单已经走OA审批，其他付款单不能进行反审批！");
							abort();
						}*/
						
						//如果付款申请单已审核需手动删除，否则直接删除付款申请单。
						if(requestBillInfo.getState().equals(FDCBillStateEnum.AUDITTED)){
							MsgBox.showWarning("下游单据付款申请单已经审核，不能进行反审核！");
							abort();
						}else{
							remoteInstance.delete(new ObjectUuidPK(payRequest.getId().toString()));
							SelectorItemCollection collection = new SelectorItemCollection();
							collection.add("payRequest");
							collection.add("contract");
							collection.add("billstates");
							paymentInfo.setPayRequest(null);
							paymentInfo.setContract(null);
							paymentInfo.setBillstates(FDCBillStateEnum.SUBMITTED);
							OtherPaymentFactory.getRemoteInstance().updatePartial(paymentInfo, collection);
						}
					}else{
						//add  by ypf on 20140126 付款申请单已经被删除，但是其他付款单的付款申请单id没有被清除
						SelectorItemCollection collection = new SelectorItemCollection();
						collection.add("payRequest");
						collection.add("contract");
						collection.add("billstates");
						paymentInfo.setPayRequest(null);
						paymentInfo.setContract(null);
						paymentInfo.setBillstates(FDCBillStateEnum.SUBMITTED);
						OtherPaymentFactory.getRemoteInstance().updatePartial(paymentInfo, collection);
					}
				}/* mod by ypf on 20140126 else{
					SelectorItemCollection collection = new SelectorItemCollection();
					collection.add("billstates");
					paymentInfo.setBillstates(FDCBillStateEnum.SUBMITTED);
					OtherPaymentFactory.getRemoteInstance().updatePartial(paymentInfo, collection);
				}*/
				
				SelectorItemCollection collection = new SelectorItemCollection();
				collection.add("billstates");
				paymentInfo.setBillstates(FDCBillStateEnum.SUBMITTED);
				OtherPaymentFactory.getRemoteInstance().updatePartial(paymentInfo, collection);
			}
			this.tblMain.refresh();
			MsgBox.showInfo("反审核成功");
			abort();
		}else{
			MsgBox.showWarning("请选择行！");
			abort();
		}
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	super.prepareUIContext(uiContext, e);
    	uiContext.put("treeVal", treeVal);
    }
    
    private Object getRootObject() {
		return null;
	}

	private Object getRootName() {
		return null;
	}

	private int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	private int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}

	private FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		return filter;
	}
	
	private ITreeBase getTreeInterface() {

		ITreeBase treeBase = null;
		try {
			treeBase = ContractTypeFactory.getRemoteInstance();
		} catch (BOSException e) {
			abort(e);
		}

		return treeBase;
	}

	protected String getEditUIModal() {
		return com.kingdee.eas.common.client.UIFactoryName.NEWTAB;
	}
	
	private ILNTreeNodeCtrl getLNTreeNodeCtrl() {
		return (ILNTreeNodeCtrl) new DefaultLNTreeNodeCtrl(getTreeInterface());
	}

	/**
	 * 关联合同生成付款单
	 */
	public void actionRcontract_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex > -1){
			Object contVal =  this.tblMain.getCell(rowIndex, "billstates").getValue();
			if(contVal != null && !contVal.toString().equals("已审批") ){
				MsgBox.showWarning("单据未审核不能关联合同！");
				abort();
			}
			
			String billid = (String) this.tblMain.getCell(rowIndex, "id").getValue();
			if(billid != null){
				OtherPaymentInfo paymentInfo = OtherPaymentFactory.getRemoteInstance().getOtherPaymentInfo(new ObjectUuidPK(billid));
				PayRequestBillInfo payRequest = paymentInfo.getPayRequest();
				if(payRequest != null){
					MsgBox.showWarning("单据已生成付款申请单,请先断开与付款申请单的关联关系！");
					abort();
				}
			}
			String curProjectid = (String) this.tblMain.getCell(rowIndex, "curProjectid").getValue();
			String number = (String) this.tblMain.getCell(rowIndex, "number").getValue();
			String payRequestid = (String) this.tblMain.getCell(rowIndex, "payRequestId").getValue();
			
			HashMap map = new HashMap();
			map.put("Owner", this); //必须。被启动UI的父UI对象
			map.put("billid",billid );
			map.put("curProjectid", curProjectid);
			map.put("number", number);
			map.put("payRequestid", payRequestid);
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory"); //以模态对话框方式启动
			IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.finance.client.PaymentRelateContractUI", /* 被启动对象的类名称 */map,null, OprtState.ADDNEW);
			uiWindow.show();
		}else{
			MsgBox.showWarning("请选择行！");
			abort();
		}
		this.tblMain.refresh();
	}
	
	/**
	 * 终止关联
	 */
	public void actionRemRelation_actionPerformed(ActionEvent e)
			throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex > -1){
			String billid = (String) this.tblMain.getCell(rowIndex, "id").getValue();
			if(billid != null){
				OtherPaymentInfo paymentInfo = OtherPaymentFactory.getRemoteInstance().getOtherPaymentInfo(new ObjectUuidPK(billid));
				PayRequestBillInfo payRequest = paymentInfo.getPayRequest();
				if(payRequest != null){
					IPayRequestBill remoteInstance = PayRequestBillFactory.getRemoteInstance();
					PayRequestBillCollection billCollection = remoteInstance.getPayRequestBillCollection("where id = '"+payRequest.getId().toString()+"'");
					if(billCollection.size() > 0){
						PayRequestBillInfo requestBillInfo =billCollection.get(0);
						//如果付款申请单已审核需手动删除，否则直接删除付款申请单。
						if(requestBillInfo.getState().equals(FDCBillStateEnum.AUDITTED)){
							MsgBox.showWarning("下游单据付款申请单已经审核，不能进行终止关联！");
							abort();
						}else{
							int confirm2 = MsgBox.showConfirm2("是否删除关联时同时删除付款申请单?");
							if(MsgBox.YES == confirm2){
								remoteInstance.delete(new ObjectUuidPK(payRequest.getId().toString()));
								SelectorItemCollection collection = new SelectorItemCollection();
								collection.add("payRequest");
								collection.add("contract");
								paymentInfo.setPayRequest(null);
								paymentInfo.setContract(null);
								OtherPaymentFactory.getRemoteInstance().updatePartial(paymentInfo, collection);
							}else if(MsgBox.NO == confirm2){
								PayRequestBillInfo billInfo = remoteInstance.getPayRequestBillInfo(new ObjectUuidPK(payRequest.getId().toString()));
								billInfo.setIsOtherPay(false);
								remoteInstance.save(billInfo);
								SelectorItemCollection collection = new SelectorItemCollection();
								collection.add("payRequest");
								collection.add("contract");
								paymentInfo.setPayRequest(null);
								paymentInfo.setContract(null);
								OtherPaymentFactory.getRemoteInstance().updatePartial(paymentInfo, collection);
							}
						}
					}else{
						SelectorItemCollection collection = new SelectorItemCollection();
						collection.add("payRequest");
						collection.add("contract");
						paymentInfo.setPayRequest(null);
						paymentInfo.setContract(null);
						OtherPaymentFactory.getRemoteInstance().updatePartial(paymentInfo, collection);
						MsgBox.showWarning("付款申请单已被手动删除，可直接关联合同！");
						abort();
					}
				}else{
					MsgBox.showWarning("未关联合同不能做此操作");
					abort();
				}
			}
		}else{
			MsgBox.showWarning("请选择行！");
			abort();
		}
		this.tblMain.refresh();
	}
	
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
        FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
        super.actionAddNew_actionPerformed(e);
    }

    public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}
    
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	//mod by ypf on 20140125 
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	if(rowIndex > -1){
			// 取到行
			IRow row = this.tblMain.getRow(rowIndex);
			// 取列值
			String state = (row.getCell("billstates").getValue()!=null)?row.getCell("billstates").getValue().toString():"";
			String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";
			System.out.println("billstates:" + state + "   isOAAudit:" + isOAAudit);
	
			if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
					&& isOAAudit.equals("true")) {
				MsgBox.showInfo("单据已经发起OA流程审批，不能进行修改");
				SysUtil.abort();
			} else if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
					&& isOAAudit.equals("true")) {
				MsgBox.showInfo("单据已经通过OA审批，不能进行修改");
				SysUtil.abort();
			} else {
				if(rowIndex > -1){
					Object contVal =  this.tblMain.getCell(rowIndex, "billstates").getValue();
					if(contVal != null && contVal.toString().equals("已审批") ){
						MsgBox.showWarning("单据已审批不能 修改！");
						abort();
					}
				}
		        super.actionEdit_actionPerformed(e);
			}
    	}else{
			MsgBox.showWarning("请选择行！");
			abort();
		}
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	//mod by ypf on 20140125
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex > -1){
		
			// 取到行
			IRow row = this.tblMain.getRow(rowIndex);
			// 取列值
			String state = (row.getCell("billstates").getValue()!=null)?row.getCell("billstates").getValue().toString():"";
			String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";
			System.out.println("billstates:" + state + "   isOAAudit:" + isOAAudit);
	
			if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
					&& isOAAudit.equals("true")) {
				MsgBox.showInfo("单据已经发起OA流程审批，不能进行删除");
				SysUtil.abort();
			} else if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
					&& isOAAudit.equals("true")) {
				MsgBox.showInfo("单据已经通过OA审批，不能进行删除");
				SysUtil.abort();
			} else {
				if(rowIndex > -1){
					Object contVal =  this.tblMain.getCell(rowIndex, "billstates").getValue();
					if(contVal != null && contVal.toString().equals("已审批") ){
						MsgBox.showWarning("单据已审批不能删除！");
						SysUtil.abort();
					}
				}
				super.actionRemove_actionPerformed(e);
			}
		}else{
			MsgBox.showWarning("请选择行！");
			abort();
		}
    }

    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.finance.OtherPaymentFactory.getRemoteInstance();
    }

    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.finance.OtherPaymentInfo objectValue = new com.kingdee.eas.fdc.finance.OtherPaymentInfo();
		
        return objectValue;
    }

}