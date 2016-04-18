package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.BizAccountBillFactory;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.ExpenseAccountFacadeFactory;
import com.kingdee.eas.cp.bc.ExpenseCommenFacadeFactory;
import com.kingdee.eas.cp.bc.IBizAccountBill;
import com.kingdee.eas.cp.bc.IExpenseAccountFacade;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class BizExpenseAccountListUICTEx extends BizExpenseAccountListUI {

	public BizExpenseAccountListUICTEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		afterSelectLine(e);
	}

	private void afterSelectLine(KDTSelectEvent e) throws Exception {
		IRow selectRow = null;
		BizEnumValueInfo enumValue = null;
		int nStatus = -1;
		if (e == null)
			return;
		KDTSelectBlock selectBlock = e.getSelectBlock();
		if (selectBlock == null)
			return;
		int selectBottom = selectBlock.getBottom();
		selectRow = tblMain.getRow(selectBottom);
		if (selectRow != null) {
			String applierName = null;
			if (selectRow.getCell("applier.name") != null) {
				applierName = (String) selectRow.getCell("applier.name")
						.getValue();
				setViewRcrdsOfLendAndRepay(applierName);
			}
			if (selectRow.getCell("state") != null) {
				enumValue = (BizEnumValueInfo) selectRow.getCell("state")
						.getValue();
				nStatus = enumValue.getInt();
			}
		}
		boolean isEnbleByApplierCompany = BizCollUtil
				.enbleByApplierCompany(tblMain);
		actionRemove.setEnabled((nStatus != 80 &&( nStatus == 27 ||  nStatus == 20 )) && isEnbleByApplierCompany);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rows[] = KDTableUtil.getSelectedRows(tblMain);
		Arrays.sort(rows);
		IRow row = null;
		BizEnumValueInfo enumValue = null;
		StringBuffer sb = new StringBuffer();
		String errorRowIndexString = null;
		int nStatus = -1;
		for (int i = 0; i < rows.length; i++) {
			row = tblMain.getRow(rows[i]);
			if (row.getCell("state") != null)
				enumValue = (BizEnumValueInfo) row.getCell("state").getValue();
			if (enumValue != null)
				nStatus = enumValue.getInt();
			// if(nStatus >= 25 && nStatus != 27 ||
			// !BizCollUtil.enbleByApplierCompany(tblMain))
			if (nStatus != 27)
				sb.append("" + (rows[i] + 1) + " , ");
		}

		if (sb.length() > 0) {
			int lastPosition = sb.lastIndexOf(",");
			errorRowIndexString = sb.substring(0, lastPosition);
		}
		if (errorRowIndexString != null) {
			throw new BizCollException(BizCollException.ERROR_OPERATION,
					new Object[] { errorRowIndexString });
		} else {
			super.actionRemove_actionPerformed(e);
			return;
		}
	}
	
	   public void actionAbandon_actionPerformed(java.awt.event.ActionEvent e)
       throws Exception
   {
       checkSelected();
       int result = MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.cp.bc.ImportDataResource", "ABANDONCONFIRM"));
       if(result == 0)
       {
           IBizAccountBill iBiz = BizAccountBillFactory.getRemoteInstance();
           BOSUuid id = BOSUuid.read(getSelectedKeyValue());
           BizAccountBillInfo billInfo = iBiz.getBizAccountBillInfo(new ObjectUuidPK(id));
           if(billInfo.getState().getValue() == 60)
           {
               UserInfo currentUserInfo = (UserInfo)SysContext.getSysContext().getCurrentUser();
               UserInfo biller = billInfo.getBiller();
               if(!currentUserInfo.getId().equals(biller.getId()))
               {
                   MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.cp.bc.ImportDataResource", "currentUserIsNotBiller"));
                   SysUtil.abort();
               } else
               if(BizCollUtil.checkBillHasNextBill(billInfo))
               {
                   MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.cp.bc.ImportDataResource", "existNextBill"));
                   SysUtil.abort();
               } else
               {
                   ExpenseCommenFacadeFactory.getRemoteInstance().returnBudget(id, billInfo, false);
                   IExpenseAccountFacade facade = ExpenseAccountFacadeFactory.getRemoteInstance();
                   facade.setState(id, StateEnum.ALREADYABANDON);
               }
           } else
           {
              // showErrorForNoWorkFlow();
               iBiz.abandon(id);
           }
           MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.cp.bc.ImportDataResource", "ABANDONSUCCESS"));
           refresh(e);
       }
   }

}
