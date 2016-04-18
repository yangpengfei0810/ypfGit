package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Map;

import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class ContractBillLinkProgContEditUIPIEx extends ContractBillLinkProgContEditUI {
	private static final long serialVersionUID = -6435273412046996618L;
	private ProgrammingContractInfo pcInfo;
	private ContractBillInfo contractBillInfo;
	public ContractBillLinkProgContEditUIPIEx() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		init();
		//F7选择框架合约
		prmtContract.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent arg0) {
				Object object=prmtContract.getData();
				if(object instanceof ProgrammingContractInfo){
					pcInfo=(ProgrammingContractInfo) object;
					txtBalance.setValue(pcInfo.getBalance());//规划余额
				}
		}});
	}
	
	private void init(){
		setTextFormat(txtBalance);
		setTextFormat(txtSplitAmt);
		pcInfo=(ProgrammingContractInfo)prmtContract.getData();
		if(pcInfo!=null){
			txtBalance.setValue(pcInfo.getBalance());//规划余额
		}
		Map uiContext = getUIContext();
	    contractBillInfo = (ContractBillInfo)uiContext.get("contractBillInfo");//当前的合同
	    txtSplitAmt.setValue(FDCHelper.toBigDecimal(contractBillInfo.getSplitAmt()));//本次分配
	}
	
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		//本次分配金额须小于等于规划余额
		BigDecimal balance=txtBalance.getBigDecimalValue();
		BigDecimal splitAmont=txtSplitAmt.getBigDecimalValue(); //本次分配金额
		if(splitAmont.compareTo(balance)>0){
			MsgBox.showError("本次分配金额须小于等于规划余额");
			SysUtil.abort();
		}
		pcInfo = (ProgrammingContractInfo)prmtContract.getValue();
        contractBillInfo.setProgrammingContract(pcInfo);
        contractBillInfo.setSplitAmt(splitAmont);
		destroyWindow();
//		ContractBillFactory.getRemoteInstance().update(new ObjectUuidPK(contractBillInfo.getId()), contractBillInfo);
//		super.actionConfirm_actionPerformed(e);
	}
	
	private static void setTextFormat(KDFormattedTextField textField)
    {
        textField.setRemoveingZeroInDispaly(false);
        textField.setRemoveingZeroInEdit(false);
        textField.setPrecision(2);
        textField.setDataType(1);
        textField.setHorizontalAlignment(4);
        textField.setSupportedEmpty(true);
    }
}
