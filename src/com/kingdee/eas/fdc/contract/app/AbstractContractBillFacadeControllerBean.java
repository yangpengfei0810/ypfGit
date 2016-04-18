package com.kingdee.eas.fdc.contract.app;

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
import java.math.BigDecimal;



public abstract class AbstractContractBillFacadeControllerBean extends AbstractBizControllerBean implements ContractBillFacadeController
{
    protected AbstractContractBillFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("667627AE");
    }

    public boolean updateSplitAmt(Context ctx, String id, BigDecimal splitAmt) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5de19739-d231-4a5a-8a86-a3bf5cce8388"), new Object[]{ctx, id, splitAmt});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_updateSplitAmt(ctx, id, splitAmt);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected boolean _updateSplitAmt(Context ctx, String id, BigDecimal splitAmt) throws BOSException
    {    	
        return false;
    }

    public boolean updateTotalSplitAmount(Context ctx, String id, BigDecimal totalSplitAmount) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7aa10b94-259e-494e-8d18-60c2770bc7fb"), new Object[]{ctx, id, totalSplitAmount});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_updateTotalSplitAmount(ctx, id, totalSplitAmount);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected boolean _updateTotalSplitAmount(Context ctx, String id, BigDecimal totalSplitAmount) throws BOSException
    {    	
        return false;
    }

    public BigDecimal getTotalSplitAmount(Context ctx, String pcID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("43dc9030-c0b7-499c-ac04-3ac9857a7cbd"), new Object[]{ctx, pcID});
            invokeServiceBefore(svcCtx);
            BigDecimal retValue = (BigDecimal)_getTotalSplitAmount(ctx, pcID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected BigDecimal _getTotalSplitAmount(Context ctx, String pcID) throws BOSException
    {    	
        return null;
    }

}