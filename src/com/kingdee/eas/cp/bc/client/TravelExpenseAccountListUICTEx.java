package com.kingdee.eas.cp.bc.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.eas.cp.bc.BizCollUtil;

public class TravelExpenseAccountListUICTEx extends TravelExpenseAccountListUI {

	public TravelExpenseAccountListUICTEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	 protected void tblMain_tableSelectChanged(KDTSelectEvent e)
     throws Exception
	 {
	     super.tblMain_tableSelectChanged(e);
	     afterSelectLine(e);
	 }

 private void afterSelectLine(KDTSelectEvent e)
     throws Exception
 {
     IRow selectRow = null;
     BizEnumValueInfo enumValue = null;
     int nStatus = -1;
     if(e == null)
         return;
     KDTSelectBlock selectBlock = e.getSelectBlock();
     if(selectBlock == null)
         return;
     int selectBottom = selectBlock.getBottom();
     selectRow = tblMain.getRow(selectBottom);
     if(selectRow != null)
     {
         String applierName = null;
         if(selectRow.getCell("applier.name") != null)
         {
             applierName = (String)selectRow.getCell("applier.name").getValue();
             setViewRcrdsOfLendAndRepay(applierName);
         }
         if(selectRow.getCell("state") != null)
         {
             enumValue = (BizEnumValueInfo)selectRow.getCell("state").getValue();
             nStatus = enumValue.getInt();
         }
     }
     boolean isEnbleByApplierCompany = BizCollUtil.enbleByApplierCompany(tblMain);
     actionRemove.setEnabled((nStatus !=80) && isEnbleByApplierCompany);
     
 }


}
