package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import java.math.BigDecimal;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;

public class DynCostDetailFacade extends AbstractBizCtrl implements IDynCostDetailFacade
{
    public DynCostDetailFacade()
    {
        super();
        registerInterface(IDynCostDetailFacade.class, this);
    }
    public DynCostDetailFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IDynCostDetailFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4FB7D69A");
    }
    private DynCostDetailFacadeController getController() throws BOSException
    {
        return (DynCostDetailFacadeController)getBizController();
    }
    /**
     *���涯̬�ɱ���ϸ����-User defined method
     *@param dynCostDetailDataCollection ��̬�ɱ���ϸ���ݼ���
     *@param dynCostDetailInfo ���ð汾��Ϣ
     *@return
     */
    public boolean saveDynCostDetailData(DynCostDetailDataCollection dynCostDetailDataCollection, DynCostDetailInfo dynCostDetailInfo) throws BOSException, EASBizException
    {
        try {
            return getController().saveDynCostDetailData(getContext(), dynCostDetailDataCollection, dynCostDetailInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     * -User defined method
     *@param curProjectID ������ĿID
     *@return
     */
    public IRowSet getDynCostDetailData(String curProjectID) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCostDetailData(getContext(), curProjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ID��ȡ�Ѿ������������-User defined method
     *@param parentID parentID
     *@return
     */
    public IRowSet getDetailDataForID(String parentID) throws BOSException
    {
        try {
            return getController().getDetailDataForID(getContext(), parentID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������İ汾��-User defined method
     *@param curProjectID ��ǰ������ĿID
     *@return
     */
    public BigDecimal getMaxVer(String curProjectID) throws BOSException
    {
        try {
            return getController().getMaxVer(getContext(), curProjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ��ܺ�Լ��Ӧ��ͬ���������-User defined method
     *@param curProjectID ������ĿID
     *@return
     */
    public Map getSumLastCost(String curProjectID) throws BOSException
    {
        try {
            return getController().getSumLastCost(getContext(), curProjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Կ�ܺ�ԼΪά�Ȼ�ȡ�������������-User defined method
     *@param parentID parentID
     *@return
     */
    public Map getSumLastCostForSave(String parentID) throws BOSException
    {
        try {
            return getController().getSumLastCostForSave(getContext(), parentID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ۼ����룬�ۼ�ʵ��-User defined method
     *@param contractid ��ͬid
     *@param pcid ��ܺ�Լid
     *@return
     */
    public Map getpayRequest(String contractid, String pcid) throws BOSException, EASBizException
    {
        try {
            return getController().getpayRequest(getContext(), contractid, pcid);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}