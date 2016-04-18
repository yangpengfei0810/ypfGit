package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConOccurSplitListEntryFactory
{
    private ConOccurSplitListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IConOccurSplitListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccurSplitListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C88C50BB") ,com.kingdee.eas.fdc.aimcost.IConOccurSplitListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IConOccurSplitListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccurSplitListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C88C50BB") ,com.kingdee.eas.fdc.aimcost.IConOccurSplitListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IConOccurSplitListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccurSplitListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C88C50BB"));
    }
    public static com.kingdee.eas.fdc.aimcost.IConOccurSplitListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccurSplitListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C88C50BB"));
    }
}