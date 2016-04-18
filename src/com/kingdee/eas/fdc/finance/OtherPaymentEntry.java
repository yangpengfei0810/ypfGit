package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class OtherPaymentEntry extends CoreBillEntryBase implements IOtherPaymentEntry
{
    public OtherPaymentEntry()
    {
        super();
        registerInterface(IOtherPaymentEntry.class, this);
    }
    public OtherPaymentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOtherPaymentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6EB5500D");
    }
    private OtherPaymentEntryController getController() throws BOSException
    {
        return (OtherPaymentEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public OtherPaymentEntryInfo getOtherPaymentEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherPaymentEntryInfo(getContext(), pk);
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
    public OtherPaymentEntryInfo getOtherPaymentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherPaymentEntryInfo(getContext(), pk, selector);
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
    public OtherPaymentEntryInfo getOtherPaymentEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherPaymentEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OtherPaymentEntryCollection getOtherPaymentEntryCollection() throws BOSException
    {
        try {
            return getController().getOtherPaymentEntryCollection(getContext());
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
    public OtherPaymentEntryCollection getOtherPaymentEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOtherPaymentEntryCollection(getContext(), view);
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
    public OtherPaymentEntryCollection getOtherPaymentEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOtherPaymentEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}