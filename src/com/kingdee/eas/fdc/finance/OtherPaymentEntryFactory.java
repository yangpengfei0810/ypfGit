package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherPaymentEntryFactory
{
    private OtherPaymentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IOtherPaymentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOtherPaymentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6EB5500D") ,com.kingdee.eas.fdc.finance.IOtherPaymentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IOtherPaymentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOtherPaymentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6EB5500D") ,com.kingdee.eas.fdc.finance.IOtherPaymentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IOtherPaymentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOtherPaymentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6EB5500D"));
    }
    public static com.kingdee.eas.fdc.finance.IOtherPaymentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IOtherPaymentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6EB5500D"));
    }
}