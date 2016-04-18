package com.kingdee.eas.fdc.contract.client;

public class ContractContentUIPIEx extends ContractContentUI {

	public ContractContentUIPIEx() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		kDWorkButton2.setEnabled(false);
	}
	
	public void onShow() throws Exception {
		super.onShow();
		kDWorkButton2.setEnabled(false);
	}
}
