package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractExecFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProjectCostRptFacadeControllerBean extends AbstractProjectCostRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.ProjectCostRptFacadeControllerBean");
    protected RetValue _getData(Context ctx, ParamValue param)throws BOSException, EASBizException
    {
    	if(param==null){
    		throw new NullPointerException("bad param!");
    	}
    	String lang = ctx.getLocale().getLanguage();
    	String prjId=param.getString("prjId");
    	if(prjId==null){
    		throw new NullPointerException("bad prjId!");
    	}
    	RetValue retValue=new RetValue();
    	//ȡ��Ŀ
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.appendSql("select prj1.fid as id ,prj1.fname_"+lang+" as name,prj1.fnumber as number from T_FDC_CurProject prj1 ");
    	builder.appendSql(" inner join T_FDC_CurProject prj2 on prj1.ffullorgUnit=prj2.ffullorgUnit ");
    	builder.appendSql(" where (charindex(prj2.flongnumber||'!',prj1.flongnumber)=1 or prj1.flongnumber=prj2.flongnumber) and prj2.fid=? and prj1.fisleaf=1 ");
    	builder.addParam(prjId);
    	IRowSet rowSet=builder.executeQuery();
    	String[] prjIds=new String[rowSet.size()];
    	try {
    		int i=0;
    		CurProjectCollection c=new CurProjectCollection();
			while (rowSet.next()) {
				CurProjectInfo info=new CurProjectInfo();
				String id = rowSet.getString("id");
				info.setId(BOSUuid.read(id));
				info.setName(rowSet.getString("name"));
				c.add(info);
				prjIds[i++]=id;
			}
			retValue.put("CurProjectCollection", c);
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
		
		//�Ƿ���ڳɱ��仯������仯�������µ�����
		Set prjSet=new HashSet();
		prjSet.addAll(Arrays.asList(prjIds));
		//ȡ�ɱ��仯�˵Ľ��б���
		prjSet=ProjectCostChangeLogFactory.getLocalInstance(ctx).getChangePrjs(prjSet);
		saveShot(ctx, prjSet);
		
    	//ȡ��Ʒ
    	builder.clear();

    	builder.appendSql("select distinct prod.fid as id,prod.fname_"+lang+" as name ,prod.fnumber as number  from T_FDC_CurProjProductEntries prjProd inner join T_FDC_ProductType prod on prjProd.FProductType=prod.fid where ");
    	builder.appendParam("prjProd.FCurProject", prjIds);
    	builder.appendSql("order by prod.fnumber");
    	rowSet=builder.executeQuery();
    	try{
    		ProductTypeCollection c=new ProductTypeCollection();
    		while(rowSet.next()){
    			ProductTypeInfo info=new ProductTypeInfo();
    			info.setId(BOSUuid.read(rowSet.getString("id")));
    			info.setNumber(rowSet.getString("number"));
    			info.setName(rowSet.getString("name"));
    			c.add(info);
    		}
    		retValue.put("ProductTypeCollection", c);
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	
    	//ȡ��Ŀ
    	EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("level");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("name");
    	view.getSelector().add("isLeaf");
    	view.setFilter(new FilterInfo());
    	view.getFilter().appendFilterItem("curProject.id", prjId);
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	CostAccountCollection c=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
    	retValue.put("CostAccountCollection", c);
    	//�����Ŀ����
    	int maxLevel=0;
    	Set numberSet=new HashSet();
    	for(int i=0;i<c.size();i++){
    		CostAccountInfo info = c.get(i);
			if(info.getLevel()>maxLevel){
    			maxLevel=info.getLevel();
    		}
			numberSet.add(info.getLongNumber());
    	}
    	retValue.setInt("maxLevel", maxLevel);
    	//ȡ����
    	//��Ŀ����
    	builder.clear();
    	builder.appendSql("select fcostAcctLgNumber as acctNumber, sum(FAimCostAmt) as aimcost,sum(fdynCostAmt) as dynCost,sum(fsoFarHasAmt) as happenCost from T_AIM_DynCostSnapShot where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("fprojectid", prjIds);
    	builder.appendSql(" group by fcostAcctLgNumber");
    	rowSet=builder.executeQuery();
    	try{
    		while(rowSet.next()){
    			String acctNumber =rowSet.getString("acctNumber");
    			RetValue subRet=getRowRetValue(acctNumber, numberSet, retValue);
    			subRet.setBigDecimal("aimCost", FDCNumberHelper.add(subRet.getBigDecimal("aimCost"), rowSet.getBigDecimal("aimCost")));
    			subRet.setBigDecimal("dynCost", FDCNumberHelper.add(subRet.getBigDecimal("dynCost"), rowSet.getBigDecimal("dynCost")));
    			subRet.setBigDecimal("happenCost", FDCNumberHelper.add(subRet.getBigDecimal("happenCost"), rowSet.getBigDecimal("happenCost")));
    		}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	//��Ʒ����
    	builder.clear();
    	builder.appendSql("select fprojectid,fproducttypeid,fcostAcctLgNumber as acctNumber,entry.FAimCostAmt as aimCost,entry.FHasHappenAmt as happenCost,entry.FDynCostAmt as dynCost from T_AIM_DynCostSnpShtProTypEntry entry inner join T_AIM_DynCostSnapShot shot on entry.fparentid=shot.fid where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("fprojectid", prjIds);
    	rowSet=builder.executeQuery();
    	try{
			while(rowSet.next()){
				String acctNumber =rowSet.getString("acctNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, retValue);
				String key=rowSet.getString("fprojectid")+rowSet.getString("fproducttypeid");
				subRet.setBigDecimal(key+"aimCost", FDCNumberHelper.add(subRet.getBigDecimal(key+"aimCost"), rowSet.getBigDecimal("aimCost")));
				subRet.setBigDecimal(key+"dynCost", FDCNumberHelper.add(subRet.getBigDecimal(key+"dynCost"), rowSet.getBigDecimal("dynCost")));
				subRet.setBigDecimal(key+"happenCost", FDCNumberHelper.add(subRet.getBigDecimal(key+"happenCost"), rowSet.getBigDecimal("happenCost")));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
        return retValue;
    }
    
    private RetValue getRowRetValue(String acctNumber,Set acctNumberSet,RetValue retValue){
    	String number=getAcctNumber(acctNumber, acctNumberSet);
    	if(number==null){
    		throw new NullPointerException("bad number!");
    	}
    	RetValue subRet = (RetValue)retValue.get(number);
    	if(subRet==null){
    		subRet=new RetValue();
    		retValue.put(number, subRet);
    	}
		return subRet;
    }
  
    private String getAcctNumber(String acctNumber,Set acctNumberSet){
    	if(acctNumber!=null){
    		acctNumber=acctNumber.replace('.', '!');
    	}
    	//R101216-187  ��֮ǰ�Ĵ���acctNumber��acctNumberSet������ʱ������ѭ��  by zhiyuan_tang
    	while (acctNumber != null) {
    		if(acctNumberSet.contains(acctNumber)){
    			return acctNumber;
    		} else {
    			acctNumber = getParentAcctNumber(acctNumber);
    		}
    	}
    	return null;
    }
    
    /**
     * ��ȡ�ÿ�Ŀ���ϼ���Ŀ��û��ʱ����NULL
     * @param acctNumber
     * @return �ϼ���Ŀ��Number
     */
    private String getParentAcctNumber(String acctNumber) {
    	if (acctNumber.lastIndexOf('!') > 0) {
    		return acctNumber.substring(0, acctNumber.lastIndexOf('!'));
    	} else {
    		return null;
    	}
    }

	protected void _updateData(Context ctx, String prjId) throws BOSException,
			EASBizException {
		if(prjId==null){
			throw new NullPointerException("prjId is null");
		}
    	//ȡ��Ŀ
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.appendSql("select prj1.fid as id from T_FDC_CurProject prj1 ");
    	builder.appendSql(" inner join T_FDC_CurProject prj2 on prj1.ffullorgUnit=prj2.ffullorgUnit ");
    	builder.appendSql(" where (charindex(prj2.flongnumber||'!',prj1.flongnumber)=1 or prj1.flongnumber=prj2.flongnumber) and prj2.fid=? and prj1.fisleaf=1 ");
    	builder.addParam(prjId);
    	IRowSet rowSet=builder.executeQuery();
    	try {
    		Set prjSet=new HashSet();
			while (rowSet.next()) {
				String id=rowSet.getString("id");
				prjSet.add(id);
			}
			//������ȫͬ�������ݳ�ʼ�����������ݾ�����
//			prjSet=ProjectCostChangeLogFactory.getLocalInstance(ctx).getChangePrjs(prjSet);
			saveShot(ctx, prjSet);
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
	}

	protected void _updateAllData(Context ctx) throws BOSException {
		//��һ�������������е���Ŀ
		Set prjSet = ProjectCostChangeLogFactory.getLocalInstance(ctx).getAllChangePrjs();
		try{
			saveShot(ctx, prjSet);
		}finally{
			ProjectCostChangeLogFactory.getLocalInstance(ctx).deleteLog();
		}
	}
	
	private void saveShot(Context ctx,Set prjSet) throws BOSException {
		if(prjSet==null||prjSet.size()==0){
			return;
		}
		try{
			for (Iterator iter = prjSet.iterator(); iter.hasNext();) {
				String prjId = (String) iter.next();
				try{
					FDCCostRptFacadeFactory.getLocalInstance(ctx).saveSnapShot(prjId, CostMonthlySaveTypeEnum.ONLYONE);
				}catch(Exception e){
					logger.error(e.getMessage(), e);
				}
			}
		}finally{
			//���±仯��־
			ProjectCostChangeLogFactory.getLocalInstance(ctx).updateLog(prjSet);
		}

	}

	/****
	 * ��Ŀ��ͬ��ϸ��ȡ��
	 */
	protected RetValue _getCollectionContractAcctCost(Context ctx,
			ParamValue paramValue) throws BOSException, EASBizException {
		if(paramValue==null){
    		throw new NullPointerException("bad param!");
    	}
		String lang = ctx.getLocale().getLanguage();
		String selectObjID = (String)paramValue.get("selectObjID");		
		Set leafPrjIDs     = (HashSet)paramValue.get("leafPrjIDs");
		if(selectObjID==null||leafPrjIDs==null||leafPrjIDs.size()==0||paramValue.get("selectObjIsPrj")==null){
			throw new NullPointerException("bad param!");
		}
		
		this._updateProjectsCost(ctx, leafPrjIDs);
		boolean selectObjIsPrj = ((Boolean)paramValue.get("selectObjIsPrj")).booleanValue();
		RetValue retValue = new RetValue();
		
		/***
		 * 1.ȡ��Ŀ
		 */
		EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("level");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("name");
    	view.getSelector().add("isLeaf");
    	view.setFilter(new FilterInfo());
    	if(selectObjIsPrj)
    		view.getFilter().appendFilterItem("curProject.id", selectObjID);
    	else
    		view.getFilter().appendFilterItem("fullOrgUnit.id", selectObjID);
    	view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	CostAccountCollection c=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
    	retValue.put("CostAccountCollection", c);
    	
    	//�����Ŀ����
    	//ȡ��Ŀ�ĺ�ͬ��ϸ
    	int maxLevel=0;
    	Set numberSet=new HashSet();
    	Map costAcctContractDetailMap = new HashMap();
    	for(int i=0;i<c.size();i++){
    		CostAccountInfo info = c.get(i);
    		paramValue.put("acctLongNumber", info.getLongNumber());
    		costAcctContractDetailMap.put(info.getLongNumber(), this._getCollectionContractAcctCostDetails(ctx, paramValue));
			if(info.getLevel()>maxLevel){
    			maxLevel=info.getLevel();
    		}
			numberSet.add(info.getLongNumber());
    	}
    	retValue.put("costAcctContractDetailMap", costAcctContractDetailMap);
    	retValue.setInt("maxLevel", maxLevel);
    	
    	/****
    	 * 2.ȡ��Ŀ�ĸ��ɱ���Ϣ
    	 */
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.clear();
    	builder.appendSql("select fcostAcctLgNumber as acctNumber,  ");
    	builder.appendSql(" sum(FUnSettSignAmt) as unSettSignAmt,");
    	builder.appendSql(" sum(FSettSignAmt) as settSignAmt,");
    	builder.appendSql(" sum(FSettAdjustAmt) as settAdjustAmt,");
    	builder.appendSql(" sum(FUnContractCostAmt) as unContractCostAmt,");
    	builder.appendSql(" sum(FSoFarHasAmt) as soFarHasAmt,");
    	builder.appendSql(" sum(FCostPayedAmt) as costPayedAmt,");
    	builder.appendSql(" sum(FPayedAmt) as payedAmt,");
    	builder.appendSql(" sum(FSoFarToAmt) as soFarToAmt,");
    	builder.appendSql(" sum(FDynCostAmt) as dynCost,");
    	builder.appendSql(" sum(FAimCostAmt) as aimCost,");
    	builder.appendSql(" sum(FDiffAmt) as diffAmt ");
    	builder.appendSql(" from T_AIM_DynCostSnapShot shot where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("fprojectid", leafPrjIDs.toArray());
    	builder.appendSql(" group by fcostAcctLgNumber ");
    	IRowSet rowSet=builder.executeQuery();
    	RetValue costValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("acctNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, costValues);
				//Ŀǰ�ѷ���
				subRet.setBigDecimal("soFarHasAmt", FDCNumberHelper.add(subRet.getBigDecimal("soFarHasAmt"), rowSet.getBigDecimal("soFarHasAmt")));
				//Ŀǰ������
				subRet.setBigDecimal("soFarToAmt", FDCNumberHelper.add(subRet.getBigDecimal("soFarToAmt"), rowSet.getBigDecimal("soFarToAmt")));
				//��̬�ɱ�
    			subRet.setBigDecimal("dynCost", FDCNumberHelper.add(subRet.getBigDecimal("dynCost"), rowSet.getBigDecimal("dynCost")));
    			//Ŀ��ɱ�
    			subRet.setBigDecimal("aimCost", FDCNumberHelper.add(subRet.getBigDecimal("aimCost"), rowSet.getBigDecimal("aimCost")));
    			//����
    			subRet.setBigDecimal("diffAmt", FDCNumberHelper.add(subRet.getBigDecimal("diffAmt"), rowSet.getBigDecimal("diffAmt")));
    			
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("costValues", costValues);
    	
    	RetValue splitValues=new RetValue(); 
    	
    	/***
    	 * 3.ȡ��ͬ��֣���ֵ��˿�Ŀ�Ľ��--��ͬ��ֽ��
    	 */
    	builder.clear();
		builder.appendSql("select acct.FLongNumber,sum(entry.FAmount) as amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql(" inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FId  \n");
		builder.appendSql(" inner join T_CON_ContractBill contract on head.FCostBillID=contract.FID  \n");
		builder.appendSql(" where FCostBillType='CONTRACTSPLIT' and entry.FIsProduct=0 and head.FIsInvalid=0 And  ");
		builder.appendParam("acct.fcurProject", leafPrjIDs.toArray());
		builder.appendSql(" group by FLongNumber\n");
		builder.appendSql(" ");
		/***
		 * �������ı���ͬ�ĸ����ֽ��
		 */
		builder.appendSql(" union ");
		
		builder.appendSql("select acct.FLongNumber,sum(entry.FAmount) as amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql(" inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FId  \n");
		builder.appendSql("inner join T_CAS_Paymentbill payment on head.fcostbillid=payment.fid ");
		builder.appendSql("inner join t_con_contractwithouttext contract on payment.fcontractbillid=contract.fid ");
		builder.appendSql(" where FCostBillType='PAYMENTSPLIT' and entry.FIsProduct=0 and head.FIsInvalid=0 And  ");
		builder.appendParam("acct.fcurProject", leafPrjIDs.toArray());
		builder.appendSql(" group by FLongNumber\n");
		builder.appendSql(" ");
		
		rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("FLongNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, splitValues);
				subRet.setBigDecimal("contractSplitAmt", FDCNumberHelper.add(subRet.getBigDecimal("contractSplitAmt"), rowSet.getBigDecimal("amount")));
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	
    	/***
    	 * 4.ȡ�����֣���ֵ��˿�Ŀ�Ľ��--�����ֽ��
    	 */
		builder.clear();
		builder.appendSql("select acct.FLongNumber,sum(entry.FAmount) as amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql(" inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FId  \n");
		builder.appendSql(" inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FID  \n");
		builder.appendSql(" where FCostBillType='CNTRCHANGESPLIT' and entry.FIsProduct=0 and head.FIsInvalid=0 And  \n");
		builder.appendParam("acct.fcurProject", leafPrjIDs.toArray());
		builder.appendSql(" group by FLongNumber\n");
		
		rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("FLongNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, splitValues);
				subRet.setBigDecimal("conChangeSplitAmt", FDCNumberHelper.add(subRet.getBigDecimal("conChangeSplitAmt"), rowSet.getBigDecimal("amount")));
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	
    	/***
    	 * 5.ȡ�����֣���ֵ��˿�Ŀ�Ľ��--�����ֽ��
    	 */
    	
		builder.clear();
		builder.appendSql("select acct.FLongNumber,sum(entry.FAmount) as amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql(" inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FId  \n");
		builder.appendSql(" inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID  \n");
		builder.appendSql(" where FCostBillType='SETTLEMENTSPLIT' and entry.FIsProduct=0 and head.FIsInvalid=0 And  \n");
		builder.appendSql(" ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) or (settle.FIsSettled=0 and settle.FIsFinalSettle=0)) and \n");
		builder.appendParam("acct.fcurProject", leafPrjIDs.toArray());
		builder.appendSql(" group by FLongNumber\n");
		
		rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("FLongNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, splitValues);
				subRet.setBigDecimal("settlementSplitAmt", FDCNumberHelper.add(subRet.getBigDecimal("settlementSplitAmt"), rowSet.getBigDecimal("amount")));
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
		
		/***
    	 * ��ʵ�ֳɱ�:�����ֵĹ����ɱ����򹤳������
    	 */
		
		builder.clear();
		builder.appendSql("select FLongNumber,sum(amount) as amount from ( \n");
		builder.appendSql("select acct.FLongNumber as FLongNumber,sum(entry.famount) as amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql(" inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FId  \n");
		builder.appendSql(" inner join T_FNC_PaymentSplit split on head.FSplitBillID=split.FID and split.FIsWorkLoadBill=0 \n");
		builder.appendSql(" where FCostBillType='PAYMENTSPLIT' and entry.FIsProduct=0 and head.FIsInvalid=0 And  \n");
		builder.appendParam("acct.fcurProject", leafPrjIDs.toArray());
		builder.appendSql(" group by FLongNumber\n");
		
		builder.appendSql(" union all \n");
		
		builder.appendSql("select acct.FLongNumber,sum(entry.famount) as amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql(" inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FId  \n");
		builder.appendSql(" inner join T_FNC_PaymentSplit split on head.FSplitBillID=split.FID and split.FIsWorkLoadBill=1 \n");
		builder.appendSql(" where FCostBillType='PAYMENTSPLIT' and entry.FIsProduct=0 and head.FIsInvalid=0 And  \n");
		builder.appendParam("acct.fcurProject", leafPrjIDs.toArray());
		builder.appendSql(" group by FLongNumber\n");
		builder.appendSql(")t group by FLongNumber "); 
		rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("FLongNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, splitValues);
				subRet.setBigDecimal("hasPayCostAmt", FDCNumberHelper.add(subRet.getBigDecimal("hasPayCostAmt"), rowSet.getBigDecimal("amount")));
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
		
    	/***
    	 * 6.ȡ�����֣���ֵ��˿�Ŀ�Ľ��--�����ֽ��
    	 */
		
		builder.clear();
		builder.appendSql("select acct.FLongNumber,sum(entry.fpaidamount) as amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql(" inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FId  \n");
		builder.appendSql(" inner join T_CAS_Paymentbill payment on head.FCostBillID=payment.FID  \n");
		builder.appendSql(" where FCostBillType='PAYMENTSPLIT' and entry.FIsProduct=0 and head.FIsInvalid=0 And  \n");
		builder.appendParam("acct.fcurProject", leafPrjIDs.toArray());
		builder.appendSql(" group by FLongNumber\n");
		
		rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("FLongNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, splitValues);
				subRet.setBigDecimal("paymentSplitAmt", FDCNumberHelper.add(subRet.getBigDecimal("paymentSplitAmt"), rowSet.getBigDecimal("amount")));
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	
		retValue.put("splitValues", splitValues);
		
		/***
    	 * 7.��̬���ָ��
    	 */
    	RetValue areaValue = new RetValue();
    	builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select ind_e.fapportiontypeid,sum(ind_e.findexvalue) as findexvalue from t_fdc_projectindexdata ind inner join t_fdc_projectindexdataentry ind_e on ind.fid=ind_e.fparentid");
		builder.appendSql(" where ind.fislatestver=1 and ");
		builder.appendSql(" ind.fproducttypeid is null and ");
		builder.appendParam("ind.fprojectstage",ProjectStageEnum.DYNCOST_VALUE);
		builder.appendSql(" and ");
		builder.appendParam("ind.fprojororgid",leafPrjIDs.toArray());
		builder.appendSql("  group by ind_e.fapportiontypeid ");
		
		rowSet=builder.executeQuery();
    	try{
    		while(rowSet.next()){
    			String key = rowSet.getString("fapportiontypeid");
    			areaValue.put(key, rowSet.getBigDecimal("findexvalue"));
    		}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("areaValue", areaValue);
    	
    	
    	// start���깤
		builder.clear();
		builder.appendSql("select fid,fcurprojectid from t_con_contractbill where ");
		builder.appendParam("fcurprojectid", leafPrjIDs.toArray());
		rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				String prjId = rowSet.getString("fcurprojectid");
				String conId = rowSet.getString("fid");
				HashSet lnUps = new HashSet();
				if (retValue.containsKey(prjId)) {
					lnUps = (HashSet) retValue.get(prjId);
					lnUps.add(conId);
				} else {
					lnUps.add(conId);
					retValue.put(prjId, lnUps);
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		builder.clear();
		builder.appendSql("select distinct(ffullorgunit),fid from t_fdc_curproject where ");
		builder.appendParam("fid", leafPrjIDs.toArray());
		rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				String key = rowSet.getString("fid");
				String org = rowSet.getString("ffullorgunit");
				HashSet lnUps = (HashSet) retValue.get(key);
				Map completeMap = ContractExecFacadeFactory.getLocalInstance(ctx)._getCompleteAmt(org, lnUps);// ��ʼ���ۼ��깤������
				retValue.put(key, completeMap);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
    	// end���깤
		
		return retValue;
	}

	/******
	 * ȫ��Ŀ��̬�ɱ���ȡ��
	 * ����: 
	 * 		selectObjID ��ǰ��ѡ�ڵ㣬�����ǹ�����Ŀ��Ҳ��������֯ID
	 * 		selectObjIsPrj ��ǰ��ѡ�ڵ��Ƿ񹤳���Ŀ
	 * 		leafPrjIDs ͨ���ݹ�������ȡ��ѡ�ڵ��µ�������ϸ������Ŀ��
	 */
	protected RetValue _getCollectionFullDynCost(Context ctx,
			ParamValue paramValue) throws BOSException, EASBizException {
		
		if(paramValue==null){
    		throw new NullPointerException("bad param!");
    	}
		String lang = ctx.getLocale().getLanguage();
		String selectObjID = (String)paramValue.get("selectObjID");		
		Set leafPrjIDs     = (HashSet)paramValue.get("leafPrjIDs");
		if(selectObjID==null||leafPrjIDs==null||leafPrjIDs.size()==0||paramValue.get("selectObjIsPrj")==null){
			throw new NullPointerException("bad param!");
		}
		
		this._updateProjectsCost(ctx, leafPrjIDs);
		
		boolean selectObjIsPrj = ((Boolean)paramValue.get("selectObjIsPrj")).booleanValue();
		RetValue retValue = new RetValue();
		/***
		 * 1.ȡ��Ŀ
		 */
		EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("level");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("name");
    	view.getSelector().add("isLeaf");
    	view.setFilter(new FilterInfo());
    	if(selectObjIsPrj)
    		view.getFilter().appendFilterItem("curProject.id", selectObjID);
    	else
    		view.getFilter().appendFilterItem("fullOrgUnit.id", selectObjID);
    	view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	CostAccountCollection c=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
    	retValue.put("CostAccountCollection", c);
    	//�����Ŀ����
    	int maxLevel=0;
    	Set numberSet=new HashSet();
    	for(int i=0;i<c.size();i++){
    		CostAccountInfo info = c.get(i);
			if(info.getLevel()>maxLevel){
    			maxLevel=info.getLevel();
    		}
			numberSet.add(info.getLongNumber());
    	}
    	retValue.setInt("maxLevel", maxLevel);
    	/****
    	 * 2.ȡ��Ŀ�ĸ��ɱ���Ϣ
    	 */
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.clear();
    	builder.appendSql("select fcostAcctLgNumber as acctNumber,  ");
    	builder.appendSql(" sum(FUnSettSignAmt) as unSettSignAmt,");
    	builder.appendSql(" sum(FSettSignAmt) as settSignAmt,");
    	builder.appendSql(" sum(FSettAdjustAmt) as settAdjustAmt,");
    	builder.appendSql(" sum(FUnContractCostAmt) as unContractCostAmt,");
    	builder.appendSql(" sum(FSoFarHasAmt) as soFarHasAmt,");
    	builder.appendSql(" sum(FCostPayedAmt) as costPayedAmt,");
    	builder.appendSql(" sum(FPayedAmt) as payedAmt,");
    	builder.appendSql(" sum(FSoFarToAmt) as soFarToAmt,");
    	builder.appendSql(" sum(FDynCostAmt) as dynCost,");
    	builder.appendSql(" sum(FAimCostAmt) as aimCost,");
    	builder.appendSql(" sum(FDiffAmt) as diffAmt ");
    	builder.appendSql(" from T_AIM_DynCostSnapShot shot where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("fprojectid", leafPrjIDs.toArray());
    	builder.appendSql(" group by fcostAcctLgNumber ");
    	IRowSet rowSet=builder.executeQuery();
    	RetValue costValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("acctNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, costValues);
				//δ�����ͬǩԼ���
				subRet.setBigDecimal("unSettSignAmt", FDCNumberHelper.add(subRet.getBigDecimal("unSettSignAmt"), rowSet.getBigDecimal("unSettSignAmt")));
				//�ѽ����ͬǩԼ���
				subRet.setBigDecimal("settSignAmt", FDCNumberHelper.add(subRet.getBigDecimal("settSignAmt"), rowSet.getBigDecimal("settSignAmt")));
				//�������
				subRet.setBigDecimal("settAdjustAmt", FDCNumberHelper.add(subRet.getBigDecimal("settAdjustAmt"), rowSet.getBigDecimal("settAdjustAmt")));
				//�Ǻ�ͬ�Գɱ�
				subRet.setBigDecimal("unContractCostAmt", FDCNumberHelper.add(subRet.getBigDecimal("unContractCostAmt"), rowSet.getBigDecimal("unContractCostAmt")));
				//Ŀǰ�ѷ���
				subRet.setBigDecimal("soFarHasAmt", FDCNumberHelper.add(subRet.getBigDecimal("soFarHasAmt"), rowSet.getBigDecimal("soFarHasAmt")));
				
				//��ʵ��
				subRet.setBigDecimal("costPayedAmt", FDCNumberHelper.add(subRet.getBigDecimal("costPayedAmt"), rowSet.getBigDecimal("costPayedAmt")));
				//�Ѹ���
				subRet.setBigDecimal("payedAmt", FDCNumberHelper.add(subRet.getBigDecimal("payedAmt"), rowSet.getBigDecimal("payedAmt")));
				//Ŀǰ������
				subRet.setBigDecimal("soFarToAmt", FDCNumberHelper.add(subRet.getBigDecimal("soFarToAmt"), rowSet.getBigDecimal("soFarToAmt")));
				
				//��̬�ɱ�
    			subRet.setBigDecimal("dynCost", FDCNumberHelper.add(subRet.getBigDecimal("dynCost"), rowSet.getBigDecimal("dynCost")));
    			//Ŀ��ɱ�
    			subRet.setBigDecimal("aimCost", FDCNumberHelper.add(subRet.getBigDecimal("aimCost"), rowSet.getBigDecimal("aimCost")));
    			//����
				subRet.setBigDecimal("diffAmt", FDCNumberHelper.add(subRet.getBigDecimal("diffAmt"), rowSet.getBigDecimal("diffAmt")));
				
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("costValues", costValues);
    	
    	/***
    	 * 3.��̬���ָ��
    	 */
    	RetValue areaValue = new RetValue();
    	builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select ind_e.fapportiontypeid,sum(ind_e.findexvalue) as findexvalue from t_fdc_projectindexdata ind inner join t_fdc_projectindexdataentry ind_e on ind.fid=ind_e.fparentid");
		builder.appendSql(" where ind.fislatestver=1 and ");
		builder.appendSql(" ind.fproducttypeid is null and ");
		builder.appendParam("ind.fprojectstage",ProjectStageEnum.DYNCOST_VALUE);
		builder.appendSql(" and ");
		builder.appendParam("ind.fprojororgid",leafPrjIDs.toArray());
		builder.appendSql("  group by ind_e.fapportiontypeid ");
		
		rowSet=builder.executeQuery();
    	try{
    		while(rowSet.next()){
    			String key = rowSet.getString("fapportiontypeid");
    			areaValue.put(key, rowSet.getBigDecimal("findexvalue"));
    		}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("areaValue", areaValue);
    	
    	/***
    	 * 4.ȡ�������
    	 */
    	ChangeTypeCollection changeTypeCollection = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection("select * where isEnabled=1");
    	retValue.put("ChangeTypeCollection", changeTypeCollection);
    	
    	/***
    	 * 5.ȡ�����¼
    	 */
    	
    	builder.clear();
    	builder.appendSql("select fcostAcctLgNumber as acctNumber,entry.FChangeTypeId as changeTypeID , ");
    	builder.appendSql(" sum(FUnSettleAmt) as unSettleAmt,");
    	builder.appendSql(" sum(FSettleAmt) as settleAmt");
    	builder.appendSql(" from T_AIM_DynCostSnapShot shot  inner join T_AIM_DynCostSnapShotSettEntry entry on shot.FID=entry.FParentID where shot.fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("shot.fprojectid", leafPrjIDs.toArray());
    	builder.appendSql(" group by fcostAcctLgNumber,entry.FChangeTypeId ");
    	rowSet=builder.executeQuery();
    	RetValue settEntryValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("acctNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, settEntryValues);
				String key = rowSet.getString("changeTypeID");
				//δ�����ͬǩԼ���
				subRet.setBigDecimal(key+"unSettleAmt", FDCNumberHelper.add(subRet.getBigDecimal(key+"unSettleAmt"), rowSet.getBigDecimal("unSettleAmt")));
				//�ѽ����ͬǩԼ���
				subRet.setBigDecimal(key+"settleAmt", FDCNumberHelper.add(subRet.getBigDecimal(key+"settleAmt"), rowSet.getBigDecimal("settleAmt")));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("settEntryValues", settEntryValues);
		
		return retValue;
	}

	/**
	 * ����Ʒ����Ŀ��ɱ���ȡ��
	 */
	protected RetValue _getCollectionProductAimCost(Context ctx,
			ParamValue paramValue) throws BOSException, EASBizException {
		if(paramValue==null){
    		throw new NullPointerException("bad param!");
    	}
		String lang = ctx.getLocale().getLanguage();
		String selectObjID = (String)paramValue.get("selectObjID");		
		Set leafPrjIDs     = (HashSet)paramValue.get("leafPrjIDs");
		if(selectObjID==null||leafPrjIDs==null||leafPrjIDs.size()==0||paramValue.get("selectObjIsPrj")==null){
			throw new NullPointerException("bad param!");
		}
		this._updateProjectsCost(ctx, leafPrjIDs);
		
		boolean selectObjIsPrj = ((Boolean)paramValue.get("selectObjIsPrj")).booleanValue();
		RetValue retValue = new RetValue(); 
		/*********
		 * 1. ��ȡ���й�����Ŀ�Ĳ�Ʒ���ͣ��޳��ظ�ֵ
		 */
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.clear();
    	builder.appendSql("select distinct prod.fid as id,prod.fname_"+lang+" as name ,prod.fnumber as number  from T_FDC_CurProjProductEntries prjProd inner join T_FDC_ProductType prod on prjProd.FProductType=prod.fid where ");
    	builder.appendParam("prjProd.FCurProject", leafPrjIDs.toArray());
    	builder.appendSql("order by prod.fnumber");
    	IRowSet rowSet=builder.executeQuery();
    	try{
    		ProductTypeCollection c=new ProductTypeCollection();
    		while(rowSet.next()){
    			ProductTypeInfo info=new ProductTypeInfo();
    			info.setId(BOSUuid.read(rowSet.getString("id")));
    			info.setNumber(rowSet.getString("number"));
    			info.setName(rowSet.getString("name"));
    			c.add(info);
    		}
    		retValue.put("ProductTypeCollection", c);
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	
    	/*****
    	 * 2.ȡ����Ʒ���͵Ŀ������
    	 * ����Ŀ��--��̬
    	 */
    	RetValue sellAreaValue = _getProductSellArea(ctx,leafPrjIDs);
    	retValue.put("ProductSellAreaValue", sellAreaValue);
    	
    	/****
    	 * 3.ȡ��Ŀ
    	 */
    	EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("level");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("name");
    	view.getSelector().add("isLeaf");
    	view.setFilter(new FilterInfo());
    	if(selectObjIsPrj)
    		view.getFilter().appendFilterItem("curProject.id", selectObjID);
    	else
    		view.getFilter().appendFilterItem("fullOrgUnit.id", selectObjID);
    	view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	CostAccountCollection c=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
    	retValue.put("CostAccountCollection", c);
    	//�����Ŀ����
    	int maxLevel=0;
    	Set numberSet=new HashSet();
    	for(int i=0;i<c.size();i++){
    		CostAccountInfo info = c.get(i);
			if(info.getLevel()>maxLevel){
    			maxLevel=info.getLevel();
    		}
			numberSet.add(info.getLongNumber());
    	}
    	retValue.setInt("maxLevel", maxLevel);
		
    	/***
    	 * 4.ȡĿ��ɱ�
    	 */
    	builder.clear();
    	Set shotIDSet = new HashSet();
    	shotIDSet.add("noData");
    	builder.appendSql("select fcostAcctLgNumber as acctNumber, fid, sum(FAimCostAmt) as aimCostAmt from T_AIM_DynCostSnapShot where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("fprojectid", leafPrjIDs.toArray());
    	builder.appendSql(" group by fcostAcctLgNumber,fid");
    	rowSet=builder.executeQuery();
    	RetValue costValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber = rowSet.getString("acctNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, costValues);
				subRet.setBigDecimal("aimCostAmt", FDCNumberHelper.add(subRet.getBigDecimal("aimCostAmt"),rowSet.getBigDecimal("aimCostAmt")));
				shotIDSet.add(rowSet.getString("fid"));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("costValues", costValues);
    	/***
    	 * 5.����Ʒ���͵�Ŀ��ɱ�
    	 */
    	builder.clear();
    	builder.appendSql("select entry.fproducttypeid fproducttypeid, shot.fcostAcctLgNumber as acctNumber, sum(entry.FAimAimCostAmt) as aimAimCostAmt, sum(entry.FAimAimSaleUnitAmt) as aimAimSaleUnitAmt from T_AIM_DynCostSnpShtProTypEntry entry inner join T_AIM_DynCostSnapShot shot on entry.fparentid=shot.fid where ");
    	builder.appendParam("entry.fparentid", shotIDSet.toArray());
    	builder.appendSql(" group by fproducttypeid, fcostAcctLgNumber");
    	rowSet=builder.executeQuery();
    	RetValue productCostValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber = rowSet.getString("acctNumber");
				String key=rowSet.getString("fproducttypeid");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, productCostValues);
				subRet.setBigDecimal(key+"aimAimCostAmt", FDCNumberHelper.add(subRet.getBigDecimal(key+"aimAimCostAmt"), rowSet.getBigDecimal("aimAimCostAmt")));
				subRet.setBigDecimal(key+"aimAimSaleUnitAmt", FDCNumberHelper.add(subRet.getBigDecimal(key+"aimAimSaleUnitAmt"), rowSet.getBigDecimal("aimAimSaleUnitAmt")));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("productCostValues", productCostValues);
		return retValue;
	}

	/**
	 * ����Ʒ���Ͷ�̬Ŀ��ɱ���
	 */
	protected RetValue _getCollectionProductDynAimCost(Context ctx,
			ParamValue paramValue) throws BOSException, EASBizException {
		if(paramValue==null){
    		throw new NullPointerException("bad param!");
    	}
		String lang = ctx.getLocale().getLanguage();
		String selectObjID = (String)paramValue.get("selectObjID");		
		Set leafPrjIDs     = (HashSet)paramValue.get("leafPrjIDs");
		if(selectObjID==null||leafPrjIDs==null||leafPrjIDs.size()==0||paramValue.get("selectObjIsPrj")==null){
			throw new NullPointerException("bad param!");
		}
		this._updateProjectsCost(ctx, leafPrjIDs);
		
		boolean selectObjIsPrj = ((Boolean)paramValue.get("selectObjIsPrj")).booleanValue();
		RetValue retValue = new RetValue(); 
		/*********
		 * 1. ��ȡ���й�����Ŀ�Ĳ�Ʒ���ͣ��޳��ظ�ֵ
		 */
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.clear();
    	builder.appendSql("select distinct prod.fid as id,prod.fname_"+lang+" as name ,prod.fnumber as number  from T_FDC_CurProjProductEntries prjProd inner join T_FDC_ProductType prod on prjProd.FProductType=prod.fid where ");
    	builder.appendParam("prjProd.FCurProject", leafPrjIDs.toArray());
    	builder.appendSql("order by prod.fnumber");
    	IRowSet rowSet=builder.executeQuery();
    	try{
    		ProductTypeCollection c=new ProductTypeCollection();
    		while(rowSet.next()){
    			ProductTypeInfo info=new ProductTypeInfo();
    			info.setId(BOSUuid.read(rowSet.getString("id")));
    			info.setNumber(rowSet.getString("number"));
    			info.setName(rowSet.getString("name"));
    			c.add(info);
    		}
    		retValue.put("ProductTypeCollection", c);
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	
//    	/*****
//    	 * 2.��̬���ָ��
//    	 */
//    	RetValue areaValue = new RetValue();
//    	builder=new FDCSQLBuilder(ctx);
//		builder.appendSql("select ind_e.fapportiontypeid,sum(ind_e.findexvalue) as findexvalue from t_fdc_projectindexdata ind inner join t_fdc_projectindexdataentry ind_e on ind.fid=ind_e.fparentid");
//		builder.appendSql(" where ind.fislatestver=1 and ");
//		builder.appendSql(" ind.fproducttypeid is null and ");
//		builder.appendParam("ind.fprojectstage",ProjectStageEnum.DYNCOST_VALUE);
//		builder.appendSql(" and ");
//		builder.appendParam("ind.fprojororgid",leafPrjIDs.toArray());
//		builder.appendSql("  group by ind_e.fapportiontypeid ");
//		
//		rowSet=builder.executeQuery();
//    	try{
//    		while(rowSet.next()){
//    			String key = rowSet.getString("fapportiontypeid");
//    			areaValue.put(key, rowSet.getBigDecimal("findexvalue"));
//    		}
//    	}catch(SQLException e){
//    		throw new BOSException(e);
//    	}
//    	retValue.put("areaValue", areaValue);
    	/*****
    	 * 2.ȡ����Ʒ���͵Ŀ������
    	 * ����Ŀ��--��̬
    	 */
    	RetValue sellAreaValue = _getProductSellArea(ctx,leafPrjIDs);
    	retValue.put("ProductSellAreaValue", sellAreaValue);
    	/****
    	 * 3.ȡ��Ŀ
    	 */
    	EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("level");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("name");
    	view.getSelector().add("isLeaf");
    	view.setFilter(new FilterInfo());
    	if(selectObjIsPrj)
    		view.getFilter().appendFilterItem("curProject.id", selectObjID);
    	else
    		view.getFilter().appendFilterItem("fullOrgUnit.id", selectObjID);
    	view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	CostAccountCollection c=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
    	retValue.put("CostAccountCollection", c);
    	//�����Ŀ����
    	int maxLevel=0;
    	Set numberSet=new HashSet();
    	for(int i=0;i<c.size();i++){
    		CostAccountInfo info = c.get(i);
			if(info.getLevel()>maxLevel){
    			maxLevel=info.getLevel();
    		}
			numberSet.add(info.getLongNumber());
    	}
    	retValue.setInt("maxLevel", maxLevel);
		
    	/***
    	 * 4.ȡĿ��ɱ�
    	 */
    	builder.clear();
    	Set shotIDSet = new HashSet();
    	shotIDSet.add("noData");
    	builder.appendSql("select fcostAcctLgNumber as acctNumber, fid, sum(FAimCostAmt) as aimCostAmt from T_AIM_DynCostSnapShot where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("fprojectid", leafPrjIDs.toArray());
    	builder.appendSql(" group by fcostAcctLgNumber,fid");
    	rowSet=builder.executeQuery();
    	RetValue costValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber = rowSet.getString("acctNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, costValues);
				subRet.setBigDecimal("aimCostAmt", FDCNumberHelper.add(subRet.getBigDecimal("aimCostAmt"),rowSet.getBigDecimal("aimCostAmt")));
				shotIDSet.add(rowSet.getString("fid"));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("costValues", costValues);
    	/***
    	 * 5.����Ʒ���͵�Ŀ��ɱ�
    	 */
    	builder.clear();
    	builder.appendSql("select entry.fproducttypeid fproducttypeid, shot.fcostAcctLgNumber as acctNumber, sum(entry.FAimCostAmt) as aimCostAmt, sum(entry.FSalableUnitAmt) as aimSaleUnitAmt from T_AIM_DynCostSnpShtProTypEntry entry inner join T_AIM_DynCostSnapShot shot on entry.fparentid=shot.fid where ");
    	builder.appendParam("entry.fparentid", shotIDSet.toArray());
    	builder.appendSql(" group by fproducttypeid, fcostAcctLgNumber");
    	rowSet=builder.executeQuery();
    	RetValue productCostValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber = rowSet.getString("acctNumber");
				String key=rowSet.getString("fproducttypeid");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, productCostValues);
				subRet.setBigDecimal(key+"aimAimCostAmt", FDCNumberHelper.add(subRet.getBigDecimal(key+"aimAimCostAmt"), rowSet.getBigDecimal("aimCostAmt")));
				subRet.setBigDecimal(key+"aimAimSaleUnitAmt", FDCNumberHelper.add(subRet.getBigDecimal(key+"aimAimSaleUnitAmt"), rowSet.getBigDecimal("aimSaleUnitAmt")));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("productCostValues", productCostValues);
		return retValue;
	}

	/***
	 * ����Ʒ���Ͷ�̬�ɱ������ϸ�ڵ�ȡ��
	 * ����: 
	 * 		selectObjID ��ǰ��ѡ�ڵ㣬�����ǹ�����Ŀ��Ҳ��������֯ID
	 * 		selectObjIsPrj ��ǰ��ѡ�ڵ��Ƿ񹤳���Ŀ
	 * 		leafPrjIDs ͨ���ݹ�������ȡ��ѡ�ڵ��µ�������ϸ������Ŀ��
	 */
	protected RetValue _getCollectionProductDynCost(Context ctx,
			ParamValue paramValue) throws BOSException, EASBizException {
		if(paramValue==null){
    		throw new NullPointerException("bad param!");
    	}
		String lang = ctx.getLocale().getLanguage();
		String selectObjID = (String)paramValue.get("selectObjID");		
		Set leafPrjIDs     = (HashSet)paramValue.get("leafPrjIDs");
		if(selectObjID==null||leafPrjIDs==null||leafPrjIDs.size()==0||paramValue.get("selectObjIsPrj")==null){
			throw new NullPointerException("bad param!");
		}
		
		this._updateProjectsCost(ctx, leafPrjIDs);
		
		boolean selectObjIsPrj = ((Boolean)paramValue.get("selectObjIsPrj")).booleanValue();
		RetValue retValue = new RetValue(); 
		/*********
		 * 1. ��ȡ���й�����Ŀ�Ĳ�Ʒ���ͣ��޳��ظ�ֵ
		 */
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.clear();
    	builder.appendSql("select distinct prod.fid as id,prod.fname_"+lang+" as name ,prod.fnumber as number  from T_FDC_CurProjProductEntries prjProd inner join T_FDC_ProductType prod on prjProd.FProductType=prod.fid where ");
    	builder.appendParam("prjProd.FCurProject", leafPrjIDs.toArray());
    	builder.appendSql("order by prod.fnumber");
    	IRowSet rowSet=builder.executeQuery();
    	try{
    		ProductTypeCollection c=new ProductTypeCollection();
    		while(rowSet.next()){
    			ProductTypeInfo info=new ProductTypeInfo();
    			info.setId(BOSUuid.read(rowSet.getString("id")));
    			info.setNumber(rowSet.getString("number"));
    			info.setName(rowSet.getString("name"));
    			c.add(info);
    		}
    		retValue.put("ProductTypeCollection", c);
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	
    	/*****
    	 * 2.ȡ����Ʒ���͵Ŀ������
    	 * ����Ŀ��--��̬
    	 */
    	RetValue sellAreaValue = _getProductSellArea(ctx,leafPrjIDs);
    	retValue.put("ProductSellAreaValue", sellAreaValue);
    	
    	/****
    	 * 3.ȡ��Ŀ
    	 */
    	EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("level");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("name");
    	view.getSelector().add("isLeaf");
    	view.setFilter(new FilterInfo());
    	if(selectObjIsPrj)
    		view.getFilter().appendFilterItem("curProject.id", selectObjID);
    	else
    		view.getFilter().appendFilterItem("fullOrgUnit.id", selectObjID);
    	view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	CostAccountCollection c=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
    	retValue.put("CostAccountCollection", c);
    	//�����Ŀ����
    	int maxLevel=0;
    	Set numberSet=new HashSet();
    	for(int i=0;i<c.size();i++){
    		CostAccountInfo info = c.get(i);
			if(info.getLevel()>maxLevel){
    			maxLevel=info.getLevel();
    		}
			numberSet.add(info.getLongNumber());
    	}
    	retValue.setInt("maxLevel", maxLevel);
    	
    	/***
    	 * 4.ȡ����Ʒ���͵�Ŀ�꣬�ѷ�����δ��������̬
    	 */
    	builder.clear();
    	builder.appendSql("select fproducttypeid,fcostAcctLgNumber as acctNumber,sum(entry.FAimCostAmt) as aimCost,sum(entry.FHasHappenAmt) as happenCost,sum(entry.FDynCostAmt) as dynCost from T_AIM_DynCostSnpShtProTypEntry entry inner join T_AIM_DynCostSnapShot shot on entry.fparentid=shot.fid where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("fprojectid", leafPrjIDs.toArray());
    	builder.appendSql(" group by fproducttypeid,fcostAcctLgNumber ");
    	rowSet=builder.executeQuery();
    	RetValue productCostValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber = rowSet.getString("acctNumber");
				String key=rowSet.getString("fproducttypeid");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, productCostValues);
				subRet.setBigDecimal(key+"aimCost", FDCNumberHelper.add(subRet.getBigDecimal(key+"aimCost"), rowSet.getBigDecimal("aimCost")));
				subRet.setBigDecimal(key+"dynCost", FDCNumberHelper.add(subRet.getBigDecimal(key+"dynCost"), rowSet.getBigDecimal("dynCost")));
				subRet.setBigDecimal(key+"happenCost", FDCNumberHelper.add(subRet.getBigDecimal(key+"happenCost"), rowSet.getBigDecimal("happenCost")));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("productCostValues", productCostValues);
    	
    	/***
    	 * 5.ȡ��Ŀ�Ķ�̬�ɱ�
    	 * 
    	 */
    	builder.clear();
    	builder.appendSql("select fcostAcctLgNumber as acctNumber,sum(FDynCostAmt) as dynCost from T_AIM_DynCostSnapShot shot where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("fprojectid", leafPrjIDs.toArray());
    	builder.appendSql(" group by fcostAcctLgNumber ");
    	rowSet=builder.executeQuery();
    	RetValue costValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("acctNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, costValues);
    			subRet.setBigDecimal("dynCost", FDCNumberHelper.add(subRet.getBigDecimal("dynCost"), rowSet.getBigDecimal("dynCost")));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("costValues", costValues);
    	
    	
		return retValue;
	}

	/***
	 * ȡĳ��֯�򹤳���Ŀ�£���Ʒ���͵Ŀ������
	 * key=productTypeID+fprojectstage
	 * value=indexValue
	 * select ind.fprojororgid,ind.fproducttypeid,ind_e.findexvalue from t_fdc_projectindexdata ind inner join t_fdc_projectindexdataentry ind_e on ind.fid=ind_e.fparentid

		where ind.fislatestver=1 //���°汾
		and ind_e.fapportiontypeid='qHQt0wEMEADgAAaHwKgTuzW0boA=' //�������
		and ind.fproducttypeid is not null 
		and ind.fprojectstage='DYNCOST' //��̬�ɱ�
		and ind.fprojororgid='//kP5klxTRWnzBzDvtJ3ZPnl6Ss=' //������Ŀ

		order by ind.fprojororgid,ind.fproducttypeid
	 */
	protected RetValue _getProductSellArea(Context ctx, Set leafPrjIDs)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select ind.fproducttypeid,ind.fprojectstage,sum(ind_e.findexvalue) as findexvalue from t_fdc_projectindexdata ind inner join t_fdc_projectindexdataentry ind_e on ind.fid=ind_e.fparentid");
		builder.appendSql(" where ind.fislatestver=1 and ");
		builder.appendParam("ind_e.fapportiontypeid", ApportionTypeInfo.sellAreaType);
		builder.appendSql(" and ind.fproducttypeid is not null and ");
		builder.appendParam("ind.fprojororgid",leafPrjIDs.toArray());
		builder.appendSql(" group by ind.fproducttypeid,ind.fprojectstage ");
		RetValue retValue = new RetValue();
		
		IRowSet rowSet=builder.executeQuery();
    	try{
    		while(rowSet.next()){
    			String key =rowSet.getString("fproducttypeid")+rowSet.getString("fprojectstage");
    			retValue.put(key, rowSet.getBigDecimal("findexvalue"));
    		}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
		
		return retValue;
	}
	
	/**
	 * ���´���Ĺ�����Ŀ������
	 * 
	 * ���µ�ʱ������ж�,ֻ���³ɱ��仯�˵�����
	 * @param ctx
	 * @param prjSet
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _updateProjectsCost(Context ctx,Set prjSet) throws BOSException,EASBizException {
		//ȡ�ɱ��仯�˵Ľ��б���
		prjSet = ProjectCostChangeLogFactory.getLocalInstance(ctx).getChangePrjs(prjSet);
		saveShot(ctx, prjSet);
	}

	protected RetValue _getCollectionContractAcctCostDetails(Context ctx,
			ParamValue paramValue) throws BOSException, EASBizException {
		if(paramValue==null){
    		throw new NullPointerException("bad param!");
    	}
		RetValue retValue = new RetValue();
		String acctLongNumber=paramValue.getString("acctLongNumber");
		Set leafPrjIds = (Set)paramValue.get("leafPrjIds");
		boolean displayNoText = ((Boolean)paramValue.get("displayNoText")).booleanValue();
		boolean displayContract = ((Boolean)paramValue.get("displayContract")).booleanValue();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		/***
		 * ȡ��ͬ��ֽ��
		 */
		if(displayContract){
			builder.appendSql("select acct.fcurproject as curprojectid,acct.fid as acctid,acct.flongnumber as acctlongnumber,acct.flevel as acctlevel,contract.fid as contractid,contract.fcreatetime as createtime,costsplit.fcostbilltype as splittype,0 as isNotText,sum(splitentry.famount) as amount from t_aim_costsplit costsplit  ");
			builder.appendSql("inner join t_aim_costsplitentry splitentry on costsplit.fid=splitentry.fparentid ");
			builder.appendSql("inner join t_fdc_costaccount acct on splitentry.fcostaccountid=acct.fid ");
			builder.appendSql("inner join t_con_contractbill contract on costsplit.fcostbillid=contract.fid ");
			builder.appendSql("where costsplit.fcostbilltype='CONTRACTSPLIT' ");
			builder.appendSql("AND ");
			builder.appendSql("costsplit.fisinvalid=0 ");
			builder.appendSql("and ");
			builder.appendSql("(charindex(?||'!',acct.flongnumber)=1 or acct.flongnumber=?) ");
			builder.addParam(acctLongNumber);
			builder.addParam(acctLongNumber);
			builder.appendSql("and  ");
			builder.appendSql("splitentry.fisproduct=0 ");
			builder.appendSql("and ");
			builder.appendSql("acct.fisleaf=1 ");
			builder.appendSql("and ");
			builder.appendParam("acct.fcurproject",leafPrjIds.toArray());
			builder.appendSql(" ");
			builder.appendSql("group by acct.fcurproject,acct.fid,acct.flongnumber,acct.flevel,contract.fid,contract.fcreatetime,costsplit.fcostbilltype ");
			builder.appendSql(" ");
			builder.appendSql("union ");
			builder.appendSql(" ");
			/***
			 * ȡ�����ֽ��
			 */
			builder.appendSql("select acct.fcurproject as curprojectid,acct.fid as acctid,acct.flongnumber as acctlongnumber,acct.flevel as acctlevel,contract.fid as contractid,contract.fcreatetime as createtime,costsplit.fcostbilltype as splittype,0 as isNotText,sum(splitentry.famount) as amount from t_aim_costsplit costsplit  ");
			builder.appendSql("inner join t_aim_costsplitentry splitentry on costsplit.fid=splitentry.fparentid ");
			builder.appendSql("inner join t_fdc_costaccount acct on splitentry.fcostaccountid=acct.fid ");
			builder.appendSql("inner join T_CON_ContractChangeBill conchange on costsplit.fcostbillid=conchange.fid ");
			builder.appendSql("inner join t_con_contractbill contract on conchange.fcontractbillid=contract.fid ");
			builder.appendSql("where costsplit.fcostbilltype='CNTRCHANGESPLIT' ");
			builder.appendSql("AND ");
			builder.appendSql("costsplit.fisinvalid=0 ");
			builder.appendSql("and ");
			builder.appendSql("(charindex(?||'!',acct.flongnumber)=1 or acct.flongnumber=?) ");
			builder.addParam(acctLongNumber);
			builder.addParam(acctLongNumber);
			builder.appendSql("and  ");
			builder.appendSql("splitentry.fisproduct=0 ");
			builder.appendSql("and ");
			builder.appendSql("acct.fisleaf=1 ");
			builder.appendSql("and ");
			builder.appendParam("acct.fcurproject",leafPrjIds.toArray());
			builder.appendSql(" ");
			builder.appendSql("group by acct.fcurproject,acct.fid,acct.flongnumber,acct.flevel,contract.fid,contract.fcreatetime,costsplit.fcostbilltype ");
			builder.appendSql(" ");
			builder.appendSql("union ");
			builder.appendSql(" ");
			/***
			 * ȡ�����ֽ���Ҫ�жϲ��ֽ�������ս���
			 */
			builder.appendSql("select acct.fcurproject as curprojectid,acct.fid as acctid,acct.flongnumber as acctlongnumber,acct.flevel as acctlevel,contract.fid as contractid,contract.fcreatetime as createtime,costsplit.fcostbilltype as splittype,0 as isNotText,sum(splitentry.famount) as amount from t_aim_costsplit costsplit  ");
			builder.appendSql("inner join t_aim_costsplitentry splitentry on costsplit.fid=splitentry.fparentid ");
			builder.appendSql("inner join t_fdc_costaccount acct on splitentry.fcostaccountid=acct.fid ");
			builder.appendSql("inner join T_CON_ContractSettlementBill settle on costsplit.fcostbillid=settle.fid ");
			builder.appendSql("inner join t_con_contractbill contract on settle.fcontractbillid=contract.fid ");
			builder.appendSql("where costsplit.fcostbilltype='SETTLEMENTSPLIT' ");
			builder.appendSql("AND ");
			builder.appendSql("costsplit.fisinvalid=0 ");
			builder.appendSql("and ");
			builder.appendSql("(charindex(?||'!',acct.flongnumber)=1 or acct.flongnumber=?) ");
			builder.addParam(acctLongNumber);
			builder.addParam(acctLongNumber);
			builder.appendSql("and ");
			builder.appendSql(" ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) or (settle.FIsSettled=0 and settle.FIsFinalSettle=0)) ");
			builder.appendSql("and ");
			builder.appendSql("splitentry.fisproduct=0 ");
			builder.appendSql("and ");
			builder.appendSql("acct.fisleaf=1 ");
			builder.appendSql("and ");
			builder.appendParam("acct.fcurproject",leafPrjIds.toArray());
			builder.appendSql(" ");
			builder.appendSql(" group by acct.fcurproject,acct.fid,acct.flongnumber,acct.flevel,contract.fid,contract.fcreatetime,costsplit.fcostbilltype ");
			builder.appendSql(" ");
			builder.appendSql("union ");
			builder.appendSql(" ");
			/***
			 * ȡ��ʵ�ֲ�ֵ=�����ֻ���
			 */
			builder.appendSql("select acct.fcurproject as curprojectid,acct.fid as acctid,acct.flongnumber as acctlongnumber,acct.flevel as acctlevel,contract.fid as contractid,contract.fcreatetime as createtime,'totalSettlePrice' as splittype,0 as isNotText,sum(splitentry.famount) as amount from t_aim_costsplit costsplit  ");
			builder.appendSql("inner join t_aim_costsplitentry splitentry on costsplit.fid=splitentry.fparentid ");
			builder.appendSql("inner join t_fdc_costaccount acct on splitentry.fcostaccountid=acct.fid ");
			builder.appendSql("inner join T_CON_ContractSettlementBill settle on costsplit.fcostbillid=settle.fid ");
			builder.appendSql("inner join t_con_contractbill contract on settle.fcontractbillid=contract.fid ");
			builder.appendSql("where costsplit.fcostbilltype='SETTLEMENTSPLIT' ");
			builder.appendSql("AND ");
			builder.appendSql("costsplit.fisinvalid=0 ");
			builder.appendSql("and ");
			builder.appendSql("(charindex(?||'!',acct.flongnumber)=1 or acct.flongnumber=?) ");
			builder.addParam(acctLongNumber);
			builder.addParam(acctLongNumber);
			builder.appendSql("and ");
			builder.appendSql("splitentry.fisproduct=0 ");
			builder.appendSql("and ");
			builder.appendSql("acct.fisleaf=1 ");
			builder.appendSql("and ");
			builder.appendParam("acct.fcurproject",leafPrjIds.toArray());
			builder.appendSql(" ");
			builder.appendSql(" group by acct.fcurproject,acct.fid,acct.flongnumber,acct.flevel,contract.fid,contract.fcreatetime ");
			builder.appendSql(" ");
			builder.appendSql("union ");
			builder.appendSql(" ");
			
			/***
			 * ȡ��ʵ�ֳɱ�-����--������ͬ
			 */
			builder.appendSql("select acct.fcurproject as curprojectid,acct.fid as acctid,acct.flongnumber as acctlongnumber,acct.flevel as acctlevel,contract.fid as contractid,contract.fcreatetime as createtime,'HASPAYCOSTAMT' as splittype,0 as isNotText,sum(splitentry.famount) as amount from t_aim_costsplit costsplit  ");
			builder.appendSql("inner join t_aim_costsplitentry splitentry on costsplit.fid=splitentry.fparentid ");
			builder.appendSql("inner join t_fdc_costaccount acct on splitentry.fcostaccountid=acct.fid ");
			builder.appendSql("inner join T_FNC_PaymentSplit split on costsplit.fcostbillid=split.fpaymentbillid and split.fisworkloadbill=0 ");
			builder.appendSql("inner join t_con_contractbill contract on split.fcontractbillid=contract.fid ");
			builder.appendSql("where costsplit.fcostbilltype='PAYMENTSPLIT' ");
			builder.appendSql("AND ");
			builder.appendSql("costsplit.fisinvalid=0 ");
			builder.appendSql("and ");
			builder.appendSql("(charindex(?||'!',acct.flongnumber)=1 or acct.flongnumber=?) ");
			builder.addParam(acctLongNumber);
			builder.addParam(acctLongNumber);
			builder.appendSql("and  ");
			builder.appendSql("splitentry.fisproduct=0 ");
			builder.appendSql("and ");
			builder.appendSql("acct.fisleaf=1 ");
			builder.appendSql("and ");
			builder.appendParam("acct.fcurproject",leafPrjIds.toArray());
			builder.appendSql(" ");
			builder.appendSql("group by acct.fcurproject,acct.fid,acct.flongnumber,acct.flevel,contract.fid,contract.fcreatetime ");
			builder.appendSql(" ");
			builder.appendSql("union ");
			builder.appendSql(" ");
			
			/***
			 * ȡ��ʵ�ֳɱ�-������--������ͬ
			 */
			builder.appendSql("select acct.fcurproject as curprojectid,acct.fid as acctid,acct.flongnumber as acctlongnumber,acct.flevel as acctlevel,contract.fid as contractid,contract.fcreatetime as createtime,'HASPAYCOSTAMT' as splittype,0 as isNotText,sum(splitentry.famount) as amount from t_aim_costsplit costsplit  ");
			builder.appendSql("inner join t_aim_costsplitentry splitentry on costsplit.fid=splitentry.fparentid ");
			builder.appendSql("inner join t_fdc_costaccount acct on splitentry.fcostaccountid=acct.fid ");
			builder.appendSql("inner join T_FNC_WorkLoadConfirmBill bill on costsplit.fcostbillid=bill.fid ");
			builder.appendSql("inner join t_con_contractbill contract on bill.fcontractbillid=contract.fid ");
			builder.appendSql("where costsplit.fcostbilltype='PAYMENTSPLIT' ");
			builder.appendSql("AND ");
			builder.appendSql("costsplit.fisinvalid=0 ");
			builder.appendSql("and ");
			builder.appendSql("(charindex(?||'!',acct.flongnumber)=1 or acct.flongnumber=?) ");
			builder.addParam(acctLongNumber);
			builder.addParam(acctLongNumber);
			builder.appendSql("and  ");
			builder.appendSql("splitentry.fisproduct=0 ");
			builder.appendSql("and ");
			builder.appendSql("acct.fisleaf=1 ");
			builder.appendSql("and ");
			builder.appendParam("acct.fcurproject",leafPrjIds.toArray());
			builder.appendSql(" ");
			builder.appendSql("group by acct.fcurproject,acct.fid,acct.flongnumber,acct.flevel,contract.fid,contract.fcreatetime ");
			builder.appendSql(" ");
			builder.appendSql("union ");
			builder.appendSql(" ");
			
			/***
			 * ȡ�����ֽ��--������ͬ
			 */
			builder.appendSql("select acct.fcurproject as curprojectid,acct.fid as acctid,acct.flongnumber as acctlongnumber,acct.flevel as acctlevel,contract.fid as contractid,contract.fcreatetime as createtime,costsplit.fcostbilltype as splittype,0 as isNotText,sum(splitentry.fpaidamount) as amount from t_aim_costsplit costsplit  ");
			builder.appendSql("inner join t_aim_costsplitentry splitentry on costsplit.fid=splitentry.fparentid ");
			builder.appendSql("inner join t_fdc_costaccount acct on splitentry.fcostaccountid=acct.fid ");
			builder.appendSql("inner join T_CAS_Paymentbill payment on costsplit.fcostbillid=payment.fid ");
			builder.appendSql("inner join t_con_contractbill contract on payment.fcontractbillid=contract.fid ");
			builder.appendSql("where costsplit.fcostbilltype='PAYMENTSPLIT' ");
			builder.appendSql("AND ");
			builder.appendSql("costsplit.fisinvalid=0 ");
			builder.appendSql("and ");
			builder.appendSql("(charindex(?||'!',acct.flongnumber)=1 or acct.flongnumber=?) ");
			builder.addParam(acctLongNumber);
			builder.addParam(acctLongNumber);
			builder.appendSql("and  ");
			builder.appendSql("splitentry.fisproduct=0 ");
			builder.appendSql("and ");
			builder.appendSql("acct.fisleaf=1 ");
			builder.appendSql("and ");
			builder.appendParam("acct.fcurproject",leafPrjIds.toArray());
			builder.appendSql(" ");
			builder.appendSql("group by acct.fcurproject,acct.fid,acct.flongnumber,acct.flevel,contract.fid,contract.fcreatetime,costsplit.fcostbilltype ");
			builder.appendSql(" ");
			
		}
		if(displayNoText){
			if(displayContract){
				builder.appendSql("union ");
				builder.appendSql(" ");
			}
			/***
			 * ȡ��ʵ�ֳɱ�--�������ı���ͬ
			 */
			builder.appendSql("select acct.fcurproject as curprojectid,acct.fid as acctid,acct.flongnumber as acctlongnumber,acct.flevel as acctlevel,contract.fid as contractid,contract.fcreatetime as createtime,'HASPAYCOSTAMT' as splittype,1 as isNotText,sum(splitentry.famount) as amount from t_aim_costsplit costsplit  ");
			builder.appendSql("inner join t_aim_costsplitentry splitentry on costsplit.fid=splitentry.fparentid ");
			builder.appendSql("inner join t_fdc_costaccount acct on splitentry.fcostaccountid=acct.fid ");
			builder.appendSql("inner join T_CAS_Paymentbill payment on costsplit.fcostbillid=payment.fid ");
			builder.appendSql("inner join t_con_contractwithouttext contract on payment.fcontractbillid=contract.fid ");
			builder.appendSql("where costsplit.fcostbilltype='PAYMENTSPLIT' ");
			builder.appendSql("AND ");
			builder.appendSql("costsplit.fisinvalid=0 ");
			builder.appendSql("and ");
			builder.appendSql("(charindex(?||'!',acct.flongnumber)=1 or acct.flongnumber=?) ");
			builder.addParam(acctLongNumber);
			builder.addParam(acctLongNumber);
			builder.appendSql("and  ");
			builder.appendSql("splitentry.fisproduct=0 ");
			builder.appendSql("and ");
			builder.appendSql("acct.fisleaf=1 ");
			builder.appendSql("and ");
			builder.appendParam("acct.fcurproject",leafPrjIds.toArray());
			builder.appendSql(" ");
			builder.appendSql("group by acct.fcurproject,acct.fid,acct.flongnumber,acct.flevel,contract.fid,contract.fcreatetime ");
			
			builder.appendSql("union ");
			builder.appendSql(" ");
			
			/***
			 * ȡ�����ֽ��--�������ı���ͬ
			 */
			builder.appendSql("select acct.fcurproject as curprojectid,acct.fid as acctid,acct.flongnumber as acctlongnumber,acct.flevel as acctlevel,contract.fid as contractid,contract.fcreatetime as createtime,costsplit.fcostbilltype as splittype,1 as isNotText,sum(splitentry.fpaidamount) as amount from t_aim_costsplit costsplit  ");
			builder.appendSql("inner join t_aim_costsplitentry splitentry on costsplit.fid=splitentry.fparentid ");
			builder.appendSql("inner join t_fdc_costaccount acct on splitentry.fcostaccountid=acct.fid ");
			builder.appendSql("inner join T_CAS_Paymentbill payment on costsplit.fcostbillid=payment.fid ");
			builder.appendSql("inner join t_con_contractwithouttext contract on payment.fcontractbillid=contract.fid ");
			builder.appendSql("where costsplit.fcostbilltype='PAYMENTSPLIT' ");
			builder.appendSql("AND ");
			builder.appendSql("costsplit.fisinvalid=0 ");
			builder.appendSql("and ");
			builder.appendSql("(charindex(?||'!',acct.flongnumber)=1 or acct.flongnumber=?) ");
			builder.addParam(acctLongNumber);
			builder.addParam(acctLongNumber);
			builder.appendSql("and  ");
			builder.appendSql("splitentry.fisproduct=0 ");
			builder.appendSql("and ");
			builder.appendSql("acct.fisleaf=1 ");
			builder.appendSql("and ");
			builder.appendParam("acct.fcurproject",leafPrjIds.toArray());
			builder.appendSql(" ");
			builder.appendSql("group by acct.fcurproject,acct.fid,acct.flongnumber,acct.flevel,contract.fid,contract.fcreatetime,costsplit.fcostbilltype ");
			builder.appendSql(" ");
			
			/***
			 * ���ı���ͬ�ĺ�ͬ��ֽ��ҲҪ������
			 */
			builder.appendSql("union ");
			builder.appendSql(" ");
			
			builder.appendSql("select acct.fcurproject as curprojectid,acct.fid as acctid,acct.flongnumber as acctlongnumber,acct.flevel as acctlevel,contract.fid as contractid,contract.fcreatetime as createtime,'CONTRACTSPLIT' as splittype,1 as isNotText,sum(splitentry.famount) as amount from t_aim_costsplit costsplit  ");
			builder.appendSql("inner join t_aim_costsplitentry splitentry on costsplit.fid=splitentry.fparentid ");
			builder.appendSql("inner join t_fdc_costaccount acct on splitentry.fcostaccountid=acct.fid ");
			builder.appendSql("inner join T_CAS_Paymentbill payment on costsplit.fcostbillid=payment.fid ");
			builder.appendSql("inner join t_con_contractwithouttext contract on payment.fcontractbillid=contract.fid ");
			builder.appendSql("where costsplit.fcostbilltype='PAYMENTSPLIT' ");
			builder.appendSql("AND ");
			builder.appendSql("costsplit.fisinvalid=0 ");
			builder.appendSql("and ");
			builder.appendSql("(charindex(?||'!',acct.flongnumber)=1 or acct.flongnumber=?) ");
			builder.addParam(acctLongNumber);
			builder.addParam(acctLongNumber);
			builder.appendSql("and  ");
			builder.appendSql("splitentry.fisproduct=0 ");
			builder.appendSql("and ");
			builder.appendSql("acct.fisleaf=1 ");
			builder.appendSql("and ");
			builder.appendParam("acct.fcurproject",leafPrjIds.toArray());
			builder.appendSql(" ");
			builder.appendSql("group by acct.fcurproject,acct.fid,acct.flongnumber,acct.flevel,contract.fid,contract.fcreatetime,costsplit.fcostbilltype ");
			builder.appendSql(" ");
			
		}
		
		builder.appendSql("order by curprojectid,acctlongnumber,isnottext,createtime ");
		
		
		IRowSet rowSet = builder.executeQuery();
		RetValue projectCostAccounts = new RetValue();
		RetValue accountContracts = new RetValue();
		RetValue accountContractSplitValues = new RetValue();
		Set contractIdSet = new HashSet();
		Set noTextIdSet = new HashSet();
		Set allIdSet = new HashSet();
		try {
			while (rowSet.next()) {
				List costAccounts ;
				Map costAccountMap ;
				String projectid = rowSet.getString("curprojectid");
				if(projectCostAccounts.containsKey(projectid)){
					costAccounts = (ArrayList)projectCostAccounts.get(projectid);
					costAccountMap = (Map)projectCostAccounts.get(projectid+"Map");
				}
				else{
					costAccounts = new ArrayList();
					costAccountMap = new HashMap();
					projectCostAccounts.put(projectid, costAccounts);
					projectCostAccounts.put(projectid+"Map", costAccountMap);
				}
				
				String acctid = rowSet.getString("acctid");
				String acctlongnumber = rowSet.getString("acctlongnumber");
				int acctlevel = rowSet.getInt("acctlevel");
				if(!costAccountMap.containsKey(acctlongnumber)){
					CostAccountInfo costAccountInfo = new  CostAccountInfo();
					costAccountInfo.setId(BOSUuid.read(acctid));
					costAccountInfo.setLongNumber(acctlongnumber);
					costAccountInfo.setIsLeaf(true);
					costAccountInfo.setLevel(acctlevel);
					costAccounts.add(costAccountInfo);
					costAccountMap.put(acctlongnumber, Boolean.TRUE);
				}
				List contractIds;
				if(accountContracts.containsKey(acctid)){
					contractIds = (List)accountContracts.get(acctid);
				}else{
					contractIds = new ArrayList();
					accountContracts.put(acctid, contractIds);
				}
				
				String contractId = rowSet.getString("contractid") ;
				int isNotText  = rowSet.getInt("isnottext");
				RetValue accountContractSplitValue;
				if(accountContractSplitValues.containsKey(acctid+contractId)){
					accountContractSplitValue = (RetValue)accountContractSplitValues.get(acctid+contractId);
				}else{
					contractIds.add(contractId);
					accountContractSplitValue = new RetValue();
					accountContractSplitValues.put(acctid+contractId, accountContractSplitValue);
				}
				String splitType = rowSet.getString("splittype");
				BigDecimal amount = rowSet.getBigDecimal("amount");
				//��ӣ����⸲�ǣ���ʵ�ֳɱ�sqlû��group by�������ۼ����,��֧�ּ��븴�� ģʽ��ȡ��,����ֻдһ�д���)by hpw 2010.07.28
				accountContractSplitValue.put(splitType, FDCHelper.add(accountContractSplitValue.get(splitType), amount));
				
				if(isNotText==0)
					contractIdSet.add(contractId);
				else
					noTextIdSet.add(contractId);
				allIdSet.add(contractId);
			}
			
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
		
		retValue.put("projectCostAccounts", projectCostAccounts);
		retValue.put("accountContracts", accountContracts);
		retValue.put("accountContractSplitValues", accountContractSplitValues);
		
		/***
		 * ȡ��ͬ��Ϣ
		 */
		EntityViewInfo view ;
		FilterInfo filter ;
		if(contractIdSet.size()>0){
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",contractIdSet,CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("signDate");
			view.getSelector().add("contractSourceId.id");
			view.getSelector().add("contractSourceId.name");
			view.getSelector().add("amount");
			view.getSelector().add("hasSettled");
			view.getSelector().add("curProject.name");
			view.getSelector().add("curProject.id");
			view.getSelector().add("curProject.longNumber");
			view.getSelector().add("partB.name");
			view.getSelector().add("currency.name");
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
			Map contractBills = new HashMap();
			for(Iterator it=contractBillColl.iterator();it.hasNext();){
				ContractBillInfo contractBill = (ContractBillInfo)it.next();
				contractBills.put(contractBill.getId().toString(), contractBill);
			}
			retValue.put("contractBillMap", contractBills);
		}
		
		
		
		/***
		 * ȡ���ı���ͬ��Ϣ
		 */
		if(noTextIdSet.size()>0){
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",noTextIdSet,CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("signDate");
			view.getSelector().add("amount");
			view.getSelector().add("curProject.name");
			view.getSelector().add("curProject.id");
			view.getSelector().add("curProject.longNumber");
			view.getSelector().add("currency.name");
			ContractWithoutTextCollection contractWithoutTextColl = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(view);
			Map contractWithoutTextBills = new HashMap();
			for(Iterator it=contractWithoutTextColl.iterator();it.hasNext();){
				ContractWithoutTextInfo contractWithoutTextBill = (ContractWithoutTextInfo)it.next();
				contractWithoutTextBills.put(contractWithoutTextBill.getId().toString(), contractWithoutTextBill);
			}
			/***
			 * ȡ���ı���ͬ�Է���λ
			 */
			view = new EntityViewInfo();
			view.getSelector().add("realSupplier.name");
			view.getSelector().add("contractId");
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("contractId",noTextIdSet,CompareType.INCLUDE));
			PayRequestBillCollection conWithoutTextPays = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
			for(Iterator it=conWithoutTextPays.iterator();it.hasNext();){
				PayRequestBillInfo payRequestBillInfo = (PayRequestBillInfo)it.next();
				String key = payRequestBillInfo.getContractId();
				ContractWithoutTextInfo contractWithoutTextBill = (ContractWithoutTextInfo)contractWithoutTextBills.get(key);
				if(contractWithoutTextBill!=null&&payRequestBillInfo.getRealSupplier()!=null){
					contractWithoutTextBill.put("partB", payRequestBillInfo.getRealSupplier());
				}
			}
			retValue.put("contractWithoutTextMap", contractWithoutTextBills);
		}
		
		
		
		
		/**
		 * �Ѹ�������ȡ
		 */
		if(allIdSet.size()>0){
			IPaymentBill ipb = PaymentBillFactory.getLocalInstance(ctx);
			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.getSelector().add(new SelectorItemInfo("id"));
			view.getSelector().add(new SelectorItemInfo("actPayAmt"));
			view.getSelector().add(new SelectorItemInfo("actPayLocAmt"));
			view.getSelector().add(new SelectorItemInfo("projectPriceInContract"));
			view.getSelector().add(new SelectorItemInfo("contractBillId"));
			view.getSelector().add(new SelectorItemInfo("billStatus"));
			view.getSelector().add(new SelectorItemInfo("fdcPayType.*"));
			view.getSelector().add(new SelectorItemInfo("fdcPayType.payType.*"));
			filter.getFilterItems().add(new FilterItemInfo("contractBillId", allIdSet, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
			view.setFilter(filter);
			PaymentBillCollection pbc = ipb.getPaymentBillCollection(view);
			/***
			 * �Ѹ���ͬ�ڹ��̿�
			 */
			Map pbPrjConPriceMap = new HashMap();
			/***
			 * ��ͬ�Ѹ���
			 */
			Map pbAmtMap = new HashMap();
			/***
			 * ��ͬ�Ѹ����ȿ�
			 */
			Map pbProgAmtMap = new HashMap();
			
			/***
			 * ��ͬ�Ѹ������
			 */
			Map pbSetlAmtMap = new HashMap();
			/***
			 * ��ͬ�Ѹ����޿�
			 */
			Map pbKeepAmtMap = new HashMap();
			if (pbc.size() != 0) {
				for (int i = 0; i < pbc.size(); i++) {
					String key = pbc.get(i).getContractBillId();
					// ��ͬ�ڹ��̿��
					if (pbPrjConPriceMap.containsKey(key)) {
						BigDecimal tmp = (BigDecimal) pbPrjConPriceMap.get(key);
						tmp = FDCHelper.add(tmp, pbc.get(i).getProjectPriceInContract());
						pbPrjConPriceMap.put(key, tmp);
					} else {
						pbPrjConPriceMap.put(key, pbc.get(i).getProjectPriceInContract());
					}
					//�Ѹ�����Ӧȡ����
					if (pbAmtMap.containsKey(key)) {
						BigDecimal tmp = (BigDecimal) pbAmtMap.get(key);
						tmp = FDCHelper.add(tmp,pbc.get(i).getActPayLocAmt());
						pbAmtMap.put(key, tmp);
					} else {
						pbAmtMap.put(key, pbc.get(i).getActPayLocAmt());
					}
					
					if(pbc.get(i).getFdcPayType().getPayType()!=null
							&&pbc.get(i).getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.progressID)){
						if (pbProgAmtMap.containsKey(key)) {
							BigDecimal tmp = (BigDecimal) pbProgAmtMap.get(key);
							tmp = FDCHelper.add(tmp,pbc.get(i).getActPayLocAmt());
							pbProgAmtMap.put(key, tmp);
						} else {
							pbProgAmtMap.put(key, pbc.get(i).getActPayLocAmt());
						}
					}
					if(pbc.get(i).getFdcPayType().getPayType()!=null
							&&pbc.get(i).getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)){
						if (pbSetlAmtMap.containsKey(key)) {
							BigDecimal tmp = (BigDecimal) pbSetlAmtMap.get(key);
							tmp = FDCHelper.add(tmp,pbc.get(i).getActPayLocAmt());
							pbSetlAmtMap.put(key, tmp);
						} else {
							pbSetlAmtMap.put(key, pbc.get(i).getActPayLocAmt());
						}
					}
					if(pbc.get(i).getFdcPayType().getPayType()!=null
							&&pbc.get(i).getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)){
						if (pbKeepAmtMap.containsKey(key)) {
							BigDecimal tmp = (BigDecimal) pbKeepAmtMap.get(key);
							tmp = FDCHelper.add(tmp,pbc.get(i).getActPayLocAmt());
							pbKeepAmtMap.put(key, tmp);
						} else {
							pbKeepAmtMap.put(key, pbc.get(i).getActPayLocAmt());
						}
					}
					
				}
			}
			retValue.put("hasPayPrjConPriceMap", pbPrjConPriceMap);
			retValue.put("hasPayAmtMap", pbAmtMap);
			retValue.put("hasPayProgAmtMap", pbProgAmtMap);
			retValue.put("hasPaySetlAmtMap", pbSetlAmtMap);
			retValue.put("hasPayKeepAmtMap", pbKeepAmtMap);
			
			
			IPayRequestBill iprq = PayRequestBillFactory.getLocalInstance(ctx);
			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.getSelector().add(new SelectorItemInfo("id"));
			view.getSelector().add(new SelectorItemInfo("amount"));
			view.getSelector().add(new SelectorItemInfo("contractId"));
			view.getSelector().add(new SelectorItemInfo("state"));
			view.getSelector().add(new SelectorItemInfo("paymentType.*"));
			view.getSelector().add(new SelectorItemInfo("paymentType.payType.*"));
			filter.getFilterItems().add(new FilterItemInfo("contractId", allIdSet, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			view.setFilter(filter);
			PayRequestBillCollection pqrc = iprq.getPayRequestBillCollection(view);
			/***
			 * ��ͬӦ����
			 */
			Map prqAmtMap = new HashMap();
			/***
			 * ��ͬӦ�����ȿ�
			 */
			Map prqProgAmtMap = new HashMap();
			
			/***
			 * ��ͬӦ�������
			 */
			Map prqSetlAmtMap = new HashMap();
			/***
			 * ��ͬӦ�����޿�
			 */
			Map prqKeepAmtMap = new HashMap();
			if (pqrc.size() != 0) {
				for (int i = 0; i < pqrc.size(); i++) {
					String key = pqrc.get(i).getContractId();
					//�Ѹ�����Ӧȡ����
					if (prqAmtMap.containsKey(key)) {
						BigDecimal tmp = (BigDecimal) prqAmtMap.get(key);
						tmp = FDCHelper.add(tmp,pqrc.get(i).getAmount());
						prqAmtMap.put(key, tmp);
					} else {
						prqAmtMap.put(key, pqrc.get(i).getAmount());
					}
					
					if(pqrc.get(i).getPaymentType().getPayType()!=null
							&&pqrc.get(i).getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.progressID)){
						if (prqProgAmtMap.containsKey(key)) {
							BigDecimal tmp = (BigDecimal) prqProgAmtMap.get(key);
							tmp = FDCHelper.add(tmp,pqrc.get(i).getAmount());
							prqProgAmtMap.put(key, tmp);
						} else {
							prqProgAmtMap.put(key, pqrc.get(i).getAmount());
						}
					}
					if(pqrc.get(i).getPaymentType().getPayType()!=null
							&&pqrc.get(i).getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)){
						if (prqSetlAmtMap.containsKey(key)) {
							BigDecimal tmp = (BigDecimal) prqSetlAmtMap.get(key);
							tmp = FDCHelper.add(tmp,pqrc.get(i).getAmount());
							prqSetlAmtMap.put(key, tmp);
						} else {
							prqSetlAmtMap.put(key, pqrc.get(i).getAmount());
						}
					}
					if(pqrc.get(i).getPaymentType().getPayType()!=null
							&&pqrc.get(i).getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)){
						if (prqKeepAmtMap.containsKey(key)) {
							BigDecimal tmp = (BigDecimal) prqKeepAmtMap.get(key);
							tmp = FDCHelper.add(tmp,pqrc.get(i).getAmount());
							prqKeepAmtMap.put(key, tmp);
						} else {
							prqKeepAmtMap.put(key, pqrc.get(i).getAmount());
						}
					}
					
				}
			}
			// �ظ�
			// retValue.put("hasPayAmtMap", pbAmtMap);
			// retValue.put("hasPayProgAmtMap", pbProgAmtMap);
			// retValue.put("hasPaySetlAmtMap", pbSetlAmtMap);
			// retValue.put("hasPayKeepAmtMap", pbKeepAmtMap);
			
			retValue.put("shouldPayAmtMap", prqAmtMap);
			retValue.put("shouldPayProgAmtMap", prqProgAmtMap);
			retValue.put("shouldPaySetlAmtMap", prqSetlAmtMap);
			retValue.put("shouldPayKeepAmtMap", prqKeepAmtMap);
			
		}
		
		
		if(contractIdSet.size()>0){
			
			/***
			 * ��ͬ���������ȡ
			 */
			Object[] objects = contractIdSet.toArray();
			String[] contractIds = new String[objects.length];

			for (int i = 0; i < objects.length; i++) {
				contractIds[i] = (String) objects[i];
			}
			Map lastPriceMap = ContractFacadeFactory.getLocalInstance(ctx).getLastAmt(contractIds);
			retValue.put("lastPriceMap", lastPriceMap);
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill",contractIdSet,CompareType.INCLUDE));
			view.setFilter(filter);
			SorterItemInfo sorter = new SorterItemInfo();
			sorter.setPropertyName("createTime");
			sorter.setSortType(SortType.DESCEND);
			view.getSorter().add(sorter);
			view.getSelector().add("id");
			view.getSelector().add("qualityGuaranteRate");
			view.getSelector().add("isFinalSettle");
			view.getSelector().add("contractBill.id");
			view.getSelector().add("contractBill.grtRate");
			view.getSelector().add("contractBill.hasSettled");
			view.getSelector().add("totalSettlePrice");
			ContractSettlementBillCollection settles = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(view);
			Map totalSettlePriceMap = new HashMap(); // �ۼƽ��㱾λ��

			/**
			 * Added By Owen_wen 2011-03-01  R100528-051
			 * Ӧ���� 
			 * ����ǰ��Ӧ����� = ��ͬ������� * ��1-��ͬ�ʱ��������; 
			 * �����Ӧ����� = ��ͬ������� ����1-���㵥�ʱ��������
			 */
			Map payableAmtMap = new HashMap(); 
			
			// �ȴ���δ����ĺ�ͬ������㵥�޹أ�ֱ�Ӵ���
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", contractIdSet, CompareType.INCLUDE));
			view.getSelector().add("id");
			view.getSelector().add("grtRate");
			view.getSelector().add("hasSettled");
			view.setFilter(filter);
			ContractBillCollection conCols = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
			for (Iterator it = conCols.iterator(); it.hasNext();) {
				ContractBillInfo conBillInfo = (ContractBillInfo) it.next();
				String contractId = conBillInfo.getId().toString();
				if (!conBillInfo.isHasSettled()) {
					payableAmtMap.put(contractId, ((BigDecimal) lastPriceMap.get(contractId)).multiply(FDCConstants.ONE.subtract((conBillInfo
							.getGrtRate().divide(FDCConstants.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP)))));
				}
			}
			
			for (Iterator it = settles.iterator(); it.hasNext();) {
				ContractSettlementBillInfo settle = (ContractSettlementBillInfo)it.next();
				String contractId = settle.getContractBill().getId().toString();
				if(!totalSettlePriceMap.containsKey(contractId))
					totalSettlePriceMap.put(contractId, settle.getTotalSettlePrice());
				
				if (!payableAmtMap.containsKey(contractId)) { 
					if (settle.getContractBill().isHasSettled()) {//�����ĺ�ͬ
						payableAmtMap.put(contractId, ((BigDecimal) lastPriceMap.get(contractId)).multiply(FDCConstants.ONE.subtract((settle
								.getQualityGuaranteRate().divide(FDCConstants.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP)))));
					}
				}
			}
			
			retValue.put("payableAmtMap", payableAmtMap);
			retValue.put("totalSettlePriceMap", totalSettlePriceMap);
		}
		
		
		return retValue;
	}

	/**
	 * ������������Ŀ�ɱ�������(����Ŀ����Ϊά��)
	 */
	protected RetValue _getProjectCostAnalysis(Context ctx,
			ParamValue paramValue) throws BOSException, EASBizException {
		if(paramValue==null){
    		throw new NullPointerException("bad param!");
    	}
		TimeTools.getInstance().reset();
		TimeTools.getInstance().msValuePrintln("----------start----------");
		
		RetValue retValue = new RetValue();
		Set leafPrjIDs = (Set) paramValue.get("leafPrjIDs");// just one element
		
		String measureId = ""; // ���հ汾Ŀ��ɱ�����ID
		String reverseId = ""; // ���հ汾Ŀ��ɱ��޶�ID
		
		// Ŀ��ɱ�����
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("project.id", leafPrjIDs, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isAimMeasure", Boolean.TRUE));
		view.getSelector().add("id");
		view.getSelector().add("versionNumber");
		view.getSelector().add("versionName");
		view.getSelector().add("isLastVersion");
		view.getSelector().add("costEntry.id");
		view.getSelector().add("costEntry.amount");
        view.getSelector().add("costEntry.costAccount.longNumber");
//		view.getSorter().add(new SorterItemInfo("versionNumber"));
		MeasureCostCollection measureCosts = MeasureCostFactory.getLocalInstance(ctx).getMeasureCostCollection(view);
		Map measureCostsMap = new HashMap();
		Map measureEntrysMap = new HashMap();
		Map headIDsMap = new HashMap();
		if (measureCosts != null && measureCosts.size() > 0) {
			for (int i = 0; i < measureCosts.size(); i++) {
				MeasureCostInfo measureCost = measureCosts.get(i);
				String id = measureCost.getId().toString();
				if(measureCost.isIsLastVersion()){
					measureId = id;
				}
				measureCostsMap.put(measureCost.getId().toString(), measureCost);
				for(int j=0;j<measureCost.getCostEntry().size();j++){
					MeasureEntryInfo measureEntry = measureCost.getCostEntry().get(j);
					String entryId = measureEntry.getId().toString();
					headIDsMap.put(entryId, id);
					String key = id +measureEntry.getCostAccount().getLongNumber();
					if(measureEntrysMap.containsKey(key)){
						BigDecimal amount = (BigDecimal)measureEntrysMap.get(key);
						amount=FDCHelper.add(amount, measureEntry.getAmount());
						measureEntrysMap.put(key, amount);
					}else{
						measureEntrysMap.put(key, measureEntry.getAmount());
					}
				}
			}
		} else {
			measureCosts = new MeasureCostCollection();
			measureCosts.add(new MeasureCostInfo());
		}
		retValue.put("measureCosts", measureCosts);
		retValue.put("measureEntrysMap", measureEntrysMap);
		
		// Ŀ��ɱ�������汾ָ��
		Set headIDSet = new HashSet(measureCostsMap.keySet());
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("headID");
		view.getSelector().add("entrys.sellArea");
		view.getSelector().add("entrys.buildArea");
		view.getSelector().add("entrys.product");
		view.getSelector().add("entrys.isProduct");
		view.getSelector().add("entrys.isSplit");
		filter.getFilterItems().add(new FilterItemInfo("headID", headIDSet, CompareType.INCLUDE));
		view.getSorter().add(new SorterItemInfo("entrys.type"));
		view.getSorter().add(new SorterItemInfo("entrys.index"));
		PlanIndexCollection planIndexs = PlanIndexFactory.getLocalInstance(ctx).getPlanIndexCollection(view);
		RetValue indexValues = new RetValue();
		boolean isBuildPartLogic = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_BUILDPARTLOGIC);
		for(int i=0;i<planIndexs.size();i++){
			RetValue indexValue = new RetValue();
			BigDecimal buildArea = FDCHelper.ZERO; // �������
			BigDecimal sellArea = FDCHelper.ZERO; // �������
			PlanIndexInfo planIndex = planIndexs.get(i);
			for(int j=0;j<planIndex.getEntrys().size();j++){
				if(planIndex.getEntrys().get(j)==null||planIndex.getEntrys().get(j).getProduct()==null){
					continue;
				}
				if(planIndex.getEntrys().get(j).isIsProduct()&&planIndex.getEntrys().get(j).isIsSplit()){
					sellArea=sellArea.add(FDCHelper.toBigDecimal(planIndex.getEntrys().get(j).getSellArea()));
					if(isBuildPartLogic){
						buildArea=buildArea.add(FDCHelper.toBigDecimal(planIndex.getEntrys().get(j).getBuildArea()));
					}
				}
			}
			if(!isBuildPartLogic){
				buildArea = buildArea.add(FDCHelper.toBigDecimal(planIndex.getTotalBuildArea()));
			}
			indexValue.setBigDecimal("sellArea", sellArea);
			indexValue.setBigDecimal("buildArea", buildArea);
			indexValues.put(planIndex.getHeadID(), indexValue);
		}
		retValue.put("indexValues", indexValues);
		
		// Ŀ��ɱ��޶�
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", leafPrjIDs, CompareType.INCLUDE));
		view.getSelector().add("id");
		view.getSelector().add("versionNumber");
		view.getSelector().add("versionName");
		view.getSelector().add("isLastVersion");
//		view.getSelector().add("costEntry.measureEntryID"); // ΪʲôĿ��ɱ�ͷ������Ŀ��ɱ��ֶΣ����ڷ�¼��������¼ID ?
		view.getSelector().add("costEntry.costAmount");
        view.getSelector().add("costEntry.costAccount.longNumber");
//		view.getSorter().add(new SorterItemInfo("versionNumber"));
		AimCostCollection aimCosts = AimCostFactory.getLocalInstance(ctx).getAimCostCollection(view);
		Map costEntrysMap = new HashMap();
		if (aimCosts != null && aimCosts.size() > 0) {
			for (int i = 0; i < aimCosts.size(); i++) {
				AimCostInfo aimCost = aimCosts.get(i);
				String id = aimCost.getId().toString();
				if(aimCost.isIsLastVersion()){
					reverseId = id; 
				}
				if (aimCost.getCostEntry() == null) {
					continue;
				}
				for (int j = 0; j < aimCost.getCostEntry().size(); j++) {
					CostEntryInfo costEntry = aimCost.getCostEntry().get(j);
					String key = id + costEntry.getCostAccount().getLongNumber();
					if(costEntrysMap.containsKey(key)){
						BigDecimal costAmount = (BigDecimal)costEntrysMap.get(key);
						costAmount = FDCHelper.add(costAmount, costEntry.getCostAmount());
						costEntrysMap.put(key, costAmount);
					}else{
						costEntrysMap.put(key, costEntry.getCostAmount());
					}
				}
			}
		} else {
			aimCosts = new AimCostCollection();
			aimCosts.add(new AimCostInfo());
		}
		retValue.put("aimCosts", aimCosts);
		retValue.put("costEntrysMap", costEntrysMap);
		
		this._updateProjectsCost(ctx, leafPrjIDs);
		
		// ��̬�ɱ�,Ŀ��ɱ�
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.appendSql("select fcostAcctLgNumber as acctNumber,  ");
    	builder.appendSql(" sum(FDynCostAmt) as dynCost ");
    	builder.appendSql(" from T_AIM_DynCostSnapShot where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("fprojectid", leafPrjIDs.toArray());
    	builder.appendSql(" group by fcostAcctLgNumber ");
    	IRowSet rs=builder.executeQuery();
    	RetValue costValues = new RetValue();
    	// �������հ汾Ŀ��ɱ�
		BigDecimal lastMeasureAmt = FDCHelper.ZERO;
		// �޶����հ汾Ŀ��ɱ�
		BigDecimal lastReverseAmt = FDCHelper.ZERO;
    	try{
			while(rs.next()){
				RetValue costValue = new RetValue();
				String acctNumber=rs.getString("acctNumber").replace('.', '!');
				BigDecimal dynCost = rs.getBigDecimal("dynCost");
				costValue.setBigDecimal("dynCost", dynCost);
				lastMeasureAmt = FDCHelper.toBigDecimal(measureEntrysMap.get(measureId+acctNumber));
				lastReverseAmt = FDCHelper.toBigDecimal(costEntrysMap.get(reverseId+acctNumber));
				// Ŀ��ɱ��޶�-Ŀ��ɱ�����仯����=(Ŀ��ɱ��޶����һ�汾��Ŀ��ɱ�-Ŀ��ɱ��������һ�汾��Ŀ��ɱ�����)/Ŀ��ɱ��������һ�汾��Ŀ��ɱ�����
				BigDecimal difAimCost = FDCHelper.subtract(lastReverseAmt, lastMeasureAmt);
				if(FDCHelper.toBigDecimal(lastMeasureAmt).signum()!=0){
					costValue.setBigDecimal("rate_aimCost", FDCHelper.divide(difAimCost, lastMeasureAmt, 4, BigDecimal.ROUND_HALF_UP));
				}
				// ��̬�ɱ�-Ŀ��ɱ��޶��仯����=(��̬�ɱ�-Ŀ��ɱ��޶����һ�汾��Ŀ��ɱ�)/Ŀ��ɱ��޶����һ�汾��Ŀ��ɱ�
				BigDecimal difReverse = FDCHelper.subtract(dynCost, lastReverseAmt);
				if(FDCHelper.toBigDecimal(lastReverseAmt).signum()!=0){
					costValue.setBigDecimal("rate_dyn_reverse", FDCHelper.divide(difReverse, lastReverseAmt, 4, BigDecimal.ROUND_HALF_UP));
				}
				// ��̬�ɱ�-Ŀ��ɱ�����仯����=(��̬�ɱ�-Ŀ��ɱ��������һ�汾��Ŀ��ɱ�����)/Ŀ��ɱ��������һ�汾��Ŀ��ɱ�����
				BigDecimal difMeasure = FDCHelper.subtract(dynCost, lastMeasureAmt);
				if(FDCHelper.toBigDecimal(lastMeasureAmt).signum()!=0){
					costValue.setBigDecimal("rate_dyn_measure", FDCHelper.divide(difMeasure, lastMeasureAmt, 4, BigDecimal.ROUND_HALF_UP));
				}
				costValues.put(acctNumber, costValue);
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("costValues", costValues);
    	
    	/***
    	 * ��̬���ָ��
    	 */
    	RetValue areaValue = new RetValue();
    	builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select ind_e.fapportiontypeid,sum(ind_e.findexvalue) as findexvalue from t_fdc_projectindexdata ind inner join t_fdc_projectindexdataentry ind_e on ind.fid=ind_e.fparentid");
		builder.appendSql(" where ind.fislatestver=1 and ");
		builder.appendSql(" ind.fproducttypeid is null and ");
		builder.appendParam("ind.fprojectstage",ProjectStageEnum.DYNCOST_VALUE);
		builder.appendSql(" and ");
		builder.appendParam("ind.fprojororgid",leafPrjIDs.toArray());
		builder.appendSql("  group by ind_e.fapportiontypeid ");
		
		rs=builder.executeQuery();
    	try{
    		while(rs.next()){
    			String key = rs.getString("fapportiontypeid");
    			areaValue.put(key, rs.getBigDecimal("findexvalue"));
    		}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("areaValue", areaValue);
    	
		TimeTools.getInstance().msValuePrintln("------------ end ----------");
		return retValue;
	}
  
	protected RetValue _getDynCostInfo(Context ctx, ParamValue param)
			throws BOSException, EASBizException {

		if(param==null){
    		throw new NullPointerException("bad param!");
    	}
		String lang = ctx.getLocale().getLanguage();
		Set accountIds = (Set) param.get("accountIds");
		String selectObjID = (String)param.get("selectObjID");		
		Set leafPrjIDs     = (HashSet)param.get("leafPrjIDs");
		if(leafPrjIDs==null||leafPrjIDs.size()==0||accountIds==null || accountIds.size()==0){
			throw new NullPointerException("bad param!");
		}
		
		this._updateProjectsCost(ctx, leafPrjIDs);
		
//		boolean selectObjIsPrj = ((Boolean)param.get("selectObjIsPrj")).booleanValue();
		RetValue retValue = new RetValue();
		/***
		 * 1.ȡ��Ŀ
		 */
		EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("id");
    	view.getSelector().add("level");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("name");
    	view.getSelector().add("isLeaf");
    	view.setFilter(new FilterInfo());
    	//TODO 
//    	if(selectObjIsPrj)
//    		view.getFilter().appendFilterItem("curProject.id", selectObjID);
//    	else
//    		view.getFilter().appendFilterItem("fullOrgUnit.id", selectObjID);
//    	view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
    	view.getFilter().getFilterItems().add(new FilterItemInfo("id", accountIds,CompareType.INCLUDE));
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	CostAccountCollection c=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
    	retValue.put("CostAccountCollection", c);
    	//�����Ŀ����
    	int maxLevel=0;
    	Set numberSet=new HashSet();
    	for(int i=0;i<c.size();i++){
    		CostAccountInfo info = c.get(i);
			if(info.getLevel()>maxLevel){
    			maxLevel=info.getLevel();
    		}
			numberSet.add(info.getLongNumber());
    	}
    	retValue.setInt("maxLevel", maxLevel);
    	/****
    	 * 2.ȡ��Ŀ�ĸ��ɱ���Ϣ
    	 */
    	
    	//TODO 
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.clear();
//    	builder.appendSql("select fcostAcctLgNumber as acctNumber,  ");
//    	builder.appendSql(" sum(FUnContractCostAmt) as unContractCostAmt,");
//    	builder.appendSql(" sum(FSoFarHasAmt) as soFarHasAmt,");
//    	builder.appendSql(" sum(FSoFarToAmt) as soFarToAmt,");
//    	builder.appendSql(" sum(FDynCostAmt) as dynCost,");
//    	builder.appendSql(" sum(FAimCostAmt) as aimCost,");
//    	builder.appendSql(" sum(FDiffAmt) as diffAmt ");
//    	builder.appendSql(" from T_AIM_DynCostSnapShot shot where fsavedType=? and ");
    	builder.appendSql("select fcostAcctLgNumber as acctNumber,  ");
    	builder.appendSql(" sum(FUnSettSignAmt) as unSettSignAmt,");
    	builder.appendSql(" sum(FSettSignAmt) as settSignAmt,");
    	builder.appendSql(" sum(FSettAdjustAmt) as settAdjustAmt,");
    	builder.appendSql(" sum(FUnContractCostAmt) as unContractCostAmt,");
    	builder.appendSql(" sum(FSoFarHasAmt) as soFarHasAmt,");
    	builder.appendSql(" sum(FCostPayedAmt) as costPayedAmt,");
    	builder.appendSql(" sum(FPayedAmt) as payedAmt,");
    	builder.appendSql(" sum(FSoFarToAmt) as soFarToAmt,");
    	builder.appendSql(" sum(FDynCostAmt) as dynCost,");
    	builder.appendSql(" sum(FAimCostAmt) as aimCost,");
    	builder.appendSql(" sum(FDiffAmt) as diffAmt ");
    	builder.appendSql(" from T_AIM_DynCostSnapShot shot where fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	//TODO acct
    	
    	builder.appendParam("fprojectid", leafPrjIDs.toArray());
    	builder.appendSql(" and ");
    	builder.appendParam("fcostAccountId", accountIds.toArray());
    	builder.appendSql(" group by fcostAcctLgNumber ");
    	IRowSet rowSet=builder.executeQuery();
    	RetValue costValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("acctNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, costValues);
//				//�Ǻ�ͬ�Գɱ�
//				subRet.setBigDecimal("unContractCostAmt", FDCNumberHelper.add(subRet.getBigDecimal("unContractCostAmt"), rowSet.getBigDecimal("unContractCostAmt")));
//				//Ŀǰ�ѷ���
//				subRet.setBigDecimal("soFarHasAmt", FDCNumberHelper.add(subRet.getBigDecimal("soFarHasAmt"), rowSet.getBigDecimal("soFarHasAmt")));
//				//��̬�ɱ�
//    			subRet.setBigDecimal("dynCost", FDCNumberHelper.add(subRet.getBigDecimal("dynCost"), rowSet.getBigDecimal("dynCost")));
//    			//Ŀ��ɱ�
//    			subRet.setBigDecimal("aimCost", FDCNumberHelper.add(subRet.getBigDecimal("aimCost"), rowSet.getBigDecimal("aimCost")));
//    			//����
//				subRet.setBigDecimal("diffAmt", FDCNumberHelper.add(subRet.getBigDecimal("diffAmt"), rowSet.getBigDecimal("diffAmt")));
				subRet.setBigDecimal("unSettSignAmt", FDCNumberHelper.add(subRet.getBigDecimal("unSettSignAmt"), rowSet.getBigDecimal("unSettSignAmt")));
				//�ѽ����ͬǩԼ���
				subRet.setBigDecimal("settSignAmt", FDCNumberHelper.add(subRet.getBigDecimal("settSignAmt"), rowSet.getBigDecimal("settSignAmt")));
				//�������
				subRet.setBigDecimal("settAdjustAmt", FDCNumberHelper.add(subRet.getBigDecimal("settAdjustAmt"), rowSet.getBigDecimal("settAdjustAmt")));
				//�Ǻ�ͬ�Գɱ�
				subRet.setBigDecimal("unContractCostAmt", FDCNumberHelper.add(subRet.getBigDecimal("unContractCostAmt"), rowSet.getBigDecimal("unContractCostAmt")));
				//Ŀǰ�ѷ���
				subRet.setBigDecimal("soFarHasAmt", FDCNumberHelper.add(subRet.getBigDecimal("soFarHasAmt"), rowSet.getBigDecimal("soFarHasAmt")));
				
				//��ʵ��
				subRet.setBigDecimal("costPayedAmt", FDCNumberHelper.add(subRet.getBigDecimal("costPayedAmt"), rowSet.getBigDecimal("costPayedAmt")));
				//�Ѹ���
				subRet.setBigDecimal("payedAmt", FDCNumberHelper.add(subRet.getBigDecimal("payedAmt"), rowSet.getBigDecimal("payedAmt")));
				//Ŀǰ������
				subRet.setBigDecimal("soFarToAmt", FDCNumberHelper.add(subRet.getBigDecimal("soFarToAmt"), rowSet.getBigDecimal("soFarToAmt")));
				
				//��̬�ɱ�
    			subRet.setBigDecimal("dynCost", FDCNumberHelper.add(subRet.getBigDecimal("dynCost"), rowSet.getBigDecimal("dynCost")));
    			//Ŀ��ɱ�
    			subRet.setBigDecimal("aimCost", FDCNumberHelper.add(subRet.getBigDecimal("aimCost"), rowSet.getBigDecimal("aimCost")));
    			//����
				subRet.setBigDecimal("diffAmt", FDCNumberHelper.add(subRet.getBigDecimal("diffAmt"), rowSet.getBigDecimal("diffAmt")));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("costValues", costValues);
    	
    	/***
    	 * 3.��̬���ָ��
    	 */
    	RetValue areaValue = new RetValue();
    	builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select ind_e.fapportiontypeid,sum(ind_e.findexvalue) as findexvalue from t_fdc_projectindexdata ind inner join t_fdc_projectindexdataentry ind_e on ind.fid=ind_e.fparentid");
		builder.appendSql(" where ind.fislatestver=1 and ");
		builder.appendSql(" ind.fproducttypeid is null and ");
		builder.appendParam("ind.fprojectstage",ProjectStageEnum.DYNCOST_VALUE);
		builder.appendSql(" and ");
		//TODO 
		builder.appendParam("ind.fprojororgid",leafPrjIDs.toArray());
		builder.appendSql("  group by ind_e.fapportiontypeid ");
		
		rowSet=builder.executeQuery();
    	try{
    		while(rowSet.next()){
    			String key = rowSet.getString("fapportiontypeid");
    			areaValue.put(key, rowSet.getBigDecimal("findexvalue"));
    		}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("areaValue", areaValue);
    	/***
    	 * 4.ȡ�������
    	 */
    	ChangeTypeCollection changeTypeCollection = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection("select * where isEnabled=1");
    	retValue.put("ChangeTypeCollection", changeTypeCollection);
    	
    	
    	/***
    	 * 5.ȡ�����¼
    	 */
    	
    	builder.clear();
    	builder.appendSql("select fcostAcctLgNumber as acctNumber,entry.FChangeTypeId as changeTypeID , ");
    	builder.appendSql(" sum(FUnSettleAmt) as unSettleAmt,");
    	builder.appendSql(" sum(FSettleAmt) as settleAmt");
    	builder.appendSql(" from T_AIM_DynCostSnapShot shot  inner join T_AIM_DynCostSnapShotSettEntry entry on shot.FID=entry.FParentID where shot.fsavedType=? and ");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.appendParam("shot.fprojectid", leafPrjIDs.toArray());
    	builder.appendSql(" and ");
    	builder.appendParam("shot.FCostAccountId", accountIds.toArray());
    	builder.appendSql(" group by fcostAcctLgNumber,entry.FChangeTypeId ");
    	rowSet=builder.executeQuery();
    	RetValue settEntryValues = new RetValue();
    	try{
			while(rowSet.next()){
				String acctNumber=rowSet.getString("acctNumber");
				RetValue subRet=getRowRetValue(acctNumber, numberSet, settEntryValues);
				String key = rowSet.getString("changeTypeID");
				//δ�����ͬǩԼ���
				subRet.setBigDecimal(key+"unSettleAmt", FDCNumberHelper.add(subRet.getBigDecimal(key+"unSettleAmt"), rowSet.getBigDecimal("unSettleAmt")));
				//�ѽ����ͬǩԼ���
				subRet.setBigDecimal(key+"settleAmt", FDCNumberHelper.add(subRet.getBigDecimal(key+"settleAmt"), rowSet.getBigDecimal("settleAmt")));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("settEntryValues", settEntryValues);
    	
		return retValue;
	}

	protected RetValue _getAimCostDynInfo(Context ctx, ParamValue param) throws BOSException, EASBizException {
		if(param==null){
    		throw new NullPointerException("bad param!");
    	}
    	String prjId=param.getString("prjId");
    	String yearFrom=param.getString("yearFrom");
    	String monthFrom=param.getString("monthFrom");
    	String yearTo=param.getString("yearTo");
    	String monthTo=param.getString("monthTo");
    	String fromDate=yearFrom+(new Integer(monthFrom).intValue()>=10?monthFrom:"0"+monthFrom);
    	
    	boolean isDebug = false;
    	
    	if(prjId==null || yearFrom==null|| monthFrom==null|| yearTo==null||monthTo==null){
    		throw new NullPointerException("bad param!");
    	}
    	RetValue retValue=new RetValue();
    	
    	//�Ƿ���ڳɱ��仯������仯�������µ�����
		Set prjSet=new HashSet();
		prjSet.add(prjId);
		//ȡ�ɱ��仯�˵Ľ��б���
		prjSet=ProjectCostChangeLogFactory.getLocalInstance(ctx).getChangePrjs(prjSet);
		saveShot(ctx, prjSet);
		
  	    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	//��ͬ
    	builder.clear();
    	/* ���д�Ĵ��룬��ʱע�ͣ�
    	builder.appendSql("select bill.FID contractId,entry.FCostAccountID acctId,bill.FPeriod period,sum(entry.FAmount) amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join (select FID,case when month(fbookeddate)>=10 then to_char(year(fbookeddate)||month(fbookeddate)) else year(fbookeddate)||'0'||month(fbookeddate) end as FPeriod from T_CON_ContractBill) bill on bill.FID=head.FCostBillID  \n");
		builder.appendSql("where FCostBillType='CONTRACTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? \n");
		builder.appendSql("group by bill.FID,entry.FCostAccountID,bill.FPeriod ");
		
		*/
    	
    	/* ��SQL���Աȣ�����������ȡ�������Ƿ�һ��
		���д��builder���ɵ�SQL��		
			select bill.FID contractId,entry.FCostAccountID acctId,bill.FPeriod period,sum(entry.FAmount) amount from T_AIM_CostSplitEntry entry  
			inner join T_AIM_CostSplit head on entry.FParentId=head.FId  
			inner join (select FID,case when month(fbookeddate)>=10 then to_char(year(fbookeddate)||month(fbookeddate)) else year(fbookeddate)||'0'||month(fbookeddate) end as FPeriod from T_CON_ContractBill) bill on bill.FID=head.FCostBillID  
			where FCostBillType='CONTRACTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid='c14xpuUTSSWdMOpipve5efnl6Ss=' 
			group by bill.FID,entry.FCostAccountID,bill.FPeriod 
		
		Owen_wen�޸ĺ��builder���ɵ�SQL��		
			select bill.FID contractId,entry.FCostAccountID acctId, bill.Pyear Pyear, bill.Pmonth Pmonth, sum(entry.FAmount) amount from T_AIM_CostSplitEntry entry  
			inner join T_AIM_CostSplit head on entry.FParentId=head.FId  
			inner join (select FID, year(FBookedDate) as Pyear,month(FBookedDate) as Pmonth from T_CON_ContractBill) bill on bill.FID=head.FCostBillID  
			where FCostBillType = 'CONTRACTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? 
			group by bill.FID, entry.FCostAccountID, bill.Pyear,bill.Pmonth 
		*/
    	
    	// added by Owen_wen Ϊ����DB2���ݣ���ƴ�����£�ֱ��ȡ�����·ŵ�group by��
		builder.appendSql("select bill.FID contractId,entry.FCostAccountID acctId, bill.Pyear Pyear, bill.Pmonth Pmonth, sum(entry.FAmount) amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join (select FID, year(FBookedDate) as Pyear,month(FBookedDate) as Pmonth from T_CON_ContractBill) bill on bill.FID=head.FCostBillID  \n");
		builder.appendSql("where FCostBillType = 'CONTRACTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? \n");
		builder.appendSql("group by bill.FID, entry.FCostAccountID, bill.Pyear,bill.Pmonth ");
		
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		Map conSplits = new HashMap();
		Map conBills = new HashMap();
		Set conCounts = null;
		//�ϼ�
		Set conTotalCounts = new HashSet();
		Set conIds = new HashSet();
		//ÿ��
		Set conPeriodTotalCounts=new HashSet();
		//
		Set conTotalCount = new HashSet();
		conBills.put("conTotalCount", conTotalCount);
		try{
			if(isDebug){
				System.out.println(builder.getTestSql());
			}
			while(rowSet.next()){
				String acctId=rowSet.getString("acctId");
				String contractId=rowSet.getString("contractId");
				String period= getPeriod(rowSet);
				BigDecimal amount = rowSet.getBigDecimal("amount");
				String key = acctId + contractId+period ;
				String fromKey = acctId + contractId+fromDate ;
//				String amtPeriodKey = period+"amt";
				String acctConKey = acctId + contractId;
				String conPeriodTotalKey = period;
				String conFromDateTotalKey = fromDate;
				String acctConTotalKey = acctId + contractId+"total";
				if(period.compareTo(fromDate)>0){
					//��Ŀÿ�ڽ��
					conSplits.put(key, FDCHelper.add(conSplits.get(key), amount));
					//�ϼ���
					if(!conTotalCount.contains(contractId)){
						conTotalCount.add(contractId);
					}
					
					//�ϼƷ�������Ŀ
					if(!conBills.containsKey(acctConTotalKey)){
						conTotalCounts=new HashSet();
						conTotalCounts.add(contractId);
						conBills.put(acctConTotalKey, conTotalCounts);
					}else{
						conTotalCounts = (Set)conBills.get(acctConTotalKey);
						conTotalCounts.add(contractId);
						conBills.put(acctConTotalKey, conTotalCounts);
					}
					//ÿ�ںϼƷ���
					if(!conBills.containsKey(conPeriodTotalKey)){
						conPeriodTotalCounts=new HashSet();
						conPeriodTotalCounts.add(contractId);
						conBills.put(conPeriodTotalKey, conPeriodTotalCounts);
					}else{
						conPeriodTotalCounts = (Set)conBills.get(conPeriodTotalKey);
						conPeriodTotalCounts.add(contractId);
						conBills.put(conPeriodTotalKey, conPeriodTotalCounts);
					}
					
					//��Ŀÿ�ڷ���=1
					conCounts = (Set)conBills.get(key);
					if(conCounts==null){
						conCounts = new HashSet();
						conBills.put(key, conCounts);
					}
					if(!conCounts.contains(contractId)){
						conCounts.add(contractId);
					}
//					if(!conBills.containsKey(amtPeriodKey)){
//						conBills.put(amtPeriodKey, FDCHelper.add(conBills.get(amtPeriodKey), amount));
//					}
					//�ϼƽ��
					conSplits.put(acctConTotalKey,FDCHelper.add(conSplits.get(acctConTotalKey), amount));
				}else{
					//��Ŀÿ�ڽ��
					conSplits.put(fromKey,FDCHelper.add(conSplits.get(fromKey), amount));
								
					conPeriodTotalCounts = (Set)conBills.get(conFromDateTotalKey);
					if(conPeriodTotalCounts==null){
						conPeriodTotalCounts = new HashSet();
						conBills.put(conFromDateTotalKey, conPeriodTotalCounts);
					}
					if(!conPeriodTotalCounts.contains(contractId)){
						conPeriodTotalCounts.add(contractId);
					}
					
				}
				if(!conIds.contains(contractId)){
					conIds.add(contractId);
				}
				conSplits.put(acctConKey,FDCHelper.add(conSplits.get(acctConKey), amount));
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
		retValue.put("conSplits", conSplits);
		retValue.put("conBills", conBills);
		
		/**
		 * �������������֣�
		 */
		builder.clear();
		/* ���д�Ĵ��룬��ʱע�ͣ�
    	builder.appendSql("select change.FID changeId,change.FContractBillID contractId,entry.FCostAccountID acctId,split.FPeriod period,type.FID typeId,sum(entry.FAmount) amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FID  \n");
		builder.appendSql("inner join T_FDC_ChangeType type on type.FID=change.FChangeTypeID \n");
		builder.appendSql("inner join (select FID,FContractChangeID,case when month(fcreatetime)>=10 then to_char(year(fcreatetime)||month(fcreatetime)) else year(fcreatetime)||'0'||month(fcreatetime) end as FPeriod from T_CON_ConChangeSplit) split on change.FID=split.FContractChangeID  \n");
		builder.appendSql("where FCostBillType='CNTRCHANGESPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? \n");
		builder.appendSql("group by change.FID,change.FContractBillID,entry.FCostAccountID,split.FPeriod,type.FID \n");//���ɱ���Ŀ���ͬ��Ŀ��Ϸ���
		*/
		
		// added by Owen_wen Ϊ����DB2���ݣ���ƴ�����£�ֱ��ȡ�����·ŵ�group by��
		builder.appendSql("select change.FID changeId,change.FContractBillID contractId,entry.FCostAccountID acctId, split.Pyear Pyear, split.Pmonth Pmonth, type.FID typeId,sum(entry.FAmount) amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FID  \n");
		builder.appendSql("inner join T_FDC_ChangeType type on type.FID=change.FChangeTypeID \n");
		builder.appendSql("inner join (select FID,FContractChangeID, year(FCreateTime) Pyear, month(FCreateTime) Pmonth from T_CON_ConChangeSplit) split on change.FID=split.FContractChangeID  \n");
		builder.appendSql("where FCostBillType='CNTRCHANGESPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? \n");
		builder.appendSql("group by change.FID,change.FContractBillID,entry.FCostAccountID,split.Pyear, split.PMonth,type.FID \n");//���ɱ���Ŀ���ͬ��Ŀ��Ϸ���
		builder.addParam(prjId);
		rowSet=builder.executeQuery();
		Map changeSplits = new HashMap();
		Map changeBills = new HashMap();
		Set changeCounts = null;
		Set changeTotalCounts=new HashSet();
		Set changePeriodTotalCounts=new HashSet();
		Set changeTypeTotalCounts=new HashSet();
		try{
			if(isDebug){
				System.out.println(builder.getTestSql());
			}
			while(rowSet.next()){
				String acctId=rowSet.getString("acctId");
				String contractId=rowSet.getString("contractId");
				String period = getPeriod(rowSet);				
				BigDecimal amount = rowSet.getBigDecimal("amount");
				String changeId = rowSet.getString("changeId");
				String typeId = rowSet.getString("typeId");
				String key = acctId + contractId+period +typeId;
				String fromKey = acctId + contractId+fromDate +typeId;
				//��Ŀ��ͬ�ϵı������
				String numKey = acctId +contractId+fromDate+typeId;
				String totalKey = acctId+contractId+typeId;
				String changePeriodTotalKey = period+typeId;
				String changeFromDateTotalKey = fromDate+typeId;
				
				if(period.compareTo(fromDate)>0){
					//��Ŀ���
					changeSplits.put(key, FDCHelper.add(changeSplits.get(key), amount));
					//�ϼƷ���
					if(!changeBills.containsKey(totalKey)){
						changeTotalCounts=new HashSet();
						changeTotalCounts.add(changeId);
						changeBills.put(totalKey, changeTotalCounts);
					}else{
						changeTotalCounts = (Set)changeBills.get(totalKey);
						changeTotalCounts.add(changeId);
						changeBills.put(totalKey, changeTotalCounts);
					}
					//�ϼƷ���
					if(!changeBills.containsKey(changePeriodTotalKey)){
						changePeriodTotalCounts=new HashSet();
						changePeriodTotalCounts.add(changeId);
						changeBills.put(changePeriodTotalKey, changePeriodTotalCounts);
					}else{
						changePeriodTotalCounts = (Set)changeBills.get(changePeriodTotalKey);
						changePeriodTotalCounts.add(changeId);
						changeBills.put(changePeriodTotalKey, changePeriodTotalCounts);
					}
					
					//�ϼƽ��
					changeSplits.put(totalKey, FDCHelper.add(changeSplits.get(totalKey), amount));
					//����
					changeCounts = (Set)changeBills.get(key);
					if(changeCounts==null){
						changeCounts = new HashSet();
						changeBills.put(key, changeCounts);
					}
					if(!changeCounts.contains(changeId)){
						changeCounts.add(changeId);
					}
					changeSplits.put(period, FDCHelper.add(changeSplits.get(period), amount));
				}else{
					changeSplits.put(fromKey,FDCHelper.add(changeSplits.get(fromKey), amount));
					
					changeCounts = (Set)changeBills.get(numKey);
					if(changeCounts==null){
						changeCounts = new HashSet();
						changeBills.put(numKey, changeCounts);
					}
					if(!changeCounts.contains(changeId)){
						changeCounts.add(changeId);
					}
					
					changePeriodTotalCounts = (Set)changeBills.get(changeFromDateTotalKey);
					if(changePeriodTotalCounts==null){
						changePeriodTotalCounts = new HashSet();
						changeBills.put(changeFromDateTotalKey, changePeriodTotalCounts);
					}
					if(!changePeriodTotalCounts.contains(changeId)){
						changePeriodTotalCounts.add(changeId);
					}
					
					changeSplits.put(fromDate, FDCHelper.add(changeSplits.get(fromDate), amount));
				}
				if(!conIds.contains(contractId)){
					conIds.add(contractId);
				}
				if(!changeBills.containsKey(typeId)){
					changeTypeTotalCounts=new HashSet();
					changeTypeTotalCounts.add(changeId);
					changeBills.put(typeId, changeTypeTotalCounts);
				}else{
					changeTypeTotalCounts=(Set)changeBills.get(typeId);
					changeTypeTotalCounts.add(changeId);
					changeBills.put(typeId, changeTypeTotalCounts);
				}
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
		retValue.put("changeSplits", changeSplits);
		retValue.put("changeBills", changeBills);
		
		
    	/***
		 * ��ͬ
		 */
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (conIds.size() > 0) {
			Set contractIds = new HashSet();
			filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", conIds, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.getSelector().add(new SelectorItemInfo("costAccount.id"));
			view.getSelector().add(new SelectorItemInfo("parent.contractBill.id"));
			view.getSelector().add(new SelectorItemInfo("parent.contractBill.bookedDate"));
			view.getSelector().add(new SelectorItemInfo("parent.contractBill.amount"));
			view.getSelector().add(new SelectorItemInfo("parent.contractBill.number"));
			view.getSelector().add(new SelectorItemInfo("parent.contractBill.name"));
			ContractCostSplitEntryCollection splitEntrys = ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(view);

			if (splitEntrys.size() != 0) {
				for (int i = 0; i < splitEntrys.size(); i++) {
					ContractBillInfo conInfo = splitEntrys.get(i).getParent().getContractBill();
					if(!contractIds.contains(conInfo.getId().toString())){
						contractIds.add(conInfo.getId().toString());
						retValue.put("allConAmt", FDCHelper.add(retValue.get("allConAmt"), conInfo.getAmount()));
					}
					if (!retValue.containsKey(splitEntrys.get(i).getCostAccount().getId().toString())) {
						ContractBillCollection infos = new ContractBillCollection();
						infos.add(splitEntrys.get(i).getParent().getContractBill());
						retValue.put(splitEntrys.get(i).getCostAccount().getId().toString(), infos);
					} else {
						ContractBillCollection infos = (ContractBillCollection) retValue.get(splitEntrys.get(i).getCostAccount().getId().toString());
						if (!infos.contains(splitEntrys.get(i).getParent().getContractBill())) {
							infos.add(splitEntrys.get(i).getParent().getContractBill());
							retValue.put(splitEntrys.get(i).getCostAccount().getId().toString(), infos);
						}
					}
				}
			}
			//���
			filter=new FilterInfo();
			view=new EntityViewInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", conIds, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.getSelector().add(new SelectorItemInfo("costAccount.id"));
			view.getSelector().add(new SelectorItemInfo("parent.contractBill.id"));
			view.getSelector().add(new SelectorItemInfo("parent.contractBill.bookedDate"));
			view.getSelector().add(new SelectorItemInfo("parent.contractBill.amount"));
			view.getSelector().add(new SelectorItemInfo("parent.contractBill.number"));
			view.getSelector().add(new SelectorItemInfo("parent.contractBill.name"));
			ConChangeSplitEntryCollection changeSplitEntrys = ConChangeSplitEntryFactory.getLocalInstance(ctx).getConChangeSplitEntryCollection(view);

			if (changeSplitEntrys.size() != 0) {
				for (int i = 0; i < changeSplitEntrys.size(); i++) {
					ContractBillInfo conInfo = changeSplitEntrys.get(i).getParent().getContractBill();
					if(!contractIds.contains(conInfo.getId().toString())){
						contractIds.add(conInfo.getId().toString());
						retValue.put("allConAmt", FDCHelper.add(retValue.get("allConAmt"), conInfo.getAmount()));
					}
					if (!retValue.containsKey(changeSplitEntrys.get(i).getCostAccount().getId().toString())) {
						ContractBillCollection infos = new ContractBillCollection();
						infos.add(changeSplitEntrys.get(i).getParent().getContractBill());
						retValue.put(changeSplitEntrys.get(i).getCostAccount().getId().toString(), infos);
					} else {
						ContractBillCollection infos = (ContractBillCollection) retValue.get(changeSplitEntrys.get(i).getCostAccount().getId().toString());
						if (!infos.contains(changeSplitEntrys.get(i).getParent().getContractBill())) {
							infos.add(changeSplitEntrys.get(i).getParent().getContractBill());
							retValue.put(changeSplitEntrys.get(i).getCostAccount().getId().toString(), infos);
						}
					}
				}
			}
		}
		
		builder.clear();
    	builder.appendSql("select FCostAccountId as costAccountId, \n");
    	builder.appendSql("sum(FAimCostAmt) as aimCost, \n");
    	builder.appendSql("sum(FSoFarHasAmt) as soFarHasAmt \n");
    	builder.appendSql("from T_AIM_DynCostSnapShot shot where fsavedType=? and fprojectid=? \n");
    	builder.addParam(CostMonthlySaveTypeEnum.ONLYONE_VALUE);
    	builder.addParam(prjId);
    	builder.appendSql("group by FCostAccountId ");
    	rowSet=builder.executeQuery();
    	Map aimCosts = new HashMap();
    	try{
    		if(isDebug){
				System.out.println(builder.getTestSql());
			}
			while(rowSet.next()){
				String acctId=rowSet.getString("costAccountId");
				//Ŀ��ɱ�
    			aimCosts.put(acctId+"aimCost", rowSet.getBigDecimal("aimCost"));
    			//Ŀ��ɱ�
    			aimCosts.put(acctId+"soFarHasAmt", rowSet.getBigDecimal("soFarHasAmt"));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}
    	retValue.put("aimCosts", aimCosts);
    	
    	//�ܽ������
    	builder.clear();
    	builder.appendSql("select pi.FTotalConstructArea from t_aim_planindex pi ");
    	builder.appendSql("inner join t_aim_measurecost mc on mc.fid=pi.fheadid ");
    	builder.appendSql("where mc.FProjectID=? and mc.FIsLastVersion=1 ");
    	builder.addParam(prjId);
    	rowSet = builder.executeQuery();
    	try{
    		if(isDebug){
				System.out.println(builder.getTestSql());
			}
			while(rowSet.next()){
				retValue.setBigDecimal("totalconstructArea", rowSet.getBigDecimal("FTotalConstructArea"));
			}
    	}catch(SQLException e){
    		throw new BOSException(e);
    	}

    	/**
    	 * �������
    	 */
    	view=new EntityViewInfo();
    	filter=new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	view.getSelector().add("id");
    	view.getSelector().add("name");
    	view.getSorter().add(new SorterItemInfo("number"));
    	ChangeTypeCollection changeTypes = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection(view);
    	retValue.put("changeTypes", changeTypes);

    	/**
    	 * ���ı�
    	 */
    	builder.clear();
    	/*���д�Ĵ��룬��ʱע��
    	builder.appendSql("select FCostAccountId,split.FPeriod,sum(entry.FAmount) amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CAS_PaymentBill bill on head.FCostBillID=bill.FID  \n");
		builder.appendSql("inner join (select FID,FPaymentBillID,case when month(fcreatetime)>=10 then to_char(year(fcreatetime)||month(fcreatetime)) else year(fcreatetime)||'0'||month(fcreatetime) end as FPeriod from T_FNC_PaymentSplit) split on bill.FID=split.FPaymentBillID  \n");
		builder.appendSql("where FCostBillType='NOTEXTCONSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? \n");
		builder.appendSql("group by fcostaccountid,split.FPeriod \n");//���ɱ���Ŀ���ͬ��Ŀ��Ϸ���
		*/
    	
    	// added by Owen_wen Ϊ����DB2���ݣ���ƴ�����£�ֱ��ȡ�����·ŵ�group by��
    	builder.appendSql("select FCostAccountId,split.Pyear, split.Pmonth,sum(entry.FAmount) amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CAS_PaymentBill bill on head.FCostBillID=bill.FID  \n");
		builder.appendSql("inner join (select FID,FPaymentBillID, year(fcreatetime) as Pyear, month(fcreatetime) Pmonth from T_FNC_PaymentSplit) split on bill.FID=split.FPaymentBillID  \n");
		builder.appendSql("where FCostBillType='NOTEXTCONSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? \n");
		builder.appendSql("group by fcostaccountid, split.Pyear, split.Pmonth \n");//���ɱ���Ŀ���ͬ��Ŀ��Ϸ���
		builder.addParam(prjId);
		rowSet=builder.executeQuery();
		Map noTextSplits = new HashMap();
		try{
			if(isDebug){
				System.out.println(builder.getTestSql());
			}
			while(rowSet.next()){
				String costaccountId=rowSet.getString("Fcostaccountid");
				String period = this.getPeriod(rowSet);
				BigDecimal amount = rowSet.getBigDecimal("amount");
				String key = costaccountId + period;
				String fromKey = costaccountId + fromDate;
				String totalKey =costaccountId+"total";
				if(period.compareTo(fromDate)>0){
					noTextSplits.put(key, FDCHelper.add(noTextSplits.get(key), amount));
					noTextSplits.put(totalKey, FDCHelper.add(noTextSplits.get(totalKey), amount));
					noTextSplits.put(period, FDCHelper.add(noTextSplits.get(period), amount));
				}else{
					//����
					noTextSplits.put(fromKey,FDCHelper.add(noTextSplits.get(fromKey), amount));
					noTextSplits.put(fromDate, FDCHelper.add(noTextSplits.get(fromDate), amount));
				}
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
		retValue.put("noTextSplits", noTextSplits);
		
		/**
    	 * ��ͬ����
    	 */
    	builder.clear();
    	/*���д�Ĵ��룬��ʱע��
    	builder.appendSql("select FCostAccountId,bill.FContractBillID,split.FPeriod,sum(entry.fpaidAmount) amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CAS_PaymentBill bill on head.FCostBillID=bill.FID  \n");
		builder.appendSql("inner join (select FID,FPaymentBillID,case when month(fcreatetime)>=10 then to_char(year(fcreatetime)||month(fcreatetime)) else year(fcreatetime)||'0'||month(fcreatetime) end as FPeriod from T_FNC_PaymentSplit) split on bill.FID=split.FPaymentBillID  \n");
		builder.appendSql("where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? \n");
		builder.appendSql("group by fcostaccountid,bill.FContractBillID,split.FPeriod \n");//���ɱ���Ŀ���ͬ��Ŀ��Ϸ���
		*/
    	
    	// added by Owen_wen Ϊ����DB2���ݣ���ƴ�����£�ֱ��ȡ�����·ŵ�group by��
		builder.appendSql("select FCostAccountId,bill.FContractBillID, split.Pyear, split.Pmonth, sum(entry.fpaidAmount) amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CAS_PaymentBill bill on head.FCostBillID=bill.FID  \n");
		builder.appendSql("inner join (select FID,FPaymentBillID, year(fcreatetime) Pyear, month(fcreatetime) Pmonth from T_FNC_PaymentSplit) split on bill.FID=split.FPaymentBillID  \n");
		builder.appendSql("where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? \n");
		builder.appendSql("group by fcostaccountid,bill.FContractBillID, split.Pyear, split.Pmonth \n");//���ɱ���Ŀ���ͬ��Ŀ��Ϸ���
		builder.addParam(prjId);
		rowSet=builder.executeQuery();
		Map paySplits = new HashMap();
		try{
			if(isDebug){
				System.out.println(builder.getTestSql());
			}
			while(rowSet.next()){
				String costaccountId=rowSet.getString("Fcostaccountid");
				String contractId = rowSet.getString("FContractBillID");
				String period = this.getPeriod(rowSet);
				BigDecimal amount = rowSet.getBigDecimal("amount");
				String key = costaccountId + contractId+period;
				String fromKey = costaccountId + contractId+fromDate;
				String totalKey = costaccountId+contractId;
				if(period.compareTo(fromDate)>0){
					paySplits.put(key, FDCHelper.add(paySplits.get(key), amount));
					paySplits.put(totalKey, FDCHelper.add(paySplits.get(totalKey), amount));
					paySplits.put(period, FDCHelper.add(paySplits.get(period), amount));
				}else{
					//����
					paySplits.put(fromKey,FDCHelper.add(paySplits.get(fromKey), amount));
					paySplits.put(fromDate, FDCHelper.add(paySplits.get(fromDate), amount));
				}
				paySplits.put("total", FDCHelper.add(paySplits.get("total"), amount));
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}
		retValue.put("paySplits", paySplits);
		
		//�깤
		retValue.put("completePrjAmts", getCompletePrjAmt(ctx,prjId,fromDate,conIds));
		
		builder.clear();
		builder.appendSql("select entry.FCostAccountID,entry.FChangeReason from T_AIM_MeasureEntry entry ");
		builder.appendSql("inner join T_AIM_MeasureCost head on head.FID=entry.FHeadID ");
		builder.appendSql("where head.FProjectID=? ");
		builder.addParam(prjId);
		builder.appendSql("order by entry.FCostAccountID ");
		rowSet=builder.executeQuery();
		Map changeReasons = new HashMap();
		StringBuffer sb = null;
		try {
			if(isDebug){
				System.out.println(builder.getTestSql());
			}
			while (rowSet.next()) {
				String acctId = rowSet.getString("FCostAccountID");
				String changeReason = rowSet.getString("FChangeReason");
				if(changeReason!=null){
				sb = (StringBuffer) changeReasons.get(acctId);
					if (sb == null) {
						sb = new StringBuffer();
					}
					changeReasons.put(acctId, sb.append(changeReason));
				}
				
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		retValue.put("changeReasons", changeReasons);
		builder.clear();
		
    	return retValue;
	}

	public Map getCompletePrjAmt(Context ctx, String prjId,String fromDate, Set idSet)throws BOSException, EASBizException{
		boolean isDebug=true;
		if(idSet.size()==0){
			return new HashMap();
		}
		String companyId = FDCHelper.getCurCompanyId(ctx, prjId);
		boolean isSeparate = FDCUtils.getDefaultFDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		boolean isSimpleFinancial = FDCUtils.getDefaultFDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		
		Map completeAmtMap=new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		//��ͬ�����ս��㲢�����ü�ģʽ����ɱ�һ�廯
		if(isSimpleFinancial){
			builder.clear();
			builder.appendSql("select payReq.FContractId, year(payReq.fcreatetime) Pyear, month(payReq.fcreatetime) Pmonth, payReq.FCompletePrjAmt as FCompletePrjAmt from T_CON_PayRequestBill  payReq ");
			builder.appendSql("inner join T_CON_ContractBill con on con.Fid=payReq.FContractId ");
			builder.appendSql("where payReq.FState='4AUDITTED'  and con.FHasSettled=1  ");
			builder.appendParam("and payReq.FContractId  ",idSet.toArray());
			IRowSet rowSet = builder.executeQuery();
			try {
				if(isDebug){
					System.out.println(builder.getTestSql());
				}
				while(rowSet.next()){
					String contractId = rowSet.getString("FContractId");
					String period = this.getPeriod(rowSet);
					BigDecimal completeAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("FCompletePrjAmt"),2) ;
					String key = contractId + period;
					String fromKey = contractId + fromDate;
					if(period.compareTo(fromDate)>0){
						completeAmtMap.put(key, FDCHelper.add(completeAmtMap.get(key), completeAmt));
						completeAmtMap.put(contractId, FDCHelper.add(completeAmtMap.get(contractId), completeAmt));
						//ÿ�ڵĺ�ͬ��
						completeAmtMap.put(period, FDCHelper.add(completeAmtMap.get(period), completeAmt));
					}else{
						completeAmtMap.put(fromKey, FDCHelper.add(completeAmtMap.get(fromKey), completeAmt));
//						completeAmtMap.put(contractId, FDCHelper.add(completeAmtMap.get(contractId), completeAmt));
						completeAmtMap.put(fromDate, FDCHelper.add(completeAmtMap.get(fromDate), completeAmt));
					}
					completeAmtMap.put(contractId+"total", FDCHelper.add(completeAmtMap.get(contractId+"total"), completeAmt));
					completeAmtMap.put("total", FDCHelper.add(completeAmtMap.get("total"), completeAmt));
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}else{//��ͬ�����ս��㲢��δ���ü�ģʽ����ɱ�һ�廯
			builder.clear();
			builder.appendSql("select FID, year(fcreatetime) Pyear, month(fcreatetime) Pmonth, FSettleAmt  from T_CON_ContractBill  ");
			builder.appendSql("where FHasSettled=1 and fstate='4AUDITTED'  ");
			builder.appendParam("and FID ",idSet.toArray());
			IRowSet rowSet = builder.executeQuery();
			try {
				if(isDebug){
					System.out.println(builder.getTestSql());
				}
				while(rowSet.next()){
					String contractId = rowSet.getString("FID");
					String period = this.getPeriod(rowSet);
					BigDecimal settlePrice = FDCHelper.toBigDecimal(rowSet.getBigDecimal("FSettleAmt"),2);
					String key = contractId + period;
					String fromKey = contractId + fromDate;
					if(period.compareTo(fromDate)>0){
						completeAmtMap.put(key, FDCHelper.add(completeAmtMap.get(key), settlePrice));
						completeAmtMap.put(contractId, FDCHelper.add(completeAmtMap.get(contractId), settlePrice));
						completeAmtMap.put(period, FDCHelper.add(completeAmtMap.get(period), settlePrice));
					}else{
						completeAmtMap.put(fromKey, FDCHelper.add(completeAmtMap.get(fromKey), settlePrice));
//						completeAmtMap.put(contractId, FDCHelper.add(completeAmtMap.get(contractId), settlePrice));
						completeAmtMap.put(fromDate, FDCHelper.add(completeAmtMap.get(fromDate), settlePrice));
					}
					completeAmtMap.put(contractId+"total", FDCHelper.add(completeAmtMap.get(contractId+"total"), settlePrice));
					completeAmtMap.put("total", FDCHelper.add(completeAmtMap.get("total"), settlePrice));
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		//��ͬδ���ս��㲢�ҹ�����ȷ�������븶������δ����
		if(!isSeparate){
			builder.clear();
			builder.appendSql("select FContractId, year(fcreatetime) Pyear, month(fcreatetime) Pmonth, FCompletePrjAmt as FCompletePrjAmt from T_CON_PayRequestBill ");
			builder.appendSql("where  FState = '4AUDITTED' ");
			builder.appendParam("and FContractId  ",idSet.toArray());
			IRowSet rowSet = builder.executeQuery();
			try {
				if(isDebug){
					System.out.println(builder.getTestSql());
				}
				while(rowSet.next()){
					String contractId = rowSet.getString("FContractId");
					String period = this.getPeriod(rowSet);
					BigDecimal  complateAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("FCompletePrjAmt"),2);
					String key = contractId + period;
					String fromKey = contractId + fromDate;
					if(period.compareTo(fromDate)>0){
						completeAmtMap.put(key, FDCHelper.add(completeAmtMap.get(key), complateAmt));
						completeAmtMap.put(contractId, FDCHelper.add(completeAmtMap.get(contractId), complateAmt));
						completeAmtMap.put(period, FDCHelper.add(completeAmtMap.get(period), complateAmt));
					}else{
						completeAmtMap.put(fromKey, FDCHelper.add(completeAmtMap.get(fromKey), complateAmt));
//						completeAmtMap.put(contractId, FDCHelper.add(completeAmtMap.get(contractId), complateAmt));
						completeAmtMap.put(fromDate, FDCHelper.add(completeAmtMap.get(fromDate), complateAmt));
					}
					completeAmtMap.put(contractId+"total", FDCHelper.add(completeAmtMap.get(contractId+"total"), complateAmt));
					completeAmtMap.put("total", FDCHelper.add(completeAmtMap.get("total"), complateAmt));
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}else{//��ͬδ���ս��㲢�ҹ�����ȷ�������븶�����̷���
			builder.clear();
			builder.appendSql("select FContractBillId, year(fcreatetime) Pyear, month(fcreatetime) Pmonth, FworkLoad as FworkLoad from T_FNC_WorkLoadConfirmBill ");
			builder.appendSql("where FState = '4AUDITTED' ");
			builder.appendParam("and FContractBillId ",idSet.toArray());
			IRowSet rowSet = builder.executeQuery();
			try {
				if(isDebug){
					System.out.println(builder.getTestSql());
				}
				while(rowSet.next()){
					String contractId = rowSet.getString("FContractBillId");
					String period = this.getPeriod(rowSet);
					BigDecimal  workLoad = FDCHelper.toBigDecimal(rowSet.getBigDecimal("FworkLoad"),2);
					String key = contractId + period;
					String fromKey = contractId + fromDate;
					if(period.compareTo(fromDate)>0){
						completeAmtMap.put(key, FDCHelper.add(completeAmtMap.get(key), workLoad));
						completeAmtMap.put(contractId, FDCHelper.add(completeAmtMap.get(contractId), workLoad));
						completeAmtMap.put(period, FDCHelper.add(completeAmtMap.get(period), workLoad));
					}else{
						completeAmtMap.put(fromKey, FDCHelper.add(completeAmtMap.get(fromKey), workLoad));
//						completeAmtMap.put(contractId, FDCHelper.add(completeAmtMap.get(contractId), workLoad));
						completeAmtMap.put(fromDate, FDCHelper.add(completeAmtMap.get(fromDate), workLoad));
					}
					completeAmtMap.put(contractId+"total", FDCHelper.add(completeAmtMap.get(contractId+"total"), workLoad));
					completeAmtMap.put("total", FDCHelper.add(completeAmtMap.get("total"), workLoad));
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		
		return completeAmtMap;
	}
	
	/**
	 * ��ȡrowSet�е�����£���ϳ��ڼ��ʽ����
	 */
	private String getPeriod(IRowSet rowSet) throws SQLException{
		String period_year = rowSet.getString("Pyear");
		String period_month = rowSet.getString("Pmonth");
		if (period_month.length()==1)
			period_month = "0" + period_month;
		return period_year + period_month;
	}
	
	//��ȡ��Լ��������ֵ�Ŀǰ������
	protected Map _getConOccurSplitListEntry(Context ctx, String probjectID)
			throws BOSException, EASBizException {
		Map cosAmount = new HashMap();
		CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(probjectID));
//		StringBuffer sql = new StringBuffer();
//		sql.append(" select (sum(FNeedAmt)+sum(FAdjAmt)) as amount ,FCostAccountID ");
//		sql.append(" from T_AIM_ConOccSplitListEntry  coslEntry ");
//		sql.append(" left join T_AIM_ConOccSplitList cosl on coslEntry.FParentID = cosl.FID ");
//		sql.append(" left join T_FDC_CurProject cup on cosl.FprojectID = cup.FID  ");
//		sql.append(" where ");
//		if(curProjectInfo.isIsLeaf()){
//			sql.append(" cosl.fprojectid  ='"+probjectID+"' ");
//		}else{
//			sql.append(" cup.fparentid  ='"+probjectID+"' ");
//		}
//		sql.append(" group by FCostAccountID ");
//		
//		IRowSet rowSet = DbUtil.executeQuery(ctx,sql.toString());
//		Map map = new HashMap();
//		try {
//			while (rowSet.next()) {
//				map.put(rowSet.getString("FCostAccountID"), rowSet.getBigDecimal("amount"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		String curprojectid = "";
		if(curProjectInfo.isIsLeaf()){
			curprojectid = "'"+curProjectInfo.getId()+"' ";
		}else{
			StringBuffer cupid = new StringBuffer();
			CurProjectCollection projectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("where parent = '"+probjectID+"'");
			for (int i = 0; i < projectCollection.size(); i++) {
				CurProjectInfo projectInfo = projectCollection.get(i);
				if(i > 0){
					cupid.append(",");
				}
				cupid.append("'"+projectInfo.getId()+"'");
			}
			curprojectid = cupid.toString();
		}
		

		//----------����֧��
		Map sxzcmap = new HashMap();
		StringBuffer sqlsx = new StringBuffer();
		sqlsx.append(" select detailData.FNeedSpending as amount ,detail.FCurProjectID as projectID ,pc.fname_l2 as pcname,pc.flongnumber as pcnumber "); 
		sqlsx.append(" from T_AIM_DynCostDetailData detailData ");         
		sqlsx.append(" left join T_AIM_DynCostDetail detail on detailData.FParentID = detail.FID "); 
		sqlsx.append(" left join T_CON_ProgrammingContract pc on detailData.FProgrammingContractID = pc.FID ");
		sqlsx.append(" inner join ( select max(fversion) as version,FCurProjectID as projectID from T_AIM_DynCostDetail where "); 
		sqlsx.append(" FCurProjectID  in ("+curprojectid+") "); 
		sqlsx.append(" and fstate = '4AUDITTED' group by FCurProjectID ) t1 on t1.projectID = detail.FCurProjectID "); 
		sqlsx.append(" and t1.version = detail.fversion "); 
		IRowSet rs = DbUtil.executeQuery(ctx,sqlsx.toString());
		try {
			while (rs.next()) {
				sxzcmap.put(rs.getString("projectID")+rs.getString("pcname")+rs.getString("pcnumber"), rs.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//---����֧���������---
		StringBuffer sqls = new StringBuffer();
		sqls.append(" select cosl.fprojectid as projectid ,cosl.FAdjAmount as AdjAmount ,pmmconcost.fcontractassign as contractassign, ");
		sqls.append(" pmmcon.famount as ghamoun ,coslEntry.FAimCost as AimCost,pmmconcost.fgoalcost as goalcost,pty.fname_l2 as ptyname,cosa.fid as cosfid, ");
		sqls.append(" cosa.Fname_l2 as cosaname,cosa.flongnumber as cosanumber,pmmcon.fname_l2 as pmmconname,pmmcon.flongnumber as pmmconnumber,coslEntry.FNeedAmt  as need ,coslEntry.FAdjAmt as adjamt ");
		sqls.append(" from T_AIM_ConOccSplitListEntry  coslEntry  "); 
		sqls.append(" left join T_AIM_ConOccSplitList cosl on coslEntry.FParentID = cosl.FID ");
		sqls.append(" left join  T_CON_ProgrammingContract pmmcon on cosl.FProgrammingContractID = pmmcon.FID  ");
		sqls.append(" left join  T_CON_ProgrammingContracCost pmmconcost on pmmcon.fid = pmmconcost.fcontractid and coslEntry.fcostaccountid = pmmconcost.fcostaccountid ");
		sqls.append(" left join T_CON_Programming prm on pmmcon.fProgrammingid = prm.fid ");
		sqls.append(" left join T_FDC_CostAccount cosa on coslEntry.FCostAccountID = cosa.fid ");
		sqls.append(" left join T_FDC_ProductType pty on coslEntry.fproductid = pty.fid ");
		sqls.append(" where  cosl.fprojectid  in ("+curprojectid+") ");
//		sqls.append(" and cosa.flongnumber = '4001 ");//and flongnumber = '4001!03!02!01!01!06!01' and pmmconcost.fcostaccountid = '3WEoQPCKTRa7iB1faTu6WIQj/24=' ");
		IRowSet ros = DbUtil.executeQuery(ctx,sqls.toString());
		
		cosAmount = new HashMap();
//		List ls = new ArrayList();
		Map needMap = new HashMap();
//		Map adj = new HashMap();
		try {
			while (ros.next()) {
				String projectid = ros.getString("projectid");//������Ŀ
				BigDecimal AdjAmount = ros.getBigDecimal("AdjAmount");//����
				BigDecimal contractassign = ros.getBigDecimal("contractassign");//���η�����
				BigDecimal ghamoun = ros.getBigDecimal("ghamoun");//�滮���
				BigDecimal AimCost = ros.getBigDecimal("AimCost");//Ŀ��ɱ�
				BigDecimal goalcost = ros.getBigDecimal("goalcost");//��ĿĿ��ɱ�
				String ptyname = ros.getString("ptyname");//��Ʒ����
				String cosfid = ros.getString("cosfid");//��Ŀid
				String cosaname = ros.getString("cosaname");//��Ŀ����
				String cosanumber = ros.getString("cosanumber");//��Ŀ����
				String pmmconname = ros.getString("pmmconname");//��Լ����
				String pmmconnumber = ros.getString("pmmconnumber");//��Լ����
				BigDecimal need =ros.getBigDecimal("need");//����֧���������
				BigDecimal adjamt =ros.getBigDecimal("adjamt");//����֧��������
				BigDecimal needSpending = (BigDecimal) sxzcmap.get(projectid+pmmconname+pmmconnumber);//����֧��
//				if(pmmconname.equals("���ڴ�װ�޹���")&& cosanumber.equals("4001!03!02!01!04!01")){//cosanumber.equals("4001!03!02!01!01!06!01")||
//					System.out.println(cosanumber+pmmconname);
//				}
				if(!(ghamoun.compareTo(new BigDecimal(0)) != 0 )&& needSpending.compareTo(new BigDecimal(0)) != 0){
//				 	String key = pmmconnumber+cosfid;
				 	String key = cosfid;
					if(needMap.get(key)!= null){
						BigDecimal needval = (BigDecimal) needMap.get(key);
						needMap.put(key, needval.add(need.add(adjamt)));
					}else{
						needMap.put(key, need.add(adjamt));
					}
					
//					ls.add(key);
//					ss.put(cosfid, needSpending);
//					System.out.println("< 0"+key+" & "+need.add(adjamt));
				}else if(ghamoun.compareTo(new BigDecimal(0)) > 0 && contractassign.compareTo(new BigDecimal(0)) > 0){
					//����֧���������=����֧��*�����η�����/�滮��*��Ŀ��ɱ�/��ĿĿ��ɱ���
					BigDecimal needAmount = needSpending.multiply(contractassign.divide(ghamoun,28,RoundingMode.HALF_UP)).multiply(AimCost.divide(contractassign,28,RoundingMode.HALF_UP));
					
					//����������=����*�����η�����/�滮��*��Ŀ��ɱ�/��ĿĿ��ɱ���
					BigDecimal needAdjAmount = AdjAmount.multiply(contractassign.divide(ghamoun,28,RoundingMode.HALF_UP)).multiply(AimCost.divide(contractassign,28,RoundingMode.HALF_UP));
					if(cosAmount.get(cosfid)!= null){
						BigDecimal amval = (BigDecimal) cosAmount.get(cosfid);
						cosAmount.put(cosfid, amval.add(needAmount.add(needAdjAmount)));
//						System.out.println("> 0"+cosfid+" & "+amval.add(needAmount.add(needAdjAmount)));
					}else{
						cosAmount.put(cosfid, needAmount.add(needAdjAmount));
//						System.out.println("> 0"+cosfid+" & "+needAmount.add(needAdjAmount));
					}
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Iterator iterator = needMap.keySet().iterator();
		while(iterator.hasNext()){
			String val = (String) iterator.next();
			BigDecimal bval = (BigDecimal) needMap.get(val);
//			adj.put(val, bval);
//			System.out.println(val+"------------------"+bval);
//			System.out.println(val+"-------))))))))))))"+adj);
			if(cosAmount.get(val)!= null){
				BigDecimal amval = (BigDecimal) cosAmount.get(val);
				cosAmount.put(val, amval.add(bval));
			}else{
				cosAmount.put(val, bval);
			}
		}
		
		return cosAmount;
	}
	
}