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
import java.util.Date;
import java.util.Set;



public abstract class AbstractProductDyCostSumRptFacadeControllerBean extends AbstractBizControllerBean implements ProductDyCostSumRptFacadeController
{
    protected AbstractProductDyCostSumRptFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("1A7DED1F");
    }

    public IRowSet getProject(Context ctx, String curOrgUnitLongNumber) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("840f4998-1b4b-4704-aada-53188ea1959b"), new Object[]{ctx, curOrgUnitLongNumber});
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

    public Map getAimSellData(Context ctx, Set projectIDs) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ae14173c-dd9b-467b-98c1-7ca231060687"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAimSellData(ctx, projectIDs);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getAimSellData(Context ctx, Set projectIDs) throws BOSException
    {    	
        return null;
    }

    public Map getDynSellData(Context ctx, Set projectIDs) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("72fe0bb6-be7c-4e45-9a9a-62e5ab76b96c"), new Object[]{ctx, projectIDs});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getDynSellData(ctx, projectIDs);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getDynSellData(Context ctx, Set projectIDs) throws BOSException
    {    	
        return null;
    }

    public Map getTotalCost(Context ctx, Set projectIDs, Date bizDate) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e1296757-d731-428e-a7b5-25bd6d40ccb6"), new Object[]{ctx, projectIDs, bizDate});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getTotalCost(ctx, projectIDs, bizDate);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getTotalCost(Context ctx, Set projectIDs, Date bizDate) throws BOSException
    {    	
        return null;
    }

}