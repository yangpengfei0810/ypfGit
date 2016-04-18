package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.bot.IBOTRelation;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.AmountControlTypeEnum;
import com.kingdee.eas.cp.bc.BizAccountBillEntryCollection;
import com.kingdee.eas.cp.bc.BizAccountBillEntryFactory;
import com.kingdee.eas.cp.bc.BizAccountBillEntryInfo;
import com.kingdee.eas.cp.bc.BizAccountBillFactory;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.BizBillUtil;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.DailyLoanBillFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.DailyPurchaseAccountBillFactory;
import com.kingdee.eas.cp.bc.DailyPurchaseAccountBillInfo;
import com.kingdee.eas.cp.bc.EntryStateEnum;
import com.kingdee.eas.cp.bc.EvectionLoanBillFactory;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.cp.bc.ExpenseAccountBillInfo;
import com.kingdee.eas.cp.bc.ExpenseAccountFacadeFactory;
import com.kingdee.eas.cp.bc.IBizAccountBill;
import com.kingdee.eas.cp.bc.IBizAccountBillEntry;
import com.kingdee.eas.cp.bc.IDailyLoanBill;
import com.kingdee.eas.cp.bc.IDailyPurchaseAccountBill;
import com.kingdee.eas.cp.bc.IEvectionLoanBill;
import com.kingdee.eas.cp.bc.IExpenseAccountFacade;
import com.kingdee.eas.cp.bc.ITravelAccountBill;
import com.kingdee.eas.cp.bc.LoanBillInfo;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.cp.bc.TravelAccountBillFactory;
import com.kingdee.eas.cp.bc.TravelAccountBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;
import com.kingdee.util.db.SQLUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Locale;
import org.apache.log4j.Logger;

public abstract class ExpenseAccountBillControllerBean extends AbstractExpenseAccountBillControllerBean
{
  private static Logger logger = Logger.getLogger("com.kingdee.eas.cp.bc.app.ExpenseAccountBillControllerBean");

  private static String bizCollResource = "com.kingdee.eas.cp.bc.BizCollResource";

  private BigDecimal ZERO = new BigDecimal("0.00");

  protected BigDecimal _sumBudgetBalance(Context ctx) throws BOSException, EASBizException
  {
    return null;
  }

  protected BigDecimal _sumAmountRefunded(Context ctx) throws BOSException, EASBizException
  {
    return null;
  }

  protected void checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException
  {
    CoreBillBaseInfo dataBaseInfo = (CoreBillBaseInfo)model;
    FilterInfo filter = new FilterInfo();
    FilterItemInfo filterItem = new FilterItemInfo("number", dataBaseInfo.getNumber(), CompareType.EQUALS);

    filter.getFilterItems().add(filterItem);
    if (dataBaseInfo.getId() != null) {
      filterItem = new FilterItemInfo("id", dataBaseInfo.getId(), CompareType.NOTEQUALS);

      filter.getFilterItems().add(filterItem);
      filter.setMaskString("#0 and #1");
    }

    if (super._exists(ctx, filter))
      throw new ExpAccException(ExpAccException.NUMBER_NOT_DUP);
  }

  private void checkCodingRuleNumber(Context ctx, IObjectValue model)
    throws CodingRuleException, EASBizException, BOSException
  {
    ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);

    String orgId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();

