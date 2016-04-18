package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;

public class AimHleperFacade extends AbstractBizCtrl implements IAimHleperFacade
{
    public AimHleperFacade()
    {
        super();
        registerInterface(IAimHleperFacade.class, this);
    }
    public AimHleperFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IAimHleperFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("561F8904");
    }
    private AimHleperFacadeController getController() throws BOSException
    {
        return (AimHleperFacadeController)getBizController();
    }
    /**
     *获取面积指标管理中的动态指标-User defined method
     *@param projectID 项目ID
     *@return
     */
    public Map getApportionIndex(String projectID) throws BOSException
    {
        try {
            return getController().getApportionIndex(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取报销单-User defined method
     *@param id id
     *@return
     */
    public Object getParam(String id) throws BOSException, EASBizException
    {
        try {
            return getController().getParam(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}