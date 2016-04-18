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
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PublicToSplitFacadeController extends BizController
{
    public void initDBTable(Context ctx, String curProjectID) throws BOSException, RemoteException;
    public IRowSet getRowData(Context ctx, String curProjectID) throws BOSException, RemoteException;
    public IRowSet getPublicToSplitColums(Context ctx, String curProjectID) throws BOSException, RemoteException;
    public IRowSet getPublicRow(Context ctx, String curProjectID) throws BOSException, RemoteException;
}