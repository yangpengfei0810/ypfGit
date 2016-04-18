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
	private static String templateId = "";//oa����ģ��id
	private static String billState ="";
	private static boolean bl_ok = true;
	private static String fid="";//eas����id
	private static int num = 100;
	private static String oaBillID ="";
	private static CurProjectInfo PROJECT_INFO = null;//������Ŀ����
	private static FDCUtil util;
	private static OtherPaymentListUI listUI=null;
	
	private static String CALLTYPE_SEND ="����";//дlog�õ�
    
    
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
    	
    	//���ñ���
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
		set.add("�����");
		set.add("���޿�");
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("payType.name",set,CompareType.NOTINCLUDE));
		viewInfo.setFilter(filterInfo);
		prmtpayType.setEntityViewInfo(viewInfo);
		
		changeBizDate(null);
		
		//���ظ����������б�
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
     * ����ҵ�����ڴ����ڼ�
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
						prmtapplyPeriod.setDisplayFormat(periodInfo.getPeriodYear()+"��"+periodInfo.getPeriodNumber()+"��");
						prmtapplyPeriod.setData(periodInfo);
						return;
					}
				}
				
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
    		
    	}
    }
    
    //У��ʵ���տλ
    protected  void changerRealityPayee(DataChangeEvent e){
    	
    }
    
    
    /**
     * .���ȱ���= ������� / �����깤������
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
     * ����ҵ�����ڴ����ڼ�
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
//        prmtapplyPeriod.setDisplayFormat(period.getPeriodYear()+"��"+period.getPeriodNumber()+"��");
    
    }
    
    /**
     * �����տλ���������ʺ�
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
     * ���ظ����������б�
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
    	map.put("prmtwithMSector", "�ÿ��");
    	map.put("prmtpayee", "�տλȫ��");
    	map.put("prmtrealityPayee", "ʵ���տλ");
    	map.put("txtNumber", "���ݱ��");
    	map.put("txtname", "��������");
    	map.put("pkBizDate", "ҵ������");
    	map.put("prmtapplyPeriod", "��������");
    	map.put("pkpaymentDate", "��������");
    	map.put("prmtpayType", "��������");
    	map.put("txtapplyAMT", "����ԭ�ҽ��");
    	map.put("txtapplyAMTHC", "���뱾�ҽ��");
    	map.put("txtPaymentProportion", "���ȿ�����");
    	map.put("txtcompletedQuantities", "�����깤���������");
    	
    	for (int i = 0; i < kjname.length; i++) {
    		if(kjname[i] instanceof KDBizPromptBox){
    			KDBizPromptBox promptBox = (KDBizPromptBox)kjname[i];
    			Object withMSector = promptBox.getValue();
    			if(withMSector == null){
    	    		MsgBox.showWarning(map.get(promptBox.getName())+"����Ϊ�գ�");
    	    		abort();
    	    	}
    		}else if(kjname[i] instanceof KDTextField){
    			KDTextField textField = (KDTextField)kjname[i];
    			Object withMSector = textField.getText();
    			if(withMSector == null || withMSector.equals("")){
    	    		MsgBox.showWarning(map.get(textField.getName())+"����Ϊ�գ�");
    	    		abort();
    	    	}
    		}else if(kjname[i] instanceof KDDatePicker){
    			KDDatePicker textField = (KDDatePicker)kjname[i];
    			Object withMSector = textField.getValue();
    			if(withMSector == null){
    	    		MsgBox.showWarning(map.get(textField.getName())+"����Ϊ�գ�");
    	    		abort();
    	    	}
    		}else if(kjname[i] instanceof KDFormattedTextField ){
    			KDFormattedTextField  textField = (KDFormattedTextField )kjname[i];
    			Object withMSector = textField.getBigDecimalValue();
    			if(withMSector == null){
    	    		MsgBox.showWarning(map.get(textField.getName())+"����Ϊ�գ�");
    	    		abort();
    	    	}
    		}
		}
    }
    
    
    /**
     * ����ԭ��
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
     * �ұ�
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
	 * ************************************************** ����˵��:
	 * getJsonForm(��ȡ���ݵ�json)
	 * 
	 * ������@param fid ������@return ����ֵ��String
	 * 
	 * �޸��ˣ� �޸�ʱ�䣺2012-8-25 ����03:06:18
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
		System.out.println("----�������'" + otherNumber+otherName + "'-----json:" + json);

		return json;
	}
    
	/**
	 * ���������ʺ�
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
    	String org = "";// ��֯
		String proj="";//������Ŀ
		String contractNo="";//��ͬ��
		String useDepartment="";//�ÿ��
		String supplier="";//�տλȫ��
		String realSupplier="";//ʵ���տλ
		String payRequnum = "";//�������뵥
		String otherNumber="";//����������
		String otherName="";//�����������
		String bookedDate="";//ҵ������
		String period="";//��������
		String billstate="";//״̬
		String payDate="";//��������
		String payment="";//��������
		String recBank="";//�տ�����
		String amount="";//ԭ�ҽ��
		String currency="";//�ұ�
		String recAccount="";//�տ��˺�
		String bcAmount="";//���ҽ��
		String exchangeRate="";//����
		String invoiceNumber="";//��Ʊ��
		String paymentProportion="";//���ȿ�����
		String completePrjAmt="";//�����깤���������
		String invoiceAmt="";//��Ʊ���
		String attachment="";//����
		String moneyDesc="";//��ע
		String promptCreator="";//�Ƶ���
		String dateCreateTim="";//�Ƶ�����
		String promptAuditor="";//�����
		String auditDate="";//�������
    	
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
		//����
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
				"<td class='td_normal_title_head' width=100% height='25' colspan='6'>�������</td>" 
				+"</tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' width=16% height='25' bgcolor='#66FFFF'>��֯</td>" +
				"    <td width=16% >"+org+"</td>" +
				"    <td class='td_normal_title' width=16% >������Ŀ</td>" +
				"    <td width=16% >"+proj+"</td>" +
				"    <td class='td_normal_title' width=16% >��ͬ</td>" +
				"    <td width=16% >"+contractNo+"</td>" +
				"</tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>�ÿ��</td>" +
				"    <td>"+useDepartment+"</td>" +
				"    <td class='td_normal_title' >�տλȫ��</td>" +
				"    <td>"+supplier+"</td>" +
				"    <td class='td_normal_title' >ʵ���տλ</td>" +
				"    <td>"+realSupplier+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>�������뵥</td>" +
				"    <td>"+payRequnum+"</td>" +
				"    <td class='td_normal_title' >���ݱ��</td>" +
				"    <td>"+otherNumber+"</td>" +
				"    <td class='td_normal_title' >��������</td>" +
				"    <td>"+otherName+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>ҵ������</td>" +
				"    <td>"+bookedDate+"</td>" +
				"    <td class='td_normal_title' >�����ڼ�</td>" +
				"    <td>"+period+"</td>" +
				"    <td class='td_normal_title' >״̬</td>" +
				"    <td>"+billstate+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>��������</td>" +
				"    <td>"+payDate+"</td>" +
				"    <td class='td_normal_title' >��������</td>" +
				"    <td>"+payment+"</td>" +
				"    <td class='td_normal_title' >�տ�����</td>" +
				"    <td>"+recBank+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>�����ԭ�ң�</td>" +
				"    <td>"+amount+"</td>" +
				"    <td class='td_normal_title' >�ұ�</td>" +
				"    <td>"+currency+"</td>" +
				"    <td class='td_normal_title' >�տ��ʺ�</td>" +
				"    <td>"+recAccount+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>��������ң�</td>" +
				"    <td>"+bcAmount+"</td>" +
				"    <td class='td_normal_title' >����</td>" +
				"    <td>"+exchangeRate+"</td>" +
				"    <td class='td_normal_title' >��Ʊ��</td>" +
				"    <td>"+invoiceNumber+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>���ȿ�����</td>" +
				"    <td>"+paymentProportion+"</td>" +
				"    <td>�����깤������</td>" +
				"    <td>"+completePrjAmt+"</td>" +
				"    <td class='td_normal_title' >��Ʊ���</td>" +
				"    <td>"+invoiceAmt+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>�����б�</td>" +
				"    <td colspan='5'>"+attachment+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>��ע</td>" +
				"    <td colspan='5'>"+moneyDesc+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>�����</td>" +
				"    <td colspan='2'>"+promptAuditor+"</td>" +
				"    <td class='td_normal_title' >������</td>" +
				"    <td colspan='2'>"+promptCreator+"</td>" +
				"  </tr>" +
				"  <tr>" +
				"    <td class='td_normal_title' height='25'>�������</td>" +
				"    <td colspan='2'>"+auditDate+"</td>" +
				"    <td>��������</td>" +
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
	
	//��ȡ��Ŀ���̶���
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
		//mod by ypf on 20121113 ˢ��listui,����ҳ�治ˢ��
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
	
	//�Ƿ���OA����
	private int isOAAudit()
	{
		return MsgBox.showConfirm3("��ѡ���Ƿ���OA�������̣�");
	}
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSubmit();
    	//���þ�̬����
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
			
			//��ʼ��fid
			 fid = editData.getId().toString();
			
			//�����Ժ����Ȣ������״̬
			 billState = getBillState(fid);
		}
		else if(oprtState.equals("EDIT"))
		{
			 fid = (editData.getId()==null)?"":editData.getId().toString();
			 billState = getBillState(fid);
			 if(billState.equals("����"))
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
			MsgBox.showInfo("�����Ѿ��ύOA���������̣������ظ��ύ");
			SysUtil.abort();
		}
		
	    num = isOAAudit();
	    
		//1��ѡ���Ƿ���OA����
		if(num == 0)
		{
			PROJECT_INFO=getPrjInfo(fid);//��ʼ����Ŀ���̶���
			
			//�ж��Ƿ���ڸ���
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
	         
		    //���������״̬������uicontext����ȡ��prepareUIContext����������ʱ��ѹֵ���������ֶ�ȡ
		    String prjMappingName = PROJECT_INFO.getPrjMappingName();
		    
		  //������Ŀ+��������+��������
			String subject = prmtcurProject.getText()+prmtpayType.getText()+txtname.getText();
		    System.out.println("--------subject���⣺"+subject);
		    
			if(prjMappingName ==null || prjMappingName.equals(""))
			{
				if(MsgBox.showConfirm3("�ù�����Ŀû������ӳ�������п���ȡ����ģ�壬�Ƿ�Ҫ������")==MsgBox.OK)
				{
					
				}else{
				   SysUtil.abort();
				}
			}
			
			//2��ѡ��ģ��
		    //��ȡ����OA�ṩģ�岢���浽eas��
			List modelIDList = null;
			String keyForModel = "EAS7("+prjMappingName+")";
			if(billState != null && billState != "" && !billState.equals("����"))
			{
				getOAModelAndSaveToEAS(keyForModel,fid);
			    modelIDList = showFilterDialog(fid);
			}
			
			//4��׼��Ҫ�������̵����ݣ������ݸ�oa����
			AlterAttachment at = new AlterAttachment();
			String creator = SysContext.getSysContext().getCurrentUserInfo().getNumber();
			
			if(billState != null && billState != "" && !billState.equals("����"))
			{
				templateId = (modelIDList.size()>0) ? modelIDList.get(0).toString() : "";
			}
			oaBillID = getOAID(fid);
			System.out.println("-------���������ύʱ���ݵ���id���ҵ���oa����id:"+oaBillID);
			
			//mod by ypf on 20140827 �����˺�Ҫ��json
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
			form.setDocId(oaBillID);//oa����id   oaBillID
			System.out.println("---form----creator:"+creator+"  "+(oprtState.equals("ADDNEW")?"����״̬":"�༭״̬")+"oaBillID:"+oaBillID+"  subject:"+subject+"   templateId:"+templateId+"   jsonForm:"+jsonForm+"  formHTML"+formHTML);
			
			try {
				proxy = service.getZHKmReviewWebserviceServiceImpPort();
				bl_ok = true ;
			} catch (ServiceException e2) {
				e2.printStackTrace();
				
				if(MsgBox.showConfirm3("Զ�̵���OA�ӿ�ʧ�ܣ���ǰ�����Ƿ���Ҫ�����ύ��")==MsgBox.OK)
				{
					try {
						this.billstates.setSelectedItem(FDCBillStateEnum.SUBMITTED);
						super.actionSubmit_actionPerformed(e);
					} catch (Exception e1) {
						e1.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("ʧ��");
						logInfo.setLogTitle("�ύʱʧ��");
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
			
			//5������Ƿ��д����¼
			try {
				proxy.delTasksByID(fid);
			} catch (Exception e1) {
				e1.printStackTrace();
				if(MsgBox.showConfirm3("Զ�̵���OA�ӿ�ʧ�ܣ���ǰ�����Ƿ���Ҫ���棿")==MsgBox.OK)
				{
					try {
						super.actionSave_actionPerformed(e);
					} catch (Exception e11) {
						e11.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("ʧ��");
						logInfo.setLogTitle("Զ�̵���OA�ӿ�ʧ��ʱ�Ե�ǰ���ݽ��б���ʧ��");
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
			
			//6������oa�����̷���ӿڴ�ֵ
			try {
				LongTimeDialog dialog = UITools.getDialog(this);
				dialog.setTitle("��ʾ");//��ʾ�����
				if(dialog==null)
					return;
		        dialog.setLongTimeTask(new ILongTimeTask() {
		            public Object exec() throws Exception {
		            	//����Ҫ�ȴ��Ĵ����
		            	try {
		            		oaId = proxy.addReview(form);
		            		if(!oprtState.equals("ADDNEW") && !oaBillID.equals(""))
		            		{
		            		   oaId = oaBillID;
		            		}
						} catch (Exception e2) {
							bl_ok = false;
							MsgBox.showDetailAndOK(null, "����OA�ӿڴ��͵�������ʧ��. \r\n�����ϸ��Ϣ�鿴����.", e2.toString(), 1);
							System.out.println("-----------e2:"+e2);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("ʧ��");
							logInfo.setLogTitle("����OA�ӿڴ��͵�������ʱʧ��");
							logInfo.setLogDetail(e2.toString());
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS7");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
		            		SysUtil.abort();
						}
		            	System.out.println("-----��oa�������ݺ��----oaId:"+oaId);
		                return oaId;
		            }
		            public void afterExec(Object result) throws Exception {
		                
		            }
		        });
		        Component[] cps=dialog.getContentPane().getComponents();  
		        for(Component cp:cps){  
		            if(cp instanceof JLabel){  
		            	//��ʾ����
		                ((JLabel) cp).setText("��ȴ������ڵ���OA�ӿڴ�������......");  
		            }  
		        }
		        dialog.show();
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "����OA�ӿڴ��͵�������ʧ��. \r\n�����ϸ��Ϣ�鿴����.", e1.toString(), 1);
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("ʧ��");
				logInfo.setLogTitle("����OA�ӿڴ��͵�������ʧ��. ");
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
			
			//7������eas��ͬ���ݵġ��Ƿ���OA�������ֶ�
			if(bl_ok)
			{
				if(!updateOtherPayment(fid,true,templateId,oaId)){
					MsgBox.showInfo("��дEAS����ʧ��");
					SysUtil.abort();
				}else
				{
					MsgBox.showInfo("����OA�������̳ɹ�");
					
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
							logInfo.setState("�ɹ�");
							logInfo.setLogTitle("���̷���ɹ�");
							logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
							logInfo.setUrl(url);
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS7");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							//mod by ypf on 20121111 ˢ��listui
							rebackRefreshListUI(e);
							
							//�ȱ����ٹرգ�����ᵯ�����������޸ģ��Ƿ���Ҫ���桱��ʾ
							uiWindow.close();
							BareBonesBrowserLaunch.openURL(url);
						}else
						{
							MsgBox.showInfo("û�л�ȡ�������¼URL");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						MsgBox.showDetailAndOK(null, "�����¼OAʧ��,��ֱ�ӵ�¼OA�鿴����. \r\n�����ϸ��Ϣ�鿴����.", e1.toString(), 1);
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("ʧ��");
						logInfo.setLogTitle("�����¼OAʧ��,��ֱ�ӵ�¼OA�鿴����");
						logInfo.setLogDetail(e1.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS7");
						WSLogFactory.getRemoteInstance().addnew(logInfo);
					}
				}
			}else{
				MsgBox.showWarning("����OA��������ʧ��");
				SysUtil.abort();
			}
			if(bl_ok)
			{
				System.out.println("-----�����������ӵ�¼oa�鿴���û�����"+creator+"  ���룺1��------http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
			}
		}else if(num == 1)
		{
			//ֱ�ӱ���
			try {
				this.billstates.setSelectedItem(FDCBillStateEnum.SUBMITTED);
				super.actionSubmit_actionPerformed(e);
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "�ύʧ��. \r\n�����ϸ��Ϣ�鿴����.", e1.toString(), 1);
				System.out.println("----------"+e1.toString());
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("ʧ��");
				logInfo.setLogTitle("ֱ����EAS�ύʱʧ��-����OA����������");
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
				//ȡ����Ҫ���¸����Ķ���
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//���¸�������
					   OtherPaymentFactory.getRemoteInstance().updatePartial(info, selector);
				   } catch (Exception e1) {
					   e1.printStackTrace();
				  } 
				}
			} catch (BOSException e1) {
				e1.printStackTrace();
				
			}
			
			MsgBox.showInfo("�ύ����ɹ�");

			//mod by ypf on 20121111 ˢ��listui
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
         
	    //���������״̬������uicontext����ȡ��prepareUIContext����������ʱ��ѹֵ���������ֶ�ȡ
	    String prjMappingName = PROJECT_INFO.getPrjMappingName();
		
	    //������Ŀ+��������+��������
		String subject = prmtcurProject.getText()+prmtpayType.getText()+txtname.getText();
	    System.out.println("--------subject���⣺"+subject);

	    //1��ѡ���Ƿ���OA����
		if(num == 0)
		{
			if(prjMappingName ==null || prjMappingName.equals(""))
			{
				if(MsgBox.showConfirm3("�ù�����Ŀû������ӳ�������п���ȡ����ģ�壬�Ƿ�Ҫ������")==MsgBox.OK)
				{
					
				}else{
				   SysUtil.abort();
				}
			}
			
			//2��ѡ��ģ��
		    //��ȡ����OA�ṩģ�岢���浽eas��
			List modelIDList = null;
			String keyForModel = "EAS7("+prjMappingName+")";
			if(oprtState.equals("ADDNEW") || (billState != null && billState != "" && !billState.equals("����")))
			{
				getOAModelAndSaveToEAS(keyForModel,fid);
			    modelIDList = showFilterDialog(fid);
			}
			
			//4��׼��Ҫ�������̵����ݣ������ݸ�oa����
			AlterAttachment at = new AlterAttachment();
			String creator = SysContext.getSysContext().getCurrentUserInfo().getNumber();
			
			if(oprtState.equals("ADDNEW") || (billState != null && billState != "" && !billState.equals("����")))
			{
				templateId = (modelIDList.size()>0) ? modelIDList.get(0).toString() : "";
			}
			oaBillID = getOAID(fid);
			System.out.println("-------���������ύʱ���ݵ���id���ҵ���oa����id:"+oaBillID);
			
			//mod by ypf on 20140827 �����˺�Ҫ��json
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
			noAttForm.setDocId(oaBillID);//oa����id   oaBillID
			System.out.println("---form----creator:"+creator+"  oaBillID:"+oaBillID+"  subject:"+subject+"   templateId:"+templateId+"   jsonForm:"+jsonForm+"  formHTML"+formHTML);
			
			try {
				proxy = service.getZHKmReviewWebserviceServiceImpPort();
				bl_ok = true ;
			} catch (ServiceException e2) {
				e2.printStackTrace();
				
				if(MsgBox.showConfirm3("Զ�̵���OA�ӿ�ʧ�ܣ���ǰ�����Ƿ���Ҫ�����ύ��")==MsgBox.OK)
				{
					try {
						super.actionSubmit_actionPerformed(e);
					} catch (Exception e1) {
						e1.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("ʧ��");
						logInfo.setLogTitle("�ύʱʧ��");
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
			
			//5������Ƿ��д����¼
			try {
				proxy.delTasksByID(fid);
			} catch (Exception e1) {
				e1.printStackTrace();
				if(MsgBox.showConfirm3("Զ�̵���OA�ӿ�ʧ�ܣ���ǰ�����Ƿ���Ҫ���棿")==MsgBox.OK)
				{
					try {
						super.actionSave_actionPerformed(e);
					} catch (Exception e11) {
						e11.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("ʧ��");
						logInfo.setLogTitle("Զ�̵���OA�ӿ�ʧ��ʱ�Ե�ǰ���ݽ��б���ʧ��");
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
			
			//6������oa�����̷���ӿڴ�ֵ
			try {
				LongTimeDialog dialog = UITools.getDialog(this);
				dialog.setTitle("��ʾ");//��ʾ�����
				if(dialog==null)
					return;
		        dialog.setLongTimeTask(new ILongTimeTask() {
		            public Object exec() throws Exception {
		            	//����Ҫ�ȴ��Ĵ����
		            	try {
		            		oaId = proxy.addReviewNoatt(noAttForm);
		            		if(!oprtState.equals("ADDNEW") && !oaBillID.equals(""))
		            		{
		            		   oaId = oaBillID;
		            		}
						} catch (Exception e2) {
							bl_ok = false;
							MsgBox.showDetailAndOK(null, "����OA�ӿڴ��͵�������ʧ��. \r\n�����ϸ��Ϣ�鿴����.", e2.toString(), 1);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("ʧ��");
							logInfo.setLogTitle("����OA�ӿڴ��͵�������ʱʧ��");
							logInfo.setLogDetail(e2.toString());
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS7");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
		            		SysUtil.abort();
						}
		            	System.out.println("-----��oa�������ݺ��----oaId:"+oaId);
		                return oaId;
		            }
		            public void afterExec(Object result) throws Exception {
		                
		            }
		        });
		        Component[] cps=dialog.getContentPane().getComponents();  
		        for(Component cp:cps){  
		            if(cp instanceof JLabel){  
		            	//��ʾ����
		                ((JLabel) cp).setText("��ȴ������ڵ���OA�ӿڴ�������......");  
		            }  
		        }
		        dialog.show();
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "Զ�̵���OA�ӿڴ�������ʧ��. \r\n�����ϸ��Ϣ�鿴����.", e1.toString(), 1);
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("ʧ��");
				logInfo.setLogTitle("�����¼ʧ��,���¼OA�鿴");
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
			
			//7������eas��ͬ���ݵġ��Ƿ���OA�������ֶ�
			if(bl_ok)
			{
				if(!updateOtherPayment(fid,true,templateId,oaId)){
					MsgBox.showInfo("��дEAS����ʧ��");
					SysUtil.abort();
				}else
				{
					MsgBox.showInfo("����OA�������̳ɹ�");
					try {
						String url = OASSOUtil.getSSOURL(creator, oaId);
						System.out.println("-----sso url:"+url);
						if(!url.equals(""))
						{
							this.billstates.setSelectedItem(FDCBillStateEnum.SUBMITTED);
							super.actionSubmit_actionPerformed(e);
							//�ȱ����ٹرգ�����ᵯ�����������޸ģ��Ƿ���Ҫ���桱��ʾ
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("�ɹ�");
							logInfo.setLogTitle("���̷���ɹ�");
							logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
							logInfo.setUrl(url);
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS7");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							//mod by ypf on 20121111 ˢ��listui
							rebackRefreshListUI(e);
							
							uiWindow.close();
							
							BareBonesBrowserLaunch.openURL(url);
						}else
						{
							MsgBox.showInfo("û�л�ȡ�������¼URL");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						MsgBox.showDetailAndOK(null, "�����¼OAʧ��,��ֱ�ӵ�¼OA�鿴����. \r\n�����ϸ��Ϣ�鿴����.", e1.toString(), 1);
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("ʧ��");
						logInfo.setLogTitle("�����¼OAʧ��,��ֱ�ӵ�¼OA�鿴����");
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
				MsgBox.showWarning("����OA��������ʧ��");
				SysUtil.abort();
			}
			if(bl_ok)
			{
				System.out.println("-----�����������ӵ�¼oa�鿴���û�����"+creator+"  ���룺1��------http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
			}
		}else if(num == 1)
		{
			//ֱ�ӱ���
			try {
				this.billstates.setSelectedItem(FDCBillStateEnum.SUBMITTED);
				super.actionSubmit_actionPerformed(e);
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "�ύʧ��. \r\n�����ϸ��Ϣ�鿴����.", e1.toString(), 1);
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("ʧ��");
				logInfo.setLogTitle("ֱ����EAS�ύʱʧ��-����OA����������");
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
				//ȡ����Ҫ���¸����Ķ���
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//���¸�������
					   OtherPaymentFactory.getRemoteInstance().updatePartial(info, selector);
				   } catch (Exception e1) {
					   e1.printStackTrace();
				  } 
				}
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
			
			MsgBox.showInfo("�ύ����ɹ�");

			//mod by ypf on 20121111 ˢ��listui
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
	 * ����˵��: updateOtherPayment(�����ύ�󣬸��¡��Ƿ���oa�������ֶ�)  
	 *  
	 * ������@param id
	 * ������@param isOAAudit
	 * ������@return                                                 
	 * ����ֵ��boolean
	 * 
	 * �޸��ˣ�yangpengfei
	 * �޸�ʱ�䣺2012-8-21 ����08:43:04
	 * ***************************************************
	 */
	public boolean updateOtherPayment(String id,boolean isOAAudit,String billTempletID,String oaBillID)
	{
		System.out.println("------�������̳ɹ�����º�ͬ��Ϣ��"+"  eas����id:"+id +"  �Ƿ���oa������"+isOAAudit+"  ģ��id��"+billTempletID +"  oa����id��"+oaBillID);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		OtherPaymentCollection col;
		try {
			col = OtherPaymentFactory.getRemoteInstance().getOtherPaymentCollection(view);
			OtherPaymentInfo info = new OtherPaymentInfo();
			//ȡ����Ҫ���¸����Ķ���
			if(col != null && col.size() > 0)
			{
			  info = col.get(0);
			  info.setIsOAAudit(isOAAudit);
			  chkisOAAudit.setSelected(isOAAudit);
			  
			  String oabillid = (info.getOABillID()==null || info.getOABillID()=="")?"":info.getOABillID();
			  //!oabillid.equals("") ��һ�ν�����OABillID�ֶ�Ϊ�գ������ΪoaBillID���ڶ��Σ���ȻOABillID�ֶβ�Ϊ��
			  if(oaBillID!=null && oaBillID !="" && oabillid.equals(""))
			  {
			    info.setOABillID(oaBillID);
			    txtOABillID.setText(oaBillID);
			  }
			  //���Ҫ��oa�������̣���ô�Ͱ�oa���ݵ�ģ��idҲ����
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
					//���¸�������
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
	 * ����˵��: getOAModelAndSaveToEAS(��ȡ����OA�ṩģ�岢���浽eas��)  
	 *  
	 * ������@throws Exception                                                 
	 * ����ֵ��void
	 * 
	 * �޸��ˣ�yangpengfei
	 * �޸�ʱ�䣺2012-8-21 ����04:34:11
	 * ***************************************************
	 */
	public void getOAModelAndSaveToEAS(String type, String user) {
		String obj = null;
		try {
			proxy = service.getZHKmReviewWebserviceServiceImpPort();
			obj = proxy.getTemplate(type);
		} catch (Exception e) {
			e.printStackTrace();
			MsgBox.showWarning("�޷���ȡ����OA�ӿ��ṩ��ģ�����ͣ�����ϵ����Ա");
			SysUtil.abort();
		}
		if (obj.length() > 0) {
			// �����ԭ��ģ��
			try {
				ModelFactory.getRemoteInstance().delete("where id is not null and description='" + user.trim()+ "'");
			} catch (EASBizException e) {
				e.printStackTrace();
				MsgBox.showWarning("ִ�����EASϵͳԭ��OAģ��ʱ�쳣");
				SysUtil.abort();
			} catch (BOSException e) {
				e.printStackTrace();
				MsgBox.showWarning("ִ�����EASϵͳԭ��OAģ��ʱ�쳣");
				SysUtil.abort();
			}

			ModelInfo info = null;
			
			try {
				// ������ֵ��
				JSONArray jsonArray = new JSONArray(obj);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					for (int j = 0; j < object.length(); j++) {
						JSONArray list =   (JSONArray)object.get(j+1+"");
						String fid = (list.get(0) == null) ? "" : list.get(0).toString().trim();
						String fname = (list.get(1) == null) ? "" :list.get(1).toString().trim();
						
						System.out.println("fid='" + fid + "' -- fname='" + fname + "'");

						// ���浽eas���ݱ���
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
							MsgBox.showWarning("����OAģ��ʱ�쳣111");
							SysUtil.abort();
						} catch (BOSException e) {
							e.printStackTrace();
							MsgBox.showWarning("����OAģ��ʱ�쳣222");
							SysUtil.abort();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			MsgBox.showInfo("û�л�ȡ��OA�ӿ��ṩ��ģ������");
			SysUtil.abort();
		}
	}
	
	/**
	 * **************************************************
	 * ����˵��: showFilterDialog(ģ��ѡ��Ի���)  
	 *  
	 * ������@return
	 * ������@throws Exception                                                 
	 * ����ֵ��String
	 * 
	 * �޸��ˣ�yangpengfei
	 * �޸�ʱ�䣺2012-8-23 ����04:21:44
	 * ***************************************************
	 */
	protected List showFilterDialog(String keyForModel) {
		List fids = new ArrayList();
		KDBizPromptBox modelBox = new KDBizPromptBox();
		modelBox.setVisible(false);
		modelBox.setEnabledMultiSelection(true);
		modelBox.setQueryInfo("com.kingdee.eas.fdc.contract.app.ModelQuery");
		
		//���ù���
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
				System.out.println("----ѡ�е�ģ����룺"+((ModelInfo)infos[i]).getSimpleName().toString());
			}
		}
        return fids;
	}
	
	//����ui�������
	public boolean destroyWindow() {
		System.out.println("---------------����ui---�����");
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

		// ��鵱ǰ�����Ƿ���"���ύ"����"������"��������oa����ʱ�������ظ��ύ��ɾ�����༭
		if (!fid.equals("")) {
			boolean isEnableSubmit = FDCUtil
					.isEnableSubmit("SELECT cfbillstates state,cfisoaaudit isOAAudit FROM CT_FNC_OtherPayment WHERE fid='"
							+ fid + "'");
			if (!isEnableSubmit) {
				MsgBox.showInfo("�����Ѿ��ύOA���������̣����ܱ༭");
				SysUtil.abort();
			}
		}
		
		if(editData.getBillstates().getValue().equals("4AUDITTED"))
		{
			MsgBox.showWarning("�������������ܱ༭");
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

		// ��鵱ǰ�����Ƿ���"���ύ"����"������"��������oa����ʱ�������ظ��ύ��ɾ�����༭
		if (!fid.equals("")) {
			boolean isEnableSubmit = FDCUtil
					.isEnableSubmit("SELECT cfbillstates state,cfisoaaudit isOAAudit FROM CT_FNC_OtherPayment WHERE fid='"
							+ fid + "'");
			if (!isEnableSubmit) {
				MsgBox.showInfo("�����Ѿ��ύOA���������̣�����ɾ��");
				SysUtil.abort();
			}
		}
		
		if(editData.getBillstates().getValue().equals("4AUDITTED"))
		{
			MsgBox.showWarning("��������������ɾ��");
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
    			MsgBox.showWarning("������ܴ��ڵ��ʱ����깤������");
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