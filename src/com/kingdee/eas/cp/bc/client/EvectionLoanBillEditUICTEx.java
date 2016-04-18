package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;

import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

public class EvectionLoanBillEditUICTEx extends EvectionLoanBillEditUI{

	public EvectionLoanBillEditUICTEx() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		bizPromptExpenseType.setRequired(true);
	}

	public void loadFields() {
		super.loadFields();
		bizPromptExpenseType.setRequired(true);
	}

	public void verifyData() throws Exception {
		super.verifyData();
		if(null==bizPromptExpenseType.getValue())
		{
			MsgBox.showInfo("ҵ�������Ϊ�գ�");
			SysUtil.abort();
		}
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		if(null==bizPromptExpenseType.getValue())
		{
			MsgBox.showInfo("ҵ�������Ϊ�գ�");
			SysUtil.abort();
		}
	}
}
