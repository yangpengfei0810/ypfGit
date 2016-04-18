package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConOccSplitListEntryFactory
{
    private ConOccSplitListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IConOccSplitListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccSplitListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("88ABD9DE") ,com.kingdee.eas.fdc.aimcost.IConOccSplitListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IConOccSplitListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccSplitListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("88ABD9DE") ,com.kingdee.eas.fdc.aimcost.IConOccSplitListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IConOccSplitListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccSplitListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("88ABD9DE"));
    }
    public static com.kingdee.eas.fdc.aimcost.IConOccSplitListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccSplitListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("88ABD9DE"));
    }
}