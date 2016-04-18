package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentVoucherTempFactory
{
    private PaymentVoucherTempFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPaymentVoucherTemp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentVoucherTemp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D9F550B") ,com.kingdee.eas.fdc.finance.IPaymentVoucherTemp.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPaymentVoucherTemp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentVoucherTemp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D9F550B") ,com.kingdee.eas.fdc.finance.IPaymentVoucherTemp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPaymentVoucherTemp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentVoucherTemp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D9F550B"));
    }
    public static com.kingdee.eas.fdc.finance.IPaymentVoucherTemp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPaymentVoucherTemp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D9F550B"));
    }
}