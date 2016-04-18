package com.kingdee.eas.fdc.finance.app;

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
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.cas.DisposerActionEnum;



public abstract class AbstractNewOldRelationFacadeControllerBean extends AbstractBizControllerBean implements NewOldRelationFacadeController
{
    protected AbstractNewOldRelationFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("775FC58C");
    }

    public void getOldOrgRelation(Context ctx, String treeID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("152ee70e-f96e-4a19-be33-394930f5cd37"), new Object[]{ctx, treeID});
            invokeServiceBefore(svcCtx);
            _getOldOrgRelation(ctx, treeID);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _getOldOrgRelation(Context ctx, String treeID) throws BOSException
    {    	
        return;
    }

    public void importGroupAccountRelation(Context ctx, String company) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e721dc17-4425-4df9-a3a4-7ed429ba4111"), new Object[]{ctx, company});
            invokeServiceBefore(svcCtx);
            _importGroupAccountRelation(ctx, company);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _importGroupAccountRelation(Context ctx, String company) throws BOSException, EASBizException
    {    	
        return;
    }

    public void dispose(Context ctx, PaymentBillInfo paymentBillInfo, DisposerActionEnum disposerActionEnum) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ecec7c8e-b7ba-40a9-84c0-6103aa247b29"), new Object[]{ctx, paymentBillInfo, disposerActionEnum});
            invokeServiceBefore(svcCtx);
            _dispose(ctx, paymentBillInfo, disposerActionEnum);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _dispose(Context ctx, IObjectValue paymentBillInfo, DisposerActionEnum disposerActionEnum) throws BOSException, EASBizException
    {    	
        return;
    }

}