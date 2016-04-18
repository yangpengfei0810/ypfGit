package com.kingdee.eas.cp.bc.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.appframework.uistatemanage.ActionState;
import com.kingdee.bos.appframework.uistatemanage.ActionStateProvider;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.function.OperationInfo;
import com.kingdee.bos.metadata.function.UIActionRefInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.IBizobjectFacade;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.AmountControlTypeEnum;
import com.kingdee.eas.cp.bc.BillPrintFacadeFactory;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollBillTypeEnum;
import com.kingdee.eas.cp.bc.BizCollCoreBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.CacheCreateForm;
import com.kingdee.eas.cp.bc.EvectionReqBillInfo;
import com.kingdee.eas.cp.bc.ExpenseAccountBillInfo;
import com.kingdee.eas.cp.bc.ExpenseCommenFacadeFactory;
import com.kingdee.eas.cp.bc.ForPrintFacadeFactory;
import com.kingdee.eas.cp.bc.IBillPrintFacade;
import com.kingdee.eas.cp.bc.IExpenseCommenFacade;
import com.kingdee.eas.cp.bc.IForPrintFacade;
import com.kingdee.eas.cp.bc.PriorEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.fi.cas.client.ClientUtils;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ObjectValueUtil;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.batchHandler.client.UIActionPostman;
import com.kingdee.eas.framework.client.AbstractCoreBillEditUI;
import com.kingdee.eas.framework.client.AbstractEditUI;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.IntEnum;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

// Referenced classes of package com.kingdee.eas.cp.bc.client:
//            AbstractBizCollCoreBillEditUI, BcVOChangeListener

public abstract class BizCollCoreBillEditUI extends AbstractBizCollCoreBillEditUI
{
    public class DataProvider
        implements BOSQueryDelegate
    {

        public IRowSet execute(BOSQueryDataSource ds)
        {
            String str;
            IRowSet rowSet = null;
            str = ds.getID();
            try
            {
            if("bill".equals(str))
            {
                IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getRemoteInstance();
                rowSet = ibizForPrint.getRowset(getSelectIdList(), getReportQueryName());
                rowSet = parsePrintValue(rowSet);
                return rowSet;
            }
            if("r1bill".equals(str))
            {
                IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getRemoteInstance();
                rowSet = ibizForPrint.getRowset(getSelectIdList(), getR1ReportBillQueryName());
                rowSet = updateBillReprotRowSet(rowSet);
                return rowSet;
            }
            if("approveInfo".equals(str))
            {
                IBillPrintFacade billPrintFacade = BillPrintFacadeFactory.getRemoteInstance();
                ArrayList params = ds.getParams();
                FilterInfo filterInfo = new FilterInfo();
                int size = params.size();
                for(int i = 0; i < size; i++)
                {
                    DSParam dsParam = (DSParam)params.get(i);
                    String paramName = dsParam.getColName();
                    Variant value;
                    if(paramName.equals("billId") || paramName.equals("billID"))
                    {
                        value = dsParam.getValue();
                        if(value != null && !"".equals(value.toString()))
                            filterInfo.getFilterItems().add(new FilterItemInfo("billId", value.toString()));
                    }
                    if(!paramName.equals("isPass"))
                        continue;
                    value = dsParam.getValue();
                    if(value != null && !"".equals(value.toString()))
                        filterInfo.getFilterItems().add(new FilterItemInfo("isPass", value.toString().toLowerCase()));
                }

                int filterSize = filterInfo.getFilterItems().size();
                if(filterSize > 0)
                {
                    StringBuffer sb = new StringBuffer();
                    for(int i = 0; i < filterSize - 1; i++)
                        sb.append("#" + i + " and ");

                    sb.append("#" + (filterSize - 1));
                    filterInfo.setMaskString(sb.toString());
                }
                return billPrintFacade.queryApproveInfo(filterInfo);
            }
            if("r1entry".equals(str))
            {
                IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getRemoteInstance();
                Variant v = ((DSParam)ds.getParams().get(0)).getValue();
                rowSet = ibizForPrint.getEntryRowSet(v.toString(), getR1ReportEntryQueryName());
                rowSet = updateEntryReprotRowSet(rowSet);
                return rowSet;
            }
           
                if("entrysum".equals(str))
                {
                    IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getRemoteInstance();
                    Variant v = ((DSParam)ds.getParams().get(0)).getValue();
                    rowSet = ibizForPrint.getEntryRowSet(v.toString(), getR1ReportEntrySumQueryName());
                    return rowSet;
                }
            }
            catch(Exception e)
            {
                handUIException(e);
            }
            return null;
        }

        public DataProvider()
        {
            super();
        }
    }

