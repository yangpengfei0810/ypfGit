/**
 * output package name
 */
package com.kingdee.eas.fdc.wslog;

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
public abstract class AbstractWebServiceTestUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractWebServiceTestUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.swing.KDToolBar WebServiceTestUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelWS;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelLog;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdPnlClearCde;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTest;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdPannelBrowser;
    protected com.kingdee.bos.ctrl.swing.KDEditorPane kDEditorPaneBrowser;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBillType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOABillID;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBillState;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtIsSuc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lb123;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer cmbState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSearch;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dtime1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbRSState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbBillType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbRecordState;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dtime2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbCallType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOABillId;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdBtnClear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kdTxtAreaClearRslt;
    protected ActionTest actionTest = null;
    protected ActionSearch actionSearch = null;
    protected ActionSave actionSave = null;
    /**
     * output class constructor
     */
    public AbstractWebServiceTestUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractWebServiceTestUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionTest
        this.actionTest = new ActionTest(this);
        getActionManager().registerAction("actionTest", actionTest);
         this.actionTest.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSearch
        this.actionSearch = new ActionSearch(this);
        getActionManager().registerAction("actionSearch", actionSearch);
         this.actionSearch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.panelWS = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelLog = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdPnlClearCde = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnTest = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdPannelBrowser = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDEditorPaneBrowser = new com.kingdee.bos.ctrl.swing.KDEditorPane();
        this.txtBillType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOABillID = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBillState = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtIsSuc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lb123 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cmbState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSearch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dtime1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cmbRSState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cmbBillType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cmbRecordState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.dtime2 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cmbCallType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtOABillId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdBtnClear = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdTxtAreaClearRslt = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.setName("WebServiceTestUI");
        this.toolBar.setName("WebServiceTestUI_toolbar");
        this.menuBar.setName("WebServiceTestUI_menubar");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.panelWS.setName("panelWS");
        this.panelLog.setName("panelLog");
        this.kdPnlClearCde.setName("kdPnlClearCde");
        this.btnTest.setName("btnTest");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kdPannelBrowser.setName("kdPannelBrowser");
        this.kDEditorPaneBrowser.setName("kDEditorPaneBrowser");
        this.txtBillType.setName("txtBillType");
        this.txtOABillID.setName("txtOABillID");
        this.txtBillState.setName("txtBillState");
        this.txtIsSuc.setName("txtIsSuc");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.lb123.setName("lb123");
        this.cmbState.setName("cmbState");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.tblMain.setName("tblMain");
        this.btnSearch.setName("btnSearch");
        this.btnSave.setName("btnSave");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.dtime1.setName("dtime1");
        this.cmbRSState.setName("cmbRSState");
        this.cmbBillType.setName("cmbBillType");
        this.cmbRecordState.setName("cmbRecordState");
        this.dtime2.setName("dtime2");
        this.cmbCallType.setName("cmbCallType");
        this.txtOABillId.setName("txtOABillId");
        this.kdBtnClear.setName("kdBtnClear");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kdTxtAreaClearRslt.setName("kdTxtAreaClearRslt");
        // WebServiceTestUI
        // WebServiceTestUI_toolbar
        // WebServiceTestUI_menubar
        // kDTabbedPane1
        // panelWS
        // panelLog
        // kdPnlClearCde
        // btnTest
        this.btnTest.setAction((IItemAction)ActionProxyFactory.getProxy(actionTest, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTest.setText(resHelper.getString("btnTest.text"));
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
        // kdPannelBrowser
        // kDEditorPaneBrowser		
        this.kDEditorPaneBrowser.setText(resHelper.getString("kDEditorPaneBrowser.text"));
        // txtBillType
        // txtOABillID
        // txtBillState
        // txtIsSuc
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // lb123		
        this.lb123.setBoundLabelText(resHelper.getString("lb123.boundLabelText"));		
        this.lb123.setBoundLabelLength(100);		
        this.lb123.setBoundLabelUnderline(true);
        // cmbState		
        this.cmbState.setBoundLabelText(resHelper.getString("cmbState.boundLabelText"));		
        this.cmbState.setBoundLabelLength(100);		
        this.cmbState.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // tblMain
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isChecked\" t:width=\"30\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"billType\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"sourceBillId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"state\" t:width=\"45\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /><t:Column t:key=\"isClosed\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" /><t:Column t:key=\"callType\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"inParam\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" /><t:Column t:key=\"logTitle\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" /><t:Column t:key=\"logDetail\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" /><t:Column t:key=\"url\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" /><t:Column t:key=\"createTime\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{isChecked}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{billType}</t:Cell><t:Cell>$Resource{sourceBillId}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{isClosed}</t:Cell><t:Cell>$Resource{callType}</t:Cell><t:Cell>$Resource{inParam}</t:Cell><t:Cell>$Resource{logTitle}</t:Cell><t:Cell>$Resource{logDetail}</t:Cell><t:Cell>$Resource{url}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));

        

        // btnSearch
        this.btnSearch.setAction((IItemAction)ActionProxyFactory.getProxy(actionSearch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSearch.setText(resHelper.getString("btnSearch.text"));
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // dtime1
        this.dtime1.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    dtime1_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cmbRSState		
        this.cmbRSState.addItems(resHelper.getArray("cmbRSState.items"));
        // cmbBillType		
        this.cmbBillType.addItems(resHelper.getArray("cmbBillType.items"));
        this.cmbBillType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cmbBillType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cmbRecordState		
        this.cmbRecordState.addItems(resHelper.getArray("cmbRecordState.items"));
        // dtime2
        // cmbCallType		
        this.cmbCallType.addItems(resHelper.getArray("cmbCallType.items"));
        this.cmbCallType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cmbCallType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtOABillId
        // kdBtnClear		
        this.kdBtnClear.setText(resHelper.getString("kdBtnClear.text"));
        this.kdBtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kdBtnClear_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kdTxtAreaClearRslt		
        this.kdTxtAreaClearRslt.setEditable(false);
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
		list.add(this.toolBar);
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1034, 680));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1034, 680));
        kDTabbedPane1.setBounds(new Rectangle(12, 8, 1017, 597));
        this.add(kDTabbedPane1, new KDLayout.Constraints(12, 8, 1017, 597, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDTabbedPane1
        kDTabbedPane1.add(panelWS, resHelper.getString("panelWS.constraints"));
        kDTabbedPane1.add(panelLog, resHelper.getString("panelLog.constraints"));
        kDTabbedPane1.add(kdPnlClearCde, resHelper.getString("kdPnlClearCde.constraints"));
        //panelWS
        panelWS.setLayout(new KDLayout());
        panelWS.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1016, 564));        btnTest.setBounds(new Rectangle(774, 492, 81, 19));
        panelWS.add(btnTest, new KDLayout.Constraints(774, 492, 81, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(16, 16, 576, 19));
        panelWS.add(kDLabelContainer1, new KDLayout.Constraints(16, 16, 576, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(16, 47, 576, 19));
        panelWS.add(kDLabelContainer2, new KDLayout.Constraints(16, 47, 576, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(16, 78, 837, 19));
        panelWS.add(kDLabelContainer3, new KDLayout.Constraints(16, 78, 837, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(18, 110, 837, 356));
        panelWS.add(kDLabelContainer4, new KDLayout.Constraints(18, 110, 837, 356, 0));
        kdPannelBrowser.setBounds(new Rectangle(119, 577, 617, 11));
        panelWS.add(kdPannelBrowser, new KDLayout.Constraints(119, 577, 617, 11, 0));
        kDEditorPaneBrowser.setBounds(new Rectangle(7, 575, 150, 10));
        panelWS.add(kDEditorPaneBrowser, new KDLayout.Constraints(7, 575, 150, 10, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtBillType);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtOABillID);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtBillState);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtIsSuc);
        kdPannelBrowser.setLayout(null);        //panelLog
        panelLog.setLayout(new KDLayout());
        panelLog.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1016, 564));        kDLabelContainer5.setBounds(new Rectangle(14, 13, 270, 19));
        panelLog.add(kDLabelContainer5, new KDLayout.Constraints(14, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(14, 76, 270, 19));
        panelLog.add(kDLabelContainer6, new KDLayout.Constraints(14, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lb123.setBounds(new Rectangle(14, 44, 270, 19));
        panelLog.add(lb123, new KDLayout.Constraints(14, 44, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cmbState.setBounds(new Rectangle(327, 44, 270, 19));
        panelLog.add(cmbState, new KDLayout.Constraints(327, 44, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer9.setBounds(new Rectangle(327, 13, 270, 19));
        panelLog.add(kDLabelContainer9, new KDLayout.Constraints(327, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer7.setBounds(new Rectangle(327, 76, 270, 19));
        panelLog.add(kDLabelContainer7, new KDLayout.Constraints(327, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tblMain.setBounds(new Rectangle(7, 105, 1002, 482));
        panelLog.add(tblMain, new KDLayout.Constraints(7, 105, 1002, 482, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnSearch.setBounds(new Rectangle(917, 12, 76, 19));
        panelLog.add(btnSearch, new KDLayout.Constraints(917, 12, 76, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSave.setBounds(new Rectangle(917, 45, 76, 19));
        panelLog.add(btnSave, new KDLayout.Constraints(917, 45, 76, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer8.setBounds(new Rectangle(641, 76, 353, 19));
        panelLog.add(kDLabelContainer8, new KDLayout.Constraints(641, 76, 353, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(dtime1);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(cmbRSState);
        //lb123
        lb123.setBoundEditor(cmbBillType);
        //cmbState
        cmbState.setBoundEditor(cmbRecordState);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(dtime2);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(cmbCallType);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtOABillId);
        //kdPnlClearCde
        kdPnlClearCde.setLayout(new KDLayout());
        kdPnlClearCde.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1016, 564));        kdBtnClear.setBounds(new Rectangle(23, 22, 114, 40));
        kdPnlClearCde.add(kdBtnClear, new KDLayout.Constraints(23, 22, 114, 40, 0));
        kDLabelContainer10.setBounds(new Rectangle(18, 88, 979, 378));
        kdPnlClearCde.add(kDLabelContainer10, new KDLayout.Constraints(18, 88, 979, 378, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(kdTxtAreaClearRslt);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.wslog.WebServiceTestUIHandler";
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
	 * ????????§µ??
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
     * output dtime1_dataChanged method
     */
    protected void dtime1_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output cmbBillType_actionPerformed method
     */
    protected void cmbBillType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output cmbCallType_actionPerformed method
     */
    protected void cmbCallType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdBtnClear_actionPerformed method
     */
    protected void kdBtnClear_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
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
     * output actionTest_actionPerformed method
     */
    public void actionTest_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSearch_actionPerformed method
     */
    public void actionSearch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionTest(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTest() {
    	return false;
    }
	public RequestContext prepareActionSearch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSearch() {
    	return false;
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }

    /**
     * output ActionTest class
     */     
    protected class ActionTest extends ItemAction {     
    
        public ActionTest()
        {
            this(null);
        }

        public ActionTest(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionTest.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTest.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTest.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebServiceTestUI.this, "ActionTest", "actionTest_actionPerformed", e);
        }
    }

    /**
     * output ActionSearch class
     */     
    protected class ActionSearch extends ItemAction {     
    
        public ActionSearch()
        {
            this(null);
        }

        public ActionSearch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSearch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSearch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSearch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebServiceTestUI.this, "ActionSearch", "actionSearch_actionPerformed", e);
        }
    }

    /**
     * output ActionSave class
     */     
    protected class ActionSave extends ItemAction {     
    
        public ActionSave()
        {
            this(null);
        }

        public ActionSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebServiceTestUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.wslog", "WebServiceTestUI");
    }




}