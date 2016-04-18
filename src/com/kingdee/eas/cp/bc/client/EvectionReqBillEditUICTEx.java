package com.kingdee.eas.cp.bc.client;

import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

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
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.EvectionLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.EvectionReqBillEntryInfo;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.framework.ObjectValueUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

public class EvectionReqBillEditUICTEx extends EvectionReqBillEditUI{

	/*  134 */   private String RES = "com.kingdee.eas.cp.bc.client.EvectionReqResource";
	/*  104 */   private String TB_REQ_STARTDATE = "startDate";
	/*      */ 
	/*  106 */   private String TB_REQ_ENDDATE = "endDate";
	/*      */ 
	/*  108 */   private String TB_REQ_FROM = "from";
	/*      */ 
	/*  110 */   private String TB_REQ_TO = "to";
	/*      */ 
	/*  112 */   private String TB_REQ_VEHICLE = "vehicle";
	/*      */ 
	/*  114 */   private String TB_REQ_PARTNER = "partner";
	/*      */ 
	/*  116 */   private String TB_LOAN_STARTDATE = "startDate";
	/*      */ 
	/*  118 */   private String TB_LOAN_ENDDATE = "endDate";
	/*      */ 
	/*  120 */   private String TB_LOAN_FROM = "from";
	/*      */ 
	/*  122 */   private String TB_LOAN_TO = "to";
	/*      */ 
	/*  124 */   private String TB_LOAN_VEHICLE = "vehicle";
	/*      */ 
	/*  126 */   private String TB_LOAN_TAXIEXPENSE = "taxiExpense";
	/*      */ 
	/*  128 */   private String TB_LOAN_BUSSESEXPENSE = "bussesExpense";
	/*      */ 
	/*  130 */   private String TB_LOAN_HOTELEXPENSE = "hotelExpense";
	/*      */ 
	/*  132 */   private String TB_LOAN_OTHEREXPENSE = "otherExpense";
	/*  152 */   private String TB_AMOUNT = "amount";
	
	public EvectionReqBillEditUICTEx() throws Exception {
		super();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
	}
	
    protected void kdtEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	int rowIndex = e.getRowIndex();
    	int collIndex = e.getColIndex();
    	IRow row = kdtEntries.getRow(rowIndex);
    	
