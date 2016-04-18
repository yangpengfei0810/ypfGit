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

public abstract class LoanBill extends BizCollBillBase implements ILoanBill
{
    public LoanBill()
    {
        super();
        registerInterface(ILoanBill.class, this);
    }
    public LoanBill(Context ctx)
    {
        super(ctx);
        registerInterface(ILoanBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A8808375");
    }
    private LoanBillController getController() throws BOSException
    {
        return (LoanBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public LoanBillInfo getLoanBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getLoanBillInfo(getContext(), pk);
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
    public LoanBillInfo getLoanBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getLoanBillInfo(getContext(), pk, selector);
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
    public LoanBillInfo getLoanBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getLoanBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public LoanBillCollection getLoanBillCollection() throws BOSException
    {
        try {
            return getController().getLoanBillCollection(getContext());
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
    public LoanBillCollection getLoanBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getLoanBillCollection(getContext(), view);
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
    public LoanBillCollection getLoanBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getLoanBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}