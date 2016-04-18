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
import com.kingdee.jdbc.rowset.IRowSet;



public abstract class AbstractPublicToSplitFacadeControllerBean extends AbstractBizControllerBean implements PublicToSplitFacadeController
{
    protected AbstractPublicToSplitFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("38302C39");
    }

    public void initDBTable(Context ctx, String curProjectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d6d375c8-79cf-42cf-bbc9-d3e4b54a7b67"), new Object[]{ctx, curProjectID});
            invokeServiceBefore(svcCtx);
            _initDBTable(ctx, curProjectID);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _initDBTable(Context ctx, String curProjectID) throws BOSException
    {    	
        return;
    }

    public IRowSet getRowData(Context ctx, String curProjectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("972eb337-89b8-4d24-9704-96e86683e66d"), new Object[]{ctx, curProjectID});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getRowData(ctx, curProjectID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _getRowData(Context ctx, String curProjectID) throws BOSException
    {    	
        return null;
    }

    public IRowSet getPublicToSplitColums(Context ctx, String curProjectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("932c72c1-306a-4539-8c76-adf9224ec781"), new Object[]{ctx, curProjectID});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getPublicToSplitColums(ctx, curProjectID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _getPublicToSplitColums(Context ctx, String curProjectID) throws BOSException
    {    	
        return null;
    }

    public IRowSet getPublicRow(Context ctx, String curProjectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e1be2282-d411-4725-9a79-776a7121bf07"), new Object[]{ctx, curProjectID});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getPublicRow(ctx, curProjectID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _getPublicRow(Context ctx, String curProjectID) throws BOSException
    {    	
        return null;
    }

}