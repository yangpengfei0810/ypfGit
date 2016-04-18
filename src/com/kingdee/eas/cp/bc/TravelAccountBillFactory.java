package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TravelAccountBillFactory
{
    private TravelAccountBillFactory()
    {
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C57003BC") ,com.kingdee.eas.cp.bc.ITravelAccountBill.class);
    }
    
    public static com.kingdee.eas.cp.bc.ITravelAccountBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C57003BC") ,com.kingdee.eas.cp.bc.ITravelAccountBill.class, objectCtx);
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C57003BC"));
    }
    public static com.kingdee.eas.cp.bc.ITravelAccountBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.cp.bc.ITravelAccountBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C57003BC"));
    }
}