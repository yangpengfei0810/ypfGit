package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasureCollectDataFactory
{
    private MeasureCollectDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCollectData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCollectData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B571FD4D") ,com.kingdee.eas.fdc.aimcost.IMeasureCollectData.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IMeasureCollectData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCollectData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B571FD4D") ,com.kingdee.eas.fdc.aimcost.IMeasureCollectData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCollectData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCollectData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B571FD4D"));
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureCollectData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureCollectData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B571FD4D"));
    }
}