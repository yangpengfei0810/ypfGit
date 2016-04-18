package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import java.util.Set;

public class ProjectCostDynSumRptFacade extends AbstractBizCtrl implements IProjectCostDynSumRptFacade
{
    public ProjectCostDynSumRptFacade()
    {
        super();
        registerInterface(IProjectCostDynSumRptFacade.class, this);
    }
    public ProjectCostDynSumRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectCostDynSumRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6134D46F");
    }
    private ProjectCostDynSumRptFacadeController getController() throws BOSException
    {
        return (ProjectCostDynSumRptFacadeController)getBizController();
    }
    /**
     *ȡ��ǰ��֯�µ���Ŀ-User defined method
     *@param curOrgUnitLongNumber ��ǰ��֯�ĳ�����
     *@return
     */
    public IRowSet getProject(String curOrgUnitLongNumber) throws BOSException
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
    public Map getAimCost(Set projectIDs) throws BOSException
    {
        try {
            return getController().getAimCost(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ���¶�̬�ɱ�-User defined method
     *@param projectIDs ��ǰ��֯�����й�����ĿID
     *@return
     */
    public Map getNewDynCost(Set projectIDs) throws BOSException
    {
        try {
            return getController().getNewDynCost(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡӪ������Ŀ��ɱ�-User defined method
     *@param projectIDs ��ǰ��֯�����й�����ĿID
     *@return
     */
    public Map getMarketingAimCost(Set projectIDs) throws BOSException
    {
        try {
            return getController().getMarketingAimCost(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡӪ���������¶�̬�ɱ�-User defined method
     *@param projectIDs ��ǰ��֯�����й�����ĿID
     *@return
     */
    public Map getMarketingNewDynCost(Set projectIDs) throws BOSException
    {
        try {
            return getController().getMarketingNewDynCost(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}