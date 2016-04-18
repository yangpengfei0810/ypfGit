package com.kingdee.eas.fdc.aimcost;

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

public interface IJanDynamicCostSumFacade extends IBizCtrl
{
    public IRowSet getProject(String curOrgUnitLongNumber) throws BOSException, EASBizException;
    public Map getAimCost(Set projectIDs) throws BOSException, EASBizException;
    public Map getMaterialAdj(Set projectIDs) throws BOSException, EASBizException;
    public Map getMaterialAdjRate(Set projectIDs) throws BOSException;
}