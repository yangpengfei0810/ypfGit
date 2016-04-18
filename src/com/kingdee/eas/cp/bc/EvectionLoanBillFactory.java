package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvectionLoanBillFactory
{
    private EvectionLoanBillFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IEvectionLoanBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionLoanBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7AE53B38") ,com.kingdee.eas.cp.bc.IEvectionLoanBill.class);
    }
    
    public static com.kingdee.eas.cp.bc.IEvectionLoanBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionLoanBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7AE53B38") ,com.kingdee.eas.cp.bc.IEvectionLoanBill.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IEvectionLoanBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionLoanBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7AE53B38"));
    }
    public static com.kingdee.eas.cp.bc.IEvectionLoanBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionLoanBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7AE53B38"));
    }
}