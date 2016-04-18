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
import java.util.Date;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProductDyCostSumRptFacadeController extends BizController
{
    public IRowSet getProject(Context ctx, String curOrgUnitLongNumber) throws BOSException, RemoteException;
    public Map getAimSellData(Context ctx, Set projectIDs) throws BOSException, RemoteException;
    public Map getDynSellData(Context ctx, Set projectIDs) throws BOSException, RemoteException;
    public Map getTotalCost(Context ctx, Set projectIDs, Date bizDate) throws BOSException, RemoteException;
}