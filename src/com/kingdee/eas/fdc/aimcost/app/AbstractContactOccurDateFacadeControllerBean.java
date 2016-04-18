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
import java.util.List;



public abstract class AbstractContactOccurDateFacadeControllerBean extends AbstractBizControllerBean implements ContactOccurDateFacadeController
{
    protected AbstractContactOccurDateFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("F1E998CB");
    }

    public List getDate(Context ctx, String projectID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("927c2d5e-9e45-4fc5-8cb0-7f201e287d3e"), new Object[]{ctx, projectID});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getDate(ctx, projectID);
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
    protected List _getDate(Context ctx, String projectID) throws BOSException, EASBizException
    {    	
        return null;
    }

    public List getProductType(Context ctx, String projectID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c922ccad-20b8-4eaa-a7f9-eb9986435ac6"), new Object[]{ctx, projectID});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getProductType(ctx, projectID);
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
    protected List _getProductType(Context ctx, String projectID) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Map getAimAndHappenedData(Context ctx, String projectID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f7775754-1a0e-4295-9bfe-7d42bc2dd169"), new Object[]{ctx, projectID});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAimAndHappenedData(ctx, projectID);
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
    protected Map _getAimAndHappenedData(Context ctx, String projectID) throws BOSException, EASBizException
    {    	
        return null;
    }

    public List getContractData(Context ctx, String projectID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c62ad04a-de60-49a8-a35d-65b9f101434d"), new Object[]{ctx, projectID});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getContractData(ctx, projectID);
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
    protected List _getContractData(Context ctx, String projectID) throws BOSException, EASBizException
    {    	
        return null;
    }

    public List getDymData(Context ctx, String projectID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cc703262-9740-4177-99e6-ee9be2236178"), new Object[]{ctx, projectID});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getDymData(ctx, projectID);
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
    protected List _getDymData(Context ctx, String projectID) throws BOSException, EASBizException
    {    	
        return null;
    }

    public Map getAimCost(Context ctx, String projectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("16b60ef3-e432-44dd-af2a-b6a48888edff"), new Object[]{ctx, projectID});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAimCost(ctx, projectID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getAimCost(Context ctx, String projectID) throws BOSException
    {    	
        return null;
    }

    public Map getHappenedCost(Context ctx, String projectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("384154c6-68bc-4663-aeba-7abb4a49a19a"), new Object[]{ctx, projectID});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getHappenedCost(ctx, projectID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getHappenedCost(Context ctx, String projectID) throws BOSException
    {    	
        return null;
    }

}