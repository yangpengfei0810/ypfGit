package com.kingdee.eas.cp.bc.client;

import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTEditManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.cp.bc.BizAccountBillEntryInfo;
import com.kingdee.eas.cp.bc.BizCollBillTypeEnum;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.cp.bc.ExpenseReqException;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;

public class BizAccountEditUICTEx extends BizAccountEditUI{

	String TB_AMOUNT = "amount";
	String TB_EXPENSETYPE = "expenseType";
	String TB_AMOUNTAPPROVED = "approvedAmount";
	String TB_BUDGETAMOUNT = "budgetAmount";
	
	  protected void beforeStoreFields(ActionEvent e)
	    throws Exception
	  {
		  checkEntry(getDetailTable()); 
	  }

	  protected IObjectValue createNewData() 
	  {
		  return super.createNewData(); 
	  }

	  protected IObjectValue createNewDetailData(KDTable table) 
	  {
		  BizAccountBillEntryInfo lineInfo = (BizAccountBillEntryInfo)super.createNewDetailData(table);
		  lineInfo.setHappenTime(new Date());
		  return lineInfo;
	  }

	  protected void checkEntry(KDTable table) throws Exception 
	  {
		  int rows = getDetailTable().getRowCount();
		  IRow row = null;
		  KDTEditManager kdtManager = getDetailTable().getEditManager();
		  for (int i = 0; i < rows; ++i)
		  {
			  row = getDetailTable().getRow(i);

			  ExpenseTypeInfo expenseTypeInfo = (ExpenseTypeInfo)row.getCell(this.TB_EXPENSETYPE).getValue();
			  if (expenseTypeInfo == null)
			  {
				  kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_EXPENSETYPE));
				  throw new ExpAccException(ExpAccException.EXPENSE_TYPE_NOT_NULL);
			  }
			  BigDecimal amount = (BigDecimal)row.getCell(this.TB_AMOUNT).getValue();
			  String sourceId = this.editData.getSourceBillId();
			  if (sourceId != null)
			  {
				  if ((amount == null) || (amount.compareTo(new BigDecimal("0.00")) < 0))
				  {
					  kdtManager.editCellAt(i, getDetailTable().getColumnIndex("amount"));
					  throw new ExpAccException(ExpAccException.AMOUNTNOTLESSTHENZERO);
				  }
			  }

			  if ((amount == null) || (amount.compareTo(new BigDecimal("0.00")) <= 0))
			  {
				  kdtManager.editCellAt(i, getDetailTable().getColumnIndex("amount"));
				  throw new ExpenseReqException(ExpenseReqException.NEGTIVE_ENTRY_AMOUNT_ERROR);
			  }
			  BigDecimal amountApproved = (BigDecimal)row.getCell(this.TB_AMOUNTAPPROVED).getValue();
			  if ((amountApproved != null) && (amountApproved.compareTo(new BigDecimal("0.00")) < 0))
			  {
				  kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_AMOUNTAPPROVED));
				  throw new ExpAccException(ExpAccException.APPROVEDACOUNT_NOTNULL);
			  }
			  if ((AMOUNTAPPROVED.equals(this.oprtState)) && (isFromWorkFlow()) && (((amountApproved == null) || (amountApproved.intValue() < 0))))
			  {
				  kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_AMOUNTAPPROVED));
				  throw new ExpAccException(ExpAccException.APPROVEDACOUNT_NOTNULL);
			  }
			  if (!(BizCollUtil.bigDecimalObjectLessThan(row.getCell(this.TB_AMOUNTAPPROVED).getValue(), amount)))
			  {
				  kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_AMOUNTAPPROVED));
				  throw new ExpAccException(ExpAccException.APPROVED_GT_AMOUNT);
			  }
			  if (row.getCell("costCenter").getValue() == null)
			  {
				  kdtManager.editCellAt(i, getDetailTable().getColumnIndex("costCenter"));
				  throw new ExpAccException(ExpAccException.ENTRY_COSTCENTER_NOT_NULL);
			  }
			  if (row.getCell("company").getValue() != null)
				  continue;
			  kdtManager.editCellAt(i, getDetailTable().getColumnIndex("company"));
			  throw new ExpAccException(ExpAccException.ENTRY_COMPANY_NOT_NULL);
	    }
	  }
	  
	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		SelectorItemCollection selectors= super.getSelectors();
		selectors.add(new SelectorItemInfo("sourceBillType"));
		
		return selectors;
	}
	
	public BizAccountEditUICTEx() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if(this.editData.getSourceBillId()!=null&&this.editData.getSourceBillType()!=null&&this.editData.getSourceBillType().equals(BizCollBillTypeEnum.DAILY_LOAN)){
			BankNo.setEnabled(false);
			PayUnitName.setEnabled(false);
			AccountNo.setEnabled(false);
		}
//		bizPromptPayMode.addDataChangeListener(new DataChangeListener()
//		{
//			public void dataChanged(DataChangeEvent arg0) {
//				Object obj = bizPromptPayMode.getValue();
//				if(null!=obj)
//				{
//					SettlementTypeInfo info = (SettlementTypeInfo)obj;
//					if("05".equals(info.getNumber()))
//					{
//						initContral(true);
//					}else
//					{
//						initContral(false);
//					}
//				}else
//				{
//					initContral(false);
//				}
//			}
//		});		
	}
	
	private void initContral(boolean result)
	{
		kDLabelContainer4.setEnabled(result);
		kDLabelContainer4.setVisible(result);
		kDLabelContainer5.setEnabled(result);
		kDLabelContainer5.setVisible(result);
		kDLabelContainer6.setEnabled(result);
		kDLabelContainer6.setVisible(result);
		kDLabelContainer7.setEnabled(result);
		kDLabelContainer7.setVisible(result);
		BankNo.setEnabled(result);
		BankNo.setVisible(result);
		PayUnitName.setEnabled(result);
		PayUnitName.setVisible(result);
		AccountNo.setEnabled(result);
		AccountNo.setVisible(result);
		LeadPerson.setEnabled(result);
		LeadPerson.setVisible(result);		
	}
}
