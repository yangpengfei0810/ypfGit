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

public interface ITravelAccountBill extends IExpenseAccountBill
{
    public TravelAccountBillInfo getTravelAccountBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TravelAccountBillInfo getTravelAccountBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TravelAccountBillInfo getTravelAccountBillInfo(String oql) throws BOSException, EASBizException;
    public TravelAccountBillCollection getTravelAccountBillCollection() throws BOSException;
    public TravelAccountBillCollection getTravelAccountBillCollection(EntityViewInfo view) throws BOSException;
    public TravelAccountBillCollection getTravelAccountBillCollection(String oql) throws BOSException;
}