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
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import java.util.List;

public class ContactOccurDateFacade extends AbstractBizCtrl implements IContactOccurDateFacade
{
    public ContactOccurDateFacade()
    {
        super();
        registerInterface(IContactOccurDateFacade.class, this);
    }
    public ContactOccurDateFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IContactOccurDateFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F1E998CB");
    }
    private ContactOccurDateFacadeController getController() throws BOSException
    {
        return (ContactOccurDateFacadeController)getBizController();
    }
    /**
     *�������-User defined method
     *@param projectID ��ĿID
     *@return
     */
    public List getDate(String projectID) throws BOSException, EASBizException
    {
        try {
            return getController().getDate(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������-User defined method
     *@param projectID ��ĿID
     *@return
     */
    public List getProductType(String projectID) throws BOSException, EASBizException
    {
        try {
            return getController().getProductType(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ��Ŀ��Ӧ��Ʒ���͵�����-User defined method
     *@param projectID ����ID
     *@return
     */
    public Map getAimAndHappenedData(String projectID) throws BOSException, EASBizException
    {
        try {
            return getController().getAimAndHappenedData(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ��ܺ�Լ����-User defined method
     *@param projectID ��ĿID
     *@return
     */
    public List getContractData(String projectID) throws BOSException, EASBizException
    {
        try {
            return getController().getContractData(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ö�̬��ϸ������-User defined method
     *@param projectID ��ĿID
     *@return
     */
    public List getDymData(String projectID) throws BOSException, EASBizException
    {
        try {
            return getController().getDymData(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡĿ��ɱ�-User defined method
     *@param projectID ������ĿID
     *@return
     */
    public Map getAimCost(String projectID) throws BOSException
    {
        try {
            return getController().getAimCost(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�ѷ����ɱ�-User defined method
     *@param projectID ������ĿID
     *@return
     */
    public Map getHappenedCost(String projectID) throws BOSException
    {
        try {
            return getController().getHappenedCost(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}