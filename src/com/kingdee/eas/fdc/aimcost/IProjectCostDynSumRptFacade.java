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
import java.util.Set;

public interface IProjectCostDynSumRptFacade extends IBizCtrl
{
    public IRowSet getProject(String curOrgUnitLongNumber) throws BOSException;
    public Map getAimCost(Set projectIDs) throws BOSException;
    public Map getNewDynCost(Set projectIDs) throws BOSException;
    public Map getMarketingAimCost(Set projectIDs) throws BOSException;
    public Map getMarketingNewDynCost(Set projectIDs) throws BOSException;
}