package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PDCostDetailEntryFactory
{
    private PDCostDetailEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPDCostDetailEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPDCostDetailEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4B0A07A9") ,com.kingdee.eas.fdc.aimcost.IPDCostDetailEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPDCostDetailEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPDCostDetailEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4B0A07A9") ,com.kingdee.eas.fdc.aimcost.IPDCostDetailEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPDCostDetailEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPDCostDetailEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4B0A07A9"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPDCostDetailEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPDCostDetailEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4B0A07A9"));
    }
}