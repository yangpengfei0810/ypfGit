package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractChangeVisaFacadeFactory
{
    private ContractChangeVisaFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeVisaFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeVisaFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("39212A78") ,com.kingdee.eas.fdc.contract.IContractChangeVisaFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractChangeVisaFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeVisaFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("39212A78") ,com.kingdee.eas.fdc.contract.IContractChangeVisaFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeVisaFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeVisaFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("39212A78"));
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeVisaFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeVisaFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("39212A78"));
    }
}