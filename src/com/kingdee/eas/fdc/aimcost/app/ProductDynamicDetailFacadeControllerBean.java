package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProductDynamicDetailFacadeControllerBean extends AbstractProductDynamicDetailFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.ProductDynamicDetailFacadeControllerBean");
    
    protected Map _getDetailData(Context ctx, Set projectIDs) throws BOSException {
    	Map dataMap = new HashMap();
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	//取目标可售面积
    	builder.appendSql("select proType.fid as productID, plxEntry.fsellArea aimSellArea \n");
    	builder.appendSql("from t_aim_planIndexEntry plxEntry \n");
    	builder.appendSql("left join t_fdc_producttype proType on plxEntry.Fproductid=proType.Fid \n");
    	builder.appendSql("left join t_aim_planindex plx on plx.fid=plxEntry.Fparentid \n");
    	builder.appendSql("left join t_aim_measurecost mct on mct.fid=plx.fheadid \n");
    	builder.appendSql("left join t_fdc_curProject prj on prj.fid=mct.fprojectid where \n");
    	builder.appendParam("prj.FID ", projectIDs.toArray());
    	builder.appendSql("\n and mct.Fislastversion='1'  \n");
    	builder.appendSql("and plxEntry.fisproduct='1'  \n");
    	builder.appendSql("and plxEntry.fisproduct='1'  \n");
    	
    	IRowSet rs = builder.executeQuery();
    	Map aimSellAreaMap = new HashMap();
    	try {
			while(rs.next()){
				aimSellAreaMap.put(rs.getString("productID"), rs.getBigDecimal("aimSellArea"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		dataMap.put("aimSellAreaMap", aimSellAreaMap) ;
    	
    	//取动态可售面积
		builder.clear();
    	builder.appendSql("select productType.fid as productID, projIndexEntry.findexValue as dynSellArea \n");
    	builder.appendSql("from T_FDC_ProjectIndexData projIndex \n");
    	builder.appendSql("left join T_FDC_ProjectIndexDataEntry projIndexEntry on projIndexEntry.fparentid = projIndex.fid \n");
    	builder.appendSql("left join t_fdc_apportionType apporType on projIndexEntry.fApportionTypeID = apporType.fid \n");
    	builder.appendSql("left join t_fdc_productType productType on productType.fid = projIndex.fProductTypeID \n where ");
    	builder.appendParam("projIndex.fprojororgid ", projectIDs.toArray());
    	builder.appendSql("\n and projIndex.fisLatestVer='1' \n");
    	builder.appendSql("and apporType.fname_l2='可售面积' \n");  //奇怪，有时候是"经营区面积"，有时候是“可售面积”
    	builder.appendSql("and projIndex.fprojectstage = 'DYNCOST' \n");
    	builder.appendSql("and productType.fname_l2 is not null ");
    	
    	rs = builder.executeQuery();
    	Map dynSellAreaMap = new HashMap();
    	try {
			while(rs.next()){
				dynSellAreaMap.put(rs.getString("productID"), rs.getBigDecimal("dynSellArea"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		dataMap.put("dynSellAreaMap", dynSellAreaMap) ;
		
		builder.clear();
		builder.appendSql("select costAccount.flongnumber as longNumber,entry.FProductID as productID, ");
    	builder.appendSql("isNull(sum(entry.FHapCost),0)+isNull(sum(entry.FNeedAmt),0)+isNull(sum(entry.FAdjAmt),0) as dynamicCost ");
    	builder.appendSql("from T_AIM_ConOccSplitListEntry entry ");
    	builder.appendSql("left join T_AIM_ConOccSplitList parent on entry.fparentid = parent.fid ");
    	builder.appendSql("inner join T_FDC_CostAccount costAccount on entry.FCostAccountID = costAccount.fid where ");
    	builder.appendParam("parent.fprojectid", projectIDs.toArray());
    	builder.appendSql(" and costAccount.flongnumber is not null ");
    	builder.appendSql("group by costAccount.flongnumber,entry.FProductID ");
    	builder.appendSql("order by costAccount.flongnumber,entry.FProductID ");
    	rs = builder.executeQuery();
    	Map dynamicMap = new HashMap();
    	try {
			while(rs.next()){
				Map productMap = new HashMap();
				String longNumber = rs.getString("longNumber");
				longNumber = longNumber.replace('!', '.');
				String productID = rs.getString("productID");
				if(dynamicMap.containsKey(longNumber)){
					productMap = (Map) dynamicMap.get(longNumber);
					productMap.put(productID, rs.getBigDecimal("dynamicCost"));
				}else{
					productMap.put(productID, rs.getBigDecimal("dynamicCost"));
				}
				dynamicMap.put(longNumber, productMap);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		dataMap.put("dynamicMap", dynamicMap);
		
		builder.clear();
		builder.appendSql("select costAccount.flongnumber as longNumber,sale.fproductid as productID,sum(sale.famount) as aimCost ");
    	builder.appendSql("from T_AIM_SaleAreaData sale ");
    	builder.appendSql("left join T_AIM_MeasureCost parent on sale.FParentID = parent.fid ");
    	builder.appendSql("inner join T_FDC_CostAccount costAccount on sale.fcostaccountid = costAccount.fid where ");
    	builder.appendParam("parent.fprojectid", projectIDs.toArray());
    	builder.appendSql(" and parent.fislastversion = '1' ");
    	builder.appendSql("group by costAccount.flongnumber,sale.fproductid ");
    	builder.appendSql("order by costAccount.flongnumber,sale.fproductid ");
    	rs = builder.executeQuery();
    	Map aimCostMap = new HashMap();
    	try {
			while(rs.next()){
				Map productMap = new HashMap();
				String longNumber = rs.getString("longNumber");
				longNumber = longNumber.replace('!', '.');
				String productID = rs.getString("productID");
				if(aimCostMap.containsKey(longNumber)){
					productMap = (Map) aimCostMap.get(longNumber);
					productMap.put(productID, rs.getBigDecimal("aimCost"));
				}else{
					productMap.put(productID, rs.getBigDecimal("aimCost"));
				}
				aimCostMap.put(longNumber, productMap);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		dataMap.put("aimCostMap", aimCostMap);
		
		return dataMap;
    }
    
    protected ProductTypeCollection _getProductTypes(Context ctx, Set projectIDs) throws BOSException {
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	builder.appendSql("select prod.fid as id,prod.fname_l2 as name ,prod.fnumber as number ");
    	builder.appendSql("from T_FDC_ProductType prod ");
    	builder.appendSql("left join T_AIM_PlanIndexEntry planIndexEntry on planIndexEntry.fproductid = prod.fid ");
    	builder.appendSql("left join T_AIM_PlanIndex planIndex on planIndexEntry.fparentid = planIndex.fid ");
    	builder.appendSql("left join T_AIM_MeasureCost measureCost on planIndex.fheadid = measureCost.fid where ");
    	builder.appendParam("measureCost.fprojectid ", projectIDs.toArray());
    	builder.appendSql("and measureCost.fislastversion = '1' ");
    	builder.appendSql("and planIndexEntry.fissplit = '1' ");
    	
    	IRowSet rs = builder.executeQuery();
    	ProductTypeCollection coll = new ProductTypeCollection();
        try {
            ProductTypeInfo info;
            while(rs.next()){
            	info = new ProductTypeInfo();
                info.setId(BOSUuid.read(rs.getString("id")));
                info.setNumber(rs.getString("number"));
                info.setName(rs.getString("name"));
                coll.add(info);
            }
        } catch(SQLException e) {
            throw new BOSException(e);
        }
        return coll;
    }
    
    protected Map _getSplitAmount(Context ctx, String projectID) throws BOSException {
    	Map splitMap = new HashMap();
    	StringBuffer sb = new StringBuffer();
    	sb.append("select toSplitEntry.FProductID as productID, sum(toSplitEntry.FSplitNeedAmt) + sum(toSplitEntry.FSplitDiffAmt) as amount \n");
    	sb.append("from T_AIM_PublicToSplit toSplit \n");
    	sb.append("left join T_AIM_PublicToSplitEntry toSplitEntry on toSplitEntry.FParentID = toSplit.FID \n");
    	sb.append("where toSplit.FCurProjectID = '"+projectID+"' \n");
    	sb.append("group by toSplitEntry.FProductID \n");
    	IRowSet rs = DbUtil.executeQuery(ctx, sb.toString());
    	try {
			while(rs.next()){
				splitMap.put(rs.getString("productID"), rs.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
    	
    	sb = new StringBuffer();
    	sb.append("select setSplitEntry.FProductID as productID, sum(setSplitEntry.FAmt) as amount \n");
    	sb.append("from T_AIM_PublicSetSplit setSplit \n");
    	sb.append("left join T_AIM_PublicSetSplitEntry setSplitEntry on setSplitEntry.FParentID = setSplit.FID \n");
    	sb.append("where setSplit.FCurProjectID = '"+projectID+"' \n");
    	sb.append("group by setSplitEntry.FProductID \n");
    	rs = DbUtil.executeQuery(ctx, sb.toString());
    	try {
			while(rs.next()){
				String productID = rs.getString("productID");
				if(splitMap.containsKey(productID)){
					BigDecimal amount = FDCHelper.add(splitMap.get(productID), rs.getBigDecimal("amount"));
					splitMap.put(productID, amount);
				}else{
					splitMap.put(productID, rs.getBigDecimal("amount"));
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
    	return splitMap;
    }
}