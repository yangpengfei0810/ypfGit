package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillFacadeFactory
{
    private ContractBillFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("667627AE") ,com.kingdee.eas.fdc.contract.IContractBillFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("667627AE") ,com.kingdee.eas.fdc.contract.IContractBillFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("667627AE"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("667627AE"));
    }
}