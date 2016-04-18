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
import com.kingdee.eas.base.attachment.AttachmentCollection;



public abstract class AbstractWriteBackBillStatusFacadeControllerBean extends AbstractBizControllerBean implements WriteBackBillStatusFacadeController
{
    protected AbstractWriteBackBillStatusFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("4F313C7E");
    }

    public boolean alterAttachmentByID(Context ctx, String fid, String fileName, byte[] file) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8cbcd54e-4249-4fc9-8bb9-43f10582b2e7"), new Object[]{ctx, fid, fileName, file});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_alterAttachmentByID(ctx, fid, fileName, file);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected boolean _alterAttachmentByID(Context ctx, String fid, String fileName, byte[] file) throws BOSException
    {    	
        return false;
    }

    public String modBillInfo(Context ctx, String billType, String billId, String state, boolean isExistAttachment, AttachmentCollection attachmentInfoList) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c0fd59a0-95c3-444e-bd68-32546d02c0d1"), new Object[]{ctx, billType, billId, state, new Boolean(isExistAttachment), attachmentInfoList});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_modBillInfo(ctx, billType, billId, state, isExistAttachment, attachmentInfoList);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _modBillInfo(Context ctx, String billType, String billId, String state, boolean isExistAttachment, IObjectCollection attachmentInfoList) throws BOSException
    {    	
        return null;
    }

    public byte[] getAttachment(Context ctx, String id) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5360ef5e-112a-4db7-b6c4-cc0f4420c1fd"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            byte[] retValue = (byte[])_getAttachment(ctx, id);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected byte[] _getAttachment(Context ctx, String id) throws BOSException
    {    	
        return null;
    }

    public String getBillState(Context ctx, String id) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3944ad2d-cd75-48b7-ad18-ac9d994d7e48"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_getBillState(ctx, id);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _getBillState(Context ctx, String id) throws BOSException
    {    	
        return null;
    }

    public void createPayBill(Context ctx, String billId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("19aa62ac-d1bd-4ffc-9c3c-c366e0fd0ec8"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            _createPayBill(ctx, billId);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _createPayBill(Context ctx, String billId) throws BOSException
    {    	
        return;
    }

}