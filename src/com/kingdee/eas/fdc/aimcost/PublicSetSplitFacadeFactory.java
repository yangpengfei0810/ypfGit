package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PublicSetSplitFacadeFactory
{
    private PublicSetSplitFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("254E0132") ,com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("254E0132") ,com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("254E0132"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPublicSetSplitFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("254E0132"));
    }
}