package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.math.BigDecimal;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractBillFacade extends AbstractBizCtrl implements IContractBillFacade
{
    public ContractBillFacade()
    {
        super();
        registerInterface(IContractBillFacade.class, this);
    }
    public ContractBillFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("667627AE");
    }
    private ContractBillFacadeController getController() throws BOSException
    {
        return (ContractBillFacadeController)getBizController();
    }
    /**
     *更新本次分配金额-User defined method
     *@param id 单据ID
     *@param splitAmt 分配金额
     *@return
     */
    public boolean updateSplitAmt(String id, BigDecimal splitAmt) throws BOSException
    {
        try {
            return getController().updateSplitAmt(getContext(), id, splitAmt);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新框架合约的累计分配字段-User defined method
     *@param id 框架合约ID
     *@param totalSplitAmount 累计分配
     *@return
     */
    public boolean updateTotalSplitAmount(String id, BigDecimal totalSplitAmount) throws BOSException
    {
        try {
            return getController().updateTotalSplitAmount(getContext(), id, totalSplitAmount);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取框架合约的累计分配-User defined method
     *@param pcID 框架合约ID
     *@return
     */
    public BigDecimal getTotalSplitAmount(String pcID) throws BOSException
    {
        try {
            return getController().getTotalSplitAmount(getContext(), pcID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}