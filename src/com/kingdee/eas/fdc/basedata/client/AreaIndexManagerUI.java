/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.ApportionTypesEnum;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexVerTypeEnum;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class AreaIndexManagerUI extends AbstractAreaIndexManagerUI
{
    private static final Logger logger = CoreUIObject.getLogger(AreaIndexManagerUI.class);
    private static final Map dataMap=new HashMap();
    private static Map changeData=null;
    private int count = 0;
    private Set<String> set = new HashSet<String>();
	
    /**
     * output class constructor
     */
    public AreaIndexManagerUI() throws Exception
    {
        super();
    }
    
    private Map getChangeData(){
    	if(changeData==null){
    		changeData=new HashMap();
    	}
    	return changeData;  
           
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    protected String getSelectedKeyValue() {
//    	没有什么用处随便返回一个BOSID即可
    	return "DaUAVQEQEADgBZAaCgALC/nl6Ss=";
//    	return super.getSelectedKeyValue();
    }
    protected String getKeyFieldName() {
    	return "productType";
    }
    /**
     * output treeProject_valueChanged method
     */
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	dataChangeTip();
    	super.treeProject_valueChanged(e);
        loadData();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	getMainTable().getHeadRow(0).getCell(0).setValue("");
    	getMainTable().getHeadRow(0).getCell(1).setValue("");
		getMainTable().getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	getMainTable().getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    	for(int i=2;i<getMainTable().getColumnCount();i++){
    		FDCHelper.formatTableNumber(getMainTable(), getMainTable().getColumnKey(i));
    	}
    	((KDTTransferAction)getMainTable().getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
    	((KDTTransferAction)getMainTable().getActionMap().get(KDTAction.CUT)).setPasteMode(KDTEditHelper.VALUE);
    	((KDTTransferAction)getMainTable().getActionMap().get(KDTAction.COPY)).setPasteMode(KDTEditHelper.VALUE);
    	initCtrlLisener();
    	FDCTableHelper.setCutCopyPaseMode(getMainTable(), KDTEditHelper.VALUE);
    }
    private void initCtrlLisener(){
    	getMainTable().addKDTPropertyChangeListener(new KDTPropertyChangeListener(){
    		public void propertyChange(KDTPropertyChangeEvent evt) {
    			tblMain_ValueChange(evt);
    		}
    	});
    }

    public void onShow() throws Exception {
    	super.onShow();
    	actionAudit.setEnabled(false);
    	actionAudit.setVisible(false);
    	actionUnAudit.setEnabled(false);
    	actionUnAudit.setVisible(false);
    	actionLocate.setEnabled(false);
    	actionLocate.setVisible(false);
    	actionView.setEnabled(false);
    	actionView.setVisible(false);
    	actionWorkFlowG.setEnabled(false);
    	actionWorkFlowG.setVisible(false);
    	actionAttachment.setEnabled(false);
    	actionAttachment.setVisible(false);
    	menuWorkFlow.setVisible(false);
    	menuBiz.setVisible(false);
    	actionAddNew.setEnabled(false);
    	actionAddNew.setVisible(false);
    	menuEdit.setEnabled(false);
    	menuEdit.setVisible(false);
    	menuItemEdit.setEnabled(false);
    	menuItemEdit.setVisible(false);
    	menuItemRemove.setEnabled(false);
    	menuItemRemove.setVisible(false);
    	btnEdit.setEnabled(false);
    	btnEdit.setVisible(false);
    	btnRemove.setEnabled(false);
    	btnRemove.setVisible(false);
    	btnAuditResult.setVisible(false);
    	btnAuditResult.setEnabled(false);
    	if(cantEdit()){
    		actionSave.setEnabled(false);
    	}
    	
    	//biaobiao
    	set.add(ApportionTypesEnum.BYBUILDAREA.toString());
    	set.add(ApportionTypesEnum.BYSELLAREA.toString());
    	set.add(ApportionTypesEnum.BYCONTAINAREA.toString());
    	set.add(ApportionTypesEnum.BYCUSTOM.toString());
//    	String[] srt ={ProjectStageEnum.RESEARCH_VALUE,ProjectStageEnum.AIMCOST_VALUE,ProjectStageEnum.DYNCOST_VALUE};
    	List list = new ArrayList();
    	list.add(ProjectStageEnum.RESEARCH_VALUE);
    	list.add(ProjectStageEnum.AIMCOST_VALUE);
    	list.add(ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE);
    	list.add(ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE);
    	EntityViewInfo viewInfo = new EntityViewInfo();
    	FilterInfo filterInfo = new FilterInfo();
    	filterInfo.getFilterItems().add(new FilterItemInfo("name",set,CompareType.NOTINCLUDE));
    	filterInfo.getFilterItems().add(new FilterItemInfo("isEnabled",true,CompareType.EQUALS));
    	filterInfo.getFilterItems().add(new FilterItemInfo("forCostApportion",true,CompareType.EQUALS));
    	filterInfo.getFilterItems().add(new FilterItemInfo("id","qHQt0wEMEADgAAaOoKgTuzW0boA=",CompareType.NOTEQUALS));
    	viewInfo.setFilter(filterInfo);
    	ApportionTypeCollection typeCollection = ApportionTypeFactory.getRemoteInstance().getApportionTypeCollection(viewInfo);
    	String[] headName = {"contain_res","contain_aim","contain_dyn1","contain_dyn2"} ;//biaobiao
    	for (int i = 0; i < headName.length; i++) {
    		int columnIndex = getMainTable().getColumn(headName[i]).getColumnIndex()+1;
    		for (int j = 0; j < typeCollection.size(); j++) {
        		ApportionTypeInfo typeInfo = typeCollection.get(j);
        		IColumn addColumn5 = getMainTable().addColumn(columnIndex+j);
    	    	getMainTable().getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
    	    	addColumn5.setKey(list.get(i)+typeInfo.getId().toString());
    	    	if(i == 0){
    	    		getMainTable().getHeadMergeManager().mergeBlock(0, 2, 0, columnIndex+j, KDTMergeManager.SPECIFY_MERGE);
    	    	}else{
    	    		int Index = getMainTable().getColumn(headName[i-1]).getColumnIndex()+1;
    	    		getMainTable().getHeadMergeManager().mergeBlock(0, Index+typeCollection.size(), 0, columnIndex+j, KDTMergeManager.SPECIFY_MERGE);
    	    	}
    	    	getMainTable().getHeadRow(1).getCell(list.get(i)+typeInfo.getId().toString()).setValue(typeInfo.getName());
    	    	getMainTable().getColumn(list.get(i)+typeInfo.getId().toString()).getStyleAttributes().setNumberFormat("%r-{#,##0.00*}f");
    		}
		}
    	
    
    	
    }
    protected void initWorkButton() {
    	super.initWorkButton();
    	actionSave.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl s"));
    	actionSave.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_save"));
    	actionSave.setEnabled(true);
    }
	protected ICoreBase getRemoteInterface() throws BOSException {
		return ProjectIndexDataFactory.getRemoteInstance();
	}
	protected void audit(List ids) throws Exception {
	}
	protected void unAudit(List ids) throws Exception {
	}
	
	public boolean destroyWindow() {
		dataChangeTip();
		dataMap.clear();
		count = 0;
		if(changeData!=null){
			changeData.clear();
		}
		return super.destroyWindow();
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		save();
	}
	private boolean isModify=false;
	private boolean isModify(){
//		isModify=true;
		if(cantEdit()){
			return false;
		}
		return isModify;
	}
	
    private void tblMain_ValueChange(KDTPropertyChangeEvent e){
    	if(e.getType()!= KDTStyleConstants.BODY_ROW){
    		return;
    	}
		BigDecimal oldValue = (BigDecimal)e.getOldValue();
		BigDecimal newValue=(BigDecimal)e.getNewValue();
		if(oldValue==null&&newValue==null){
			return;
		}
		if(oldValue!=null&&newValue!=null&&oldValue.compareTo(newValue)==0){
			return;
		}
		final int rowIndex = e.getRowIndex();
		final int colIndex = e.getColIndex();
		IRow row=getMainTable().getRow(rowIndex);
		if(!(row.getUserObject() instanceof ProductTypeInfo) &&!(row.getUserObject() instanceof CurProjectInfo)){
			return;
		}
		final String colKey=getMainTable().getColumnKey(colIndex);
		Map map = getChangeData();
		String key="";//key=项目ID+[产品ID]+指标+版本名
		Object objs[]=new Object[5];
		Object obj = dataMap.get("curProject");
		if(obj instanceof CurProjectInfo){
			key+=((CurProjectInfo)obj).getId().toString();
			objs[0]=obj;
		}else{
			return;
		}
		obj=row.getUserObject();
		if(obj instanceof ProductTypeInfo){
			key+=((ProductTypeInfo)obj).getId().toString();
			objs[1]=obj;
		}
		if(colKey.indexOf("build")>-1){
			objs[2]=ApportionTypeInfo.buildAreaType;
		}
		if(colKey.indexOf("sell")>-1){
			objs[2]=ApportionTypeInfo.sellAreaType;
			if(obj instanceof ProductTypeInfo&&prodSetMap.containsKey(((ProductTypeInfo)obj).getId().toString())){
				BigDecimal settleAmt = FDCHelper.toBigDecimal(prodSetMap.get(((ProductTypeInfo)obj).getId().toString()),2);
				if(settleAmt.compareTo(FDCHelper.toBigDecimal(newValue,2))>0){
					MsgBox.showWarning(this, "该产品已经部分结算，可售面积不能小于竣工结算的面积!");
					EventListener[] listeners = getMainTable().getListeners(KDTPropertyChangeListener.class);
					for(int i=listeners.length-1;i>=0;i--){
						getMainTable().removeKDTPropertyChangeListener((KDTPropertyChangeListener)listeners[i]);
					}
					row.getCell(colIndex).setValue(oldValue);
					for(int i=listeners.length-1;i>=0;i--){
						getMainTable().addKDTPropertyChangeListener((KDTPropertyChangeListener)listeners[i]);
					}
					listeners=null;
//					this.abort();
				}
			}
		}
		if(colKey.indexOf("contain")>-1){
			objs[2]=ApportionTypeInfo.placeAreaType;
		}
		
		//biaobiao
		if(colKey.indexOf(ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE)>-1){
			objs[2]=colKey.substring((ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE).length());
		}
		
		if(colKey.indexOf(ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE)>-1){
			objs[2]=colKey.substring((ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE).length());
		}
		
		if(colKey.indexOf(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE)>-1){
			objs[3]=ProjectIndexVerTypeEnum.PRESELLAREA_VALUE;
		}
		
		if(colKey.indexOf(ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE)>-1){
			objs[3]=ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE;
			//删除PRESELLAREA_VALUE改变了的数据
			String appid = colKey.substring((ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE).length());
			map.remove(key+appid+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE);
		}
		
		if(colKey.endsWith("dyn1")){
			objs[3]=ProjectIndexVerTypeEnum.PRESELLAREA_VALUE;
		}
		if(colKey.endsWith("dyn2")){
			objs[3]=ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE;
			//删除PRESELLAREA_VALUE改变了的数据
			map.remove(key+ApportionTypeInfo.buildAreaType+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE);
			map.remove(key+ApportionTypeInfo.sellAreaType+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE);
			map.remove(key+ApportionTypeInfo.placeAreaType+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE);
			
		}
		key=key+objs[2]+objs[3];
		objs[4]=newValue;
		
		map.put(key, objs);
		isModify=true;
		if(row.getUserObject() instanceof ProductTypeInfo){
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					setUnion();
					updatePrjArea(colIndex, colKey);
				}
			});

		}
		
    }
    
    @Override
    protected void initTable() {
    	super.initTable();
    }
    
    private void updatePrjArea(final int colIndex, final String colKey) {
		
		//占地面积，可售面积要汇总到产品
		BigDecimal total=FDCHelper.ZERO;
		if(colKey.indexOf("contain")>-1){
			return;
		}
		String verName=ProjectIndexVerTypeEnum.PRESELLAREA_VALUE;
		if(colKey.indexOf("dyn2")>-1){
			verName=ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE;
		}
		
		//biaobiao
		if(colKey.indexOf(ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE)>-1){
			verName=ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE;
		}
		
		if(colKey.indexOf("build")>-1){
			String myKey=ApportionTypeInfo.buildAreaType+"dif";
			if(colKey.indexOf("dyn1")>-1){
				//预售查丈建筑面积如果没有则取目标成本的
				if(dataMap.get(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+myKey)==null){
					dataMap.put(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+myKey, dataMap.get(ProjectIndexVerTypeEnum.AIMCOSTAREA_VALUE+myKey));
				}
				myKey=ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+myKey;
			}else{
				//峻工查丈建筑面积如果没有则取预售查丈的,否则取目标成本
				if(dataMap.get(ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE+myKey)==null){
					if(dataMap.get(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+myKey)==null){
						dataMap.put(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+myKey, dataMap.get(ProjectIndexVerTypeEnum.AIMCOSTAREA_VALUE+myKey));
					}else{
						dataMap.put(ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE+myKey, dataMap.get(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+myKey));
					}
				}
				
				myKey=ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE+myKey;
			}
			BigDecimal dif=FDCHelper.toBigDecimal(dataMap.get(myKey));
			for(int i=0;i<getMainTable().getRowCount()-3;i++){
				Object value = getMainTable().getCell(i, colIndex).getValue();
				if(value==null){
					//建筑面积的汇总，如果该行产品没有数据，则取其上一个版本的数据
					ProductTypeInfo prod = (ProductTypeInfo)getMainTable().getRow(i).getUserObject();
					if(prod==null) continue;
					String key=prod.getId().toString()+verName;
					if(dataMap.get(key)==null){
						if(verName.equals(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE)){
							//取目标成本指标
							value=dataMap.get(prod.getId().toString()+getKey("build_aim"));
						}else{
							if(dataMap.get(prod.getId().toString()+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE)!=null){
								value=dataMap.get(prod.getId().toString()+getKey("build_dy1"));
							}else{
								value=dataMap.get(prod.getId().toString()+getKey("build_aim"));
							}
						}
					}
				}
				total=total.add(FDCHelper.toBigDecimal(value));
			}
			total=total.add(dif);
			
		}
		if(colKey.indexOf("sell")>-1){
			for(int i=0;i<getMainTable().getRowCount()-3;i++){
				Boolean isSplit=(Boolean)getMainTable().getCell(i, 1).getValue();
				if(isSplit!=null&&isSplit.booleanValue()){
					Object value = getMainTable().getCell(i, colIndex).getValue();
					if(value==null){
						//可售的汇总，如果该行产品没有数据，则取其上一个版本的数据
						ProductTypeInfo prod = (ProductTypeInfo)getMainTable().getRow(i).getUserObject();
						if(prod==null) continue;
						String key=prod.getId().toString()+verName;
						if(dataMap.get(key)==null){
							if(verName.equals(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE)){
								//取目标成本指标
								value=dataMap.get(prod.getId().toString()+getKey("sell_aim"));
							}else{
								if(dataMap.get(prod.getId().toString()+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE)!=null){
									value=dataMap.get(prod.getId().toString()+getKey("sell_dy1"));
								}else{
									value=dataMap.get(prod.getId().toString()+getKey("sell_aim"));
								}
							}
						}
					}
					total=total.add(FDCHelper.toBigDecimal(value));
				}
			}
		}
		
		//biaobiao
		if(colKey.indexOf(ProjectStageEnum.DYNCOST_VALUE)>-1){
			for(int i=0;i<getMainTable().getRowCount()-3;i++){
				Boolean isSplit=(Boolean)getMainTable().getCell(i, 1).getValue();
				if(isSplit!=null&&isSplit.booleanValue()){
					Object value = getMainTable().getCell(i, colIndex).getValue();
					if(value==null){
						//自定义指标的汇总，如果该行产品没有数据，则取其上一个版本的数据
						ProductTypeInfo prod = (ProductTypeInfo)getMainTable().getRow(i).getUserObject();
						if(prod==null) continue;
						String key=prod.getId().toString()+verName;
						if(dataMap.get(key)==null){
							if(verName.equals(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE)){
								//取目标成本指标
								value=dataMap.get(prod.getId().toString()+colKey);
							}else{
								if(dataMap.get(prod.getId().toString()+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE)!=null){
									value=dataMap.get(prod.getId().toString()+colKey);
								}
							}
						}
					}
					total=total.add(FDCHelper.toBigDecimal(value));
				}
			}
		}
		
//		if(colKey.indexOf(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE)>-1){}
		
//		if(colKey.indexOf(ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE)>-1){}
		getMainTable().getCell(getMainTable().getRowCount()-1, colIndex).setValue(total);
	}
	private void save(){
		if(!isModify()){
			MsgBox.showWarning(this, FDCClientUtils.getRes("notchange"));
			return;
		}
		Map map = getChangeData();
		if(map.size()==0){
			return;
		}
		try {
			Map helpMap=new HashMap();
			CoreBaseCollection colls=new CoreBaseCollection();
			for(Iterator iter=map.values().iterator();iter.hasNext();){
				Object[] objs=(Object[])iter.next();
				
				String helpKey=((CurProjectInfo)objs[0]).getId().toString();
				if((ProductTypeInfo)objs[1]!=null){
					helpKey+=((ProductTypeInfo)objs[1]).getId().toString();
				}
				helpKey+=(String)objs[3];
				
				ProjectIndexDataInfo info=(ProjectIndexDataInfo)helpMap.get(helpKey);
				if(info==null){
					info=new ProjectIndexDataInfo();
					info.setProjOrOrgID(((CurProjectInfo)objs[0]).getId());
					info.setProductType((ProductTypeInfo)objs[1]);
					info.setVerName((String)objs[3]);
					info.setProjectStage(ProjectStageEnum.DYNCOST);
					colls.add(info);
				}
				
				ProjectIndexDataEntryInfo entry=new ProjectIndexDataEntryInfo();
				ApportionTypeInfo appt=new  ApportionTypeInfo();
				appt.setId(BOSUuid.read((String)objs[2]));
				entry.setApportionType(appt);
				entry.setParent(info);
				entry.setIndexValue((BigDecimal)objs[4]);
				if(info.getProductType()==null){
					entry.setDifValue(FDCHelper.toBigDecimal(dataMap.get(info.getVerName()+entry.getApportionType().getId().toString()+"dif")));
				}
				info.getEntries().add(entry);
				helpMap.put(helpKey, info);
			}
			
			ProjectIndexDataFactory.getRemoteInstance().submitAreaIndex(colls);
			FDCClientHelper.showSubmitSuccess(this, new  ProjectIndexDataInfo());
			String okMsg = "保存成功！";
			String idxRefMsg = okMsg + "\n\n面积指标已经修改,是否进行指标刷新?";
			int choose = MsgBox.showConfirm2New(this, idxRefMsg);
			if (choose == MsgBox.YES) {
				idxRefresh(colls);
			}
		} catch (Exception e) {	
			this.handUIException(e);
		}finally{
			map.clear();
			isModify=false;
			try {
				loadData();
			} catch (EASBizException e) {
				this.handUIException(e);
			} catch (BOSException e) {
				this.handUIException(e);
			}
		}
	}
	
	private void idxRefresh(CoreBaseCollection colls){
		CurProjectInfo prj=(CurProjectInfo)dataMap.get("curProject");
		final CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		if(currentFIUnit==null||currentFIUnit.getId()==null){
			MsgBox.showError(this, "当前财务组织为空,不能进行此操作,请切换到财务实体！");
			SysUtil.abort();
		}
		try{
			boolean isFinacial=FDCUtils.IsFinacial(null, currentFIUnit.getId().toString());
			if(isFinacial){
				FilterInfo filter=new FilterInfo();
				filter.appendFilterItem("isCostEnd", Boolean.TRUE);
				filter.appendFilterItem("isFinacialEnd", Boolean.FALSE);
				filter.appendFilterItem("project.id", prj.getId().toString());
				boolean exits=ProjectPeriodStatusFactory.getRemoteInstance().exists(filter);
				if(exits){
					MsgBox.showError(this, "当前项目成本已经月结,财务成本还没有月结不能进行指标刷新!");
					SysUtil.abort();
				}
				
			}
		}catch(BOSException e){
			handUIException(e);
		}catch(EASBizException e){
			handUIException(e);
		}
		
		Map param=new HashMap();
		List list=new ArrayList();
		param.put("refreshSrcList", list);
		for(Iterator iter=colls.iterator();iter.hasNext();){
			ProjectIndexDataInfo info=(ProjectIndexDataInfo)iter.next();
			String prjId=info.getProjOrOrgID().toString();
			String productId=info.getProductType()==null?null:info.getProductType().getId().toString();
			Set changeApportions=new HashSet();
			for(int i=0;i<info.getEntries().size();i++){
				changeApportions.add(info.getEntries().get(i).getApportionType().getId().toString());
			}
			Map dataMap=new HashMap();
			dataMap.put("projId", prjId);
			dataMap.put("productId", productId);
			dataMap.put("apportionsId", changeApportions);
			list.add(dataMap);
		}
		param.put("companyId", SysContext.getSysContext().getCurrentFIUnit().getId().toString());
		param.put("projId", prj.getId().toString());
		ProjectIndexDataUtils.idxRefresh(this, param);
	}
	private void dataChangeTip(){
		if(isModify()){
			int reValue = MsgBox.showConfirm2New(this, "数据已经修改，是否保存？");
			if(reValue==MsgBox.YES){
				save();
			}else{
				if(changeData!=null){
					changeData.clear();
				}
			}
		}
	}
	
	private void loadData() throws EASBizException, BOSException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		Object obj = node.getUserObject();
		if (!(obj instanceof CurProjectInfo)) {
			return;
		}
		CurProjectInfo prj = (CurProjectInfo) obj;
		fillTable(prj.getId().toString());
		
		initFIProductSettleData(prj.getId().toString());
	}
	
	private void fillTable(String prjId) throws EASBizException, BOSException{
		EventListener[] listeners = getMainTable().getListeners(KDTPropertyChangeListener.class);
		for(int i=listeners.length-1;i>=0;i--){
			getMainTable().removeKDTPropertyChangeListener((KDTPropertyChangeListener)listeners[i]);
		}
		int dynCol1=8;
		int dynCol2=11;
		getMainTable().removeRows(false);
		getMainTable().getStyleAttributes().setLocked(true);
		CurProjectInfo prj = getCurProject(prjId);
//		ProjectIndexDataCollection projectIndexDatas = getProjectIndexDatas(prjId);
		setDataMap(getProjectIndexDatas(prjId));
		setCanEdit(prjId);
		dataMap.put("curProject", prj);
		
		FDCHelper.sortObjectCollection(prj.getCurProjProductEntries(), getComparator());
		for(int i=0;i<prj.getCurProjProductEntries().size();i++){
			CurProjProductEntriesInfo info = prj.getCurProjProductEntries().get(i);
			IRow row = getMainTable().addRow();
			row.getCell("productType").setValue(info.getProductType().getName());
			row.getCell("isSplit").setValue(Boolean.valueOf(info.isIsSplit()));
			row.setUserObject(info.getProductType());
			for(int j=2;j<getMainTable().getColumnCount();j++){
				final String productId = info.getProductType().getId().toString();
				String key=productId;
				key+=getKey(getMainTable().getColumnKey(j));
				row.getCell(j).setValue(dataMap.get(key));
				if(true){
					System.out.println("column: "+row.getCell(j).getValue());
					System.out.println("key: "+key);
				}
				StyleAttributes sa = row.getCell(j).getStyleAttributes();
				//设置动态成本可以编辑
				if(j>=dynCol1&&j<dynCol2){
					String myKeyDyn1 = productId+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+"IsLatestVer";
					String myKeyDyn2 = productId+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE+"IsLatestVer";
					if((dataMap.get(myKeyDyn1)==null&&dataMap.get(myKeyDyn2)==null)
							||(dataMap.get(myKeyDyn1)!=null)&&isCellCanEdit(productId)){
						//不存在动态成本数据或该动态成本数据为最新版本可编辑
						sa.setLocked(false);
						row.getCell(j).setEditor(FDCTableHelper.getCellNumberEditor());
					}
				}else if(j>=dynCol2&&isCellCanEdit(productId)){
					sa.setLocked(false);
					row.getCell(j).setEditor(FDCTableHelper.getCellNumberEditor());
				}
				
			}
		}
		//汇总
		IRow row = getMainTable().addRow();
		dataMap.put("collectRow", row);
		row.getCell("productType").setValue("汇总");
		row.getStyleAttributes().setBackground(FDCTableHelper.daySubTotalColor);
		
		row = getMainTable().addRow();
		//整个工程
		row = getMainTable().addRow();
		row.getCell("productType").setValue("整个工程");
		row.getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
		for(int j=2;j<getMainTable().getColumnCount();j++){
			StyleAttributes sa = row.getCell(j).getStyleAttributes();
			String key=prj.getId().toString();
			key+=getKey(getMainTable().getColumnKey(j));
			row.getCell(j).setValue(dataMap.get(key));
			//只有占地面积可以编辑
			if(getMainTable().getColumnKey(j).indexOf("contain")==-1){
				continue;
			}
			//设置动态成本可以编辑
			if(j>=dynCol1&&j<dynCol2){
				String myKeyDyn1 = null+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+"IsLatestVer";
				String myKeyDyn2 = null+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE+"IsLatestVer";
				if((dataMap.get(myKeyDyn1)==null&&dataMap.get(myKeyDyn2)==null)
						||(dataMap.get(myKeyDyn1)!=null)&&isCellCanEdit(null)){
					//不存在动态成本数据或该动态成本数据为最新版本可编辑
					sa.setLocked(false);
					row.getCell(j).setEditor(FDCTableHelper.getCellNumberEditor());
				}
			}else if(j>=dynCol2&&isCellCanEdit(null)){
				sa.setLocked(false);
				row.getCell(j).setEditor(FDCTableHelper.getCellNumberEditor());
			}
		}
		row.setUserObject(prj);
		
		setUnion();
		isModify=false;
		
		for(int i=listeners.length-1;i>=0;i--){
			getMainTable().addKDTPropertyChangeListener((KDTPropertyChangeListener)listeners[i]);
		}
		listeners=null;
		if(cantEdit()){
			getMainTable().getStyleAttributes().setLocked(true);
		}
	}
	/**
	 * 指标值：key=项目（产品）ID+阶段+[动态成本版本]+指标
	 * 动态成本最新版本：key=产品ID[]+版本名
	 * 建筑面积差值：版本名+指标ID+"dif"
	 * @param projectIndexDatas
	 */
	private void setDataMap(ProjectIndexDataCollection projectIndexDatas){
		dataMap.clear();
		if(changeData!=null){
			changeData.clear();
		}
		for(int i=0;i<projectIndexDatas.size();i++){
			ProjectIndexDataInfo projectIndexDataInfo = projectIndexDatas.get(i);
			String key=projectIndexDataInfo.getProjOrOrgID().toString();
			if(projectIndexDataInfo.getProductType()!=null){
				key=projectIndexDataInfo.getProductType().getId().toString();
			}
			
			//导入指标:存在项目阶段为空的数据
			if(projectIndexDataInfo.getProjectStage()==null){
				continue ;	
			}
			
			key+=projectIndexDataInfo.getProjectStage().getValue();
			for(Iterator iter=projectIndexDataInfo.getEntries().iterator();iter.hasNext();){
//				String myKey=new String(key);
				String myKey = key ;
				ProjectIndexDataEntryInfo entry=(ProjectIndexDataEntryInfo)iter.next();
				if(projectIndexDataInfo.getProjectStage()==ProjectStageEnum.DYNCOST){
					myKey+=projectIndexDataInfo.getVerName();
				}
				myKey+=entry.getApportionType().getId().toString();
				if(false){
					System.out.println(projectIndexDataInfo.getProjectStage().getValue()+"\t"+projectIndexDataInfo.getVerName()
						+"\t"+projectIndexDataInfo.getProductType()+"\t "+entry.getApportionType()
						+"\t"+entry.getIndexValue()+"\t "+myKey);
				}
				dataMap.put(myKey, entry.getIndexValue());
				if(projectIndexDataInfo.isIsLatestVer()&&projectIndexDataInfo.getProjectStage()==ProjectStageEnum.DYNCOST){
					dataMap.put((projectIndexDataInfo.getProductType()==null?null:projectIndexDataInfo.getProductType().getId().toString())+projectIndexDataInfo.getVerName()+"IsLatestVer", Boolean.TRUE);
				}
				if(projectIndexDataInfo.getProductType()==null){
					if(false){
						System.out.print("Key:"+projectIndexDataInfo.getVerName()+entry.getApportionType().getId().toString()+"dif");
						System.out.println("\t value:"+entry.getDifValue());
					}
					dataMap.put(projectIndexDataInfo.getVerName()+entry.getApportionType().getId().toString()+"dif", entry.getDifValue());
				}
				if(entry.getApportionType()!= null){
					try {
						ApportionTypeInfo apportionType = ApportionTypeFactory.getRemoteInstance().getApportionTypeInfo(new ObjectUuidPK(entry.getApportionType().getId()));
						//如果指标不等于建筑面积,可售面积,占地面积
		    			if (!(ApportionTypesEnum.BYBUILDAREA.toString().equals(apportionType.getName().toString()) && ApportionTypesEnum.BYSELLAREA.toString().equals(apportionType.getName().toString())
		    					&& ApportionTypesEnum.BYCONTAINAREA.toString().equals(apportionType.getName().toString()))) {
		    				dataMap.put("zidingyizhibiao", count++);
		    			}
		    			
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
			
		}
		
	}
	
	private BigDecimal getIndexValue(String productTypeId,String colKey){
		BigDecimal data=FDCHelper.ZERO;
		return data;
	}
	
	private CurProjectInfo getCurProject(String prjId) throws EASBizException, BOSException{
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("curProjProductEntries.*"));
		selector.add(new SelectorItemInfo("curProjProductEntries.productType.*"));
		CurProjectInfo prj=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(prjId),selector);
		return prj;
	}
	
	private ProjectIndexDataCollection getProjectIndexDatas(String prjId) throws EASBizException, BOSException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("projOrOrgID", prjId);
		filter.appendFilterItem("isLatestVer", Boolean.TRUE);
		filter.appendFilterItem("isLatestSubVer", Boolean.TRUE);
		filter.setMaskString("#0 and (#1 or #2)");
		view.setFilter(filter);
		
		view.getSelector().add("*");
		view.getSelector().add("productType.*");
		view.getSelector().add("entries.*");
		view.getSelector().add("entries.apportionType.*");
		ProjectIndexDataCollection collection=ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
		return collection;
	}
	
	private void setUnion(){
		IRow row=(IRow)dataMap.get("collectRow");
		if(row==null){
			return;
		}
		for(int i=2;i<getMainTable().getColumnCount();i++){
			BigDecimal data=FDCHelper.ZERO;
			for(int j=0;j<getMainTable().getRowCount()-3;j++){
				Object value = getMainTable().getCell(j, 1).getValue();
				/*if(value instanceof Boolean && ((Boolean)value).booleanValue()){
					data=data.add(FDCHelper.toBigDecimal(getMainTable().getCell(j, i).getValue()));
				}*/
				data=data.add(FDCHelper.toBigDecimal(getMainTable().getCell(j, i).getValue()));
			}
			row.getCell(i).setValue(data.signum()==0?null:data);
//			row.getCell(i).setValue(data);
		}
	}
	
	private String getKey(String colKey){
		String key=null;
		int columnIndex = getMainTable().getColumnIndex(colKey);
		if(columnIndex<=0){
			return null;
		}
		if(colKey.equals("productType")){
			
		}
		if(colKey.equals("isSplit")){
			
		}
		if(colKey.equals("build_res")){
			key=ProjectStageEnum.RESEARCH_VALUE+ApportionTypeInfo.buildAreaType;
		}
		if(colKey.equals("sell_res")){
			key=ProjectStageEnum.RESEARCH_VALUE+ApportionTypeInfo.sellAreaType;
		}
		if(colKey.equals("contain_res")){
			key=ProjectStageEnum.RESEARCH_VALUE+ApportionTypeInfo.placeAreaType;
		}
		if(colKey.equals("build_aim")){
			key=ProjectStageEnum.AIMCOST_VALUE+ApportionTypeInfo.buildAreaType;
		}
		if(colKey.equals("sell_aim")){
			key=ProjectStageEnum.AIMCOST_VALUE+ApportionTypeInfo.sellAreaType;
		}
		if(colKey.equals("contain_aim")){
			key=ProjectStageEnum.AIMCOST_VALUE+ApportionTypeInfo.placeAreaType;
		}
		if(colKey.equals("build_dyn1")){
			key=ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+ApportionTypeInfo.buildAreaType;
		}
		if(colKey.equals("sell_dyn1")){
			key=ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+ApportionTypeInfo.sellAreaType;
		}
		if(colKey.equals("contain_dyn1")){
			key=ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+ApportionTypeInfo.placeAreaType;
		}
		if(colKey.equals("build_dyn2")){
			key=ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE+ApportionTypeInfo.buildAreaType;	
		}
		if(colKey.equals("sell_dyn2")){
			key=ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE+ApportionTypeInfo.sellAreaType;
		}
		if(colKey.equals("contain_dyn2")){
			key=ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE+ApportionTypeInfo.placeAreaType;
		}
		if(key == null){
			return colKey;
		}

		return key;
	}

	private Comparator c=null;
	public Comparator getComparator(){
		 if(c==null){
			 return new Comparator(){
				 public int compare(Object o1, Object o2) {
					if(o1==null&&o2==null){
						return 0;
					}
					if(o1!=null&&o2==null){
						return 1;
					}
					if(o1==null&&o2!=null){
						return -1;
					}
					CurProjProductEntriesInfo info1 = (CurProjProductEntriesInfo) o1;
					CurProjProductEntriesInfo info2 = (CurProjProductEntriesInfo) o2;
					o1=info1.getProductType().getNumber();
					o2=info2.getProductType().getNumber();
					if(o1==null&&o2==null){
						return 0;
					}
					if(o1!=null&&o2==null){
						return 1;
					}
					if(o1==null&&o2!=null){
						return -1;
					}
					
					return ((String)o1).compareTo((String)o2);
				}
			 };
		 }
		 return c;
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		dataChangeTip();
		loadData();
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
	}
	private boolean cantEdit(){
		//财务组织及成本中心可编辑
		OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		if(org!=null&&((org.isIsCompanyOrgUnit()&&SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit())
				||(org.isIsCostOrgUnit()&&SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()))){
			return false;
		}
		return true;
	}
	
	private void setCanEdit(String projectID) throws BOSException, EASBizException {

		BigDecimal sellArea = FDCHelper.ZERO;
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		Set status = new HashSet();
		status.add(ProjectStatusInfo.settleID);
		status.add(ProjectStatusInfo.closeID);
		filter.getFilterItems().add(new FilterItemInfo("id", projectID));
		filter.getFilterItems().add(new FilterItemInfo("projectStatus.id", status, CompareType.NOTINCLUDE));
		view.setFilter(filter);
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("curProjProductEntries.productType.id");
		selector.add("curProjProductEntries.isCompSettle");
		selector.add("projectStatus.id");
		final CurProjectInfo curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectID), selector);
		dataMap.put("prj_canEdit", Boolean.TRUE);
		if(curProjectInfo.getProjectStatus()!=null
				&&(curProjectInfo.getProjectStatus().equals(ProjectStatusInfo.settleID)
				||curProjectInfo.getProjectStatus().equals(ProjectStatusInfo.closeID))){
			dataMap.put("prj_canEdit", Boolean.FALSE);
			return;
		}
		for(Iterator iter=curProjectInfo.getCurProjProductEntries().iterator();iter.hasNext();){
			CurProjProductEntriesInfo entry=(CurProjProductEntriesInfo)iter.next();
			if(entry.isIsCompSettle()){
				dataMap.put("product_canEdit"+entry.getProductType().getId().toString(), Boolean.FALSE);
			}else{
				//TODO没有时间以后再实现可售面积不能大于峻工结算的面积
			}
			
		}
	}
	
	/**productId=null 为工程项目
	 * @param productId
	 */
	private boolean isCellCanEdit(String productId){
		if(productId==null){
			Boolean canEdit=(Boolean)dataMap.get("prj_canEdit");
			if(canEdit!=null){
				return  canEdit.booleanValue();
			}
		}else{
			Boolean canEdit=(Boolean)dataMap.get("prj_canEdit");
			if(canEdit!=null&&!canEdit.booleanValue()){
				return false;
			}else{
				canEdit=(Boolean)dataMap.get("product_canEdit"+productId);
				if(canEdit!=null&&!canEdit.booleanValue()){
					return false;
				}
			}
		}
		return true;
	}
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		
	}
	
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
//		super.actionQuery_actionPerformed(e);
	}
	
	private Map prodSetMap =null;
	private void initFIProductSettleData(String projectID)  throws EASBizException, BOSException {
		prodSetMap= new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select t.fid,sum(b.fcompArea) fcompArea from T_FNC_ProductSettleBill b ");
		builder.appendSql("inner join T_FDC_CurProjProductEntries entries on entries.fid=b.FCurProjProductEntriesID ");
		builder.appendSql("inner join t_fdc_producttype t on t.fid=entries.fproducttype ");
		builder.appendSql("where entries.FCurProject=? ");
		builder.addParam(projectID);
		builder.appendSql(" group by t.fid");
		IRowSet rs = builder.executeQuery();
		try{
			while(rs.next()){
				prodSetMap.put(rs.getString("fid"), rs.getBigDecimal("fcompArea"));
			}
		}catch(Exception e){
			throw new BOSException(e);
		}
	}
	
	public int getRowCountFromDB() {
//		super.getRowCountFromDB();
		return -1;
	}
}