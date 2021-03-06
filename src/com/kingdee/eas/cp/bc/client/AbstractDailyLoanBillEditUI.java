/**
 * output package name
 */
package com.kingdee.eas.cp.bc.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractDailyLoanBillEditUI extends com.kingdee.eas.cp.bc.client.LoanReqEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDailyLoanBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer19;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer20;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer21;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer22;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer23;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer24;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer25;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnEntry;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabel mark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer26;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer27;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer28;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckAmountControl;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptApplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptPosition;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCompany;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPrior;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCurrencyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCostedDept;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField FormattedTextFieldAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptSupportedObj;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptExpenseType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateBillDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptBiller;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateReqDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptPayType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateAuditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptUpdator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateUpdateDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextAreaCause;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextAreaDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateYjBack;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField FormattedTextFieldAmountApproved;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmountUsed;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmountBalance;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptApplierCompany;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewRcrdsOfLendAndRepay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewRcds;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBudget;
    protected com.kingdee.eas.cp.bc.DailyLoanBillInfo editData = null;
    protected ActionViewRrcdsOfLendAndRepay actionViewRrcdsOfLendAndRepay = null;
    /**
     * output class constructor
     */
    public AbstractDailyLoanBillEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractDailyLoanBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionTraceDown
        actionTraceDown.setEnabled(true);
        actionTraceDown.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionTraceDown.SHORT_DESCRIPTION");
        actionTraceDown.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionTraceDown.LONG_DESCRIPTION");
        actionTraceDown.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionTraceDown.NAME");
        actionTraceDown.putValue(ItemAction.NAME, _tempStr);
         this.actionTraceDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionTraceDown.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionTraceDown.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionViewBudgetBalance
        actionViewBudgetBalance.setEnabled(true);
        actionViewBudgetBalance.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionViewBudgetBalance.SHORT_DESCRIPTION");
        actionViewBudgetBalance.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionViewBudgetBalance.LONG_DESCRIPTION");
        actionViewBudgetBalance.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionViewBudgetBalance.NAME");
        actionViewBudgetBalance.putValue(ItemAction.NAME, _tempStr);
         this.actionViewBudgetBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCreatePayNotifyBill
        actionCreatePayNotifyBill.setEnabled(false);
        actionCreatePayNotifyBill.setDaemonRun(true);

        _tempStr = resHelper.getString("ActionCreatePayNotifyBill.SHORT_DESCRIPTION");
        actionCreatePayNotifyBill.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreatePayNotifyBill.LONG_DESCRIPTION");
        actionCreatePayNotifyBill.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreatePayNotifyBill.NAME");
        actionCreatePayNotifyBill.putValue(ItemAction.NAME, _tempStr);
        this.actionCreatePayNotifyBill.setBindWorkFlow(true);
         this.actionCreatePayNotifyBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCreateVoucher
        actionCreateVoucher.setEnabled(true);
        actionCreateVoucher.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionCreateVoucher.SHORT_DESCRIPTION");
        actionCreateVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreateVoucher.LONG_DESCRIPTION");
        actionCreateVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreateVoucher.NAME");
        actionCreateVoucher.putValue(ItemAction.NAME, _tempStr);
         this.actionCreateVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewRrcdsOfLendAndRepay
        this.actionViewRrcdsOfLendAndRepay = new ActionViewRrcdsOfLendAndRepay(this);
        getActionManager().registerAction("actionViewRrcdsOfLendAndRepay", actionViewRrcdsOfLendAndRepay);
         this.actionViewRrcdsOfLendAndRepay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer19 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer20 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer21 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer22 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer23 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer24 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer25 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ctnEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.mark = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer26 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer27 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer28 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDCheckAmountControl = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.bizPromptApplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptPosition = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboPrior = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.bizPromptCurrencyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptCostedDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.FormattedTextFieldAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.bizPromptSupportedObj = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptExpenseType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateBillDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptBiller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateReqDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.bizPromptPayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptUpdator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateUpdateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTextAreaCause = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDTextAreaDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.dateYjBack = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.FormattedTextFieldAmountApproved = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmountUsed = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmountBalance = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.bizPromptApplierCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnViewRcrdsOfLendAndRepay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewRcds = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewBudget = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.kDLabelContainer19.setName("kDLabelContainer19");
        this.kDLabelContainer20.setName("kDLabelContainer20");
        this.kDLabelContainer21.setName("kDLabelContainer21");
        this.kDLabelContainer22.setName("kDLabelContainer22");
        this.kDLabelContainer23.setName("kDLabelContainer23");
        this.kDLabelContainer24.setName("kDLabelContainer24");
        this.kDLabelContainer25.setName("kDLabelContainer25");
        this.ctnEntry.setName("ctnEntry");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.mark.setName("mark");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.kDLabelContainer26.setName("kDLabelContainer26");
        this.kDLabelContainer27.setName("kDLabelContainer27");
        this.kDLabelContainer28.setName("kDLabelContainer28");
        this.kDCheckAmountControl.setName("kDCheckAmountControl");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.bizPromptApplier.setName("bizPromptApplier");
        this.bizPromptOrgUnit.setName("bizPromptOrgUnit");
        this.bizPromptPosition.setName("bizPromptPosition");
        this.bizPromptCompany.setName("bizPromptCompany");
        this.comboPrior.setName("comboPrior");
        this.bizPromptCurrencyType.setName("bizPromptCurrencyType");
        this.bizPromptCostedDept.setName("bizPromptCostedDept");
        this.FormattedTextFieldAmount.setName("FormattedTextFieldAmount");
        this.bizPromptSupportedObj.setName("bizPromptSupportedObj");
        this.bizPromptExpenseType.setName("bizPromptExpenseType");
        this.dateBillDate.setName("dateBillDate");
        this.bizPromptBiller.setName("bizPromptBiller");
        this.dateReqDate.setName("dateReqDate");
        this.txtTel.setName("txtTel");
        this.bizPromptPayType.setName("bizPromptPayType");
        this.bizPromptAuditor.setName("bizPromptAuditor");
        this.dateAuditDate.setName("dateAuditDate");
        this.bizPromptUpdator.setName("bizPromptUpdator");
        this.dateUpdateDate.setName("dateUpdateDate");
        this.kdtEntries.setName("kdtEntries");
        this.kDTextAreaCause.setName("kDTextAreaCause");
        this.kDTextAreaDescription.setName("kDTextAreaDescription");
        this.dateYjBack.setName("dateYjBack");
        this.FormattedTextFieldAmountApproved.setName("FormattedTextFieldAmountApproved");
        this.txtAmountUsed.setName("txtAmountUsed");
        this.txtAmountBalance.setName("txtAmountBalance");
        this.bizPromptApplierCompany.setName("bizPromptApplierCompany");
        this.btnViewRcrdsOfLendAndRepay.setName("btnViewRcrdsOfLendAndRepay");
        this.menuItemViewRcds.setName("menuItemViewRcds");
        this.menuItemViewBudget.setName("menuItemViewBudget");
        // CoreUI		
        this.setPreferredSize(new Dimension(800,600));		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnWFViewSubmitProccess.setVisible(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW9.setVisible(false);		
        this.separatorFW7.setVisible(false);		
        this.btnCreateTo.setText(resHelper.getString("btnCreateTo.text"));		
        this.menuItemCreateTo.setText(resHelper.getString("menuItemCreateTo.text"));		
        this.btnCreatePayNotifyBill.setVisible(false);		
        this.btnCreateVoucher.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelAlignment(7);		
        this.kDLabelContainer5.setVisible(true);		
        this.kDLabelContainer5.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelLength(100);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(60);		
        this.kDLabelContainer15.setVisible(true);		
        this.kDLabelContainer15.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);		
        this.kDLabelContainer16.setEnabled(false);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);		
        this.kDLabelContainer17.setEnabled(false);
        // kDLabelContainer19		
        this.kDLabelContainer19.setBoundLabelText(resHelper.getString("kDLabelContainer19.boundLabelText"));		
        this.kDLabelContainer19.setBoundLabelLength(100);		
        this.kDLabelContainer19.setBoundLabelUnderline(true);
        // kDLabelContainer20		
        this.kDLabelContainer20.setBoundLabelText(resHelper.getString("kDLabelContainer20.boundLabelText"));		
        this.kDLabelContainer20.setBoundLabelLength(100);		
        this.kDLabelContainer20.setBoundLabelUnderline(true);
        // kDLabelContainer21		
        this.kDLabelContainer21.setBoundLabelText(resHelper.getString("kDLabelContainer21.boundLabelText"));		
        this.kDLabelContainer21.setBoundLabelLength(100);		
        this.kDLabelContainer21.setBoundLabelUnderline(true);
        // kDLabelContainer22		
        this.kDLabelContainer22.setBoundLabelText(resHelper.getString("kDLabelContainer22.boundLabelText"));		
        this.kDLabelContainer22.setBoundLabelUnderline(true);		
        this.kDLabelContainer22.setBoundLabelLength(100);		
        this.kDLabelContainer22.setEnabled(false);
        // kDLabelContainer23		
        this.kDLabelContainer23.setBoundLabelText(resHelper.getString("kDLabelContainer23.boundLabelText"));		
        this.kDLabelContainer23.setBoundLabelUnderline(true);		
        this.kDLabelContainer23.setBoundLabelLength(100);		
        this.kDLabelContainer23.setEnabled(false);
        // kDLabelContainer24		
        this.kDLabelContainer24.setBoundLabelText(resHelper.getString("kDLabelContainer24.boundLabelText"));		
        this.kDLabelContainer24.setBoundLabelLength(100);		
        this.kDLabelContainer24.setBoundLabelUnderline(true);		
        this.kDLabelContainer24.setEnabled(false);
        // kDLabelContainer25		
        this.kDLabelContainer25.setBoundLabelText(resHelper.getString("kDLabelContainer25.boundLabelText"));		
        this.kDLabelContainer25.setBoundLabelUnderline(true);		
        this.kDLabelContainer25.setBoundLabelLength(100);		
        this.kDLabelContainer25.setEnabled(false);
        // ctnEntry		
        this.ctnEntry.setTitle(resHelper.getString("ctnEntry.title"));		
        this.ctnEntry.setTitleStyle(2);		
        this.ctnEntry.setEnableActive(false);
        // kDScrollPane1
        // kDScrollPane2
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // mark		
        this.mark.setText(resHelper.getString("mark.text"));
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(60);
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer18.setBoundLabelUnderline(true);
        // kDLabelContainer26		
        this.kDLabelContainer26.setBoundLabelText(resHelper.getString("kDLabelContainer26.boundLabelText"));		
        this.kDLabelContainer26.setBoundLabelUnderline(true);		
        this.kDLabelContainer26.setBoundLabelLength(100);
        // kDLabelContainer27		
        this.kDLabelContainer27.setBoundLabelText(resHelper.getString("kDLabelContainer27.boundLabelText"));		
        this.kDLabelContainer27.setBoundLabelLength(100);		
        this.kDLabelContainer27.setBoundLabelUnderline(true);
        // kDLabelContainer28		
        this.kDLabelContainer28.setBoundLabelText(resHelper.getString("kDLabelContainer28.boundLabelText"));		
        this.kDLabelContainer28.setBoundLabelLength(100);		
        this.kDLabelContainer28.setBoundLabelUnderline(true);
        // kDCheckAmountControl		
        this.kDCheckAmountControl.setText(resHelper.getString("kDCheckAmountControl.text"));
        // txtName		
        this.txtName.setMaxLength(80);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(60);
        // bizPromptApplier		
        this.bizPromptApplier.setCommitFormat("$number$");		
        this.bizPromptApplier.setDisplayFormat("$name$");		
        this.bizPromptApplier.setEditFormat("$number$");		
        this.bizPromptApplier.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.bizPromptApplier.setEditable(true);
        this.bizPromptApplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    bizPromptApplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // bizPromptOrgUnit		
        this.bizPromptOrgUnit.setDisplayFormat("$name$");		
        this.bizPromptOrgUnit.setEditFormat("$number$");		
        this.bizPromptOrgUnit.setCommitFormat("$number$");		
        this.bizPromptOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.bizPromptOrgUnit.setEditable(true);
        // bizPromptPosition		
        this.bizPromptPosition.setCommitFormat("$number$");		
        this.bizPromptPosition.setDisplayFormat("$name$");		
        this.bizPromptPosition.setEditFormat("$number$");		
        this.bizPromptPosition.setEditable(true);		
        this.bizPromptPosition.setVisible(true);		
        this.bizPromptPosition.setEnabled(true);		
        this.bizPromptPosition.setForeground(new java.awt.Color(0,0,0));		
        this.bizPromptPosition.setRequired(false);
        // bizPromptCompany		
        this.bizPromptCompany.setDisplayFormat("$name$");		
        this.bizPromptCompany.setEditFormat("$number$");		
        this.bizPromptCompany.setCommitFormat("$number$");		
        this.bizPromptCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.bizPromptCompany.setEditable(true);
        // comboPrior		
        this.comboPrior.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.PriorEnum").toArray());		
        this.comboPrior.setRequired(true);
        // bizPromptCurrencyType		
        this.bizPromptCurrencyType.setDisplayFormat("$name$");		
        this.bizPromptCurrencyType.setEditFormat("$number$");		
        this.bizPromptCurrencyType.setCommitFormat("$number$");		
        this.bizPromptCurrencyType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.bizPromptCurrencyType.setEditable(true);
        // bizPromptCostedDept		
        this.bizPromptCostedDept.setCommitFormat("$number$");		
        this.bizPromptCostedDept.setDisplayFormat("$name$");		
        this.bizPromptCostedDept.setEditFormat("$number$");		
        this.bizPromptCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery4AsstAcct");		
        this.bizPromptCostedDept.setEditable(true);
        // FormattedTextFieldAmount		
        this.FormattedTextFieldAmount.setText(resHelper.getString("FormattedTextFieldAmount.text"));		
        this.FormattedTextFieldAmount.setEnabled(false);		
        this.FormattedTextFieldAmount.setRequired(true);
        // bizPromptSupportedObj		
        this.bizPromptSupportedObj.setDisplayFormat("$name$");		
        this.bizPromptSupportedObj.setEditFormat("$number$");		
        this.bizPromptSupportedObj.setCommitFormat("$number$");		
        this.bizPromptSupportedObj.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CostObjectQuery");		
        this.bizPromptSupportedObj.setEditable(true);
        // bizPromptExpenseType		
        this.bizPromptExpenseType.setDisplayFormat("$name$");		
        this.bizPromptExpenseType.setEditFormat("$number$");		
        this.bizPromptExpenseType.setCommitFormat("$id$");		
        this.bizPromptExpenseType.setQueryInfo("com.kingdee.eas.cp.bc.app.F7OperationTypeQuery");
        // dateBillDate		
        this.dateBillDate.setEnabled(false);
        // bizPromptBiller		
        this.bizPromptBiller.setEnabled(false);		
        this.bizPromptBiller.setEditable(true);		
        this.bizPromptBiller.setDisplayFormat("$name$");
        // dateReqDate
        // txtTel		
        this.txtTel.setText(resHelper.getString("txtTel.text"));		
        this.txtTel.setMaxLength(50);
        // bizPromptPayType
        // bizPromptAuditor		
        this.bizPromptAuditor.setEnabled(false);		
        this.bizPromptAuditor.setDisplayFormat("$name$");
        // dateAuditDate		
        this.dateAuditDate.setEnabled(false);
        // bizPromptUpdator		
        this.bizPromptUpdator.setEnabled(false);		
        this.bizPromptUpdator.setDisplayFormat("$name$");
        // dateUpdateDate		
        this.dateUpdateDate.setEnabled(false);
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>%r-[=]{#,##0.00}.2f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"expensetype\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" /><t:Column t:key=\"purpose\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" /><t:Column t:key=\"particiants\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"budgetamount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"amount\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"approvedamount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"amountUsed\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"amountBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol8\" /><t:Column t:key=\"flag\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol9\" /><t:Column t:key=\"comment\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{expensetype}</t:Cell><t:Cell>$Resource{purpose}</t:Cell><t:Cell>$Resource{particiants}</t:Cell><t:Cell>$Resource{budgetamount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{approvedamount}</t:Cell><t:Cell>$Resource{amountUsed}</t:Cell><t:Cell>$Resource{amountBalance}</t:Cell><t:Cell>$Resource{flag}</t:Cell><t:Cell>$Resource{comment}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));
        this.kdtEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntries_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntries.putBindContents("editData",new String[] {"id","expenseType","purpose","participants","budgetAmount","amount","amountApproved","amountUsed","amountBalance","","comment"});


        this.kdtEntries.checkParsed();
        final KDBizPromptBox kdtEntries_expensetype_PromptBox = new KDBizPromptBox();
        kdtEntries_expensetype_PromptBox.setQueryInfo("com.kingdee.eas.cp.bc.app.ExpenseTypeQuery");
        kdtEntries_expensetype_PromptBox.setVisible(true);
        kdtEntries_expensetype_PromptBox.setEditable(true);
        kdtEntries_expensetype_PromptBox.setDisplayFormat("$number$");
        kdtEntries_expensetype_PromptBox.setEditFormat("$number$");
        kdtEntries_expensetype_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_expensetype_CellEditor = new KDTDefaultCellEditor(kdtEntries_expensetype_PromptBox);
        this.kdtEntries.getColumn("expensetype").setEditor(kdtEntries_expensetype_CellEditor);
        ObjectValueRender kdtEntries_expensetype_OVR = new ObjectValueRender();
        kdtEntries_expensetype_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("expensetype").setRenderer(kdtEntries_expensetype_OVR);
        // kDTextAreaCause		
        this.kDTextAreaCause.setText(resHelper.getString("kDTextAreaCause.text"));		
        this.kDTextAreaCause.setMaxLength(400);
        // kDTextAreaDescription		
        this.kDTextAreaDescription.setText(resHelper.getString("kDTextAreaDescription.text"));		
        this.kDTextAreaDescription.setMaxLength(1000);
        // dateYjBack
        this.dateYjBack.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    dateYjBack_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // FormattedTextFieldAmountApproved		
        this.FormattedTextFieldAmountApproved.setEnabled(false);
        // txtAmountUsed
        this.txtAmountUsed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtAmountUsed_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtAmountBalance
        // bizPromptApplierCompany		
        this.bizPromptApplierCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.bizPromptApplierCompany.setRequired(true);
        // btnViewRcrdsOfLendAndRepay
        this.btnViewRcrdsOfLendAndRepay.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewRcrdsOfLendAndRepay.setText(resHelper.getString("btnViewRcrdsOfLendAndRepay.text"));		
        this.btnViewRcrdsOfLendAndRepay.setEnabled(false);		
        this.btnViewRcrdsOfLendAndRepay.setVisible(false);		
        this.btnViewRcrdsOfLendAndRepay.setToolTipText(resHelper.getString("btnViewRcrdsOfLendAndRepay.toolTipText"));
        // menuItemViewRcds
        this.menuItemViewRcds.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewRcds.setText(resHelper.getString("menuItemViewRcds.text"));		
        this.menuItemViewRcds.setMnemonic(82);
        // menuItemViewBudget
        this.menuItemViewBudget.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBudgetBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBudget.setText(resHelper.getString("menuItemViewBudget.text"));		
        this.menuItemViewBudget.setMnemonic(66);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 800, 600));
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 510, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 10, 510, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(10, 32, 240, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(10, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(550, 10, 240, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(550, 10, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(550, 32, 240, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(550, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer5.setBounds(new Rectangle(10, 76, 240, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(10, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(280, 76, 240, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(280, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(10, 54, 240, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(10, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(10, 98, 240, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(10, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer9.setBounds(new Rectangle(280, 54, 240, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(280, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer11.setBounds(new Rectangle(550, 120, 240, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(550, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer12.setBounds(new Rectangle(550, 76, 240, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(550, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer13.setBounds(new Rectangle(280, 120, 240, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(280, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer15.setBounds(new Rectangle(13, 460, 270, 19));
        this.add(kDLabelContainer15, new KDLayout.Constraints(13, 460, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer16.setBounds(new Rectangle(550, 568, 240, 19));
        this.add(kDLabelContainer16, new KDLayout.Constraints(550, 568, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer17.setBounds(new Rectangle(550, 544, 240, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(550, 544, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer19.setBounds(new Rectangle(280, 32, 240, 19));
        this.add(kDLabelContainer19, new KDLayout.Constraints(280, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer20.setBounds(new Rectangle(10, 120, 240, 19));
        this.add(kDLabelContainer20, new KDLayout.Constraints(10, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer21.setBounds(new Rectangle(280, 98, 240, 19));
        this.add(kDLabelContainer21, new KDLayout.Constraints(280, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer22.setBounds(new Rectangle(10, 544, 240, 19));
        this.add(kDLabelContainer22, new KDLayout.Constraints(10, 544, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer23.setBounds(new Rectangle(10, 568, 240, 19));
        this.add(kDLabelContainer23, new KDLayout.Constraints(10, 568, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer24.setBounds(new Rectangle(280, 544, 240, 19));
        this.add(kDLabelContainer24, new KDLayout.Constraints(280, 544, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer25.setBounds(new Rectangle(280, 568, 240, 19));
        this.add(kDLabelContainer25, new KDLayout.Constraints(280, 568, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        ctnEntry.setBounds(new Rectangle(10, 263, 780, 195));
        this.add(ctnEntry, new KDLayout.Constraints(10, 263, 780, 195, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(9, 210, 780, 45));
        this.add(kDScrollPane1, new KDLayout.Constraints(9, 210, 780, 45, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane2.setBounds(new Rectangle(10, 480, 780, 60));
        this.add(kDScrollPane2, new KDLayout.Constraints(10, 480, 780, 60, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer10.setBounds(new Rectangle(550, 98, 240, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(550, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        mark.setBounds(new Rectangle(63, 188, 718, 19));
        this.add(mark, new KDLayout.Constraints(63, 188, 718, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer14.setBounds(new Rectangle(11, 187, 280, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(11, 187, 280, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer18.setBounds(new Rectangle(550, 142, 240, 19));
        this.add(kDLabelContainer18, new KDLayout.Constraints(550, 142, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer26.setBounds(new Rectangle(280, 142, 240, 19));
        this.add(kDLabelContainer26, new KDLayout.Constraints(280, 142, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer27.setBounds(new Rectangle(280, 164, 240, 19));
        this.add(kDLabelContainer27, new KDLayout.Constraints(280, 164, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer28.setBounds(new Rectangle(550, 54, 240, 19));
        this.add(kDLabelContainer28, new KDLayout.Constraints(550, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDCheckAmountControl.setBounds(new Rectangle(10, 142, 140, 19));
        this.add(kDCheckAmountControl, new KDLayout.Constraints(10, 142, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtName);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(bizPromptApplier);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(bizPromptOrgUnit);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(bizPromptPosition);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(bizPromptCompany);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(comboPrior);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(bizPromptCurrencyType);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(bizPromptCostedDept);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(FormattedTextFieldAmount);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(bizPromptSupportedObj);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(bizPromptExpenseType);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(dateBillDate);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(bizPromptBiller);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(dateReqDate);
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(txtTel);
        //kDLabelContainer21
        kDLabelContainer21.setBoundEditor(bizPromptPayType);
        //kDLabelContainer22
        kDLabelContainer22.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer23
        kDLabelContainer23.setBoundEditor(dateAuditDate);
        //kDLabelContainer24
        kDLabelContainer24.setBoundEditor(bizPromptUpdator);
        //kDLabelContainer25
        kDLabelContainer25.setBoundEditor(dateUpdateDate);
        //ctnEntry
ctnEntry.getContentPane().setLayout(new BorderLayout(0, 0));        ctnEntry.getContentPane().add(kdtEntries, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDTextAreaCause, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(kDTextAreaDescription, null);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(dateYjBack);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(FormattedTextFieldAmountApproved);
        //kDLabelContainer26
        kDLabelContainer26.setBoundEditor(txtAmountUsed);
        //kDLabelContainer27
        kDLabelContainer27.setBoundEditor(txtAmountBalance);
        //kDLabelContainer28
        kDLabelContainer28.setBoundEditor(bizPromptApplierCompany);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        this.menuBar.add(kDSeparator6);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(menuItemUdateReqDate);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator5);
        menuView.add(menuItemLocate);
        menuView.add(menuItemViewRcds);
        menuView.add(kDSeparator7);
        menuView.add(menuItemViewBudget);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(MenuItemSuspenseAcc);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnSuspenseAcc);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW10);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnViewBudgetBalance);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnViewRcrdsOfLendAndRepay);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateVoucher);
        this.toolBar.add(btnCreatePayNotifyBill);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW6);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("applier", com.kingdee.eas.basedata.person.PersonInfo.class, this.bizPromptApplier, "data");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.bizPromptOrgUnit, "data");
		dataBinder.registerBinding("position", com.kingdee.eas.basedata.org.PositionInfo.class, this.bizPromptPosition, "data");
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.bizPromptCompany, "data");
		dataBinder.registerBinding("prior", com.kingdee.eas.cp.bc.PriorEnum.class, this.comboPrior, "selectedItem");
		dataBinder.registerBinding("currencyType", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.bizPromptCurrencyType, "data");
		dataBinder.registerBinding("costedDept", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.bizPromptCostedDept, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.FormattedTextFieldAmount, "value");
		dataBinder.registerBinding("supportedObj", com.kingdee.eas.basedata.assistant.CostObjectInfo.class, this.bizPromptSupportedObj, "data");
		dataBinder.registerBinding("operationType", com.kingdee.eas.cp.bc.OperationTypeInfo.class, this.bizPromptExpenseType, "data");
		dataBinder.registerBinding("billDate", java.util.Date.class, this.dateBillDate, "value");
		dataBinder.registerBinding("biller", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptBiller, "data");
		dataBinder.registerBinding("bizReqDate", java.util.Date.class, this.dateReqDate, "value");
		dataBinder.registerBinding("tel", String.class, this.txtTel, "text");
		dataBinder.registerBinding("payMode", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.bizPromptPayType, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.dateAuditDate, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptUpdator, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.dateUpdateDate, "value");
		dataBinder.registerBinding("entries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntries, "id.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.cp.bc.DailyLoanBillEntryInfo.class, this.kdtEntries, "userObject");
		dataBinder.registerBinding("entries.purpose", String.class, this.kdtEntries, "purpose.text");
		dataBinder.registerBinding("entries.amount", java.math.BigDecimal.class, this.kdtEntries, "amount.text");
		dataBinder.registerBinding("entries.comment", String.class, this.kdtEntries, "comment.text");
		dataBinder.registerBinding("entries.participants", String.class, this.kdtEntries, "particiants.text");
		dataBinder.registerBinding("entries.budgetAmount", java.math.BigDecimal.class, this.kdtEntries, "budgetamount.text");
		dataBinder.registerBinding("entries.amountApproved", java.math.BigDecimal.class, this.kdtEntries, "approvedamount.text");
		dataBinder.registerBinding("entries.expenseType", com.kingdee.eas.cp.bc.ExpenseTypeInfo.class, this.kdtEntries, "expensetype.text");
		dataBinder.registerBinding("entries.amountUsed", java.math.BigDecimal.class, this.kdtEntries, "amountUsed.text");
		dataBinder.registerBinding("entries.amountBalance", java.math.BigDecimal.class, this.kdtEntries, "amountBalance.text");
		dataBinder.registerBinding("cause", String.class, this.kDTextAreaCause, "text");
		dataBinder.registerBinding("description", String.class, this.kDTextAreaDescription, "text");
		dataBinder.registerBinding("foreseeDate", java.util.Date.class, this.dateYjBack, "value");
		dataBinder.registerBinding("amountApproved", java.math.BigDecimal.class, this.FormattedTextFieldAmountApproved, "value");
		dataBinder.registerBinding("amountUsed", java.math.BigDecimal.class, this.txtAmountUsed, "value");
		dataBinder.registerBinding("amountBalance", java.math.BigDecimal.class, this.txtAmountBalance, "value");
		dataBinder.registerBinding("applierCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.bizPromptApplierCompany, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.cp.bc.app.DailyLoanBillEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.cp.bc.DailyLoanBillInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Company",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Company");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Company");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????��??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prior", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supportedObj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("operationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("biller", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizReqDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.purpose", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.participants", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.budgetAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.expenseType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountUsed", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("foreseeDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountUsed", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierCompany", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output bizPromptApplier_dataChanged method
     */
    protected void bizPromptApplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_editStopped method
     */
    protected void kdtEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output dateYjBack_dataChanged method
     */
    protected void dateYjBack_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtAmountUsed_actionPerformed method
     */
    protected void txtAmountUsed_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("applier.*"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("position.*"));
        sic.add(new SelectorItemInfo("company.*"));
        sic.add(new SelectorItemInfo("prior"));
        sic.add(new SelectorItemInfo("currencyType.*"));
        sic.add(new SelectorItemInfo("costedDept.*"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("supportedObj.*"));
        sic.add(new SelectorItemInfo("operationType.*"));
        sic.add(new SelectorItemInfo("billDate"));
        sic.add(new SelectorItemInfo("biller.*"));
        sic.add(new SelectorItemInfo("bizReqDate"));
        sic.add(new SelectorItemInfo("tel"));
        sic.add(new SelectorItemInfo("payMode.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
    sic.add(new SelectorItemInfo("entries.id"));
        sic.add(new SelectorItemInfo("entries.*"));
//        sic.add(new SelectorItemInfo("entries.number"));
    sic.add(new SelectorItemInfo("entries.purpose"));
    sic.add(new SelectorItemInfo("entries.amount"));
    sic.add(new SelectorItemInfo("entries.comment"));
    sic.add(new SelectorItemInfo("entries.participants"));
    sic.add(new SelectorItemInfo("entries.budgetAmount"));
    sic.add(new SelectorItemInfo("entries.amountApproved"));
        sic.add(new SelectorItemInfo("entries.expenseType.*"));
//        sic.add(new SelectorItemInfo("entries.expenseType.number"));
    sic.add(new SelectorItemInfo("entries.amountUsed"));
    sic.add(new SelectorItemInfo("entries.amountBalance"));
        sic.add(new SelectorItemInfo("cause"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("foreseeDate"));
        sic.add(new SelectorItemInfo("amountApproved"));
        sic.add(new SelectorItemInfo("amountUsed"));
        sic.add(new SelectorItemInfo("amountBalance"));
        sic.add(new SelectorItemInfo("applierCompany.*"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionTraceDown_actionPerformed method
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }
    	

    /**
     * output actionViewBudgetBalance_actionPerformed method
     */
    public void actionViewBudgetBalance_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewBudgetBalance_actionPerformed(e);
    }
    	

    /**
     * output actionCreatePayNotifyBill_actionPerformed method
     */
    public void actionCreatePayNotifyBill_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreatePayNotifyBill_actionPerformed(e);
    }
    	

    /**
     * output actionCreateVoucher_actionPerformed method
     */
    public void actionCreateVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateVoucher_actionPerformed(e);
    }
    	

    /**
     * output actionViewRrcdsOfLendAndRepay_actionPerformed method
     */
    public void actionViewRrcdsOfLendAndRepay_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionTraceDown(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionTraceDown(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTraceDown() {
    	return false;
    }
	public RequestContext prepareActionViewBudgetBalance(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionViewBudgetBalance(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewBudgetBalance() {
    	return false;
    }
	public RequestContext prepareActionCreatePayNotifyBill(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCreatePayNotifyBill(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreatePayNotifyBill() {
    	return false;
    }
	public RequestContext prepareActionCreateVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCreateVoucher(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateVoucher() {
    	return false;
    }
	public RequestContext prepareActionViewRrcdsOfLendAndRepay(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewRrcdsOfLendAndRepay() {
    	return false;
    }

    /**
     * output ActionViewRrcdsOfLendAndRepay class
     */     
    protected class ActionViewRrcdsOfLendAndRepay extends ItemAction {     
    
        public ActionViewRrcdsOfLendAndRepay()
        {
            this(null);
        }

        public ActionViewRrcdsOfLendAndRepay(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl R"));
            _tempStr = resHelper.getString("ActionViewRrcdsOfLendAndRepay.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewRrcdsOfLendAndRepay.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewRrcdsOfLendAndRepay.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDailyLoanBillEditUI.this, "ActionViewRrcdsOfLendAndRepay", "actionViewRrcdsOfLendAndRepay_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.cp.bc.client", "DailyLoanBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.cp.bc.client.DailyLoanBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.DailyLoanBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.DailyLoanBillInfo objectValue = new com.kingdee.eas.cp.bc.DailyLoanBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/cp/bc/DailyLoanBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.cp.bc.app.DailyLoanBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntries;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
				vo.put("amount",new java.math.BigDecimal(0.00));
		vo.put("amountApproved",new java.math.BigDecimal(0.0));
		vo.put("amountUsed",new java.math.BigDecimal(0.0));
		vo.put("amountBalance",new java.math.BigDecimal(0.0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}