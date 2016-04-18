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
import com.kingdee.eas.cp.bc.BizCollBillBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.cp.bc.BizCollCoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.cp.bc.BizAccountBillCollection;
import com.kingdee.eas.cp.bc.ExpenseAccountBillCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;



public abstract class AbstractBizAccountBillControllerBean extends ExpenseAccountBillControllerBean implements BizAccountBillController
{
    protected AbstractBizAccountBillControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("4A44F49F");
    }

    public BizAccountBillInfo getBizAccountBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-001bc0a813b4"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            BizAccountBillInfo retValue = (BizAccountBillInfo)_getValue(ctx, pk, selector);
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

    public BizAccountBillInfo getBizAccountBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-001cc0a813b4"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            BizAccountBillInfo retValue = (BizAccountBillInfo)_getValue(ctx, pk);
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

    public BizAccountBillInfo getBizAccountBillInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-001dc0a813b4"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            BizAccountBillInfo retValue = (BizAccountBillInfo)_getValue(ctx, oql);
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

    public BizAccountBillCollection getBizAccountBillCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-001ec0a813b4"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            BizAccountBillCollection retValue = (BizAccountBillCollection)_getCollection(ctx, svcCtx);
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

    public BizAccountBillCollection getBizAccountBillCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-001fc0a813b4"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            BizAccountBillCollection retValue = (BizAccountBillCollection)_getCollection(ctx, svcCtx, view);
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

    public BizAccountBillCollection getBizAccountBillCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("990fd9fb-0104-1000-e000-0020c0a813b4"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            BizAccountBillCollection retValue = (BizAccountBillCollection)_getCollection(ctx, svcCtx, oql);
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
    	return (ExpenseAccountBillCollection)(getBizAccountBillCollection(ctx).cast(ExpenseAccountBillCollection.class));
    }
    public ExpenseAccountBillCollection getExpenseAccountBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ExpenseAccountBillCollection)(getBizAccountBillCollection(ctx, view).cast(ExpenseAccountBillCollection.class));
    }
    public ExpenseAccountBillCollection getExpenseAccountBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (ExpenseAccountBillCollection)(getBizAccountBillCollection(ctx, oql).cast(ExpenseAccountBillCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getBizAccountBillCollection(ctx).cast(BizCollBillBaseCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getBizAccountBillCollection(ctx, view).cast(BizCollBillBaseCollection.class));
    }
    public BizCollBillBaseCollection getBizCollBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BizCollBillBaseCollection)(getBizAccountBillCollection(ctx, oql).cast(BizCollBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getBizAccountBillCollection(ctx).cast(BizCollCoreBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getBizAccountBillCollection(ctx, view).cast(BizCollCoreBillBaseCollection.class));
    }
    public BizCollCoreBillBaseCollection getBizCollCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BizCollCoreBillBaseCollection)(getBizAccountBillCollection(ctx, oql).cast(BizCollCoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getBizAccountBillCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getBizAccountBillCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getBizAccountBillCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getBizAccountBillCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getBizAccountBillCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getBizAccountBillCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getBizAccountBillCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getBizAccountBillCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getBizAccountBillCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}