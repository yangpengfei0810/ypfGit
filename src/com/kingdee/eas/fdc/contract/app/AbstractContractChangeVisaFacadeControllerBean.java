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
import com.kingdee.jdbc.rowset.IRowSet;
import java.math.BigDecimal;



public abstract class AbstractContractChangeVisaFacadeControllerBean extends AbstractBizControllerBean implements ContractChangeVisaFacadeController
{
    protected AbstractContractChangeVisaFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("39212A78");
    }

    public String saveCostAmountByChngSupEntyID(Context ctx, String pk, BigDecimal amount) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("909372f2-5e14-4b82-b98a-0ed1175090d2"), new Object[]{ctx, pk, amount});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_saveCostAmountByChngSupEntyID(ctx, pk, amount);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _saveCostAmountByChngSupEntyID(Context ctx, String pk, BigDecimal amount) throws BOSException
    {    	
        return null;
    }

    public String saveCostAmountByChngAuditBillID(Context ctx, String pk) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1019a214-b9f4-4595-8d51-16076a6805ce"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_saveCostAmountByChngAuditBillID(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _saveCostAmountByChngAuditBillID(Context ctx, String pk) throws BOSException
    {    	
        return null;
    }

    public IRowSet getCostAmountByChngAuditBillID(Context ctx, String pk, String rmk) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d8d353d3-73d1-4223-b90a-bc2472a31b90"), new Object[]{ctx, pk, rmk});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getCostAmountByChngAuditBillID(ctx, pk, rmk);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _getCostAmountByChngAuditBillID(Context ctx, String pk, String rmk) throws BOSException
    {    	
        return null;
    }

    public String getChngAuditBillNumber(Context ctx, String orgId, String prjId, String chngId, String prefix) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e58b0712-bdf8-4743-b149-b99ce49a5d90"), new Object[]{ctx, orgId, prjId, chngId, prefix});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_getChngAuditBillNumber(ctx, orgId, prjId, chngId, prefix);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _getChngAuditBillNumber(Context ctx, String orgId, String prjId, String chngId, String prefix) throws BOSException
    {    	
        return null;
    }

    public void executeUpdate(Context ctx, String sql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("080bd620-f6da-49f1-84c3-7fed67b3f6aa"), new Object[]{ctx, sql});
            invokeServiceBefore(svcCtx);
            _executeUpdate(ctx, sql);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _executeUpdate(Context ctx, String sql) throws BOSException
    {    	
        return;
    }

    public String saveVisaAmountByChngAuditBillID(Context ctx, String pk) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("226fdf24-bb3b-47ce-8a15-421636066a50"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_saveVisaAmountByChngAuditBillID(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _saveVisaAmountByChngAuditBillID(Context ctx, String pk) throws BOSException
    {    	
        return null;
    }

    public String unSaveCostAmountByChngAuditBillID(Context ctx, String pk) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("68829f78-3c34-4c44-af9b-57741b59dfdc"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_unSaveCostAmountByChngAuditBillID(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _unSaveCostAmountByChngAuditBillID(Context ctx, String pk) throws BOSException
    {    	
        return null;
    }

    public String unSaveVisaAmountByChngAuditBillID(Context ctx, String pk) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6c2e2702-36d0-42f5-b820-f4e3d2d99052"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_unSaveVisaAmountByChngAuditBillID(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _unSaveVisaAmountByChngAuditBillID(Context ctx, String pk) throws BOSException
    {    	
        return null;
    }

    public String clearCode(Context ctx, String prmStr) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("52580613-4eaa-4dba-b89b-b73c053302e2"), new Object[]{ctx, prmStr});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_clearCode(ctx, prmStr);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _clearCode(Context ctx, String prmStr) throws BOSException
    {    	
        return null;
    }

}