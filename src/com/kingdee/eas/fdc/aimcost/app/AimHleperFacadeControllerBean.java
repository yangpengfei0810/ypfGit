package com.kingdee.eas.fdc.aimcost.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizAccountBillCollection;
import com.kingdee.eas.cp.bc.BizAccountBillFactory;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.TravelAccountBillCollection;
import com.kingdee.eas.cp.bc.TravelAccountBillFactory;
import com.kingdee.eas.cp.bc.TravelAccountBillInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class AimHleperFacadeControllerBean extends AbstractAimHleperFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.AimHleperFacadeControllerBean");
    
    //获取面积指标管理中的动态指标
    protected Map _getApportionIndex(Context ctx, String projectID)throws BOSException{
    	StringBuffer sb = new StringBuffer();
    	sb.append("select proType.fid as productID,proType.Fname_L2,appType.fname_l2 as apportionName,appType.fid as appid , plxDataEntry.findexValue as indexValue \n");
    	sb.append("from t_aim_planindexentry plxEntry \n");
    	sb.append("left join t_fdc_producttype proType on plxEntry.Fproductid=proType.Fid \n");
    	sb.append("left join t_aim_planindex plx on plx.fid=plxEntry.Fparentid \n");
    	sb.append("left join t_aim_measurecost mct on mct.fid=plx.fheadid \n");
    	sb.append("left join t_fdc_projectIndexData plxData on plxData.fprojOrOrgID = mct.fprojectid and plxData.fProductTypeID = proType.Fid \n");
		sb.append("left join t_fdc_projectIndexDataEntry plxDataEntry on plxData.fid=plxDataEntry.fParentID \n");
		sb.append("left join t_fdc_apportionType appType on plxDataEntry.fApportionTypeID=appType.fid \n");
    	sb.append("where  mct.fprojectid='"+projectID+"' \n");
    	sb.append("and mct.Fislastversion='1' \n");
    	sb.append("and plxEntry.Fissplit='1' \n");
    	sb.append("and plxEntry.fisproduct='1' \n");
    	sb.append("and plxData.fisLatestVer='1' \n");
    	sb.append("and plxData.fprojectstage ='DYNCOST' \n");
    	sb.append("order by proType.fname_l2 desc \n");

//    	sb.append("select plxData.fProductTypeID as productID,appType.fname_l2 as apportionName,appType.fid as appid ,plxDataEntry.findexValue as indexValue \n");
//    	sb.append("from t_fdc_projectIndexData plxData \n");
//    	sb.append("left join t_fdc_projectIndexDataEntry plxDataEntry on plxData.fid=plxDataEntry.fParentID \n");
//    	sb.append("left join t_fdc_apportionType appType on plxDataEntry.fApportionTypeID=appType.fid \n");
//    	sb.append("where plxData.fprojOrOrgID = '"+projectID+"' \n");
//    	sb.append("and plxData.fisLatestVer='1' \n");
//    	sb.append("and plxData.fprojectstage ='DYNCOST' \n");
//    	sb.append("and plxData.fProductTypeID is not null \n");
    	IRowSet rs = DbUtil.executeQuery(ctx,sb.toString());
    	Map map = new HashMap();
    	try {
			while(rs.next()){
				String productID = rs.getString("productID");
				if(map.containsKey(productID)){
					Map temp = (Map) map.get(productID);
//					temp.put(rs.getString("apportionName"), rs.getBigDecimal("indexValue"));
					temp.put(rs.getString("appid"), rs.getBigDecimal("indexValue"));
					temp.put(productID, temp);
				}else{
					Map temp = new HashMap();
//					temp.put(rs.getString("apportionName"), rs.getBigDecimal("indexValue"));
					temp.put(rs.getString("appid"), rs.getBigDecimal("indexValue"));
					map.put(productID, temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return map;
    }
    
    protected Object _getParam(Context ctx, String id) throws BOSException,
    		EASBizException {
    	BizAccountBillCollection accountBillCollection = BizAccountBillFactory.getLocalInstance(ctx).getBizAccountBillCollection("where number = '"+id+"'");
    	if(accountBillCollection.size() > 0){
    		BizAccountBillInfo accountBillInfo = accountBillCollection.get(0);
    		return accountBillInfo;
    	}else{
    		TravelAccountBillCollection billCollection = TravelAccountBillFactory.getLocalInstance(ctx).getTravelAccountBillCollection("where number = '"+id+"'");
    		if(billCollection.size() > 0 ){
    			TravelAccountBillInfo billInfo = billCollection.get(0);
    			 return billInfo;
    		}
    		return null;
    	}
    }
    
}