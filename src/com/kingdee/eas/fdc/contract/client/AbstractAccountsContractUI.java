/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractAccountsContractUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAccountsContractUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSaleArea;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisplayAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisplaNoText;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisplayContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSubmit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRecense;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRevert;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemVersionInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpression;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFirstVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPreVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNextVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemLastVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemApportion;
    protected ActionDisplayAll actionDisplayAll = null;
    protected ActionDisplayConNoText actionDisplayConNoText = null;
    protected ActionDisplayContract actionDisplayContract = null;
    /**
     * output class constructor
     */
    public AbstractAccountsContractUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractAccountsContractUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionMoveTree
        String _tempStr = null;
        actionMoveTree.setEnabled(true);
        actionMoveTree.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionMoveTree.SHORT_DESCRIPTION");
        actionMoveTree.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionMoveTree.LONG_DESCRIPTION");
        actionMoveTree.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionMoveTree.NAME");
        actionMoveTree.putValue(ItemAction.NAME, _tempStr);
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionDisplayAll
        this.actionDisplayAll = new ActionDisplayAll(this);
        getActionManager().registerAction("actionDisplayAll", actionDisplayAll);
         this.actionDisplayAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisplayConNoText
        this.actionDisplayConNoText = new ActionDisplayConNoText(this);
        getActionManager().registerAction("actionDisplayConNoText", actionDisplayConNoText);
         this.actionDisplayConNoText.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisplayContract
        this.actionDisplayContract = new ActionDisplayContract(this);
        getActionManager().registerAction("actionDisplayContract", actionDisplayContract);
         this.actionDisplayContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contBuildArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBuildArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSaleArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnDisplayAll = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisplaNoText = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisplayContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSubmit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRecense = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAddRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDeleteRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRevert = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemVersionInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpression = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFirstVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPreVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNextVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemLastVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemApportion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel1.setName("kDPanel1");
        this.contBuildArea.setName("contBuildArea");
        this.contSaleArea.setName("contSaleArea");
        this.txtBuildArea.setName("txtBuildArea");
        this.txtSaleArea.setName("txtSaleArea");
        this.btnDisplayAll.setName("btnDisplayAll");
        this.btnDisplaNoText.setName("btnDisplaNoText");
        this.btnDisplayContract.setName("btnDisplayContract");
        this.menuItemSubmit.setName("menuItemSubmit");
        this.menuItemRecense.setName("menuItemRecense");
        this.menuItemAddRow.setName("menuItemAddRow");
        this.menuItemDeleteRow.setName("menuItemDeleteRow");
        this.menuItemRevert.setName("menuItemRevert");
        this.menuItemVersionInfo.setName("menuItemVersionInfo");
        this.menuItemExpression.setName("menuItemExpression");
        this.menuItemFirstVersion.setName("menuItemFirstVersion");
        this.menuItemPreVersion.setName("menuItemPreVersion");
        this.menuItemNextVersion.setName("menuItemNextVersion");
        this.menuItemLastVersion.setName("menuItemLastVersion");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.menuItemApportion.setName("menuItemApportion");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol25\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"acctNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"acctName\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"dyCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"hasHappen\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /><t:Column t:key=\"indentingHappen\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"conNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"contract\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" /><t:Column t:key=\"unit\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" /><t:Column t:key=\"date\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" /><t:Column t:key=\"contractSource\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" /><t:Column t:key=\"isInvite\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /><t:Column t:key=\"diff\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:configured=\"false\" t:styleID=\"sCol12\" /><t:Column t:key=\"amt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:configured=\"false\" t:styleID=\"sCol13\" /><t:Column t:key=\"lastPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:configured=\"false\" /><t:Column t:key=\"totalSettPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:configured=\"false\" /><t:Column t:key=\"isFinalSett\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:configured=\"false\" /><t:Column t:key=\"payableAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:configured=\"false\" /><t:Column t:key=\"hasPayAmt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:configured=\"false\" t:styleID=\"sCol18\" /><t:Column t:key=\"allNotPaid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:configured=\"false\" /><t:Column t:key=\"payPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:configured=\"false\" /><t:Column t:key=\"splitAmt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:configured=\"false\" t:styleID=\"sCol21\" /><t:Column t:key=\"changeSplitAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:configured=\"false\" /><t:Column t:key=\"settSplitAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:configured=\"false\" /><t:Column t:key=\"hasPayCostAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:configured=\"false\" /><t:Column t:key=\"paymentSplitAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:configured=\"false\" t:styleID=\"sCol25\" /><t:Column t:key=\"sellPart\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:configured=\"false\" t:styleID=\"sCol26\" /><t:Column t:key=\"buildPart\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:configured=\"false\" t:styleID=\"sCol27\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{acctNumber}</t:Cell><t:Cell>$Resource{acctName}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{dyCost}</t:Cell><t:Cell>$Resource{hasHappen}</t:Cell><t:Cell>$Resource{indentingHappen}</t:Cell><t:Cell>$Resource{conNumber}</t:Cell><t:Cell>$Resource{contract}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{contractSource}</t:Cell><t:Cell>$Resource{isInvite}</t:Cell><t:Cell>$Resource{diff}</t:Cell><t:Cell>$Resource{amt}</t:Cell><t:Cell>$Resource{lastPrice}</t:Cell><t:Cell>$Resource{totalSettPrice}</t:Cell><t:Cell>$Resource{isFinalSett}</t:Cell><t:Cell>$Resource{payableAmt}</t:Cell><t:Cell>$Resource{hasPayAmt}</t:Cell><t:Cell>$Resource{allNotPaid}</t:Cell><t:Cell>$Resource{payPercent}</t:Cell><t:Cell>$Resource{splitAmt}</t:Cell><t:Cell>$Resource{changeSplitAmt}</t:Cell><t:Cell>$Resource{settSplitAmt}</t:Cell><t:Cell>$Resource{hasPayCostAmt}</t:Cell><t:Cell>$Resource{paymentSplitAmt}</t:Cell><t:Cell>$Resource{sellPart}</t:Cell><t:Cell>$Resource{buildPart}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","","","","","","","","","","","","","","","","","",""});

		
        this.menuItemAddNew.setVisible(false);		
        this.MenuItemAttachment.setEnabled(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.pnlMain.setDividerLocation(180);		
        this.pnlMain.setOneTouchExpandable(true);
        // kDPanel3
        // kDPanel1		
        this.kDPanel1.setMinimumSize(new Dimension(500,35));		
        this.kDPanel1.setPreferredSize(new Dimension(500,35));		
        this.kDPanel1.setMaximumSize(new Dimension(500,35));
        // contBuildArea		
        this.contBuildArea.setBoundLabelText(resHelper.getString("contBuildArea.boundLabelText"));		
        this.contBuildArea.setBoundLabelLength(100);		
        this.contBuildArea.setBoundLabelUnderline(true);
        // contSaleArea		
        this.contSaleArea.setBoundLabelText(resHelper.getString("contSaleArea.boundLabelText"));		
        this.contSaleArea.setBoundLabelLength(100);		
        this.contSaleArea.setBoundLabelUnderline(true);
        // txtBuildArea
        // txtSaleArea
        // btnDisplayAll
        this.btnDisplayAll.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisplayAll, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisplayAll.setText(resHelper.getString("btnDisplayAll.text"));		
        this.btnDisplayAll.setToolTipText(resHelper.getString("btnDisplayAll.toolTipText"));
        // btnDisplaNoText
        this.btnDisplaNoText.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisplayConNoText, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisplaNoText.setText(resHelper.getString("btnDisplaNoText.text"));		
        this.btnDisplaNoText.setToolTipText(resHelper.getString("btnDisplaNoText.toolTipText"));
        // btnDisplayContract
        this.btnDisplayContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisplayContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisplayContract.setText(resHelper.getString("btnDisplayContract.text"));		
        this.btnDisplayContract.setToolTipText(resHelper.getString("btnDisplayContract.toolTipText"));
        // menuItemSubmit		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));
        // menuItemRecense		
        this.menuItemRecense.setText(resHelper.getString("menuItemRecense.text"));		
        this.menuItemRecense.setToolTipText(resHelper.getString("menuItemRecense.toolTipText"));
        // menuItemAddRow		
        this.menuItemAddRow.setText(resHelper.getString("menuItemAddRow.text"));
        // menuItemDeleteRow		
        this.menuItemDeleteRow.setText(resHelper.getString("menuItemDeleteRow.text"));
        // menuItemRevert		
        this.menuItemRevert.setText(resHelper.getString("menuItemRevert.text"));		
        this.menuItemRevert.setToolTipText(resHelper.getString("menuItemRevert.toolTipText"));
        // menuItemVersionInfo		
        this.menuItemVersionInfo.setText(resHelper.getString("menuItemVersionInfo.text"));		
        this.menuItemVersionInfo.setToolTipText(resHelper.getString("menuItemVersionInfo.toolTipText"));
        // menuItemExpression		
        this.menuItemExpression.setText(resHelper.getString("menuItemExpression.text"));		
        this.menuItemExpression.setToolTipText(resHelper.getString("menuItemExpression.toolTipText"));
        // menuItemFirstVersion		
        this.menuItemFirstVersion.setText(resHelper.getString("menuItemFirstVersion.text"));		
        this.menuItemFirstVersion.setVisible(false);
        // menuItemPreVersion		
        this.menuItemPreVersion.setText(resHelper.getString("menuItemPreVersion.text"));		
        this.menuItemPreVersion.setVisible(false);
        // menuItemNextVersion		
        this.menuItemNextVersion.setText(resHelper.getString("menuItemNextVersion.text"));		
        this.menuItemNextVersion.setVisible(false);
        // menuItemLastVersion		
        this.menuItemLastVersion.setText(resHelper.getString("menuItemLastVersion.text"));		
        this.menuItemLastVersion.setVisible(false);
        // menuItemAudit		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));
        // menuItemUnAudit		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));
        // menuItemApportion		
        this.menuItemApportion.setText(resHelper.getString("menuItemApportion.text"));
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        pnlMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(kDPanel3, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDPanel3
kDPanel3.setLayout(new BorderLayout(0, 0));        kDPanel3.add(tblMain, BorderLayout.CENTER);
        kDPanel3.add(kDPanel1, BorderLayout.NORTH);
        //kDPanel1
        kDPanel1.setLayout(null);        contBuildArea.setBounds(new Rectangle(39, 9, 270, 19));
        kDPanel1.add(contBuildArea, null);
        contSaleArea.setBounds(new Rectangle(530, 10, 270, 19));
        kDPanel1.add(contSaleArea, null);
        //contBuildArea
        contBuildArea.setBoundEditor(txtBuildArea);
        //contSaleArea
        contSaleArea.setBoundEditor(txtSaleArea);

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
        this.menuBar.add(menuTool);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemRecense);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
        menuEdit.add(menuItemAddRow);
        menuEdit.add(menuItemDeleteRow);
        menuEdit.add(menuItemRevert);
        menuEdit.add(menuItemVersionInfo);
        menuEdit.add(menuItemExpression);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(separatorView1);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemLocate);
        menuView.add(menuItemQuery);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemFirstVersion);
        menuView.add(menuItemPreVersion);
        menuView.add(menuItemNextVersion);
        menuView.add(menuItemLastVersion);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemApportion);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnDisplayAll);
        this.toolBar.add(btnDisplaNoText);
        this.toolBar.add(btnDisplayContract);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.AccountsContractUIHandler";
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
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
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
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output tblMain_editStopping method
     */
    protected void tblMain_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblMain_editStopped method
     */
    protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionMoveTree_actionPerformed method
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }
    	

    /**
     * output actionDisplayAll_actionPerformed method
     */
    public void actionDisplayAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisplayConNoText_actionPerformed method
     */
    public void actionDisplayConNoText_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisplayContract_actionPerformed method
     */
    public void actionDisplayContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionMoveTree(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionMoveTree(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMoveTree() {
    	return false;
    }
	public RequestContext prepareActionDisplayAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisplayAll() {
    	return false;
    }
	public RequestContext prepareActionDisplayConNoText(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisplayConNoText() {
    	return false;
    }
	public RequestContext prepareActionDisplayContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisplayContract() {
    	return false;
    }

    /**
     * output ActionDisplayAll class
     */     
    protected class ActionDisplayAll extends ItemAction {     
    
        public ActionDisplayAll()
        {
            this(null);
        }

        public ActionDisplayAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisplayAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAccountsContractUI.this, "ActionDisplayAll", "actionDisplayAll_actionPerformed", e);
        }
    }

    /**
     * output ActionDisplayConNoText class
     */     
    protected class ActionDisplayConNoText extends ItemAction {     
    
        public ActionDisplayConNoText()
        {
            this(null);
        }

        public ActionDisplayConNoText(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisplayConNoText.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayConNoText.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayConNoText.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAccountsContractUI.this, "ActionDisplayConNoText", "actionDisplayConNoText_actionPerformed", e);
        }
    }

    /**
     * output ActionDisplayContract class
     */     
    protected class ActionDisplayContract extends ItemAction {     
    
        public ActionDisplayContract()
        {
            this(null);
        }

        public ActionDisplayContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisplayContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAccountsContractUI.this, "ActionDisplayContract", "actionDisplayContract_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "AccountsContractUI");
    }




}