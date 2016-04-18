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
import com.kingdee.eas.common.EASBizException;
import java.util.Map;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.Set;



public abstract class AbstractJanDynamicCostSumFacadeControllerBean extends AbstractBizControllerBean implements JanDynamicCostSumFacadeController
{
    protected AbstractJanDynamicCostSumFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("DDC48CF9");
    }

    public IRowSet getProject(Context ctx, String curOrgUnitLongNumber) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2acdb6f8-6d59-4ca9-acb8-dbd58fa5fead"), new Object[]{ctx, curOrgUnitLongNumber});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getProject(ctx, curOrgUnitLongNumber);
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
    protected IRowSet _getProject(Context ctx, String curOrgUnitLongNumber) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Map getAimCost(Context ctx, Set projectIDs) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0c2c2ef9-fb11-4695-a073-701193713498"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAimCost(ctx, projectIDs);
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
    protected Map _getAimCost(Context ctx, Set projectIDs) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Map getMaterialAdj(Context ctx, Set projectIDs) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b87ccb1a-34a7-4671-adfb-f5093a73c709"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getMaterialAdj(ctx, projectIDs);
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
    protected Map _getMaterialAdj(Context ctx, Set projectIDs) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Map getMaterialAdjRate(Context ctx, Set projectIDs) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e54c23d3-6714-420d-8f6a-8e5ebb317d20"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getMaterialAdjRate(ctx, projectIDs);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getMaterialAdjRate(Context ctx, Set projectIDs) throws BOSException
    {    	
        return null;
    }

}