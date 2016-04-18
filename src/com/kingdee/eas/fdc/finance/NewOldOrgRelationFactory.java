package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewOldOrgRelationFactory
{
    private NewOldOrgRelationFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.INewOldOrgRelation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldOrgRelation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8680CCEA") ,com.kingdee.eas.fdc.finance.INewOldOrgRelation.class);
    }
    
    public static com.kingdee.eas.fdc.finance.INewOldOrgRelation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldOrgRelation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8680CCEA") ,com.kingdee.eas.fdc.finance.INewOldOrgRelation.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.INewOldOrgRelation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldOrgRelation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8680CCEA"));
    }
    public static com.kingdee.eas.fdc.finance.INewOldOrgRelation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.INewOldOrgRelation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8680CCEA"));
    }
}