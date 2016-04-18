package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.contract.WriteBackBillStatusFacadeFactory;

public class CreatePaymentBill {

	public void createPayBillByPayReqBill(String id)
	{
		//付款申请单生成付款单
		try {
			WriteBackBillStatusFacadeFactory.getRemoteInstance().createPayBill(id);
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
}
