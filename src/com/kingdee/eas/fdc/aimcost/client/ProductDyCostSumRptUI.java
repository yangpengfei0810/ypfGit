/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.ProductDyCostSumRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ��Ŀ����Ʒ���Ͷ�̬�ɱ����ܱ�
 */
public class ProductDyCostSumRptUI extends AbstractProductDyCostSumRptUI
{
	private static final Logger logger = CoreUIObject.getLogger(ProductDyCostSumRptUI.class);
	
    private static String KEY_projectName="projectName";//��Ŀ����
    private static String KEY_productType="productType";//��Ʒ����
    private static String KEY_deviationRate="deviationRate";//ƫ����
    private static String KEY_aimSellArea="aimSellArea";//Ŀ��ɱ�-�������
    private static String KEY_aimTotalCost="aimTotalCost";//Ŀ��ɱ�-�ܳɱ�
    private static String KEY_aimSellUnit="aimSellUnit";//Ŀ��ɱ�-���۵���
    private static String KEY_dynSellArea="dynSellArea";//��̬�ɱ�-�������
    private static String KEY_dynTotalCost="dynTotalCost";//��̬�ɱ�-�ܳɱ�
    private static String KEY_dynSellUnit="dynSellUnit";//��̬�ɱ�-���۵���
    private static String KEY_sign="sign";//��ʶ��
    
    private Set projectIDs = new HashSet();
    
    public ProductDyCostSumRptUI() throws Exception {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	btnSearch.setEnabled(true);
    	pkBizDate.setValue(new Date());
    	initTable();
    	fillTable();
    	meregeTable();
    	setSumRow();
    }
    
    private void initTable() throws Exception {
    	tblMain.checkParsed();
    	//���ӱ�ʶ��
    	IColumn coll=tblMain.addColumn();
    	coll.getStyleAttributes().setHided(true);
    	//��ʽ��Ŀ��ɱ����кͶ�̬�ɱ�����
    	int start=tblMain.getColumn(KEY_aimSellArea).getColumnIndex();
    	int end=tblMain.getColumn(KEY_dynSellUnit).getColumnIndex();
    	for(int i=start;i<=end;i++){
    		 tblMain.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	     tblMain.getColumn(i).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	}
    	//ƫ���� 
    	tblMain.getColumn(KEY_deviationRate).getStyleAttributes().setNumberFormat("%r-{#,##0.00%*}f");
    	tblMain.getColumn(KEY_deviationRate).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	tblMain.getStyleAttributes().setLocked(true);
	}
    
