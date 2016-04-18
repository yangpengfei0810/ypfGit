package com.kingdee.eas.cp.bc.app;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.EvectionReqBillEntryInfo;
import com.kingdee.eas.cp.bc.EvectionReqBillException;
import com.kingdee.eas.cp.bc.EvectionReqBillInfo;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.util.StringUtils;

public class EvectionReqBillControllerBeanEx extends EvectionReqBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.cp.bc.app.EvectionReqBillControllerBeanEx");
	@Override
	protected void checkValidate(Context ctx, EvectionReqBillInfo info)
			throws EASBizException {

		/* 252 */     if ((!(StringUtils.isEmpty(info.getName()))) && 
		/* 253 */       (info.getName().trim().length() > 80)) {
		/* 254 */       throw new EvectionReqBillException(EvectionReqBillException.NAME_TOO_LONG);
		/*     */     }
		/*     */ 
		/* 260 */     if (BizCollUtil.objectIsNull(info.getBizReqDate())) {
		/* 261 */       throw new EvectionReqBillException(EvectionReqBillException.REQDATE_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 273 */     if (StringUtils.isEmpty(info.getNumber())) {
		/* 274 */       throw new EvectionReqBillException(EvectionReqBillException.NUMBER_NOT_NULL);
		/*     */     }
		/* 276 */     if (info.getNumber().trim().length() > 80) {
		/* 277 */       throw new EvectionReqBillException(EvectionReqBillException.NUMBER_TOO_LONG);
		/*     */     }
		/*     */ 
		/* 282 */     if ((BizCollUtil.objectIsNull(info.getApplier())) && (BizCollUtil.objectIsNull(info.getApplier().getId())))
		/*     */     {
		/* 284 */       throw new EvectionReqBillException(EvectionReqBillException.APPLIER_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 288 */     if (!(BizCollUtil.isBizUnitCostCenter(ctx, info.getCostedDept()))) {
		/* 289 */       throw new ExpAccException(ExpAccException.COSTCENTER_NOT_BIZUNIT);
		/*     */     }
		/*     */ 
		/* 292 */     if ((BizCollUtil.objectIsNull(info.getOrgUnit())) && (BizCollUtil.objectIsNull(info.getOrgUnit().getId())))
		/*     */     {
		/* 294 */       throw new EvectionReqBillException(EvectionReqBillException.ORGUNIT_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 299 */     if ((BizCollUtil.objectIsNull(info.getCompany())) && (BizCollUtil.objectIsNull(info.getCompany().getId())))
		/*     */     {
		/* 301 */       throw new EvectionReqBillException(EvectionReqBillException.COMPANY_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 306 */     if (BizCollUtil.objectIsNull(info.getPrior())) {
		/* 307 */       throw new EvectionReqBillException(EvectionReqBillException.PRIOR_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 312 */     if (BizCollUtil.objectIsNull(info.getBillDate())) {
		/* 313 */       throw new EvectionReqBillException(EvectionReqBillException.BILLDATE_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 318 */     if ((BizCollUtil.objectIsNull(info.getBiller())) && (BizCollUtil.objectIsNull(info.getBiller().getId())))
		/*     */     {
		/* 320 */       throw new EvectionReqBillException(EvectionReqBillException.BILLER_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 325 */     if ((BizCollUtil.objectIsNull(info.getCostedDept())) && (BizCollUtil.objectIsNull(info.getCostedDept().getId())))
		/*     */     {
		/* 327 */       throw new EvectionReqBillException(EvectionReqBillException.COSTEDDEPT_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 332 */     if ((BizCollUtil.objectIsNull(info.getCurrencyType())) && (BizCollUtil.objectIsNull(info.getCurrencyType().getId())))
		/*     */     {
		/* 334 */       throw new EvectionReqBillException(EvectionReqBillException.CURRENCYTYPE_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 339 */     if ((!(BizCollUtil.objectIsNull(info.getCause()))) && (info.getCause().trim().length() > 200))
		/*     */     {
		/* 341 */       throw new EvectionReqBillException(EvectionReqBillException.CAUSE_TOO_LONG);
		/*     */     }
		/*     */ 
		/* 346 */     if ((!(BizCollUtil.objectIsNull(info.getDescription()))) && (info.getDescription().trim().length() > 200))
		/*     */     {
		/* 348 */       throw new EvectionReqBillException(EvectionReqBillException.DESCRIPTION_TOO_LONG);
		/*     */     }
		/*     */ 
		/* 353 */     if (BizCollUtil.bigDecimalObjectLessThan(info.getIntendingDays(), new BigDecimal("0.0")))
		/*     */     {
		/* 355 */       throw new EvectionReqBillException(EvectionReqBillException.INTENDINGDAYS_ILLEGAL);
		/*     */     }
		/*     */ 
		//×¢ÊÍµôÐ£Ñé½ð¶î
//		/* 360 */     11 if (BizCollUtil.bigDecimalObjectLessThan(info.getAmount(), new BigDecimal("0.0")))
//		/*     */     {
//		/* 362 */       throw new EvectionReqBillException(EvectionReqBillException.AMOUNT_ILLEGAL);
//		/*     */     }
		/*     */ 
		/* 367 */     if (info.getTotalPeople() < 0) {
		/* 368 */       throw new EvectionReqBillException(EvectionReqBillException.TOTALPEOPLE_ILLEGAL);
		/*     */     }
		/*     */ 
		/* 373 */     if (BizCollUtil.objectIsNull(info.getEntries())) {
		/* 374 */       throw new EvectionReqBillException(EvectionReqBillException.ENTRY_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 377 */     int size = info.getEntries().size();
		/*     */ 
		/* 379 */     if (size <= 0) {
		/* 380 */       throw new EvectionReqBillException(EvectionReqBillException.ENTRY_NOT_NULL);
		/*     */     }
		/*     */ 
		/* 383 */     EvectionReqBillEntryInfo entryInfo = null;
		/*     */ 
		/* 385 */     for (int i = 0; i < size; ++i) {
		/* 386 */       entryInfo = info.getEntries().get(i);
		/*     */ 
		/* 389 */       if (BizCollUtil.objectIsNull(entryInfo.getStartDate())) {
		/* 390 */         throw new EvectionReqBillException(EvectionReqBillException.STARTDATE_NOT_NULL);
		/*     */       }
		/*     */ 
		/* 395 */       if (BizCollUtil.objectIsNull(entryInfo.getFrom())) {
		/* 396 */         throw new EvectionReqBillException(EvectionReqBillException.FROM_NOT_NULL);
		/*     */       }
		/* 398 */       if (entryInfo.getFrom().trim().length() > 100) {
		/* 399 */         throw new EvectionReqBillException(EvectionReqBillException.FROM_TOO_LONG);
		/*     */       }
		/*     */ 
		/* 404 */       if (BizCollUtil.objectIsNull(entryInfo.getEndDate())) {
		/* 405 */         throw new EvectionReqBillException(EvectionReqBillException.ENDDATE_NOT_NULL);
		/*     */       }
		/*     */ 
		/* 410 */       if (entryInfo.getStartDate().after(entryInfo.getEndDate())) {
		/* 411 */         throw new EvectionReqBillException(EvectionReqBillException.STARTDATE_NOT_GREATER_ENDDATE);
		/*     */       }
		/*     */ 
		/* 416 */       if (BizCollUtil.objectIsNull(entryInfo.getTo())) {
		/* 417 */         throw new EvectionReqBillException(EvectionReqBillException.TO_NOT_NULL);
		/*     */       }
		/* 419 */       if (entryInfo.getTo().trim().length() > 100) {
		/* 420 */         throw new EvectionReqBillException(EvectionReqBillException.TO_TOO_LONG);
		/*     */       }
		/*     */ 
		/* 425 */       if (BizCollUtil.objectIsNull(entryInfo.getVehicle()))
		/* 426 */         throw new EvectionReqBillException(EvectionReqBillException.VEHICLE_NOT_NULL);
		/*     */     }
		/*     */   
	}
}
