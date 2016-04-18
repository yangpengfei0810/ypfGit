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
     *ȡֵ-System defined method
     *@param pk ȡֵ
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
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
     *ȡֵ-System defined method
     *@param oql ȡֵ
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
     *ȡ����-System defined method
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
     *ȡ����-System defined method
     *@param view ȡ����
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
     *ȡ����-System defined method
     *@param oql ȡ����
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