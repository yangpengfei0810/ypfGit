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
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContactOccurDateFacadeController extends BizController
{
    public List getDate(Context ctx, String projectID) throws BOSException, EASBizException, RemoteException;
    public List getProductType(Context ctx, String projectID) throws BOSException, EASBizException, RemoteException;
    public Map getAimAndHappenedData(Context ctx, String projectID) throws BOSException, EASBizException, RemoteException;
    public List getContractData(Context ctx, String projectID) throws BOSException, EASBizException, RemoteException;
    public List getDymData(Context ctx, String projectID) throws BOSException, EASBizException, RemoteException;
    public Map getAimCost(Context ctx, String projectID) throws BOSException, RemoteException;
    public Map getHappenedCost(Context ctx, String projectID) throws BOSException, RemoteException;
}