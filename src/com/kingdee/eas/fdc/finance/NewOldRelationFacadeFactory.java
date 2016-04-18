package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewOldRelationFacadeFactory
{
    private NewOldRelationFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.INewOldRelationFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldRelationFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("775FC58C") ,com.kingdee.eas.fdc.finance.INewOldRelationFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.INewOldRelationFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldRelationFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("775FC58C") ,com.kingdee.eas.fdc.finance.INewOldRelationFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.INewOldRelationFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldRelationFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("775FC58C"));
    }
    public static com.kingdee.eas.fdc.finance.INewOldRelationFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldRelationFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("775FC58C"));
    }
}