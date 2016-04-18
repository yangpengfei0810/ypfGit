package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleAreaDataFactory
{
    private SaleAreaDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ISaleAreaData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ISaleAreaData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AFE41215") ,com.kingdee.eas.fdc.aimcost.ISaleAreaData.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ISaleAreaData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ISaleAreaData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AFE41215") ,com.kingdee.eas.fdc.aimcost.ISaleAreaData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ISaleAreaData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ISaleAreaData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AFE41215"));
    }
    public static com.kingdee.eas.fdc.aimcost.ISaleAreaData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ISaleAreaData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AFE41215"));
    }
}