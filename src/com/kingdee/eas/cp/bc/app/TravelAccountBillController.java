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
import com.kingdee.eas.cp.bc.TravelAccountBillCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.cp.bc.TravelAccountBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TravelAccountBillController extends ExpenseAccountBillController
{
    public TravelAccountBillInfo getTravelAccountBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TravelAccountBillInfo getTravelAccountBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TravelAccountBillInfo getTravelAccountBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TravelAccountBillCollection getTravelAccountBillCollection(Context ctx) throws BOSException, RemoteException;
    public TravelAccountBillCollection getTravelAccountBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TravelAccountBillCollection getTravelAccountBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
}