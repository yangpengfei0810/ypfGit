package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import com.kingdee.eas.base.attachment.AttachmentCollection;

public class WriteBackBillStatusFacade extends AbstractBizCtrl implements IWriteBackBillStatusFacade
{
    public WriteBackBillStatusFacade()
    {
        super();
        registerInterface(IWriteBackBillStatusFacade.class, this);
    }
    public WriteBackBillStatusFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWriteBackBillStatusFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4F313C7E");
    }
    private WriteBackBillStatusFacadeController getController() throws BOSException
    {
        return (WriteBackBillStatusFacadeController)getBizController();
    }
    /**
     *oa������id��д����-User defined method
     *@param fid ����id
     *@param fileName ��������
     *@param file �������ݣ��ֽ���
     *@return
     */
    public boolean alterAttachmentByID(String fid, String fileName, byte[] file) throws BOSException
    {
        try {
            return getController().alterAttachmentByID(getContext(), fid, fileName, file);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *oa��дeas������Ϣ-User defined method
     *@param billType ��������,�磺��1�������ͬ�������ͣ���2�����������뵥���ͣ���3��������㵥���ͣ���4��������ǩ֤ȷ�ϵ����ͣ���5���������ı���ͬ����
     *@param billId ����id
     *@param state ����״̬
     *@param isExistAttachment �Ƿ���ڸ���
     *@param attachmentInfoList ��������
     *@return
     */
    public String modBillInfo(String billType, String billId, String state, boolean isExistAttachment, AttachmentCollection attachmentInfoList) throws BOSException
    {
        try {
            return getController().modBillInfo(getContext(), billType, billId, state, isExistAttachment, attachmentInfoList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ����-User defined method
     *@param id ����id
     *@return
     */
    public byte[] getAttachment(String id) throws BOSException
    {
        try {
            return getController().getAttachment(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ���ݵ�״̬-User defined method
     *@param id ����id
     *@return
     */
    public String getBillState(String id) throws BOSException
    {
        try {
            return getController().getBillState(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������ɸ��-User defined method
     *@param billId ����id
     */
    public void createPayBill(String billId) throws BOSException
    {
        try {
            getController().createPayBill(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}