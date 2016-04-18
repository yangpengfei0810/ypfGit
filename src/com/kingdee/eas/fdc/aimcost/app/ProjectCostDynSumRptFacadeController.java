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
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectCostDynSumRptFacadeController extends BizController
{
    public IRowSet getProject(Context ctx, String curOrgUnitLongNumber) throws BOSException, RemoteException;
    public Map getAimCost(Context ctx, Set projectIDs) throws BOSException, RemoteException;
    public Map getNewDynCost(Context ctx, Set projectIDs) throws BOSException, RemoteException;
    public Map getMarketingAimCost(Context ctx, Set projectIDs) throws BOSException, RemoteException;
    public Map getMarketingNewDynCost(Context ctx, Set projectIDs) throws BOSException, RemoteException;
}