package com.kingdee.eas.fdc.aimcost.app;

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
import java.util.Map;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import java.util.Set;



public abstract class AbstractProductDynamicDetailFacadeControllerBean extends AbstractBizControllerBean implements ProductDynamicDetailFacadeController
{
    protected AbstractProductDynamicDetailFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("5B3C1B52");
    }

    public ProductTypeCollection getProductTypes(Context ctx, Set projectIDs) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("17873f21-d1b1-40fb-9193-a7bb42de0567"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            ProductTypeCollection retValue = (ProductTypeCollection)_getProductTypes(ctx, projectIDs);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected ProductTypeCollection _getProductTypes(Context ctx, Set projectIDs) throws BOSException
    {    	
        return null;
    }

    public Map getDetailData(Context ctx, Set projectIDs) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8e584bb8-75d2-4072-ae6c-748e8e06f63b"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getDetailData(ctx, projectIDs);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getDetailData(Context ctx, Set projectIDs) throws BOSException
    {    	
        return null;
    }

    public Map getSplitAmount(Context ctx, String projectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b8c94ba2-57dc-4ccf-a51c-a2035879ea1c"), new Object[]{ctx, projectID});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getSplitAmount(ctx, projectID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getSplitAmount(Context ctx, String projectID) throws BOSException
    {    	
        return null;
    }

}