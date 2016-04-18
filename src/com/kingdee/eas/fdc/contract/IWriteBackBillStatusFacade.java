package com.kingdee.eas.fdc.contract;

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

public interface IWriteBackBillStatusFacade extends IBizCtrl
{
    public boolean alterAttachmentByID(String fid, String fileName, byte[] file) throws BOSException;
    public String modBillInfo(String billType, String billId, String state, boolean isExistAttachment, AttachmentCollection attachmentInfoList) throws BOSException;
    public byte[] getAttachment(String id) throws BOSException;
    public String getBillState(String id) throws BOSException;
    public void createPayBill(String billId) throws BOSException;
}