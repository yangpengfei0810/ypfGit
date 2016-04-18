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

public class PublicSetSplitFacade extends AbstractBizCtrl implements IPublicSetSplitFacade
{
    public PublicSetSplitFacade()
    {
        super();
        registerInterface(IPublicSetSplitFacade.class, this);
    }
    public PublicSetSplitFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IPublicSetSplitFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("254E0132");
    }
    private PublicSetSplitFacadeController getController() throws BOSException
    {
        return (PublicSetSplitFacadeController)getBizController();
    }
    /**
     *获取报表界面的列-User defined method
     *@param curProjectID 工程ID
     *@return
     */
    public IRowSet getPublicSetSplitColums(String curProjectID) throws BOSException
    {
        try {
            return getController().getPublicSetSplitColums(getContext(), curProjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *清空数据表-User defined method
     *@param projectID 项目工程ID
     */
    public void initDBTable(String projectID) throws BOSException
    {
        try {
            getController().initDBTable(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取行数据-User defined method
     *@param curProjectID 项目工程ID
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