package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public class DynCostDetailData extends CoreBase implements IDynCostDetailData
{
    public DynCostDetailData()
    {
        super();
        registerInterface(IDynCostDetailData.class, this);
    }
    public DynCostDetailData(Context ctx)
    {
        super(ctx);
        registerInterface(IDynCostDetailData.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("832495AA");
    }
    private DynCostDetailDataController getController() throws BOSException
    {
        return (DynCostDetailDataController)getBizController();
    }
}