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
public abstract class AbstractChangeAuditListUI extends com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseContainChangeTypeUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChangeAuditListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel treePnl;
    protected com.kingdee.bos.ctrl.swing.KDPanel rightPnl;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane3;
    protected com.kingdee.bos.ctrl.swing.KDContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDContainer contChangeType;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisPatch;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAheadDisPatch;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSetRespite;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelRespite;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDisPatch;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAheadDisPatch;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSetRespite;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCancelRespite;
    protected ActionDisPatch actionDisPatch = null;
    protected ActionAheadDisPatch actionAheadDisPatch = null;
    protected ActionSetRespite actionSetRespite = null;
    protected ActionCancelRespite actionCancelRespite = null;
    /**
     * output class constructor
     */
    public AbstractChangeAuditListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChangeAuditListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ChangeAuditQuery");
        //actionRemove
        String _tempStr = null;
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
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
        //actionTraceUp
        actionTraceUp.setEnabled(false);
        actionTraceUp.setDaemonRun(false);

        actionTraceUp.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F8"));
        _tempStr = resHelper.getString("ActionTraceUp.SHORT_DESCRIPTION");
        actionTraceUp.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionTraceUp.LONG_DESCRIPTION");
        actionTraceUp.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionTraceUp.NAME");
        actionTraceUp.putValue(ItemAction.NAME, _tempStr);
         this.actionTraceUp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionTraceUp.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionTraceUp.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionTraceDown
        actionTraceDown.setEnabled(false);
        actionTraceDown.setDaemonRun(false);

        actionTraceDown.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F9"));
        _tempStr = resHelper.getString("ActionTraceDown.SHORT_DESCRIPTION");
        actionTraceDown.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionTraceDown.LONG_DESCRIPTION");
        actionTraceDown.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionTraceDown.NAME");
        actionTraceDown.putValue(ItemAction.NAME, _tempStr);
         this.actionTraceDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionTraceDown.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionTraceDown.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionDisPatch
        this.actionDisPatch = new ActionDisPatch(this);
        getActionManager().registerAction("actionDisPatch", actionDisPatch);
         this.actionDisPatch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAheadDisPatch
        this.actionAheadDisPatch = new ActionAheadDisPatch(this);
        getActionManager().registerAction("actionAheadDisPatch", actionAheadDisPatch);
        this.actionAheadDisPatch.setBindWorkFlow(true);
         this.actionAheadDisPatch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSetRespite
        this.actionSetRespite = new ActionSetRespite(this);
        getActionManager().registerAction("actionSetRespite", actionSetRespite);
         this.actionSetRespite.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelRespite
        this.actionCancelRespite = new ActionCancelRespite(this);
        getActionManager().registerAction("actionCancelRespite", actionCancelRespite);
         this.actionCancelRespite.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.treePnl = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.rightPnl = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSplitPane3 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contChangeType = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnDisPatch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAheadDisPatch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSetRespite = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelRespite = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemDisPatch = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAheadDisPatch = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSetRespite = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCancelRespite = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.treePnl.setName("treePnl");
        this.rightPnl.setName("rightPnl");
        this.kDSplitPane3.setName("kDSplitPane3");
        this.contProject.setName("contProject");
        this.contChangeType.setName("contChangeType");
        this.btnDisPatch.setName("btnDisPatch");
        this.btnAheadDisPatch.setName("btnAheadDisPatch");
        this.btnSetRespite.setName("btnSetRespite");
        this.btnCancelRespite.setName("btnCancelRespite");
        this.menuItemDisPatch.setName("menuItemDisPatch");
        this.menuItemAheadDisPatch.setName("menuItemAheadDisPatch");
        this.menuItemSetRespite.setName("menuItemSetRespite");
        this.menuItemCancelRespite.setName("menuItemCancelRespite");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"bookedDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"period\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"changeState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" /><t:Column t:key=\"changeBefAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"changeAftAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" /><t:Column t:key=\"curProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" /><t:Column t:key=\"changeReason.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" /><t:Column t:key=\"auditType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" /><t:Column t:key=\"urgentDegree\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /><t:Column t:key=\"jobType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:configured=\"false\" /><t:Column t:key=\"specialtyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:configured=\"false\" /><t:Column t:key=\"auditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:configured=\"false\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:configured=\"false\" t:styleID=\"sCol15\" /><t:Column t:key=\"isRespite\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:configured=\"false\" /><t:Column t:key=\"isOAAudit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{period}</t:Cell><t:Cell>$Resource{changeState}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{changeBefAmt}</t:Cell><t:Cell>$Resource{changeAftAmt}</t:Cell><t:Cell>$Resource{curProject.name}</t:Cell><t:Cell>$Resource{changeReason.name}</t:Cell><t:Cell>$Resource{auditType.name}</t:Cell><t:Cell>$Resource{urgentDegree}</t:Cell><t:Cell>$Resource{jobType.name}</t:Cell><t:Cell>$Resource{specialtyName}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{isRespite}</t:Cell><t:Cell>$Resource{isOAAudit}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","bookedDate","period.number","changeState","number","name","","","curProject.name","changeReason.name","auditType.name","urgentDegree","jobType.name","specialName","auditor.name","auditTime","isRespite","isOAAudit"});

		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setToolTipText(resHelper.getString("btnAttachment.toolTipText"));		
        this.MenuItemAttachment.setText(resHelper.getString("MenuItemAttachment.text"));		
        this.MenuItemAttachment.setToolTipText(resHelper.getString("MenuItemAttachment.toolTipText"));		
        this.kDSplitPane1.setDividerLocation(250);		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        this.treeChangeType.addMouseListener(new java.awt.event.MouseAdapter() {
        });
        // treePnl		
        this.treePnl.setDoubleBuffered(false);
        // rightPnl		
        this.rightPnl.setDoubleBuffered(false);
        // kDSplitPane3		
        this.kDSplitPane3.setOrientation(0);		
        this.kDSplitPane3.setDividerLocation(300);		
        this.kDSplitPane3.setResizeWeight(0.5);		
        this.kDSplitPane3.setLastDividerLocation(200);
        // contProject		
        this.contProject.setEnableActive(false);		
        this.contProject.setTitle(resHelper.getString("contProject.title"));
        // contChangeType		
        this.contChangeType.setTitle(resHelper.getString("contChangeType.title"));
        // btnDisPatch
        this.btnDisPatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisPatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisPatch.setText(resHelper.getString("btnDisPatch.text"));
        // btnAheadDisPatch
        this.btnAheadDisPatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionAheadDisPatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAheadDisPatch.setText(resHelper.getString("btnAheadDisPatch.text"));		
        this.btnAheadDisPatch.setToolTipText(resHelper.getString("btnAheadDisPatch.toolTipText"));
        // btnSetRespite
        this.btnSetRespite.setAction((IItemAction)ActionProxyFactory.getProxy(actionSetRespite, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSetRespite.setText(resHelper.getString("btnSetRespite.text"));		
        this.btnSetRespite.setToolTipText(resHelper.getString("btnSetRespite.toolTipText"));		
        this.btnSetRespite.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_stopserve"));
        // btnCancelRespite
        this.btnCancelRespite.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelRespite, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelRespite.setText(resHelper.getString("btnCancelRespite.text"));		
        this.btnCancelRespite.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_stopserve"));		
        this.btnCancelRespite.setToolTipText(resHelper.getString("btnCancelRespite.toolTipText"));
        // menuItemDisPatch
        this.menuItemDisPatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisPatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDisPatch.setText(resHelper.getString("menuItemDisPatch.text"));
        // menuItemAheadDisPatch
        this.menuItemAheadDisPatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionAheadDisPatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAheadDisPatch.setText(resHelper.getString("menuItemAheadDisPatch.text"));
        // menuItemSetRespite
        this.menuItemSetRespite.setAction((IItemAction)ActionProxyFactory.getProxy(actionSetRespite, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSetRespite.setText(resHelper.getString("menuItemSetRespite.text"));		
        this.menuItemSetRespite.setToolTipText(resHelper.getString("menuItemSetRespite.toolTipText"));		
        this.menuItemSetRespite.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_startupserve"));
        // menuItemCancelRespite
        this.menuItemCancelRespite.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelRespite, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCancelRespite.setText(resHelper.getString("menuItemCancelRespite.text"));		
        this.menuItemCancelRespite.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_stopserve"));		
        this.menuItemCancelRespite.setToolTipText(resHelper.getString("menuItemCancelRespite.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 1020, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1020, 629));
        kDSplitPane1.setBounds(new Rectangle(10, 10, 1005, 615));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 1005, 615, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(leftTree, "left");
        kDSplitPane1.add(rightMain, "right");
        kDSplitPane1.add(treePnl, "left");
        kDSplitPane1.add(rightPnl, "right");
        //leftTree
        leftTree.setLayout(new KDLayout());
        leftTree.putClientProperty("OriginalBounds", new Rectangle(0, 0, 249, 614));        kDSplitPane2.setBounds(new Rectangle(0, 0, 248, 617));
        leftTree.add(kDSplitPane2, new KDLayout.Constraints(0, 0, 248, 617, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane2
        kDSplitPane2.add(kDContainer2, "bottom");
        kDContainer2.getContentPane().setLayout(new KDLayout());
        kDContainer2.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 247, 306));        rightMain.setLayout(new KDLayout());
        rightMain.putClientProperty("OriginalBounds", new Rectangle(0, 0, 744, 614));        //treePnl
        treePnl.setLayout(new KDLayout());
        treePnl.putClientProperty("OriginalBounds", new Rectangle(0, 0, 249, 614));        kDSplitPane3.setBounds(new Rectangle(0, 0, 247, 615));
        treePnl.add(kDSplitPane3, new KDLayout.Constraints(0, 0, 247, 615, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane3
        kDSplitPane3.add(contProject, "top");
        kDSplitPane3.add(contChangeType, "bottom");
        //contProject
        contProject.getContentPane().setLayout(new KDLayout());
        contProject.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 246, 299));        kDScrollPane1.setBounds(new Rectangle(0, 0, 247, 281));
        contProject.getContentPane().add(kDScrollPane1, new KDLayout.Constraints(0, 0, 247, 281, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeProject, null);
        //contChangeType
        contChangeType.getContentPane().setLayout(new KDLayout());
        contChangeType.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 246, 304));        kDScrollPane2.setBounds(new Rectangle(0, 0, 247, 283));
        contChangeType.getContentPane().add(kDScrollPane2, new KDLayout.Constraints(0, 0, 247, 283, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDScrollPane2
        kDScrollPane2.getViewport().add(treeChangeType, null);
        //rightPnl
        rightPnl.setLayout(new KDLayout());
        rightPnl.putClientProperty("OriginalBounds", new Rectangle(0, 0, 744, 614));        kDContainer1.setBounds(new Rectangle(2, 1, 742, 617));
        rightPnl.add(kDContainer1, new KDLayout.Constraints(2, 1, 742, 617, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(2, 1, 742, 617));        tblMain.setBounds(new Rectangle(0, 0, 739, 594));
        kDContainer1.getContentPane().add(tblMain, new KDLayout.Constraints(0, 0, 739, 594, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemDisPatch);
        menuBiz.add(menuItemAheadDisPatch);
        menuBiz.add(menuItemSetRespite);
        menuBiz.add(menuItemCancelRespite);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnDisPatch);
        this.toolBar.add(btnAheadDisPatch);
        this.toolBar.add(btnSetRespite);
        this.toolBar.add(btnCancelRespite);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ChangeAuditListUIHandler";
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
     * output treeChangeType_valueChanged method
     */
    protected void treeChangeType_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("changeState"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("curProject.name"));
        sic.add(new SelectorItemInfo("changeReason.name"));
        sic.add(new SelectorItemInfo("auditType.name"));
        sic.add(new SelectorItemInfo("urgentDegree"));
        sic.add(new SelectorItemInfo("jobType.name"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("bookedDate"));
        sic.add(new SelectorItemInfo("period.number"));
        sic.add(new SelectorItemInfo("isRespite"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("specialName"));
        sic.add(new SelectorItemInfo("isOAAudit"));
        return sic;
    }        
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }
    	

    /**
     * output actionTraceUp_actionPerformed method
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }
    	

    /**
     * output actionTraceDown_actionPerformed method
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }
    	

    /**
     * output actionDisPatch_actionPerformed method
     */
    public void actionDisPatch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAheadDisPatch_actionPerformed method
     */
    public void actionAheadDisPatch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSetRespite_actionPerformed method
     */
    public void actionSetRespite_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelRespite_actionPerformed method
     */
    public void actionCancelRespite_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
    }
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAttachment(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
    	return false;
    }
	public RequestContext prepareActionTraceUp(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionTraceUp(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTraceUp() {
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
	public RequestContext prepareActionDisPatch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisPatch() {
    	return false;
    }
	public RequestContext prepareActionAheadDisPatch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAheadDisPatch() {
    	return false;
    }
	public RequestContext prepareActionSetRespite(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSetRespite() {
    	return false;
    }
	public RequestContext prepareActionCancelRespite(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelRespite() {
    	return false;
    }

    /**
     * output ActionDisPatch class
     */     
    protected class ActionDisPatch extends ItemAction {     
    
        public ActionDisPatch()
        {
            this(null);
        }

        public ActionDisPatch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDisPatch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisPatch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisPatch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeAuditListUI.this, "ActionDisPatch", "actionDisPatch_actionPerformed", e);
        }
    }

    /**
     * output ActionAheadDisPatch class
     */     
    protected class ActionAheadDisPatch extends ItemAction {     
    
        public ActionAheadDisPatch()
        {
            this(null);
        }

        public ActionAheadDisPatch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAheadDisPatch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAheadDisPatch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAheadDisPatch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeAuditListUI.this, "ActionAheadDisPatch", "actionAheadDisPatch_actionPerformed", e);
        }
    }

    /**
     * output ActionSetRespite class
     */     
    protected class ActionSetRespite extends ItemAction {     
    
        public ActionSetRespite()
        {
            this(null);
        }

        public ActionSetRespite(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSetRespite.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetRespite.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetRespite.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeAuditListUI.this, "ActionSetRespite", "actionSetRespite_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelRespite class
     */     
    protected class ActionCancelRespite extends ItemAction {     
    
        public ActionCancelRespite()
        {
            this(null);
        }

        public ActionCancelRespite(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancelRespite.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelRespite.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelRespite.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractChangeAuditListUI.this, "ActionCancelRespite", "actionCancelRespite_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ChangeAuditListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}