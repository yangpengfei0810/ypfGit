package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DailyLoanBillFactory
{
    private DailyLoanBillFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IDailyLoanBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IDailyLoanBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8110AAB2") ,com.kingdee.eas.cp.bc.IDailyLoanBill.class);
    }
    
    public static com.kingdee.eas.cp.bc.IDailyLoanBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IDailyLoanBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8110AAB2") ,com.kingdee.eas.cp.bc.IDailyLoanBill.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IDailyLoanBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IDailyLoanBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8110AAB2"));
    }
    public static com.kingdee.eas.cp.bc.IDailyLoanBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IDailyLoanBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8110AAB2"));
    }
}