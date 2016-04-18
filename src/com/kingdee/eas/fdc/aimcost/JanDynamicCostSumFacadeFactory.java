package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JanDynamicCostSumFacadeFactory
{
    private JanDynamicCostSumFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IJanDynamicCostSumFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IJanDynamicCostSumFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DDC48CF9") ,com.kingdee.eas.fdc.aimcost.IJanDynamicCostSumFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IJanDynamicCostSumFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IJanDynamicCostSumFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DDC48CF9") ,com.kingdee.eas.fdc.aimcost.IJanDynamicCostSumFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IJanDynamicCostSumFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IJanDynamicCostSumFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DDC48CF9"));
    }
    public static com.kingdee.eas.fdc.aimcost.IJanDynamicCostSumFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IJanDynamicCostSumFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DDC48CF9"));
    }
}