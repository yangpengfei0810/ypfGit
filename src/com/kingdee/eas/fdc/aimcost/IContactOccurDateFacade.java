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
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IContactOccurDateFacade extends IBizCtrl
{
    public List getDate(String projectID) throws BOSException, EASBizException;
    public List getProductType(String projectID) throws BOSException, EASBizException;
    public Map getAimAndHappenedData(String projectID) throws BOSException, EASBizException;
    public List getContractData(String projectID) throws BOSException, EASBizException;
    public List getDymData(String projectID) throws BOSException, EASBizException;
    public Map getAimCost(String projectID) throws BOSException;
    public Map getHappenedCost(String projectID) throws BOSException;
}