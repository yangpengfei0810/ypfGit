/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeEntryCollection;
import com.kingdee.eas.fdc.contract.ContractChangeEntryFactory;
import com.kingdee.eas.fdc.contract.ContractChangeEntryInfo;
import com.kingdee.eas.fdc.contract.ContractChangeException;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.CopySupplierEntryFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SupplierContentEntryCollection;
import com.kingdee.eas.fdc.contract.SupplierContentEntryFactory;
import com.kingdee.eas.fdc.contract.SupplierContentEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

// Referenced classes of package com.kingdee.eas.fdc.contract.app:
//            AbstractChangeAuditBillControllerBean

public class ChangeAuditBillControllerBean extends AbstractChangeAuditBillControllerBean
{
    class ProConMapping
    {

        boolean isCheckAmountPass()
        {
            return proBalanceAmount.compareTo(conBudgetAmountTotal) >= 0;
        }

        void meger(ProConMapping p)
        {
            conSet.addAll(p.conSet);
        }

        public int hashCode()
        {
            return proId != null ? proId.hashCode() : "".hashCode();
        }

        public boolean equals(Object obj)
        {
            if(obj == null || !(obj instanceof ProConMapping))
                return false;
            else
                return proId.equals(((ProConMapping)obj).proId);
        }

        String proId;
        Set conSet;
        BigDecimal proBalanceAmount;
        BigDecimal conBudgetAmountTotal;

        ProConMapping(String _proId, Set _conSet, BigDecimal _proBalanceAmount)
        {
            super();
            proId = _proId;
            conSet = _conSet;
            proBalanceAmount = _proBalanceAmount != null ? _proBalanceAmount : FDCHelper.ZERO;
            conBudgetAmountTotal = FDCHelper.ZERO;
        }
    }


    public ChangeAuditBillControllerBean()
    {
    }

    protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
        if(info.getChangeState() == null)
            info.setChangeState(ChangeBillStateEnum.Saved);
        if(info.getAuditType() != null)
            info.setAuditTypeName(info.getAuditType().getName());
        if(info.getJobType() != null)
            info.setJobTypeName(info.getJobType().getName());
        if(info.getSpecialtyType() != null)
            info.setSpecialtyTypeName(info.getSpecialtyType().getName());
        super._save(ctx, pk, info);
    }

    protected IObjectPK _save(Context ctx, IObjectValue model)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
        if(info.getChangeState() == null)
            info.setChangeState(ChangeBillStateEnum.Saved);
        if(info.getAuditType() != null)
            info.setAuditTypeName(info.getAuditType().getName());
        if(info.getJobType() != null)
            info.setJobTypeName(info.getJobType().getName());
        if(info.getSpecialtyType() != null)
            info.setSpecialtyTypeName(info.getSpecialtyType().getName());
        return super._save(ctx, info);
    }

    protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
        info.setChangeState(ChangeBillStateEnum.Submit);
        if(info.getAuditType() != null)
            info.setAuditTypeName(info.getAuditType().getName());
        if(info.getJobType() != null)
            info.setJobTypeName(info.getJobType().getName());
        if(info.getSpecialtyType() != null)
            info.setSpecialtyTypeName(info.getSpecialtyType().getName());
        super._submit(ctx, pk, info);
        if(info.getSuppEntry() != null && info.getSuppEntry().size() > 0)
        {
            if(isGenerateAfterAudit(ctx))
                return;
            ChangeBill(ctx, model, FDCBillStateEnum.SUBMITTED);
        }
    }

    protected IObjectPK _submit(Context ctx, IObjectValue model)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
        checkBillForSubmit(ctx, info);
        ChangeSupplierEntryCollection infoCollection = info.getSuppEntry();
        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
