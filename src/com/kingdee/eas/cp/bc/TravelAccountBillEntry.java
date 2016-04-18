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

public class TravelAccountBillEntry extends EvectionExpBillEntryBase implements ITravelAccountBillEntry
{
    public TravelAccountBillEntry()
    {
        super();
        registerInterface(ITravelAccountBillEntry.class, this);
    }
    public TravelAccountBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITravelAccountBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("83E90A16");
    }
    private TravelAccountBillEntryController getController() throws BOSException
    {
        return (TravelAccountBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TravelAccountBillEntryInfo getTravelAccountBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTravelAccountBillEntryInfo(getContext(), pk);
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
    public TravelAccountBillEntryInfo getTravelAccountBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTravelAccountBillEntryInfo(getContext(), pk, selector);
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
    public TravelAccountBillEntryInfo getTravelAccountBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTravelAccountBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TravelAccountBillEntryCollection getTravelAccountBillEntryCollection() throws BOSException
    {
        try {
            return getController().getTravelAccountBillEntryCollection(getContext());
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
    public TravelAccountBillEntryCollection getTravelAccountBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTravelAccountBillEntryCollection(getContext(), view);
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
    public TravelAccountBillEntryCollection getTravelAccountBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTravelAccountBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}