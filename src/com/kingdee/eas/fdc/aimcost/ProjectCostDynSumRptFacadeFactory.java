package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectCostDynSumRptFacadeFactory
{
    private ProjectCostDynSumRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IProjectCostDynSumRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostDynSumRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6134D46F") ,com.kingdee.eas.fdc.aimcost.IProjectCostDynSumRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IProjectCostDynSumRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostDynSumRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6134D46F") ,com.kingdee.eas.fdc.aimcost.IProjectCostDynSumRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IProjectCostDynSumRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostDynSumRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6134D46F"));
    }
    public static com.kingdee.eas.fdc.aimcost.IProjectCostDynSumRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProjectCostDynSumRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6134D46F"));
    }
}