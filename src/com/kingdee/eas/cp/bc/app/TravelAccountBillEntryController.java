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
import com.kingdee.bos.util.*;
import com.kingdee.eas.cp.bc.TravelAccountBillEntryInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TravelAccountBillEntryController extends EvectionExpBillEntryBaseController
{
    public TravelAccountBillEntryInfo getTravelAccountBillEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TravelAccountBillEntryInfo getTravelAccountBillEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TravelAccountBillEntryInfo getTravelAccountBillEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TravelAccountBillEntryCollection getTravelAccountBillEntryCollection(Context ctx) throws BOSException, RemoteException;
    public TravelAccountBillEntryCollection getTravelAccountBillEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TravelAccountBillEntryCollection getTravelAccountBillEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}