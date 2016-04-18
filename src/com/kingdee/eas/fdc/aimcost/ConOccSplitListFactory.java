package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConOccSplitListFactory
{
    private ConOccSplitListFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IConOccSplitList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccSplitList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FF0C76F4") ,com.kingdee.eas.fdc.aimcost.IConOccSplitList.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IConOccSplitList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccSplitList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FF0C76F4") ,com.kingdee.eas.fdc.aimcost.IConOccSplitList.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IConOccSplitList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccSplitList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FF0C76F4"));
    }
    public static com.kingdee.eas.fdc.aimcost.IConOccSplitList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConOccSplitList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FF0C76F4"));
    }
}