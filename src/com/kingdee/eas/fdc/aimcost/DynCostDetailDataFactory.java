package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynCostDetailDataFactory
{
    private DynCostDetailDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetailData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetailData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("832495AA") ,com.kingdee.eas.fdc.aimcost.IDynCostDetailData.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetailData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetailData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("832495AA") ,com.kingdee.eas.fdc.aimcost.IDynCostDetailData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetailData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetailData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("832495AA"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostDetailData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostDetailData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("832495AA"));
    }
}