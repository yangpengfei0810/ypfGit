package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TravelAccountBillEntryFactory
{
    private TravelAccountBillEntryFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("83E90A16") ,com.kingdee.eas.cp.bc.ITravelAccountBillEntry.class);
    }
    
    public static com.kingdee.eas.cp.bc.ITravelAccountBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("83E90A16") ,com.kingdee.eas.cp.bc.ITravelAccountBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("83E90A16"));
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("83E90A16"));
    }
}