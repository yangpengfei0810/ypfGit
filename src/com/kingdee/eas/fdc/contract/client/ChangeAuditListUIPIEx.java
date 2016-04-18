package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeVisaFacadeFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class ChangeAuditListUIPIEx extends ChangeAuditListUI{
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.client.ChangeAuditListUIPIEx");
	
	public ChangeAuditListUIPIEx() throws Exception {
		super();
	}
	
	//mod by ypf on 20140108 增加走OA审批校验
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		ArrayList ids = getSelectedIdValues();
		
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("changeState").getValue()!=null)?row.getCell("changeState").getValue().toString():"";
		String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";

		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		//add by ypf on 20150711,删除时释放编码
		for (int i = 0; i < ids.size(); i++) {
			ChangeAuditBillInfo changeAuditBillInfo = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(" where id='"+ids.get(i)+"'");
			String sql = "update CT_COM_CODE set cfisenable=0 where cfcde='"+changeAuditBillInfo.getNumber()+"' and CFORGID='"+changeAuditBillInfo.getCU().getId()+"' and CFCURPROJECTID='"+changeAuditBillInfo.getCurProject().getId()+"' and cfchngtypid='"+changeAuditBillInfo.getAuditType().getId()+"' and cfisenable=1";
			try {
				ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sql);
			} catch (Exception e1) {
				logger.error("--exception client--sql:"+sql);
				e1.printStackTrace();
			}
		}
		
		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经发起OA流程审批，不能进行删除");
			SysUtil.abort();
		} else if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经通过OA审批，不能进行删除");
			SysUtil.abort();
		} else {
			super.actionRemove_actionPerformed(e);
		}
	}

	//mod by ypf on 20140108 增加走OA审批校验
	public void actionAudit_actionPerformed(ActionEvent arg0) throws Exception {
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("changeState").getValue()!=null)?row.getCell("changeState").getValue().toString():"";
		String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";

		System.out.println("changeState:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经走OA流程审批，不能在EAS审批");
			SysUtil.abort();
		} else {
			super.actionAudit_actionPerformed(arg0);
		}
		
		//add by ypf on 20150723  合同变更发起单审批后，变更签证单状态设置为保存状态
		String id = row.getCell("id").getValue()+"";
		if(id != null && !id.equals("")){
			ContractChangeBillCollection chgBillCol = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(" where changeAudit='"+id+"'");
			if(chgBillCol!=null && chgBillCol.size()>0)
			{
				for (int i = 0; i < chgBillCol.size(); i++) {
					ContractChangeBillInfo contractChangeBillInfo = chgBillCol.get(i);
					String sql = "update t_con_contractchangebill set fstate='1SAVED' where fchangeauditid='"+id+"'";
					logger.info("---合同变更发起单审批后，变更签证单状态设置为保存状态:"+sql);
					ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sql);
				}
			}
			
			//add by ypf on 20150723 合同变更发起单审批时，将测试金额更新到预估金额字段
			ContractChangeVisaFacadeFactory.getRemoteInstance().saveCostAmountByChngAuditBillID(id);
		}
		
	}

	//mod by ypf on 20140108 增加走OA审批校验
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("changeState").getValue()!=null)?row.getCell("changeState").getValue().toString():"";
		String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";

		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经通过OA审批，不能进行反审批");
			SysUtil.abort();
		} else {
			super.actionUnAudit_actionPerformed(e);
		}
		
		//add by ypf on 20150723  合同变更发起单审批后，变更签证单状态设置为保存状态
		String id = row.getCell("id").getValue()+"";
		if(id != null && !id.equals("")){
			ContractChangeBillCollection chgBillCol = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(" where changeAudit='"+id+"'");
			if(chgBillCol!=null && chgBillCol.size()>0)
			{
				for (int i = 0; i < chgBillCol.size(); i++) {
					ContractChangeBillInfo contractChangeBillInfo = chgBillCol.get(i);
					String sql = "update t_con_contractchangebill set fstate='1SAVED' where fchangeauditid='"+id+"'";
					logger.info("---合同变更发起单审批后，变更签证单状态设置为保存状态:"+sql);
					ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sql);
				}
			}
			
			//add by ypf on 20150723 合同变更发起单审批时，将测试金额更新到预估金额字段
			ContractChangeVisaFacadeFactory.getRemoteInstance().unSaveCostAmountByChngAuditBillID(id);
		}
	}
	
	//mod by ypf on 20140108 增加走OA审批校验
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit(e);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		// 取到行
		IRow row = this.tblMain.getRow(rowIndex);
		// 取列值
		String state = (row.getCell("changeState").getValue()!=null)?row.getCell("changeState").getValue().toString():"";
		String isOAAudit = (row.getCell("isOAAudit").getValue()!=null)?row.getCell("isOAAudit").getValue().toString():"";

		System.out.println("state:" + state + "   isOAAudit:" + isOAAudit);

		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& isOAAudit.equals("true")) {
			MsgBox.showInfo("单据已经走OA流程审批，不能进行修改");
			SysUtil.abort();
		} else {
			super.actionEdit_actionPerformed(e);
		}
	}
}
