package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.client.util.PermissionHelper;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryFactory;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.ITargetCostSplit;
import com.kingdee.eas.fdc.aimcost.MeasureCollectDataInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.SaleAreaDataInfo;
import com.kingdee.eas.fdc.aimcost.TargetCostSplitCollection;
import com.kingdee.eas.fdc.aimcost.TargetCostSplitFactory;
import com.kingdee.eas.fdc.aimcost.TargetCostSplitInfo;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostCollection;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MeasureIndexCollection;
import com.kingdee.eas.fdc.basedata.MeasureIndexFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class AimMeasureCostEditUICTEx extends AbstractAimMeasureCostEditUI {
	
	private java.util.List tables;
    private CurProjectInfo project;
    private Map measureCostMap;
    private TreeModel costAcctTree;
    public Map apportionMap;
    private PlanIndexTable planIndexTable;
    private MeasureCollectTable measureCollectTable;
    private SaleUnilaterallyTable saleUnilaterallyTable = null;
    private boolean isFirstLoad;
    CoreUI MeasureIncomeEditUI;
    java.util.List lockIds;
    java.util.List lockId2s;
    boolean hasMutex;
    private Set nameSet;
    private Object COLLECTITEMS[];
    private Object MAINITEMS[];
    private boolean hasChanged;
    private TemplateMeasureCostPromptBox selector;
    private HashMap params;
    public Object SIXITEMS[];
    private Set accreditSet;
    
    //公共配套分摊
	protected KDWorkButton btnSplit;
	protected ActionSplit actionSplit = null;
    
    


    public AimMeasureCostEditUICTEx()
        throws Exception
    {
        tables = null;
        project = null;
        measureCostMap = new HashMap();
        costAcctTree = null;
        apportionMap = new HashMap();
        planIndexTable = null;
        measureCollectTable = null;
        isFirstLoad = true;
        MeasureIncomeEditUI = null;
        lockIds = new ArrayList();
        lockId2s = new ArrayList();
        hasMutex = true;
        nameSet = null;
        COLLECTITEMS = null;
        MAINITEMS = null;
        hasChanged = false;
        selector = null;
        params = null;
        SIXITEMS = null;
        accreditSet = null;
    }
    
    public void initUIToolBarLayout() {
		super.initUIToolBarLayout();
		initButton();
		this.toolBar.add(btnSplit);
	}
    
    private void initButton() {
		// actionSplit
		this.actionSplit = new ActionSplit(this);
		getActionManager().registerAction("actionSplit", actionSplit);
		this.actionSplit.setBindWorkFlow(true);
		this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());

		this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
		this.btnSplit.setName("btnSplit");
		// btnSplit
		this.btnSplit.setAction((IItemAction) ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));
		this.btnSplit.setText("公共配套分摊");//resHelper.getString("btnSplit.text"));
		this.btnSplit.setToolTipText("公共配套分摊");//resHelper	.getString("btnSplit.toolTipText"));
		this.btnSplit.setIcon(EASResource.getIcon("imgTbtn_split"));

		getActionManager().registerUIState("FINDVIEW", this.actionSplit, ActionStateConst.DISABLED);
	}
    
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		MeasureCostInfo measureCostInfo = (MeasureCostInfo)this.editData;
		PlanIndexInfo planIndexInfo = getPlanIndexTable().getPlanIndexInfo();
		PlanIndexEntryCollection planIndexEntrys = getEntrys(planIndexInfo);
		uiContext.put("editData", editData);
		uiContext.put("measureCostInfo", measureCostInfo);
		uiContext.put("planIndexInfo", planIndexInfo);
		uiContext.put("planIndexEntrys", planIndexEntrys);
		uiContext.put("measureCostEditUI", this);
		IUIWindow uiWindow = null;
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PublicMatchingSplitEditUI.class.getName(), uiContext, null,
				oprtState);
		uiWindow.show();
		
		if (plTables.getSelectedIndex() == 1) {
			saleUnilaterallyTable.refresh();
		}
	}
    
    public Map getSplitData() {
    	Map splitDataMap = new HashMap();
    	if(editData != null){
        	MeasureCostInfo cost = (MeasureCostInfo)editData;
        	TargetCostSplitCollection coll = cost.getTargetCostSplit();
        	for (int j = 0; j < coll.size(); j++) {
        		String key = null;
    			TargetCostSplitInfo target = coll.get(j);
    			ProductTypeInfo productInfo = target.getProductType();
    			if(productInfo instanceof ProductTypeInfo){
    				key = productInfo.getId().toString();
    			}else{
    				try {
    					if(target != null && target.getId() != null){
    						target = TargetCostSplitFactory.getRemoteInstance().getTargetCostSplitInfo(new ObjectUuidPK(target.getId().toString()));
    					}
    				} catch (EASBizException e) {
    					e.printStackTrace();
    				} catch (BOSException e) {
    					e.printStackTrace();
    				}
    				coll.add(target);
    				if(target != null && target.getId() != null){
    					key = target.getProductType().getId().toString();
    				}
    				
    			}
	    		if(splitDataMap.containsKey(key)){
	    			BigDecimal amount = FDCHelper.add(target.getAmount(), splitDataMap.get(key));
	    			splitDataMap.put(key, amount);
	    		}else{
	    			splitDataMap.put(key, target.getAmount());
	    		}
        	}
        }
    	return splitDataMap;
    }
    
    /**
     * 保存可售单方汇总表
     */
    private void submitSaleAreaData() {
    	MeasureCostInfo measureCostInfo = (MeasureCostInfo)this.editData;
    	measureCostInfo.getSaleAreaData().clear();
    	saleUnilaterallyTable.refresh();
    	KDTable table = saleUnilaterallyTable.getTable();
    	
    	PlanIndexInfo planIndexInfo = getPlanIndexTable().getPlanIndexInfo();
        PlanIndexEntryCollection entrys = new PlanIndexEntryCollection();
        for(int i = 0; i < planIndexInfo.getEntrys().size(); i++)
        {
            PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
            if(entry.isIsProduct()){
    			if(entry.isIsSplit()){
    				entrys.add(entry);
    			}
            }
        }

        IRow headRow = table.getHeadRow(1);
    	
    	for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			Object obj = row.getUserObject();
			if(!(obj instanceof CostAccountInfo))
	            continue;
	        CostAccountInfo costAcct = (CostAccountInfo)obj;
	        if(!costAcct.isIsLeaf()){
	        	continue;
	        }
	        //需要加上科目编码、名称、平均列
			for (int j = entrys.size()+2+1; j < table.getColumnCount()-2; j++) {
				SaleAreaDataInfo info = new SaleAreaDataInfo();
				String productID = headRow.getCell(j).getUserObject().toString();
				info.setCostAccount(costAcct);
				info.setProductID(productID);
				info.setAmount((BigDecimal)row.getCell(j).getValue());
				measureCostInfo.getSaleAreaData().add(info);
			}
		}
	}
    
    /**
     * 保存建筑单方汇总表
     */
    private void submitMeasureCollData() {
    	MeasureCostInfo measureCostInfo = (MeasureCostInfo)this.editData;
    	measureCostInfo.getMeasureCollectData().clear();
    	measureCollectTable.refresh();
    	KDTable table = measureCollectTable.getTable();
    	
    	PlanIndexInfo planIndexInfo = getPlanIndexTable().getPlanIndexInfo();
        PlanIndexEntryCollection entrys = new PlanIndexEntryCollection();
        for(int i = 0; i < planIndexInfo.getEntrys().size(); i++)
        {
            PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
            if(entry.isIsProduct()){
//    			if(entry.isIsSplit()){
    				entrys.add(entry);
//    			}
            }
        }

        IRow headRow = table.getHeadRow(1);
    	
    	for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			Object obj = row.getUserObject();
			if(!(obj instanceof CostAccountInfo))
	            continue;
	        CostAccountInfo costAcct = (CostAccountInfo)obj;
	        if(!costAcct.isIsLeaf()){
	        	continue;
	        }
	        //需要加上科目编码、名称、平均列
			for (int j = entrys.size()+2+1; j < table.getColumnCount()-2; j++) {
				MeasureCollectDataInfo info = new MeasureCollectDataInfo();
				String productID = headRow.getCell(j).getUserObject().toString();
				info.setCostAccount(costAcct);
				info.setProductID(productID);
				info.setAmount((BigDecimal)row.getCell(j).getValue());
				measureCostInfo.getMeasureCollectData().add(info);
			}
		}
	}
    
    private PlanIndexEntryCollection getEntrys(PlanIndexInfo planIndexInfo) {
		PlanIndexEntryCollection entrys = new PlanIndexEntryCollection();
		for (int i = 0; i < planIndexInfo.getEntrys().size(); i++) {
			PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
			if (entry.isIsProduct()) {
				entrys.add(entry);
			}
		}
		return entrys;
	}

    public boolean destroyWindow()
    {
        boolean destroyWindow = super.destroyWindow();
        if(destroyWindow)
        {
            if("RELEASEALL".equals(getOprtState()) && hasMutex)
                try
                {
                    FDCClientUtils.releaseDataObjectLock(this, lockIds);
                    if(lockId2s.size() > 0)
                        FDCClientUtils.releaseDataObjectLock(MeasureIncomeEditUI, lockId2s);
                }
                catch(Throwable e1)
                {
                    handUIException(e1);
                }
            return true;
        } else
        {
            return destroyWindow;
        }
    }

    public void storeFields()
    {
        super.storeFields();
        MeasureCostInfo cost = (MeasureCostInfo)editData;
        Object objStage = comboMeasureStage.getSelectedItem();
        if(objStage instanceof MeasureStageInfo)
            cost.setMeasureStage((MeasureStageInfo)objStage);
        else
            cost.setMeasureStage(null);
        cost.setVersionName(txtVersionName.getText());
        cost.setVersionNumber(txtVersionNumber.getText());
        Object objPrj = prmtProject.getValue();
        if(cost.getProject() == null && (objPrj instanceof CurProjectInfo))
            cost.setProject((CurProjectInfo)objPrj);
        Object objPrjType = prmtProjectType.getValue();
        if(objPrjType instanceof ProjectTypeInfo)
            cost.setProjectType((ProjectTypeInfo)objPrjType);
        else
            cost.setProjectType(null);
        try
        {
            handleAimCostAccredit(cost);
        }
        catch(BOSException e)
        {
            handUIException(e);
        }
        Map splitTypeMap = measureCollectTable.getSplitTypes();
        for(int i = 3; i < tables.size(); i++)
        {
            KDTable table = (KDTable)tables.get(i);
            ProductTypeInfo product = (ProductTypeInfo)table.getHeadRow(0).getUserObject();
            for(int j = 0; j < table.getRowCount(); j++)
            {
                IRow row = table.getRow(j);
                if(!(row.getUserObject() instanceof MeasureEntryInfo))
                    continue;
                if(isDetailAcctRow(row) && !isDetailAcctHasInput(row))
                {
                    boolean isEmpty = true;
                    int k = 3;
                    do
                    {
                        if(k >= table.getColumnCount())
                            break;
                        if(!FDCHelper.isEmpty(row.getCell(k).getValue()))
                        {
                            isEmpty = false;
                            break;
                        }
                        k++;
                    } while(true);
                    if(isEmpty)
                        continue;
                }
                MeasureEntryInfo entry = (MeasureEntryInfo)row.getUserObject();
                entry.setEntryName((String)row.getCell("acctName").getValue());
                entry.setProduct(product);
                Object obj = row.getCell("indexName").getValue();
                if(obj instanceof Item)
                {
                    Item item = (Item)obj;
                    entry.setSimpleName(item.key);
                    entry.setIndexName(item.toString());
                    entry.setIndexValue(FDCHelper.toBigDecimal(row.getCell("index").getValue()));
                }
                entry.setCoefficientName((String)row.getCell("coefficientName").getValue());
                entry.setCoefficient((BigDecimal)row.getCell("coefficient").getValue());
                Object value = row.getCell("unit").getValue();
                if(value instanceof IObjectValue)
                    entry.setUnit((MeasureUnitInfo)value);
                else
                if(value != null)
                    entry.setName(value.toString());
                entry.setWorkload((BigDecimal)row.getCell("workload").getValue());
                entry.setPrice((BigDecimal)row.getCell("price").getValue());
                entry.setCostAmount((BigDecimal)row.getCell("sumPrice").getValue());
                entry.setProgram((String)row.getCell("program").getValue());
                entry.setDesc((String)row.getCell("desc").getValue());
                entry.setChangeReason((String)row.getCell("changeReason").getValue());
                entry.setDescription((String)row.getCell("description").getValue());
                if(entry.getCostAccount().getType() == CostAccountTypeEnum.SIX)
                {
                    Object splitType = splitTypeMap.get(entry.getCostAccount().getId().toString());
                    if(splitType != null)
                        entry.setNumber(splitType.toString());
                }
                entry.setAdjustCoefficient((BigDecimal)row.getCell("adjustCoefficient").getValue());
                entry.setAdjustAmt((BigDecimal)row.getCell("adjustAmt").getValue());
                entry.setAmount((BigDecimal)row.getCell("amount").getValue());
                cost.getCostEntry().add(entry);
            }

        }

        if(cost != null && cost.getId() != null)
            cost.setIsLastVersion(isLastVersion(cost.getId().toString()));
    }

    protected void initWorkButton()
    {
        super.initWorkButton();
        actionFirst.setVisible(false);
        actionPre.setVisible(false);
        actionNext.setVisible(false);
        actionLast.setVisible(false);
        actionCancel.setVisible(false);
        actionCancelCancel.setVisible(false);
        actionRemove.setVisible(false);
        actionAddRow.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
        actionDeleteRow.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
        btnImportApportion.setIcon(EASResource.getIcon("imgTbtn_input"));
        actionImportTemplate.putValue("SmallIcon", EASResource.getIcon("imgTbtn_input"));
        menuEdit.setEnabled(true);
        menuEdit.setVisible(true);
        actionExportAllToExcel.putValue("SmallIcon", EASResource.getIcon("imgTbtn_output"));
        actionExportAllToExcel.setVisible(true);
        actionExportAllToExcel.setEnabled(true);
        menuItemExportAll.setMnemonic('E');
        menuItemExportAll.setText(menuItemExportAll.getText() + "(E)");
        menuItemExportAll.setAccelerator(KeyStroke.getKeyStroke("ctrl shift E"));
        menuItemImportTemplate.setMnemonic('T');
        menuItemImportTemplate.setText(menuItemImportTemplate.getText() + "(T)");
        menuItemImportTemplate.setAccelerator(KeyStroke.getKeyStroke("ctrl shift T"));
        menuItemAddRow.setMnemonic('A');
        menuItemAddRow.setText(menuItemAddRow.getText() + "(A)");
        menuItemAddRow.setAccelerator(KeyStroke.getKeyStroke("alt A"));
        menuItemDeleteRow.setMnemonic('D');
        menuItemDeleteRow.setText(menuItemDeleteRow.getText() + "(D)");
        menuItemDeleteRow.setAccelerator(KeyStroke.getKeyStroke("alt D"));
        chkMenuItemSubmitAndAddNew.setEnabled(false);
        chkMenuItemSubmitAndAddNew.setVisible(false);
        btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
        btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
        menuItemSave.setIcon(EASResource.getIcon("imgTbtn_save"));
        menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
        menuItemSave.setVisible(true);
        prmtProject.setEnabled(false);
    }

    protected IObjectValue createNewData()
    {
        MeasureCostInfo cost = new MeasureCostInfo();
        String orgId = (String)getUIContext().get("orgId");
        String prjId = (String)getUIContext().get("projectId");
        Boolean isAimMeasure = (Boolean)getUIContext().get("isAimMeasure");
        cost.setIsAimMeasure(isAimMeasure.booleanValue());
        if(getUIContext().get("MeasureEditData") instanceof MeasureCostInfo)
        {
            MeasureCostInfo editData1 = (MeasureCostInfo)getUIContext().get("MeasureEditData");
            cost.putAll(editData1);
            getUIContext().remove("MeasureEditData");
            return cost;
        }
        MeasureStageInfo lastStageInfo = null;
        try
        {
            SelectorItemCollection selector = new SelectorItemCollection();
            selector.add("name");
            selector.add("number");
            selector.add("longNumber");
            selector.add("projectType.*");
            selector.add("curProjProductEntries.isAccObj");
            if(prjId != null)
            {
                project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(prjId)), selector);
                cost.setProject(project);
                cost.setProjectType(project.getProjectType());
                lastStageInfo = AimCostClientHelper.getLastMeasureStage(project, isAimMeasure.booleanValue());
            }
            MeasureCostVersionHandler version = new MeasureCostVersionHandler(orgId, prjId, isAimMeasure.booleanValue(), lastStageInfo);
            cost.setVersionNumber(MeasureCostVersionHandler.getNextVersion(version.getLastVersion()));
        }
        catch(BOSException e)
        {
            handUIException(e);
        }
        catch(SQLException e)
        {
            handUIException(e);
        }
        catch(EASBizException e)
        {
            handUIException(e);
        }
        FullOrgUnitInfo org = new FullOrgUnitInfo();
        org.setId(BOSUuid.read(orgId));
        cost.setOrgUnit(org);
        if(lastStageInfo == null)
            lastStageInfo = (MeasureStageInfo)comboMeasureStage.getItemAt(comboMeasureStage.getItemCount() - 1);
        cost.setMeasureStage(lastStageInfo);
        return cost;
    }

    protected ICoreBase getBizInterface()
        throws Exception
    {
        return MeasureCostFactory.getRemoteInstance();
    }

    private void addPanel()
        throws Exception
    {
        if(isFirstLoad)
            isFirstLoad = false;
        else
            return;
        ChangeListener changeListeners[] = plTables.getChangeListeners();
        for(int i = 0; i < changeListeners.length; i++)
            plTables.removeChangeListener(changeListeners[i]);

        plTables.removeAll();
        Object obj = getUIContext().get("ID");
        if(editData.getId() != null)
            obj = editData.getId();
        tables = new ArrayList();
        KDTable table = null;
        
        measureCollectTable = new MeasureCollectTable(this);
        table = measureCollectTable.getTable();
        tables.add(table);
        FDCTableHelper.setColumnMoveable(table, true);
        FDCTableHelper.addTableMenu(table);
        plTables.add(table, "建筑单方汇总表");
        
        saleUnilaterallyTable = new SaleUnilaterallyTable(this);
	    table = saleUnilaterallyTable.getTable();
		this.tables.add(table);
	    FDCTableHelper.setColumnMoveable(table, true);
	    FDCTableHelper.addTableMenu(table);
	    this.plTables.add(table, "可售单方汇总表");
        
        planIndexTable = new PlanIndexTable(getInitPlanIndexInfo(), this);
        table = planIndexTable.getTable();
        tables.add(table);
        FDCTableHelper.setColumnMoveable(table, true);
        FDCTableHelper.addTableMenu(table);
        plTables.add(planIndexTable.getContentPanel(), "规划指标表");
        
        table = planIndexTable.getConstructTable();
        tables.add(table);
        plTables.add(table, "建造标准");
        
        PlanIndexInfo info = planIndexTable.getPlanIndexInfo();
        table = new KDTable();
        table.setName("六类公摊及期间费");
        
        tables.add(table);
        FDCTableHelper.addTableMenu(table);
        FDCTableHelper.setColumnMoveable(table, true);
        initTable(table, CostAccountTypeEnum.SIX, null);
        PlanIndexTable _tmp = planIndexTable;
        BigDecimal amount = PlanIndexTable.getAllSellArea(info);
        info.put("allSellArea", amount);
        PlanIndexTable _tmp1 = planIndexTable;
        amount = PlanIndexTable.getAllBuildArea(info);
        info.put("allBuildArea", amount);
        table.getHeadRow(0).getCell(0).setUserObject(info);
        fillTable(table);
        setUnionData(table);
        plTables.add(table, "六类公摊及期间费");
        
        for(int i = 0; i < info.getEntrys().size(); i++)
        {
            PlanIndexEntryInfo entry = info.getEntrys().get(i);
            if(entry.getProduct() != null)
                table = addProductTypeTable(entry.getProduct());
        }

        measureCollectTable.refresh();
        for(int i = 0; i < changeListeners.length; i++)
            plTables.addChangeListener(changeListeners[i]);

    }

    public KDTable addProductTypeTable(ProductTypeInfo product)
    {
        boolean isadd = true;
        int i = 3;
        do
        {
            if(i >= tables.size())
                break;
            KDTable table = (KDTable)tables.get(i);
            if((table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo) && ((ProductTypeInfo)table.getHeadRow(0).getUserObject()).getId().equals(product.getId()))
            {
                isadd = false;
                break;
            }
            i++;
        } while(true);
        if(!isadd)
            return null;
        KDTable table = new KDTable();
        tables.add(table);
        FDCTableHelper.setColumnMoveable(table, true);
        initTable(table, CostAccountTypeEnum.MAIN, product.getId().toString());
        table.getHeadRow(0).getCell(0).setUserObject(planIndexTable.getPlanIndexEntryInfo(product.getId().toString()));
        table.getHeadRow(0).setUserObject(product);
        try
        {
            fillTable(table);
        }
        catch(Exception e)
        {
            handUIException(e);
        }
        table.setName(product.getName());
        plTables.add(table, product.getName());
        KDTable constrTable = planIndexTable.getConstructTable();
        IRow headRow = constrTable.getHeadRow(0);
        boolean isHasAdd = true;
        i = 4;
        do
        {
            if(i >= constrTable.getColumnCount())
                break;
            ProductTypeInfo type = (ProductTypeInfo)headRow.getCell(i).getUserObject();
            if(type.getId().toString().equals(product.getId().toString()))
            {
                isHasAdd = false;
                break;
            }
            i++;
        } while(true);
        if(isHasAdd)
        {
            IColumn column = constrTable.addColumn();
            column.setWidth(150);
            column.setKey(product.getId().toString());
            headRow.getCell(product.getId().toString()).setUserObject(product);
            headRow.getCell(product.getId().toString()).setValue(product.getName());
        }
        setUnionData(table);
        FDCTableHelper.addTableMenu(table);
        return table;
    }

    public void addConstructIndexTable()
    {
        KDTable table = planIndexTable.getConstructTable();
        tables.add(table);
    }

    public void deleteProductTypeTable(ProductTypeInfo product)
    {
        int i = 3;
        do
        {
            if(i >= tables.size())
                break;
            KDTable table = (KDTable)tables.get(i);
            if((table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo) && ((ProductTypeInfo)table.getHeadRow(0).getUserObject()).getId().equals(product.getId()))
            {
                tables.remove(i);
                plTables.remove(table);
                disableTableMenus(table);
                break;
            }
            i++;
        } while(true);
    }

    public void addTableChangeEnvent(final KDTable table)
    {
        table.addKDTEditListener(new KDTEditAdapter() {

            public void editStopped(KDTEditEvent e)
            {
                try
                {
                    table_editStopped(table, e);
                }
                catch(Exception exc)
                {
                    handUIException(exc);
                }
            }
        });
    }

    public void initTable(KDTable table, CostAccountTypeEnum type, String productId)
    {
        table.getViewManager().setFreezeView(-1, 3);
        table.getSelectManager().setSelectMode(1);
        table.setActiveCellStatus(1);
        IColumn column = table.addColumn();
        column.setKey("id");
        column.getStyleAttributes().setHided(true);
        column = table.addColumn();
        column.setKey("acctNumber");
        column = table.addColumn();
        column.setKey("acctName");
        column = table.addColumn();
        column.setKey("indexName");
        column = table.addColumn();
        column.setKey("index");
        column = table.addColumn();
        column.setKey("coefficientName");
        column = table.addColumn();
        column.setKey("coefficient");
        column = table.addColumn();
        column.setKey("workload");
        column = table.addColumn();
        column.setKey("unit");
        column = table.addColumn();
        column.setKey("price");
        column = table.addColumn();
        column.setKey("sumPrice");
        column = table.addColumn();
        column.setKey("adjustCoefficient");
        column = table.addColumn();
        column.setKey("adjustAmt");
        column = table.addColumn();
        column.setKey("amount");
        column = table.addColumn();
        column.setKey("buildPart");
        column = table.addColumn();
        column.setKey("sellPart");
        column = table.addColumn();
        column.setKey("program");
        column = table.addColumn();
        column.setKey("desc");
        column = table.addColumn();
        column.setKey("changeReason");
        column = table.addColumn();
        column.setKey("description");
        IRow row = table.addHeadRow();
        row.getCell("acctNumber").setValue("科目编码");
        row.getCell("acctName").setValue("科目名称");
        row.getCell("indexName").setValue("原始指标名称");
        row.getCell("index").setValue("原始指标值");
        row.getCell("coefficientName").setValue("系数名称");
        row.getCell("coefficient").setValue("系数值");
        row.getCell("workload").setValue("工作量");
        row.getCell("unit").setValue("单位");
        row.getCell("price").setValue("单价");
        if (isUseAdjustCoefficient())
          row.getCell("sumPrice").setValue("调整前合价");
        else
          row.getCell("sumPrice").setValue("合价");

        row.getCell("adjustCoefficient").setValue("调整系数");
        row.getCell("adjustAmt").setValue("调整金额");
        row.getCell("amount").setValue("合价");
        row.getCell("buildPart").setValue("建筑单方");
        row.getCell("sellPart").setValue("可售单方");
        row.getCell("program").setValue("合约规划");
        row.getCell("desc").setValue("备注");
        row.getCell("changeReason").setValue("变化原因");
        if(isUseQuality())
            row.getCell("description").setValue("品质特征");
        else
            table.getColumn("description").getStyleAttributes().setHided(true);
        if(!isUseAdjustCoefficient())
        {
            table.getColumn("adjustCoefficient").getStyleAttributes().setHided(true);
            table.getColumn("adjustAmt").getStyleAttributes().setHided(true);
            table.getColumn("amount").getStyleAttributes().setHided(true);
        }
        FDCHelper.formatTableNumber(table, new String[] {
            "index", "workload", "sumPrice", "amount", "adjustAmt", "buildPart", "sellPart"
        });
        FDCHelper.formatTableNumber(table, "adjustCoefficient", "#,##0.000000;-#,##0.000000");
        FDCHelper.formatTableNumber(table, "coefficient", "#,##0.0000;-#,##0.0000");
        FDCHelper.formatTableNumber(table, "price", "#,##0.0000;-#,##0.0000");
        KDTextField textField = new KDTextField();
        textField.setMaxLength(80);
        ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
        table.getColumn("acctName").setEditor(txtEditor);
        KDFormattedTextField formattedTextField = new KDFormattedTextField(1);
        formattedTextField.setPrecision(2);
        formattedTextField.setSupportedEmpty(true);
        formattedTextField.setNegatived(false);
        ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
        table.getColumn("index").setEditor(numberEditor);
        textField = new KDTextField();
        textField.setMaxLength(80);
        txtEditor = new KDTDefaultCellEditor(textField);
        table.getColumn("coefficientName").setEditor(txtEditor);
        formattedTextField = new KDFormattedTextField(1);
        formattedTextField.setPrecision(4);
        formattedTextField.setMaximumValue(FDCNumberHelper.TEN_THOUSAND);
        formattedTextField.setMinimumValue(FDCNumberHelper._TEN_THOUSAND);
        formattedTextField.setSupportedEmpty(true);
        formattedTextField.setNegatived(true);
        numberEditor = new KDTDefaultCellEditor(formattedTextField);
        table.getColumn("coefficient").setEditor(numberEditor);
        table.getColumn("price").setEditor(numberEditor);
        formattedTextField = new KDFormattedTextField(1);
        formattedTextField.setPrecision(2);
        formattedTextField.setSupportedEmpty(true);
        formattedTextField.setNegatived(false);
        numberEditor = new KDTDefaultCellEditor(formattedTextField);
        table.getColumn("workload").setEditor(numberEditor);
        textField = new KDTextField();
        textField.setMaxLength(80);
        KDBizPromptBox f7Unit = new KDBizPromptBox() {

        };
        f7Unit.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
        f7Unit.setEditable(true);
        f7Unit.setDisplayFormat("$name$");
        f7Unit.setEditFormat("$number$");
        f7Unit.setCommitFormat("$number$");
        ICellEditor f7Editor = new KDTDefaultCellEditor(f7Unit);
        table.getColumn("unit").setEditor(f7Editor);
        formattedTextField = new KDFormattedTextField(1);
        formattedTextField.setPrecision(2);
        formattedTextField.setSupportedEmpty(true);
        formattedTextField.setNegatived(false);
        numberEditor = new KDTDefaultCellEditor(formattedTextField);
        table.getColumn("sumPrice").setEditor(numberEditor);
        table.getColumn("amount").setEditor(numberEditor);
        table.getColumn("adjustAmt").setEditor(numberEditor);
        formattedTextField = new KDFormattedTextField(1);
        formattedTextField.setPrecision(6);
        formattedTextField.setMaximumValue(FDCNumberHelper.TEN_THOUSAND);
        formattedTextField.setMinimumValue(FDCNumberHelper._TEN_THOUSAND);
        formattedTextField.setSupportedEmpty(true);
        formattedTextField.setNegatived(true);
        numberEditor = new KDTDefaultCellEditor(formattedTextField);
        table.getColumn("adjustCoefficient").setEditor(numberEditor);
        textField = new KDTextField();
        textField.setMaxLength(80);
        txtEditor = new KDTDefaultCellEditor(textField);
        table.getColumn("description").setEditor(txtEditor);
        table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
        Color lockColor = FDCTableHelper.cantEditColor;
        table.getColumn("acctNumber").getStyleAttributes().setBackground(lockColor);
        table.getColumn("index").getStyleAttributes().setLocked(true);
        table.getColumn("index").getStyleAttributes().setBackground(lockColor);
        table.getColumn("buildPart").getStyleAttributes().setLocked(true);
        table.getColumn("buildPart").getStyleAttributes().setBackground(lockColor);
        table.getColumn("sellPart").getStyleAttributes().setLocked(true);
        table.getColumn("sellPart").getStyleAttributes().setBackground(lockColor);
        table.getColumn("amount").getStyleAttributes().setLocked(true);
        table.getColumn("amount").getStyleAttributes().setBackground(lockColor);
        ICellEditor editor = getIndexEditor(type, productId);
        table.getColumn("indexName").setEditor(editor);
        setTemplateMeasureCostF7Editor(table);
        addTableChangeEnvent(table);
    }

    public void fillTable(KDTable table)
        throws Exception
    {
        table.removeRows();
        table.setUserObject(null);
        DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)costAcctTree.getRoot();
        Enumeration childrens = root.depthFirstEnumeration();
        int maxLevel = 0;
        do
        {
            if(!childrens.hasMoreElements())
                break;
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)childrens.nextElement();
            if(node.getUserObject() != null && node.getLevel() > maxLevel)
                maxLevel = node.getLevel();
        } while(true);
        table.getTreeColumn().setDepth(maxLevel + 1);
        for(int i = 0; i < root.getChildCount(); i++)
        {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)root.getChildAt(i);
            fillNode(table, child);
        }

    }

    private void fillNode(KDTable table, DefaultMutableTreeNode node)
        throws BOSException, SQLException, EASBizException
    {
        CostAccountInfo costAcct = (CostAccountInfo)node.getUserObject();
        if(costAcct == null)
        {
            MsgBox.showError("成本科目的级别太多!");
            return;
        }
        if(costAcct.getType() != null)
            if(table.getHeadRow(0).getUserObject() != null)
            {
                if(costAcct.getType().equals(CostAccountTypeEnum.SIX))
                    return;
            } else
            if(costAcct.getType().equals(CostAccountTypeEnum.MAIN))
                return;
        ProductTypeInfo product = (ProductTypeInfo)table.getHeadRow(0).getUserObject();
        IRow row = table.addRow();
        row.setTreeLevel(node.getLevel() - 1);
        String longNumber = costAcct.getLongNumber();
        longNumber = longNumber.replace('!', '.');
        row.getCell("acctNumber").setValue(longNumber);
        row.getCell("acctName").setValue(costAcct.getName());
        row.setUserObject(costAcct);
        if(node.isLeaf() && node.getLevel() > 1)
        {
            String key = costAcct.getId().toString();
            if(product != null)
                key = key + product.getId().toString();
            MeasureEntryCollection coll = (MeasureEntryCollection)measureCostMap.get(key);
            if(coll != null && coll.size() > 0)
            {
                if(coll.size() == 1)
                {
                    MeasureEntryInfo info = coll.get(0);
                    IRow entryRow = row;
                    entryRow.setUserObject(info);
                    loadRow(table, entryRow, product);
                    setDetailAcctRow(entryRow);
                    row.getCell("acctName").setValue(costAcct.getName());
                } else
                {
                    row.getStyleAttributes().setLocked(true);
                    row.getStyleAttributes().setBackground(new Color(15789529));
                    row.setUserObject(costAcct);
                    for(int i = 0; i < coll.size(); i++)
                    {
                        MeasureEntryInfo info = coll.get(i);
                        IRow entryRow = table.addRow();
                        entryRow.setTreeLevel(node.getLevel());
                        entryRow.setUserObject(info);
                        loadRow(table, entryRow, product);
                    }

                }
            } else
            {
                MeasureEntryInfo info = new MeasureEntryInfo();
                info.setCostAccount(costAcct);
                row.setUserObject(info);
                setTemplateMeasureCostF7Editor(table, row);
                setDetailAcctRow(row);
            }
        } else
        {
            for(int i = 0; i < node.getChildCount(); i++)
                fillNode(table, (DefaultMutableTreeNode)node.getChildAt(i));

            row.getStyleAttributes().setLocked(true);
            row.getStyleAttributes().setBackground(new Color(15789529));
        }
    }

    protected IObjectValue getValue(IObjectPK pk)
        throws Exception
    {
        return super.getValue(pk);
    }

    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sels = super.getSelectors();
        sels.add("*");
        sels.add("project.name");
        sels.add("project.longNumber");
        sels.add("project.number");
        sels.add("project.curProjProductEntries.isAccObj");
        sels.add("project.curProjProductEntries.*");
        sels.add("projectType.*");
        sels.add("orgUnit.id");
        sels.add("costEntry.*");
        sels.add("costEntry.costAccount.*");
        sels.add("costEntry.apportionType.*");
        sels.add("costEntry.unit.id");
        sels.add("costEntry.unit.name");
        sels.add("costEntry.unit.number");
        sels.add("measureStage.id");
        sels.add("measureStage.name");
        sels.add("measureStage.number");
        sels.add("constrEntrys.*");
        sels.add("constrEntrys.productType.*");
        return sels;
    }

    public void loadRow(KDTable table, IRow row, ProductTypeInfo product)
    {
        MeasureEntryInfo info = (MeasureEntryInfo)row.getUserObject();
        row.getCell("acctName").setValue(info.getEntryName());
        row.getCell("index").setValue(info.getIndexValue());
        if(info.getSimpleName() != null && table.getColumn("indexName").getEditor() != null)
        {
            KDComboBox box = (KDComboBox)table.getColumn("indexName").getEditor().getComponent();
            if(box != null)
            {
                int i = 0;
                do
                {
                    if(i >= box.getItemCount())
                        break;
                    if(((Item)box.getItemAt(i)).key.equals(info.getSimpleName()))
                    {
                        row.getCell("indexName").setValue(box.getItemAt(i));
                        break;
                    }
                    i++;
                } while(true);
            }
        }
        row.getCell("coefficientName").setValue(info.getCoefficientName());
        BigDecimal coefficient = info.getCoefficient();
        if(coefficient != null && coefficient.compareTo(FDCHelper.ZERO) == 0)
            coefficient = null;
        row.getCell("coefficient").setValue(FDCHelper.toBigDecimal(coefficient, 4));
        BigDecimal workload = info.getWorkload();
        if(workload != null && workload.compareTo(FDCHelper.ZERO) == 0)
            workload = null;
        if(info.getSimpleName() != null && coefficient != null)
            row.getCell("workload").getStyleAttributes().setLocked(true);
        row.getCell("workload").setValue(workload);
        if(info.getUnit() == null)
            row.getCell("unit").setValue(info.getName());
        else
            row.getCell("unit").setValue(info.getUnit());
        BigDecimal price = info.getPrice();
        if(price != null && price.compareTo(FDCHelper.ZERO) == 0)
            price = null;
        row.getCell("price").setValue(FDCHelper.toBigDecimal(price, 4));
        row.getCell("sumPrice").setValue(info.getCostAmount());
        if(workload != null && price != null)
            row.getCell("sumPrice").getStyleAttributes().setLocked(true);
        Object obj = table.getHeadRow(0).getCell(0).getUserObject();
        if(obj instanceof PlanIndexEntryInfo)
        {
            BigDecimal sellArea = ((PlanIndexEntryInfo)obj).getSellArea();
            if(sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0)
            {
                BigDecimal sellPart = FDCNumberHelper.divide(info.getAmount(), sellArea);
                row.getCell("sellPart").setValue(sellPart);
            } else
            {
                row.getCell("sellPart").setValue(null);
            }
            BigDecimal buildArea = ((PlanIndexEntryInfo)obj).getBuildArea();
            if(buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0)
            {
                BigDecimal buildPart = FDCNumberHelper.divide(info.getAmount(), buildArea);
                row.getCell("buildPart").setValue(buildPart);
            } else
            {
                row.getCell("buildPart").setValue(null);
            }
        } else
        if(obj instanceof PlanIndexInfo)
        {
            BigDecimal sellArea = ((PlanIndexInfo)obj).getBigDecimal("allSellArea");
            if(sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0)
            {
                BigDecimal sellPart = FDCNumberHelper.divide(info.getAmount(), sellArea);
                row.getCell("sellPart").setValue(sellPart);
            } else
            {
                row.getCell("sellPart").setValue(null);
            }
            BigDecimal buildArea = ((PlanIndexInfo)obj).getBigDecimal("allBuildArea");
            if(buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0)
            {
                BigDecimal buildPart = FDCNumberHelper.divide(info.getAmount(), buildArea);
                row.getCell("buildPart").setValue(buildPart);
            } else
            {
                row.getCell("buildPart").setValue(null);
            }
        }
        row.getCell("amount").setValue(info.getAmount());
        row.getCell("adjustCoefficient").setValue(info.getAdjustCoefficient());
        row.getCell("adjustAmt").setValue(info.getAdjustAmt());
        row.getCell("program").setValue(info.getProgram());
        row.getCell("desc").setValue(info.getDesc());
        row.getCell("changeReason").setValue(info.getChangeReason());
        row.getCell("description").setValue(info.getDescription());
        setTemplateMeasureCostF7Editor(table, row);
    }

    public void onLoad()
        throws Exception
    {
        if(getOprtState().equals("ADDNEW1"))
            setOprtState("ADDNEW");
        getMenuManager(null);
        txtVersionNumber.setEnabled(false);
        actionImportApportion.setVisible(false);
        menuSubmitOption.setVisible(false);
        actionPrint.setEnabled(true);
        actionPrintPreview.setEnabled(true);
        boolean isEdit = false;
        if("ADDNEW".equals(getOprtState()) || "EDIT".equals(getOprtState()))
            isEdit = true;
        else
            isEdit = false;
        FDCClientHelper.initComboMeasureStage(comboMeasureStage, isEdit);
        initMeasureIndex();
        super.onLoad();
        txtVersionName.setMaxLength(80);
        initCtrlListener();
        Boolean isEditVersion = (Boolean)getUIContext().get("isEditVersion");
        if(isEditVersion != null && isEditVersion.booleanValue())
            comboMeasureStage.setEnabled(false);
        Boolean isAimMeasure = (Boolean)getUIContext().get("isAimMeasure");
        if(isAimMeasure == null)
        {
            isAimMeasure = Boolean.TRUE;
            getUIContext().put("isAimMeasure", Boolean.TRUE);
        }
        if(!isAimMeasure.booleanValue())
            setUITitle("可研成本测算");
        else
            setUITitle("目标成本测算");
        ((MeasureCostInfo)editData).setIsAimMeasure(isAimMeasure.booleanValue());
        if(getOprtState().equals(OprtState.ADDNEW))
            actionImportTemplate.setEnabled(true);
        actionAddNew.setEnabled(false);
        actionAddNew.setVisible(false);
        registerMeasureDefaultSplitTypeSetKey();
        actionEdit.setVisible(false);
        actionEdit.setEnabled(false);
        editVersion();
        setShowMessagePolicy(1);
        actionCopy.setVisible(false);
        if(editData != null && ((MeasureCostInfo)editData).getMeasureStage() != null)
            comboMeasureStage.setSelectedItem(((MeasureCostInfo)editData).getMeasureStage());
        storeFields();
        if(!"ADDNEW".equals(getOprtState())){
        	setTargetCostSplit();
        }
    }
    
    //2013.04.17
    //获取数据库中公共配套分摊结果设置到editdata中
    private void setTargetCostSplit() throws BOSException{
    	ITargetCostSplit service = TargetCostSplitFactory.getRemoteInstance();
    	TargetCostSplitCollection coll = service.getTargetCostSplitCollection("select * where FParent = '"+editData.getId()+"'");
    	((MeasureCostInfo)editData).getTargetCostSplit().clear();
    	((MeasureCostInfo)editData).getTargetCostSplit().addCollection(coll);
    }

    private void editVersion()
        throws BOSException, SQLException
    {
        Boolean isEditVersion = (Boolean)getUIContext().get("isEditVersion");
        if(isEditVersion != null && isEditVersion.booleanValue())
        {
            String sourceId = ((MeasureCostInfo)editData).getSourceBillId();
            if(sourceId == null)
                sourceId = editData.getId().toString();
            FDCSQLBuilder builder = new FDCSQLBuilder();
            builder.appendSql("select FVersionNumber from T_AIM_MeasureCost where fsourceBillId=?");
            builder.addParam(sourceId);
            IRowSet rowSet = builder.executeQuery();
            int lastSubVersion = 1;
            String verNo = ((MeasureCostInfo)editData).getVersionNumber();
            verNo = verNo.replaceAll("\\.", "!");
            do
            {
                if(!rowSet.next())
                    break;
                verNo = rowSet.getString("FVersionNumber");
                verNo = verNo.replaceAll("\\.", "!");
                if(verNo != null && verNo.indexOf('!') > 0)
                {
                    int temp = Integer.parseInt(verNo.substring(verNo.indexOf('!') + 1));
                    if(temp >= lastSubVersion)
                        lastSubVersion = temp + 1;
                }
            } while(true);
            if(!FDCHelper.isEmpty(verNo) && verNo.indexOf('!') > 0)
            {
                int index = verNo.lastIndexOf("!");
                verNo = verNo.substring(0, index + 1) + lastSubVersion;
            }
            txtVersionNumber.setText(verNo.replaceAll("!", "\\."));
        }
    }

    private void registerMeasureDefaultSplitTypeSetKey()
    {
        String actionName = "MeasureDefaultSplitTypeSetUI";
        final UIContext uiContext = new UIContext(this);
        getActionMap().put(actionName, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					setCursorOfWair();
					IUIFactory fy = UIFactory.createUIFactory(UIFactoryName.MODEL);
					IUIWindow wnd = fy.create(MeasureDefaultSplitTypeSetUI.class.getName(), uiContext, null, "EDIT",
							WinStyle.SHOW_KINGDEELOGO);
					wnd.show();
				} catch (Exception e1) {
					handUIException(e1);
				} finally {
					setCursorOfDefault();
				}
			}
		});
        getInputMap(2).put(KeyStroke.getKeyStroke("ctrl shift alt F12"), actionName);
    }

    private void initCtrlListener() {
        prmtProjectType.addDataChangeListener(new DataChangeListener() {

            public void dataChanged(DataChangeEvent e) {
                if(e.getNewValue() == null)
                    prmtProject.setData(null);
                if(e.getOldValue() != null ? !e.getOldValue().equals(e.getNewValue()) : e.getNewValue() != null)
                    prmtProject.setData(null);
            }
        });
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("name");
        selector.add("number");
        selector.add("longNumber");
        selector.add("projectType.*");
        prmtProject.setSelectorCollection(selector);
        prmtProject.addDataChangeListener(new DataChangeListener() {

            public void dataChanged(DataChangeEvent e)
            {
                if(prmtProjectType.getData() == null && e.getNewValue() != null)
                {
                    java.util.EventListener listeners[] = prmtProjectType.getListeners(DataChangeListener.class);//prmtProjectType.getListeners(AimMeasureCostEditUI.class$com$kingdee$bos$ctrl$swing$event$DataChangeListener != null ? AimMeasureCostEditUI.class$com$kingdee$bos$ctrl$swing$event$DataChangeListener : (AimMeasureCostEditUI.class$com$kingdee$bos$ctrl$swing$event$DataChangeListener = AimMeasureCostEditUI._mthclass$("com.kingdee.bos.ctrl.swing.event.DataChangeListener")));
                    for(int i = 0; i < listeners.length; i++)
                        prmtProjectType.removeDataChangeListener((DataChangeListener)listeners[i]);

                    prmtProjectType.setData(((CurProjectInfo)e.getNewValue()).getProjectType());
                    for(int i = 0; i < listeners.length; i++)
                        prmtProjectType.addDataChangeListener((DataChangeListener)listeners[i]);

                }
            }
        });
        prmtProject.addSelectorListener(new SelectorListener() {

            public void willShow(SelectorEvent e)
            {
                prmtProject.getQueryAgent().resetRuntimeEntityView();
                String projectTypeid = FDCHelper.getF7Id(prmtProjectType);
                EntityViewInfo view = prmtProject.getEntityViewInfo();
                if(view == null)
                    view = new EntityViewInfo();
                view.setFilter(new FilterInfo());
                if(projectTypeid != null)
                    view.getFilter().appendFilterItem("projectType.id", projectTypeid);
                view.getFilter().appendFilterItem("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
                prmtProject.setEntityViewInfo(view);
            }
        });
        plTables.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e)
            {
                Object obj = plTables.getClientProperty("oldIndex");
                if((obj instanceof Integer) && ((Integer)obj).intValue() == 2)
                    refreshAllMeasureTable();
                plTables.putClientProperty("oldIndex", new Integer(plTables.getSelectedIndex()));
                if(plTables.getSelectedIndex() == 0){
                    measureCollectTable.refresh();
                }
                if (plTables.getSelectedIndex() == 1) {
					saleUnilaterallyTable.refresh();
				}
            }
        });
    }

    public void loadFields()
    {
        System.out.println("sdjfk");
        super.loadFields();
        String orgId = (String)getUIContext().get("orgId");
        MeasureCostInfo cost = (MeasureCostInfo)editData;
        if(getUIContext().get("isAimMeasure") == null)
            getUIContext().put("isAimMeasure", Boolean.valueOf(cost.isIsAimMeasure()));
        if(orgId == null)
        {
            orgId = ((MeasureCostInfo)editData).getOrgUnit().getId().toString();
            getUIContext().put("orgId", orgId);
        }
        if(costAcctTree == null)
            try
            {
                FilterInfo acctFilter = new FilterInfo();
                acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
                if(cost.getProject() == null || cost.getCostEntry().size() > 0 && cost.getCostEntry().get(0).getCostAccount().getCurProject() == null)
                {
                    acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", orgId));
                    accreditSet = AcctAccreditHelper.handAcctAccreditFilter(null, orgId, acctFilter);
                } else
                {
                    String prjId = cost.getProject().getId().toString();
                    acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", prjId));
                    accreditSet = AcctAccreditHelper.handAcctAccreditFilter(null, prjId, acctFilter);
                }
                costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
            }
            catch(Exception e)
            {
                handUIException(e);
            }
        GlUtils.setSelectedItem(comboMeasureStage, cost.getMeasureStage());
        prmtProject.setValue(cost.getProject());
        prmtProjectType.setValue(cost.getProjectType());
        if(cost.getVersionNumber() != null)
        {
            txtVersionNumber.setText(cost.getVersionNumber().replaceAll("!", "\\."));
        } else
        {
            MsgBox.showWarning(this, "版本号不能为空");
            SysUtil.abort();
        }
        txtVersionName.setText(cost.getVersionName());
        measureCostMap.clear();
        MeasureEntryCollection costEntrys = cost.getCostEntry();
        for(int i = 0; i < costEntrys.size(); i++)
        {
            MeasureEntryInfo info = costEntrys.get(i);
            CostAccountInfo costAccount = info.getCostAccount();
            String key = costAccount.getId().toString();
            if(info.getProduct() != null)
                key = key + info.getProduct().getId().toString();
            if(measureCostMap.containsKey(key))
            {
                MeasureEntryCollection coll = (MeasureEntryCollection)measureCostMap.get(key);
                coll.add(info);
            } else
            {
                MeasureEntryCollection newColl = new MeasureEntryCollection();
                newColl.add(info);
                measureCostMap.put(key, newColl);
            }
        }

        try
        {
            addPanel();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        TimeTools.getInstance().msValuePrintln("end loadFields");
        if("EDIT".equals(getOprtState()) && cost != null && cost.getId() != null)
        {
            String billId = cost.getId().toString();
            lockIds.add(billId);
            String measureIncomeId = getMeasureIncomeId(billId);
            if(measureIncomeId != null)
            {
                lockId2s.add(measureIncomeId);
                try
                {
                    FDCClientUtils.requestDataObjectLock(this, lockIds, "edit");
                    Map uiContext = new HashMap();
                    uiContext.put("ID", measureIncomeId);
                    IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
                    IUIWindow window = uiFactory.create((com.kingdee.eas.fdc.aimcost.client.MeasureIncomeEditUI.class).getName(), uiContext, null, OprtState.VIEW);
                    MeasureIncomeEditUI = (CoreUI)window.getUIObject();
                    if(lockId2s.size() > 0)
                        FDCClientUtils.requestDataObjectLock(MeasureIncomeEditUI, lockId2s, "edit");
                }
                catch(Throwable e1)
                {
                    handUIException(e1);
                    hasMutex = FDCClientUtils.hasMutexed(e1);
                }
            }
        }
    }

    private String getMeasureIncomeId(String measureCostId)
    {
        FDCSQLBuilder builder = new FDCSQLBuilder();
        builder.appendSql("select fid from t_aim_measureincome where fsrcmeasurecostid=? ");
        builder.addParam(measureCostId);
        try
        {
            IRowSet rs = builder.executeQuery();
            if(rs != null && rs.next())
                return rs.getString("fid");
        }
        catch(SQLException e)
        {
            handUIException(e);
        }
        catch(BOSException e1)
        {
            handUIException(e1);
        }
        return null;
    }

    private boolean isLastVersion(String measureCostId)
    {
        FDCSQLBuilder builder = new FDCSQLBuilder();
        builder.appendSql("select FIsLastVersion from t_aim_measurecost where fid=? ");
        builder.addParam(measureCostId);
        try
        {
            IRowSet rs = builder.executeQuery();
            if(rs != null && rs.next())
                return rs.getBoolean("FIsLastVersion");
        }
        catch(SQLException e)
        {
            handUIException(e);
        }
        catch(BOSException e1)
        {
            handUIException(e1);
        }
        return false;
    }

    protected void table_editStopped(KDTable table, KDTEditEvent e)
        throws Exception
    {
        Object oldValue = e.getOldValue();
        Object newValue = e.getValue();
        if(oldValue != null && newValue != null && oldValue.equals(newValue))
            return;
        Object objTmp = table.getHeadRow(0).getCell(0).getUserObject();
        IObjectValue info = null;
        if(objTmp instanceof IObjectValue)
            info = (IObjectValue)objTmp;
        int rowIndex = e.getRowIndex();
        int columnIndex = e.getColIndex();
        if(oldValue == null && newValue == null && !table.getColumnKey(columnIndex).equals("indexName"))
            return;
        IRow row = table.getRow(rowIndex);
        if(table.getColumnKey(columnIndex).equals("index") || table.getColumnKey(columnIndex).equals("coefficient"))
            refreshWorkload(table, row);
        else
        if(table.getColumnKey(columnIndex).equals("price") || table.getColumnKey(columnIndex).equals("workload"))
            refreshSumPrice(table, row);
        else
        if(table.getColumnKey(columnIndex).equals("sumPrice"))
        {
            row.getCell("adjustCoefficient").setUserObject("adjust");
            refreshAdjustAmount(table, row);
        } else
        if(table.getColumnKey(columnIndex).equals("indexName"))
        {
            Object obj = row.getCell("indexName").getValue();
            if(obj instanceof Item)
            {
                Item item = (Item)obj;
                if(info != null)
                {
                    BigDecimal amount = FDCHelper.ZERO;
                    if(info instanceof PlanIndexInfo)
                    {
                        PlanIndexInfo planIndexInfo = (PlanIndexInfo)info;
                        if(item.key.equals("viewArea"))
                            amount = FDCHelper.toBigDecimal(planIndexInfo.getTotalRoadArea()).add(FDCHelper.toBigDecimal(planIndexInfo.getTotalGreenArea()));
                        if(item.key.equals("doors"))
                        {
                            for(int i = 0; i < planIndexInfo.getEntrys().size(); i++)
                            {
                                PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
                                amount = amount.add(FDCHelper.toBigDecimal(entry.getDoors()));
                            }

                        }
                    }
                    if(amount.compareTo(FDCHelper.ZERO) == 0)
                        row.getCell("index").setValue(getCustomIndexValue(info, item));
                    else
                        row.getCell("index").setValue(amount);
                    if(item.key.equals("empty"))
                    {
                        row.getCell("coefficient").setValue(null);
                        row.getCell("coefficientName").setValue(null);
                    }
                }
            }
            refreshWorkload(table, row);
        }
        if(table.getColumnKey(columnIndex).equals("adjustCoefficient"))
        {
            row.getCell(columnIndex).setUserObject("adjust");
            refreshAdjustAmount(table, row);
        }
        if(table.getColumnKey(columnIndex).equals("adjustAmt"))
            refreshAdjustAmount(table, row);
        if(isDetailAcctRow(row))
            if(newValue != null)
            {
                setDetailAcctHashInput(row);
            } else
            {
                boolean isEmpty = true;
                int i = 3;
                do
                {
                    if(i >= table.getColumnCount())
                        break;
                    if(!FDCHelper.isEmpty(row.getCell(i).getValue()))
                    {
                        isEmpty = false;
                        break;
                    }
                    i++;
                } while(true);
                if(isEmpty)
                    setDetailAcctHasNotInput(row);
            }
        setUnionData(table);
        setDataChange(true);
    }

    private void refreshSumPrice(KDTable table, IRow row)
    {
        BigDecimal price = (BigDecimal)row.getCell("price").getValue();
        BigDecimal workload = (BigDecimal)row.getCell("workload").getValue();
        if(price == null)
            price = FDCHelper.ZERO;
        if(workload == null)
            workload = FDCHelper.ZERO;
        if(price.compareTo(FDCHelper.ZERO) == 0 && workload.compareTo(FDCHelper.ZERO) == 0)
        {
            row.getCell("price").setValue(null);
            row.getCell("workload").setValue(null);
            row.getCell("sumPrice").getStyleAttributes().setLocked(false);
        } else
        {
            BigDecimal sumPrice = price.multiply(workload).setScale(2, 4);
            row.getCell("sumPrice").setValue(sumPrice);
            row.getCell("sumPrice").getStyleAttributes().setLocked(true);
        }
        row.getCell("adjustCoefficient").setUserObject("adjust");
        refreshAdjustAmount(table, row);
    }

    private void refreshWorkload(KDTable table, IRow row)
    {
        BigDecimal index = FDCHelper.toBigDecimal(row.getCell("index").getValue());
        BigDecimal coefficient = FDCHelper.toBigDecimal(row.getCell("coefficient").getValue());
        if(index == null)
            index = FDCHelper.ZERO;
        if(coefficient == null)
            coefficient = FDCHelper.ZERO;
        if(index.compareTo(FDCHelper.ZERO) == 0 && coefficient.compareTo(FDCHelper.ZERO) == 0)
        {
            row.getCell("index").setValue(null);
            row.getCell("coefficient").setValue(null);
            row.getCell("workload").getStyleAttributes().setLocked(false);
        } else
        {
            BigDecimal workload = index.multiply(coefficient).setScale(2, 4);
            row.getCell("workload").setValue(workload);
            row.getCell("workload").getStyleAttributes().setLocked(true);
        }
        refreshSumPrice(table, row);
    }

    private void refreshAdjustAmount(KDTable table, IRow row)
    {
        BigDecimal sumPrice = (BigDecimal)row.getCell("sumPrice").getValue();
        if(row.getCell("adjustCoefficient").getUserObject() != null)
        {
            BigDecimal adjustAmt = FDCNumberHelper.multiply(sumPrice, row.getCell("adjustCoefficient").getValue());
            row.getCell("adjustAmt").setValue(adjustAmt);
            row.getCell("amount").setValue(FDCNumberHelper.add(sumPrice, adjustAmt));
        } else
        {
            BigDecimal adjustAmt = (BigDecimal)row.getCell("adjustAmt").getValue();
            row.getCell("adjustCoefficient").setValue(FDCNumberHelper.divide(adjustAmt, sumPrice, 6, 4));
            row.getCell("amount").setValue(FDCNumberHelper.add(sumPrice, adjustAmt));
        }
        row.getCell("adjustCoefficient").setUserObject(null);
        BigDecimal amount = (BigDecimal)row.getCell("amount").getValue();
        if(amount != null)
        {
            Object obj = table.getHeadRow(0).getCell(0).getUserObject();
            if(obj instanceof PlanIndexEntryInfo)
            {
                BigDecimal sellArea = ((PlanIndexEntryInfo)obj).getSellArea();
                if(sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0)
                {
                    BigDecimal sellPart = amount.divide(sellArea, 4);
                    row.getCell("sellPart").setValue(sellPart);
                } else
                {
                    row.getCell("sellPart").setValue(null);
                }
                BigDecimal buildArea = ((PlanIndexEntryInfo)obj).getBuildArea();
                if(buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0)
                {
                    BigDecimal buildPart = amount.divide(buildArea, 4);
                    row.getCell("buildPart").setValue(buildPart);
                } else
                {
                    row.getCell("buildPart").setValue(null);
                }
            } else
            if(obj instanceof PlanIndexInfo)
            {
                BigDecimal sellArea = ((PlanIndexInfo)obj).getBigDecimal("allSellArea");
                if(sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0)
                {
                    BigDecimal sellPart = amount.divide(sellArea, 4);
                    row.getCell("sellPart").setValue(sellPart);
                } else
                {
                    row.getCell("sellPart").setValue(null);
                }
                BigDecimal buildArea = ((PlanIndexInfo)obj).getBigDecimal("allBuildArea");
                if(buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0)
                {
                    BigDecimal buildPart = amount.divide(buildArea, 4);
                    row.getCell("buildPart").setValue(buildPart);
                } else
                {
                    row.getCell("buildPart").setValue(null);
                }
            } else
            {
                row.getCell("buildPart").setValue(null);
                row.getCell("sellPart").setValue(null);
            }
        } else
        {
            row.getCell("buildPart").setValue(null);
            row.getCell("sellPart").setValue(null);
        }
    }

    public void actionAddRow_actionPerformed(ActionEvent arg0)
        throws Exception
    {
        if(plTables.getSelectedIndex() == 0)
            return;
        if(plTables.getSelectedIndex() == 2)
        {
            planIndexTable.addRow(arg0);
            return;
        }
        if(plTables.getSelectedIndex() == 4)
        {
            Object v = prmtProjectType.getValue();
            if(v == null)
            {
                MsgBox.showWarning(this, "六类公摊测算必须先设置项目系列");
                return;
            }
        }
        if(plTables.getSelectedIndex() == 3)
        {
            planIndexTable.addConstrIndexRow(arg0);
            return;
        }
        KDTable table = (KDTable)tables.get(plTables.getSelectedIndex());
        if(table.getRowCount() == 0)
            return;
        int index = table.getSelectManager().getActiveRowIndex();
        if(index == -1)
        {
            table.getSelectManager().set(table.getRowCount() - 1, 0);
            index = table.getRowCount() - 1;
        }
        IRow selectRow = table.getRow(index);
        if(selectRow.getUserObject() instanceof CostAccountInfo)
        {
            CostAccountInfo acct = (CostAccountInfo)selectRow.getUserObject();
            if(acct.isIsLeaf() && acct.getLevel() > 1)
            {
                IRow row = table.addRow(index + 1);
                row.setTreeLevel(selectRow.getTreeLevel() + 1);
                MeasureEntryInfo info = new MeasureEntryInfo();
                info.setCostAccount(acct);
                row.setUserObject(info);
                table.setUserObject("addRow");
                setTemplateMeasureCostF7Editor(table, row);
            } else
            {
                setMessageText("非明细行或一级明细行不能添加子行!");
                showMessage();
            }
        } else
        {
            MeasureEntryInfo infoUp = (MeasureEntryInfo)selectRow.getUserObject();
            if(isDetailAcctRow(selectRow))
            {
                Map splitTypeMap = measureCollectTable.getSplitTypes();
                MeasureEntryInfo entry = (MeasureEntryInfo)selectRow.getUserObject();
                ProductTypeInfo product = (ProductTypeInfo)table.getHeadRow(0).getUserObject();
                entry.setEntryName((String)selectRow.getCell("acctName").getValue());
                entry.setProduct(product);
                Object obj = selectRow.getCell("indexName").getValue();
                if(obj instanceof Item)
                {
                    Item item = (Item)obj;
                    entry.setSimpleName(item.key);
                    entry.setIndexName(item.toString());
                    entry.setIndexValue(FDCHelper.toBigDecimal(selectRow.getCell("index").getValue()));
                }
                entry.setCoefficientName((String)selectRow.getCell("coefficientName").getValue());
                entry.setCoefficient((BigDecimal)selectRow.getCell("coefficient").getValue());
                Object value = selectRow.getCell("unit").getValue();
                entry.setUnit((MeasureUnitInfo)value);
                entry.setWorkload((BigDecimal)selectRow.getCell("workload").getValue());
                entry.setPrice((BigDecimal)selectRow.getCell("price").getValue());
                entry.setCostAmount((BigDecimal)selectRow.getCell("sumPrice").getValue());
                entry.setProgram((String)selectRow.getCell("program").getValue());
                entry.setDesc((String)selectRow.getCell("desc").getValue());
                entry.setChangeReason((String)selectRow.getCell("changeReason").getValue());
                entry.setDescription((String)selectRow.getCell("description").getValue());
                if(entry.getCostAccount().getType() == CostAccountTypeEnum.SIX)
                {
                    Object splitType = splitTypeMap.get(entry.getCostAccount().getId().toString());
                    if(splitType != null)
                        entry.setNumber(splitType.toString());
                }
                entry.setAdjustCoefficient((BigDecimal)selectRow.getCell("adjustCoefficient").getValue());
                entry.setAdjustAmt((BigDecimal)selectRow.getCell("adjustAmt").getValue());
                entry.setAmount((BigDecimal)selectRow.getCell("amount").getValue());
                IRow tempRow = table.addRow(index + 1);
                tempRow.setUserObject(infoUp);
                tempRow.setTreeLevel(selectRow.getTreeLevel() + 1);
                loadRow(table, tempRow, infoUp.getProduct());
                selectRow.setUserObject(infoUp.getCostAccount());
                selectRow.getCell(0).setUserObject(null);
                clearDetailAcctRow(table, selectRow);
                setDetailAcctRowNull(selectRow);
                selectRow.getStyleAttributes().setLocked(true);
                selectRow.getStyleAttributes().setBackground(new Color(15789529));
                index++;
                IRow row = table.addRow(index + 1);
                row.setTreeLevel(selectRow.getTreeLevel() + 1);
                MeasureEntryInfo info = new MeasureEntryInfo();
                info.setCostAccount(infoUp.getCostAccount());
                row.setUserObject(info);
                table.setUserObject("addRow");
                setTemplateMeasureCostF7Editor(table, row);
                setUnionData(table);
            } else
            {
                IRow row = table.addRow(index + 1);
                row.setTreeLevel(selectRow.getTreeLevel());
                MeasureEntryInfo info = new MeasureEntryInfo();
                info.setCostAccount(infoUp.getCostAccount());
                row.setUserObject(info);
                table.setUserObject("addRow");
                setTemplateMeasureCostF7Editor(table, row);
            }
        }
    }

    private void setTemplateMeasureCostF7Editor(KDTable kdtable, IRow irow)
    {
    }

    public void actionDeleteRow_actionPerformed(ActionEvent arg0)
        throws Exception
    {
        if(plTables.getSelectedIndex() == 0)
            return;
        if(plTables.getSelectedIndex() == 2)
        {
            planIndexTable.deleteRow(arg0);
            return;
        }
        if(plTables.getSelectedIndex() == 3)
        {
            planIndexTable.deleteConstrIndexRow(arg0);
            return;
        }
        KDTable table = (KDTable)tables.get(plTables.getSelectedIndex());
        KDTSelectManager selectManager = table.getSelectManager();
        if(selectManager == null || selectManager.size() == 0)
            return;
        for(int i = 0; i < selectManager.size(); i++)
        {
            KDTSelectBlock selectBlock = selectManager.get(i);
            for(int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++)
            {
                IRow selectRow = table.getRow(j);
                if(selectRow != null && (selectRow.getUserObject() instanceof MeasureEntryInfo))
                    selectRow.getCell("price").setUserObject("delete");
            }

        }

        for(int i = 0; i < table.getRowCount(); i++)
        {
            IRow row = table.getRow(i);
            if(row.getCell("price").getUserObject() == null)
                continue;
            if(isDetailAcctRow(row))
            {
                clearDetailAcctRow(table, row);
                row.getCell("price").setUserObject(null);
            } else
            {
                int j = row.getRowIndex() - 1;
                int k = row.getRowIndex() + 1;
                if(j > 0)
                {
                    IRow parentRow = table.getRow(j);
                    if((parentRow.getUserObject() instanceof CostAccountInfo) && (k == table.getRowCount() || isDetailAcctRow(table.getRow(k)) || (table.getRow(k).getUserObject() instanceof CostAccountInfo)))
                    {
                        clearDetailAcctRow(table, parentRow);
                        parentRow.getStyleAttributes().setBackground(Color.WHITE);
                        parentRow.getStyleAttributes().setLocked(false);
                        MeasureEntryInfo info = new MeasureEntryInfo();
                        info.setCostAccount((CostAccountInfo)parentRow.getUserObject());
                        parentRow.setUserObject(info);
                        setTemplateMeasureCostF7Editor(table, parentRow);
                        setDetailAcctRow(parentRow);
                        parentRow.getCell("acctNumber").getStyleAttributes().setLocked(true);
                        parentRow.getCell("acctNumber").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
                        parentRow.getCell("acctName").getStyleAttributes().setLocked(true);
                        parentRow.getCell("acctName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
                        parentRow.getCell("amount").getStyleAttributes().setLocked(true);
                        parentRow.getCell("amount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
                        parentRow.getCell("buildPart").getStyleAttributes().setLocked(true);
                        parentRow.getCell("buildPart").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
                        parentRow.getCell("sellPart").getStyleAttributes().setLocked(true);
                        parentRow.getCell("sellPart").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
                        parentRow.getCell("index").getStyleAttributes().setLocked(true);
                        parentRow.getCell("index").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
                    }
                }
                table.removeRow(row.getRowIndex());
                i--;
            }
            table.setUserObject("delteRow");
            setUnionData(table);
        }

    }

    public void setUnionData(KDTable table)
    {
        String cols[] = {
            "sumPrice", "amount", "buildPart", "sellPart", "adjustAmt"
        };
label0:
        for(int i = 0; i < table.getRowCount(); i++)
        {
            IRow row = table.getRow(i);
            if(!(row.getUserObject() instanceof CostAccountInfo))
                continue;
            int level = row.getTreeLevel();
            java.util.List aimRowList = new ArrayList();
            int j;
            for(j = i + 1; j < table.getRowCount(); j++)
            {
                IRow rowAfter = table.getRow(j);
                if(rowAfter.getTreeLevel() <= level)
                    break;
                if(rowAfter.getUserObject() instanceof MeasureEntryInfo)
                    aimRowList.add(rowAfter);
            }

            j = 0;
            do
            {
                if(j >= cols.length)
                    continue label0;
                BigDecimal sum = FDCHelper.ZERO;
                if(cols[j].equals("sellPart"))
                {
                    BigDecimal amount = (BigDecimal)row.getCell("amount").getValue();
                    if(amount != null)
                    {
                        Object obj = table.getHeadRow(0).getCell(0).getUserObject();
                        if(obj instanceof PlanIndexEntryInfo)
                        {
                            BigDecimal sellArea = ((PlanIndexEntryInfo)obj).getSellArea();
                            if(sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0)
                            {
                                BigDecimal sellPart = FDCNumberHelper.divide(amount, sellArea, 2, 4);
                                sum = sellPart;
                            }
                        } else
                        if(obj instanceof PlanIndexInfo)
                        {
                            BigDecimal sellArea = ((PlanIndexInfo)obj).getBigDecimal("allSellArea");
                            if(sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0)
                            {
                                BigDecimal sellPart = FDCNumberHelper.divide(amount, sellArea, 2, 4);
                                sum = sellPart;
                            }
                        }
                    }
                } else
                if(cols[j].equals("buildPart"))
                {
                    BigDecimal amount = (BigDecimal)row.getCell("amount").getValue();
                    if(amount != null)
                    {
                        Object obj = table.getHeadRow(0).getCell(0).getUserObject();
                        if(obj instanceof PlanIndexEntryInfo)
                        {
                            BigDecimal buildArea = ((PlanIndexEntryInfo)obj).getBuildArea();
                            if(buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0)
                            {
                                BigDecimal buildPart = FDCNumberHelper.divide(amount, buildArea, 2, 4);
                                sum = buildPart;
                            }
                        } else
                        if(obj instanceof PlanIndexInfo)
                        {
                            BigDecimal buildArea = ((PlanIndexInfo)obj).getBigDecimal("allBuildArea");
                            if(buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0)
                            {
                                BigDecimal buildPart = FDCNumberHelper.divide(amount, buildArea, 2, 4);
                                sum = buildPart;
                            }
                        }
                    }
                } else
                {
                    for(int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++)
                    {
                        IRow rowAdd = (IRow)aimRowList.get(rowIndex);
                        Object value = rowAdd.getCell(cols[j]).getValue();
                        if(value == null)
                            continue;
                        if(value instanceof BigDecimal)
                        {
                            sum = sum.add((BigDecimal)value);
                            continue;
                        }
                        if(value instanceof Integer)
                            sum = sum.add(new BigDecimal(((Integer)value).toString()));
                    }

                }
                if(sum != null && sum.compareTo(FDCHelper.ZERO) == 0)
                    sum = null;
                row.getCell(cols[j]).setValue(sum);
                j++;
            } while(true);
        }

        addTotalRow(table);
    }

    public void actionImportApportion_actionPerformed(ActionEvent arg0)
        throws Exception
    {
        super.actionImportApportion_actionPerformed(arg0);
    }

    private boolean dealWithEmptyRow()
    {
        String titleStr = "存在分录数据填写不完整的记录，导致调整前合价为零或者为空。 \n \n是否系统自动删除对应记录，然后保存？\n若选择是，则系统自动将信息不完整的纪录行删除，然后保存。\n选择否，则需要返回将对应记录填写完整后才能进行保存！";
        Map detailMap = new HashMap();
        for(int i = 0; i < tables.size(); i++)
        {
            KDTable table = (KDTable)tables.get(i);
            for(int j = 0; j < table.getRowCount(); j++)
            {
                IRow row = table.getRow(j);
                if(!(row.getUserObject() instanceof MeasureEntryInfo) || isDetailAcctRow(row) && !isDetailAcctHasInput(row))
                    continue;
                BigDecimal sumPrice = FDCHelper.toBigDecimal(row.getCell("sumPrice").getValue());
                String key = table.getName();
                if(sumPrice.compareTo(FDCHelper.ZERO) != 0)
                    continue;
                if(detailMap.containsKey(key))
                {
                    StringBuffer detail = (StringBuffer)detailMap.get(key);
                    detail.append((row.getRowIndex() + 1) + "、");
                } else
                {
                    StringBuffer detail = new StringBuffer();
                    detail.append((row.getRowIndex() + 1) + "、");
                    detailMap.put(key, detail);
                }
            }

        }

        if(detailMap.size() > 0)
        {
            StringBuffer msg = new StringBuffer();
            for(Iterator iter = detailMap.keySet().iterator(); iter.hasNext(); msg.append("\n"))
            {
                String key = (String)iter.next();
                String val = ((StringBuffer)detailMap.get(key)).toString();
                msg.append("页签：");
                msg.append(key);
                msg.append("，");
                msg.append("分录行第 ");
                msg.append(val.substring(0, val.length() - 1));
                msg.append(" 行");
            }

            int v = MsgBox.showConfirm3(this, titleStr, msg.toString());
            if(v == 0)
            {
                for(int i = 0; i < tables.size(); i++)
                {
                    KDTable table = (KDTable)tables.get(i);
                    for(int j = 0; j < table.getRowCount(); j++)
                    {
                        IRow row = table.getRow(j);
                        if(!(row.getUserObject() instanceof MeasureEntryInfo) || isDetailAcctRow(row) && !isDetailAcctHasInput(row))
                            continue;
                        BigDecimal sumPrice = FDCHelper.toBigDecimal(row.getCell("sumPrice").getValue());
                        if(sumPrice.compareTo(FDCHelper.ZERO) != 0)
                            continue;
                        setDetailAcctHasNotInput(row);
                        for(int k = 3; k < table.getColumnCount() - 1; k++)
                            row.getCell(k).setValue(null);

                    }

                }

            }
        }
        return false;
    }

    protected void beforeStoreFields(ActionEvent e)
        throws Exception
    {
        dealWithEmptyRow();
        if(txtVersionName.getText() == null || txtVersionName.getText().length() == 0)
        {
            MsgBox.showInfo("版本名称不能为空!");
            abort();
        }
        for(int i = 0; i < tables.size(); i++)
        {
            KDTable table = (KDTable)tables.get(i);
            for(int j = 0; j < table.getRowCount(); j++)
            {
                IRow row = table.getRow(j);
                if(!(row.getUserObject() instanceof MeasureEntryInfo) || isDetailAcctRow(row) && !isDetailAcctHasInput(row))
                    continue;
                int k = table.getColumnIndex("workload");
                if(row.getCell("sumPrice").getValue() != null)
                {
                    BigDecimal value = (BigDecimal)row.getCell("workload").getValue();
                    if(value != null && value.compareTo(FDCHelper.MAX_VALUE) > 0)
                    {
                        setMessageText("工作量超出最大值!");
                        showMessage();
                        plTables.setSelectedIndex(i);
                        table.getSelectManager().select(0, 0);
                        table.getSelectManager().select(row.getRowIndex(), k);
                        abort();
                    }
                }
                k = table.getColumnIndex("sumPrice");
                String msg = "合价";
                if(isUseAdjustCoefficient())
                    msg = "调整前合价";
                if(row.getCell("sumPrice").getValue() == null)
                {
                    setMessageText(msg + "不能为空!");
                    showMessage();
                    plTables.setSelectedIndex(i);
                    table.getSelectManager().select(0, 0);
                    table.getSelectManager().select(row.getRowIndex(), k);
                    abort();
                    continue;
                }
                BigDecimal value = (BigDecimal)row.getCell("sumPrice").getValue();
                if(value.compareTo(FDCHelper.ZERO) == 0)
                {
                    setMessageText(msg + "不能为0!");
                    showMessage();
                    plTables.setSelectedIndex(i);
                    table.getSelectManager().select(0, 0);
                    table.getSelectManager().select(row.getRowIndex(), k);
                    abort();
                }
                if(value.compareTo(FDCHelper.MAX_VALUE) > 0)
                {
                    setMessageText(msg + "超出最大值!");
                    showMessage();
                    plTables.setSelectedIndex(i);
                    table.getSelectManager().select(0, 0);
                    table.getSelectManager().select(row.getRowIndex(), k);
                    abort();
                }
            }

        }

    }

    public void actionSave_actionPerformed(ActionEvent e)
        throws Exception
    {
        setOprtState("EDIT");
        editData.put("PlanIndex", planIndexTable.getPlanIndexInfo());
        editData.put("constrEntrys", planIndexTable.getConstrEntrys());
        Boolean isEditVersion = (Boolean)getUIContext().get("isEditVersion");
        submitSaleAreaData();
        if(isEditVersion != null && isEditVersion.booleanValue())
            handleVersion((MeasureCostInfo)editData);
        if(editData.getId() != null)
        {
            FilterInfo filter = new FilterInfo();
            filter.appendFilterItem("id", editData.getId().toString());
            filter.appendFilterItem("state", "4AUDITTED");
            if(MeasureCostFactory.getRemoteInstance().exists(filter))
            {
                FDCMsgBox.showWarning(this, "测算已审批不能进行此操作");
                SysUtil.abort();
            }
        }
        
        
        
        	
        System.out.println("))))))))))))))))))))))))))))))))))))))))))))))))))))))))"+planIndexTable.getPlanIndexInfo());
        confirmVersionOnly();
        submitMeasureCollData();
        MsgBox.showWarning("当自定义指标产品类型不全时，系统将自动补全产品类型，默认指标值为零!");
        
        super.actionSave_actionPerformed(e);
        //biaobiao 2013-6-15 保存自定义指标时默认为没有录入的产品设置指标值为0（用于导出指标不出现异常数据）
        setCustomer();
        storeFields();
        initOldData(editData);
        actionImportTemplate.setEnabled(false);
        getUIContext().put("isEditVersion", null);
        setDataChange(false);
    }

    /**
     * biaobiao
     * 保存自定义指标时默认为没有录入的产品设置指标值为0（用于导出指标不出现异常数据）
     * @throws EASBizException
     * @throws BOSException
     */
	private void setCustomer() throws EASBizException, BOSException {
		MeasureCostInfo measureCostInfo = (MeasureCostInfo)this.editData;
    	measureCostInfo.getSaleAreaData().clear();
		List planIndex = new ArrayList();
        List list = new ArrayList();
        PlanIndexInfo planIndexInfo = planIndexTable.getPlanIndexInfo();
        
        PlanIndexEntryCollection entrys = planIndexInfo.getEntrys();
        for (int i = 0; i < entrys.size(); i++) {
        	PlanIndexEntryInfo entryInfo = entrys.get(i);
        	ProductTypeInfo typeInfo = entryInfo.getProduct();
        	if(typeInfo != null){
        		planIndex.add(entryInfo);
        	}
        }
        
      
        //
        Map appmap = new HashMap();
        CustomPlanIndexEntryCollection customEntrys = planIndexInfo.getCustomEntrys();
        for (int i = 0; i < customEntrys.size(); i++) {
        	CustomPlanIndexEntryInfo entryInfo = customEntrys.get(i);
        	ProductTypeInfo productType = entryInfo.getProductType();
        	ApportionTypeInfo apportType = entryInfo.getApportType();
        	if(appmap.get(apportType) == null ){
        		list = new  ArrayList();
        		list.add(productType);
        		appmap.put(apportType,list);
        	}else{
        		List objval = (List)appmap.get(apportType);
        		objval.add(productType);
        	}
        }
        
        
        
        
        for (int i = 0; i < planIndex.size(); i++) {
        	//entryInfo
        	PlanIndexEntryInfo prtyid = (PlanIndexEntryInfo)planIndex.get(i);
        	//遍历map
        	Iterator itr = appmap.entrySet().iterator();
        	while(itr.hasNext()){
        		Map.Entry key = (Map.Entry)itr.next();
        		ApportionTypeInfo key2 = (ApportionTypeInfo)key.getKey();
        		List lisval = (List)appmap.get(key2);
        		int ss = 0 ;
        		for ( int j = 0; j < lisval.size(); j++) {
        			ProductTypeInfo prod = (ProductTypeInfo)lisval.get(j);
        			if(prtyid.getProduct().getId().equals(prod.getId())){
        				break;
        			}
        			ss++;
				}
        		if(ss == lisval.size()){
        			CustomPlanIndexEntryInfo info = new CustomPlanIndexEntryInfo();
					info.setApportType(key2);
					info.setProductType(prtyid.getProduct());
					info.setValue(new BigDecimal(0));
					info.setIsProduct(true);
					PlanIndexCollection indexCollection = PlanIndexFactory.getRemoteInstance().getPlanIndexCollection("where headid = '"+editData.getId()+"'");
					if(indexCollection.size() > 0 ){
						PlanIndexInfo indexInfo = indexCollection.get(0);
						info.setParent(indexInfo);
						CustomPlanIndexEntryFactory.getRemoteInstance().addnew(info);
					}
					customEntrys.add(info);
					System.out.println("))))))))))))))))))))))))))))))))))))))))))))))))))))))))"+customEntrys);
        		}
        	}
		}
	}

    public void confirmVersionOnly()
        throws BOSException
    {
        Boolean isAimMeasure = (Boolean)getUIContext().get("isAimMeasure");
        String versionNum = txtVersionNumber.getText();
        MeasureCostInfo info = (MeasureCostInfo)editData;
        CurProjectInfo prj = (CurProjectInfo)prmtProject.getValue();
        if(versionNum.indexOf('!') == -1)
            versionNum = versionNum.replace('.', '!');
        MeasureStageInfo stage = (MeasureStageInfo)comboMeasureStage.getSelectedItem();
        if(isAimMeasure.booleanValue())
        {
            String selectOrgId = getUIContext().get("orgId").toString();
            FDCSQLBuilder builder = new FDCSQLBuilder();
            builder.appendSql("select REPLACE(FVersionNumber, '!', '.') as FVersionNumber from T_AIM_MeasureCost where FOrgUnitID = ? ");
            builder.addParam(selectOrgId);
            if(prj != null && prj.getId() != null)
            {
                builder.appendSql(" and FProjectID = ? ");
                builder.addParam(prj.getId().toString());
            }
            builder.appendSql(" and FIsAimMeasure = ? ");
            builder.addParam(isAimMeasure);
            builder.appendSql(" and FVersionNumber = ? ");
            builder.addParam(versionNum);
            if(info != null && info.getId() != null)
            {
                builder.appendSql(" and FID <> ? ");
                builder.addParam(info.getId().toString());
            }
            if(stage != null && stage.getId() != null)
            {
                builder.appendSql(" and FMeasureStageID =? ");
                builder.addParam(stage.getId().toString());
            }
            IRowSet row = builder.executeQuery();
            if(row.size() != 0)
            {
                FDCMsgBox.showWarning(this, "该版本号已经存在");
                SysUtil.abort();
            }
        } else
        {
            String selectOrgId = getUIContext().get("orgId").toString();
            FDCSQLBuilder builder = new FDCSQLBuilder();
            builder.appendSql("select REPLACE(FVersionNumber, '!', '.') as FVersionNumber from T_AIM_MeasureCost where FOrgUnitID = ? ");
            builder.addParam(selectOrgId);
            if(prj != null && prj.getId() != null)
            {
                builder.appendSql(" and FProjectID = ? ");
                builder.addParam(prj.getId().toString());
            }
            builder.appendSql(" and FIsAimMeasure = ? ");
            builder.addParam(isAimMeasure);
            builder.appendSql(" and FVersionNumber = ? ");
            builder.addParam(versionNum);
            if(info != null && info.getId() != null)
            {
                builder.appendSql(" and FID <> ? ");
                builder.addParam(info.getId().toString());
            }
            if(stage != null && stage.getId() != null)
            {
                builder.appendSql(" and FMeasureStageID =? ");
                builder.addParam(stage.getId().toString());
            }
            IRowSet row = builder.executeQuery();
            if(row.size() != 0)
            {
                FDCMsgBox.showWarning(this, "该版本号已经存在");
                SysUtil.abort();
            }
        }
    }

    protected boolean isModifySave()
    {
        return isModify();
    }

    public void actionSubmit_actionPerformed(ActionEvent e)
        throws Exception
    {
        setOprtState("EDIT");
        editData.put("PlanIndex", planIndexTable.getPlanIndexInfo());
        editData.put("constrEntrys", planIndexTable.getConstrEntrys());
        Boolean isEditVersion = (Boolean)getUIContext().get("isEditVersion");
        submitSaleAreaData();
        if(isEditVersion != null && isEditVersion.booleanValue())
            handleVersion((MeasureCostInfo)editData);
        if(editData.getId() != null)
        {
            FilterInfo filter = new FilterInfo();
            filter.appendFilterItem("id", editData.getId().toString());
            filter.appendFilterItem("state", "4AUDITTED");
            if(MeasureCostFactory.getRemoteInstance().exists(filter))
            {
                FDCMsgBox.showWarning(this, "测算已审批不能进行此操作");
                SysUtil.abort();
            }
        }
        confirmVersionOnly();
        submitMeasureCollData();
//        MsgBox.showWarning("当自定义指标产品类型不全时，系统将自动补全产品类型，默认指标值为零!");
        super.actionSubmit_actionPerformed(e);
        setCustomer();
        actionImportTemplate.setEnabled(false);
        storeFields();
        initOldData(editData);
        actionImportTemplate.setEnabled(false);
        getUIContext().put("isEditVersion", null);
        setDataChange(false);
    }

    protected void showSaveSuccess()
    {
        setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
        setShowMessagePolicy(0);
        setIsShowTextOnly(false);
        showMessage();
    }

    protected void showSubmitSuccess()
    {
        setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Submit_OK"));
        if(chkMenuItemSubmitAndAddNew.isSelected())
            setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
        else
        if(!chkMenuItemSubmitAndPrint.isSelected() && chkMenuItemSubmitAndAddNew.isSelected())
            setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
        setIsShowTextOnly(false);
        setShowMessagePolicy(0);
        showMessage();
    }

    public void actionPrint_actionPerformed(ActionEvent e)
        throws Exception
    {
        KDTable table = (KDTable)tables.get(plTables.getSelectedIndex());
        table.getPrintManager().print();
    }

    private void refreshAllMeasureTable()
    {
    	System.out.println("begin "+System.nanoTime());
        for(int i = 4; i < tables.size(); i++)
        {
            KDTable table = (KDTable)tables.get(i);
            refreshMeasureTable(table);
        }
        System.out.println("end "+System.nanoTime());
    }

    private void refreshMeasureTable(KDTable table)
    {
        if(table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo)
        {
            ProductTypeInfo product = (ProductTypeInfo)table.getHeadRow(0).getUserObject();
            table.getHeadRow(0).getCell(0).setUserObject(planIndexTable.getPlanIndexEntryInfo(product.getId().toString()));
            ICellEditor editor = getIndexEditor(CostAccountTypeEnum.MAIN, product.getId().toString());
            table.getColumn("indexName").setEditor(editor);
        } else
        {
            PlanIndexInfo planIndexInfo = planIndexTable.getPlanIndexInfo();
            ICellEditor editor = getIndexEditor(CostAccountTypeEnum.SIX, null);
            table.getColumn("indexName").setEditor(editor);
            PlanIndexTable _tmp = planIndexTable;
            BigDecimal amount = PlanIndexTable.getAllSellArea(planIndexInfo);
            planIndexInfo.put("allSellArea", amount);
            PlanIndexTable _tmp1 = planIndexTable;
            amount = PlanIndexTable.getAllBuildArea(planIndexInfo);
            planIndexInfo.put("allBuildArea", amount);
            table.getHeadRow(0).getCell(0).setUserObject(planIndexInfo);
        }
        for(int j = 0; j < table.getRowCount(); j++)
        {
            IRow row = table.getRow(j);
            if(!(row.getUserObject() instanceof MeasureEntryInfo))
                continue;
            try
            {
                table_editStopped(table, new KDTEditEvent(table, null, null, j, table.getColumnIndex("indexName"), false, 1));
            }
            catch(Exception e)
            {
                handUIException(e);
            }
        }

    }

    public void actionPrintPreview_actionPerformed(ActionEvent e)
        throws Exception
    {
        KDTable table = (KDTable)tables.get(plTables.getSelectedIndex());
        table.getPrintManager().printPreview();
    }

    void refreshMeasureCostMap()
    {
        storeFields();
        MeasureCostInfo cost = (MeasureCostInfo)editData;
        measureCostMap.clear();
        MeasureEntryCollection costEntrys = cost.getCostEntry();
        for(int i = 0; i < costEntrys.size(); i++)
        {
            MeasureEntryInfo info = costEntrys.get(i);
            CostAccountInfo costAccount = info.getCostAccount();
            String key = costAccount.getId().toString();
            if(info.getProduct() != null)
                key = key + info.getProduct().getId().toString();
            if(measureCostMap.containsKey(key))
            {
                MeasureEntryCollection coll = (MeasureEntryCollection)measureCostMap.get(key);
                coll.add(info);
            } else
            {
                MeasureEntryCollection newColl = new MeasureEntryCollection();
                newColl.add(info);
                measureCostMap.put(key, newColl);
            }
        }

    }

    TreeModel getCostAcctTree()
    {
        return costAcctTree;
    }

    Map getMeasureCostMap()
    {
        return measureCostMap;
    }

    PlanIndexTable getPlanIndexTable()
    {
        return planIndexTable;
    }

    KDTabbedPane getplTables()
    {
        return plTables;
    }

    java.util.List getTables()
    {
        return tables;
    }

    KDTDefaultCellEditor getCollectIndexEditor()
    {
        if(isMeasureIndex())
        {
            return getCollectMeasureIndexEditor();
        } else
        {
            Object items[] = getCollectItems();
            KDComboBox box = new KDComboBox(items);
            return new KDTDefaultCellEditor(box);
        }
    }

    KDTDefaultCellEditor getCollectMeasureIndexEditor()
    {
        Object items[] = null;
        if(nameSet != null)
        {
            Object nameItems[] = new Object[nameSet.size()];
            int h = 0;
            for(int i = 0; i < getCollectItems().length; i++)
            {
                String name = getCollectItems()[i].toString();
                if(nameSet.contains(name))
                {
                    nameItems[h] = getCollectItems()[i];
                    h++;
                }
            }

            items = nameItems;
        }
        KDComboBox box = new KDComboBox(items);
        return new KDTDefaultCellEditor(box);
    }

    KDTDefaultCellEditor getIndexEditor(CostAccountTypeEnum type, String productId)
    {
        Object items[] = null;
        if(type == CostAccountTypeEnum.SIX)
            items = getSixItems();
        else
        if(type == CostAccountTypeEnum.MAIN)
            items = getMainItems();
        else
            return null;
        KDComboBox box = new KDComboBox(items);
        return new KDTDefaultCellEditor(box);
    }

    private void initMeasureIndex()
        throws BOSException
    {
        nameSet = new HashSet();
        if(isMeasureIndex())
        {
            EntityViewInfo view = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            view.setFilter(filter);
            filter.getFilterItems().add(new FilterItemInfo("type", "0STANDARD"));
            filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
            view.getSelector().add("name");
            MeasureIndexCollection indexNames = MeasureIndexFactory.getRemoteInstance().getMeasureIndexCollection(view);
            if(indexNames != null)
            {
                for(int i = 0; i < indexNames.size(); i++)
                    nameSet.add(indexNames.get(i).getName());

            }
        }
    }

    private Object[] getCollectItems()
    {
        COLLECTITEMS = null;
        if(COLLECTITEMS == null)
        {
            CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs("productId");
            if(customPlanIndexs.size() == 0)
            {
                COLLECTITEMS = Item.SPLITITEMS;
            } else
            {
                java.util.List list = new ArrayList();
                Set appSet = new HashSet();
                int i;
                for(i = 0; i < customPlanIndexs.size(); i++)
                {
                    String appId = customPlanIndexs.get(i).getApportType().getId().toString();
                    if(!appSet.contains(appId))
                    {
                        appSet.add(appId);
                        list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
                    }
                }

                COLLECTITEMS = new Item[list.size() + Item.SPLITITEMS.length];
                System.arraycopy(Item.SPLITITEMS, 0, ((Object) (COLLECTITEMS)), 0, Item.SPLITITEMS.length);
                i = Item.SPLITITEMS.length;
                for(Iterator iter = list.iterator(); iter.hasNext();)
                    COLLECTITEMS[i++] = iter.next();

            }
        }
        return COLLECTITEMS;
    }

    private Object[] getMainItems()
    {
        MAINITEMS = null;
        if(MAINITEMS == null)
        {
            CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs("productId");
            if(customPlanIndexs.size() == 0)
            {
                MAINITEMS = Item.PRODUCTITEMS;
            } else
            {
                java.util.List list = new ArrayList();
                Set appSet = new HashSet();
                int i;
                for(i = 0; i < customPlanIndexs.size(); i++)
                {
                    String appId = customPlanIndexs.get(i).getApportType().getId().toString();
                    if(!appSet.contains(appId))
                    {
                        appSet.add(appId);
                        list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
                    }
                }

                MAINITEMS = new Item[list.size() + Item.PRODUCTITEMS.length];
                System.arraycopy(Item.PRODUCTITEMS, 0, ((Object) (MAINITEMS)), 0, Item.PRODUCTITEMS.length);
                i = Item.PRODUCTITEMS.length;
                for(Iterator iter = list.iterator(); iter.hasNext();)
                    MAINITEMS[i++] = iter.next();

            }
        }
        return MAINITEMS;
    }

    protected void verifyInput(ActionEvent e)
        throws Exception
    {
        FDCClientVerifyHelper.verifyRequire(this);
        if(comboMeasureStage.getSelectedItem() == null)
        {
            FDCMsgBox.showWarning(this, "测算阶段不能为空");
            abort();
        }
        super.verifyInput(e);
    }

    public void actionImportTemplate_actionPerformed(ActionEvent e)
        throws Exception
    {
        TimeTools.getInstance().setDebug(true);
        TimeTools.getInstance().reset();
        TimeTools.getInstance().msValuePrintln("start");
        Map context = new HashMap();
        String orgId = (String)getUIContext().get("orgId");
        context.put("orgUnit.id", orgId);
        context.put("Owner", this);
        context.put("isAimMeasure", getUIContext().get("isAimMeasure"));
        IUIFactory uiFactory = null;
        try
        {
            uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
            IUIWindow classDlg = uiFactory.create("com.kingdee.eas.fdc.aimcost.client.TemplateMeasureCostListUI", context, null, null, 16);
            classDlg.show();
            TemplateMeasureCostListUI ui = (TemplateMeasureCostListUI)classDlg.getUIObject();
            if(!ui.isCancel())
            {
                TemplateMeasureCostCollection data = ui.getData();
                if(data != null && data.size() > 0)
                {
                    String versionName = txtVersionName.getText();
                    String versionNumber = txtVersionNumber.getText();
                    Object projectType = prmtProjectType.getData();
                    Object project = prmtProject.getData();
                    TimeTools.getInstance().msValuePrintln("start storeFromTemplate");
                    String objectId = orgId;
                    if(project != null)
                        objectId = ((CurProjectInfo)project).getId().toString();
                    MeasureCostInfo editData2 = MeasureCostFactory.getRemoteInstance().getMeasureFromTemplate(data.get(0).getId().toString(), objectId);
                    editData2.setIsAimMeasure(isAimMeasure());
                    editData2.setCreateTime(null);
                    editData2.setCreator(null);
                    FullOrgUnitInfo org = new FullOrgUnitInfo();
                    org.setId(BOSUuid.read(orgId));
                    editData2.setOrgUnit(org);
                    editData2.setProject((CurProjectInfo)project);
                    MeasureCostVersionHandler version = new MeasureCostVersionHandler(orgId, objectId, editData2.isIsAimMeasure(), editData2.getMeasureStage());
                    versionNumber = MeasureCostVersionHandler.getNextVersion(version.getLastVersion());
                    editData2.setVersionNumber(versionNumber);
                    editData.putAll(editData2);
                    TimeTools.getInstance().msValuePrintln("end storeFromTemplate");
                    isFirstLoad = true;
                    loadFields();
                    txtVersionName.setText(versionName);
                    if(editData2.getVersionNumber() != null)
                        txtVersionNumber.setText(editData2.getVersionNumber().replaceAll("!", "\\."));
                    if(projectType != null)
                        prmtProjectType.setData(projectType);
                    if(project != null)
                        prmtProject.setData(project);
                }
                setDataChange(true);
            }
            TimeTools.getInstance().msValuePrintln("end");
        }
        catch(BOSException ex)
        {
            ExceptionHandler.handle(this, ex);
            return;
        }
    }

    public void actionExitCurrent_actionPerformed(ActionEvent e)
        throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    protected void comboMeasureStage_itemStateChanged(ItemEvent e)
        throws Exception
    {
        super.comboMeasureStage_itemStateChanged(e);
        MeasureStageInfo stageInfo = (MeasureStageInfo)comboMeasureStage.getSelectedItem();
        if(stageInfo == null || project == null)
            return;
        if("ADDNEW".equals(getOprtState()) || "EDIT".equals(getOprtState()))
        {
            String msg = AimCostClientHelper.getRes("addNew");
            Boolean isAimMeasure = (Boolean)getUIContext().get("isAimMeasure");
            MeasureStageInfo lastStageInfo = AimCostClientHelper.getLastMeasureStage(project, isAimMeasure.booleanValue());
            if(lastStageInfo != null && FDCHelper.subtract(lastStageInfo.getNumber(), stageInfo.getNumber()).compareTo(FDCHelper.ZERO) == 1)
            {
                comboMeasureStage.setSelectedItem(null);
                StringBuffer sb = new StringBuffer();
                sb.append("已存在 [ ").append(lastStageInfo.getNumber()).append(lastStageInfo.getName()).append(" ] 最终版本的目标成本测算,不能").append(msg).append(" [ ").append(stageInfo.getNumber()).append(stageInfo.getName()).append(" ] 目标成本测算。");
                FDCMsgBox.showWarning(sb.toString());
                SysUtil.abort();
            }
            MeasureCostInfo info = (MeasureCostInfo)editData;
            MeasureCostVersionHandler version = new MeasureCostVersionHandler(info.getOrgUnit().getId().toString(), info.getProject().getId().toString(), isAimMeasure.booleanValue(), stageInfo);
            String versionNumber = version.getLastVersion();
            if(versionNumber.indexOf('!') == -1)
                versionNumber = versionNumber.replace('.', '!');
            txtVersionNumber.setText(MeasureCostVersionHandler.getNextVersion(versionNumber).replaceAll("!", "\\."));
        }
    }

    public void actionExportAllToExcel_actionPerformed(ActionEvent e)
        throws Exception
    {
        ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp", ".xls");
        path = tempFile.getCanonicalPath();
        KDTables2KDSBookVO tablesVO[] = new KDTables2KDSBookVO[tables.size()];
        for(int i = 0; i < tables.size(); i++)
        {
            tablesVO[i] = new KDTables2KDSBookVO((KDTable)tables.get(i));
            String title = plTables.getTitleAt(i);
            title = title.replaceAll("[{\\\\}{\\*}{\\?}{\\[}{\\]}{\\/}]", "|");
            tablesVO[i].setTableName(title);
        }

        com.kingdee.bos.ctrl.kdf.kds.KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO, true, true);
        exportM.exportToExcel(book, path);
        try
        {
            KDTMenuManager.openFileInExcel(path);
            tempFile.deleteOnExit();
        }
        catch(IOException e2)
        {
            KDFileChooser fileChooser = new KDFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if(result == 0)
            {
                File dest = fileChooser.getSelectedFile();
                try
                {
                    File src = new File(path);
                    if(dest.exists())
                        dest.delete();
                    src.renameTo(dest);
                }
                catch(Exception e3)
                {
                    handUIException(e3);
                }
            }
        }
    }

    public void onShow()
        throws Exception
    {
        setQueryPreference(false);
        super.onShow();
        FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {

            public void run()
            {
                refreshAllMeasureTable();
                setDataChange(false);
            }
        });
        if(!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT))
        {
            for(int i = 0; i < tables.size(); i++)
            {
                if(i == 2)
                    continue;
                ((KDTable)tables.get(i)).getStyleAttributes().setLocked(true);
                if(i <= 3)
                    continue;
                ICellEditor editor = ((KDTable)tables.get(i)).getColumn("workload").getEditor();
                if(editor != null && editor.getComponent() != null)
                    editor.getComponent().setEnabled(false);
                editor = ((KDTable)tables.get(i)).getColumn("sumPrice").getEditor();
                if(editor != null && editor.getComponent() != null)
                    editor.getComponent().setEnabled(false);
            }

            actionAddRow.setEnabled(false);
            actionDeleteRow.setEnabled(false);
        }
        prmtProject.setEnabled(false);
    }

    private void handleVersion(MeasureCostInfo info)
    {
        if(info.getId() == null)
            return;
        if(info.getSourceBillId() == null)
            info.setSourceBillId(info.getId().toString());
        info.setSrcMeasureCostId(info.getId().toString());
        info.setId(null);
        info.setIsLastVersion(false);
        info.setState(FDCBillStateEnum.SAVED);
        UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
        java.sql.Timestamp timeStamp = null;
        try
        {
            timeStamp = FDCCommonServerHelper.getServerTimeStamp();
        }
        catch(BOSException e)
        {
            handUIException(e);
        }
        info.setCreateTime(timeStamp);
        info.setLastUpdateTime(timeStamp);
        info.setCreator(user);
        info.setLastUpdateUser(user);
        for(Iterator iter = info.getCostEntry().iterator(); iter.hasNext(); ((MeasureEntryInfo)iter.next()).setId(null));
        for(Iterator iter = info.getConstrEntrys().iterator(); iter.hasNext(); ((ConstructPlanIndexEntryInfo)iter.next()).setId(null));
        PlanIndexInfo planIndex = (PlanIndexInfo)editData.get("PlanIndex");
        if(planIndex == null)
            return;
        planIndex.setId(null);
        for(Iterator iter = planIndex.getEntrys().iterator(); iter.hasNext(); ((PlanIndexEntryInfo)iter.next()).setId(null));
        for(Iterator iter = planIndex.getCustomEntrys().iterator(); iter.hasNext(); ((CustomPlanIndexEntryInfo)iter.next()).setId(null));
        
        for (Iterator iter = info.getTargetCostSplit().iterator(); iter.hasNext();) {
			((TargetCostSplitInfo) iter.next()).setId(null);
		}
    }

    void setDataChange(boolean hasChange)
    {
        hasChanged = hasChange;
    }

    public boolean isModify()
    {
        if(OprtState.VIEW.equals(getOprtState()))
            return false;
        else
            return hasChanged;
    }

    private FDCBillInfo getFDCBillInfo()
    {
        return (FDCBillInfo)editData;
    }

    private PlanIndexInfo getInitPlanIndexInfo()
        throws BOSException
    {
        if(editData == null)
            return null;
        if(editData.get("PlanIndex") == null && editData.getId() != null)
        {
            EntityViewInfo view = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            view.setFilter(filter);
            filter.appendFilterItem("headID", editData.getId().toString());
            SelectorItemCollection selector = view.getSelector();
            selector.add("*");
            selector.add("entrys.*");
            selector.add("entrys.product.*");
            selector.add("customEntrys.*");
            selector.add("customEntrys.productType.id");
            selector.add("customEntrys.productType.number");
            selector.add("customEntrys.productType.name");
            selector.add("customEntrys.apportType.id");
            selector.add("customEntrys.apportType.number");
            selector.add("customEntrys.apportType.name");
            selector.add("customEntrys.value");
            view.getSorter().add(new SorterItemInfo("entrys.type"));
            view.getSorter().add(new SorterItemInfo("entrys.index"));
            PlanIndexCollection planIndexCollection = PlanIndexFactory.getRemoteInstance().getPlanIndexCollection(view);
            if(planIndexCollection.size() == 1)
                editData.put("PlanIndex", planIndexCollection.get(0));
        }
        return (PlanIndexInfo)editData.get("PlanIndex");
    }

    public boolean isAimMeasure()
    {
        Boolean isAimMeasure = (Boolean)getUIContext().get("isAimMeasure");
        if(isAimMeasure != null)
            return isAimMeasure.booleanValue();
        else
            return false;
    }

    private void addTotalRow(KDTable table)
    {
        try
        {
            AimCostClientHelper.setTotalCostRow(table, new String[] {
                "sumPrice", "buildPart", "sellPart", "adjustAmt", "amount"
            });
        }
        catch(Exception e)
        {
            handUIException(e);
        }
    }

    private boolean isDetailAcctRow(IRow row)
    {
        return row != null && row.getCell(0).getUserObject() != null && row.getCell(0).getUserObject().equals("DetailInput");
    }

    public void setDetailAcctRowNull(IRow row)
    {
        if(row != null && row.getCell(0).getUserObject() != null)
            row.getCell(0).setUserObject(null);
    }

    private void setDetailAcctRow(IRow row)
    {
        if(row != null && row.getCell(0) != null)
            row.getCell(0).setUserObject("DetailInput");
        if(row != null && row.getCell("acctName") != null)
        {
            row.getCell("acctName").getStyleAttributes().setLocked(true);
            row.getCell("acctName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
        }
    }

    private void setDetailAcctHashInput(IRow row)
    {
        if(row != null && row.getCell(1) != null)
            row.getCell(1).setUserObject("HasInput");
    }

    private boolean isDetailAcctHasInput(IRow row)
    {
        return row != null && row.getCell(1).getUserObject() != null && row.getCell(1).getUserObject().equals("HasInput");
    }

    public void setDetailAcctHasNotInput(IRow row)
    {
        if(row != null && row.getCell(1) != null)
            row.getCell(1).setUserObject(null);
    }

    public void clearDetailAcctRow(KDTable table, IRow row)
    {
        for(int i = 3; i < table.getColumnCount(); i++)
            row.getCell(i).setValue(null);

    }

    private void setTemplateMeasureCostF7Editor(final KDTable table)
    {
        KDBizPromptBox myPrmtBox = new KDBizPromptBox() {

            protected Object stringToValue(String t)
            {
                Object obj = super.stringToValue(t);
                if(obj != null)
                    return FDCHelper.toBigDecimal(t, 4);
                else
                    return obj;
            }
        };
        myPrmtBox.addSelectorListener(new SelectorListener() {

            public void willShow(SelectorEvent e)
            {
                IRow row = KDTableUtil.getSelectedRow(table);
                int colIdx = table.getSelectManager().getActiveColumnIndex();
                boolean isPrice = true;
                if(table.getColumnIndex("price") == colIdx)
                    isPrice = true;
                else
                    isPrice = false;
                Object obj = row.getUserObject();
                CostAccountInfo acct;
                if(obj instanceof MeasureEntryInfo)
                    acct = ((MeasureEntryInfo)obj).getCostAccount();
                else
                    return;
                String acctLongNumber = acct.getLongNumber();
                String projectTypeId = null;
                String productId = null;
                Object product = table.getHeadRow(0).getUserObject();
                if(product instanceof ProductTypeInfo)
                    productId = ((ProductTypeInfo)product).getId().toString();
                if(acct.getType() == CostAccountTypeEnum.SIX)
                {
                    Object v = prmtProjectType.getValue();
                    if(v instanceof ProjectTypeInfo)
                    {
                        projectTypeId = ((ProjectTypeInfo)v).getId().toString();
                    } else
                    {
                        MsgBox.showWarning(getplTables(), "六类公摊必须先设置项目系列");
                        e.setCanceled(true);
                        return;
                    }
                }
                TemplateMeasureCostPromptBox selector = getTemplateMeasureCostPromptBox();
                selector.setAcctLongNumber(acctLongNumber);
                selector.setProjectTypeID(projectTypeId);
                selector.setProductId(productId);
                selector.setIsPrice(isPrice);
                if(!isPrice)
                {
                    if(row.getCell("indexName").getValue() instanceof Item)
                    {
                        String key = ((Item)row.getCell("indexName").getValue()).key;
                        if(key == null || key.equals("empty"))
                        {
                            MsgBox.showWarning(getplTables(), "请先选择指标");
                            e.setCanceled(true);
                            return;
                        }
                        selector.setIndex(key);
                    } else
                    {
                        MsgBox.showWarning(getplTables(), "请先选择指标");
                        e.setCanceled(true);
                        return;
                    }
                } else
                {
                    selector.setIndex(null);
                }
                ((KDBizPromptBox)e.getSource()).setSelector(selector);
            }
        });
        ICellEditor f7Editor = new KDTDefaultCellEditor(myPrmtBox);
        table.getColumn("price").setEditor(f7Editor);
        table.getColumn("coefficient").setEditor(f7Editor);
    }

    private TemplateMeasureCostPromptBox getTemplateMeasureCostPromptBox()
    {
        if(selector == null)
        {
            String orgId = (String)getUIContext().get("orgId");
            Boolean objBoolean = (Boolean)getUIContext().get("isAimMeasure");
            boolean isAimMeasure = true;
            if(objBoolean != null)
                isAimMeasure = objBoolean.booleanValue();
            selector = new TemplateMeasureCostPromptBox(this, isAimMeasure, orgId);
        }
        return selector;
    }

    protected MeasureCostInfo getEditData()
    {
        return (MeasureCostInfo)editData;
    }

    private HashMap getParams()
    {
        if(params == null)
        {
            HashMap hmParamIn = new HashMap();
            hmParamIn.put("FDC5001002_MEASUREADJUST", null);
            hmParamIn.put("FDC5001003_MEASUREQUALITY", null);
            hmParamIn.put("FDC5001004_USECOSTOMINDEX", null);
            hmParamIn.put("FDC5001007_PLANINDXLOGIC", null);
            hmParamIn.put("FDC5001008_BUILDPARTLOGIC", null);
            hmParamIn.put("FDC5013_ISINCOMEJOINCOST", null);
            hmParamIn.put("FDC5001010_MEASUREINDEX", null);
            try
            {
                HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
                params = hmAllParam;
            }
            catch(Exception e)
            {
                handUIException(e);
            }
        }
        if(params == null)
            params = new HashMap();
        return params;
    }

    private boolean isMeasureIndex()
    {
        Object theValue = getParams().get("FDC5001010_MEASUREINDEX");
        if(theValue != null)
            return Boolean.valueOf(theValue.toString()).booleanValue();
        else
            return false;
    }

    private boolean isUseAdjustCoefficient()
    {
        Object theValue = getParams().get("FDC5001002_MEASUREADJUST");
        if(theValue != null)
            return Boolean.valueOf(theValue.toString()).booleanValue();
        else
            return false;
    }

    private boolean isUseQuality()
    {
        Object theValue = getParams().get("FDC5001003_MEASUREQUALITY");
        if(theValue != null)
            return Boolean.valueOf(theValue.toString()).booleanValue();
        else
            return false;
    }

    protected boolean isUseCustomIndex()
    {
        Object theValue = getParams().get("FDC5001004_USECOSTOMINDEX");
        if(theValue != null)
            return Boolean.valueOf(theValue.toString()).booleanValue();
        else
            return false;
    }

    protected boolean isPlanIndexLogic()
    {
        Object theValue = getParams().get("FDC5001007_PLANINDXLOGIC");
        if(theValue != null)
            return Boolean.valueOf(theValue.toString()).booleanValue();
        else
            return false;
    }

    protected boolean isBuildPartLogic()
    {
        Object theValue = getParams().get("FDC5001008_BUILDPARTLOGIC");
        if(theValue != null)
            return Boolean.valueOf(theValue.toString()).booleanValue();
        else
            return false;
    }

    protected boolean isIncomeJoinCost()
    {
        Object theValue = getParams().get("FDC5013_ISINCOMEJOINCOST");
        if(theValue != null)
            return Boolean.valueOf(theValue.toString()).booleanValue();
        else
            return false;
    }

    private Object[] getSixItems()
    {
        SIXITEMS = null;
        if(SIXITEMS == null)
        {
            CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs(null);
            if(customPlanIndexs.size() == 0)
            {
                SIXITEMS = Item.SIXITEMS;
            } else
            {
                java.util.List list = new ArrayList();
                Set appSet = new HashSet();
                int i;
                for(i = 0; i < customPlanIndexs.size(); i++)
                {
                    String appId = customPlanIndexs.get(i).getApportType().getId().toString();
                    if(!appSet.contains(appId))
                    {
                        appSet.add(appId);
                        list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
                    }
                }

                SIXITEMS = new Item[list.size() + Item.SIXITEMS.length];
                System.arraycopy(Item.SIXITEMS, 0, ((Object) (SIXITEMS)), 0, Item.SIXITEMS.length);
                i = Item.SIXITEMS.length;
                for(Iterator iter = list.iterator(); iter.hasNext();)
                    SIXITEMS[i++] = iter.next();

            }
        }
        return SIXITEMS;
    }

    public BigDecimal getCustomIndexValue(IObjectValue info, Item item)
    {
        if(item.isCustomIndex())
        {
            CustomPlanIndexEntryInfo customEntry = null;
            if(info instanceof PlanIndexInfo)
                customEntry = planIndexTable.getCustomPlanIndexEntryInfo(item.key, null);
            if(info instanceof PlanIndexEntryInfo)
            {
                PlanIndexEntryInfo entry = (PlanIndexEntryInfo)info;
                if(entry.getProduct() == null)
                    return FDCHelper.ZERO;
                String productId = entry.getProduct().getId().toString();
                customEntry = planIndexTable.getCustomPlanIndexEntryInfo(item.key, productId);
            }
            if(customEntry != null)
                return customEntry.getValue();
            else
                return null;
        } else
        {
            return info.getBigDecimal(item.key);
        }
    }

    private void handleAimCostAccredit(MeasureCostInfo measureCost)
        throws BOSException
    {
        if(!AcctAccreditHelper.hasUsed(null) || accreditSet == null || accreditSet.size() == 0)
        {
            measureCost.getCostEntry().clear();
            return;
        }
        for(int i = measureCost.getCostEntry().size() - 1; i >= 0; i--)
        {
            MeasureEntryInfo entry = measureCost.getCostEntry().get(i);
            if(entry == null || entry.getCostAccount() == null)
                continue;
            if(accreditSet.contains(entry.getCostAccount().getId().toString()))
                measureCost.getCostEntry().remove(entry);
            else
                entry.setId(null);
        }

    }

    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        MeasureCostInfo info = (MeasureCostInfo)editData;
        actionSubmit.setEnabled(true);
        actionSave.setEnabled(true);
        if("ADDNEW".equals(oprtState))
        {
            actionSubmit.setEnabled(true);
            actionSave.setEnabled(true);
        } else
        if("EDIT".equals(oprtState))
        {
            actionSave.setEnabled(true);
            actionSubmit.setEnabled(true);
            if(info != null && info.getState() == FDCBillStateEnum.SUBMITTED)
            {
                actionSubmit.setEnabled(true);
                actionSave.setEnabled(false);
                btnSave.setEnabled(false);
            } else
            if(info != null && info.getState() == FDCBillStateEnum.SAVED)
                actionSubmit.setEnabled(true);
        } else
        if("VIEW".equals(oprtState))
        {
            actionSave.setEnabled(false);
            actionSubmit.setEnabled(false);
        }
    }

    public void beforeActionPerformed(ActionEvent e)
    {
        super.beforeActionPerformed(e);
        String action = e.getActionCommand();
        if(action == null || action.length() == 0 || action.indexOf('$') == -1)
        {
            return;
        } else
        {
            handlePermissionForEachItemAction(action);
            return;
        }
    }

    private String getPermItemNameByAction(String actionName)
    {
        String permItemName = "ActionOnLoad";
        boolean isAimMeasure = true;
        if(getUIContext().get("isAimMeasure") != null)
            isAimMeasure = ((Boolean)getUIContext().get("isAimMeasure")).booleanValue();
        if("ActionOnLoad".endsWith(actionName))
        {
            if(isAimMeasure)
                permItemName = "costdb_aimcost_cesuan_view";
            else
                permItemName = "aim_measureCost_view";
        } else
        if("ActionAddRow".endsWith(actionName) || "ActionDeleteRow".endsWith(actionName) || "ActionSave".endsWith(actionName) || "ActionSubmit".endsWith(actionName))
        {
            if(isAimMeasure)
                permItemName = "costdb_aimcost_cesuan_edit";
            else
                permItemName = "aim_measureCost_edit";
        } else
        if("ActionImportTemplate".endsWith(actionName))
        {
            if(isAimMeasure)
                permItemName = "costdb_aimcost_useTemp";
            else
                permItemName = "aim_measureCost_useTemp";
        } else
        if("ActionPrint".endsWith(actionName) || "ActionPrintPreview".endsWith(actionName))
        {
            if(isAimMeasure)
                permItemName = "costdb_aimcost_cesuan_print";
            else
                permItemName = "aim_measureCost_print";
        } else
        if(isAimMeasure)
            permItemName = "costdb_aimcost_cesuan_view";
        else
            permItemName = "aim_measureCost_view";
        return permItemName;
    }

    private void handlePermissionForEachItemAction(String actionName)
    {
        if(actionName == null || actionName.length() == 0 || actionName.indexOf('$') == -1)
            return;
        int index = actionName.indexOf('$');
        actionName = actionName.substring(index + 1, actionName.length());
        try
        {
            PermissionHelper.checkFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()), new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), getPermItemNameByAction(actionName));
        }
        catch(EASBizException e)
        {
            handUIExceptionAndAbort(e);
        }
        catch(BOSException e)
        {
            handUIExceptionAndAbort(e);
        }
    }

    protected void handlePermissionForItemAction(ItemAction action)
    {
        String actionName = action.getActionName();
        handlePermissionForEachItemAction(actionName);
    }

    public void actionImportData_actionPerformed(ActionEvent e)
        throws Exception
    {
        if(editData == null || editData.getId() == null)
        {
            FDCMsgBox.showConfirm2(this, "请先保存目标成本测算！");
            abort();
        }
        DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        if(getImportParam() != null)
            task.invoke(getImportParam(), 0, true, true);
        isFirstLoad = true;
        IObjectPK pk = new ObjectUuidPK(editData.getId());
        setDataObject(getValue(pk));
        loadFields();
    }

    protected ArrayList getImportParam()
    {
        DatataskParameter param = new DatataskParameter();
        Hashtable hs = new Hashtable();
        hs.put("editData", editData);
        editData.put("planIndex", planIndexTable.getPlanIndexInfo());
        if(editData.get("project") != null)
        {
            String prjId = ((CurProjectInfo)editData.get("project")).getId().toString();
            SelectorItemCollection sic = new SelectorItemCollection();
            sic.add(new SelectorItemInfo("*"));
            sic.add(new SelectorItemInfo("curProjProductEntries.*"));
            sic.add(new SelectorItemInfo("curProjProductEntries.curProjProEntrApporData"));
            sic.add(new SelectorItemInfo("curProjCostEntries.*"));
            CurProjectInfo paramProject = new CurProjectInfo();
            try
            {
                paramProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(prjId), sic);
                hs.put("project", paramProject);
            }
            catch(EASBizException e)
            {
                e.printStackTrace();
            }
            catch(BOSException e)
            {
                e.printStackTrace();
            }
        }
        param.setContextParam(hs);
        param.solutionName = getSolutionName();
        param.alias = getDatataskAlias();
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        return paramList;
    }

    protected String getSolutionName()
    {
        return "eas.fdc.costmanager.MeasureCost";
    }

    protected String getDatataskAlias()
    {
        return "目标成本测算";
    }

    public static class Item
    {

        public static Item getCustomItem(ApportionTypeInfo info)
        {
            if(hashMap == null)
                hashMap = new HashMap();
            Item item = (Item)hashMap.get(info.getId());
            if(item == null)
                item = new Item(info);
            hashMap.put(info.getId(), item);
            return item;
        }

        public boolean isCustomIndex()
        {
            return isCustom;
        }

        public String toString()
        {
            return name;
        }

        public int hashCode()
        {
            return super.hashCode();
        }

        String key;
        String name;
        String productId;
        BigDecimal sellArea;
        boolean isCustom;
        private static Map hashMap = null;
        public static Item[] SIXITEMS = { new Item("empty", ""), new Item("totalContainArea", "总占地面积"), new Item("buildArea", "建筑用地面积"), new Item("totalBuildArea", "总建筑面积"), new Item("buildContainArea", "建筑物占地面积"), new Item("cubageRateArea", "计容积率面积"), new Item("totalRoadArea", "道路用地合计"), new Item("totalGreenArea", "绿化用地合计\t"), new Item("pitchRoad", "沥青路面车行道"), new Item("concreteRoad", "砼路面车行道（停车场）"), new Item("hardRoad", "硬质铺装车行道"), new Item("hardSquare", "硬质铺装广场\t"), new Item("hardManRoad", "硬质铺装人行道"), new Item("importPubGreenArea", "重要公共绿地\t"), new Item("houseGreenArea", "组团宅间绿化\t"), new Item("privateGarden", "底层私家花园\t"), new Item("warterViewArea", "水景面积"), new Item("viewArea", "景观面积") };
        public static Item[] PRODUCTITEMS = { new Item("empty", ""), new Item("containArea", "占地面积"), new Item("buildArea", "建筑面积"), new Item("sellArea", "可售面积"), new Item("cubageRate", "容积率"), new Item("productRate", "产品比例"), new Item("unitArea", "平均每户面积"), new Item("units", "单元数"), new Item("doors", "户数") };
        public static Item[] SPLITITEMS = { new Item("man", "指定分摊"), new Item("buildArea", "建筑面积"), new Item("sellArea", "可售面积"), new Item("containArea", "占地面积"), new Item("cubageRate", "容积率"), new Item("productRate", "产品比例"), new Item("unitArea", "平均每户面积"), new Item("units", "单元数"), new Item("doors", "户数\t") };

        Item(String key, String name)
        {
            this.key = null;
            this.name = null;
            productId = null;
            sellArea = null;
            isCustom = false;
            this.key = key;
            this.name = name;
        }

        private Item(ApportionTypeInfo info)
        {
            key = null;
            name = null;
            productId = null;
            sellArea = null;
            isCustom = false;
            key = info.getId().toString();
            name = info.getName();
            isCustom = true;
        }
    }
    
    protected class ActionSplit extends ItemAction {

		public ActionSplit() {
			this(null);
		}

		public ActionSplit(IUIObject uiObject) {
			super(uiObject);

			String _tempStr = null;
//			_tempStr = resHelper.getString("ActionSplit.SHORT_DESCRIPTION");
			this.putValue(ItemAction.SHORT_DESCRIPTION, "分摊");
//			_tempStr = resHelper.getString("ActionSplit.LONG_DESCRIPTION");
			this.putValue(ItemAction.LONG_DESCRIPTION, "分摊");
//			_tempStr = resHelper.getString("ActionSplit.NAME");
			this.putValue(ItemAction.NAME, "split");
		}

		public void actionPerformed(ActionEvent e) {
			getUIContext().put("ORG.PK", getOrgPK(this));
			innerActionPerformed("eas", AimMeasureCostEditUICTEx.this, "ActionSplit", "actionSplit_actionPerformed", e);
		}
	}
}