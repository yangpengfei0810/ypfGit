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



public abstract class AbstractPublicSetSplitFacadeControllerBean extends AbstractBizControllerBean implements PublicSetSplitFacadeController
{
    protected AbstractPublicSetSplitFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("254E0132");
    }

    public IRowSet getPublicSetSplitColums(Context ctx, String curProjectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5f34b4f4-e8e4-4a9f-bef3-a0a4be150958"), new Object[]{ctx, curProjectID});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getPublicSetSplitColums(ctx, curProjectID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _getPublicSetSplitColums(Context ctx, String curProjectID) throws BOSException
    {    	
        return null;
    }

    public void initDBTable(Context ctx, String projectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fc8d64b7-338d-479d-aadb-0dd0fefe443a"), new Object[]{ctx, projectID});
            invokeServiceBefore(svcCtx);
            _initDBTable(ctx, projectID);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _initDBTable(Context ctx, String projectID) throws BOSException
    {    	
        return;
    }

    public IRowSet getRowData(Context ctx, String curProjectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("535d74d1-8cc5-4c74-bf46-075ac7735a53"), new Object[]{ctx, curProjectID});
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

    public IRowSet getPublicRow(Context ctx, String curProjectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("eeb7f210-7af5-438c-8f87-12f6df1d5d2e"), new Object[]{ctx, curProjectID});
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