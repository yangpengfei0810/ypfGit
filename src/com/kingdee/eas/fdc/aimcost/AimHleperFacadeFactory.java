package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimHleperFacadeFactory
{
    private AimHleperFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimHleperFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimHleperFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("561F8904") ,com.kingdee.eas.fdc.aimcost.IAimHleperFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimHleperFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimHleperFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("561F8904") ,com.kingdee.eas.fdc.aimcost.IAimHleperFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimHleperFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimHleperFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("561F8904"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimHleperFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimHleperFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("561F8904"));
    }
}