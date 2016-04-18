package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PublicToSplitFacadeFactory
{
    private PublicToSplitFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("38302C39") ,com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("38302C39") ,com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("38302C39"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicToSplitFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("38302C39"));
    }
}