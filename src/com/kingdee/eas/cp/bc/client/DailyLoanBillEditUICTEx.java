package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTEditManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.cp.bc.ExpenseReqException;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.framework.ObjectValueUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

public class DailyLoanBillEditUICTEx extends DailyLoanBillEditUI {

	public DailyLoanBillEditUICTEx() throws Exception {

		super();
		   TB_PURPOSE = "purpose";
	        TB_AMOUNT = "amount";
	        TB_COMMENT = "comment";
	        TB_EXPENSETYPE = "expensetype";
	        TB_AMOUNTAPPROVED = "approvedamount";
	        RES = "com.kingdee.eas.cp.bc.client.LoanReqResource";
		// TODO Auto-generated constructor stub
	}
	
    private String TB_PURPOSE;
    private String TB_AMOUNT;
    private String TB_COMMENT;
    private String TB_EXPENSETYPE;
    private String TB_AMOUNTAPPROVED;
    private String RES;
	
	protected void beforeSubmit()
        throws Exception
    {
        BigDecimal amountApproved = editData.getAmountApproved() != null ? editData.getAmountApproved() : ZERO;
        BigDecimal amountTotal = editData.getAmount() != null ? editData.getAmount() : ZERO;
        if(AMOUNTAPPROVED.equals(oprtState) && isFromWorkFlow() && amountApproved.compareTo(new BigDecimal(0.0D)) < 0)
        {
            FormattedTextFieldAmountApproved.requestFocus();
            throw new ExpAccException(ExpAccException.APPROVEDACOUNT_NOTNULL);
        }
        if(amountApproved.compareTo(amountTotal) > 0)
        {
            FormattedTextFieldAmountApproved.requestFocus();
            throw new ExpAccException(ExpAccException.APPROVED_GT_AMOUNT);
        }
        if(BizCollUtil.bigDecimalObjectLessEqual(editData.getAmount(), null))
            throw new ExpAccException(ExpAccException.AMOUNT_GT_ZERO);
        else
            return;
    }
	
	
    protected void beforeStoreFields(ActionEvent e)
    throws Exception
{
    if(e != null && (btnSubmit.equals(e.getSource()) || menuItemSubmit.equals(e.getSource())))
    {
        FormattedTextFieldAmount.setValue(calculateTotalAmount(getTotalRow(kdtEntries)));
        if(txtName.getText().trim().length() > 80)
        {
            txtName.requestFocus();
            showErrorMessage("dailyLoanNameTooLong");
        }
        if(BizCollUtil.objectIsNull(bizPromptApplier.getData()))
        {
            bizPromptApplier.requestFocus();
            showErrorMessage("dailyLoanApplierNotNull");
        }
        if(!StringUtils.isEmpty(txtNumber.getText()) && txtNumber.getText().trim().length() > 80)
        {
            txtNumber.requestFocus();
            showErrorMessage("dailyLoanNumberTooLong");
        }
        if(BizCollUtil.objectIsNull(dateReqDate.getValue()))
        {
            dateReqDate.requestFocus();
            showErrorMessage("dailyLoanReqDateNotNull");
        }
        if(BizCollUtil.objectIsNull(bizPromptOrgUnit.getData()))
        {
            bizPromptOrgUnit.requestFocus();
            showErrorMessage("dailyLoanOrgUnitNotNull");
        }
        if(BizCollUtil.objectIsNull(bizPromptCompany.getData()))
        {
            bizPromptCompany.requestFocus();
            showErrorMessage("dailyLoanCompanyNotNull");
        }
        if(!BizCollUtil.isBizUnitCompany((CompanyOrgUnitInfo)bizPromptCompany.getData()))
        {
            bizPromptCompany.requestFocus();
            throw new ExpAccException(ExpAccException.ISBIZUNIT);
        }
        if(BizCollUtil.objectIsNull(bizPromptApplierCompany.getData()))
        {
            bizPromptApplierCompany.requestFocus();
            throw new ExpAccException(ExpAccException.A_COMPANY_NOTNULL);
        }
        if(!BizCollUtil.isBizUnitCompany((CompanyOrgUnitInfo)bizPromptApplierCompany.getData()))
        {
            bizPromptApplierCompany.requestFocus();
            throw new ExpAccException(ExpAccException.A_COMPANY_NOTBIZUNIT);
        }
        if(BizCollUtil.objectIsNull(bizPromptCostedDept.getData()))
        {
            bizPromptCostedDept.requestFocus();
            showErrorMessage("dailyLoanCostedDeptNotNull");
        }
        if(bizPromptPayType.getData() == null)
        {
            bizPromptPayType.requestFocus();
            showErrorMessage("payModeNotNull");
        }
        if(bizPromptCostedDept.getData() instanceof FullOrgUnitInfo)
        {
            ICostCenterOrgUnit iCost = CostCenterOrgUnitFactory.getRemoteInstance();
            BOSUuid ids = ((FullOrgUnitInfo)bizPromptCostedDept.getData()).getId();
            CostCenterOrgUnitInfo cost = (CostCenterOrgUnitInfo)iCost.getValue(new ObjectUuidPK(ids));
            bizPromptCostedDept.setData(cost);
        }
        if(BizCollUtil.objectIsNull(comboPrior.getSelectedItem()))
        {
            comboPrior.requestFocus();
            showErrorMessage("dailyLoanPriorNotNull");
        }
        if(BizCollUtil.objectIsNull(bizPromptCurrencyType.getData()))
        {
            bizPromptCurrencyType.requestFocus();
            showErrorMessage("dailyLoanCurrencyTypeNotNull");
        }
        if(BizCollUtil.objectIsNull(dateBillDate.getValue()))
        {
            dateBillDate.requestFocus();
            showErrorMessage("dailyLoanBillDateNotNull");
        }
        if(BizCollUtil.objectIsNull(bizPromptBiller.getData()))
        {
            bizPromptBiller.requestFocus();
            showErrorMessage("dailyLoanBillerNotNull");
        }
        if(StringUtils.isEmpty(FormattedTextFieldAmount.getText()))
        {
            FormattedTextFieldAmount.requestFocus();
            showErrorMessage("dailyLoanAmountNotNull");
        }
        if(BizCollUtil.bigDecimalObjectLessEqual(BizCollUtil.MAX, FormattedTextFieldAmount.getBigDecimalValue()))
            throw new BizCollException(BizCollException.AMOUNT_TOO_LG);
        if(BizCollUtil.bigDecimalObjectLessEqual(BizCollUtil.MAX, FormattedTextFieldAmountApproved.getBigDecimalValue()))
            throw new BizCollException(BizCollException.AMOUNT_TOO_LG);
        if(!StringUtils.isEmpty(kDTextAreaCause.getText()) && kDTextAreaCause.getText().trim().length() > 200)
        {
            kDTextAreaCause.requestFocus();
            showErrorMessage("dailyLoanCauseTooLong");
        }
        if(!StringUtils.isEmpty(kDTextAreaDescription.getText()) && kDTextAreaDescription.getText().trim().length() > 1000)
        {
            kDTextAreaDescription.requestFocus();
            showErrorMessage("dailyLoanDescriptionTooLong");
        }
        checkEntry();
    } else
    if(AMOUNTAPPROVED.equals(oprtState) && isFromWorkFlow())
        checkEntry();
}
    
