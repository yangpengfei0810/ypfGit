package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.framework.ICoreBase;

public interface IPaymentVoucherTemp extends ICoreBase
{
    public PaymentVoucherTempInfo getPaymentVoucherTempInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PaymentVoucherTempInfo getPaymentVoucherTempInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PaymentVoucherTempInfo getPaymentVoucherTempInfo(String oql) throws BOSException, EASBizException;
    public PaymentVoucherTempCollection getPaymentVoucherTempCollection() throws BOSException;
    public PaymentVoucherTempCollection getPaymentVoucherTempCollection(EntityViewInfo view) throws BOSException;
    public PaymentVoucherTempCollection getPaymentVoucherTempCollection(String oql) throws BOSException;
}