    	if(this.TB_REQ_STARTDATE.equals(kdtEntries.getColumnKey(rowIndex)))
    	{
    		Date start = (Date)row.getCell(this.TB_REQ_STARTDATE).getValue();
    		Date end = (Date)row.getCell(this.TB_REQ_ENDDATE).getValue();
    		//计算天数    		
    		if((null!=start)&&(null!=end))
    		{
    			if (start.after(end)) {
    				showErrorMessage("evectionReqstartDateNotAfterEndDate");
    			}
    			int day = (int)((end.getTime()-start.getTime())/(3600*1000*24));
    			BigDecimal days = new BigDecimal(day);
        		row.getCell("preDays").setValue(days);
    		}
    		
    	}
    	if(this.TB_REQ_ENDDATE.equals(kdtEntries.getColumnKey(rowIndex)))
    	{
    		Date start = (Date)row.getCell(this.TB_REQ_STARTDATE).getValue();
    		Date end = (Date)row.getCell(this.TB_REQ_ENDDATE).getValue();
    		//计算天数    		
    		if((null!=start)&&(null!=end))
    		{
    			if (start.after(end)) {
    				showErrorMessage("evectionReqstartDateNotAfterEndDate");
    			}
    			
    			int day = (int)((end.getTime()-start.getTime())/(3600*1000*24));
    			BigDecimal days = new BigDecimal(day);
        		row.getCell("preDays").setValue(days);
    		}
    	}
    	
    	
    }
   
					@Override
					protected void beforeStoreFields(ActionEvent e) throws Exception
					{
		/* 1817 */       if ("".equals(this.txtName.getText())) {
		/* 1818 */         this.txtName.requestFocus();
		/* 1819 */         MsgBox.showInfo("主题不能为空！");
					       SysUtil.abort();
		/*      */       }
						
		/* 1817 */       if (this.txtName.getText().trim().length() > 80) {
		/* 1818 */         this.txtName.requestFocus();
		/* 1819 */         showErrorMessage("evectionReqNameTooLong");
		/*      */       }
		/*      */ 
		/* 1823 */       if (BizCollUtil.objectIsNull(this.bizPromptApplier.getData())) {
		/* 1824 */         this.bizPromptApplier.requestFocus();
		/* 1825 */         showErrorMessage("evectionReqApplierNotNull");
		/*      */       }
		/*      */ 
		/* 1829 */       if (!(StringUtils.isEmpty(this.txtNumber.getText())))
		/*      */       {
		/* 1832 */         if (this.txtNumber.getText().trim().length() > 80) {
		/* 1833 */           this.txtNumber.requestFocus();
		/* 1834 */           showErrorMessage("evectionReqNumberTooLong");
		/*      */         }
		/*      */       }
		/*      */ 
		/* 1838 */       if (BizCollUtil.objectIsNull(this.dateReqDate.getValue())) {
		/* 1839 */         this.dateReqDate.requestFocus();
		/* 1840 */         showErrorMessage("evectionReqReqDateNotNull");
		/*      */       }
		/*      */ 
		/* 1844 */       if (BizCollUtil.objectIsNull(this.bizPromptOrgUnit.getData())) {
		/* 1845 */         this.bizPromptOrgUnit.requestFocus();
		/* 1846 */         showErrorMessage("evectionReqOrgUnitNotNull");
		/*      */       }
		/*      */ 
		/* 1856 */       if (BizCollUtil.objectIsNull(this.bizPromptCompany.getData())) {
		/* 1857 */         this.bizPromptCompany.requestFocus();
		/* 1858 */         showErrorMessage("evectionReqCompanyNotNull");
		/*      */       }
		/*      */ 
		/* 1861 */       if (!(BizCollUtil.isBizUnitCompany((CompanyOrgUnitInfo)this.bizPromptCompany.getData())))
		/*      */       {
		/* 1864 */         this.bizPromptCompany.requestFocus();
		/* 1865 */         throw new ExpAccException(ExpAccException.ISBIZUNIT);
		/*      */       }
		/*      */ 
		/* 1868 */       if (BizCollUtil.objectIsNull(this.bizPromptApplierCompany.getData()))
		/*      */       {
		/* 1870 */         this.bizPromptApplierCompany.requestFocus();
		/* 1871 */         throw new ExpAccException(ExpAccException.A_COMPANY_NOTNULL);
		/*      */       }
		/*      */ 
		/* 1874 */       if (!(BizCollUtil.isBizUnitCompany((CompanyOrgUnitInfo)this.bizPromptApplierCompany.getData())))
		/*      */       {
		/* 1877 */         this.bizPromptApplierCompany.requestFocus();
		/* 1878 */         throw new ExpAccException(ExpAccException.A_COMPANY_NOTBIZUNIT);
		/*      */       }
		/*      */ 
		/* 1881 */       if (BizCollUtil.objectIsNull(this.bizPromptCostedDept.getData())) {
		/* 1882 */         this.bizPromptCostedDept.requestFocus();
		/* 1883 */         showErrorMessage("evectionReqCostedDeptNotNull");
		/*      */       }
		/*      */ 
		/* 1899 */       if (this.bizPromptCostedDept.getData() instanceof FullOrgUnitInfo) {
		/* 1900 */         ICostCenterOrgUnit iCost = CostCenterOrgUnitFactory.getRemoteInstance();
		/*      */ 
		/* 1902 */         BOSUuid ids = ((FullOrgUnitInfo)this.bizPromptCostedDept.getData()).getId();
		/*      */ 
		/* 1904 */         CostCenterOrgUnitInfo cost = (CostCenterOrgUnitInfo)iCost.getValue(new ObjectUuidPK(ids));
		/*      */ 
		/* 1906 */         this.bizPromptCostedDept.setData(cost);
		/*      */       }
		/*      */ 
		/* 1910 */       if (BizCollUtil.objectIsNull(this.comboPrior.getSelectedItem())) {
		/* 1911 */         this.comboPrior.requestFocus();
		/* 1912 */         showErrorMessage("evectionReqPriorNotNull");
		/*      */       }
		/*      */ 
		/* 1916 */       if (BizCollUtil.objectIsNull(this.bizPromptCurrencyType.getData())) {
		/* 1917 */         this.bizPromptCurrencyType.requestFocus();
		/* 1918 */         showErrorMessage("evectionReqCurrencyTypeNotNull");
		/*      */       }
		/*      */ 
		/* 1922 */       if (BizCollUtil.objectIsNull(this.dateBillDate.getValue())) {
		/* 1923 */         this.dateBillDate.requestFocus();
		/* 1924 */         showErrorMessage("evectionReqBillDateNotNull");
		/*      */       }
		/*      */ 
		/* 1928 */       if (BizCollUtil.objectIsNull(this.bizPromptBiller.getData())) {
		/* 1929 */         this.bizPromptBiller.requestFocus();
		/* 1930 */         showErrorMessage("evectionReqBillerNotNull");
		/*      */       }
		/*      */ 
		/* 1934 */       if (StringUtils.isEmpty(this.numberTextFieldTotalPeople.getText())) {
		/* 1935 */         this.numberTextFieldTotalPeople.requestFocus();
		/* 1936 */         showErrorMessage("evectionReqTotalPeopleNotNull");
		/* 1937 */       } else if (BizCollUtil.bigDecimalObjectLessThan(this.numberTextFieldTotalPeople.getText(), new BigDecimal("0.0")))
		/*      */       {
		/* 1940 */         this.numberTextFieldTotalPeople.requestFocus();
		/* 1941 */         showErrorMessage("evectionReqTotalPeopleIllegal");
		/*      */       }
		/*      */ 
//		/* 1958 */       if (StringUtils.isEmpty(this.formattedTextFieldAmount.getText())) {
//		/* 1959 */         this.formattedTextFieldAmount.requestFocus();
//		/* 1960 */         showErrorMessage("evectionReqAmountNotNull");
//		/*      */       }
		/*      */ 
		/* 1983 */       if ((!(StringUtils.isEmpty(this.textAreaCause.getText()))) && (this.textAreaCause.getText().trim().length() > 200))
		/*      */       {
		/* 1985 */         this.textAreaCause.requestFocus();
		/* 1986 */         showErrorMessage("evectionReqCauseTooLong");
		/*      */       }
		/*      */ 
		/* 1990 */       if ((!(StringUtils.isEmpty(this.textAreaDescription.getText()))) && (this.textAreaDescription.getText().trim().length() > 200))
		/*      */       {
		/* 1992 */         this.textAreaDescription.requestFocus();
		/* 1993 */         showErrorMessage("evectionReqDescriptionTooLong");
		/*      */       }
		/*      */ 
		/* 2004 */       checkReqEntry();
		/*      */ 
		/* 2007 */       if ((this.radioButtonTogetherAudit.isSelected()) && (this.ckNeedLoan.isSelected()))
		/*      */       {
		/* 2010 */         checkLoanEntry();
		/*      */       }
		/*      */     
	}
	
	/*      */   private void showErrorMessage(String message)
	/*      */   {
	/* 2926 */     String s = EASResource.getString(this.RES, message);
	/* 2927 */     MsgBox.showError(this, s);
	/* 2928 */     setCursorOfDefault();
	/* 2929 */     SysUtil.abort();
	/*      */   }
	
				 private void checkReqEntry()
	/*      */   {
		/* 2023 */     int rows = -1;
		/*      */ 
		/* 2025 */     if (getDetailTable() == null)
		/* 2026 */       rows = -1;
		/*      */     else {
		/* 2028 */       rows = getDetailTable().getRowCount();
		/*      */     }
		/*      */ 
		/* 2034 */     if (rows <= 0) {
		/* 2035 */       showErrorMessage("ReqEntryNotNull");
		/*      */     }
		/*      */ 
		/* 2038 */     KDTEditManager kdtManager = getDetailTable().getEditManager();
		/* 2039 */     IRow row = null;
		/* 2040 */     EvectionReqBillEntryInfo entryInfo = null;
		/* 2041 */     int count = 0;
		/*      */ 
		/* 2044 */     for (int i = 0; i < rows; ++i) {
		/* 2045 */       row = getDetailTable().getRow(i);
		/*      */ 
		/* 2048 */       if (checkIsCountLine(getDetailTable(), row)) {
		/*      */         continue;
		/*      */       }
		/*      */ 
		/* 2052 */       entryInfo = new EvectionReqBillEntryInfo();
		/* 2053 */       storeLineFields2(this.kdtEntries, row, entryInfo);
		/* 2054 */       if (checkIsBlankLine(this.kdtEntries, entryInfo))
		/*      */       {
		/*      */         continue;
		/*      */       }
		/*      */ 
		/* 2059 */       if (BizCollUtil.objectIsNull(row.getCell(this.TB_REQ_STARTDATE).getValue()))
		/*      */       {
		/* 2061 */         kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_REQ_STARTDATE));
		/*      */ 
		/* 2063 */         showErrorMessage("evectionReqStartDateNotNull");
		/*      */       }
		/*      */ 
		/* 2067 */       if (BizCollUtil.objectIsNull(row.getCell(this.TB_REQ_FROM).getValue())) {
		/* 2068 */         kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_REQ_FROM));
		/*      */ 
		/* 2070 */         showErrorMessage("evectionReqFromNotNull");
		/* 2071 */       } else if (row.getCell(this.TB_REQ_FROM).getValue().toString().trim().length() > 100)
		/*      */       {
		/* 2073 */         kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_REQ_FROM));
		/*      */ 
		/* 2075 */         showErrorMessage("evectionReqFromTooLong");
		/*      */       }
		/*      */ 
		/* 2079 */       if (BizCollUtil.objectIsNull(row.getCell(this.TB_REQ_ENDDATE).getValue()))
		/*      */       {
		/* 2081 */         kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_REQ_ENDDATE));
		/*      */ 
		/* 2083 */         showErrorMessage("evectionReqEndDateNotNull");
		/*      */       }
		/*      */ 
		/* 2087 */       Date start = (Date)row.getCell(this.TB_REQ_STARTDATE).getValue();
		/* 2088 */       Date end = (Date)row.getCell(this.TB_REQ_ENDDATE).getValue();
		/* 2089 */       if (start.after(end)) {
		/* 2090 */         kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_REQ_STARTDATE));
		/*      */ 
		/* 2092 */         showErrorMessage("evectionReqstartDateNotAfterEndDate");
		/*      */       }
		/*      */ 
		/* 2096 */       if (BizCollUtil.objectIsNull(row.getCell(this.TB_REQ_TO).getValue())) {
		/* 2097 */         kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_REQ_TO));
		/*      */ 
		/* 2099 */         showErrorMessage("evectionReqToNotNull");
		/* 2100 */       } else if (row.getCell(this.TB_REQ_TO).getValue().toString().trim().length() > 100)
		/*      */       {
		/* 2102 */         kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_REQ_TO));
		/*      */ 
		/* 2104 */         showErrorMessage("evectionReqToTooLong");
		/*      */       }
		/*      */ 
		/* 2108 */       if (BizCollUtil.objectIsNull(row.getCell(this.TB_REQ_VEHICLE).getValue()))
		/*      */       {
		/* 2110 */         kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_REQ_VEHICLE));
		/*      */ 
		/* 2112 */         showErrorMessage("evectionReqVehicleNotNull");
		/*      */       }
		/*      */ 
