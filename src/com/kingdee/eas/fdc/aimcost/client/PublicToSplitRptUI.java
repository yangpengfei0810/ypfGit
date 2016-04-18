/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.event.TreeSelectionEvent;
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
import com.kingdee.eas.fdc.aimcost.AimHleperFacadeFactory;
import com.kingdee.eas.fdc.aimcost.ApportionTypesEnum;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryFactory;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade;
import com.kingdee.eas.fdc.aimcost.PublicSetSplitFacadeFactory;
import com.kingdee.eas.fdc.aimcost.PublicToSplitCollection;
import com.kingdee.eas.fdc.aimcost.PublicToSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.PublicToSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.PublicToSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.PublicToSplitFacadeFactory;
import com.kingdee.eas.fdc.aimcost.PublicToSplitFactory;
import com.kingdee.eas.fdc.aimcost.PublicToSplitInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 公共配套待发生成本分摊
 */
public class PublicToSplitRptUI extends AbstractPublicToSplitRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(PublicToSplitRptUI.class);
    KDBizPromptBox f7SplitType = new KDBizPromptBox();// 分摊类型F7  -biaobiao
    Map map =new HashMap();
    public PublicToSplitRptUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception {
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
    
    //初始化表格
	private void initTable(){
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
//	    tblMain.getColumn("apportionType").setEditor(appTypeEditor);
	    
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
	    
	    //尚需支出
	    tblMain.getColumn(KEY_needSpendingAmt).getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
        tblMain.getColumn(KEY_needSpendingAmt).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
        //调差
        tblMain.getColumn(KEY_adjustmentDiffAmt).getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
        tblMain.getColumn(KEY_adjustmentDiffAmt).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	    tblMain.setColumnMoveable(true);
		//当分摊方式发生变化时重新计算分摊金额
	    tblMain.addKDTEditListener(new KDTEditAdapter(){
		    public void editStopped(KDTEditEvent e) {
		    	if(e.getColIndex()==4){
		    		//-biaobiao
		    		if(e.getValue() instanceof ApportionTypeInfo){
		    			ApportionTypeInfo apportionTypeInfo =(ApportionTypeInfo) e.getValue();
		    			int count=tblMain.getColumnCount();
		    			int rowIndex=e.getRowIndex();
		    			IRow row=tblMain.getRow(rowIndex);
		    			BigDecimal needAmt=BigDecimal.ZERO;//尚需支出
		    			BigDecimal diffAmt=BigDecimal.ZERO;//调差
		    			needAmt=FDCHelper.toBigDecimal(row.getCell(KEY_needSpendingAmt).getValue());
		    			diffAmt=FDCHelper.toBigDecimal(row.getCell(KEY_adjustmentDiffAmt).getValue());
		    			//指定分摊
		    			try {
							apportionTypeInfo= ApportionTypeFactory.getRemoteInstance().getApportionTypeInfo(new ObjectUuidPK(apportionTypeInfo.getId()));
						} catch (Exception e1) {
							e1.printStackTrace();
						} 
		    			if(apportionTypeInfo.getName().equals(ApportionTypesEnum.BYCUSTOM.toString())){
		    				for(int i=5;i<count ;i++){
		    					row.getCell(i).getStyleAttributes().setLocked(false);//分摊金额可以编辑
		    					row.getCell(i).setValue(BigDecimal.ZERO);
		    				}
		    			}else{
		    				for(int i=5;i<count;i++){
		    					row.getCell(i).getStyleAttributes().setLocked(true);
		    					String key=tblMain.getColumn(i).getKey();
		    					String proTypeID=null;//产品类型ID
		    					//公共配套调差成本分摊
		    					if(key.endsWith(KEY_adjustmentDiffAmt)){
		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_adjustmentDiffAmt));
		    					}
		    					//公共配套尚需支出
		    					if(key.endsWith(KEY_needSpendingAmt)){
		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_needSpendingAmt));
		    					}
		    					if(map.containsKey(proTypeID)){
		    						Map maptemp = (Map)map.get(proTypeID);
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
	    								System.out.println(en);
	    								Map objectMap  = (Map)map.get(key2);
	    								
	    								Object val = objectMap.get(apportionTypeInfo.getId().toString());//map中的key	
	    								if(val != null){
	    									totalSumAreaa = totalSumAreaa.add(new BigDecimal(val.toString()));
	    								}
	    							}
		    						if(totalSumAreaa.compareTo(BigDecimal.ZERO)!=0){
		    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
//		    							System.out.println("map + :"+map);
		    							
		    							if(key.endsWith(KEY_adjustmentDiffAmt)){
					    					splitAmt=diffAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));
		    							}
		    							if(key.endsWith(KEY_needSpendingAmt)){
		    								splitAmt=needAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));
		    							}
		    							row.getCell(key).setValue(splitAmt);
		    						}else
		    							row.getCell(key).setValue(BigDecimal.ZERO);
		    					}else{
		    						row.getCell(key).setValue(BigDecimal.ZERO);
		    					}
		    				}
		    			}
		    		}
