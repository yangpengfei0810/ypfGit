package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContactOccurDateFacadeFactory
{
    private ContactOccurDateFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F1E998CB") ,com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F1E998CB") ,com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F1E998CB"));
    }
    public static com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IContactOccurDateFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F1E998CB"));
    }
}