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
public abstract class AbstractBizAccountEditUI extends com.kingdee.eas.cp.bc.client.ExpenseAccountEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBizAccountEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.swing.KDTextField BankNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField PayUnitName;
    protected com.kingdee.bos.ctrl.swing.KDTextField AccountNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField LeadPerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewRcrdsOfLendAndRepay;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewRcds;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBudget;
    protected com.kingdee.eas.cp.bc.BizAccountBillInfo editData = null;
    protected ActionViewRrcdsOfLendAndRepay actionViewRrcdsOfLendAndRepay = null;
    /**
     * output class constructor
     */
    public AbstractBizAccountEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBizAccountEditUI.class.getName());
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
        //actionCreateFrom
        actionCreateFrom.setEnabled(true);
        actionCreateFrom.setDaemonRun(false);

        actionCreateFrom.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt R"));
        _tempStr = resHelper.getString("ActionCreateFrom.SHORT_DESCRIPTION");
        actionCreateFrom.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreateFrom.LONG_DESCRIPTION");
        actionCreateFrom.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCreateFrom.NAME");
        actionCreateFrom.putValue(ItemAction.NAME, _tempStr);
        this.actionCreateFrom.setBindWorkFlow(true);
         this.actionCreateFrom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCreateFrom.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCreateFrom.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionVoucher
        actionVoucher.setEnabled(true);
        actionVoucher.setDaemonRun(false);

        actionVoucher.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl V"));
        _tempStr = resHelper.getString("ActionVoucher.SHORT_DESCRIPTION");
        actionVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionVoucher.LONG_DESCRIPTION");
        actionVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionVoucher.NAME");
        actionVoucher.putValue(ItemAction.NAME, _tempStr);
        this.actionVoucher.setBindWorkFlow(true);
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionShowBg
        actionShowBg.setEnabled(true);
        actionShowBg.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionShowBg.SHORT_DESCRIPTION");
        actionShowBg.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionShowBg.LONG_DESCRIPTION");
        actionShowBg.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionShowBg.NAME");
        actionShowBg.putValue(ItemAction.NAME, _tempStr);
         this.actionShowBg.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionShowBg.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionShowBg.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionViewRrcdsOfLendAndRepay
        this.actionViewRrcdsOfLendAndRepay = new ActionViewRrcdsOfLendAndRepay(this);
        getActionManager().registerAction("actionViewRrcdsOfLendAndRepay", actionViewRrcdsOfLendAndRepay);
         this.actionViewRrcdsOfLendAndRepay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.ctnEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.BankNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.PayUnitName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.AccountNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.LeadPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnViewRcrdsOfLendAndRepay = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewRcds = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewBudget = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.ctnEntry.setName("ctnEntry");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kdtEntries.setName("kdtEntries");
        this.BankNo.setName("BankNo");
        this.PayUnitName.setName("PayUnitName");
        this.AccountNo.setName("AccountNo");
        this.LeadPerson.setName("LeadPerson");
        this.btnViewRcrdsOfLendAndRepay.setName("btnViewRcrdsOfLendAndRepay");
        this.menuItemViewRcds.setName("menuItemViewRcds");
        this.menuItemViewBudget.setName("menuItemViewBudget");
        // CoreUI		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.separatorFW1.setVisible(false);		
        this.separatorFW3.setVisible(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW9.setVisible(false);		
        this.separatorFW7.setVisible(false);		
        this.menuItemCreateTo.setText(resHelper.getString("menuItemCreateTo.text"));		
        this.txtName.setRequired(false);		
        this.contExpenseType.setBoundLabelText(resHelper.getString("contExpenseType.boundLabelText"));		
        this.contCostedDept.setBoundLabelText(resHelper.getString("contCostedDept.boundLabelText"));		
        this.bizPromptCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery4AsstAcct");		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.txtTel.setRequired(false);		
        this.contPrior.setForeground(new java.awt.Color(60,60,60));		
        this.contAmountApproved.setBoundLabelText(resHelper.getString("contAmountApproved.boundLabelText"));		
        this.contAmountApproved.setEnabled(false);		
        this.txtAmountApproved.setEnabled(false);		
        this.txtAmountEncashed.setEnabled(false);		
        this.separatorCommon.setVisible(false);		
        this.txtDesc.setMaxLength(1000);
        // ctnEntry		
        this.ctnEntry.setTitle(resHelper.getString("ctnEntry.title"));		
        this.ctnEntry.setTitleStyle(2);		
        this.ctnEntry.setEnableActive(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>%{yyyy-MM-dd}t</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>%r-[=]{#,##0.00}n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>%r-[=]{#,##0.00}n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>%r-[=]{#,##0.00}n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"operationType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"expenseType\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"purpose\" t:width=\"173\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"participants\" t:width=\"166\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"happenTime\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"budgetAmount\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"amount\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"approvedAmount\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"costCenter\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"company\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"receiveState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"comment\" t:width=\"132\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{operationType}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{expenseType}</t:Cell><t:Cell>$Resource{purpose}</t:Cell><t:Cell>$Resource{participants}</t:Cell><t:Cell>$Resource{happenTime}</t:Cell><t:Cell>$Resource{budgetAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{approvedAmount}</t:Cell><t:Cell>$Resource{costCenter}</t:Cell><t:Cell>$Resource{company}</t:Cell><t:Cell>$Resource{receiveState}</t:Cell><t:Cell>$Resource{payState}</t:Cell><t:Cell>$Resource{comment}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));

                this.kdtEntries.putBindContents("editData",new String[] {"id","operationType","name","expenseType","purpose","participants","happenTime","budgetAmount","amount","amountApproved","costCenter","company","receiveState","payState","comment"});


        this.kdtEntries.checkParsed();
        final KDBizPromptBox kdtEntries_operationType_PromptBox = new KDBizPromptBox();
        kdtEntries_operationType_PromptBox.setQueryInfo("com.kingdee.eas.cp.bc.app.OperationTypeQuery");
        kdtEntries_operationType_PromptBox.setVisible(true);
        kdtEntries_operationType_PromptBox.setEditable(true);
        kdtEntries_operationType_PromptBox.setDisplayFormat("$number$");
        kdtEntries_operationType_PromptBox.setEditFormat("$number$");
        kdtEntries_operationType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_operationType_CellEditor = new KDTDefaultCellEditor(kdtEntries_operationType_PromptBox);
        this.kdtEntries.getColumn("operationType").setEditor(kdtEntries_operationType_CellEditor);
        ObjectValueRender kdtEntries_operationType_OVR = new ObjectValueRender();
        kdtEntries_operationType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("operationType").setRenderer(kdtEntries_operationType_OVR);
        final KDBizPromptBox kdtEntries_expenseType_PromptBox = new KDBizPromptBox();
        kdtEntries_expenseType_PromptBox.setQueryInfo("com.kingdee.eas.cp.bc.app.ExpenseTypeQuery");
        kdtEntries_expenseType_PromptBox.setVisible(true);
        kdtEntries_expenseType_PromptBox.setEditable(true);
        kdtEntries_expenseType_PromptBox.setDisplayFormat("$number$");
        kdtEntries_expenseType_PromptBox.setEditFormat("$number$");
        kdtEntries_expenseType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_expenseType_CellEditor = new KDTDefaultCellEditor(kdtEntries_expenseType_PromptBox);
        this.kdtEntries.getColumn("expenseType").setEditor(kdtEntries_expenseType_CellEditor);
        ObjectValueRender kdtEntries_expenseType_OVR = new ObjectValueRender();
        kdtEntries_expenseType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("expenseType").setRenderer(kdtEntries_expenseType_OVR);
        final KDBizPromptBox kdtEntries_costCenter_PromptBox = new KDBizPromptBox();
        kdtEntries_costCenter_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterItemQuery");
        kdtEntries_costCenter_PromptBox.setVisible(true);
        kdtEntries_costCenter_PromptBox.setEditable(true);
        kdtEntries_costCenter_PromptBox.setDisplayFormat("$number$");
        kdtEntries_costCenter_PromptBox.setEditFormat("$number$");
        kdtEntries_costCenter_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_costCenter_CellEditor = new KDTDefaultCellEditor(kdtEntries_costCenter_PromptBox);
        this.kdtEntries.getColumn("costCenter").setEditor(kdtEntries_costCenter_CellEditor);
        ObjectValueRender kdtEntries_costCenter_OVR = new ObjectValueRender();
        kdtEntries_costCenter_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("costCenter").setRenderer(kdtEntries_costCenter_OVR);
        final KDBizPromptBox kdtEntries_company_PromptBox = new KDBizPromptBox();
        kdtEntries_company_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery4AsstAcct");
        kdtEntries_company_PromptBox.setVisible(true);
        kdtEntries_company_PromptBox.setEditable(true);
        kdtEntries_company_PromptBox.setDisplayFormat("$number$");
        kdtEntries_company_PromptBox.setEditFormat("$number$");
        kdtEntries_company_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntries_company_CellEditor = new KDTDefaultCellEditor(kdtEntries_company_PromptBox);
        this.kdtEntries.getColumn("company").setEditor(kdtEntries_company_CellEditor);
        ObjectValueRender kdtEntries_company_OVR = new ObjectValueRender();
        kdtEntries_company_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntries.getColumn("company").setRenderer(kdtEntries_company_OVR);
        KDComboBox kdtEntries_receiveState_ComboBox = new KDComboBox();
        kdtEntries_receiveState_ComboBox.setName("kdtEntries_receiveState_ComboBox");
        kdtEntries_receiveState_ComboBox.setVisible(true);
        kdtEntries_receiveState_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.EntryStateEnum").toArray());
        KDTDefaultCellEditor kdtEntries_receiveState_CellEditor = new KDTDefaultCellEditor(kdtEntries_receiveState_ComboBox);
        this.kdtEntries.getColumn("receiveState").setEditor(kdtEntries_receiveState_CellEditor);
        KDComboBox kdtEntries_payState_ComboBox = new KDComboBox();
        kdtEntries_payState_ComboBox.setName("kdtEntries_payState_ComboBox");
        kdtEntries_payState_ComboBox.setVisible(true);
        kdtEntries_payState_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.cp.bc.EntryStateEnum").toArray());
        KDTDefaultCellEditor kdtEntries_payState_CellEditor = new KDTDefaultCellEditor(kdtEntries_payState_ComboBox);
        this.kdtEntries.getColumn("payState").setEditor(kdtEntries_payState_CellEditor);
        // BankNo
        // PayUnitName
        // AccountNo
        // LeadPerson
        // btnViewRcrdsOfLendAndRepay
        this.btnViewRcrdsOfLendAndRepay.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewRcrdsOfLendAndRepay.setText(resHelper.getString("btnViewRcrdsOfLendAndRepay.text"));		
        this.btnViewRcrdsOfLendAndRepay.setToolTipText(resHelper.getString("btnViewRcrdsOfLendAndRepay.toolTipText"));
        // menuItemViewRcds
        this.menuItemViewRcds.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewRrcdsOfLendAndRepay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewRcds.setText(resHelper.getString("menuItemViewRcds.text"));		
        this.menuItemViewRcds.setMnemonic(82);
        // menuItemViewBudget
        this.menuItemViewBudget.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowBg, new Class[] { IItemAction.class }, getServiceContext()));		
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
        labState.setBounds(new Rectangle(281, 529, 67, 19));
        this.add(labState, new KDLayout.Constraints(281, 529, 67, 19, 0));
        comboState.setBounds(new Rectangle(362, 528, 170, 19));
        this.add(comboState, new KDLayout.Constraints(362, 528, 170, 19, 0));
        contNumber.setBounds(new Rectangle(10, 32, 240, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(10, 10, 510, 19));
        this.add(contName, new KDLayout.Constraints(10, 10, 510, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizReqDate.setBounds(new Rectangle(280, 32, 240, 19));
        this.add(contBizReqDate, new KDLayout.Constraints(280, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrgUnit.setBounds(new Rectangle(550, 32, 240, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(550, 32, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contExpenseType.setBounds(new Rectangle(280, 120, 240, 19));
        this.add(contExpenseType, new KDLayout.Constraints(280, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostedDept.setBounds(new Rectangle(280, 54, 240, 19));
        this.add(contCostedDept, new KDLayout.Constraints(280, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCompany.setBounds(new Rectangle(280, 76, 240, 19));
        this.add(contCompany, new KDLayout.Constraints(280, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupportedObj.setBounds(new Rectangle(550, 76, 240, 19));
        this.add(contSupportedObj, new KDLayout.Constraints(550, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplier.setBounds(new Rectangle(550, 10, 240, 19));
        this.add(contApplier, new KDLayout.Constraints(550, 10, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPosition.setBounds(new Rectangle(10, 76, 240, 19));
        this.add(contPosition, new KDLayout.Constraints(10, 76, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTel.setBounds(new Rectangle(10, 98, 240, 19));
        this.add(contTel, new KDLayout.Constraints(10, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrencyType.setBounds(new Rectangle(10, 120, 240, 19));
        this.add(contCurrencyType, new KDLayout.Constraints(10, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPayMode.setBounds(new Rectangle(280, 98, 240, 19));
        this.add(contPayMode, new KDLayout.Constraints(280, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPrior.setBounds(new Rectangle(10, 54, 240, 19));
        this.add(contPrior, new KDLayout.Constraints(10, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCause.setBounds(new Rectangle(10, 184, 40, 19));
        this.add(contCause, new KDLayout.Constraints(10, 184, 40, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmount.setBounds(new Rectangle(550, 120, 240, 19));
        this.add(contAmount, new KDLayout.Constraints(550, 120, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBudgetAmount.setBounds(new Rectangle(280, 498, 240, 19));
        this.add(contBudgetAmount, new KDLayout.Constraints(280, 498, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBudgetBalance.setBounds(new Rectangle(550, 520, 240, 19));
        this.add(contBudgetBalance, new KDLayout.Constraints(550, 520, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmountApproved.setBounds(new Rectangle(10, 498, 240, 19));
        this.add(contAmountApproved, new KDLayout.Constraints(10, 498, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmountStriked.setBounds(new Rectangle(280, 520, 240, 19));
        this.add(contAmountStriked, new KDLayout.Constraints(280, 520, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmountRefunded.setBounds(new Rectangle(10, 520, 240, 19));
        this.add(contAmountRefunded, new KDLayout.Constraints(10, 520, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmountEncashed.setBounds(new Rectangle(550, 495, 240, 19));
        this.add(contAmountEncashed, new KDLayout.Constraints(550, 495, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(10, 430, 40, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 430, 40, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBiller.setBounds(new Rectangle(550, 549, 240, 19));
        this.add(contBiller, new KDLayout.Constraints(550, 549, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBillDate.setBounds(new Rectangle(550, 571, 240, 19));
        this.add(contBillDate, new KDLayout.Constraints(550, 571, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblAuditor.setBounds(new Rectangle(10, 549, 240, 19));
        this.add(lblAuditor, new KDLayout.Constraints(10, 549, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblAuditDate.setBounds(new Rectangle(10, 571, 240, 19));
        this.add(lblAuditDate, new KDLayout.Constraints(10, 571, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblUpdate.setBounds(new Rectangle(280, 551, 240, 19));
        this.add(lblUpdate, new KDLayout.Constraints(280, 551, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblUpdateDate.setBounds(new Rectangle(280, 575, 240, 19));
        this.add(lblUpdateDate, new KDLayout.Constraints(280, 575, 240, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(10, 544, 780, 1));
        this.add(kDLabelContainer2, new KDLayout.Constraints(10, 544, 780, 1, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(10, 205, 780, 40));
        this.add(kDScrollPane1, new KDLayout.Constraints(10, 205, 780, 40, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane2.setBounds(new Rectangle(10, 455, 780, 40));
        this.add(kDScrollPane2, new KDLayout.Constraints(10, 455, 780, 40, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAccessoryCount.setBounds(new Rectangle(550, 98, 240, 19));
        this.add(contAccessoryCount, new KDLayout.Constraints(550, 98, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(550, 54, 240, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(550, 54, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        ctnEntry.setBounds(new Rectangle(10, 247, 780, 184));
        this.add(ctnEntry, new KDLayout.Constraints(10, 247, 780, 184, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(10, 143, 240, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(10, 143, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(280, 143, 240, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(280, 143, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(550, 143, 240, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(550, 143, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(10, 164, 240, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(10, 164, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contBizReqDate
        contBizReqDate.setBoundEditor(dateBizReqDate);
        //contOrgUnit
        contOrgUnit.setBoundEditor(bizPromptOrgUnit);
        //contExpenseType
        contExpenseType.setBoundEditor(bizPromptExpenseType);
        //contCostedDept
        contCostedDept.setBoundEditor(bizPromptCostedDept);
        //contCompany
        contCompany.setBoundEditor(bizPromptCompany);
        //contSupportedObj
        contSupportedObj.setBoundEditor(bizPromptSupportedObj);
        //contApplier
        contApplier.setBoundEditor(bizPromptApplier);
        //contPosition
        contPosition.setBoundEditor(bizPromptPosition);
        //contTel
        contTel.setBoundEditor(txtTel);
        //contCurrencyType
        contCurrencyType.setBoundEditor(bizPromptCurrencyType);
        //contPayMode
        contPayMode.setBoundEditor(bizPromptPayMode);
        //contPrior
        contPrior.setBoundEditor(comboPrior);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contBudgetAmount
        contBudgetAmount.setBoundEditor(txtBudgetAmount);
        //contBudgetBalance
        contBudgetBalance.setBoundEditor(txtSendedBack);
        //contAmountApproved
        contAmountApproved.setBoundEditor(txtAmountApproved);
        //contAmountStriked
        contAmountStriked.setBoundEditor(txtAmountStriked);
        //contAmountRefunded
        contAmountRefunded.setBoundEditor(txtAmountRefunded);
        //contAmountEncashed
        contAmountEncashed.setBoundEditor(txtAmountEncashed);
        //contBiller
        contBiller.setBoundEditor(txtBiller);
        //contBillDate
        contBillDate.setBoundEditor(dateBillDate);
        //lblAuditor
        lblAuditor.setBoundEditor(txtAutitor);
        //lblAuditDate
        lblAuditDate.setBoundEditor(dateAudit);
        //lblUpdate
        lblUpdate.setBoundEditor(txtUpdate);
        //lblUpdateDate
        lblUpdateDate.setBoundEditor(dateUpdate);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtCause, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtDesc, null);
        //contAccessoryCount
        contAccessoryCount.setBoundEditor(txtAccessoryCount);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(bizPromptApplierCompany);
        //ctnEntry
ctnEntry.getContentPane().setLayout(new BorderLayout(0, 0));        ctnEntry.getContentPane().add(kdtEntries, BorderLayout.CENTER);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(BankNo);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(PayUnitName);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(AccountNo);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(LeadPerson);

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
        menuView.add(menuItemViewRcds);
        menuView.add(kDSeparator6);
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
        this.toolBar.add(separatorCommon);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnSuspenseAcc);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW10);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnShowBg);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnViewRcrdsOfLendAndRepay);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCreateToReceiveBill);
        this.toolBar.add(btnCreateToPayBill);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("operationType", com.kingdee.eas.cp.bc.OperationTypeInfo.class, this.bizPromptExpenseType, "data");
		dataBinder.registerBinding("entries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntries, "id.text");
		dataBinder.registerBinding("entries.name", String.class, this.kdtEntries, "name.text");
		dataBinder.registerBinding("entries.purpose", String.class, this.kdtEntries, "purpose.text");
		dataBinder.registerBinding("entries.amount", java.math.BigDecimal.class, this.kdtEntries, "amount.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.cp.bc.BizAccountBillEntryInfo.class, this.kdtEntries, "userObject");
		dataBinder.registerBinding("entries.happenTime", java.util.Date.class, this.kdtEntries, "happenTime.text");
		dataBinder.registerBinding("entries.participants", String.class, this.kdtEntries, "participants.text");
		dataBinder.registerBinding("entries.comment", String.class, this.kdtEntries, "comment.text");
		dataBinder.registerBinding("entries.expenseType", com.kingdee.eas.cp.bc.ExpenseTypeInfo.class, this.kdtEntries, "expenseType.text");
		dataBinder.registerBinding("entries.budgetAmount", java.math.BigDecimal.class, this.kdtEntries, "budgetAmount.text");
		dataBinder.registerBinding("entries.amountApproved", java.math.BigDecimal.class, this.kdtEntries, "approvedAmount.text");
		dataBinder.registerBinding("entries.operationType", com.kingdee.eas.cp.bc.OperationTypeInfo.class, this.kdtEntries, "operationType.text");
		dataBinder.registerBinding("entries.costCenter", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.kdtEntries, "costCenter.text");
		dataBinder.registerBinding("entries.company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.kdtEntries, "company.text");
		dataBinder.registerBinding("entries.receiveState", com.kingdee.eas.cp.bc.EntryStateEnum.class, this.kdtEntries, "receiveState.text");
		dataBinder.registerBinding("entries.payState", com.kingdee.eas.cp.bc.EntryStateEnum.class, this.kdtEntries, "payState.text");
		dataBinder.registerBinding("BankNo", String.class, this.BankNo, "text");
		dataBinder.registerBinding("PayUnitName", String.class, this.PayUnitName, "text");
		dataBinder.registerBinding("AccountNo", String.class, this.AccountNo, "text");
		dataBinder.registerBinding("LeadPerson", String.class, this.LeadPerson, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.cp.bc.app.BizAccountEditUIHandler";
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
        this.editData = (com.kingdee.eas.cp.bc.BizAccountBillInfo)ov;
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
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizReqDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("operationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supportedObj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currencyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prior", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("budgetAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountSendedBack", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountStriked", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountRefunded", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountEncashed", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("biller.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accessoryCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applierCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.purpose", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.happenTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.participants", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.expenseType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.budgetAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amountApproved", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.operationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.costCenter", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.receiveState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.payState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BankNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayUnitName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AccountNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LeadPerson", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("operationType.*"));
    sic.add(new SelectorItemInfo("entries.id"));
    sic.add(new SelectorItemInfo("entries.name"));
    sic.add(new SelectorItemInfo("entries.purpose"));
    sic.add(new SelectorItemInfo("entries.amount"));
        sic.add(new SelectorItemInfo("entries.*"));
//        sic.add(new SelectorItemInfo("entries.number"));
    sic.add(new SelectorItemInfo("entries.happenTime"));
    sic.add(new SelectorItemInfo("entries.participants"));
    sic.add(new SelectorItemInfo("entries.comment"));
        sic.add(new SelectorItemInfo("entries.expenseType.*"));
//        sic.add(new SelectorItemInfo("entries.expenseType.number"));
    sic.add(new SelectorItemInfo("entries.budgetAmount"));
    sic.add(new SelectorItemInfo("entries.amountApproved"));
        sic.add(new SelectorItemInfo("entries.operationType.*"));
//        sic.add(new SelectorItemInfo("entries.operationType.number"));
        sic.add(new SelectorItemInfo("entries.costCenter.*"));
//        sic.add(new SelectorItemInfo("entries.costCenter.number"));
        sic.add(new SelectorItemInfo("entries.company.*"));
//        sic.add(new SelectorItemInfo("entries.company.number"));
    sic.add(new SelectorItemInfo("entries.receiveState"));
    sic.add(new SelectorItemInfo("entries.payState"));
        sic.add(new SelectorItemInfo("BankNo"));
        sic.add(new SelectorItemInfo("PayUnitName"));
        sic.add(new SelectorItemInfo("AccountNo"));
        sic.add(new SelectorItemInfo("LeadPerson"));
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
     * output actionCreateFrom_actionPerformed method
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }
    	

    /**
     * output actionVoucher_actionPerformed method
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }
    	

    /**
     * output actionShowBg_actionPerformed method
     */
    public void actionShowBg_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionShowBg_actionPerformed(e);
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
	public RequestContext prepareActionCreateFrom(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCreateFrom(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreateFrom() {
    	return false;
    }
	public RequestContext prepareActionVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionVoucher(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVoucher() {
    	return false;
    }
	public RequestContext prepareActionShowBg(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionShowBg(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowBg() {
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
            innerActionPerformed("eas", AbstractBizAccountEditUI.this, "ActionViewRrcdsOfLendAndRepay", "actionViewRrcdsOfLendAndRepay_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.cp.bc.client", "BizAccountEditUI");
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
        return com.kingdee.eas.cp.bc.client.BizAccountEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.cp.bc.BizAccountBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.cp.bc.BizAccountBillInfo objectValue = new com.kingdee.eas.cp.bc.BizAccountBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/cp/bc/BizAccountBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.cp.bc.app.BizAccountQuery");
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
		vo.put("amountSendedBack",new java.math.BigDecimal(0.0));
		vo.put("amountApproved",new java.math.BigDecimal(0.0));
		vo.put("amountStriked",new java.math.BigDecimal(0.00));
		vo.put("amountRefunded",new java.math.BigDecimal(0.00));
		vo.put("amountEncashed",new java.math.BigDecimal(0.00));
		vo.put("accessoryCount",new Integer(0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}