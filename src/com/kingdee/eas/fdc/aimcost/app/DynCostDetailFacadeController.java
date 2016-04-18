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
import java.math.BigDecimal;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.DynCostDetailDataCollection;
import com.kingdee.eas.fdc.aimcost.DynCostDetailInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DynCostDetailFacadeController extends BizController
{
    public boolean saveDynCostDetailData(Context ctx, DynCostDetailDataCollection dynCostDetailDataCollection, DynCostDetailInfo dynCostDetailInfo) throws BOSException, EASBizException, RemoteException;
    public IRowSet getDynCostDetailData(Context ctx, String curProjectID) throws BOSException, EASBizException, RemoteException;
    public IRowSet getDetailDataForID(Context ctx, String parentID) throws BOSException, RemoteException;
    public BigDecimal getMaxVer(Context ctx, String curProjectID) throws BOSException, RemoteException;
    public Map getSumLastCost(Context ctx, String curProjectID) throws BOSException, RemoteException;
    public Map getSumLastCostForSave(Context ctx, String parentID) throws BOSException, RemoteException;
    public Map getpayRequest(Context ctx, String contractid, String pcid) throws BOSException, EASBizException, RemoteException;
}