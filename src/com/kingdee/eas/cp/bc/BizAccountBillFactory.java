package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BizAccountBillFactory
{
    private BizAccountBillFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4A44F49F") ,com.kingdee.eas.cp.bc.IBizAccountBill.class);
    }
    
    public static com.kingdee.eas.cp.bc.IBizAccountBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4A44F49F") ,com.kingdee.eas.cp.bc.IBizAccountBill.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4A44F49F"));
    }
    public static com.kingdee.eas.cp.bc.IBizAccountBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.IBizAccountBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4A44F49F"));
    }
}