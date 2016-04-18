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
import com.kingdee.eas.cp.bc.EvectionReqBillInfo;
import com.kingdee.eas.cp.bc.BizCollBillBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.cp.bc.BizCollCoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.cp.bc.EvectionReqBillCollection;
import java.util.List;



public abstract class AbstractEvectionReqBillControllerBean extends BizCollBillBaseControllerBean implements EvectionReqBillController
{
    protected AbstractEvectionReqBillControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("DE853384");
    }

    public EvectionReqBillInfo getEvectionReqBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-0046c0a813d4"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            EvectionReqBillInfo retValue = (EvectionReqBillInfo)_getValue(ctx, pk);
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

    public EvectionReqBillInfo getEvectionReqBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-0047c0a813d4"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            EvectionReqBillInfo retValue = (EvectionReqBillInfo)_getValue(ctx, pk, selector);
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

    public EvectionReqBillInfo getEvectionReqBillInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-0048c0a813d4"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            EvectionReqBillInfo retValue = (EvectionReqBillInfo)_getValue(ctx, oql);
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

    public EvectionReqBillCollection getEvectionReqBillCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-0049c0a813d4"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            EvectionReqBillCollection retValue = (EvectionReqBillCollection)_getCollection(ctx, svcCtx);
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

    public EvectionReqBillCollection getEvectionReqBillCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-004ac0a813d4"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            EvectionReqBillCollection retValue = (EvectionReqBillCollection)_getCollection(ctx, svcCtx, view);
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

    public EvectionReqBillCollection getEvectionReqBillCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("846b59a5-0104-1000-e000-004bc0a813d4"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            EvectionReqBillCollection retValue = (EvectionReqBillCollection)_getCollection(ctx, svcCtx, oql);
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-0040c0a81038"), new Object[]{ctx, id});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-0041c0a81038"), new Object[]{ctx, id});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-0049c0a81038"), new Object[]{ctx, id});
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

    public void setCloseState(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-004ac0a81038"), new Object[]{ctx, id});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-004bc0a81038"), new Object[]{ctx, id});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-004cc0a81038"), new Object[]{ctx, id});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70d5ce2c-0105-1000-e000-004ec0a81038"), new Object[]{ctx, id});
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

    public void setDraftState(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("747a6e60-0105-1000-e000-0077c0a81038"), new Object[]{ctx, id});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aa74960a-0105-1000-e000-0020c0a812ce"), new Object[]{ctx, list});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aa74960a-0105-1000-e000-002bc0a812ce"), new Object[]{ctx, id});
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

    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getEvectionReqBillCollection(ctx).cast(BizCollBillBaseCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getEvectionReqBillCollection(ctx, view).cast(BizCollBillBaseCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getEvectionReqBillCollection(ctx, oql).cast(BizCollBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getEvectionReqBillCollection(ctx).cast(BizCollCoreBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getEvectionReqBillCollection(ctx, view).cast(BizCollCoreBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getEvectionReqBillCollection(ctx, oql).cast(BizCollCoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getEvectionReqBillCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getEvectionReqBillCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getEvectionReqBillCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getEvectionReqBillCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getEvectionReqBillCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getEvectionReqBillCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getEvectionReqBillCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getEvectionReqBillCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getEvectionReqBillCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}