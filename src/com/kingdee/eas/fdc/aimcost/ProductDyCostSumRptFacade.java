package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import java.util.Date;
import java.util.Set;

public class ProductDyCostSumRptFacade extends AbstractBizCtrl implements IProductDyCostSumRptFacade
{
    public ProductDyCostSumRptFacade()
    {
        super();
        registerInterface(IProductDyCostSumRptFacade.class, this);
    }
    public ProductDyCostSumRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProductDyCostSumRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1A7DED1F");
    }
    private ProductDyCostSumRptFacadeController getController() throws BOSException
    {
        return (ProductDyCostSumRptFacadeController)getBizController();
    }
    /**
     *��ȡ��ǰ��֯�µĹ�����Ŀ-User defined method
     *@param curOrgUnitLongNumber ��ǰ��֯�ĳ�����
     *@return
     */
    public IRowSet getProject(String curOrgUnitLongNumber) throws BOSException
    {
        try {
            return getController().getProject(getContext(), curOrgUnitLongNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡĿ��ɱ�_�������-User defined method
     *@param projectIDs ��ǰ��֯�����й�����ĿID
     *@return
     */
    public Map getAimSellData(Set projectIDs) throws BOSException
    {
        try {
            return getController().getAimSellData(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ��̬�ɱ�_�������-User defined method
     *@param projectIDs ��ǰ��֯�����й�����ĿID
     *@return
     */
    public Map getDynSellData(Set projectIDs) throws BOSException
    {
        try {
            return getController().getDynSellData(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�ò�Ʒ���Ͷ�Ӧ��Ŀ��ɱ��Ͷ�̬�ɱ�-User defined method
     *@param projectIDs ��ǰ��֯�����й�����ĿID
     *@param bizDate ҵ������
     *@return
     */
    public Map getTotalCost(Set projectIDs, Date bizDate) throws BOSException
    {
        try {
            return getController().getTotalCost(getContext(), projectIDs, bizDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}