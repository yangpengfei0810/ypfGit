package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetCollection;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetFactory;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;

public class SaleUnilaterallyTable {
	
	private KDTable table;
    private AimMeasureCostEditUICTEx measureCostEditUI;
    private PlanIndexInfo planIndexInfo;
    private PlanIndexEntryCollection entrys;
    private boolean isBuildPartLogic;
    private BigDecimal allBuildArea;
    private BigDecimal allSellArea;
    private KDTDefaultCellEditor editor;
    private HashMap defaultCostAcctMap;

    public SaleUnilaterallyTable(AimMeasureCostEditUICTEx measureCostEditUI)
        throws EASBizException, BOSException
    {
        table = null;
        this.measureCostEditUI = null;
        planIndexInfo = null;
        entrys = null;
        isBuildPartLogic = false;
        allBuildArea = FDCHelper.ZERO;
        allSellArea = FDCHelper.ZERO;
        editor = null;
        defaultCostAcctMap = null;
        this.measureCostEditUI = measureCostEditUI;
        isBuildPartLogic = measureCostEditUI.isBuildPartLogic();
        table = new KDTable();
        table.setName("可售单方汇总表");
        table.getStyleAttributes().setLocked(true);
        
        table.addKDTEditListener(new KDTEditAdapter() {
            public void editStopped(KDTEditEvent e) {
                tableEditStopped(e);
            }
        });
    }

    private PlanIndexEntryCollection getEntrys(PlanIndexInfo planIndexInfo)
    {
        allSellArea = PlanIndexTable.getAllSellArea(planIndexInfo);
        allBuildArea = PlanIndexTable.getAllBuildArea(planIndexInfo);
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

        return entrys;
    }

    private boolean isBuildPartLogic()
    {
        return isBuildPartLogic;
    }

    private BigDecimal getAllBuildArea()
    {
        return allBuildArea;
    }

    private BigDecimal getAllSellArea()
    {
        return allSellArea;
    }

    private BigDecimal getCustomIndex(AimMeasureCostEditUICTEx.Item item, String productId)
    {
        BigDecimal value = FDCHelper.ZERO;
        CustomPlanIndexEntryInfo entry = measureCostEditUI.getPlanIndexTable().getCustomPlanIndexEntryInfo(item.key, productId);
        if(entry != null)
            value = entry.getValue();
        return value;
    }

    private BigDecimal getCustomSumIndex()
    {
        BigDecimal sum = FDCHelper.ZERO;
        CustomPlanIndexEntryCollection entrys = measureCostEditUI.getPlanIndexTable().getCustomPlanIndexs("productId");
        for(Iterator iter = entrys.iterator(); iter.hasNext();)
        {
            CustomPlanIndexEntryInfo entry = (CustomPlanIndexEntryInfo)iter.next();
            sum = FDCHelper.add(sum, entry.getValue());
        }

        return sum;
    }

    private BigDecimal getSumArea(String splitType)
    {
        BigDecimal sum = FDCHelper.ZERO;
        for(int i = 0; i < entrys.size(); i++)
            if(entrys.get(i).isIsSplit())
                sum = sum.add(FDCHelper.toBigDecimal(entrys.get(i).getBigDecimal(splitType)));

        return sum;
    }

    public KDTable getTable()
    {
        return table;
    }

    private void initTable()
    {
        if(entrys == null)
            return;
        table.removeColumns();
        int entrysSize = entrys.size();
        int colums = entrysSize * 2 + 5;
        table.createBlankTable(colums, 2, 0);
        table.getColumn(0).setKey("acctNumber");
        table.getColumn(1).setKey("acctName");
        int base = 2;
//        for(int i = 0; i < entrysSize; i++)
//        {
//            table.getColumn(base + i).setKey("buildAvg" + i);
//            FDCHelper.formatTableNumber(table, "buildAvg" + i);
//        }
//
//        table.getColumn(base + entrysSize).setKey("buildAvg");
//        FDCHelper.formatTableNumber(table, "buildAvg");
//        base = base + entrysSize + 1;
        for(int i = 0; i < entrysSize; i++)
        {
            table.getColumn(base + i).setKey("avg" + i);
            FDCHelper.formatTableNumber(table, "avg" + i);
            
//            //隐藏公共配套产品
//			PlanIndexEntryInfo info = entrys.get(i);
//			if(!info.isIsSplit()){
//				table.getColumn(base + i).getStyleAttributes().setHided(true);
//			}
        }

        table.getColumn(base + entrysSize).setKey("avg");
        FDCHelper.formatTableNumber(table, "avg");
        
        base = base + entrysSize + 1;
        for(int i = 0; i < entrysSize; i++)
        {
            table.getColumn(base + i).setKey("split" + i);
            FDCHelper.formatTableNumber(table, "split" + i);
        }

        table.getColumn(base + entrysSize).setKey("total");
        FDCHelper.formatTableNumber(table, "total");
        table.getColumn(colums - 1).setKey("splitType");
        table.getColumn(colums - 1).getStyleAttributes().setHided(true);
        IRow headRow = table.getHeadRow(0);
        headRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.CENTER);
        headRow.getCell("acctNumber").setValue("科目编码");
        headRow.getCell("acctName").setValue("科目名称");
//        headRow.getCell("buildAvg").setValue("建筑面积单位成本");
        headRow.getCell("avg").setValue("可售面积单位成本");
        headRow.getCell("total").setValue("总成本");
        headRow.getCell("splitType").setValue("分配方式");
        headRow = table.getHeadRow(1);
        headRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.CENTER);
        for(int i = 0; i < entrysSize; i++)
        {
            String name = entrys.get(i).getProduct().getName();
//            headRow.getCell("buildAvg" + i).setValue(name);
            headRow.getCell("avg" + i).setValue(name);
            headRow.getCell("split" + i).setValue(name);
            headRow.getCell("split" + i).setUserObject(entrys.get(i).getProduct().getId().toString());
        }

