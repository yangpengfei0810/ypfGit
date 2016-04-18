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
import java.math.BigDecimal;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.fdc.aimcost.DynCostDetailDataCollection;
import com.kingdee.eas.fdc.aimcost.DynCostDetailInfo;



public abstract class AbstractDynCostDetailFacadeControllerBean extends AbstractBizControllerBean implements DynCostDetailFacadeController
{
    protected AbstractDynCostDetailFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("4FB7D69A");
    }

    public boolean saveDynCostDetailData(Context ctx, DynCostDetailDataCollection dynCostDetailDataCollection, DynCostDetailInfo dynCostDetailInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("041506df-56d2-486d-8f05-8af58ab86b7d"), new Object[]{ctx, dynCostDetailDataCollection, dynCostDetailInfo});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_saveDynCostDetailData(ctx, dynCostDetailDataCollection, dynCostDetailInfo);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
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
    protected boolean _saveDynCostDetailData(Context ctx, DynCostDetailDataCollection dynCostDetailDataCollection, DynCostDetailInfo dynCostDetailInfo) throws BOSException, EASBizException
    {    	
        return false;
    }

    public IRowSet getDynCostDetailData(Context ctx, String curProjectID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("446215ea-8f12-4866-94d5-d010806f856c"), new Object[]{ctx, curProjectID});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getDynCostDetailData(ctx, curProjectID);
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
    protected IRowSet _getDynCostDetailData(Context ctx, String curProjectID) throws BOSException, EASBizException
    {    	
        return null;
    }

    public IRowSet getDetailDataForID(Context ctx, String parentID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3fbc3698-167c-425b-918e-c9ef08e66bef"), new Object[]{ctx, parentID});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getDetailDataForID(ctx, parentID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _getDetailDataForID(Context ctx, String parentID) throws BOSException
    {    	
        return null;
    }

    public BigDecimal getMaxVer(Context ctx, String curProjectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d4fdaed5-79ee-4d1b-8f6f-e22c12f100bc"), new Object[]{ctx, curProjectID});
            invokeServiceBefore(svcCtx);
            BigDecimal retValue = (BigDecimal)_getMaxVer(ctx, curProjectID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected BigDecimal _getMaxVer(Context ctx, String curProjectID) throws BOSException
    {    	
        return null;
    }

    public Map getSumLastCost(Context ctx, String curProjectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a16c4792-99d4-42dc-bb84-230de663632e"), new Object[]{ctx, curProjectID});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getSumLastCost(ctx, curProjectID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getSumLastCost(Context ctx, String curProjectID) throws BOSException
    {    	
        return null;
    }

    public Map getSumLastCostForSave(Context ctx, String parentID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ad63a6cb-9181-4db4-9e16-5e5df609d072"), new Object[]{ctx, parentID});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getSumLastCostForSave(ctx, parentID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getSumLastCostForSave(Context ctx, String parentID) throws BOSException
    {    	
        return null;
    }

    public Map getpayRequest(Context ctx, String contractid, String pcid) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("63e6d029-ec24-446c-b11f-a7a66753a767"), new Object[]{ctx, contractid, pcid});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getpayRequest(ctx, contractid, pcid);
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
    protected Map _getpayRequest(Context ctx, String contractid, String pcid) throws BOSException, EASBizException
    {    	
        return null;
    }

}