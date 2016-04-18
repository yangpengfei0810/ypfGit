package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fi.cas.DisposerActionEnum;

public interface INewOldRelationFacade extends IBizCtrl
{
    public void getOldOrgRelation(String treeID) throws BOSException;
    public void importGroupAccountRelation(String company) throws BOSException, EASBizException;
    public void dispose(PaymentBillInfo paymentBillInfo, DisposerActionEnum disposerActionEnum) throws BOSException, EASBizException;
}