//        headRow.getCell("buildAvg").setValue("平均");
        headRow.getCell("avg").setValue("平均");
        headRow.getCell("total").setValue("合计");
        KDTMergeManager mm = table.getHeadMergeManager();
        
//        mm.mergeBlock(0, 0, 1, 0);
//        mm.mergeBlock(0, 1, 1, 1);
//        mm.mergeBlock(0, colums - 1, 1, colums - 1);
//        mm.mergeBlock(0, 2, 0, 2 + entrysSize);
//        mm.mergeBlock(0, 2 + entrysSize + 1, 0, 2 + entrysSize * 2 + 1);
//        mm.mergeBlock(0, 2 + entrysSize * 2 + 2, 0, colums - 2);
//        table.getViewManager().setFreezeView(-1, 2);
        
     // 科目编码
		mm.mergeBlock(0, 0, 1, 0);
		// 科目名称
		mm.mergeBlock(0, 1, 1, 1);
		// 分配方式
		mm.mergeBlock(0, colums - 1, 1, colums - 1);
		// 建筑面积单位成本
		mm.mergeBlock(0, 2, 0, 2 + entrysSize);
		// 可售面积单位成本(加1是因为建筑面积单位成本多了平均列，总成本加2类同 by hpw)
//		mm.mergeBlock(0, 2 + entrysSize + 1, 0, 2 + entrysSize * 2 + 1);
		// 总成本
