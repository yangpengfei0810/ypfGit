package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.query.server.SQLDataAccessFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.contract.WriteBackBillStatusFacadeFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProjectListUIPIEx extends ProjectListUI {

	private ProjectInfo info = null;
	private String id = "";
	private String name = "";
	
	public ProjectListUIPIEx() throws Exception {
		super();
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);

		if (tblMain.getSelectManager().getActiveRowIndex() != -1) {
			int i = tblMain.getSelectManager().getActiveRowIndex();
			id = tblMain.getRow(i).getCell("id").getValue().toString();
			name = tblMain.getRow(i).getCell("name").getValue().toString();
			getProjectInfo(id, name);
		}
	}

	private ProjectInfo getProjectInfo(String id, String name) {
		info = new ProjectInfo();
		info.setId(BOSUuid.read(id));
		info.setName(name);
		return info;
	}

	public void actionSetMappingName_actionPerformed(ActionEvent e)
			throws Exception {
		String destBillEditUIClassName = "com.kingdee.eas.fdc.basedata.client.SetMappingUI";
		Map map = new UIContext(this);
		map.put("prjID", id);
		map.put("prjName", name);
		map.put(UIContext.OWNER, this);
		
		IUIWindow uiWindow = null;
		// UIFactoryName.MODEL 为弹出模式
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				destBillEditUIClassName, map, null, OprtState.ADDNEW);
		uiWindow.show();
		
		//鹏基生成客户资料
//		importExcel();
		
		//付款申请单生成付款单
//		WriteBackBillStatusFacadeFactory.getRemoteInstance().createPayBill("JmUXc3PSRkiO9HLE1cNNjMmlqGk=");
//		WriteBackBillStatusFacadeFactory.getRemoteInstance().modBillInfo("EAS2", "13bb747ba06270aa0f27e114a9eb8028", "4AUDITTED", false, null);
		
		//无文本合同生成付款单
//		WriteBackBillStatusFacadeFactory.getRemoteInstance().createPayBill("gyfZaLMDSsOSm+oJLi5Ddj2aU4g=");
//		WriteBackBillStatusFacadeFactory.getRemoteInstance().modBillInfo("EAS5", "13bb8cdae89a365ba04e1664d89bd737", "4AUDITTED", false, null);
	}
	
	private void importExcel()
	{
		KDSBook kdsBook = null;
		try {
			kdsBook = POIXlsReader.parse("E:/客户.xls");
		} catch (Exception e1) {
			
			SysUtil.abort();
		}
		if (kdsBook == null) {
			MsgBox.showError("导入失败.\r\n附件没有BooKSheett.");
			SysUtil.abort();
		}
		int bookCount = kdsBook.getSheetCount();
		for (int i = 0; i < bookCount; i++) {
			KDSSheet sheet = kdsBook.getSheet(new Integer(i));
			int ColumnCount = sheet.getColumnCount();
			int rowCount = sheet.getRowCount();
			if (ColumnCount > 0 && rowCount > 0) {
				for (int j = 0; j < rowCount; j++) {
					List valueList = new ArrayList();
					String customerName = "";
					String bankNumber = "";
					String bankName = "";
					for (int k = 0; k < ColumnCount; k++) {
						
						String value = sheet.getCell(j, k, false).getValue().toString();
						
						if(k==1)
						{
							bankNumber = value.trim();
						}
						
						if(k==2){
							/*String sql = "select fid from t_bd_bank where fnumber='"+value+"'";
							try {
								IRowSet rs = SQLDataAccessFactory.getRemoteInstance().getRowSet(sql);
								if(rs!=null && rs.size()>0)
								{
									try {
										bankName = rs.getString("fid");
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
							} catch (BOSException e) {
								e.printStackTrace();
							}*/
							
							bankName = value.trim();
						}
						
						if(k==0)
						{
							customerName = value.trim();
						}
						
//						System.out.println(customerName + "   " + bankNumber + "   "+bankName);
						if(!customerName.equals("") && !bankNumber.equals("") && !bankName.equals("")){
							String sql_update = "update T_SHE_FDCCustomer set cfcollectionscheme='nIJ8udFpSSWkRUlG+f9t7EOCB/g=',cfremittingbank='"+bankName+"',cfbanknumber='"+bankNumber+"' where  fname_l2 ='"+customerName+"';\r\ncommit;";
						    System.out.println("------第"+j+"条-------更新客户资料:"+sql_update);
						}
					}
					
				}
			}
		}

	}
}
