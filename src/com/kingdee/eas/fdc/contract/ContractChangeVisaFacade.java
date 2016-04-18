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
     *������Գɱ���ͨ�������������¼ID�������-User defined method
     *@param pk ����
     *@param amount ���
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
     *������Գɱ���ͨ�����������ID������Գɱ����-User defined method
     *@param pk ����
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
     *��ȡԤ������ǩ֤���,�����ֶθ��Եĺϼ�ֵ-User defined method
     *@param pk ����
     *@param rmk ��ע
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
     *����������Զ���ţ����ҽ��в��ϺŴ���-User defined method
     *@param orgId ��֯ID
     *@param prjId ��Ŀ����Id
     *@param chngId �������Id
     *@param prefix ��ѯǰ׺
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
     *ִ�и���-User defined method
     *@param sql ����sql
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
     *����ǩ֤��ͨ�����ǩ֤ȷ�ϵ�ID����ǩ֤���-User defined method
     *@param pk ���ǩ֤ȷ�ϵ�id
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
     *������ʱ�������ʱ�����Ԥ�����-User defined method
     *@param pk ����
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
     *�����ʱ��������ʱ�����ǩ֤���-User defined method
     *@param pk ����
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
     *ͨ����ʱ�����������-User defined method
     *@param prmStr ���ò���
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