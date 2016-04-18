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
import java.util.Map;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import java.util.Set;

public class JanDynamicCostSumFacade extends AbstractBizCtrl implements IJanDynamicCostSumFacade
{
    public JanDynamicCostSumFacade()
    {
        super();
        registerInterface(IJanDynamicCostSumFacade.class, this);
    }
    public JanDynamicCostSumFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IJanDynamicCostSumFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DDC48CF9");
    }
    private JanDynamicCostSumFacadeController getController() throws BOSException
    {
        return (JanDynamicCostSumFacadeController)getBizController();
    }
    /**
     *ȡ��ǰ��֯�µ����й�����Ŀ-User defined method
     *@param curOrgUnitLongNumber ��ǰ��֯�ĳ�����
     *@return
     */
    public IRowSet getProject(String curOrgUnitLongNumber) throws BOSException, EASBizException
    {
        try {
            return getController().getProject(getContext(), curOrgUnitLongNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡĿ��ɱ�-User defined method
     *@param projectIDs ��ǰ��֯�����й�����ĿID
     *@return
     */
    public Map getAimCost(Set projectIDs) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCost(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ���ϵ���-User defined method
     *@param projectIDs ��ǰ��֯�����й�����ĿID
     *@return
     */
    public Map getMaterialAdj(Set projectIDs) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialAdj(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�����ϵ����ƫ����-User defined method
     *@param projectIDs ��ǰ��֯�����й�����ĿID
     *@return
     */
    public Map getMaterialAdjRate(Set projectIDs) throws BOSException
    {
        try {
            return getController().getMaterialAdjRate(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}