package com.kingdee.eas.fdc.aimcost.app;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class PublicToSplitFacadeControllerBean extends AbstractPublicToSplitFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.PublicToSplitFacadeControllerBean");
    
    public void initDBTable(Context ctx, String curProjectID) throws BOSException {
    	//根据当前工程项目ID查询出是否存在公共配套待发生分摊数据
    	IRowSet rowSet=DbUtil.executeQuery(ctx,"select * from t_aim_publicToSplit where fcurprojectid='"+curProjectID+"'" );
    	if(rowSet.size()>0){
    		DbUtil.execute(ctx, "delete from t_aim_publicToSplit where fcurprojectid='"+curProjectID+"'" );
    		try {
    			StringBuffer str=new StringBuffer();
				while(rowSet.next()){
					str.append("'"+rowSet.getString("fid")+"',");
				}
				String param=str.substring(0, str.lastIndexOf(","));
				DbUtil.execute(ctx, "delete from t_aim_publicToSplitEntry where fparentID in ("+param+")" );
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public IRowSet getRowData(Context ctx, String curProjectID)throws BOSException {
    	StringBuffer str=new StringBuffer();
    	str.append(" select sum(cslEntry.fneedAmt) needAmt ,sum(cslEntry.fadjamt) adjAmt ,cslEntry.fproductid as proTypeID ")
    	.append(" from t_aim_ConOccSplitListEntry  cslEntry ")
    	.append(" left join t_aim_conOccSplitList csl on  csl.fid  =cslEntry.fparentid ")
    	.append(" where csl.fprojectID='"+curProjectID+"' ")
    	.append(" group by cslEntry.fproductid ");
    	IRowSet rowSet=DbUtil.executeQuery(ctx, str.toString());
    	return rowSet;
    }
    
    public IRowSet getPublicToSplitColums(Context ctx, String curProjectID) throws BOSException {
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
    
    public IRowSet getPublicRow(Context ctx, String curProjectID)throws BOSException {
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
}