    private void fillTable() throws Exception {
    	fillProject();
    	Date bizDate=(Date) pkBizDate.getValue();
    	Map aimSallAreaMap = ProductDyCostSumRptFacadeFactory.getRemoteInstance().getAimSellData(projectIDs);
    	Map dynSallAreaMap = ProductDyCostSumRptFacadeFactory.getRemoteInstance().getDynSellData(projectIDs);
    	Map costMap = ProductDyCostSumRptFacadeFactory.getRemoteInstance().getTotalCost(projectIDs, bizDate);
    	
    	for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			Object obj = row.getUserObject();
			if(obj == null){
				continue;
			}
			String keys = obj.toString();
			BigDecimal aimSaleArea = null; 
			BigDecimal dynSaleArea = null; 
			BigDecimal aimCostTotal = null;
			BigDecimal dynCostTotal = null;
			
			//Ŀ��ɱ�--�������
			if(aimSallAreaMap.containsKey(keys)){
				aimSaleArea = (BigDecimal) aimSallAreaMap.get(keys);
				row.getCell("aimSellArea").setValue(aimSallAreaMap.get(keys));
			}
			//��̬�ɱ�--�������
			if(dynSallAreaMap.containsKey(keys)){
				dynSaleArea = (BigDecimal) dynSallAreaMap.get(keys);
				row.getCell("dynSellArea").setValue(dynSallAreaMap.get(keys));
			}
			if(costMap.containsKey(keys)){
				BigDecimal[] cost = (BigDecimal[]) costMap.get(keys);
				aimCostTotal = cost[0];
				dynCostTotal = cost[1];
				//Ŀ��ɱ�--�ܳɱ�
				row.getCell("aimTotalCost").setValue(cost[0]);
				//��̬�ɱ�--�ܳɱ�
				row.getCell("dynTotalCost").setValue(cost[1]);
				
				row.getCell("deviationRate").setValue(FDCHelper.multiply(FDCHelper.subtract(FDCHelper.divide(cost[1], cost[0], 4, BigDecimal.ROUND_HALF_UP),1), 100));
			}
			//Ŀ��ɱ�--���۵���
			row.getCell("aimSellUnit").setValue(FDCHelper.divide(aimCostTotal, aimSaleArea));
			//��̬�ɱ�--���۵���
			row.getCell("dynSellUnit").setValue(FDCHelper.divide(dynCostTotal, dynSaleArea));
		}
	}
    
    /**
     * ���س���ǰ��֯�µĹ�����Ŀ
     */
    private void fillProject() throws Exception {
    	String curOrgUnitLongNumber= SysContext.getSysContext().getCurrentOrgUnit().getLongNumber().toString();
		IRowSet rowSet=ProductDyCostSumRptFacadeFactory.getRemoteInstance().getProject(curOrgUnitLongNumber);
		while(rowSet.next()){
			IRow row=tblMain.addRow();
			row.getCell(KEY_projectName).setValue(rowSet.getString("projectName"));
			row.getCell(KEY_productType).setValue(rowSet.getString("productName"));
			row.setUserObject(rowSet.getString("keys"));
			projectIDs.add(rowSet.getString("projectID"));
		}
	}
    
    /**
     * �ֶ����úϼ���
     */
    private void setSumRow(){
    	int rowCount=tblMain.getRowCount();
    	IRow footRow;
    	if(rowCount==0){
    		return;
    	}
    	
    	//�кϼ��������
    	if(tblMain.getCell(rowCount-1,0).getValue().equals("�ϼƣ�����Ӫ�����ã�")){
    		tblMain.removeRow(rowCount-1);
    	}
    	footRow=tblMain.addRow();//��ʱ�ĺϼ���
		for(int i=2;i<tblMain.getColumnCount()-2;i++){
			IColumn column=tblMain.getColumn(i);
			if(!column.getKey().equals(KEY_aimSellUnit)&& !column.getKey().equals(KEY_dynSellUnit)&&!column.getKey().equals(KEY_deviationRate)){
				BigDecimal tempAmt=BigDecimal.ZERO;
				for(int j=0;j<tblMain.getRowCount()-1;j++){
					tempAmt=tempAmt.add(FDCHelper.toBigDecimal(tblMain.getCell(j, i).getValue()));
				}
				footRow.getCell(i).setValue(tempAmt);
			}
		}
		BigDecimal aimCostTotal = FDCHelper.toBigDecimal(footRow.getCell("aimTotalCost").getValue());
		BigDecimal dynCostTotal = FDCHelper.toBigDecimal(footRow.getCell("dynTotalCost").getValue());
		footRow.getCell("deviationRate").setValue(FDCHelper.multiply(FDCHelper.subtract(FDCHelper.divide(dynCostTotal, aimCostTotal, 4, BigDecimal.ROUND_HALF_UP),1), 100));
		footRow.getCell(0).setValue("�ϼƣ�����Ӫ�����ã�");
		footRow.getStyleAttributes().setLocked(true);
		footRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
    }
    
    public void actionSearch_actionPerformed(ActionEvent e) throws Exception {
    	tblMain.refresh();
    	fillTable();
    	meregeTable();
    	setSumRow();
    }
    /**
     * ��Ҫ�ںϵ���
     */
    private String[] getMergeRowKeys(){
    	String [] keys={KEY_projectName};
    	return keys;
    }
    
    /**
     *���ں�
     */
    public void meregeTable() {
		KDTable table = tblMain;
		String reference = KEY_projectName;
		String[] col= getMergeRowKeys();
		int beginRow = 0;
		int endRow = table.getRowCount() - 1;
		if (endRow < 1 || endRow - beginRow < 1)
			return;
		KDTMergeManager kmm = table.getMergeManager();
		int[] indexs = new int[col.length];
		for (int j = 0, m = col.length; j < m; j++) {
			indexs[j] = table.getColumnIndex(col[j]);
		}
		int i = beginRow, temp = -1;
		Object begin = null;
		Object cur = null;
		for (; i <= endRow; i++) {
			if (i == beginRow) {
				begin = table.getCell(i, reference).getValue();
				cur = table.getCell(i, reference).getValue();
				temp = i;
				continue;
			}
			cur = table.getCell(i, reference).getValue();
			if (cur != null && cur.equals(begin)) {
				continue;
			}
			for (int j = 0, m = col.length; j < m; j++) {
				kmm.mergeBlock(temp, indexs[j], i - 1, indexs[j],
						KDTMergeManager.SPECIFY_MERGE);
			}
			begin = table.getCell(i, reference).getValue();
			temp = i;
		}
		for (int j = 0, m = col.length; j < m; j++) {
			kmm.mergeBlock(temp, indexs[j], i - 1, indexs[j],
					KDTMergeManager.SPECIFY_MERGE);
		}
	}
}