//		/* 2115 */       BigDecimal amount = (BigDecimal)row.getCell(this.TB_AMOUNT).getValue();
//		/* 2116 */       if ((amount == null) || (amount.compareTo(new BigDecimal("0.00")) <= 0)) {
//		/* 2117 */         kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_AMOUNT));
//		/*      */ 
//		/* 2119 */         showErrorMessage("amountGtZero");
//		/*      */       }
		/*      */ 
		/* 2136 */       ++count;
		/*      */     }
		/*      */ 
		/* 2140 */     if (count <= 0)
		/* 2141 */       showErrorMessage("ReqEntryNotNull");
		/*      */   }
		/*      */   private void checkLoanEntry()
		/*      */   {
			/* 2150 */     int rows = -1;
			/*      */ 
			/* 2153 */     this.tabbedPaneEntries.setSelectedIndex(1);
			/*      */ 
			/* 2155 */     if (this.loanEntries == null)
			/* 2156 */       rows = -1;
			/*      */     else {
			/* 2158 */       rows = this.loanEntries.getRowCount();
			/*      */     }
			/* 2160 */     if (rows <= 0) {
			/* 2161 */       showErrorMessage("loanEntryNotNull");
			/*      */     }
			/* 2163 */     KDTEditManager kdtManager = this.loanEntries.getEditManager();
			/* 2164 */     IRow row = null;
			/* 2165 */     EvectionLoanBillEntryInfo entryInfo = null;
			/* 2166 */     int count = 0;
			/*      */ 
			/* 2170 */     for (int i = 0; i < rows; ++i) {
			/* 2171 */       row = this.loanEntries.getRow(i);
			/*      */ 
			/* 2174 */       if (checkIsCountLine(this.loanEntries, row)) {
			/*      */         continue;
			/*      */       }
			/*      */ 
			/* 2178 */       entryInfo = new EvectionLoanBillEntryInfo();
			/* 2179 */       storeLineFields2(this.loanEntries, row, entryInfo);
			/* 2180 */       if (checkIsBlankLine(this.loanEntries, entryInfo))
			/*      */       {
			/*      */         continue;
			/*      */       }
			/*      */ 
			/* 2185 */       if (BizCollUtil.objectIsNull(row.getCell(this.TB_LOAN_STARTDATE).getValue()))
			/*      */       {
			/* 2187 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_STARTDATE));
			/*      */ 
			/* 2189 */         showErrorMessage("evectionLoanStartDateNotNull");
			/*      */       }
			/*      */ 
			/* 2193 */       if (BizCollUtil.objectIsNull(row.getCell(this.TB_LOAN_FROM).getValue())) {
			/* 2194 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_FROM));
			/*      */ 
			/* 2196 */         showErrorMessage("evectionLoanFromNotNull");
			/* 2197 */       } else if (row.getCell(this.TB_LOAN_FROM).getValue().toString().trim().length() > 100)
			/*      */       {
			/* 2199 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_FROM));
			/*      */ 
			/* 2201 */         showErrorMessage("evectionLoanFromTooLong");
			/*      */       }
			/*      */ 
			/* 2205 */       if (BizCollUtil.objectIsNull(row.getCell(this.TB_LOAN_ENDDATE).getValue()))
			/*      */       {
			/* 2207 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_ENDDATE));
			/*      */ 
			/* 2209 */         showErrorMessage("evectionLoanEndDateNotNull");
			/*      */       }
			/*      */ 
			/* 2213 */       Date start = (Date)row.getCell(this.TB_LOAN_STARTDATE).getValue();
			/* 2214 */       Date end = (Date)row.getCell(this.TB_LOAN_ENDDATE).getValue();
			/* 2215 */       if (start.after(end)) {
			/* 2216 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_STARTDATE));
			/*      */ 
			/* 2218 */         showErrorMessage("evectionLoanstartDateNotAfterEndDate");
			/*      */       }
			/*      */ 
			/* 2222 */       if (BizCollUtil.objectIsNull(row.getCell(this.TB_LOAN_TO).getValue())) {
			/* 2223 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_TO));
			/*      */ 
			/* 2225 */         showErrorMessage("evectionLoanToNotNull");
			/* 2226 */       } else if (row.getCell(this.TB_LOAN_TO).getValue().toString().trim().length() > 100)
			/*      */       {
			/* 2228 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_TO));
			/*      */ 
			/* 2230 */         showErrorMessage("evectionLoanToTooLong");
			/*      */       }
			/*      */ 
			/* 2234 */       if (BizCollUtil.objectIsNull(row.getCell(this.TB_LOAN_VEHICLE).getValue()))
			/*      */       {
			/* 2236 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_VEHICLE));
			/*      */ 
			/* 2238 */         showErrorMessage("evectionLoanVehicleNotNull");
			/*      */       }
			/*      */ 
			/* 2249 */       if (BizCollUtil.bigDecimalObjectLessThan(row.getCell(this.TB_LOAN_BUSSESEXPENSE).getValue(), new BigDecimal("0.0")))
			/*      */       {
			/* 2251 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_BUSSESEXPENSE));
			/*      */ 
			/* 2253 */         showErrorMessage("evectionLoanBussexpenseIllegal");
			/*      */       }
			/*      */ 
			/* 2264 */       if (BizCollUtil.bigDecimalObjectLessThan(row.getCell(this.TB_LOAN_TAXIEXPENSE).getValue(), new BigDecimal("0.0")))
			/*      */       {
			/* 2266 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_TAXIEXPENSE));
			/*      */ 
			/* 2268 */         showErrorMessage("evectionLoanTaxiexpenseIllegal");
			/*      */       }
			/*      */ 
			/* 2279 */       if (BizCollUtil.bigDecimalObjectLessThan(row.getCell(this.TB_LOAN_HOTELEXPENSE).getValue(), new BigDecimal("0.0")))
			/*      */       {
			/* 2281 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_HOTELEXPENSE));
			/*      */ 
			/* 2283 */         showErrorMessage("evectionLoanHotelExpenseIllegal");
			/*      */       }
			/*      */ 
			/* 2294 */       if (BizCollUtil.bigDecimalObjectLessThan(row.getCell(this.TB_LOAN_OTHEREXPENSE).getValue(), new BigDecimal("0.0")))
			/*      */       {
			/* 2296 */         kdtManager.editCellAt(i, this.loanEntries.getColumnIndex(this.TB_LOAN_OTHEREXPENSE));
			/*      */ 
			/* 2298 */         showErrorMessage("evectionLoanOtherExpenseIllegal");
			/*      */       }
			/*      */ 
			/* 2302 */       ++count;
			/*      */     }
			/*      */ 
			/* 2306 */     if (count <= 0)
			/* 2307 */       showErrorMessage("loanEntryNotNull");
			/*      */   }
		/*      */   private void storeLineFields2(KDTable table, IRow row, IObjectValue obj)
		/*      */   {
		/* 2312 */     table.checkParsed();
		/*      */ 
		/* 2314 */     int i = 0; for (int n = table.getColumnCount(); i < n; ++i) {
		/* 2315 */       String bindField = table.getColumn(i).getFieldName();
		/* 2316 */       if (!(StringUtils.isEmpty(bindField)))
		/* 2317 */         if (bindField.indexOf(".") >= 0) {
		/* 2318 */           String[] subItems = StringUtils.split(bindField, ".");
		/*      */ 
		/* 2320 */           IObjectValue subObj = obj;
		/* 2321 */           int k = 0; for (int l = subItems.length - 1; k < l; ++k) {
		/* 2322 */             subObj = (IObjectValue)subObj.get(subItems[k]);
		/* 2323 */             if (subObj == null)
		/*      */               break;
		/*      */           }
		/* 2326 */           if (subObj != null)
		/* 2327 */             subObj.put(subItems[(subItems.length - 1)], row.getCell(i).getValue());
		/*      */         }
		/*      */         else {
		/* 2330 */           Class type = obj.getClass();
		/*      */           try {
		/* 2332 */             String setMethodName = "set" + StringUtils.headCharUpperCase(bindField);
		/*      */ 
		/* 2334 */             Method[] methods = type.getMethods();
		/* 2335 */             Method method = null;
		/* 2336 */             int k = 0; for (int l = methods.length; k < l; ++k) {
		/* 2337 */               if (!(setMethodName.equalsIgnoreCase(methods[k].getName())))
		/*      */                 continue;
		/* 2339 */               method = methods[k];
		/* 2340 */               break;
		/*      */             }
		/*      */ 
		/* 2343 */             if (method != null) {
		/* 2344 */               method.invoke(obj, new Object[] { row.getCell(i).getValue() });
		/*      */             }
		/*      */             else
		/* 2347 */               obj.put(bindField, row.getCell(i).getValue());
		/*      */           } catch (Exception e) {
		/* 2349 */             obj.put(bindField, row.getCell(i).getValue());
		/*      */           }
		/*      */         }
		/*      */     }
		/*      */   }
		/*      */   private boolean checkIsBlankLine(KDTable table, AbstractObjectValue objValue)
		/*      */   {
		/* 2505 */     return ObjectValueUtil.objectValueEquals((AbstractObjectValue)createNewDetailData(table), objValue);
		/*      */   }
		/*      */ 
		/*      */   private boolean checkIsCountLine(KDTable table, IRow row)
		/*      */   {
		/* 2513 */     if (row == null) {
		/* 2514 */       return false;
		/*      */     }
		/*      */ 
		/* 2517 */     if (table == this.loanEntries) {
		/* 2518 */       if (row.getCell(this.TB_LOAN_STARTDATE).getValue() == null) {
		/* 2519 */         return false;
		/*      */       }
		/*      */ 
		/* 2522 */       return EASResource.getString(this.RES, "count").equals(row.getCell(this.TB_LOAN_STARTDATE).getValue().toString());
		/*      */     }
		/*      */ 
		/* 2525 */     if (row.getCell(this.TB_REQ_STARTDATE).getValue() == null) {
		/* 2526 */       return false;
		/*      */     }
		/*      */ 
		/* 2529 */     return EASResource.getString(this.RES, "count").equals(row.getCell(this.TB_REQ_STARTDATE).getValue().toString());
		/*      */   }
		
		@Override
		protected void beforeSubmit() throws Exception {
		}
}
