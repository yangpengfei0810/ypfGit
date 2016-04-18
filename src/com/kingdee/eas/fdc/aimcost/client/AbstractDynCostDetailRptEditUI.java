/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

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
public abstract class AbstractDynCostDetailRptEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDynCostDetailRptEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewProgram;
    protected com.kingdee.bos.ctrl.swing.KDTimePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTimePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtVersion;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefresh;
    protected com.kingdee.eas.fdc.aimcost.DynCostDetailInfo editData = null;
    protected ActionViewProgram actinViewProgram = null;
    protected ActionRefresh actionRefresh = null;
    /**
     * output class constructor
     */
    public AbstractDynCostDetailRptEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDynCostDetailRptEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actinViewProgram
        this.actinViewProgram = new ActionViewProgram(this);
        getActionManager().registerAction("actinViewProgram", actinViewProgram);
         this.actinViewProgram.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefresh
        this.actionRefresh = new ActionRefresh(this);
        getActionManager().registerAction("actionRefresh", actionRefresh);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.btnViewProgram = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDTimePicker();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDTimePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtVersionName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtVersion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.treeProject = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnRefresh = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contState.setName("contState");
        this.contName.setName("contName");
        this.contVersion.setName("contVersion");
        this.contCurProject.setName("contCurProject");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.btnViewProgram.setName("btnViewProgram");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.comboState.setName("comboState");
        this.txtVersionName.setName("txtVersionName");
        this.txtVersion.setName("txtVersion");
        this.prmtCurProject.setName("prmtCurProject");
        this.kdtEntry.setName("kdtEntry");
        this.kDContainer1.setName("kDContainer1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.treeProject.setName("treeProject");
        this.btnRefresh.setName("btnRefresh");
        // CoreUI
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setVisible(false);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);		
        this.contState.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contVersion		
        this.contVersion.setBoundLabelText(resHelper.getString("contVersion.boundLabelText"));		
        this.contVersion.setBoundLabelLength(100);		
        this.contVersion.setBoundLabelUnderline(true);		
        this.contVersion.setVisible(false);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(300);
        // btnViewProgram
        this.btnViewProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actinViewProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewProgram.setText(resHelper.getString("btnViewProgram.text"));
        // pkCreateTime
        // pkLastUpdateTime
        // txtNumber
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.comboState.setEnabled(false);
        // txtVersionName		
        this.txtVersionName.setRequired(true);
        // txtVersion
        // prmtCurProject		
        this.prmtCurProject.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"programmingContractID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"contractID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"programmingContractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"programmingContractName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"promAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"contractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"contractName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"contractParty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"contractPartyC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"contractAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"actualContractingAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"tempAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"changeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"latestCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"cumulativeCashOut\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"cumulativeActuallyPaid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"promBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"needSpendingAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"dynamicCostOneAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"costDiffOneAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"targetCostDiffOneRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"adjustmentDiffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"dynamicCostTwoAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"costDiffTwoAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"targetCostDiffTwoRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"sumLatestCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"dataID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{programmingContractID}</t:Cell><t:Cell>$Resource{contractID}</t:Cell><t:Cell>$Resource{programmingContractNumber}</t:Cell><t:Cell>$Resource{programmingContractName}</t:Cell><t:Cell>$Resource{promAmount}</t:Cell><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{contractNumber}</t:Cell><t:Cell>$Resource{contractName}</t:Cell><t:Cell>$Resource{contractParty}</t:Cell><t:Cell>$Resource{contractPartyC}</t:Cell><t:Cell>$Resource{contractAmount}</t:Cell><t:Cell>$Resource{actualContractingAmount}</t:Cell><t:Cell>$Resource{tempAmount}</t:Cell><t:Cell>$Resource{changeAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{latestCost}</t:Cell><t:Cell>$Resource{cumulativeCashOut}</t:Cell><t:Cell>$Resource{cumulativeActuallyPaid}</t:Cell><t:Cell>$Resource{promBalance}</t:Cell><t:Cell>$Resource{needSpendingAmount}</t:Cell><t:Cell>$Resource{dynamicCostOneAmount}</t:Cell><t:Cell>$Resource{costDiffOneAmount}</t:Cell><t:Cell>$Resource{targetCostDiffOneRate}</t:Cell><t:Cell>$Resource{adjustmentDiffAmount}</t:Cell><t:Cell>$Resource{dynamicCostTwoAmount}</t:Cell><t:Cell>$Resource{costDiffTwoAmount}</t:Cell><t:Cell>$Resource{targetCostDiffTwoRate}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{sumLatestCost}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell><t:Cell>$Resource{dataID}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{programmingContractID_Row2}</t:Cell><t:Cell>$Resource{contractID_Row2}</t:Cell><t:Cell>$Resource{programmingContractNumber_Row2}</t:Cell><t:Cell>$Resource{programmingContractName_Row2}</t:Cell><t:Cell>$Resource{promAmount_Row2}</t:Cell><t:Cell>$Resource{status_Row2}</t:Cell><t:Cell>$Resource{contractNumber_Row2}</t:Cell><t:Cell>$Resource{contractName_Row2}</t:Cell><t:Cell>$Resource{contractParty_Row2}</t:Cell><t:Cell>$Resource{contractPartyC_Row2}</t:Cell><t:Cell>$Resource{contractAmount_Row2}</t:Cell><t:Cell>$Resource{actualContractingAmount_Row2}</t:Cell><t:Cell>$Resource{tempAmount_Row2}</t:Cell><t:Cell>$Resource{changeAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{latestCost_Row2}</t:Cell><t:Cell>$Resource{cumulativeCashOut_Row2}</t:Cell><t:Cell>$Resource{cumulativeActuallyPaid_Row2}</t:Cell><t:Cell>$Resource{promBalance_Row2}</t:Cell><t:Cell>$Resource{needSpendingAmount_Row2}</t:Cell><t:Cell>$Resource{dynamicCostOneAmount_Row2}</t:Cell><t:Cell>$Resource{costDiffOneAmount_Row2}</t:Cell><t:Cell>$Resource{targetCostDiffOneRate_Row2}</t:Cell><t:Cell>$Resource{adjustmentDiffAmount_Row2}</t:Cell><t:Cell>$Resource{dynamicCostTwoAmount_Row2}</t:Cell><t:Cell>$Resource{costDiffTwoAmount_Row2}</t:Cell><t:Cell>$Resource{targetCostDiffTwoRate_Row2}</t:Cell><t:Cell>$Resource{remark_Row2}</t:Cell><t:Cell>$Resource{sumLatestCost_Row2}</t:Cell><t:Cell>$Resource{level_Row2}</t:Cell><t:Cell>$Resource{isLeaf_Row2}</t:Cell><t:Cell>$Resource{dataID_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"1\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"1\" t:right=\"7\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"1\" t:right=\"8\" /><t:Block t:top=\"0\" t:left=\"9\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"0\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"1\" t:right=\"18\" /><t:Block t:top=\"0\" t:left=\"19\" t:bottom=\"1\" t:right=\"19\" /><t:Block t:top=\"0\" t:left=\"20\" t:bottom=\"1\" t:right=\"20\" /><t:Block t:top=\"0\" t:left=\"21\" t:bottom=\"1\" t:right=\"21\" /><t:Block t:top=\"0\" t:left=\"22\" t:bottom=\"1\" t:right=\"22\" /><t:Block t:top=\"0\" t:left=\"23\" t:bottom=\"1\" t:right=\"23\" /><t:Block t:top=\"0\" t:left=\"24\" t:bottom=\"1\" t:right=\"24\" /><t:Block t:top=\"0\" t:left=\"25\" t:bottom=\"1\" t:right=\"25\" /><t:Block t:top=\"0\" t:left=\"26\" t:bottom=\"1\" t:right=\"26\" /><t:Block t:top=\"0\" t:left=\"27\" t:bottom=\"1\" t:right=\"27\" /><t:Block t:top=\"0\" t:left=\"28\" t:bottom=\"1\" t:right=\"28\" /><t:Block t:top=\"0\" t:left=\"29\" t:bottom=\"1\" t:right=\"29\" /><t:Block t:top=\"0\" t:left=\"30\" t:bottom=\"1\" t:right=\"30\" /><t:Block t:top=\"0\" t:left=\"31\" t:bottom=\"1\" t:right=\"31\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    kdtEntry_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntry.putBindContents("editData",new String[] {"programmingContractID","contractID","","","promAmount","state","","","contractParty","contractPartyC","contractAmount","actualContractingAmount","tempAmount","changeAmount","balanceAmount","latestCost","cumulativeCashOut","cumulativeActuallyPaid","promBalance","needSpendingAmount","dynamicCostOneAmount","costDiffOneAmount","targetCostDiffOneRate","adjustmentDiffAmount","dynamicCostTwoAmount","costDiffTwoAmount","targetCostDiffTwoRate","remark","","level","","id"});


        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDScrollPane1
        // treeProject
        this.treeProject.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeProjectreeProject_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnRefresh
        this.btnRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefresh.setText(resHelper.getString("btnRefresh.text"));		
        this.btnRefresh.setToolTipText(resHelper.getString("btnRefresh.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contCreateTime.setBounds(new Rectangle(1000, 86, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(1000, 86, 270, 19, 0));
        contLastUpdateTime.setBounds(new Rectangle(1001, 112, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(1001, 112, 270, 19, 0));
        contNumber.setBounds(new Rectangle(1001, 136, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(1001, 136, 270, 19, 0));
        contState.setBounds(new Rectangle(608, 10, 270, 19));
        this.add(contState, new KDLayout.Constraints(608, 10, 270, 19, 0));
        contName.setBounds(new Rectangle(312, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(312, 10, 270, 19, 0));
        contVersion.setBounds(new Rectangle(1000, 159, 270, 19));
        this.add(contVersion, new KDLayout.Constraints(1000, 159, 270, 19, 0));
        contCurProject.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contCurProject, new KDLayout.Constraints(10, 10, 270, 19, 0));
        kDSplitPane1.setBounds(new Rectangle(10, 35, 993, 584));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 35, 993, 584, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnViewProgram.setBounds(new Rectangle(912, 6, 84, 26));
        this.add(btnViewProgram, new KDLayout.Constraints(912, 6, 84, 26, 0));
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contState
        contState.setBoundEditor(comboState);
        //contName
        contName.setBoundEditor(txtVersionName);
        //contVersion
        contVersion.setBoundEditor(txtVersion);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //kDSplitPane1
        kDSplitPane1.add(kdtEntry, "right");
        kDSplitPane1.add(kDContainer1, "left");
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeProject, null);

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
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnRefresh);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("name", String.class, this.txtVersionName, "text");
		dataBinder.registerBinding("version", java.math.BigDecimal.class, this.txtVersion, "value");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("entries.state", boolean.class, this.kdtEntry, "status.text");
		dataBinder.registerBinding("entries.contractAmount", java.math.BigDecimal.class, this.kdtEntry, "contractAmount.text");
		dataBinder.registerBinding("entries.actualContractingAmount", java.math.BigDecimal.class, this.kdtEntry, "actualContractingAmount.text");
		dataBinder.registerBinding("entries.tempAmount", java.math.BigDecimal.class, this.kdtEntry, "tempAmount.text");
		dataBinder.registerBinding("entries.changeAmount", java.math.BigDecimal.class, this.kdtEntry, "changeAmount.text");
		dataBinder.registerBinding("entries.balanceAmount", java.math.BigDecimal.class, this.kdtEntry, "settlementAmount.text");
		dataBinder.registerBinding("entries.latestCost", java.math.BigDecimal.class, this.kdtEntry, "latestCost.text");
		dataBinder.registerBinding("entries.promBalance", java.math.BigDecimal.class, this.kdtEntry, "promBalance.text");
		dataBinder.registerBinding("entries.needSpendingAmount", java.math.BigDecimal.class, this.kdtEntry, "needSpendingAmount.text");
		dataBinder.registerBinding("entries.dynamicCostOneAmount", java.math.BigDecimal.class, this.kdtEntry, "dynamicCostOneAmount.text");
		dataBinder.registerBinding("entries.costDiffOneAmount", java.math.BigDecimal.class, this.kdtEntry, "costDiffOneAmount.text");
		dataBinder.registerBinding("entries.targetCostDiffTwoRate", java.math.BigDecimal.class, this.kdtEntry, "targetCostDiffTwoRate.text");
		dataBinder.registerBinding("entries.targetCostDiffOneRate", java.math.BigDecimal.class, this.kdtEntry, "targetCostDiffOneRate.text");
		dataBinder.registerBinding("entries.adjustmentDiffAmount", java.math.BigDecimal.class, this.kdtEntry, "adjustmentDiffAmount.text");
		dataBinder.registerBinding("entries.dynamicCostTwoAmount", java.math.BigDecimal.class, this.kdtEntry, "dynamicCostTwoAmount.text");
		dataBinder.registerBinding("entries.costDiffTwoAmount", java.math.BigDecimal.class, this.kdtEntry, "costDiffTwoAmount.text");
		dataBinder.registerBinding("entries.remark", String.class, this.kdtEntry, "remark.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.fdc.aimcost.DynCostDetailDataInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntry, "dataID.text");
		dataBinder.registerBinding("entries.promAmount", java.math.BigDecimal.class, this.kdtEntry, "promAmount.text");
		dataBinder.registerBinding("entries.programmingContractID", String.class, this.kdtEntry, "programmingContractID.text");
		dataBinder.registerBinding("entries.contractID", String.class, this.kdtEntry, "contractID.text");
		dataBinder.registerBinding("entries.level", int.class, this.kdtEntry, "level.text");
		dataBinder.registerBinding("entries.contractParty", String.class, this.kdtEntry, "contractParty.text");
		dataBinder.registerBinding("entries.contractPartyC", String.class, this.kdtEntry, "contractPartyC.text");
		dataBinder.registerBinding("entries.cumulativeCashOut", java.math.BigDecimal.class, this.kdtEntry, "cumulativeCashOut.text");
		dataBinder.registerBinding("entries.cumulativeActuallyPaid", java.math.BigDecimal.class, this.kdtEntry, "cumulativeActuallyPaid.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.aimcost.app.DynCostDetailRptEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.aimcost.DynCostDetailInfo)ov;
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
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("version", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.contractAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.actualContractingAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.tempAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.changeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.balanceAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.latestCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.promBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.needSpendingAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.dynamicCostOneAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.costDiffOneAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.targetCostDiffTwoRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.targetCostDiffOneRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.adjustmentDiffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.dynamicCostTwoAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.costDiffTwoAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.promAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.programmingContractID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.contractID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.contractParty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.contractPartyC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.cumulativeCashOut", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.cumulativeActuallyPaid", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_tableSelectChanged method
     */
    protected void kdtEntry_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output treeProjectreeProject_valueChanged method
     */
    protected void treeProjectreeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("curProject.*"));
    sic.add(new SelectorItemInfo("entries.state"));
    sic.add(new SelectorItemInfo("entries.contractAmount"));
    sic.add(new SelectorItemInfo("entries.actualContractingAmount"));
    sic.add(new SelectorItemInfo("entries.tempAmount"));
    sic.add(new SelectorItemInfo("entries.changeAmount"));
    sic.add(new SelectorItemInfo("entries.balanceAmount"));
    sic.add(new SelectorItemInfo("entries.latestCost"));
    sic.add(new SelectorItemInfo("entries.promBalance"));
    sic.add(new SelectorItemInfo("entries.needSpendingAmount"));
    sic.add(new SelectorItemInfo("entries.dynamicCostOneAmount"));
    sic.add(new SelectorItemInfo("entries.costDiffOneAmount"));
    sic.add(new SelectorItemInfo("entries.targetCostDiffTwoRate"));
    sic.add(new SelectorItemInfo("entries.targetCostDiffOneRate"));
    sic.add(new SelectorItemInfo("entries.adjustmentDiffAmount"));
    sic.add(new SelectorItemInfo("entries.dynamicCostTwoAmount"));
    sic.add(new SelectorItemInfo("entries.costDiffTwoAmount"));
    sic.add(new SelectorItemInfo("entries.remark"));
        sic.add(new SelectorItemInfo("entries.*"));
//        sic.add(new SelectorItemInfo("entries.number"));
    sic.add(new SelectorItemInfo("entries.id"));
    sic.add(new SelectorItemInfo("entries.promAmount"));
    sic.add(new SelectorItemInfo("entries.programmingContractID"));
    sic.add(new SelectorItemInfo("entries.contractID"));
    sic.add(new SelectorItemInfo("entries.level"));
    sic.add(new SelectorItemInfo("entries.contractParty"));
    sic.add(new SelectorItemInfo("entries.contractPartyC"));
    sic.add(new SelectorItemInfo("entries.cumulativeCashOut"));
    sic.add(new SelectorItemInfo("entries.cumulativeActuallyPaid"));
        return sic;
    }        
    	

    /**
     * output actionViewProgram_actionPerformed method
     */
    public void actionViewProgram_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionViewProgram(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewProgram() {
    	return false;
    }
	public RequestContext prepareActionRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefresh() {
    	return false;
    }

    /**
     * output ActionViewProgram class
     */     
    protected class ActionViewProgram extends ItemAction {     
    
        public ActionViewProgram()
        {
            this(null);
        }

        public ActionViewProgram(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewProgram.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewProgram.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewProgram.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDynCostDetailRptEditUI.this, "ActionViewProgram", "actionViewProgram_actionPerformed", e);
        }
    }

    /**
     * output ActionRefresh class
     */     
    protected class ActionRefresh extends ItemAction {     
    
        public ActionRefresh()
        {
            this(null);
        }

        public ActionRefresh(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDynCostDetailRptEditUI.this, "ActionRefresh", "actionRefresh_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "DynCostDetailRptEditUI");
    }




}