//		mm.mergeBlock(0, 2 + entrysSize * 2 + 2, 0, colums - 2);
		mm.mergeBlock(0, 2 + entrysSize + 1, 0, 2 + entrysSize * 2 + 1);
		// table.getColumn("splitType").setEditor(measureCostEditUI.getIndexEditor(CostAccountTypeEnum.MAIN,
		// null));
		// 冻结
		table.getViewManager().setFreezeView(-1, 2);
    }

    private void fillTable()
    {
        TreeModel costAcctTree = measureCostEditUI.getCostAcctTree();
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
            fillNode(child);
        }

        String oprtState = measureCostEditUI.getOprtState();
        if(oprtState != null && !oprtState.equals(OprtState.ADDNEW) && !oprtState.equals(OprtState.EDIT))
        {
            for(int i = 0; i < table.getRowCount(); i++)
                if(table.getCell(i, "splitType").getEditor() != null && table.getCell(i, "splitType").getEditor().getComponent() != null)
                    table.getCell(i, "splitType").getEditor().getComponent().setEnabled(false);

        }
    }

    private void fillNode(DefaultMutableTreeNode node)
    {
        CostAccountInfo costAcct = (CostAccountInfo)node.getUserObject();
        if(costAcct == null)
            return;
        IRow row = table.addRow();
        row.setTreeLevel(node.getLevel() - 1);
        row.setUserObject(costAcct);
        String longNumber = costAcct.getLongNumber();
        longNumber = longNumber.replace('!', '.');
        row.getCell("acctNumber").setValue(longNumber);
        row.getCell("acctName").setValue(costAcct.getName());
        
        if("4001.04".equals(longNumber)){
        	int base = entrys.size()+1;
        	BigDecimal allTotal = BigDecimal.ZERO;
        	for (int i = base; i < table.getColumnCount(); i++) {
        		Map splitDataMap = measureCostEditUI.getSplitData();
    			IRow headRow = table.getHeadRow(1);
    			ICell cell = headRow.getCell("split" + (i - base));
    			if(cell != null){
        			String key = cell.getUserObject().toString();
        			if(splitDataMap.containsKey(key)){
        				BigDecimal amount = (BigDecimal) splitDataMap.get(key);
        				row.getCell("split" + (i - base)).setValue(amount);
        				
        				allTotal =allTotal.add(amount);
        				
        				 for (int j = 0; j < entrys.size(); j++) {
							PlanIndexEntryInfo entry = entrys.get(j);
							ProductTypeInfo  productInfo = entry.getProduct();
							if(productInfo instanceof ProductTypeInfo && productInfo.getId() != null && productInfo.getId().toString().equals(key)){
								BigDecimal sellArea = entry.getSellArea();
								if(sellArea != null)
			                    {
			                        BigDecimal avg = amount.divide(sellArea, 2, 4);
			                        row.getCell("avg" + (i - base)).setValue(avg);
			                    }
							}
						}
        			}
    			}
			}
        	row.getCell("total").setValue(allTotal);
        	
//			BigDecimal allTotal = FDCHelper.ZERO;
//			costAcct.setIsLeaf(true);
//			row.setUserObject(costAcct);
			
//			if(j >= base && j < base * 2 - 1){
//    			Map splitDataMap = measureCostEditUI.getSplitData();
//    			IRow headRow = table.getHeadRow(1);
//    			ICell cell = headRow.getCell("split" + (j - base));
//    			if(cell != null){
//        			String key = cell.getUserObject().toString();
//        			if(splitDataMap.containsKey(key)){
//        				BigDecimal amount = (BigDecimal) splitDataMap.get(key);
//        				sum = FDCHelper.add(sum, amount);
////        				row.getCell("split" + j).setValue(amount);
////        				allTotal = allTotal.add(amount);
//        			}
//    			}
//    			total = FDCHelper.add(total, sum);
//			}
        }
        
        if(node.isLeaf())
        {
            loadRow(row);
        } else
        {
            for(int i = 0; i < node.getChildCount(); i++)
                fillNode((DefaultMutableTreeNode)node.getChildAt(i));

        }
    }

    private void loadRow(IRow row)
    {
        Object obj = row.getUserObject();
        if(!(obj instanceof CostAccountInfo))
            return;
        CostAccountInfo costAcct = (CostAccountInfo)obj;
        String key = costAcct.getId().toString();
        Map measureCostMap = measureCostEditUI.getMeasureCostMap();
        if(costAcct.getType() == CostAccountTypeEnum.SIX)
        {
            MeasureEntryCollection coll = (MeasureEntryCollection)measureCostMap.get(key);
            String splitType = null;
            row.getCell("splitType").setEditor(getCollectSplitTypeEditor());
            row.getCell("splitType").getStyleAttributes().setLocked(false);
            row.getCell("splitType").getStyleAttributes().setLocked(false);
            if(coll != null && coll.size() > 0)
            {
                BigDecimal total = FDCHelper.ZERO;
                for(int i = 0; i < coll.size(); i++)
                {
                    MeasureEntryInfo info = coll.get(i);
                    total = total.add(FDCHelper.toBigDecimal(info.getAmount()));
                    if(splitType == null)
                        splitType = info.getNumber();
                }

                if(total.signum() == 0)
                    return;
                row.getCell("total").setValue(total);
                if(splitType != null)
                    if(isManSplit(splitType))
                    {
                        manSplit(splitType, row);
                    } else
                    {
                        row.getCell("splitType").setValue(getSelectItem(splitType));
                        splitSIX(row);
                    }
            }
            if(splitType == null)
                setDefaultSplitType(row);
        } else
        {
            row.getCell("splitType").setValue("直接归属");
            BigDecimal allTotal = FDCHelper.ZERO;
            BigDecimal allSellArea = FDCHelper.ZERO;
            BigDecimal allBuildArea = FDCHelper.ZERO;
            for(int i = 0; i < entrys.size(); i++)
            {
                PlanIndexEntryInfo entry = entrys.get(i);
                BigDecimal sellArea = entry.getSellArea();
                BigDecimal buildArea = entry.getBuildArea();
                if(FDCHelper.toBigDecimal(sellArea).compareTo(FDCHelper.ZERO) == 0)
                    sellArea = null;
                if(FDCHelper.toBigDecimal(buildArea).compareTo(FDCHelper.ZERO) == 0)
                    buildArea = null;
                String productKey = key + entry.getProduct().getId().toString();
                MeasureEntryCollection coll = (MeasureEntryCollection)measureCostMap.get(productKey);
                if(coll != null && coll.size() > 0)
                {
                    BigDecimal total = FDCHelper.ZERO;
                    for(int j = 0; j < coll.size(); j++)
                    {
                        MeasureEntryInfo info = coll.get(j);
                        if(info != null)
                            total = total.add(FDCHelper.toBigDecimal(info.getAmount()));
                    }

                    allTotal = allTotal.add(total);
                    row.getCell("split" + i).setValue(total);
                    if(sellArea != null)
                    {
                        BigDecimal avg = total.divide(sellArea, 2, 4);
                        row.getCell("avg" + i).setValue(avg);
                    }
//                    if(buildArea != null)
//                    {
//                        BigDecimal avg = total.divide(buildArea, 2, 4);
//                        row.getCell("buildAvg" + i).setValue(avg);
//                    }
                }
                if(entrys.get(i).getType() == null || entrys.get(i).getType() != PlanIndexTypeEnum.parking);
            }

            if(allTotal.compareTo(FDCHelper.ZERO) == 0)
                return;
            row.getCell("total").setValue(allTotal);
        }
    }

    public void refresh()
    {
        planIndexInfo = measureCostEditUI.getPlanIndexTable().getPlanIndexInfo();
        entrys = getEntrys(planIndexInfo);
        measureCostEditUI.refreshMeasureCostMap();
        editor = measureCostEditUI.getCollectIndexEditor();
        initTable();
        fillTable();
        setUnionData();
    }

    KDTDefaultCellEditor getCollectSplitTypeEditor()
    {
        if(editor != null)
        {
            return editor;
        } else
        {
            editor = measureCostEditUI.getCollectIndexEditor();
            return editor;
        }
    }

    KDTDefaultCellEditor getSplitTypeEditor()
    {
        if(editor != null)
        {
            return editor;
        } else
        {
            Object items[] = AimMeasureCostEditUICTEx.Item.SPLITITEMS;
            KDComboBox box = new KDComboBox(items);
            editor = new KDTDefaultCellEditor(box);
            return editor;
        }
    }

    private AimMeasureCostEditUICTEx.Item getSelectItem(String key)
    {
        if(editor == null)
            return null;
        KDComboBox box = (KDComboBox)editor.getComponent();
        for(int i = 0; i < box.getItemCount(); i++)
        {
        	AimMeasureCostEditUICTEx.Item item = (AimMeasureCostEditUICTEx.Item)box.getItemAt(i);
            if(key.equals(item.key))
                return item;
        }

        return null;
    }

    public void setUnionData() {
        String cols[] = new String[entrys.size() * 2 + 2];
        for(int i = 0; i < entrys.size(); i++) {
        	cols[i] = "avg" + i;
//            cols[i] = "buildAvg" + i;
//            cols[entrys.size() + 1 + i] = "avg" + i;
            cols[entrys.size() + 1 + i] = "split" + i;
        }

        cols[entrys.size()] = "avg";
//        cols[entrys.size()] = "buildAvg";
//        cols[entrys.size() * 2 + 1] = "avg";
        cols[entrys.size() * 2 + 1] = "total";
        
        for(int i = 0; i < table.getRowCount(); i++)
        {
            IRow row = table.getRow(i);
            if(!(row.getUserObject() instanceof CostAccountInfo))
                continue;
            CostAccountInfo acct = (CostAccountInfo)row.getUserObject();
            
            if(acct.isIsLeaf())
                continue;
            int level = row.getTreeLevel();
            List aimRowList = new ArrayList();
            for(int j = i + 1; j < table.getRowCount(); j++)
            {
                IRow rowAfter = table.getRow(j);
                if(rowAfter.getTreeLevel() <= level)
                    break;
                if(!(rowAfter.getUserObject() instanceof CostAccountInfo))
                    continue;
                CostAccountInfo acctTemp = (CostAccountInfo)rowAfter.getUserObject();
                if(acctTemp.isIsLeaf())
                    aimRowList.add(rowAfter);
            }
            
            BigDecimal total = FDCHelper.ZERO;
            int base = entrys.size() + 1;
            String acctNumber = acct.getLongNumber().toString();
            for (int j = 0; j < cols.length; j++) {
            	BigDecimal sum = null;
                for(int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++)
                {
                    IRow rowAdd = (IRow)aimRowList.get(rowIndex);
                    Object value = rowAdd.getCell(cols[j]).getValue();
                    if(value != null)
                        sum = FDCHelper.toBigDecimal(sum, 2).add(FDCHelper.toBigDecimal(value, 2));
                }

                //设置公建配套的总成本,公建配套的父成本科目的总成本也要加上分摊后的金额
//        		if("4001!03!04".equals(acctNumber) || "4001!03".equals(acctNumber) || "4001".equals(acctNumber)){
//        		if("4001!07".equals(acctNumber) || "4001".equals(acctNumber)){
////        			BigDecimal allTotal = FDCHelper.ZERO;
////        			costAcct.setIsLeaf(true);
////        			row.setUserObject(costAcct);
//        			
//        			if(j >= base && j < base * 2 - 1){
//	        			Map splitDataMap = measureCostEditUI.getSplitData();
//	        			IRow headRow = table.getHeadRow(1);
//	        			ICell cell = headRow.getCell("split" + (j - base));
//	        			if(cell != null){
//		        			String key = cell.getUserObject().toString();
//		        			if(splitDataMap.containsKey(key)){
//		        				BigDecimal amount = (BigDecimal) splitDataMap.get(key);
//		        				sum = FDCHelper.add(sum, amount);
//		//        				row.getCell("split" + j).setValue(amount);
//		//        				allTotal = allTotal.add(amount);
//		        			}
//	        			}
//	        			total = FDCHelper.add(total, sum);
//        			}
//        		}
//        		//公建配套的父成本科目的总成本也要加上分摊后的金额
//        		if("4001!03".equals(acct.getLongNumber().toString())){
//        			if(j >= base && j < base * 2 - 1){
//	        			Map splitDataMap = measureCostEditUI.getSplitData();
//	        			IRow headRow = table.getHeadRow(1);
//	        			ICell cell = headRow.getCell("split" + (j - base));
//	        			if(cell != null){
//		        			String key = cell.getUserObject().toString();
//		        			if(splitDataMap.containsKey(key)){
//		        				BigDecimal amount = (BigDecimal) splitDataMap.get(key);
//		        				sum = FDCHelper.add(sum, amount);
//		        			}
//	        			}
//	        			total = FDCHelper.add(total, sum);
//        			}
//        		}
//        		//公建配套的父成本科目的总成本也要加上分摊后的金额
//        		if("4001".equals(acct.getLongNumber().toString())){
//        			if(j >= base && j < base * 2 - 1){
//	        			Map splitDataMap = measureCostEditUI.getSplitData();
//	        			IRow headRow = table.getHeadRow(1);
//	        			ICell cell = headRow.getCell("split" + (j - base));
//	        			if(cell != null){
//		        			String key = cell.getUserObject().toString();
//		        			if(splitDataMap.containsKey(key)){
//		        				BigDecimal amount = (BigDecimal) splitDataMap.get(key);
//		        				sum = FDCHelper.add(sum, amount);
//		        			}
//	        			}
//	        			total = FDCHelper.add(total, sum);
//        			}
//        		}
                row.getCell(cols[j]).setValue(sum);
			}
//            if("4001!03!04".equals(acctNumber) || "4001!03".equals(acctNumber) || "4001".equals(acctNumber)){
//            if("4001!07".equals(acctNumber) || "4001".equals(acctNumber)){
//            	row.getCell("total").setValue(total);
//            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    AimCostClientHelper.setTotalCostRow(getTable(), getColumns());
                } catch(Exception e) {
                    measureCostEditUI.handUIException(e);
                }
            }
        });
        afterSetUnionData();
    }

    private void afterSetUnionData()
    {
        BigDecimal allSellArea = getAllSellArea();
        BigDecimal allBuildArea = getAllBuildArea();
        for(int i = 0; i < table.getRowCount(); i++)
        {
            IRow row = table.getRow(i);
            if(!(row.getUserObject() instanceof CostAccountInfo))
                continue;
            BigDecimal allTotal = (BigDecimal)row.getCell("total").getValue();
            if(allTotal == null)
                continue;
            if(FDCHelper.toBigDecimal(allSellArea).compareTo(FDCHelper.ZERO) != 0)
            {
                BigDecimal avg = allTotal.divide(allSellArea, 2, 4);
                row.getCell("avg").setValue(avg);
            }
//            if(FDCHelper.toBigDecimal(allBuildArea).compareTo(FDCHelper.ZERO) != 0)
//            {
//                BigDecimal avg = allTotal.divide(allBuildArea, 2, 4);
//                row.getCell("buildAvg").setValue(avg);
//            }
        }

    }

    private void tableEditStopped(KDTEditEvent e)
    {
        Object oldValue = e.getOldValue();
        Object newValue = e.getValue();
        if(oldValue == null && newValue == null)
            return;
        if(oldValue != null && newValue != null && oldValue.equals(newValue))
            return;
        int rowIndex = e.getRowIndex();
        int columnIndex = e.getColIndex();
        IRow row = table.getRow(rowIndex);
        if(table.getColumnKey(columnIndex).equals("splitType"))
        {
            Object obj = row.getUserObject();
            if((obj instanceof CostAccountInfo) && ((CostAccountInfo)obj).getType() == CostAccountTypeEnum.SIX)
            {
                splitSIX(row);
                setUnionData();
            }
        }
        if(table.getColumnKey(columnIndex).startsWith("split") && !table.getColumnKey(columnIndex).equals("split") && !table.getColumnKey(columnIndex).equals("splitType"))
        {
            Object obj = row.getCell("splitType").getValue();
            if((obj instanceof AimMeasureCostEditUICTEx.Item) && ((AimMeasureCostEditUICTEx.Item)obj).key.equals("man"))
            {
                row.getCell(columnIndex).setValue(row.getCell(columnIndex).getValue());
                int index = Integer.parseInt(table.getColumnKey(columnIndex).substring("split".length()));
                BigDecimal sellArea = entrys.get(index).getSellArea();
                BigDecimal buildArea = entrys.get(index).getBuildArea();
                if(FDCHelper.toBigDecimal(sellArea).signum() != 0)
                    row.getCell("avg" + index).setValue(FDCHelper.toBigDecimal(newValue).divide(sellArea, 2, 4));
//                if(FDCHelper.toBigDecimal(buildArea).signum() != 0)
//                    row.getCell("buildAvg" + index).setValue(FDCHelper.toBigDecimal(newValue).divide(buildArea, 2, 4));
                setUnionData();
            }
        }
        measureCostEditUI.setDataChange(true);
    }

    public Map getSplitTypes()
    {
        Map map = new HashMap();
        for(int i = 0; i < table.getRowCount(); i++)
        {
            IRow row = table.getRow(i);
            Object value = row.getCell("splitType").getValue();
            String splitType = null;
            if(value instanceof AimMeasureCostEditUICTEx.Item)
            {
            	AimMeasureCostEditUICTEx.Item item = (AimMeasureCostEditUICTEx.Item)value;
                splitType = item.key;
            }
            if(splitType == null)
                continue;
            Object obj = row.getUserObject();
            if(!(obj instanceof CostAccountInfo))
                continue;
            CostAccountInfo acct = (CostAccountInfo)obj;
            if(!acct.isIsLeaf() || acct.getType() != CostAccountTypeEnum.SIX)
                continue;
            if(splitType.equals("man"))
            {
                for(int k = 0; k < entrys.size(); k++)
                {
                    if(!entrys.get(k).isIsSplit())
                        continue;
                    if(row.getCell("split" + k) == null)
                        break;
                    Object v = row.getCell("split" + k).getValue();
                    String productId = entrys.get(k).getProduct().getId().toString();
                    if(productId == null)
                        break;
                    splitType = splitType + "[]" + productId + "|" + FDCHelper.toBigDecimal(v).toString();
                }

            }
            map.put(acct.getId().toString(), splitType);
        }

        return map;
    }

    private void setDefaultSplitType(IRow row)
    {
        Object obj = row.getUserObject();
        if((obj instanceof CostAccountInfo) && ((CostAccountInfo)obj).isIsLeaf())
        {
            Object key = getDefaultCostAcctMap().get(((CostAccountInfo)obj).getLongNumber());
            if(key == null)
                row.getCell("splitType").setValue(null);
            else
                row.getCell("splitType").setValue(getSelectItem(key.toString()));
        }
    }

    private HashMap getDefaultCostAcctMap()
    {
        if(defaultCostAcctMap == null)
        {
            defaultCostAcctMap = new HashMap();
            EntityViewInfo view = new EntityViewInfo();
            view.getSelector().add("costAccount.longNumber");
            view.getSelector().add("splitType");
            try
            {
                MeasureDefaultSplitTypeSetCollection c = MeasureDefaultSplitTypeSetFactory.getRemoteInstance().getMeasureDefaultSplitTypeSetCollection(view);
                MeasureDefaultSplitTypeSetInfo info;
                for(Iterator iter = c.iterator(); iter.hasNext(); defaultCostAcctMap.put(info.getCostAccount().getLongNumber(), info.getSplitType()))
                    info = (MeasureDefaultSplitTypeSetInfo)iter.next();

            }
            catch(Exception e)
            {
                measureCostEditUI.handUIException(e);
            }
        }
        return defaultCostAcctMap;
    }

    private int getProductColumnIndex(String productId)
    {
        IRow headRow = table.getHeadRow(0);
        for(int i = 0; i < getTable().getColumnCount(); i++)
            if(headRow.getCell(i) != null && headRow.getCell(i).getValue() != null && headRow.getCell(i).getValue().equals(productId))
                return i;

        return -1;
    }

    private String getColumnProdcutId(String key)
    {
        IRow headRow = table.getHeadRow(1);
        if(headRow.getCell(key) != null && headRow.getCell(key).getUserObject() != null)
            return (String)headRow.getCell(key).getUserObject();
        else
            return null;
    }

    private void splitSIX(IRow row)
    {
        Object value = row.getCell("splitType").getValue();
        if(!(value instanceof AimMeasureCostEditUICTEx.Item))
            return;
        String splitType = ((AimMeasureCostEditUICTEx.Item)value).key;
        value = row.getCell("total").getValue();
        BigDecimal total = FDCHelper.toBigDecimal(value);
        if(total.signum() == 0 || splitType == null)
        {
            for(int i = 0; i < entrys.size(); i++)
            {
//                row.getCell("buildAvg" + i).setValue(null);
                row.getCell("avg" + i).setValue(null);
                row.getCell("split" + i).setValue(null);
            }

//            row.getCell("buildAvg").setValue(null);
            row.getCell("avg").setValue(null);
            row.getCell("total").setValue(null);
            return;
        }
        Object obj = row.getCell("splitType").getValue();
        boolean isManSplit = isManSplit(splitType);
        boolean isCustomIndex = ((AimMeasureCostEditUICTEx.Item)obj).isCustomIndex();
        if(isManSplit)
        {
            manSplit(splitType, row);
            for(int i = 0; i < entrys.size(); i++)
            {
//                row.getCell("buildAvg" + i).setValue(null);
                row.getCell("avg" + i).setValue(null);
                row.getCell("split" + i).setValue(null);
            }

        } else
        if(isCustomIndex)
        {
            BigDecimal sum = getCustomSumIndex();
            BigDecimal tmp = FDCHelper.ZERO;
            int lastIndex = 0;
            for(int i = 0; i < entrys.size(); i++)
                if(!entrys.get(i).isIsSplit())
                {
                    row.getCell("split" + i).setValue(null);
                    row.getCell("avg" + i).setValue(null);
//                    row.getCell("buildAvg" + i).setValue(null);
                    if(i == entrys.size() - 1)
                    {
                        BigDecimal dif = total.subtract(tmp);
                        if(lastIndex > 0)
                        {
                            BigDecimal amount = FDCHelper.toBigDecimal(row.getCell(lastIndex).getValue());
                            amount = amount.add(dif);
                            row.getCell(lastIndex).setValue(amount);
                        }
                    }
                } else
                {
                    BigDecimal splitAmt = FDCHelper.ZERO;
                    BigDecimal amt = FDCHelper.ZERO;
                    if(i == entrys.size() - 1)
                    {
                        splitAmt = total.subtract(tmp);
                    } else
                    {
                        lastIndex = table.getColumnIndex("split" + i);
                        String productId = entrys.get(i).getProduct().getId().toString();
                        amt = getCustomIndex((AimMeasureCostEditUICTEx.Item)obj, productId);
                        splitAmt = FDCHelper.toBigDecimal(amt).multiply(total).divide(sum, 2, 4);
                        tmp = tmp.add(splitAmt);
                    }
                    row.getCell("split" + i).setValue(splitAmt);
                    amt = FDCHelper.toBigDecimal(entrys.get(i).getSellArea());
                    if(amt.compareTo(FDCHelper.ZERO) != 0)
                    {
                        BigDecimal avgAmt = splitAmt.divide(amt, 2, 4);
                        row.getCell("avg" + i).setValue(avgAmt);
                        amt = FDCHelper.toBigDecimal(entrys.get(i).getBuildArea());
                        if(amt.compareTo(FDCHelper.ZERO) != 0)
                        {
                            avgAmt = splitAmt.divide(amt, 2, 4);
//                            row.getCell("buildAvg" + i).setValue(avgAmt);
                        }
                    }
                }

        } else
        {
            BigDecimal sum = getSumArea(splitType);
            if(sum.signum() > 0)
            {
                BigDecimal tmp = FDCHelper.ZERO;
                int lastIndex = 0;
                for(int i = 0; i < entrys.size(); i++)
                    if(!entrys.get(i).isIsSplit())
                    {
                        row.getCell("split" + i).setValue(null);
                        row.getCell("avg" + i).setValue(null);
//                        row.getCell("buildAvg" + i).setValue(null);
                        if(i == entrys.size() - 1)
                        {
                            BigDecimal dif = total.subtract(tmp);
                            if(lastIndex > 0)
                            {
                                BigDecimal amount = FDCHelper.toBigDecimal(row.getCell(lastIndex).getValue());
                                amount = amount.add(dif);
                                row.getCell(lastIndex).setValue(amount);
                            }
                        }
                    } else
                    {
                        BigDecimal splitAmt = FDCHelper.ZERO;
                        BigDecimal amt = FDCHelper.ZERO;
                        if(i == entrys.size() - 1)
                        {
                            splitAmt = total.subtract(tmp);
                        } else
                        {
                            lastIndex = table.getColumnIndex("split" + i);
                            amt = entrys.get(i).getBigDecimal(splitType);
                            splitAmt = FDCHelper.toBigDecimal(amt).multiply(total).divide(sum, 2, 4);
                            tmp = tmp.add(splitAmt);
                        }
                        row.getCell("split" + i).setValue(splitAmt);
                        amt = FDCHelper.toBigDecimal(entrys.get(i).getSellArea());
                        if(amt.compareTo(FDCHelper.ZERO) != 0)
                        {
                            BigDecimal avgAmt = splitAmt.divide(amt, 2, 4);
                            row.getCell("avg" + i).setValue(avgAmt);
                            amt = FDCHelper.toBigDecimal(entrys.get(i).getBuildArea());
                            if(amt.compareTo(FDCHelper.ZERO) != 0)
                            {
                                avgAmt = splitAmt.divide(amt, 2, 4);
//                                row.getCell("buildAvg" + i).setValue(avgAmt);
                            }
                        }
                    }

            } else
            {
                for(int i = 0; i < entrys.size(); i++)
                {
//                    row.getCell("buildAvg" + i).setValue(null);
                    row.getCell("avg" + i).setValue(null);
                    row.getCell("split" + i).setValue(null);
                }

            }
            BigDecimal sumSellArea = getAllSellArea();
            BigDecimal sumBuildArea = getAllBuildArea();
            if(sumSellArea.signum() != 0)
                row.getCell("avg").setValue(total.divide(sumSellArea, 2, 4));
//            if(sumBuildArea.signum() != 0)
//                row.getCell("buildAvg").setValue(total.divide(sumBuildArea, 2, 4));
        }
    }

    private void manSplit(String splitType, IRow row)
    {
        String split[] = splitType.split("\\[\\]");
        row.getCell("splitType").setValue(getSelectItem(split[0]));
        if(split.length > 1)
        {
            HashMap map = new HashMap();
            for(int i = 1; i < split.length; i++)
            {
                String s[] = split[i].split("\\|");
                if(s.length == 2)
                    map.put(s[0], s[1]);
            }

            for(int i = 0; i < entrys.size(); i++)
            {
                BigDecimal splitAmt = FDCHelper.toBigDecimal(map.get(getColumnProdcutId("split" + i)));
                row.getCell("split" + i).setValue(splitAmt);
                row.getCell("split" + i).getStyleAttributes().setLocked(false);
                BigDecimal amt = FDCHelper.toBigDecimal(entrys.get(i).getSellArea());
                if(amt.compareTo(FDCHelper.ZERO) == 0)
                    continue;
                BigDecimal avgAmt = splitAmt.divide(amt, 2, 4);
                row.getCell("avg" + i).setValue(avgAmt);
                amt = FDCHelper.toBigDecimal(entrys.get(i).getBuildArea());
                if(amt.compareTo(FDCHelper.ZERO) != 0)
                {
                    avgAmt = splitAmt.divide(amt, 2, 4);
//                    row.getCell("buildAvg" + i).setValue(avgAmt);
                }
            }

        }
        BigDecimal sumSellArea = FDCHelper.toBigDecimal(getAllSellArea());
        BigDecimal sumBuildArea = FDCHelper.toBigDecimal(getAllBuildArea());
        BigDecimal total = FDCHelper.toBigDecimal(row.getCell("total").getValue());
        if(sumSellArea.compareTo(FDCHelper.ZERO) != 0)
            row.getCell("avg").setValue(total.divide(sumSellArea, 2, 4));
        if(sumBuildArea.compareTo(FDCHelper.ZERO) != 0)
//            row.getCell("buildAvg").setValue(total.divide(sumBuildArea, 2, 4));
        for(int i = 0; i < entrys.size(); i++)
        {
            String oprtState = measureCostEditUI.getOprtState();
            if(oprtState != null && !oprtState.equals(OprtState.ADDNEW) && !oprtState.equals(OprtState.EDIT))
            {
                row.getCell("split" + i).getStyleAttributes().setLocked(true);
                continue;
            }
            if(!entrys.get(i).isIsSplit())
                row.getCell("split" + i).getStyleAttributes().setLocked(true);
            else
                row.getCell("split" + i).getStyleAttributes().setLocked(false);
        }

    }

    private boolean isManSplit(String splitType)
    {
        return splitType.startsWith("man");
    }

    private String[] getColumns()
    {
        /*int size = 1;
        if(entrys != null && entrys.size() > 0)
            size += entrys.size();
        String columns[] = new String[size];
        columns[0] = "total";
        for(int i = 1; i < size; i++)
            columns[i] = "split" + (i - 1);*/

        int size = 1;
    	int j=1;
        if(entrys != null && entrys.size() > 0){
            size += entrys.size();
        }
        String columns[] = new String[size*2];
        //总成本中的合计
        columns[0] = "total";
        for(int i = 1; i < size; i++){
            columns[i] = "split" + (i - 1);
            j++;
        }
        //可售单方成本 中的合计
        columns[j] = "avg";
        for(int i = 1; i < size; i++){
            columns[i+j] = "avg" + (i - 1);
        }
        return columns;
    }

    void clear()
    {
        java.util.EventListener listeners[] = table.getListeners(com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener.class);
        for(int i = 0; i < listeners.length; i++)
            table.removeKDTEditListener((KDTEditListener)listeners[i]);

        listeners = table.getListeners(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener.class);
        for(int i = 0; i < listeners.length; i++)
            table.removeKDTMouseListener((KDTMouseListener)listeners[i]);

        table.setBeforeAction(null);
        table.setAfterAction(null);
        table.removeAll();
        editor = null;
        table = null;
        measureCostEditUI = null;
        planIndexInfo = null;
        entrys = null;
    }
}