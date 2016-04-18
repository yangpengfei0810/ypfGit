package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvectionReqBillEntryFactory
{
    private EvectionReqBillEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IEvectionReqBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionReqBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5902774E") ,com.kingdee.eas.cp.bc.IEvectionReqBillEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.IEvectionReqBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionReqBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5902774E") ,com.kingdee.eas.cp.bc.IEvectionReqBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IEvectionReqBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionReqBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5902774E"));
    }
    public static com.kingdee.eas.cp.bc.IEvectionReqBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IEvectionReqBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5902774E"));
    }
}