    private void checkEntry()
    throws Exception
{
    int rows = -1;
    if(getDetailTable() == null)
        rows = -1;
    else
        rows = getDetailTable().getRowCount();
    if(rows <= 0)
        showErrorMessage("entryNotNull");
    KDTEditManager kdtManager = getDetailTable().getEditManager();
    IRow row = null;
    Set expenseTypeSet = new HashSet();
    DailyLoanBillEntryInfo entryInfo = null;
    int count = 0;
    for(int i = 0; i < rows; i++)
    {
        row = getDetailTable().getRow(i);
        if(checkIsCountLine(row))
            continue;
        entryInfo = new DailyLoanBillEntryInfo();
        storeLineFields2(getDetailTable(), row, entryInfo);
        if(checkIsBlankLine(entryInfo))
            continue;
        ExpenseTypeInfo expenseTypeInfo = (ExpenseTypeInfo)row.getCell(TB_EXPENSETYPE).getValue();
        if(expenseTypeInfo == null)
        {
            kdtManager.editCellAt(i, getDetailTable().getColumnIndex(TB_EXPENSETYPE));
            showErrorMessage("dailyLoanExpenseTypeNotNull");
        }
        if(expenseTypeInfo != null && expenseTypeInfo.getId() != null)
        {
            String key = expenseTypeInfo.getId().toString();
            if(expenseTypeSet.contains(key))
                throw new ExpAccException(ExpAccException.EXPENSETYPE_IS_DUPLICATE);
            expenseTypeSet.add(key);
        }
        if(BizCollUtil.objectIsNull(row.getCell(TB_PURPOSE).getValue()))
        {
            kdtManager.editCellAt(i, getDetailTable().getColumnIndex(TB_PURPOSE));
            showErrorMessage("dailyLoanPurposeNotNull");
        } else
        if(row.getCell(TB_PURPOSE).getValue().toString().trim().length() > 200)
        {
            kdtManager.editCellAt(i, getDetailTable().getColumnIndex(TB_PURPOSE));
            showErrorMessage("dailyLoanPurposeTooLong");
        }
        if(!BizCollUtil.objectIsNull(row.getCell("particiants").getValue()) && row.getCell("particiants").getValue().toString().trim().length() > 80)
        {
            kdtManager.editCellAt(i, getDetailTable().getColumnIndex("particiants"));
            showErrorMessage("participants_tooLong");
        }
        BigDecimal amount = (BigDecimal)row.getCell(TB_AMOUNT).getValue();
        String sourceId = editData.getSourceBillId();
        if(sourceId != null && !BizCollUtil.isEvectionReq(sourceId))
        {
            if(amount == null || amount.compareTo(new BigDecimal("0.00")) < 0)
            {
                kdtManager.editCellAt(i, getDetailTable().getColumnIndex(TB_AMOUNT));
                throw new ExpAccException(ExpAccException.AMOUNTNOTLESSTHENZERO);
            }
        } else
        if(amount == null || amount.compareTo(new BigDecimal("0.00")) <= 0)
        {
            kdtManager.editCellAt(i, getDetailTable().getColumnIndex(TB_AMOUNT));
            throw new ExpenseReqException(ExpenseReqException.NEGTIVE_ENTRY_AMOUNT_ERROR);
        }
        BigDecimal amountApproved = (BigDecimal)row.getCell(TB_AMOUNTAPPROVED).getValue();
        if(amountApproved != null && amountApproved.compareTo(new BigDecimal("0.00")) < 0)
        {
            kdtManager.editCellAt(i, getDetailTable().getColumnIndex(TB_AMOUNTAPPROVED));
            throw new ExpAccException(ExpAccException.APPROVEDACOUNT_NOTNULL);
        }
        if(AMOUNTAPPROVED.equals(oprtState) && isFromWorkFlow() && (amountApproved == null || amountApproved.intValue() < 0))
        {
            kdtManager.editCellAt(i, getDetailTable().getColumnIndex(TB_AMOUNTAPPROVED));
            throw new ExpAccException(ExpAccException.APPROVEDACOUNT_NOTNULL);
        }
        if(!BizCollUtil.bigDecimalObjectLessThan(row.getCell(TB_AMOUNTAPPROVED).getValue(), amount))
        {
            kdtManager.editCellAt(i, getDetailTable().getColumnIndex(TB_AMOUNTAPPROVED));
            throw new ExpAccException(ExpAccException.APPROVED_GT_AMOUNT);
        }
        if(!BizCollUtil.objectIsNull(row.getCell(TB_COMMENT).getValue()) && row.getCell(TB_COMMENT).getValue().toString().trim().length() > 200)
        {
            kdtManager.editCellAt(i, getDetailTable().getColumnIndex(TB_COMMENT));
            showErrorMessage("dailyLoanCommentTooLong");
        }
        count++;
    }

    if(count <= 0)
        showErrorMessage("entryNotNull");
}
    
