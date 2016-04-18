package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ma.bg.BgCtrlParam;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class EvectionLoanBill extends LoanBill implements IEvectionLoanBill
{
    public EvectionLoanBill()
    {
        super();
        registerInterface(IEvectionLoanBill.class, this);
    }
    public EvectionLoanBill(Context ctx)
    {
        super(ctx);
        registerInterface(IEvectionLoanBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7AE53B38");
    }
    private EvectionLoanBillController getController() throws BOSException
    {
        return (EvectionLoanBillController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public EvectionLoanBillInfo getEvectionLoanBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionLoanBillInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public EvectionLoanBillInfo getEvectionLoanBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionLoanBillInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public EvectionLoanBillInfo getEvectionLoanBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionLoanBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public EvectionLoanBillCollection getEvectionLoanBillCollection() throws BOSException
    {
        try {
            return getController().getEvectionLoanBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public EvectionLoanBillCollection getEvectionLoanBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEvectionLoanBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public EvectionLoanBillCollection getEvectionLoanBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getEvectionLoanBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������ͨ��״̬-User defined method
     *@param id id
     */
    public void setPassState(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setPassState(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������ͨ��״̬-User defined method
     *@param id id
     */
    public void setNotPassState(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setNotPassState(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ��Ԥ���������Ĳ���-User defined method
     *@param id id
     *@return
     */
    public BgCtrlParam[] getBgParam(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            return getController().getBgParam(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���������ɸ��״̬-User defined method
     *@param id id
     */
    public void setPaymentState(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setPaymentState(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ùر�״̬-User defined method
     *@param id id
     */
    public void setCloseState(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setCloseState(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ƿ���ҪԤ�����-User defined method
     *@param id id
     *@return
     */
    public boolean needBgAudit(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            return getController().needBgAudit(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ԥ��-User defined method
     *@param id id
     */
    public void reBackBg(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().reBackBg(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ɸ��-User defined method
     *@param id id
     */
    public void payment(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().payment(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������״̬-User defined method
     *@param id id
     */
    public void setCheckingState(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setCheckingState(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ݴ�״̬-User defined method
     *@param id id
     */
    public void setDraftState(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setDraftState(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ؿ���ɾ����id�б�-User defined method
     *@param list list
     *@return
     */
    public List getCanDeleteIDs(List list) throws BOSException, EASBizException
    {
        try {
            return getController().getCanDeleteIDs(getContext(), list);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ƿ���޸�-User defined method
     *@param id id
     *@return
     */
    public boolean isCanModify(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            return getController().isCanModify(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����״̬-User defined method
     *@param id id
     *@param state state
     */
    public void setState(BOSUuid id, StateEnum state) throws BOSException, EASBizException
    {
        try {
            getController().setState(getContext(), id, state);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}