//		    			//按建筑面积
//		    			else if(apportionTypeInfo.getName().equals(ApportionTypesEnum.BYBUILDAREA.toString())){
//		    				for(int i=5;i<count;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(true);
//		    					String key=tblMain.getColumn(i).getKey();
//		    					String proTypeID=null;//产品类型ID
//		    					//公共配套调差成本分摊
//		    					if(key.endsWith(KEY_adjustmentDiffAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_adjustmentDiffAmt));
//		    					}
//		    					//公共配套尚需支出
//		    					if(key.endsWith(KEY_needSpendingAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_needSpendingAmt));
//		    					}
//		    					if(map.containsKey(proTypeID)){
//		    						Map maptemp = (Map)map.get(proTypeID);
//		    						BigDecimal tempSellArea = BigDecimal.ZERO;
//		    						if(maptemp.get(apportionTypeInfo.getId().toString()) != null){
//		    							tempSellArea = (BigDecimal) maptemp.get(apportionTypeInfo.getId().toString());
//		    						}
//		    						BigDecimal totalSumAreaa = BigDecimal.ZERO;
//		    						Iterator ite = map.entrySet().iterator();	
//	    							while(ite.hasNext()){	
//	    								Map.Entry en =  (Entry) ite.next();
//	    								Object key2 = en.getKey();
//	    								System.out.println(en);
//	    								Map objectMap  = (Map)map.get(key2);
//	    								
//	    								Object val = objectMap.get(apportionTypeInfo.getId().toString());//map中的key	
//	    								if(val != null){
//	    									totalSumAreaa = totalSumAreaa.add(new BigDecimal(val.toString()));
//	    								}
//	    							}
//		    						if(totalSumAreaa.compareTo(BigDecimal.ZERO)!=0){
//		    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
//		    							System.out.println("map + :"+map);
//		    							//根据分摊方式汇总指标
//		    							
//		    							if(key.endsWith(KEY_adjustmentDiffAmt)){
//					    					splitAmt=diffAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							if(key.endsWith(KEY_needSpendingAmt)){
//		    								splitAmt=needAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							row.getCell(key).setValue(splitAmt);
//		    						}else
//		    							row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}else{
//		    						row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}
////		    					if(buildMap.containsKey(proTypeID)){
////		    						BigDecimal tempBuildArea=buildMap.get(proTypeID);//该产品类型的建筑面积
////		    						if(totalBuildArea.compareTo(BigDecimal.ZERO)!=0){
////		    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
////		    							if(key.endsWith(KEY_adjustmentDiffAmt)){
////		    								splitAmt=diffAmt.multiply(tempBuildArea.divide(totalBuildArea,12,BigDecimal.ROUND_HALF_UP));
////		    							}
////		    							if(key.endsWith(KEY_needSpendingAmt)){
////		    								splitAmt=needAmt.multiply(tempBuildArea.divide(totalBuildArea,12,BigDecimal.ROUND_HALF_UP));
////		    							}
////		    							row.getCell(key).setValue(splitAmt);
////		    						}else
////		    							row.getCell(key).setValue(BigDecimal.ZERO);
////		    					}
//		    				}
//		    			}
//		    			//按占地面积
//		    			else if(apportionTypeInfo.getName().equals(ApportionTypesEnum.BYCONTAINAREA.toString())){
//		    				for(int i=5;i<count;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(true);
//		    					String key=tblMain.getColumn(i).getKey();
//		    					String proTypeID=null;//产品类型ID
//		    					//公共配套调差成本分摊
//		    					if(key.endsWith(KEY_adjustmentDiffAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_adjustmentDiffAmt));
//		    					}
//		    					//公共配套尚需支出
//		    					if(key.endsWith(KEY_needSpendingAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_needSpendingAmt));
//		    					}
//		    					if(map.containsKey(proTypeID)){
//		    						Map maptemp = (Map)map.get(proTypeID);
//		    						BigDecimal tempSellArea = BigDecimal.ZERO;
//		    						if(maptemp.get(apportionTypeInfo.getId().toString()) != null){
//		    							tempSellArea = (BigDecimal) maptemp.get(apportionTypeInfo.getId().toString());
//		    						}
//		    						if(totalSumArea.compareTo(BigDecimal.ZERO)!=0){
//		    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
//		    							BigDecimal totalSumAreaa = BigDecimal.ZERO;
//		    							System.out.println("map + :"+map);
//		    							//根据分摊方式汇总指标
//		    							Iterator ite = map.entrySet().iterator();	
//		    							while(ite.hasNext()){	
//		    								Map.Entry en =  (Entry) ite.next();
//		    								Object key2 = en.getKey();
//		    								System.out.println(en);
//		    								Map objectMap  = (Map)map.get(key2);
//		    								
//		    								Object val = objectMap.get(apportionTypeInfo.getId().toString());//map中的key	
//		    								if(val != null){
//		    									totalSumAreaa = totalSumAreaa.add(new BigDecimal(val.toString()));
//		    								}
//		    							}
//		    							if(key.endsWith(KEY_adjustmentDiffAmt)){
//					    					splitAmt=diffAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							if(key.endsWith(KEY_needSpendingAmt)){
//		    								splitAmt=needAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							row.getCell(key).setValue(splitAmt);
//		    						}else
//		    							row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}else{
//		    						row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}
////		    					if(containMap.containsKey(proTypeID)){
////		    						BigDecimal tempContainArea=containMap.get(proTypeID);//该产品类型的建筑面积
////		    						if(totalContainArea.compareTo(BigDecimal.ZERO)!=0){
////		    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
////		    							if(key.endsWith(KEY_adjustmentDiffAmt)){
////		    								splitAmt=diffAmt.multiply(tempContainArea.divide(totalContainArea,12,BigDecimal.ROUND_HALF_UP));
////		    							}
////		    							if(key.endsWith(KEY_needSpendingAmt)){
////		    								splitAmt=needAmt.multiply(tempContainArea.divide(totalContainArea,12,BigDecimal.ROUND_HALF_UP));
////		    							}
////		    							row.getCell(key).setValue(splitAmt);
////		    						}else
////		    							row.getCell(key).setValue(BigDecimal.ZERO);
////		    					}
//		    				}
//		    			}
//		    			//按可售面积
//		    			else if(apportionTypeInfo.getName().equals(ApportionTypesEnum.BYSELLAREA.toString())){
//		    				for(int i=5;i<count;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(true);
//		    					String key=tblMain.getColumn(i).getKey();
//		    					String proTypeID=null;//产品类型ID
//		    					//公共配套调差成本分摊
//		    					if(key.endsWith(KEY_adjustmentDiffAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_adjustmentDiffAmt));
//		    					}
//		    					//公共配套尚需支出
//		    					if(key.endsWith(KEY_needSpendingAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_needSpendingAmt));
//		    					}
//		    					if(map.containsKey(proTypeID)){
//		    						Map maptemp = (Map)map.get(proTypeID);
//		    						BigDecimal tempSellArea = BigDecimal.ZERO;
//		    						if(maptemp.get(apportionTypeInfo.getId().toString()) != null){
//		    							tempSellArea = (BigDecimal) maptemp.get(apportionTypeInfo.getId().toString());
//		    						}
//		    						if(totalSumArea.compareTo(BigDecimal.ZERO)!=0){
//		    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
//		    							BigDecimal totalSumAreaa = BigDecimal.ZERO;
//		    							System.out.println("map + :"+map);
//		    							//根据分摊方式汇总指标
//		    							Iterator ite = map.entrySet().iterator();	
//		    							while(ite.hasNext()){	
//		    								Map.Entry en =  (Entry) ite.next();
//		    								Object key2 = en.getKey();
//		    								System.out.println(en);
//		    								Map objectMap  = (Map)map.get(key2);
//		    								
//		    								Object val = objectMap.get(apportionTypeInfo.getId().toString());//map中的key	
//		    								if(val != null){
//		    									totalSumAreaa = totalSumAreaa.add(new BigDecimal(val.toString()));
//		    								}
//		    							}
//		    							if(key.endsWith(KEY_adjustmentDiffAmt)){
//					    					splitAmt=diffAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							if(key.endsWith(KEY_needSpendingAmt)){
//		    								splitAmt=needAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							row.getCell(key).setValue(splitAmt);
//		    						}else
//		    							row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}else{
//		    						row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}
////		    					if(sellMap.containsKey(proTypeID)){
////		    						BigDecimal tempSellArea=sellMap.get(proTypeID);//该产品类型的建筑面积
////		    						if(totalBuildArea.compareTo(BigDecimal.ZERO)!=0){
////		    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
////		    							if(key.endsWith(KEY_adjustmentDiffAmt)){
////		    								splitAmt=diffAmt.multiply(tempSellArea.divide(totalSellArea,12,BigDecimal.ROUND_HALF_UP));
////		    							}
////		    							if(key.endsWith(KEY_needSpendingAmt)){
////		    								splitAmt=needAmt.multiply(tempSellArea.divide(totalSellArea,12,BigDecimal.ROUND_HALF_UP));
////		    							}
////		    							row.getCell(key).setValue(splitAmt);
////		    						}else
////		    							row.getCell(key).setValue(BigDecimal.ZERO);
////		    					}
//		    				}
//		    			}else{//--biaobiao
//		    				//判断分摊方式是否在自定义表里
//		    					if(isCustomPlanindex(apportionTypeInfo)){
//		    						for(int i=5;i<count;i++){
//				    					row.getCell(i).getStyleAttributes().setLocked(true);
//				    					String key=tblMain.getColumn(i).getKey();
//				    					String proTypeID=null;//产品类型ID
//				    					//公共配套调差成本分摊
//				    					if(key.endsWith(KEY_adjustmentDiffAmt)){
//				    						proTypeID=key.substring(0,key.lastIndexOf(KEY_adjustmentDiffAmt));
//				    					}
//				    					//公共配套尚需支出
//				    					if(key.endsWith(KEY_needSpendingAmt)){
//				    						proTypeID=key.substring(0,key.lastIndexOf(KEY_needSpendingAmt));
//				    					}
//				    					if(map.containsKey(proTypeID)){
//				    						Map maptemp = (Map)map.get(proTypeID);
//				    						BigDecimal tempSellArea = BigDecimal.ZERO;
//				    						if(maptemp.get(apportionTypeInfo.getId().toString()) != null){
//				    							tempSellArea = (BigDecimal) maptemp.get(apportionTypeInfo.getId().toString());
//				    						}
//				    						if(totalSumArea.compareTo(BigDecimal.ZERO)!=0){
//				    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
//				    							BigDecimal totalSumAreaa = BigDecimal.ZERO;
//				    							System.out.println("map + :"+map);
//				    							//根据分摊方式汇总指标
//				    							Iterator ite = map.entrySet().iterator();	
//				    							while(ite.hasNext()){	
//				    								Map.Entry en =  (Entry) ite.next();
//				    								Object key2 = en.getKey();
//				    								System.out.println(en);
//				    								Map objectMap  = (Map)map.get(key2);
//				    								
//				    								Object val = objectMap.get(apportionTypeInfo.getId().toString());//map中的key	
//				    								if(val != null){
//				    									totalSumAreaa = totalSumAreaa.add(new BigDecimal(val.toString()));
//				    								}
//				    							}
//				    							if(key.endsWith(KEY_adjustmentDiffAmt)){
//							    					splitAmt=diffAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));
//				    							}
//				    							if(key.endsWith(KEY_needSpendingAmt)){
//				    								splitAmt=needAmt.multiply(tempSellArea.divide(totalSumAreaa,12,BigDecimal.ROUND_HALF_UP));
//				    							}
//				    							row.getCell(key).setValue(splitAmt);
//				    						}else
//				    							row.getCell(key).setValue(BigDecimal.ZERO);
//				    					}else{
//				    						row.getCell(key).setValue(BigDecimal.ZERO);
//				    					}
//				    					
//				    					
////				    					if(customMap.containsKey(proTypeID)){
////				    						BigDecimal tempSellArea=customMap.get(proTypeID);//该产品类型的建筑面积
////				    						if(totalSumArea.compareTo(BigDecimal.ZERO)!=0){
////				    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
////				    							if(key.endsWith(KEY_adjustmentDiffAmt)){
////				    								splitAmt=diffAmt.multiply(tempSellArea.divide(totalSumArea,12,BigDecimal.ROUND_HALF_UP));
////				    							}
////				    							if(key.endsWith(KEY_needSpendingAmt)){
////				    								splitAmt=needAmt.multiply(tempSellArea.divide(totalSumArea,12,BigDecimal.ROUND_HALF_UP));
////				    							}
////				    							row.getCell(key).setValue(splitAmt);
////				    						}else
////				    							row.getCell(key).setValue(BigDecimal.ZERO);
////				    					}else{
////				    						row.getCell(key).setValue(BigDecimal.ZERO);
////				    					}
//		    						}
//		    					}
//		    			}
//		    		}
		    		
