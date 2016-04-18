package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ITravelAccountBillEntry extends IEvectionExpBillEntryBase
{
    public TravelAccountBillEntryInfo getTravelAccountBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TravelAccountBillEntryInfo getTravelAccountBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TravelAccountBillEntryInfo getTravelAccountBillEntryInfo(String oql) throws BOSException, EASBizException;
    public TravelAccountBillEntryCollection getTravelAccountBillEntryCollection() throws BOSException;
    public TravelAccountBillEntryCollection getTravelAccountBillEntryCollection(EntityViewInfo view) throws BOSException;
    public TravelAccountBillEntryCollection getTravelAccountBillEntryCollection(String oql) throws BOSException;
}