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
     *��ʼ���������״�������̯���ݱ�-User defined method
     *@param curProjectID ��ǰ��Ŀ����ID
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
     *��ȡ������-User defined method
     *@param curProjectID ��ǰ����ID
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
     *��ȡ����������-User defined method
     *@param curProjectID ��ǰ��Ŀ����ID
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
     *��ȡ�������ף���Ʒ���ͣ�-User defined method
     *@param curProjectID ��ǰ������ĿID
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