/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.JanDynamicCostSumFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class JanDynamicCostSumRptUI extends AbstractJanDynamicCostSumRptUI {

	private Set projectIDs = new HashSet();
	public JanDynamicCostSumRptUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		fillTable();
	}
	
	private void initTable() {
		kDTableJan.checkParsed();
		FDCHelper.formatTableNumber(kDTableJan,"aimCost");
		FDCHelper.formatTableNumber(kDTableJan,"unincMatAdj");
		FDCHelper.formatTableNumber(kDTableJan,"incMatAdj");
		kDTableJan.getColumn("unerrorRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kDTableJan.getColumn("errorRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kDTableJan.getColumn("unerrorRate").getStyleAttributes().setNumberFormat("%r-{#,##0.00%*}f");
		kDTableJan.getColumn("errorRate").getStyleAttributes().setNumberFormat("%r-{#,##0.00%*}f");
	}
	
	private void fillTable() throws BOSException, EASBizException {
		BigDecimal aimCostTotal = BigDecimal.ZERO;
		BigDecimal unMaterialAdjTotal = BigDecimal.ZERO;
		BigDecimal materialAdjTotal = BigDecimal.ZERO;
		
		String curOrgUnitLongNumber = getCurOrgUnitLongNumber();
		fillProduct(curOrgUnitLongNumber);
		
		Map aimCostMap = JanDynamicCostSumFacadeFactory.getRemoteInstance().getAimCost(projectIDs);
		Map materialAdjMap = JanDynamicCostSumFacadeFactory.getRemoteInstance().getMaterialAdj(projectIDs);
//		Map materialAdjRateMap = JanDynamicCostSumFacadeFactory.getRemoteInstance().getMaterialAdjRate(projectIDs);
		
		for (int i = 0; i < kDTableJan.getRowCount(); i++) {
			BigDecimal aimCost = BigDecimal.ZERO;
			BigDecimal unMaterialAdj = BigDecimal.ZERO;
			BigDecimal materialAdj = BigDecimal.ZERO;
			IRow row = kDTableJan.getRow(i);
			String productID = row.getUserObject().toString();
			if(aimCostMap.containsKey(productID)){
				aimCost = (BigDecimal) aimCostMap.get(productID);
				aimCostTotal = FDCHelper.add(aimCostTotal, aimCost);
				row.getCell("aimCost").setValue(aimCost);
			}
			
			if(materialAdjMap.containsKey(productID)){
				BigDecimal[] materials = (BigDecimal[]) materialAdjMap.get(productID);
				unMaterialAdj = materials[0];
				materialAdj = materials[1];
				unMaterialAdjTotal = FDCHelper.add(unMaterialAdjTotal, unMaterialAdj);
				materialAdjTotal = FDCHelper.add(materialAdjTotal, materialAdj);
				row.getCell("unincMatAdj").setValue(unMaterialAdj);
				row.getCell("incMatAdj").setValue(materialAdj);
			}
			
			if(aimCost != null && aimCost.compareTo(BigDecimal.ZERO) != 0){
				row.getCell("unerrorRate").setValue(FDCHelper.multiply(FDCHelper.divide(FDCHelper.subtract(unMaterialAdj, aimCost),aimCost,4,BigDecimal.ROUND_HALF_UP),100));
				row.getCell("errorRate").setValue(FDCHelper.multiply(FDCHelper.divide(FDCHelper.subtract(materialAdj, aimCost),aimCost,4,BigDecimal.ROUND_HALF_UP),100));
			}
		}
		
		appendFootRow(aimCostTotal,unMaterialAdjTotal,materialAdjTotal);
	}
	
	/**
	 * 填充工程项目
	 * @param curOrgUnitLongNumber
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void fillProduct(String curOrgUnitLongNumber) throws EASBizException, BOSException {
		IRowSet rs = JanDynamicCostSumFacadeFactory.getRemoteInstance().getProject(curOrgUnitLongNumber);
		try {
			while(rs.next()){
				IRow row = kDTableJan.addRow();
				row.setUserObject(rs.getString("projectID"));
				row.getCell("projectName").setValue(rs.getString("projectName"));
				projectIDs.add(rs.getString("projectID"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
	}
	
	/**
	 * 获取当前组织长编码
	 * @return
	 */
	private String getCurOrgUnitLongNumber() {
		return SysContext.getSysContext().getCurrentOrgUnit().getLongNumber().toString();
	}

	protected void appendFootRow(BigDecimal aimCostTotal,BigDecimal unMaterialAdjTotal,BigDecimal materialAdjTotal) {
		IRow row = kDTableJan.addRow();
		row.getCell("aimCost").setValue(aimCostTotal);
		row.getCell("unincMatAdj").setValue(unMaterialAdjTotal);
		row.getCell("incMatAdj").setValue(materialAdjTotal);
		
		row.getCell("projectName").setValue("合     计");
		row.getCell("projectName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		row.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
	}
}