package com.kingdee.eas.fdc.finance.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.assistant.PeriodCollection;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.JsDataTools;
import com.kingdee.eas.fdc.contract.ModelFactory;
import com.kingdee.eas.fdc.contract.ModelInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.client.AlterAttachment;
import com.kingdee.eas.fdc.contract.client.BareBonesBrowserLaunch;
import com.kingdee.eas.fdc.contract.client.FDCUtil;
import com.kingdee.eas.fdc.contract.client.OASSOUtil;
import com.kingdee.eas.fdc.finance.OtherPaymentCollection;
import com.kingdee.eas.fdc.finance.OtherPaymentFactory;
import com.kingdee.eas.fdc.finance.OtherPaymentInfo;
import com.kingdee.eas.fdc.wslog.WSLogFactory;
import com.kingdee.eas.fdc.wslog.WSLogInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.landray.kmss.km.importfile.webservice.AttachmentForm;
import com.landray.kmss.km.importfile.webservice.KmReviewParamterForm;
import com.landray.kmss.km.importfile.webservice.KmReviewParamterNoattForm;
import com.landray.kmss.km.importfile.webservice.ZHIKmReviewWebserviceService;
import com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpService;
import com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpServiceLocator;

public class OtherPaymentEditUI extends AbstractOtherPaymentEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(OtherPaymentEditUI.class);
    
    private static KmReviewParamterForm form = null;
	private static KmReviewParamterNoattForm noAttForm = null;
	private static ZHKmReviewWebserviceServiceImpService service = new ZHKmReviewWebserviceServiceImpServiceLocator();
	private static ZHIKmReviewWebserviceService proxy = null;
	private static String oaId="";
	private static String templateId = "";//oa单据模板id
	private static String billState ="";
	private static boolean bl_ok = true;
	private static String fid="";//eas单据id
	private static int num = 100;
	private static String oaBillID ="";
	private static CurProjectInfo PROJECT_INFO = null;//工程项目对象
	private static FDCUtil util;
	private static OtherPaymentListUI listUI=null;
	
	private static String CALLTYPE_SEND ="发送";//写log用的
    
    
    public OtherPaymentEditUI() throws Exception
    {
        super();
    }
    public void loadFields()
    {
        super.loadFields();
        try {
			ICurrency remoteInstance;
			remoteInstance = CurrencyFactory.getRemoteInstance();
			CurrencyCollection currencyCollection = remoteInstance.getCurrencyCollection("where isoCode = 'RMB'");
	        if(currencyCollection.size() > 0 ){
	        	CurrencyInfo currencyInfo = currencyCollection.get(0);
	        	prmtCurrency.setValue(currencyInfo);
	        }
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void getNumberByCodingRule(IObjectValue arg0, String arg1) {
    	System.out.println("--arg0:"+arg0+"  ---arg1:" +arg1);
    	super.getNumberByCodingRule(arg0, arg1);
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
         this.txtPaymentProportion.setHorizontalAlignment(2);		
         this.txtPaymentProportion.setDataType(1);		
         this.txtPaymentProportion.setSupportedEmpty(true);		
         this.txtPaymentProportion.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
         this.txtPaymentProportion.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
         this.txtPaymentProportion.setPrecision(2);		
         
    	this.prmtcontract.setEnabled(false);
    	this.prmtpayRequest.setEnabled(false);
    	this.contDescription.setVisible(false);
    	this.pkauditDate.setEnabled(false);
    	txtparities.setEnabled(false);
    	txtapplyAMTHC.setEnabled(false);
    	txtPaymentProportion.setEnabled(false);
    	
    	//设置必填
    	prmtwithMSector.setRequired(true);
    	prmtpayee.setRequired(true);
    	prmtrealityPayee.setRequired(true);
    	txtNumber.setRequired(true);
    	txtname.setRequired(true);
    	pkBizDate.setRequired(true);
    	prmtapplyPeriod.setRequired(true);
    	pkpaymentDate.setRequired(true);
		prmtpayType.setRequired(true);
		txtapplyAMT.setRequired(true);
		txtapplyAMTHC.setRequired(true);
//		txtPaymentProportion.setRequired(true);
		txtcompletedQuantities.setRequired(true);
		
		Object value = this.prmtorganize.getValue();
		if(value != null ){
			AdminOrgUnitInfo unitInfo = (AdminOrgUnitInfo) value;
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("longNumber",unitInfo.getLongNumber()+"%",CompareType.LIKE));
			viewInfo.setFilter(filterInfo);
			this.prmtwithMSector.setEntityViewInfo(viewInfo);
		}
		
		Set set = new HashSet();
		set.add("结算款");
		set.add("保修款");
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("payType.name",set,CompareType.NOTINCLUDE));
		viewInfo.setFilter(filterInfo);
		prmtpayType.setEntityViewInfo(viewInfo);
		
		changeBizDate(null);
		
		//加载附件到下拉列表
		fillAttachmnetList();
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	this.chkisOAAudit.setEnabled(false);
    	this.contbillTempletID.setVisible(false);
    	this.contOABillID.setVisible(false);
    	pkauditDate.setEnabled(false);
    	boolean selected = this.chkisOAAudit.isSelected();
    	if(selected){
    		this.btnEdit.setEnabled(false);
    		this.btnRemove.setEnabled(false);
    		this.btnCopy.setEnabled(false);
    	}
    }
    
    protected void initListener() {
    	super.initListener();
    	prmtpayee.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent arg0) {
				prmtpayeeDataChanged(arg0);
			}
    	});
    	
    	
    	prmtrealityPayee.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent e) {
				changerRealityPayee(e);
			}
    	});
    	
    	pkBizDate.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent datachangeevent) {
				changeBizDate(datachangeevent);
				
			}
    	});
    }
    
    /**
     * 根据业务日期带出期间
     * @param e
     */
    protected void changeBizDate(DataChangeEvent e){
    	
    	
    	Object newValue = null;
    	if(e != null){
    		newValue = e.getNewValue();
    		Object oldValue = e.getOldValue();
    		if(oldValue == newValue){
    			return;
    		}
    	}else{
    		newValue  = this.pkBizDate.getValue();
    	}
    	if(newValue != null && newValue instanceof Date){
    		Date date = (Date) newValue;
    		int year = date.getYear()+1900;
    		int month = date.getMonth()+1;
    		try {
    			EntityViewInfo viewInfo = new EntityViewInfo();
    			FilterInfo filterInfo = new FilterInfo();
    			filterInfo.getFilterItems().add(new FilterItemInfo("periodYear",year));
    			viewInfo.setFilter(filterInfo);
				PeriodCollection periodCollection = PeriodFactory.getRemoteInstance().getPeriodCollection(viewInfo);
				for (int i = 0; i < periodCollection.size(); i++) {
					PeriodInfo periodInfo = periodCollection.get(i);
					System.out.println(periodInfo.getPeriodNumber());
					if(periodInfo.getPeriodNumber() == month ){
						
						prmtapplyPeriod.setValue(periodInfo);
						prmtapplyPeriod.setDisplayFormat(periodInfo.getPeriodYear()+"年"+periodInfo.getPeriodNumber()+"期");
						prmtapplyPeriod.setData(periodInfo);
						return;
					}
				}
				
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
    		
    	}
    }
    
    //校验实际收款单位
    protected  void changerRealityPayee(DataChangeEvent e){
    	
    }
    
    
    /**
     * .进度比例= 申请金额本币 / 本期完工工程量
     */
    protected void txtcompletedQuantities_dataChanged(DataChangeEvent e)
    		throws Exception {
    	BigDecimal txtapplyAMTHC = this.txtapplyAMTHC.getBigDecimalValue();
    	Object newValue = e.getNewValue();
    	if(txtapplyAMTHC != null && newValue != null ){
    		BigDecimal val = new BigDecimal(newValue.toString());
    		if(val.compareTo(new BigDecimal(0)) != 0){
	    		BigDecimal proportion = txtapplyAMTHC.divide(val,2,4);
	    		this.txtPaymentProportion.setValue(proportion);
    		}else{
        		this.txtPaymentProportion.setValue(null);
        	}
    	}else{
    		this.txtPaymentProportion.setValue(null);
    	}
    }
    
    /**
     * 根据业务日期带出期间
     * @param e
     */
    protected void pkBizDate_dataChanged(DataChangeEvent e) throws Exception {

//        if(this.pkBizDate == null)
//            return;
//        Date bookedDate = (Date)pkBizDate.getValue();
//        PeriodInfo period = null;
//        PeriodInfo oldPeriod = null;
//       
//    	CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
//        period = PeriodUtils.getPeriodInfo(null, bookedDate, new ObjectUuidPK(company.getId().toString()));
//        oldPeriod = PeriodUtils.getPeriodInfo(null, (Date)e.getOldValue(), new ObjectUuidPK(company.getId().toString()));
//        if(period == null)
//        {
//            FDCMsgBox.showConfirm2(this, "\u6CA1\u6709\u5BF9\u5E94\u7684\u671F\u95F4\u3002\u8BF7\u5148\u5728\u57FA\u7840\u8D44\u6599\u7EF4\u62A4\u671F\u95F4");
//            if(oldPeriod != null)
//            	pkBizDate.setValue(e.getOldValue());
//            abort();
//        }
//        prmtapplyPeriod.setValue(period);
//        prmtapplyPeriod.setDisplayFormat(period.getPeriodYear()+"年"+period.getPeriodNumber()+"期");
    
    }
    
    /**
     * 根据收款单位带出银行帐号
     * @param arg0
     */
    protected void prmtpayeeDataChanged(DataChangeEvent arg0){
    	Object newValue = arg0.getNewValue();
    	if(newValue != null ){
    		SupplierInfo supplierInfo = (SupplierInfo) newValue;
//    		SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(supplierInfo.getId()));
    		EntityViewInfo viewInfo = new EntityViewInfo();
    		FilterInfo filterInfo = new FilterInfo();
    		filterInfo.getFilterItems().add(new FilterItemInfo("supplier",supplierInfo.getId()));
    		viewInfo.setFilter(filterInfo);
    		try {
				SupplierCompanyInfoCollection supplierCompanyInfoCollection = SupplierCompanyInfoFactory.getRemoteInstance().getSupplierCompanyInfoCollection(viewInfo);
				for (int i = 0; i < supplierCompanyInfoCollection.size(); i++) {
					SupplierCompanyInfoInfo supplierCompanyInfoInfo = supplierCompanyInfoCollection.get(0);
					SupplierCompanyBankCollection supplierBank = supplierCompanyInfoInfo.getSupplierBank();
					if(supplierBank.size() > 0){
						SupplierCompanyBankInfo companyBankInfo = supplierBank.get(0);
						companyBankInfo = SupplierCompanyBankFactory.getRemoteInstance().getSupplierCompanyBankInfo(new ObjectUuidPK(companyBankInfo.getId()));
						this.txtreceiptNumber.setText(companyBankInfo.getBankAccount());
					}else{
						this.txtreceiptNumber.setText(null);
					}
				}
    		} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}
    	}
    }
    
    /**
     * 加载附件到下拉列表
     * @throws EASBizException
     * @throws BOSException
     * @throws Exception
     */
    public void fillAttachmnetList() throws EASBizException, BOSException, Exception
    {

        kdcbofujian.removeAllItems();
        String boId = null;
        if(editData.getId() == null)
            return;
        boId = editData.getId().toString();
        if(boId != null)
        {
            SelectorItemCollection sic = new SelectorItemCollection();
            sic.add(new SelectorItemInfo("id"));
            sic.add(new SelectorItemInfo("attachment.id"));
            sic.add(new SelectorItemInfo("attachment.name"));
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("boID", boId));
            EntityViewInfo evi = new EntityViewInfo();
            evi.setFilter(filter);
            evi.setSelector(sic);
            BoAttchAssoCollection cols = null;
            try
            {
                cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
            }
            catch(BOSException e)
            {
                e.printStackTrace();
            }
            if(cols != null && cols.size() > 0)
            {
                AttachmentInfo attachment;
                for(Iterator it = cols.iterator(); it.hasNext(); kdcbofujian.addItem(attachment))
                    attachment = ((BoAttchAssoInfo)it.next()).getAttachment();
            } 
        }

    }
    
    
    
    @Override
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	
    	Object [] kjname = {prmtwithMSector,prmtpayee,prmtrealityPayee,txtNumber,txtname,pkBizDate,prmtapplyPeriod,pkpaymentDate,
    			prmtpayType,txtapplyAMT,txtapplyAMTHC,txtPaymentProportion,txtcompletedQuantities};
    	Map map = new HashMap();
    	map.put("prmtwithMSector", "用款部门");
    	map.put("prmtpayee", "收款单位全称");
    	map.put("prmtrealityPayee", "实际收款单位");
    	map.put("txtNumber", "单据编号");
    	map.put("txtname", "单据名称");
    	map.put("pkBizDate", "业务日期");
    	map.put("prmtapplyPeriod", "申请日期");
    	map.put("pkpaymentDate", "付款日期");
    	map.put("prmtpayType", "付款类型");
    	map.put("txtapplyAMT", "申请原币金额");
    	map.put("txtapplyAMTHC", "申请本币金额");
    	map.put("txtPaymentProportion", "进度款付款比例");
    	map.put("txtcompletedQuantities", "本期完工工程量金额");
    	
    	for (int i = 0; i < kjname.length; i++) {
    		if(kjname[i] instanceof KDBizPromptBox){
    			KDBizPromptBox promptBox = (KDBizPromptBox)kjname[i];
    			Object withMSector = promptBox.getValue();
    			if(withMSector == null){
    	    		MsgBox.showWarning(map.get(promptBox.getName())+"不能为空！");
    	    		abort();
    	    	}
    		}else if(kjname[i] instanceof KDTextField){
    			KDTextField textField = (KDTextField)kjname[i];
    			Object withMSector = textField.getText();
    			if(withMSector == null || withMSector.equals("")){
    	    		MsgBox.showWarning(map.get(textField.getName())+"不能为空！");
    	    		abort();
    	    	}
    		}else if(kjname[i] instanceof KDDatePicker){
    			KDDatePicker textField = (KDDatePicker)kjname[i];
    			Object withMSector = textField.getValue();
    			if(withMSector == null){
    	    		MsgBox.showWarning(map.get(textField.getName())+"不能为空！");
    	    		abort();
    	    	}
    		}else if(kjname[i] instanceof KDFormattedTextField ){
    			KDFormattedTextField  textField = (KDFormattedTextField )kjname[i];
    			Object withMSector = textField.getBigDecimalValue();
    			if(withMSector == null){
    	    		MsgBox.showWarning(map.get(textField.getName())+"不能为空！");
    	    		abort();
    	    	}
    		}
		}
    }
    
    
    /**
     * 申请原币
     */
    protected void txtapplyAMT_dataChanged(DataChangeEvent e) throws Exception {
    	super.txtapplyAMT_dataChanged(e);
    	setAmount();
    	BigDecimal txtapplyAMTHC = this.txtapplyAMTHC.getBigDecimalValue();
    	BigDecimal newValue = this.txtcompletedQuantities.getBigDecimalValue();
    	if(txtapplyAMTHC != null && newValue != null && newValue.compareTo(new BigDecimal(0)) != 0){
    		BigDecimal val = new BigDecimal(newValue.toString());
    		BigDecimal proportion = txtapplyAMTHC.divide(val,2,4);
    		this.txtPaymentProportion.setValue(proportion);
    	}else{
    		this.txtPaymentProportion.setValue(null);
    	}
    }
    
    /**
     * 币别
     */
    protected void prmtCurrency_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtCurrency_dataChanged(e);
    	  Object newValue = e.getNewValue();
          if(newValue instanceof CurrencyInfo)
          {
              if(e.getOldValue() != null && ((CurrencyInfo)e.getOldValue()).getId().equals(((CurrencyInfo)newValue).getId()))
                  return;
              BOSUuid srcid = ((CurrencyInfo)newValue).getId();
              Date bookedDate = (Date)pkBizDate.getValue();
              ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, srcid,SysContext.getSysContext().getCurrentFIUnit(), bookedDate);
              int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
              BigDecimal exRate = FDCHelper.ONE;
              int exPrecision = curPrecision;
              if(exchangeRate != null)
              {
                  exRate = exchangeRate.getConvertRate();
                  exPrecision = exchangeRate.getPrecision();
              }
              txtparities.setValue(exRate);
              txtparities.setPrecision(exPrecision);
              txtapplyAMTHC.setPrecision(curPrecision);
              setAmount();
          }
    }
    
    private void setAmount(){
    	BigDecimal amount = (BigDecimal)txtapplyAMT.getNumberValue();
        Object exchangeRate = txtparities.getNumberValue();
        if(amount != null)
        {
            Object value = prmtCurrency.getValue();
            if(value instanceof CurrencyInfo)
            {
                CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
                CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
                BOSUuid srcid = ((CurrencyInfo)value).getId();
                if(baseCurrency != null)
                    if(srcid.equals(baseCurrency.getId()))
                    {
                    	txtapplyAMTHC.setValue(amount);
                    	txtparities.setValue(FDCConstants.ONE);
                    	txtparities.setEditable(false);
                    }
            }
            BigDecimal localamount = (BigDecimal)txtapplyAMTHC.getNumberValue();
            if(exchangeRate instanceof BigDecimal)
            {
                localamount = amount.multiply((BigDecimal)exchangeRate);
                txtapplyAMTHC.setValue(localamount);
            }
        } else
        {
            Object value = prmtCurrency.getValue();
            if(value instanceof CurrencyInfo)
            {
                CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
                CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
                BOSUuid srcid = ((CurrencyInfo)value).getId();
                if(baseCurrency != null)
                {
                    if(srcid.equals(baseCurrency.getId()))
                    {
                    	txtapplyAMTHC.setValue(amount);
                    	txtparities.setValue(FDCConstants.ONE);
                    	txtparities.setEditable(false);
                        return;
                    }
                    txtparities.setEditable(true);
                }
            }
            txtapplyAMTHC.setValue(FDCConstants.ZERO);
        }
    }
    
    
	/**
	 * ************************************************** 方法说明:
	 * getJsonForm(获取单据的json)
	 * 
	 * 参数：@param fid 参数：@return 返回值：String
	 * 
	 * 修改人： 修改时间：2012-8-25 下午03:06:18
	 * ***************************************************
	 */
	private String getJsonForm(String fid) {
		String json = "";
		String org=(prmtorganize.getText()==null)?"":prmtorganize.getText();
		String proj=(prmtcurProject.getText()==null)?"":prmtcurProject.getText();
		String useDepartment=(prmtwithMSector.getText()==null)?"":prmtwithMSector.getText();												
		String supplier=(prmtpayee.getText()==null)?"":prmtpayee.getText();												
		String realSupplier=(prmtrealityPayee.getText()==null)?"":prmtrealityPayee.getText();
		String otherNumber=(txtNumber.getText()==null)?"":txtNumber.getText();
		String otherName = (txtname.getText()==null)?"":txtname.getText();    
		String payment=(prmtpayType.getText()==null)?"":prmtpayType.getText();
		String recBank=(txtreceiptBank.getText()==null)?"":txtreceiptBank.getText();
		String amount=(txtapplyAMT.getText()==null)?"":txtapplyAMT.getText();
		String currency=(prmtCurrency.getText()==null)?"":prmtCurrency.getText();
		String recAccount=(txtreceiptNumber.getText()==null)?"":txtreceiptNumber.getText();
		String moneyDesc=(txtremark.getText()==null)?"":txtremark.getText();
		

		Map map = new HashMap();
		map.put("org_unit",org);
		map.put("project",proj);
		map.put("use_department",useDepartment);
		map.put("rec_supplier",supplier);
		map.put("real_supplier",realSupplier);
		map.put("other_bill_number",otherNumber);
		map.put("other_bill_name",otherName);
		map.put("pay_type",payment);
		map.put("rec_bank",recBank);
		map.put("rec_account",recAccount);
		map.put("currency",currency);
		map.put("amount",amount);
		map.put("desc",moneyDesc);


		JsDataTools js = new JsDataTools();
		json = js.createJsData(map);
		System.out.println("----其它付款单'" + otherNumber+otherName + "'-----json:" + json);

		return json;
	}
    
	/**
	 * 带出银行帐号
	 */
	protected void prmtrealityPayee_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtrealityPayee_dataChanged(e);
        Object newValue = e.getNewValue();
        if(newValue instanceof SupplierInfo)
        {
            if((e.getOldValue() instanceof SupplierInfo) && ((SupplierInfo)e.getOldValue()).getId().equals(((SupplierInfo)newValue).getId()) && !getOprtState().equals(OprtState.ADDNEW))
                return;
            if(PROJECT_INFO == null){
            	Object value = this.prmtcurProject.getValue();
            	CurProjectInfo object = (CurProjectInfo) value;
            	PROJECT_INFO = object;
            }
            
            SupplierInfo supplier = (SupplierInfo)newValue;
            BOSUuid supplierid = supplier.getId();
            String supperid = supplierid.toString();
            FDCSQLBuilder builder = new FDCSQLBuilder();
            builder.clear();
            builder.appendSql("select b.fbank,b.fbankaccount from T_BD_SupplierCompanyInfo a ");
            builder.appendSql("inner join T_BD_SupplierCompanyBank b on a.fid=b.fsuppliercompanyinfoid ");
            builder.appendSql("where a.fcontrolunitid= ");
            builder.appendParam(PROJECT_INFO.getCU().getId().toString());
            builder.appendSql(" and a.fsupplierid=");
            builder.appendParam(supperid);
            builder.appendSql(" order by b.fseq");
            IRowSet rowSet = builder.executeQuery();
            if(rowSet != null && rowSet.next())
            {
                String bank = rowSet.getString("fbank");
                String bankaccount = rowSet.getString("fbankaccount");
                editData.setReceiptBank(bank);
                editData.setReceiptNumber(bankaccount);
            } else
            {
            	 editData.setReceiptBank(null);
                 editData.setReceiptNumber(null);
            }
            txtreceiptNumber.setText(editData.getReceiptNumber());
            txtreceiptBank.setText(editData.getReceiptBank());
        }
	}
    
    
    public String getHTML(String fid){
    	
    	String html = "";
    	String org = "";// 组织
		String proj="";//工程项目
		String contractNo="";//合同号
		String useDepartment="";//用款部门
		String supplier="";//收款单位全称
		String realSupplier="";//实际收款单位
		String payRequnum = "";//付款申请单
		String otherNumber="";//其它付款单编号
		String otherName="";//其它付款单名称
		String bookedDate="";//业务日期
		String period="";//申请日期
		String billstate="";//状态
		String payDate="";//付款日期
		String payment="";//付款类型
		String recBank="";//收款银行
		String amount="";//原币金额
		String currency="";//币别
		String recAccount="";//收款账号
		String bcAmount="";//本币金额
		String exchangeRate="";//汇率
		String invoiceNumber="";//发票号
		String paymentProportion="";//进度款付款比例
		String completePrjAmt="";//本期完工工程量金额
		String invoiceAmt="";//发票金额
		String attachment="";//附件
		String moneyDesc="";//备注
		String promptCreator="";//制单人
		String dateCreateTim="";//制单日期
		String promptAuditor="";//审核人
		String auditDate="";//审核日期
    	
		org=(prmtorganize.getText()==null)?"":prmtorganize.getText();
		proj=(prmtcurProject.getText()==null)?"":prmtcurProject.getText();
		contractNo=(prmtcontract.getText()==null)?"":prmtcontract.getText();
		payRequnum =(prmtpayRequest.getText()==null)?"":prmtpayRequest.getText();
		useDepartment=(prmtwithMSector.getText()==null)?"":prmtwithMSector.getText();
		bookedDate=(pkBizDate.getText()==null)?"":pkBizDate.getText();
		otherNumber=(txtNumber.getText()==null)?"":txtNumber.getText();
		otherName = (txtname.getText()==null)?"":txtname.getText();
		payDate=(pkpaymentDate.getText()==null)?"":pkpaymentDate.getText();
		period=(prmtapplyPeriod.getText()==null)?"":prmtapplyPeriod.getText();
		billstate = (billstates.getSelectedItem()== null)?"":billstates.getSelectedItem().toString();
		payment=(prmtpayType.getText()==null)?"":prmtpayType.getText();
		supplier=(prmtpayee.getText()==null)?"":prmtpayee.getText();
		recBank=(txtreceiptBank.getText()==null)?"":txtreceiptBank.getText();
		realSupplier=(prmtrealityPayee.getText()==null)?"":prmtrealityPayee.getText();
		recAccount=(txtreceiptNumber.getText()==null)?"":txtreceiptNumber.getText();
		currency=(prmtCurrency.getText()==null)?"":prmtCurrency.getText();
		exchangeRate=(txtparities.getText()==null)?"":txtparities.getText();
		paymentProportion=(txtPaymentProportion.getText()==null)?"":txtPaymentProportion.getText();
		amount=(txtapplyAMT.getText()==null)?"":txtapplyAMT.getText();
		completePrjAmt=(txtcompletedQuantities.getText()==null)?"":txtcompletedQuantities.getText();
		bcAmount=(txtapplyAMTHC.getText()==null)?"":txtapplyAMTHC.getText();
		invoiceNumber=(txtinvoiceNumber.getText()==null)?"":txtinvoiceNumber.getText();
		invoiceAmt=(txtinvoiceAMT.getText()==null)?"":txtinvoiceAMT.getText();
		//附件
		if(kdcbofujian.getItemCount()>0){
			String atts="";
			  for(int i=0;i<kdcbofujian.getItemCount();i++){
				  if(kdcbofujian.getItemAt(i)!=null){
					  atts=atts+kdcbofujian.getItemAt(i)+",";
				  }
			  }
		    int num= atts.lastIndexOf(",");
		    attachment=atts.substring(0, num);
		}
		moneyDesc=(txtremark.getText()==null)?"":txtremark.getText();
		promptCreator=(prmtCreator.getText()==null)?"":prmtCreator.getText();
		dateCreateTim=(kDDateCreateTime.getText()==null)?"":kDDateCreateTime.getText();
		promptAuditor=(prmtAuditor.getText()==null)?"":prmtAuditor.getText();
		auditDate=(pkauditDate.getText()==null)?"":pkauditDate.getText();
    	
		html="\r\n<table class='tb_normal' width=100% height='151' border='1'>  \r\n" +
				"<tr>" +
				"<td class='td_normal_title_head' width=100% height='25' colspan='6'>其它付款单</td>" 
				+"</tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' width=16% height='25' bgcolor='#66FFFF'>组织</td>" +
				"    <td width=16% >"+org+"</td>" +
				"    <td class='td_normal_title' width=16% >工程项目</td>" +
				"    <td width=16% >"+proj+"</td>" +
				"    <td class='td_normal_title' width=16% >合同</td>" +
				"    <td width=16% >"+contractNo+"</td>" +
				"</tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>用款部门</td>" +
				"    <td>"+useDepartment+"</td>" +
				"    <td class='td_normal_title' >收款单位全称</td>" +
				"    <td>"+supplier+"</td>" +
				"    <td class='td_normal_title' >实际收款单位</td>" +
				"    <td>"+realSupplier+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>付款申请单</td>" +
				"    <td>"+payRequnum+"</td>" +
				"    <td class='td_normal_title' >单据编号</td>" +
				"    <td>"+otherNumber+"</td>" +
				"    <td class='td_normal_title' >单据名称</td>" +
				"    <td>"+otherName+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>业务日期</td>" +
				"    <td>"+bookedDate+"</td>" +
				"    <td class='td_normal_title' >申请期间</td>" +
				"    <td>"+period+"</td>" +
				"    <td class='td_normal_title' >状态</td>" +
				"    <td>"+billstate+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>付款日期</td>" +
				"    <td>"+payDate+"</td>" +
				"    <td class='td_normal_title' >付款类型</td>" +
				"    <td>"+payment+"</td>" +
				"    <td class='td_normal_title' >收款银行</td>" +
				"    <td>"+recBank+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>申请金额（原币）</td>" +
				"    <td>"+amount+"</td>" +
				"    <td class='td_normal_title' >币别</td>" +
				"    <td>"+currency+"</td>" +
				"    <td class='td_normal_title' >收款帐号</td>" +
				"    <td>"+recAccount+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>申请金额（本币）</td>" +
				"    <td>"+bcAmount+"</td>" +
				"    <td class='td_normal_title' >汇率</td>" +
				"    <td>"+exchangeRate+"</td>" +
				"    <td class='td_normal_title' >发票号</td>" +
				"    <td>"+invoiceNumber+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>进度款付款比例</td>" +
				"    <td>"+paymentProportion+"</td>" +
				"    <td>本期完工工程量</td>" +
				"    <td>"+completePrjAmt+"</td>" +
				"    <td class='td_normal_title' >发票金额</td>" +
				"    <td>"+invoiceAmt+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>附件列表</td>" +
				"    <td colspan='5'>"+attachment+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>备注</td>" +
				"    <td colspan='5'>"+moneyDesc+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>审核人</td>" +
				"    <td colspan='2'>"+promptAuditor+"</td>" +
				"    <td class='td_normal_title' >创建者</td>" +
				"    <td colspan='2'>"+promptCreator+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>审核日期</td>" +
				"    <td colspan='2'>"+auditDate+"</td>" +
				"    <td>创建日期</td>" +
				"    <td colspan='2'>"+dateCreateTim+"</td>" +
				"  </tr>" +
				"</table>";
		    	
    	
    	return html;
    }
    
    public void resetParam()
	{
		form = null;
		noAttForm = null;
		proxy = null;
		oaId="";
		templateId = "";
		billState ="";
		bl_ok = true;
		fid="";
		num = 100;
		oaBillID ="";
		PROJECT_INFO = null;
	}
    
    private String getBillState(String id)
	{
		String state = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		
		try {
			OtherPaymentCollection col = OtherPaymentFactory.getRemoteInstance().getOtherPaymentCollection(view);
			if(col != null && col.size() > 0)
			{
				state = (col.get(0).getBillstates() == null)?"":col.get(0).getBillstates().toString();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return state;
	}
	
	private String getOABillTemplet(String id)
	{
		String billTempletID = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		
		try {
			OtherPaymentCollection col = OtherPaymentFactory.getRemoteInstance().getOtherPaymentCollection(view);
			if(col != null && col.size() > 0)
			{
				billTempletID = (col.get(0).getBillTempletID() == null)?"":col.get(0).getBillTempletID().toString();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return billTempletID;
	}
	
	//获取项目工程对象
	private CurProjectInfo getPrjInfo(String id)
	{
		CurProjectInfo info=null;
		String prjID = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		
		try {
			OtherPaymentCollection col = OtherPaymentFactory.getRemoteInstance().getOtherPaymentCollection(view);
			if(col != null && col.size() > 0)
			{
				prjID = (col.get(0).getCurProject() == null)?"":col.get(0).getCurProject().getId().toString();
				EntityViewInfo view1 = new EntityViewInfo();
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", prjID));
				view1.setFilter(filter1);
				CurProjectCollection prjCol = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view1);
				if(prjCol !=null && prjCol.size()>0)
				{
				   info = prjCol.get(0);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	

	public void rebackRefreshListUI(ActionEvent e)
	{
		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("VIEW")||oprtState.equals("EDIT"))
			{
			   listUI = (OtherPaymentListUI) this.getUIContext().get("Owner");
			   try {
				    listUI.actionRefresh_actionPerformed(e);
			   } catch (Exception e1) {
					e1.printStackTrace();
			   }
			}
		}
	}
	
	//是否走OA审批
	private int isOAAudit()
	{
		return MsgBox.showConfirm3("请选择是否走OA审批流程？");
	}
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSubmit();
    	//重置静态变量
		resetParam();
		
		try {
			super.verifyData();
		} catch (Exception e3) {
			e3.printStackTrace();
			SysUtil.abort();
		}
		if(oprtState.equals("ADDNEW"))
		{
			 actionSave_actionPerformed(e);
			
			//初始化fid
			 fid = editData.getId().toString();
			
			//保存以后才能娶到单据状态
			 billState = getBillState(fid);
		}
		else if(oprtState.equals("EDIT"))
		{
			 fid = (editData.getId()==null)?"":editData.getId().toString();
			 billState = getBillState(fid);
			 if(billState.equals("驳回"))
			 {
				 templateId = getOABillTemplet(fid);
			 }
		}else
		{
			SysUtil.abort();
		}
		
		boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT billstates state,cfisoaaudit isOAAudit FROM CT_FNC_OtherPayment WHERE fid='"+fid+"'");
		if(!isEnableSubmit)
		{
			MsgBox.showInfo("单据已经提交OA走审批流程，不能重复提交");
			SysUtil.abort();
		}
		
	    num = isOAAudit();
	    
		//1、选择是否走OA审批
		if(num == 0)
		{
			PROJECT_INFO=getPrjInfo(fid);//初始化项目工程对象
			
			//判断是否存在附件
			String sql = "SELECT t1.fid billID,t3.fid attachmentID FROM CT_FNC_OtherPayment t1 left join T_BAS_BoAttchAsso t2 on t1.fid=t2.fboid left join  T_BAS_Attachment t3 on t2.fattachmentid =t3.fid WHERE t1.fid='"+fid+"'";
			 util = new FDCUtil();
			if(!util.checkExistAttachment(sql))
			{
				actionSubmit_no_attachment(e);
				SysUtil.abort();
			}
			
			bl_ok = true;
			String jsonForm = getJsonForm((String) fid);
			String formHTML="";
			formHTML = getHTML(fid);
		    System.out.println("------actionSubmit_actionPerformed-----fid:"+fid);
	         
		    //如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
		    String prjMappingName = PROJECT_INFO.getPrjMappingName();
		    
		  //工程项目+付款类型+单据名称
			String subject = prmtcurProject.getText()+prmtpayType.getText()+txtname.getText();
		    System.out.println("--------subject主题："+subject);
		    
			if(prjMappingName ==null || prjMappingName.equals(""))
			{
				if(MsgBox.showConfirm3("该工程项目没有配置映射名称有可能取不到模板，是否还要继续？")==MsgBox.OK)
				{
					
				}else{
				   SysUtil.abort();
				}
			}
			
			//2、选择模板
		    //获取蓝凌OA提供模板并保存到eas中
			List modelIDList = null;
			String keyForModel = "EAS7("+prjMappingName+")";
			if(billState != null && billState != "" && !billState.equals("驳回"))
			{
				getOAModelAndSaveToEAS(keyForModel,fid);
			    modelIDList = showFilterDialog(fid);
			}
			
			//4、准备要发起流程的数据，并传递给oa审批
			AlterAttachment at = new AlterAttachment();
			String creator = SysContext.getSysContext().getCurrentUserInfo().getNumber();
			
			if(billState != null && billState != "" && !billState.equals("驳回"))
			{
				templateId = (modelIDList.size()>0) ? modelIDList.get(0).toString() : "";
			}
			oaBillID = getOAID(fid);
			System.out.println("-------驳回重新提交时根据单据id查找到的oa单据id:"+oaBillID);
			
			//mod by ypf on 20140827 发起账号要是json
			String creatorJsonString = "";
			try {
				JSONObject creatorJson = new JSONObject();
				creatorJsonString = creatorJson.put("LoginName", creator)+"";
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			AttachmentForm [] forms = at.getAttacmentInfo(fid);
			form = new KmReviewParamterForm();
			form.setDocCreator(creatorJsonString);
			form.setDocSubject(subject);
			form.setFdTemplateId(templateId);
			form.setFormValues(jsonForm);
			form.setEasHtml(formHTML);
			form.setAttachmentForms(forms);
			form.setDocId(oaBillID);//oa单据id   oaBillID
			System.out.println("---form----creator:"+creator+"  "+(oprtState.equals("ADDNEW")?"新增状态":"编辑状态")+"oaBillID:"+oaBillID+"  subject:"+subject+"   templateId:"+templateId+"   jsonForm:"+jsonForm+"  formHTML"+formHTML);
			
			try {
				proxy = service.getZHKmReviewWebserviceServiceImpPort();
				bl_ok = true ;
			} catch (ServiceException e2) {
				e2.printStackTrace();
				
				if(MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存提交？")==MsgBox.OK)
				{
					try {
						this.billstates.setSelectedItem(FDCBillStateEnum.SUBMITTED);
						super.actionSubmit_actionPerformed(e);
					} catch (Exception e1) {
						e1.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("提交时失败");
						logInfo.setLogDetail(e1.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS7");
						WSLogFactory.getRemoteInstance().addnew(logInfo);
						
						SysUtil.abort();
					}
				}
				SysUtil.abort();
			}
			
			//5、检查是否有待办记录
			try {
				proxy.delTasksByID(fid);
			} catch (Exception e1) {
				e1.printStackTrace();
				if(MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存？")==MsgBox.OK)
				{
					try {
						super.actionSave_actionPerformed(e);
					} catch (Exception e11) {
						e11.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("远程调用OA接口失败时对当前单据进行保存失败");
						logInfo.setLogDetail(e11.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS7");
						WSLogFactory.getRemoteInstance().addnew(logInfo);
						
						SysUtil.abort();
					}
				}
				SysUtil.abort();
			}
			
			//6、调用oa的流程发起接口传值
			try {
				LongTimeDialog dialog = UITools.getDialog(this);
				dialog.setTitle("提示");//提示框标题
				if(dialog==null)
					return;
		        dialog.setLongTimeTask(new ILongTimeTask() {
		            public Object exec() throws Exception {
		            	//加入要等待的代码块
		            	try {
		            		oaId = proxy.addReview(form);
		            		if(!oprtState.equals("ADDNEW") && !oaBillID.equals(""))
		            		{
		            		   oaId = oaBillID;
		            		}
						} catch (Exception e2) {
							bl_ok = false;
							MsgBox.showDetailAndOK(null, "调用OA接口传送单据数据失败. \r\n点击详细信息查看详情.", e2.toString(), 1);
							System.out.println("-----------e2:"+e2);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("失败");
							logInfo.setLogTitle("调用OA接口传送单据数据时失败");
							logInfo.setLogDetail(e2.toString());
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS7");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
		            		SysUtil.abort();
						}
		            	System.out.println("-----向oa传送数据后的----oaId:"+oaId);
		                return oaId;
		            }
		            public void afterExec(Object result) throws Exception {
		                
		            }
		        });
		        Component[] cps=dialog.getContentPane().getComponents();  
		        for(Component cp:cps){  
		            if(cp instanceof JLabel){  
		            	//提示内容
		                ((JLabel) cp).setText("请等待，正在调用OA接口传送数据......");  
		            }  
		        }
		        dialog.show();
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "调用OA接口传送单据数据失败. \r\n点击详细信息查看详情.", e1.toString(), 1);
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("失败");
				logInfo.setLogTitle("调用OA接口传送单据数据失败. ");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS7");
				try {
					WSLogFactory.getRemoteInstance().addnew(logInfo);
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				SysUtil.abort();
			}
			
			//7、更新eas合同单据的‘是否走OA审批’字段
			if(bl_ok)
			{
				if(!updateOtherPayment(fid,true,templateId,oaId)){
					MsgBox.showInfo("回写EAS单据失败");
					SysUtil.abort();
				}else
				{
					MsgBox.showInfo("发起OA审批流程成功");
					
					try {
						String url = OASSOUtil.getSSOURL(creator, oaId);
						System.out.println("-----sso url:"+url);
						if(!url.equals(""))
						{
							this.billstates.setSelectedItem(FDCBillStateEnum.SUBMITTED);
							super.actionSubmit_actionPerformed(e);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("成功");
							logInfo.setLogTitle("流程发起成功");
							logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
							logInfo.setUrl(url);
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS7");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							//mod by ypf on 20121111 刷新listui
							rebackRefreshListUI(e);
							
							//先保存再关闭，否则会弹出“数据已修改，是否需要保存”提示
							uiWindow.close();
							BareBonesBrowserLaunch.openURL(url);
						}else
						{
							MsgBox.showInfo("没有获取到单点登录URL");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						MsgBox.showDetailAndOK(null, "单点登录OA失败,请直接登录OA查看流程. \r\n点击详细信息查看详情.", e1.toString(), 1);
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("单点登录OA失败,请直接登录OA查看流程");
						logInfo.setLogDetail(e1.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS7");
						WSLogFactory.getRemoteInstance().addnew(logInfo);
					}
				}
			}else{
				MsgBox.showWarning("发起OA审批流程失败");
				SysUtil.abort();
			}
			if(bl_ok)
			{
				System.out.println("-----复制这条链接登录oa查看（用户名："+creator+"  密码：1）------http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
			}
		}else if(num == 1)
		{
			//直接保存
			try {
				this.billstates.setSelectedItem(FDCBillStateEnum.SUBMITTED);
				super.actionSubmit_actionPerformed(e);
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "提交失败. \r\n点击详细信息查看详情.", e1.toString(), 1);
				System.out.println("----------"+e1.toString());
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("失败");
				logInfo.setLogTitle("直接在EAS提交时失败-不走OA工作流审批");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS7");
				WSLogFactory.getRemoteInstance().addnew(logInfo);
				
				SysUtil.abort();
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", fid));
			view.setFilter(filter);
			OtherPaymentCollection col;
			try {
				col = OtherPaymentFactory.getRemoteInstance().getOtherPaymentCollection(view);
				OtherPaymentInfo info = new OtherPaymentInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//更新附件内容
					   OtherPaymentFactory.getRemoteInstance().updatePartial(info, selector);
				   } catch (Exception e1) {
					   e1.printStackTrace();
				  } 
				}
			} catch (BOSException e1) {
				e1.printStackTrace();
				
			}
			
			MsgBox.showInfo("提交保存成功");

			//mod by ypf on 20121111 刷新listui
			rebackRefreshListUI(e);
		}else
		{
			SysUtil.abort();
		}
		
		deleteModel(fid);
    }
    
    public void actionSubmit_no_attachment(ActionEvent e) {
		bl_ok = true;
		String jsonForm = getJsonForm((String) fid);
		String formHTML="";
		formHTML = getHTML(fid);
	    System.out.println("------actionSubmit_actionPerformed-----fid:"+fid);
         
	    //如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
	    String prjMappingName = PROJECT_INFO.getPrjMappingName();
		
	    //工程项目+付款类型+单据名称
		String subject = prmtcurProject.getText()+prmtpayType.getText()+txtname.getText();
	    System.out.println("--------subject主题："+subject);

	    //1、选择是否走OA审批
		if(num == 0)
		{
			if(prjMappingName ==null || prjMappingName.equals(""))
			{
				if(MsgBox.showConfirm3("该工程项目没有配置映射名称有可能取不到模板，是否还要继续？")==MsgBox.OK)
				{
					
				}else{
				   SysUtil.abort();
				}
			}
			
			//2、选择模板
		    //获取蓝凌OA提供模板并保存到eas中
			List modelIDList = null;
			String keyForModel = "EAS7("+prjMappingName+")";
			if(oprtState.equals("ADDNEW") || (billState != null && billState != "" && !billState.equals("驳回")))
			{
				getOAModelAndSaveToEAS(keyForModel,fid);
			    modelIDList = showFilterDialog(fid);
			}
			
			//4、准备要发起流程的数据，并传递给oa审批
			AlterAttachment at = new AlterAttachment();
			String creator = SysContext.getSysContext().getCurrentUserInfo().getNumber();
			
			if(oprtState.equals("ADDNEW") || (billState != null && billState != "" && !billState.equals("驳回")))
			{
				templateId = (modelIDList.size()>0) ? modelIDList.get(0).toString() : "";
			}
			oaBillID = getOAID(fid);
			System.out.println("-------驳回重新提交时根据单据id查找到的oa单据id:"+oaBillID);
			
			//mod by ypf on 20140827 发起账号要是json
			String creatorJsonString = "";
			try {
				JSONObject creatorJson = new JSONObject();
				creatorJsonString = creatorJson.put("LoginName", creator)+"";
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			noAttForm = new KmReviewParamterNoattForm();
			noAttForm.setDocCreator(creatorJsonString);
			noAttForm.setDocSubject(subject);
			noAttForm.setFdTemplateId(templateId);
			noAttForm.setFormValues(jsonForm);
			noAttForm.setEasHtml(formHTML);
			noAttForm.setDocId(oaBillID);//oa单据id   oaBillID
			System.out.println("---form----creator:"+creator+"  oaBillID:"+oaBillID+"  subject:"+subject+"   templateId:"+templateId+"   jsonForm:"+jsonForm+"  formHTML"+formHTML);
			
			try {
				proxy = service.getZHKmReviewWebserviceServiceImpPort();
				bl_ok = true ;
			} catch (ServiceException e2) {
				e2.printStackTrace();
				
				if(MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存提交？")==MsgBox.OK)
				{
					try {
						super.actionSubmit_actionPerformed(e);
					} catch (Exception e1) {
						e1.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("提交时失败");
						logInfo.setLogDetail(e1.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS7");
						try {
							WSLogFactory.getRemoteInstance().addnew(logInfo);
						} catch (BOSException e3) {
							e3.printStackTrace();
						}
						
						SysUtil.abort();
					}
				}
				SysUtil.abort();
			}
			
			//5、检查是否有待办记录
			try {
				proxy.delTasksByID(fid);
			} catch (Exception e1) {
				e1.printStackTrace();
				if(MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存？")==MsgBox.OK)
				{
					try {
						super.actionSave_actionPerformed(e);
					} catch (Exception e11) {
						e11.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("远程调用OA接口失败时对当前单据进行保存失败");
						logInfo.setLogDetail(e11.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS7");
						try {
							WSLogFactory.getRemoteInstance().addnew(logInfo);
						} catch (BOSException e2) {
							e2.printStackTrace();
						}
						
						SysUtil.abort();
					}
				}
				SysUtil.abort();
			}
			
			//6、调用oa的流程发起接口传值
			try {
				LongTimeDialog dialog = UITools.getDialog(this);
				dialog.setTitle("提示");//提示框标题
				if(dialog==null)
					return;
		        dialog.setLongTimeTask(new ILongTimeTask() {
		            public Object exec() throws Exception {
		            	//加入要等待的代码块
		            	try {
		            		oaId = proxy.addReviewNoatt(noAttForm);
		            		if(!oprtState.equals("ADDNEW") && !oaBillID.equals(""))
		            		{
		            		   oaId = oaBillID;
		            		}
						} catch (Exception e2) {
							bl_ok = false;
							MsgBox.showDetailAndOK(null, "调用OA接口传送单据数据失败. \r\n点击详细信息查看详情.", e2.toString(), 1);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("失败");
							logInfo.setLogTitle("调用OA接口传送单据数据时失败");
							logInfo.setLogDetail(e2.toString());
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS7");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
		            		SysUtil.abort();
						}
		            	System.out.println("-----向oa传送数据后的----oaId:"+oaId);
		                return oaId;
		            }
		            public void afterExec(Object result) throws Exception {
		                
		            }
		        });
		        Component[] cps=dialog.getContentPane().getComponents();  
		        for(Component cp:cps){  
		            if(cp instanceof JLabel){  
		            	//提示内容
		                ((JLabel) cp).setText("请等待，正在调用OA接口传送数据......");  
		            }  
		        }
		        dialog.show();
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "远程调用OA接口传送数据失败. \r\n点击详细信息查看详情.", e1.toString(), 1);
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("失败");
				logInfo.setLogTitle("单点登录失败,请登录OA查看");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS7");
				try {
					WSLogFactory.getRemoteInstance().addnew(logInfo);
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				
				SysUtil.abort();
			}
			
			//7、更新eas合同单据的‘是否走OA审批’字段
			if(bl_ok)
			{
				if(!updateOtherPayment(fid,true,templateId,oaId)){
					MsgBox.showInfo("回写EAS单据失败");
					SysUtil.abort();
				}else
				{
					MsgBox.showInfo("发起OA审批流程成功");
					try {
						String url = OASSOUtil.getSSOURL(creator, oaId);
						System.out.println("-----sso url:"+url);
						if(!url.equals(""))
						{
							this.billstates.setSelectedItem(FDCBillStateEnum.SUBMITTED);
							super.actionSubmit_actionPerformed(e);
							//先保存再关闭，否则会弹出“数据已修改，是否需要保存”提示
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("成功");
							logInfo.setLogTitle("流程发起成功");
							logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
							logInfo.setUrl(url);
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS7");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							//mod by ypf on 20121111 刷新listui
							rebackRefreshListUI(e);
							
							uiWindow.close();
							
							BareBonesBrowserLaunch.openURL(url);
						}else
						{
							MsgBox.showInfo("没有获取到单点登录URL");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						MsgBox.showDetailAndOK(null, "单点登录OA失败,请直接登录OA查看流程. \r\n点击详细信息查看详情.", e1.toString(), 1);
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("单点登录OA失败,请直接登录OA查看流程");
						logInfo.setLogDetail(e1.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS7");
						try {
							WSLogFactory.getRemoteInstance().addnew(logInfo);
						} catch (BOSException e2) {
							e2.printStackTrace();
						}
					}
				}
			}else{
				MsgBox.showWarning("发起OA审批流程失败");
				SysUtil.abort();
			}
			if(bl_ok)
			{
				System.out.println("-----复制这条链接登录oa查看（用户名："+creator+"  密码：1）------http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
			}
		}else if(num == 1)
		{
			//直接保存
			try {
				this.billstates.setSelectedItem(FDCBillStateEnum.SUBMITTED);
				super.actionSubmit_actionPerformed(e);
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "提交失败. \r\n点击详细信息查看详情.", e1.toString(), 1);
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("失败");
				logInfo.setLogTitle("直接在EAS提交时失败-不走OA工作流审批");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS7");
				try {
					WSLogFactory.getRemoteInstance().addnew(logInfo);
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				SysUtil.abort();
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", fid));
			view.setFilter(filter);
			OtherPaymentCollection col;
			try {
				col = OtherPaymentFactory.getRemoteInstance().getOtherPaymentCollection(view);
				OtherPaymentInfo info = new OtherPaymentInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//更新附件内容
					   OtherPaymentFactory.getRemoteInstance().updatePartial(info, selector);
				   } catch (Exception e1) {
					   e1.printStackTrace();
				  } 
				}
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
			
			MsgBox.showInfo("提交保存成功");

			//mod by ypf on 20121111 刷新listui
			rebackRefreshListUI(e);
			
		}else
		{
			SysUtil.abort();
		}
		
		deleteModel(fid);
	}
    
    private void deleteModel(String billFid)
	{
		try {
			ModelFactory.getRemoteInstance().delete("where id is not null and description='"+billFid.trim()+"'");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * **************************************************
	 * 方法说明: updateOtherPayment(在走提交后，更新‘是否走oa审批’字段)  
	 *  
	 * 参数：@param id
	 * 参数：@param isOAAudit
	 * 参数：@return                                                 
	 * 返回值：boolean
	 * 
	 * 修改人：yangpengfei
	 * 修改时间：2012-8-21 下午08:43:04
	 * ***************************************************
	 */
	public boolean updateOtherPayment(String id,boolean isOAAudit,String billTempletID,String oaBillID)
	{
		System.out.println("------发起流程成功后更新合同信息："+"  eas单据id:"+id +"  是否走oa审批："+isOAAudit+"  模板id："+billTempletID +"  oa单据id："+oaBillID);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		OtherPaymentCollection col;
		try {
			col = OtherPaymentFactory.getRemoteInstance().getOtherPaymentCollection(view);
			OtherPaymentInfo info = new OtherPaymentInfo();
			//取到需要更新附件的对象
			if(col != null && col.size() > 0)
			{
			  info = col.get(0);
			  info.setIsOAAudit(isOAAudit);
			  chkisOAAudit.setSelected(isOAAudit);
			  
			  String oabillid = (info.getOABillID()==null || info.getOABillID()=="")?"":info.getOABillID();
			  //!oabillid.equals("") 第一次进来，OABillID字段为空，则更新为oaBillID；第二次，显然OABillID字段不为空
			  if(oaBillID!=null && oaBillID !="" && oabillid.equals(""))
			  {
			    info.setOABillID(oaBillID);
			    txtOABillID.setText(oaBillID);
			  }
			  //如果要走oa审批流程，那么就把oa单据的模板id也保存
			  if(isOAAudit){
			      info.setBillTempletID(billTempletID);
			      txtbillTempletID.setText(billTempletID);
			  }
			  SelectorItemCollection selector = new SelectorItemCollection();
			  selector.add("isOAAudit");
			  selector.add("billTempletID");
			  if(oaBillID!=null && oaBillID !="" && oabillid.equals(""))
			  {
				  selector.add("OABillID");
			  }
			  try {
					//更新附件内容
				   OtherPaymentFactory.getRemoteInstance().updatePartial(info, selector);
				   return true;
			   } catch (Exception e) {
				   e.printStackTrace();
				   return false;
			  } 
			}else
			{
				return false;
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
    /**
	 * **************************************************
	 * 方法说明: getOAModelAndSaveToEAS(获取蓝凌OA提供模板并保存到eas中)  
	 *  
	 * 参数：@throws Exception                                                 
	 * 返回值：void
	 * 
	 * 修改人：yangpengfei
	 * 修改时间：2012-8-21 下午04:34:11
	 * ***************************************************
	 */
	public void getOAModelAndSaveToEAS(String type, String user) {
		String obj = null;
		try {
			proxy = service.getZHKmReviewWebserviceServiceImpPort();
			obj = proxy.getTemplate(type);
		} catch (Exception e) {
			e.printStackTrace();
			MsgBox.showWarning("无法获取蓝凌OA接口提供的模板类型，请联系管理员");
			SysUtil.abort();
		}
		if (obj.length() > 0) {
			// 先清空原有模板
			try {
				ModelFactory.getRemoteInstance().delete("where id is not null and description='" + user.trim()+ "'");
			} catch (EASBizException e) {
				e.printStackTrace();
				MsgBox.showWarning("执行清空EAS系统原有OA模板时异常");
				SysUtil.abort();
			} catch (BOSException e) {
				e.printStackTrace();
				MsgBox.showWarning("执行清空EAS系统原有OA模板时异常");
				SysUtil.abort();
			}

			ModelInfo info = null;
			
			try {
				// 遍历键值对
				JSONArray jsonArray = new JSONArray(obj);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					for (int j = 0; j < object.length(); j++) {
						JSONArray list =   (JSONArray)object.get(j+1+"");
						String fid = (list.get(0) == null) ? "" : list.get(0).toString().trim();
						String fname = (list.get(1) == null) ? "" :list.get(1).toString().trim();
						
						System.out.println("fid='" + fid + "' -- fname='" + fname + "'");

						// 保存到eas数据表中
						info = new ModelInfo();
						info.setId(BOSUuid.create(info.getBOSType()));
						info.setSimpleName(fid);
						info.setName(fname);
						info.setNumber(new SimpleDateFormat("yyyyMMddHHmmssms").format(new Timestamp(new Date().getTime()))+ j + "");
						info.setDescription(user.trim());
						try {
							ModelFactory.getRemoteInstance().addnew(info);
						} catch (EASBizException e) {
							e.printStackTrace();
							MsgBox.showWarning("保存OA模板时异常111");
							SysUtil.abort();
						} catch (BOSException e) {
							e.printStackTrace();
							MsgBox.showWarning("保存OA模板时异常222");
							SysUtil.abort();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			MsgBox.showInfo("没有获取到OA接口提供的模板类型");
			SysUtil.abort();
		}
	}
	
	/**
	 * **************************************************
	 * 方法说明: showFilterDialog(模板选择对话框)  
	 *  
	 * 参数：@return
	 * 参数：@throws Exception                                                 
	 * 返回值：String
	 * 
	 * 修改人：yangpengfei
	 * 修改时间：2012-8-23 下午04:21:44
	 * ***************************************************
	 */
	protected List showFilterDialog(String keyForModel) {
		List fids = new ArrayList();
		KDBizPromptBox modelBox = new KDBizPromptBox();
		modelBox.setVisible(false);
		modelBox.setEnabledMultiSelection(true);
		modelBox.setQueryInfo("com.kingdee.eas.fdc.contract.app.ModelQuery");
		
		//设置过滤
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("description", keyForModel.trim()));
		view.setFilter(filter);
		modelBox.setEntityViewInfo(view);
		
		modelBox.setDataBySelector();
		  
		if (modelBox.getSelector().isCanceled() || modelBox.getData() == null){
			SysUtil.abort();
		}else{
			Object infos[] = (Object[]) modelBox.getData();
			for (int i = 0; i < infos.length; i++) {
				fids.add(((ModelInfo)infos[i]).getSimpleName().toString());
				System.out.println("----选中的模板编码："+((ModelInfo)infos[i]).getSimpleName().toString());
			}
		}
        return fids;
	}
	
	//销毁ui，不检查
	public boolean destroyWindow() {
		System.out.println("---------------销毁ui---不检查");
		return true;
	}
	
	private String getOAID(String billID)
	{
		System.out.println("---getOAID---billID:"+billID);
		String oaID="";
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", billID));
		view.setFilter(filter);
		
		OtherPaymentInfo info = null;
		try {
			OtherPaymentCollection col = OtherPaymentFactory.getRemoteInstance().getOtherPaymentCollection(view);
			if(col != null && col.size()>0)
			{
				info = col.get(0);
				oaID = (info.getOABillID()==null || info.getOABillID()=="")?"":info.getOABillID();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		System.out.println("-----getOAID----oaID:"+oaID);
		
		return oaID;
	}

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	Object value = this.prmtcurProject.getValue();
        super.actionAddNew_actionPerformed(e);
        this.prmtcurProject.setValue(value);
    }

    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        String id = (!editData.getId().equals("") && editData.getId() != null) ? editData.getId().toString(): "";
		if (oprtState.equals("VIEW")) {
			fid = id;
		}

		// 检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
		if (!fid.equals("")) {
			boolean isEnableSubmit = FDCUtil
					.isEnableSubmit("SELECT cfbillstates state,cfisoaaudit isOAAudit FROM CT_FNC_OtherPayment WHERE fid='"
							+ fid + "'");
			if (!isEnableSubmit) {
				MsgBox.showInfo("单据已经提交OA走审批流程，不能编辑");
				SysUtil.abort();
			}
		}
		
		if(editData.getBillstates().getValue().equals("4AUDITTED"))
		{
			MsgBox.showWarning("单据已审批不能编辑");
			SysUtil.abort();
		}
		
		super.actionEdit_actionPerformed(e);
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	String id = (!editData.getId().equals("") && editData.getId() != null) ? editData.getId().toString(): "";
		if (oprtState.equals("VIEW")) {
			fid = id;
		}

		// 检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
		if (!fid.equals("")) {
			boolean isEnableSubmit = FDCUtil
					.isEnableSubmit("SELECT cfbillstates state,cfisoaaudit isOAAudit FROM CT_FNC_OtherPayment WHERE fid='"
							+ fid + "'");
			if (!isEnableSubmit) {
				MsgBox.showInfo("单据已经提交OA走审批流程，不能删除");
				SysUtil.abort();
			}
		}
		
		if(editData.getBillstates().getValue().equals("4AUDITTED"))
		{
			MsgBox.showWarning("单据已审批不能删除");
			SysUtil.abort();
		}
		
        super.actionRemove_actionPerformed(e);
    }

    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
        fillAttachmnetList();
    }

    private void checkSubmit(){
    	BigDecimal amthc = this.txtapplyAMTHC.getBigDecimalValue();
    	BigDecimal Quantities = this.txtcompletedQuantities.getBigDecimalValue();
    	if(amthc != null && Quantities != null ){
    		if(amthc.compareTo(Quantities) > 0){
    			MsgBox.showWarning("申请金额不能大于单笔本期完工工程量");
    			abort();
    		}
    	}
    	
    }
    
   
    protected IObjectValue createNewDetailData(KDTable table)
    {
        return null;
    }

    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.finance.OtherPaymentInfo objectValue = new com.kingdee.eas.fdc.finance.OtherPaymentInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        AdminOrgUnitInfo currentAdminUnit = SysContext.getSysContext().getCurrentAdminUnit();
        objectValue.setOrganize(currentAdminUnit);
        objectValue.setBillstates(FDCBillStateEnum.SAVED);
        objectValue.setBizDate(new Date());
        objectValue.setPaymentDate(new Date());
		
        if(this.getUIContext().get("treeVal")!= null){
        	CurProjectInfo object = (CurProjectInfo) this.getUIContext().get("treeVal");
        	PROJECT_INFO = object;
        	objectValue.setCurProject(object);
        }
        return objectValue;
    }

}