package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.attachment.AttachmentCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WriteBackBillStatusFacadeController extends BizController
{
    public boolean alterAttachmentByID(Context ctx, String fid, String fileName, byte[] file) throws BOSException, RemoteException;
    public String modBillInfo(Context ctx, String billType, String billId, String state, boolean isExistAttachment, AttachmentCollection attachmentInfoList) throws BOSException, RemoteException;
    public byte[] getAttachment(Context ctx, String id) throws BOSException, RemoteException;
    public String getBillState(Context ctx, String id) throws BOSException, RemoteException;
    public void createPayBill(Context ctx, String billId) throws BOSException, RemoteException;
}