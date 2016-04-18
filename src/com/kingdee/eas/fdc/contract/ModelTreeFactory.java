package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ModelTreeFactory
{
    private ModelTreeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IModelTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IModelTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4C7AF68C") ,com.kingdee.eas.fdc.contract.IModelTree.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IModelTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IModelTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4C7AF68C") ,com.kingdee.eas.fdc.contract.IModelTree.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IModelTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IModelTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4C7AF68C"));
    }
    public static com.kingdee.eas.fdc.contract.IModelTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IModelTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4C7AF68C"));
    }
}