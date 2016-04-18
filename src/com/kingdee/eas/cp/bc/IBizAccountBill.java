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

public interface IBizAccountBill extends IExpenseAccountBill
{
    public BizAccountBillInfo getBizAccountBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BizAccountBillInfo getBizAccountBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BizAccountBillInfo getBizAccountBillInfo(String oql) throws BOSException, EASBizException;
    public BizAccountBillCollection getBizAccountBillCollection() throws BOSException;
    public BizAccountBillCollection getBizAccountBillCollection(EntityViewInfo view) throws BOSException;
    public BizAccountBillCollection getBizAccountBillCollection(String oql) throws BOSException;
}