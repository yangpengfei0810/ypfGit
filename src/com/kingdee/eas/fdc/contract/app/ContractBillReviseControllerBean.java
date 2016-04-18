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

//add by ypf on 20140521 ��������ɾ����ԭ������Ժ�ͬ�޶����жϴ���ӽ����ģ�ԭ���Ǳ�׼��Ʒ��sql�����⣬�������
//��������޸ĺ󣬺�ͬ�޶����������⣬���������Ǳ�׼��Ʒ���⣬������Ƿ񵥶����㡱Ϊ��ʱ����׼��Ʒ���ܶ�������ۻ���û�н����޶�
//��˼��ڹ�����������£��ͻ�ͬ�� �˹�����ʱ��������
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
    * ������
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
		
    	super._unAudit(ctx, billId);//ע��������룬���û��෽��
		
    	//R110506-0421���ǵ�������Ĳ����ͬ����ۼӵ�����ͬ�ϣ��������  by zhiyuan_tang 2010-05-16
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
    	
		//֮ǰ��Ӧ������ͬҪ��ȥ�ϴεĲ��Ƴɱ����
		subtractAmountToMainContract(ctx, model.getId());
		//���ڶ�Ӧ������ͬҪ������εĲ��Ƴɱ����
		if (lastModel != null) {
			addAmountToMainContract(ctx, lastModel);
		}
    	
    	boolean newCostSplit = model.isIsCoseSplit();//Ĭ��Ϊ��ǰ�޶�ֵ
    	boolean oldCostSplit = model.isIsCoseSplit();//Ŀǰ��֧���޸��Ƿ���붯̬�ɱ�
		// �����������޶���ͬ��
    	if(lastModel!=null){
    		newCostSplit = lastModel.isIsCoseSplit();
    		synToContractBill(ctx, lastModel);
    	}else{
    		revertContract(ctx, model);
    	}
		
		// ��ͬ�޶�����ʱ ���º�ִͬ�б���Ӧ����
		ContractExecInfosFactory.getLocalInstance(ctx).updateChange(ContractExecInfosInfo.EXECINFO_UNAUDIT, conId);
		
		// ����Ƿ���붯̬�ɱ���״̬����,��������в��
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
				// ���ݺ�ͬԭ�ҽ���ֽ��Ա�ˢ�º�ͬ�����ֱ�Ĳ��״̬�ֶ�
				// refreshContractSplitState(ctx, conId);
				ClearSplitFacadeFactory.getLocalInstance(ctx).clearAllSplit(conId, false);

				// ͬʱɾ���½����ݱ��е�����
				PeriodInfo currentPeriod = model.getPeriod();
				if (currentPeriod != null) {
					CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(conId, currentPeriod.getId().toString());
				}

			}

		} else {
			if (oldCostSplit != newCostSplit) {
				if (oldCostSplit) {
					// ��ճɱ����
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("contractBill.id", conId);

					// ɾ��������
					PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
					// ɾ��������
					SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
					// ɾ��������
					ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
					// ɾ����ͬ���
					ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
				} else {// ����ǳɱ���Ĳ��
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
						// ��ճɱ����
						FilterInfo filter = new FilterInfo();
						filter.appendFilterItem("contractBill.id", conId);

						// ɾ��������
						PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
						// ɾ��������
						SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
						// ���º�ͬ���Ϊ���ֲ��
						//�˴�Ϊʲô���������жϺ�ͬ�Ƿ��ֹ�����ֱ�ӽ���ͬ�Ĳ��״̬����Ϊ"���ֲ��"�أ�����������ֻҪ������"��ͬ�޶�����"�ú�ͬ�Ͳ����ٽ����κ��޸�
						//��ʱ��Ϊ�жϺ�ͬ�Ƿ��й���֣�����й���־�����Ϊ"���ֲ��"���������û��ֹ��ú�ͬ�Ͳ����κβ���   by Cassiel_peng  2009-8-29
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						builder.appendSql("select fid from T_CON_ContractCostSplit where fcontractbillid=? ");
						builder.addParam(conId);
						IRowSet rowSet=builder.executeQuery();
						try {
							if(rowSet.next()){
								
								// �ɱ��ࣺ���Զ����������ʱ���ֲ��(��ǰ�߼�)������(���ò���)�Զ���ֲ���״̬Ϊȫ�����
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
					} else {// ����ǳɱ���Ĳ��
						FilterInfo filter = new FilterInfo();
						filter.appendFilterItem("contractBill.id", conId);

						PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
						SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
						
						
						//��û���жϺ�ͬ�Ƿ��ֹ���ֱ�ӽ���ͬ�Ĳ��״̬����Ϊ"���ֲ��"Ӧ���ǲ������
						//���޸�Ϊ�Ѿ���ֹ��ĺ�ͬ�����ò��״̬  by Cassiel_peng  2009-8-29
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						builder.appendSql("select fid from T_CON_ConNoCostSplit where fcontractBillId=? ");
						builder.addParam(conId);
						IRowSet rowSet=builder.executeQuery();
						try {
							if(rowSet.next()){
								// ���º�ͬ���Ϊ���ֲ��
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
		// ����δ�����ĸ������뵥�Լ�����ĺ�ͬ���Ϊ�޶���Ľ�� by sxhong 2008��12��19
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
						"��ͬ�������С�ں�ͬ�Ĺ���ȷ�ϵ��������ۼƽ������޸Ĺ�����ȷ�ϵ���"));
			}
		}
		try {
			synContractProgAmt(ctx, model, false);
		} catch (SQLException e) {
			logger.error(e);
		}
    }
    
    //���
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
    	
		//R110506-0421���ǵ�������Ĳ����ͬ����ۼӵ�����ͬ�ϣ��������  by zhiyuan_tang 2010-05-16
		//֮ǰ��Ӧ������ͬҪ��ȥ�ϴεĲ��Ƴɱ����
    	if (lastModel != null) {
    		subtractAmountToMainContract(ctx, lastModel.getId());
    	}
    	// TODO �����Ѿ������޶������ͬ��,��ʵ���������ǿ���
		//���ڶ�Ӧ������ͬҪ������εĲ��Ƴɱ����
		addAmountToMainContract(ctx, model);
		
		boolean newCostSplit = model.isIsCoseSplit();
		// ͬ����ͬ
		synToContractBill(ctx, model);
		boolean oldCostSplit = model.getBoolean("oldCostSplit");
		
		// ��ͬ�޶�����ʱ ���º�ִͬ�б���Ӧ����
		ContractExecInfosFactory.getLocalInstance(ctx).updateChange(ContractExecInfosInfo.EXECINFO_AUDIT, conId);
		
		// ����Ƿ���붯̬�ɱ���״̬����,��������в��
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
				// ���ݺ�ͬԭ�ҽ���ֽ��Ա�ˢ�º�ͬ�����ֱ�Ĳ��״̬�ֶ�
				// refreshContractSplitState(ctx, conId);
				ClearSplitFacadeFactory.getLocalInstance(ctx).clearAllSplit(conId, false);

				// ͬʱɾ���½����ݱ��е�����
				PeriodInfo currentPeriod = model.getPeriod();
				if (currentPeriod != null) {
					CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(conId, currentPeriod.getId().toString());
				}

			}

		} else {
			if (oldCostSplit != newCostSplit) {
				if (oldCostSplit) {
					// ��ճɱ����
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("contractBill.id", conId);

					// ɾ��������
					PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
					// ɾ��������
					SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
					// ɾ��������
					ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
					// ɾ����ͬ���
					ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
				} else {// ����ǳɱ���Ĳ��
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
						// ��ճɱ����
						FilterInfo filter = new FilterInfo();
						filter.appendFilterItem("contractBill.id", conId);

						// ɾ��������
						PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
						// ɾ��������
						SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
						// ���º�ͬ���Ϊ���ֲ��
						//�˴�Ϊʲô���������жϺ�ͬ�Ƿ��ֹ�����ֱ�ӽ���ͬ�Ĳ��״̬����Ϊ"���ֲ��"�أ�����������ֻҪ������"��ͬ�޶�����"�ú�ͬ�Ͳ����ٽ����κ��޸�
						//��ʱ��Ϊ�жϺ�ͬ�Ƿ��й���֣�����й���־�����Ϊ"���ֲ��"���������û��ֹ��ú�ͬ�Ͳ����κβ���   by Cassiel_peng  2009-8-29
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						builder.appendSql("select fid from T_CON_ContractCostSplit where fcontractbillid=? ");
						builder.addParam(conId);
						IRowSet rowSet=builder.executeQuery();
						try {
							if(rowSet.next()){
								//�ɱ��ࣺ���Զ����������ʱ���ֲ��(��ǰ�߼�)������(���ò���)�Զ���ֲ���״̬Ϊȫ����� by hpw 2010-06-25
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
						
					} else {// ����ǳɱ���Ĳ��
						FilterInfo filter = new FilterInfo();
						filter.appendFilterItem("contractBill.id", conId);

						PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
						SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
						
						
						//��û���жϺ�ͬ�Ƿ��ֹ���ֱ�ӽ���ͬ�Ĳ��״̬����Ϊ"���ֲ��"Ӧ���ǲ������
						//���޸�Ϊ�Ѿ���ֹ��ĺ�ͬ�����ò��״̬  by Cassiel_peng  2009-8-29
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						builder.appendSql("select fid from T_CON_ConNoCostSplit where fcontractBillId=? ");
						builder.addParam(conId);
						IRowSet rowSet=builder.executeQuery();
						try {
							if(rowSet.next()){
								// ���º�ͬ���Ϊ���ֲ��
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
		// ����δ�����ĸ������뵥�Լ�����ĺ�ͬ���Ϊ�޶���Ľ�� by sxhong 2008��12��19
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
						"��ͬ�������С�ں�ͬ�Ĺ���ȷ�ϵ��������ۼƽ������޸Ĺ�����ȷ�ϵ���"));
			}
		}
		
		try {
			synContractProgAmt(ctx, model, true);
		} catch (SQLException e) {
			logger.error(e);
		}
		// �������
		moneyCount(ctx, model);
	}
    //������Ļ��� 
    private void moneyCount(Context ctx,ContractBillReviseInfo model) throws EASBizException, BOSException
    {
    	String conId = model.getContractBill().getId().toString();
    	String prjId = model.getCurProject().getId().toString();
    	BigDecimal changeAmount = model.getRevLocalAmount();// ����
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
			// TODO ��Ҫ��fdetail��frowkey����
			builder.appendSql("select fcontent from T_CON_ContractBillEntry where fparentid =? and frowkey = 'lo'");
			builder.addParam(suppContract.getId().toString());
			IRowSet rs = builder.executeQuery();
			try {
				if (rs.next()) {
					String temp = rs.getString("fcontent");
					if ("��".equals(temp)) {
						builder.clear();
						builder.appendSql("select fcontent from T_CON_ContractBillEntry where fparentid = ? and frowkey = 'am'");
						builder.addParam(suppContract.getId().toString());
						IRowSet rs2 = builder.executeQuery();
						if (rs2.next()) {
							String temp2 = rs2.getString("fcontent");
							// ��Ŷ����¼����ԭ��
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
	 * �滮���=�滮���-���޶����ǩԼ���+��������������=���ƽ��-�޶����ǩԼ���
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
		// �滮���
		BigDecimal balanceAmt = pcInfo.getBalance();
		// �������
		BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
		// ��ͬǩԼ���
		BigDecimal signAmount = model.getAmount();
		// �޶����ͬ���ҽ��
		BigDecimal revLocalAmount = model.getRevLocalAmount();
		// ��ܺ�ԼǩԼ���
		BigDecimal signUpAmount = pcInfo.getSignUpAmount();
		// ���
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
		// ���������ĺ�Լ�滮�汾���
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
		// �˹���Ϊ��ϸ��Ϣ����
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
    
	//�ύʱУ�鵥���ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ
	protected void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
		//�������ڵ�ǰ�ɱ��ڼ�֮ǰ
		ContractBillReviseInfo contractBill = (ContractBillReviseInfo)model;
		
		//�Ƿ����ò���һ�廯
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
				//�����ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
	}
	
	//���У��
	private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
    
		ContractBillReviseInfo model = (ContractBillReviseInfo)billInfo;

        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
        	model= this.getContractBillReviseInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
//        ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new ObjectUuidPK(billId.toString()),sic);
		//��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
			
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();	
			//�ɱ��Ѿ��½�
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
	
	
	
	
	
	//�����У��
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
		ContractBillReviseInfo model = (ContractBillReviseInfo)billInfo;
		// �˹���Ϊ��ϸ��Ϣ����
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
		//��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
			
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();

			//�����ڼ��ڹ�����Ŀ��ǰ�ڼ�֮ǰ�����ܷ����
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
	 * ��ͬ�޶�ʱ���޶����ԭ�ҽ���븶����ԭ�ҽ��бȽ�
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
	 * ������Լ��֤�𼰷���
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
		//ҵ���������������� ���Կ�����ô��
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
     * ͬ������ͬ
     * @param ctx
     * @param info
     * @throws BOSException
     * @throws EASBizException
     */
    private void synToContractBill(Context ctx, ContractBillReviseInfo info)  throws BOSException, EASBizException {
    	//��ͬID
    	BOSUuid id = info.getContractBill().getId();
    	
    	/**
    	 * �޶��˺�֮ͬ�������ͬ��������仯����Ҫͬ������ͬ��Լ��֤�𼰷������֡����������з�����ؽ��ͱ���  by Cassiel_peng 2008-8-30
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
    	//�½�һ���հ׺�ͬ
    	ContractBillInfo newConInfo = new ContractBillInfo();
    	
    	//�Ƚ��޶�Entry���Ƶ���ͬ��Entry
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
		
		//���޶�entry���
		info.put("entrys", null);
		
		// ���������� add by zhiyuan_tang 2010/01/007
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
		
    	//���޶����������Ը��Ƶ���ͬ
    	newConInfo.putAll(info);
    	newConInfo.put("entrys", conCol);
    	newConInfo.put("payItems", payCol);
    	
    	//��ԭһЩ���ܸĵ�����
    	
    	newConInfo.setId(contractBillInfo.getId());
    	newConInfo.setCreateTime(contractBillInfo.getCreateTime());
    	newConInfo.setLastUpdateTime(contractBillInfo.getLastUpdateTime());
    	newConInfo.setCreator(contractBillInfo.getCreator());
    	newConInfo.setAuditor(contractBillInfo.getAuditor());
    	newConInfo.setAuditTime(contractBillInfo.getAuditTime());
    	newConInfo.setLastUpdateUser(contractBillInfo.getLastUpdateUser());
    	newConInfo.setState(contractBillInfo.getState());
    	newConInfo.setIsPartAMaterialCon(contractBillInfo.isIsPartAMaterialCon());
    	newConInfo.setPeriod(contractBillInfo.getPeriod());//��ͬ�Ķ����ڼ䲻�����޸� by cassiel 2010-08-25
    	//�����ֶξ��Բ��ܶ���������ǰûע�⣬���º�ͬ�������޶�֮�󸶿����뵥�Ľ�ֹ�����ۼ�ʵ��(��ͬ�ڹ��̿��Ӧ�ۼ׹��Ŀ�)����������  by cassiel_peng 2010-04-13
    	newConInfo.setPrjPriceInConPaid(contractBillInfo.getPrjPriceInConPaid());
    	newConInfo.setPaidPartAMatlAmt(contractBillInfo.getPaidPartAMatlAmt());
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	//��������˲���Ҫ�Ѻ�ͬ��źͺ�ͬ����ͬ����ȥ
    	boolean isOk = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_CANMODIFYCONTRACTNUMBER);
    	if(isOk){
    		newConInfo.setMainContractNumber(info.getMainContractNumber());
    		//newConInfo.setMainContractNumber(info.getNumber());
    		newConInfo.setNumber(info.getNumber());
//    		newConInfo.setName(info.getMainContractNumber());
    		//���¸������뵥
    		builder.appendSql("UPDATE T_CON_PAYREQUESTBILL SET FCONTRACTNO = ?,FCONTRACTNAME = ? WHERE FCONTRACTID = ?");
    		builder.addParam(newConInfo.getNumber());
    		builder.addParam(newConInfo.getName());
    		builder.addParam(newConInfo.getId().toString());
    		builder.execute();
    		
    		//�������в����ͬ������ͬ����
    		builder.clear();
    		builder.appendSql("update t_con_contractbillentry set fcontent = ? where  frowkey = 'na' and fparentid in (select fparentid from t_con_contractbillentry where fcontent = ? and frowkey ='nu')");
    		builder.addParam(newConInfo.getName());
    		builder.addParam(contractBillInfo.getId().toString());
    		builder.execute();
    		builder.clear();
    		
    		
    		//���¸��������
    	   builder.clear();
    	   builder.appendSql("UPDATE T_CAS_PAYMENTBILL SET fcontractno = ?  WHERE fcontractbillid = ?");
    	   builder.addParam(newConInfo.getNumber());
    	   builder.addParam(newConInfo.getId().toString());
    	   builder.execute();
    	   builder.clear();
    		
    	}
    	 
    	// ��ǩԼ����޶�����ֱ�Ӹ�ֵ,monyCount���������������ͬԭ�Ҽ�����
    	newConInfo.setCeremonyb(info.getRevAmount());
		newConInfo.setCeremonybb(info.getRevLocalAmount());
		
		
    	//ԭ�ҽ����setOriginalAmount
		// newConInfo.setOriginalAmount(info.getRevAmount());
		// newConInfo.setAmount(info.getRevLocalAmount());
    	//���ò��Ƴɱ��Ľ��
    	
    	//������Լ��֤�𼰷�������
		ContractBailInfo bailInfo = null;
		if (info.getBail() != null) {
			bailInfo = new ContractBailInfo();
			bailInfo.setProp(info.getBail().getProp());
			bailInfo.setAmount(info.getBail().getAmount());
			//�����¼
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
    	
//    	//������Լ��֤�𼰷�������  by Cassiel_peng 
//    	BigDecimal oldContractAmount=FDCHelper.toBigDecimal(info.getRevAmount(),2);//�޶����ͬ�Ľ��
//    	ContractBailInfo bailInfo=contractBillInfo.getBail();
//    	if(bailInfo!=null){
//    		if(bailInfo.getProp()!=null){//�����Լ��֤�������Ϊ��
//    			//���ݴ˴��޶��ĺ�ͬ������Լ��֤�����������Լ��֤����ԭ��
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
//    	//����������
//    	ContractPayItemCollection conPayItemColleciton=new ContractPayItemCollection();
//    	builder.appendSql("select  FPayItemDate,FPaymentTypeid,FPayCondition,FProp,FAmount,FDesc,FContractBillId,FID from t_con_contractpayitem where fcontractbillid=? \n ");
////    	builder.appendSql("select  FProp,FContractBillId,FID from t_con_contractpayitem where fcontractbillid=? \n ");
//    	builder.addParam(id.toString());
//    	IRowSet rowSet=builder.executeQuery();
//    	try {
//			while(rowSet.next()){
//				BigDecimal payRate=rowSet.getBigDecimal("FProp");
//				ContractPayItemInfo conPayItemInfo=new ContractPayItemInfo();
//				//���㸶������Ľ��
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
//				//̫�����ˣ��������ַ�����Ȼ�����ܸ��¸�����������ˣ�
//				//�������
//				conPayItemInfo.setProp(payRate);
//				//��������
//				conPayItemInfo.setPayItemDate(rowSet.getDate("FPayItemDate"));
//				//���渶������
//				PaymentTypeInfo payTypeInfo=new PaymentTypeInfo();
//				if(rowSet.getString("FPaymentTypeid")!=null&&!"".equals(rowSet.getString("FPaymentTypeid"))){
//					payTypeInfo.setId(BOSUuid.read(rowSet.getString("FPaymentTypeid")));
//				}
//				conPayItemInfo.setPaymentType(payTypeInfo);
//				//���渶������
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
//    	//����ں�ͬ��ͨ����ͬ����Ӳ�ǲ���(ֻ��ContractBailInfoͷ���Ը���,ContractBailEntryInfo��¼ȴ���ܸ���)��Ӳ�ǵõ����Լ��Ľӿڸ�������   by Cassiel_peng
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
	
	//������ʱ���༭�����ʼ���ݴ����ȷ���
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		
		Map initMap = super._fetchInitData(ctx,paramMap);	
		//������Ŀ
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
	 * �����������Ƴɱ���������ͬ�м����������²��״̬ ���ﲻ�ô���ǩԼ����addAmountToMainContract��
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
		//����ǷǶ�������Ĳ����ͬ,����ۼӵ�
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
				//�������ͬ����ȫ��� ���������������ͬ
				if(splitState!=null &&splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
					throw new ContractException(ContractException.HASALLAPLIT);
				}
				//��������ͬ���
				String mainId = rs.getString("mainId");
				//�����ͬ���
				BigDecimal supAmount = rs.getBigDecimal("amount");
				
				// ����ͬ���ҽ��
				BigDecimal mainAmount = rs.getBigDecimal("mainAmount");
				//����ͬԭ�ҽ��
				BigDecimal oriAmount = rs.getBigDecimal("oriAmount");
				//����ͬ����
				BigDecimal mainRate = rs.getBigDecimal("mainRate");
				//����ͬ���ޱ���
				BigDecimal grtRate = rs.getBigDecimal("grtRate");
				//����ͬ���޽��
				BigDecimal grtAmount = rs.getBigDecimal("grtAmount");
				//����ͬӡ��˰��
				BigDecimal stampRate = rs.getBigDecimal("stampRate");
				//����ͬӡ��˰���
				BigDecimal stampAmt = rs.getBigDecimal("stampAmt");
				
				String updateSql = "update T_CON_Contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
//					String updateSql = "update T_CON_Contractbill set FAmount=?,FlocalAmount=? where Fid=?";
				//DbUtil.execute(ctx,updateSql,new Object[]{mainAmount.subtract(supAmount),localAmount.subtract(supAmount),mainId});
				
				//R101227-311��ͬ������ʱ���� �Ƚ��п�ָ�봦��
		    	//�����ͬ��� ��λ��
				if(supAmount==null){
					supAmount = FDCConstants.ZERO;
				}
		    	BigDecimal revAmount = supAmount.multiply(mainRate);
		    	//�������ͬ�Ѳ�ֽ������������� �򲹳��ͬ���ܷ�����
		    	String splitSql = "select famount from T_CON_ContractCostSplit where fcontractBillId=?";
		    	IRowSet ir = DbUtil.executeQuery(ctx,splitSql,new Object[]{mainId.toString()});
		    	if(ir!=null && ir.next()){
		    		BigDecimal splitAmount = ir.getBigDecimal("famount");
		    		if(splitAmount.compareTo(mainAmount.subtract(revAmount))==1){
		    			throw new ContractException(ContractException.HASSPLIT);
		    		}
		    	}
				//����������뵥���ۼ��������Ѿ�����revAmount
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
		    	//���±��޽��
		    	BigDecimal newGrtAmount = FDCConstants.ZERO;
		    	if(grtRate.compareTo(FDCConstants.ZERO)==1){
		    		newGrtAmount = (oriAmount.subtract(supAmount)).multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED,4,2);
		    	}
		    	//����ӡ��˰���
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
				
				//R110228-280��¼��ǵ�������Ĳ����֮ͬ������ͬ��ʾΪȫ�����(֮ǰû�п��Ǻ�ͬ�����붯̬�ɱ������)
				ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
				if (contractbill.isIsCoseSplit()) {
					
					//�ɱ��ࣺ���Զ����������ʱ���ֲ�֣������Զ���ֲ���״̬Ϊȫ����� by hpw 2010-06-25
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
								//�������ͬԭ����������ͬ�Ѳ�ֽ�� ��������ͬ���״̬Ϊ��ȫ���
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
					
					//�ǳɱ��ࣺ����״̬Ϊ���ֲ��
					ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
					for(int i=0;i < col.size();i++){
						ConNoCostSplitInfo info = col.get(i);
						if(info!=null){
							//�������ͬԭ����������ͬ�Ѳ�ֽ�� ��������ͬ���״̬Ϊ��ȫ���
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
				
		    	//���º�ͬ����ƻ�    	
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
			/*****��������ͬ�ĺ�ִͬ��������ͬ�ɱ���� -by neo****/
			ContractExecInfosFactory.getLocalInstance(ctx).
					updateSuppliedContract("audit", mainId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}

	/**
	 * �����������Ƴɱ�����ۼӵ�����ͬ�У������²��״̬ ��������ǩԼ���
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
		//����ǷǶ�������Ĳ����ͬ,����ۼӵ�
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
				//��������ͬ���
				String splitState = rs1.getString("splitState");
				String mainId = rs1.getString("mainId");
				//�����ͬ���
				BigDecimal supAmount = rs1.getBigDecimal("amount");
				// ����ͬǩԼԭ��
				BigDecimal ceremonyb = rs1.getBigDecimal("ceremonyb");
				// ����ͬǩԼ����
				BigDecimal ceremonybb = rs1.getBigDecimal("ceremonybb");
				//����ͬ��λ�ҽ��
				BigDecimal mainAmount = rs1.getBigDecimal("mainAmount");
//					BigDecimal localAmount = rs1.getBigDecimal("mainLocalAmount");
				//����ͬԭ�ҽ��
				BigDecimal oriAmount = rs1.getBigDecimal("oriAmount");
				//����ͬ����
				BigDecimal mainRate = rs1.getBigDecimal("mainRate");
				//����ͬ���ޱ���
				BigDecimal grtRate = rs1.getBigDecimal("grtRate");
				//����ͬ���޽��
				BigDecimal grtAmount = rs1.getBigDecimal("grtAmount");
				//����ͬӡ��˰��
				BigDecimal stampRate = rs1.getBigDecimal("stampRate");
				//����ͬӡ��˰���
				BigDecimal stampAmt = rs1.getBigDecimal("stampAmt");
				//��������ͬԭ�Һͱ�λ�ҽ��
				String updateSql = "update T_CON_Contractbill set FCeremonyb=?, FCeremonybb=?,ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
				
		    	//ԭ�ҽ��
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
		    	//�������� by hpw 2011.10.26
		    	if(FDCHelper.compareTo(model.getRevAmount(), model.getContractBill().getOriginalAmount())!=0){
		    		//��ȥԭ���ģ��ټ������µ�,���һ�����ͬҲֱ�ӼӼ�
		    		oriAmount = FDCHelper.subtract(oriAmount, model.getContractBill().getOriginalAmount()).add(revAmount);
		    		mainAmount= FDCHelper.subtract(mainAmount, model.getContractBill().getAmount()).add(supAmount);
		    	}
		    	// ֱ�ӵ����޶����������ͬ
				ceremonyb = revAmount;
				ceremonybb = supAmount;
		    	
		    	//���±��޽��
		    	BigDecimal newGrtAmount = FDCConstants.ZERO;
		    	if(grtRate.compareTo(FDCConstants.ZERO)==1){
		    		newGrtAmount = oriAmount.multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED,4,2);
		    	}
		    	//����ӡ��˰���
		    	BigDecimal newStampAmt = FDCConstants.ZERO;
		    	if(stampRate.compareTo(FDCConstants.ZERO)==1){
		    		newStampAmt = oriAmount.multiply(mainRate).multiply(stampRate).divide(FDCConstants.ONE_HUNDRED,4,2);
		    	}
		    	
		    	DbUtil.execute(ctx, updateSql, new Object[] { ceremonyb, ceremonybb, oriAmount, mainAmount, newGrtAmount, newStampAmt, mainId });
						    		
				//�ж�����ͬ�Ľ�� �� ��ͬ��ֵ��Ѳ�ֽ�� ���Ƿ���ȡ�������ȣ�������ͬ�ͺ�ͬ��ֵĲ��״̬��Ϊ���ֲ��
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
				
				//R110228-280��¼��ǵ�������Ĳ����֮ͬ������ͬ��ʾΪȫ�����(֮ǰû�п��Ǻ�ͬ�����붯̬�ɱ������)
				ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
				if (contractbill.isIsCoseSplit()) {
					
					//�ɱ��ࣺ���Զ����������ʱ���ֲ��(��ǰ�߼�)������(���ò���)�Զ���ֲ���״̬Ϊȫ����� by hpw 2010-06-25
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
								//��������ͬ+����ͬ����������ͬ�Ѳ�ֽ�� ��������ͬ���״̬Ϊ���ֲ��
								if((mainAmount.add(revAmount)).compareTo(info.getBigDecimal("amount"))==1
										&& splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
									builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.getSql();
									builder.execute();
									builder.clear();//������New
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
					//�ǳɱ��ࣺ����״̬Ϊ���ֲ��
					ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
					for(int i=0;i < col.size();i++){
						ConNoCostSplitInfo info = col.get(i);
						if(info!=null){
							//��������ͬ+����ͬ����������ͬ�Ѳ�ֽ�� ��������ͬ���״̬Ϊ���ֲ��
							if((mainAmount.add(revAmount)).compareTo(info.getBigDecimal("amount"))==1
									&& splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
								builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
								builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
								builder.addParam(mainId.toString());
								builder.getSql();
								builder.execute();
								builder.clear();//������New
								builder.appendSql("update T_CON_ConNoCostSplit set fsplitState=? where fcontractbillid=?");
								builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
								builder.addParam(mainId.toString());
								builder.execute();
								builder.clear();
							}
						}
					}
				}
				
		    	//���º�ͬ����ƻ�    					
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
			/*****��������ͬ�ĺ�ִͬ��������ͬ�ɱ���� -by neo****/
			ContractExecInfosFactory.getLocalInstance(ctx).
					updateSuppliedContract("audit", mainId);
			}else{
				
				//TODO �޸��Ƿ񵥶���������δ֧��  by hpw
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}
	
}