package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProductDyCostSumRptFacadeControllerBean extends AbstractProductDyCostSumRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.ProductDyCostSumRptFacadeControllerBean");
    
    /**
     * 获取当前组织下的项目及其产品类型
     */
    protected IRowSet _getProject(Context ctx, String curOrgUnitLongNumber) throws BOSException {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("select concat(project.fid ,product.FID) as keys,project.FID as projectID, project.fname_l2 as projectName, product.fname_l2 as productName \n");
    	sql.append("from t_fdc_curProject project \n");
    	sql.append("left join T_FDC_CurProjProductEntries projProductEntry on projProductEntry.FCurProject = project.FID \n");
    	sql.append("left join T_FDC_ProductType product on projProductEntry.FProductType = product.FID and product.FIsEnabled = '1' \n");
    	sql.append("left join T_AIM_MeasureCost measureCost on measureCost.FProjectID = project.FID and FIsLastVersion = '1' \n");
    	sql.append("left join T_AIM_PlanIndex planIndex on planIndex.FHeadID = measureCost.FID \n");
    	sql.append("left join T_AIM_PlanIndexEntry planIndexEntry on planIndexEntry.FParentID = planIndex.FID and planIndexEntry.FProductID = product.FID \n");
    	sql.append("where project.ffullorgunit in (select fid from T_ORG_BaseUnit where flongnumber like '"+curOrgUnitLongNumber+"%') \n");
    	sql.append("and project.fisleaf = '1' \n");
    	sql.append("and project.fisenabled = '1' \n");
    	sql.append("and planIndexEntry.FIsSplit <> '0' \n");
    	sql.append("order by project.flongnumber, product.fnumber \n");
    	
    	return DbUtil.executeQuery(ctx,sql.toString());
    }
    
    /**
     * 获取目标成本下的可售面积
     */
    public Map _getAimSellData(Context ctx, Set projectIDs) throws BOSException {
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	builder.appendSql("select concat(prj.FID ,proType.fid) as keys, plxEntry.fsellArea aimSellArea \n");
    	builder.appendSql("from t_aim_planIndexEntry plxEntry \n");
    	builder.appendSql("left join t_fdc_producttype proType on plxEntry.Fproductid=proType.Fid \n");
    	builder.appendSql("left join t_aim_planindex plx on plx.fid=plxEntry.Fparentid \n");
    	builder.appendSql("left join t_aim_measurecost mct on mct.fid=plx.fheadid \n");
    	builder.appendSql("left join t_fdc_curProject prj on prj.fid=mct.fprojectid where \n");
    	builder.appendParam("prj.FID ", projectIDs.toArray());
    	builder.appendSql("\n and mct.Fislastversion='1'  \n");
    	builder.appendSql("and plxEntry.fisproduct='1'  \n");
    	
    	IRowSet rowSet = builder.executeQuery();
    	Map<String, BigDecimal> aimSellAreaMap=new TreeMap<String, BigDecimal>();
    	try {
			rowSet.beforeFirst();
			while(rowSet.next()){
	    		aimSellAreaMap.put(rowSet.getString("keys"),rowSet.getBigDecimal("aimSellArea"));
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return aimSellAreaMap;
    }
    
    /**
     * 取动态成本下的可售面积
     */
    public Map _getDynSellData(Context ctx, Set projectIDs)throws BOSException {
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	builder.appendSql("select concat(prj.FID ,proType.fid) as keys, plxDataEntry.findexValue dynSellArea \n");
    	builder.appendSql("from t_fdc_projectIndexData plxData \n");
    	builder.appendSql("left join t_fdc_productType proType on proType.fid=plxData.fProductTypeID \n");
    	builder.appendSql("left join t_fdc_projectIndexDataEntry plxDataEntry on plxData.fid=plxDataEntry.fParentID \n");
    	builder.appendSql("left join t_fdc_apportionType appType on plxDataEntry.fApportionTypeID=appType.fid \n");
    	builder.appendSql("left join t_fdc_curProject prj on prj.fid=plxData.fprojOrOrgID \n where ");
    	builder.appendParam("prj.FID ", projectIDs.toArray());
    	builder.appendSql("\n and plxData.fisLatestVer='1' \n");
    	builder.appendSql("and appType.fname_l2='可售面积' \n"); //奇怪，有时候是"经营区面积"，有时候是“可售面积”
    	builder.appendSql("and plxData.fprojectstage ='DYNCOST' \n");
    	builder.appendSql("and proType.fname_l2 is not null \n");
    	
    	IRowSet rowSet = builder.executeQuery();
    	Map<String, BigDecimal> dynSellAreaMap=new TreeMap<String, BigDecimal>();
    	try {
			rowSet.beforeFirst();
			while(rowSet.next()){
				dynSellAreaMap.put(rowSet.getString("keys"),rowSet.getBigDecimal("dynSellArea"));
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return dynSellAreaMap;
    }
    
    /**
     * 该产品类型的目标成本和动态成本
     */
    public Map _getTotalCost(Context ctx, Set projectIDs, Date bizDate) throws BOSException {
    	String keyYear=String.valueOf(bizDate.getYear()+1900);
    	String keyMonth=String.valueOf(bizDate.getMonth()+1);
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	builder.appendSql("select concat(prj.fid ,pddEntry.fproductTypeID) as keys , \n");
    	builder.appendSql("sum(pddEntry.faimCost) aimCost,sum(pddEntry.fdynamicCost) dynCost \n");
    	builder.appendSql("from t_aim_pdCostDetailEntry  pddEntry \n");
    	builder.appendSql("left join t_aim_pdCostDetail pdd on pddEntry.fparentID=pdd.fid  \n");
    	builder.appendSql("left join t_fdc_curProject prj on pdd.fcurProjectID=prj.fid \n");
    	builder.appendSql("inner join  T_FDC_CostAccount costAcct on pddEntry.FCostAccountID = costAcct.FID \n where ");
    	builder.appendParam("prj.FID ", projectIDs.toArray());
    	builder.appendSql("\n and pdd.fstate='4AUDITTED'  \n");
    	builder.appendSql("and to_char(year(pdd.fbizDate))='"+keyYear+"' \n");
    	builder.appendSql("and to_char(month(pdd.fbizDate))='"+keyMonth+"' \n");
    	builder.appendSql("and costAcct.FLongNumber not like '4001!08%' \n");
    	builder.appendSql("and costAcct.FLongNumber not like '4002%' \n");
    	builder.appendSql("group by concat(prj.fid ,pddEntry.fproductTypeID) \n");

    	IRowSet rowSet = builder.executeQuery();
    	Map<String, BigDecimal[]> costMap = new TreeMap<String, BigDecimal[]>();
    	try {
			rowSet.beforeFirst();
			while(rowSet.next()){
				BigDecimal[] cost = new BigDecimal[2];
				cost[0] = rowSet.getBigDecimal("aimCost");
				cost[1] = rowSet.getBigDecimal("dynCost");
				costMap.put(rowSet.getString("keys"),cost);
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return costMap;
    }
}