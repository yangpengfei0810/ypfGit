/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.DynCostDetailCollection;
import com.kingdee.eas.fdc.aimcost.DynCostDetailDataFactory;
import com.kingdee.eas.fdc.aimcost.DynCostDetailDataInfo;
import com.kingdee.eas.fdc.aimcost.DynCostDetailFacadeFactory;
import com.kingdee.eas.fdc.aimcost.DynCostDetailFactory;
import com.kingdee.eas.fdc.aimcost.DynCostDetailInfo;
import com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractEditUI;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class DynCostDetailRptEditUI extends AbstractDynCostDetailRptEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(DynCostDetailRptEditUI.class);
   
    public static String KEY_programmingContractID = "programmingContractID";
	public static String KEY_contractID = "contractID";
	public static String KEY_programmingContractNumber = "programmingContractNumber";
	public static String KEY_programmingContractName = "programmingContractName";
	public static String KEY_promAmount = "promAmount";
	public static String KEY_status = "status";
	public static String KEY_contractName = "contractName";
	public static String KEY_contractAmount = "contractAmount";
	public static String KEY_actualContractingAmount = "actualContractingAmount";
	public static String KEY_tempAmount = "tempAmount";
	public static String KEY_changeAmount = "changeAmount";
	public static String KEY_settlementAmount = "settlementAmount";
	public static String KEY_latestCost = "latestCost";
	public static String KEY_promBalance = "promBalance";
	public static String KEY_needSpendingAmount = "needSpendingAmount";//����֧��
	public static String KEY_dynamicCostOneAmount = "dynamicCostOneAmount";
	public static String KEY_costDiffOneAmount = "costDiffOneAmount";
	public static String KEY_targetCostDiffOneRate = "targetCostDiffOneRate";
	public static String KEY_adjustmentDiffAmount = "adjustmentDiffAmount";
	public static String KEY_dynamicCostTwoAmount = "dynamicCostTwoAmount";
	public static String KEY_costDiffTwoAmount = "costDiffTwoAmount";
	public static String KEY_targetCostDiffTwoRate = "targetCostDiffTwoRate";
	public static String KEY_remark = "remark";
	public static String KEY_entryID = "dataID";
    public Map needMap = new HashMap();//biaobiao ����֧��
    private String curProjectid = null;
	
    private IDynCostDetailFacade iFacade = null;
    
    public DynCostDetailRptEditUI() throws Exception
    {
        super();
    }
    
    protected void initListener() {
    	
    }
    public void onLoad() throws Exception {
    	kdtEntry.checkParsed();
		super.onLoad();
		
//		//biaobiao  2013-12-22 ��ͬ�ҷ�  --begin
//		int columnIndex2 = this.kdtEntry.getColumn(KEY_contractName).getColumnIndex()+1;
//		this.kdtEntry.addColumn(columnIndex2);
//		KDTMergeManager mm = kdtEntry.getHeadMergeManager();
//		int colIndexPartyC = this.kdtEntry.getColumn(KEY_contractName).getColumnIndex()+2;
//		this.kdtEntry.addColumn(colIndexPartyC);
//		// ����ָ���ں�
//		mm.mergeBlock(0, columnIndex2, 1, columnIndex2, KDTMergeManager.SPECIFY_MERGE);
//		this.kdtEntry.getColumn(columnIndex2).setKey("contractParty");
//		this.kdtEntry.getHeadRow(0).getCell(columnIndex2).setValue("��ͬ�ҷ�");
//		
//		//��ͬ����
//		mm.mergeBlock(0, colIndexPartyC, 1, colIndexPartyC, KDTMergeManager.SPECIFY_MERGE);
//		this.kdtEntry.getColumn(colIndexPartyC).setKey("contractPartyC");
//		this.kdtEntry.getHeadRow(0).getCell(colIndexPartyC).setValue("��ͬ����");
//		
//		//�ۼ����
//		int columnIndex3 = this.kdtEntry.getColumn(KEY_latestCost).getColumnIndex()+1;
//		this.kdtEntry.addColumn(columnIndex3);
//		this.kdtEntry.getColumn(columnIndex3).setKey("cumulativeCashOut");
//		this.kdtEntry.getHeadRow(1).getCell(columnIndex3).setValue("�ۼ����");
//		
//		//�ۼ�ʵ��
//		int columnIndex4 = this.kdtEntry.getColumn("cumulativeCashOut").getColumnIndex()+1;
//		this.kdtEntry.addColumn(columnIndex4);
//		this.kdtEntry.getColumn(columnIndex4).setKey("cumulativeActuallyPaid");
//		this.kdtEntry.getHeadRow(1).getCell(columnIndex4).setValue("�ۼ�ʵ��");
//		
//		int columnIndex5 = this.kdtEntry.getColumn("contractAmount").getColumnIndex();
//		int columnIndex6 = this.kdtEntry.getColumn("cumulativeActuallyPaid").getColumnIndex();
//		// �ں�ָ������
//		KDTMergeManager mm1 = kdtEntry.getHeadMergeManager();
//		mm1.mergeBlock(0, columnIndex5, 0, columnIndex6, KDTMergeManager.SPECIFY_MERGE);
//		
		//���� ��ʽ
		kdtEntry.getColumn("cumulativeCashOut").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntry.getColumn("cumulativeCashOut").getStyleAttributes().setNumberFormat("%r-{#,##0.00*}f");
		kdtEntry.getColumn("cumulativeActuallyPaid").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntry.getColumn("cumulativeActuallyPaid").getStyleAttributes().setNumberFormat("%r-{#,##0.00*}f");
		
		// 2013-12-22 ��ͬ�ҷ� ---end
		
		initButon();
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, this.treeProject, this.actionOnLoad);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeProject.getModel().getRoot();
		treeProject.expandAllNodes(true, root);
		initTable();
		KDTableHelper.setEnterKeyJumpOrientation(kdtEntry, KDTableHelper.HORIZON);
		
		if (null == iFacade) {
			iFacade = DynCostDetailFacadeFactory.getRemoteInstance();
		}
		if("VIEW".equals(oprtState) || "EDIT".equals(oprtState)){
			kDContainer1.setVisible(false);
			if(null != editData && null != editData.getId()){
				setDataObject(editData);
				IRowSet rs = iFacade.getDetailDataForID(editData.getId().toString());
				curProjectid = editData.getCurProject().getId().toString();//biaobiao
				fillTableRowDataForSave(rs);
				
			}
		}
