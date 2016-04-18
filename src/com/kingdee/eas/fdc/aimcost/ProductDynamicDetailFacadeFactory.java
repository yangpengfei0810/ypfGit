package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProductDynamicDetailFacadeFactory
{
    private ProductDynamicDetailFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IProductDynamicDetailFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProductDynamicDetailFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5B3C1B52") ,com.kingdee.eas.fdc.aimcost.IProductDynamicDetailFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IProductDynamicDetailFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProductDynamicDetailFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5B3C1B52") ,com.kingdee.eas.fdc.aimcost.IProductDynamicDetailFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IProductDynamicDetailFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProductDynamicDetailFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5B3C1B52"));
    }
    public static com.kingdee.eas.fdc.aimcost.IProductDynamicDetailFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IProductDynamicDetailFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5B3C1B52"));
    }
}