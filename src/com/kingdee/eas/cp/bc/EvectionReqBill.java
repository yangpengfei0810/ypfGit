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
     *取值-System defined method
     *@param pk 取值
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
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
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
     *取值-System defined method
     *@param oql 取值
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
     *取集合-System defined method
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
     *取集合-System defined method
     *@param view 取集合
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
     *取集合-System defined method
     *@param oql 取集合
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
     *设置审批通过状态-User defined method
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
     *设置审批不通过状态-User defined method
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
     *取得预算控制所需的参数-User defined method
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
     *设置关闭状态-User defined method
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
     *是否需要预算控制-User defined method
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
     *返还预算-User defined method
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
     *设置审批中状态-User defined method
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
     *设置暂存状态-User defined method
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
     *返回可以删除的id列表-User defined method
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
     *是否可修改-User defined method
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