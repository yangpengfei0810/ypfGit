package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;


@SuppressWarnings("serial")
public class ContactOccurDateFacadeControllerBean extends AbstractContactOccurDateFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.ContactOccurDateFacadeControllerBean");

    protected List _getDate(Context ctx, String projectID) throws BOSException, EASBizException {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("select ProgrammingContract.fid programmingContractid, programmingContract.fnumber frameContractNumber, \n");
    	sql.append("programmingContract.fname_l2  frameContractName, programmingContract.famount planningMoney, dcDetailEntry.FlatestCost latestCost, \n");
    	sql.append("sum(dcDetailEntry.FNeedSpending)  unexpend,sum(dcDetailEntry.FAdjustmentDiff) adjustDifferentiation, \n");
    	sql.append("cost.fname_l2  belongtoCostSubject, cost.fid costID, cost.FSrcCostAccountId  srcCostAccountId,pcCost.fgoalcost goalcost, \n");
    	sql.append("pcCost.fcontractassign contractassign  \n");
    	sql.append("from T_CON_ProgrammingContract programmingContract \n");
    	sql.append("left join T_CON_Programming  programming on programmingContract.FProgrammingID = programming.FID \n");
    	sql.append("left join T_AIM_DynCostDetail dcDetail on programming.fprojectid = dcDetail.FCurProjectID and dcDetail.fstate = '4AUDITTED' \n");
    	sql.append("left join T_AIM_DynCostDetailData dcDetailEntry  on dcDetailEntry.fparentid = dcDetail.fid \n");
    	sql.append("and dcDetailEntry.FProgrammingContractID = programmingContract.fid \n");
    	sql.append("left join T_CON_ProgrammingContracCost pcCost on programmingContract.fid = pcCost.fcontractid \n");
    	sql.append("left join T_FDC_CostAccount cost on pcCost.fcostaccountID = cost.fid \n");
    	sql.append("WHERE programming.FProjectID =  '" + projectID + "' \n" );
    	sql.append("and programming.fislatest = '1' \n");
    	sql.append("and dcDetail.fversion = (select max(fversion) from T_AIM_DynCostDetail where fcurprojectid = '" + projectID + "') \n" );
    	sql.append("group by ProgrammingContract.fid,programmingContract.fnumber,programmingContract.fname_l2,programmingContract.famount, \n");
    	sql.append("dcDetailEntry.FlatestCost,cost.fname_l2, cost.fid,cost.FSrcCostAccountId,pcCost.fgoalcost,pcCost.fcontractassign \n");
    	sql.append("ORDER BY programmingContract.fnumber,programmingContract.fname_l2 DESC \n");
    	
        List dataList = new ArrayList();
    	try {
    		IRowSet rs = DbUtil.executeQuery(ctx,sql.toString());
    		while (rs.next()) {
    			Map rowMap = new HashMap();
    			String frameContractNumber = rs.getString("frameContractNumber");
    			String frameContractName = rs.getString("frameContractName");
    			String planningMoney = rs.getString("planningMoney");
    			BigDecimal latestCost = rs.getBigDecimal("latestCost");
    			String unexpend = rs.getString("unexpend");
    			String adjustDifferentiation = rs.getString("adjustDifferentiation");
    			String belongtoCostSubject = rs.getString("belongtoCostSubject");
    			rowMap.put("frameContractNumber", frameContractNumber);
    			rowMap.put("frameContractName", frameContractName);
    			rowMap.put("planningMoney", planningMoney);
    			rowMap.put("latestCost", latestCost);
    			rowMap.put("unexpend", unexpend);
    			rowMap.put("adjustDifferentiation", adjustDifferentiation);
    			rowMap.put("belongtoCostSubject", belongtoCostSubject);
    			rowMap.put("costID", rs.getString("costID"));
    			rowMap.put("programmingContractid", rs.getString("programmingContractid"));
    			rowMap.put("srcCostAccountId", rs.getString("srcCostAccountId"));
    			rowMap.put("goalcost", rs.getBigDecimal("goalcost"));
    			rowMap.put("contractassign", rs.getString("contractassign"));
    			dataList.add(rowMap);
    		}
    	} catch (Exception e) {
    		throw new BOSException(e);
    	}
    	return dataList;
    }
    
    protected List _getContractData(Context ctx, String projectID) throws BOSException, EASBizException 
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select distinct ProgrammingContract.fid programmingContractid,programmingContract.fname_l2  frameContractName, \n");
    	sql.append("programmingContract.famount planningMoney, cost.fname_l2  belongtoCostSubject, cost.fid costID, \n");
    	sql.append("cost.FSrcCostAccountId  srcCostAccountId,pcCost.fgoalcost  goalcost,pcCost.fcontractassign contractassign, \n");
    	sql.append("programmingContract.flongnumber longnumber ,programmingContract.flevel flevel \n");
    	sql.append("from T_CON_ProgrammingContract programmingContract \n");
    	sql.append("left join T_CON_Programming  programming on programmingContract.FProgrammingID = programming.FID \n");
    	sql.append("left join T_CON_ProgrammingContracCost pcCost on programmingContract.fid = pcCost.fcontractid \n");
    	sql.append("left join T_FDC_CostAccount cost on pcCost.fcostaccountID = cost.fid \n");
		sql.append("WHERE programming.FProjectID = '" + projectID + "' \n");
		sql.append("and programming.fislatest = '1'\n");
		sql.append("ORDER BY programmingContract.flongnumber,programmingContract.fname_l2 DESC\n");
		List dataList = new ArrayList();
	    	try {
	    		IRowSet rs = DbUtil.executeQuery(ctx,sql.toString());
	    		while (rs.next()) {
	    			Map rowMap = new HashMap();
	    			String programmingContractid = rs.getString("programmingContractid");
	    			//String frameContractNumber = rs.getString("frameContractNumber");
	    			String frameContractName = rs.getString("frameContractName");
	    			String planningMoney = rs.getString("planningMoney");
	    			String belongtoCostSubject = rs.getString("belongtoCostSubject");
	    			rowMap.put("programmingContractid", programmingContractid);
	    			//rowMap.put("frameContractNumber", frameContractNumber);
	    			rowMap.put("frameContractName", frameContractName);
	    			rowMap.put("planningMoney", planningMoney);
	    			rowMap.put("belongtoCostSubject", belongtoCostSubject);
	    			rowMap.put("costID", rs.getString("costID"));
	    			rowMap.put("programmingContractid", rs.getString("programmingContractid"));
	    			rowMap.put("srcCostAccountId", rs.getString("srcCostAccountId"));
	    			rowMap.put("goalcost", rs.getBigDecimal("goalcost"));
	    			rowMap.put("contractassign", rs.getString("contractassign"));
	    			rowMap.put("longnumber", rs.getString("longnumber"));
	    			rowMap.put("level", rs.getString("flevel"));
	    			dataList.add(rowMap);
	    		}
	    	} catch (Exception e) {
	    		throw new BOSException(e);
	    	}
	    	return dataList;
    }
    
    protected List _getDymData(Context ctx, String projectID) throws BOSException, EASBizException 
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select dcDetailEntry.FProgrammingContractID programmingContractid, sum(dcDetailEntry.FlatestCost) latestCost, \n");
    	sql.append("dcDetailEntry.FNeedSpending  unexpend, dcDetailEntry.FAdjustmentDiff adjustDifferentiation \n");
    	sql.append("from T_AIM_DynCostDetailData dcDetailEntry \n");
    	sql.append("left join  T_AIM_DynCostDetail dcDetail on dcDetailEntry.fparentid = dcDetail.fid \n");
    	sql.append("left join  T_CON_ProgrammingContract pc on dcDetailEntry.fProgrammingContractID = pc.FProgrammingID and pc.flevel = '1'\n");
    	sql.append("where dcDetail.fcurprojectid = '" + projectID + "' \n");
    	sql.append("and dcDetail.fstate = '4AUDITTED' \n");
    	//sql.append("and pc.flevel = '1' \n");
    	sql.append("and dcDetail.fversion = (select max(fversion) from T_AIM_DynCostDetail where fcurprojectid = '" + projectID + "') \n");
    	sql.append("group by dcDetailEntry.FProgrammingContractID, dcDetailEntry.FNeedSpending, dcDetailEntry.FAdjustmentDiff \n");
    	  List dataList = new ArrayList();
      	try {
      		IRowSet rs = DbUtil.executeQuery(ctx,sql.toString());
      		while (rs.next()) {
      			Map rowMap = new HashMap();
      			BigDecimal latestCost = rs.getBigDecimal("latestCost");
      			String programmingContractid = rs.getString("programmingContractid");
      			String unexpend = rs.getString("unexpend");
      			String adjustDifferentiation = rs.getString("adjustDifferentiation");
      			rowMap.put("programmingContractid", programmingContractid);
      			rowMap.put("latestCost", latestCost);
      			rowMap.put("unexpend", unexpend);
      			rowMap.put("adjustDifferentiation", adjustDifferentiation);
      			dataList.add(rowMap);
      		}
      	} catch (Exception e) {
      		throw new BOSException(e);
      	}
      	return dataList;
    }
    
    protected List _getProductType(Context ctx, String projectID) throws BOSException, EASBizException 
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select product.fid productId,product.fname_l2 productName \n");
    	sql.append("from T_FDC_ProductType product \n");
    	sql.append("left join  T_AIM_PlanIndexEntry plinEntry on plinEntry.fproductid = product.fid \n");
    	sql.append("left join  T_AIM_PlanIndex plin on plin.fid = plinEntry.fparentid \n");
    	sql.append("left join T_AIM_MeasureCost mc on plin.fheadID =  mc.fid and mc.fislastversion = '1' \n");
    	sql.append("where \n");
    	sql.append("mc.fprojectid =  '" + projectID + "' \n");
    	
    	 List dataList = new ArrayList();
     	try {
     		IRowSet rs = DbUtil.executeQuery(ctx,sql.toString());
     		while (rs.next()) {
     			Map rowMap = new HashMap();
     			String productName = rs.getString("productName");
     			String productId = rs.getString("productId");
     			rowMap.put("productName", productName);
     			rowMap.put("productId", productId);
     			dataList.add(rowMap);
     		}
     	} catch (Exception e) {
     		throw new BOSException(e);
     	}
     	return dataList;
    	
    }
    
    protected Map _getAimAndHappenedData(Context ctx, String projectID) throws BOSException, EASBizException 
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select programmingContract.fid as programmingContractID, shot.fcostaccountid as costID, entry.fproducttypeid as productTypeID, \n");
    	sql.append("sum(entry.FAimCostAmt) as aimCost,sum(entry.FHasHappenAmt) as happenCost \n");
    	sql.append("from T_AIM_DynCostSnpShtProTypEntry entry  \n");
    	sql.append("left join T_AIM_DynCostSnapShot shot on entry.fparentid=shot.fid \n");
    	sql.append("left join T_FDC_CostAccount cost on shot.fcostaccountid = cost.fid \n");
    	sql.append("inner join T_CON_ProgrammingContracCost pcCost on pcCost.fcostaccountID = shot.fcostaccountid \n");
    	sql.append("left join T_CON_ProgrammingContract programmingContract on programmingContract.fid = pcCost.fcontractid \n");
    	sql.append("left join T_CON_Programming  programming on programmingContract.FProgrammingID = programming.FID and programming.fprojectid = shot.fprojectid \n");
    	sql.append("where shot.fsavedType='5ONLYONE' and shot.fprojectid = '" + projectID + "' \n");
    	sql.append("and programming.fislatest = '1'\n");
    	sql.append("and programming.fversion = (select max(fversion) from T_CON_Programming where fprojectid = '" + projectID + "') \n" );
    	sql.append("group by programmingContract.fid,cost.flongnumber, shot.fcostaccountid, entry.fproducttypeid \n");
    	sql.append("order by programmingContract.fid,cost.flongnumber, shot.fcostaccountid, entry.fproducttypeid  \n");
    	Map pcMap = new HashMap();  //框架合约
      	try {
      		IRowSet rs = DbUtil.executeQuery(ctx,sql.toString());
      		while (rs.next()) {
      			String pcID = rs.getString("programmingContractID");
      			String costID = rs.getString("costID");
      			String productTypeID = rs.getString("productTypeID");
      			//String longnumber = rs.getString("longnumber");
      			BigDecimal[] cost = new BigDecimal[2];
      			cost[0] = rs.getBigDecimal("aimCost");
      			cost[1] = rs.getBigDecimal("happenCost");
      			Map pcMapTemp = new HashMap(); //成本科目
      			Map costMapTemp = new HashMap(); //成本科目
      			Map productMapTemp = new HashMap();  //产品类型
//      			productMapTemp.put(productTypeID, cost);
      			
      			if(pcMap.containsKey(pcID)){
      				costMapTemp = (Map) pcMap.get(pcID);
      				if(costMapTemp.containsKey(costID)){
      					productMapTemp = (Map) costMapTemp.get(costID);
      					productMapTemp.put(productTypeID, cost);
      					//productMapTemp.put("longnumber", longnumber);
      				}else{
      					productMapTemp.put(productTypeID, cost);
      				}
      				costMapTemp.put(costID, productMapTemp);
      			}else{
      				productMapTemp.put(productTypeID, cost);
      				costMapTemp.put(costID, productMapTemp);
      			}
      			pcMap.put(pcID, costMapTemp);
      		}
      	} catch (Exception e) {
      		throw new BOSException(e);
      	}
      	return pcMap;
    }
    
    protected Map _getAimCost(Context ctx, String projectID) throws BOSException {
    	StringBuffer sb = new StringBuffer();
    	sb.append("select pcCost.FContractID as pcID,pcCost.FCostAccountID as costAccountID,mesureColl.FProductID as productID, \n");
    	sb.append("round(case when pcCost.FGoalCost <> 0 then sum(mesureColl.FAmount) / pcCost.FGoalCost * pcCost.FContractAssign end,8) as amount \n");
    	sb.append("from T_CON_ProgrammingContracCost pcCost \n");
    	sb.append("left join T_CON_ProgrammingContract pc on pcCost.FContractID = pc.FID \n");
    	sb.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID \n");
    	sb.append("left join T_AIM_MeasureCost measureCost on measureCost.FProjectID = p.FProjectID \n");
    	sb.append("left join T_AIM_MeasureCollectData mesureColl on mesureColl.FParentID = measureCost.FID and mesureColl.FCostAccountID = pcCost.FCostAccountID\n");
//    	sb.append("left join T_AIM_MeasureEntry measureEntry on measureEntry.FHeadID = measureCost.FID and measureEntry.FCostAccountID = pcCost.FCostAccountID \n");
    	sb.append("left join T_FDC_CostAccount costAccount on pcCost.FCostAccountID = costAccount.FID \n");
    	sb.append("where p.FProjectID = '" + projectID + "' \n");
    	sb.append("and p.FState = '4AUDITTED' and p.FIsLatest = 1 \n");
    	sb.append("and measureCost.FState = '4AUDITTED' and measureCost.FIsLastVersion = 1 \n");
    	sb.append("group by pcCost.FContractID,pcCost.FCostAccountID,mesureColl.FProductID,pcCost.FGoalCost,pcCost.FContractAssign \n");

    	Map pcMap = new HashMap();  //框架合约
      	try {
      		IRowSet rs = DbUtil.executeQuery(ctx,sb.toString());
      		while (rs.next()) {
      			String pcID = rs.getString("pcID");
      			String costAccountID = rs.getString("costAccountID");
      			String productID = rs.getString("productID");
      			BigDecimal amount = rs.getBigDecimal("amount");
      			
      			Map costMap = new HashMap(); //成本科目
      			Map productMap = new HashMap();  //产品类型
      			
      			if(pcMap.containsKey(pcID)){
      				costMap = (Map) pcMap.get(pcID);
      				if(costMap.containsKey(costAccountID)){
      					productMap = (Map) costMap.get(costAccountID);
      					productMap.put(productID, amount);
      				}else{
      					productMap.put(productID, amount);
      				}
      				costMap.put(costAccountID, productMap);
      			}else{
      				productMap.put(productID, amount);
      				costMap.put(costAccountID, productMap);
      			}
      			pcMap.put(pcID, costMap);
      		}
      	} catch (Exception e) {
      		throw new BOSException(e);
      	}
      	return pcMap;
    }
    
    protected Map _getHappenedCost(Context ctx, String projectID) throws BOSException {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select pcID,costAccountID,productID,sum(amount) as amount from ( \n");
    	sql.append("select pc.fid as pcID,contSplitEntry.FCostAccountID as costAccountID, \n");
    	sql.append("contSplitEntry.FProductID as productID,contSplitEntry.FAmount as amount \n");
    	sql.append("from T_CON_ContractCostSplitEntry contSplitEntry \n");
    	sql.append("left join T_CON_ContractCostSplit contSplit on contSplitEntry.FParentID = contSplit.FID \n");
    	sql.append("left join T_CON_ContractBill contract on contSplit.fcontractbillid = contract.fid \n");
    	sql.append("left join T_CON_ProgrammingContract pc on contract.FSrcProID = pc.fid \n");
    	sql.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID and contSplit.FCurProjectID = p.FProjectID \n");
    	sql.append("where p.FProjectID = '" + projectID + "' \n");
    	sql.append("AND p.FState = '4AUDITTED' AND p.FIsLatest = 1 \n");
    	sql.append("and contract.fid not in ( \n");
    	sql.append("select contract.fid \n");
    	sql.append("from T_CON_SettlementCostSplit settSplit \n");
    	sql.append("left join T_CON_ContractBill contract on settSplit.fcontractbillid = contract.fid \n");
    	sql.append("left join T_CON_ProgrammingContract pc on contract.FSrcProID = pc.fid \n");
    	sql.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID and settSplit.FCurProjectID = p.FProjectID \n");
    	sql.append("where p.FProjectID = '" + projectID + "' \n");
    	sql.append("AND p.FState = '4AUDITTED' AND p.FIsLatest = 1 \n");
    	sql.append(") \n");
    	sql.append("union all \n");
    	sql.append("select pc.fid as pcID,changeSplitEntry.FCostAccountID as costAccountID, \n");
    	sql.append("changeSplitEntry.FProductID as productID,changeSplitEntry.FAmount as amount \n");
    	sql.append("from T_CON_ConChangeSplitEntry changeSplitEntry \n");
    	sql.append("left join T_CON_ConChangeSplit changeSplit on changeSplitEntry.FParentID = changeSplit.FID \n");
    	sql.append("left join T_CON_ContractBill contract on changeSplit.fcontractbillid = contract.fid \n");
    	sql.append("left join T_CON_ProgrammingContract pc on contract.FSrcProID = pc.fid \n");
    	sql.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID and changeSplit.FCurProjectID = p.FProjectID \n");  
    	sql.append("where p.FProjectID = '" + projectID + "' \n");
    	sql.append("AND p.FState = '4AUDITTED' AND p.FIsLatest = 1 \n");
    	sql.append("and contract.fid not in ( \n");
    	sql.append("select contract.fid \n");
    	sql.append("from T_CON_SettlementCostSplit settSplit \n");
    	sql.append("left join T_CON_ContractBill contract on settSplit.fcontractbillid = contract.fid \n");
    	sql.append("left join T_CON_ProgrammingContract pc on contract.FSrcProID = pc.fid \n");
    	sql.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID and settSplit.FCurProjectID = p.FProjectID \n");
    	sql.append("where p.FProjectID = '" + projectID + "' \n");
    	sql.append("AND p.FState = '4AUDITTED' AND p.FIsLatest = 1 \n");
    	sql.append(") \n");
    	sql.append("union all \n");
    	sql.append("select pc.fid as pcID,settSplitEntry.FCostAccountID as costAccountID,settSplitEntry.FProductID as productID,settSplitEntry.FAmount as amount \n");
    	sql.append("from T_CON_SettlementCostSplit settSplit \n");
    	sql.append("left join T_CON_SettlementCostSplitEntry settSplitEntry on settSplitEntry.FParentID = settSplit.FID \n");
    	sql.append("left join T_CON_ContractBill contract on settSplit.fcontractbillid = contract.fid \n");
    	sql.append("left join T_CON_ProgrammingContract pc on contract.FSrcProID = pc.fid \n");
    	sql.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID and settSplit.FCurProjectID = p.FProjectID \n");
    	sql.append("where p.FProjectID = '" + projectID + "' \n");
    	sql.append("AND p.FState = '4AUDITTED' AND p.FIsLatest = 1 \n");
    	sql.append("and settSplitEntry.FCostAccountID is not null \n");
    	sql.append("union all \n");
    	//取无文本合同的数据
    	sql.append("select  pc.fid as pcID, paySplitEntry.FCostAccountID as costAccountID, paySplitEntry.FProductID as productID ,paySplitEntry.FAmount as amount \n");
    	sql.append("from T_FNC_PaymentSplitEntry paySplitEntry \n");
    	sql.append("left join T_FNC_PaymentSplit paySplit on paySplitEntry.FParentID = paySplit.FID \n");
    	sql.append("left join T_CON_ContractWithoutText conWithoutText on paySplit.FConWithoutTextID = conWithoutText.FID \n");
    	sql.append("left join T_CON_ProgrammingContract pc on conWithoutText.FProgrammingContract = pc.fid \n");
    	sql.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID and paySplit.FCurProjectID = p.FProjectID \n");
    	sql.append("where p.FProjectID = '" + projectID + "' \n");
    	sql.append("AND p.FState = '4AUDITTED' AND p.FIsLatest = 1 \n");
    	
    	sql.append(") \n");
    	sql.append("where productID is not null \n");
    	sql.append("group by pcID,costAccountID,productID \n");
    	
    	Map pcMap = new HashMap();  //框架合约
      	try {
      		IRowSet rs = DbUtil.executeQuery(ctx,sql.toString());
      		while (rs.next()) {
      			String pcID = rs.getString("pcID");
      			String costAccountID = rs.getString("costAccountID");
      			String productID = rs.getString("productID");
      			BigDecimal amount = rs.getBigDecimal("amount");
      			
      			Map costMap = new HashMap(); //成本科目
      			Map productMap = new HashMap();  //产品类型
      			
      			if(pcMap.containsKey(pcID)){
      				costMap = (Map) pcMap.get(pcID);
      				if(costMap.containsKey(costAccountID)){
      					productMap = (Map) costMap.get(costAccountID);
      					productMap.put(productID, amount);
      				}else{
      					productMap.put(productID, amount);
      				}
      				costMap.put(costAccountID, productMap);
      			}else{
      				productMap.put(productID, amount);
      				costMap.put(costAccountID, productMap);
      			}
      			pcMap.put(pcID, costMap);
      		}
      	} catch (Exception e) {
      		throw new BOSException(e);
      	}
      	return pcMap;
    }
}