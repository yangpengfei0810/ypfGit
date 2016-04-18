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
public abstract class AbstractEvectionReqBillEditUI extends com.kingdee.eas.cp.bc.client.BizCollCoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEvectionReqBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer19;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer20;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPaneEntries;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer22;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer23;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer24;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer25;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer26;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer27;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane attachmentScrollPanel;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnReqEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer28;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isDispatch;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptApplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptPosition;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCompany;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPrior;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField formattedTextFieldIntendingDays;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField numberTextFieldTotalPeople;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCostedDept;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField formattedTextFieldAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCurrencyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptSupportedObj;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptExpenseType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateBillDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptBiller;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioButtonNeedLoan;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioButtonTogetherAudit;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioButtonEachAudit;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup buttonGroupAudit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox ckNeedLoan;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnLoanEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable loanEntries;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateUpdateDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptUpdator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateReqDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel attachmentPanel;
    protected com.kingdee.bos.ctrl.swing.KDTextArea textAreaCause;
    protected com.kingdee.bos.ctrl.swing.KDTextArea textAreaDescription;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptApplierCompany;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBudgetBalance;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBudget;
    protected com.kingdee.eas.cp.bc.EvectionReqBillInfo editData = null;
    protected ActionViewBudgetBalance actionViewBudgetBalance = null;
    /**
     * output class constructor
     */
    public AbstractEvectionReqBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEvectionReqBillEditUI.class.getName());
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
        //actionAttachment
        actionAttachment.setEnabled(true);
        actionAttachment.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
        actionAttachment.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
        actionAttachment.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.NAME");
        actionAttachment.putValue(ItemAction.NAME, _tempStr);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
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
        this.actionViewBudgetBalance = new ActionViewBudgetBalance(this);
        getActionManager().registerAction("actionViewBudgetBalance", actionViewBudgetBalance);
         this.actionViewBudgetBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionViewBudgetBalance.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer19 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer20 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tabbedPaneEntries = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDLabelContainer22 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer23 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer24 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer25 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer26 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer27 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.attachmentScrollPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.ctnReqEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDLabelContainer28 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isDispatch = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.bizPromptApplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptPosition = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboPrior = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.formattedTextFieldIntendingDays = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.numberTextFieldTotalPeople = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.bizPromptCostedDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.formattedTextFieldAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.bizPromptCurrencyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptSupportedObj = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptExpenseType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateBillDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptBiller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.radioButtonNeedLoan = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioButtonTogetherAudit = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioButtonEachAudit = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.buttonGroupAudit = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.ckNeedLoan = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.ctnLoanEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.loanEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.bizPromptAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateUpdateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptUpdator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dateReqDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.attachmentPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.textAreaCause = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.textAreaDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.bizPromptApplierCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnViewBudgetBalance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewBudget = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.kDLabelContainer19.setName("kDLabelContainer19");
        this.kDLabelContainer20.setName("kDLabelContainer20");
        this.kDPanel1.setName("kDPanel1");
        this.tabbedPaneEntries.setName("tabbedPaneEntries");
        this.kDLabelContainer22.setName("kDLabelContainer22");
        this.kDLabelContainer23.setName("kDLabelContainer23");
        this.kDLabelContainer24.setName("kDLabelContainer24");
        this.kDLabelContainer25.setName("kDLabelContainer25");
        this.kDLabelContainer26.setName("kDLabelContainer26");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer27.setName("kDLabelContainer27");
        this.attachmentScrollPanel.setName("attachmentScrollPanel");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.ctnReqEntry.setName("ctnReqEntry");
        this.kDLabelContainer28.setName("kDLabelContainer28");
        this.isDispatch.setName("isDispatch");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.bizPromptApplier.setName("bizPromptApplier");
        this.bizPromptPosition.setName("bizPromptPosition");
        this.bizPromptOrgUnit.setName("bizPromptOrgUnit");
        this.bizPromptCompany.setName("bizPromptCompany");
        this.comboPrior.setName("comboPrior");
        this.formattedTextFieldIntendingDays.setName("formattedTextFieldIntendingDays");
        this.numberTextFieldTotalPeople.setName("numberTextFieldTotalPeople");
        this.bizPromptCostedDept.setName("bizPromptCostedDept");
        this.formattedTextFieldAmount.setName("formattedTextFieldAmount");
        this.bizPromptCurrencyType.setName("bizPromptCurrencyType");
        this.bizPromptSupportedObj.setName("bizPromptSupportedObj");
        this.bizPromptExpenseType.setName("bizPromptExpenseType");
        this.dateBillDate.setName("dateBillDate");
        this.bizPromptBiller.setName("bizPromptBiller");
        this.radioButtonNeedLoan.setName("radioButtonNeedLoan");
        this.radioButtonTogetherAudit.setName("radioButtonTogetherAudit");
        this.radioButtonEachAudit.setName("radioButtonEachAudit");
        this.ckNeedLoan.setName("ckNeedLoan");
        this.ctnLoanEntry.setName("ctnLoanEntry");
        this.loanEntries.setName("loanEntries");
        this.txtTel.setName("txtTel");
        this.bizPromptAuditor.setName("bizPromptAuditor");
        this.dateAuditDate.setName("dateAuditDate");
        this.dateUpdateDate.setName("dateUpdateDate");
        this.bizPromptUpdator.setName("bizPromptUpdator");
        this.dateReqDate.setName("dateReqDate");
        this.attachmentPanel.setName("attachmentPanel");
        this.textAreaCause.setName("textAreaCause");
        this.textAreaDescription.setName("textAreaDescription");
        this.kdtEntries.setName("kdtEntries");
        this.bizPromptApplierCompany.setName("bizPromptApplierCompany");
        this.btnViewBudgetBalance.setName("btnViewBudgetBalance");
        this.menuItemViewBudget.setName("menuItemViewBudget");
        // CoreUI		
        this.setPreferredSize(new Dimension(800,600));
        this.btnAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.menuBiz.setEnabled(false);		
        this.menuBiz.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW9.setVisible(false);		
        this.separatorFW7.setVisible(false);		
        this.menuItemCreateTo.setText(resHelper.getString("menuItemCreateTo.text"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
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
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelLength(100);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelLength(100);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelLength(100);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelLength(100);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelLength(100);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
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
        this.kDLabelContainer13.setBoundLabelUnderline(true);		
        this.kDLabelContainer13.setBoundLabelLength(100);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelUnderline(true);		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setEnabled(false);		
        this.kDLabelContainer14.setVisible(false);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelUnderline(true);		
        this.kDLabelContainer15.setBoundLabelLength(100);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(65);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(65);
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelUnderline(true);		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer18.setEnabled(false);
        // kDLabelContainer19		
        this.kDLabelContainer19.setBoundLabelText(resHelper.getString("kDLabelContainer19.boundLabelText"));		
        this.kDLabelContainer19.setBoundLabelUnderline(true);		
        this.kDLabelContainer19.setBoundLabelLength(100);		
        this.kDLabelContainer19.setEnabled(false);
        // kDLabelContainer20		
        this.kDLabelContainer20.setBoundLabelText(resHelper.getString("kDLabelContainer20.boundLabelText"));		
        this.kDLabelContainer20.setBoundLabelLength(20);
        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));		
        this.kDPanel1.setVisible(false);
        // tabbedPaneEntries		
        this.tabbedPaneEntries.setVisible(false);
        // kDLabelContainer22		
        this.kDLabelContainer22.setBoundLabelText(resHelper.getString("kDLabelContainer22.boundLabelText"));		
        this.kDLabelContainer22.setBoundLabelLength(100);		
        this.kDLabelContainer22.setBoundLabelUnderline(true);
        // kDLabelContainer23		
        this.kDLabelContainer23.setBoundLabelText(resHelper.getString("kDLabelContainer23.boundLabelText"));		
        this.kDLabelContainer23.setBoundLabelLength(100);		
        this.kDLabelContainer23.setBoundLabelUnderline(true);		
        this.kDLabelContainer23.setEnabled(false);
        // kDLabelContainer24		
        this.kDLabelContainer24.setBoundLabelText(resHelper.getString("kDLabelContainer24.boundLabelText"));		
        this.kDLabelContainer24.setBoundLabelLength(100);		
        this.kDLabelContainer24.setBoundLabelUnderline(true);		
        this.kDLabelContainer24.setEnabled(false);
        // kDLabelContainer25		
        this.kDLabelContainer25.setBoundLabelText(resHelper.getString("kDLabelContainer25.boundLabelText"));		
        this.kDLabelContainer25.setBoundLabelLength(100);		
        this.kDLabelContainer25.setBoundLabelUnderline(true);		
        this.kDLabelContainer25.setEnabled(false);
        // kDLabelContainer26		
        this.kDLabelContainer26.setBoundLabelText(resHelper.getString("kDLabelContainer26.boundLabelText"));		
        this.kDLabelContainer26.setBoundLabelLength(100);		
        this.kDLabelContainer26.setBoundLabelUnderline(true);		
        this.kDLabelContainer26.setEnabled(false);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer27		
        this.kDLabelContainer27.setBoundLabelText(resHelper.getString("kDLabelContainer27.boundLabelText"));		
        this.kDLabelContainer27.setBoundLabelLength(70);		
        this.kDLabelContainer27.setVisible(false);		
        this.kDLabelContainer27.setEnabled(false);
        // attachmentScrollPanel		
        this.attachmentScrollPanel.setPreferredSize(new Dimension(669,51));		
        this.attachmentScrollPanel.setBackground(new java.awt.Color(255,255,255));		
        this.attachmentScrollPanel.setVisible(false);		
        this.attachmentScrollPanel.setEnabled(false);
        // kDScrollPane1
        // kDScrollPane2
        // ctnReqEntry		
        this.ctnReqEntry.setTitleStyle(2);		
        this.ctnReqEntry.setEnableActive(false);		
        this.ctnReqEntry.setPreferredSize(new Dimension(780,178));		
        this.ctnReqEntry.setTitle(resHelper.getString("ctnReqEntry.title"));
        // kDLabelContainer28		
        this.kDLabelContainer28.setBoundLabelText(resHelper.getString("kDLabelContainer28.boundLabelText"));		
        this.kDLabelContainer28.setBoundLabelLength(100);		
        this.kDLabelContainer28.setBoundLabelUnderline(true);
        // isDispatch		
        this.isDispatch.setText(resHelper.getString("isDispatch.text"));
        this.isDispatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    isDispatch_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtName		
        this.txtName.setMaxLength(80);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(60);
        // bizPromptApplier		
        this.bizPromptApplier.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.bizPromptApplier.setCommitFormat("$number$");		
        this.bizPromptApplier.setEditFormat("$number$");		
        this.bizPromptApplier.setDisplayFormat("$name$");		
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
        // bizPromptPosition		
        this.bizPromptPosition.setDisplayFormat("$name$");		
        this.bizPromptPosition.setEditFormat("$number$");		
        this.bizPromptPosition.setCommitFormat("$number$");		
        this.bizPromptPosition.setEditable(true);
        // bizPromptOrgUnit		
        this.bizPromptOrgUnit.setDisplayFormat("$name$");		
        this.bizPromptOrgUnit.setEditFormat("$number$");		
        this.bizPromptOrgUnit.setCommitFormat("$number$");		
        this.bizPromptOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.bizPromptOrgUnit.setEditable(true);
        // bizPromptCompany		
        this.bizPromptCompany.setCommitFormat("$number$");		
        this.bizPromptCompany.setDisplayFormat("$name$");		
        this.bizPromptCompany.setEditFormat("$number$");		
        this.bizPromptCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.bizPromptCompany.setEditable(true);
        // comboPrior		
        this.comboPrior.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.PriorEnum").toArray());		
        this.comboPrior.setRequired(true);
        // formattedTextFieldIntendingDays		
        this.formattedTextFieldIntendingDays.setText(resHelper.getString("formattedTextFieldIntendingDays.text"));		
        this.formattedTextFieldIntendingDays.setRequired(true);
        // numberTextFieldTotalPeople		
        this.numberTextFieldTotalPeople.setText(resHelper.getString("numberTextFieldTotalPeople.text"));		
        this.numberTextFieldTotalPeople.setRequired(true);
        // bizPromptCostedDept		
        this.bizPromptCostedDept.setDisplayFormat("$name$");		
        this.bizPromptCostedDept.setEditFormat("$number$");		
        this.bizPromptCostedDept.setCommitFormat("$number$");		
        this.bizPromptCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery4AsstAcct");		
        this.bizPromptCostedDept.setEditable(true);
        // formattedTextFieldAmount		
        this.formattedTextFieldAmount.setText(resHelper.getString("formattedTextFieldAmount.text"));		
        this.formattedTextFieldAmount.setDataType(1);		
        this.formattedTextFieldAmount.setPrecision(2);
        // bizPromptCurrencyType		
        this.bizPromptCurrencyType.setDisplayFormat("$name$");		
        this.bizPromptCurrencyType.setEditFormat("$number$");		
        this.bizPromptCurrencyType.setCommitFormat("$number$");		
        this.bizPromptCurrencyType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.bizPromptCurrencyType.setEditable(true);
        // bizPromptSupportedObj		
        this.bizPromptSupportedObj.setCommitFormat("$number$");		
        this.bizPromptSupportedObj.setDisplayFormat("$name$");		
        this.bizPromptSupportedObj.setEditFormat("$number$");		
        this.bizPromptSupportedObj.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CostObjectQuery");		
        this.bizPromptSupportedObj.setEditable(true);
        // bizPromptExpenseType		
        this.bizPromptExpenseType.setDisplayFormat("$name$");		
        this.bizPromptExpenseType.setEditFormat("$number$");		
        this.bizPromptExpenseType.setCommitFormat("$number$");		
        this.bizPromptExpenseType.setQueryInfo("com.kingdee.eas.cp.bc.app.F7OperationTypeQuery");		
        this.bizPromptExpenseType.setEditable(true);
        // dateBillDate		
        this.dateBillDate.setEnabled(false);
        // bizPromptBiller		
        this.bizPromptBiller.setEnabled(false);		
        this.bizPromptBiller.setDisplayFormat("$name$");		
        this.bizPromptBiller.setEditFormat("$number$");		
        this.bizPromptBiller.setCommitFormat("$number$");		
        this.bizPromptBiller.setEditable(true);
        // radioButtonNeedLoan		
        this.radioButtonNeedLoan.setText(resHelper.getString("radioButtonNeedLoan.text"));		
        this.radioButtonNeedLoan.setEnabled(false);		
        this.radioButtonNeedLoan.setVisible(false);
        this.radioButtonNeedLoan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    radioButtonNeedLoan_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // radioButtonTogetherAudit		
        this.radioButtonTogetherAudit.setText(resHelper.getString("radioButtonTogetherAudit.text"));
        this.radioButtonTogetherAudit.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    radioButtonTogetherAudit_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // radioButtonEachAudit		
        this.radioButtonEachAudit.setText(resHelper.getString("radioButtonEachAudit.text"));
        // kDButtonGroup1
        // buttonGroupAudit
        // ckNeedLoan		
        this.ckNeedLoan.setText(resHelper.getString("ckNeedLoan.text"));
        this.ckNeedLoan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    ckNeedLoan_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.ckNeedLoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    ckNeedLoan_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // ctnLoanEntry		
        this.ctnLoanEntry.setEnableActive(false);		
        this.ctnLoanEntry.setGap(0);		
        this.ctnLoanEntry.setTitleStyle(2);		
        this.ctnLoanEntry.setTitle(resHelper.getString("ctnLoanEntry.title"));
        // loanEntries		
        this.loanEntries.setFormatXml(resHelper.getString("loanEntries.formatXml"));
        this.loanEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    loanEntries_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.loanEntries.checkParsed();
        // txtTel		
        this.txtTel.setText(resHelper.getString("txtTel.text"));		
        this.txtTel.setMaxLength(50);
        // bizPromptAuditor		
        this.bizPromptAuditor.setEnabled(false);		
        this.bizPromptAuditor.setDisplayFormat("$name$");		
        this.bizPromptAuditor.setEditFormat("$number$");
        // dateAuditDate		
        this.dateAuditDate.setEnabled(false);
        // dateUpdateDate		
        this.dateUpdateDate.setEnabled(false);
        // bizPromptUpdator		
        this.bizPromptUpdator.setEnabled(false);		
        this.bizPromptUpdator.setEditFormat("$number$");		
        this.bizPromptUpdator.setDisplayFormat("$name$");
        // dateReqDate
        // attachmentPanel		
        this.attachmentPanel.setAutoscrolls(true);		
        this.attachmentPanel.setPreferredSize(new Dimension(380,200));		
        this.attachmentPanel.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));		
        this.attachmentPanel.setBackground(new java.awt.Color(255,255,255));
        // textAreaCause		
        this.textAreaCause.setText(resHelper.getString("textAreaCause.text"));		
        this.textAreaCause.setMaxLength(400);
        // textAreaDescription		
        this.textAreaDescription.setText(resHelper.getString("textAreaDescription.text"));		
        this.textAreaDescription.setMaxLength(400);
        // kdtEntries		
        this.kdtEntries.setFormatXml(resHelper.getString("kdtEntries.formatXml"));
        this.kdtEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntries_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntries.putBindContents("editData",new String[] {"id","startDate","from","endDate","to","vehicle","partner","amount","PreDays"});


        this.kdtEntries.checkParsed();
        KDComboBox kdtEntries_vehicle_ComboBox = new KDComboBox();
        kdtEntries_vehicle_ComboBox.setName("kdtEntries_vehicle_ComboBox");
        kdtEntries_vehicle_ComboBox.setVisible(true);
        kdtEntries_vehicle_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.VehicleEnum").toArray());
        KDTDefaultCellEditor kdtEntries_vehicle_CellEditor = new KDTDefaultCellEditor(kdtEntries_vehicle_ComboBox);
        this.kdtEntries.getColumn("vehicle").setEditor(kdtEntries_vehicle_CellEditor);
        KDTextField kdtEntries_partner_TextField = new KDTextField();
        kdtEntries_partner_TextField.setName("kdtEntries_partner_TextField");
        kdtEntries_partner_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntries_partner_CellEditor = new KDTDefaultCellEditor(kdtEntries_partner_TextField);
        this.kdtEntries.getColumn("partner").setEditor(kdtEntries_partner_CellEditor);
        KDFormattedTextField kdtEntries_preDays_TextField = new KDFormattedTextField();
        kdtEntries_preDays_TextField.setName("kdtEntries_preDays_TextField");
        kdtEntries_preDays_TextField.setVisible(true);
        kdtEntries_preDays_TextField.setEditable(true);
        kdtEntries_preDays_TextField.setHorizontalAlignment(2);
        kdtEntries_preDays_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntries_preDays_CellEditor = new KDTDefaultCellEditor(kdtEntries_preDays_TextField);
        this.kdtEntries.getColumn("preDays").setEditor(kdtEntries_preDays_CellEditor);
        // bizPromptApplierCompany		
        this.bizPromptApplierCompany.setRequired(true);
        // btnViewBudgetBalance
        this.btnViewBudgetBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBudgetBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBudgetBalance.setText(resHelper.getString("btnViewBudgetBalance.text"));
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
        kDLabelContainer4.setBounds(new Rectangle(10, 76, 240, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(10, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(550, 32, 240, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(550, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer6.setBounds(new Rectangle(280, 76, 240, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(280, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(10, 54, 240, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(10, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer9.setBounds(new Rectangle(10, 98, 160, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(10, 98, 160, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer10.setBounds(new Rectangle(550, 76, 240, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(550, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer11.setBounds(new Rectangle(280, 54, 240, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(280, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer12.setBounds(new Rectangle(550, 98, 240, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(550, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer13.setBounds(new Rectangle(280, 98, 240, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(280, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer14.setBounds(new Rectangle(550, 132, 240, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(550, 132, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer15.setBounds(new Rectangle(280, 120, 240, 19));
        this.add(kDLabelContainer15, new KDLayout.Constraints(280, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer16.setBounds(new Rectangle(11, 460, 270, 19));
        this.add(kDLabelContainer16, new KDLayout.Constraints(11, 460, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer17.setBounds(new Rectangle(10, 147, 241, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(10, 147, 241, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer18.setBounds(new Rectangle(549, 567, 240, 19));
        this.add(kDLabelContainer18, new KDLayout.Constraints(549, 567, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer19.setBounds(new Rectangle(549, 543, 240, 19));
        this.add(kDLabelContainer19, new KDLayout.Constraints(549, 543, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer20.setBounds(new Rectangle(178, 98, 30, 19));
        this.add(kDLabelContainer20, new KDLayout.Constraints(178, 98, 30, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel1.setBounds(new Rectangle(787, 210, 8, 10));
        this.add(kDPanel1, new KDLayout.Constraints(787, 210, 8, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        tabbedPaneEntries.setBounds(new Rectangle(757, 143, 19, 11));
        this.add(tabbedPaneEntries, new KDLayout.Constraints(757, 143, 19, 11, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer22.setBounds(new Rectangle(10, 120, 240, 19));
        this.add(kDLabelContainer22, new KDLayout.Constraints(10, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer23.setBounds(new Rectangle(10, 543, 240, 19));
        this.add(kDLabelContainer23, new KDLayout.Constraints(10, 543, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer24.setBounds(new Rectangle(10, 567, 240, 19));
        this.add(kDLabelContainer24, new KDLayout.Constraints(10, 567, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer25.setBounds(new Rectangle(279, 567, 240, 19));
        this.add(kDLabelContainer25, new KDLayout.Constraints(279, 567, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer26.setBounds(new Rectangle(279, 543, 240, 19));
        this.add(kDLabelContainer26, new KDLayout.Constraints(279, 543, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(280, 32, 240, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(280, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer27.setBounds(new Rectangle(380, 460, 406, 20));
        this.add(kDLabelContainer27, new KDLayout.Constraints(380, 460, 406, 20, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        attachmentScrollPanel.setBounds(new Rectangle(376, 480, 410, 60));
        this.add(attachmentScrollPanel, new KDLayout.Constraints(376, 480, 410, 60, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(10, 170, 780, 56));
        this.add(kDScrollPane1, new KDLayout.Constraints(10, 170, 780, 56, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane2.setBounds(new Rectangle(10, 480, 780, 60));
        this.add(kDScrollPane2, new KDLayout.Constraints(10, 480, 780, 60, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        ctnReqEntry.setBounds(new Rectangle(10, 231, 780, 227));
        this.add(ctnReqEntry, new KDLayout.Constraints(10, 231, 780, 227, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer28.setBounds(new Rectangle(550, 54, 240, 19));
        this.add(kDLabelContainer28, new KDLayout.Constraints(550, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        isDispatch.setBounds(new Rectangle(550, 120, 177, 19));
        this.add(isDispatch, new KDLayout.Constraints(550, 120, 177, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtName);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(bizPromptApplier);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(bizPromptPosition);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(bizPromptOrgUnit);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(bizPromptCompany);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(comboPrior);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(formattedTextFieldIntendingDays);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(numberTextFieldTotalPeople);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(bizPromptCostedDept);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(formattedTextFieldAmount);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(bizPromptCurrencyType);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(bizPromptSupportedObj);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(bizPromptExpenseType);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(dateBillDate);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(bizPromptBiller);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(787, 210, 8, 10));        radioButtonNeedLoan.setBounds(new Rectangle(167, 28, 140, 19));
        kDPanel1.add(radioButtonNeedLoan, new KDLayout.Constraints(167, 28, 140, 19, 0));
        radioButtonTogetherAudit.setBounds(new Rectangle(225, 11, 148, 19));
        kDPanel1.add(radioButtonTogetherAudit, new KDLayout.Constraints(225, 11, 148, 19, 0));
        radioButtonEachAudit.setBounds(new Rectangle(378, 11, 150, 19));
        kDPanel1.add(radioButtonEachAudit, new KDLayout.Constraints(378, 11, 150, 19, 0));
        ckNeedLoan.setBounds(new Rectangle(21, 11, 140, 19));
        kDPanel1.add(ckNeedLoan, new KDLayout.Constraints(21, 11, 140, 19, 0));
        //tabbedPaneEntries
        tabbedPaneEntries.add(ctnLoanEntry, resHelper.getString("ctnLoanEntry.constraints"));
        //ctnLoanEntry
ctnLoanEntry.getContentPane().setLayout(new BorderLayout(0, 0));        ctnLoanEntry.getContentPane().add(loanEntries, BorderLayout.CENTER);
        //kDLabelContainer22
        kDLabelContainer22.setBoundEditor(txtTel);
        //kDLabelContainer23
        kDLabelContainer23.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer24
        kDLabelContainer24.setBoundEditor(dateAuditDate);
        //kDLabelContainer25
        kDLabelContainer25.setBoundEditor(dateUpdateDate);
        //kDLabelContainer26
        kDLabelContainer26.setBoundEditor(bizPromptUpdator);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(dateReqDate);
        //attachmentScrollPanel
        attachmentScrollPanel.getViewport().add(attachmentPanel, null);
        attachmentPanel.setLayout(new KDLayout());
        attachmentPanel.putClientProperty("OriginalBounds", new Rectangle(10, 10, 380, 200));        //kDScrollPane1
        kDScrollPane1.getViewport().add(textAreaCause, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(textAreaDescription, null);
        //ctnReqEntry
ctnReqEntry.getContentPane().setLayout(new BorderLayout(0, 0));        ctnReqEntry.getContentPane().add(kdtEntries, BorderLayout.CENTER);
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
        menuFile.add(kDSeparator6);
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
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancel);
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
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnViewBudgetBalance);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnSuspenseAcc);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW6);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isDispatch", boolean.class, this.isDispatch, "selected");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("applier", com.kingdee.eas.basedata.person.PersonInfo.class, this.bizPromptApplier, "data");
		dataBinder.registerBinding("position", com.kingdee.eas.basedata.org.PositionInfo.class, this.bizPromptPosition, "data");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.bizPromptOrgUnit, "data");
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.bizPromptCompany, "data");
		dataBinder.registerBinding("prior", com.kingdee.eas.cp.bc.PriorEnum.class, this.comboPrior, "selectedItem");
		dataBinder.registerBinding("intendingDays", java.math.BigDecimal.class, this.formattedTextFieldIntendingDays, "value");
		dataBinder.registerBinding("totalPeople", int.class, this.numberTextFieldTotalPeople, "text");
		dataBinder.registerBinding("costedDept", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.bizPromptCostedDept, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.formattedTextFieldAmount, "value");
		dataBinder.registerBinding("currencyType", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.bizPromptCurrencyType, "data");
		dataBinder.registerBinding("supportedObj", com.kingdee.eas.basedata.assistant.CostObjectInfo.class, this.bizPromptSupportedObj, "data");
		dataBinder.registerBinding("operationType", com.kingdee.eas.cp.bc.OperationTypeInfo.class, this.bizPromptExpenseType, "data");
		dataBinder.registerBinding("billDate", java.util.Date.class, this.dateBillDate, "value");
		dataBinder.registerBinding("biller", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptBiller, "data");
		dataBinder.registerBinding("tel", String.class, this.txtTel, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.dateAuditDate, "value");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.dateUpdateDate, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptUpdator, "data");
		dataBinder.registerBinding("bizReqDate", java.util.Date.class, this.dateReqDate, "value");
		dataBinder.registerBinding("cause", String.class, this.textAreaCause, "text");
		dataBinder.registerBinding("description", String.class, this.textAreaDescription, "text");
		dataBinder.registerBinding("entries.partner", String.class, this.kdtEntries, "partner.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.cp.bc.EvectionReqBillEntryInfo.class, this.kdtEntries, "userObject");
		dataBinder.registerBinding("entries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntries, "id.text");
		dataBinder.registerBinding("entries.startDate", java.util.Date.class, this.kdtEntries, "startDate.text");
		dataBinder.registerBinding("entries.from", String.class, this.kdtEntries, "from.text");
		dataBinder.registerBinding("entries.endDate", java.util.Date.class, this.kdtEntries, "endDate.text");
		dataBinder.registerBinding("entries.to", String.class, this.kdtEntries, "to.text");
		dataBinder.registerBinding("entries.vehicle", com.kingdee.eas.cp.bc.VehicleEnum.class, this.kdtEntries, "vehicle.text");
		dataBinder.registerBinding("entries.amount", java.math.BigDecimal.class, this.kdtEntries, "amount.text");
		dataBinder.registerBinding("entries.PreDays", int.class, this.kdtEntries, "preDays.text");
		dataBinder.registerBinding("applierCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.bizPromptApplierCompany, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.cp.bc.app.EvectionReqBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.cp.bc.EvectionReqBillInfo)ov;
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
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("isDispatch", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prior", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("intendingDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalPeople", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supportedObj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("operationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("biller", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizReqDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.partner", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.from", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.to", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.vehicle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.PreDays", ValidateHelper.ON_SAVE);    
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
     * output isDispatch_actionPerformed method
     */
    protected void isDispatch_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output bizPromptApplier_dataChanged method
     */
    protected void bizPromptApplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output radioButtonNeedLoan_stateChanged method
     */
    protected void radioButtonNeedLoan_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output radioButtonTogetherAudit_stateChanged method
     */
    protected void radioButtonTogetherAudit_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output ckNeedLoan_mouseClicked method
     */
    protected void ckNeedLoan_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output ckNeedLoan_stateChanged method
     */
    protected void ckNeedLoan_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output loanEntries_editStopped method
     */
    protected void loanEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_editStopped method
     */
    protected void kdtEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isDispatch"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("applier.*"));
        sic.add(new SelectorItemInfo("position.*"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("company.*"));
        sic.add(new SelectorItemInfo("prior"));
        sic.add(new SelectorItemInfo("intendingDays"));
        sic.add(new SelectorItemInfo("totalPeople"));
        sic.add(new SelectorItemInfo("costedDept.*"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("currencyType.*"));
        sic.add(new SelectorItemInfo("supportedObj.*"));
        sic.add(new SelectorItemInfo("operationType.*"));
        sic.add(new SelectorItemInfo("billDate"));
        sic.add(new SelectorItemInfo("biller.*"));
        sic.add(new SelectorItemInfo("tel"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("bizReqDate"));
        sic.add(new SelectorItemInfo("cause"));
        sic.add(new SelectorItemInfo("description"));
    sic.add(new SelectorItemInfo("entries.partner"));
        sic.add(new SelectorItemInfo("entries.*"));
//        sic.add(new SelectorItemInfo("entries.number"));
    sic.add(new SelectorItemInfo("entries.id"));
    sic.add(new SelectorItemInfo("entries.startDate"));
    sic.add(new SelectorItemInfo("entries.from"));
    sic.add(new SelectorItemInfo("entries.endDate"));
    sic.add(new SelectorItemInfo("entries.to"));
    sic.add(new SelectorItemInfo("entries.vehicle"));
    sic.add(new SelectorItemInfo("entries.amount"));
    sic.add(new SelectorItemInfo("entries.PreDays"));
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
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
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
    }

    /**
     * output ActionViewBudgetBalance class
     */     
    protected class ActionViewBudgetBalance extends ItemAction {     
    
        public ActionViewBudgetBalance()
        {
            this(null);
        }

        public ActionViewBudgetBalance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewBudgetBalance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBudgetBalance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBudgetBalance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEvectionReqBillEditUI.this, "ActionViewBudgetBalance", "actionViewBudgetBalance_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.cp.bc.client", "EvectionReqBillEditUI");
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
        return com.kingdee.eas.cp.bc.client.EvectionReqBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.EvectionReqBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.EvectionReqBillInfo objectValue = new com.kingdee.eas.cp.bc.EvectionReqBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/cp/bc/EvectionReqBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.cp.bc.app.EvectionReqQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return loanEntries;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
				vo.put("intendingDays",new java.math.BigDecimal(0.0));
		vo.put("amount",new java.math.BigDecimal(0.00));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}