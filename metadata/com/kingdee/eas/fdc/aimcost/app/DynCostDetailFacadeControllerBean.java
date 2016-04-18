package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class DynCostDetailFacadeControllerBean extends AbstractDynCostDetailFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.DynCostDetailFacadeControllerBean");
    
    protected IRowSet _getDynCostDetailData(Context ctx, String curProjectID) throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer(" ");

		sql.append("select programmingContract.fid as programmingContractID, programmingContract.flongnumber as programmingContractNumber, \n");
		sql.append("programmingContract.fname_l2 as programmingContractName, programmingContract.famount as promAmount, \n");
		sql.append("programmingContract.fisciting as isCiting,programmingContract.Fbalance as promBalance,programmingContract.flevel,t0.* \n");
		sql.append("from T_CON_ProgrammingContract programmingContract \n");  
		sql.append("left join T_CON_Programming programming on programmingContract.FProgrammingID = programming.FID \n"); 
		sql.append("left join \n"); 
		sql.append("( \n");
		
		sql.append("--取正常的合同的相关数据 \n");
		sql.append("select programmingContract.fid as programmingContractID, \n"); 
		sql.append("contract.fid as contractID, contract.fnumber as contractNumber, contract.fname as contractName,contract.famount as signUpAmount, \n"); 
		sql.append("contractEntry.Fcontent as Fcontent,contractEntry2.Fcontent as isLonelyCal,isnull(contract.famount,0) - isnull(contractEntry.Fcontent,0) as tempAmount, \n"); 
		sql.append("settleBill.fcursettleprice as balanceAmount,contract.FHasSettled,t0.changeAmount, contract.FContractPropert as contractPropert, \n"); 
		sql.append("case when contract.FHasSettled = '1' then settleBill.fcursettleprice else isnull(contract.famount,0) + isnull(t0.changeAmount,0) end as latestCost \n"); 
		sql.append("from T_CON_ProgrammingContract programmingContract \n");  
		sql.append("left join T_CON_Programming programming on programmingContract.FProgrammingID = programming.FID \n"); 
		sql.append("left join T_CON_ContractBill contract on contract.FSrcProID = programmingContract.fid \n"); 
		sql.append("left join T_CON_ContractBillEntry contractEntry on contractEntry.Fparentid = contract.fid and contractEntry.Fdetail = '实际承包金额' \n"); 
		sql.append("left join T_CON_ContractBillEntry contractEntry2 on contractEntry2.Fparentid = contract.fid and contractEntry2.Fdetail = '是否单独计算' \n"); 
		sql.append("left join T_CON_ContractSettlementBill settleBill on settleBill.fcontractbillid = contract.fid and settleBill.fstate = '4AUDITTED' \n"); 
		sql.append("left join ( \n"); 
		sql.append("select \n"); 
		sql.append("programmingContract.fid as programmingContractID,contract.fid as contractID, \n"); 
		sql.append("sum(case when contractChange.Fhassettled = 1 then contractChange.fbalanceamount else contractChange.Famount end) as changeAmount \n"); 
		sql.append("from T_CON_ProgrammingContract programmingContract \n"); 
		sql.append("left join T_CON_Programming programming \n"); 
		sql.append("on programmingContract.FProgrammingID = programming.FID AND programming.FState = '4AUDITTED' AND programming.FIsLatest = 1 \n"); 
		sql.append("left join T_CON_ContractBill contract on contract.FSrcProID = programmingContract.fid \n"); 
		sql.append("left join T_CON_ContractChangeBill contractChange on contractChange.Fcontractbillid = contract.fid \n"); 
		sql.append("where programming.FProjectID = '" + curProjectID + "' \n"); 
		sql.append("and (contractChange.fstate = '4AUDITTED' or contractChange.fstate = '8VISA') \n"); 
		sql.append("group by programmingContract.fid,contract.fid \n"); 
		sql.append(") as t0 on t0.programmingContractID = programmingContract.fid and t0.contractID = contract.fid \n"); 
		sql.append("where programming.FProjectID = '" + curProjectID + "' \n"); 
		sql.append("AND programming.FState = '4AUDITTED' AND programming.FIsLatest = 1 \n"); 
		sql.append("and contract.fid is not null \n");
		sql.append("and contract.fstate = '4AUDITTED' \n");

		sql.append("union \n");

		sql.append("--取无文本合同的相关数据 \n");
		sql.append("select programmingContract.fid as programmingContractID, \n");
		sql.append("contractText.fid as contractID, contractText.fnumber as contractNumber, contractText.fname as contractName,contractText.famount as signUpAmount, \n"); 
		sql.append("--因为关联无文本合同的时候，无文本合同的相关金额只有签约金额，故把其他列置为空 \n");
		sql.append("null as Fcontent,null as isLonelyCal,null as tempAmount,null as balanceAmount,null as fissettled,null as changeAmount,null as contractPropert, contractText.famount as latestCost \n");
		sql.append("from T_CON_ProgrammingContract programmingContract \n"); 
		sql.append("left join T_CON_Programming programming \n"); 
		sql.append("on programmingContract.FProgrammingID = programming.FID AND programming.FState = '4AUDITTED' AND programming.FIsLatest = 1 \n"); 
		sql.append("left join T_CON_ContractWithoutText contractText on contractText.FProgrammingContract = programmingContract.fid and contractText.FState = '4AUDITTED' \n");
		sql.append("where programming.FProjectID = '" + curProjectID + "' \n"); 
		sql.append("AND programming.FState = '4AUDITTED' \n"); 
		sql.append("AND programming.FIsLatest = 1 \n"); 
		sql.append("and contractText.fid is not null \n");
		sql.append(") t0 on t0.programmingContractID = programmingContract.FID \n"); 
		sql.append("where programming.FProjectID = '" + curProjectID + "' \n"); 
		sql.append("AND programming.FState = '4AUDITTED' AND programming.FIsLatest = 1 \n"); 
		sql.append("order by programmingContract.flongnumber, programmingContract.fname_l2 \n");
		
		return DbUtil.executeQuery(ctx, sql.toString());
	}
	
	public IRowSet getDetailDataForID(Context ctx, String parentID) throws BOSException {
		StringBuffer sql = new StringBuffer(" ");
		
		sql.append("select \n"); 
		sql.append("entry.FID as entryID, \n"); 
		sql.append("entry.FProgrammingContractID as programmingContractID, \n"); 
		sql.append("pc.flongnumber as programmingContractNumber, \n"); 
		sql.append("pc.fname_l2 as programmingContractName, \n"); 
		sql.append("t0.contractID as contractID, \n"); 
		sql.append("t0.contractNumber as contractNumber, \n"); 
		sql.append("t0.contractName as contractName, \n"); 
		sql.append("entry.FPromAmount as promAmount, \n"); 
		sql.append("entry.FState as state, \n"); 
		sql.append("entry.FContractAmount as contractAmount, \n"); 
		sql.append("entry.FActualContractingAmount as actualContractingAmount, \n"); 
		sql.append("entry.FTempAmount as tempAmount, \n"); 
		sql.append("entry.FChangeAmount as changeAmount, \n"); 
		sql.append("entry.FBalanceAmount balanceAmount, \n"); 
		sql.append("entry.FLatestCost as latestCost, \n"); 
		sql.append("entry.FPromBalance as promBalance, \n"); 
		sql.append("entry.FNeedSpending as needSpending, \n"); 
		sql.append("entry.FDynamicCostOneAmount as dynamicCostOneAmount, \n"); 
		sql.append("entry.FCostDiffOneAmount as costDiffOneAmount, \n"); 
		sql.append("entry.FTargetCostDiffOneRate as targetCostDiffOneRate, \n"); 
		sql.append("entry.FAdjustmentDiff as adjustmentDiff, \n"); 
		sql.append("entry.FDynamicCostTwoAmount as dynamicCostTwoAmount, \n"); 
		sql.append("entry.FCostDiffTwoAmount as costDiffTwoAmount, \n"); 
		sql.append("entry.FTargetCostDiffTwoRate as targetCostDiffTwoRate, \n"); 
		sql.append("entry.FRemark as remark, \n"); 
		sql.append("entry.FLevel as flevel \n");
		sql.append("from T_AIM_DynCostDetailData entry \n"); 
		sql.append("left join T_AIM_DynCostDetail parent on entry.fparentid = parent.fid \n"); 
		sql.append("left join T_CON_ProgrammingContract pc on entry.FProgrammingContractID = pc.fid \n"); 
		sql.append("left join \n"); 
		sql.append("( \n");
		
		sql.append("--关联正常合同 \n");
		sql.append("select contract.fid as contractID, contract.fnumber as contractNumber, contract.fname as contractName \n");
		sql.append("from T_AIM_DynCostDetailData entry \n"); 
		sql.append("left join T_AIM_DynCostDetail parent on entry.fparentid = parent.fid \n"); 
		sql.append("left join T_CON_ProgrammingContract pc on entry.FProgrammingContractID = pc.fid \n"); 
		sql.append("left join T_CON_ContractBill contract on entry.FContractID = contract.fid \n"); 
		sql.append("where parent.fid = '"+parentID+"' \n"); 
		sql.append("and contract.fid is not null \n");
		
		sql.append("union \n");

		sql.append("--关联无文本合同 \n");
		sql.append("select contractText.fid as contractID, contractText.fnumber as contractNumber, contractText.fname as contractName \n");
		sql.append("from T_AIM_DynCostDetailData entry \n"); 
		sql.append("left join T_AIM_DynCostDetail parent on entry.fparentid = parent.fid \n"); 
		sql.append("left join T_CON_ProgrammingContract pc on entry.FProgrammingContractID = pc.fid \n"); 
		sql.append("left join T_CON_ContractWithoutText contractText on entry.FContractID = contractText.fid \n"); 
		sql.append("where parent.fid = '"+parentID+"' \n"); 
		sql.append("and contractText.fid is not null \n");
		sql.append(") t0 on t0.contractID = entry.FContractID \n");
		
		sql.append("where parent.fid = '"+parentID+"' \n"); 
		sql.append("ORDER BY pc.flongnumber,pc.fname_l2 DESC \n");
		
		return DbUtil.executeQuery(ctx, sql.toString());
	}
	
	public BigDecimal getMaxVer(Context ctx, String curProjectID)throws BOSException {
		StringBuffer str=new StringBuffer();
		str.append(" select max(fversion) lastVer ")
		.append(" from t_aim_dynCostDetail ")
		.append(" where fcurProjectID='"+curProjectID+"' ");
		IRowSet rowSet=DbUtil.executeQuery(ctx,str.toString());
		BigDecimal lastVer=BigDecimal.ZERO;
		try {
			rowSet.beforeFirst();
			if(rowSet.next()){
				lastVer=rowSet.getBigDecimal("lastVer");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastVer;
	}
	
	//该方法已废弃
	protected Map _getSumLastCost(Context ctx, String curProjectID) throws BOSException {
		
		return null;
	}
	
	//该方法已废弃
	protected Map _getSumLastCostForSave(Context ctx, String parentID) throws BOSException {
		
		return null;
	}
}