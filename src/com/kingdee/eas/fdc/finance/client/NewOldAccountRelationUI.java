/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.util.LowTimeHelper;
import com.kingdee.eas.basedata.master.account.AccountTableInfo;
import com.kingdee.eas.basedata.master.account.AccountTools;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ObjectValueRenderImpl;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.finance.INewOldAccountRelation;
import com.kingdee.eas.fdc.finance.INewOldOrgRelation;
import com.kingdee.eas.fdc.finance.NewOldAccountRelationCollection;
import com.kingdee.eas.fdc.finance.NewOldAccountRelationFactory;
import com.kingdee.eas.fdc.finance.NewOldAccountRelationInfo;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationCollection;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationFactory;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class NewOldAccountRelationUI extends AbstractNewOldAccountRelationUI
{
	private NewOldAccountRelationCollection newOldAccountRelationCollection;
    private boolean isModify;
    private String currentSelectNode;
    private boolean isFirstIn;
    private CompanyOrgUnitInfo newCompanyOrg;
    /**
     * output class constructor
     */
    public NewOldAccountRelationUI() throws Exception
    {
        isModify = false;
        currentSelectNode = null;
        isFirstIn = true;
        newCompanyOrg=null;
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    @Override
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("seq"));
        sic.add(new SelectorItemInfo("oldCostAccount.*"));
        sic.add(new SelectorItemInfo("newAccount.*"));
        return sic;
    }
    
    @Override
    public void onLoad() throws Exception {
    	tblMain.checkParsed();
        tblMain.getColumn("oldCostAccount").getStyleAttributes().setBackground(tblMain.getRequiredColor());
        tblMain.getColumn("newAccount").getStyleAttributes().setBackground(tblMain.getRequiredColor());
        tblMain.getColumn("costAccountName").getStyleAttributes().setLocked(true);
        tblMain.getColumn("newAccountName").getStyleAttributes().setLocked(true);
        super.onLoad();
        buildProjectTree();
        
        tblMain.getSelectManager().setSelectMode(2);
        if(tblMain.getRowCount() > 0)
            tblMain.getSelectManager().select(0, 0);
        else
            btnSave.setEnabled(false);
        treeMain.setSelectionRow(0);
        
        refreshNewOldAccountRelation((DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent());
        
        currentSelectNode = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
        setTblMainEditStatus();
        
        importTempAction.putValue("SmallIcon", EASResource.getIcon("imgTbtn_importcyclostyle"));
    }
    
    private void setTblMainEditStatus() throws BOSException
    {
    	tblMain.setEditable(btnSave.isEnabled());
    }
    
    private void buildProjectTree()
    throws Exception
	{
    	
    	ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
        treeMain.setShowsRootHandles(true);
        projectTreeBuilder.build(this, treeMain, actionOnLoad);
        
    	/*LowTimeHelper timeHelper;
        timeHelper = new LowTimeHelper();
        String  curOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
        TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY, "treeMain", curOrgId, null,null);
        DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)orgTreeModel.getRoot();
        if (root.getUserObject() != null)
        {
          OrgStructureInfo orgStru = (OrgStructureInfo)root.getUserObject();
          if (!orgStru.getUnit().isIsCostOrgUnit()) {
            MsgBox.showWarning(FDCClientUtils.getRes("needCostcenter"));
            SysUtil.abort();
          }
          removeNoneCostCenterNode(root);
        }
        timeHelper.printTime2Last("reBuildBySortCode over!");
        treeMain.setModel(orgTreeModel);
        treeMain.setRootVisible(true);
        treeMain.setShowsRootHandles(true);
        timeHelper.printTime2Last("setTreeExpandLevel begin!");
        setTreeExpandLevel((DefaultKingdeeTreeNode)orgTreeModel.getRoot());*/
        
	}
    
    private void removeNoneCostCenterNode(DefaultKingdeeTreeNode node)
    {
      for (int i = 0; i < node.getChildCount(); i++) {
        DefaultKingdeeTreeNode curNode = (DefaultKingdeeTreeNode)node.getChildAt(i);

        OrgStructureInfo orgStru = (OrgStructureInfo)curNode.getUserObject();

        if (!orgStru.getUnit().isIsCostOrgUnit()
        		||orgStru.getUnit().getLongNumber().contains("ZHDC!001DC")
        		||orgStru.getUnit().getLongNumber().contains("ZHDC!002WY")) {
          node.remove(i);
          i--;
        }
        else if (curNode.getChildCount() > 0) {
          removeNoneCostCenterNode(curNode);
        }
      }
    }
    
    private void setTreeExpandLevel(DefaultKingdeeTreeNode root)
    {
        if(root != null)
        {
            OrgStructureInfo structInfo = (OrgStructureInfo)root.getUserObject();
            if(structInfo != null){
                if(structInfo.getLevel() <= 2)
                {
                    treeMain.expandPath(new TreePath(root));
                    treeMain.setSelectionNode(root);
                } else
                {
                    treeMain.collapsePath(treeMain.getSelectionPath());
                    return;
                }
            }
            for(int i = 0; i < root.getChildCount(); i++)
            {
                DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
                setTreeExpandLevel(node);
            }

        }
    }
    
    private void loadData(FilterInfo filter)
    throws Exception
	{
    	Locale locale = SysContext.getSysContext().getLocale();
	    tblMain.removeRows();
	    tblMain.getColumn("oldCostAccount").setEditor(new KDTDefaultCellEditor(createCostAccountF7()));
		tblMain.getColumn("newAccount").setEditor(new KDTDefaultCellEditor(createAccountViewF7(newCompanyOrg)));
		ObjectValueRenderImpl accountAvr = new ObjectValueRenderImpl();
		accountAvr.setFormat(new BizDataFormat("$longNumber$"));
		tblMain.getColumn("oldCostAccount").setRenderer(accountAvr);
		accountAvr = new ObjectValueRenderImpl();
		accountAvr.setFormat(new BizDataFormat("$number$"));
		tblMain.getColumn("newAccount").setRenderer(accountAvr);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(255);
		tblMain.getColumn("remark").setEditor(new KDTDefaultCellEditor(textField));
		INewOldAccountRelation iNewOldAccountRelation = NewOldAccountRelationFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSorter().add(new SorterItemInfo("oldCostAccount.longnumber"));
		evi.getSelector().add("*");
		evi.getSelector().add("oldCostAccount.name");
		evi.getSelector().add("oldCostAccount.longNumber");
		evi.getSelector().add("oldCostAccount.number");
		evi.getSelector().add("oldCostAccount.displayName");
		evi.getSelector().add("oldCostAccount.curProject.id");
		evi.getSelector().add("oldCostAccount.curProject.name");
		evi.getSelector().add("newAccount.name");
		evi.getSelector().add("newAccount.number");
		evi.getSelector().add("newAccount.displayName");
		evi.getSelector().add("newAccount.longNumber");
		evi.getSelector().add("newAccount.companyID.displayName");
		if(filter.getFilterItems().size() == 0)
		{
		    filter.getFilterItems().add(new FilterItemInfo("oldCostAccount.fullOrgUnit.id", SysContext.getSysContext().getCurrentFIUnit().getId().toString()));
		    filter.getFilterItems().add(new FilterItemInfo("oldCostAccount.curProject.fullOrgUnit.id", SysContext.getSysContext().getCurrentFIUnit().getId().toString()));
            filter.getFilterItems().add(new FilterItemInfo("oldCostAccount.curProject.isEnabled", Boolean.TRUE));
            filter.setMaskString("#0 or (#1 and #2)");
            if("00000000-0000-0000-0000-000000000000CCE7AED4".equals(SysContext.getSysContext().getCurrentFIUnit().getId().toString()))
                setImportButtonStatus(true, true);
            else
                setImportButtonStatus(false, false);
		}
		evi.setFilter(filter);
		newOldAccountRelationCollection=iNewOldAccountRelation.getNewOldAccountRelationCollection(evi);
		if(newOldAccountRelationCollection.size() > 0)
		{
		    for(int i = 0; i < newOldAccountRelationCollection.size(); i++)
		    {
		    	NewOldAccountRelationInfo newOldAccountRelationInfo = newOldAccountRelationCollection.get(i);
		        IRow row = tblMain.addRow();
		        ICell oldAccountCell = row.getCell("oldCostAccount");
		        oldAccountCell.getStyleAttributes().setLocked(false);
		        oldAccountCell.setValue(newOldAccountRelationInfo.getOldCostAccount());
	            
				row.getCell("costAccountName").setValue(newOldAccountRelationInfo.getOldCostAccount().getDisplayName(locale));
				if(newOldAccountRelationInfo.getOldCostAccount().getCurProject()!=null)
					row.getCell("curProject").setValue(newOldAccountRelationInfo.getOldCostAccount().getCurProject().getName());
	            ICell accountCell = row.getCell("newAccount");
	            accountCell.getStyleAttributes().setLocked(false);
	            accountCell.setValue(newOldAccountRelationInfo.getNewAccount());
	            if(newOldAccountRelationInfo.getNewAccount() != null){
	                row.getCell("newAccountName").setValue(newOldAccountRelationInfo.getNewAccount().getDisplayName(locale));
	                row.getCell("newOrg").setValue(newOldAccountRelationInfo.getNewAccount().getCompanyID().getDisplayName(locale));
	            }
	            row.getCell("remark").setValue(newOldAccountRelationInfo.getRemark());
	            row.getCell("id").setValue(newOldAccountRelationInfo.getId().toString());
	            row.setUserObject(newOldAccountRelationInfo);
	        }
	
	    }
	    isModify = false;
	}
    
    private KDBizPromptBox createCostAccountF7()
    throws BOSException
	{
	    KDBizPromptBox f7 = new KDBizPromptBox() {
	
	        protected String valueToString(Object o)
	        {
	            String s = super.valueToString(o);
	            if(!FDCHelper.isEmpty(s) && (o instanceof IObjectValue))
	                return s.replaceAll("!", "\\.");
	        else
	            return s;
	    }
	
		};
		f7.setEditable(true);
		f7.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
		f7.setEditFormat("$longNumber$");
		f7.setCommitFormat("$longNumber$");
		f7.setDisplayFormat("$longNumber$");
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("curProject.displayName");
		sic.add("curProject.name");
		sic.add("curProject.id");
		sic.add("fullOrgUnit.displayName");
		sic.add("fullOrgUnit.id");
		sic.add("curProject.fullOrgUnit.displayName");
		f7.setSelectorCollection(sic);
		EntityViewInfo view = f7.getEntityViewInfo();
		if(view == null)
		    view = new EntityViewInfo();
		if(view.getFilter() == null)
		    view.setFilter(new FilterInfo());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(node != null && (node.getUserObject() instanceof CoreBaseInfo))
		{
		    CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo)node.getUserObject();
		    if(projTreeNodeInfo instanceof OrgStructureInfo)
		    {
		        if("00000000-0000-0000-0000-000000000000CCE7AED4".equals(((OrgStructureInfo)projTreeNodeInfo).getUnit().getId().toString()))
		        {
		            view.getFilter().appendFilterItem("fullOrgUnit.id", "00000000-0000-0000-0000-000000000000CCE7AED4");
		            f7.setEntityViewInfo(view);
		        }
		    } else
		    if(projTreeNodeInfo instanceof CurProjectInfo)
		    {
		        BOSUuid id = projTreeNodeInfo.getId();
		        view.getFilter().appendFilterItem("curProject.id", id.toString());
		        f7.setEntityViewInfo(view);
		    }
		} else
		{
		    view.getFilter().appendFilterItem("fullOrgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		        f7.setEntityViewInfo(view);
	    }
	    return f7;
	}
    
    private KDBizPromptBox createAccountViewF7(CompanyOrgUnitInfo company)
    {
        KDBizPromptBox f7 = new KDBizPromptBox();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("isCFreeze", new Integer(0)));
        AccountPromptBox xx = new AccountPromptBox(this, company==null?SysContext.getSysContext().getCurrentFIUnit():company, filter, false, true);
        f7.setSelector(xx);
        f7.setEditable(false);
        f7.setEditFormat("$number$");
        f7.setCommitFormat("$number$");
        f7.setDisplayFormat("$number$");
        f7.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
        SelectorItemCollection sic = new SelectorItemCollection();
        f7.setSelectorCollection(sic);
        FilterInfo f = new FilterInfo();
        EntityViewInfo view = new EntityViewInfo();
        f.getFilterItems().add(new FilterItemInfo("isCFreeze", new Integer(0)));
        AccountTableInfo tableInfo = null;
        CompanyOrgUnitInfo fiOrgInfo = company==null?SysContext.getSysContext().getCurrentFIUnit():company;
        if(fiOrgInfo != null && fiOrgInfo.getAccountTable() != null)
            tableInfo = fiOrgInfo.getAccountTable();
        else
            try
            {
                tableInfo = AccountTools.getDefaultAccountTableByCU(company==null?SysContext.getSysContext().getCurrentCtrlUnit():company.getCU());
            }
            catch(BOSException e)
            {
                e.printStackTrace();
            }
        if(tableInfo != null && tableInfo.getId() != null)
            f.appendFilterItem("accountTableID.id", tableInfo.getId().toString());
        if(company != null && company.getId() != null)
        {
            f.getFilterItems().add(new FilterItemInfo("companyID.id", company.getId().toString(), CompareType.EQUALS));
        }
        else if(SysContext.getSysContext().getCurrentFIUnit() != null && SysContext.getSysContext().getCurrentFIUnit().getId() != null)
        {
            DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
            if(node == null)
                f.getFilterItems().add(new FilterItemInfo("companyID.id", SysContext.getSysContext().getCurrentFIUnit().getId().toString()));
            else
            if(node.getUserObject() instanceof OrgStructureInfo)
                f.getFilterItems().add(new FilterItemInfo("companyID.id", ((OrgStructureInfo)node.getUserObject()).getUnit().getId().toString(), CompareType.EQUALS));
        }
        view.setFilter(f);
        f7.setEntityViewInfo(view);
        f7.setEditable(true);
        return f7;
    }
    
    
    @Override
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	if(!isFirstIn)
        {
            isFirstIn = true;
            return;
        }
    	
        if(isModify)
        {
            int result = MsgBox.showConfirm2New(this, "\u6570\u636E\u5DF2\u4FEE\u6539\uFF0C\u662F\u5426\u4FDD\u5B58\uFF1F");
            if(result == 0)
            {
              actionSave_actionPerformed(null);
                if(isModify)
                {
                    isFirstIn = false;
                    treeMain.setSelectionPath(e.getOldLeadSelectionPath());
                } else
                {
                    isFirstIn = true;
                    refreshNewOldAccountRelation((DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent());
                }
            } else
            {
                isFirstIn = true;
                refreshNewOldAccountRelation((DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent());
            }
        } else
        {
            isFirstIn = true;
            refreshNewOldAccountRelation((DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent());
        }
        setTblMainEditStatus();
    }
    
    @Override
    protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
    	Object obj=e.getValue();
    	if(obj instanceof AccountViewInfo ){
    		AccountViewInfo account=(AccountViewInfo)obj;
    		if( e.getColIndex()==2){
    			tblMain.getCell(e.getRowIndex(), "oldCompanyName").setValue(account.getDisplayName());
    		}else if( e.getColIndex()==4){
    			tblMain.getCell(e.getRowIndex(), "newCompanyName").setValue(account.getDisplayName());
    		}
    	}
    }
    
    @Override
    protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
    	Object obj=e.getValue();
    	if(obj ==null && (e.getColIndex()==2||e.getColIndex()==4)){
    		tblMain.getCell(e.getRowIndex(), e.getColIndex()+1).setValue(null);
    		if(e.getColIndex()==4){
    			tblMain.getCell(e.getRowIndex(), e.getColIndex()+2).setValue(null);
    		}
    		abort();
    	}
		if( e.getColIndex()==2){
			CostAccountInfo costAccount=(CostAccountInfo)obj;
			for(int i=0;i<tblMain.getRowCount();i++){
    			if(costAccount.equals(tblMain.getRow(i).getCell("oldCostAccount").getValue())&& e.getRowIndex()!=i){
    				MsgBox.showInfo("旧帐套科目已存在，请重新选择！");
    				tblMain.getCell(e.getRowIndex(), "oldCostAccount").setValue(null);
    				tblMain.getCell(e.getRowIndex(), "costAccountName").setValue(null);
    				return;
    			}
    		}
			tblMain.getCell(e.getRowIndex(), "costAccountName").setValue(costAccount.getDisplayName());
			if(costAccount.getCurProject()!=null){
				tblMain.getCell(e.getRowIndex(), "curProject").setValue(costAccount.getCurProject().getName());
			}
		}else if( e.getColIndex()==4){
			AccountViewInfo account=(AccountViewInfo)obj;
			tblMain.getCell(e.getRowIndex(), "newAccountName").setValue(account.getDisplayName());
			CompanyOrgUnitInfo companyOrg=CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitInfo(new ObjectUuidPK(account.getCompanyID().getId().toString()));
			tblMain.getCell(e.getRowIndex(), "newOrg").setValue(companyOrg.getDisplayName());
		}
    }
    
    private void refreshNewOldAccountRelation(DefaultKingdeeTreeNode node) throws Exception {
    	FilterInfo filter = new FilterInfo();
        FilterItemCollection filterItems = filter.getFilterItems();
        if(node == null)
            node = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
        if(node != null && (node.getUserObject() instanceof CoreBaseInfo))
        {
            CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo)node.getUserObject();
            if(projTreeNodeInfo instanceof OrgStructureInfo)
            {
                FullOrgUnitInfo orgUnit = ((OrgStructureInfo)projTreeNodeInfo).getUnit();
                BOSUuid id = orgUnit.getId();
                currentSelectNode = id.toString();
                Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
              //获取新旧组织映射关系
            	INewOldOrgRelation newOldOrgRelation=NewOldOrgRelationFactory.getRemoteInstance();
            	EntityViewInfo view=new EntityViewInfo();
            	view.setFilter(new FilterInfo());
            	view.getFilter().getFilterItems().add(new FilterItemInfo("oldCompanyOrg.id",currentSelectNode));
            	view.setSelector(new SelectorItemCollection());
            	view.getSelector().add(new SelectorItemInfo("*"));
            	view.getSelector().add(new SelectorItemInfo("oldCompanyOrg.*"));
            	view.getSelector().add(new SelectorItemInfo("newCompanyOrg.*"));
            	NewOldOrgRelationCollection orgColl=newOldOrgRelation.getNewOldOrgRelationCollection(view);
            	if(orgColl.size()>0){
            		NewOldOrgRelationInfo org=orgColl.get(0);
            		if(org.getNewCompanyOrg()!=null){
            			newCompanyOrg=org.getNewCompanyOrg();
            		}else{
            			newCompanyOrg=null;
            		}
            	}else{
            		newCompanyOrg=null;
            	}
                
                filterItems.add(new FilterItemInfo("oldCostAccount.curProject.fullOrgUnit.id", idSet, CompareType.INCLUDE));
                if("00000000-0000-0000-0000-000000000000CCE7AED4".equals(orgUnit.getId().toString()))
                {
                    setImportButtonStatus(true, true);
                    loadData(new FilterInfo());
                    return;
                }
                setImportButtonStatus(false, false);
            } else
            if(projTreeNodeInfo instanceof CurProjectInfo)
            {
                CurProjectInfo pInfo = (CurProjectInfo)projTreeNodeInfo;

              //获取新旧组织映射关系
            	INewOldOrgRelation newOldOrgRelation=NewOldOrgRelationFactory.getRemoteInstance();
            	EntityViewInfo view=new EntityViewInfo();
            	view.setFilter(new FilterInfo());
            	view.getFilter().getFilterItems().add(new FilterItemInfo("oldCompanyOrg.id",pInfo.getFullOrgUnit().getId().toString()));
            	view.setSelector(new SelectorItemCollection());
            	view.getSelector().add(new SelectorItemInfo("*"));
            	view.getSelector().add(new SelectorItemInfo("oldCompanyOrg.*"));
            	view.getSelector().add(new SelectorItemInfo("newCompanyOrg.*"));
            	NewOldOrgRelationCollection orgColl=newOldOrgRelation.getNewOldOrgRelationCollection(view);
            	if(orgColl.size()>0){
            		NewOldOrgRelationInfo org=orgColl.get(0);
            		if(org.getNewCompanyOrg()!=null){
            			newCompanyOrg=org.getNewCompanyOrg();
            		}else{
            			newCompanyOrg=null;
            		}
            	}else{
            		newCompanyOrg=null;
            	}
                
                BOSUuid id = projTreeNodeInfo.getId();
                currentSelectNode = id.toString();
                Set idSet = FDCClientUtils.genProjectIdSet(id);
                filterItems.add(new FilterItemInfo("oldCostAccount.curProject.id", idSet, CompareType.INCLUDE));
                if(pInfo.isIsLeaf() && newCompanyOrg!=null)
                    setImportButtonStatus(true, true);
                else
                    setImportButtonStatus(false, false);
            }
            filterItems.add(new FilterItemInfo("oldCostAccount.curProject.isEnabled", Boolean.TRUE));
            filter.setMaskString("#0 and #1");
        }
        loadData(filter);
	}
    
    private void setImportButtonStatus(boolean canDo, boolean canImport)
    {
        btnAddLine.setEnabled(canDo);
        btnRemoveLine.setEnabled(canDo);
        btnInsertLine.setEnabled(canDo);
        btnSave.setEnabled(canDo);
        btnSubmit.setEnabled(canDo);
        menuItemAddLine.setEnabled(canDo);
        menuItemRemoveLine.setEnabled(canDo);
        menuItemInsertLine.setEnabled(canDo);
        menuItemSave.setEnabled(canDo);
        importTempAction.setEnabled(canImport);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	verifyInput(e);
    	NewOldAccountRelationCollection accountRelationColl=new NewOldAccountRelationCollection();
    	INewOldAccountRelation newOldAccountRelation=NewOldAccountRelationFactory.getRemoteInstance();
    	newOldAccountRelation.delete(new FilterInfo());
    	for(int i = 0; i < tblMain.getRowCount(); i++)
        {
            NewOldAccountRelationInfo newOldAccountRelationInfo = new NewOldAccountRelationInfo();
            newOldAccountRelationInfo.setOldCostAccount((CostAccountInfo)tblMain.getRow(i).getCell("oldCostAccount").getValue());
            newOldAccountRelationInfo.setNewAccount((AccountViewInfo)tblMain.getRow(i).getCell("newAccount").getValue());
            if(tblMain.getRow(i).getCell("id").getValue() != null)
            	newOldAccountRelationInfo.setId(BOSUuid.read(tblMain.getRow(i).getCell("id").getValue().toString()));
            if(tblMain.getRow(i).getCell("remark").getValue() != null)
            {
            	newOldAccountRelationInfo.setRemark(tblMain.getRow(i).getCell("remark").getValue().toString());
            }
            accountRelationColl.add(newOldAccountRelationInfo);
        }
    	newOldAccountRelation.submitAll(accountRelationColl,currentSelectNode);
    	
    	MsgBox.showInfo(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Save_Successed"));
        this.isModify = false;
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	verifyInput(e);
    	NewOldAccountRelationCollection accountRelationColl=new NewOldAccountRelationCollection();
    	INewOldAccountRelation newOldAccountRelation=NewOldAccountRelationFactory.getRemoteInstance();
    	newOldAccountRelation.delete(new FilterInfo());
    	for(int i = 0; i < tblMain.getRowCount(); i++)
        {
            NewOldAccountRelationInfo newOldAccountRelationInfo = new NewOldAccountRelationInfo();
            newOldAccountRelationInfo.setOldCostAccount((CostAccountInfo)tblMain.getRow(i).getCell("oldCostAccount").getValue());
            newOldAccountRelationInfo.setNewAccount((AccountViewInfo)tblMain.getRow(i).getCell("newAccount").getValue());
            if(tblMain.getRow(i).getCell("id").getValue() != null)
            	newOldAccountRelationInfo.setId(BOSUuid.read(tblMain.getRow(i).getCell("id").getValue().toString()));
            if(tblMain.getRow(i).getCell("remark").getValue() != null)
            {
            	newOldAccountRelationInfo.setRemark(tblMain.getRow(i).getCell("remark").getValue().toString());
            }
            accountRelationColl.add(newOldAccountRelationInfo);
        }
    	newOldAccountRelation.submitAll(accountRelationColl,currentSelectNode);
    	
    	MsgBox.showInfo(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Save_Successed"));
        this.isModify = false;
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    	 tblMain.checkParsed();
         int index = -1;
         index = tblMain.getRowCount();
         IRow row;
         if(index != -1)
             row = tblMain.addRow(index);
         else
             row = tblMain.addRow();
         ICell cellValueAttribute = row.getCell("oldCostAccount");
         cellValueAttribute.getStyleAttributes().setLocked(false);
         cellValueAttribute.setValue(null);
         JTextField txtEnable = new JTextField();
         KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(txtEnable);
         ceEnable = new KDTDefaultCellEditor(createCostAccountF7());
         cellValueAttribute.setEditor(ceEnable);
         cellValueAttribute = row.getCell("newAccount");
         cellValueAttribute.getStyleAttributes().setLocked(false);
         cellValueAttribute.setValue(null);
         txtEnable = new JTextField();
         ceEnable = new KDTDefaultCellEditor(txtEnable);
         ceEnable = new KDTDefaultCellEditor(createAccountViewF7(newCompanyOrg));
         cellValueAttribute.setEditor(ceEnable);
         
         row.getCell("newAccountName").getStyleAttributes().setLocked(true);
         row.getCell("costAccountName").getStyleAttributes().setLocked(true);
         row.getCell("remark").getStyleAttributes().setLocked(false);
         if(newOldAccountRelationCollection.size() == 0)
             btnSave.setEnabled(true);
         isModify = true;
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    	 int i = -1;
         i = tblMain.getSelectManager().getActiveRowIndex();
         if(i == -1)
         {
             MsgBox.showError(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Insert"));
             return;
         } else
         {
             IRow row = tblMain.addRow(i);
             ICell cellValueAttribute = row.getCell("oldCostAccount");
             cellValueAttribute.getStyleAttributes().setLocked(false);
             cellValueAttribute.setValue(null);
             JTextField txtEnable = new JTextField();
             KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(txtEnable);
             ceEnable = new KDTDefaultCellEditor(createCostAccountF7());
             cellValueAttribute.setEditor(ceEnable);
             cellValueAttribute = row.getCell("newAccount");
             cellValueAttribute.getStyleAttributes().setLocked(false);
             cellValueAttribute.setValue(null);
             txtEnable = new JTextField();
             ceEnable = new KDTDefaultCellEditor(txtEnable);
             ceEnable = new KDTDefaultCellEditor(createAccountViewF7(newCompanyOrg));
             cellValueAttribute.setEditor(ceEnable);
         
             row.getCell("newAccountName").getStyleAttributes().setLocked(true);
             row.getCell("costAccountName").getStyleAttributes().setLocked(true);
             row.getCell("remark").getStyleAttributes().setLocked(false);
             isModify = true;
             return;
         }
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    	int i = -1;
        i = tblMain.getSelectManager().getActiveRowIndex();
        if(i == -1)
        {
            MsgBox.showError(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Delete"));
            return;
        }
       // checkRef(tblMain);
        tblMain.removeRow(i);
        if(tblMain.getRowCount() <= 0 && newOldAccountRelationCollection.size() <= 0)
        {
            btnSave.setEnabled(false);
        } else
        {
            isModify = true;
            btnSave.setEnabled(true);
        }
    }
    
    @Override
    public void importTempAction_actionPerformed(ActionEvent e)
    		throws Exception {
         /*DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
         if(node != null && (node.getUserObject() instanceof CoreBaseInfo))
         {
        	 OrgStructureInfo nodeInfo = (OrgStructureInfo)node.getUserObject();
        	 refreshNewOldAccountRelation((DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent());
         }*/
    	 MsgBox.showInfo(this, "\u5DF2\u5B58\u5728\u7684\u6210\u672C\u79D1\u76EE\u5C06\u4E0D\u518D\u5F15\u5165\uFF1B\u4F1A\u8BA1\u79D1\u76EE\u4E0D\u662F\u660E\u7EC6\u79D1\u76EE\u7684\u5C06\u4F1A\u8BBE\u7F6E\u4E3A\u5BF9\u5E94\u4F1A\u8BA1\u79D1\u76EE\u4E0B\u7EA7\u7684\u7B2C\u4E00\u4E2A\u660E\u7EC6\u4F1A\u8BA1\u79D1\u76EE");
         Set idSet = null;
         DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
         if(node != null && (node.getUserObject() instanceof CoreBaseInfo))
         {
             CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo)node.getUserObject();
             if(projTreeNodeInfo instanceof OrgStructureInfo)
                 idSet = FDCClientUtils.getProjIdsOfCostOrg(((OrgStructureInfo)projTreeNodeInfo).getUnit(), true);
             else
             if(projTreeNodeInfo instanceof CurProjectInfo)
                 idSet = FDCClientUtils.genProjectIdSet(projTreeNodeInfo.getId(), true);
         } else
         {
             idSet = FDCClientUtils.getProjIdsOfCostOrg((FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit(), true);
         }
         if(idSet != null && idSet.size() > 0)
         {
             NewOldAccountRelationFactory.getRemoteInstance().importGroupData((HashSet)idSet,newCompanyOrg);
             refreshNewOldAccountRelation((DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent());
         }
    }
    
    @Override
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	for(int i = 0; i < tblMain.getRowCount(); i++)
        {
            if(tblMain.getRow(i).getCell("oldCostAccount").getValue() == null)
            {
                MsgBox.showError("第"+(i+1)+"行旧帐套成本科目不允许为空！");
                this.abort();
            }
            if(tblMain.getRow(i).getCell("newAccount").getValue() == null)
            {
                MsgBox.showError("第"+(i+1)+"行新帐套会计科目不允许为空！");
                this.abort();
            }
        }
    }

	@Override
	protected IObjectCollection createNewCollection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return NewOldAccountRelationFactory.getRemoteInstance();
	}

}