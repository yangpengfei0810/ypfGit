package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.cp.bc.BizAccountBillCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BizAccountBillController extends ExpenseAccountBillController
{
    public BizAccountBillInfo getBizAccountBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BizAccountBillInfo getBizAccountBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BizAccountBillInfo getBizAccountBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BizAccountBillCollection getBizAccountBillCollection(Context ctx) throws BOSException, RemoteException;
    public BizAccountBillCollection getBizAccountBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BizAccountBillCollection getBizAccountBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
}