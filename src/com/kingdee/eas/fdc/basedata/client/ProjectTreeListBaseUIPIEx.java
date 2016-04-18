package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class ProjectTreeListBaseUIPIEx extends ProjectTreeListBaseUI {

	public ProjectTreeListBaseUIPIEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void audit(List ids) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected ICoreBase getRemoteInterface() throws BOSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void unAudit(List ids) throws Exception {
		// TODO Auto-generated method stub

	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit(e);
		super.actionEdit_actionPerformed(e);
	}
	
	protected void checkBeforeEdit(ActionEvent e) throws Exception {
		checkSelected();

		CoreBaseInfo billInfo = getRemoteInterface().getValue(
				new ObjectUuidPK(getSelectedKeyValue()));
		String billState = billInfo.getString(getBillStatePropertyName());
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if (billState.equals(states[i])|| billState.equals("10REJECT")) {
				pass = true;
			}
		}
		if (!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
	}

}
