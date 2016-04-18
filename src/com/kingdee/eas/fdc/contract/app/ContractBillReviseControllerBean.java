package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ClearSplitFacadeFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBailEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBailFactory;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillReviseCollection;
import com.kingdee.eas.fdc.contract.ContractBillReviseEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillReviseEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillReviseFactory;
import com.kingdee.eas.fdc.contract.ContractBillReviseInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemCollection;
import com.kingdee.eas.fdc.contract.ContractPayItemFactory;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.ContractReviseBailEntryCollection;
import com.kingdee.eas.fdc.contract.ContractReviseBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractReviseBailFactory;
import com.kingdee.eas.fdc.contract.ContractRevisePayItemCollection;
import com.kingdee.eas.fdc.contract.ContractRevisePayItemInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.eas.fdc.finance.IContractPayPlan;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.IllegalConversionException;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.UuidException;

//add by ypf on 20140521 这个类可以删除，原先是针对合同修订报中断错误加进来的，原因是标准产品的sql有问题，但是这个
//这个问题修改后，合同修订还是有问题，经分析还是标准产品问题，如果“是否单独计算”为否时，标准产品功能对最新造价还是没有进行修订
//因此鉴于功能有限情况下，客户同意 此功能暂时不做处理
public class ContractBillReviseControllerBean extends AbstractContractBillReviseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillReviseControllerBean");
	private boolean isCanModifyNumberAndName = false;
    protected boolean isCanModifyNumberAndName(Context ctx) {
    	boolean isOk = false;
    	try {
			isOk = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_CANMODIFYCONTRACTNUMBER);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return isOk;
	}
    protected boolean _contractBillStore(Context ctx, IObjectValue cbInfo, String storeNumber)throws BOSException, EASBizException
    {
        return false;
    }
    
    public boolean checkNumberDup(Context ctx, IObjectPK pk,
    		CoreBillBaseInfo model) throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	this.isCanModifyNumberAndName = isCanModifyNumberAndName(ctx);
    	return super.checkNumberDup(ctx, pk, model);
    }
    
    
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	this.isCanModifyNumberAndName = isCanModifyNumberAndName(ctx);
    	super.checkNameDup(ctx, billInfo);
    }
    protected boolean isUseName() {
    	return this.isCanModifyNumberAndName;
    }
    
    protected boolean isUseNumber() {
    
    	return isCanModifyNumberAndName;
    }
    
    protected void trimName(FDCBillInfo billInfo) {
		if(billInfo.getName() != null) {
			billInfo.setName(billInfo.getName().trim());
		}

	}
   /**
    * 反审批
    * @author ling_peng
    */
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {

    	SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add("entrys.*");
		selectors.add("payItems.*");
		selectors.add("bail.*");
		selectors.add("bail.entry.*");
		ContractBillReviseInfo model = (ContractBillReviseInfo) _getValue(ctx, new ObjectUuidPK(billId), selectors);
		checkBillForUnAudit(ctx, billId, model);
		
    	super._unAudit(ctx, billId);//注释上面代码，调用基类方法
		
    	//R110506-0421：非单独结算的补充合同金额累加到主合同上，计算错误  by zhiyuan_tang 2010-05-16
    	String conId = model.getContractBill().getId().toString();
    	ContractBillReviseInfo lastModel = null;
    	
    	EntityViewInfo reviseView = new EntityViewInfo();
    	reviseView.getSelector().add("*");
    	reviseView.getSelector().add("entrys.*");
    	reviseView.getSelector().add("payItems.*");
    	reviseView.getSelector().add("bail.*");
		reviseView.getSelector().add("bail.entry.*");
    	FilterInfo reviseFilter = new FilterInfo();
    	reviseView.setFilter(reviseFilter);
    	reviseFilter.getFilterItems().add(new FilterItemInfo("contractBill.id",conId));
    	reviseFilter.getFilterItems().add(new FilterItemInfo("id",billId.toString(),CompareType.NOTEQUALS));
    	reviseFilter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
    	reviseFilter.getFilterItems().add(new FilterItemInfo("auditTime", model.getAuditTime(), CompareType.LESS));
    	SorterItemInfo sorter = new SorterItemInfo("auditTime");
    	sorter.setSortType(SortType.DESCEND);
    	reviseView.getSorter().add(sorter);
    	ContractBillReviseCollection colls = ContractBillReviseFactory.getLocalInstance(ctx).getContractBillReviseCollection(reviseView);
    	if(colls!=null&&colls.size()>0){
    		lastModel = colls.get(0);
    	}
    	
		//之前对应的主合同要减去上次的不计成本金额
		subtractAmountToMainContract(ctx, model.getId());
		//现在对应的主合同要加上这次的不计成本金额
		if (lastModel != null) {
			addAmountToMainContract(ctx, lastModel);
		}
    	
    	boolean newCostSplit = model.isIsCoseSplit();//默认为当前修订值
    	boolean oldCostSplit = model.isIsCoseSplit();//目前不支持修改是否进入动态成本
		// 存在审批的修订则同步
    	if(lastModel!=null){
    		newCostSplit = lastModel.isIsCoseSplit();
    		synToContractBill(ctx, lastModel);
    	}else{
    		revertContract(ctx, model);
    	}
		
		// 合同修订审批时 更新合同执行表相应数据
		ContractExecInfosFactory.getLocalInstance(ctx).updateChange(ContractExecInfosInfo.EXECINFO_UNAUDIT, conId);
		
		// 如果是否进入动态成本的状态变了,则清空所有拆分
		String companyId = FDCHelper.getCurCompanyId(ctx, model.getCurProject().getId().toString());
		Map paramItem = FDCUtils.getDefaultFDCParam(ctx, companyId);
		boolean isFinacial = false;
		boolean isSeparate = false;
		if(paramItem.get(FDCConstants.FDC_PARAM_FINACIAL)!=null){
			isFinacial = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_FINACIAL).toString()).booleanValue();
		}
		if(paramItem.get( FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)!=null){
			isSeparate = Boolean.valueOf(paramItem.get( FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT).toString()).booleanValue();
		}
		
		if (isFinacial) {
			if (!model.getAmount().equals(model.getRevAmount()) || oldCostSplit != newCostSplit) {
				// 根据合同原币金额及拆分金额对比刷新合同表及其拆分表的拆分状态字段
				// refreshContractSplitState(ctx, conId);
				ClearSplitFacadeFactory.getLocalInstance(ctx).clearAllSplit(conId, false);

				// 同时删除月结数据表中的数据
				PeriodInfo currentPeriod = model.getPeriod();
				if (currentPeriod != null) {
					CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(conId, currentPeriod.getId().toString());
				}

			}

		} else {
			if (oldCostSplit != newCostSplit) {
				if (oldCostSplit) {
					// 清空成本拆分
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("contractBill.id", conId);

					// 删除付款拆分
					PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
					// 删除结算拆分
					SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
					// 删除变更拆分
					ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
					// 删除合同拆分
					ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
				} else {// 清除非成本类的拆分
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("contractBill.id", conId);

					PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
					SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
					ConChangeNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
					ConNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
				}
			} else {
				if (!model.getAmount().equals(model.getRevAmount())) {
					if (oldCostSplit) {
						// 清空成本拆分
						FilterInfo filter = new FilterInfo();
						filter.appendFilterItem("contractBill.id", conId);

						// 删除付款拆分
						PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
						// 删除结算拆分
						SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
						// 更新合同拆分为部分拆分
						//此处为什么都不进行判断合同是否拆分过，就直接将合同的拆分状态设置为"部分拆分"呢？这样做导致只要审批了"合同修订单据"该合同就不能再进行任何修改
						//暂时改为判断合同是否有过拆分，如果有过拆分就设置为"部分拆分"，如果根本没拆分过该合同就不做任何操作   by Cassiel_peng  2009-8-29
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						builder.appendSql("select fid from T_CON_ContractCostSplit where fcontractbillid=? ");
						builder.addParam(conId);
						IRowSet rowSet=builder.executeQuery();
						try {
							if(rowSet.next()){
								
								// 成本类：非自动按比例拆分时部分拆分(以前逻辑)，否则(启用参数)自动拆分并且状态为全部拆分
								// by hpw 2010-06-25
								String costCenterId = FDCHelper.getCostCenter(model.getCurProject(), ctx).getId().toString();
								boolean isSplitBaseOnProp = false;
								if (model != null && oldCostSplit && costCenterId != null) {
									isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_SPLITBASEONPROP);
								}
								
								if(isSplitBaseOnProp){
									ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(conId));
								}else{
									builder.clear();
									builder.appendSql("update T_Con_ContractCostSplit set fsplitState=? where fcontractBillId=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(conId);
									builder.execute();
									
									builder.clear();
									builder.appendSql("update T_Con_Contractbill set fsplitstate=? where fid=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(conId);
									builder.execute();
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {// 清除非成本类的拆分
						FilterInfo filter = new FilterInfo();
						filter.appendFilterItem("contractBill.id", conId);

						PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
						SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
						
						
						//还没有判断合同是否拆分过就直接将合同的拆分状态设置为"部分拆分"应该是不合理的
						//现修改为已经拆分过的合同才重置拆分状态  by Cassiel_peng  2009-8-29
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						builder.appendSql("select fid from T_CON_ConNoCostSplit where fcontractBillId=? ");
						builder.addParam(conId);
						IRowSet rowSet=builder.executeQuery();
						try {
							if(rowSet.next()){
								// 更新合同拆分为部分拆分
								builder.clear();
								builder.appendSql("update T_Con_ConNoCostSplit set fsplitState=? where fcontractBillId=?");
								builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
								builder.addParam(conId);
								builder.execute();
								
								builder.clear();
								builder.appendSql("update T_Con_Contractbill set fsplitstate=? where fid=?");
								builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
								builder.addParam(conId);
								builder.execute();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}

					}
				}

			}

		}
		BigDecimal latestPrice = FDCUtils.getContractLastAmt(ctx, conId);
		// 更新未审批的付款申请单以及付款单的合同造价为修订后的金额 by sxhong 2008－12－19
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Con_PayRequestBill set FContractPrice=?,FLatestPrice=? where fcontractid=? and ");
		builder.addParam(model.getOriginalAmount());
		builder.addParam(latestPrice);
		builder.addParam(conId);
		builder.appendParam("fstate", new String[] { FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE,FDCBillStateEnum.AUDITTING_VALUE });
		builder.execute();
		
		if(isSeparate){
			BigDecimal workload = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(conId);
			if (latestPrice.compareTo(FDCHelper.toBigDecimal(workload, 2)) == -1) {
				throw new EASBizException(new NumericExceptionSubItem("111",
						"合同最新造价小于合同的工程确认单已审批累计金额，请先修改工程量确认单！"));
			}
		}
		try {
			synContractProgAmt(ctx, model, false);
		} catch (SQLException e) {
			logger.error(e);
		}
    }
    
    //审核
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {

    	SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add("entrys.*");
		selectors.add("payItems.*");
		selectors.add("bail.*");
		selectors.add("bail.entry.*");
		selectors.add("contractBill.originalAmount");
		selectors.add("contractBill.amount");
		ContractBillReviseInfo model = (ContractBillReviseInfo) _getValue(ctx, new ObjectUuidPK(billId), selectors);
		checkBillForAudit(ctx, billId, model);
		
		String conId = model.getContractBill().getId().toString();
    	ContractBillReviseInfo lastModel = null;
    	
    	EntityViewInfo reviseView = new EntityViewInfo();
    	reviseView.getSelector().add("*");
    	reviseView.getSelector().add("entrys.*");
    	reviseView.getSelector().add("payItems.*");
    	reviseView.getSelector().add("bail.*");
		reviseView.getSelector().add("bail.entry.*");
    	FilterInfo reviseFilter = new FilterInfo();
    	reviseView.setFilter(reviseFilter);
    	reviseFilter.getFilterItems().add(new FilterItemInfo("contractBill.id",conId));
    	reviseFilter.getFilterItems().add(new FilterItemInfo("id",billId.toString(),CompareType.NOTEQUALS));
    	reviseFilter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
    	SorterItemInfo sorter = new SorterItemInfo("auditTime");
    	sorter.setSortType(SortType.DESCEND);
    	reviseView.getSorter().add(sorter);
    	ContractBillReviseCollection colls = ContractBillReviseFactory.getLocalInstance(ctx).getContractBillReviseCollection(reviseView);
    	if(colls!=null&&colls.size()>0){
    		lastModel = colls.get(0);
    	}
		
    	super._audit(ctx, billId);
    	
		//R110506-0421：非单独结算的补充合同金额累加到主合同上，计算错误  by zhiyuan_tang 2010-05-16
		//之前对应的主合同要减去上次的不计成本金额
    	if (lastModel != null) {
    		subtractAmountToMainContract(ctx, lastModel.getId());
    	}
    	// TODO 需求已经不能修订补充合同啦,其实这个功能蛮强大的
		//现在对应的主合同要加上这次的不计成本金额
		addAmountToMainContract(ctx, model);
		
		boolean newCostSplit = model.isIsCoseSplit();
		// 同步合同
		synToContractBill(ctx, model);
		boolean oldCostSplit = model.getBoolean("oldCostSplit");
		
		// 合同修订审批时 更新合同执行表相应数据
		ContractExecInfosFactory.getLocalInstance(ctx).updateChange(ContractExecInfosInfo.EXECINFO_AUDIT, conId);
		
		// 如果是否进入动态成本的状态变了,则清空所有拆分
		String companyId = FDCHelper.getCurCompanyId(ctx, model.getCurProject().getId().toString());//ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		Map paramItem = FDCUtils.getDefaultFDCParam(ctx, companyId);
		boolean isFinacial = false;
		boolean isSeparate = false;
		if(paramItem.get(FDCConstants.FDC_PARAM_FINACIAL)!=null){
			isFinacial = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_FINACIAL).toString()).booleanValue();
		}
		if(paramItem.get( FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)!=null){
			isSeparate = Boolean.valueOf(paramItem.get( FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT).toString()).booleanValue();
		}
		
		if (isFinacial) {
			if (!model.getAmount().equals(model.getRevAmount()) || oldCostSplit != newCostSplit) {
				// 根据合同原币金额及拆分金额对比刷新合同表及其拆分表的拆分状态字段
				// refreshContractSplitState(ctx, conId);
				ClearSplitFacadeFactory.getLocalInstance(ctx).clearAllSplit(conId, false);

				// 同时删除月结数据表中的数据
				PeriodInfo currentPeriod = model.getPeriod();
				if (currentPeriod != null) {
					CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(conId, currentPeriod.getId().toString());
				}

			}

		} else {
			if (oldCostSplit != newCostSplit) {
				if (oldCostSplit) {
					// 清空成本拆分
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("contractBill.id", conId);

					// 删除付款拆分
					PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
					// 删除结算拆分
					SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
					// 删除变更拆分
					ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
					// 删除合同拆分
					ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
				} else {// 清除非成本类的拆分
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("contractBill.id", conId);

					PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
					SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
					ConChangeNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
					ConNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
				}
			} else {
				if (!model.getAmount().equals(model.getRevAmount())) {
					if (oldCostSplit) {
						// 清空成本拆分
						FilterInfo filter = new FilterInfo();
						filter.appendFilterItem("contractBill.id", conId);

						// 删除付款拆分
						PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
						// 删除结算拆分
						SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
						// 更新合同拆分为部分拆分
						//此处为什么都不进行判断合同是否拆分过，就直接将合同的拆分状态设置为"部分拆分"呢？这样做导致只要审批了"合同修订单据"该合同就不能再进行任何修改
						//暂时改为判断合同是否有过拆分，如果有过拆分就设置为"部分拆分"，如果根本没拆分过该合同就不做任何操作   by Cassiel_peng  2009-8-29
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						builder.appendSql("select fid from T_CON_ContractCostSplit where fcontractbillid=? ");
						builder.addParam(conId);
						IRowSet rowSet=builder.executeQuery();
						try {
							if(rowSet.next()){
								//成本类：非自动按比例拆分时部分拆分(以前逻辑)，否则(启用参数)自动拆分并且状态为全部拆分 by hpw 2010-06-25
								boolean isSplitBaseOnProp = false;
								String costCenterId = FDCHelper.getCostCenter(model.getCurProject(), ctx).getId().toString();
								if (model != null && oldCostSplit && costCenterId != null) {
									isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_SPLITBASEONPROP);
								}
								
								if(isSplitBaseOnProp){
									ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(conId));
								}else{
									
									builder.clear();
									builder.appendSql("update T_Con_ContractCostSplit set fsplitState=? where fcontractBillId=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(conId);
									builder.execute();
									
									builder.clear();
									builder.appendSql("update T_Con_Contractbill set fsplitstate=? where fid=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(conId);
									builder.execute();
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
					} else {// 清除非成本类的拆分
						FilterInfo filter = new FilterInfo();
						filter.appendFilterItem("contractBill.id", conId);

						PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
						SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
						
						
						//还没有判断合同是否拆分过就直接将合同的拆分状态设置为"部分拆分"应该是不合理的
						//现修改为已经拆分过的合同才重置拆分状态  by Cassiel_peng  2009-8-29
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						builder.appendSql("select fid from T_CON_ConNoCostSplit where fcontractBillId=? ");
						builder.addParam(conId);
						IRowSet rowSet=builder.executeQuery();
						try {
							if(rowSet.next()){
								// 更新合同拆分为部分拆分
								builder.clear();
								builder.appendSql("update T_Con_ConNoCostSplit set fsplitState=? where fcontractBillId=?");
								builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
								builder.addParam(conId);
								builder.execute();
								
								builder.clear();
								builder.appendSql("update T_Con_Contractbill set fsplitstate=? where fid=?");
								builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
								builder.addParam(conId);
								builder.execute();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}

					}
				}

			}

		}
		BigDecimal latestPrice = FDCUtils.getContractLastAmt(ctx, conId);
		// 更新未审批的付款申请单以及付款单的合同造价为修订后的金额 by sxhong 2008－12－19
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Con_PayRequestBill set FContractPrice=?,FLatestPrice=? where fcontractid=? and ");
		builder.addParam(model.getRevAmount());
		builder.addParam(latestPrice);
		builder.addParam(conId);
		builder.appendParam("fstate", new String[] { FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE ,FDCBillStateEnum.AUDITTING_VALUE});
		builder.execute();
		
		if(isSeparate){
			BigDecimal workload = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(conId);
			if (latestPrice.compareTo(FDCHelper.toBigDecimal(workload, 2)) == -1) {
				throw new EASBizException(new NumericExceptionSubItem("111",
						"合同最新造价小于合同的工程确认单已审批累计金额，请先修改工程量确认单！"));
			}
		}
		
		try {
			synContractProgAmt(ctx, model, true);
		} catch (SQLException e) {
			logger.error(e);
		}
		// 补充金额处理
		moneyCount(ctx, model);
	}
    //计算金额的汇总 
    private void moneyCount(Context ctx,ContractBillReviseInfo model) throws EASBizException, BOSException
    {
    	String conId = model.getContractBill().getId().toString();
    	String prjId = model.getCurProject().getId().toString();
    	BigDecimal changeAmount = model.getRevLocalAmount();// 本币
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		ContractBillInfo contractInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(conId));
		String contractid=contractInfo.getId().toString();

		String hql = "where curProject='" + prjId + "' and mainContractNumber = '" + contractInfo.getCodingNumber() + "' and state = '4AUDITTED' and contractPropert = 'SUPPLY'";
		ContractBillCollection contracts = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(hql);
		BigDecimal total = FDCHelper.ZERO;
		ContractBillInfo suppContract=null;
		for (int i = 0; i < contracts.size(); i++) {
			suppContract = contracts.get(i);
			builder.clear();
			// TODO 需要将fdetail用frowkey代替
			builder.appendSql("select fcontent from T_CON_ContractBillEntry where fparentid =? and frowkey = 'lo'");
			builder.addParam(suppContract.getId().toString());
			IRowSet rs = builder.executeQuery();
			try {
				if (rs.next()) {
					String temp = rs.getString("fcontent");
					if ("否".equals(temp)) {
						builder.clear();
						builder.appendSql("select fcontent from T_CON_ContractBillEntry where fparentid = ? and frowkey = 'am'");
						builder.addParam(suppContract.getId().toString());
						IRowSet rs2 = builder.executeQuery();
						if (rs2.next()) {
							String temp2 = rs2.getString("fcontent");
							// 晕哦，分录中是原币
							total = FDCHelper.add(total, temp2);
						}
					}
				}
			} catch (Exception e) {
				throw new BOSException(e);
			}

		}
		BigDecimal finalAmount = FDCHelper.add(changeAmount, FDCHelper.multiply(total, contractInfo.getExRate()));
		BigDecimal fceremonybb = FDCHelper.divide(finalAmount, contractInfo.getExRate(), 4, BigDecimal.ROUND_HALF_EVEN);
		
		builder.clear();
		builder.appendSql("update t_con_contractbill set foriginalamount =?,famount =? where fid =?");
		builder.addParam(fceremonybb);
		builder.addParam(finalAmount);
		builder.addParam(contractid);
		builder.execute();
    	
    }
    
    /**
	 * 规划余额=规划金额-（修订后的签约金额+变更金额），控制余额=控制金额-修订后的签约金额
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private void synContractProgAmt(Context ctx, ContractBillReviseInfo model, boolean flag) throws EASBizException, BOSException, SQLException {
		BOSUuid contractBillId = model.getContractBill().getId();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(
				new ObjectUuidPK(contractBillId.toString()), sic);
		ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
		if (pcInfo == null)
			return;
		// 规划余额
		BigDecimal balanceAmt = pcInfo.getBalance();
		// 控制余额
		BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
		// 合同签约金额
		BigDecimal signAmount = model.getAmount();
		// 修订后合同本币金额
		BigDecimal revLocalAmount = model.getRevLocalAmount();
		// 框架合约签约金额
		BigDecimal signUpAmount = pcInfo.getSignUpAmount();
		// 差额
		BigDecimal otherSignUpAmount = FDCHelper.ZERO;
		if (flag) {
			pcInfo.setBalance(FDCHelper.subtract(FDCHelper.add(balanceAmt, signAmount), revLocalAmount));
			pcInfo.setControlBalance(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, signAmount), revLocalAmount));
			pcInfo.setSignUpAmount(FDCHelper.add(FDCHelper.subtract(signUpAmount, signAmount), revLocalAmount));
			otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(FDCHelper.subtract(signUpAmount, signAmount), revLocalAmount), signUpAmount);
		} else {
			pcInfo.setBalance(FDCHelper.subtract(FDCHelper.add(balanceAmt, revLocalAmount), signAmount));
			pcInfo.setControlBalance(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, revLocalAmount), signAmount));
			pcInfo.setSignUpAmount(FDCHelper.add(FDCHelper.subtract(signUpAmount, revLocalAmount), signAmount));
			otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(FDCHelper.subtract(signUpAmount, revLocalAmount), signAmount), signUpAmount);
		}
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("controlBalance");
		sict.add("signUpAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("srcId");
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		service.updatePartial(pcInfo, sict);
		// 更新其他的合约规划版本金额
		String progId = pcInfo.getId().toString();
		while (progId != null) {
			String nextVersionProgId = getNextVersionProg(ctx, progId);
			if (nextVersionProgId != null) {
				pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
				pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), otherSignUpAmount));
				pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(), otherSignUpAmount));
				pcInfo.setSignUpAmount(FDCHelper.add(pcInfo.getSignUpAmount(), otherSignUpAmount));
				service.updatePartial(pcInfo, sict);
				progId = pcInfo.getId().toString();
			} else {
				progId = null;
			}
		}
	}

	private String getNextVersionProg(Context ctx, String nextProgId) throws BOSException, SQLException {
		String tempId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder.appendSql(" select fid from t_con_programmingContract where  ");
		builder.appendParam("FSrcId", nextProgId);
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			tempId = rowSet.getString("fid");
		}
		return tempId;
	}
	
    private SelectorItemCollection getSic(){
		// 此过滤为详细信息定义
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("period.id"));
        sic.add(new SelectorItemInfo("period.beginDate"));	
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));	
        sic.add(new SelectorItemInfo("contractBill.id"));	
        sic.add(new SelectorItemInfo("revLocalAmount"));	
        return sic;
    }
    
	//提交时校验单据期间不能在工程项目的当前期间之前
	protected void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
		//不能落于当前成本期间之前
		ContractBillReviseInfo contractBill = (ContractBillReviseInfo)model;
		
		//是否启用财务一体化
		String comId = null;//contractBill.getCurProject().getFullOrgUnit().getId().toString();
		if( contractBill.getCurProject().getFullOrgUnit()==null){
			//contractBill= this.getContractBillReviseInfo(ctx,new ObjectUuidPK(contractBill.getId().toString()),getSic());
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(new ObjectUuidPK(contractBill.getCurProject().getId().toString()),sic);
			
			comId = curProject.getFullOrgUnit().getId().toString();
		}else{
			comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
		}
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractBill.getCurProject().getId().toString(),true);
			if(contractBill.getPeriod()!=null && contractBill.getPeriod().getEndDate().before(bookedPeriod.getBeginDate())){
				//单据期间不能在工程项目的当前期间之前 CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
	}
	
	//审核校验
	private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
    
		ContractBillReviseInfo model = (ContractBillReviseInfo)billInfo;

        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
        	model= this.getContractBillReviseInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
//        ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new ObjectUuidPK(billId.toString()),sic);
		//检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
			
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();	
			//成本已经月结
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			if(model.getPeriod().getBeginDate().after(bookedPeriod.getEndDate())){
				throw new  ContractException(ContractException.AUD_AFTERPERIOD,new Object[]{model.getNumber()});
			}
			
//			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,false);
//			if(bookedPeriod.getBeginDate().after(finPeriod.getEndDate())){
//				throw new  ContractException(ContractException.AUD_FINNOTCLOSE,new Object[]{model.getNumber()});
//			}	
		}
		
	}
	
	
	
	
	
	//反审核校验
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
		ContractBillReviseInfo model = (ContractBillReviseInfo)billInfo;
		// 此过滤为详细信息定义
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));
        sic.add(new SelectorItemInfo("period.beginDate"));	

        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
        	model= this.getContractBillReviseInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
		//检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
			
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();

			//单据期间在工程项目当前期间之前，不能反审核
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			if(model.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				throw new  ContractException(ContractException.CNTPERIODBEFORE);
			}	
			
//			if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
//				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{model.getCurProject().getDisplayName()});
//			}	
		}
	}
	
	/**
	 * 
	 * 合同修订时用修订后的原币金额与付款单金额原币进行比较
	 * 
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ContractBillReviseInfo info = (ContractBillReviseInfo) model;
		saveBail(ctx, info);
		return super._submit(ctx, info);
	}

	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		saveBail(ctx, model);
		super._submit(ctx, pk, model);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		saveBail(ctx, model);
		return super._save(ctx, model);
	}

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		saveBail(ctx, model);
		super._save(ctx, pk, model);
	}

	/**
	 * 保存履约保证金及返还
	 * 
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void saveBail(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ContractBillReviseInfo info = (ContractBillReviseInfo) model;
		if (info.getBail() != null) {
			ContractReviseBailFactory.getLocalInstance(ctx).save(info.getBail());
		}
	}
	
	private BigDecimal multiply(BigDecimal b1, BigDecimal b2){
		if(b1 == null || b2 == null){
			return FDCHelper.ZERO;
		}
		return FDCHelper.divide(FDCHelper.multiply(b1, b2), FDCHelper.ONE_HUNDRED);
	}
	
	private void revertContract(Context ctx,  ContractBillReviseInfo reviseBill ) throws EASBizException, BOSException{
		BOSUuid contractId = reviseBill.getContractBill().getId();
		BigDecimal amount = reviseBill.getAmount();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("stampTaxRate");
		selector.add("grtRate");
		selector.add("bail");
		selector.add("bail.id");
    	selector.add("bail.amount");
    	selector.add("bail.prop");
    	selector.add("bail.entry.id");
    	selector.add("bail.entry.amount");
    	selector.add("bail.entry.prop");
    	selector.add("payItems.*");
    	
		ContractBillInfo contract = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId),selector);
		selector.clear();
		
		selector.add("originalAmount");
		selector.add("amount");
		selector.add("stampTaxAmt");
		selector.add("grtAmount");
		selector.add("grtLocalAmount");
		contract.setOriginalAmount(reviseBill.getOriginalAmount());
		contract.setAmount(amount);		
		contract.setStampTaxAmt(multiply(amount, contract.getStampTaxRate()));		
		contract.setGrtLocalAmount(multiply(amount, contract.getGrtRate()));
		contract.setGrtAmount(multiply(contract.getOriginalAmount(), contract.getGrtRate()));
		ContractBillFactory.getLocalInstance(ctx).updatePartial(contract, selector);	
		selector.clear();
		
		EntityViewInfo payItemsView = new EntityViewInfo();
		payItemsView.setSelector(selector);
		selector.add("id");
		selector.add("prop");
		selector.add("amount");
		FilterInfo filter = new FilterInfo();
		payItemsView.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractbill", contractId.toString()));
		//业务上数据量并不大， 可以考虑这么做
		ContractPayItemCollection payItems = ContractPayItemFactory.getLocalInstance(ctx).getContractPayItemCollection(payItemsView);
		if(payItems!=null){
			for(int i = 0; i < payItems.size(); ++i){
				ContractPayItemInfo item = payItems.get(i);
				item.setAmount(multiply(amount, item.getProp()));
				ContractPayItemFactory.getLocalInstance(ctx).updatePartial(item, selector);
			}
		}
		selector.clear();
		
		ContractBailInfo bail = contract.getBail();
		if(bail != null){
			selector.add("prop");
			selector.add("amount");
			bail.setAmount(multiply(amount, bail.getProp()));
			ContractBailFactory.getLocalInstance(ctx).updatePartial(bail, selector);
			for(int i = 0; i < bail.getEntry().size(); ++i){
				ContractBailEntryInfo bailEntry = bail.getEntry().get(i);
				bailEntry.setAmount(multiply(bail.getAmount(),bailEntry.getProp()));
				ContractBailEntryFactory.getLocalInstance(ctx).updatePartial(bailEntry, selector);
			}
		}
//		ContractPayItemCollection ContractBailInfo
		
	}
	
    /**
     * 同步到合同
     * @param ctx
     * @param info
     * @throws BOSException
     * @throws EASBizException
     */
    private void synToContractBill(Context ctx, ContractBillReviseInfo info)  throws BOSException, EASBizException {
    	//合同ID
    	BOSUuid id = info.getContractBill().getId();
    	
    	/**
    	 * 修订了合同之后，如果合同金额有所变化，需要同步到合同履约保证金及返还部分、付款事项中反算相关金额和比例  by Cassiel_peng 2008-8-30
    	 */
    	SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("*");
    	selector.add("entries.*");
    	selector.add("payItems.*");
    	selector.add("bail");
    	selector.add("bail.amount");
    	selector.add("bail.prop");
    	selector.add("bail.entry.amount");
    	selector.add("bail.entry.prop");
    	selector.add("bail.entry.bailDate");
    	selector.add("bail.entry.bailConditon");
    	selector.add("bail.entry.desc");
    	selector.add("period.*");
    	selector.add("period.id");
    	
    	ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(id),selector);
    	info.setBoolean("oldCostSplit", contractBillInfo.isIsCoseSplit());
    	//新建一个空白合同
    	ContractBillInfo newConInfo = new ContractBillInfo();
    	
    	//先将修订Entry复制到合同的Entry
    	ContractBillEntryCollection conCol = new ContractBillEntryCollection();
    	ContractBillEntryInfo entryInfo = null;
    	
		ContractBillReviseEntryCollection entrys = info.getEntrys();
		
		if(entrys != null && entrys.size() > 0) {
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				ContractBillReviseEntryInfo element = (ContractBillReviseEntryInfo) iter.next();
				entryInfo = new ContractBillEntryInfo();
				entryInfo.putAll(element);
				entryInfo.setParent(contractBillInfo);
				conCol.add(entryInfo);
			}
		}
		
		//把修订entry清空
		info.put("entrys", null);
		
		// 处理付款事项 add by zhiyuan_tang 2010/01/007
		ContractRevisePayItemCollection rePayCol = info.getPayItems();
		ContractPayItemCollection payCol = new ContractPayItemCollection();
		ContractPayItemInfo payInfo = null;
		if(rePayCol != null && rePayCol.size() > 0) {
			for (Iterator iter = rePayCol.iterator(); iter.hasNext();) {
				ContractRevisePayItemInfo element = (ContractRevisePayItemInfo) iter.next();
				payInfo = new ContractPayItemInfo();
				payInfo.putAll(element);
				payInfo.setId(null);
				payInfo.setContractbill(newConInfo);
				payCol.add(payInfo);
			}
		}
		info.put("payItems", null);
		
    	//将修订的所有属性复制到合同
    	newConInfo.putAll(info);
    	newConInfo.put("entrys", conCol);
    	newConInfo.put("payItems", payCol);
    	
    	//还原一些不能改的属性
    	
    	newConInfo.setId(contractBillInfo.getId());
    	newConInfo.setCreateTime(contractBillInfo.getCreateTime());
    	newConInfo.setLastUpdateTime(contractBillInfo.getLastUpdateTime());
    	newConInfo.setCreator(contractBillInfo.getCreator());
    	newConInfo.setAuditor(contractBillInfo.getAuditor());
    	newConInfo.setAuditTime(contractBillInfo.getAuditTime());
    	newConInfo.setLastUpdateUser(contractBillInfo.getLastUpdateUser());
    	newConInfo.setState(contractBillInfo.getState());
    	newConInfo.setIsPartAMaterialCon(contractBillInfo.isIsPartAMaterialCon());
    	newConInfo.setPeriod(contractBillInfo.getPeriod());//合同的订立期间不允许修改 by cassiel 2010-08-25
    	//这俩字段绝对不能丢，哎，以前没注意，导致合同在作了修订之后付款申请单的截止上期累计实付(合同内工程款和应扣甲供材款)老是有问题  by cassiel_peng 2010-04-13
    	newConInfo.setPrjPriceInConPaid(contractBillInfo.getPrjPriceInConPaid());
    	newConInfo.setPaidPartAMatlAmt(contractBillInfo.getPaidPartAMatlAmt());
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	//如果启用了参数要把合同编号和合同名称同步过去
    	boolean isOk = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_CANMODIFYCONTRACTNUMBER);
    	if(isOk){
    		newConInfo.setMainContractNumber(info.getMainContractNumber());
    		//newConInfo.setMainContractNumber(info.getNumber());
    		newConInfo.setNumber(info.getNumber());
//    		newConInfo.setName(info.getMainContractNumber());
    		//更新付款申请单
    		builder.appendSql("UPDATE T_CON_PAYREQUESTBILL SET FCONTRACTNO = ?,FCONTRACTNAME = ? WHERE FCONTRACTID = ?");
    		builder.addParam(newConInfo.getNumber());
    		builder.addParam(newConInfo.getName());
    		builder.addParam(newConInfo.getId().toString());
    		builder.execute();
    		
    		//更新所有补充合同的主合同编码
    		builder.clear();
    		builder.appendSql("update t_con_contractbillentry set fcontent = ? where  frowkey = 'na' and fparentid in (select fparentid from t_con_contractbillentry where fcontent = ? and frowkey ='nu')");
    		builder.addParam(newConInfo.getName());
    		builder.addParam(contractBillInfo.getId().toString());
    		builder.execute();
    		builder.clear();
    		
    		
    		//更新付款单对引用
    	   builder.clear();
    	   builder.appendSql("UPDATE T_CAS_PAYMENTBILL SET fcontractno = ?  WHERE fcontractbillid = ?");
    	   builder.addParam(newConInfo.getNumber());
    	   builder.addParam(newConInfo.getId().toString());
    	   builder.execute();
    	   builder.clear();
    		
    	}
    	 
    	// 对签约金额修订，则直接赋值,monyCount方法处理补充金额并给合同原币及本币
    	newConInfo.setCeremonyb(info.getRevAmount());
		newConInfo.setCeremonybb(info.getRevLocalAmount());
		
		
    	//原币金额用setOriginalAmount
		// newConInfo.setOriginalAmount(info.getRevAmount());
		// newConInfo.setAmount(info.getRevLocalAmount());
    	//设置不计成本的金额
    	
    	//处理履约保证金及返还部分
		ContractBailInfo bailInfo = null;
		if (info.getBail() != null) {
			bailInfo = new ContractBailInfo();
			bailInfo.setProp(info.getBail().getProp());
			bailInfo.setAmount(info.getBail().getAmount());
			//处理分录
			ContractReviseBailEntryCollection reBailEntryCol = info.getBail().getEntry();
			ContractBailEntryInfo bailEntryInfo = null;
			if(reBailEntryCol != null && reBailEntryCol.size() > 0) {
				for (Iterator iter = reBailEntryCol.iterator(); iter.hasNext();) {
					ContractReviseBailEntryInfo element = (ContractReviseBailEntryInfo) iter.next();
					bailEntryInfo = new ContractBailEntryInfo();
					bailEntryInfo.putAll(element);
					bailEntryInfo.setId(null);
					bailEntryInfo.setParent(bailInfo);
					bailInfo.getEntry().add(bailEntryInfo);
				}
			}
		}
		newConInfo.setBail(bailInfo);
		if (bailInfo != null) {
			ContractBailFactory.getLocalInstance(ctx).addnew(bailInfo);
		}
		if (contractBillInfo.getBail() != null) {
			ContractBailFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(contractBillInfo.getBail().getId()));
		}
    	
//    	//处理履约保证金及返还部分  by Cassiel_peng 
//    	BigDecimal oldContractAmount=FDCHelper.toBigDecimal(info.getRevAmount(),2);//修订后合同的金额
//    	ContractBailInfo bailInfo=contractBillInfo.getBail();
//    	if(bailInfo!=null){
//    		if(bailInfo.getProp()!=null){//如果履约保证金比例不为空
//    			//根据此次修订的合同金额和履约保证金比例计算履约保证金金额原币
//    			BigDecimal bailOriAmount= FDCHelper.ZERO;
//    			BigDecimal bailRate= FDCHelper.toBigDecimal(bailInfo.getProp(),2);
//    			bailOriAmount=FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(bailRate,oldContractAmount),FDCHelper.ONE_HUNDRED),2);
//    			bailInfo.setAmount(bailOriAmount);
//    		}
//    		ContractBailEntryCollection bailEntryInfoCollection=bailInfo.getEntry();
//    		if(bailEntryInfoCollection!=null&&bailEntryInfoCollection.size()!=0){
//    			for (int i = 0; i < bailEntryInfoCollection.size(); i++) {
//    				ContractBailEntryInfo bailEntryInfo=bailEntryInfoCollection.get(i);
//    				if(bailEntryInfo.getProp()!=null){
//    					BigDecimal bailEntryAmount= FDCHelper.ZERO;
//    					BigDecimal bailEntryRate= FDCHelper.toBigDecimal(bailEntryInfo.getProp(),2);
//    					bailEntryAmount=FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.toBigDecimal(FDCHelper.multiply(bailEntryRate,oldContractAmount),2),FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP),2);
//    					bailEntryInfo.setAmount(bailEntryAmount);
//    					bailEntryInfo.setParent(bailInfo);
//    				}
//    			}
//    		}
//    	}
//    	newConInfo.setBail(bailInfo);
//    	
//    	//处理付款事项
//    	ContractPayItemCollection conPayItemColleciton=new ContractPayItemCollection();
//    	builder.appendSql("select  FPayItemDate,FPaymentTypeid,FPayCondition,FProp,FAmount,FDesc,FContractBillId,FID from t_con_contractpayitem where fcontractbillid=? \n ");
////    	builder.appendSql("select  FProp,FContractBillId,FID from t_con_contractpayitem where fcontractbillid=? \n ");
//    	builder.addParam(id.toString());
//    	IRowSet rowSet=builder.executeQuery();
//    	try {
//			while(rowSet.next()){
//				BigDecimal payRate=rowSet.getBigDecimal("FProp");
//				ContractPayItemInfo conPayItemInfo=new ContractPayItemInfo();
//				//反算付款事项的金额
//				BigDecimal bailOriAmount= FDCHelper.ZERO;
//				if(payRate!=null){
//	    			bailOriAmount=FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(payRate,oldContractAmount),FDCHelper.ONE_HUNDRED));
//	    			conPayItemInfo.setAmount(bailOriAmount);
//				}
//				/*builder.clear();
//				builder.appendSql("update t_con_contractpayitem set FAmount=? ,FID=?,FContractBillId=? where FContractBillId=? and FID=? \n ");
//				builder.addParam(bailOriAmount);
//				builder.addParam(rowSet.getString("FID"));
//				builder.addParam(id.toString());
//				builder.addParam(id.toString());
//				builder.addParam(rowSet.getString("FID"));
//				builder.executeUpdate();*/
//				//太郁闷了，下面这种方法竟然都不能更新付款事项，烦死了！
//				//保存比例
//				conPayItemInfo.setProp(payRate);
//				//保存日期
//				conPayItemInfo.setPayItemDate(rowSet.getDate("FPayItemDate"));
//				//保存付款类型
//				PaymentTypeInfo payTypeInfo=new PaymentTypeInfo();
//				if(rowSet.getString("FPaymentTypeid")!=null&&!"".equals(rowSet.getString("FPaymentTypeid"))){
//					payTypeInfo.setId(BOSUuid.read(rowSet.getString("FPaymentTypeid")));
//				}
//				conPayItemInfo.setPaymentType(payTypeInfo);
//				//保存付款条件
//				conPayItemInfo.setPayCondition(rowSet.getString("FPayCondition"));
//				conPayItemInfo.setDesc(rowSet.getString("FDesc"));
//				conPayItemInfo.setContractbill(contractBillInfo);
//				conPayItemInfo.setId(BOSUuid.read(rowSet.getString("Fid")));
//				conPayItemColleciton.add(conPayItemInfo);
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
    	
    	ContractBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(id), newConInfo);
//    	//想挂在合同上通过合同更新硬是不行(只有ContractBailInfo头可以更新,ContractBailEntryInfo分录却不能更新)，硬是得调用自己的接口更新数据   by Cassiel_peng
//    	if(bailInfo!=null){
//    		ContractBailFactory.getLocalInstance(ctx).update(new ObjectUuidPK(bailInfo.getId()), bailInfo);
//    	}
//    	if(conPayItemColleciton!=null){
//    		for (Iterator iter=conPayItemColleciton.iterator();iter.hasNext();) {
//    			ContractPayItemInfo tempConPaItemInfo=(ContractPayItemInfo)iter.next();
//    			if(tempConPaItemInfo!=null){
//    				ContractPayItemFactory.getLocalInstance(ctx).save(new ObjectUuidPK(tempConPaItemInfo.getId()),tempConPaItemInfo);
//    			}
//    		}
//    	}
    }
    
    
    
    
    
	private void refreshContractSplitState(Context ctx,String contrcatId) throws BOSException{
		String splitState = null;
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("select C.FAmount,CC.FAmount AS FSplitAmount from T_CON_ContractBill C ");
			builder.appendSql(" LEFT OUTER JOIN T_CON_CONTRACTCOSTSPLIT CC ON CC.FContractBillId=C.FID ");
			builder.appendSql(" where CC.FIsInvalid=0 AND C.FId=? ");
			builder.addParam(contrcatId);
			IRowSet rowSet = builder.executeQuery();
			builder.clear();
			if(rowSet == null||rowSet.size() < 1){
				splitState = CostSplitStateEnum.NOSPLIT_VALUE;
			}else{
				if(rowSet.next()){
					if(rowSet.getBigDecimal("FAmount").equals(rowSet.getBigDecimal("FSplitAmount"))){
						splitState = CostSplitStateEnum.ALLSPLIT_VALUE;
					}else{
						splitState = CostSplitStateEnum.PARTSPLIT_VALUE;
					}
				}
			}
			
			builder.clear();
			builder.appendSql("update T_CON_ContractBill set FSplitState=? where fid=?");
			builder.addParam(splitState);
			builder.addParam(contrcatId);
			builder.executeUpdate(ctx);
			
			builder.clear();
			builder.appendSql("update T_CON_ContractCostSplit set FSplitState=? where FContractBillID=?");
			builder.addParam(splitState);
			builder.addParam(contrcatId);
			builder.executeUpdate(ctx);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//单据序时簿编辑界面初始数据粗粒度方法
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		
		Map initMap = super._fetchInitData(ctx,paramMap);	
		//工程项目
		CurProjectInfo curProject = (CurProjectInfo) initMap.get(FDCConstants.FDC_INIT_PROJECT);
		if(curProject!=null){
			PeriodInfo costperiod = FDCUtils.getCurrentPeriod(ctx,curProject.getId().toString(),true);
			PeriodInfo finaperiod = FDCUtils.getCurrentPeriod(ctx,curProject.getId().toString(),true);
			if(costperiod!=null && finaperiod!=null && !costperiod.getId().toString().equals(finaperiod.getId().toString())){
				throw new ContractException(ContractException.CANTXIUDING,new Object[]{new Integer(costperiod.getNumber()),new Integer(finaperiod.getNumber())});
			}
		}
		
		return initMap;
	}

	/**
	 * 描述：将不计成本金额从主合同中减掉，并更新拆分状态 这里不用处理签约金额，在addAmountToMainContract中
	 * 
	 * @param ctx
	 * @param billId
	 * @throws BOSException
	 * @throws ContractException
	 * @throws EASBizException
	 * @throws IllegalConversionException
	 * @throws UuidException
	 */
	private void subtractAmountToMainContract(Context ctx, BOSUuid billId) throws BOSException,
			ContractException, EASBizException, IllegalConversionException, UuidException {
		//如果是非独立结算的补充合同,金额累加到
		String sql = " select entry.fcontent as amount,parent.fsplitState splitState,parent.fid mainId,parent.FAmount mainAmount,parent.ForiginalAmount oriAmount 			\r\n"
				+
				",parent.FexRate mainRate,parent.FgrtRate grtRate,parent.FGrtAmount grtAmount,parent.FStampTaxRate stampRate,parent.FStampTaxAmt stampAmt    \r\n"+
				" ,parent.FHasSettled    from T_CON_ContractBillReviseEntry entry 																	\r\n" +
				"inner join T_CON_ContractBillRevise con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' 	\r\n" +
				"inner join T_CON_ContractBill parent on parent.fnumber = con.fmainContractNumber 	and parent.fcurprojectid=con.fcurprojectid					\r\n" +
				"where   con.fid=? 	and  entry.FRowkey='am' 	 																							\r\n";
		IRowSet rs = DbUtil.executeQuery(ctx,sql,new Object[]{billId.toString()});
		try {
			if(rs!=null && rs.next()){
				//
				boolean hasSettle = rs.getBoolean("FHasSettled");
				if(hasSettle){
					throw new ContractException(ContractException.MAINCONHASSETTLED);
				}
				String splitState = rs.getString("splitState");	
				//如果主合同已完全拆分 则不允许反审批补充合同
				if(splitState!=null &&splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
					throw new ContractException(ContractException.HASALLAPLIT);
				}
				//更新主合同金额
				String mainId = rs.getString("mainId");
				//补充合同金额
				BigDecimal supAmount = rs.getBigDecimal("amount");
				
				// 主合同本币金额
				BigDecimal mainAmount = rs.getBigDecimal("mainAmount");
				//主合同原币金额
				BigDecimal oriAmount = rs.getBigDecimal("oriAmount");
				//主合同汇率
				BigDecimal mainRate = rs.getBigDecimal("mainRate");
				//主合同保修比例
				BigDecimal grtRate = rs.getBigDecimal("grtRate");
				//主合同保修金额
				BigDecimal grtAmount = rs.getBigDecimal("grtAmount");
				//主合同印花税率
				BigDecimal stampRate = rs.getBigDecimal("stampRate");
				//主合同印花税金额
				BigDecimal stampAmt = rs.getBigDecimal("stampAmt");
				
				String updateSql = "update T_CON_Contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
//					String updateSql = "update T_CON_Contractbill set FAmount=?,FlocalAmount=? where Fid=?";
				//DbUtil.execute(ctx,updateSql,new Object[]{mainAmount.subtract(supAmount),localAmount.subtract(supAmount),mainId});
				
				//R101227-311合同反审批时报错， 先进行空指针处理
		    	//补充合同金额 本位币
				if(supAmount==null){
					supAmount = FDCConstants.ZERO;
				}
		    	BigDecimal revAmount = supAmount.multiply(mainRate);
		    	//如果主合同已拆分金额大于其自身金额 则补充合同不能反审批
		    	String splitSql = "select famount from T_CON_ContractCostSplit where fcontractBillId=?";
		    	IRowSet ir = DbUtil.executeQuery(ctx,splitSql,new Object[]{mainId.toString()});
		    	if(ir!=null && ir.next()){
		    		BigDecimal splitAmount = ir.getBigDecimal("famount");
		    		if(splitAmount.compareTo(mainAmount.subtract(revAmount))==1){
		    			throw new ContractException(ContractException.HASSPLIT);
		    		}
		    	}
				//如果付款申请单的累计申请金额已经大于revAmount
		    	if(mainAmount==null){
		    		mainAmount = FDCConstants.ZERO;
		    	}
		    	if(oriAmount==null){
		    		oriAmount = FDCConstants.ZERO;
		    	}
		    	BigDecimal temp = FDCConstants.ZERO;
		    	if(mainRate.compareTo(FDCConstants.ZERO)==1){
		    		temp = supAmount.multiply(mainRate);
		    	}
		    	//更新保修金额
		    	BigDecimal newGrtAmount = FDCConstants.ZERO;
		    	if(grtRate.compareTo(FDCConstants.ZERO)==1){
		    		newGrtAmount = (oriAmount.subtract(supAmount)).multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED,4,2);
		    	}
		    	//更新印花税金额
		    	BigDecimal newStampAmt = FDCConstants.ZERO;
		    	if(stampRate.compareTo(FDCConstants.ZERO)==1){
		    		newStampAmt = (oriAmount.subtract(supAmount)).multiply(mainRate).multiply(stampRate).divide(FDCConstants.ONE_HUNDRED,4,2);
		    	}
		    	DbUtil.execute(ctx,updateSql,new Object[]{oriAmount.subtract(supAmount),mainAmount.subtract(temp),newGrtAmount,newStampAmt,mainId});
				EntityViewInfo viewInfo = new EntityViewInfo();
				viewInfo.getSelector().add("*");
				viewInfo.getSelector().add("curProject.costCenter");
				viewInfo.getSelector().add("contractBill.isCoseSplit");
				FilterInfo filterInfo = new FilterInfo();
				viewInfo.setFilter(filterInfo);
				filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id",mainId));
				
				//R110228-280：录入非单独结算的补充合同之后主合同显示为全部拆分(之前没有考虑合同不进入动态成本的情况)
				ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
				if (contractbill.isIsCoseSplit()) {
					
					//成本类：非自动按比例拆分时部分拆分，否则自动拆分并且状态为全部拆分 by hpw 2010-06-25
					ContractCostSplitCollection coll = ContractCostSplitFactory
					.getLocalInstance(ctx).getContractCostSplitCollection(viewInfo);
					boolean isSplitBaseOnProp = false;
					if (coll != null && coll.size() > 0) {
						ContractCostSplitInfo info = coll.get(0);
						if (info != null && info.getContractBill() != null && info.getContractBill().isIsCoseSplit() && info.getCurProject() != null && info.getCurProject().getCostCenter() != null
								&& info.getCurProject().getCostCenter().getId() != null) {
							isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, info.getCurProject().getCostCenter().getId().toString(), FDCConstants.FDC_PARAM_SPLITBASEONPROP);
						}
					}
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
					for(int j=0;j < coll.size();j++){
						ContractCostSplitInfo splitInfo = coll.get(j);
						if(splitInfo!=null){
							if(!isSplitBaseOnProp){
								//如果主合同原金额等于主合同已拆分金额 则变更主合同拆分状态为完全拆分
								if((mainAmount.subtract(revAmount)).compareTo(splitInfo.getBigDecimal("amount"))==0 
										&& splitState.equals(CostSplitStateEnum.PARTSPLIT_VALUE)){
									builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
									builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.getSql();
									builder.execute();
									//								DbUtil.execute(ctx,updateState1,new Object[]{CostSplitStateEnum.PARTSPLIT_VALUE,mainId.toString()});
									//								FDCSQLBuilder builder2=new FDCSQLBuilder(ctx);
									builder.clear();
									builder.appendSql("update T_CON_ContractCostSplit set fsplitState=? where fcontractbillid=?");
									builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.execute();
									builder.clear();
								}
							}
						}
					}
					
					if(isSplitBaseOnProp){
						ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(mainId));
					}
				} else {
					
					//非成本类：更新状态为部分拆分
					ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
					for(int i=0;i < col.size();i++){
						ConNoCostSplitInfo info = col.get(i);
						if(info!=null){
							//如果主合同原金额等于主合同已拆分金额 则变更主合同拆分状态为完全拆分
							if((mainAmount.subtract(revAmount)).compareTo(info.getBigDecimal("amount"))==0
									&& splitState.equals(CostSplitStateEnum.PARTSPLIT_VALUE)){
								builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
								builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
								builder.addParam(mainId.toString());
								builder.getSql();
								builder.execute();
								builder.clear();
								builder.appendSql("update T_CON_ConNoCostSplit set fsplitState=? where fcontractbillid=?");
								builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
								builder.addParam(mainId.toString());
								builder.execute();
								builder.clear();
							}
						}
					}
				}
				
		    	//更新合同付款计划    	
		    	revAmount = revAmount.add(FDCUtils.getContractLastPrice (ctx,mainId,false));
		    	
				EntityViewInfo view = new EntityViewInfo();
				view.getSelector().add(new SelectorItemInfo("payProportion"));
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("contractId.id", mainId));
				
				IContractPayPlan iContractPayPlan =  ContractPayPlanFactory.getLocalInstance(ctx);
				ContractPayPlanCollection payPlans =iContractPayPlan.getContractPayPlanCollection(view);
				for (int i = 0; i < payPlans.size(); i++) {
					ContractPayPlanInfo info = payPlans.get(i);
					
					info.setPayAmount(revAmount.multiply(info.getPayProportion()).divide(new BigDecimal(100), 2,
							BigDecimal.ROUND_HALF_UP));					
					iContractPayPlan.update(new ObjectUuidPK(info.getId().toString()),info);
				}
			/*****更新主合同的合同执行情况表合同成本金额 -by neo****/
			ContractExecInfosFactory.getLocalInstance(ctx).
					updateSuppliedContract("audit", mainId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}

	/**
	 * 描述：将不计成本金额累加到主合同中，并更新拆分状态 包括处理签约金额
	 * 
	 * @param ctx
	 * @param billId
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws IllegalConversionException
	 * @throws UuidException
	 */
	private void addAmountToMainContract(Context ctx, ContractBillReviseInfo model) throws BOSException, EASBizException,
			IllegalConversionException, UuidException {
		//如果是非独立结算的补充合同,金额累加到
		String sql1 = " select entry.fcontent as amount,parent.fsplitState splitState,parent.fid mainId,parent.fceremonyb ceremonyb,parent.fceremonybb ceremonybb,parent.FAmount mainAmount,parent.ForiginalAmount oriAmount 			\r\n"
				+
				",parent.FexRate mainRate,parent.FgrtRate grtRate,parent.FGrtAmount grtAmount,parent.FStampTaxRate stampRate,parent.FStampTaxAmt stampAmt                             \r\n"+
				"from T_CON_ContractBillReviseEntry entry 																							\r\n" +
				"inner join T_CON_ContractBillRevise con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' 	\r\n" +
				"inner join T_CON_ContractBill parent on parent.fnumber = con.fmainContractNumber  and parent.fcurprojectid=con.fcurprojectid												\r\n" +
				"where con.fid=? and entry.FRowkey='am' 																				\r\n";
		IRowSet rs1 = DbUtil.executeQuery(ctx,sql1,new Object[]{model.getId().toString()});
		
		try {
			if(rs1!=null && rs1.next()){
				//更新主合同金额
				String splitState = rs1.getString("splitState");
				String mainId = rs1.getString("mainId");
				//补充合同金额
				BigDecimal supAmount = rs1.getBigDecimal("amount");
				// 主合同签约原币
				BigDecimal ceremonyb = rs1.getBigDecimal("ceremonyb");
				// 主合同签约本币
				BigDecimal ceremonybb = rs1.getBigDecimal("ceremonybb");
				//主合同本位币金额
				BigDecimal mainAmount = rs1.getBigDecimal("mainAmount");
//					BigDecimal localAmount = rs1.getBigDecimal("mainLocalAmount");
				//主合同原币金额
				BigDecimal oriAmount = rs1.getBigDecimal("oriAmount");
				//主合同汇率
				BigDecimal mainRate = rs1.getBigDecimal("mainRate");
				//主合同保修比例
				BigDecimal grtRate = rs1.getBigDecimal("grtRate");
				//主合同保修金额
				BigDecimal grtAmount = rs1.getBigDecimal("grtAmount");
				//主合同印花税率
				BigDecimal stampRate = rs1.getBigDecimal("stampRate");
				//主合同印花税金额
				BigDecimal stampAmt = rs1.getBigDecimal("stampAmt");
				//更新主合同原币和本位币金额
				String updateSql = "update T_CON_Contractbill set FCeremonyb=?, FCeremonybb=?,ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
				
		    	//原币金额
		    	if(supAmount==null){
		    		supAmount = FDCConstants.ZERO;
		    	}
		    	if(mainAmount==null){
		    		mainAmount = FDCConstants.ZERO;
		    	}
		    	if(oriAmount==null){
		    		oriAmount = FDCConstants.ZERO;
		    	}
		    	BigDecimal revAmount = FDCConstants.ZERO;
		    	if(mainRate.compareTo(FDCConstants.ZERO)==1){
		    		revAmount = supAmount.multiply(mainRate);
		    	}
		    	//处理金额不相等 by hpw 2011.10.26
		    	if(FDCHelper.compareTo(model.getRevAmount(), model.getContractBill().getOriginalAmount())!=0){
		    		//减去原来的，再加上最新的,本币汇率相同也直接加减
		    		oriAmount = FDCHelper.subtract(oriAmount, model.getContractBill().getOriginalAmount()).add(revAmount);
		    		mainAmount= FDCHelper.subtract(mainAmount, model.getContractBill().getAmount()).add(supAmount);
		    	}
		    	// 直接等于修订金额不处理补充合同
				ceremonyb = revAmount;
				ceremonybb = supAmount;
		    	
		    	//更新保修金额
		    	BigDecimal newGrtAmount = FDCConstants.ZERO;
		    	if(grtRate.compareTo(FDCConstants.ZERO)==1){
		    		newGrtAmount = oriAmount.multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED,4,2);
		    	}
		    	//更新印花税金额
		    	BigDecimal newStampAmt = FDCConstants.ZERO;
		    	if(stampRate.compareTo(FDCConstants.ZERO)==1){
		    		newStampAmt = oriAmount.multiply(mainRate).multiply(stampRate).divide(FDCConstants.ONE_HUNDRED,4,2);
		    	}
		    	
		    	DbUtil.execute(ctx, updateSql, new Object[] { ceremonyb, ceremonybb, oriAmount, mainAmount, newGrtAmount, newStampAmt, mainId });
						    		
				//判断主合同的金额 和 合同拆分的已拆分金额 ，是否相等。如果不等，把主合同和合同拆分的拆分状态改为部分拆分
//					String updateState1 = "update T_CON_Contractbill set FSplitState=? where Fid=?";
//					String updateState2 = "update T_CON_ContractCostSplit set FSplitState=? where Fid=?";
				EntityViewInfo viewInfo = new EntityViewInfo();
//					viewInfo.getSelector().add("amount");
//					viewInfo.getSelector().add("splitState");
				viewInfo.getSelector().add("*");
				viewInfo.getSelector().add("curProject.costCenter");
				viewInfo.getSelector().add("contractBill.isCoseSplit");
				FilterInfo filterInfo = new FilterInfo();
				viewInfo.setFilter(filterInfo);
				filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id",mainId));
				
				//R110228-280：录入非单独结算的补充合同之后主合同显示为全部拆分(之前没有考虑合同不进入动态成本的情况)
				ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
				if (contractbill.isIsCoseSplit()) {
					
					//成本类：非自动按比例拆分时部分拆分(以前逻辑)，否则(启用参数)自动拆分并且状态为全部拆分 by hpw 2010-06-25
					ContractCostSplitCollection col = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(viewInfo);
					boolean isSplitBaseOnProp = false;
					if (col != null && col.size() > 0) {
						ContractCostSplitInfo info = col.get(0);
						if (info != null && info.getContractBill() != null && info.getContractBill().isIsCoseSplit() && info.getCurProject() != null && info.getCurProject().getCostCenter() != null
								&& info.getCurProject().getCostCenter().getId() != null) {
							isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, info.getCurProject().getCostCenter().getId().toString(), FDCConstants.FDC_PARAM_SPLITBASEONPROP);
						}
					}
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
					for(int i=0;i < col.size();i++){
						ContractCostSplitInfo info = col.get(i);
						if(info!=null){
							if(!isSplitBaseOnProp){
								//如果补充合同+主合同金额大于主合同已拆分金额 则变更主合同拆分状态为部分拆分
								if((mainAmount.add(revAmount)).compareTo(info.getBigDecimal("amount"))==1
										&& splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
									builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.getSql();
									builder.execute();
									builder.clear();//不用再New
									builder.appendSql("update T_CON_ContractCostSplit set fsplitState=? where fcontractbillid=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.execute();
									builder.clear();
								}
							}
						}
					}
					
					if(isSplitBaseOnProp){
						ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(mainId));
					}
					
				} else {
					//非成本类：更新状态为部分拆分
					ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
					for(int i=0;i < col.size();i++){
						ConNoCostSplitInfo info = col.get(i);
						if(info!=null){
							//如果补充合同+主合同金额大于主合同已拆分金额 则变更主合同拆分状态为部分拆分
							if((mainAmount.add(revAmount)).compareTo(info.getBigDecimal("amount"))==1
									&& splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
								builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
								builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
								builder.addParam(mainId.toString());
								builder.getSql();
								builder.execute();
								builder.clear();//不用再New
								builder.appendSql("update T_CON_ConNoCostSplit set fsplitState=? where fcontractbillid=?");
								builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
								builder.addParam(mainId.toString());
								builder.execute();
								builder.clear();
							}
						}
					}
				}
				
		    	//更新合同付款计划    					
				revAmount = revAmount.add(FDCUtils.getContractLastPrice (ctx,mainId,false));
				
				EntityViewInfo view = new EntityViewInfo();
				view.getSelector().add(new SelectorItemInfo("payProportion"));
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("contractId.id", mainId));
				
				IContractPayPlan iContractPayPlan =  ContractPayPlanFactory.getLocalInstance(ctx);
				ContractPayPlanCollection payPlans =iContractPayPlan.getContractPayPlanCollection(view);
				for (int i = 0; i < payPlans.size(); i++) {
					ContractPayPlanInfo info = payPlans.get(i);
					
					info.setPayAmount(revAmount.multiply(info.getPayProportion()).divide(new BigDecimal(100), 2,
							BigDecimal.ROUND_HALF_UP));					
					iContractPayPlan.update(new ObjectUuidPK(info.getId().toString()),info);
				}
			/*****更新主合同的合同执行情况表合同成本金额 -by neo****/
			ContractExecInfosFactory.getLocalInstance(ctx).
					updateSuppliedContract("audit", mainId);
			}else{
				
				//TODO 修改是否单独计算属性未支持  by hpw
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}
	
}