label0:
        for(int i = 0; i < infoCollection.size(); i++)
        {
            ChangeSupplierEntryInfo entryInfo = infoCollection.get(i);
            BigDecimal oriCostAmount = FDCHelper.toBigDecimal(entryInfo.getCostAmount(), 2);
            if(entryInfo.getContractBill() != null && entryInfo.getContractBill().isIsCoseSplit())
                try
                {
                    String contractChangeId = entryInfo.getContractChange().getId().toString();
                    boolean isCostSplit = false;
                    FilterInfo filter = null;
                    builder.clear();
                    builder.appendSql("select fid from t_con_conchangesplit where FContractChangeID =? ");
                    builder.addParam(contractChangeId);
                    for(IRowSet rowSet1 = builder.executeQuery(); rowSet1.next();)
                        isCostSplit = true;

                    if(isCostSplit)
                    {
                        builder.clear();
                        builder.appendSql("select famount from t_con_conchangesplit where FContractChangeID=? ");
                        builder.addParam(contractChangeId);
                        IRowSet rowSet2 = builder.executeQuery(ctx);
                        do
                        {
                            if(!rowSet2.next())
                                break;
                            BigDecimal splitCostAmountSum = FDCHelper.toBigDecimal(rowSet2.getBigDecimal("famount"), 2);
                            if(oriCostAmount != null && !oriCostAmount.equals(splitCostAmountSum))
                            {
                                builder.clear();
                                builder.appendSql("select distinct(fparentid) from  T_FNC_PaymentSplitEntry  where  fcostbillid=? ");
                                builder.addParam(contractChangeId);
                                for(IRowSet rowSet3 = builder.executeQuery(); rowSet3.next(); PaymentSplitFactory.getLocalInstance(ctx).delete(filter))
                                {
                                    filter = new FilterInfo();
                                    filter.appendFilterItem("costBillId", contractChangeId);
                                    PaymentSplitEntryFactory.getLocalInstance(ctx).delete(filter);
                                    filter = new FilterInfo();
                                    filter.appendFilterItem("id", rowSet3.getString("fparentid"));
                                }

                                builder.clear();
                                builder.appendSql("select distinct(fparentid) from  T_CON_SettlementCostSplitEntry  where  fcostbillid=? ");
                                builder.addParam(contractChangeId);
                                for(IRowSet rowSet4 = builder.executeQuery(); rowSet4.next(); SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter))
                                {
                                    filter = new FilterInfo();
                                    filter.appendFilterItem("costBillId", contractChangeId);
                                    SettlementCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
                                    filter = new FilterInfo();
                                    filter.appendFilterItem("id", rowSet4.getString("fparentid"));
                                }

                                filter = new FilterInfo();
                                filter.appendFilterItem("parent.contractChange.id", contractChangeId);
                                ConChangeSplitEntryFactory.getLocalInstance(ctx).delete(filter);
                                filter = new FilterInfo();
                                filter.appendFilterItem("contractChange.id", contractChangeId);
                                ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
                            }
                        } while(true);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            if(entryInfo.getContractBill() == null || entryInfo.getContractBill().isIsCoseSplit())
                continue;
            try
            {
                String contractChangeId = entryInfo.getContractChange().getId().toString();
                boolean isCostSplit = false;
                builder.clear();
                builder.appendSql("select fid from T_CON_ConChangeNoCostSplit where FContractChangeID =? ");
                builder.addParam(contractChangeId);
                for(IRowSet rowSet1 = builder.executeQuery(); rowSet1.next();)
                    isCostSplit = true;

                FilterInfo filter = null;
                if(!isCostSplit)
                    continue;
                builder.clear();
                builder.appendSql("select famount from T_CON_ConChangeNoCostSplit where FContractChangeID=? ");
                builder.addParam(contractChangeId);
                IRowSet rowSet = builder.executeQuery(ctx);
                do
                {
                    BigDecimal splitCostAmountSum;
                    do
                    {
                        if(!rowSet.next())
                            continue label0;
                        splitCostAmountSum = FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"), 2);
                    } while(oriCostAmount == null || oriCostAmount.equals(splitCostAmountSum));
                    builder.clear();
                    builder.appendSql("select distinct(fparentid) from  T_FNC_PaymentNoCostSplitEntry  where  fcostbillid=? ");
                    builder.addParam(contractChangeId);
                    for(IRowSet rowSet3 = builder.executeQuery(); rowSet3.next(); PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter))
                    {
                        filter = new FilterInfo();
                        filter.appendFilterItem("costBillId", contractChangeId);
                        PaymentNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
                        filter = new FilterInfo();
                        filter.appendFilterItem("id", rowSet3.getString("fparentid"));
                    }

                    builder.clear();
                    builder.appendSql("select distinct(fparentid) from  T_CON_SettNoCostSplitEntry  where  fcostbillid=? ");
                    builder.addParam(contractChangeId);
                    for(IRowSet rowSet4 = builder.executeQuery(); rowSet4.next(); SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter))
                    {
                        filter = new FilterInfo();
                        filter.appendFilterItem("costBillId", contractChangeId);
                        SettNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
                        filter = new FilterInfo();
                        filter.appendFilterItem("id", rowSet4.getString("fparentid"));
                    }

                    filter = new FilterInfo();
                    filter.appendFilterItem("parent.contractChange.id", contractChangeId);
                    ConChangeNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
                    filter = new FilterInfo();
                    filter.appendFilterItem("contractChange.id", contractChangeId);
                    ConChangeNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
                } while(true);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        info.setChangeState(ChangeBillStateEnum.Submit);
        System.err.println("");
        if(info.getAuditType() != null)
            info.setAuditTypeName(info.getAuditType().getName());
        if(info.getJobType() != null)
            info.setJobTypeName(info.getJobType().getName());
        if(info.getSpecialtyType() != null)
            info.setSpecialtyTypeName(info.getSpecialtyType().getName());
        if(info.getSuppEntry() != null && info.getSuppEntry().size() > 0)
        {
            if(isGenerateAfterAudit(ctx))
                return super._submit(ctx, info);
            ChangeBill(ctx, model, FDCBillStateEnum.SUBMITTED);
        }
        return super._submit(ctx, info);
    }

    protected void _delete(Context ctx, IObjectPK pk)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo info = (ChangeAuditBillInfo)super._getValue(ctx, pk);
        ChangeSupplierEntryCollection c = info.getSuppEntry();
        for(int i = 0; i < c.size(); i++)
        {
            FilterInfo filter = new FilterInfo();
            FilterItemCollection filterItems = filter.getFilterItems();
            filterItems.add(new FilterItemInfo("parent.id", c.get(i).getId().toString()));
            SupplierContentEntryFactory.getLocalInstance(ctx).delete(filter);
            CopySupplierEntryFactory.getLocalInstance(ctx).delete(filter);
        }

        FilterInfo filter = new FilterInfo();
        FilterItemCollection filterItems = filter.getFilterItems();
        filterItems.add(new FilterItemInfo("changeAudit.id", info.getId().toString()));
        ContractChangeBillFactory.getLocalInstance(ctx).delete(filter);
        super._delete(ctx, pk);
    }

    protected void _delete(Context ctx, IObjectPK arrayPK[])
        throws BOSException, EASBizException
    {
        for(int i = 0; i < arrayPK.length; i++)
        {
            ChangeAuditBillInfo info = getChangeAuditBillInfo(ctx, arrayPK[i]);
            ChangeSupplierEntryCollection c = info.getSuppEntry();
            for(int j = 0; j < c.size(); j++)
                info.getSuppEntry().removeObject(j);

            FilterInfo filter = new FilterInfo();
            FilterItemCollection filterItems = filter.getFilterItems();
            filterItems.add(new FilterItemInfo("changeAudit.id", info.getId().toString()));
            ContractChangeBillFactory.getLocalInstance(ctx).delete(filter);
        }

        super._delete(ctx, arrayPK);
    }

    protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
        if(info.getAuditType() != null)
            info.setAuditTypeName(info.getAuditType().getName());
        if(info.getJobType() != null)
            info.setJobTypeName(info.getJobType().getName());
        if(info.getSpecialtyType() != null)
            info.setSpecialtyTypeName(info.getSpecialtyType().getName());
        super._update(ctx, pk, info);
    }

    private void removeDetailEntries(Context ctx, IObjectValue model)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
        ChangeSupplierEntryCollection c = info.getSuppEntry();
        for(int i = 0; i < c.size(); i++)
        {
            FilterInfo filter = new FilterInfo();
            FilterItemCollection filterItems = filter.getFilterItems();
            if(c.get(i).getId() != null)
            {
                filterItems.add(new FilterItemInfo("parent.id", c.get(i).getId().toString()));
                SupplierContentEntryFactory.getLocalInstance(ctx).delete(filter);
            }
        }

    }

    private void ChangeBill(Context ctx, IObjectValue model, FDCBillStateEnum state)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
        ChangeSupplierEntryCollection c = info.getSuppEntry();
        if(c != null)
        {
            for(int i = 0; i < c.size(); i++)
            {
                ChangeSupplierEntryInfo entry = c.get(i);
                ContractChangeBillInfo change;
                if(entry.getContractChange() == null)
                {
                    change = new ContractChangeBillInfo();
                    change.setConductTime(FDCHelper.getCurrentDate());
                    change.setSettleTime(FDCHelper.getCurrentDate());
                    change.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
                    change.setSourceType(info.getSourceType());
                    change.setOrgUnit(info.getOrgUnit());
                    change.setChangeAudit(info);
                    if(info.getNumber() != null)
                        change.setChangeAuditNumber(info.getNumber());
                    change.setState(state);
                    change.setChangeType(info.getAuditType());
                    if(info.getAuditTypeName() != null)
                        change.setChangeTypeName(info.getAuditTypeName());
                    if(i == 0)
                    {
                        change.setInvalidCostReason(info.getInvalidCostReason());
                        change.setCostNouse(info.getCostNouse());
                    } else
                    {
                        change.setInvalidCostReason(null);
                        change.setCostNouse(GlUtils.zero);
                    }
                    change.setBookedDate(info.getBookedDate());
                    change.setPeriod(info.getPeriod());
                    change.setChangeReason(info.getChangeReason());
                    change.setChangeSubject(info.getChangeSubject());
                    change.setConductDept(info.getConductDept());
                    change.setUrgentDegree(info.getUrgentDegree());
                    change.setCurProject(info.getCurProject());
                    if(info.getCurProjectName() != null)
                        change.setCurProjectName(info.getCurProjectName());
                    change.setJobType(info.getJobType());
                    if(info.getJobTypeName() != null)
                        change.setJobTypeName(info.getJobTypeName());
                    change.setSpecialtyType(info.getSpecialtyType());
                    if(info.getSpecialtyTypeName() != null)
                        change.setSpecialtyTypeName(info.getSpecialtyTypeName());
                    change.setGraphCount(info.getGraphCount());
                    change.setConductTime(info.getCreateTime());
                    change.setMainSupp(entry.getMainSupp());
                    change.setContractBill(entry.getContractBill());
                    if(entry.getContractBill() != null && entry.getContractBill().getNumber() != null)
                    {
                        change.setContractBillNumber(entry.getContractBill().getNumber());
                        change.setIsConSetted(entry.getContractBill().isHasSettled());
                        change.setIsCostSplit(entry.getContractBill().isIsCoseSplit());
                    }
                    change.setBalanceType(entry.getBalanceType());
                    change.setIsDeduct(entry.isIsDeduct());
                    change.setDeductAmount(entry.getDeductAmount());
                    change.setDeductReason(entry.getDeductReason());
                    change.setAmount(entry.getCostAmount());
                    change.setOriginalAmount(entry.getOriCostAmount());
                    change.setCurrency(entry.getCurrency());
                    change.setExRate(entry.getExRate());
                    change.setOriginalContactNum(entry.getOriginalContactNum());
                    change.setIsSureChangeAmt(entry.isIsSureChangeAmt());
                    change.setIsImportChange(info.isIsImportChange());
                    change.setConstructPrice(entry.getConstructPrice());
                    SupplierContentEntryCollection coll = new SupplierContentEntryCollection();
                    EntityViewInfo vit = new EntityViewInfo();
                    FilterInfo fit = new FilterInfo();
                    FilterItemCollection itt = fit.getFilterItems();
                    if(info.getId() != null && entry.getId() != null)
                    {
                        itt.add(new FilterItemInfo("parent.id", entry.getId().toString(), CompareType.EQUALS));
                        vit.setFilter(fit);
                        vit.getSelector().add("content.*");
                        SorterItemInfo sortName = new SorterItemInfo("seq");
                        sortName.setSortType(SortType.ASCEND);
                        vit.getSorter().add(sortName);
                        coll = SupplierContentEntryFactory.getLocalInstance(ctx).getSupplierContentEntryCollection(vit);
                    } else
                    {
                        coll = entry.getEntrys();
                    }
                    ContractChangeEntryCollection entrycoll = change.getEntrys();
                    for(int j = 0; j < coll.size(); j++)
                    {
                        SupplierContentEntryInfo con = coll.get(j);
                        ContractChangeEntryInfo test = new ContractChangeEntryInfo();
                        test.setNumber(con.getContent().getNumber());
                        test.setChangeContent(con.getContent().getChangeContent());
                        test.setIsBack(con.getContent().isIsBack());
                        test.setSeq(con.getContent().getSeq());
                        entrycoll.add(test);
                    }

                    change.setName(info.getName() + "_" + (i + 1));
                    String orgId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
                    String billNumber = null;
                    try
                    {
                        billNumber = FDCHelper.getNumberByCodingRule(ctx, change, orgId);
                    }
                    catch(Exception e1)
                    {
                        logger.error("\u53D8\u66F4\u6307\u4EE4\u5355\u751F\u6210\u65F6\uFF0C\u8C03\u7528\u7F16\u7801\u89C4\u5219\u62A5\u9519\uFF0C\u8BF7\u68C0\u67E5\u7F16\u7801\u89C4\u5219\u662F\u5426\u6709\u95EE\u9898,\u4F8B\u5982\uFF1A\u987A\u5E8F\u53F7\u662F\u5426\u5DF2\u7ECF\u4F7F\u7528\u5B8C\uFF01", e1);
                        if(e1 instanceof BOSException)
                            throw (BOSException)e1;
                        if(e1 instanceof EASBizException)
                            throw (EASBizException)e1;
                    }
                    if(billNumber == null)
                        billNumber = info.getNumber() + "_" + (i + 1);
                    change.setNumber(billNumber);
                    if(info.getConductUnit() != null)
                        change.setConductUnit(info.getConductUnit());
                    if(info.getOffer() != null)
                        change.setOffer(info.getOffer());
                    if(info.getConstrUnit() != null)
                        change.setConstrUnit(info.getConstrUnit());
                    if(info.getConstrSite() != null)
                        change.setConstrSite(info.getConstrSite());
                    ContractChangeBillFactory.getLocalInstance(ctx).addnew(change);
                    entry.setContractChange(change);
                    if(entry != null && entry.getId() != null)
                    {
                        SelectorItemCollection _selector = new SelectorItemCollection();
                        _selector.add("contractChange");
                        ChangeSupplierEntryFactory.getLocalInstance(ctx).updatePartial(entry, _selector);
                    }
                    continue;
                }
                change = entry.getContractChange();
                FilterInfo fi = new FilterInfo();
                FilterItemCollection it = fi.getFilterItems();
                if(change.getId() != null)
                {
                    it.add(new FilterItemInfo("parent.id", change.getId().toString(), CompareType.EQUALS));
                    ContractChangeEntryFactory.getLocalInstance(ctx).delete(fi);
                }
                if(info.getNumber() != null)
                    change.setChangeAuditNumber(info.getNumber());
                if(i == 0)
                {
                    change.setInvalidCostReason(info.getInvalidCostReason());
                    change.setCostNouse(info.getCostNouse());
                } else
                {
                    change.setInvalidCostReason(null);
                    change.setCostNouse(GlUtils.zero);
                }
                change.setBookedDate(info.getBookedDate());
                change.setPeriod(info.getPeriod());
                change.setSourceType(info.getSourceType());
                change.setChangeType(info.getAuditType());
                if(info.getAuditTypeName() != null)
                    change.setChangeTypeName(info.getAuditTypeName());
                change.setChangeReason(info.getChangeReason());
                change.setChangeSubject(info.getChangeSubject());
                change.setConductDept(info.getConductDept());
                change.setUrgentDegree(info.getUrgentDegree());
                change.setCurProject(info.getCurProject());
                if(info.getCurProjectName() != null)
                    change.setCurProjectName(info.getCurProjectName());
                change.setJobType(info.getJobType());
                if(info.getJobTypeName() != null)
                    change.setJobTypeName(info.getJobTypeName());
                change.setState(state);
                change.setSpecialtyType(info.getSpecialtyType());
                if(info.getSpecialtyTypeName() != null)
                    change.setSpecialtyTypeName(info.getSpecialtyTypeName());
                change.setGraphCount(info.getGraphCount());
                change.setConductTime(info.getCreateTime());
                change.setMainSupp(entry.getMainSupp());
                change.setContractBill(entry.getContractBill());
                if(entry.getContractBill() != null && entry.getContractBill().getNumber() != null)
                {
                    change.setContractBillNumber(entry.getContractBill().getNumber());
                    change.setIsConSetted(entry.getContractBill().isHasSettled());
                    change.setIsCostSplit(entry.getContractBill().isIsCoseSplit());
                }
                change.setBalanceType(entry.getBalanceType());
                change.setIsDeduct(entry.isIsDeduct());
                change.setDeductAmount(entry.getDeductAmount());
                change.setDeductReason(entry.getDeductReason());
                change.setAmount(entry.getCostAmount());
                change.setOriginalAmount(entry.getOriCostAmount());
                change.setCurrency(entry.getCurrency());
                change.setExRate(entry.getExRate());
                change.setOriginalContactNum(entry.getOriginalContactNum());
                change.setIsSureChangeAmt(entry.isIsSureChangeAmt());
                change.setIsImportChange(info.isIsImportChange());
                change.setConstructPrice(entry.getConstructPrice());
                EntityViewInfo vit = new EntityViewInfo();
                FilterInfo fit = new FilterInfo();
                FilterItemCollection itt = fit.getFilterItems();
                if(entry.getId() != null)
                    itt.add(new FilterItemInfo("parent.id", entry.getId().toString(), CompareType.EQUALS));
                vit.setFilter(fit);
                vit.getSelector().add("content.*");
                SorterItemInfo sortName = new SorterItemInfo("seq");
                sortName.setSortType(SortType.ASCEND);
                vit.getSorter().add(sortName);
                SupplierContentEntryCollection coll = SupplierContentEntryFactory.getLocalInstance(ctx).getSupplierContentEntryCollection(vit);
                for(int j = 0; j < coll.size(); j++)
                {
                    SupplierContentEntryInfo con = coll.get(j);
                    ContractChangeEntryInfo test = new ContractChangeEntryInfo();
                    test.setNumber(con.getContent().getNumber());
                    test.setChangeContent(con.getContent().getChangeContent());
                    test.setIsBack(con.getContent().isIsBack());
                    test.setSeq(con.getContent().getSeq());
                    test.setParent(change);
                    ContractChangeEntryFactory.getLocalInstance(ctx).addnew(test);
                }

                if(info.getConductUnit() != null)
                    change.setConductUnit(info.getConductUnit());
                if(info.getOffer() != null)
                    change.setOffer(info.getOffer());
                if(info.getConstrUnit() != null)
                    change.setConstrUnit(info.getConstrUnit());
                if(info.getConstrSite() != null)
                    change.setConstrSite(info.getConstrSite());
                SelectorItemCollection selector = new SelectorItemCollection();
                selector.add("changeType");
                selector.add("changeReason");
                selector.add("changeSubject");
                selector.add("conductDept");
                selector.add("urgentDegree");
                selector.add("curProject");
                selector.add("jobType");
                selector.add("specialtyType");
                selector.add("graphCount");
                selector.add("mainSupp");
                selector.add("contractBill");
                selector.add("balanceType");
                selector.add("isDeduct");
                selector.add("deductAmount");
                selector.add("deductReason");
                selector.add("amount");
                selector.add("contractBillNumber");
                selector.add("conductTime");
                selector.add("state");
                selector.add("changeAuditNumber");
                selector.add("changeTypeName");
                selector.add("curProjectName");
                selector.add("jobTypeName");
                selector.add("specialtyTypeName");
                selector.add("costNouse");
                selector.add("invalidCostReason");
                selector.add("originalAmount");
                selector.add("bookedDate");
                selector.add("period");
                selector.add("isConSetted");
                selector.add("isCostSplit");
                selector.add("conductUnit");
                selector.add("offer");
                selector.add("constrUnit");
                selector.add("constrSite");
                selector.add("originalContactNum");
                selector.add("isSureChangeAmt");
                selector.add("isImportChange");
                selector.add("constructPrice");
                ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, selector);
            }

        }
    }

    protected void _register(Context ctx, Set idSet)
        throws BOSException, EASBizException
    {
        String id;
        for(Iterator iter = idSet.iterator(); iter.hasNext(); _register4WF(ctx, new ObjectUuidPK(BOSUuid.read(id))))
            id = (String)iter.next();

    }

    protected void _disPatch(Context ctx, Set idSet)
        throws BOSException, EASBizException
    {
        String id;
        for(Iterator iter = idSet.iterator(); iter.hasNext(); _disPatch4WF(ctx, new ObjectUuidPK(BOSUuid.read(id))))
            id = (String)iter.next();

    }

    protected void _aheadDisPatch(Context ctx, Set idSet)
        throws BOSException, EASBizException
    {
        IObjectPK pk;
        for(Iterator iter = idSet.iterator(); iter.hasNext(); _aheadDisPatch4WF(ctx, pk))
        {
            String id = (String)iter.next();
            pk = new ObjectUuidPK(BOSUuid.read(id));
        }

    }

    protected void _cancel(Context ctx, IObjectPK pk)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();
        billInfo.setId(BOSUuid.read(pk.toString()));
        billInfo.setState(FDCBillStateEnum.INVALID);
        billInfo.setChangeState(ChangeBillStateEnum.INVALID);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("state");
        selector.add("changeState");
        _updatePartial(ctx, billInfo, selector);
    }

    protected void _cancelCancel(Context ctx, IObjectPK pk)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();
        billInfo.setId(BOSUuid.read(pk.toString()));
        billInfo.setState(FDCBillStateEnum.AUDITTED);
        billInfo.setChangeState(ChangeBillStateEnum.Audit);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("state");
        selector.add("changeState");
        _updatePartial(ctx, billInfo, selector);
    }

    protected void _setAudittingStatus(Context ctx, BOSUuid billId)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId));
        billInfo.setChangeState(ChangeBillStateEnum.Auditting);
        billInfo.setState(FDCBillStateEnum.AUDITTING);
        int num = billInfo.getSuppEntry().size();
        ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
        if(num > 0)
        {
            for(int i = 0; i < num; i++)
            {
                ChangeSupplierEntryInfo entry = c.get(i);
                if(entry.getContractChange() != null)
                {
                    ContractChangeBillInfo change = entry.getContractChange();
                    ContractChangeBillFactory.getLocalInstance(ctx).setAudittingStatus(change.getId());
                }
            }

        }
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("changeState");
        selector.add("state");
        _updatePartial(ctx, billInfo, selector);
    }

    protected void _setSubmitStatus(Context ctx, BOSUuid billId)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId));
        billInfo.setChangeState(ChangeBillStateEnum.Submit);
        billInfo.setState(FDCBillStateEnum.SUBMITTED);
        int num = billInfo.getSuppEntry().size();
        ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
        if(num > 0)
        {
            for(int i = 0; i < num; i++)
            {
                ChangeSupplierEntryInfo entry = c.get(i);
                if(entry.getContractChange() != null)
                {
                    ContractChangeBillInfo change = entry.getContractChange();
                    ContractChangeBillFactory.getLocalInstance(ctx).setSubmitStatus(change.getId());
                }
            }

        }
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("changeState");
        selector.add("state");
        _updatePartial(ctx, billInfo, selector);
    }

    protected void _audit(Context ctx, BOSUuid billId)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId));
        checkBillForAudit(ctx, billId, billInfo);
        billInfo.setChangeState(ChangeBillStateEnum.Audit);
        billInfo.setState(FDCBillStateEnum.AUDITTED);
        billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
        billInfo.setAuditTime(new Date());
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("changeState");
        selector.add("state");
        selector.add("auditor");
        selector.add("auditTime");
        ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
        if(c != null)
        {
            int num = billInfo.getSuppEntry().size();
            if(num > 0)
            {
                Set set = new HashSet();
                for(int i = 0; i < num; i++)
                {
                    ChangeSupplierEntryInfo entry = c.get(i);
                    set.add(entry.getContractBill().getId().toString());
                }

                EntityViewInfo view = new EntityViewInfo();
                view.setFilter(new FilterInfo());
                view.getFilter().getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
                view.getSelector().add("hasSettled");
                view.getSelector().add("number");
                ContractBillCollection cons = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
                boolean hasSettled = false;
                String conNumber = "";
                for(int i = 0; i < cons.size(); i++)
                    if(cons.get(i).isHasSettled())
                    {
                        hasSettled = true;
                        conNumber = conNumber + "\u3001\u201C" + cons.get(i).getNumber() + "\u201D";
                    }

                //mod by ypf on 2015年7月25日18:24:49，不检查是否已结算
//                if(hasSettled)
//                    throw new EASBizException(new NumericExceptionSubItem("111", "\u5BA1\u6279\u4E0D\u901A\u8FC7\uFF1A\u53D8\u66F4\u5BA1\u6279\u5355\u767B\u8BB0\u4E0B\u53D1\u5355\u4F4D\u5185\u5B58\u5728\u5DF2\u7ED3\u7B97\u7684\u5408\u540C\uFF0C\u5408\u540C\u7F16\u7801\uFF1A" + conNumber.substring(1)));
                List list = new ArrayList();
                for(int i = 0; i < num; i++)
                {
                    ChangeSupplierEntryInfo entry = c.get(i);
                    if(entry.getContractChange() != null)
                    {
                        ContractChangeBillInfo change = entry.getContractChange();
                        list.add(change.getId().toString());
                    }
                }

                Map initParam = FDCUtils.getDefaultFDCParam(ctx, null);
                boolean isGenerateAfterAuidt = true;
                if(initParam.get("FDC221_AUTOCHANGETOPROJECTVISA") != null)
                    isGenerateAfterAuidt = Boolean.valueOf(initParam.get("FDC222_GENERATEAFTERAUDIT").toString()).booleanValue();
                if(c != null && c.size() > 0 && isGenerateAfterAuidt)
                    ChangeBill(ctx, billInfo, FDCBillStateEnum.SAVED);
                if(list != null && list.size() > 0 && !isGenerateAfterAuidt)
                    ContractChangeBillFactory.getLocalInstance(ctx).audit(list);
            }
        }
        _updatePartial(ctx, billInfo, selector);
        updatePeriod(ctx, billId);
    }

    private void updatePeriod(Context ctx, BOSUuid billId)
        throws EASBizException, BOSException
    {
        SelectorItemCollection selectors = new SelectorItemCollection();
        selectors.add("isRespite");
        selectors.add("curProject.id");
        selectors.add("curProject.fullOrgUnit.id");
        selectors.add("bookedDate");
        selectors.add("period.*");
        ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId), selectors);
        String companyID = billInfo.getCurProject().getFullOrgUnit().getId().toString();
        Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyID);
        boolean isInCore = FDCUtils.getParamValue(paramMap, "FDC003_INCORPORATION");
        if(isInCore)
        {
            String prjId = billInfo.getCurProject().getId().toString();
            PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
            PeriodInfo billPeriod = billInfo.getPeriod();
            PeriodInfo shouldPeriod = null;
            Date bookedDate = DateTimeUtils.truncateDate(billInfo.getBookedDate());
            if(finPeriod == null)
                throw new EASBizException(new NumericExceptionSubItem("100", "\u5355\u636E\u6240\u5BF9\u5E94\u7684\u7EC4\u7EC7\u6CA1\u6709\u5F53\u524D\u65F6\u95F4\u7684\u671F\u95F4\uFF0C\u8BF7\u5148\u8BBE\u7F6E\uFF01"));
            if(billPeriod != null && billPeriod.getNumber() > finPeriod.getNumber())
            {
                if(bookedDate.before(billPeriod.getBeginDate()))
                    bookedDate = billPeriod.getBeginDate();
                else
                if(bookedDate.after(billPeriod.getEndDate()))
                    bookedDate = billPeriod.getEndDate();
                shouldPeriod = billPeriod;
            } else
            if(finPeriod != null)
            {
                if(bookedDate.before(finPeriod.getBeginDate()))
                    bookedDate = finPeriod.getBeginDate();
                else
                if(bookedDate.after(finPeriod.getEndDate()))
                    bookedDate = finPeriod.getEndDate();
                shouldPeriod = finPeriod;
            }
            selectors = new SelectorItemCollection();
            selectors.add("period");
            selectors.add("bookedDate");
            selectors.add("isRespite");
            billInfo.setBookedDate(bookedDate);
            billInfo.setPeriod(shouldPeriod);
            billInfo.setIsRespite(false);
            _updatePartial(ctx, billInfo, selectors);
        }
    }

    protected void _unAudit(Context ctx, BOSUuid billId)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId));
        checkBillForUnAudit(ctx, billId, billInfo);
        billInfo.setChangeState(ChangeBillStateEnum.Submit);
        billInfo.setState(FDCBillStateEnum.SUBMITTED);
        billInfo.setAuditor(null);
        billInfo.setAuditTime(null);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("changeState");
        selector.add("state");
        selector.add("auditor");
        selector.add("auditTime");
        int num = billInfo.getSuppEntry().size();
        ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
        if(num > 0)
        {
            List list = new ArrayList();
            for(int i = 0; i < num; i++)
            {
                ChangeSupplierEntryInfo entry = c.get(i);
                if(entry.getContractChange() == null)
                    continue;
                ContractChangeBillInfo change = entry.getContractChange();
                FilterInfo filterSett = new FilterInfo();
                filterSett.getFilterItems().add(new FilterItemInfo("contractChange.id", change.getId().toString()));
                filterSett.getFilterItems().add(new FilterItemInfo("state", "9INVALID", CompareType.NOTEQUALS));
                boolean hasSettleSplit = false;
                if(ConChangeSplitFactory.getLocalInstance(ctx).exists(filterSett) || ConChangeNoCostSplitFactory.getLocalInstance(ctx).exists(filterSett))
                    hasSettleSplit = true;
                if(hasSettleSplit)
                    throw new ContractChangeException(ContractChangeException.HASSPLIT);
                list.add(change.getId().toString());
            }

            if(list != null && list.size() > 0)
            {
                boolean isGenerateAfterAuidt = FDCUtils.getDefaultFDCParamByKey(ctx, null, "FDC222_GENERATEAFTERAUDIT");
                if(!isGenerateAfterAuidt)
                    ContractChangeBillFactory.getLocalInstance(ctx).unAudit(list);
            }
        }
        _updatePartial(ctx, billInfo, selector);
    }

    protected void _register4WF(Context ctx, IObjectPK pk)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo billInfo = (ChangeAuditBillInfo)super._getValue(ctx, pk);
        billInfo.setChangeState(ChangeBillStateEnum.Register);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("changeState");
        _updatePartial(ctx, billInfo, selector);
    }

    protected void _disPatch4WF(Context ctx, IObjectPK pk)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo billInfo = (ChangeAuditBillInfo)super._getValue(ctx, pk);
        billInfo.setChangeState(ChangeBillStateEnum.Announce);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("changeState");
        _updatePartial(ctx, billInfo, selector);
    }

    protected void _aheadDisPatch4WF(Context ctx, IObjectPK pk)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo info = (ChangeAuditBillInfo)super._getValue(ctx, pk);
        if(info.getSuppEntry().size() > 0)
            ChangeBill(ctx, info, FDCBillStateEnum.ANNOUNCE);
        info.setChangeState(ChangeBillStateEnum.AheadDisPatch);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("changeState");
        _updatePartial(ctx, info, selector);
    }

    private SelectorItemCollection getSic()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("period.id"));
        sic.add(new SelectorItemInfo("period.beginDate"));
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));
        return sic;
    }

    private void checkBillForSubmit(Context ctx, IObjectValue model)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo contractBill = (ChangeAuditBillInfo)model;
        String comId = null;
        if(contractBill.getCurProject().getFullOrgUnit() == null)
        {
            SelectorItemCollection sic = new SelectorItemCollection();
            sic.add("fullOrgUnit.id");
            CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(contractBill.getCurProject().getId().toString()), sic);
            comId = curProject.getFullOrgUnit().getId().toString();
        } else
        {
            comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
        }
        boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
        if(isInCore)
        {
            PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx, contractBill.getCurProject().getId().toString(), true);
            if(bookedPeriod != null && contractBill.getPeriod() != null && contractBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate()))
                throw new ContractException(ContractException.CNTPERIODBEFORE);
        }
        ChangeSupplierEntryCollection c = contractBill.getSuppEntry();
        for(int i = 0; i < c.size(); i++)
        {
            ChangeSupplierEntryInfo entry = c.get(i);
            if(entry.getContractBill() == null)
                continue;
            SelectorItemCollection sic = new SelectorItemCollection();
            sic.add(new SelectorItemInfo("period.beginDate"));
            ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(entry.getContractBill().getId().toString()), sic);
            if(contractBill.getPeriod() != null && contractBillInfo.getPeriod() != null && contractBill.getPeriod().getBeginDate() != null && contractBill.getPeriod().getBeginDate().before(contractBillInfo.getPeriod().getBeginDate()))
                throw new ContractException(ContractException.CNTPERIODBEFORECON);
        }

    }

    private void checkBillForAudit(Context ctx, BOSUuid billId, FDCBillInfo billInfo)
        throws BOSException, EASBizException
    {
        ChangeAuditBillInfo model = (ChangeAuditBillInfo)billInfo;
        if(model == null || model.getCurProject() == null || model.getCurProject().getFullOrgUnit() == null)
            model = getChangeAuditBillInfo(ctx, new ObjectUuidPK(billId.toString()), getSic());
        String comId = model.getCurProject().getFullOrgUnit().getId().toString();
        boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
        if(isInCore)
        {
            String curProject = model.getCurProject().getId().toString();
            if(!ProjectPeriodStatusUtil._isClosed(ctx, curProject))
                throw new ProjectPeriodStatusException(ProjectPeriodStatusException.ISNOT_INIT, new Object[] {
                    model.getCurProject().getDisplayName()
                });
            PeriodInfo costPeriod = FDCUtils.getCurrentPeriod(ctx, curProject, true);
            if(costPeriod == null)
                throw new ProjectPeriodStatusException(ProjectPeriodStatusException.PERIOD_CNT_EMPTY, new Object[] {
                    model.getCurProject().getDisplayName()
                });
            if(model.getPeriod().getBeginDate().after(costPeriod.getEndDate()))
                throw new ContractException(ContractException.AUD_AFTERPERIOD, new Object[] {
                    model.getNumber()
                });
        }
    }

    private void checkBillForUnAudit(Context ctx, BOSUuid billId, FDCBillInfo billInfo)
        throws BOSException, EASBizException
    {
        checkContractChangeRef(ctx, billId.toString());
        ChangeAuditBillInfo model = (ChangeAuditBillInfo)billInfo;
        if(model == null || model.getCurProject() == null || model.getCurProject().getFullOrgUnit() == null)
            model = getChangeAuditBillInfo(ctx, new ObjectUuidPK(billId.toString()), getSic());
        String comId = model.getCurProject().getFullOrgUnit().getId().toString();
        boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
        if(isInCore)
        {
            String curProject = model.getCurProject().getId().toString();
            PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx, curProject, true);
            if(model.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate()))
                throw new ContractException(ContractException.CNTPERIODBEFORE);
        }
    }

    private boolean isGenerateAfterAudit(Context ctx)
        throws BOSException, EASBizException
    {
        return FDCUtils.getDefaultFDCParamByKey(ctx, null, "FDC222_GENERATEAFTERAUDIT");
    }

    private void checkContractChangeRef(Context ctx, String billId)
        throws BOSException, EASBizException
    {
        boolean isGenerateAfterAuidt = FDCUtils.getDefaultFDCParamByKey(ctx, null, "FDC222_GENERATEAFTERAUDIT");
        if(isGenerateAfterAuidt)
        {
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("changeAudit.id", billId));
            if(ContractChangeBillFactory.getLocalInstance(ctx).exists(filter))
                throw new EASBizException(new NumericExceptionSubItem("001", "\u8FDB\u884C\u6B64\u64CD\u4F5C\u4E4B\u524D\u8BF7\u5148\u5220\u9664\u5BF9\u5E94\u7684\u53D8\u66F4\u6307\u4EE4\u5355\uFF01"));
        }
    }

    private String getConAmountSql(Set contractIdSet)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT CB.FID CONID, CB.FNUMBER CONNUMBER, PC.FID PROID                       \n");
        sql.append(" ,ISNULL(SUM(PC.FBalance), 0) AMOUNT FROM T_CON_ProgrammingContract PC        \n");
        sql.append("\tINNER JOIN T_CON_ContractBill CB ON CB.FSrcProID = PC.FID                 \n");
        sql.append("\tWHERE CB.FID IN ").append(FDCUtils.buildBillIds(contractIdSet)).append("  \n");
        sql.append(" GROUP BY CB.FID, CB.FNUMBER, PC.FID");
        return sql.toString();
    }

    protected Object _checkAmount(Context ctx, IObjectPK pk, Map contractMap)
        throws BOSException, EASBizException
    {
        StringBuffer result = new StringBuffer();
        FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx, getConAmountSql(contractMap.keySet()));
        IRowSet rs = fdcSB.executeQuery();
        Map pcMappingMap = new HashMap();
        Map conDetailMap = new HashMap();
        do
            try
            {
                if(!rs.next())
                    break;
                String proId = rs.getString("PROID");
                String conId = rs.getString("CONID");
                BigDecimal proAmount = rs.getBigDecimal("AMOUNT");
                String conNumber = rs.getString("CONNUMBER");
                conDetailMap.put(conId, conNumber);
                ProConMapping mapping = (ProConMapping)pcMappingMap.get(proId);
                if(mapping == null)
                {
                    Set conSet = new HashSet();
                    conSet.add(conId);
                    mapping = new ProConMapping(proId, conSet, proAmount);
                    pcMappingMap.put(proId, mapping);
                } else
                {
                    mapping.conSet.add(conId);
                }
                BigDecimal conBudgetAmount = (BigDecimal)contractMap.get(conId);
                if(conBudgetAmount != null)
                    mapping.conBudgetAmountTotal = conBudgetAmount.add(mapping.conBudgetAmountTotal);
            }
            catch(SQLException e)
            {
                throw new SQLDataException(e);
            }
        while(true);
        Iterator it = pcMappingMap.entrySet().iterator();
        do
        {
            if(!it.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
            ProConMapping mapping = (ProConMapping)entry.getValue();
            if(!mapping.isCheckAmountPass())
            {
                result.append("\u5408\u540C ");
                String conNumber;
                for(Iterator it2 = mapping.conSet.iterator(); it2.hasNext(); result.append(" [").append(conNumber).append("] "))
                {
                    String conId = (String)it2.next();
                    conNumber = (String)conDetailMap.get(conId);
                }

                result.append("\n\u53D8\u66F4\u6D4B\u7B97\u91D1\u989D\u5927\u4E8E\u6846\u67B6\u5408\u7EA6\u7684\u89C4\u5212\u4F59\u989D\uFF0C\u662F\u5426\u63D0\u4EA4?\n\n");
            }
        } while(true);
        return result.toString();
    }

    private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.app.ChangeAuditBillControllerBean");

}


/*
	DECOMPILATION REPORT

	Decompiled from: F:\workspace\ZHDC\lib\server\eas\fdc_contract-server.jar
	Total time: 73 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/