//		    		if(e.getValue() instanceof ApportionTypesEnum){
//		    			ApportionTypesEnum typesEnum=(ApportionTypesEnum) e.getValue();
//		    			int count=tblMain.getColumnCount();
//		    			int colIndex=e.getColIndex();
//		    			int rowIndex=e.getRowIndex();
//		    			IRow row=tblMain.getRow(rowIndex);
//		    			BigDecimal needAmt=BigDecimal.ZERO;//尚需支出
//		    			BigDecimal diffAmt=BigDecimal.ZERO;//调差
//		    			needAmt=FDCHelper.toBigDecimal(row.getCell(KEY_needSpendingAmt).getValue());
//		    			diffAmt=FDCHelper.toBigDecimal(row.getCell(KEY_adjustmentDiffAmt).getValue());
//		    			//指定分摊
//		    			if(typesEnum.getValue().equals(ApportionTypesEnum.BYCUSTOM_VALUE)){
//		    				for(int i=5;i<count ;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(false);//分摊金额可以编辑
//		    					row.getCell(i).setValue(BigDecimal.ZERO);
//		    				}
//		    			}
//		    			//按建筑面积
//		    			if(typesEnum.getValue().equals(ApportionTypesEnum.BYBUILDAREA_VALUE)){
//		    				for(int i=5;i<count;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(true);
//		    					String key=tblMain.getColumn(i).getKey();
//		    					String proTypeID=null;//产品类型ID
//		    					//公共配套调差成本分摊
//		    					if(key.endsWith(KEY_adjustmentDiffAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_adjustmentDiffAmt));
//		    					}
//		    					//公共配套尚需支出
//		    					if(key.endsWith(KEY_needSpendingAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_needSpendingAmt));
//		    					}
//		    					if(buildMap.containsKey(proTypeID)){
//		    						BigDecimal tempBuildArea=buildMap.get(proTypeID);//该产品类型的建筑面积
//		    						if(totalBuildArea.compareTo(BigDecimal.ZERO)!=0){
//		    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
//		    							if(key.endsWith(KEY_adjustmentDiffAmt)){
//		    								splitAmt=diffAmt.multiply(tempBuildArea.divide(totalBuildArea,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							if(key.endsWith(KEY_needSpendingAmt)){
//		    								splitAmt=needAmt.multiply(tempBuildArea.divide(totalBuildArea,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							row.getCell(key).setValue(splitAmt);
//		    						}else
//		    							row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}
//		    				}
//		    			}
//		    			//按占地面积
//		    			if(typesEnum.getValue().equals(ApportionTypesEnum.BYCONTAINAREA_VALUE)){
//		    				for(int i=5;i<count;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(true);
//		    					String key=tblMain.getColumn(i).getKey();
//		    					String proTypeID=null;//产品类型ID
//		    					//公共配套调差成本分摊
//		    					if(key.endsWith(KEY_adjustmentDiffAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_adjustmentDiffAmt));
//		    					}
//		    					//公共配套尚需支出
//		    					if(key.endsWith(KEY_needSpendingAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_needSpendingAmt));
//		    					}
//		    					if(containMap.containsKey(proTypeID)){
//		    						BigDecimal tempContainArea=containMap.get(proTypeID);//该产品类型的建筑面积
//		    						if(totalContainArea.compareTo(BigDecimal.ZERO)!=0){
//		    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
//		    							if(key.endsWith(KEY_adjustmentDiffAmt)){
//		    								splitAmt=diffAmt.multiply(tempContainArea.divide(totalContainArea,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							if(key.endsWith(KEY_needSpendingAmt)){
//		    								splitAmt=needAmt.multiply(tempContainArea.divide(totalContainArea,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							row.getCell(key).setValue(splitAmt);
//		    						}else
//		    							row.getCell(key).setValue(BigDecimal.ZERO);
//		    					}
//		    				}
//		    			}
//		    			//按可售面积
//		    			if(typesEnum.getValue().equals(ApportionTypesEnum.BYSELLAREA_VALUE)){
//		    				for(int i=5;i<count;i++){
//		    					row.getCell(i).getStyleAttributes().setLocked(true);
//		    					String key=tblMain.getColumn(i).getKey();
//		    					String proTypeID=null;//产品类型ID
//		    					//公共配套调差成本分摊
//		    					if(key.endsWith(KEY_adjustmentDiffAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_adjustmentDiffAmt));
//		    					}
//		    					//公共配套尚需支出
//		    					if(key.endsWith(KEY_needSpendingAmt)){
//		    						proTypeID=key.substring(0,key.lastIndexOf(KEY_needSpendingAmt));
//		    					}
//		    					if(sellMap.containsKey(proTypeID)){
//		    						BigDecimal tempSellArea=sellMap.get(proTypeID);//该产品类型的建筑面积
//		    						if(totalBuildArea.compareTo(BigDecimal.ZERO)!=0){
//		    							BigDecimal splitAmt=BigDecimal.ZERO;//分配金额
//		    							if(key.endsWith(KEY_adjustmentDiffAmt)){
//		    								splitAmt=diffAmt.multiply(tempSellArea.divide(totalSellArea,12,BigDecimal.ROUND_HALF_UP));
//		    							}
//		    							if(key.endsWith(KEY_needSpendingAmt)){
//		    								splitAmt=needAmt.multiply(tempSellArea.divide(totalSellArea,12,BigDecimal.ROUND_HALF_UP));
//		    							}
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
	    
	  //判断选择的分摊方式是否自定义指标表里的
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
			} //planIndexInfo.getCustomEntrys();//自定义指标
