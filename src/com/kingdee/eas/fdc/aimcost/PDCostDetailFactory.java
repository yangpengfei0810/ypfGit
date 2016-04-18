package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PDCostDetailFactory
{
    private PDCostDetailFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPDCostDetail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPDCostDetail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E1959649") ,com.kingdee.eas.fdc.aimcost.IPDCostDetail.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPDCostDetail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPDCostDetail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E1959649") ,com.kingdee.eas.fdc.aimcost.IPDCostDetail.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPDCostDetail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPDCostDetail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E1959649"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPDCostDetail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPDCostDetail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E1959649"));
    }
}