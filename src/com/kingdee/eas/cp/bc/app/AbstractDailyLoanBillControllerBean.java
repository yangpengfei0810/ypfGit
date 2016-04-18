package com.kingdee.eas.cp.bc.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ma.bg.BgCtrlParam;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.cp.bc.LoanBillCollection;
import com.kingdee.eas.cp.bc.BizCollBillBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.cp.bc.BizCollCoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.cp.bc.DailyLoanBillCollection;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;



public abstract class AbstractDailyLoanBillControllerBean extends LoanBillControllerBean implements DailyLoanBillController
{
    protected AbstractDailyLoanBillControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("8110AAB2");
    }

    public DailyLoanBillInfo getDailyLoanBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-0061c0a813d4"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            DailyLoanBillInfo retValue = (DailyLoanBillInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk);
    }

    public DailyLoanBillInfo getDailyLoanBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-0062c0a813d4"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            DailyLoanBillInfo retValue = (DailyLoanBillInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk, selector);
    }

    public DailyLoanBillInfo getDailyLoanBillInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-0063c0a813d4"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            DailyLoanBillInfo retValue = (DailyLoanBillInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._getValue(ctx, oql);
    }

    public DailyLoanBillCollection getDailyLoanBillCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-0064c0a813d4"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            DailyLoanBillCollection retValue = (DailyLoanBillCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx) throws BOSException
    {
        return super._getCollection(ctx, svcCtx);
    }

    public DailyLoanBillCollection getDailyLoanBillCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-0065c0a813d4"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            DailyLoanBillCollection retValue = (DailyLoanBillCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, EntityViewInfo view) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, view);
    }

    public DailyLoanBillCollection getDailyLoanBillCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-0066c0a813d4"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            DailyLoanBillCollection retValue = (DailyLoanBillCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, String oql) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, oql);
    }

    public void setPassState(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-003dc0a81038"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _setPassState(ctx, id);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setPassState(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public void setNotPassState(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-003ec0a81038"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _setNotPassState(ctx, id);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setNotPassState(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public BgCtrlParam[] getBgParam(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-0043c0a81038"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            BgCtrlParam[] retValue = (BgCtrlParam[])_getBgParam(ctx, id);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract BgCtrlParam[] _getBgParam(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public void setPaymentState(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-0044c0a81038"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _setPaymentState(ctx, id);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setPaymentState(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public void setCloseState(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-0045c0a81038"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _setCloseState(ctx, id);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setCloseState(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public boolean needBgAudit(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-0046c0a81038"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_needBgAudit(ctx, id);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _needBgAudit(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public void reBackBg(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-0047c0a81038"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _reBackBg(ctx, id);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _reBackBg(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public void setCheckingState(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-0050c0a81038"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _setCheckingState(ctx, id);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setCheckingState(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public void payment(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-009fc0a81038"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _payment(ctx, id);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _payment(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public void setDraftState(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("747a6e60-0105-1000-e000-0081c0a81038"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _setDraftState(ctx, id);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setDraftState(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public List getCanDeleteIDs(Context ctx, List list) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aa74960a-0105-1000-e000-0023c0a812ce"), new Object[]{ctx, list});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getCanDeleteIDs(ctx, list);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract List _getCanDeleteIDs(Context ctx, List list) throws BOSException, EASBizException;

    public boolean isCanModify(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aa74960a-0105-1000-e000-0029c0a812ce"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_isCanModify(ctx, id);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _isCanModify(Context ctx, BOSUuid id) throws BOSException, EASBizException;

    public void setState(Context ctx, BOSUuid id, StateEnum state) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aa7a193c-0105-1000-e000-0202c0a812ce"), new Object[]{ctx, id, state});
            invokeServiceBefore(svcCtx);
            _setState(ctx, id, state);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setState(Context ctx, BOSUuid id, StateEnum state) throws BOSException, EASBizException;

    public LoanBillCollection getLoanBillCollection (Context ctx) throws BOSException
    {
    	return (LoanBillCollection)(getDailyLoanBillCollection(ctx).cast(LoanBillCollection.class));
    }
    public LoanBillCollection getLoanBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (LoanBillCollection)(getDailyLoanBillCollection(ctx, view).cast(LoanBillCollection.class));
    }
    public LoanBillCollection getLoanBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (LoanBillCollection)(getDailyLoanBillCollection(ctx, oql).cast(LoanBillCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getDailyLoanBillCollection(ctx).cast(BizCollBillBaseCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getDailyLoanBillCollection(ctx, view).cast(BizCollBillBaseCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getDailyLoanBillCollection(ctx, oql).cast(BizCollBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getDailyLoanBillCollection(ctx).cast(BizCollCoreBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getDailyLoanBillCollection(ctx, view).cast(BizCollCoreBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getDailyLoanBillCollection(ctx, oql).cast(BizCollCoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getDailyLoanBillCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getDailyLoanBillCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getDailyLoanBillCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getDailyLoanBillCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getDailyLoanBillCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getDailyLoanBillCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getDailyLoanBillCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getDailyLoanBillCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getDailyLoanBillCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}