package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.fdc.contract.WriteBackBillStatusFacadeFactory;

public class TestWS {

	public void calContractBill(String billType,String oaBillId,String status,boolean isExistAtt,AttachmentCollection col) {
		try {
			String isSuc = WriteBackBillStatusFacadeFactory.getRemoteInstance().modBillInfo(billType, oaBillId, status, isExistAtt,col);
			System.out.println("---------isSuc:"+isSuc);
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

}
