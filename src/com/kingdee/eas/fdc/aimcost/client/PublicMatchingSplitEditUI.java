/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.*;
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

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.PreChangeEvent;
import com.kingdee.bos.ctrl.swing.event.PreChangeListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.ApportionTypesEnum;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.aimcost.TargetCostSplitCollection;
import com.kingdee.eas.fdc.aimcost.TargetCostSplitFactory;
import com.kingdee.eas.fdc.aimcost.TargetCostSplitInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class PublicMatchingSplitEditUI extends AbstractPublicMatchingSplitEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PublicMatchingSplitEditUI.class);
    
    private AimMeasureCostEditUICTEx measureCostEditUI;
    KDComboBox cmbSplitType = new KDComboBox();
    KDBizPromptBox f7SplitType = new KDBizPromptBox();// ��̯����F7  -biaobiao
    MeasureCostInfo measureCostInfo = null;
	PlanIndexInfo planIndexInfo = null;
	PlanIndexEntryCollection planIndexEntrys = null;
	MeasureCostInfo cost = null;
	Set keySet = new HashSet();  //��̯�Ĳ�Ʒ����ID
	ProductTypeCollection splitProducts = new ProductTypeCollection();     //��̯�Ĳ�Ʒ���ͣ����۲�Ʒ
	ProductTypeCollection notSplitProducts = new ProductTypeCollection();  //����̯�Ĳ�Ʒ���ͣ���������
	
	BigDecimal targerCost = null;  //��������Ŀ��ɱ��ϼ�
	BigDecimal targerCostNot = null;  //��������Ŀ��ɱ��ϼ�
    
    public PublicMatchingSplitEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	if("VIEW".equals(oprtState)){
    		btnSave.setEnabled(false);
    	}
    	Map uiContext = this.getUIContext();
    	measureCostInfo = (MeasureCostInfo) uiContext.get("measureCostInfo");
    	planIndexInfo = (PlanIndexInfo) uiContext.get("planIndexInfo");
    	planIndexEntrys = (PlanIndexEntryCollection) uiContext.get("planIndexEntrys");
    	measureCostEditUI = (AimMeasureCostEditUICTEx) uiContext.get("measureCostEditUI");
    	cost = (MeasureCostInfo)uiContext.get("editData");
    }
  //2013.04.17
    /**
     * �����ָ����̯������ʾ�ϴη�̯�Ľ�����������¼���
     */
    public void onShow() throws Exception {
    	super.onShow();
    	initTable();
    	fillTable();
    	
    	appendFootRow1();
    	appendFootRow2();
    	String en = "" ;
    	for (int i = 0; i < kdtSplit.getRowCount(); i++) {
    		IRow row = kdtSplit.getRow(i);
    		
    		ApportionTypeInfo enu = (ApportionTypeInfo) publicProduct.get(((ProductTypeInfo)row.getUserObject()).getId().toString());
    		if(enu != null ){
    			enu = ApportionTypeFactory.getRemoteInstance().getApportionTypeInfo(new ObjectUuidPK(enu.getId()));
    			//�������
    			if (ApportionTypesEnum.BYBUILDAREA.toString().equals(enu.getName().toString())) {
    				calculatorSplit("buildArea", row);
    			}
    			//�������
    			if (ApportionTypesEnum.BYSELLAREA.toString().equals(enu.getName().toString())) {
    				calculatorSplit("saleArea", row);
    			}
    			//ռ�����
    			if (ApportionTypesEnum.BYCONTAINAREA.toString().equals(enu.getName().toString())) {
    				calculatorSplit("containArea", row);
    			}
    			//�Զ���ָ��
    			calculatorSplitByCustomer(row, enu);
    			if(null != enu.getName().toString()){
    				calculatorResult();
    			}
    			row.getCell("splitType").setValue(enu);
    		}
    		
    		if(null == enu ){
    			en = ApportionTypesEnum.BYCUSTOM.toString();
    			ApportionTypeInfo typeInfo = ApportionTypeFactory.getRemoteInstance().getApportionTypeInfo("where name ='"+en+"'");
    			enu = typeInfo;
    			row.getCell("splitType").setValue(typeInfo);
    		}
//			row.getCell("splitType").setValue(enu);
			
			//ָ����̯
			if(enu != null && ApportionTypesEnum.BYCUSTOM.toString().equals(enu.getName().toString())){
				TargetCostSplitCollection coll = cost.getTargetCostSplit();
				
				ProductTypeInfo rowPublicProduct = (ProductTypeInfo) row.getCell("publicMatching").getValue();  //��ǰ�еĹ�������
				int columns = kdtSplit.getColumnCount();
				for(int j = 4;j<columns;j++){
					String key = kdtSplit.getColumnKey(j);  //�������׷�̯�Ĳ�ƷID
					//ȡ���ϴη�̯�Ľ��
					for (int k = 0; k < coll.size(); k++) {
						TargetCostSplitInfo info = coll.get(k);
						if(info.getId() != null){
//							System.out.println("inf_-------------:"+info);
							info = TargetCostSplitFactory.getRemoteInstance().getTargetCostSplitInfo(new ObjectUuidPK(info.getId()));
						}
						ProductTypeInfo publicProduct = info.getPublicProduct();
						ProductTypeInfo productType = info.getProductType();
//						System.out.println("j:"+j+"--------k:"+k);
						if(publicProduct != null && rowPublicProduct != null && rowPublicProduct.getId().equals(publicProduct.getId()) 
								&& productType != null && key.equals(productType.getId().toString())){
							row.getCell(j).setValue(info.getAmount());
						}
					}
					//�ǲ鿴״̬�����Ա༭
					if(!"VIEW".equals(oprtState)){
						row.getCell(j).getStyleAttributes().setLocked(false);
//						row.getCell(j).setValue(null);
					}
				}
			}
			//�������
			if (ApportionTypesEnum.BYBUILDAREA.equals(enu)) {
				calculatorSplit("buildArea", row);
			}
			//�������
			if (ApportionTypesEnum.BYSELLAREA.equals(enu)) {
				calculatorSplit("saleArea", row);
			}
			//ռ�����
			if (ApportionTypesEnum.BYCONTAINAREA.equals(enu)) {
				calculatorSplit("containArea", row);
			}
			calculatorSplitByCustomer(row, enu);
			if(null != cmbSplitType.getSelectedItem()){
				calculatorResult();
			}
			
		}
    	
    }

    //��ȡ�Զ���ָ��
	private void calculatorSplitByCustomer(IRow row, ApportionTypeInfo enu) {
		BigDecimal targerCost = FDCHelper.toBigDecimal(row.getCell("targerCost").getValue());  //Ŀ��ɱ�
		if(targerCost!=null){
		BigDecimal xxArea = null;
		ApportionTypeInfo apportionTypeInfo = (ApportionTypeInfo)enu;//��̯��ʽ
		if(apportionTypeInfo != null && isCustomPlanindex(apportionTypeInfo)){
			CustomPlanIndexEntryCollection customEntrys = planIndexInfo.getCustomEntrys();//�Զ���ָ��
			//����Զ���ָ����ķ�̯��ʽ �� Ŀ��ɱ���ġ���̯��ʽ���� �Զ���ָ����� "��Ʒ���� "�� Ŀ��ɱ����"��������"��� ����ȡ�Զ���ָ�����ָ��ֵ
			for (int a = 0; a < splitProducts.size(); a++) {
				ProductTypeInfo productType = splitProducts.get(a);
				row.getCell(productType.getId().toString()).setValue(new BigDecimal(0));
				for (int j = 0; j < customEntrys.size(); j++) {
					CustomPlanIndexEntryInfo planIndexEntryInfo = customEntrys.get(j);
					//�����̯��ʽ�Ͳ�Ʒ������ȣ���ȡ�Զ�������ָ��ֵ
					if(productType.getId().toString().equals(planIndexEntryInfo.getProductType().getId().toString()) && 
							planIndexEntryInfo.getApportType().getId().equals(apportionTypeInfo.getId())){
						xxArea = FDCHelper.toBigDecimal(planIndexEntryInfo.getValue());//ָ��ֵ
						}
					}
				if(xxArea!= null){
					BigDecimal customPlanIndexEntryTotal = getCustomPlanIndexEntryTotal(apportionTypeInfo);
					BigDecimal splitAmt=FDCHelper.multiply(targerCost, FDCHelper.divide(xxArea, customPlanIndexEntryTotal,12,BigDecimal.ROUND_HALF_UP));
					row.getCell(productType.getId().toString()).setValue(splitAmt);
					xxArea = null;
				}
				}
		}
		}
	}

    protected void kdtSplit_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
    	calculatorResult();
    }

    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	verify();
    	cost.getTargetCostSplit().clear();
    	IRow row = null;
    	for (int i = 0; i < kdtSplit.getRowCount(); i++) {
			row = kdtSplit.getRow(i);  
			for (int j = 4; j < kdtSplit.getColumnCount(); j++) {
				TargetCostSplitInfo info = new TargetCostSplitInfo();
				info.setPublicProduct((ProductTypeInfo) row.getUserObject());
				String key = kdtSplit.getColumnKey(j);
				info.setProductType((ProductTypeInfo)kdtSplit.getColumn(j).getUserObject());
//				info.setSplitType((ApportionTypesEnum) row.getCell("splitType").getValue());
				info.setSplitTypeF7((ApportionTypeInfo)row.getCell("splitType").getValue());//-biaobiao
				info.setAmount(FDCHelper.toBigDecimal(row.getCell(key).getValue()));
				cost.getTargetCostSplit().add(info);
//				TargetCostSplitCollection targetCostSplit = cost.getTargetCostSplit();
//				CoreBaseCollection baseCollection  = new CoreBaseCollection();
//				
//				TargetCostSplitFactory.getRemoteInstance().save(baseCollection);
			}
		}
    	FDCMsgBox.showInfo("����ɹ�");
    }
    private void verify()
    {   
    	IRow row = null;
    	for (int i = 0; i < kdtSplit.getRowCount(); i++) {
			row = kdtSplit.getRow(i);  
			BigDecimal targerCost = FDCHelper.toBigDecimal(row.getCell("targerCost").getValue());
			String publicMatching = ((ProductTypeInfo)row.getUserObject()).toString();
			BigDecimal totResult = new BigDecimal(0);
			for (int j = 4; j < kdtSplit.getColumnCount(); j++) {
				String key = kdtSplit.getColumnKey(j);
				totResult = totResult.add(FDCHelper.toBigDecimal(row.getCell(key).getValue()));
			}
			if(!checkDiff(targerCost,totResult))
			{
				MsgBox.showInfo(this, ""+publicMatching+"��Ŀ��ɱ����̯��ĸ���Ʒ����Ŀ��ɱ�֮�Ͳ����");
				SysUtil.abort();
			}
    	}
    }
    
    /**
	 * �Ƚ�2��������ֵ�Ƿ���1����
	 * true ��ʾ2������ȣ������1��
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
    
    private void initTable(){
    	ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType());
    	kdtSplit.getColumn("publicMatching").setEditor(f7Editor);
    	kdtProduct.getColumn("productType").setEditor(f7Editor);
    	
    	cmbSplitType.setName("cmbApportionType");
    	cmbSplitType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.aimcost.ApportionTypesEnum").toArray());
    	ICellEditor splitTypeEditor = new KDTDefaultCellEditor(cmbSplitType);
    	kdtSplit.getColumn("splitType").setEditor(splitTypeEditor);
//    	kdtSplit.getColumn("splitType").setEditor(splitTypeEditor);
    	
    	/**
    	 * 
    	 * @param e
    	 * name --biaobiao
    	 */
    	f7SplitType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ApportionTypeQuery");
    	f7SplitType.setDisplayFormat("$name$");
    	f7SplitType.setEditFormat("$number$");
    	f7SplitType.setCommitFormat("$number$");
    	EntityViewInfo viewInfo = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",true,CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("forCostApportion",true,CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("cu.id","11111111-1111-1111-1111-111111111111CCE7AED4",CompareType.EQUALS));
    	filter.setMaskString("#0 and #1 or #2");
    	viewInfo.setFilter(filter);
    	f7SplitType.setEntityViewInfo(viewInfo);
    	ICellEditor SplitType = new KDTDefaultCellEditor(f7SplitType);
    	kdtSplit.getColumn("splitType").setEditor(SplitType);
    	
    	f7SplitType.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				f7SplitType_dataChanged(e);
			}
    	});
    	
    	
    	
    	cmbSplitType.addItemListener(new java.awt.event.ItemListener() {
    		public void itemStateChanged(java.awt.event.ItemEvent e) {
    			cmbSplitType_itemStateChanged(e);
    		}
    	});
    	
    	kdtSplit.addHeadRow(1, (IRow) kdtSplit.getHeadRow(0).clone());
		for (int i = 0; i < kdtSplit.getColumnCount(); i++) {
			kdtSplit.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		
		kdtSplit.getStyleAttributes().setLocked(true);
		kdtSplit.getColumn("splitType").getStyleAttributes().setLocked(false);
		kdtProduct.getStyleAttributes().setLocked(true);
		
		if("VIEW".equals(oprtState)){
    		kdtSplit.getColumn("splitType").getStyleAttributes().setLocked(true);
    	}
		
		initFormatNumber();
    }
    
    //��ȡ��������Ŀ��ɱ�
    public Map getCost() throws EASBizException, BOSException {
    	Map amountMap = new HashMap();
    	MeasureCollectTable measureCollectTable = new MeasureCollectTable(measureCostEditUI);
    	measureCollectTable.refresh();
    	KDTable table = measureCollectTable.getTable();
    	IRow row = table.getRow(0);
    	int base = 0;
    	for (int i = 0; i < table.getColumnCount()-2; i++) {
    		if(table.getColumnKey(i).startsWith("split")){
    			String productID = table.getHeadRow(1).getCell("split" + base).getUserObject().toString();
    			amountMap.put(productID, row.getCell(i).getValue());
    			base++;
    		}
		}
		return amountMap;
	}
    
    
    Map publicProduct = new HashMap();
    private void fillTable() throws EASBizException, BOSException {
    	publicProduct.clear();
    	IRow row = null;
    	
    	TargetCostSplitCollection c = cost.getTargetCostSplit();
    	TargetCostSplitCollection coll = new TargetCostSplitCollection();
    	for (int j = 0; j < c.size(); j++) {
			TargetCostSplitInfo target = c.get(j);
			ProductTypeInfo productInfo = target.getProductType();
			if(null == productInfo){
				target = TargetCostSplitFactory.getRemoteInstance().getTargetCostSplitInfo(new ObjectUuidPK(target.getId().toString()));
			}
			coll.add(target);
    	}
    	
    	
    	
    	for (int i = 0; i < planIndexEntrys.size(); i++) {
    		PlanIndexEntryInfo entryInfo = planIndexEntrys.get(i);
    		ProductTypeInfo info = entryInfo.getProduct();
    		if(entryInfo.isIsSplit()){
    			splitProducts.add(info);
    			keySet.add(info.getId().toString());
    			row = kdtProduct.addRow();
    			row.getCell("productType").setValue(info);
    		}else{
    			notSplitProducts.add(info);
    			row = kdtSplit.addRow();
    			row.getCell("publicMatching").setValue(info);
    		}
    		for (int j = 0; j < coll.size(); j++) {
    			TargetCostSplitInfo target = coll.get(j);
    			if(info.getId().toString().equals(target.getPublicProduct().getId().toString())){
    				publicProduct.put(info.getId().toString(), target.getSplitTypeF7());
    			}
			}
    		row.setUserObject(info);
    		System.out.println(info.getId().toString()+" "+info.getName());
		}
    	
    	for (int i = 0; i < splitProducts.size(); i++) {
    		ProductTypeInfo product = (ProductTypeInfo)splitProducts.get(i);
    		String key = product.getId().toString();
    		IColumn column = kdtSplit.addColumn();
    		column.setKey(key);
    		column.setUserObject(product);
    		
    		KDFormattedTextField formattedTextField = new KDFormattedTextField(1);
    	    formattedTextField.setPrecision(2);
    	    formattedTextField.setSupportedEmpty(true);
    	    formattedTextField.setNegatived(false);
    	    ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
    	    column.setEditor(numberEditor);
    		
    	    column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	    column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    		
			kdtSplit.getHeadRow(0).getCell(key).setValue("��������Ŀ��ɱ���̯");
			kdtSplit.getHeadRow(1).getCell(key).setValue(product.getName());
		}
    	kdtSplit.getHeadMergeManager().mergeBlock(0, 4, 0, kdtSplit.getColumnCount()-1);
    	
    	Map amountMap = getCost();

	    //��乫�����׵�Ŀ��ɱ�
    	for (int i = 0; i < kdtSplit.getRowCount(); i++) {
    		row = kdtSplit.getRow(i);
    		ProductTypeInfo product = (ProductTypeInfo) row.getCell("publicMatching").getValue();
    		row.getCell("targerCost").setValue(amountMap.get(product.getId().toString()));
    		targerCost = FDCHelper.add(targerCost, amountMap.get(product.getId().toString()));
		}
    	getSaleAreaMap();
    	//��䲻������Ŀ��ɱ�
    	for (int i = 0; i < kdtProduct.getRowCount(); i++) {
    		row = kdtProduct.getRow(i);
    		ProductTypeInfo product = (ProductTypeInfo) row.getCell("productType").getValue();
    		row.getCell("targetCostNotMatching").setValue(amountMap.get(product.getId().toString()));
    		row.getCell("saleArea").setValue(saleAreaMap.get(product.getId().toString()));
    		targerCostNot = FDCHelper.add(targerCostNot, amountMap.get(product.getId().toString()));
		}
	}

    public KDBizPromptBox getF7productType() {
    	KDBizPromptBox f7productType = new KDBizPromptBox() {
			protected Object stringToValue(String t) {
				Object obj = super.stringToValue(t);
				if (obj instanceof IObjectValue) {
					return obj;
				} else {
					return t;
				}

			}
		};
		f7productType
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));

		view.setFilter(filter);
		f7productType.setEntityViewInfo(view);
		f7productType.setEditable(true);
		f7productType.setDisplayFormat("$name$");
		f7productType.setEditFormat("$number$");
		f7productType.setCommitFormat("$number$");


		return f7productType;
	}
    
    /**
	 *  �Ƿ���ʾ�ϼ���
	 */
	protected boolean isFootVisible() {
		return true;
	}

	/**
	 *  ���ӵ�һ����¼�ϼ���
	 */
	protected IRow appendFootRow1() {
		if (!this.isFootVisible()) {
			return null;
		}
		try {
			IRow footRow = null;
			KDTFootManager footRowManager = kdtSplit.getFootManager();
			if (footRowManager == null) {
				String total = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
				footRowManager = new KDTFootManager(this.kdtSplit);
				footRowManager.addFootView();
				this.kdtSplit.setFootManager(footRowManager);
				footRow = footRowManager.addFootRow(0);
				footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				this.kdtSplit.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
				this.kdtSplit.getIndexColumn().setWidth(30);
				footRowManager.addIndexText(0, total);
			} else {
				footRow = footRowManager.getFootRow(0);
			}
			footRow.getCell("targerCost").setValue(targerCost);
			
			footRow.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
			return footRow;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  ���ӵڶ�����¼�ϼ���
	 */
	protected IRow appendFootRow2() {
		if (!this.isFootVisible()) {
			return null;
		}
		try {
			IRow footRow = null;
			KDTFootManager footRowManager = kdtProduct.getFootManager();
			if (footRowManager == null) {
				String total = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
				footRowManager = new KDTFootManager(this.kdtProduct);
				footRowManager.addFootView();
				this.kdtProduct.setFootManager(footRowManager);
				footRow = footRowManager.addFootRow(0);
				footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				this.kdtProduct.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
				this.kdtProduct.getIndexColumn().setWidth(30);
				footRowManager.addIndexText(0, total);
			} else {
				footRow = footRowManager.getFootRow(0);
			}
			footRow.getCell("targetCostNotMatching").setValue(targerCostNot);
			
			footRow.getStyleAttributes().setBackground(
					new Color(0xf6, 0xf6, 0xbf));
			return footRow;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param e
	 * name --biaobiao
	 */
	private void f7SplitType_dataChanged(DataChangeEvent e) {
		IRow row = kdtSplit.getRow(kdtSplit.getSelectManager().getActiveRowIndex());
		if(e != null){
			BigDecimal targerCost = FDCHelper.toBigDecimal(row.getCell("targerCost").getValue());  //Ŀ��ɱ�
			BigDecimal xxArea = null;
			Object newValue = e.getNewValue();
			Object oldValue = e.getOldValue();
			if(oldValue != null  && !e.getOldValue().equals(e.getNewValue())){
			if(newValue != null && newValue instanceof ApportionTypeInfo){
				ApportionTypeInfo apportionTypeInfo = (ApportionTypeInfo)newValue;//��̯��ʽ
				if(apportionTypeInfo != null && isCustomPlanindex(apportionTypeInfo)){
					CustomPlanIndexEntryCollection customEntrys = planIndexInfo.getCustomEntrys();//�Զ���ָ��
					//����Զ���ָ����ķ�̯��ʽ �� Ŀ��ɱ���ġ���̯��ʽ���� �Զ���ָ����� "��Ʒ���� "�� Ŀ��ɱ����"��������"��� ����ȡ�Զ���ָ�����ָ��ֵ
					for (int i = 0; i < splitProducts.size(); i++) {
						ProductTypeInfo productType = splitProducts.get(i);
						row.getCell(productType.getId().toString()).setValue(new BigDecimal(0));
						for (int j = 0; j < customEntrys.size(); j++) {
							CustomPlanIndexEntryInfo planIndexEntryInfo = customEntrys.get(j);
							//�����̯��ʽ�Ͳ�Ʒ������ȣ���ȡ�Զ�������ָ��ֵ
							if(productType.getId().toString().equals(planIndexEntryInfo.getProductType().getId().toString()) && 
									planIndexEntryInfo.getApportType().getId().equals(apportionTypeInfo.getId())){
								xxArea = FDCHelper.toBigDecimal(planIndexEntryInfo.getValue());//ָ��ֵ
								}
							}
						if(xxArea!= null){
							BigDecimal customPlanIndexEntryTotal = getCustomPlanIndexEntryTotal(apportionTypeInfo);
							BigDecimal splitAmt=FDCHelper.multiply(targerCost, FDCHelper.divide(xxArea, customPlanIndexEntryTotal,12,BigDecimal.ROUND_HALF_UP));
							row.getCell(productType.getId().toString()).setValue(splitAmt);
							row.getCell(productType.getId().toString()).getStyleAttributes().setLocked(true);
							xxArea = null;
						}
						}
				}else{
						//ָ����̯
						if (ApportionTypesEnum.BYCUSTOM.toString().equals(apportionTypeInfo.getName().toString())){
							int columns = kdtSplit.getColumnCount();
							for(int i = 4;i<columns;i++){
								row.getCell(i).getStyleAttributes().setLocked(false);
								row.getCell(i).setValue(null);
							}
						}else
						
						//�������
						if (ApportionTypesEnum.BYBUILDAREA.toString().equals(apportionTypeInfo.getName().toString())) {
							calculatorSplit("buildArea", row);
						}else
						//�������
						if (ApportionTypesEnum.BYSELLAREA.toString().equals(apportionTypeInfo.getName().toString())) {
							calculatorSplit("saleArea", row);
						}else
						//ռ�����
						if (ApportionTypesEnum.BYCONTAINAREA.toString().equals(apportionTypeInfo.getName().toString())) {
							calculatorSplit("containArea", row);
						}else
						//û���ҵ��Զ����̯��ʽ
						{
							int columns = kdtSplit.getColumnCount();
							for(int i = 4;i<columns;i++){
								row.getCell(i).getStyleAttributes().setLocked(true);
								row.getCell(i).setValue(null);
							}
						}
					}
				if(null != apportionTypeInfo.getName()){
					calculatorResult();
				}
				}
			}
		}
		}
	
	
	protected void cmbSplitType_itemStateChanged(java.awt.event.ItemEvent e) {
		if(e != null){
			IRow row = kdtSplit.getRow(kdtSplit.getSelectManager().getActiveRowIndex());
			
			//ָ����̯
			if(ApportionTypesEnum.BYCUSTOM.equals(cmbSplitType.getSelectedItem())){
				int columns = kdtSplit.getColumnCount();
				for(int i = 4;i<columns;i++){
					row.getCell(i).getStyleAttributes().setLocked(false);
					row.getCell(i).setValue(null);
				}
			}
			//�������
			if (ApportionTypesEnum.BYBUILDAREA.equals(cmbSplitType.getSelectedItem())) {
				calculatorSplit("buildArea", row);
			}
			//�������
			if (ApportionTypesEnum.BYSELLAREA.equals(cmbSplitType.getSelectedItem())) {
				calculatorSplit("saleArea", row);
			}
			//ռ�����
			if (ApportionTypesEnum.BYCONTAINAREA.equals(cmbSplitType.getSelectedItem())) {
				calculatorSplit("containArea", row);
			}
			if(null != cmbSplitType.getSelectedItem()){
				calculatorResult();
			}
		}
	}
	
	@Override
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionCalculator_actionPerformed(e);
	}
	
	//�����̯���
	private void calculatorSplit(String splitType, IRow row) {
		BigDecimal targerCost = FDCHelper.toBigDecimal(row.getCell("targerCost").getValue());  //Ŀ��ɱ�
		BigDecimal xxArea = null;
		BigDecimal productAreaTotal = getProductAreaTotal(splitType);
		
		for (int i = 0; i < splitProducts.size(); i++) {
			ProductTypeInfo productType = splitProducts.get(i);
			
			for (int j = 0; j < planIndexEntrys.size(); j++) {
				PlanIndexEntryInfo entryInfo = planIndexEntrys.get(j);
	    		ProductTypeInfo info = entryInfo.getProduct();
	    		if(productType.getId().toString().equals(info.getId().toString())){
	    			if("containArea".equals(splitType)){
	    				xxArea = FDCHelper.toBigDecimal(entryInfo.getContainArea());
					}
					if("buildArea".equals(splitType)){
						xxArea = FDCHelper.toBigDecimal(entryInfo.getBuildArea());
					}
					if("saleArea".equals(splitType)){
						xxArea = FDCHelper.toBigDecimal(entryInfo.getSellArea());
					}
				}
			}
			BigDecimal splitAmt=FDCHelper.multiply(targerCost, FDCHelper.divide(xxArea, productAreaTotal,12,BigDecimal.ROUND_HALF_UP));
			row.getCell(productType.getId().toString()).setValue(splitAmt);
		}
	}
	
	Map saleAreaMap = new HashMap();//���۲�Ʒ���͵Ŀ������
	
	public void getSaleAreaMap() {
		for (int i = 0; i < planIndexEntrys.size(); i++) {
			PlanIndexEntryInfo entryInfo = planIndexEntrys.get(i);
			for (int j = 0; j < splitProducts.size(); j++) {
				ProductTypeInfo productType = splitProducts.get(j);
				if(entryInfo.getProduct().getId().toString().equals(productType.getId().toString())){
					saleAreaMap.put(productType.getId().toString(), entryInfo.getSellArea());
				}
			}
		}
	}
	
	//��ȡ�Զ���ָ���ָ��֮��
	private BigDecimal getCustomPlanIndexEntryTotal(ApportionTypeInfo apportionTypeInfo ){
		BigDecimal total = new BigDecimal("0");
		CustomPlanIndexEntryCollection customEntrys = planIndexInfo.getCustomEntrys();//�Զ���ָ��
		for (int i = 0; i < customEntrys.size(); i++) {
			CustomPlanIndexEntryInfo planIndexEntryInfo = customEntrys.get(i);
			if(planIndexEntryInfo.getApportType().getId().equals(apportionTypeInfo.getId())){
				total = FDCHelper.add(total, planIndexEntryInfo.getValue());
			}
		}
		return total;
	}
	
	//�ж�ѡ��ķ�̯��ʽ�Ƿ��Զ���ָ������
	private Boolean isCustomPlanindex(ApportionTypeInfo apportionTypeInfo ){
		CustomPlanIndexEntryCollection customEntrys = planIndexInfo.getCustomEntrys();//�Զ���ָ��
		for (int i = 0; i < customEntrys.size(); i++) {
			CustomPlanIndexEntryInfo planIndexEntryInfo = customEntrys.get(i);
			if(planIndexEntryInfo.getApportType().getId().equals(apportionTypeInfo.getId())){
				return true;
			}
		}
		return false;
		
	}
	
	
	//��ȡ���۲�Ʒ���͵�ռ��������������������֮��
	private BigDecimal getProductAreaTotal(String splitType) {
		BigDecimal total = new BigDecimal("0");
		for (int i = 0; i < planIndexEntrys.size(); i++) {
			PlanIndexEntryInfo entryInfo = planIndexEntrys.get(i);
			for (int j = 0; j < splitProducts.size(); j++) {
				ProductTypeInfo productType = splitProducts.get(j);
				if(entryInfo.getProduct().getId().toString().equals(productType.getId().toString())){
					if("containArea".equals(splitType)){
						total = FDCHelper.add(total, entryInfo.getContainArea());
					}
					if("buildArea".equals(splitType)){
						total = FDCHelper.add(total, entryInfo.getBuildArea());
					}
					if("saleArea".equals(splitType)){
						total = FDCHelper.add(total, entryInfo.getSellArea());
					}
				}
			}
		}
		return total;
	}
	
	Map splitMap = new HashMap();  //��һ����¼�ĺϼ�
	
	//�����һ����¼�ĺϼ�
	private void calculatorTotal1() {
		splitMap.clear();
		IRow row = null;
		for (int i = 0; i < kdtSplit.getRowCount(); i++) {
			row = kdtSplit.getRow(i);
			for (Iterator iter = keySet.iterator(); iter.hasNext();) {
				String key = iter.next().toString();
				BigDecimal value = FDCHelper.toBigDecimal(row.getCell(key).getValue());
				if(splitMap.containsKey(key)){
					splitMap.put(key, FDCHelper.add(value, splitMap.get(key)));
				}else{
					splitMap.put(key, value);
				}
			}
		}
		KDTFootManager footRowManager = kdtSplit.getFootManager();
		IRow footRow = footRowManager.getFootRow(0);
		for (int i = 4; i < kdtSplit.getColumnCount(); i++) {
			String titleKey = kdtSplit.getColumn(i).getKey();
			footRow.getCell(i).setValue(splitMap.get(titleKey));
		}
	}
	
	//����ڶ�����¼�ĺϼ�
	private void calculatorTotal2() {
		IRow row = null;
		Map map = new HashMap();
		for (int i = 0; i < kdtProduct.getRowCount(); i++) {
			row = kdtProduct.getRow(i);
			for (int j = 3; j < kdtProduct.getColumnCount(); j++) {
				String key = kdtProduct.getColumn(j).getKey();
				BigDecimal value = (BigDecimal) row.getCell(key).getValue();
				if(map.containsKey(key)){
					map.put(key, FDCHelper.add(value, map.get(key)));
				}else{
					map.put(key, value);
				}
			}
		}
		
		KDTFootManager footRowManager = kdtProduct.getFootManager();
		IRow footRow = footRowManager.getFootRow(0);
		for (int i = 3; i < kdtProduct.getColumnCount()-2; i++) {
			String titleKey = kdtProduct.getColumn(i).getKey();
			footRow.getCell(i).setValue(map.get(titleKey));
		}
	}
	
	//����һ����¼��ֵ�ı�ʱ����Ҫ���¼���ڶ�����¼��ֵ
	private void calculatorResult() {
		calculatorTotal1();
		IRow row = null;
		for (int i = 0; i < kdtProduct.getRowCount(); i++) {
			row = kdtProduct.getRow(i);
			ProductTypeInfo productType = (ProductTypeInfo) row.getCell("productType").getValue();
			BigDecimal publicTargerCost = FDCHelper.toBigDecimal(splitMap.get(productType.getId().toString()));
			row.getCell("targetCostPublicMatching").setValue(publicTargerCost);
			BigDecimal targetCost = FDCHelper.add(row.getCell("targetCostNotMatching").getValue(), publicTargerCost);
			row.getCell("targetCostMatching").setValue(targetCost);
			row.getCell("targetSale").setValue(FDCHelper.divide(targetCost, saleAreaMap.get(productType.getId().toString())));
		}
		
		calculatorTotal2();
	}
	
	private void initFormatNumber() {
		IColumn column = kdtSplit.getColumn("targerCost");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    
	    column = kdtProduct.getColumn("targetCostNotMatching"); 
	    column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    
	    column = kdtProduct.getColumn("targetCostPublicMatching"); 
	    column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    
	    column = kdtProduct.getColumn("targetCostMatching"); 
	    column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    
	    column = kdtProduct.getColumn("saleArea"); 
	    column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    
	    column = kdtProduct.getColumn("targetSale"); 
	    column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	}
}