    if ((iCodingRuleManager.isExist(model, orgId)) && ((iCodingRuleManager.isUseIntermitNumber(model, orgId)) || (((ExpenseAccountBillInfo)model).getNumber() == null)))
    {
      String number = iCodingRuleManager.getNumber(model, orgId);
      ((ExpenseAccountBillInfo)model).setNumber(number);
    }
  }

  private void recycleRuleNumber(Context ctx, IObjectPK pk, CoreBillBaseInfo info)
    throws CodingRuleException, EASBizException, BOSException
  {
    ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);

    String orgId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
    if (BizCollUtil.objectIsNull(orgId)) {
      orgId = BizCollUtil.getNextCompanyId(ctx);
    }
    if ((iCodingRuleManager.isExist(info, orgId)) && (iCodingRuleManager.isUseIntermitNumber(info, orgId)))
    {
      iCodingRuleManager.recycleNumber(info, orgId, info.getNumber());
    }
  }

  private boolean objectIsNull(Object obj)
  {
    return (obj == null) || (StringUtils.isEmpty(obj.toString()));
  }

  protected void checkBill(Context ctx, IObjectValue info)
    throws BOSException, EASBizException
  {
    ExpenseAccountBillInfo model = (ExpenseAccountBillInfo)info;
    StateEnum state = model.getState();
    if (StateEnum.CLOSED.equals(state)) {
      throw new ExpAccException(ExpAccException.CLOSE_NOT_DO);
    }

    if (objectIsNull(model.getNumber())) {
      throw new ExpAccException(ExpAccException.NUMBER_NOT_NULL);
    }

    if ((!objectIsNull(model.getName())) && 
      (model.getName().length() > 80)) {
      throw new ExpAccException(ExpAccException.NAME_TOO_LONG);
    }

    if (model.getBizReqDate() == null) {
      throw new ExpAccException(ExpAccException.EXPENSEDATE_NOT_NULL);
    }

    if (model.getApplier() == null) {
      throw new ExpAccException(ExpAccException.APPLIER_NOT_NULL);
    }

    if (model.getOrgUnit() == null) {
      throw new ExpAccException(ExpAccException.ORGUNIT_NOT_NULL);
    }

    if (model.getCostedDept() == null) {
      throw new ExpAccException(ExpAccException.REQDEPARTMENT_NOT_NULL);
    }

    if (!BizCollUtil.isBizUnitCostCenter(ctx, model.getCostedDept())) {
      throw new ExpAccException(ExpAccException.COSTCENTER_NOT_BIZUNIT);
    }

    if (!BizCollUtil.isBizUnitCompany(model.getApplierCompany())) {
      throw new ExpAccException(ExpAccException.A_COMPANY_NOTBIZUNIT);
    }

    if (model.getCurrencyType() == null) {
      throw new ExpAccException(ExpAccException.CUR_NOT_NULL);
    }

    if (model.getPrior() == null) {
      throw new ExpAccException(ExpAccException.PRIOR_NOT_NULL);
    }

    if ((model.getCause() != null) && (model.getCause().length() > 200)) {
      throw new ExpAccException(ExpAccException.CAUSE_TOO_LONG);
    }
    if ((model.getDescription() != null) && (model.getDescription().length() > 1000))
    {
      throw new ExpAccException(ExpAccException.DESCRIPTIOIN_TOO_LONG);
    }

    if ((model.getTel() != null) && (model.getTel().length() > 50)) {
      throw new ExpAccException(ExpAccException.TEL_TOO_LONG);
    }

    checkEntry(model);
  }

  protected void checkEntry(ExpenseAccountBillInfo model)
    throws BOSException, EASBizException
  {
  }

  private void reWriteBillState(Context ctx, String id, StateEnum state)
    throws BOSException, EASBizException
  {
    IExpenseAccountFacade facade = ExpenseAccountFacadeFactory.getLocalInstance(ctx);

    facade.setState(BOSUuid.read(id), state);
  }

  protected IObjectPK _addnew(Context ctx, IObjectValue model)
    throws BOSException, EASBizException
  {
    checkNumberDup(ctx, model);
    bizRuleBind(ctx, (ExpenseAccountBillInfo)model);

    return super._addnew(ctx, model);
  }

  protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
    throws BOSException, EASBizException
  {
    checkNumberDup(ctx, model);
    bizRuleBind(ctx, (ExpenseAccountBillInfo)model);

    super._addnew(ctx, pk, model);
  }

  protected void checkAddNew(Context ctx, IObjectValue model, String action) throws BOSException, EASBizException
  {
    BizCollUtil.verdictRuleNumber(ctx, model);
    if ("submit".equals(action))
      checkBill(ctx, (ExpenseAccountBillInfo)model);
    else if ("save".equals(action)) {
      checkSaveEntry((ExpenseAccountBillInfo)model);
    }
    checkNumberDup(ctx, model);
  }

  protected void checkUpdate(Context ctx, IObjectValue model, String action)
    throws BOSException, EASBizException
  {
    if ("submit".equals(action))
      checkBill(ctx, (ExpenseAccountBillInfo)model);
    else if ("save".equals(action)) {
      checkSaveEntry((ExpenseAccountBillInfo)model);
    }
    checkNumberDup(ctx, model);
  }

  protected void checkSaveEntry(ExpenseAccountBillInfo info)
    throws BOSException, EASBizException
  {
    if (objectIsNull(info.getNumber()))
      throw new ExpAccException(ExpAccException.NUMBER_NOT_NULL);
  }

  protected void bizRuleBind(Context ctx, ExpenseAccountBillInfo model)
  {
    BigDecimal budgetAmount = model.getBudgetAmount() == null ? this.ZERO : model.getBudgetAmount();

    BigDecimal sendedBack = model.getAmountSendedBack() == null ? this.ZERO : model.getAmountSendedBack();

    BigDecimal amountApproved = model.getAmountApproved() == null ? this.ZERO : model.getAmountApproved();

    if (BizCollUtil.isLoanBill(model.getSourceBillId()))
    {
      if (AmountControlTypeEnum.oneTOone.equals(model.getAmountControlType()))
      {
        model.setAmountSendedBack(amountApproved.add(budgetAmount.negate()));
      }
      else if (AmountControlTypeEnum.oneTOmore.equals(model.getAmountControlType()))
      {
        model.setAmountSendedBack(amountApproved.add(budgetAmount.negate()));
      }
      else
      {
        model.setAmountSendedBack(this.ZERO);
      }
    }
    else {
      model.setAmountSendedBack(this.ZERO);
    }

    model.setBudgetBalance(budgetAmount.add(amountApproved.negate()));
  }

  protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
    throws BOSException, EASBizException
  {
    bizRuleBind(ctx, (ExpenseAccountBillInfo)model);

    super._update(ctx, pk, model);
  }

  protected void checkDelete(Context ctx, CoreBillBaseInfo info)
    throws BOSException, EASBizException
  {
    ExpenseAccountBillInfo accountInfo = (ExpenseAccountBillInfo)info;

    StateEnum state = accountInfo.getState();
  /*  if ((StateEnum.DRAFT.equals(state)) || (StateEnum.CANCELED.equals(state)) || (StateEnum.NEW.equals(state)) || (StateEnum.SUBMIT.equals(state)) || (StateEnum.ALREADYABANDON.equals(state)))
    {
      return;
    }*/
    if (!StateEnum.CLOSED.equals(state)&&(StateEnum.ALREADYABANDON.equals(state)||(StateEnum.DRAFT.equals(state))))
    {
      return;
    }
    throw new ExpAccException(ExpAccException.DEL_ONLY_STATE);
  }

  protected void _delete(Context ctx, IObjectPK pk)
    throws BOSException, EASBizException
  {
    CoreBillBaseInfo info = (CoreBillBaseInfo)getValue(ctx, pk);
    checkDelete(ctx, info);
    abortWorkingWf(ctx, pk);
    super._delete(ctx, pk);

    recycleRuleNumber(ctx, pk, info);
  }

  private boolean isDoPayment(Context ctx, BOSUuid billId)
    throws BOSException
  {
    IBOTRelation iBOTRelation = BOTRelationFactory.getLocalInstance(ctx);

    FilterInfo filter = new FilterInfo();

    filter.getFilterItems().add(new FilterItemInfo("srcObjectID", billId, CompareType.EQUALS));

    Connection cn = null;
    try {
      cn = getConnection(ctx);
      IORMappingDAO dao = new ORMappingDAO(iBOTRelation.getType(), ctx, cn);

      boolean bool = dao.exists(filter);
      return bool; } finally { SQLUtils.cleanup(cn); }
  }

  protected void _wfPayment(Context ctx, BOSUuid billId)
    throws BOSException, EASBizException
  {
    if (!isDoPayment(ctx, billId)) {
      return;
    }

    IExpenseAccountFacade facade = ExpenseAccountFacadeFactory.getLocalInstance(ctx);
    facade.setState(billId, StateEnum.ALREADYPAYMENT);
  }

  private boolean isDoVoucher(Context ctx, BOSUuid billId) throws BOSException
  {
    return true;
  }

  protected void _wfGenVoucher(Context ctx, BOSUuid billId)
    throws BOSException, EASBizException
  {
    if (!isDoVoucher(ctx, billId)) {
      return;
    }

    IExpenseAccountFacade facade = ExpenseAccountFacadeFactory.getLocalInstance(ctx);
    facade.setState(billId, StateEnum.CLOSED);
  }

  protected void _reverseSave(Context ctx, IObjectPK srcBillPK, IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum, IObjectValue bOTRelationInfo)
    throws BOSException, EASBizException
  {
    Locale local = ctx.getLocale();

    BOTRelationInfo botObj = (BOTRelationInfo)bOTRelationInfo;

    ExpenseAccountBillInfo srcObj = null;

    if ((srcBillVO instanceof BizAccountBillInfo)) {
      srcObj = BizAccountBillFactory.getLocalInstance(ctx).getBizAccountBillInfo(new ObjectUuidPK(((BizCollBillBaseInfo)srcBillVO).getId().toString()));
    }
    else if ((srcBillVO instanceof TravelAccountBillInfo)) {
      srcObj = TravelAccountBillFactory.getLocalInstance(ctx).getTravelAccountBillInfo(new ObjectUuidPK(((BizCollBillBaseInfo)srcBillVO).getId().toString()));
    }
    else if ((srcBillVO instanceof DailyPurchaseAccountBillInfo)) {
      srcObj = DailyPurchaseAccountBillFactory.getLocalInstance(ctx).getDailyPurchaseAccountBillInfo(new ObjectUuidPK(((BizCollBillBaseInfo)srcBillVO).getId().toString()));
    }

    IDynamicObject iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);

    ObjectUuidPK objpk = new ObjectUuidPK(botObj.getBOTMappingID());
    BOTMappingInfo mappingInfo = (BOTMappingInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk);

    boolean isSuspenseAcc = "Y".equals(srcObj.getActionFlag());

    logger.info(ResourceBase.getString(bizCollResource, "RewriteExpenseAccountBill", local) + "id, isSuspenseAcc " + objpk.toString() + " ," + isSuspenseAcc);

    if (isSuspenseAcc) {
      setActionFlag(ctx, srcObj, null);
    }

    boolean isFi = BizCollUtil.isNeedRelaFi(ctx, srcObj.getId());
    boolean isToVoucher = false;
    isToVoucher = BizCollUtil.isLoanToVoucher(ctx, srcObj.getId());
    boolean isReceiveBill = false;
    boolean isPayBill = false;
    Object param = null;
    try {
      param = BizBillUtil.getParamByNumber(ctx, "CP004");
      if (param == null)
        isReceiveBill = false;
      else {
        isReceiveBill = new Boolean(param.toString()).booleanValue();
      }
      param = BizBillUtil.getParamByNumber(ctx, "CP006");
      if (param == null)
        isPayBill = false;
      else
        isPayBill = new Boolean(param.toString()).booleanValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    BOSObjectType type = BizCollUtil.getBOSType(botObj.getDestObjectID());
    if (new PaymentBillInfo().getBOSType().equals(type)) {
      logger.info(ResourceBase.getString(bizCollResource, "ReverseFromPaymentBill", local));

      if ((BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) || (BOTBillOperStateEnum.EDIT.equals(bOTBillOperStateEnum)))
      {
        if ((StateEnum.CHECKED.equals(srcObj.getState())) || (StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState())) || (StateEnum.ISSUSPENSEACCOUNT.equals(srcObj.getState())))
        {
          logger.info(ResourceBase.getString(bizCollResource, "AccountBillBeAlreadyPayment", local) + srcBillPK.toString());

          if (StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()))
          {
            setBillfiVouchered(srcObj, ctx, true);

            _deleteVoucher(ctx, new ObjectUuidPK(srcObj.getId()));

            setBillfiVouchered(srcObj, ctx, false);
          }
          reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ISPAYBILL);

          logger.info(ResourceBase.getString(bizCollResource, "AccountBillBePayment", local));
        }
        else {
          logger.info(ResourceBase.getString(bizCollResource, "BillMustBeChecked", local));
        }
      }
      else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
        if (isSourceBillClose(ctx, srcObj)) {
          throw new BizCollException(BizCollException.ISSOURCEBILLCLOSE);
        }
        if (isSourceBillRelatedBill(ctx, srcObj, botObj.getDestObjectID()))
        {
          throw new BizCollException(BizCollException.CANNOT_DELPAYMENT_HASBOTP);
        }

        if (StateEnum.ISPAYBILL.equals(srcObj.getState()))
        {
          logger.info(ResourceBase.getString(bizCollResource, "AccountBillBeChecked", local) + srcBillPK.toString());

          ctx.put("isReWrite", new Boolean(true));
          srcObj.setState(StateEnum.CHECKED);

          if ("R".equals(srcObj.getActionFlag())) {
            reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ISSUSPENSEACCOUNT);
          }
          else {
            reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CHECKED);
          }

          ctx.put("isReWrite", null);
          logger.info(ResourceBase.getString(bizCollResource, "AccountBillHasBeChecked", local));
        }
        else {
          logger.info(ResourceBase.getString(bizCollResource, "BillMustBePayed", local));
        }
      }

    }
    else if (new VoucherInfo().getBOSType().equals(type)) {
      logger.info(ResourceBase.getString(bizCollResource, "ReverseFromVoucher", local));

      BigDecimal backAmount = srcObj.getAmountSendedBack() == null ? this.ZERO : srcObj.getAmountSendedBack();

      if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum))
      {
        if (mappingInfo.getIsTempSave() == 0) {
          if (isSuspenseAcc) {
            reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ISSUSPENSEACCOUNT);

            setActionFlag(ctx, srcObj, "R");
          } else {
            if ((isPayBill) || (isReceiveBill)) {
              reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ALREADYPAYMENT);
            }
            else if (StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()))
            {
              setBillfiVouchered(srcObj, ctx, true);

              _deleteVoucher(ctx, new ObjectUuidPK(srcObj.getId()));

              setBillfiVouchered(srcObj, ctx, false);
              reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CLOSED);
            }
            else if (StateEnum.ISSUSPENSEACCOUNT.equals(srcObj.getState()))
            {
              reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CLOSED);
            }
            else if ((StateEnum.CHECKED.equals(srcObj.getState())) || (StateEnum.ALREADYPAYMENT.equals(srcObj.getState())))
            {
              reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CLOSED);
            }

            logger.info(ResourceBase.getString(bizCollResource, "AccountBillBeClosed", local));

            setBillfiVouchered(srcObj, ctx, true);
          }

        }
        else if (isSuspenseAcc) {
          reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ALREADYSUSPENSEACCOUNT);
        }
        else {
          if ((isPayBill) || (isReceiveBill)) {
            reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ALREADYVOUCHER);
          }
          else if (StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState()))
          {
            setBillfiVouchered(srcObj, ctx, true);

            _deleteVoucher(ctx, new ObjectUuidPK(srcObj.getId()));

            setBillfiVouchered(srcObj, ctx, false);

            reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CLOSED);
          }
          else if (StateEnum.ISSUSPENSEACCOUNT.equals(srcObj.getState()))
          {
            reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CLOSED);
          }
          else if ((StateEnum.CHECKED.equals(srcObj.getState())) || (StateEnum.ALREADYPAYMENT.equals(srcObj.getState())))
          {
            reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CLOSED);
          }

          logger.info(ResourceBase.getString(bizCollResource, "AccountBillBeClosed", local));

          setBillfiVouchered(srcObj, ctx, true);
        }
      }
      else if (BOTBillOperStateEnum.EDIT.equals(bOTBillOperStateEnum)) {
        if (StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState())) {
          reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ISSUSPENSEACCOUNT);

          setActionFlag(ctx, srcObj, "R");
        } else if (StateEnum.ALREADYVOUCHER.equals(srcObj.getState()))
        {
          reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ALREADYPAYMENT);
        }
      }
      else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum))
      {
        if (isSourceBillClose(ctx, srcObj)) {
          throw new BizCollException(BizCollException.ISSOURCEBILLCLOSE);
        }

        if (isSourceBillRelatedBill(ctx, srcObj, botObj.getDestObjectID()))
        {
          throw new BizCollException(BizCollException.CANNOT_DELVOCHER_HASBOTP);
        }

        if ((StateEnum.ALREADYSUSPENSEACCOUNT.equals(srcObj.getState())) || (StateEnum.ISSUSPENSEACCOUNT.equals(srcObj.getState())))
        {
          ctx.put("isReWrite", new Boolean(true));
          reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CHECKED);

          setActionFlag(ctx, srcObj, null);
        }
        else if ((this.ZERO.intValue() - backAmount.doubleValue() == 0.0D) && (BizCollUtil.isLoanBill(srcObj.getSourceBillId())))
        {
          ctx.put("isReWrite", new Boolean(true));
          reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CHECKED);

          setBillfiVouchered(srcObj, ctx, false);
        } else {
          if (!isToVoucher) {
            ctx.put("isReWrite", new Boolean(true));
            reWriteBillState(ctx, srcBillPK.toString(), StateEnum.CHECKED);
          }
          else {
            reWriteBillState(ctx, srcBillPK.toString(), StateEnum.ALREADYPAYMENT);
          }

          setBillfiVouchered(srcObj, ctx, false);
        }

        ctx.put("isReWrite", null);
        logger.info(ResourceBase.getString(bizCollResource, "AccountBillBeToChecked", local));
      }

    }
    else if (new com.kingdee.eas.fi.ar.OtherBillInfo().getBOSType().equals(type))
    {
      int i;
      if ((BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) || (BOTBillOperStateEnum.EDIT.equals(bOTBillOperStateEnum)))
      {
        if ((srcObj instanceof BizAccountBillInfo)) {
          for (i = 0; i < ((BizAccountBillInfo)srcObj).getEntries().size(); )
          {
            BizAccountBillEntryInfo bizAccountEntryBillInfo = ((BizAccountBillInfo)srcObj).getEntries().get(i);

            bizAccountEntryBillInfo.setReceiveState(EntryStateEnum.ISRECEIVEBILL);

            SelectorItemCollection sic = new SelectorItemCollection();
            sic.add(new SelectorItemInfo("receiveState"));
            BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(bizAccountEntryBillInfo, sic);

            i++;
          }

        }

      }
      else if ((BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) && 
        (isSourceBillClose(ctx, srcObj))) {
        throw new BizCollException(BizCollException.ISSOURCEBILLCLOSE);
      }

    }
    else if (new com.kingdee.eas.fi.ap.OtherBillInfo().getBOSType().equals(type))
    {
      int i;
      if ((BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) || (BOTBillOperStateEnum.EDIT.equals(bOTBillOperStateEnum)))
      {
        for (i = 0; i < ((BizAccountBillInfo)srcObj).getEntries().size(); )
        {
          BizAccountBillEntryInfo bizAccountEntryBillInfo = ((BizAccountBillInfo)srcObj).getEntries().get(i);

          bizAccountEntryBillInfo.setPayState(EntryStateEnum.ISDEALBILL);

          SelectorItemCollection sic = new SelectorItemCollection();
          sic.add(new SelectorItemInfo("payState"));
          BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(bizAccountEntryBillInfo, sic);

          i++;
        }

      }
      else if ((BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) && 
        (isSourceBillClose(ctx, srcObj)))
        throw new BizCollException(BizCollException.ISSOURCEBILLCLOSE);
    }
  }

  private boolean isSourceBillClose(Context ctx, BizCollBillBaseInfo info)
    throws BOSException
  {
    boolean isSourceBillClose = false;
    String sourceBillId = info.getSourceBillId();
    if (sourceBillId != null) {
      ObjectUuidPK objpk = new ObjectUuidPK(sourceBillId);
      IDynamicObject iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);

      SelectorItemCollection sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("state"));
      BizCollBillBaseInfo sourceInfo = (BizCollBillBaseInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk, sic);

      if (StateEnum.CLOSED.equals(sourceInfo.getState())) {
        isSourceBillClose = true;
      }
    }
    return isSourceBillClose;
  }

  private void setLoanState(Context ctx, String srcBillId)
    throws BOSException, EASBizException
  {
    if (BizCollUtil.isLoanBill(srcBillId)) {
      ObjectUuidPK objpk = new ObjectUuidPK(srcBillId);
      IObjectValue billInfo = DynamicObjectFactory.getLocalInstance(ctx).getValue(objpk.getObjectType(), objpk);

      if ((AmountControlTypeEnum.oneTOone.equals(((LoanBillInfo)billInfo).getAmountControlType())) || ((((LoanBillInfo)billInfo).getAmountUsed() != null) && (((LoanBillInfo)billInfo).getAmountBalance().doubleValue() == 0.0D)))
      {
        LoanBillInfo loanInfo = null;
        if ((billInfo instanceof DailyLoanBillInfo)) {
          loanInfo = (DailyLoanBillInfo)billInfo;
          loanInfo.setLoanState("N");
          loanInfo.setReturnAmount(new BigDecimal("0.00"));

          loanInfo.setAmountUsed(new BigDecimal("0.00"));
          loanInfo.setAmountBalance(loanInfo.getAmountApproved());
          SelectorItemCollection sic = new SelectorItemCollection();
          sic.add(new SelectorItemInfo("loanState"));

          sic.add(new SelectorItemInfo("returnAmount"));
          sic.add(new SelectorItemInfo("amountUsed"));
          sic.add(new SelectorItemInfo("amountBalance"));
          DailyLoanBillFactory.getLocalInstance(ctx).updatePartial(loanInfo, sic);
        }
        else if ((billInfo instanceof EvectionLoanBillInfo)) {
          loanInfo = (EvectionLoanBillInfo)billInfo;
          loanInfo.setLoanState("N");
          loanInfo.setReturnAmount(new BigDecimal("0.00"));

          loanInfo.setAmountUsed(new BigDecimal("0.00"));
          loanInfo.setAmountBalance(loanInfo.getAmountApproved());
          SelectorItemCollection sic = new SelectorItemCollection();
          sic.add(new SelectorItemInfo("loanState"));

          sic.add(new SelectorItemInfo("returnAmount"));
          sic.add(new SelectorItemInfo("amountUsed"));
          sic.add(new SelectorItemInfo("amountBalance"));
          EvectionLoanBillFactory.getLocalInstance(ctx).updatePartial(loanInfo, sic);
        }
      }
    }
  }

  private void setActionFlag(Context ctx, ExpenseAccountBillInfo srcObj, String actionFlag)
    throws BOSException, EASBizException
  {
    if ((srcObj instanceof BizAccountBillInfo)) {
      BizAccountBillInfo bizAccountBillInfo = (BizAccountBillInfo)srcObj;
      bizAccountBillInfo.setActionFlag(actionFlag);
      SelectorItemCollection sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("actionFlag"));
      BizAccountBillFactory.getLocalInstance(ctx).updatePartial(bizAccountBillInfo, sic);
    }
    else if ((srcObj instanceof TravelAccountBillInfo)) {
      TravelAccountBillInfo travelAccountBillInfo = (TravelAccountBillInfo)srcObj;
      travelAccountBillInfo.setActionFlag(actionFlag);
      SelectorItemCollection sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("actionFlag"));
      TravelAccountBillFactory.getLocalInstance(ctx).updatePartial(travelAccountBillInfo, sic);
    }
    else if ((srcObj instanceof DailyPurchaseAccountBillInfo)) {
      DailyPurchaseAccountBillInfo dailyPurchaseAccountBillInfo = (DailyPurchaseAccountBillInfo)srcObj;
      dailyPurchaseAccountBillInfo.setActionFlag(actionFlag);
      SelectorItemCollection sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("actionFlag"));
      DailyPurchaseAccountBillFactory.getLocalInstance(ctx).updatePartial(dailyPurchaseAccountBillInfo, sic);
    }
  }

  private boolean isSourceBillRelatedBill(Context ctx, BizCollBillBaseInfo info)
  {
    boolean isSourceBillRelatedBill = false;
    try {
      if (BizCollUtil.checkBillHasTracDown(ctx, info))
        isSourceBillRelatedBill = true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return isSourceBillRelatedBill;
  }

  private boolean isSourceBillRelatedBill(Context ctx, BizCollBillBaseInfo info, String destObjectID)
  {
    boolean isSourceBillRelatedBill = false;
    try {
      if (BizCollUtil.checkBillHasTracDown(ctx, info, destObjectID))
        isSourceBillRelatedBill = true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return isSourceBillRelatedBill;
  }

  private void setBillfiVouchered(ExpenseAccountBillInfo srcObj, Context ctx, boolean flag)
    throws BOSException, EASBizException
  {
    if ((srcObj instanceof BizAccountBillInfo)) {
      BizAccountBillInfo bizAccountBillInfo = (BizAccountBillInfo)srcObj;
      bizAccountBillInfo.setFiVouchered(flag);
      SelectorItemCollection sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("fiVouchered"));
      IBizAccountBill iBizAccountBill = null;
      iBizAccountBill = BizAccountBillFactory.getLocalInstance(ctx);
      iBizAccountBill.updatePartial(bizAccountBillInfo, sic);
    } else if ((srcObj instanceof TravelAccountBillInfo)) {
      TravelAccountBillInfo travelAccountBillInfo = (TravelAccountBillInfo)srcObj;
      travelAccountBillInfo.setFiVouchered(flag);
      SelectorItemCollection sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("fiVouchered"));
      ITravelAccountBill iTravelAccountBill = null;
      iTravelAccountBill = TravelAccountBillFactory.getLocalInstance(ctx);
      iTravelAccountBill.updatePartial(travelAccountBillInfo, sic);
    } else if ((srcObj instanceof DailyPurchaseAccountBillInfo)) {
      DailyPurchaseAccountBillInfo dailyPurchaseAccountBillInfo = (DailyPurchaseAccountBillInfo)srcObj;
      dailyPurchaseAccountBillInfo.setFiVouchered(flag);
      SelectorItemCollection sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("fiVouchered"));
      IDailyPurchaseAccountBill iDailyPurchaseAccountBill = null;
      iDailyPurchaseAccountBill = DailyPurchaseAccountBillFactory.getLocalInstance(ctx);

      iDailyPurchaseAccountBill.updatePartial(dailyPurchaseAccountBillInfo, sic);
    }
  }
  
  protected void _abandon(Context context, BOSUuid bosuuid)
  throws BOSException, EASBizException
{
	  IExpenseAccountFacade facade = ExpenseAccountFacadeFactory.getLocalInstance(context);
	  facade.setState(bosuuid, StateEnum.ALREADYABANDON);
}
}