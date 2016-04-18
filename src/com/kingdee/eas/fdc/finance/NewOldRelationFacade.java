package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fi.cas.DisposerActionEnum;

public class NewOldRelationFacade extends AbstractBizCtrl implements INewOldRelationFacade
{
    public NewOldRelationFacade()
    {
        super();
        registerInterface(INewOldRelationFacade.class, this);
    }
    public NewOldRelationFacade(Context ctx)
    {
        super(ctx);
        registerInterface(INewOldRelationFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("775FC58C");
    }
    private NewOldRelationFacadeController getController() throws BOSException
    {
        return (NewOldRelationFacadeController)getBizController();
    }
    /**
     *��ȡ�ɵ���֯��ϵ-User defined method
     *@param treeID treeID
     */
    public void getOldOrgRelation(String treeID) throws BOSException
    {
        try {
            getController().getOldOrgRelation(getContext(), treeID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���뼯��ģ���Ŀӳ���ϵ-User defined method
     *@param company ��˾ID
     */
    public void importGroupAccountRelation(String company) throws BOSException, EASBizException
    {
        try {
            getController().importGroupAccountRelation(getContext(), company);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ͽ�����ƾ֤����-User defined method
     *@param paymentBillInfo paymentBillInfo
     *@param disposerActionEnum disposerActionEnum
     */
    public void dispose(PaymentBillInfo paymentBillInfo, DisposerActionEnum disposerActionEnum) throws BOSException, EASBizException
    {
        try {
            getController().dispose(getContext(), paymentBillInfo, disposerActionEnum);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}