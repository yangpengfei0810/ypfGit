package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProductDyCostSumRptFacadeFactory
{
    private ProductDyCostSumRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IProductDyCostSumRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProductDyCostSumRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1A7DED1F") ,com.kingdee.eas.fdc.aimcost.IProductDyCostSumRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IProductDyCostSumRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProductDyCostSumRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1A7DED1F") ,com.kingdee.eas.fdc.aimcost.IProductDyCostSumRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IProductDyCostSumRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProductDyCostSumRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1A7DED1F"));
    }
    public static com.kingdee.eas.fdc.aimcost.IProductDyCostSumRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProductDyCostSumRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1A7DED1F"));
    }
}