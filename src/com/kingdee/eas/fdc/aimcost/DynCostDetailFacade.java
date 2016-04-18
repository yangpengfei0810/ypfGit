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
     *保存动态成本明细数据-User defined method
     *@param dynCostDetailDataCollection 动态成本明细数据集合
     *@param dynCostDetailInfo 引用版本信息
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
     *@param curProjectID 工程项目ID
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
     *根据ID获取已经保存过的数据-User defined method
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
     *返回最大的版本号-User defined method
     *@param curProjectID 当前工程项目ID
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
     *获取框架合约对应合同的最新造价-User defined method
     *@param curProjectID 工程项目ID
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
     *以框架合约为维度获取保存后的最新造价-User defined method
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
     *累计申请，累计实付-User defined method
     *@param contractid 合同id
     *@param pcid 框架合约id
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