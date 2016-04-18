package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class PaymentVoucherTemp extends CoreBase implements IPaymentVoucherTemp
{
    public PaymentVoucherTemp()
    {
        super();
        registerInterface(IPaymentVoucherTemp.class, this);
    }
    public PaymentVoucherTemp(Context ctx)
    {
        super(ctx);
        registerInterface(IPaymentVoucherTemp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1D9F550B");
    }
    private PaymentVoucherTempController getController() throws BOSException
    {
        return (PaymentVoucherTempController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public PaymentVoucherTempInfo getPaymentVoucherTempInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentVoucherTempInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PaymentVoucherTempInfo getPaymentVoucherTempInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentVoucherTempInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public PaymentVoucherTempInfo getPaymentVoucherTempInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentVoucherTempInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PaymentVoucherTempCollection getPaymentVoucherTempCollection() throws BOSException
    {
        try {
            return getController().getPaymentVoucherTempCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public PaymentVoucherTempCollection getPaymentVoucherTempCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPaymentVoucherTempCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public PaymentVoucherTempCollection getPaymentVoucherTempCollection(String oql) throws BOSException
    {
        try {
            return getController().getPaymentVoucherTempCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}