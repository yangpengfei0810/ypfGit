package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TargetCostSplitFactory
{
    private TargetCostSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ITargetCostSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITargetCostSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("49033285") ,com.kingdee.eas.fdc.aimcost.ITargetCostSplit.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ITargetCostSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITargetCostSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("49033285") ,com.kingdee.eas.fdc.aimcost.ITargetCostSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ITargetCostSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITargetCostSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("49033285"));
    }
    public static com.kingdee.eas.fdc.aimcost.ITargetCostSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITargetCostSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("49033285"));
    }
}