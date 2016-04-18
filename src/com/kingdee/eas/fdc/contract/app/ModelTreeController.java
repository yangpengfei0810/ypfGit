package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.ModelTreeInfo;
import com.kingdee.eas.fdc.contract.ModelTreeCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ModelTreeController extends TreeBaseController
{
    public ModelTreeInfo getModelTreeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ModelTreeInfo getModelTreeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ModelTreeInfo getModelTreeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ModelTreeCollection getModelTreeCollection(Context ctx) throws BOSException, RemoteException;
    public ModelTreeCollection getModelTreeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ModelTreeCollection getModelTreeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}