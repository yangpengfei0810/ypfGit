/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.DynCostDetailFactory;
import com.kingdee.eas.fdc.aimcost.PDCostDetailEntryCollection;
import com.kingdee.eas.fdc.aimcost.PDCostDetailEntryInfo;
import com.kingdee.eas.fdc.aimcost.PDCostDetailFactory;
import com.kingdee.eas.fdc.aimcost.PDCostDetailInfo;
import com.kingdee.eas.fdc.aimcost.ProductDynamicDetailFacadeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProductDynamicCostDetailUI extends AbstractProductDynamicCostDetailUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProductDynamicCostDetailUI.class);
    
    private CostCenterOrgUnitInfo currentOrg;
    private Map productTypeMap = new HashMap();
	private Set productKeySet = new HashSet();
	
	Map detailDataMap = new HashMap();
	
//	Set leafPrjIds = new HashSet();
    
    public ProductDynamicCostDetailUI() throws Exception
    {
        super();
        currentOrg = SysContext.getSysContext().getCurrentCostUnit();
    }
    
    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	btnAuditResult.setVisible(false);
    	btnAttachment.setVisible(false);
    	
    	initTree();
    	
    	tblMain.checkParsed();
    	tblMain.getDataRequestManager().setDataRequestMode(0);
        tblMain.getViewManager().setFreezeView(-1, 2);
        tblMain.setRefresh(false);
        tblMain.getSelectManager().setSelectMode(2);
        tblMain.getColumn("acctNumber").getStyleAttributes().setLocked(true);
        tblMain.getColumn("aimCostTotal").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        tblMain.getColumn("dynamicCostTotal").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        tblMain.getColumn("aimCostTotal").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
        tblMain.getColumn("dynamicCostTotal").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
        tblMain.addHeadRow(1, (IRow)tblMain.getHeadRow(0).clone());
        for(int i = 0; i < tblMain.getColumnCount(); i++){
            tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
        }
        tblMain.setColumnMoveable(true);
        
        if("VIEW".equals(oprtState) || "EDIT".equals(oprtState)){
        	kDTreeView1.setVisible(false);
        	actionQuery_actionPerformed(null);
        }
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	btnAddNew.setVisible(false);
    	btnCopy.setVisible(false);
    	btnAttachment.setVisible(false);
    	btnFirst.setVisible(false);
    	btnPre.setVisible(false);
    	btnNext.setVisible(false);
    	btnLast.setVisible(false);
    	btnTraceUp.setVisible(false);
    	btnTraceDown.setVisible(false);
    	btnWorkFlowG.setVisible(false);
    	btnCreateFrom.setVisible(false);
    	btnAddLine.setVisible(false);
    	btnInsertLine.setVisible(false);
    	btnRemoveLine.setVisible(false);
    	btnAuditResult.setVisible(false);
    	btnMultiapprove.setVisible(false);
    	btnNextPerson.setVisible(false);
    	btnPrint.setVisible(false);
    	btnPrintPreview.setVisible(false);
    }
    
    protected void initListener() {
    	super.initListener();
    	this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                	treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
    }
    
    @Override
    protected void tblMain_tableSelectChanged(KDTSelectEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    	super.tblMain_tableSelectChanged(e);
    }
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	oprtState = "ADDNEW";
    	String selectProjectID = getSelectProjectID();
    	if(null != editData && null != editData.getId()){
    		actionAddNew_actionPerformed(null);
    	}
		if (selectProjectID == null) {
			return;
		}
		actionQuery_actionPerformed(null);
    }
    
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
//		String curProjectID = getSelectProjectID();
//		if("".equals(prmtCurProject.getValue())){
//			MsgBox.showInfo(this, "请选择工程项目节点（非公司，如果没有工程项目，请先增加工程项目）");
//			SysUtil.abort();
//		}
		
		if(null != editData && null != editData.getId()){
			if(PDCostDetailFactory.getRemoteInstance().exists(new ObjectUuidPK(editData.getId().toString()))){
				editData = PDCostDetailFactory.getRemoteInstance().getPDCostDetailInfo(new ObjectUuidPK(editData.getId().toString()));
				if(editData == null )return;
				setDataObject(editData);
//				IRowSet rs = iFacade.getDetailDataForID(editData.getId().toString());
				fillTableForSave();
			}
		}else{
//			IRowSet rs = iFacade.getDynCostDetailData(curProjectID);
//			fillTableRowData(rs);
			fetchAndFill();
			initUserConfig();
		}
	}
    
    Map saveDataMap = new HashMap();
    Map costTotalMap = new HashMap();
    
    //填充已保存的数据
    private void fillTableForSave() throws Exception {
    	PDCostDetailEntryCollection coll = editData.getEntries();
    	
    	Set projectIDs = new HashSet();
    	projectIDs.add(editData.getCurProjectID());
    	getProductTypeMap(projectIDs);
    	resetProductCol();
    	
    	for (int i = 0; i < coll.size(); i++) {
    		PDCostDetailEntryInfo info = coll.get(i);
    		
    		String acctKey = info.getCostAccountID().toString();
    		String productKey = info.getProductTypeID().toString();
    		
    		PDCostDetailEntryInfo costInfo = new PDCostDetailEntryInfo();
    		costInfo.setAimCostTotal(info.getAimCostTotal());
    		costInfo.setDynamicCostTotal(info.getDynamicCostTotal());
    		costInfo.setId(info.getId());
    		costTotalMap.put(acctKey, costInfo);
    		
    		Map acctMap = new HashMap();
    		if(saveDataMap.containsKey(acctKey)){
    			acctMap = (Map) saveDataMap.get(acctKey);
    			acctMap.put(productKey, info);
    		}else{
    			acctMap.put(productKey, info);
    		}
    		saveDataMap.put(acctKey, acctMap);
		}
    	
    	fillTableCost();
	}
    
    public void fillTableCost() throws Exception {
        tblMain.removeRows();
        String selectProjectID = editData.getCurProjectID();
        BOSObjectType bosType = BOSUuid.read(selectProjectID).getType();
        FilterInfo acctFilter = new FilterInfo();
        if((new CurProjectInfo()).getBOSType().equals(bosType)){
            acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", selectProjectID));
        } else {
            acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", selectProjectID));
        }
        acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
        AcctAccreditHelper.handAcctAccreditFilter(null, selectProjectID, acctFilter);
        TreeModel costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
        
        DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)costAcctTree.getRoot();
        Enumeration childrens = root.depthFirstEnumeration();
        int maxLevel = 0;
        do {
            if(!childrens.hasMoreElements()){
                break;
            }
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)childrens.nextElement();
            if(node.getUserObject() != null && node.getLevel() > maxLevel){
                maxLevel = node.getLevel();
            }
        } while(true);
        tblMain.getTreeColumn().setDepth(maxLevel);
        for(int i = 0; i < root.getChildCount(); i++){
        	fillNodeForSave(tblMain, (DefaultMutableTreeNode)root.getChildAt(i));
        }
        setUnionData();
        appendFootRow();
    }
    
    private void fillNodeForSave(KDTable table, DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
        CostAccountInfo costAcct = (CostAccountInfo)node.getUserObject();
        if(costAcct == null) {
            MsgBox.showError("too many costAccount level!");
            return;
        }
        
        IRow row = table.addRow();
        row.setTreeLevel(node.getLevel() - 1);
        row.getStyleAttributes().setLocked(true);
        String longNumber = costAcct.getLongNumber();
        longNumber = longNumber.replace('!', '.');
        row.setUserObject(costAcct);
        row.getCell("acctNumber").setValue(longNumber);
        row.getCell("acctName").setValue(costAcct.getName());
        
        
        //对公建配套做特殊处理
//        if("4001.07".equals(longNumber)){
//        	IRow cloneRow = table.addRow(row.getRowIndex()+1, (IRow) row.clone());
//        	cloneRow.setTreeLevel(node.getLevel());
//        	CostAccountInfo cloneCostAcct = (CostAccountInfo) costAcct.clone();
//        	cloneCostAcct.setIsLeaf(true);
//        	cloneRow.setUserObject(cloneCostAcct);
//        	
//        	String acctKey = costAcct.getId().toString();
//        	Map acctMap = (Map) saveDataMap.get(acctKey);
//        	
//        	for (int j = 4; j < tblMain.getColumnCount(); j++) {
//				String colKey = tblMain.getColumnKey(j);
//				if(colKey.endsWith("dynamicCost")){
//					String productKey = colKey.substring(0, colKey.length() - 11);
//					if(acctMap.containsKey(productKey)){
//						PDCostDetailEntryInfo info = (PDCostDetailEntryInfo) acctMap.get(productKey);
//						row.getCell(colKey).setValue(info.getDynamicCost());
//					}
//				}
//			}
//        	cloneRow.getStyleAttributes().setHided(true);
//        }
        
        	
        if(node.isLeaf()) {
        	String acctKey = costAcct.getId().toString();
        	Map acctMap = (Map) saveDataMap.get(acctKey);
        	
        	PDCostDetailEntryInfo costTotalInfo = (PDCostDetailEntryInfo) costTotalMap.get(acctKey);
//        	row.getCell("ID").setValue(costTotalInfo.getId());
        	row.getCell("aimCostTotal").setValue(costTotalInfo.getAimCostTotal());
        	row.getCell("dynamicCostTotal").setValue(costTotalInfo.getDynamicCostTotal());
        	
        	for (int j = 4; j < tblMain.getColumnCount(); j++) {
				String colKey = tblMain.getColumnKey(j);
				
				if(colKey.endsWith("aimSale")){
					String productKey = colKey.substring(0, colKey.length() - 7);
					if(acctMap.containsKey(productKey)){
						PDCostDetailEntryInfo info = (PDCostDetailEntryInfo) acctMap.get(productKey);
						row.getCell(colKey).setValue(info.getAimSale());
					}
				}
				if(colKey.endsWith("aimCost")){
					String productKey = colKey.substring(0, colKey.length() - 7);
					if(acctMap.containsKey(productKey)){
						PDCostDetailEntryInfo info = (PDCostDetailEntryInfo) acctMap.get(productKey);
						row.getCell(colKey).setValue(info.getAimCost());
					}
				}
				if(colKey.endsWith("dynamicSale")){
					String productKey = colKey.substring(0, colKey.length() - 11);
					if(acctMap.containsKey(productKey)){
						PDCostDetailEntryInfo info = (PDCostDetailEntryInfo) acctMap.get(productKey);
						row.getCell(colKey).setValue(info.getDynamicSale());
					}
				}
				if(colKey.endsWith("dynamicCost")){
					String productKey = colKey.substring(0, colKey.length() - 11);
					if(acctMap.containsKey(productKey)){
						PDCostDetailEntryInfo info = (PDCostDetailEntryInfo) acctMap.get(productKey);
						row.getCell(colKey).setValue(info.getDynamicCost());
					}
				}
			}
        } else {
            for(int i = 0; i < node.getChildCount(); i++){
            	fillNodeForSave(table, (DefaultMutableTreeNode)node.getChildAt(i));
            }
        }
    }
    
    private void initTree() throws Exception {
    	ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, this.treeMain, this.actionOnLoad);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeMain.getModel().getRoot();
		treeMain.expandAllNodes(true, root);
	}
    
    private String getSelectProjectID() {
        DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
        if(node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)){
            return null;
        }
        if(node.isLeaf()){
        	btnSave.setEnabled(true);
        	btnSubmit.setEnabled(true);
        }else{
        	btnSave.setEnabled(false);
        	btnSubmit.setEnabled(false);
        }
        if(node.getUserObject() instanceof CurProjectInfo) {
            CurProjectInfo projectInfo = (CurProjectInfo)node.getUserObject();
            return projectInfo.getId().toString();
        }
        if(node.getUserObject() instanceof OrgStructureInfo) {
            OrgStructureInfo oui = (OrgStructureInfo)node.getUserObject();
            if(oui.getUnit() != null);
            FullOrgUnitInfo info = oui.getUnit();
            return info.getId().toString();
        } else {
            return null;
        }
    }
    
