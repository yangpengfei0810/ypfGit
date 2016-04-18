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
import com.kingdee.jdbc.rowset.IRowSet;
import java.math.BigDecimal;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractChangeVisaFacade extends AbstractBizCtrl implements IContractChangeVisaFacade
{
    public ContractChangeVisaFacade()
    {
        super();
        registerInterface(IContractChangeVisaFacade.class, this);
    }
    public ContractChangeVisaFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IContractChangeVisaFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("39212A78");
    }
    private ContractChangeVisaFacadeController getController() throws BOSException
    {
        return (ContractChangeVisaFacadeController)getBizController();
    }
    /**
     *保存测试成本金额，通过变更审批单分录ID保存测试-User defined method
     *@param pk 主键
     *@param amount 金额
     *@return
     */
    public String saveCostAmountByChngSupEntyID(String pk, BigDecimal amount) throws BOSException
    {
        try {
            return getController().saveCostAmountByChngSupEntyID(getContext(), pk, amount);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存测试成本金额，通过变更审批单ID保存测试成本金额-User defined method
     *@param pk 主键
     *@return
     */
    public String saveCostAmountByChngAuditBillID(String pk) throws BOSException
    {
        try {
            return getController().saveCostAmountByChngAuditBillID(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取预估金额和签证金额,两个字段各自的合计值-User defined method
     *@param pk 主键
     *@param rmk 备注
     *@return
     */
    public IRowSet getCostAmountByChngAuditBillID(String pk, String rmk) throws BOSException
    {
        try {
            return getController().getCostAmountByChngAuditBillID(getContext(), pk, rmk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *按变更类型自动编号，并且进行不断号处理-User defined method
     *@param orgId 组织ID
     *@param prjId 项目工程Id
     *@param chngId 变更类型Id
     *@param prefix 查询前缀
     *@return
     */
    public String getChngAuditBillNumber(String orgId, String prjId, String chngId, String prefix) throws BOSException
    {
        try {
            return getController().getChngAuditBillNumber(getContext(), orgId, prjId, chngId, prefix);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *执行更新-User defined method
     *@param sql 更新sql
     */
    public void executeUpdate(String sql) throws BOSException
    {
        try {
            getController().executeUpdate(getContext(), sql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存签证金额，通过变更签证确认单ID保存签证金额-User defined method
     *@param pk 变更签证确认单id
     *@return
     */
    public String saveVisaAmountByChngAuditBillID(String pk) throws BOSException
    {
        try {
            return getController().saveVisaAmountByChngAuditBillID(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批时清除审批时保存的预估金额-User defined method
     *@param pk 主键
     *@return
     */
    public String unSaveCostAmountByChngAuditBillID(String pk) throws BOSException
    {
        try {
            return getController().unSaveCostAmountByChngAuditBillID(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审核时，清除审核时保存的签证金额-User defined method
     *@param pk 主键
     *@return
     */
    public String unSaveVisaAmountByChngAuditBillID(String pk) throws BOSException
    {
        try {
            return getController().unSaveVisaAmountByChngAuditBillID(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *通过定时任务进行清理-User defined method
     *@param prmStr 备用参数
     *@return
     */
    public String clearCode(String prmStr) throws BOSException
    {
        try {
            return getController().clearCode(getContext(), prmStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}