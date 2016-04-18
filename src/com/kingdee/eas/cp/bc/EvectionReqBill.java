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

public class EvectionReqBill extends BizCollBillBase implements IEvectionReqBill
{
    public EvectionReqBill()
    {
        super();
        registerInterface(IEvectionReqBill.class, this);
    }
    public EvectionReqBill(Context ctx)
    {
        super(ctx);
        registerInterface(IEvectionReqBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DE853384");
    }
    private EvectionReqBillController getController() throws BOSException
    {
        return (EvectionReqBillController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public EvectionReqBillInfo getEvectionReqBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionReqBillInfo(getContext(), pk);
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
    public EvectionReqBillInfo getEvectionReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionReqBillInfo(getContext(), pk, selector);
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
    public EvectionReqBillInfo getEvectionReqBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionReqBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public EvectionReqBillCollection getEvectionReqBillCollection() throws BOSException
    {
        try {
            return getController().getEvectionReqBillCollection(getContext());
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
    public EvectionReqBillCollection getEvectionReqBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEvectionReqBillCollection(getContext(), view);
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
    public EvectionReqBillCollection getEvectionReqBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getEvectionReqBillCollection(getContext(), oql);
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
}