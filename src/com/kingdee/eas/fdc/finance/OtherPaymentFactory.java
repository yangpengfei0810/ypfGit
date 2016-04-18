package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherPaymentFactory
{
    private OtherPaymentFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IOtherPayment getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOtherPayment)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B58F9F65") ,com.kingdee.eas.fdc.finance.IOtherPayment.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IOtherPayment getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOtherPayment)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B58F9F65") ,com.kingdee.eas.fdc.finance.IOtherPayment.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IOtherPayment getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOtherPayment)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B58F9F65"));
    }
    public static com.kingdee.eas.fdc.finance.IOtherPayment getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOtherPayment)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B58F9F65"));
    }
}