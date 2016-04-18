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
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.Set;



public abstract class AbstractProjectCostDynSumRptFacadeControllerBean extends AbstractBizControllerBean implements ProjectCostDynSumRptFacadeController
{
    protected AbstractProjectCostDynSumRptFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("6134D46F");
    }

    public IRowSet getProject(Context ctx, String curOrgUnitLongNumber) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("43098ec7-59aa-4cc4-8dd3-7c29b48da77d"), new Object[]{ctx, curOrgUnitLongNumber});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getProject(ctx, curOrgUnitLongNumber);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _getProject(Context ctx, String curOrgUnitLongNumber) throws BOSException
    {    	
        return null;
    }

    public Map getAimCost(Context ctx, Set projectIDs) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2820aa2a-61ea-4557-82e8-b894d93d7b25"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAimCost(ctx, projectIDs);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getAimCost(Context ctx, Set projectIDs) throws BOSException
    {    	
        return null;
    }

    public Map getNewDynCost(Context ctx, Set projectIDs) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cb2e5bbd-a483-4887-b504-401087325d3d"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getNewDynCost(ctx, projectIDs);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getNewDynCost(Context ctx, Set projectIDs) throws BOSException
    {    	
        return null;
    }

    public Map getMarketingAimCost(Context ctx, Set projectIDs) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("dc6ed88e-15ba-4845-9fe5-eed5e60d8af0"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getMarketingAimCost(ctx, projectIDs);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getMarketingAimCost(Context ctx, Set projectIDs) throws BOSException
    {    	
        return null;
    }

    public Map getMarketingNewDynCost(Context ctx, Set projectIDs) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("875d8b20-0b41-4093-b3fb-c3d883bd6445"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getMarketingNewDynCost(ctx, projectIDs);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getMarketingNewDynCost(Context ctx, Set projectIDs) throws BOSException
    {    	
        return null;
    }

}