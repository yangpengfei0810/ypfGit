package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;

public class ContractFullListUICTEx extends ContractFullListUI{

	public ContractFullListUICTEx() throws Exception {
		super();
	}

	/**
	 *加载是否审批字段
	 */
	private void getisOAauditname() throws BOSException{
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			Object val = this.tblMain.getCell(i, "number").getValue();
			if(val != null){
				ContractBillCollection contractBillCollection = ContractBillFactory.getRemoteInstance().getContractBillCollection("where number = '"+val+"'");
				if(contractBillCollection.size() > 0 ){
					ContractBillInfo contractBillInfo = contractBillCollection.get(0);
					boolean isOAAudit = contractBillInfo.isIsOAAudit();
					this.tblMain.getCell(i, "isOAAudit").setValue(isOAAudit);
				}
			}
		}
	}
	
	
	@Override
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		getisOAauditname();
	}
	
	@Override
	public void refreshList() throws Exception {
		super.refreshList();
		getisOAauditname();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		getisOAauditname();
	}
	
	@Override
	public void onShow() throws Exception {
		super.onShow();
//		getisOAauditname();
	}
}
