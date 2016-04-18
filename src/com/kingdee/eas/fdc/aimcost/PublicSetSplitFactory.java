package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PublicSetSplitFactory
{
    private PublicSetSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5B781E78") ,com.kingdee.eas.fdc.aimcost.IPublicSetSplit.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5B781E78") ,com.kingdee.eas.fdc.aimcost.IPublicSetSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5B781E78"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5B781E78"));
    }
}