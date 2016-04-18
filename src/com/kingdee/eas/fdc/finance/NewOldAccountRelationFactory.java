package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewOldAccountRelationFactory
{
    private NewOldAccountRelationFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.INewOldAccountRelation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldAccountRelation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("20034A33") ,com.kingdee.eas.fdc.finance.INewOldAccountRelation.class);
    }
    
    public static com.kingdee.eas.fdc.finance.INewOldAccountRelation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldAccountRelation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("20034A33") ,com.kingdee.eas.fdc.finance.INewOldAccountRelation.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.INewOldAccountRelation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldAccountRelation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("20034A33"));
    }
    public static com.kingdee.eas.fdc.finance.INewOldAccountRelation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldAccountRelation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("20034A33"));
    }
}