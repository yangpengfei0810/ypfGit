/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractOtherPaymentEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractOtherPaymentEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contorganize;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contwithMSector;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contapplyPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaymentDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayee;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreceiptBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrealityPayee;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreceiptNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contapplyAMT;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contapplyAMTHC;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contparities;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvoiceAMT;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contremark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcompletedQuantities;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayRequest;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contname;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbillstates;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditDate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisOAAudit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbillTempletID;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOABillID;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtorganize;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtwithMSector;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtapplyPeriod;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkpaymentDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpayee;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtreceiptBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpayType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrealityPayee;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtreceiptNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtapplyAMT;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtapplyAMTHC;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtparities;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtinvoiceAMT;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneremark;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtremark;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcompletedQuantities;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kdcbofujian;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcontract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpayRequest;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtname;
    protected com.kingdee.bos.ctrl.swing.KDComboBox billstates;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbillTempletID;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOABillID;
    protected com.kingdee.eas.fdc.finance.OtherPaymentInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractOtherPaymentEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractOtherPaymentEditUI.class.getName());
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
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contorganize = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contwithMSector = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contapplyPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpaymentDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayee = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreceiptBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrealityPayee = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreceiptNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contapplyAMT = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contapplyAMTHC = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contparities = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continvoiceNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continvoiceAMT = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contremark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPaymentProportion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcompletedQuantities = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayRequest = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contname = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbillstates = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisOAAudit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contbillTempletID = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOABillID = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtorganize = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtwithMSector = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtapplyPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkpaymentDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtpayee = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtreceiptBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtpayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtrealityPayee = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtreceiptNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtapplyAMT = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtapplyAMTHC = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtparities = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtinvoiceNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtinvoiceAMT = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.scrollPaneremark = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtremark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtPaymentProportion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcompletedQuantities = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdcbofujian = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtcontract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtpayRequest = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtname = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.billstates = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtbillTempletID = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOABillID = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contorganize.setName("contorganize");
        this.contcurProject.setName("contcurProject");
        this.contwithMSector.setName("contwithMSector");
        this.contapplyPeriod.setName("contapplyPeriod");
        this.contpaymentDate.setName("contpaymentDate");
        this.contpayee.setName("contpayee");
        this.contreceiptBank.setName("contreceiptBank");
        this.contpayType.setName("contpayType");
        this.contrealityPayee.setName("contrealityPayee");
        this.contreceiptNumber.setName("contreceiptNumber");
        this.contapplyAMT.setName("contapplyAMT");
        this.contapplyAMTHC.setName("contapplyAMTHC");
        this.contCurrency.setName("contCurrency");
        this.contparities.setName("contparities");
        this.continvoiceNumber.setName("continvoiceNumber");
        this.continvoiceAMT.setName("continvoiceAMT");
        this.contremark.setName("contremark");
        this.contPaymentProportion.setName("contPaymentProportion");
        this.contcompletedQuantities.setName("contcompletedQuantities");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contcontract.setName("contcontract");
        this.contpayRequest.setName("contpayRequest");
        this.kDSeparator8.setName("kDSeparator8");
        this.contname.setName("contname");
        this.contbillstates.setName("contbillstates");
        this.contauditDate.setName("contauditDate");
        this.chkisOAAudit.setName("chkisOAAudit");
        this.contbillTempletID.setName("contbillTempletID");
        this.contOABillID.setName("contOABillID");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtorganize.setName("prmtorganize");
        this.prmtcurProject.setName("prmtcurProject");
        this.prmtwithMSector.setName("prmtwithMSector");
        this.prmtapplyPeriod.setName("prmtapplyPeriod");
        this.pkpaymentDate.setName("pkpaymentDate");
        this.prmtpayee.setName("prmtpayee");
        this.txtreceiptBank.setName("txtreceiptBank");
        this.prmtpayType.setName("prmtpayType");
        this.prmtrealityPayee.setName("prmtrealityPayee");
        this.txtreceiptNumber.setName("txtreceiptNumber");
        this.txtapplyAMT.setName("txtapplyAMT");
        this.txtapplyAMTHC.setName("txtapplyAMTHC");
        this.prmtCurrency.setName("prmtCurrency");
        this.txtparities.setName("txtparities");
        this.txtinvoiceNumber.setName("txtinvoiceNumber");
        this.txtinvoiceAMT.setName("txtinvoiceAMT");
        this.scrollPaneremark.setName("scrollPaneremark");
        this.txtremark.setName("txtremark");
        this.txtPaymentProportion.setName("txtPaymentProportion");
        this.txtcompletedQuantities.setName("txtcompletedQuantities");
        this.kdcbofujian.setName("kdcbofujian");
        this.prmtcontract.setName("prmtcontract");
        this.prmtpayRequest.setName("prmtpayRequest");
        this.txtname.setName("txtname");
        this.billstates.setName("billstates");
        this.pkauditDate.setName("pkauditDate");
        this.txtbillTempletID.setName("txtbillTempletID");
        this.txtOABillID.setName("txtOABillID");
        // CoreUI		
        this.btnAddLine.setVisible(false);		
        this.btnCopyLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.btnCreateTo.setVisible(true);		
        this.menuItemCreateTo.setVisible(true);		
        this.menuItemCopyLine.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contorganize		
        this.contorganize.setBoundLabelText(resHelper.getString("contorganize.boundLabelText"));		
        this.contorganize.setBoundLabelLength(100);		
        this.contorganize.setBoundLabelUnderline(true);		
        this.contorganize.setVisible(true);
        // contcurProject		
        this.contcurProject.setBoundLabelText(resHelper.getString("contcurProject.boundLabelText"));		
        this.contcurProject.setBoundLabelLength(100);		
        this.contcurProject.setBoundLabelUnderline(true);		
        this.contcurProject.setVisible(true);
        // contwithMSector		
        this.contwithMSector.setBoundLabelText(resHelper.getString("contwithMSector.boundLabelText"));		
        this.contwithMSector.setBoundLabelLength(100);		
        this.contwithMSector.setBoundLabelUnderline(true);		
        this.contwithMSector.setVisible(true);
        // contapplyPeriod		
        this.contapplyPeriod.setBoundLabelText(resHelper.getString("contapplyPeriod.boundLabelText"));		
        this.contapplyPeriod.setBoundLabelLength(100);		
        this.contapplyPeriod.setBoundLabelUnderline(true);		
        this.contapplyPeriod.setVisible(true);
        // contpaymentDate		
        this.contpaymentDate.setBoundLabelText(resHelper.getString("contpaymentDate.boundLabelText"));		
        this.contpaymentDate.setBoundLabelLength(100);		
        this.contpaymentDate.setBoundLabelUnderline(true);		
        this.contpaymentDate.setVisible(true);
        // contpayee		
        this.contpayee.setBoundLabelText(resHelper.getString("contpayee.boundLabelText"));		
        this.contpayee.setBoundLabelLength(100);		
        this.contpayee.setBoundLabelUnderline(true);		
        this.contpayee.setVisible(true);
        // contreceiptBank		
        this.contreceiptBank.setBoundLabelText(resHelper.getString("contreceiptBank.boundLabelText"));		
        this.contreceiptBank.setBoundLabelLength(100);		
        this.contreceiptBank.setBoundLabelUnderline(true);		
        this.contreceiptBank.setVisible(true);
        // contpayType		
        this.contpayType.setBoundLabelText(resHelper.getString("contpayType.boundLabelText"));		
        this.contpayType.setBoundLabelLength(100);		
        this.contpayType.setBoundLabelUnderline(true);		
        this.contpayType.setVisible(true);
        // contrealityPayee		
        this.contrealityPayee.setBoundLabelText(resHelper.getString("contrealityPayee.boundLabelText"));		
        this.contrealityPayee.setBoundLabelLength(100);		
        this.contrealityPayee.setBoundLabelUnderline(true);		
        this.contrealityPayee.setVisible(true);
        // contreceiptNumber		
        this.contreceiptNumber.setBoundLabelText(resHelper.getString("contreceiptNumber.boundLabelText"));		
        this.contreceiptNumber.setBoundLabelLength(100);		
        this.contreceiptNumber.setBoundLabelUnderline(true);		
        this.contreceiptNumber.setVisible(true);
        // contapplyAMT		
        this.contapplyAMT.setBoundLabelText(resHelper.getString("contapplyAMT.boundLabelText"));		
        this.contapplyAMT.setBoundLabelLength(100);		
        this.contapplyAMT.setBoundLabelUnderline(true);		
        this.contapplyAMT.setVisible(true);
        // contapplyAMTHC		
        this.contapplyAMTHC.setBoundLabelText(resHelper.getString("contapplyAMTHC.boundLabelText"));		
        this.contapplyAMTHC.setBoundLabelLength(100);		
        this.contapplyAMTHC.setBoundLabelUnderline(true);		
        this.contapplyAMTHC.setVisible(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);		
        this.contCurrency.setVisible(true);
        // contparities		
        this.contparities.setBoundLabelText(resHelper.getString("contparities.boundLabelText"));		
        this.contparities.setBoundLabelLength(100);		
        this.contparities.setBoundLabelUnderline(true);		
        this.contparities.setVisible(true);
        // continvoiceNumber		
        this.continvoiceNumber.setBoundLabelText(resHelper.getString("continvoiceNumber.boundLabelText"));		
        this.continvoiceNumber.setBoundLabelLength(100);		
        this.continvoiceNumber.setBoundLabelUnderline(true);		
        this.continvoiceNumber.setVisible(true);
        // continvoiceAMT		
        this.continvoiceAMT.setBoundLabelText(resHelper.getString("continvoiceAMT.boundLabelText"));		
        this.continvoiceAMT.setBoundLabelLength(100);		
        this.continvoiceAMT.setBoundLabelUnderline(true);		
        this.continvoiceAMT.setVisible(true);
        // contremark		
        this.contremark.setBoundLabelText(resHelper.getString("contremark.boundLabelText"));		
        this.contremark.setBoundLabelLength(100);		
        this.contremark.setBoundLabelUnderline(true);		
        this.contremark.setVisible(true);
        // contPaymentProportion		
        this.contPaymentProportion.setBoundLabelText(resHelper.getString("contPaymentProportion.boundLabelText"));		
        this.contPaymentProportion.setBoundLabelLength(100);		
        this.contPaymentProportion.setBoundLabelUnderline(true);		
        this.contPaymentProportion.setVisible(true);
        // contcompletedQuantities		
        this.contcompletedQuantities.setBoundLabelText(resHelper.getString("contcompletedQuantities.boundLabelText"));		
        this.contcompletedQuantities.setBoundLabelLength(100);		
        this.contcompletedQuantities.setBoundLabelUnderline(true);		
        this.contcompletedQuantities.setVisible(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contcontract		
        this.contcontract.setBoundLabelText(resHelper.getString("contcontract.boundLabelText"));		
        this.contcontract.setBoundLabelLength(100);		
        this.contcontract.setBoundLabelUnderline(true);		
        this.contcontract.setVisible(true);
        // contpayRequest		
        this.contpayRequest.setBoundLabelText(resHelper.getString("contpayRequest.boundLabelText"));		
        this.contpayRequest.setBoundLabelLength(100);		
        this.contpayRequest.setBoundLabelUnderline(true);		
        this.contpayRequest.setVisible(true);
        // kDSeparator8
        // contname		
        this.contname.setBoundLabelText(resHelper.getString("contname.boundLabelText"));		
        this.contname.setBoundLabelLength(100);		
        this.contname.setBoundLabelUnderline(true);		
        this.contname.setVisible(true);
        // contbillstates		
        this.contbillstates.setBoundLabelText(resHelper.getString("contbillstates.boundLabelText"));		
        this.contbillstates.setBoundLabelLength(100);		
        this.contbillstates.setBoundLabelUnderline(true);		
        this.contbillstates.setVisible(true);
        // contauditDate		
        this.contauditDate.setBoundLabelText(resHelper.getString("contauditDate.boundLabelText"));		
        this.contauditDate.setBoundLabelLength(100);		
        this.contauditDate.setBoundLabelUnderline(true);		
        this.contauditDate.setVisible(true);
        // chkisOAAudit		
        this.chkisOAAudit.setText(resHelper.getString("chkisOAAudit.text"));		
        this.chkisOAAudit.setVisible(true);		
        this.chkisOAAudit.setHorizontalAlignment(2);
        // contbillTempletID		
        this.contbillTempletID.setBoundLabelText(resHelper.getString("contbillTempletID.boundLabelText"));		
        this.contbillTempletID.setBoundLabelLength(100);		
        this.contbillTempletID.setBoundLabelUnderline(true);		
        this.contbillTempletID.setVisible(true);
        // contOABillID		
        this.contOABillID.setBoundLabelText(resHelper.getString("contOABillID.boundLabelText"));		
        this.contOABillID.setBoundLabelLength(100);		
        this.contOABillID.setBoundLabelUnderline(true);		
        this.contOABillID.setVisible(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);
        this.pkBizDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkBizDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtorganize		
        this.prmtorganize.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtorganize.setVisible(true);		
        this.prmtorganize.setEditable(true);		
        this.prmtorganize.setDisplayFormat("$name$");		
        this.prmtorganize.setEditFormat("$number$");		
        this.prmtorganize.setCommitFormat("$number$");		
        this.prmtorganize.setRequired(false);
        // prmtcurProject		
        this.prmtcurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");		
        this.prmtcurProject.setVisible(true);		
        this.prmtcurProject.setEditable(true);		
        this.prmtcurProject.setDisplayFormat("$displayName$");		
        this.prmtcurProject.setEditFormat("$number$");		
        this.prmtcurProject.setCommitFormat("$number$");		
        this.prmtcurProject.setRequired(false);
        // prmtwithMSector		
        this.prmtwithMSector.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtwithMSector.setVisible(true);		
        this.prmtwithMSector.setEditable(true);		
        this.prmtwithMSector.setDisplayFormat("$name$");		
        this.prmtwithMSector.setEditFormat("$number$");		
        this.prmtwithMSector.setCommitFormat("$number$");		
        this.prmtwithMSector.setRequired(false);
        // prmtapplyPeriod		
        this.prmtapplyPeriod.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");		
        this.prmtapplyPeriod.setVisible(true);		
        this.prmtapplyPeriod.setEditable(true);		
        this.prmtapplyPeriod.setDisplayFormat("$number$");		
        this.prmtapplyPeriod.setEditFormat("$number$");		
        this.prmtapplyPeriod.setCommitFormat("$number$");		
        this.prmtapplyPeriod.setRequired(false);
        // pkpaymentDate		
        this.pkpaymentDate.setVisible(true);		
        this.pkpaymentDate.setRequired(false);
        // prmtpayee		
        this.prmtpayee.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.PSupplierQuery");		
        this.prmtpayee.setVisible(true);		
        this.prmtpayee.setEditable(true);		
        this.prmtpayee.setDisplayFormat("$name$");		
        this.prmtpayee.setEditFormat("$number$");		
        this.prmtpayee.setCommitFormat("$number$");		
        this.prmtpayee.setRequired(false);
        // txtreceiptBank		
        this.txtreceiptBank.setVisible(true);		
        this.txtreceiptBank.setHorizontalAlignment(2);		
        this.txtreceiptBank.setMaxLength(100);		
        this.txtreceiptBank.setRequired(false);
        // prmtpayType		
        this.prmtpayType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");		
        this.prmtpayType.setVisible(true);		
        this.prmtpayType.setEditable(true);		
        this.prmtpayType.setDisplayFormat("$name$");		
        this.prmtpayType.setEditFormat("$number$");		
        this.prmtpayType.setCommitFormat("$number$");		
        this.prmtpayType.setRequired(false);
        // prmtrealityPayee		
        this.prmtrealityPayee.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.PSupplierQuery");		
        this.prmtrealityPayee.setVisible(true);		
        this.prmtrealityPayee.setEditable(true);		
        this.prmtrealityPayee.setDisplayFormat("$name$");		
        this.prmtrealityPayee.setEditFormat("$number$");		
        this.prmtrealityPayee.setCommitFormat("$number$");		
        this.prmtrealityPayee.setRequired(false);
        this.prmtrealityPayee.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtrealityPayee_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtreceiptNumber		
        this.txtreceiptNumber.setVisible(true);		
        this.txtreceiptNumber.setHorizontalAlignment(2);		
        this.txtreceiptNumber.setMaxLength(100);		
        this.txtreceiptNumber.setRequired(false);
        // txtapplyAMT		
        this.txtapplyAMT.setVisible(true);		
        this.txtapplyAMT.setHorizontalAlignment(2);		
        this.txtapplyAMT.setDataType(1);		
        this.txtapplyAMT.setSupportedEmpty(true);		
        this.txtapplyAMT.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtapplyAMT.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtapplyAMT.setPrecision(2);		
        this.txtapplyAMT.setRequired(false);
        this.txtapplyAMT.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtapplyAMT_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtapplyAMTHC		
        this.txtapplyAMTHC.setVisible(true);		
        this.txtapplyAMTHC.setHorizontalAlignment(2);		
        this.txtapplyAMTHC.setDataType(1);		
        this.txtapplyAMTHC.setSupportedEmpty(true);		
        this.txtapplyAMTHC.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtapplyAMTHC.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtapplyAMTHC.setPrecision(2);		
        this.txtapplyAMTHC.setRequired(false);
        // prmtCurrency		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtCurrency.setVisible(true);		
        this.prmtCurrency.setEditable(true);		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setEditFormat("$number$");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setRequired(false);
        this.prmtCurrency.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCurrency_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtparities		
        this.txtparities.setVisible(true);		
        this.txtparities.setHorizontalAlignment(2);		
        this.txtparities.setDataType(1);		
        this.txtparities.setSupportedEmpty(true);		
        this.txtparities.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtparities.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtparities.setPrecision(2);		
        this.txtparities.setRequired(false);
        // txtinvoiceNumber		
        this.txtinvoiceNumber.setVisible(true);		
        this.txtinvoiceNumber.setHorizontalAlignment(2);		
        this.txtinvoiceNumber.setMaxLength(100);		
        this.txtinvoiceNumber.setRequired(false);
        // txtinvoiceAMT		
        this.txtinvoiceAMT.setVisible(true);		
        this.txtinvoiceAMT.setHorizontalAlignment(2);		
        this.txtinvoiceAMT.setDataType(1);		
        this.txtinvoiceAMT.setSupportedEmpty(true);		
        this.txtinvoiceAMT.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtinvoiceAMT.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtinvoiceAMT.setPrecision(2);		
        this.txtinvoiceAMT.setRequired(false);
        // scrollPaneremark
        // txtremark		
        this.txtremark.setVisible(true);		
        this.txtremark.setRequired(false);		
        this.txtremark.setMaxLength(255);
        // txtPaymentProportion
        // txtcompletedQuantities		
        this.txtcompletedQuantities.setVisible(true);		
        this.txtcompletedQuantities.setHorizontalAlignment(2);		
        this.txtcompletedQuantities.setDataType(1);		
        this.txtcompletedQuantities.setSupportedEmpty(true);		
        this.txtcompletedQuantities.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtcompletedQuantities.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtcompletedQuantities.setPrecision(2);		
        this.txtcompletedQuantities.setRequired(false);
        this.txtcompletedQuantities.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtcompletedQuantities_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdcbofujian
        // prmtcontract		
        this.prmtcontract.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillQuery");		
        this.prmtcontract.setVisible(true);		
        this.prmtcontract.setEditable(true);		
        this.prmtcontract.setDisplayFormat("$name$");		
        this.prmtcontract.setEditFormat("$number$");		
        this.prmtcontract.setCommitFormat("$number$");		
        this.prmtcontract.setRequired(false);
        // prmtpayRequest		
        this.prmtpayRequest.setQueryInfo("com.kingdee.eas.fdc.contract.app.PayRequestBillQuery");		
        this.prmtpayRequest.setVisible(true);		
        this.prmtpayRequest.setEditable(true);		
        this.prmtpayRequest.setDisplayFormat("$number$");		
        this.prmtpayRequest.setEditFormat("$number$");		
        this.prmtpayRequest.setCommitFormat("$number$");		
        this.prmtpayRequest.setRequired(false);
        // txtname		
        this.txtname.setVisible(true);		
        this.txtname.setHorizontalAlignment(2);		
        this.txtname.setMaxLength(100);		
        this.txtname.setRequired(false);
        // billstates		
        this.billstates.setVisible(true);		
        this.billstates.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.billstates.setRequired(false);
        // pkauditDate		
        this.pkauditDate.setVisible(true);		
        this.pkauditDate.setRequired(false);
        // txtbillTempletID		
        this.txtbillTempletID.setVisible(true);		
        this.txtbillTempletID.setHorizontalAlignment(2);		
        this.txtbillTempletID.setMaxLength(100);		
        this.txtbillTempletID.setRequired(false);
        // txtOABillID		
        this.txtOABillID.setVisible(true);		
        this.txtOABillID.setHorizontalAlignment(2);		
        this.txtOABillID.setMaxLength(100);		
        this.txtOABillID.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtorganize,prmtcurProject,prmtwithMSector,prmtapplyPeriod,pkpaymentDate,prmtpayee,txtreceiptBank,prmtpayType,prmtrealityPayee,txtreceiptNumber,txtapplyAMT,txtapplyAMTHC,prmtCurrency,txtparities,txtinvoiceNumber,txtinvoiceAMT,txtremark,txtcompletedQuantities,prmtcontract,prmtpayRequest,txtname,billstates,pkauditDate,chkisOAAudit,txtbillTempletID,txtOABillID}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 1130, 524));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1130, 524));
        contCreator.setBounds(new Rectangle(399, 454, 316, 19));
        this.add(contCreator, new KDLayout.Constraints(399, 454, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(787, 455, 316, 19));
        this.add(contCreateTime, new KDLayout.Constraints(787, 455, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(399, 488, 316, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(399, 488, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(787, 489, 316, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(787, 489, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(10, 137, 520, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 137, 520, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(10, 188, 316, 19));
        this.add(contBizDate, new KDLayout.Constraints(10, 188, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(100, 505, 316, 19));
        this.add(contDescription, new KDLayout.Constraints(100, 505, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(12, 454, 316, 19));
        this.add(contAuditor, new KDLayout.Constraints(12, 454, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contorganize.setBounds(new Rectangle(10, 17, 520, 19));
        this.add(contorganize, new KDLayout.Constraints(10, 17, 520, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurProject.setBounds(new Rectangle(581, 17, 520, 19));
        this.add(contcurProject, new KDLayout.Constraints(581, 17, 520, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contwithMSector.setBounds(new Rectangle(582, 47, 520, 19));
        this.add(contwithMSector, new KDLayout.Constraints(582, 47, 520, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contapplyPeriod.setBounds(new Rectangle(399, 188, 316, 19));
        this.add(contapplyPeriod, new KDLayout.Constraints(399, 188, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpaymentDate.setBounds(new Rectangle(10, 220, 316, 19));
        this.add(contpaymentDate, new KDLayout.Constraints(10, 220, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpayee.setBounds(new Rectangle(581, 78, 520, 19));
        this.add(contpayee, new KDLayout.Constraints(581, 78, 520, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contreceiptBank.setBounds(new Rectangle(785, 188, 316, 19));
        this.add(contreceiptBank, new KDLayout.Constraints(785, 188, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpayType.setBounds(new Rectangle(399, 220, 316, 19));
        this.add(contpayType, new KDLayout.Constraints(399, 220, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrealityPayee.setBounds(new Rectangle(581, 108, 520, 19));
        this.add(contrealityPayee, new KDLayout.Constraints(581, 108, 520, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contreceiptNumber.setBounds(new Rectangle(785, 220, 316, 19));
        this.add(contreceiptNumber, new KDLayout.Constraints(785, 220, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contapplyAMT.setBounds(new Rectangle(10, 252, 316, 19));
        this.add(contapplyAMT, new KDLayout.Constraints(10, 252, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contapplyAMTHC.setBounds(new Rectangle(10, 284, 316, 19));
        this.add(contapplyAMTHC, new KDLayout.Constraints(10, 284, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(399, 252, 316, 19));
        this.add(contCurrency, new KDLayout.Constraints(399, 252, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contparities.setBounds(new Rectangle(785, 252, 316, 19));
        this.add(contparities, new KDLayout.Constraints(785, 252, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        continvoiceNumber.setBounds(new Rectangle(399, 284, 316, 19));
        this.add(continvoiceNumber, new KDLayout.Constraints(399, 284, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continvoiceAMT.setBounds(new Rectangle(785, 284, 316, 19));
        this.add(continvoiceAMT, new KDLayout.Constraints(785, 284, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contremark.setBounds(new Rectangle(12, 374, 1089, 69));
        this.add(contremark, new KDLayout.Constraints(12, 374, 1089, 69, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contPaymentProportion.setBounds(new Rectangle(10, 314, 316, 19));
        this.add(contPaymentProportion, new KDLayout.Constraints(10, 314, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcompletedQuantities.setBounds(new Rectangle(399, 314, 316, 19));
        this.add(contcompletedQuantities, new KDLayout.Constraints(399, 314, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(12, 343, 704, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(12, 343, 704, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontract.setBounds(new Rectangle(10, 47, 520, 19));
        this.add(contcontract, new KDLayout.Constraints(10, 47, 520, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpayRequest.setBounds(new Rectangle(10, 78, 520, 19));
        this.add(contpayRequest, new KDLayout.Constraints(10, 78, 520, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(13, 171, 1091, 10));
        this.add(kDSeparator8, new KDLayout.Constraints(13, 171, 1091, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contname.setBounds(new Rectangle(581, 137, 520, 19));
        this.add(contname, new KDLayout.Constraints(581, 137, 520, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbillstates.setBounds(new Rectangle(11, 108, 520, 19));
        this.add(contbillstates, new KDLayout.Constraints(11, 108, 520, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contauditDate.setBounds(new Rectangle(12, 488, 316, 19));
        this.add(contauditDate, new KDLayout.Constraints(12, 488, 316, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkisOAAudit.setBounds(new Rectangle(787, 314, 270, 19));
        this.add(chkisOAAudit, new KDLayout.Constraints(787, 314, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbillTempletID.setBounds(new Rectangle(787, 327, 270, 19));
        this.add(contbillTempletID, new KDLayout.Constraints(787, 327, 270, 19, 0));
        contOABillID.setBounds(new Rectangle(787, 346, 270, 19));
        this.add(contOABillID, new KDLayout.Constraints(787, 346, 270, 19, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contorganize
        contorganize.setBoundEditor(prmtorganize);
        //contcurProject
        contcurProject.setBoundEditor(prmtcurProject);
        //contwithMSector
        contwithMSector.setBoundEditor(prmtwithMSector);
        //contapplyPeriod
        contapplyPeriod.setBoundEditor(prmtapplyPeriod);
        //contpaymentDate
        contpaymentDate.setBoundEditor(pkpaymentDate);
        //contpayee
        contpayee.setBoundEditor(prmtpayee);
        //contreceiptBank
        contreceiptBank.setBoundEditor(txtreceiptBank);
        //contpayType
        contpayType.setBoundEditor(prmtpayType);
        //contrealityPayee
        contrealityPayee.setBoundEditor(prmtrealityPayee);
        //contreceiptNumber
        contreceiptNumber.setBoundEditor(txtreceiptNumber);
        //contapplyAMT
        contapplyAMT.setBoundEditor(txtapplyAMT);
        //contapplyAMTHC
        contapplyAMTHC.setBoundEditor(txtapplyAMTHC);
        //contCurrency
        contCurrency.setBoundEditor(prmtCurrency);
        //contparities
        contparities.setBoundEditor(txtparities);
        //continvoiceNumber
        continvoiceNumber.setBoundEditor(txtinvoiceNumber);
        //continvoiceAMT
        continvoiceAMT.setBoundEditor(txtinvoiceAMT);
        //contremark
        contremark.setBoundEditor(scrollPaneremark);
        //scrollPaneremark
        scrollPaneremark.getViewport().add(txtremark, null);
        //contPaymentProportion
        contPaymentProportion.setBoundEditor(txtPaymentProportion);
        //contcompletedQuantities
        contcompletedQuantities.setBoundEditor(txtcompletedQuantities);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(kdcbofujian);
        //contcontract
        contcontract.setBoundEditor(prmtcontract);
        //contpayRequest
        contpayRequest.setBoundEditor(prmtpayRequest);
        //contname
        contname.setBoundEditor(txtname);
        //contbillstates
        contbillstates.setBoundEditor(billstates);
        //contauditDate
        contauditDate.setBoundEditor(pkauditDate);
        //contbillTempletID
        contbillTempletID.setBoundEditor(txtbillTempletID);
        //contOABillID
        contOABillID.setBoundEditor(txtOABillID);

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
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
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
        menuWorkflow.add(kDSeparator5);
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
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isOAAudit", boolean.class, this.chkisOAAudit, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("organize", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtorganize, "data");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtcurProject, "data");
		dataBinder.registerBinding("withMSector", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtwithMSector, "data");
		dataBinder.registerBinding("applyPeriod", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.prmtapplyPeriod, "data");
		dataBinder.registerBinding("paymentDate", java.util.Date.class, this.pkpaymentDate, "value");
		dataBinder.registerBinding("payee", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtpayee, "data");
		dataBinder.registerBinding("receiptBank", String.class, this.txtreceiptBank, "text");
		dataBinder.registerBinding("payType", com.kingdee.eas.fi.cas.PaymentBillTypeInfo.class, this.prmtpayType, "data");
		dataBinder.registerBinding("realityPayee", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtrealityPayee, "data");
		dataBinder.registerBinding("receiptNumber", String.class, this.txtreceiptNumber, "text");
		dataBinder.registerBinding("applyAMT", java.math.BigDecimal.class, this.txtapplyAMT, "value");
		dataBinder.registerBinding("applyAMTHC", java.math.BigDecimal.class, this.txtapplyAMTHC, "value");
		dataBinder.registerBinding("Currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("parities", java.math.BigDecimal.class, this.txtparities, "value");
		dataBinder.registerBinding("invoiceNumber", String.class, this.txtinvoiceNumber, "text");
		dataBinder.registerBinding("invoiceAMT", java.math.BigDecimal.class, this.txtinvoiceAMT, "value");
		dataBinder.registerBinding("remark", String.class, this.txtremark, "text");
		dataBinder.registerBinding("PaymentProportion", java.math.BigDecimal.class, this.txtPaymentProportion, "value");
		dataBinder.registerBinding("completedQuantities", java.math.BigDecimal.class, this.txtcompletedQuantities, "value");
		dataBinder.registerBinding("contract", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtcontract, "data");
		dataBinder.registerBinding("payRequest", com.kingdee.eas.fdc.contract.PayRequestBillInfo.class, this.prmtpayRequest, "data");
		dataBinder.registerBinding("name", String.class, this.txtname, "text");
		dataBinder.registerBinding("billstates", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.billstates, "selectedItem");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkauditDate, "value");
		dataBinder.registerBinding("billTempletID", String.class, this.txtbillTempletID, "text");
		dataBinder.registerBinding("OABillID", String.class, this.txtOABillID, "text");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_ADDNEW, this.prmtorganize, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_ADDNEW, this.prmtcurProject, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtapplyAMTHC, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_ADDNEW, this.billstates, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_ADDNEW, this.prmtapplyPeriod, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtparities, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_ADDNEW, this.pkauditDate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.prmtorganize, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.prmtcurProject, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.txtapplyAMTHC, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.prmtpayRequest, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.prmtcontract, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.prmtapplyPeriod, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.txtparities, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.billstates, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.pkauditDate, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.prmtorganize, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.prmtcurProject, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtapplyAMTHC, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.prmtpayRequest, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.prmtcontract, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.prmtapplyPeriod, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtparities, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.billstates, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.pkauditDate, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.OtherPaymentEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.prmtorganize.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.finance.OtherPaymentInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
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

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
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
		getValidateHelper().registerBindProperty("isOAAudit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("organize", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("withMSector", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applyPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receiptBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("realityPayee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receiptNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applyAMT", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applyAMTHC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parities", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceAMT", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PaymentProportion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("completedQuantities", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payRequest", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billstates", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billTempletID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OABillID", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.prmtorganize.setEnabled(false);
		            this.prmtcurProject.setEnabled(false);
		            this.txtapplyAMTHC.setEnabled(false);
		            this.billstates.setEnabled(false);
		            this.prmtapplyPeriod.setEnabled(false);
		            this.txtparities.setEnabled(false);
		            this.pkauditDate.setEnabled(false);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.prmtorganize.setEnabled(false);
		            this.prmtcurProject.setEnabled(false);
		            this.txtapplyAMTHC.setEnabled(false);
		            this.prmtpayRequest.setEnabled(false);
		            this.prmtcontract.setEnabled(false);
		            this.prmtapplyPeriod.setEnabled(false);
		            this.txtparities.setEnabled(false);
		            this.billstates.setEnabled(false);
		            this.pkauditDate.setEnabled(false);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.prmtorganize.setEnabled(false);
		            this.prmtcurProject.setEnabled(false);
		            this.txtapplyAMTHC.setEnabled(false);
		            this.prmtpayRequest.setEnabled(false);
		            this.prmtcontract.setEnabled(false);
		            this.prmtapplyPeriod.setEnabled(false);
		            this.txtparities.setEnabled(false);
		            this.billstates.setEnabled(false);
		            this.pkauditDate.setEnabled(false);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output pkBizDate_dataChanged method
     */
    protected void pkBizDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtrealityPayee_dataChanged method
     */
    protected void prmtrealityPayee_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtapplyAMT_dataChanged method
     */
    protected void txtapplyAMT_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCurrency_dataChanged method
     */
    protected void prmtCurrency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtcompletedQuantities_dataChanged method
     */
    protected void txtcompletedQuantities_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isOAAudit"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("organize.*"));
        sic.add(new SelectorItemInfo("curProject.*"));
        sic.add(new SelectorItemInfo("withMSector.*"));
        sic.add(new SelectorItemInfo("applyPeriod.*"));
        sic.add(new SelectorItemInfo("paymentDate"));
        sic.add(new SelectorItemInfo("payee.*"));
        sic.add(new SelectorItemInfo("receiptBank"));
        sic.add(new SelectorItemInfo("payType.*"));
        sic.add(new SelectorItemInfo("realityPayee.*"));
        sic.add(new SelectorItemInfo("receiptNumber"));
        sic.add(new SelectorItemInfo("applyAMT"));
        sic.add(new SelectorItemInfo("applyAMTHC"));
        sic.add(new SelectorItemInfo("Currency.*"));
        sic.add(new SelectorItemInfo("parities"));
        sic.add(new SelectorItemInfo("invoiceNumber"));
        sic.add(new SelectorItemInfo("invoiceAMT"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("PaymentProportion"));
        sic.add(new SelectorItemInfo("completedQuantities"));
        sic.add(new SelectorItemInfo("contract.*"));
        sic.add(new SelectorItemInfo("payRequest.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("billstates"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("billTempletID"));
        sic.add(new SelectorItemInfo("OABillID"));
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
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
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
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "OtherPaymentEditUI");
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
        return com.kingdee.eas.fdc.finance.client.OtherPaymentEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.finance.OtherPaymentFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.finance.OtherPaymentInfo objectValue = new com.kingdee.eas.fdc.finance.OtherPaymentInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/finance/OtherPayment";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.finance.app.OtherPaymentQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("billstates","1SAVED");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}