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
import java.util.Map;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.Date;
import java.util.Set;

public interface IProductDyCostSumRptFacade extends IBizCtrl
{
    public IRowSet getProject(String curOrgUnitLongNumber) throws BOSException;
    public Map getAimSellData(Set projectIDs) throws BOSException;
    public Map getDynSellData(Set projectIDs) throws BOSException;
    public Map getTotalCost(Set projectIDs, Date bizDate) throws BOSException;
}