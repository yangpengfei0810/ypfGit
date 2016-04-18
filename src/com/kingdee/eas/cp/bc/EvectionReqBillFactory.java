package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvectionReqBillFactory
{
    private EvectionReqBillFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IEvectionReqBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionReqBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DE853384") ,com.kingdee.eas.cp.bc.IEvectionReqBill.class);
    }
    
    public static com.kingdee.eas.cp.bc.IEvectionReqBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionReqBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DE853384") ,com.kingdee.eas.cp.bc.IEvectionReqBill.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IEvectionReqBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionReqBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DE853384"));
    }
    public static com.kingdee.eas.cp.bc.IEvectionReqBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionReqBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DE853384"));
    }
}