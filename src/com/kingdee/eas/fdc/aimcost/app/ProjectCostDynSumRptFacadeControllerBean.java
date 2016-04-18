package com.kingdee.eas.fdc.aimcost.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProjectCostDynSumRptFacadeControllerBean extends AbstractProjectCostDynSumRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.ProjectCostDynSumRptFacadeControllerBean");

    /**
     * 取当前组织下的所有工程项目
     */
    protected IRowSet _getProject(Context ctx, String curOrgUnitLongNumber) throws BOSException {
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
    protected Map _getAimCost(Context ctx, Set projectIDs) throws BOSException {    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	builder.appendSql("select measureCost.FProjectID as projectid, sum(mesureColl.FAmount) as amount \n");
    	builder.appendSql("from T_AIM_MeasureCollectData mesureColl \n");
    	builder.appendSql("left join T_AIM_MeasureCost measureCost on mesureColl.FParentID = measureCost.FID \n");
    	builder.appendSql("left join T_FDC_CostAccount costAccount on  mesureColl.FCostAccountID = costAccount.FID where \n");
    	builder.appendParam("measureCost.FProjectID ", projectIDs.toArray());
    	builder.appendSql("\n and measureCost.FIsLastVersion = '1' \n");
    	builder.appendSql("and measureCost.FState = '4AUDITTED' \n");
    	//builder.appendSql("and costAccount.FLongNumber like  '4001!03%' \n");
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
     * 获取最新动态成本
     */
    protected Map _getNewDynCost(Context ctx, Set projectIDs) throws BOSException {
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	 
    	builder.appendSql("select t2.projectID as projectID, sum(t2.newDynCost) as newDynCost from ( \n");
    	builder.appendSql("select detail.FCurProjectID as projectID, detailData.fdynamicCostTwoAmount as newDynCost, pc.fid as pcID \n");
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

    	builder.appendSql("where detailData.flevel = '1' \n");
    	//builder.appendParam("detail.FCurProjectID ", projectIDs.toArray());
    	//builder.appendSql("\n and detail.fstate = '4AUDITTED' \n"); 
    	//builder.appendSql("and detailData.flevel = 1 \n");
    	builder.appendSql("group by detail.FCurProjectID,detailData.fdynamicCostTwoAmount,pc.fid \n");
    	builder.appendSql(") t2 \n");
    	builder.appendSql("group by t2.projectID \n");
    
    
    	
    	Map map = new HashMap();
    	try {
    		IRowSet rs = builder.executeQuery();
			while(rs.next()){
				map.put(rs.getString("projectID"), rs.getBigDecimal("newDynCost"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return map;
    }
    
    //获取营销费用目标成本
    protected Map _getMarketingAimCost(Context ctx, Set projectIDs)
    		throws BOSException {
    	FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
    	sql.appendSql(" select sum(mesureColl.FAmount) as amount ,measureCost.FProjectID as projectID  from T_AIM_MeasureCollectData  mesureColl\n");
    	sql.appendSql(" left join T_FDC_CostAccount costAccount on  mesureColl.FCostAccountID = costAccount.FID \n");
    	sql.appendSql(" left join T_AIM_MeasureCost measureCost on mesureColl.FParentID = measureCost.FID\n");
    	sql.appendSql(" where costAccount.fparentid in (select fid from T_FDC_CostAccount where \n");
    	sql.appendParam(" FcurProject ",projectIDs.toArray());
    	sql.appendSql(" and (fnumber like '4002%' or fnumber like '4001.08%' or fcodingnumber like '4002%' or fcodingnumber like '4001.08%'))  and \n");
    	sql.appendParam(" measureCost.FProjectID ", projectIDs.toArray());
    	sql.appendSql(" and measureCost.FIsLastVersion = '1' and measureCost.FState = '4AUDITTED' \n");
    	sql.appendSql(" group by measureCost.FProjectID ");
    	
    	Map map = new HashMap();
    	try {
    		IRowSet rs = sql.executeQuery();
			while(rs.next()){
				map.put(rs.getString("projectID"), rs.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return map;
    }
    
    //获取营销费用最新动态成本
    protected Map _getMarketingNewDynCost(Context ctx, Set projectIDs)
    		throws BOSException {
    	FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
    	
    	sql.appendSql(" select detailData.FDynamicCostTwoAmount as amount ,detail.FCurProjectID  as projectID from T_AIM_DynCostDetailData detailData \n");
    	sql.appendSql(" left join T_AIM_DynCostDetail detail on detailData.FParentID = detail.FID \n");
    	sql.appendSql(" left join T_CON_ProgrammingContract pc on detailData.FProgrammingContractID = pc.FID \n");
    	sql.appendSql(" inner join ( select max(fversion) as version,FCurProjectID as projectID from T_AIM_DynCostDetail where \n");
    	sql.appendParam(" FCurProjectID ",projectIDs.toArray());
    	sql.appendSql(" and fstate = '4AUDITTED' group by FCurProjectID ) t1 on t1.projectID = detail.FCurProjectID and t1.version = detail.fversion");
    	sql.appendSql(" where  pc.fname_l2 = '营销费用' ");
    	
    	Map map = new HashMap();
    	try {
    		IRowSet rs = sql.executeQuery();
			while(rs.next()){
				map.put(rs.getString("projectID"), rs.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return map;
    }
}