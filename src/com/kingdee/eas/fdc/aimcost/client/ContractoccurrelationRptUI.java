/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.ConOccSplitListFactory;
import com.kingdee.eas.fdc.aimcost.ConOccSplitListInfo;
import com.kingdee.eas.fdc.aimcost.ConOccurSplitListEntryFactory;
import com.kingdee.eas.fdc.aimcost.ConOccurSplitListEntryInfo;
import com.kingdee.eas.fdc.aimcost.ContactOccurDateFacadeFactory;
import com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractoccurrelationRptUI extends AbstractContractoccurrelationRptUI {
	
	private static final Logger logger = CoreUIObject.getLogger(ContractoccurrelationRptUI.class);
	
	private IContactOccurDateFacade iFacade = null;

	public ContractoccurrelationRptUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		kDTable_contact.checkParsed();
		super.onLoad();
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, this.kDTree1, this.actionOnLoad);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) kDTree1.getModel().getRoot();
		kDTree1.expandAllNodes(true, root);
		
		initTable();
	}
	
	private void initTable() {
		btnSubmit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
		btnSubmit.setEnabled(true);
		this.kDTable_contact.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		KDTableHelper.setEnterKeyJumpOrientation(kDTable_contact, KDTableHelper.HORIZON);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(1);
	    formattedTextField.setPrecision(2);
	    formattedTextField.setDataType(1);
	    formattedTextField.setSupportedEmpty(true);
	    formattedTextField.setNegatived(false);
	    ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
	    
		kDTable_contact.getColumn("planningMoney").getStyleAttributes().setLocked(true);
		kDTable_contact.getColumn("longnumber").getStyleAttributes().setLocked(true);
		
		kDTable_contact.getColumn("unexpend").setEditor(numberEditor);
		kDTable_contact.getColumn("presentOccurred").setEditor(numberEditor);
		kDTable_contact.getColumn("adjustDifferentiation").setEditor(numberEditor);
		kDTable_contact.getColumn("planningMoney").setEditor(numberEditor);
		kDTable_contact.getColumn("unexpend").setEditor(numberEditor);
		
		kDTable_contact.getColumn("adjustDifferentiation").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kDTable_contact.getColumn("presentOccurred").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kDTable_contact.getColumn("unexpend").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kDTable_contact.getColumn("planningMoney").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kDTable_contact.getColumn("planningMoney").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kDTable_contact.getColumn("presentOccurred").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kDTable_contact.getColumn("unexpend").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kDTable_contact.getColumn("adjustDifferentiation").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		if (null == iFacade) {
			iFacade = ContactOccurDateFacadeFactory.getRemoteInstance();
		}

		String curProjectID = getCurProjectID();
		if ("".equals(curProjectID)) {
			MsgBox.showInfo(this, "��ѡ�񹤳���Ŀ�ڵ㣨�ǹ�˾�����û�й�����Ŀ���������ӹ�����Ŀ��");
			SysUtil.abort();
		}
		List productList = iFacade.getProductType(curProjectID);
		fillTableProductTypeData(productList);
		fillTableRowData();
		fillDataAimAndHappenned(curProjectID);
		setRowLeaf();
		setUnionData();
		appendFootRow();
//		setMergeColumn();
	}
	
	/**
	 * ��̬��Ӳ�Ʒ��
	 * @param list
	 */
	private void fillTableProductTypeData(List list) {
		kDTable_contact.removeRows();
		
		KDFormattedTextField formattedTextField=new KDFormattedTextField();
		formattedTextField.setVisible(true);
		formattedTextField.setHorizontalAlignment(2);
		formattedTextField.setDataType(1);
		formattedTextField.setPrecision(2);
		
		int columnCount = kDTable_contact.getColumnCount();
		
		for (int i = 0; i < columnCount - 12; i++){
			kDTable_contact.removeColumn(12);
		}
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map rowMap = (Map) list.get(j);

				IColumn column = kDTable_contact.addColumn();
				String key = rowMap.get("productId").toString() + "aimCost";
				column.setKey(key);
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				kDTable_contact.getHeadRow(0).getCell(key).setValue(rowMap.get("productName"));
				kDTable_contact.getHeadRow(1).getCell(key).setValue("Ŀ��ɱ�");
				column.getStyleAttributes().setLocked(true);

				column = kDTable_contact.addColumn();
				key = rowMap.get("productId").toString() + "hapCost";
				column.setKey(key);
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				kDTable_contact.getHeadRow(0).getCell(key).setValue(rowMap.get("productName"));
				kDTable_contact.getHeadRow(1).getCell(key).setValue("�ѷ����ɱ�");
				column.getStyleAttributes().setLocked(true);

				column = kDTable_contact.addColumn();
				key = rowMap.get("productId").toString() + "needAmt";
				column.setKey(key);
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				kDTable_contact.getHeadRow(0).getCell(key).setValue(rowMap.get("productName"));
				kDTable_contact.getHeadRow(1).getCell(key).setValue("����֧���������");
				column.getStyleAttributes().setLocked(false);
				
		    	
		    	formattedTextField.setName(key+j);
		    	KDTDefaultCellEditor needEditor=new KDTDefaultCellEditor(formattedTextField);
		    	kDTable_contact.getColumn(key).setEditor(needEditor);
		    	
				column = kDTable_contact.addColumn();
				key = rowMap.get("productId").toString() + "adujAmt";
				column.setKey(key);
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				kDTable_contact.getHeadRow(0).getCell(key).setValue(rowMap.get("productName"));
				kDTable_contact.getHeadRow(1).getCell(key).setValue("����������");
				column.getStyleAttributes().setLocked(false);
				
				formattedTextField.setName(key+j);
				KDTDefaultCellEditor ajdustEditor=new KDTDefaultCellEditor(formattedTextField);
				kDTable_contact.getColumn(key).setEditor(ajdustEditor);
				
			    
			    column = kDTable_contact.addColumn();
				key = rowMap.get("productId").toString() + "dynCostTotal";
				column.setKey(key);
				column.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
				column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				kDTable_contact.getHeadRow(0).getCell(key).setValue(rowMap.get("productName"));
				kDTable_contact.getHeadRow(1).getCell(key).setValue("��̬�ɱ�С��");
				column.getStyleAttributes().setLocked(true);

				kDTable_contact.getHeadMergeManager().mergeBlock(0, 12 + j * 5, 0, 12 + j * 5 + 4);
			}
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verify();
		String projectID = getCurProjectID();
		IRow row = null;
		// ������ݱ�
		IFMIsqlFacade isql_temp = FMIsqlFacadeFactory.getRemoteInstance();
		String sql_temp = "delete from T_AIM_ConOccSplitListEntry  where fparentid  in (select fid from T_AIM_ConOccSplitList where fprojectid ='" + projectID + "') ";
		isql_temp.executeSql(sql_temp);
		String sql = "delete from T_AIM_ConOccSplitList where fprojectid ='"+ projectID + "' ";
		IFMIsqlFacade isql = FMIsqlFacadeFactory.getRemoteInstance();
		isql.executeSql(sql);
		
		String parentID = "" ; 
		Map conListMap = new HashMap();
		ConOccSplitListInfo conOccSplitListInfo_temp = new ConOccSplitListInfo();
		CoreBaseCollection coll = new CoreBaseCollection();
		coll.clear();
		
		for (int i = 0; i < kDTable_contact.getRowCount(); i++) {
			row = kDTable_contact.getRow(i);
			
			//ֻ����Ҷ�ӽڵ������
			String isLeaf = row.getCell("isLeaf").getValue().toString();
			if("false".equals(isLeaf)){
				continue;
			}
			
			String pcID = (String) row.getCell("pcID").getValue(); // ��ܺ�ԼID 
			String costID = (String) row.getCell("costID").getValue(); // �ɱ���ĿID
			BigDecimal  curHappend = FDCHelper.toBigDecimal(row.getCell("presentOccurred").getValue());//Ŀǰ�ѷ���
			BigDecimal needAmount = FDCHelper.toBigDecimal(row.getCell("unexpend").getValue());//����֧��
			BigDecimal adjAmount = FDCHelper.toBigDecimal(row.getCell("adjustDifferentiation").getValue());//����
			
			ConOccSplitListInfo conOccSplitListInfo = new ConOccSplitListInfo();
			conOccSplitListInfo.setProjectID(projectID);
			conOccSplitListInfo.setProgrammingContractID(pcID);
			conOccSplitListInfo.setCurHappend(curHappend);
			conOccSplitListInfo.setNeedAmount(needAmount);
			conOccSplitListInfo.setAdjAmount(adjAmount);
			
			if(!conListMap.containsKey(pcID)){
				conListMap.put(pcID, conOccSplitListInfo);
				parentID = ConOccSplitListFactory.getRemoteInstance().save(conOccSplitListInfo).toString();
				conOccSplitListInfo_temp = ConOccSplitListFactory.getRemoteInstance().getConOccSplitListInfo(new ObjectUuidPK(parentID));
			}
			// ��̬��
			int dynamicCols = kDTable_contact.getColumnCount() - 12;
			ConOccurSplitListEntryInfo entryinfo = new ConOccurSplitListEntryInfo();
			for (int j = 0; j < dynamicCols; j++) {
				int colIndex = 12 + j;
				if (j % 5 == 0) {
					entryinfo = new ConOccurSplitListEntryInfo();
					String colKey = kDTable_contact.getColumnKey(colIndex);
					String productID = colKey.substring(0, colKey.length() - 7); // ��ƷID
					entryinfo.setProductID(productID);
					entryinfo.setParent(conOccSplitListInfo_temp);
					entryinfo.setCostAccountID(costID);
					coll.add(entryinfo);
				}
				if (kDTable_contact.getColumnKey(colIndex).endsWith("aimCost")) {
					entryinfo.setAimCost(FDCHelper.toBigDecimal( row.getCell(colIndex).getValue()));
				}
				if (kDTable_contact.getColumnKey(colIndex).endsWith("hapCost")) {
					entryinfo.setHapCost(FDCHelper.toBigDecimal( row.getCell(colIndex).getValue()));
				}
				if (kDTable_contact.getColumnKey(colIndex).endsWith("needAmt")) {
					entryinfo.setNeedAmt(FDCHelper.toBigDecimal( row.getCell(colIndex).getValue()));
				}
				
				if (kDTable_contact.getColumnKey(colIndex).endsWith("adujAmt")) {
					entryinfo.setAdjAmt(FDCHelper.toBigDecimal( row.getCell(colIndex).getValue()));
				}
			}
		}
		ConOccurSplitListEntryFactory.getRemoteInstance().save(coll);
		this.setMessageText("����ɹ�");
		this.showMessage();
	}
	
	private Map submitData() {
		Map verifyDataMap = new HashMap();
		String pcNumber = "";
		for (int i = 0; i < kDTable_contact.getRowCount(); i++) {
			IRow row = kDTable_contact.getRow(i);
			String number = row.getCell("longnumber").getValue().toString();
			BigDecimal needCost = FDCHelper.toBigDecimal(row.getCell("unexpend").getValue());
			BigDecimal adujAmt = FDCHelper.toBigDecimal(row.getCell("adjustDifferentiation").getValue());
			BigDecimal aimCost = FDCHelper.toBigDecimal(row.getCell("planningMoney").getValue());
			BigDecimal hapCost = FDCHelper.toBigDecimal(row.getCell("presentOccurred").getValue());
			if(verifyDataMap.containsKey(number)){
				BigDecimal[] temp = (BigDecimal[]) verifyDataMap.get(number);
				temp[0] = needCost;
				temp[2] = adujAmt;
				temp[4] = aimCost;
				temp[6] = hapCost;
				for (int j = 12; j < kDTable_contact.getColumnCount(); j++) {
					String titleKey =  kDTable_contact.getColumnKey(j);
					if (titleKey.endsWith("needAmt")) {
						BigDecimal needCostTemp = FDCHelper.toBigDecimal(row.getCell(titleKey).getValue());
						BigDecimal needCostTotal = temp[1];
						temp[1] = FDCHelper.add(needCostTotal, needCostTemp);
					}
					if (titleKey.endsWith("adujAmt")) {
						BigDecimal adujAmtTemp = FDCHelper.toBigDecimal(row.getCell(titleKey).getValue());
						BigDecimal adujAmtTotal = temp[3];
						temp[3] = FDCHelper.add(adujAmtTotal, adujAmtTemp);
					}
					if (titleKey.endsWith("aimCost")) {
						BigDecimal aimCostTemp = FDCHelper.toBigDecimal(row.getCell(titleKey).getValue());
						BigDecimal aimCostTotal = temp[5];
						temp[5] = FDCHelper.add(aimCostTotal, aimCostTemp);
					}
					if (titleKey.endsWith("hapCost")) {
						BigDecimal hapCostTemp = FDCHelper.toBigDecimal(row.getCell(titleKey).getValue());
						BigDecimal hapCostTotal = temp[7];
						temp[7] = FDCHelper.add(hapCostTotal, hapCostTemp);
					}
				}
				verifyDataMap.put(number, temp);
			}else{
				BigDecimal[] temp = new BigDecimal[8];
				temp[0] = needCost;
				temp[2] = adujAmt;
				temp[4] = aimCost;
				temp[6] = hapCost;
				for (int j = 12; j < kDTable_contact.getColumnCount(); j++) {
					String titleKey =  kDTable_contact.getColumnKey(j);
					if (titleKey.endsWith("needAmt")) {
						BigDecimal needCostTemp = FDCHelper.toBigDecimal(row.getCell(titleKey).getValue());
						BigDecimal needCostTotal = temp[1];
						temp[1] = FDCHelper.add(needCostTotal, needCostTemp);
					}
					if (titleKey.endsWith("adujAmt")) {
						BigDecimal adujAmtTemp = FDCHelper.toBigDecimal(row.getCell(titleKey).getValue());
						BigDecimal adujAmtTotal = temp[3];
						temp[3] = FDCHelper.add(adujAmtTotal, adujAmtTemp);
					}
					if (titleKey.endsWith("aimCost")) {
						BigDecimal aimCostTemp = FDCHelper.toBigDecimal(row.getCell(titleKey).getValue());
						BigDecimal aimCostTotal = temp[5];
						temp[5] = FDCHelper.add(aimCostTotal, aimCostTemp);
					}
					if (titleKey.endsWith("hapCost")) {
						BigDecimal hapCostTemp = FDCHelper.toBigDecimal(row.getCell(titleKey).getValue());
						BigDecimal hapCostTotal = temp[7];
						temp[7] = FDCHelper.add(hapCostTotal, hapCostTemp);
					}
				}
				verifyDataMap.put(number, temp);
			}
		}
		return verifyDataMap;
	}
	
	private void verify() {
		Map data = submitData();
		Set keySet = data.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = iterator.next().toString();
			BigDecimal[] temp = (BigDecimal[]) data.get(key);
			Double needResult = FDCHelper.subtract(temp[0], temp[1]).doubleValue();
			Double adjResult = FDCHelper.subtract(temp[2], temp[3]).doubleValue();
			Double aimResult = FDCHelper.subtract(temp[4], temp[5]).doubleValue();
			Double hapResult = FDCHelper.subtract(temp[6], temp[7]).doubleValue();
			if(needResult >= 1 || needResult <= -5){
				MsgBox.showInfo(this, "��ܺ�Լ����Ϊ '"+key+"' ������֧�������Ʒ���͵�����֧���������֮�Ͳ����");
				SysUtil.abort();
			}
			if(adjResult >= 1 || adjResult <=-5) {
				MsgBox.showInfo(this, "��ܺ�Լ����Ϊ '"+key+"' �ĵ��������Ʒ���͵ĵ���������֮�Ͳ����");
				SysUtil.abort();
			}
			if(aimResult >= 1 || aimResult <= -5){
				MsgBox.showInfo(this, "��ܺ�Լ����Ϊ '"+key+"' �Ĺ滮��������Ʒ���͵�Ŀ��ɱ�֮�Ͳ����");
				SysUtil.abort();
			}
			if(hapResult >= 1 || hapResult <=-5) {
				MsgBox.showInfo(this, "��ܺ�Լ����Ϊ '"+key+"' ��Ŀǰ�ѷ��������Ʒ���͵��ѷ����ɱ�֮�Ͳ����");
				SysUtil.abort();
			}
		}
	}

	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {
		if ("".equals(getCurProjectID())) {
			return;
		}
		LongTimeDialog dialog = UITools.getDialog(this);
		if (dialog == null){
			return;
		}
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				actionQuery_actionPerformed(null);
				setMergeColumn();
				return null;
			}
			public void afterExec(Object obj) throws Exception {
			}
		});
		if (dialog != null)
			dialog.show();
	}

	private String getCurProjectID() {
		DefaultKingdeeTreeNode projectNode = getProjSelectedTreeNode();
		if (projectNode instanceof KDTreeNode) {
			KDTreeNode node = (KDTreeNode) projectNode;
			Object obj = node.getUserObject();
			if (obj instanceof CurProjectInfo) {
				CurProjectInfo info = (CurProjectInfo) obj;
				return info.getId().toString();
			}
		}
		return "";
	}

	private DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.kDTree1
				.getLastSelectedPathComponent();
	}

	/**
	 * ����Ʒ��Ŀ��ɱ����ѷ����ɱ�
	 * @param curProjectID
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void fillDataAimAndHappenned(String curProjectID) throws BOSException, EASBizException {
		Map aimCostMap = iFacade.getAimCost(curProjectID);
		Map happenedCostMap = iFacade.getHappenedCost(curProjectID);
		
		for (int i = 0; i < kDTable_contact.getRowCount(); i++) {
			IRow row = kDTable_contact.getRow(i);
			String pcID = (String) row.getCell("pcID").getValue(); // ��ܺ�ԼID
			String costID = (String) row.getCell("costID").getValue(); // �ɱ���ĿID
			BigDecimal needSpa = FDCHelper.toBigDecimal( row.getCell("unexpend").getValue());// ����֧��
			BigDecimal adjDiff = FDCHelper.toBigDecimal(row.getCell("adjustDifferentiation").getValue());//����
			BigDecimal promAmount = FDCHelper.toBigDecimal(row.getCell("planningMoney").getValue());// �滮���
			BigDecimal contractAssign = FDCHelper.toBigDecimal( row.getCell("contractassign").getValue()); // ���η�����
			for (int j = 12; j < kDTable_contact.getColumnCount(); j++) {
				String colKey = kDTable_contact.getColumnKey(j);
				//Ŀ��ɱ�
				if(colKey.endsWith("aimCost")){
					String productID = colKey.substring(0, colKey.length() - 7);
					if (aimCostMap.containsKey(pcID)) {
						Map pcMap = (Map) aimCostMap.get(pcID);
						if (pcMap.containsKey(costID)) {
							Map costMap = (Map) pcMap.get(costID);
							if (costMap.containsKey(productID)) {
								BigDecimal  happCost = (BigDecimal) costMap.get(productID);
								row.getCell(colKey).setValue(happCost);
							}
						}
					}
				}
				//�ѷ����ɱ�
				if(colKey.endsWith("hapCost")){
					String productID = colKey.substring(0, colKey.length() - 7);
					if("ZwNonIJFS9OQvVma2HfwZuzgeds=".equals(pcID) && "cL7JMRjFQjqBzVum54XrUuEgPpc=".equals(productID)){
						String s = new String();
					}
					if (happenedCostMap.containsKey(pcID)) {
						Map pcMap = (Map) happenedCostMap.get(pcID);
						if (pcMap.containsKey(costID)) {
							Map costMap = (Map) pcMap.get(costID);
							if (costMap.containsKey(productID)) {
								BigDecimal  happCost = (BigDecimal) costMap.get(productID);
								row.getCell(colKey).setValue(happCost);
							}
						}
					}
				}
				
				//����֧���������
				if(colKey.endsWith("needAmt")){
					int colIndex = kDTable_contact.getColumnIndex(colKey);
					BigDecimal hapCost =  FDCHelper.toBigDecimal(row.getCell(colIndex-1).getValue());  //�ѷ����ɱ�
					BigDecimal productCost =  FDCHelper.toBigDecimal(row.getCell(colIndex-2).getValue());  //��Ʒ���Ͷ�Ӧ��Ŀ��Ŀ��ɱ�
					BigDecimal goalCost = FDCHelper.toBigDecimal(row.getCell("goalcost").getValue());  //��ĿĿ��ɱ�
					if("01.03.05.06".equals(row.getCell("longnumber").getValue().toString())){
						String s = new String();
					}
					
					//Ŀ��ɱ�������Ŀ��ɱ�/��ĿĿ��ɱ�) = ��Ʒ���Ͷ�Ӧ��Ŀ��Ŀ��ɱ�/���η�����
					BigDecimal aimCost = FDCHelper.divide(productCost, contractAssign,13,BigDecimal.ROUND_HALF_UP);
					//�����η�����/�滮��*��Ŀ��ɱ�/��ĿĿ��ɱ�)
					BigDecimal temp = FDCHelper.multiply(FDCHelper.divide(contractAssign, promAmount,13,BigDecimal.ROUND_HALF_UP), aimCost);
					
					// ����֧���������=����֧��*�����η�����/�滮��*��Ŀ��ɱ�/��ĿĿ��ɱ���
					BigDecimal needAmt = FDCHelper.multiply(needSpa, temp);
					
					//����������=����*�����η�����/�滮��*��Ŀ��ɱ�/��ĿĿ��ɱ���
					BigDecimal adjAmt = FDCHelper.multiply(adjDiff, temp);
					
					BigDecimal dynCostTotal = FDCHelper.add(hapCost, FDCHelper.add(needAmt, adjAmt));//��̬�ɱ�С��
					
					row.getCell(colKey).setValue(needAmt);
					row.getCell(colIndex+1).setValue(adjAmt);
					row.getCell(colIndex+2).setValue(dynCostTotal);
				}
			}
		}
	}

	/**
	 * �������
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void fillTableRowData() throws EASBizException, BOSException {
		kDTable_contact.removeRows();
		if (null == iFacade) {
			iFacade = ContactOccurDateFacadeFactory.getRemoteInstance();
		}

		String curProjectID = getCurProjectID();
		List productList = iFacade.getProductType(curProjectID);
		fillTableProductTypeData(productList);
		List dataList = iFacade.getContractData(curProjectID);
		List dataListDym = iFacade.getDymData(curProjectID);
		iFacade.getDate(curProjectID);
		if (dataList != null && dataList.size() > 0) {
			IRow row = null;
			for (int i = 0; i < dataList.size(); i++) {
				Map rowMap = (Map) dataList.get(i);
				row = kDTable_contact.addRow();
				row.setTreeLevel(Integer.parseInt(rowMap.get("level").toString()) - 1);
				kDTable_contact.getTreeColumn().setDepth(Integer.parseInt(rowMap.get("level").toString()));
				if(dataListDym != null && dataListDym.size() > 0) {
					BigDecimal tempLatestCost = new BigDecimal(0);//add by ypf on 20140307  ��Լ����ֵ��ġ�Ŀǰ�ѷ�������ֵͳ������
					for (int j = 0; j < dataListDym.size(); j++) {
						Map rowMap_temp = (Map) dataListDym.get(j);
						if(rowMap_temp.get("programmingContractid").equals(rowMap.get("programmingContractid")) ) {
							tempLatestCost = tempLatestCost.add(FDCHelper.toBigDecimal(rowMap_temp.get("latestCost")));//add by ypf on 20140307  ��Լ����ֵ��ġ�Ŀǰ�ѷ�������ֵͳ������
						    row.getCell("presentOccurred").setValue(tempLatestCost);//add by ypf on 20140307  ��Լ����ֵ��ġ�Ŀǰ�ѷ�������ֵͳ������
							row.getCell("unexpend").setValue(FDCHelper.toBigDecimal(rowMap_temp.get("unexpend")));
							row.getCell("adjustDifferentiation").setValue(FDCHelper.toBigDecimal(rowMap_temp.get("adjustDifferentiation")));
						}
					}	
				}
				row.getCell("longnumber").setValue(rowMap.get("longnumber").toString());
				row.getCell("frameContractName").setValue(rowMap.get("frameContractName"));
			    row.getCell("planningMoney").setValue(FDCHelper.toBigDecimal(rowMap.get("planningMoney")));
				row.getCell("belongtoCostSubject").setValue(rowMap.get("belongtoCostSubject"));
				row.getCell("pcID").setValue(rowMap.get("programmingContractid"));
				row.getCell("costID").setValue(rowMap.get("costID"));
				row.getCell("goalcost").setValue(rowMap.get("goalcost"));
				row.getCell("contractassign").setValue(rowMap.get("contractassign"));
			}
		}
	}
	
	protected void kDTable_contact_editStopped(KDTEditEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if ((oldValue == null) && (newValue == null)) {
			return;
		}
		if ((oldValue != null) && (newValue != null)) {
			if (oldValue.equals(newValue)) {
				return;
			}
		}
		IRow editRow = kDTable_contact.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		String colName = kDTable_contact.getColumnKey(colIndex);
		if(colName.endsWith("needAmt")){
			BigDecimal hapCost = FDCHelper.toBigDecimal(editRow.getCell(colIndex-1).getValue());   //�ѷ����ɱ�
			BigDecimal needAmt = FDCHelper.toBigDecimal(e.getValue());  //����֧���������
			BigDecimal adjAmt = FDCHelper.toBigDecimal(editRow.getCell(colIndex+1).getValue());    //����������
			BigDecimal dynCostTotal = FDCHelper.add(hapCost, FDCHelper.add(needAmt, adjAmt));  //��̬�ɱ�С��
			editRow.getCell(colIndex+2).setValue(dynCostTotal);
		}
		if(colName.endsWith("adujAmt")){
			BigDecimal hapCost = FDCHelper.toBigDecimal(editRow.getCell(colIndex-2).getValue());   //�ѷ����ɱ�
			BigDecimal needAmt = FDCHelper.toBigDecimal(editRow.getCell(colIndex-1).getValue());  //����֧���������
			BigDecimal adjAmt = FDCHelper.toBigDecimal(e.getValue());    //����������
			BigDecimal dynCostTotal = FDCHelper.add(hapCost, FDCHelper.add(needAmt, adjAmt));  //��̬�ɱ�С��
			editRow.getCell(colIndex+1).setValue(dynCostTotal);
		}
		setUnionData();
		appendFootRow();
	}
	
	
	/**
     * �������Ƿ���Ҷ�ӽڵ�
     */
    private void setRowLeaf() {
    	IRow curRow, nextRow;
    	int curLevel, nextLevel;
    	String curPCID, nextPCID;
    	
    	int rowCount = kDTable_contact.getRowCount();
    	
        for(int i = 0; i < rowCount; i++){
        	curRow = kDTable_contact.getRow(i);
        	curLevel = curRow.getTreeLevel();
        	curPCID = curRow.getCell("pcID").getValue().toString();
        	
        	//���һ�п϶���Ҷ�ӽڵ�
        	if(i == rowCount-1){
        		curRow.getCell("isLeaf").setValue("true");
        		continue;
        	}
        	
        	for (int j = i+1; j < rowCount; j++) {
        		nextRow = kDTable_contact.getRow(j);
        		nextLevel = nextRow.getTreeLevel();
        		nextPCID = nextRow.getCell("pcID").getValue().toString();
        		if(!curPCID.equals(nextPCID)){
        			//�����ǰ�еļ��δ��ڵ�����һ�еļ��Σ����Ҵ��ڵ�����һ�еļ��Σ���ô��ǰ�о���Ҷ�ӽڵ�
                    if(curLevel >= nextLevel){
                    	curRow.getCell("isLeaf").setValue("true");
                    }else{
                    	curRow.getCell("isLeaf").setValue("false");
                    }
        			break;
        		}
        		//2013.04.17
        		//������һ����ܺ�Լ���ڶ��У���˿�ܺ�Լ����Ҷ�ӽڵ�
        		IRow lastRow = kDTable_contact.getRow(rowCount-1);
        		String lastPcID = lastRow.getCell("pcID").getValue().toString();
        		if(curPCID.equals(lastPcID)){
        			curRow.getCell("isLeaf").setValue("true");
        		}
			}
        }
	}
	
	public void setUnionData() {
		//���ܸ���ܺ�Լ����Ӧ��Ʒ����ؽ��
		String cols[] = new String[kDTable_contact.getColumnCount() - 12];
		for (int i = 12; i < kDTable_contact.getColumnCount(); i++) {
			cols[i-12] = kDTable_contact.getColumnKey(i);
		}
		
		for(int i = 0; i < kDTable_contact.getRowCount(); i++){
            IRow row = kDTable_contact.getRow(i);
            String isLeaf = row.getCell("isLeaf").getValue().toString();
            if("true".equals(isLeaf))
                continue;
            int level = row.getTreeLevel();
            List aimRowList = new ArrayList();
            for(int j = i + 1; j < kDTable_contact.getRowCount(); j++)
            {
                IRow rowAfter = kDTable_contact.getRow(j);
                if(rowAfter.getTreeLevel() <= level)
                    break;
                String isLeafTemp = rowAfter.getCell("isLeaf").getValue().toString();
                if("true".equals(isLeafTemp))
                    aimRowList.add(rowAfter);
            }
            
            for (int j = 0; j < cols.length; j++) {
            	BigDecimal sum = null;
                for(int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++)
                {
                    IRow rowAdd = (IRow)aimRowList.get(rowIndex);
                    Object value = rowAdd.getCell(cols[j]).getValue();
                    if(value != null)
                        sum = FDCHelper.toBigDecimal(sum, 2).add(FDCHelper.toBigDecimal(value, 2));
                }
                row.getCell(cols[j]).setValue(sum);
			}
        }
		
		/*for (int i = 0; i < kDTable_contact.getRowCount(); i++) {
			IRow row = kDTable_contact.getRow(i);
			if("06".equals(row.getCell("longnumber").getValue().toString())){
				String s = new String();
			}
			//if (row.getUserObject() instanceof CostAccountInfo) {
				// ���û�����
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				if (kDTable_contact.getRowCount() == 1) {
					IRow rowAfter = kDTable_contact.getRow(0);
					aimRowList.add(rowAfter);
				}
				for (int j = i + 1; j < kDTable_contact.getRowCount(); j++) {
					IRow rowAfter = kDTable_contact.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					//if (rowAfter.getUserObject() instanceof MeasureEntryInfo) {
						aimRowList.add(rowAfter);
					//}

				}
				if(aimRowList.size() == 0){
					continue;
				}
				for (int j = 0; j < cols.length; j++) {
	            	BigDecimal sum = null;
	                for(int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++)
	                {
	                    IRow rowAdd = (IRow)aimRowList.get(rowIndex);
	                    Object value = rowAdd.getCell(cols[j]).getValue();
	                    if(value != null)
	                        sum = FDCHelper.toBigDecimal(sum, 2).add(FDCHelper.toBigDecimal(value, 2));
	                }
	                row.getCell(cols[j]).setValue(sum);
				}
		}*/
    }
	
	/**
	 * ���Ӻϼ���
	 */
	protected void appendFootRow() {
		try {
			IRow footRow = null;
			KDTFootManager footRowManager = kDTable_contact.getFootManager();
			if (footRowManager == null) {
				String total = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
				footRowManager = new KDTFootManager(this.kDTable_contact);
				footRowManager.addFootView();
				this.kDTable_contact.setFootManager(footRowManager);
				footRow = footRowManager.addFootRow(0);
				footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				this.kDTable_contact.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
				this.kDTable_contact.getIndexColumn().setWidth(30);
				footRowManager.addIndexText(0, total);
			} else {
				footRow = footRowManager.getFootRow(0);
			}
			sumContractAmt(footRow);
			sumPCAmt(footRow);
			footRow.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���û����е�ֵ
	 */
	private void sumFootRow(List sumRowList, List sumColNameList, IRow footRow) {
		//���ܸ��ڵ������
        Map totalMap = new HashMap();
        for (int i = 0; i < sumRowList.size(); i++) {
			IRow row = (IRow) sumRowList.get(i);
			for (int j = 0; j < kDTable_contact.getColumnCount(); j++) {
				String colName = this.kDTable_contact.getColumn(j).getKey();
				for (int k = 0; k < sumColNameList.size(); k++) {
					String fieldName = (String) sumColNameList.get(k);
					if (colName.equalsIgnoreCase(fieldName)) {
						if(totalMap.containsKey(fieldName)){
							BigDecimal promAmount = FDCHelper.toBigDecimal(totalMap.get(fieldName));
							promAmount = FDCHelper.add(promAmount, row.getCell(fieldName).getValue());
							totalMap.put(fieldName, promAmount);
						}else{
							totalMap.put(fieldName, row.getCell(fieldName).getValue());
						}
					}
				}
			}
		}
        
        int columnCount = this.kDTable_contact.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			String colName = this.kDTable_contact.getColumn(i).getKey();
			for (int j = 0; j < sumColNameList.size(); j++) {
				String fieldName = (String) sumColNameList.get(j);
				if (colName.equalsIgnoreCase(fieldName)) {
					ICell cell = footRow.getCell(i);
					cell.getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
					cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					cell.getStyleAttributes().setFontColor(Color.BLACK);
					cell.setValue(totalMap.get(fieldName));
				}
			}
		}
	}
	
	/**
	 * ���ò�Ʒ���ͺϼ��е�ֵ
	 */
	private void sumContractAmt(IRow footRow) {
		List sumRowList = new ArrayList();
		for (int i = 0; i < kDTable_contact.getRowCount(); i++) {
			IRow row = kDTable_contact.getRow(i);
			if(row.getTreeLevel() == 0){
				sumRowList.add(row);
			}
		}
		
		List sumList = new ArrayList();
		for (int i = 12; i < kDTable_contact.getColumnCount(); i++) {
			sumList.add(kDTable_contact.getColumnKey(i));
		}
		
		sumFootRow(sumRowList, sumList, footRow);
	}
	
	/**
	 *  ���úϼ��е�ֵ���滮��Ŀǰ�ѷ���������֧��������
	 */
	private void sumPCAmt(IRow footRow) {
		int curLevel;
    	
    	String prePromID = null;
    	String curPromID = null;
    	String nextPromID = null;
    	
    	IRow preRow = null;
    	IRow curRow = null;
    	IRow nextRow = null;
    	
    	
    	int rowCount = kDTable_contact.getRowCount();
    	
    	//�õ���Ҫ���ܵ��У��Կ�ܺ�ԼΪά��
    	List sumRowList = new ArrayList();
        for(int i = 0; i < rowCount; i++){
        	curRow = kDTable_contact.getRow(i);
        	curLevel = curRow.getTreeLevel();
        	
        	//�������һ���ĺ�Լ���򲻻���
        	if(curLevel != 0){
        		continue;
        	}
        	
        	//��һ�п϶���һ���ĺ�Լ
        	if(i == 0){
        		sumRowList.add(curRow);
        		continue;
            }
        	
        	preRow = kDTable_contact.getRow(i-1);
			prePromID = preRow.getCell("pcID").getValue().toString();
			curPromID = curRow.getCell("pcID").getValue().toString();
			
			if(curPromID.equals(prePromID)){
				continue;
			}else{
				sumRowList.add(curRow);
			}
        }
        
        List sumList = new ArrayList();
        sumList.add("planningMoney");
        sumList.add("presentOccurred");
        sumList.add("unexpend");
        sumList.add("adjustDifferentiation");
        
        sumFootRow(sumRowList, sumList, footRow);
	}

	/**
	 * ����ں�����
	 */
	private void setMergeColumn() {
		kDTable_contact.checkParsed();
		kDTable_contact.getGroupManager().setGroup(true);
		int longNumberTop = 1;
		boolean longNumberMerge = false;
		for (int i = 1; i < kDTable_contact.getRowCount(); i++) {
			// ��ܺ�Լ������
			Object topLN = kDTable_contact.getCell(i - 1, "pcID").getValue();
			Object bottomLN = kDTable_contact.getCell(i, "pcID").getValue();

			if (topLN != null && bottomLN != null) {
				if (topLN.toString().equals(bottomLN.toString())) {
					if (kDTable_contact.getMergeManager().mergeBlock(longNumberTop - 1, 0, i, 0)) {
						longNumberMerge = true;
					}
					kDTable_contact.getMergeManager().mergeBlock(longNumberTop - 1, 1, i, 1);
					kDTable_contact.getMergeManager().mergeBlock(longNumberTop - 1, 2, i, 2);
					kDTable_contact.getMergeManager().mergeBlock(longNumberTop - 1, 3, i, 3);
					kDTable_contact.getMergeManager().mergeBlock(longNumberTop - 1, 4, i, 4);
					kDTable_contact.getMergeManager().mergeBlock(longNumberTop - 1, 5, i, 5);
					kDTable_contact.getMergeManager().mergeBlock(longNumberTop - 1, 6, i, 6);
					kDTable_contact.getMergeManager().mergeBlock(longNumberTop - 1, 7, i, 7);
				} else {
					longNumberMerge = false;
				}
			}
			if (!longNumberMerge) {
				longNumberTop = i + 1;
			}
		}
	}
}