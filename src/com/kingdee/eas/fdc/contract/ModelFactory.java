package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ModelFactory
{
    private ModelFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IModel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IModel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("86D6A5CE") ,com.kingdee.eas.fdc.contract.IModel.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IModel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IModel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("86D6A5CE") ,com.kingdee.eas.fdc.contract.IModel.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IModel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IModel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("86D6A5CE"));
    }
    public static com.kingdee.eas.fdc.contract.IModel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IModel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("86D6A5CE"));
    }
}