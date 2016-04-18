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
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import java.util.Set;

public class ProductDynamicDetailFacade extends AbstractBizCtrl implements IProductDynamicDetailFacade
{
    public ProductDynamicDetailFacade()
    {
        super();
        registerInterface(IProductDynamicDetailFacade.class, this);
    }
    public ProductDynamicDetailFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProductDynamicDetailFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5B3C1B52");
    }
    private ProductDynamicDetailFacadeController getController() throws BOSException
    {
        return (ProductDynamicDetailFacadeController)getBizController();
    }
    /**
     *获取产品类型的集合-User defined method
     *@param projectIDs 工程项目ID
     *@return
     */
    public ProductTypeCollection getProductTypes(Set projectIDs) throws BOSException
    {
        try {
            return getController().getProductTypes(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取明细数据-User defined method
     *@param projectIDs 工程项目ID
     *@return
     */
    public Map getDetailData(Set projectIDs) throws BOSException
    {
        try {
            return getController().getDetailData(getContext(), projectIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取已发生、待发生分摊中分摊到相应产品类型的合计-User defined method
     *@param projectID 工程项目ID
     *@return
     */
    public Map getSplitAmount(String projectID) throws BOSException
    {
        try {
            return getController().getSplitAmount(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}