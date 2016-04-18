package com.kingdee.eas.fdc.aimcost.app;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class PublicSetSplitFacadeControllerBean extends AbstractPublicSetSplitFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.PublicSetSplitFacadeControllerBean");
    
    /**
     * 公共配套对应的产品类型
     */
    protected IRowSet _getPublicSetSplitColums(Context ctx, String curProjectID)
    		throws BOSException {
    	StringBuffer str=new StringBuffer();
		str.append(" select proType.Fname_L2, ")
		.append(" plxEntry.Fissplit, ")
		.append(" plxEntry.Fcontainarea, ")
		.append(" plxEntry.Fbuildarea, ")
		.append(" plxEntry.Fsellarea, ")
		.append(" proType.fid ")
		.append(" from t_aim_planindexentry plxEntry ")
		.append(" left join t_fdc_producttype proType on plxEntry.Fproductid=proType.Fid ")
		.append(" left join t_aim_planindex plx on plx.fid=plxEntry.Fparentid ")
		.append(" left join t_aim_measurecost mct on mct.fid=plx.fheadid ")
		.append(" where  mct.fprojectid='"+curProjectID+"' ")
		.append(" and mct.Fislastversion='1' ")
		.append(" and plxEntry.Fissplit='1' ")
		.append(" and plxEntry.fisproduct='1' ")
		.append(" order by proType.fname_l2 desc ");
		IRowSet rs = DbUtil.executeQuery(ctx,str.toString());
    	return rs;
    }
    
    public void initDBTable(Context ctx, String projectID) throws BOSException {
    	//根据当前工程项目ID查询出是否存在公共配套已发生分摊数据
    	IRowSet rowSet=DbUtil.executeQuery(ctx,"select * from t_aim_publicSetSplit where fcurprojectid='"+projectID+"'" );
    	if(rowSet.size()>0){
    		DbUtil.execute(ctx, "delete from t_aim_publicSetSplit where fcurprojectid='"+projectID+"'" );
    		try {
    			StringBuffer str=new StringBuffer();
				while(rowSet.next()){
					str.append("'"+rowSet.getString("fid")+"',");
				}
				String param=str.substring(0, str.lastIndexOf(","));
				DbUtil.execute(ctx, "delete from t_aim_publicSetSplitEntry where fparentID in ("+param+")" );
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public IRowSet getPublicRow(Context ctx, String curProjectID) throws BOSException {
    	StringBuffer str=new StringBuffer();
		str.append(" select proType.Fname_L2, ")
		.append(" proType.fid ")
		.append(" from t_aim_planindexentry plxEntry ")
		.append(" left join t_fdc_producttype proType on plxEntry.Fproductid=proType.Fid ")
		.append(" left join t_aim_planindex plx on plx.fid=plxEntry.Fparentid ")
		.append(" left join t_aim_measurecost mct on mct.fid=plx.fheadid ")
		.append(" where  mct.fprojectid='"+curProjectID+"' ")
		.append(" and mct.Fislastversion='1' ")
		.append(" and plxEntry.Fissplit='0' ")
		.append(" and plxEntry.fisproduct='1' ")
		.append(" order by proType.fname_l2 desc ");
		IRowSet rs = DbUtil.executeQuery(ctx,str.toString());
    	return rs;
    }
    
    /**
     * 加载行数据
     */
    public IRowSet getRowData(Context ctx, String curProjectID)throws BOSException {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select productID as productTypeID,sum(amount) as happenedAmt from ( \n");
    	sql.append("select pcID,costAccountID,productID,sum(amount) as amount from ( \n");
    	sql.append("select pc.fid as pcID,contSplitEntry.FCostAccountID as costAccountID, \n");
    	sql.append("contSplitEntry.FProductID as productID,contSplitEntry.FAmount as amount \n");
    	sql.append("from T_CON_ContractCostSplitEntry contSplitEntry \n");
    	sql.append("left join T_CON_ContractCostSplit contSplit on contSplitEntry.FParentID = contSplit.FID \n");
    	sql.append("left join T_CON_ContractBill contract on contSplit.fcontractbillid = contract.fid \n");
    	sql.append("left join T_CON_ProgrammingContract pc on contract.FSrcProID = pc.fid \n");
    	sql.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID and contSplit.FCurProjectID = p.FProjectID \n");
    	sql.append("where p.FProjectID = '" + curProjectID + "' \n");
    	sql.append("AND p.FState = '4AUDITTED' AND p.FIsLatest = 1 \n");
    	sql.append("and contract.fid not in ( \n");
    	sql.append("select contract.fid \n");
    	sql.append("from T_CON_SettlementCostSplit settSplit \n");
    	sql.append("left join T_CON_ContractBill contract on settSplit.fcontractbillid = contract.fid \n");
    	sql.append("left join T_CON_ProgrammingContract pc on contract.FSrcProID = pc.fid \n");
    	sql.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID and settSplit.FCurProjectID = p.FProjectID \n");
    	sql.append("where p.FProjectID = '" + curProjectID + "' \n");
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
    	sql.append("where p.FProjectID = '" + curProjectID + "' \n");
    	sql.append("AND p.FState = '4AUDITTED' AND p.FIsLatest = 1 \n");
    	sql.append("and contract.fid not in ( \n");
    	sql.append("select contract.fid \n");
    	sql.append("from T_CON_SettlementCostSplit settSplit \n");
    	sql.append("left join T_CON_ContractBill contract on settSplit.fcontractbillid = contract.fid \n");
    	sql.append("left join T_CON_ProgrammingContract pc on contract.FSrcProID = pc.fid \n");
    	sql.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID and settSplit.FCurProjectID = p.FProjectID \n");
    	sql.append("where p.FProjectID = '" + curProjectID + "' \n");
    	sql.append("AND p.FState = '4AUDITTED' AND p.FIsLatest = 1 \n");
    	sql.append(") \n");
    	sql.append("union all \n");
    	sql.append("select pc.fid as pcID,settSplitEntry.FCostAccountID as costAccountID,settSplitEntry.FProductID as productID,settSplitEntry.FAmount as amount \n");
    	sql.append("from T_CON_SettlementCostSplit settSplit \n");
    	sql.append("left join T_CON_SettlementCostSplitEntry settSplitEntry on settSplitEntry.FParentID = settSplit.FID \n");
    	sql.append("left join T_CON_ContractBill contract on settSplit.fcontractbillid = contract.fid \n");
    	sql.append("left join T_CON_ProgrammingContract pc on contract.FSrcProID = pc.fid \n");
    	sql.append("left join T_CON_Programming p on pc.FProgrammingID = p.FID and settSplit.FCurProjectID = p.FProjectID \n");
    	sql.append("where p.FProjectID = '" + curProjectID + "' \n");
    	sql.append("AND p.FState = '4AUDITTED' AND p.FIsLatest = 1 \n");
    	sql.append("and settSplitEntry.FCostAccountID is not null \n");
    	sql.append(") \n");
    	sql.append("where productID is not null \n");
    	sql.append("group by pcID,costAccountID,productID \n");
    	sql.append(") \n");
    	sql.append("group by productID \n");
    	
    	return DbUtil.executeQuery(ctx,sql.toString());
    }
}