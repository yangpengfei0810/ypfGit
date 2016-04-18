/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimHleperFacadeFactory;
import com.kingdee.eas.fdc.aimcost.ApportionTypesEnum;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryFactory;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade;
import com.kingdee.eas.fdc.aimcost.PublicSetSplitCollection;
import com.kingdee.eas.fdc.aimcost.PublicSetSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.PublicSetSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.PublicSetSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.PublicSetSplitFacadeFactory;
import com.kingdee.eas.fdc.aimcost.PublicSetSplitFactory;
import com.kingdee.eas.fdc.aimcost.PublicSetSplitInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;

/**
 * 公共配套已发生分摊
 */
public class PublicSetSplitRptUI extends AbstractPublicSetSplitRptUI
{
    
    public PublicSetSplitRptUI() throws Exception
    {
    	isFirstLoad=true;
    }
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	//手动 刷新动态成本快照
//    	FDCCostRptFacadeFactory.getRemoteInstance().autoSaveSnapShot();
    	tblMain.checkParsed();
		super.onLoad();
		btnSubmit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
		initTree();
    }
    
    //初始化左树
    private void initTree() throws Exception {
    	ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
    	treeBuilder.build(this, treeProject, actionOnLoad);
        treeProject.expandAllNodes(true, (TreeNode)treeProject.getModel().getRoot());
        initTable();
	}
    
    
    
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	String curProjectID=getCurProjectID();//当前工程项目ID
    	if("".equals(curProjectID)){
			return;
		}
    	
    	resetTableCol(curProjectID); //设置列
    	getTableRow(curProjectID);	 //设置行
    	setSumRow();//合计行
    	addFormatType();//列金额输入控制
    	setSellEditable();
    }
    
    private void addFormatType(){
    	KDFormattedTextField formattedTextField=new KDFormattedTextField();
    	formattedTextField.setVisible(true);
    	formattedTextField.setHorizontalAlignment(2);
    	formattedTextField.setDataType(1);
    	formattedTextField.setPrecision(2);
    	formattedTextField.setName("happenedAmt");
    	KDTDefaultCellEditor defaultCellEditor=new KDTDefaultCellEditor(formattedTextField);
    	tblMain.getColumn("happenedAmt").setEditor(defaultCellEditor);
    	for(int i=3;i<tblMain.getColumnCount();i++){
    		formattedTextField.setName(i+"amt");
    		KDTDefaultCellEditor editor=new KDTDefaultCellEditor(formattedTextField);
    		tblMain.getColumn(i).setEditor(editor);
    	}
    }
    
    /**
     * 手动设置合计行
     */
    private void setSumRow(){
    	int rowCount=tblMain.getRowCount();
    	IRow footRow;
    	if(rowCount==0){
    		return;
    	}
    	
    	//有合计行则清空
    	if(tblMain.getCell(rowCount-1,0).getValue().equals("合计")){
    		tblMain.removeRow(rowCount-1);
    	}
    	footRow=tblMain.addRow();//临时的合计行
	    footRow.getCell(0).setValue("合计");
	    footRow.getStyleAttributes().setLocked(true);
		footRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
    	for(int i=3;i<tblMain.getColumnCount();i++){
			BigDecimal tempAmt=BigDecimal.ZERO;
			for(int j=0;j<tblMain.getRowCount()-1;j++){
				tempAmt=tempAmt.add(FDCHelper.toBigDecimal(tblMain.getCell(j, i).getValue()));
			}
			footRow.getCell(i).setValue(tempAmt);
		}
		BigDecimal totalAmt=BigDecimal.ZERO;
		for(int i=0;i<tblMain.getRowCount()-1;i++){
			totalAmt=totalAmt.add(FDCHelper.toBigDecimal(tblMain.getCell(i,"happenedAmt").getValue()));
		}
		footRow.getCell("happenedAmt").setValue(totalAmt);
		footRow.getStyleAttributes().setLocked(true);
    }
    
    //当前工程项目ID
	private String getCurProjectID() {
		DefaultKingdeeTreeNode projectNode = getProjSelectedTreeNode();
		if(projectNode instanceof KDTreeNode){
			KDTreeNode node = (KDTreeNode)projectNode;
			Object obj = node.getUserObject();
			if(obj instanceof CurProjectInfo){
				CurProjectInfo info = (CurProjectInfo)obj;
				txtCurProject.setText(info.getName());
				return info.getId().toString();
			}
		}
		return "";
	}

	private DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeProject.getLastSelectedPathComponent();
	}
	
	//保存
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkTableInfo();
		getTableDataCollection();
	}
	
	//保存前校验数据信息
	private void checkTableInfo(){
		int colCount=tblMain.getColumnCount();
		int rowCount=tblMain.getRowCount();
		for(int i=0;i<rowCount-1;i++){
//			for(int j=0;j<colCount;j++){
//				Object object=tblMain.getCell(i,j).getValue();
//				//表格的单元格不能有空
//				if(object==null || object.toString().equals("")){
//					MsgBox.showError("第"+(i+1)+"行，第"+(j+1)+"列上的数据为空！");
//					SysUtil.abort();
//				}
//			}
			IRow row=tblMain.getRow(i);
			//各产品类型分摊到的金额之和必须等于已发生成本(因计算产出的误差控制在1以内)
			BigDecimal happenedAmt=FDCHelper.toBigDecimal(row.getCell("happenedAmt").getValue());
			BigDecimal tempAmt=BigDecimal.ZERO;
			for(int k=3;k<colCount;k++){
				tempAmt=tempAmt.add(FDCHelper.toBigDecimal(row.getCell(k).getValue()));
			}
			if(!checkDiff(happenedAmt, tempAmt)){
				MsgBox.showError("第"+(i+1)+"行，各产品分摊金额之和必须等于已发生成本！");
				SysUtil.abort();
			}
		}
		
	}
	
	/**
	 * 比较2个数，差值是否在1以内
	 * true 表示2个数相等（允许差1）
	 */
	private Boolean checkDiff(BigDecimal oneAmt,BigDecimal twoAmt){
		if(oneAmt.compareTo(twoAmt)==0){
			return true;
		}
		if(oneAmt.compareTo(twoAmt)>0){
			if((oneAmt.subtract(twoAmt)).compareTo(BigDecimal.ONE)<=0){
				return true;
			}
		}
		if(oneAmt.compareTo(twoAmt)<0){
			if((twoAmt.subtract(oneAmt)).compareTo(BigDecimal.ONE)<=0){
				return true;
			}
		}
		return false;
	}
	
	private void getTableDataCollection() throws Exception{
		PublicSetSplitCollection collection=new PublicSetSplitCollection();
		IRow row=null;
		int count=tblMain.getRowCount()-1;
		String projectID=getCurProjectID();
		//清空数据表
		PublicSetSplitFacadeFactory.getRemoteInstance().initDBTable(projectID);
		for(int i=0;i<count;i++){
			row=tblMain.getRow(i);
			PublicSetSplitInfo info=new PublicSetSplitInfo();
			info.setCurProjectID(projectID);
			info.setProduct((ProductTypeInfo) row.getCell("public").getValue());
			info.setHappenedAmt(FDCHelper.toBigDecimal(row.getCell("happenedAmt").getValue()));
			info.setApportionTypeF7((ApportionTypeInfo) row.getCell("apportionType").getValue());//--biaobiao 
//			info.setApportionType((ApportionTypesEnum) row.getCell("apportionType").getValue());
			String parentID=PublicSetSplitFactory.getRemoteInstance().save(info).toString();
			PublicSetSplitInfo splitInfo=PublicSetSplitFactory.getRemoteInstance().getPublicSetSplitInfo(new ObjectUuidPK(parentID));
			//分录
			PublicSetSplitEntryCollection entryCollection=new PublicSetSplitEntryCollection();
			for(int j=3;j<tblMain.getColumnCount();j++){
				PublicSetSplitEntryInfo entryInfo=new PublicSetSplitEntryInfo();
				entryInfo.setAmt(FDCHelper.toBigDecimal(row.getCell(j).getValue()));
				String splitProductID=((ProductTypeInfo)row.getCell("public").getValue()).getId().toString();
				entryInfo.setSplitProductID(splitProductID);
				String key=tblMain.getColumnKey(j);
				ProductTypeInfo typeInfo=ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(key));
				entryInfo.setProduct(typeInfo);
				entryInfo.setParent(splitInfo);
				PublicSetSplitEntryFactory.getRemoteInstance().save(entryInfo);
			}
		}
		this.setMessageText("保存成功");
		this.showMessage();
	}
	
	//初始化表格
	private void initTable(){
		//不可编辑的列
//		tblMain.getColumn("public").getStyleAttributes().setLocked(true);
//		tblMain.getColumn("happenedAmt").getStyleAttributes().setLocked(true);
		tblMain.addHeadRow(1,(IRow) tblMain.getHeadRow(0).clone());
	    for(int i = 0; i < tblMain.getColumnCount(); i++){
	    	tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
	    }
	    //分摊类型列绑定ENUM
//	    final KDComboBox comboBox=new KDComboBox();
//	    comboBox.setName("comboBox");
//	    comboBox.setVisible(true);
//	    comboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.aimcost.ApportionTypesEnum").toArray());
//	    KDTDefaultCellEditor appTypeEditor=new KDTDefaultCellEditor(comboBox);
//	    tblMain.getColumn("apportionType").setEditor(appTypeEditor);  //--biaobiao 
	    
	    //分摊方式绑定到F7 --biaobiao
	    f7SplitType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ApportionTypeQuery");
    	f7SplitType.setDisplayFormat("$name$");
    	f7SplitType.setEditFormat("$number$");
    	f7SplitType.setCommitFormat("$number$");
    	EntityViewInfo viewInfo = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",true,CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("forCostApportion",true,CompareType.EQUALS));
    	viewInfo.setFilter(filter);
    	f7SplitType.setEntityViewInfo(viewInfo);
    	ICellEditor SplitType = new KDTDefaultCellEditor(f7SplitType);
    	tblMain.getColumn("apportionType").setEditor(SplitType);
	    
	    //已发生成本
	    tblMain.getColumn("happenedAmt").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
        tblMain.getColumn("happenedAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	    tblMain.setColumnMoveable(true);
		//当分摊方式发生变化时重新计算分摊金额
	    tblMain.addKDTEditListener(new KDTEditAdapter(){
		    public void editStopped(KDTEditEvent e) {
		    	if(e.getColIndex()==2){ //--biaobiao
		    		if(e.getValue() instanceof ApportionTypeInfo){
		    			ApportionTypeInfo apportionTypeInfo =(ApportionTypeInfo) e.getValue();
		    			int count=tblMain.getColumnCount();
		    			int colIndex=e.getColIndex();
		    			int rowIndex=e.getRowIndex();
		    			IRow row=tblMain.getRow(rowIndex);
		    			BigDecimal happenedAmt=BigDecimal.ZERO;//已发生
		    			happenedAmt=FDCHelper.toBigDecimal(row.getCell("happenedAmt").getValue());
		    			//指定分摊
		    			try {
							apportionTypeInfo= ApportionTypeFactory.getRemoteInstance().getApportionTypeInfo(new ObjectUuidPK(apportionTypeInfo.getId()));
						} catch (Exception e1) {
							e1.printStackTrace();
						} 
		    			if(apportionTypeInfo.getName().equals(ApportionTypesEnum.BYCUSTOM.toString())){
		    				for(int i=3;i<count ;i++){
		    					row.getCell(i).getStyleAttributes().setLocked(false);//分摊金额可以编辑
		    					row.getCell(i).setValue(BigDecimal.ZERO);
		    				}
		    			}
		    			//按建筑面积
//		    			else if(apportionTypeInfo.getName().equals(ApportionTypesEnum.BYBUILDAREA.toString())){
		    			else{
		    				for(int i=3;i<count;i++){
		    					row.getCell(i).getStyleAttributes().setLocked(true);
		    					String key=tblMain.getColumn(i).getKey();
		    					if(map.containsKey(key)){
		    						Map maptemp = (Map)map.get(key);
		    						BigDecimal tempSellArea = BigDecimal.ZERO;
		    						if(maptemp.get(apportionTypeInfo.getId().toString()) != null){
		    							tempSellArea = (BigDecimal) maptemp.get(apportionTypeInfo.getId().toString());
		    						}
		    						//根据分摊方式汇总指标
		    						BigDecimal totalSumAreaa = BigDecimal.ZERO;
		    						Iterator ite = map.entrySet().iterator();	
	    							while(ite.hasNext()){	
	    								Map.Entry en =  (Entry) ite.next();
	    								Object key2 = en.getKey();
//	    								System.out.println(en);
	    								Map objectMap  = (Map)map.get(key2);
	    								
	    								Object val = objectMap.get(apportionTypeInfo.getId().toString());//map中的key	
	    								if(val != null){
	    									totalSumAreaa = totalSumAreaa.add(new BigDecimal(val.toString()));
	    								}
	    							}
		    						if(totalSumAreaa.compareTo(BigDecimal.ZERO)!=0){
		    							BigDecimal splitAmt=happenedAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));//分配金额
		    							row.getCell(key).setValue(splitAmt);
		    						}else
		    							row.getCell(key).setValue(BigDecimal.ZERO);
		    					}
		    				}
		    			}
		    		}
//		    		if(e.getValue() instanceof ApportionTypesEnum){
//		    			ApportionTypesEnum typesEnum=(ApportionTypesEnum) e.getValue();
//		    			int count=tblMain.getColumnCount();
//		    			int colIndex=e.getColIndex();
//		    			int rowIndex=e.getRowIndex();
//		    			IRow row=tblMain.getRow(rowIndex);
//		    			BigDecimal happenedAmt=BigDecimal.ZERO;//已发生
//		    			happenedAmt=FDCHelper.toBigDecimal(row.getCell("happenedAmt").getValue());
//		    			//指定分摊
//		    			if(typesEnum.getValue().equals(ApportionTypesEnum.BYCUSTOM_VALUE)){
//		    				for(int i=3;i<count ;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(false);//分摊金额可以编辑
//		    					row.getCell(i).setValue(BigDecimal.ZERO);
//		    				}
//		    			}
//		    			//按建筑面积
//		    			if(typesEnum.getValue().equals(ApportionTypesEnum.BYBUILDAREA_VALUE)){
//		    				for(int i=3;i<count;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(true);
//		    					String key=tblMain.getColumn(i).getKey();
//		    					if(buildMap.containsKey(key)){
//		    						BigDecimal tempBuildArea=buildMap.get(key);//该产品类型的建筑面积
//		    						if(totalBuildArea.compareTo(BigDecimal.ZERO)!=0){
//		    							BigDecimal splitAmt=happenedAmt.multiply(tempBuildArea.divide(totalBuildArea,12,BigDecimal.ROUND_HALF_UP));//分配金额
//		    							row.getCell(key).setValue(splitAmt);
//		    						}else
//		    							row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}
//		    				}
//		    			}
//		    			//按占地面积
//		    			if(typesEnum.getValue().equals(ApportionTypesEnum.BYCONTAINAREA_VALUE)){
//		    				for(int i=3;i<count;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(true);
//		    					String key=tblMain.getColumn(i).getKey();
//		    					if(containMap.containsKey(key)){
//		    						BigDecimal tempContainArea=containMap.get(key);//该产品类型的占地面积
//		    						if(totalContainArea.compareTo(BigDecimal.ZERO)!=0){
//		    							BigDecimal splitAmt=happenedAmt.multiply(tempContainArea.divide(totalContainArea,12,BigDecimal.ROUND_HALF_UP));//分配金额
//		    							row.getCell(key).setValue(splitAmt);
//		    						}else
//		    							row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}
//		    				}
//		    				
//		    			}
//		    			//按可售面积
//		    			if(typesEnum.getValue().equals(ApportionTypesEnum.BYSELLAREA_VALUE)){
//		    				for(int i=3;i<count;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(true);
//		    					String key=tblMain.getColumn(i).getKey();
//		    					if(sellMap.containsKey(key)){
//		    						BigDecimal tempSellArea=sellMap.get(key);//该产品类型的可售面积
//		    						if(totalSellArea.compareTo(BigDecimal.ZERO)!=0){
//		    							BigDecimal splitAmt=happenedAmt.multiply(tempSellArea.divide(totalSellArea,12,BigDecimal.ROUND_HALF_UP));//分配金额
//		    							row.getCell(key).setValue(splitAmt);
//		    						}else
//		    							row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}
//		    				}
//		    			}
//		    		}
		    	}
		    	setSumRow();
		    }});
	    setSellEditable();
	}
	/**
	 * 单元格是否可编辑
	 */
	private void setSellEditable(){
		tblMain.getStyleAttributes().setLocked(true);
		int rowCount=tblMain.getRowCount1();
		int colCount=tblMain.getColumnCount();
		for(int i=0;i<rowCount-1;i++){
			IRow row=tblMain.getRow(i);
			row.getCell("apportionType").getStyleAttributes().setLocked(false);
			//如果分摊方式为“指定分摊”
			Object typeEnum=row.getCell("apportionType").getValue(); //--biaobiao
			if(typeEnum instanceof ApportionTypeInfo){
				ApportionTypeInfo apportionTypeInfo;
				try {
					apportionTypeInfo = ApportionTypeFactory.getRemoteInstance().getApportionTypeInfo(new ObjectUuidPK(((ApportionTypeInfo) typeEnum).getId()));
					if(apportionTypeInfo.getName().equals(ApportionTypesEnum.BYCUSTOM_VALUE.toString())){
						for(int j=3;j<colCount;j++){
							row.getCell(j).getStyleAttributes().setLocked(false);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			if(typeEnum instanceof ApportionTypesEnum){
//				if(((ApportionTypesEnum)typeEnum).getValue().equals(ApportionTypesEnum.BYCUSTOM_VALUE)){
//					for(int j=3;j<colCount;j++){
//						row.getCell(j).getStyleAttributes().setLocked(false);
//					}
//				}
//			}
		}
		if(rowCount>=1){
			//合计行不许编辑
			tblMain.getRow(rowCount-1).getStyleAttributes().setLocked(true);
		}
		
	}
	
	 //判断选择的分摊方式在自定义指标表里是否存在
	private Boolean isCustomPlanindex(ApportionTypeInfo apportionTypeInfo ){
		try {
			CustomPlanIndexEntryCollection customEntrys = CustomPlanIndexEntryFactory.getRemoteInstance().getCustomPlanIndexEntryCollection();
			for (int i = 0; i < customEntrys.size(); i++) {
				CustomPlanIndexEntryInfo planIndexEntryInfo = customEntrys.get(0);
				if(planIndexEntryInfo.getApportType().getId().equals(apportionTypeInfo.getId())){
					return true;
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} 
		return false;
		
	}
	
	//取不分摊的产品类型
	private void getTableRow(String curProjID) throws Exception{
		tblMain.removeRows();
		IPublicSetSplitFacade facade=PublicSetSplitFacadeFactory.getRemoteInstance();
		//先加载出公共配套产品类型
		IRowSet publicRowSet=facade.getPublicRow(curProjID);
		publicRowSet.beforeFirst();
		while(publicRowSet.next()){
			IRow row=tblMain.addRow();
			String proID=publicRowSet.getString("fid");
			if(proID!=null && !proID.equals("")){
				ProductTypeInfo proInfo=ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(proID));
				row.getCell("public").setValue(proInfo);
			}
		}
		//已发生成本
		IRowSet amtRowSet=facade.getRowData(curProjID);
		amtRowSet.beforeFirst();
		while(amtRowSet.next()){
			String proID=amtRowSet.getString("productTypeID");
			for(int i=0;i<tblMain.getRowCount();i++){
				IRow row=tblMain.getRow(i);
				String key=((ProductTypeInfo)row.getCell("public").getValue()).getId().toString();
				if(key.equals(proID)){
					row.getCell("happenedAmt").setValue(FDCHelper.toBigDecimal(amtRowSet.getBigDecimal("happenedAmt")));
				}
			}
		}
		//从数据库里加载出上次的分摊方式和分摊金额
		FilterInfo filterInfo=new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("curProjectID",curProjID));
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("product.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		EntityViewInfo viewInfo=new EntityViewInfo();
		viewInfo.setFilter(filterInfo);
		viewInfo.setSelector(sic);
		PublicSetSplitCollection  splitCollection=PublicSetSplitFactory.getRemoteInstance().getPublicSetSplitCollection(viewInfo);
  		if(splitCollection.size()>0){
			for(int i=0;i<splitCollection.size();i++){
				PublicSetSplitInfo splitInfo=splitCollection.get(i);
				for(int j=0;j<tblMain.getRowCount();j++){
					IRow row=tblMain.getRow(j);
					String prokey=((ProductTypeInfo)row.getCell("public").getValue()).getId().toString();
					if(prokey.equals(((ProductTypeInfo)splitInfo.getProduct()).getId().toString())){
						if(splitInfo.getApportionTypeF7()!= null){
							ApportionTypeInfo apportionType =ApportionTypeFactory.getRemoteInstance().getApportionTypeInfo(new ObjectUuidPK(splitInfo.getApportionTypeF7().getId()));
							row.getCell("apportionType").setValue(apportionType); // -biaobiao
						}
//						row.getCell("apportionType").setValue(splitInfo.getApportionType()); 
						PublicSetSplitEntryCollection entryCollection=splitInfo.getEntrys();
						if(entryCollection.size()>0){
							for(int k=0;k<entryCollection.size();k++){
								PublicSetSplitEntryInfo entryInfo=entryCollection.get(k);
								String pruductID=entryInfo.getProduct().getId().toString();
								for(int m=3;m<tblMain.getColumnCount();m++){
									if(tblMain.getColumn(m).getKey().equals(pruductID)){
										row.getCell(m).setValue(entryInfo.getAmt());
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	//重置表格列 (取分摊的产品类型)
	private void resetTableCol(String curProjID) throws Exception {
		int colCount=tblMain.getColumnCount();
		for(int i=0;i<colCount-3;i++){
			tblMain.removeColumn(3);
		}
		//初始化
		totalContainArea=BigDecimal.ZERO;
		totalSellArea=BigDecimal.ZERO;
		totalBuildArea=BigDecimal.ZERO;
		totalSumArea = BigDecimal.ZERO;//-biaobiao
		containMap.clear();
		sellMap.clear();
		buildMap.clear();
		IRowSet rowSet=PublicSetSplitFacadeFactory.getRemoteInstance().getPublicSetSplitColums(curProjID);
		//-biaobioa
//		ProjectIndexDataCollection indexDatas = getProjectIndexDatas(curProjID);
		map = AimHleperFacadeFactory.getRemoteInstance().getApportionIndex(curProjID);
		String sql = "select proType.Fname_L2,proType.fid as fid,cus.fisproduct,cus.findexValue as cusval  from T_AIM_CustomPlanIndexEntry cus  left join t_fdc_producttype proType on cus.fproductTypeid = proType.FID  left join t_aim_planindex plx on plx.fid = cus.fparentid  left join t_aim_measurecost mct on mct.fid=plx.fheadid where mct.fprojectid='"+curProjID+"' and mct.Fislastversion='1' order by proType.fname_l2 desc ";
		ISQLExecutor executor = SQLExecutorFactory.getRemoteInstance(sql);
		IRowSet rs = executor.executeSQL();
    	while(rs.next()){
    		String productID=rs.getString("fid");
    		BigDecimal sellArea=rs.getBigDecimal("cusval");//指标值
    		customMap.put(productID, sellArea);
    		if(sellArea==null){
    			sellArea=BigDecimal.ZERO;
			}
    		totalSumArea = totalSumArea.add(sellArea);
    	}	
		while(rowSet.next()){
			String productID=rowSet.getString("fid");
			String productName=rowSet.getString("fname_l2");
			BigDecimal containArea=rowSet.getBigDecimal("fcontainarea");//占地面积
			BigDecimal buildarea=rowSet.getBigDecimal("fbuildarea");//建筑面积
			BigDecimal sellArea=rowSet.getBigDecimal("fsellarea");//可售面积
			if(containArea==null){
				containArea=BigDecimal.ZERO;
			}
			if(buildarea==null){
				buildarea=BigDecimal.ZERO;
			}
			if(sellArea==null){
				sellArea=BigDecimal.ZERO;
			}
			String key=productID;
			IColumn column=tblMain.addColumn();
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
            column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue("公共配套已发生成本分摊");
			tblMain.getHeadRow(1).getCell(key).setValue(productName);
			containMap.put(productID, containArea);
			sellMap.put(productID, sellArea);
			buildMap.put(productID, buildarea);
			totalContainArea=totalContainArea.add(containArea);
			totalSellArea=totalSellArea.add(sellArea);
			totalBuildArea=totalBuildArea.add(buildarea);
		}
		tblMain.getHeadMergeManager().mergeBlock(0,3,0,tblMain.getColumnCount()-1);
		tblMain.setColumnMoveable(true);
	}
	
	//biaobiao
	private ProjectIndexDataCollection getProjectIndexDatas(String prjId) throws EASBizException, BOSException{
//		ProjectIndexDataEntryFactory.getRemoteInstance().getProjectIndexDataEntryCollection();
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("projOrOrgID", prjId);
		filter.appendFilterItem("isLatestVer", Boolean.TRUE);
		filter.appendFilterItem("isLatestSubVer", Boolean.TRUE);
		filter.getFilterItems().add(new FilterItemInfo("entries.apportionType",ApportionTypeInfo.buildAreaType,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("entries.apportionType",ApportionTypeInfo.sellAreaType,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("entries.apportionType",ApportionTypeInfo.placeAreaType,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("entries.apportionType",ApportionTypeInfo.aimCostType,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("entries.apportionType",ApportionTypeInfo.appointType,CompareType.NOTEQUALS));
		filter.setMaskString("#0 and (#1 or #2) and (#3 and #4 and #5 and #6 and #7 )");
		view.setFilter(filter);
		
		view.getSelector().add("*");
		view.getSelector().add("productType.*");
		view.getSelector().add("entries.*");
		view.getSelector().add("entries.apportionType.*");
		ProjectIndexDataCollection collection=ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
		return collection;
	}
	
	
	private BigDecimal totalContainArea;//产品类型的总占地面积
	private BigDecimal totalSellArea;//总可售面积
	private BigDecimal totalBuildArea;//总建筑面积
	private BigDecimal totalSumArea; //自定义总面积
	private KDBizPromptBox f7SplitType = new KDBizPromptBox();// 分摊类型F7  -biaobiao
	private Map<String, BigDecimal> containMap=new TreeMap<String,BigDecimal>(); //按占地面积
	private Map<String, BigDecimal> buildMap=new TreeMap<String,BigDecimal>(); //按建筑面积
	private Map<String, BigDecimal> sellMap=new TreeMap<String,BigDecimal>(); //按可售面积
	private Map<String, BigDecimal> customMap=new TreeMap<String,BigDecimal>(); //客户自定义
	private Map map = new HashMap();
    private boolean isFirstLoad;
    private static final Logger logger = CoreUIObject.getLogger(PublicSetSplitRptUI.class);
}