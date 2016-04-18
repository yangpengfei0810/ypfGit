package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PublicSetSplitEntryFactory
{
    private PublicSetSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("11B044DA") ,com.kingdee.eas.fdc.aimcost.IPublicSetSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("11B044DA") ,com.kingdee.eas.fdc.aimcost.IPublicSetSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("11B044DA"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("11B044DA"));
    }
}