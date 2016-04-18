package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PublicToSplitEntryFactory
{
    private PublicToSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("85E91CF3") ,com.kingdee.eas.fdc.aimcost.IPublicToSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("85E91CF3") ,com.kingdee.eas.fdc.aimcost.IPublicToSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("85E91CF3"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("85E91CF3"));
    }
}