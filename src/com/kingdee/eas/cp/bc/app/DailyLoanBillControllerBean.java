package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.bot.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.*;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.*;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.*;
import com.kingdee.eas.fi.cas.*;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.ma.bg.*;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.kingdee.eas.cp.bc.app:
//            AbstractDailyLoanBillControllerBean

public class DailyLoanBillControllerBean extends AbstractDailyLoanBillControllerBean
{

    public DailyLoanBillControllerBean()
    {
    }

    private void reWriteBillState(Context ctx, String id, StateEnum state)
        throws BOSException, EASBizException
    {
        IExpenseAccountFacade facade = ExpenseAccountFacadeFactory.getLocalInstance(ctx);
        facade.setState(BOSUuid.read(id), state);
    }

    private void reWriteBillState(Context ctx, BOSUuid id, StateEnum state)
        throws BOSException, EASBizException
    {
        IExpenseAccountFacade facade = ExpenseAccountFacadeFactory.getLocalInstance(ctx);
        facade.setState(id, state);
    }

    protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
        throws BOSException, EASBizException
    {
        super._addnew(ctx, pk, model);
    }

    protected void _delete(Context ctx, IObjectPK pk)
        throws BOSException, EASBizException
    {
        ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
        DailyLoanBillInfo info = (DailyLoanBillInfo)getValue(ctx, pk);
        String orgId = null;
        orgId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
        if(BizCollUtil.objectIsNull(orgId))
            orgId = BizCollUtil.getNextCompanyId(ctx);
        abortWorkingWf(ctx, pk);
        super._delete(ctx, pk);
        boolean returnvalue;
        if(iCodingRuleManager.isExist(info, orgId) && iCodingRuleManager.isUseIntermitNumber(info, orgId))
            returnvalue = iCodingRuleManager.recycleNumber(info, orgId, info.getNumber());
    }

    protected IObjectPK _addnew(Context ctx, IObjectValue model)
        throws BOSException, EASBizException
    {
        return super._addnew(ctx, model);
    }

    protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
        throws BOSException, EASBizException
    {
        super._update(ctx, pk, model);
    }

    protected void checkAddNew(Context ctx, IObjectValue model, String action)
        throws BOSException, EASBizException
    {
        BizCollUtil.verdictRuleNumber(ctx, model);
        if("submit".equals(action))
            checkValidate(ctx, (DailyLoanBillInfo)model);
        else
        if("save".equals(action))
            checkSaveEntry((DailyLoanBillInfo)model);
        checkNumberDup(ctx, model);
    }

    protected void checkUpdate(Context ctx, IObjectValue model, String action)
        throws BOSException, EASBizException
    {
        if("submit".equals(action))
            checkValidate(ctx, (DailyLoanBillInfo)model);
        else
        if("save".equals(action))
            checkSaveEntry((DailyLoanBillInfo)model);
        checkNumberDup(ctx, model);
    }

    protected void checkSaveEntry(DailyLoanBillInfo info)
        throws BOSException, EASBizException
    {
        if(objectIsNull(info.getNumber()))
            throw new ExpAccException(ExpAccException.NUMBER_NOT_NULL);
        int size = info.getEntries().size();
        DailyLoanBillEntryInfo entryInfo = null;
        for(int i = 0; i < size; i++)
        {
            entryInfo = info.getEntries().get(i);
            if(!BizCollUtil.objectIsNull(entryInfo.getPurpose()) && entryInfo.getPurpose().trim().length() > 200)
                throw new DailyLoanBillException(DailyLoanBillException.PURPOSE_TOO_LONG);
            if(!BizCollUtil.objectIsNull(entryInfo.getComment()) && entryInfo.getComment().trim().length() > 200)
                throw new DailyLoanBillException(DailyLoanBillException.COMMENT_TOO_LONG);
        }

    }

    protected void checkNumberDup(Context ctx, IObjectValue model)
        throws BOSException, EASBizException
    {
        DailyLoanBillInfo info = (DailyLoanBillInfo)model;
        FilterInfo filter = new FilterInfo();
        FilterItemInfo filterItem = new FilterItemInfo("number", info.getNumber(), CompareType.EQUALS);
        filter.getFilterItems().add(filterItem);
        if(info.getId() != null)
        {
            filterItem = new FilterItemInfo("id", info.getId(), CompareType.NOTEQUALS);
            filter.getFilterItems().add(filterItem);
            filter.setMaskString("#0 and #1");
        }
        if(super._exists(ctx, filter))
            throw new DailyLoanBillException(DailyLoanBillException.NUMBER_NOT_DUP);
        else
            return;
    }

