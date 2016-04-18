package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fi.cas.DisposerActionEnum;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NewOldRelationFacadeController extends BizController
{
    public void getOldOrgRelation(Context ctx, String treeID) throws BOSException, RemoteException;
    public void importGroupAccountRelation(Context ctx, String company) throws BOSException, EASBizException, RemoteException;
    public void dispose(Context ctx, PaymentBillInfo paymentBillInfo, DisposerActionEnum disposerActionEnum) throws BOSException, EASBizException, RemoteException;
}