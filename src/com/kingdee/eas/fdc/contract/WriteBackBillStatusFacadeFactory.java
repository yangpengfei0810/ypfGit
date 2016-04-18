package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WriteBackBillStatusFacadeFactory
{
    private WriteBackBillStatusFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4F313C7E") ,com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4F313C7E") ,com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4F313C7E"));
    }
    public static com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4F313C7E"));
    }
}