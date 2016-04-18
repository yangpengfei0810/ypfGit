package com.kingdee.eas.cp.bc.client;

import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.cp.bc.EducateEnum;
import com.kingdee.eas.cp.bc.TravelAccountBillInfo;
import com.kingdee.eas.util.client.MsgBox;

public class TravelAccountEditUICTEx extends TravelAccountEditUI{

	public TravelAccountEditUICTEx() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		btnAddNew.setEnabled(false);
		btnAddNew.setVisible(false);
		
//		bizPromptPayMode.addDataChangeListener(new DataChangeListener()
//		{
//			public void dataChanged(DataChangeEvent arg0) {
//				Object obj = bizPromptPayMode.getValue();
//				if(null!=obj)
//				{
//					SettlementTypeInfo info = (SettlementTypeInfo)obj;
//					if("17".equals(info.getNumber()))
//					{
//						initContral(true);
//					}else
//					{
//						initContral(true);
//					}
//				}else
//				{
//					initContral(true);
//				}
//			}
//		});		
	}
	
	private void initContral(boolean result)
	{
		BankNo.setRequired(result);
		PayUnitName.setRequired(result);
		AccountNo.setRequired(result);
		LeadPerson.setRequired(result);
	}

	public void loadFields() {
		super.loadFields();
		
		btnAddNew.setEnabled(false);
		btnAddNew.setVisible(false);
	}

	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		SelectorItemCollection selectors= super.getSelectors();
		selectors.add(new SelectorItemInfo("outAppBillNo"));
		selectors.add(new SelectorItemInfo("isEducate"));
		return selectors;
	}
	
	protected void verifyInput(java.awt.event.ActionEvent e)
    throws Exception
   {
       super.verifyInput(e);
       if(this.txtOutAppBillNo.getText()==null||this.txtOutAppBillNo.getText().equals("")){
    	   MsgBox.showInfo("出差申请单号不允许为空！");
    	   this.abort();
       }
   }

	protected IObjectValue createNewData() {
		TravelAccountBillInfo info=(TravelAccountBillInfo)super.createNewData();
		info.setIsEducate(EducateEnum.UNEDUCATE);
		return info;
	}
	
	
}
