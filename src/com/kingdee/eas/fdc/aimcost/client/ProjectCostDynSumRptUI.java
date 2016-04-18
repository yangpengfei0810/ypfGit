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

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.ProjectCostDynSumRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 项目全成本动态汇总表
 */
public class ProjectCostDynSumRptUI extends AbstractProjectCostDynSumRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectCostDynSumRptUI.class);
    
    private Set projectIDs = new HashSet();
    
    public ProjectCostDynSumRptUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	initTable();
    	fillTable();
    }
    
    private void initTable() throws Exception{
    	tblMain.checkParsed();
    	tblMain.getStyleAttributes().setLocked(true);
    	//设置金额列的格式
    	for(int i=1;i<12;i++){
   		 tblMain.getColumn(i).getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
   	     tblMain.getColumn(i).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	   	}
	   	//偏差率 
	   	tblMain.getColumn("diviRate").getStyleAttributes().setNumberFormat("%r-{#,##0.00%*}f");
	   	tblMain.getColumn("diviRate").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
	 	tblMain.getColumn("yxdiviRate").getStyleAttributes().setNumberFormat("%r-{#,##0.00%*}f");
	   	tblMain.getColumn("yxdiviRate").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
	 	tblMain.getColumn("kfdiviRate").getStyleAttributes().setNumberFormat("%r-{#,##0.00%*}f");
	   	tblMain.getColumn("kfdiviRate").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
    }
    
    private void fillTable() throws EASBizException, BOSException {
    	BigDecimal aimCostTotal = BigDecimal.ZERO;
		BigDecimal newDynCostTotal = BigDecimal.ZERO;
		BigDecimal diffAmtTotal = BigDecimal.ZERO;
		//biaobiao 
		BigDecimal mkaimCostTotal = BigDecimal.ZERO;//营销费用
		BigDecimal mknewDynCostTotal = BigDecimal.ZERO;
		BigDecimal mkdiffAmtTotal = BigDecimal.ZERO;
		BigDecimal kfaimCostTotal = BigDecimal.ZERO;//开发成本
		BigDecimal kfnewDynCostTotal = BigDecimal.ZERO;
		BigDecimal kfdiffAmtTotal = BigDecimal.ZERO;
		
    	String curOrgUnitLongNumber = getCurOrgUnitLongNumber();
		fillProduct(curOrgUnitLongNumber);
		
		Map aimCostMap = ProjectCostDynSumRptFacadeFactory.getRemoteInstance().getAimCost(projectIDs);
		Map newDynCostMap = ProjectCostDynSumRptFacadeFactory.getRemoteInstance().getNewDynCost(projectIDs);
		
		
		//biaobiao 开发成本、营销费用
		Map marketingAimCost = ProjectCostDynSumRptFacadeFactory.getRemoteInstance().getMarketingAimCost(projectIDs);//营销费用-目标成本
		Map marketingNewDynCost = ProjectCostDynSumRptFacadeFactory.getRemoteInstance().getMarketingNewDynCost(projectIDs);//营销费用-动态成本
		
    	for (int i = 0; i < tblMain.getRowCount(); i++) {
    		BigDecimal aimCost = BigDecimal.ZERO;
    		BigDecimal newDynCost = BigDecimal.ZERO;
    		
    		IRow row = tblMain.getRow(i);
			String productID = row.getUserObject().toString();
			if(aimCostMap.containsKey(productID)){
				aimCost = (BigDecimal) aimCostMap.get(productID);
				aimCostTotal = FDCHelper.add(aimCostTotal, aimCost);
				row.getCell("aimCost").setValue(aimCost);
			}
			
			if(newDynCostMap.containsKey(productID)){
				newDynCost = (BigDecimal) newDynCostMap.get(productID);
				newDynCostTotal = FDCHelper.add(newDynCostTotal, newDynCost);
				row.getCell("lastDynCost").setValue(newDynCost);
			}
			
			BigDecimal diffAmt = FDCHelper.subtract(newDynCost, aimCost);
			diffAmtTotal = FDCHelper.add(diffAmtTotal, diffAmt);
			row.getCell("diffAmt").setValue(diffAmt);
			
			if(aimCost != null && aimCost.compareTo(BigDecimal.ZERO) != 0){
				row.getCell("diviRate").setValue(FDCHelper.multiply(FDCHelper.divide(diffAmt, aimCost,4,BigDecimal.ROUND_HALF_UP),100));
			}
			
			//biaobiao 营销费用
			BigDecimal  marketingaimCost = BigDecimal.ZERO;
    		BigDecimal  marketingnewDynCost = BigDecimal.ZERO;
			if(marketingAimCost.containsKey(productID)){
				marketingaimCost = (BigDecimal) marketingAimCost.get(productID);
				mkaimCostTotal = FDCHelper.add(mkaimCostTotal, marketingaimCost);
				row.getCell("yxaimCost").setValue(marketingaimCost);
			}
			
			if(marketingNewDynCost.containsKey(productID)){
				marketingnewDynCost = (BigDecimal) marketingNewDynCost.get(productID);
				mknewDynCostTotal = FDCHelper.add(mknewDynCostTotal, marketingnewDynCost);
				row.getCell("yxlastDynCost").setValue(marketingnewDynCost);
			}
			
			BigDecimal yxdiffAmt = FDCHelper.subtract(marketingnewDynCost, marketingaimCost);
			mkdiffAmtTotal = FDCHelper.add(mkdiffAmtTotal, yxdiffAmt);
			row.getCell("yxdiffAmt").setValue(yxdiffAmt);
			
			if(marketingaimCost != null && marketingaimCost.compareTo(BigDecimal.ZERO) != 0){
				row.getCell("yxdiviRate").setValue(FDCHelper.multiply(FDCHelper.divide(yxdiffAmt, marketingaimCost,4,BigDecimal.ROUND_HALF_UP),100));
			}
			
			//biaobiao 开发成本
			BigDecimal  kfaimCost = BigDecimal.ZERO;
    		BigDecimal  kfnewDynCost = BigDecimal.ZERO;
    		if(aimCost == null){
    			aimCost = BigDecimal.ZERO;
    		}
    		if(marketingaimCost == null){
    			marketingaimCost = BigDecimal.ZERO;
    		}
//    		System.out.println(aimCost+"----"+marketingaimCost);
    		
    		kfaimCost = aimCost.subtract(marketingaimCost);
    		kfaimCostTotal = FDCHelper.add(kfaimCostTotal, kfaimCost);
    		row.getCell("kfaimCost").setValue(kfaimCost);
    		
    		kfnewDynCost = newDynCost.subtract(marketingnewDynCost);
    		kfnewDynCostTotal = FDCHelper.add(kfnewDynCostTotal, kfnewDynCost);
    		row.getCell("kflastDynCost").setValue(kfnewDynCost);
    		
    		BigDecimal kfdiffAmt = FDCHelper.subtract(kfnewDynCost, kfaimCost);
    		kfdiffAmtTotal = FDCHelper.add(kfdiffAmtTotal, kfdiffAmt);
			row.getCell("kfdiffAmt").setValue(kfdiffAmt);
    		
			if(kfaimCost != null && kfaimCost.compareTo(BigDecimal.ZERO) != 0){
				row.getCell("kfdiviRate").setValue(FDCHelper.multiply(FDCHelper.divide(kfdiffAmt, kfaimCost,4,BigDecimal.ROUND_HALF_UP),100));
			}
			
    	}
    	appendFootRow(aimCostTotal,newDynCostTotal,diffAmtTotal,mkaimCostTotal,mknewDynCostTotal,mkdiffAmtTotal,kfaimCostTotal,kfnewDynCostTotal,kfdiffAmtTotal);
	}
    
    /**
	 * 填充工程项目
	 * @param curOrgUnitLongNumber
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void fillProduct(String curOrgUnitLongNumber) throws EASBizException, BOSException {
		IRowSet rs = ProjectCostDynSumRptFacadeFactory.getRemoteInstance().getProject(curOrgUnitLongNumber);
		try {
			while(rs.next()){
				IRow row = tblMain.addRow();
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
	
	protected void appendFootRow(BigDecimal aimCostTotal,BigDecimal newDynCostTotal,BigDecimal diffAmtTotal,
			BigDecimal mkaimCostTotal,BigDecimal mknewDynCostTotal,BigDecimal mkdiffAmtTotal,
			BigDecimal kfaimCostTotal,BigDecimal kfnewDynCostTotal,BigDecimal kfdiffAmtTotal) {
		IRow row = tblMain.addRow();
		row.getCell("aimCost").setValue(aimCostTotal);
		row.getCell("lastDynCost").setValue(newDynCostTotal);
		row.getCell("diffAmt").setValue(diffAmtTotal);
		
		//biaobiao 营销费用\开发成本
		row.getCell("yxaimCost").setValue(mkaimCostTotal);
		row.getCell("yxlastDynCost").setValue(mknewDynCostTotal);
		row.getCell("yxdiffAmt").setValue(mkdiffAmtTotal);
		row.getCell("kfaimCost").setValue(kfaimCostTotal);
		row.getCell("kflastDynCost").setValue(kfnewDynCostTotal);
		row.getCell("kfdiffAmt").setValue(kfdiffAmtTotal);
		
		row.getCell("yxdiviRate").setValue(FDCHelper.multiply(FDCHelper.divide(mkdiffAmtTotal, mkaimCostTotal, 4, BigDecimal.ROUND_HALF_UP), 100));
		row.getCell("kfdiviRate").setValue(FDCHelper.multiply(FDCHelper.divide(kfdiffAmtTotal, kfaimCostTotal, 4, BigDecimal.ROUND_HALF_UP), 100));
		
		row.getCell("diviRate").setValue(FDCHelper.multiply(FDCHelper.divide(diffAmtTotal, aimCostTotal, 4, BigDecimal.ROUND_HALF_UP), 100));
		
		row.getCell("projectName").setValue("合计");//（含营销费用）
		row.getCell("projectName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		row.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
	}
}