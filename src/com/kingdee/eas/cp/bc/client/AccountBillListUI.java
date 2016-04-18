package com.kingdee.eas.cp.bc.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollBillTypeEnum;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.ExpenseAccountBillInfo;
import com.kingdee.eas.cp.bc.ExpenseAccountFacadeFactory;
import com.kingdee.eas.cp.bc.IExpenseAccountFacade;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.AbstractCoreBillListUI;
import com.kingdee.eas.framework.client.AbstractListUI;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JToolBar;
import org.apache.log4j.Logger;

// Referenced classes of package com.kingdee.eas.cp.bc.client:
//            AbstractAccountBillListUI, ExpenseReqCommonQueryUI, CollBillSelectUI, AbstractBizCollCoreBillListUI

public abstract class AccountBillListUI extends AbstractAccountBillListUI
{

    public AccountBillListUI()
        throws Exception
    {
        billMap = null;
        isSuspense = false;
    }

    public void onLoad()
        throws Exception
    {
        initDapButtons();
        super.onLoad();
        if(tblMain.getColumn("sourceId") != null)
            tblMain.getColumn("sourceId").getStyleAttributes().setHided(true);
        if(tblMain.getColumn("amountApproved") != null)
            tblMain.getColumn("amountApproved").getStyleAttributes().setHided(false);
        tblMain.getColumn("amountApproved").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
        afterSelectLine(null);
    }

    public void loadFields()
    {
        super.loadFields();
    }

    public void storeFields()
    {
        super.storeFields();
    }

    private boolean canPayment(ArrayList list)
    {
        IRow row = null;
        IRow rowEn = null;
        BizEnumValueInfo enumValue = null;
        BigDecimal amount1 = null;
        int nStatus = -1;
        KDTSelectBlock block = null;
        for(int i = 0; i < tblMain.getSelectManager().size(); i++)
        {
            block = tblMain.getSelectManager().get(i);
            for(int j = block.getTop(); j <= block.getBottom(); j++)
            {
                row = tblMain.getRow(j);
                if(row.getCell("state") != null)
                    enumValue = (BizEnumValueInfo)row.getCell("state").getValue();
                if(row.getCell("sourceId").getValue() == null)
                    continue;
                amount1 = ZERO;
                if(row.getCell("amountSendedBack").getValue() != null)
                    amount1 = (BigDecimal)row.getCell("amountSendedBack").getValue();
                if(amount1.compareTo(ZERO) == 0 && BizCollUtil.isLoanBill(row.getCell("sourceId").getValue().toString()) || enumValue != null && enumValue.getInt() != 60)
                {
                    MsgBox.showInfo(EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "canPayment"));
                    tblMain.getSelectManager().removeAll();
                    tblMain.getSelectManager().add(block);
                    return false;
                }
            }

        }

