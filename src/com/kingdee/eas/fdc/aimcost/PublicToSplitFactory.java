package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PublicToSplitFactory
{
    private PublicToSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A1FCDABF") ,com.kingdee.eas.fdc.aimcost.IPublicToSplit.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A1FCDABF") ,com.kingdee.eas.fdc.aimcost.IPublicToSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A1FCDABF"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A1FCDABF"));
    }
}