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
public abstract class AbstractPaymentRelateContractUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentRelateContractUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.swing.KDToolBar PaymentRelateContractUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kdtContract;
    protected com.kingdee.bos.ctrl.swing.KDButton kDButton1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdcboPayCon;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdcboAllCon;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox pmtContract;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtnumber;
    /**
     * output class constructor
     */
    public AbstractPaymentRelateContractUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPaymentRelateContractUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.kdtContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDButton1 = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kdcboPayCon = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdcboAllCon = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.pmtContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtnumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.setName("PaymentRelateContractUI");
        this.toolBar.setName("PaymentRelateContractUI_toolbar");
        this.menuBar.setName("PaymentRelateContractUI_menubar");
        this.kdtContract.setName("kdtContract");
        this.kDButton1.setName("kDButton1");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabel1.setName("kDLabel1");
        this.kdcboPayCon.setName("kdcboPayCon");
        this.kdcboAllCon.setName("kdcboAllCon");
        this.pmtContract.setName("pmtContract");
        this.txtnumber.setName("txtnumber");
        // PaymentRelateContractUI
        // PaymentRelateContractUI_toolbar
        // PaymentRelateContractUI_menubar
        // kdtContract		
        this.kdtContract.setBoundLabelText(resHelper.getString("kdtContract.boundLabelText"));		
        this.kdtContract.setBoundLabelLength(100);		
        this.kdtContract.setBoundLabelUnderline(true);
        // kDButton1		
        this.kDButton1.setText(resHelper.getString("kDButton1.text"));
        this.kDButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDButton1_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kdcboPayCon		
        this.kdcboPayCon.setText(resHelper.getString("kdcboPayCon.text"));
        this.kdcboPayCon.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kdcboPayCon_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdcboAllCon		
        this.kdcboAllCon.setText(resHelper.getString("kdcboAllCon.text"));
        this.kdcboAllCon.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kdcboAllCon_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pmtContract		
        this.pmtContract.setDisplayFormat("$name$");		
        this.pmtContract.setEditFormat("$number$");		
        this.pmtContract.setCommitFormat("$number$");		
        this.pmtContract.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillQuery");
        // txtnumber		
        this.txtnumber.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 400, 150));
        this.setLayout(null);
        kdtContract.setBounds(new Rectangle(15, 51, 365, 19));
        this.add(kdtContract, null);
        kDButton1.setBounds(new Rectangle(305, 105, 73, 21));
        this.add(kDButton1, null);
        kDLabelContainer1.setBounds(new Rectangle(14, 20, 365, 19));
        this.add(kDLabelContainer1, null);
        kDLabel1.setBounds(new Rectangle(15, 81, 97, 19));
        this.add(kDLabel1, null);
        kdcboPayCon.setBounds(new Rectangle(112, 108, 134, 19));
        this.add(kdcboPayCon, null);
        kdcboAllCon.setBounds(new Rectangle(112, 81, 140, 19));
        this.add(kdcboAllCon, null);
        //kdtContract
        kdtContract.setBoundEditor(pmtContract);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtnumber);

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
	    return "com.kingdee.eas.fdc.finance.app.PaymentRelateContractUIHandler";
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
     * output kDButton1_actionPerformed method
     */
    protected void kDButton1_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdcboPayCon_stateChanged method
     */
    protected void kdcboPayCon_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kdcboAllCon_stateChanged method
     */
    protected void kdcboAllCon_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PaymentRelateContractUI");
    }




}