//			
			return false;
			
		}
	/**
	 * 单元格是否可编辑
	 */
	private void setSellEditable(){
		tblMain.getStyleAttributes().setLocked(true);
		for(int i=0;i<tblMain.getRowCount()-1;i++){
			IRow row=tblMain.getRow(i);
			row.getCell("apportionType").getStyleAttributes().setLocked(false);
			//如果分摊方式为“指定分摊”
			Object typeEnum=row.getCell("apportionType").getValue();  //- biaobiao 
			if(typeEnum instanceof ApportionTypeInfo){
				ApportionTypeInfo apportionTypeInfo;
				try {
					apportionTypeInfo = ApportionTypeFactory.getRemoteInstance().getApportionTypeInfo(new ObjectUuidPK(((ApportionTypeInfo) typeEnum).getId()));
					if(apportionTypeInfo.getName().equals(ApportionTypesEnum.BYCUSTOM_VALUE.toString())){
						for(int j=5;j<tblMain.getColumnCount();j++){
							row.getCell(j).getStyleAttributes().setLocked(false);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
					
			}
			if(typeEnum instanceof ApportionTypesEnum){
				if(((ApportionTypesEnum)typeEnum).getValue().equals(ApportionTypesEnum.BYCUSTOM_VALUE)){
					for(int j=5;j<tblMain.getColumnCount();j++){
						row.getCell(j).getStyleAttributes().setLocked(false);
					}
				}
				
			}
		}
		if(tblMain.getRowCount()>=1){
			//合计行不许编辑
			tblMain.getRow(tblMain.getRowCount()-1).getStyleAttributes().setLocked(true);
		}
	}
	
    
    private DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeProject.getLastSelectedPathComponent();
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
	
	protected void treeProject_valueChanged(TreeSelectionEvent e)throws Exception {
		String curProjectID=getCurProjectID();//当前工程项目ID
    	if("".equals(curProjectID)){
			return;
		}
    	//重置表格列
    	resetTableCol(curProjectID);
    	//表格行数据
    	getTableRow(curProjectID);
    	setSumRow();	//合计行
    	addFormatType();//列金额输入控制
    	setSellEditable();
    }
    
    private void addFormatType(){
    	KDFormattedTextField formattedTextField=new KDFormattedTextField();
    	formattedTextField.setVisible(true);
    	formattedTextField.setHorizontalAlignment(2);
    	formattedTextField.setDataType(1);
    	formattedTextField.setPrecision(2);
    	formattedTextField.setName("amt");
    	for(int i=5;i<tblMain.getColumnCount();i++){
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
    	if(tblMain.getCell(rowCount-1,1).getValue().equals("合计")){
    		tblMain.removeRow(rowCount-1);
    	}
    	footRow=tblMain.addRow();//临时的合计行
	    footRow.getCell(1).setValue("合计");
	    footRow.getStyleAttributes().setLocked(true);
		footRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		for(int i=5;i<tblMain.getColumnCount();i++){
			BigDecimal tempAmt=BigDecimal.ZERO;
			for(int j=0;j<tblMain.getRowCount()-1;j++){
				tempAmt=tempAmt.add(FDCHelper.toBigDecimal((tblMain.getCell(j, i).getValue())));
			}
			footRow.getCell(i).setValue(tempAmt);
		}
		BigDecimal totalDiffAmt=BigDecimal.ZERO; 
		for(int i=0;i<tblMain.getRowCount()-1;i++){
			totalDiffAmt=totalDiffAmt.add(FDCHelper.toBigDecimal(tblMain.getCell(i,KEY_adjustmentDiffAmt).getValue()));
		}
		BigDecimal totalNeedAmt=BigDecimal.ZERO; 
		for(int i=0;i<tblMain.getRowCount()-1;i++){
			totalNeedAmt=totalNeedAmt.add(FDCHelper.toBigDecimal(tblMain.getCell(i,KEY_needSpendingAmt).getValue()));
		}
		footRow.getCell(KEY_adjustmentDiffAmt).setValue(totalDiffAmt);
		footRow.getCell(KEY_needSpendingAmt).setValue(totalNeedAmt);
		footRow.getStyleAttributes().setLocked(true);
    }
	
	private void resetTableCol(String curProjectID) throws Exception{
		int colCount=tblMain.getColumnCount();
		for(int i=0;i<colCount-5;i++){
			tblMain.removeColumn(5);
		}
		//初始化
		totalContainArea=BigDecimal.ZERO;
		totalSellArea=BigDecimal.ZERO;
		totalBuildArea=BigDecimal.ZERO;
		totalSumArea = BigDecimal.ZERO;//-biaobiao
		containMap.clear();
		sellMap.clear();
		buildMap.clear();
//		Map<String,String> colMap=new TreeMap<String, String>();
		Map<String,String> colMap=new LinkedHashMap<String, String>();
		colMap.put(KEY_needSpendingAmt, "公共配套尚需支出成本分摊");//公共配套尚需支出成本分摊
		colMap.put(KEY_adjustmentDiffAmt, "公共配套调差成本分摊");//公共配套调差成本分摊
		Set<String> colKeys=colMap.keySet();
		IRowSet rowSet=PublicToSplitFacadeFactory.getRemoteInstance().getPublicToSplitColums(curProjectID);
		//-biaobioa
		
		map = AimHleperFacadeFactory.getRemoteInstance().getApportionIndex(curProjectID);
		String sql = "select proType.Fname_L2,proType.fid as fid,cus.fisproduct,cus.findexValue as cusval  from T_AIM_CustomPlanIndexEntry cus  left join t_fdc_producttype proType on cus.fproductTypeid = proType.FID  left join t_aim_planindex plx on plx.fid = cus.fparentid  left join t_aim_measurecost mct on mct.fid=plx.fheadid where mct.fprojectid='"+curProjectID+"' and mct.Fislastversion='1' order by proType.fname_l2 desc ";
		ISQLExecutor executor = SQLExecutorFactory.getRemoteInstance(sql);
		IRowSet rs = executor.executeSQL();
    	while(rs.next()){
    		String productID=rs.getString("fid");
    		BigDecimal sellArea=rs.getBigDecimal("cusval");//可售面积
    		customMap.put(productID, sellArea);
    		if(sellArea==null){
    			sellArea=BigDecimal.ZERO;
			}
    		totalSumArea = totalSumArea.add(sellArea);
    	}	
		//生成动态列
		Iterator<String> iterator=colKeys.iterator();
		while(iterator.hasNext()){
			String colKey=iterator.next();
			int index=0; //记录列数
			int indexCount=tblMain.getColumnCount();//当前表格的列数
			while(rowSet.next()){
				index++;
				String productID=rowSet.getString("fid");
				String productName=rowSet.getString("fname_l2");
				String key=productID+colKey;
				IColumn column=tblMain.addColumn();
				column.setKey(key);
				column.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
		        column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				tblMain.getHeadRow(0).getCell(key).setValue(colMap.get(colKey));
				tblMain.getHeadRow(1).getCell(key).setValue(productName);
			}
			tblMain.getHeadMergeManager().mergeBlock(0,indexCount,0,indexCount+index-1);
			rowSet.beforeFirst();
		}
		tblMain.setColumnMoveable(true);
		//计算分摊的产品的面积等
		rowSet.beforeFirst();
		while(rowSet.next()){
			String productID=rowSet.getString("fid");
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
			containMap.put(productID, containArea);
			sellMap.put(productID, sellArea);
			buildMap.put(productID, buildarea);
			totalContainArea=totalContainArea.add(containArea);
			totalSellArea=totalSellArea.add(sellArea);
			totalBuildArea=totalBuildArea.add(buildarea);
		}
	}
	
	//取不分摊的产品类型
	private void getTableRow(String curProjID) throws Exception{
		tblMain.removeRows();
		IPublicToSplitFacade facade=PublicToSplitFacadeFactory.getRemoteInstance();
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
		//加载尚需支出和调差
		IRowSet amtRowSet=facade.getRowData(curProjID);
		amtRowSet.beforeFirst();
		while(amtRowSet.next()){
			String proID=amtRowSet.getString("proTypeID");
			for(int i=0;i<tblMain.getRowCount();i++){
				IRow row=tblMain.getRow(i);
				String key=((ProductTypeInfo)row.getCell("public").getValue()).getId().toString();
				if(key.equals(proID)){
					row.getCell(KEY_adjustmentDiffAmt).setValue(FDCHelper.toBigDecimal(amtRowSet.getBigDecimal("adjAmt")));
					row.getCell(KEY_needSpendingAmt).setValue(FDCHelper.toBigDecimal(amtRowSet.getBigDecimal("needAmt")));
				}
			}
		}
		//如果数据库里有数据则加载出上次的分摊金额 和 分摊类型
		FilterInfo filterInfo=new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("curProjectID",curProjID));
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("product.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		EntityViewInfo viewInfo=new EntityViewInfo();
		viewInfo.setFilter(filterInfo);
		viewInfo.setSelector(sic);
		PublicToSplitCollection  splitCollection=PublicToSplitFactory.getRemoteInstance().getPublicToSplitCollection(viewInfo);
  		if(splitCollection.size()>0){
			for(int i=0;i<splitCollection.size();i++){
				PublicToSplitInfo splitInfo=splitCollection.get(i);
				for(int j=0;j<tblMain.getRowCount();j++){
					IRow row=tblMain.getRow(j);
					String prokey=((ProductTypeInfo)row.getCell("public").getValue()).getId().toString();
					//-biaobiao edit
					if(prokey.equals(((ProductTypeInfo)splitInfo.getProduct()).getId().toString())){
						if(splitInfo.getApportionTypeF7() != null ){
							ApportionTypeInfo typeInfo =ApportionTypeFactory.getRemoteInstance().getApportionTypeInfo(new ObjectUuidPK(splitInfo.getApportionTypeF7().getId()));
							row.getCell("apportionType").setValue(typeInfo);
						}
//						row.getCell("apportionType").setValue(splitInfo.getApportionType());
						PublicToSplitEntryCollection entryCollection=splitInfo.getEntrys();
						if(entryCollection.size()>0){
							for(int k=0;k<entryCollection.size();k++){
								PublicToSplitEntryInfo entryInfo=entryCollection.get(k);
								String pruductID=entryInfo.getProduct().getId().toString();
								for(int m=5;m<tblMain.getColumnCount();m++){
									if(tblMain.getColumn(m).getKey().equals(pruductID+KEY_adjustmentDiffAmt)){
										row.getCell(m).setValue(entryInfo.getSplitDiffAmt());
									}
									if(tblMain.getColumn(m).getKey().equals(pruductID+KEY_needSpendingAmt)){
										row.getCell(m).setValue(entryInfo.getSplitNeedAmt());
									}
								}
							}
						}
					}
				}
			}
  		}
	}
	
	/**
	 * 保存
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		 checkTableInfo();
		 getTableDataCollection();
	}
	
	private void getTableDataCollection() throws Exception{
		PublicToSplitCollection collection=new PublicToSplitCollection();
		IRow row=null;
		int count=tblMain.getRowCount();
		String projectID=getCurProjectID();
		//获取到“分摊”的产品类型
		IRowSet splitSet=PublicSetSplitFacadeFactory.getRemoteInstance().getPublicSetSplitColums(projectID);
		//清空数据表
		PublicToSplitFacadeFactory.getRemoteInstance().initDBTable(projectID);
		for(int i=0;i<count-1;i++){
			row=tblMain.getRow(i);
			PublicToSplitInfo info=new PublicToSplitInfo();
			info.setCurProjectID(projectID);
			info.setProduct((ProductTypeInfo) row.getCell("public").getValue());
			Object needObject=row.getCell(KEY_needSpendingAmt).getValue();//尚需支出
			Object diffObject=row.getCell(KEY_adjustmentDiffAmt).getValue();//调差
			info.setNeedSpendingAmt(FDCHelper.toBigDecimal(needObject));
			info.setAdjustmentDiffAmt(FDCHelper.toBigDecimal(diffObject));
			if(row.getCell("apportionType").getValue() != null &&  row.getCell("apportionType").getValue() instanceof ApportionTypeInfo){//-biaobiao 
				info.setApportionTypeF7((ApportionTypeInfo) row.getCell("apportionType").getValue());
			}else if(row.getCell("apportionType").getValue() != null &&  row.getCell("apportionType").getValue() instanceof ApportionTypeInfo){
				info.setApportionType((ApportionTypesEnum) row.getCell("apportionType").getValue());
			}
			String parentID=PublicToSplitFactory.getRemoteInstance().save(info).toString();
			PublicToSplitInfo splitInfo=PublicToSplitFactory.getRemoteInstance().getPublicToSplitInfo(new ObjectUuidPK(parentID));
			//分录
			PublicToSplitEntryCollection entryCollection=new PublicToSplitEntryCollection();
			splitSet.beforeFirst();
			while(splitSet.next()){
				String tempProductID=splitSet.getString("fid");
				PublicToSplitEntryInfo entryInfo=new PublicToSplitEntryInfo();
				String splitProductID=((ProductTypeInfo)row.getCell("public").getValue()).getId().toString();
				entryInfo.setSplitProductID(splitProductID);//被分摊的产品类型
				for(int j=5;j<tblMain.getColumnCount();j++){
					String key=tblMain.getColumn(j).getKey();
					if(key.equals(tempProductID+KEY_adjustmentDiffAmt)){
						entryInfo.setSplitDiffAmt(FDCHelper.toBigDecimal(row.getCell(key).getValue()));//分摊到的调差金额
					}
					if(key.equals(tempProductID+KEY_needSpendingAmt)){
						entryInfo.setSplitNeedAmt(FDCHelper.toBigDecimal(row.getCell(key).getValue()));//分摊到的尚需支出
					}
				}
				ProductTypeInfo typeInfo=ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(tempProductID));
				entryInfo.setProduct(typeInfo);//产品类型
				entryInfo.setParent(splitInfo);
				PublicToSplitEntryFactory.getRemoteInstance().save(entryInfo);
			}
			
		}
		this.setMessageText("保存成功");
		this.showMessage();
	}
	
	//保存前校验信息
	private void checkTableInfo(){
		int colCount=tblMain.getColumnCount();
		int rowCount=tblMain.getRowCount();
		for(int i=0;i<rowCount-1;i++){
//			for(int j=1;j<colCount;j++){
//				Object object=tblMain.getCell(i,j).getValue();
//				//表格的单元格不能有空
//				if(object==null || object.toString().equals("")){
//					MsgBox.showError("第"+i+"行，第"+j+"列上的数据为空！");
//					SysUtil.abort();
//				}
//			}
			IRow row=tblMain.getRow(i);
			//各产品类型分摊到的金额之和不能大于被分摊金额
			BigDecimal diffAmt=FDCHelper.toBigDecimal((row.getCell(KEY_adjustmentDiffAmt).getValue()));//调差
			BigDecimal needAmt=FDCHelper.toBigDecimal((row.getCell(KEY_needSpendingAmt).getValue()));//尚需支出
			BigDecimal tempDiffAmt=BigDecimal.ZERO;
			BigDecimal tempNeedAmt=BigDecimal.ZERO;
			for(int k=5;k<colCount;k++){
				IColumn col=tblMain.getColumn(k);
				//调差
				if(col.getKey().endsWith(KEY_adjustmentDiffAmt)){
					tempDiffAmt=tempDiffAmt.add(FDCHelper.toBigDecimal(row.getCell(k).getValue()));
				}
				//尚需支出
				if(col.getKey().endsWith(KEY_needSpendingAmt)){
					tempNeedAmt=tempNeedAmt.add(FDCHelper.toBigDecimal(row.getCell(k).getValue()));
				}
				
			}
			if(!checkDiff(diffAmt,tempDiffAmt)){
				MsgBox.showError("第"+(i+1)+"行，调差成本分摊合计金额不等于分摊前的调差金额。请重新分摊！");//各产品分摊金额之和必须等于调差成本！");
				SysUtil.abort();
			}
			if(!checkDiff(needAmt,tempNeedAmt)){
				MsgBox.showError("第"+(i+1)+"行，尚需支出成本分摊合计金额不等于分摊前的尚需支出金额。请重新分摊！");//各产品分摊金额之和必须等于尚需支出成本！");
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
	
	public static String KEY_needSpendingAmt="needSpendingAmt";//尚需支出
	public static String KEY_adjustmentDiffAmt="adjustmentDiffAmt";//调差
	private BigDecimal totalContainArea;//产品类型的总占地面积
	private BigDecimal totalSellArea;//总可售面积
	private BigDecimal totalBuildArea;//总建筑面积
	private BigDecimal totalSumArea; //自定义总面积
	private Map<String, BigDecimal> containMap=new TreeMap<String,BigDecimal>(); //按占地面积
	private Map<String, BigDecimal> buildMap=new TreeMap<String,BigDecimal>(); //按建筑面积
	private Map<String, BigDecimal> sellMap=new TreeMap<String,BigDecimal>(); //按可售面积
	private Map<String, BigDecimal> customMap=new TreeMap<String,BigDecimal>(); //客户自定义
}