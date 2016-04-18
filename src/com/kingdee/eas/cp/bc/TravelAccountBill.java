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

public class TravelAccountBill extends ExpenseAccountBill implements ITravelAccountBill
{
    public TravelAccountBill()
    {
        super();
        registerInterface(ITravelAccountBill.class, this);
    }
    public TravelAccountBill(Context ctx)
    {
        super(ctx);
        registerInterface(ITravelAccountBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C57003BC");
    }
    private TravelAccountBillController getController() throws BOSException
    {
        return (TravelAccountBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TravelAccountBillInfo getTravelAccountBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTravelAccountBillInfo(getContext(), pk);
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
    public TravelAccountBillInfo getTravelAccountBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTravelAccountBillInfo(getContext(), pk, selector);
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
    public TravelAccountBillInfo getTravelAccountBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTravelAccountBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TravelAccountBillCollection getTravelAccountBillCollection() throws BOSException
    {
        try {
            return getController().getTravelAccountBillCollection(getContext());
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
    public TravelAccountBillCollection getTravelAccountBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTravelAccountBillCollection(getContext(), view);
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
    public TravelAccountBillCollection getTravelAccountBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getTravelAccountBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}