//		kdtEntry.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);//��ѡ
		prmtCurProject.setEnabled(false);
		
    }
    
    /**
     * ����֧��
     * @param id
     * @param iscurPid
     * @return
     * @throws EASBizException
     * @throws BOSException
     */
    public Map getNeedSpending(String id,boolean iscurPid) throws EASBizException, BOSException{
    	Map map = new HashMap();
    	if(id != null ){
    		DynCostDetailInfo dynCostDetailInfo = null;
    		if(iscurPid){
    			EntityViewInfo viewInfo = new EntityViewInfo();
    			FilterInfo filterInfo = new FilterInfo();
    			filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id",id));
    			filterInfo.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
    			viewInfo.setFilter(filterInfo);
    			DynCostDetailCollection dynCostDetailCollection = DynCostDetailFactory.getRemoteInstance().getDynCostDetailCollection(viewInfo);
    			BigDecimal vers = new BigDecimal(0);
    			for (int i = 0; i < dynCostDetailCollection.size(); i++) {
    				DynCostDetailInfo costDetailInfo = dynCostDetailCollection.get(i);
    				if(costDetailInfo.getVersion().compareTo(vers) > 0){
    					vers = costDetailInfo.getVersion();
    					dynCostDetailInfo = costDetailInfo;
    				}
				}
    		}else{
    			dynCostDetailInfo = DynCostDetailFactory.getRemoteInstance().getDynCostDetailInfo(new ObjectUuidPK(id));
    		}
    		if(dynCostDetailInfo != null){
	    		BigDecimal version = dynCostDetailInfo.getVersion();
	    		//�����������汾������֧��ȡ��һ���汾������
	    		if(version != null && version.compareTo(new BigDecimal(0))> 0){
		    		BigDecimal subtract = version;//.subtract(new BigDecimal(1));
		    		DynCostDetailCollection dynCostDetailCollection = DynCostDetailFactory.getRemoteInstance().getDynCostDetailCollection("where version = '"+subtract+"' and curProject.id = '"+dynCostDetailInfo.getCurProject().getId()+"'");
		    		if(dynCostDetailCollection.size() > 0){
		    			DynCostDetailInfo costDetailInfo = dynCostDetailCollection.get(0);//��һ���汾������
		    			
		    			CoreBaseCollection collection = DynCostDetailDataFactory.getRemoteInstance().getCollection("where parent.id = '"+costDetailInfo.getId()+"'");
		    			for (int i = 0; i < collection.size(); i++) {
		    				DynCostDetailDataInfo coreBaseInfo = (DynCostDetailDataInfo) collection.get(i);
		    				
		    				ProgrammingContractInfo programmingInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(coreBaseInfo.getProgrammingContractID()));
		    				if(programmingInfo.getNumber().toString().equals("010")){
		    					System.out.println("--"+i+"---"+coreBaseInfo.getProgrammingContractID()+"--"+programmingInfo.getName()+"----"+programmingInfo.getNumber()+"----"+coreBaseInfo.getNeedSpendingAmount());
		    				}
		    				
//		    				map.put(coreBaseInfo.getProgrammingContractID(), coreBaseInfo.getNeedSpendingAmount());
		    				
		    				//mod by ypf on 20141128 ����֧��ֵΪ0���⣬�����̬�ɱ���ϸ����һ���µĺ�Լ�滮�汾���޶�����ô��Ӱ����һ���µ�����֧����
		    				//ԭ���ǣ���ΪDynCostDetailFacadeControllerBean�У�_getDynCostDetailData����sql��ȡ���°汾�ĺ�Լ�滮id������һ���µ��õ����ϰ汾��Լ�滮���������idƥ������ȡ��һ�汾������֧�����ʱ���Ҳ������ݣ�������ֻ����һ��Ĭ��ֵ0
		    				//����ʽ����Ϊ��Լ�滮�ı���ǲ���ģ�����Ա��������Ϳ��Թ����һ����
		    				ProgrammingContractInfo programmingContractInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(" where id='"+coreBaseInfo.getProgrammingContractID()+"'");
		    			
		    				if(programmingContractInfo.getLongNumber().equals("04"))
		    				{
		    					System.out.println("-------04---������---number����"+programmingContractInfo.getNumber()+"  ---id:"+programmingContractInfo+"    ��"+coreBaseInfo.getNeedSpendingAmount());
		    				}
		    				
		    				//mod by ypf on 20150419 �޸���ȡ��Լ���뷢�����ظ����⣬�磺04��05.04��03.04������Ƕ̱�������⣬������Ҫ�ĳɳ�����
//		    				map.put(programmingContractInfo.getNumber(), coreBaseInfo.getNeedSpendingAmount());
		    				map.put(programmingContractInfo.getLongNumber(), coreBaseInfo.getNeedSpendingAmount());
		    			}
		    		}
	    		}
    		}
    	}
    	return map;
    }
    
    
    
    @Override
    public void onShow() throws Exception {
    	super.onShow();
    }
    
    private void initButon() {
    	if("VIEW".equals(oprtState) || "EDIT".equals(oprtState)){
	    	if(null != editData && null != editData.getState()){
	    		if(FDCBillStateEnum.AUDITTED.equals(editData.getState())){
	    			btnEdit.setEnabled(false);
	    			btnSave.setEnabled(false);
	    			btnSubmit.setEnabled(false);
	    			btnRemove.setEnabled(false);
	    			btnUnAudit.setVisible(false);
	    		}
	    	}
    	}
    	this.btnAttachment.setEnabled(false);
		this.btnAttachment.setVisible(false);
		this.btnRemoveLine.setEnabled(false);
		this.btnRemoveLine.setVisible(false);
		this.btnAuditResult.setEnabled(false);
		this.btnAuditResult.setVisible(false);
		this.btnAudit.setVisible(false);
		this.menuWorkflow.setEnabled(false);
		this.menuWorkflow.setVisible(false);
		this.menuTable1.setEnabled(false);
		this.menuTable1.setVisible(false);
		this.menuBiz.setEnabled(false);
		this.menuBiz.setVisible(false);
		this.btnWorkFlowG.setEnabled(false);
		this.btnWorkFlowG.setVisible(false);
		this.btnAddLine.setEnabled(false);
		this.btnAddLine.setVisible(false);
		this.btnCreateFrom.setEnabled(false);
		this.btnCreateFrom.setVisible(false);
		this.btnInsertLine.setEnabled(false);
		this.btnInsertLine.setVisible(false);
		this.btnMultiapprove.setEnabled(false);
		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setEnabled(false);
		this.btnNextPerson.setVisible(false);
		this.menuItemCreateFrom.setEnabled(false);
		this.menuItemCopyFrom.setEnabled(false);
		this.MenuItemAttachment.setEnabled(false);
		this.prmtCurProject.setEnabled(false);
		this.btnTraceDown.setEnabled(false);
		this.btnTraceDown.setVisible(false);
		this.btnTraceUp.setEnabled(false);
		this.btnTraceUp.setVisible(false);
		this.btnFirst.setEnabled(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setEnabled(false);
		this.btnPre.setVisible(false);
		this.btnNext.setEnabled(false);
		this.btnNext.setVisible(false);
		this.btnLast.setEnabled(false);
		this.btnLast.setVisible(false);
		this.btnAddNew.setEnabled(false);
		this.btnAddNew.setVisible(false);
		this.btnCalculator.setVisible(false);
		this.btnCalculator.setEnabled(false);
		this.btnCopy.setEnabled(false);
		this.btnCopy.setVisible(false);
		this.btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		this.btnRefresh.setText(null);
	}
    
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
    	actionQuery_actionPerformed(null);
    }
    
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	Object oldValue = e.getOldValue();
	    Object newValue = e.getValue();
	    if ((oldValue == null) && (newValue == null)) {
	        return;
	    }
	    if ((oldValue != null) && (newValue != null)) {
	        if(oldValue.equals(newValue)){
	        	return;
	        }
	    }
	    if(e.getColIndex() == kdtEntry.getColumnIndex(KEY_remark)){
	    	return;
	    }
	    
	    IRow row = kdtEntry.getRow(e.getRowIndex());
	    BigDecimal needSpendingAmount = BigDecimal.ZERO;
	    BigDecimal adjustmentDiffAmount =BigDecimal.ZERO;
	    if (e.getColIndex() == kdtEntry.getColumnIndex(KEY_needSpendingAmount)){
	    	needSpendingAmount = FDCHelper.toBigDecimal(newValue);
	    	adjustmentDiffAmount = FDCHelper.toBigDecimal(row.getCell(KEY_adjustmentDiffAmount).getValue());
	    }
	    if (e.getColIndex() == kdtEntry.getColumnIndex(KEY_adjustmentDiffAmount)){
	    	needSpendingAmount = FDCHelper.toBigDecimal(row.getCell(KEY_needSpendingAmount).getValue());
	    	adjustmentDiffAmount = FDCHelper.toBigDecimal(newValue);
	    }
	    computerValue(row, needSpendingAmount, adjustmentDiffAmount);
	    
	    //�޸��ںϺ�ĵ�Ԫ��ֻ���޸��˵�һ�е�Ԫ���ֵ�����൥Ԫ���ֵ��û�б䣬��������
	    String pcID = row.getCell(KEY_programmingContractID).getValue().toString();
	    for (int i = e.getRowIndex()+1; i < kdtEntry.getRowCount(); i++) {
	    	IRow nextRow = kdtEntry.getRow(i);
	    	String nextPCID = nextRow.getCell(KEY_programmingContractID).getValue().toString();
	    	if(pcID.equals(nextPCID)){
	    		nextRow.getCell(KEY_needSpendingAmount).setValue(row.getCell(KEY_needSpendingAmount).getValue());
	    		nextRow.getCell(KEY_dynamicCostOneAmount).setValue(row.getCell(KEY_dynamicCostOneAmount).getValue());
	    		nextRow.getCell(KEY_costDiffOneAmount).setValue(row.getCell(KEY_costDiffOneAmount).getValue());
	    		nextRow.getCell(KEY_targetCostDiffOneRate).setValue(row.getCell(KEY_targetCostDiffOneRate).getValue());
	    		nextRow.getCell(KEY_adjustmentDiffAmount).setValue(row.getCell(KEY_adjustmentDiffAmount).getValue());
	    		nextRow.getCell(KEY_dynamicCostTwoAmount).setValue(row.getCell(KEY_dynamicCostTwoAmount).getValue());
	    		nextRow.getCell(KEY_costDiffTwoAmount).setValue(row.getCell(KEY_costDiffTwoAmount).getValue());
	    		nextRow.getCell(KEY_targetCostDiffTwoRate).setValue(row.getCell(KEY_targetCostDiffTwoRate).getValue());
	    		nextRow.getCell(KEY_remark).setValue(row.getCell(KEY_remark).getValue());
	    	}else{
	    		break;
	    	}
		}
	    
	    setRowLeaf();
	    setUnionData();
	    calAimCostRate();
	    appendFootRow();
	    calFootRowRate();
    }
    
    protected void kdtEntry_tableSelectChanged(KDTSelectEvent e) throws Exception {
    }
    
    // ������֧��������ֵ�ı�ʱ����Ҫ���¼��������е�ֵ
	private void computerValue(IRow row, BigDecimal needSpendingAmount, BigDecimal adjustmentDiffAmount) {
		BigDecimal latestCost = FDCHelper.toBigDecimal(row.getCell("sumLatestCost").getValue());   //�������
		BigDecimal promAmount = FDCHelper.toBigDecimal(row.getCell(KEY_promAmount).getValue());  //�滮���
		
		BigDecimal dynamicCostOneAmount = needSpendingAmount.add(latestCost);   //��̬�ɱ�С�ƣ��������
		BigDecimal costDiffAmount = dynamicCostOneAmount.subtract(promAmount);  //�ɱ����������
		BigDecimal targetCostDiffOneRate = FDCHelper.multiply(100, FDCHelper.divide(costDiffAmount, promAmount, 4, BigDecimal.ROUND_HALF_UP));   //Ŀ��ɱ�ƫ���ʣ��������
		BigDecimal dynamicCostTwoAmount = adjustmentDiffAmount.add(dynamicCostOneAmount);  //��̬�ɱ�С�ƣ������
		BigDecimal costDiffTwoAmount = dynamicCostTwoAmount.subtract(promAmount);  //�ɱ��������
		BigDecimal targetCostDiffTwoRate = FDCHelper.multiply(100, FDCHelper.divide(costDiffTwoAmount, promAmount, 4, BigDecimal.ROUND_HALF_UP));   //Ŀ��ɱ�ƫ���ʣ������
		
		row.getCell(KEY_dynamicCostOneAmount).setValue(dynamicCostOneAmount);    
		row.getCell(KEY_costDiffOneAmount).setValue(costDiffAmount);
		row.getCell(KEY_targetCostDiffOneRate).setValue(targetCostDiffOneRate);
		row.getCell(KEY_dynamicCostTwoAmount).setValue(dynamicCostTwoAmount);
		row.getCell(KEY_costDiffTwoAmount).setValue(costDiffTwoAmount);
		row.getCell(KEY_targetCostDiffTwoRate).setValue(targetCostDiffTwoRate);
	}
    
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		if (null == iFacade) {
			iFacade = DynCostDetailFacadeFactory.getRemoteInstance();
		}
		String curProjectID = getCurProjectID();
		curProjectid = curProjectID;//biaobiao
		if("".equals(prmtCurProject.getValue())){
			MsgBox.showInfo(this, "��ѡ�񹤳���Ŀ�ڵ㣨�ǹ�˾�����û�й�����Ŀ���������ӹ�����Ŀ��");
			SysUtil.abort();
		}
		
		if(null != editData && null != editData.getId()){
			if(DynCostDetailFactory.getRemoteInstance().exists(new ObjectUuidPK(editData.getId().toString()))){
				editData = DynCostDetailFactory.getRemoteInstance().getDynCostDetailInfo(new ObjectUuidPK(editData.getId().toString()));
				if(editData == null )return;
				setDataObject(editData);
				IRowSet rs = iFacade.getDetailDataForID(editData.getId().toString());
				fillTableRowDataForSave(rs);
			}
		}else{
			IRowSet rs = iFacade.getDynCostDetailData(curProjectID);
			needMap = getNeedSpending(curProjectID,true);
			fillTableRowData(rs);
		}
		
		setMergeColumn();
		setRowLeaf();
		setUnionData();
		calAimCostRate();
		appendFootRow();
		calFootRowRate();
	}
    
  //�ϲ�����
    private void setMergeColumn() {
    	int beginRow = 0;
		int endRow = kdtEntry.getRowCount() - 1;
		String mergeColumnKeys[] = { 
				"programmingContractNumber", "programmingContractName", "promAmount", "status", "promBalance",
				"needSpendingAmount", "dynamicCostOneAmount", "costDiffOneAmount","targetCostDiffOneRate", 
				"adjustmentDiffAmount",	"dynamicCostTwoAmount", "costDiffTwoAmount", "targetCostDiffTwoRate", "remark"
		};
		
		if (endRow < 1 || endRow - beginRow < 1)
			return;
		KDTMergeManager kmm = kdtEntry.getMergeManager();
		int[] indexs = new int[mergeColumnKeys.length];
		for (int j = 0, m = mergeColumnKeys.length; j < m; j++) {
			indexs[j] = kdtEntry.getColumnIndex(mergeColumnKeys[j]);
		}
		int i = beginRow, temp = -1;
		Object begin = null;
		Object cur = null;
		for (; i <= endRow; i++) {
			if (i == beginRow) {
				begin = kdtEntry.getCell(i, "programmingContractID").getValue();
				cur = kdtEntry.getCell(i, "programmingContractID").getValue();
				temp = i;
				continue;
			}
			cur = kdtEntry.getCell(i, "programmingContractID").getValue();
			if (cur != null && cur.equals(begin)) {
				continue;
			}
			for (int j = 0, m = mergeColumnKeys.length; j < m; j++) {
				kmm.mergeBlock(temp, indexs[j], i - 1, indexs[j],
						KDTMergeManager.SPECIFY_MERGE);
			}
			begin = kdtEntry.getCell(i, "programmingContractID").getValue();
			temp = i;
		}
		for (int j = 0, m = mergeColumnKeys.length; j < m; j++) {
			kmm.mergeBlock(temp, indexs[j], i - 1, indexs[j],
					KDTMergeManager.SPECIFY_MERGE);
		}
    }

    protected void treeProjectreeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	oprtState = "ADDNEW";
    	this.btnSave.setEnabled(true);
    	if(null != editData && null != editData.getId()){
    		actionAddNew_actionPerformed(null);
    	}
    	if("".equals(getCurProjectID())){
			return;
		}
		actionQuery_actionPerformed(null);
    }
    
    private void initTable(){
		KDCheckBox chbStatus = new KDCheckBox();
		KDTDefaultCellEditor statusEditor = new KDTDefaultCellEditor(chbStatus);
		kdtEntry.getColumn(KEY_status).setEditor(statusEditor);
		
		KDTextField txtRemark = new KDTextField();
		txtRemark.setMaxLength(500);
		KDTDefaultCellEditor txtRemarkEditor = new KDTDefaultCellEditor(txtRemark);
		kdtEntry.getColumn(KEY_remark).setEditor(txtRemarkEditor);
		kdtEntry.getStyleAttributes().setLocked(true);
		kdtEntry.getColumn(KEY_needSpendingAmount).getStyleAttributes().setLocked(false);
		kdtEntry.getColumn(KEY_adjustmentDiffAmount).getStyleAttributes().setLocked(false);
		kdtEntry.getColumn(KEY_remark).getStyleAttributes().setLocked(false);
		kdtEntry.getColumn(KEY_programmingContractID).getStyleAttributes().setLocked(false);
		
		FDCHelper.formatTableNumber(kdtEntry,KEY_promAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_contractAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_actualContractingAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_tempAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_changeAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_settlementAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_latestCost);
		FDCHelper.formatTableNumber(kdtEntry,KEY_promBalance);
		FDCHelper.formatTableNumber(kdtEntry,KEY_needSpendingAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_dynamicCostOneAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_costDiffOneAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_adjustmentDiffAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_dynamicCostTwoAmount);
		FDCHelper.formatTableNumber(kdtEntry,KEY_costDiffTwoAmount);
		
		kdtEntry.getColumn(KEY_targetCostDiffOneRate).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntry.getColumn(KEY_targetCostDiffTwoRate).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntry.getColumn(KEY_targetCostDiffOneRate).getStyleAttributes().setNumberFormat("%r-{#,##0.00%*}f");
		kdtEntry.getColumn(KEY_targetCostDiffTwoRate).getStyleAttributes().setNumberFormat("%r-{#,##0.00%*}f");
		
    	KDFormattedTextField formattedTextField=new KDFormattedTextField();
    	formattedTextField.setVisible(true);
    	formattedTextField.setHorizontalAlignment(2);
    	formattedTextField.setDataType(1);
    	formattedTextField.setPrecision(2);
    	
    	formattedTextField.setName("KEY_needSpendingAmount");
    	KDTDefaultCellEditor needEditor=new KDTDefaultCellEditor(formattedTextField);
    	kdtEntry.getColumn(KEY_needSpendingAmount).setEditor(needEditor);
    	
    	formattedTextField.setName("KEY_adjustmentDiffAmount");
    	KDTDefaultCellEditor ajdustEditor=new KDTDefaultCellEditor(formattedTextField);
    	kdtEntry.getColumn(KEY_adjustmentDiffAmount).setEditor(ajdustEditor);
	}

    private String getCurProjectID() {
		DefaultKingdeeTreeNode projectNode = getProjSelectedTreeNode();
		if(projectNode instanceof KDTreeNode){
			KDTreeNode node = (KDTreeNode)projectNode;
			Object obj = node.getUserObject();
			if(obj instanceof CurProjectInfo){
				CurProjectInfo info = (CurProjectInfo)obj;
				prmtCurProject.setValue(info);
				return info.getId().toString();
			}else{
				prmtCurProject.setValue(null);
			}
		}
		return "";
	}
    
    private DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeProject
				.getLastSelectedPathComponent();
	}
    
    /**
     * ���������
     * @param rs
     * @throws SQLException
     */
    private void fillTableRowData(IRowSet rs) throws SQLException{
		kdtEntry.removeRows();
		IRow row = null;
		while (rs.next()) {
			int i=1;
			String pcID = rs.getString("programmingContractID");  //��ԼID
			/*System.out.println("----fillTableRowData(IRowSet rs)--��"+(i+1)+"��-----pcid��"+pcID);
			if(pcID.equals("cc05QY7uTeuxA8T3gRUw0+zgeds="))
			{
				System.out.println("-----fillTableRowData(IRowSet rs)------���ˣ�"+pcID);
			}
			i = i+1;*/
			
			BigDecimal promAmount = FDCHelper.toBigDecimal(rs.getBigDecimal("promAmount"));  //�滮���
			BigDecimal adjustmentDiffAmount = new BigDecimal("0");  //������ڵ��������ݣ����û����룬������ʱΪ0
			
			row = kdtEntry.addRow();
			row.setTreeLevel(rs.getInt("flevel") - 1);
			kdtEntry.getTreeColumn().setDepth(rs.getInt("flevel"));
			
			row.getCell(KEY_programmingContractID).setValue(pcID);
			row.getCell(KEY_contractID).setValue(rs.getString("contractID"));
			row.getCell(KEY_programmingContractNumber).setValue(rs.getString("programmingContractNumber"));
			row.getCell(KEY_programmingContractName).setValue(rs.getString("programmingContractName"));
			row.getCell(KEY_promAmount).setValue(promAmount);
			row.getCell(KEY_status).setValue(rs.getBoolean("isCiting"));  //�Ƿ�����
			row.getCell("contractNumber").setValue(rs.getString("contractNumber"));
			row.getCell(KEY_contractName).setValue(rs.getString("contractName"));
			//biaobiao ��ͬ�ҷ�
			row.getCell("contractParty").setValue(rs.getString("PartB"));
			row.getCell("contractPartyC").setValue(rs.getString("PartC"));
			//��ȡ������
//			Map map = new HashMap();
//			String contractid = rs.getString("contractID");
//			try {
//				map = DynCostDetailFacadeFactory.getRemoteInstance().getpayRequest(contractid, curProjectid);
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
			
			//�����ͬ����ʾ��ͬ����ؽ��
			if("SUPPLY".equals(rs.getString("contractPropert"))&& ("��".equals(rs.getString("isLonelyCal")))){
				row.getCell(KEY_contractAmount).setValue(new BigDecimal(0));
				row.getCell(KEY_actualContractingAmount).setValue(new BigDecimal(0));
				row.getCell(KEY_tempAmount).setValue(new BigDecimal(0));
				row.getCell(KEY_changeAmount).setValue(new BigDecimal(0));
				row.getCell(KEY_settlementAmount).setValue(new BigDecimal(0));
				row.getCell(KEY_latestCost).setValue(new BigDecimal(0));
				row.getCell("cumulativeCashOut").setValue(rs.getString("prjallreqamt"));//biaobiao �ۼ�����
				row.getCell("cumulativeActuallyPaid").setValue(rs.getString("lstprjallpaidamt"));
			}else{
				row.getCell(KEY_contractAmount).setValue(rs.getBigDecimal("signUpAmount"));
				row.getCell(KEY_actualContractingAmount).setValue(rs.getString("Fcontent"));
				row.getCell(KEY_tempAmount).setValue(rs.getBigDecimal("tempAmount"));
				row.getCell(KEY_changeAmount).setValue(rs.getBigDecimal("changeAmount"));
				row.getCell(KEY_settlementAmount).setValue(rs.getBigDecimal("balanceAmount"));
				row.getCell(KEY_latestCost).setValue(rs.getBigDecimal("latestCost"));
				row.getCell("cumulativeCashOut").setValue(rs.getString("prjallreqamt"));//biaobiao �ۼ�����
				row.getCell("cumulativeActuallyPaid").setValue(rs.getString("lstprjallpaidamt"));
				
			}
			row.getCell(KEY_adjustmentDiffAmount).setValue(adjustmentDiffAmount);
			row.getCell(KEY_remark).setValue("");  //��ע
			row.getCell("level").setValue(rs.getInt("flevel"));
		}
		fillTableRowData();
    }
    
    /**
     * ����Ŀ��ɱ�ƫ���ʡ���̬�ɱ�С�Ƶȵ�
     */
    private void fillTableRowData() {
    	int rowCount = kdtEntry.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntry.getRow(i);
			String PCID = row.getCell(KEY_programmingContractID).getValue().toString();
			/*System.out.println("------��"+(i+1)+"��-----pcid��"+PCID);
			if(PCID.equals("cc05QY7uTeuxA8T3gRUw0+zgeds="))
			{
				System.out.println("-----------���ˣ�"+PCID);
			}*/
			String PCNumber = row.getCell(KEY_programmingContractNumber).getValue().toString();
			BigDecimal sumLastCost = sumLastCostMap().get(PCID);
			BigDecimal promAmount = FDCHelper.toBigDecimal(row.getCell(KEY_promAmount).getValue());  //�滮���
			BigDecimal promBalance = FDCHelper.subtract(promAmount,sumLastCost);  //�滮���
			
//			BigDecimal needamt =(needMap.get(PCID) != null)? new BigDecimal(needMap.get(PCID).toString()):new BigDecimal(0);
			//mod by ypf on 20141128 ����֧��ֵΪ0���⣬�����̬�ɱ���ϸ����һ���µĺ�Լ�滮�汾���޶�����ô��Ӱ����һ���µ�����֧����
			//ԭ���ǣ���ΪDynCostDetailFacadeControllerBean�У�_getDynCostDetailData����sql��ȡ���°汾�ĺ�Լ�滮id������һ���µ��õ����ϰ汾��Լ�滮���������idƥ������ȡ��һ�汾������֧�����ʱ���Ҳ������ݣ�������ֻ����һ��Ĭ��ֵ0
			//����ʽ����Ϊ��Լ�滮�ı���ǲ���ģ�����Ա��������Ϳ��Թ����һ����
			BigDecimal needamt =(needMap.get(PCNumber) != null)? new BigDecimal(needMap.get(PCNumber).toString()):new BigDecimal(0);
			
			BigDecimal needSpendingAmount = needamt;//FDCHelper.subtract(promAmount,sumLastCost);  //biaobiao����֧��
			BigDecimal dynamicCostOneAmount = FDCHelper.add(needSpendingAmount,sumLastCost);   //��̬�ɱ�С�ƣ��������
			BigDecimal costDiffAmount = dynamicCostOneAmount.subtract(promAmount);  //�ɱ����������
			BigDecimal targetCostDiffOneRate = FDCHelper.multiply(FDCHelper.divide(costDiffAmount, promAmount,4,BigDecimal.ROUND_HALF_UP),100);   //Ŀ��ɱ�ƫ���ʣ��������
			BigDecimal adjustmentDiffAmount = new BigDecimal("0");  //������ڵ��������ݣ����û����룬������ʱΪ0
			BigDecimal dynamicCostTwoAmount = adjustmentDiffAmount.add(dynamicCostOneAmount);  //��̬�ɱ�С�ƣ������
			BigDecimal costDiffTwoAmount = dynamicCostTwoAmount.subtract(promAmount);  //�ɱ��������
			BigDecimal targetCostDiffTwoRate = FDCHelper.multiply(FDCHelper.divide(costDiffTwoAmount, promAmount,4,BigDecimal.ROUND_HALF_UP), 100);   //Ŀ��ɱ�ƫ���ʣ������
			
			row.getCell(KEY_promBalance).setValue(promBalance);
			row.getCell(KEY_needSpendingAmount).setValue(needSpendingAmount);
			row.getCell(KEY_dynamicCostOneAmount).setValue(dynamicCostOneAmount);    
			row.getCell(KEY_costDiffOneAmount).setValue(costDiffAmount);
			row.getCell(KEY_targetCostDiffOneRate).setValue(targetCostDiffOneRate);
			row.getCell(KEY_dynamicCostTwoAmount).setValue(dynamicCostTwoAmount);
			row.getCell(KEY_costDiffTwoAmount).setValue(costDiffTwoAmount);
			row.getCell(KEY_targetCostDiffTwoRate).setValue(targetCostDiffTwoRate);
			row.getCell("sumLatestCost").setValue(sumLastCost);
		}
	}
  //2013.04.17
    /*
     * �������Ƿ���Ҷ�ӽڵ�
     */
    private void setRowLeaf() {
    	IRow curRow, nextRow;
    	int curLevel, nextLevel;
    	String curPCID, nextPCID;
    	
    	int rowCount = kdtEntry.getRowCount();
    	
        for(int i = 0; i < rowCount; i++){
        	curRow = kdtEntry.getRow(i);
        	curLevel = curRow.getTreeLevel();
        	curPCID = curRow.getCell("programmingContractID").getValue().toString();
        	
        	//���һ�п϶���Ҷ�ӽڵ�
        	if(i == rowCount-1){
        		curRow.getCell("isLeaf").setValue("true");
        		continue;
        	}
        	if(i == 195){
        		String s = new String();
        		System.out.println(s);
        	}
        	
        	for (int j = i+1; j < rowCount; j++) {
        		nextRow = kdtEntry.getRow(j);
        		nextLevel = nextRow.getTreeLevel();
        		nextPCID = nextRow.getCell("programmingContractID").getValue().toString();
        		if(!curPCID.equals(nextPCID)){
        			//�����ǰ�еļ��δ��ڵ�����һ�еļ��Σ����Ҵ��ڵ�����һ�еļ��Σ���ô��ǰ�о���Ҷ�ӽڵ�
                    if(curLevel >= nextLevel){
                    	curRow.getCell("isLeaf").setValue("true");
                    }else{
                    	curRow.getCell("isLeaf").setValue("false");
                    }
        			break;
        		}
        		//������һ����ܺ�Լ���ڶ��У���˿�ܺ�Լ����Ҷ�ӽڵ�
        		IRow lastRow = kdtEntry.getRow(rowCount-1);
        		String lastPcID = lastRow.getCell("programmingContractID").getValue().toString();
        		if(curPCID.equals(lastPcID)){
        			curRow.getCell("isLeaf").setValue("true");
        		}
			}
        }
        
        
    	/*int preLevel, curLevel, nextLevel;
    	
    	String prePromID = null;
    	String curPromID = null;
    	String nextPromID = null;
    	
    	IRow preRow = null;
    	IRow curRow = null;
    	IRow nextRow = null;
    	
    	List pcList = new ArrayList();  //��ͬ��ܺ�Լ���к�
    	
    	int rowCount = kdtEntry.getRowCount();
    	
        for(int i = 0; i < rowCount; i++){
        	curRow = kdtEntry.getRow(i);
        	curLevel = curRow.getTreeLevel();
        	
        	//��һ��
        	if(i == 0){
        		if(rowCount > 1){
        			nextRow = kdtEntry.getRow(i+1);
        			nextLevel = nextRow.getTreeLevel();
        			nextPromID = nextRow.getCell(KEY_programmingContractID).getValue().toString();
        			curPromID = curRow.getCell(KEY_programmingContractID).getValue().toString();
        			
        			if(curPromID.equals(nextPromID)){
        				pcList.add(i);
        				continue;
        			}
        			if(curLevel < nextLevel){
        				curRow.getCell("isLeaf").setValue("false");//("true");
        			}else{
        				curRow.getCell("isLeaf").setValue("true");
        			}
        		}else{
        			curRow.getCell("isLeaf").setValue("true");
        		}
        		continue;
            }
        	
        	//�������һ�У����һ�п϶���Ҷ�ӽڵ�
        	if(i == rowCount-1){
        		preRow = kdtEntry.getRow(i-1);
    			preLevel = preRow.getTreeLevel();
    			prePromID = preRow.getCell(KEY_programmingContractID).getValue().toString();
    			curPromID = curRow.getCell(KEY_programmingContractID).getValue().toString();
    			
    			//������һ���뵹���ڶ��еĿ�ܺ�Լһ������ô�����ܺ�Լ�������ж���Ҷ�ӽڵ�
    			if(curPromID.equals(prePromID)){
    				pcList.add(i);
    				for (int j = 0; j < pcList.size(); j++) {
    					int rowIndex = Integer.parseInt(pcList.get(j).toString());
    					kdtEntry.getRow(rowIndex).getCell("isLeaf").setValue("true");
					}
    			}else{
    				curRow.getCell("isLeaf").setValue("true");
    			}
    			break;
        	}
        	
        	preRow = kdtEntry.getRow(i-1);
			preLevel = nextRow.getTreeLevel();
        	nextRow = kdtEntry.getRow(i+1);
			nextLevel = nextRow.getTreeLevel();
			prePromID = preRow.getCell(KEY_programmingContractID).getValue().toString();
			curPromID = curRow.getCell(KEY_programmingContractID).getValue().toString();
			nextPromID = nextRow.getCell(KEY_programmingContractID).getValue().toString();
			
			//����ͬ�Ŀ�ܺ�Լ���зŵ�һ��
			if(curPromID.equals(prePromID) || curPromID.equals(nextPromID)){
				pcList.add(i);
				continue;
			}else{
				//����һ����ܺ�Լ�ж��е����
				if(pcList.size() > 1){
					//�����ǰ�еļ���С�ڵ�����һ�еļ��Σ���ô��һ�о���Ҷ�ӽڵ�
					if(curLevel <= preLevel){
						for (int j = 0; j < pcList.size(); j++) {
							int rowIndex = Integer.parseInt(pcList.get(j).toString());
	    					kdtEntry.getRow(rowIndex).getCell("isLeaf").setValue("true");
						}
		            }else{
		            	for (int j = 0; j < pcList.size(); j++) {
		            		int rowIndex = Integer.parseInt(pcList.get(j).toString());
	    					kdtEntry.getRow(rowIndex).getCell("isLeaf").setValue("false");
						}
		            }
					pcList.clear();
				}
			}
			
			//�����ǰ�еļ��δ��ڵ�����һ�еļ��Σ����Ҵ��ڵ�����һ�еļ��Σ���ô��ǰ�о���Ҷ�ӽڵ�
            if(curLevel >= preLevel && curLevel >= nextLevel){
            	curRow.getCell("isLeaf").setValue("true");
            }else{
            	curRow.getCell("isLeaf").setValue("false");
            }
        }*/
        
        for (int j = 0; j < kdtEntry.getRowCount(); j++) {
        	curRow = kdtEntry.getRow(j);
        	
			System.out.println(curRow.getCell("programmingContractNumber").getValue()+"    "+curRow.getCell("isLeaf").getValue());
		}
	}
    
    public void setUnionData() {
    	
    	//���ܸ���ܺ�Լ���Ӧ�ĺ�ͬ����صĽ��
    	String cols[] = { "contractAmount", "actualContractingAmount","tempAmount","changeAmount","settlementAmount","latestCost","cumulativeCashOut","cumulativeActuallyPaid"};
        
        for(int i = 0; i < kdtEntry.getRowCount(); i++){
            IRow row = kdtEntry.getRow(i);
            String isLeaf = row.getCell("isLeaf").getValue().toString();
            if("true".equals(isLeaf))
                continue;
            int level = row.getTreeLevel();
            List aimRowList = new ArrayList();
            for(int j = i + 1; j < kdtEntry.getRowCount(); j++)
            {
                IRow rowAfter = kdtEntry.getRow(j);
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
        
        
        //���ܸ���ܺ�Լ����صĽ��
        String columns[] = { "promAmount", "needSpendingAmount", "dynamicCostOneAmount", "costDiffOneAmount",
				"adjustmentDiffAmount",	"dynamicCostTwoAmount", "costDiffTwoAmount", "promBalance"
		};
        
        for(int i = 0; i < kdtEntry.getRowCount(); i++){
            IRow row = kdtEntry.getRow(i);
            String isLeaf = row.getCell("isLeaf").getValue().toString();
            if("true".equals(isLeaf))
                continue;
            int level = row.getTreeLevel();
            List aimRowList = new ArrayList();
            for(int j = i + 1; j < kdtEntry.getRowCount(); j++)
            {
                IRow rowAfter = kdtEntry.getRow(j);
                if(rowAfter.getTreeLevel() <= level){
                    break;
                }
                String isLeafTemp = rowAfter.getCell("isLeaf").getValue().toString();
                if("true".equals(isLeafTemp)){
                	
                	IRow preRow = kdtEntry.getRow(j-1);
                	IRow curRow = rowAfter;
                	
                	String prePromID = preRow.getCell(KEY_programmingContractID).getValue().toString();;
                	String curPromID = curRow.getCell(KEY_programmingContractID).getValue().toString();;
                	
        			if(curPromID.equals(prePromID)){
        				continue;
        			}else{
        				aimRowList.add(rowAfter);
        			}
                }
            }
            
            for (int j = 0; j < columns.length; j++) {
            	BigDecimal sum = null;
                for(int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++)
                {
                    IRow rowAdd = (IRow)aimRowList.get(rowIndex);
                    Object value = rowAdd.getCell(columns[j]).getValue();
                    if(value != null){
                        sum = FDCHelper.toBigDecimal(sum, 2).add(FDCHelper.toBigDecimal(value, 2));
                    }
                }	
                row.getCell(columns[j]).setValue(sum);
			}
        }
    }
    
    /**
     * ����Ŀ��ɱ�ƫ����
     */
    private void calAimCostRate() {
    	for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			IRow row = kdtEntry.getRow(i);
			BigDecimal costDifOne = FDCHelper.divide(row.getCell(KEY_costDiffOneAmount).getValue(), row.getCell(KEY_promAmount).getValue(),4,BigDecimal.ROUND_HALF_UP);
			BigDecimal costDifTwo = FDCHelper.divide(row.getCell(KEY_costDiffTwoAmount).getValue(), row.getCell(KEY_promAmount).getValue(),4,BigDecimal.ROUND_HALF_UP);
			row.getCell(KEY_targetCostDiffOneRate).setValue(FDCHelper.multiply(costDifOne, 100));
			row.getCell(KEY_targetCostDiffTwoRate).setValue(FDCHelper.multiply(costDifTwo, 100));
		}
	}
    
    /**
     * ����ϼ��е�Ŀ��ɱ�ƫ����
     */
    private void calFootRowRate() {
    	IRow footRow = kdtEntry.getFootRow(0);
    	BigDecimal costDifOne = FDCHelper.divide(footRow.getCell(KEY_costDiffOneAmount).getValue(), footRow.getCell(KEY_promAmount).getValue(),4,BigDecimal.ROUND_HALF_UP);
		BigDecimal costDifTwo = FDCHelper.divide(footRow.getCell(KEY_costDiffTwoAmount).getValue(), footRow.getCell(KEY_promAmount).getValue(),4,BigDecimal.ROUND_HALF_UP);
		footRow.getCell(KEY_targetCostDiffOneRate).setValue(FDCHelper.multiply(costDifOne, 100));
		footRow.getCell(KEY_targetCostDiffTwoRate).setValue(FDCHelper.multiply(costDifTwo, 100));
	}
    
    //����Ѿ������������
    private void fillTableRowDataForSave(IRowSet rs) throws SQLException{
		kdtEntry.removeRows();
		IRow row = null;
		while (rs.next()) {
			row = kdtEntry.addRow();
			
			row.setTreeLevel(rs.getInt("flevel") - 1);
			kdtEntry.getTreeColumn().setDepth(rs.getInt("flevel"));
			
			String pcID = rs.getString("programmingContractID");
			row.getCell(KEY_programmingContractID).setValue(pcID);
			row.getCell(KEY_contractID).setValue(rs.getString("contractID"));
			row.getCell(KEY_programmingContractNumber).setValue(rs.getString("programmingContractNumber"));
			row.getCell(KEY_programmingContractName).setValue(rs.getString("programmingContractName"));
			row.getCell(KEY_promAmount).setValue(rs.getBigDecimal("promAmount"));
			row.getCell(KEY_status).setValue(rs.getBoolean("state"));
			row.getCell("contractNumber").setValue(rs.getString("contractNumber"));
			row.getCell(KEY_contractName).setValue(rs.getString("contractName"));
			//biaobiao ��ͬ�ҷ�����ͬ����
			row.getCell("contractParty").setValue(rs.getString("contractParty"));
			row.getCell("contractPartyC").setValue(rs.getString("contractPartyC"));
			
			row.getCell(KEY_contractAmount).setValue(rs.getBigDecimal("contractAmount"));
			row.getCell(KEY_actualContractingAmount).setValue(rs.getBigDecimal("actualContractingAmount"));
			row.getCell(KEY_tempAmount).setValue(rs.getBigDecimal("tempAmount"));
			row.getCell(KEY_changeAmount).setValue(rs.getBigDecimal("changeAmount"));
			row.getCell(KEY_settlementAmount).setValue(rs.getBigDecimal("balanceAmount"));
			row.getCell(KEY_latestCost).setValue(rs.getBigDecimal("latestCost"));
			row.getCell(KEY_promBalance).setValue(rs.getBigDecimal("promBalance"));
			row.getCell(KEY_needSpendingAmount).setValue(rs.getBigDecimal("needSpending"));
			//biaobiao 
			//��ȡ������
			Map map = new HashMap();
			String contractid = rs.getString("contractID");
			try {
				map = DynCostDetailFacadeFactory.getRemoteInstance().getpayRequest(contractid, curProjectid);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
//			row.getCell("cumulativeCashOut").setValue(map.get(contractid+"SQ"));//biaobiao �ۼ�����
//			row.getCell("cumulativeActuallyPaid").setValue(map.get(contractid+"SF"));
			row.getCell("cumulativeCashOut").setValue(rs.getBigDecimal("cumulativeCashOut"));//biaobiao �ۼ�����
			row.getCell("cumulativeActuallyPaid").setValue(rs.getBigDecimal("cumulativeActuallyPaid"));
			row.getCell(KEY_dynamicCostOneAmount).setValue(rs.getBigDecimal("dynamicCostOneAmount"));    
			row.getCell(KEY_costDiffOneAmount).setValue(rs.getBigDecimal("costDiffOneAmount"));
			row.getCell(KEY_targetCostDiffOneRate).setValue(rs.getBigDecimal("targetCostDiffOneRate"));
			row.getCell(KEY_adjustmentDiffAmount).setValue(rs.getBigDecimal("adjustmentDiff"));
			row.getCell(KEY_dynamicCostTwoAmount).setValue(rs.getBigDecimal("dynamicCostTwoAmount"));
			row.getCell(KEY_costDiffTwoAmount).setValue(rs.getBigDecimal("costDiffTwoAmount"));
			row.getCell(KEY_targetCostDiffTwoRate).setValue(rs.getBigDecimal("targetCostDiffTwoRate"));
			row.getCell(KEY_remark).setValue(rs.getString("remark"));
			row.getCell(KEY_entryID).setValue(rs.getString("entryID"));
			row.getCell("level").setValue(rs.getString("flevel"));
		}
		fillTableRowDataForSave();
		setMergeColumn();
		appendFootRow();
		calFootRowRate();
    }
    
    private void fillTableRowDataForSave() {
    	int rowCount = kdtEntry.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntry.getRow(i);
			String PCID = row.getCell(KEY_programmingContractID).getValue().toString();
			BigDecimal sumLastCost = sumLastCostMap().get(PCID);
			row.getCell("sumLatestCost").setValue(sumLastCost);
		}
	}
    
    private void checkData() throws EASBizException, BOSException{
    	if("".equals(prmtCurProject.getValue()) || null == prmtCurProject.getValue()){
			MsgBox.showInfo(this, "����ѡ�񹤳���Ŀ");
			SysUtil.abort();
		}
		if("ADDNEW".equals(oprtState) || "EDIT".equals(oprtState)){
			if("".equals(txtVersionName.getText().trim())){
				MsgBox.showInfo(this, "�汾���Ʋ���Ϊ�գ����޸ĺ��ٱ���");
				SysUtil.abort();
			}
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject",((CurProjectInfo)prmtCurProject.getValue()).getId(),CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("name",txtVersionName.getText().trim(),CompareType.EQUALS));
			if(null != editData && null != editData.getId()){
				filter.getFilterItems().add(new FilterItemInfo("id",editData.getId(),CompareType.NOTEQUALS));
			}
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			if(DynCostDetailFactory.getRemoteInstance().exists(filter)){
				MsgBox.showInfo(this, "�ù�����Ŀ�°汾�����Ѿ����ڣ������ظ�");
				SysUtil.abort();
			}
		}
		if(kdtEntry.getRowCount() < 1){
			MsgBox.showInfo(this, "��¼���ݲ���Ϊ�գ�");
			SysUtil.abort();
		}
		txtNumber.setText(getDateString());
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	checkData();
		super.actionSave_actionPerformed(e);
		actionQuery_actionPerformed(e);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkData();
        super.actionSubmit_actionPerformed(e);
        actionQuery_actionPerformed(e);
        this.btnSave.setEnabled(false);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionEdit_actionPerformed(e);
    	if(FDCBillStateEnum.SAVED.equals(editData.getState())){
    		this.btnSave.setEnabled(true);
    	}else{
    		this.btnSave.setEnabled(false);
    	}
    }

    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    
    //�鿴��ܺ�Լ
    public void actionViewProgram_actionPerformed(ActionEvent e)throws Exception {
    	KDTSelectBlock block=kdtEntry.getSelectManager().get();
    	if(block==null){
    		MsgBox.showInfo("����ѡ���¼�У�");
    		SysUtil.abort();
    	}
    	int rowIndex=block.getBeginRow();
    	IRow row=kdtEntry.getRow(rowIndex);
    	String proID=row.getCell(KEY_programmingContractID).getValue().toString();
    	UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingContractInfo proContInfo=getProContInfo(proID);
		uiContext.put("programmingContract", proContInfo);
		uiContext.put("programmingContractTemp", "programmingContractTemp");
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
    
    /**
	 * ��ȡ�������Ĺ滮��Լ
	 */
	private ProgrammingContractInfo getProContInfo(String id) throws EASBizException, BOSException {
		SelectorItemCollection selItemCol = new SelectorItemCollection();
		selItemCol.add("*");
		selItemCol.add("costEntries.*");
		selItemCol.add("costEntries.costAccount.*");
		selItemCol.add("costEntries.costAccount.curProject.*");
		selItemCol.add("economyEntries.*");
		selItemCol.add("economyEntries.paymentType.*");
		ProgrammingContractInfo pcInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(id), selItemCol);
		return pcInfo;
	}
	
	/**
	 *  �Ƿ���ʾ�ϼ���
	 */
	protected boolean isFootVisible() {
		return true;
	}
	
	/**
	 * ���Ӻϼ���
	 */
	protected void appendFootRow() {
		if (!this.isFootVisible()) {
			return;
		}
		try {
			IRow footRow = null;
			KDTFootManager footRowManager = kdtEntry.getFootManager();
			if (footRowManager == null) {
				String total = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
				footRowManager = new KDTFootManager(this.kdtEntry);
				footRowManager.addFootView();
				this.kdtEntry.setFootManager(footRowManager);
				footRow = footRowManager.addFootRow(0);
				footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				this.kdtEntry.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
				this.kdtEntry.getIndexColumn().setWidth(30);
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
//			System.out.println(row.getRowIndex());
			for (int j = 0; j < kdtEntry.getColumnCount(); j++) {
				String colName = this.kdtEntry.getColumn(j).getKey();
				for (int k = 0; k < sumColNameList.size(); k++) {
					String fieldName = (String) sumColNameList.get(k);
					if (colName.equalsIgnoreCase(fieldName)) {
						if(totalMap.containsKey(fieldName)){
							BigDecimal promAmount = FDCHelper.toBigDecimal(totalMap.get(fieldName));
							Object fieldval = row.getCell(fieldName).getValue();
							BigDecimal objval = (fieldval != null)?new BigDecimal(fieldval.toString()):new BigDecimal(0);
							promAmount = FDCHelper.add(promAmount,objval );
							totalMap.put(fieldName, promAmount);
						}else{
							totalMap.put(fieldName, row.getCell(fieldName).getValue());
						}
					}
				}
			}
		}
        
        int columnCount = this.kdtEntry.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			String colName = this.kdtEntry.getColumn(i).getKey();
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
	 * ���ܺ�ͬ��ؽ��
	 */
	private void sumContractAmt(IRow footRow) {
		List sumRowList = new ArrayList();
		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			IRow row = kdtEntry.getRow(i);
			if(row.getTreeLevel() == 0){
				sumRowList.add(row);
			}
		}
		
		List sumList = new ArrayList();
		sumList.add(KEY_contractAmount);
		sumList.add(KEY_actualContractingAmount);
		sumList.add(KEY_tempAmount);
		sumList.add(KEY_changeAmount);
		sumList.add(KEY_settlementAmount);
		sumList.add(KEY_latestCost);
		//biaobiao
		sumList.add("cumulativeCashOut");
		sumList.add("cumulativeActuallyPaid");
		
		sumFootRow(sumRowList, sumList, footRow);
	}
	
	/**
	 * ���ܿ�ܺ�Լ��ؽ��
	 */
	private void sumPCAmt(IRow footRow) {
		int curLevel;
    	String prePromID = null;
    	String curPromID = null;
    	IRow preRow = null;
    	IRow curRow = null;
    	int rowCount = kdtEntry.getRowCount();
    	
    	//�õ���Ҫ���ܵ��У��Կ�ܺ�ԼΪά��
    	List sumRowList = new ArrayList();
        for(int i = 0; i < rowCount; i++){
        	curRow = kdtEntry.getRow(i);
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
        	
        	preRow = kdtEntry.getRow(i-1);
			prePromID = preRow.getCell(KEY_programmingContractID).getValue().toString();
			curPromID = curRow.getCell(KEY_programmingContractID).getValue().toString();
			
			if(curPromID.equals(prePromID)){
				continue;
			}else{
				sumRowList.add(curRow);
			}
        }
        
        List sumList = new ArrayList();
        sumList.add(KEY_promAmount);
        sumList.add(KEY_promBalance);
        sumList.add(KEY_needSpendingAmount);
        sumList.add(KEY_dynamicCostOneAmount);
        sumList.add(KEY_costDiffOneAmount);
//        sumList.add(KEY_targetCostDiffOneRate);
        sumList.add(KEY_adjustmentDiffAmount);
        sumList.add(KEY_dynamicCostTwoAmount);
        sumList.add(KEY_costDiffTwoAmount);
//        sumList.add(KEY_targetCostDiffTwoRate);
        
        sumFootRow(sumRowList, sumList, footRow);
	}
	
	//���ܿ�ܺ�Լ��Ӧ��������ۣ���һ����ܺ�Լ��Ӧ���������֮��
	public Map<String, BigDecimal> sumLastCostMap(){
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		int rowCount = kdtEntry.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntry.getRow(i);
			String PCID = row.getCell(KEY_programmingContractID).getValue().toString();
			BigDecimal lastCost = (BigDecimal) row.getCell(KEY_latestCost).getValue();
			if(map.containsKey(PCID)){
				BigDecimal amount = (BigDecimal) map.get(PCID);
				BigDecimal amount2 = FDCHelper.add(amount, lastCost);//amount.add(lastCost); //2013.04.17
				map.put(PCID, amount2);
			}else{
				map.put(PCID, lastCost);
			}
		}
		return map;
	}
	
	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return DynCostDetailFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return kdtEntry;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
	
	protected IObjectValue createNewData() {
		DynCostDetailInfo objectValue = new DynCostDetailInfo();
		return objectValue;
	}

	/**
     * ��ȡ��ǰʱ���ַ������������ñ���
     */
    private String getDateString(){
    	Calendar cal = Calendar.getInstance();
    	Timestamp ts   =  new Timestamp(cal.getTimeInMillis());
    	Date bizDate = new Date(ts.getTime());
    	return bizDate.toString();
    }
}