    private boolean objectIsNull(Object obj)
    {
        return obj == null || StringUtils.isEmpty(obj.toString());
    }

    protected void checkValidate(Context ctx, DailyLoanBillInfo info)
        throws EASBizException
    {
        if(!StringUtils.isEmpty(info.getName()) && info.getName().trim().length() > 80)
            throw new DailyLoanBillException(DailyLoanBillException.NAME_TOO_LONG);
        if(StringUtils.isEmpty(info.getNumber()))
            throw new DailyLoanBillException(DailyLoanBillException.NUMBER_NOT_NULL);
        if(info.getNumber().trim().length() > 80)
            throw new DailyLoanBillException(DailyLoanBillException.NUMBER_TOO_LONG);
        if(BizCollUtil.objectIsNull(info.getBizReqDate()))
            throw new DailyLoanBillException(DailyLoanBillException.REQDATE_NOT_NULL);
        if(BizCollUtil.objectIsNull(info.getCurrencyType()) && BizCollUtil.objectIsNull(info.getCurrencyType().getId()))
            throw new DailyLoanBillException(DailyLoanBillException.CURRENCYTYPE_NOT_NULL);
        if(BizCollUtil.objectIsNull(info.getCostedDept()) && (info.getCostedDept() == null || BizCollUtil.objectIsNull(info.getCostedDept().getId())))
            throw new DailyLoanBillException(DailyLoanBillException.COSTEDDEPT_NOT_NULL);
        if(!BizCollUtil.isBizUnitCostCenter(ctx, info.getCostedDept()))
            throw new ExpAccException(ExpAccException.COSTCENTER_NOT_BIZUNIT);
        if(BizCollUtil.objectIsNull(info.getApplier()) && (info.getApplier() == null || BizCollUtil.objectIsNull(info.getApplier().getId())))
            throw new DailyLoanBillException(DailyLoanBillException.APPLIER_NOT_NULL);
        if(BizCollUtil.objectIsNull(info.getOrgUnit()) && (info.getOrgUnit() == null || BizCollUtil.objectIsNull(info.getOrgUnit().getId())))
            throw new DailyLoanBillException(DailyLoanBillException.ORGUNIT_NOT_NULL);
        if(BizCollUtil.objectIsNull(info.getCompany()) && (info.getCompany() == null || BizCollUtil.objectIsNull(info.getCompany().getId())))
            throw new DailyLoanBillException(DailyLoanBillException.COMPANY_NOT_NULL);
        if(BizCollUtil.objectIsNull(info.getPrior()))
            throw new DailyLoanBillException(DailyLoanBillException.PRIOR_NOT_NULL);
        if(BizCollUtil.objectIsNull(info.getBillDate()))
            throw new DailyLoanBillException(DailyLoanBillException.BILLDATE_NOT_NULL);
        if(BizCollUtil.objectIsNull(info.getBiller()) && BizCollUtil.objectIsNull(info.getBiller().getId()))
            throw new DailyLoanBillException(DailyLoanBillException.BILLER_NOT_NULL);
        if(!BizCollUtil.objectIsNull(info.getCause()) && info.getCause().trim().length() > 200)
            throw new DailyLoanBillException(DailyLoanBillException.CAUSE_TOO_LONG);
        if(!BizCollUtil.objectIsNull(info.getDescription()) && info.getDescription().trim().length() > 1000)
            throw new DailyLoanBillException(DailyLoanBillException.DESCRIPTION_TOO_LONG);
        if(BizCollUtil.objectIsNull(info.getEntries()))
            throw new DailyLoanBillException(DailyLoanBillException.ENTRY_NOT_NULL);
        int size = info.getEntries().size();
        if(size <= 0)
            throw new DailyLoanBillException(DailyLoanBillException.ENTRY_NOT_NULL);
        DailyLoanBillEntryInfo entryInfo = null;
        for(int i = 0; i < size; i++)
        {
            entryInfo = info.getEntries().get(i);
            if(BizCollUtil.objectIsNull(entryInfo.getPurpose()))
                throw new DailyLoanBillException(DailyLoanBillException.PURPOSE_NOT_NULL);
            if(entryInfo.getPurpose().trim().length() > 200)
                throw new DailyLoanBillException(DailyLoanBillException.PURPOSE_TOO_LONG);
            if(!BizCollUtil.objectIsNull(entryInfo.getComment()) && entryInfo.getComment().trim().length() > 200)
                throw new DailyLoanBillException(DailyLoanBillException.COMMENT_TOO_LONG);
        }

    }

