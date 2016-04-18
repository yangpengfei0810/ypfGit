package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynCostDetailFacadeFactory
{
    private DynCostDetailFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4FB7D69A") ,com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4FB7D69A") ,com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4FB7D69A"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetailFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4FB7D69A"));
    }
}