    class wfActionStateProvider
        implements ActionStateProvider
    {

        public HashMap getActionState(IObjectValue vo, IObjectPK userInfoPk, String uiClass, HashMap actionSet)
        {
            Iterator itUIAction = actionSet.keySet().iterator();
            HashMap retDisableAction = new HashMap();
            String action = oper.getUiActionRef().getActionRefName();
            if(action == null || action.equals(""))
                return null;
            do
            {
                if(!itUIAction.hasNext())
                    break;
                Object acStateTarget = itUIAction.next();
                if(!action.toUpperCase().equals(splitClassName(acStateTarget.toString().toUpperCase())))
                    retDisableAction.put(acStateTarget, new ActionState(null, 0));
            } while(true);
            return retDisableAction;
        }

        wfActionStateProvider()
        {
            super();
        }
    }


    public BizCollCoreBillEditUI()
        throws Exception
    {
        ctMap = new HashMap();
        oldData = null;
        currentCompany = null;
        isFirst = false;
        oper = null;
        isRelaFi = true;
        isToVoucher = false;
        isNeedBudget = false;
        isInitAmountApproved = false;
        isReceiveBill = false;
        isPayBill = false;
        isShowBgAudit = false;
        isShowLoanBalance = false;
        isAllowOverApply = false;
        isDelimitOrgScope = false;
        loanBalanceShowName = null;
        loanBalanceAccountNumber = null;
        serverDate = null;
        periodInfo = null;
        attachMentTempID = null;
        paramMapBack = null;
        isNotActionCopyAddnew = true;
        isLoadFieldAction = false;
        UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
        if(user != null && user.getType() != null && user.getType() != UserType.PERSON)
        {
            MsgBox.showError(this, EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "userTypeIsWrong"));
            SysUtil.abort();
        }
    }

    public void setDataObject(IObjectValue dataObject)
    {
        com.kingdee.eas.framework.client.mutex.IVOChangeListener listener = new BcVOChangeListener();
        addVOChangeListener(listener);
        super.setDataObject(dataObject);
    }

    protected boolean checkEndDate(Date date)
    {
        if(periodInfo == null)
            getPeriodInfo();
        if(periodInfo == null)
            return false;
        if(date == null)
            return false;
        else
            return date.before(periodInfo.getBeginDate());
    }

    public PeriodInfo getPeriodInfo()
    {
        try
        {
            if(periodInfo == null)
            {
                CompanyOrgUnitInfo companyInfo = ClientUtils.getCurrLoginCompany();
                periodInfo = SystemStatusCtrolUtils.getCurrentPeriod(null, SystemEnum.CASHMANAGEMENT, companyInfo);
            }
            return periodInfo;
        }
        catch(EASBizException e)
        {
            e.printStackTrace();
        }
        catch(BOSException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public CompanyOrgUnitInfo getCurCompany()
    {
        return currentCompany;
    }

    public void onLoad()
        throws Exception
    {
        currentCompany = SysContext.getSysContext().getCurrentFIUnit();
        if("ADDNEW".equals(oprtState) && (currentCompany == null || currentCompany.isIsOnlyUnion()))
            throw new BizCollException(BizCollException.COMPANY_IS_UNION);
        List paramList = new ArrayList();
        paramList.add("CP001");
        paramList.add("CP002");
        paramList.add("CP004");
        paramList.add("CP006");
        paramList.add("CP008");
        paramList.add("CP010");
        paramList.add("CP011");
        paramList.add("CP012");
        paramList.add("CP013");
        paramList.add("CP016");
        paramList.add("CP017");
        paramList.add("CP019");
        Map paramMap = ExpenseCommenFacadeFactory.getRemoteInstance().getParams(paramList);
        paramMapBack = paramMap;
        isDelimitOrgScope = paramMap.get("CP019") != null ? (new Boolean(paramMap.get("CP019").toString())).booleanValue() : false;
        isAllowOverApply = paramMap.get("CP017") != null ? (new Boolean(paramMap.get("CP017").toString())).booleanValue() : false;
        isInitAmountApproved = paramMap.get("CP016") != null ? (new Boolean(paramMap.get("CP016").toString())).booleanValue() : false;
        isRelaFi = paramMap.get("CP002") != null ? (new Boolean(paramMap.get("CP002").toString())).booleanValue() : false;
        isToVoucher = paramMap.get("CP008") != null ? (new Boolean(paramMap.get("CP008").toString())).booleanValue() : false;
        isNeedBudget = paramMap.get("CP001") != null ? (new Boolean(paramMap.get("CP001").toString())).booleanValue() : false;
        isReceiveBill = paramMap.get("CP004") != null ? (new Boolean(paramMap.get("CP004").toString())).booleanValue() : false;
        isPayBill = paramMap.get("CP006") != null ? (new Boolean(paramMap.get("CP006").toString())).booleanValue() : false;
        if(isNeedBudget)
            isShowBgAudit = paramMap.get("CP010") != null ? (new Boolean(paramMap.get("CP010").toString())).booleanValue() : false;
        isShowLoanBalance = paramMap.get("CP011") != null ? (new Boolean(paramMap.get("CP011").toString())).booleanValue() : false;
        loanBalanceAccountNumber = paramMap.get("CP012") != null ? paramMap.get("CP012").toString() : null;
        loanBalanceShowName = paramMap.get("CP013") != null ? paramMap.get("CP013").toString() : null;
        if(!BizCollUtil.checkValidPerson())
            showErrorMessage("com.kingdee.eas.hr.train.client.TrainApplyResource", "userNoPerson", this);
        super.onLoad();
        if("ADDNEW".equals(getOprtState()))
        {
            btnAddNew.setEnabled(true);
            btnAddNew.setVisible(true);
            menuItemTraceUp.setEnabled(false);
            menuItemTraceDown.setEnabled(false);
        }
        menuItemPrint.setVisible(false);
        menuItemPrintPreview.setVisible(false);
        setState(editData.getState());
        if(!OprtState.ADDNEW.equals(oprtState))
        {
            btnCopy.setVisible(true);
            btnCopy.setEnabled(true);
        }
        btnAttachment.setText(btnAttachment.getToolTipText());
    }

    public void showErrorMessage(String RES, String message, CoreUI uiObject)
    {
        MsgBox.showError(uiObject, getResource(RES, message));
        uiObject.setCursorOfDefault();
        SysUtil.abort();
    }

    public String getResource(String RES, String message)
    {
        return EASResource.getString(RES, message);
    }

    protected IObjectValue createNewDetailData(KDTable table)
    {
        return null;
    }

    protected KDTable getDetailTable()
    {
        return null;
    }

    protected void initWorkButton()
    {
        super.initWorkButton();
        menuItemUdateReqDate.setIcon(EASResource.getIcon("imgTbtn_edit"));
        btnSuspenseAcc.setIcon(EASResource.getIcon("imgTbtn_suspendinstance"));
        MenuItemSuspenseAcc.setIcon(EASResource.getIcon("imgTbtn_suspendinstance"));
    }

    protected IObjectValue createNewData()
    {
        return null;
    }

    protected ICoreBase getBizInterface()
        throws Exception
    {
        return null;
    }

    protected void lockTableForView()
    {
        KDTable table = getDetailTable();
        if(table != null)
            table.setEditable(false);
    }

    protected void openTableExceptView()
    {
        KDTable table = getDetailTable();
        if(table != null)
            table.setEditable(true);
    }

    protected void lockSomeUIForWorkFlow()
    {
    }

    protected void initAction()
    {
        actionAttachment.setVisible(true);
        MenuItemAttachment.setVisible(true);
        btnAttachment.setVisible(true);
        actionCreateFrom.setVisible(false);
        btnCreateFrom.setVisible(false);
        actionCopyFrom.setVisible(false);
        btnCopyFrom.setVisible(false);
    }

    protected void isEnableAttachment()
    {
        if(editData == null || editData.getId() == null)
            actionAttachment.setEnabled(false);
        else
            actionAttachment.setEnabled(true);
    }

    protected String getMessageText()
    {
        return null;
    }

    protected BizCollBillTypeEnum[] getCountBillType()
    {
        return null;
    }

    private void lookCreate()
    {
        isFirst = true;
        if(getUIContext().get("isListCopyAndAddNew") != null && ((Boolean)getUIContext().get("isListCopyAndAddNew")).booleanValue())
            return;
        Object billObj = getUIContext().get("billTypes");
        try
        {
            paramMapBack.put("descBosType", getBillInterface().getType());
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
        }
        if(billObj == null && getCountBillType() != null)
            billObj = CacheCreateForm.getCreateBill(getCountBillType(), paramMapBack, false);
        if(billObj == null)
            return;
        Object obj = getUIContext().get("OwnerWindow");
        Map uiContext = new UIContext(obj != null ? obj : ((Object) (this)));
        uiContext.put("billTypes", billObj);
        try
        {
            IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
            IUIWindow window = uiFactory.create((com.kingdee.eas.cp.bc.client.CreateFromBillUI.class).getName(), uiContext, null, null);
            window.getUIObject().getUIContext().put("billEdit", this);
            window.getUIObject().getUIContext().put("srcBillBOSTypeString", getBillInterface().getType());
            window.show();
        }
        catch(Exception e) { }
    }

    public void onShow()
        throws Exception
    {
        super.onShow();
        initAction();
        if(!isFirst && "ADDNEW".equals(getOprtState()) && getUIContext().get("InitDataObject") == null)
        {
            lookCreate();
            initUIData();
        } else
        if(!isFirst)
            initUIData();
    }

    protected void initUIData()
    {
    }

    public boolean isShowCreate()
    {
        return isFirst;
    }

    protected void setState(StateEnum state)
        throws Exception
    {
        actionRemove.setEnabled(false);
        btnRemove.setVisible(false);
        actionEdit.setEnabled(false);
        btnEdit.setVisible(false);
        if(isFromWorkFlow())
            actionSave.setEnabled(false);
        if("ADDNEW".equals(oprtState) || state.getValue() != 30 && state.getValue() != 25)
            updateReqDateAction.setEnabled(false);
        if(state == null)
            return;
        if(!"VIEW".equals(oprtState) && !"FINDVIEW".equals(oprtState))
        {
            actionSave.setEnabled(canModify(state));
            actionRemoveLine.setEnabled(true);
            actionAddLine.setEnabled(true);
            actionCopyLine.setEnabled(true);
        } else
        {
            actionRemoveLine.setEnabled(false);
            actionAddLine.setEnabled(false);
            actionCopyLine.setEnabled(false);
        }
        boolean isSubmit = state.getValue() > 20;
        actionViewSubmitProccess.setVisible(!isSubmit);
        actionViewSubmitProccess.setEnabled(!isSubmit);
        btnWFViewSubmitProccess.setEnabled(!isSubmit);
        menuItemViewSubmitProccess.setEnabled(!isSubmit);
        actionTraceDown.setEnabled(isSubmit);
        actionTraceUp.setEnabled(isSubmit);
        if(state.getValue() > 25)
        {
            getUIContext().put("setAuditResultButton", new Boolean(true));
            actionAuditResult.setEnabled(true);
            btnAuditResult.setEnabled(true);
        }
    }

    protected boolean canModify(StateEnum state)
    {
        if(StateEnum.DRAFT.equals(state) || StateEnum.NEW.equals(state) || state.getValue() == 27)
            return actionSave.isEnabled();
        else
            return false;
    }

    public void loadFields()
    {
        super.loadFields();
    }

    protected void getNumberByCodingRule(IObjectValue caller, String orgId)
    {
        orgId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
        super.getNumberByCodingRule(caller, orgId);
    }

    public boolean isModify()
    {
        if(OprtState.VIEW.equals(getOprtState()))
            return false;
        if("FINDVIEW".equals(getOprtState()))
            return false;
        try
        {
            storeFields();
        }
        catch(Exception exc)
        {
            return false;
        }
        return !ObjectValueUtil.objectValueEquals(oldData, editData);
    }

    public void storeFields()
    {
        super.storeFields();
    }

    protected void initDataStatus()
    {
        if(OprtState.VIEW.equals(oprtState) || "FINDVIEW".equals(oprtState))
            lockTableForView();
        else
            openTableExceptView();
        if(AMOUNTAPPROVED.equals(oprtState) && isFromWorkFlow())
        {
            lockUIForViewStatus();
            lockSomeUIForWorkFlow();
        }
        super.initDataStatus();
    }

    public boolean isExistsCodingRule(IObjectValue model)
    {
        BizCollBillBaseInfo info;
        ICodingRuleManager iCodingRuleManager;
        String orgId;
        String companyId;
        try
        {
            info = (BizCollBillBaseInfo)model;
            iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if(!BizCollUtil.objectIsNull(SysContext.getSysContext().getCurrentFIUnit()) && !BizCollUtil.objectIsNull(SysContext.getSysContext().getCurrentFIUnit().getId()))
            {
	            companyId = getNextCompanyId();
	            if(BizCollUtil.objectIsNull(companyId))
	                return false;
	            orgId = companyId.toString();
            }else{
            	 orgId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
            }
            return iCodingRuleManager.isExist(info, orgId);
        }
        catch(Exception e)
        {
            handUIException(e);
            return false;
        }   
    }

    protected void inOnload()
        throws Exception
    {
        if(getUIContext().get("InitDataObject") != null && (getUIContext().get("InitDataObject") instanceof IObjectValue))
        {
            if(isFromWorkFlow() && "ActionSubmit".toLowerCase().equals(oper.getName().toLowerCase()))
                setDataObject(createNewData());
            else
                setDataObject((IObjectValue)getUIContext().get("InitDataObject"));
        } else
        if(getUIContext().get("ID") == null)
        {
            setDataObject(createNewData());
        } else
        {
            IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get("ID").toString()));
            setDataObject(getValue(pk));
        }
        loadFields();
        Object id = getUIContext().get("ID");
        if(id != null)
            prepareData(id.toString());
    }

    public boolean isFromWorkFlow()
    {
        oper = (OperationInfo)getUIContext().get("OperationInfo");
        Boolean isFromWF = (Boolean)getUIContext().get("isFromWorkflow");
        return isFromWF != null && isFromWF.booleanValue() && oper != null && !BizCollUtil.objectIsNull(oper.getName());
    }

    public void afterActionPerformed(java.awt.event.ActionEvent e)
    {
        super.afterActionPerformed(e);
        if(e.getSource().equals(btnSubmit) && !actionSubmit.isInvokeFailed() && oper != null && oper.getName() != null && "ActionSubmit".toLowerCase().equals(oper.getName().toLowerCase()))
            getUIWindow().close();
    }

    public void actionSubmit_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        int nStatus = editData.getState().getValue();
        if(nStatus >= 30 && !StateEnum.CHECKFAILD.equals(editData.getState()) && (!AMOUNTAPPROVED.equals(oprtState) || !isFromWorkFlow()))
        {
            String s = null;
            s = EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "BILL_IS_EXIST");
            MsgBox.showError(this, s);
            abort();
        }
        if(nStatus == 60 && isFromWorkFlow())
        {
            String s = null;
            s = EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "Input_WorkFlow_Finished");
            MsgBox.showError(this, s);
            abort();
        }
        super.actionSubmit_actionPerformed(e);
        oldData = editData;
        actionSave.setEnabled(false);
    }

    public void actionSave_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        super.actionSave_actionPerformed(e);
        oldData = editData;
    }

    public IObjectPK runSave()
        throws Exception
    {
        String oprtState = getOprtState();
        if(OprtState.ADDNEW.equals(oprtState))
            editData.setState(StateEnum.DRAFT);
        if(StateEnum.NEW == editData.getState())
            editData.setState(StateEnum.DRAFT);
        return super.runSave();
    }

    public IObjectPK runSubmit()
        throws Exception
    {
        String oprtState = getOprtState();
        beforeSubmit();
        if(OprtState.ADDNEW.equals(oprtState) || StateEnum.DRAFT.equals(editData.getState()) || StateEnum.NEW.equals(editData.getState()) || StateEnum.CHECKFAILD.equals(editData.getState()) || StateEnum.ALREADYABANDON.equals(editData.getState()))
            editData.setState(StateEnum.SUBMIT);
        return super.runSubmit();
    }

    protected void beforeSave()
        throws Exception
    {
        if(isRelaFi && checkEndDate(editData.getBizReqDate()))
        {
            String s = null;
            s = EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "endDate");
            MsgBox.showError(this, s);
            abort();
        }
    }

    public void actionAddLine_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        if(AMOUNTAPPROVED.equals(oprtState) && isFromWorkFlow())
        {
            String s = null;
            s = EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "ENTRYTABLE_CANNOT_UPDATE");
            MsgBox.showError(this, s);
            abort();
        }
        super.actionAddLine_actionPerformed(e);
    }

    public void actionRemoveLine_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        if(AMOUNTAPPROVED.equals(oprtState) && isFromWorkFlow())
        {
            String s = null;
            s = EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "ENTRYTABLE_CANNOT_UPDATE");
            MsgBox.showError(this, s);
            abort();
        }
        super.actionRemoveLine_actionPerformed(e);
    }

    protected void beforeSubmit()
        throws Exception
    {
    }

    public void updateReqDateAction_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        super.updateReqDateAction_actionPerformed(e);
        Map ctx = new HashMap();
        ctx.put("billId", editData.getId().toString());
        ctx.put("reqDate", editData.getBizReqDate());
        ctx.put("Owner", this);
        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
        IUIWindow window = uiFactory.create((com.kingdee.eas.cp.bc.client.UpdateReqDateEditUI.class).getName(), ctx);
        window.show();
    }

    public void actionCreateTo_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        BizCollBillBaseInfo billInfo = (BizCollBillBaseInfo)editData;
        if(!(billInfo instanceof ExpenseAccountBillInfo) && !(billInfo instanceof EvectionReqBillInfo) && (billInfo.getAmountControlType() == null || billInfo.getAmountControlType() != null && 10 == billInfo.getAmountControlType().getValue()) && billInfo.getAmountBalance() != null && billInfo.getAmountBalance().doubleValue() <= 0.0D )//
            //throw new BizCollException(BizCollException.CANNTOTCREATETO);
        if(isRelaFi && !BizCollUtil.checkForBillCreateTo(null, editData.getId().toString()))
        {
            throw new BizCollException(BizCollException.CANNOT_CREATETO);
        } else
        {
            super.actionCreateTo_actionPerformed(e);
            setState(BizCollUtil.getBillState(editData.getId().toString()));
            setSave(true);
            setSaved(true);
            return;
        }
    }

    public void actionDelVoucher_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        BizCollBillBaseInfo billInfo = (BizCollBillBaseInfo)editData;
        if(BizCollUtil.checkForDelVoucher(billInfo))
            super.actionDelVoucher_actionPerformed(e);
        else
            throw new BizCollException(BizCollException.CANNOT_DELVOUCHER);
        setState(BizCollUtil.getBillState(editData.getId().toString()));
        setSave(true);
        setSaved(true);
    }

    public void actionVoucher_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        super.actionVoucher_actionPerformed(e);
        setState(BizCollUtil.getBillState(editData.getId().toString()));
        setSave(true);
        setSaved(true);
    }

    public void actionSuspenseAcc_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        super.actionVoucher_actionPerformed(e);
        setState(BizCollUtil.getBillState(editData.getId().toString()));
        setSave(true);
        setSaved(true);
    }

    public void actionCopy_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        serverDate = ExpenseCommenFacadeFactory.getRemoteInstance().getServerTime();
        super.actionCopy_actionPerformed(e);
        setState(editData.getState());
    }

    public void actionNext_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        super.actionNext_actionPerformed(e);
        setState(editData.getState());
    }

    public void actionFirst_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        super.actionFirst_actionPerformed(e);
        setState(editData.getState());
    }

    public void actionLast_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        super.actionLast_actionPerformed(e);
        setState(editData.getState());
    }

    public void actionPre_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        super.actionPre_actionPerformed(e);
        setState(editData.getState());
    }

    protected int showNextPersonMessage(String msg)
        throws Exception
    {
        int option = 1;
        return option;
    }

    public Context getRelationCreatedMainOrgContext()
    {
        return getMainOrgContext();
    }

    public OrgType getRelationCreatedMainBizOrgType()
    {
        return getMainBizOrgType();
    }

    public boolean isNotActionCopyAddnew()
    {
        return isNotActionCopyAddnew;
    }

    public void setNotActionCopyAddnew(boolean isNotActionCopyAddnew)
    {
        this.isNotActionCopyAddnew = isNotActionCopyAddnew;
    }

    public IUIActionPostman prepareInit()
    {
        IUIActionPostman clientHanlder = super.prepareInit();
        if(clientHanlder != null)
        {
            RequestContext request = new RequestContext();
            List paramList = new ArrayList();
            paramList.add("CP001");
            paramList.add("CP002");
            paramList.add("CP004");
            paramList.add("CP006");
            paramList.add("CP008");
            paramList.add("CP010");
            paramList.add("CP011");
            paramList.add("CP012");
            paramList.add("CP013");
            paramList.add("CP016");
            paramList.add("CP017");
            paramList.add("CP019");
            request.put("paramList", paramList);
            request.put("billTypes", getCountBillType());
            Boolean isListCopyAndAddNew = (Boolean)getUIContext().get("isListCopyAndAddNew");
            if(isListCopyAndAddNew != null && isListCopyAndAddNew.booleanValue())
                request.put("isListCopyAndAddNew", isListCopyAndAddNew);
            clientHanlder.setRequestContext(request);
        }
        return clientHanlder;
    }

    public IUIActionPostman prepareInitUIData()
    {
        IUIActionPostman handler = UIActionPostman.getInstance(this);
        handler.setAvailabe(UtilRequest.isPrepare("InitUIData", this));
        if(!handler.isAvailabe())
        {
            return handler;
        } else
        {
            RequestContext requestContext = new RequestContext();
            requestContext.setClassName(getUIHandlerClassName());
            requestContext.setMethodName("handleInitUIData");
            handler.setRequestContext(requestContext);
            return handler;
        }
    }

    public IUIActionPostman prepareActionCopyNew()
        throws Exception
    {
        return prepareAction(actionCopy.getItemAction());
    }

    public ItemAction getCopyItemAction()
        throws Exception
    {
        return actionCopy.getItemAction();
    }

    public RequestContext prepareActionSave(IItemAction itemAction)
        throws Exception
    {
        RequestContext request = super.prepareActionSave(itemAction);
        request.put("oprtState", getOprtState());
        return request;
    }

    public RequestContext prepareActionSubmit(IItemAction itemAction)
        throws Exception
    {
        RequestContext request = super.prepareActionSubmit(itemAction);
        request.put("oprtState", getOprtState());
        return request;
    }

    public boolean isPrepareActionCopy()
    {
        return isNotActionCopyAddnew;
    }

    public boolean isPrepareActionTraceUp()
    {
        return true;
    }

    public boolean isPrepareActionTraceDown()
    {
        return true;
    }

    public boolean isPrepareInitUIData()
    {
        return true;
    }

    protected void doAfterSubmit(IObjectPK pk)
        throws Exception
    {
        if(OprtState.ADDNEW.equals(getOprtState()))
            attachmentPerform(attachMentTempID, pk.toString());
        super.doAfterSubmit(pk);
    }

    protected void doAfterSave(IObjectPK pk)
        throws Exception
    {
        if(OprtState.ADDNEW.equals(getOprtState()))
            attachmentPerform(attachMentTempID, pk.toString());
        super.doAfterSave(pk);
    }

    public void actionAttachment_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        boolean isEdit = false;
        if(OprtState.ADDNEW.equals(getOprtState()))
            isEdit = true;
        else
        if(OprtState.EDIT.equals(getOprtState()) && (editData.getState() == StateEnum.DRAFT || editData.getState() == StateEnum.SUBMIT || editData.getState() == StateEnum.CHECKFAILD || editData.getState() == StateEnum.CANCELED || editData.getState() == StateEnum.ALREADYABANDON) && editData.getBiller() != null && editData.getBiller().getId() != null && editData.getBiller().getId().toString().equals(SysContext.getSysContext().getCurrentUserInfo().getId().toString()))
            isEdit = true;
        AttachmentUIContextInfo info = getAttacheInfo();
        if(info == null)
            info = new AttachmentUIContextInfo();
        if(info.getBoID() == null || info.getBoID().trim().equals(""))
        {
            String boID = getSelectBOID();
            if(boID == null)
                if(OprtState.ADDNEW.equals(getOprtState()))
                {
                    if(attachMentTempID == null)
                    {
                        boID = acm.getAttID().toString();
                        attachMentTempID = boID;
                    } else
                    {
                        boID = attachMentTempID;
                    }
                } else
                {
                    return;
                }
            info.setBoID(boID);
        }
        info.setEdit(isEdit);
        String multi = (String)getUIContext().get("MultiapproveAttachment");
        if(multi != null && multi.equals("true"))
            acm.showAttachmentListUIByBoIDNoAlready(this, info);
        else
            acm.showAttachmentListUIByBoID(this, info);
    }

    private void attachmentPerform(String tempId, String pk)
    {
        if(pk == null || tempId == null)
            return;
        try
        {
            BizobjectFacadeFactory.getRemoteInstance().upAttID2BosID(tempId, pk);
            BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(tempId);
            attachMentTempID = null;
        }
        catch(Exception e)
        {
            logger.error(e);
        }
        return;
    }

    public void actionAddNew_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        getUIContext().put("CP_BC_ADDNEW_CONTINOUSLY", new Boolean(true));
        super.actionAddNew_actionPerformed(e);
        getUIContext().put("CP_BC_ADDNEW_CONTINOUSLY", null);
    }

    public void actionCopyLine_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        if(AMOUNTAPPROVED.equals(oprtState) && isFromWorkFlow())
        {
            String s = null;
            s = EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "ENTRYTABLE_CANNOT_UPDATE");
            MsgBox.showError(this, s);
            abort();
        }
        KDTable table = getDetailTable();
        if(table == null)
            return;
        KDTSelectManager manager = table.getSelectManager();
        if(manager.getBlocks().size() > 0)
        {
            IRow copyFromRowObj = null;
            IRow copyToRowObj = null;
            int copyFromRowIndex = 0;
            int copyToRowIndex = 0;
            for(int i = 0; i < manager.getBlocks().size(); i++)
            {
                KDTSelectBlock selectedBlock = (KDTSelectBlock)manager.getBlocks().get(i);
                copyFromRowIndex = selectedBlock.getBeginRow();
                for(int j = copyFromRowIndex; j <= selectedBlock.getEndRow(); j++)
                {
                    copyFromRowObj = table.getRow(j);
                    addLine(getDetailTable());
                    copyToRowIndex = KDTableUtil.getLastVisibleRowIndex(table);
                    copyToRowObj = table.getRow(copyToRowIndex);
                    for(int cellIndex = 0; cellIndex < table.getColumnCount(); cellIndex++)
                    {
                        if(table.getColumn(cellIndex).getKDTColumn().getFieldName() != "operationType" && table.getColumn(cellIndex).getKDTColumn().getFieldName() != "expenseType" && table.getColumn(cellIndex).getKDTColumn().getFieldName() != "costCenter" && table.getColumn(cellIndex).getKDTColumn().getFieldName() != "company")
                            continue;
                        ICell copyFromCell = copyFromRowObj.getCell(cellIndex);
                        ICell copyToCell = copyToRowObj.getCell(cellIndex);
                        if(copyFromCell != null && copyToCell != null)
                        {
                            Object orgValue = copyFromCell.getValue();
                            copyToCell.setValue(orgValue);
                        }
                    }

                    if(getSubKeyFieldName() != null)
                    {
                        ICell cellId = copyToRowObj.getCell(getSubKeyFieldName());
                        if(cellId != null)
                            cellId.setValue(null);
                    }
                    dataBinder.storeLineFields(table, copyToRowObj, (IObjectValue)copyToRowObj.getUserObject());
                }

            }

        } else
        {
            MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneRow"));
            return;
        }
    }

    protected List getSelectIdList()
    {
        if(editData.getId() == null)
        {
            return null;
        } else
        {
            ArrayList list = new ArrayList();
            list.add(editData.getId().toString());
            return list;
        }
    }

    protected String getReportQueryName()
    {
        return null;
    }

    protected String getR1ReportBillQueryName()
    {
        return null;
    }

    protected String getR1ReportEntryQueryName()
    {
        return null;
    }

    protected String getR1ReportEntrySumQueryName()
    {
        return null;
    }

    protected IRowSet updateBillReprotRowSet(IRowSet rowSet)
        throws SQLException
    {
        BigDecimal amount = null;
        for(; rowSet.next(); rowSet.updateString("amountCapital", GlUtils.getChineseFormat(amount, false)))
        {
            amount = rowSet.getBigDecimal("amountApproved");
            if(amount != null && amount.compareTo(new BigDecimal("0.00")) == 0)
                amount = new BigDecimal("0.00");
        }

        rowSet.beforeFirst();
        rowSet = parseEnumValue(rowSet, "prior", "prioralias", PriorEnum.getEnumList());
        rowSet = parseEnumValue(rowSet, "state", "stateAlias", StateEnum.getEnumList());
        return rowSet;
    }

    protected IRowSet updateEntryReprotRowSet(IRowSet rowSet)
        throws SQLException
    {
        return rowSet;
    }

    protected IRowSet parsePrintValue(IRowSet rowSet)
        throws SQLException
    {
        BigDecimal amount = null;
        rowSet.previous();
        for(; rowSet.next(); rowSet.updateString("amountCapital", GlUtils.getChineseFormat(amount, false)))
        {
            amount = rowSet.getBigDecimal("amountApproved");
            if(amount != null && amount.compareTo(new BigDecimal("0.00")) == 0)
                amount = new BigDecimal("0.00");
        }

        rowSet.beforeFirst();
        rowSet = parseEnumValue(rowSet, "prior", "prioralias", PriorEnum.getEnumList());
        rowSet = parseEnumValue(rowSet, "state", "stateAlias", StateEnum.getEnumList());
        return rowSet;
    }

    public IRowSet parseEnumValue(IRowSet rowSet, String value, String valueAlias, List list)
        throws SQLException
    {
        if(rowSet == null)
            return rowSet;
        if(list == null)
            return rowSet;
        if(value == null || value.equals(""))
            return rowSet;
        if(!rowSet.next())
            return rowSet;
        String mark = "";
        int pos = rowSet.findColumn(valueAlias);
        Iterator ite = null;
        IntEnum intenum = null;
        rowSet.previous();
label0:
        for(; rowSet.next(); rowSet.updateString(pos, mark))
        {
            ite = list.iterator();
            int tempPrior = rowSet.getInt(value);
            do
            {
                if(!ite.hasNext())
                    continue label0;
                intenum = (IntEnum)ite.next();
            } while(tempPrior != intenum.getValue());
            mark = intenum.getAlias();
        }

        rowSet.beforeFirst();
        return rowSet;
    }

    private Map ctMap;
    protected AbstractObjectValue oldData;
    private CompanyOrgUnitInfo currentCompany;
    private boolean isFirst;
    private OperationInfo oper;
    boolean isRelaFi;
    boolean isToVoucher;
    boolean isNeedBudget;
    boolean isInitAmountApproved;
    boolean isReceiveBill;
    boolean isPayBill;
    boolean isShowBgAudit;
    boolean isShowLoanBalance;
    boolean isAllowOverApply;
    boolean isDelimitOrgScope;
    String loanBalanceShowName;
    String loanBalanceAccountNumber;
    Date serverDate;
    private PeriodInfo periodInfo;
    private String attachMentTempID;
    private static final Logger logger;
    Map paramMapBack;
    private boolean isNotActionCopyAddnew;
    public static final String RES = "com.kingdee.eas.cp.bc.client.ExpAccResource";
    public static String AMOUNTAPPROVED = "amountApproved";
    public boolean isLoadFieldAction;

    static 
    {
        logger = CoreUIObject.getLogger(com.kingdee.eas.cp.bc.client.BizCollCoreBillEditUI.class);
    }


}