    private void showErrorMessage(String message)
    {
        String s = EASResource.getString(RES, message);
        MsgBox.showError(this, s);
        setCursorOfDefault();
        SysUtil.abort();
    }


    private BigDecimal calculateTotalAmount(IRow countRow)
    {
        BigDecimal totalAmount = new BigDecimal("0.0");
        if(countRow == null)
            return totalAmount;
        BigDecimal amount = BizCollUtil.toBigDecimal(countRow.getCell(TB_AMOUNT).getValue());
        if(amount != null)
            return amount;
        else
            return totalAmount;
    }
    

    private IRow getTotalRow(KDTable table)
    {
        int n = table.getRowCount();
        for(int i = n - 1; i > -1; i--)
        {
            IRow row = table.getRow(i);
            if(checkIsCountLine(row))
                return row;
        }

        return null;
    }
    
    private boolean checkIsCountLine(IRow row)
    {
        if(row == null)
            return false;
        if(row.getCell(TB_EXPENSETYPE).getValue() == null)
            return false;
        Object value = row.getCell("flag").getValue();
        if(BizCollUtil.objectIsNull(value))
            return false;
        else
            return "count".equals(value.toString());
    }
    
    private void storeLineFields2(KDTable table, IRow row, IObjectValue obj)
    {
        table.checkParsed();
        int i = 0;
        for(int n = table.getColumnCount(); i < n; i++)
        {
            String bindField = table.getColumn(i).getFieldName();
            if(StringUtils.isEmpty(bindField))
                continue;
            if(bindField.indexOf(".") >= 0)
            {
                String subItems[] = StringUtils.split(bindField, ".");
                IObjectValue subObj = obj;
                int k = 0;
                int l = subItems.length - 1;
                do
                {
                    if(k >= l)
                        break;
                    subObj = (IObjectValue)subObj.get(subItems[k]);
                    if(subObj == null)
                        break;
                    k++;
                } while(true);
                if(subObj != null)
                    subObj.put(subItems[subItems.length - 1], row.getCell(i).getValue());
                continue;
            }
            Class type = obj.getClass();
            try
            {
                String setMethodName = "set" + StringUtils.headCharUpperCase(bindField);
                Method methods[] = type.getMethods();
                Method method = null;
                int k = 0;
                int l = methods.length;
                do
                {
                    if(k >= l)
                        break;
                    if(setMethodName.equalsIgnoreCase(methods[k].getName()))
                    {
                        method = methods[k];
                        break;
                    }
                    k++;
                } while(true);
                if(method != null)
                    method.invoke(obj, new Object[] {
                        row.getCell(i).getValue()
                    });
                else
                    obj.put(bindField, row.getCell(i).getValue());
            }
            catch(Exception e)
            {
                obj.put(bindField, row.getCell(i).getValue());
            }
        }

    }
    
    private boolean checkIsBlankLine(AbstractObjectValue objValue)
    {
        return ObjectValueUtil.objectValueEquals((AbstractObjectValue)createNewDetailData(kdtEntries), objValue);
    }
}
