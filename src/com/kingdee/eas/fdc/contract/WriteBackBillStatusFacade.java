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
     *oa跟附件id反写附件-User defined method
     *@param fid 附件id
     *@param fileName 附件名称
     *@param file 附件内容，字节流
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
     *oa回写eas单据信息-User defined method
     *@param billType 单据类型,如：“1”代表合同附件类型；“2”代表付款申请单类型；“3”代表结算单类型；“4”代表变更签证确认单类型；“5”代表无文本合同类型
     *@param billId 单据id
     *@param state 单据状态
     *@param isExistAttachment 是否存在附件
     *@param attachmentInfoList 附件数组
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
     *获取附件-User defined method
     *@param id 附件id
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
     *获取单据的状态-User defined method
     *@param id 单据id
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
     *测试生成付款单-User defined method
     *@param billId 单据id
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