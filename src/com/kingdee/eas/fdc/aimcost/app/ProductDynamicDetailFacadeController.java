package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProductDynamicDetailFacadeController extends BizController
{
    public ProductTypeCollection getProductTypes(Context ctx, Set projectIDs) throws BOSException, RemoteException;
    public Map getDetailData(Context ctx, Set projectIDs) throws BOSException, RemoteException;
    public Map getSplitAmount(Context ctx, String projectID) throws BOSException, RemoteException;
}