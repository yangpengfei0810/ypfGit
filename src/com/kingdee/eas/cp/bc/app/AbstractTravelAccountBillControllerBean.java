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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.cp.bc.TravelAccountBillCollection;
import com.kingdee.eas.cp.bc.BizCollBillBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.cp.bc.BizCollCoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.cp.bc.ExpenseAccountBillCollection;
import com.kingdee.eas.cp.bc.TravelAccountBillInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;



public abstract class AbstractTravelAccountBillControllerBean extends ExpenseAccountBillControllerBean implements TravelAccountBillController
{
    protected AbstractTravelAccountBillControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("C57003BC");
    }

    public TravelAccountBillInfo getTravelAccountBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-0033c0a813b4"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            TravelAccountBillInfo retValue = (TravelAccountBillInfo)_getValue(ctx, pk);
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

    public TravelAccountBillInfo getTravelAccountBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-0034c0a813b4"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            TravelAccountBillInfo retValue = (TravelAccountBillInfo)_getValue(ctx, pk, selector);
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

    public TravelAccountBillInfo getTravelAccountBillInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-0035c0a813b4"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            TravelAccountBillInfo retValue = (TravelAccountBillInfo)_getValue(ctx, oql);
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

    public TravelAccountBillCollection getTravelAccountBillCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-0036c0a813b4"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            TravelAccountBillCollection retValue = (TravelAccountBillCollection)_getCollection(ctx, svcCtx);
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

    public TravelAccountBillCollection getTravelAccountBillCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-0037c0a813b4"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            TravelAccountBillCollection retValue = (TravelAccountBillCollection)_getCollection(ctx, svcCtx, view);
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

    public TravelAccountBillCollection getTravelAccountBillCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-0038c0a813b4"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            TravelAccountBillCollection retValue = (TravelAccountBillCollection)_getCollection(ctx, svcCtx, oql);
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

    public ExpenseAccountBillCollection getExpenseAccountBillCollection (Context ctx) throws BOSException
    {
    	return (ExpenseAccountBillCollection)(getTravelAccountBillCollection(ctx).cast(ExpenseAccountBillCollection.class));
    }
    public ExpenseAccountBillCollection getExpenseAccountBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ExpenseAccountBillCollection)(getTravelAccountBillCollection(ctx, view).cast(ExpenseAccountBillCollection.class));
    }
    public ExpenseAccountBillCollection getExpenseAccountBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (ExpenseAccountBillCollection)(getTravelAccountBillCollection(ctx, oql).cast(ExpenseAccountBillCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getTravelAccountBillCollection(ctx).cast(BizCollBillBaseCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getTravelAccountBillCollection(ctx, view).cast(BizCollBillBaseCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getTravelAccountBillCollection(ctx, oql).cast(BizCollBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getTravelAccountBillCollection(ctx).cast(BizCollCoreBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getTravelAccountBillCollection(ctx, view).cast(BizCollCoreBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getTravelAccountBillCollection(ctx, oql).cast(BizCollCoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getTravelAccountBillCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getTravelAccountBillCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getTravelAccountBillCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getTravelAccountBillCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getTravelAccountBillCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getTravelAccountBillCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getTravelAccountBillCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getTravelAccountBillCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getTravelAccountBillCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}