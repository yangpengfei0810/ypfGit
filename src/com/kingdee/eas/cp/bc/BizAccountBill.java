package com.kingdee.eas.cp.bc;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.cp.bc.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class BizAccountBill extends ExpenseAccountBill implements IBizAccountBill
{
    public BizAccountBill()
    {
        super();
        registerInterface(IBizAccountBill.class, this);
    }
    public BizAccountBill(Context ctx)
    {
        super(ctx);
        registerInterface(IBizAccountBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4A44F49F");
    }
    private BizAccountBillController getController() throws BOSException
    {
        return (BizAccountBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public BizAccountBillInfo getBizAccountBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBizAccountBillInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BizAccountBillInfo getBizAccountBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBizAccountBillInfo(getContext(), pk);
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
    public BizAccountBillInfo getBizAccountBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBizAccountBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BizAccountBillCollection getBizAccountBillCollection() throws BOSException
    {
        try {
            return getController().getBizAccountBillCollection(getContext());
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
    public BizAccountBillCollection getBizAccountBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBizAccountBillCollection(getContext(), view);
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
    public BizAccountBillCollection getBizAccountBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getBizAccountBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}