        return true;
    }

    public void actionCreateTo_actionPerformed(ActionEvent e)
        throws Exception
    {
        if(isSrcBillHasPaybill())
        {
            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "CANNOT_CREATEPAYMENTBILL"));
            return;
        } else
        {
            super.actionCreateTo_actionPerformed(e);
            refreshList();
            return;
        }
    }

    private boolean canVoucher(ArrayList list)
    {
        IRow row = null;
        IRow rowEn = null;
        KDTSelectBlock block = null;
        BigDecimal backAmount = null;
        BizEnumValueInfo enumState = null;
        for(int i = 0; i < tblMain.getSelectManager().size(); i++)
        {
            block = tblMain.getSelectManager().get(i);
            for(int j = block.getTop(); j <= block.getBottom(); j++)
            {
                row = tblMain.getRow(j);
                if(!isToVoucher)
                    return true;
                if(row.getCell("sourceId").getValue() == null || !BizCollUtil.isLoanBill(row.getCell("sourceId").getValue().toString()))
                    if(isSuspense)
                    {
                        return true;
                    } else
                    {
                        MsgBox.showInfo(EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "canVoucher"));
                        tblMain.getSelectManager().removeAll();
                        tblMain.getSelectManager().add(block);
                        return false;
                    }
                backAmount = (BigDecimal)row.getCell("amountSendedBack").getValue();
                if(row.getCell("sourceId").getValue() == null)
                    continue;
                enumState = (BizEnumValueInfo)row.getCell("state").getValue();
                if(backAmount != null && backAmount.compareTo(ZERO) > 0 && 70 != enumState.getInt())
                {
                    MsgBox.showInfo(EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "firstDoPayment"));
                    tblMain.getSelectManager().removeAll();
                    tblMain.getSelectManager().add(block);
                    return false;
                }
            }

        }

        return true;
    }

    public void actionVoucher_actionPerformed(ActionEvent e)
        throws Exception
    {
        ArrayList billIdlist = getSelectedIdValues();
        if(!canVoucher(billIdlist))
        {
            return;
        } else
        {
            super.actionVoucher_actionPerformed(e);
            refresh(e);
            return;
        }
    }

    protected void tblMain_tableSelectChanged(KDTSelectEvent e)
        throws Exception
    {
        super.tblMain_tableSelectChanged(e);
        afterSelectLine(e);
    }

    private void afterSelectLine(KDTSelectEvent e)
        throws Exception
    {
        IRow selectRow = null;
        BizEnumValueInfo enumValue = null;
        int nStatus = -1;
        if(e == null)
            return;
        KDTSelectBlock selectBlock = e.getSelectBlock();
        if(selectBlock == null)
            return;
        int selectBottom = selectBlock.getBottom();
        selectRow = tblMain.getRow(selectBottom);
        if(selectRow != null)
        {
            String applierName = null;
            if(selectRow.getCell("applier.name") != null)
            {
                applierName = (String)selectRow.getCell("applier.name").getValue();
                setViewRcrdsOfLendAndRepay(applierName);
            }
            if(selectRow.getCell("state") != null)
            {
                enumValue = (BizEnumValueInfo)selectRow.getCell("state").getValue();
                nStatus = enumValue.getInt();
            }
        }
        enableByState(nStatus);
    }

    private void enableByState(int nStatus)
        throws BOSException, Exception
    {
        actionRemove.setEnabled(true);
        boolean isEnbleByApplierCompany = BizCollUtil.enbleByApplierCompany(tblMain);
        if(nStatus == -1)
        {
            actionVoucher.setVisible(false);
            separatorFW4.setVisible(false);
            actionDelVoucher.setVisible(false);
            actionCreateTo.setVisible(false);
            actionEdit.setEnabled(false);
            actionRemove.setEnabled(false);
            actionSuspenseAcc.setEnabled(false);
        }
        if(isRelaFi)
        {
            actionSuspenseAcc.setEnabled(60 == nStatus && isEnbleByApplierCompany);
            btnSuspenseAcc.setVisible(true);
            MenuItemSuspenseAcc.setEnabled(60 == nStatus && isEnbleByApplierCompany);
            if(!isToVoucher)
            {
                boolean isVoucher = isRelaFi && (60 == nStatus || 110 == nStatus || 115 == nStatus);
                actionVoucher.setVisible(true);
                actionVoucher.setEnabled(isVoucher && isEnbleByApplierCompany);
                separatorFW4.setVisible(isVoucher && isEnbleByApplierCompany);
                actionCreateTo.setVisible(true);
                actionCreateTo.setEnabled(false);
                actionDelVoucher.setVisible(false);
            } else
            {
                boolean isVoucher = isRelaFi && (60 == nStatus || 70 == nStatus || 115 == nStatus || 110 == nStatus);
                actionCreateTo.setVisible(true);
                boolean enableCreateTo = (60 == nStatus || 110 == nStatus || 115 == nStatus) && isEnbelCreateTo() && isEnbleByApplierCompany;
                btnCreateTo.setEnabled(enableCreateTo);
                menuItemCreateTo.setVisible(true);
                menuItemCreateTo.setEnabled(enableCreateTo);
                separatorFW4.setVisible(isVoucher);
                actionVoucher.setVisible(true);
                actionVoucher.setEnabled(isVoucher && isEnbleByApplierCompany);
                actionDelVoucher.setVisible(false);
            }
        } else
        {
            actionCreateTo.setEnabled(false);
            btnCreateTo.setVisible(false);
            menuItemCreateTo.setVisible(false);
            actionSuspenseAcc.setEnabled(false);
            btnSuspenseAcc.setVisible(false);
            MenuItemSuspenseAcc.setVisible(false);
            actionVoucher.setVisible(false);
            actionVoucher.setEnabled(false);
            actionDelVoucher.setVisible(false);
            actionDelVoucher.setEnabled(false);
        }
        actionEdit.setEnabled(nStatus < 60 && nStatus != 30 && nStatus != 45 && isEnbleByApplierCompany);
        actionRemove.setEnabled((nStatus < 25 || nStatus == 27) && isEnbleByApplierCompany);
        actionAntiAudit.setEnabled(30 == nStatus && isEnbleByApplierCompany);
        actionAbandon.setEnabled((25 == nStatus || 30 == nStatus || 60 == nStatus) && isEnbleByApplierCompany);
        actionCloseBill.setEnabled(70 == nStatus || 60 == nStatus && isNotRelaFiAndVoucher() && isEnbleByApplierCompany);
        btnBatchsubmit.setEnabled(20 == nStatus || 25 == nStatus || 27 == nStatus || 40 == nStatus);
        setBatchSubmitMenu(nStatus);
    }

    protected void setBatchSubmitMenu(int i)
    {
    }

    protected void setViewRcrdsOfLendAndRepay(String s)
    {
    }

    protected KDPanel getUserPanel()
    {
        KDPanel ret = null;
        try
        {
            Map map = new HashMap();
            map.put("isCalledByAccount", new Boolean(true));
            ret = new ExpenseReqCommonQueryUI(map);
        }
        catch(Exception ex)
        {
            super.handleException(ex);
        }
        return ret;
    }

    protected BizCollBillTypeEnum[] getCountBillType()
    {
        return null;
    }

    private void calllCreateCount()
    {
        if(getCountBillType() == null)
        {
            return;
        } else
        {
            (new Thread(new Runnable() {

                public void run()
                {
                    try
                    {
                        IExpenseAccountFacade facade = ExpenseAccountFacadeFactory.getRemoteInstance();
                        billMap = facade.getCreateBillCount(getCountBillType());
                    }
                    catch(BOSException e)
                    {
                        billMap = null;
                    }
                    catch(EASBizException e)
                    {
                        billMap = null;
                    }
                }

            }
)).start();
            return;
        }
    }

    public void actionCloseBill_actionPerformed(ActionEvent e)
        throws Exception
    {
        checkSelected();
        int rows[] = KDTableUtil.getSelectedRows(tblMain);
        Arrays.sort(rows);
        IRow row = null;
        BizEnumValueInfo enumValue = null;
        StringBuffer sb = new StringBuffer();
        String errorRowIndexString = null;
        int nStatus = -1;
        for(int i = 0; i < rows.length; i++)
        {
            row = tblMain.getRow(rows[i]);
            if(row.getCell("state") != null)
                enumValue = (BizEnumValueInfo)row.getCell("state").getValue();
            if(enumValue != null)
                nStatus = enumValue.getInt();
            if(70 != nStatus && (60 != nStatus || !isNotRelaFiAndVoucher() || !BizCollUtil.enbleByApplierCompany(tblMain)))
                sb.append("" + (rows[i] + 1) + " , ");
        }

        if(sb.length() > 0)
        {
            int lastPosition = sb.lastIndexOf(",");
            errorRowIndexString = sb.substring(0, lastPosition);
        }
        if(errorRowIndexString != null)
            throw new BizCollException(BizCollException.ERROR_OPERATION, new Object[] {
                errorRowIndexString
            });
        int result = MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.cp.bc.ImportDataResource", "IS_DO_CLOSE"));
        if(result == 0)
        {
            java.util.List idList = getSelectIdList();
            String tempId = null;
            Iterator ite = idList.iterator();
            int successCount;
            for(successCount = 0; ite.hasNext(); successCount++)
            {
                tempId = (String)ite.next();
                IExpenseAccountFacade facade = ExpenseAccountFacadeFactory.getRemoteInstance();
                facade.setState(BOSUuid.read(tempId), StateEnum.CLOSED);
            }

            String message = EASResource.getString("com.kingdee.eas.cp.bc.ImportDataResource", "ClOSE_SUCCESS") + successCount + EASResource.getString("com.kingdee.eas.cp.bc.ImportDataResource", "TIAO") + "," + EASResource.getString("com.kingdee.eas.cp.bc.ImportDataResource", "CLOSE_FAILD") + 0 + EASResource.getString("com.kingdee.eas.cp.bc.ImportDataResource", "TIAO");
            setMessageText(message);
            showMessage();
            refresh(e);
        }
    }

    public void actionRemove_actionPerformed(ActionEvent e)
        throws Exception
    {
        checkSelected();
        int rows[] = KDTableUtil.getSelectedRows(tblMain);
        Arrays.sort(rows);
        IRow row = null;
        BizEnumValueInfo enumValue = null;
        StringBuffer sb = new StringBuffer();
        String errorRowIndexString = null;
        int nStatus = -1;
        for(int i = 0; i < rows.length; i++)
        {
            row = tblMain.getRow(rows[i]);
            if(row.getCell("state") != null)
                enumValue = (BizEnumValueInfo)row.getCell("state").getValue();
            if(enumValue != null)
                nStatus = enumValue.getInt();
           // if(nStatus >= 25 && nStatus != 27 || !BizCollUtil.enbleByApplierCompany(tblMain))
            if((nStatus ==80 )|| !BizCollUtil.enbleByApplierCompany(tblMain))
                sb.append("" + (rows[i] + 1) + " , ");
        }

        if(sb.length() > 0)
        {
            int lastPosition = sb.lastIndexOf(",");
            errorRowIndexString = sb.substring(0, lastPosition);
        }
        if(errorRowIndexString != null)
        {
            throw new BizCollException(BizCollException.ERROR_OPERATION, new Object[] {
                errorRowIndexString
            });
        } else
        {
            super.actionRemove_actionPerformed(e);
            return;
        }
    }

    public void actionEdit_actionPerformed(ActionEvent e)
        throws Exception
    {
        checkSelected();
        int rows[] = KDTableUtil.getSelectedRows(tblMain);
        if(rows.length > 1)
        {
            throw new BizCollException(BizCollException.CANNOT_MULTI_CHOICE);
        } else
        {
            super.actionEdit_actionPerformed(e);
            return;
        }
    }

    public void actionRefresh_actionPerformed(ActionEvent e)
        throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    public void refreshList()
        throws Exception
    {
        super.refreshList();
    }

    private boolean showChoice(IUIWindow window)
    {
        if(fillBillType() == null)
            return false;
        boolean actionType = false;
        try
        {
            UIContext uiContext = new UIContext(this);
            if(window == null)
            {
                IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
                window = uiFactory.create((com.kingdee.eas.cp.bc.client.CollBillSelectUI.class).getName(), uiContext, null, null);
            }
            ((CollBillSelectUI)window.getUIObject()).addBillTypes(fillBillType());
            window.show();
            actionType = ((CollBillSelectUI)window.getUIObject()).isConfirm();
        }
        catch(UIException e)
        {
            ExceptionHandler.handle(e);
        }
        return actionType;
    }

    protected void prepareUIContext(UIContext uiContext, ActionEvent e)
    {
        super.prepareUIContext(uiContext, e);
        if(!btnView.equals(e.getSource()) && !btnEdit.equals(e.getSource()))
            try
            {
                billMap = ExpenseAccountFacadeFactory.getRemoteInstance().getCreateBillCount(getCountBillType());
            }
            catch(EASBizException e1)
            {
                super.handleException(e1);
            }
            catch(BOSException e1)
            {
                super.handleException(e1);
            }
        if(billMap != null && billMap.size() > 0)
            uiContext.put("billTypes", billMap);
    }

    public void actionAddNew_actionPerformed(ActionEvent e)
        throws Exception
    {
        if(showChoice(null) || fillBillType() == null)
            super.actionAddNew_actionPerformed(e);
    }

    protected void setTotalLine()
    {
        totalRow = tblMain.getGroupManager().getStatRowTemplate(-1);
        totalRow.getStyleAttributes().setBackground(new Color(240, 238, 217));
        totalRow.getCell(tblMain.getColumnIndex("number")).setValue(EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "total"));
        totalRow.getCell(getKeyFieldName()).setValue("0");
        totalRow.getCell("amount").setExpressions("SUM");
        totalRow.getCell("amount").getStyleAttributes().setNumberFormat("%r-[=]{#,##0.00}.2f");
        totalRow.getCell("amount").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
        totalRow.getCell("amountApproved").setExpressions("SUM");
        totalRow.getCell("amountApproved").getStyleAttributes().setNumberFormat("%r-[=]{#,##0.00}.2f");
        totalRow.getCell("amountApproved").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
        totalRow.getStyleAttributes().setLocked(true);
        tblMain.getSelectManager().remove(-1);
        tblMain.getGroupManager().setTotalize(true);
        tblMain.getGroupManager().setGroup(true);
        tblMain.getGroupManager().setOrientation(1);
        tblMain.getGroupManager().group();
    }

    public void actionSuspenseAcc_actionPerformed(ActionEvent e)
        throws Exception
    {
        ArrayList billIdlist = getSelectedIdValues();
        isSuspense = true;
        if(!canVoucher(billIdlist))
        {
            isSuspense = false;
            return;
        }
        java.util.List idList = getSelectIdList();
        if(idList != null)
        {
            Iterator it = idList.iterator();
            ExpenseAccountBillInfo info = null;
            ObjectUuidPK objpk;
            IDynamicObject iDynamicObject;
            for(; it.hasNext(); iDynamicObject.update(objpk.getObjectType(), objpk, info))
            {
                String id = (String)it.next();
                objpk = new ObjectUuidPK(id);
                iDynamicObject = DynamicObjectFactory.getRemoteInstance();
                info = (ExpenseAccountBillInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk);
                info.setActionFlag("Y");
            }

            super.actionVoucher_actionPerformed(e);
            isSuspense = false;
            refresh(e);
        }
    }

    public boolean isEnbelCreateTo()
    {
        try
        {
            java.util.List idList = getSelectIdList();
            if(idList != null)
            {
                Iterator it = idList.iterator();
                ExpenseAccountBillInfo info = null;
                if(it.hasNext())
                {
                    String id = (String)it.next();
                    ObjectUuidPK objpk = new ObjectUuidPK(id);
                    IDynamicObject iDynamicObject = DynamicObjectFactory.getCachedRemoteInstance();
                    info = (ExpenseAccountBillInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk);
                    if(info.getAmountEncashed() != null && info.getAmountEncashed().compareTo(new BigDecimal("0.00")) > 0)
                        return true;
                }
            }
        }
        catch(BOSException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isbillClosed()
        throws BOSException
    {
        java.util.List idList = getSelectIdList();
        if(idList != null)
        {
            Iterator it = idList.iterator();
            BizCollBillBaseInfo info = null;
            if(it.hasNext())
            {
                String id = (String)it.next();
                ObjectUuidPK objpk = new ObjectUuidPK(id);
                IDynamicObject iDynamicObject = DynamicObjectFactory.getRemoteInstance();
                info = (BizCollBillBaseInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk);
                if(BizCollUtil.checkBillClosed(null, info))
                    return true;
            }
        }
        return false;
    }

    public boolean isBillHasRelatedBill()
        throws Exception
    {
        java.util.List idList = getSelectIdList();
        if(idList != null)
        {
            Iterator it = idList.iterator();
            BizCollBillBaseInfo info = null;
            if(it.hasNext())
            {
                String id = (String)it.next();
                ObjectUuidPK objpk = new ObjectUuidPK(id);
                IDynamicObject iDynamicObject = DynamicObjectFactory.getRemoteInstance();
                info = (BizCollBillBaseInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk);
                if(BizCollUtil.checkBillHasTracDown(null, info))
                    return true;
            }
        }
        return false;
    }

    protected void initWorkButton()
    {
        super.initWorkButton();
        initButtonsByisRelaFi();
    }

    protected void initDapButtons()
        throws Exception
    {
        super.initDapButtons();
        initButtonsByisRelaFi();
    }

    private void initButtonsByisRelaFi()
    {
        actionDelVoucher.setVisible(false);
        if(isRelaFi)
        {
            actionCreateTo.setVisible(true);
            actionVoucher.setVisible(true);
            actionSuspenseAcc.setVisible(true);
        } else
        {
            actionCreateTo.setVisible(false);
            actionVoucher.setVisible(false);
            actionSuspenseAcc.setVisible(false);
        }
    }

    public boolean isSrcBillHasPaybill()
        throws Exception
    {
        java.util.List idList = getSelectIdList();
        if(idList != null)
        {
            Iterator it = idList.iterator();
            BizCollBillBaseInfo info = null;
            if(it.hasNext())
            {
                String id = (String)it.next();
                ObjectUuidPK objpk = new ObjectUuidPK(id);
                IDynamicObject iDynamicObject = DynamicObjectFactory.getRemoteInstance();
                info = (BizCollBillBaseInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk);
                return BizCollUtil.checkBillHasRePaybill(null, info);
            }
        }
        return false;
    }

    public RequestContext prepareActionCopyAndAddNew(IItemAction itemAction)
        throws Exception
    {
        RequestContext request = super.prepareActionCopyAndAddNew(itemAction);
        request.put("billTypes", getCountBillType());
        return request;
    }

    public RequestContext prepareActionAddNew(IItemAction itemAction)
        throws Exception
    {
        RequestContext request = super.prepareActionAddNew(itemAction);
        request.put("billTypes", getCountBillType());
        return request;
    }

    public RequestContext prepareActionEdit(IItemAction itemAction)
        throws Exception
    {
        RequestContext request = super.prepareActionEdit(itemAction);
        request.put("billTypes", getCountBillType());
        return request;
    }

    public RequestContext prepareActionView(IItemAction itemAction)
        throws Exception
    {
        RequestContext request = super.prepareActionView(itemAction);
        request.put("billTypes", getCountBillType());
        return request;
    }

    private static final Logger logger;
    private CommonQueryDialog dialog;
    public static final int NOTHING = -1;
    public static final String RES_EXPENSE = "com.kingdee.eas.cp.bc.client.ExpAccResource";
    private static final String RESIMPORT = "com.kingdee.eas.cp.bc.ImportDataResource";
    private Map billMap;
    boolean isSuspense;
    public static final String STATUS_ADDNEW = "ADDNEW";

    static 
    {
        logger = CoreUIObject.getLogger(com.kingdee.eas.cp.bc.client.AccountBillListUI.class);
    }

}
