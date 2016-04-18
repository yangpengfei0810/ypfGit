/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.IPDCostDetail;
import com.kingdee.eas.fdc.aimcost.PDCostDetailFactory;
import com.kingdee.eas.fdc.aimcost.PDCostDetailInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ProductDynamicCostDetailListUI extends AbstractProductDynamicCostDetailListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProductDynamicCostDetailListUI.class);
    
    //获取有权限的组织
	protected Set authorizedOrgs = null;
    
    public ProductDynamicCostDetailListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	buildProjectTree();
    	
		this.btnAttachment.setVisible(false);
		this.btnAuditResult.setVisible(false);
		btnAddNew.setVisible(false);
		btnCreateTo.setVisible(false);
		btnTraceUp.setVisible(false);
		btnTraceDown.setVisible(false);
		btnWorkFlowG.setVisible(false);
		
		
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.btnUnAudit.setVisible(true);
		this.btnUnAudit.setEnabled(true);
		
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
    }
    
    private void initActionStatus() throws EASBizException, BOSException {
    	changeActionState(true);
		PDCostDetailInfo info = getSelectedInfo();
		FDCBillStateEnum state = info.getState();
		if (state == FDCBillStateEnum.SAVED) {
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		} else if(state == FDCBillStateEnum.SUBMITTED){
			actionUnAudit.setEnabled(false);
		} else if (state == FDCBillStateEnum.AUDITTED) {
			actionRemove.setEnabled(false);
			actionEdit.setEnabled(false);
			actionAudit.setEnabled(false);
		}
	}
    
    private void changeActionState(boolean state) {
		actionAddNew.setEnabled(state);
		actionView.setEnabled(state);
		actionEdit.setEnabled(state);
		actionRemove.setEnabled(state);
		actionAudit.setEnabled(state);
		actionUnAudit.setEnabled(state);
	}
    
    public void buildProjectTree() throws Exception {
    	ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}		
		}
		if (treeProject.getRowCount() > 0) {
			treeProject.setSelectionRow(0);
			treeProject.expandPath(treeProject.getSelectionPath());
		}
	}

    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
        initActionStatus();
    }

    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	treeSelectChange();
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	String id = getSelectedKeyValue();
    	PDCostDetailInfo info = PDCostDetailFactory.getRemoteInstance().getPDCostDetailInfo(new ObjectUuidPK(id));
    	if (!info.getState().equals(FDCBillStateEnum.SUBMITTED)) {
    		FDCMsgBox.showWarning("存在不符合审批条件的记录，请重新选择，保证所选的记录都是提交状态的");
    		SysUtil.abort();
    	}
    	super.actionAudit_actionPerformed(e);
    	IPDCostDetail service = (IPDCostDetail) getBizInterface();
    	info.setAuditor(SysContext.getSysContext().getCurrentUserInfo());
    	info.setAuditTime(new Date());
    	info.setState(FDCBillStateEnum.AUDITTED);
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditTime");
		selector.add("auditor");
    	service.updatePartial(info, selector);
    	this.setMessageText("审批成功");
    	this.showMessage();
    	refresh(e);
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	String id = getSelectedKeyValue();
    	PDCostDetailInfo info = PDCostDetailFactory.getRemoteInstance().getPDCostDetailInfo(new ObjectUuidPK(id));
    	if (!info.getState().equals(FDCBillStateEnum.AUDITTED)) {
    		FDCMsgBox.showWarning("存在不符合反审批条件的记录，请重新选择，保证所选的记录都是审批状态的");
    		SysUtil.abort();
    	}
    	super.actionUnAudit_actionPerformed(e);
    	IPDCostDetail service = (IPDCostDetail) getBizInterface();
    	info.setAuditor(null);
    	info.setAuditTime(null);
    	info.setState(FDCBillStateEnum.SUBMITTED);
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditTime");
		selector.add("auditor");
    	service.updatePartial(info, selector);
    	this.setMessageText("反审批成功");
    	this.showMessage();
    	refresh(e);
    }
    
    protected void treeSelectChange() throws Exception {
		DefaultKingdeeTreeNode projectNode  = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		Object project  = null;
		if(projectNode!=null) {
			project = projectNode.getUserObject();
			if (project instanceof CurProjectInfo) {
				kDContainer1.setTitle(((CurProjectInfo) project).getDisplayName());
			} else {
				kDContainer1.setTitle(null);
			}
		}
		
		mainQuery.setFilter(getTreeSelectFilter(project));
		SorterItemCollection sorter = mainQuery.getSorter();
    	sorter.clear();
    	SorterItemInfo sorterVsersion = new SorterItemInfo("version");
    	sorterVsersion.setSortType(SortType.DESCEND);
    	sorter.add(sorterVsersion);
		execQuery();
    }
    
    protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			String id = projTreeNodeInfo.getId().toString();
			filterItems.add(new FilterItemInfo("curProjectID", projTreeNodeInfo instanceof CurProjectInfo ? id : ""));
		}
		return filter;
	}

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    protected ICoreBase getBizInterface() throws Exception {
    	return PDCostDetailFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	return ProductDynamicCostDetailUI.class.getName();
    }
    
    protected String getEditUIModal(){
        return UIFactoryName.NEWTAB;
    }
    
    private PDCostDetailInfo getSelectedInfo() throws BOSException, EASBizException {
		checkSelected();
		return PDCostDetailFactory.getRemoteInstance().getPDCostDetailInfo(new ObjectUuidPK(getSelectedKeyValue()), getSelectors());
	}
}