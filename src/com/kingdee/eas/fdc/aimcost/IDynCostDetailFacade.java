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
import java.math.BigDecimal;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;

public interface IDynCostDetailFacade extends IBizCtrl
{
    public boolean saveDynCostDetailData(DynCostDetailDataCollection dynCostDetailDataCollection, DynCostDetailInfo dynCostDetailInfo) throws BOSException, EASBizException;
    public IRowSet getDynCostDetailData(String curProjectID) throws BOSException, EASBizException;
    public IRowSet getDetailDataForID(String parentID) throws BOSException;
    public BigDecimal getMaxVer(String curProjectID) throws BOSException;
    public Map getSumLastCost(String curProjectID) throws BOSException;
    public Map getSumLastCostForSave(String parentID) throws BOSException;
    public Map getpayRequest(String contractid, String pcid) throws BOSException, EASBizException;
}