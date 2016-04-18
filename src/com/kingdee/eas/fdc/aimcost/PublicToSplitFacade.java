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
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;

public class PublicToSplitFacade extends AbstractBizCtrl implements IPublicToSplitFacade
{
    public PublicToSplitFacade()
    {
        super();
        registerInterface(IPublicToSplitFacade.class, this);
    }
    public PublicToSplitFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IPublicToSplitFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("38302C39");
    }
    private PublicToSplitFacadeController getController() throws BOSException
    {
        return (PublicToSplitFacadeController)getBizController();
    }
    /**
     *初始化公共配套待发生分摊数据表-User defined method
     *@param curProjectID 当前项目工程ID
     */
    public void initDBTable(String curProjectID) throws BOSException
    {
        try {
            getController().initDBTable(getContext(), curProjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取行数据-User defined method
     *@param curProjectID 当前工程ID
     *@return
     */
    public IRowSet getRowData(String curProjectID) throws BOSException
    {
        try {
            return getController().getRowData(getContext(), curProjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取报表界面的列-User defined method
     *@param curProjectID 当前项目工程ID
     *@return
     */
    public IRowSet getPublicToSplitColums(String curProjectID) throws BOSException
    {
        try {
            return getController().getPublicToSplitColums(getContext(), curProjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取公共配套（产品类型）-User defined method
     *@param curProjectID 当前工程项目ID
     *@return
     */
    public IRowSet getPublicRow(String curProjectID) throws BOSException
    {
        try {
            return getController().getPublicRow(getContext(), curProjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}