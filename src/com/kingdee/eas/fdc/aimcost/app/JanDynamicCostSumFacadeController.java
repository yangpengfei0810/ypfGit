package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface JanDynamicCostSumFacadeController extends BizController
{
    public IRowSet getProject(Context ctx, String curOrgUnitLongNumber) throws BOSException, EASBizException, RemoteException;
    public Map getAimCost(Context ctx, Set projectIDs) throws BOSException, EASBizException, RemoteException;
    public Map getMaterialAdj(Context ctx, Set projectIDs) throws BOSException, EASBizException, RemoteException;
    public Map getMaterialAdjRate(Context ctx, Set projectIDs) throws BOSException, RemoteException;
}