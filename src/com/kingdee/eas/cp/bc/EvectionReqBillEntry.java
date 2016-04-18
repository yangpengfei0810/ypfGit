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

public class EvectionReqBillEntry extends EvectionExpBillEntryBase implements IEvectionReqBillEntry
{
    public EvectionReqBillEntry()
    {
        super();
        registerInterface(IEvectionReqBillEntry.class, this);
    }
    public EvectionReqBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IEvectionReqBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5902774E");
    }
    private EvectionReqBillEntryController getController() throws BOSException
    {
        return (EvectionReqBillEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public EvectionReqBillEntryInfo getEvectionReqBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionReqBillEntryInfo(getContext(), pk);
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
    public EvectionReqBillEntryInfo getEvectionReqBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionReqBillEntryInfo(getContext(), pk, selector);
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
    public EvectionReqBillEntryInfo getEvectionReqBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEvectionReqBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public EvectionReqBillEntryCollection getEvectionReqBillEntryCollection() throws BOSException
    {
        try {
            return getController().getEvectionReqBillEntryCollection(getContext());
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
    public EvectionReqBillEntryCollection getEvectionReqBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEvectionReqBillEntryCollection(getContext(), view);
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
    public EvectionReqBillEntryCollection getEvectionReqBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getEvectionReqBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}