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
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.aimcost.app.*;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.RetValue;

public class ProjectCostRptFacade extends AbstractBizCtrl implements IProjectCostRptFacade
{
    public ProjectCostRptFacade()
    {
        super();
        registerInterface(IProjectCostRptFacade.class, this);
    }
    public ProjectCostRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectCostRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0FC51A21");
    }
    private ProjectCostRptFacadeController getController() throws BOSException
    {
        return (ProjectCostRptFacadeController)getBizController();
    }
    /**
     *ȡ�ñ�������-User defined method
     *@param param ����
     *@return
     */
    public RetValue getData(ParamValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������-User defined method
     *@param prjId ��ĿID
     */
    public void updateData(String prjId) throws BOSException, EASBizException
    {
        try {
            getController().updateData(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������б仯�ɱ�����Ŀ-User defined method
     */
    public void updateAllData() throws BOSException
    {
        try {
            getController().updateAllData(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ҫȡ���ݵ���Ŀ�ĳɱ�-User defined method
     *@param prjSet prjSet
     */
    public void updateProjectsCost(Set prjSet) throws BOSException, EASBizException
    {
        try {
            getController().updateProjectsCost(getContext(), prjSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ������ϸ������Ŀ�Ŀ������,���ڲ�Ʒ��������,���صĽ���Բ�Ʒ����,�������Ϊֵ-User defined method
     *@param leafPrjIDs leafPrjIDs
     *@return
     */
    public RetValue getProductSellArea(Set leafPrjIDs) throws BOSException, EASBizException
    {
        try {
            return getController().getProductSellArea(getContext(), leafPrjIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ʒ����Ŀ��ɱ����ȡ����-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionProductAimCost(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionProductAimCost(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ʒ���Ͷ�̬Ŀ��ɱ����ȡ����-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionProductDynAimCost(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionProductDynAimCost(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ʒ���Ͷ�̬�ɱ����ȡ����-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionProductDynCost(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionProductDynCost(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȫ��Ŀ��̬�ɱ����ȡ����-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionFullDynCost(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionFullDynCost(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��Ŀ��ͬ��ϸ���ȡ����-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionContractAcctCost(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionContractAcctCost(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��Ŀ��ͬ��ϸ�������ڻ�ȡ����-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionContractAcctCostDetails(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionContractAcctCostDetails(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������Ŀ�ɱ�������-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getProjectCostAnalysis(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectCostAnalysis(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�鿴�ɱ���Ϣ-User defined method
     *@param param param
     *@return
     */
    public RetValue getDynCostInfo(ParamValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCostInfo(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ŀ��ɱ���̬����-User defined method
     *@param param param
     *@return
     */
    public RetValue getAimCostDynInfo(ParamValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostDynInfo(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ�Ժ�Լ��������ֵ��ѱ������������-User defined method
     *@param probjectID ������Ŀid
     *@return
     */
    public Map getConOccurSplitListEntry(String probjectID) throws BOSException, EASBizException
    {
        try {
            return getController().getConOccurSplitListEntry(getContext(), probjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}