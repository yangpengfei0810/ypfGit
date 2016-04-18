package com.kingdee.eas.fdc.aimcost;

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

public interface IPublicToSplitFacade extends IBizCtrl
{
    public void initDBTable(String curProjectID) throws BOSException;
    public IRowSet getRowData(String curProjectID) throws BOSException;
    public IRowSet getPublicToSplitColums(String curProjectID) throws BOSException;
    public IRowSet getPublicRow(String curProjectID) throws BOSException;
}