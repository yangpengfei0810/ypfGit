package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynCostDetailFactory
{
    private DynCostDetailFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B9A821E0") ,com.kingdee.eas.fdc.aimcost.IDynCostDetail.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B9A821E0") ,com.kingdee.eas.fdc.aimcost.IDynCostDetail.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B9A821E0"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B9A821E0"));
    }
}