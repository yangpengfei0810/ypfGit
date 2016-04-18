package com.kingdee.eas.fdc.wslog;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WSLogFactory
{
    private WSLogFactory()
    {
    }
    public static com.kingdee.eas.fdc.wslog.IWSLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.wslog.IWSLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FCF80554") ,com.kingdee.eas.fdc.wslog.IWSLog.class);
    }
    
    public static com.kingdee.eas.fdc.wslog.IWSLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.wslog.IWSLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FCF80554") ,com.kingdee.eas.fdc.wslog.IWSLog.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.wslog.IWSLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.wslog.IWSLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FCF80554"));
    }
    public static com.kingdee.eas.fdc.wslog.IWSLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.wslog.IWSLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FCF80554"));
    }
}