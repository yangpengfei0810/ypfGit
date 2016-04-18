package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class JanDynamicCostSumFacadeControllerBean extends AbstractJanDynamicCostSumFacadeControllerBean {
	
    /**
     * 取当前组织下的所有工程项目
     */
    protected IRowSet _getProject(Context ctx, String curOrgUnitLongNumber)throws BOSException, EASBizException {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("select fid as projectID,fname_l2 as projectName \n");
    	sql.append("from t_fdc_curProject \n");
    	sql.append("where ffullorgunit in (select fid from T_ORG_BaseUnit where flongnumber like '"+curOrgUnitLongNumber+"%') \n");
    	sql.append("and fisleaf = '1' \n");
    	sql.append("and fisenabled = '1' \n");
    	sql.append("order by flongnumber \n");
    	
    	return DbUtil.executeQuery(ctx,sql.toString());
    }
    
    /**
     * 获取目标成本
     */
    protected Map _getAimCost(Context ctx, Set projectIDs) throws BOSException, EASBizException {    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	builder.appendSql("select measureCost.FProjectID as projectid, sum(mesureColl.FAmount) as amount \n");
    	builder.appendSql("from T_AIM_MeasureCollectData mesureColl \n");
    	builder.appendSql("left join T_AIM_MeasureCost measureCost on mesureColl.FParentID = measureCost.FID \n");
    	builder.appendSql("left join T_FDC_CostAccount costAccount on  mesureColl.FCostAccountID = costAccount.FID where \n");
    	builder.appendParam("measureCost.FProjectID ", projectIDs.toArray());
    	builder.appendSql("\n and measureCost.FIsLastVersion = '1' \n");
    	builder.appendSql("and measureCost.FState = '4AUDITTED' \n");
    	builder.appendSql("and costAccount.FLongNumber like  '4001!03%' \n");
    	builder.appendSql("group by measureCost.FProjectID \n");
    	
    	Map map = new HashMap();
    	try {
    		IRowSet rs = builder.executeQuery();
			while(rs.next()){
				map.put(rs.getString("projectID"), rs.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return map;
    }
    
    /**
     * 获取材料调差
     */
    protected Map _getMaterialAdj(Context ctx, Set projectIDs) throws BOSException, EASBizException {
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
 
    	builder.appendSql("select detail.FCurProjectID as projectID, sum(detailData.fdynamicCostOneAmount) as unMaterialAdj, \n");
    	builder.appendSql("sum(detailData.fdynamicCostTwoAmount) as materialAdj \n"); 
    	builder.appendSql("from T_AIM_DynCostDetailData detailData \n"); 
    	builder.appendSql("left join T_AIM_DynCostDetail detail on detailData.FParentID = detail.FID \n"); 
    	builder.appendSql("left join T_CON_ProgrammingContract pc on detailData.FProgrammingContractID = pc.FID \n"); 

    	builder.appendSql("inner join ( \n");
    	builder.appendSql("select max(fversion) as version,FCurProjectID as projectID \n"); 
    	builder.appendSql("from T_AIM_DynCostDetail where \n"); 
    	builder.appendParam("FCurProjectID ", projectIDs.toArray());
    	builder.appendSql("\n and fstate = '4AUDITTED' \n"); 
    	builder.appendSql("group by FCurProjectID \n");
    	builder.appendSql(") t1 \n");
    	builder.appendSql("on t1.projectID = detail.FCurProjectID and t1.version = detail.fversion \n");

    	builder.appendSql("where \n");
    	builder.appendParam("detail.FCurProjectID ", projectIDs.toArray());
    	builder.appendSql("\n and (pc.fname_l2 = '建安工程成本' or pc.fname_l2 like '%建安工程%') \n"); 
    	builder.appendSql("and detail.fstate = '4AUDITTED' \n"); 
    	builder.appendSql("and detailData.flevel = 1 \n");
    	builder.appendSql("group by detail.FCurProjectID \n");
    	
    	Map map = new HashMap();
    	try {
    		IRowSet rs = builder.executeQuery();
			while(rs.next()){
				BigDecimal[] materials = new BigDecimal[2];
				materials[0] = rs.getBigDecimal("unMaterialAdj");
				materials[1] = rs.getBigDecimal("materialAdj");
				map.put(rs.getString("projectID"), materials);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return map;
    }
    
    /**
     * 获取含材料调差的偏差率
     */
    protected Map _getMaterialAdjRate(Context ctx, Set projectIDs) throws BOSException {
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);

    	builder.appendSql("select measureCost.FProjectID, \n");
    	builder.appendSql("isNull(sum(targetCostSplit.FAmount),0) + isNull(sum(setSplit.FHappenedAmt),0) \n"); 
    	builder.appendSql("+ isNull(sum(toSplit.FNeedSpendingAmt),0) + isNull(sum(toSplit.FAdjustmentDiffAmt),0) as amount \n");
    	builder.appendSql("from T_AIM_TargetCostSplit targetCostSplit \n");
    	builder.appendSql("left join T_AIM_MeasureCost measureCost on targetCostSplit.FParentID = measureCost.FID \n");
    	builder.appendSql("left join T_AIM_PublicSetSplit setSplit on setSplit.FCurProjectID = measureCost.FProjectID \n");
    	builder.appendSql("left join T_AIM_PublicToSplit toSplit on toSplit.FCurProjectID = measureCost.FProjectID where \n");
    	builder.appendParam("measureCost.FProjectID ", projectIDs.toArray());
    	builder.appendSql("\n and measureCost.fislastversion = '1' \n");
    	builder.appendSql("and measureCost.fstate = '4AUDITTED' \n");
    	builder.appendSql("group by measureCost.FProjectID \n");
    	
    	Map map = new HashMap();
    	try {
    		IRowSet rs = builder.executeQuery();
			while(rs.next()){
				BigDecimal[] materials = new BigDecimal[2];
				materials[0] = rs.getBigDecimal("unMaterialAdj");
				materials[1] = rs.getBigDecimal("materialAdj");
				map.put(rs.getString("projectID"), materials);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return map;
    }
}