//    private CurProjectInfo getSelectProject() {
//        DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
//        if(node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)){
//            return null;
//        }
//        if(node.getUserObject() instanceof CurProjectInfo) {
//            CurProjectInfo projectInfo = (CurProjectInfo)node.getUserObject();
//            return projectInfo;
//        } else {
//            return null;
//        }
//    }
    
    private void fetchAndFill() throws EASBizException, BOSException, Exception {
		LongTimeDialog dialog = UITools.getDialog(this);
		if (dialog == null){
			return;
		}
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
//				Set leafPrjIds.clear();
				Set leafPrjIds = FDCClientHelper.getProjectLeafsOfNode(node);
				
				getProductTypeMap(leafPrjIds);
				
				detailDataMap.clear();
				detailDataMap = ProductDynamicDetailFacadeFactory.getRemoteInstance().getDetailData(leafPrjIds);
//				getAimCostMap(leafPrjIds);
//				getSellArea(leafPrjIds);
//				getDynamicCost(leafPrjIds);
				
				resetProductCol();
				setApporAction();
				fillTable();
				
				return null;
			}
			public void afterExec(Object obj) throws Exception {
			}
		});
		if (dialog != null)
			dialog.show();
	}
    
    private void getProductTypeMap(Set projectIDs) throws BOSException {
    	productTypeMap.clear();
    	productKeySet.clear();
    	ProductTypeCollection coll = ProductDynamicDetailFacadeFactory.getRemoteInstance().getProductTypes(projectIDs);
    	for (int i = 0; i < coll.size(); i++) {
			ProductTypeInfo info = coll.get(i);
			productTypeMap.put(info.getId().toString(), info);
			productKeySet.add(info.getId().toString());
		}
	}
    
    private void resetProductCol() {
    	int columnCount = tblMain.getColumnCount();
		for (int i = columnCount-1; i > 3; i--){
			tblMain.removeColumn(i);
		}
		int i = 0;
		Set keySet = productTypeMap.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String productKdy = (String) iter.next();
			ProductTypeInfo product = (ProductTypeInfo) productTypeMap.get(productKdy);
			
			IColumn column = tblMain.addColumn();
			String key = product.getId().toString() + "aimSale";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue("目标可售单方");
			
			column = tblMain.addColumn();
			key = product.getId().toString() + "aimCost";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue("目标成本");
			
			column = tblMain.addColumn();
			key = product.getId().toString() + "dynamicSale";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue("动态可售单方");
			
			column = tblMain.addColumn();
			key = product.getId().toString() + "dynamicCost";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue("动态成本");
			
			tblMain.getHeadMergeManager().mergeBlock(0, 4 + i * 4, 0, 4 + i * 4 + 3);
			i++;
		}
    }
    
    private void setApporAction() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (currentOrg == null || !currentOrg.isIsBizUnit()) {
		}
		if (!node.isLeaf()) {
			this.actionSubmit.setEnabled(false);
		} else {
			this.actionSubmit.setEnabled(true);
		}
	}
    
    public void fillTable() throws Exception {
        tblMain.removeRows();
        String selectProjectID = getSelectProjectID();
        BOSObjectType bosType = BOSUuid.read(selectProjectID).getType();
        FilterInfo acctFilter = new FilterInfo();
        if((new CurProjectInfo()).getBOSType().equals(bosType)){
            acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", selectProjectID));
        } else {
            acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", selectProjectID));
        }
        acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
        AcctAccreditHelper.handAcctAccreditFilter(null, selectProjectID, acctFilter);
        TreeModel costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
        
        DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)costAcctTree.getRoot();
        Enumeration childrens = root.depthFirstEnumeration();
        int maxLevel = 0;
        do {
            if(!childrens.hasMoreElements()){
                break;
            }
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)childrens.nextElement();
            if(node.getUserObject() != null && node.getLevel() > maxLevel){
                maxLevel = node.getLevel();
            }
        } while(true);
        tblMain.getTreeColumn().setDepth(maxLevel);
        for(int i = 0; i < root.getChildCount(); i++){
            fillNode(tblMain, (DefaultMutableTreeNode)root.getChildAt(i));
        }
        setUnionData();
        appendFootRow();
    }
    
    private void fillNode(KDTable table, DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
        CostAccountInfo costAcct = (CostAccountInfo)node.getUserObject();
        if(costAcct == null) {
            MsgBox.showError("too many costAccount level!");
            return;
        }
        Map aimSellAreaMap = (Map) detailDataMap.get("aimSellAreaMap");
        Map dynSellAreaMap = (Map) detailDataMap.get("dynSellAreaMap");
        Map aimCostMap =  (Map) detailDataMap.get("aimCostMap");
        Map dynamicCost = (Map) detailDataMap.get("dynamicMap");
        
        IRow row = table.addRow();
        row.setTreeLevel(node.getLevel() - 1);
        row.getStyleAttributes().setLocked(true);
        String longNumber = costAcct.getLongNumber();
        longNumber = longNumber.replace('!', '.');
        row.setUserObject(costAcct);
        row.getCell("acctNumber").setValue(longNumber);
        row.getCell("acctName").setValue(costAcct.getName());
        
        //对公建配套做特殊处理
        if("4001.04".equals(longNumber)){
        	Map splitAmountMap = ProductDynamicDetailFacadeFactory.getRemoteInstance().getSplitAmount(getSelectProjectID());
        	
        	//由于公建配套不会存在子科目，故可直接填值
        	for (int j = 4; j < tblMain.getColumnCount(); j++) {
				String colKey = tblMain.getColumnKey(j);
				if(colKey.endsWith("dynamicCost")){
					String key = colKey.substring(0, colKey.length() - 11);
					if(splitAmountMap.containsKey(key)){
						BigDecimal amount = (BigDecimal) splitAmountMap.get(key);
						row.getCell(colKey).setValue(amount);
						if(dynSellAreaMap.containsKey(key)){
							BigDecimal sellArea = (BigDecimal) dynSellAreaMap.get(key);
							row.getCell(key+"dynamicSale").setValue(FDCHelper.divide(amount, sellArea));
						}
					}
				}
			}
        	
        	//以下是针对公建配套下存在子科目的情况下
//        	IRow cloneRow = table.addRow(row.getRowIndex()+1, (IRow) row.clone());
//        	cloneRow.setTreeLevel(node.getLevel());
//        	CostAccountInfo cloneCostAcct = (CostAccountInfo) costAcct.clone();
//        	cloneCostAcct.setIsLeaf(true);
//        	cloneRow.setUserObject(cloneCostAcct);
//        	Map splitAmountMap = ProductDynamicDetailFacadeFactory.getRemoteInstance().getSplitAmount(getSelectProjectID());
//        	
//        	for (int j = 4; j < tblMain.getColumnCount(); j++) {
//				String colKey = tblMain.getColumnKey(j);
//				if(colKey.endsWith("dynamicCost")){
//					String key = colKey.substring(0, colKey.length() - 11);
//					if(splitAmountMap.containsKey(key)){
//						BigDecimal amount = (BigDecimal) splitAmountMap.get(key);
//						cloneRow.getCell(colKey).setValue(amount);
//					}
//				}
//			}
//        	cloneRow.getStyleAttributes().setHided(true);
		}
        
        if(node.isLeaf()) {
//    		String costKey = costAcct.getId().toString();
    		if(aimCostMap.containsKey(longNumber)){
    			Map productMap = (Map) aimCostMap.get(longNumber);
    			for (int j = 4; j < tblMain.getColumnCount(); j++) {
					String colKey = tblMain.getColumnKey(j);
					if(colKey.endsWith("aimCost")){
						String key = colKey.substring(0, colKey.length() - 7);
						if(productMap.containsKey(key)){
							BigDecimal aimCost = (BigDecimal) productMap.get(key);
							row.getCell(colKey).setValue(aimCost);
							if(aimSellAreaMap.containsKey(key)){
								BigDecimal sellArea = (BigDecimal) aimSellAreaMap.get(key);
								row.getCell(key+"aimSale").setValue(FDCHelper.divide(aimCost, sellArea));
							}
						}
					}
				}
    		}
    		if(dynamicCost.containsKey(longNumber)){
    			Map productMap = (Map) dynamicCost.get(longNumber);
    			for (int j = 4; j < tblMain.getColumnCount(); j++) {
					String colKey = tblMain.getColumnKey(j);
					if(colKey.endsWith("dynamicCost")){
						String key = colKey.substring(0, colKey.length() - 11);
						if(productMap.containsKey(key)){
							BigDecimal dynamicCostTemp = (BigDecimal) productMap.get(key);
							row.getCell(colKey).setValue(dynamicCostTemp);
							if(dynSellAreaMap.containsKey(key)){
								BigDecimal sellArea = (BigDecimal) dynSellAreaMap.get(key);
								row.getCell(key+"dynamicSale").setValue(FDCHelper.divide(dynamicCostTemp, sellArea));
							}
						}
					}
				}
    		}
    		BigDecimal aimCostTotal = null;
    		BigDecimal dynamicCostTotal = null; 
    		for (Iterator iterator = productKeySet.iterator(); iterator.hasNext();) {
    			String key = iterator.next().toString();
    			aimCostTotal = FDCHelper.add(aimCostTotal, row.getCell(key+"aimCost").getValue());
    			dynamicCostTotal = FDCHelper.add(dynamicCostTotal, row.getCell(key+"dynamicCost").getValue());
    		}
    		row.getCell("aimCostTotal").setValue(aimCostTotal);
    		row.getCell("dynamicCostTotal").setValue(dynamicCostTotal);
        } else {
            for(int i = 0; i < node.getChildCount(); i++){
                fillNode(table, (DefaultMutableTreeNode)node.getChildAt(i));
            }
        }
    }
    
    public void setUnionData() throws BOSException {
    	
    	List amountColumns = new ArrayList();
		amountColumns.add("aimCostTotal");
		amountColumns.add("dynamicCostTotal");
		for (Iterator iterator = productKeySet.iterator(); iterator.hasNext();) {
			String productTypeId = iterator.next().toString();
			amountColumns.add(productTypeId + "aimSale");
			amountColumns.add(productTypeId + "aimCost");
			amountColumns.add(productTypeId + "dynamicSale");
			amountColumns.add(productTypeId + "dynamicCost");
			
		}
    	
    	for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row.getUserObject() instanceof CostAccountInfo) {
				CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
				if (acct.isIsLeaf())
					continue;
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				for (int j = i + 1; j < tblMain.getRowCount(); j++) {
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() instanceof CostAccountInfo) {
						acct = (CostAccountInfo) rowAfter.getUserObject();
						if (acct.isIsLeaf()) {
							aimRowList.add(rowAfter);
						}
					}
				}
				for (int j = 0; j < amountColumns.size(); j++) {
					BigDecimal sum = null;
					String col = amountColumns.get(j).toString();
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(col).getValue();
						if (value != null) {
							sum = FDCHelper.toBigDecimal(sum, 2).add(FDCHelper.toBigDecimal(value, 2));
						}
					}
					row.getCell(col).setValue(sum);
				}
			}
		}
    	
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	if(null == editData || null == editData.getId()){
    		editData.setCurProjectID(getSelectProjectID());
    	}
    	if(null != editData && null != editData.getId()){
    		PDCostDetailInfo info = PDCostDetailFactory.getRemoteInstance().getPDCostDetailInfo(new ObjectUuidPK(editData.getId()));
    		if(info == null){
    			MsgBox.showInfo(this, "记录已不存在！");
    			SysUtil.abort();
    		}else if(info.getState() == FDCBillStateEnum.SUBMITTED){
    			MsgBox.showInfo(this, "单据已提交，不能保存！");
    			SysUtil.abort();
    		}
    	}
    	editData.setState(FDCBillStateEnum.SAVED);
    	getSubmitData();
    	super.actionSave_actionPerformed(e);
    	actionQuery_actionPerformed(null);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	if(null == editData || null == editData.getId()){
    		editData.setCurProjectID(getSelectProjectID());
    	}
    	if(null != editData && null != editData.getId()){
    		if(!PDCostDetailFactory.getRemoteInstance().exists(new ObjectUuidPK(editData.getId()))){
    			MsgBox.showInfo(this, "记录已不存在！");
    			SysUtil.abort();
    		}
    	}
    	editData.setState(FDCBillStateEnum.SUBMITTED);
    	getSubmitData();
    	super.actionSubmit_actionPerformed(e);
    	oprtState = "ADDNEW";
    	tblMain.removeRows();
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
//    	if(null == getSelectProjectID()){
//			MsgBox.showInfo(this, "请先选择工程项目");
//			SysUtil.abort();
//		}
		if("ADDNEW".equals(oprtState) || "EDIT".equals(oprtState)){
			if("".equals(txtVersionName.getText().trim())){
				MsgBox.showInfo(this, "版本名称不能为空，请修改后再保存");
				SysUtil.abort();
			}
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject",getSelectProjectID(),CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("name",txtVersionName.getText().trim(),CompareType.EQUALS));
			if(null != editData && null != editData.getId()){
				filter.getFilterItems().add(new FilterItemInfo("id",editData.getId(),CompareType.NOTEQUALS));
			}
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			if(DynCostDetailFactory.getRemoteInstance().exists(filter)){
				MsgBox.showInfo(this, "该工程项目下版本名称已经存在，不能重复");
				SysUtil.abort();
			}
		}
		if(pkBizDate.getValue() == null || "".equals(pkBizDate.getValue())){
			MsgBox.showInfo(this, "业务日期不能为空！");
			SysUtil.abort();
		}
		if(tblMain.getRowCount() < 1){
			MsgBox.showInfo(this, "分录数据不能为空！");
			SysUtil.abort();
		}
    }
    
    private CoreBaseCollection getSubmitData() {
    	PDCostDetailEntryCollection coll = editData.getEntries();
    	coll.clear();
    	for(int i = 0; i < tblMain.getRowCount(); i++){
    		IRow row = tblMain.getRow(i);
            if(row.getUserObject() == null){
                continue;
            }
            CostAccountInfo costAcct = (CostAccountInfo) row.getUserObject();
            if(!costAcct.isIsLeaf()){
            	continue;
            }
            int dynamicCol = tblMain.getColumnCount() - 4;
            PDCostDetailEntryInfo entryInfo = new PDCostDetailEntryInfo();
            for (int j = 0; j < dynamicCol; j++) {
            	int colIndex = 4+j;
            	if(j % 4 == 0){
            		entryInfo = new PDCostDetailEntryInfo();
            		entryInfo.setParent(editData);
            		entryInfo.setCostAccountID(costAcct.getId().toString());
            		String colKey = tblMain.getColumnKey(colIndex);
            		String productID = colKey.substring(0, colKey.length() - 7);
            		entryInfo.setProductTypeID(productID);
            		entryInfo.setAimCostTotal((BigDecimal) row.getCell("aimCostTotal").getValue());
            		entryInfo.setDynamicCostTotal((BigDecimal) row.getCell("dynamicCostTotal").getValue());
            		coll.add(entryInfo);
            	}
            	
            	if(tblMain.getColumnKey(colIndex).endsWith("aimSale")){
            		entryInfo.setAimSale((BigDecimal) row.getCell(colIndex).getValue());
            	}
				if (tblMain.getColumnKey(colIndex).endsWith("aimCost")) {
					entryInfo.setAimCost((BigDecimal) row.getCell(colIndex).getValue());
				}
				if (tblMain.getColumnKey(colIndex).endsWith("dynamicSale")) {
					entryInfo.setDynamicSale((BigDecimal) row.getCell(colIndex).getValue());
				}
				if (tblMain.getColumnKey(colIndex).endsWith("dynamicCost")) {
					entryInfo.setDynamicCost((BigDecimal) row.getCell(colIndex).getValue());
				}
			}
    	}
    	return null;
    }
    
	protected void appendFootRow() {
		try {
			IRow footRow = null;
			KDTFootManager footRowManager = tblMain.getFootManager();
			if (footRowManager == null) {
				String total = "合计（不含营销费用）";//EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
				footRowManager = new KDTFootManager(this.tblMain);
				footRowManager.addFootView();
				this.tblMain.setFootManager(footRowManager);
				footRow = footRowManager.addFootRow(0);
				footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
				this.tblMain.getIndexColumn().setWidth(30);
				footRowManager.addIndexText(0, total);
			} else {
				footRow = footRowManager.getFootRow(0);
			}

			for (int j = 2; j < tblMain.getColumnCount(); j++) {
				BigDecimal total = null;
		    	for (int i = 0; i < tblMain.getRowCount(); i++) {
					IRow row = tblMain.getRow(i);
					if (row.getUserObject() instanceof CostAccountInfo) {
						CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
						//合计不包含营销费用
						if (!acct.isIsLeaf() || acct.getLongNumber().startsWith("4001!08") || acct.getLongNumber().startsWith("4002")){
							continue;
						}
						total = FDCHelper.add(row.getCell(j).getValue(),total);
							
					}
				}
		    	footRow.getCell(j).setValue(total);
			}
			
			footRow.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PDCostDetailFactory.getRemoteInstance();
	}
	
	protected IObjectValue createNewData() {
		txtNumber.setText("test");
		return new PDCostDetailInfo();
	}
}