    protected void _setPassState(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        if(BizCollUtil.objectIsNull(id))
        {
            return;
        } else
        {
            DailyLoanBillInfo info = getDailyLoanBillInfo(ctx, new ObjectUuidPK(id));
            info.setState(StateEnum.CHECKED);
            info.setAuditDate(new Date());
            info.setAuditor((UserInfo)ctx.get("UserInfo"));
            update(ctx, new ObjectUuidPK(id), info);
            return;
        }
    }

    protected void _setNotPassState(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        if(BizCollUtil.objectIsNull(id))
        {
            return;
        } else
        {
            DailyLoanBillInfo info = getDailyLoanBillInfo(ctx, new ObjectUuidPK(id));
            info.setState(StateEnum.CHECKFAILD);
            info.setAuditDate(new Date());
            info.setAuditor((UserInfo)ctx.get("UserInfo"));
            update(ctx, new ObjectUuidPK(id), info);
            return;
        }
    }

    protected BgCtrlParam[] _getBgParam(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        if(BizCollUtil.objectIsNull(id))
            return null;
        DailyLoanBillInfo info = getDailyLoanBillInfo(ctx, new ObjectUuidPK(id));
        BgCtrlParam params[] = new BgCtrlParam[1];
        params[0] = new BgCtrlParam();
        BgTypeInfo bgTypeInfo = BgTypeFactory.getLocalInstance(ctx).getValueByNumber("ExpenseBudget");
        params[0].setBgTypeId(bgTypeInfo.getId());
        CompanyOrgUnitInfo couInfo = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(info.getCompany().getId()));
        PeriodInfo perInfo = new PeriodInfo();
        IPeriod iPeriod = PeriodFactory.getLocalInstance(ctx);
        EntityViewInfo evi = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("beginDate", BizCollUtil.covertToSimpleDate(info.getBizReqDate()), CompareType.LESS_EQUALS));
        filter.getFilterItems().add(new FilterItemInfo("endDate", BizCollUtil.covertToSimpleDate(info.getBizReqDate()), CompareType.GREATER_EQUALS));
        filter.getFilterItems().add(new FilterItemInfo("periodType", couInfo.getAccountPeriodType().getId().toString(), CompareType.EQUALS));
        filter.setMaskString("#0 and #1 and #2");
        evi.setFilter(filter);
        PeriodCollection periodc = iPeriod.getPeriodCollection(evi);
        if(periodc.size() > 0)
            perInfo = periodc.get(0);
        if(perInfo.getId() == null)
            perInfo = null;
        params[0].setPeriodId(perInfo.getId());
        params[0].setBizOrgUnitId(info.getCostedDept().getId());
        params[0].setCurrencyId(info.getCurrencyType().getId());
        params[0].setCompanyId(info.getCompany().getId());
        params[0].setCtrlSystem(SystemEnum.BUSINESS_COLLABORATION);
        params[0].setSrcBillId(info.getId());
        params[0].setSrcBillNumber(info.getNumber());
        params[0].setBizDate(info.getBizReqDate());
        if(info.getSupportedObj() != null && !BizCollUtil.objectIsNull(info.getSupportedObj().getId()))
        {
            BOSUuid costId = info.getSupportedObj().getId();
            ICostObject iCost = CostObjectFactory.getLocalInstance(ctx);
            CostObjectInfo costInfo = iCost.getCostObjectInfo(new ObjectUuidPK(costId));
            MaterialInfo mat = new MaterialInfo();
            mat.setId(costInfo.getRelatedId());
            params[0].addDimension(BgDimensionEnum.PRODUCT, mat);
        }
        params[0].addDimension(BgDimensionEnum.ACCOUNT, info.getExpenseType());
        BgCtrlData data = new BgCtrlData();
        data.setDataType(BgDataTypeEnum.Amount);
        data.setValue(info.getAmount());
        params[0].addData(BgElementEnum.AMT, data);
        return params;
    }

    protected void _setPaymentState(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        if(BizCollUtil.objectIsNull(id))
        {
            return;
        } else
        {
            DailyLoanBillInfo info = getDailyLoanBillInfo(ctx, new ObjectUuidPK(id));
            info.setState(StateEnum.ALREADYPAYMENT);
            update(ctx, new ObjectUuidPK(id), info);
            return;
        }
    }

    protected void _setCloseState(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        if(BizCollUtil.objectIsNull(id))
        {
            return;
        } else
        {
            DailyLoanBillInfo info = getDailyLoanBillInfo(ctx, new ObjectUuidPK(id));
            info.setState(StateEnum.CLOSED);
            update(ctx, new ObjectUuidPK(id), info);
            return;
        }
    }

    protected boolean _needBgAudit(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        if(!BizCollUtil.isNeedBudgetControl(ctx, id))
            return false;
        ObjectUuidPK objpk = new ObjectUuidPK(id);
        IDynamicObject iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);
        CoreBillBaseInfo billInfo = (CoreBillBaseInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk);
        if(billInfo.getSourceBillId() == null || "".equals(billInfo.getSourceBillId()))
            return true;
        return !BizCollUtil.getBypeByBill(BizCollBillTypeEnum.EVECTION_REQ).equals(BizCollUtil.getBOSType(billInfo.getSourceBillId())) ? true : true;
    }

    protected void _reBackBg(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        DailyLoanBgParam dlBgParam = new DailyLoanBgParam();
        BgCtrlParam params[] = dlBgParam.getBgParam(ctx, id);
        IBgControl iBgControl = BgControlFactory.getLocalInstance(ctx);
        iBgControl.bgCancelAudit(params);
    }

    protected void _payment(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        reWriteBillState(ctx, id, StateEnum.ISPAYBILL);
    }

    protected void _setCheckingState(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        if(BizCollUtil.objectIsNull(id))
        {
            return;
        } else
        {
            DailyLoanBillInfo info = getDailyLoanBillInfo(ctx, new ObjectUuidPK(id));
            info.setAuditDate(new Date());
            info.setAuditor((UserInfo)ctx.get("UserInfo"));
            info.setState(StateEnum.CHECKING);
            update(ctx, new ObjectUuidPK(id), info);
            return;
        }
    }

    protected void _setDraftState(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        if(BizCollUtil.objectIsNull(id))
        {
            return;
        } else
        {
            DailyLoanBillInfo info = getDailyLoanBillInfo(ctx, new ObjectUuidPK(id));
            info.setState(StateEnum.DRAFT);
            update(ctx, new ObjectUuidPK(id), info);
            return;
        }
    }

    protected List _getCanDeleteIDs(Context ctx, List list)
        throws BOSException, EASBizException
    {
        List returnList = new ArrayList();
        if(BizCollUtil.objectIsNull(list) || list.size() == 0)
            return returnList;
        BOSUuid userId = ContextUtil.getCurrentUserInfo(ctx).getId();
        int i = 0;
        for(int n = list.size(); i < n; i++)
        {
            String id = (String)list.get(i);
            if(BizCollUtil.objectIsNull(id))
                continue;
            DailyLoanBillInfo info = getDailyLoanBillInfo(ctx, new ObjectUuidPK(id));
            if(StateEnum.DRAFT.equals(info.getState()) || StateEnum.NEW.equals(info.getState()))
                returnList.add(id);
        }

        return returnList;
    }

    protected boolean _isCanModify(Context ctx, BOSUuid id)
        throws BOSException, EASBizException
    {
        return !BizCollUtil.objectIsNull(id);
    }

    protected void _setState(Context ctx, BOSUuid id, StateEnum state)
        throws BOSException, EASBizException
    {
        if(BizCollUtil.objectIsNull(id))
        {
            return;
        } else
        {
            DailyLoanBillInfo info = getDailyLoanBillInfo(ctx, new ObjectUuidPK(id));
            info.setState(state);
            update(ctx, new ObjectUuidPK(id), info);
            return;
        }
    }

    protected void _reverseSave(Context ctx, IObjectPK srcBillPK, IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum, IObjectValue bOTRelationInfo)
        throws BOSException, EASBizException
    {
        java.util.Locale local = ctx.getLocale();
        BOTRelationInfo botObj = (BOTRelationInfo)bOTRelationInfo;
        BizCollBillBaseInfo srcObj = DailyLoanBillFactory.getLocalInstance(ctx).getDailyLoanBillInfo(new ObjectUuidPK(((BizCollBillBaseInfo)srcBillVO).getId().toString()));
        BOSObjectType type = BizCollUtil.getBOSType(botObj.getDestObjectID());
        String destObjectId = botObj.getDestObjectID();
        IDynamicObject iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);
        ObjectUuidPK objpk = new ObjectUuidPK(botObj.getBOTMappingID());
        BOTMappingInfo mappingInfo = (BOTMappingInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk);
        DailyLoanBillInfo tempDailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
        boolean isSuspenseAcc = "Y".equals(tempDailyLoanBillinfo.getActionFlag());
        if(isSuspenseAcc)
            setActionFlag(ctx, tempDailyLoanBillinfo, null);
        if((new PaymentBillInfo()).getBOSType().equals(type))
            if(BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum) || BOTBillOperStateEnum.EDIT.equals(bOTBillOperStateEnum))
            {
                if(StateEnum.CHECKED.equals(srcObj.getState()) || StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()) || StateEnum.ISSUSPENSEACCOUNT.equals(srcObj.getState()))
                {
                    logger.info(ResourceBase.getString(bizCollResource, "LoanBillBePayment", local) + srcBillPK.toString());
                    if(StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()))
                    {
                        DailyLoanBillInfo dailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
                        dailyLoanBillinfo.setFiVouchered(true);
                        SelectorItemCollection sic = new SelectorItemCollection();
                        sic.add(new SelectorItemInfo("fiVouchered"));
                        IDailyLoanBill iDailyLoanBill = null;
                        iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                        iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                        _deleteVoucher(ctx, new ObjectUuidPK(srcObj.getId()));
                        dailyLoanBillinfo.setFiVouchered(false);
                        iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                    }
                    reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ISPAYBILL);
                    logger.info(ResourceBase.getString(bizCollResource, "LoanBillHasBePayment", local));
                } else
                if(srcObj.getState().equals(StateEnum.ALREADYPAYMENT))
                {
                    DailyLoanBillInfo dailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
                    dailyLoanBillinfo.setLoanState("P");
                    SelectorItemCollection sic = new SelectorItemCollection();
                    sic.add(new SelectorItemInfo("loanState"));
                    IDailyLoanBill iDailyLoanBill = null;
                    iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                    iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                }
            } else
            if(BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum))
            {
                IPaymentBill iPaymentBill = null;
                PaymentBillInfo destObjectInfo = null;
                PaymentBillCollection paymentbillColl = new PaymentBillCollection();
                iPaymentBill = PaymentBillFactory.getLocalInstance(ctx);
                PaymentBillInfo temPaymentbill = null;
                EntityViewInfo viewInfo = new EntityViewInfo();
                FilterInfo filter = new FilterInfo();
                StringBuffer markString = new StringBuffer("");
                filter.getFilterItems().add(new FilterItemInfo("srcObjectID", botObj.getSrcObjectID(), CompareType.EQUALS));
                markString.append("#0 ");
                filter.setMaskString(markString.toString());
                viewInfo.setFilter(filter);
                BOTRelationCollection botColl = BOTRelationFactory.getLocalInstance(ctx).getCollection(viewInfo);
                int destObjectSize = botColl.size();
                Set destObjectIdSet = new HashSet();
                for(int i = 0; i < destObjectSize; i++)
                {
                    BOTRelationInfo botInfo = botColl.get(i);
                    destObjectIdSet.add(botInfo.getDestObjectID());
                }

                if(destObjectIdSet.size() > 0)
                {
                    EntityViewInfo paymentViewInfo = new EntityViewInfo();
                    FilterInfo paymentFilter = new FilterInfo();
                    StringBuffer paymentMarkString = new StringBuffer("");
                    paymentFilter.getFilterItems().add(new FilterItemInfo("id", destObjectIdSet, CompareType.INCLUDE));
                    paymentMarkString.append("#0 ");
                    paymentViewInfo.setFilter(paymentFilter);
                    paymentbillColl = iPaymentBill.getPaymentBillCollection(paymentViewInfo);
                }
                int paymentSize = paymentbillColl.size();
                SelectorItemCollection selectorColl = new SelectorItemCollection();
                selectorColl.add(new SelectorItemInfo("*"));
                if(destObjectId != null && !"".equals(destObjectId))
                    destObjectInfo = iPaymentBill.getPaymentBillInfo(new ObjectUuidPK(destObjectId), selectorColl);
                if(paymentSize == 2)
                {
                    if(paymentbillColl.get(0).getCreateTime() != null && paymentbillColl.get(1).getCreateTime() != null)
                        if(paymentbillColl.get(0).getCreateTime().after(paymentbillColl.get(1).getCreateTime()))
                            temPaymentbill = paymentbillColl.get(0);
                        else
                            temPaymentbill = paymentbillColl.get(1);
                    return;
                }
                if(paymentSize == 1)
                    temPaymentbill = paymentbillColl.get(0);
                if(paymentSize == 1 && temPaymentbill != null && destObjectInfo.getId().toString().equals(temPaymentbill.getId().toString()))
                    return;
                if(paymentSize == 1 && temPaymentbill != null && !destObjectInfo.getId().toString().equals(temPaymentbill.getId().toString()) && destObjectInfo.getCreateTime() != null && temPaymentbill.getCreateTime() != null && destObjectInfo.getCreateTime().after(temPaymentbill.getCreateTime()) && destObjectInfo.getBillStatus() != null)
                {
                    if(10 == destObjectInfo.getBillStatus().getValue())
                        if(StateEnum.CLOSED.equals(srcObj.getState()))
                            throw new BizCollException(BizCollException.ISSOURCEBILLCLOSE);
                        else
                            return;
                    if(11 == destObjectInfo.getBillStatus().getValue())
                        if(StateEnum.CLOSED.equals(srcObj.getState()))
                        {
                            throw new BizCollException(BizCollException.ISSOURCEBILLCLOSE);
                        } else
                        {
                            DailyLoanBillInfo dailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
                            dailyLoanBillinfo.setLoanState("N");
                            SelectorItemCollection sic = new SelectorItemCollection();
                            sic.add(new SelectorItemInfo("loanState"));
                            IDailyLoanBill iDailyLoanBill = null;
                            iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                            iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                            return;
                        }
                }
                if(StateEnum.CLOSED.equals(srcObj.getState()))
                    throw new BizCollException(BizCollException.ISSOURCEBILLCLOSE);
                if(isSourceBillRelatedBill(ctx, srcObj, botObj.getDestObjectID()))
                    throw new BizCollException(BizCollException.CANNOT_DELPAYMENT_HASBOTP);
                if(StateEnum.ISPAYBILL.equals(srcObj.getState()))
                {
                    logger.info(ResourceBase.getString(bizCollResource, "LoanBillIsChecked", local) + srcBillPK.toString());
                    ctx.put("isReWrite", new Boolean(true));
                    if("R".equals(((DailyLoanBillInfo)srcObj).getActionFlag()))
                    {
                        srcObj.setState(StateEnum.ISSUSPENSEACCOUNT);
                        reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ISSUSPENSEACCOUNT);
                    } else
                    {
                        srcObj.setState(StateEnum.CHECKED);
                        reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CHECKED);
                    }
                    ctx.put("isReWrite", null);
                } else
                if(StateEnum.ALREADYPAYMENT.equals(srcObj.getState()))
                {
                    DailyLoanBillInfo dailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
                    dailyLoanBillinfo.setLoanState("N");
                    SelectorItemCollection sic = new SelectorItemCollection();
                    sic.add(new SelectorItemInfo("loanState"));
                    IDailyLoanBill iDailyLoanBill = null;
                    iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                    iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                }
            }
        StateEnum nowState = StateEnum.CHECKED;
        if((new VoucherInfo()).getBOSType().equals(type))
            if(BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum))
            {
                if(mappingInfo.getIsTempSave() == 0)
                {
                    if(isSuspenseAcc)
                    {
                        reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ISSUSPENSEACCOUNT);
                        setActionFlag(ctx, (DailyLoanBillInfo)srcObj, "R");
                    } else
                    if(StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()) || StateEnum.ISSUSPENSEACCOUNT.equals(srcObj.getState()) || nowState.equals(srcObj.getState()))
                    {
                        DailyLoanBillInfo dailyLoanBillinfo;
                        SelectorItemCollection sic;
                        IDailyLoanBill iDailyLoanBill;
                        if(StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()))
                        {
                            dailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
                            dailyLoanBillinfo.setFiVouchered(true);
                            sic = new SelectorItemCollection();
                            sic.add(new SelectorItemInfo("fiVouchered"));
                            iDailyLoanBill = null;
                            iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                            iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                            _deleteVoucher(ctx, new ObjectUuidPK(srcObj.getId()));
                            dailyLoanBillinfo.setFiVouchered(false);
                            iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                        }
                        reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ALREADYPAYMENT);
                        dailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
                        dailyLoanBillinfo.setFiVouchered(true);
                        sic = new SelectorItemCollection();
                        sic.add(new SelectorItemInfo("fiVouchered"));
                        iDailyLoanBill = null;
                        iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                        iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                    } else
                    if(srcObj.getState().equals(StateEnum.ALREADYPAYMENT))
                    {
                        DailyLoanBillInfo dailyLoanBillinfo = DailyLoanBillFactory.getLocalInstance(ctx).getDailyLoanBillInfo(new ObjectUuidPK(srcObj.getId().toString()));
                        dailyLoanBillinfo.setLoanState("Y");
                        dailyLoanBillinfo.setReturnAmount(dailyLoanBillinfo.getAmountBalance());
                        dailyLoanBillinfo.setReturnDate(new Date());
                        SelectorItemCollection sic = new SelectorItemCollection();
                        sic.add(new SelectorItemInfo("loanState"));
                        sic.add(new SelectorItemInfo("returnDate"));
                        sic.add(new SelectorItemInfo("returnAmount"));
                        IDailyLoanBill iDailyLoanBill = null;
                        iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                        iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                    }
                } else
                if(isSuspenseAcc)
                    reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ALREADYSUSPENSEACCOUNT);
                else
                if(StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()) || StateEnum.ISSUSPENSEACCOUNT.equals(srcObj.getState()) || nowState.equals(srcObj.getState()))
                {
                    DailyLoanBillInfo dailyLoanBillinfo;
                    SelectorItemCollection sic;
                    IDailyLoanBill iDailyLoanBill;
                    if(StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()))
                    {
                        dailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
                        dailyLoanBillinfo.setFiVouchered(true);
                        sic = new SelectorItemCollection();
                        sic.add(new SelectorItemInfo("fiVouchered"));
                        iDailyLoanBill = null;
                        iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                        iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                        _deleteVoucher(ctx, new ObjectUuidPK(srcObj.getId()));
                        dailyLoanBillinfo.setFiVouchered(false);
                        iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                    }
                    reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ALREADYVOUCHER);
                    dailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
                    dailyLoanBillinfo.setFiVouchered(true);
                    sic = new SelectorItemCollection();
                    sic.add(new SelectorItemInfo("fiVouchered"));
                    iDailyLoanBill = null;
                    iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                    iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                } else
                if(srcObj.getState().equals(StateEnum.ALREADYPAYMENT))
                {
                    DailyLoanBillInfo dailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
                    dailyLoanBillinfo.setLoanState("P");
                    SelectorItemCollection sic = new SelectorItemCollection();
                    sic.add(new SelectorItemInfo("loanState"));
                    IDailyLoanBill iDailyLoanBill = null;
                    iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                    iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                }
            } else
            if(BOTBillOperStateEnum.EDIT.equals(bOTBillOperStateEnum))
            {
                DailyLoanBillInfo dailyLoanBillinfo = DailyLoanBillFactory.getLocalInstance(ctx).getDailyLoanBillInfo(new ObjectUuidPK(srcObj.getId().toString()));
                if(StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()))
                {
                    reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ISSUSPENSEACCOUNT);
                    setActionFlag(ctx, (DailyLoanBillInfo)srcObj, "R");
                } else
                if(StateEnum.ALREADYVOUCHER.equals(srcObj.getState()))
                    reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ALREADYPAYMENT);
                else
                if(srcObj.getState().equals(StateEnum.ALREADYPAYMENT) && "P".equals(dailyLoanBillinfo.getLoanState()))
                {
                    dailyLoanBillinfo.setLoanState("Y");
                    dailyLoanBillinfo.setReturnAmount(dailyLoanBillinfo.getAmountBalance());
                    dailyLoanBillinfo.setReturnDate(new Date());
                    SelectorItemCollection sic = new SelectorItemCollection();
                    sic.add(new SelectorItemInfo("loanState"));
                    sic.add(new SelectorItemInfo("returnDate"));
                    sic.add(new SelectorItemInfo("returnAmount"));
                    IDailyLoanBill iDailyLoanBill = null;
                    iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                    iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                }
            } else
            if(BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum))
            {
                DailyLoanBillInfo dailyLoanInfo = DailyLoanBillFactory.getLocalInstance(ctx).getDailyLoanBillInfo(new ObjectUuidPK(srcObj.getId().toString()));
                if(StateEnum.CLOSED.equals(srcObj.getState()))
                    throw new BizCollException(BizCollException.ISSOURCEBILLCLOSE);
                if(isSourceBillRelatedBill(ctx, srcObj, botObj.getDestObjectID()))
                    throw new BizCollException(BizCollException.CANNOT_DELVOCHER_HASBOTP);
                if(StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()) || StateEnum.ISSUSPENSEACCOUNT.equals(srcObj.getState()))
                {
                    if(StateEnum.ISSUSPENSEACCOUNT.equals(srcObj.getState()) && isSrcBillRelPayBill(ctx, srcObj))
                        throw new BizCollException(BizCollException.CANNOT_DELVOUCHER_ISPAYMENT);
                    ctx.put("isReWrite", new Boolean(true));
                    reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CHECKED);
                    setActionFlag(ctx, (DailyLoanBillInfo)srcObj, null);
                } else
                if((StateEnum.ALREADYVOUCHER.equals(srcObj.getState()) || StateEnum.ALREADYPAYMENT.equals(srcObj.getState())) && "N".equals(dailyLoanInfo.getLoanState()))
                {
                    ctx.put("isReWrite", new Boolean(true));
                    reWriteBillState(ctx, srcBillPK.toString(), nowState);
                    DailyLoanBillInfo dailyLoanBillinfo = (DailyLoanBillInfo)srcObj;
                    dailyLoanBillinfo.setFiVouchered(false);
                    SelectorItemCollection sic = new SelectorItemCollection();
                    sic.add(new SelectorItemInfo("fiVouchered"));
                    IDailyLoanBill iDailyLoanBill = null;
                    iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                    iDailyLoanBill.updatePartial(dailyLoanBillinfo, sic);
                } else
                if(StateEnum.ALREADYPAYMENT.equals(srcObj.getState()) && ("Y".equals(dailyLoanInfo.getLoanState()) || "P".equals(dailyLoanInfo.getLoanState())))
                {
                    dailyLoanInfo.setLoanState("N");
                    dailyLoanInfo.setReturnAmount(new BigDecimal("0.00"));
                    SelectorItemCollection sic = new SelectorItemCollection();
                    sic.add(new SelectorItemInfo("loanState"));
                    sic.add(new SelectorItemInfo("returnAmount"));
                    IDailyLoanBill iDailyLoanBill = null;
                    iDailyLoanBill = DailyLoanBillFactory.getLocalInstance(ctx);
                    iDailyLoanBill.updatePartial(dailyLoanInfo, sic);
                }
                ctx.put("isReWrite", null);
            }
    }

    private void setActionFlag(Context ctx, DailyLoanBillInfo billInfo, String actionFlag)
        throws BOSException, EASBizException
    {
        billInfo.setActionFlag(actionFlag);
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("actionFlag"));
        DailyLoanBillFactory.getLocalInstance(ctx).updatePartial(billInfo, sic);
    }

    private boolean isSourceBillRelatedBill(Context ctx, BizCollBillBaseInfo info)
    {
        boolean isSourceBillRelatedBill = false;
        try
        {
            if(BizCollUtil.checkBillHasTracDown(ctx, info))
                isSourceBillRelatedBill = true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return isSourceBillRelatedBill;
    }

    private boolean isSourceBillRelatedBill(Context ctx, BizCollBillBaseInfo info, String destObjectID)
    {
        boolean isSourceBillRelatedBill = false;
        try
        {
            if(BizCollUtil.checkBillHasTracDown(ctx, info, destObjectID))
                isSourceBillRelatedBill = true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return isSourceBillRelatedBill;
    }

    private boolean isSrcBillRelPayBill(Context ctx, BizCollBillBaseInfo info)
    {
        boolean isSrcBillRelPayBill = false;
        try
        {
            if(BizCollUtil.checkBillHasRePaybill(ctx, info))
                isSrcBillRelPayBill = true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return isSrcBillRelPayBill;
    }

    private static Logger logger = Logger.getLogger("com.kingdee.eas.cp.bc.app.DailyLoanBillControllerBean");
    private static String bizCollResource = "com.kingdee.eas.cp.bc.BizCollResource";

}
