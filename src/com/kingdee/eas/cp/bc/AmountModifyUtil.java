package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.util.app.DbUtil;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AmountModifyUtil
{
  public static void updateBudgetDoOnSubmit(Context ctx, BizCollBillBaseInfo billInfo)
    throws BOSException, EASBizException
  {
    updateBudgetDo(ctx, billInfo);
  }

  public static boolean updateAmountField(Context ctx, BizCollBillBaseInfo billInfo)
    throws BOSException, EASBizException
  {
    boolean isPass = true;

    boolean isBgForOperationType = false;

    String bizBudgetAction = null;

    Boolean auditResultBoolean = null;
    if (ctx != null) {
      bizBudgetAction = (String)ctx.get("BizBudgetAction");
      auditResultBoolean = (Boolean)ctx.get("BizAuditResultBoolean");
    }
    if (auditResultBoolean == null) {
      auditResultBoolean = new Boolean(false);
    }

    Object param = BizBillUtil.getParamByNumber(ctx, "CP015");
    if (param == null)
      isBgForOperationType = false;
    else {
      isBgForOperationType = new Boolean(param.toString()).booleanValue();
    }
    String sourceBillId = billInfo.getSourceBillId();

    if ("bgAudit".equals(bizBudgetAction)) {
      if (sourceBillId != null) {
        isPass = updateSourceAmount(ctx, billInfo, sourceBillId, isBgForOperationType);
      }
      if (isPass) {
        updateBudgetDoForBgAudit(ctx, billInfo);
      }

    }
    else if ("returnBudget".equals(bizBudgetAction)) {
      if (sourceBillId != null) {
        isPass = updateSourceAmount(ctx, billInfo, sourceBillId, isBgForOperationType);
      }
      if (isPass) {
        updateBudgetDoForReturnBudget(ctx, billInfo, auditResultBoolean.booleanValue());
      }
    }
    return isPass;
  }

  private static boolean updateSourceAmount(Context ctx, BizCollBillBaseInfo billInfo, String sourceBillId, boolean isBgForOperationType)
    throws BOSException, EASBizException
  {
    boolean isPass = true;
    boolean isUpdate = true;
    Map map = null;
    BigDecimal headAmountUsed = new BigDecimal("0.00");
    BigDecimal headAmountBalance = new BigDecimal("0.00");
    ObjectUuidPK objpk = new ObjectUuidPK(sourceBillId);
    IDynamicObject iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);

    BizCollBillBaseInfo sourceInfo = (BizCollBillBaseInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk, getSelector());

    if ((sourceInfo instanceof OtherExpenseBillInfo)) {
      OtherExpenseBillInfo info = (OtherExpenseBillInfo)sourceInfo;
      OtherExpenseEntryCoreBaseInfo entryInfo = null;
      BigDecimal amountBalance = new BigDecimal("0.00");

      for (int i = 0; i < info.getEntries().size(); i++) {
        entryInfo = info.getEntries().get(i);
        map = getUpdateAmountMap(ctx, billInfo, entryInfo, isBgForOperationType);

        if (sourceInfo.getAmountControlType().equals(AmountControlTypeEnum.oneTOone))
        {
          if (map.size() > 0) {
            amountBalance = (BigDecimal)map.get("amountBalance");

            if (amountBalance.doubleValue() < 0.0D) {
              isPass = false;
              isUpdate = false;
            }
          }
        }
        if ((map.size() > 0) && 
          (isUpdate)) {
          entryInfo.setAmountUsed((BigDecimal)map.get("amountUsed"));

          entryInfo.setAmountBalance((BigDecimal)map.get("amountBalance"));

          Object[] param = new Object[3];
          param[0] = ((BigDecimal)map.get("amountUsed"));
          param[1] = ((BigDecimal)map.get("amountBalance"));
          param[2] = entryInfo.getId().toString();
          String sql = "update t_bc_OtherExpenseBillEntry set famountused=?,famountbalance=? where fid=?";
          DbUtil.execute(ctx, sql, param);
        }

        headAmountUsed = headAmountUsed.add(entryInfo.getAmountUsed());
        headAmountBalance = headAmountBalance.add(entryInfo.getAmountBalance());
      }

      Object[] param = new Object[4];
      param[0] = headAmountUsed;
      param[1] = headAmountBalance;

      int flag = headAmountBalance.compareTo(new BigDecimal(0.0D)) <= 0 ? 80 : 60;

      param[2] = new Integer(flag);
      param[3] = info.getId().toString();
      String sql = "update t_bc_OtherExpenseBill set famountused=?,famountbalance=?, fstate=? where fid=?";
      DbUtil.execute(ctx, sql, param);
    } else if ((sourceInfo instanceof DailyLoanBillInfo)) {
      DailyLoanBillInfo info = (DailyLoanBillInfo)sourceInfo;
      OtherExpenseEntryCoreBaseInfo entryInfo = null;
      BigDecimal amountBalance = new BigDecimal("0.00");

      for (int i = 0; i < info.getEntries().size(); i++) {
        entryInfo = info.getEntries().get(i);
        map = getUpdateAmountMap(ctx, billInfo, entryInfo, isBgForOperationType);

        if (sourceInfo.getAmountControlType().equals(AmountControlTypeEnum.oneTOone))
        {
          if (map.size() > 0) {
            amountBalance = (BigDecimal)map.get("amountBalance");

            if (amountBalance.doubleValue() < 0.0D) {
              isPass = false;
              isUpdate = false;
            }
          }
        }
        if ((map.size() > 0) && 
          (isUpdate)) {
          entryInfo.setAmountUsed((BigDecimal)map.get("amountUsed"));

          entryInfo.setAmountBalance((BigDecimal)map.get("amountBalance"));

          Object[] param = new Object[3];
          param[0] = ((BigDecimal)map.get("amountUsed"));
          param[1] = ((BigDecimal)map.get("amountBalance"));
          param[2] = entryInfo.getId().toString();
          String sql = "update t_bc_dailyloanbillentry set famountused=?,famountbalance=? where fid=?";
          DbUtil.execute(ctx, sql, param);
        }

        headAmountUsed = headAmountUsed.add(entryInfo.getAmountUsed());
        headAmountBalance = headAmountBalance.add(entryInfo.getAmountBalance());
      }

      Object[] param = new Object[3];
      param[0] = headAmountUsed;
      param[1] = headAmountBalance;
      param[2] = info.getId().toString();
      String sql = "update t_bc_dailyloanbill set famountused=?,famountbalance=? where fid=?";
      DbUtil.execute(ctx, sql, param);
    }
    else if ((sourceInfo instanceof EvectionLoanBillInfo)) {
      EvectionLoanBillInfo info = (EvectionLoanBillInfo)sourceInfo;
      EvectionExpBillEntryBaseInfo entryInfo = null;
      BigDecimal amountBalance = new BigDecimal("0.00");
      for (int i = 0; i < info.getEntries().size(); i++) {
        entryInfo = info.getEntries().get(i);
        map = getUpdateAmountMap(ctx, billInfo, entryInfo, isBgForOperationType);

        if (sourceInfo.getAmountControlType().equals(AmountControlTypeEnum.oneTOone))
        {
          if (map.size() > 0) {
            amountBalance = (BigDecimal)map.get("amountBalance");
            if (amountBalance.doubleValue() < 0.0D) {
              isPass = false;
              isUpdate = false;
            }
          }
        }
        if ((map.size() > 0) && 
          (isUpdate)) {
          entryInfo.setAmountUsed((BigDecimal)map.get("amountUsed"));

          entryInfo.setAmountBalance((BigDecimal)map.get("amountBalance"));

          Object[] param = new Object[3];
          param[0] = ((BigDecimal)map.get("amountUsed"));
          param[1] = ((BigDecimal)map.get("amountBalance"));
          param[2] = entryInfo.getId().toString();
          String sql = "update t_bc_evectionloanbillentry set famountused=?,famountbalance=? where fid=?";
          DbUtil.execute(ctx, sql, param);
        }

        headAmountUsed = headAmountUsed.add(entryInfo.getAmountUsed());
        headAmountBalance = headAmountBalance.add(entryInfo.getAmountBalance());
      }

      Object[] param = new Object[3];
      param[0] = headAmountUsed;
      param[1] = headAmountBalance;
      param[2] = info.getId().toString();
      String sql = "update t_bc_evectionloanbill set famountused=?,famountbalance=? where fid=?";
      DbUtil.execute(ctx, sql, param);
    }

    return isPass;
  }

  public static Map getUpdateAmountMap(Context ctx, BizCollBillBaseInfo info, CoreBillEntryBaseInfo sourceEntryInfo, boolean isBgForOperationType)
    throws BOSException, EASBizException
  {
    Map map = new HashMap();
    String bizBudgetAction = null;
    Boolean auditResultBoolean = null;
    if (ctx != null) {
      bizBudgetAction = (String)ctx.get("BizBudgetAction");
      auditResultBoolean = (Boolean)ctx.get("BizAuditResultBoolean");
    }
    if (auditResultBoolean == null) {
      auditResultBoolean = new Boolean(false);
    }
    if ((sourceEntryInfo instanceof OtherExpenseEntryCoreBaseInfo)) {
      OtherExpenseEntryCoreBaseInfo otherEntryInfo = (OtherExpenseEntryCoreBaseInfo)sourceEntryInfo;
      if ((info instanceof DailyLoanBillInfo)) {
        DailyLoanBillInfo dailyInfo = (DailyLoanBillInfo)info;
        DailyLoanBillEntryInfo entryInfo = null;
        DailyLoanBillEntryCollection billEntry = dailyInfo.getEntries();
        int length = billEntry.size();
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          if (!otherEntryInfo.getExpenseType().getId().equals(entryInfo.getExpenseType().getId())) {
            continue;
          }
          if ((entryInfo.getSourceAmountBalance() != null) && (entryInfo.getSourceAmountBalance().compareTo(new BigDecimal("0.00")) < 0))
          {
            return map;
          }
          if (("bgAudit".equals(bizBudgetAction)) || (bizBudgetAction == null))
          {
            BigDecimal entryCount = new BigDecimal("0.00");
            BigDecimal otherEntryCount = new BigDecimal("0.00");
            entryCount = entryInfo.getAmount();
            otherEntryCount = otherEntryInfo.getAmountUsed();
            otherEntryCount = otherEntryCount.add(entryCount);
            map.put("amountUsed", otherEntryCount);
            otherEntryCount = otherEntryInfo.getAmountBalance();
            otherEntryCount = otherEntryCount.subtract(entryCount);

            map.put("amountBalance", otherEntryCount);
          } else if ("returnBudget".equals(bizBudgetAction)) {
            BigDecimal entryCount = new BigDecimal("0.00");
            BigDecimal otherEntryCount = new BigDecimal("0.00");
            entryCount = entryInfo.getAmount();

            if (auditResultBoolean.booleanValue() == true) {
              BigDecimal entryApprovedCount = new BigDecimal("0.00");

              entryApprovedCount = entryInfo.getAmountApproved();

              if (entryCount.subtract(entryApprovedCount).doubleValue() > 0.0D)
              {
                otherEntryCount = otherEntryInfo.getAmountUsed();

                otherEntryCount = otherEntryCount.subtract(entryCount.subtract(entryApprovedCount));

                map.put("amountUsed", otherEntryCount);
                otherEntryCount = otherEntryInfo.getAmountBalance();

                otherEntryCount = otherEntryCount.add(entryCount.subtract(entryApprovedCount));

                map.put("amountBalance", otherEntryCount);
              }
            } else {
              if (StateEnum.CHECKED.equals(info.getState())) {
                entryCount = entryInfo.getAmountApproved();
              }
              otherEntryCount = otherEntryInfo.getAmountUsed();

              otherEntryCount = otherEntryCount.subtract(entryCount);

              map.put("amountUsed", otherEntryCount);
              otherEntryCount = otherEntryInfo.getAmountBalance();

              otherEntryCount = otherEntryCount.add(entryCount);

              map.put("amountBalance", otherEntryCount);
            }
          }
        }
      }
      else if ((info instanceof BizAccountBillInfo)) {
        BizAccountBillInfo bizInfo = (BizAccountBillInfo)info;
        BizAccountBillEntryInfo entryInfo = null;
        BizAccountBillEntryCollection billEntry = bizInfo.getEntries();
        int length = billEntry.size();
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          if (!otherEntryInfo.getExpenseType().getId().equals(entryInfo.getExpenseType().getId()))
            continue;
          if ((entryInfo.getSourceAmountBalance() != null) && (entryInfo.getSourceAmountBalance().compareTo(new BigDecimal("0.00")) < 0))
          {
            return map;
          }
          if (("bgAudit".equals(bizBudgetAction)) || (bizBudgetAction == null))
          {
            BigDecimal entryCount = new BigDecimal("0.00");
            BigDecimal otherEntryCount = new BigDecimal("0.00");

            entryCount = getAllAmount(ctx, bizInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);

            otherEntryCount = otherEntryInfo.getAmountUsed();
            otherEntryCount = otherEntryCount.add(entryCount);
            map.put("amountUsed", otherEntryCount);
            otherEntryCount = otherEntryInfo.getAmountBalance();
            otherEntryCount = otherEntryCount.subtract(entryCount);

            map.put("amountBalance", otherEntryCount);
          } else if ("returnBudget".equals(bizBudgetAction)) {
            BigDecimal entryCount = new BigDecimal("0.00");
            BigDecimal otherEntryCount = new BigDecimal("0.00");

            entryCount = getAllAmount(ctx, bizInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);

            if (auditResultBoolean.booleanValue() == true) {
              BigDecimal entryApprovedCount = new BigDecimal("0.00");

              entryApprovedCount = getAllAmountApproved(ctx, bizInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);

              if (entryCount.subtract(entryApprovedCount).doubleValue() > 0.0D)
              {
                otherEntryCount = otherEntryInfo.getAmountUsed();

                otherEntryCount = otherEntryCount.subtract(entryCount.subtract(entryApprovedCount));

                map.put("amountUsed", otherEntryCount);
                otherEntryCount = otherEntryInfo.getAmountBalance();

                otherEntryCount = otherEntryCount.add(entryCount.subtract(entryApprovedCount));

                map.put("amountBalance", otherEntryCount);
              }
            } else {
              if (StateEnum.CHECKED.equals(info.getState())) {
                entryCount = getAllAmountApproved(ctx, bizInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);
              }

              otherEntryCount = otherEntryInfo.getAmountUsed();

              otherEntryCount = otherEntryCount.subtract(entryCount);

              map.put("amountUsed", otherEntryCount);
              otherEntryCount = otherEntryInfo.getAmountBalance();

              otherEntryCount = otherEntryCount.add(entryCount);

              map.put("amountBalance", otherEntryCount);
            }
          }
        }
      }
      else if ((info instanceof DailyPurchaseAccountBillInfo)) {
        DailyPurchaseAccountBillInfo bizInfo = (DailyPurchaseAccountBillInfo)info;
        DailyPurchaseAccountBillEntryInfo entryInfo = null;
        DailyPurchaseAccountBillEntryCollection billEntry = bizInfo.getEntries();
        int length = billEntry.size();
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          if (!otherEntryInfo.getExpenseType().getId().equals(entryInfo.getExpenseType().getId()))
            continue;
          if ((entryInfo.getSourceAmountBalance() != null) && (entryInfo.getSourceAmountBalance().compareTo(new BigDecimal("0.00")) < 0))
          {
            return map;
          }
          if (("bgAudit".equals(bizBudgetAction)) || (bizBudgetAction == null))
          {
            BigDecimal entryCount = new BigDecimal("0.00");
            BigDecimal otherEntryCount = new BigDecimal("0.00");

            entryCount = getAllAmount(ctx, bizInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);

            otherEntryCount = otherEntryInfo.getAmountUsed();
            otherEntryCount = otherEntryCount.add(entryCount);
            map.put("amountUsed", otherEntryCount);
            otherEntryCount = otherEntryInfo.getAmountBalance();
            otherEntryCount = otherEntryCount.subtract(entryCount);

            map.put("amountBalance", otherEntryCount);
          } else if ("returnBudget".equals(bizBudgetAction)) {
            BigDecimal entryCount = new BigDecimal("0.00");
            BigDecimal otherEntryCount = new BigDecimal("0.00");

            entryCount = getAllAmount(ctx, bizInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);

            if (auditResultBoolean.booleanValue() == true) {
              BigDecimal entryApprovedCount = new BigDecimal("0.00");

              entryApprovedCount = getAllAmountApproved(ctx, bizInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);

              if (entryCount.subtract(entryApprovedCount).doubleValue() > 0.0D)
              {
                otherEntryCount = otherEntryInfo.getAmountUsed();

                otherEntryCount = otherEntryCount.subtract(entryCount.subtract(entryApprovedCount));

                map.put("amountUsed", otherEntryCount);
                otherEntryCount = otherEntryInfo.getAmountBalance();

                otherEntryCount = otherEntryCount.add(entryCount.subtract(entryApprovedCount));

                map.put("amountBalance", otherEntryCount);
              }
            } else {
              if (StateEnum.CHECKED.equals(info.getState())) {
                entryCount = getAllAmountApproved(ctx, bizInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);
              }

              otherEntryCount = otherEntryInfo.getAmountUsed();

              otherEntryCount = otherEntryCount.subtract(entryCount);

              map.put("amountUsed", otherEntryCount);
              otherEntryCount = otherEntryInfo.getAmountBalance();

              otherEntryCount = otherEntryCount.add(entryCount);

              map.put("amountBalance", otherEntryCount);
            }
          }
        }
      }
    }
    else if ((sourceEntryInfo instanceof EvectionExpBillEntryBaseInfo)) {
      EvectionExpBillEntryBaseInfo evectionEntryInfo = (EvectionExpBillEntryBaseInfo)sourceEntryInfo;
      if ((info instanceof EvectionLoanBillInfo)) {
        EvectionLoanBillInfo evectionInfo = (EvectionLoanBillInfo)info;
        EvectionLoanBillEntryInfo entryInfo = null;
        EvectionLoanBillEntryCollection billEntry = evectionInfo.getEntries();
        int length = billEntry.size();
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          if (!evectionEntryInfo.getExpenseType().getId().equals(entryInfo.getExpenseType().getId()))
            continue;
          if ((entryInfo.getSourceAmountBalance() != null) && (entryInfo.getSourceAmountBalance().compareTo(new BigDecimal("0.00")) < 0))
          {
            return map;
          }
          if (("bgAudit".equals(bizBudgetAction)) || (bizBudgetAction == null))
          {
            BigDecimal entryCount = new BigDecimal("0.00");
            BigDecimal otherEntryCount = new BigDecimal("0.00");
            entryCount = entryInfo.getAmount();
            otherEntryCount = evectionEntryInfo.getAmountUsed();
            otherEntryCount = otherEntryCount.add(entryCount);
            map.put("amountUsed", otherEntryCount);
            otherEntryCount = evectionEntryInfo.getAmountBalance();

            otherEntryCount = otherEntryCount.subtract(entryCount);

            map.put("amountBalance", otherEntryCount);
          } else if ("returnBudget".equals(bizBudgetAction)) {
            BigDecimal entryCount = new BigDecimal("0.00");
            BigDecimal otherEntryCount = new BigDecimal("0.00");
            entryCount = entryInfo.getAmount();

            if (auditResultBoolean.booleanValue() == true) {
              BigDecimal entryApprovedCount = new BigDecimal("0.00");

              entryApprovedCount = entryInfo.getAmountApproved();

              if (entryCount.subtract(entryApprovedCount).doubleValue() > 0.0D)
              {
                otherEntryCount = evectionEntryInfo.getAmountUsed();

                otherEntryCount = otherEntryCount.subtract(entryCount.subtract(entryApprovedCount));

                map.put("amountUsed", otherEntryCount);
                otherEntryCount = evectionEntryInfo.getAmountBalance();

                otherEntryCount = otherEntryCount.add(entryCount.subtract(entryApprovedCount));

                map.put("amountBalance", otherEntryCount);
              }
            } else {
              if (StateEnum.CHECKED.equals(info.getState())) {
                entryCount = entryInfo.getAmountApproved();
              }
              otherEntryCount = evectionEntryInfo.getAmountUsed();

              otherEntryCount = otherEntryCount.subtract(entryCount);

              map.put("amountUsed", otherEntryCount);
              otherEntryCount = evectionEntryInfo.getAmountBalance();

              otherEntryCount = otherEntryCount.add(entryCount);

              map.put("amountBalance", otherEntryCount);
            }
          }
        }
      }
      else if ((info instanceof TravelAccountBillInfo)) {
        TravelAccountBillInfo travelInfo = (TravelAccountBillInfo)info;
        TravelAccountBillEntryInfo entryInfo = null;
        TravelAccountBillEntryCollection billEntry = travelInfo.getEntries();
        int length = billEntry.size();
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          if (!evectionEntryInfo.getExpenseType().getId().equals(entryInfo.getExpenseType().getId()))
            continue;
          if ((entryInfo.getSourceAmountBalance() != null) && (entryInfo.getSourceAmountBalance().compareTo(new BigDecimal("0.00")) < 0))
          {
            return map;
          }
          if (("bgAudit".equals(bizBudgetAction)) || (bizBudgetAction == null))
          {
            BigDecimal entryCount = new BigDecimal("0.00");
            BigDecimal otherEntryCount = new BigDecimal("0.00");

            entryCount = getAllAmount(ctx, travelInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);

            otherEntryCount = evectionEntryInfo.getAmountUsed();
            otherEntryCount = otherEntryCount.add(entryCount);
            map.put("amountUsed", otherEntryCount);
            otherEntryCount = evectionEntryInfo.getAmountBalance();

            otherEntryCount = otherEntryCount.subtract(entryCount);

            map.put("amountBalance", otherEntryCount);
          } else if ("returnBudget".equals(bizBudgetAction)) {
            BigDecimal entryCount = new BigDecimal("0.00");
            BigDecimal otherEntryCount = new BigDecimal("0.00");

            entryCount = getAllAmount(ctx, travelInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);

            if (auditResultBoolean.booleanValue() == true) {
              BigDecimal entryApprovedCount = new BigDecimal("0.00");

              entryApprovedCount = getAllAmountApproved(ctx, travelInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);

              if (entryCount.subtract(entryApprovedCount).doubleValue() > 0.0D)
              {
                otherEntryCount = evectionEntryInfo.getAmountUsed();

                otherEntryCount = otherEntryCount.subtract(entryCount.subtract(entryApprovedCount));

                map.put("amountUsed", otherEntryCount);
                otherEntryCount = evectionEntryInfo.getAmountBalance();

                otherEntryCount = otherEntryCount.add(entryCount.subtract(entryApprovedCount));

                map.put("amountBalance", otherEntryCount);
              }
            } else {
              if (StateEnum.CHECKED.equals(info.getState())) {
                entryCount = getAllAmountApproved(ctx, travelInfo, entryInfo.getExpenseType().getId().toString(), entryInfo.getCostCenter().getId().toString(), isBgForOperationType);
              }

              otherEntryCount = evectionEntryInfo.getAmountUsed();

              otherEntryCount = otherEntryCount.subtract(entryCount);

              map.put("amountUsed", otherEntryCount);
              otherEntryCount = evectionEntryInfo.getAmountBalance();

              otherEntryCount = otherEntryCount.add(entryCount);

              map.put("amountBalance", otherEntryCount);
            }
          }
        }
      }
    }

    return map;
  }

  private static void updateBudgetDo(Context ctx, BizCollBillBaseInfo billInfo)
    throws EASBizException, BOSException
  {
    String bizBudgetAction = null;

    Boolean auditResultBoolean = null;

    if (ctx != null) {
      bizBudgetAction = (String)ctx.get("BizBudgetAction");
      auditResultBoolean = (Boolean)ctx.get("BizAuditResultBoolean");
    }
    if (auditResultBoolean == null) {
      auditResultBoolean = new Boolean(false);
    }
    if ("bgAudit".equals(bizBudgetAction)) {
      updateBudgetDoForBgAudit(ctx, billInfo);
    }
    else if ("returnBudget".equals(bizBudgetAction))
      updateBudgetDoForReturnBudget(ctx, billInfo, auditResultBoolean.booleanValue());
  }

  public static void updateBudgetDoForBgAudit(Context ctx, BizCollBillBaseInfo billInfo)
    throws EASBizException, BOSException
  {
    String sourceBillId = billInfo.getSourceBillId();

    boolean isBgForOperationType = false;

    Object param = BizBillUtil.getParamByNumber(ctx, "CP015");

    if (param == null)
      isBgForOperationType = false;
    else {
      isBgForOperationType = new Boolean(param.toString()).booleanValue();
    }

    if ((billInfo instanceof OtherExpenseBillInfo)) {
      OtherExpenseBillInfo info = (OtherExpenseBillInfo)billInfo;
      OtherExpenseBillEntryInfo entryInfo = null;
      OtherExpenseBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        entryInfo.setBudgetDo(entryInfo.getAmount());
        entryInfo.setBudgetUsed(entryInfo.getAmount());
        OtherExpenseBillEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(entryInfo.getId()), entryInfo);
      }
    }
    else if ((billInfo instanceof DailyLoanBillInfo)) {
      DailyLoanBillInfo info = (DailyLoanBillInfo)billInfo;
      DailyLoanBillEntryInfo entryInfo = null;
      DailyLoanBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (sourceBillId == null) {
          entryInfo.setBudgetDo(entryInfo.getAmount());
          entryInfo.setBudgetUsed(entryInfo.getAmount());
        }
        else if ((entryInfo.getSourceAmountBalance() != null) && (entryInfo.getSourceAmountBalance().compareTo(new BigDecimal("0.00")) < 0))
        {
          entryInfo.setBudgetDo(new BigDecimal("0.00"));
          entryInfo.setBudgetUsed(new BigDecimal("0.00"));
        } else if (entryInfo.getAmount().compareTo(entryInfo.getSourceAmountBalance()) >= 0)
        {
          entryInfo.setBudgetDo(entryInfo.getAmount().subtract(entryInfo.getSourceAmountBalance()));

          entryInfo.setBudgetUsed(entryInfo.getAmount().subtract(entryInfo.getSourceAmountBalance()));
        }

        DailyLoanBillEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(entryInfo.getId()), entryInfo);
      }

    }
    else if ((billInfo instanceof EvectionLoanBillInfo)) {
      EvectionLoanBillInfo info = (EvectionLoanBillInfo)billInfo;
      EvectionLoanBillEntryInfo entryInfo = null;
      EvectionLoanBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (sourceBillId == null) {
          entryInfo.setBudgetDo(entryInfo.getAmount());
          entryInfo.setBudgetUsed(entryInfo.getAmount());
        }
        else if ((entryInfo.getSourceAmountBalance() != null) && (entryInfo.getSourceAmountBalance().compareTo(new BigDecimal("0.00")) < 0))
        {
          entryInfo.setBudgetDo(new BigDecimal("0.00"));
          entryInfo.setBudgetUsed(new BigDecimal("0.00"));
        } else if ((entryInfo.getSourceAmountBalance() != null) && (entryInfo.getAmount().compareTo(entryInfo.getSourceAmountBalance()) >= 0))
        {
          entryInfo.setBudgetDo(entryInfo.getAmount().subtract(entryInfo.getSourceAmountBalance()));

          entryInfo.setBudgetUsed(entryInfo.getAmount().subtract(entryInfo.getSourceAmountBalance()));
        }

        EvectionLoanBillEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(entryInfo.getId()), entryInfo);
      }

    }
    else if ((billInfo instanceof BizAccountBillInfo)) {
      SelectorItemCollection selectorItemColl = new SelectorItemCollection();
      selectorItemColl.add(new SelectorItemInfo("budgetDo"));
      selectorItemColl.add(new SelectorItemInfo("budgetUsed"));
      HashMap sourceTypeMap = new HashMap();
      BizAccountBillInfo info = (BizAccountBillInfo)billInfo;
      BizAccountBillEntryInfo entryInfo = null;
      BizAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      if (sourceBillId == null) {
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          entryInfo.setBudgetDo(entryInfo.getAmount());
          entryInfo.setBudgetUsed(entryInfo.getAmount());
          BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
        }
      }
      else {
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          String sourceTypeID = null;
          if (entryInfo.getBudgetAmount() != null) {
            if (isBgForOperationType) {
              sourceTypeID = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }
            else
            {
              sourceTypeID = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }

            sourceTypeMap.put(sourceTypeID, entryInfo.getId().toString());
          }
        }
        Iterator typeIterator = sourceTypeMap.keySet().iterator();

        while (typeIterator.hasNext()) {
          String sourceTypeID = (String)typeIterator.next();
          String expenseTypeID = null;
          BigDecimal rootCenterAmount = new BigDecimal("0.00");
          BigDecimal notRootCenterAmount = new BigDecimal("0.00");
          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            String typeid = null;
            if (isBgForOperationType) {
              typeid = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getOperationType().getId().toString();
            }
            else {
              typeid = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getExpenseType().getId().toString();
            }

            if (sourceTypeID.equals(typeid)) {
              rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());
            } else {
              if (sourceTypeID.indexOf(expenseTypeID) == -1)
                continue;
              notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());
              entryInfo.setBudgetDo(entryInfo.getAmount());
              entryInfo.setBudgetUsed(entryInfo.getAmount());
              BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
            }
          }

          String entryId = (String)sourceTypeMap.get(sourceTypeID);
          BizAccountBillEntryInfo billEntryInfo = BizAccountBillEntryFactory.getLocalInstance(ctx).getBizAccountBillEntryInfo(new ObjectUuidPK(entryId));

          BigDecimal sourceAmountBalance = billEntryInfo.getSourceAmountBalance();

          BigDecimal budgetAmount = new BigDecimal("0.00");
          if (sourceAmountBalance.compareTo(new BigDecimal("0.00")) < 0) {
            billEntryInfo.setBudgetDo(new BigDecimal("0.00"));
            billEntryInfo.setBudgetUsed(new BigDecimal("0.00"));
            BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(billEntryInfo, selectorItemColl);
          }
          else
          {
            if (sourceAmountBalance.compareTo(rootCenterAmount) < 0) {
              budgetAmount = rootCenterAmount.subtract(sourceAmountBalance);
            }
            else {
              BigDecimal templeBalance = sourceAmountBalance.subtract(rootCenterAmount);

              if (notRootCenterAmount.compareTo(templeBalance) > 0) {
                budgetAmount = rootCenterAmount.subtract(sourceAmountBalance);
              }
              else {
                budgetAmount = notRootCenterAmount.multiply(new BigDecimal("-1"));
              }

            }

            billEntryInfo.setBudgetDo(budgetAmount);
            billEntryInfo.setBudgetUsed(budgetAmount);
            BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(billEntryInfo, selectorItemColl);
          }

        }

      }

    }
    else if ((billInfo instanceof TravelAccountBillInfo)) {
      SelectorItemCollection selectorItemColl = new SelectorItemCollection();
      selectorItemColl.add(new SelectorItemInfo("budgetDo"));
      selectorItemColl.add(new SelectorItemInfo("budgetUsed"));
      HashMap sourceTypeMap = new HashMap();
      TravelAccountBillInfo info = (TravelAccountBillInfo)billInfo;
      TravelAccountBillEntryInfo entryInfo = null;
      TravelAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      if (sourceBillId == null) {
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          entryInfo.setBudgetDo(entryInfo.getAmount());
          entryInfo.setBudgetUsed(entryInfo.getAmount());
          TravelAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
        }
      }
      else {
        for (int i = 0; i < length; i++)
        {
          entryInfo = billEntry.get(i);
          String sourceTypeID = null;
          if (entryInfo.getBudgetAmount() != null) {
            if (isBgForOperationType) {
              sourceTypeID = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }
            else
            {
              sourceTypeID = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }

            sourceTypeMap.put(sourceTypeID, entryInfo.getId().toString());
          }
        }
        Iterator typeIterator = sourceTypeMap.keySet().iterator();

        while (typeIterator.hasNext()) {
          String sourceTypeID = (String)typeIterator.next();
          String expenseTypeID = null;
          BigDecimal rootCenterAmount = new BigDecimal("0.00");
          BigDecimal notRootCenterAmount = new BigDecimal("0.00");
          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            String typeid = null;
            if (isBgForOperationType) {
              typeid = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getOperationType().getId().toString();
            }
            else {
              typeid = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getExpenseType().getId().toString();
            }

            if (sourceTypeID.equals(typeid)) {
              rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());
            }
            else {
              if (sourceTypeID.indexOf(expenseTypeID) == -1)
                continue;
              notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());

              entryInfo.setBudgetDo(entryInfo.getAmount());
              entryInfo.setBudgetUsed(entryInfo.getAmount());
              TravelAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
            }
          }

          String entryId = (String)sourceTypeMap.get(sourceTypeID);
          TravelAccountBillEntryInfo billEntryInfo = TravelAccountBillEntryFactory.getLocalInstance(ctx).getTravelAccountBillEntryInfo(new ObjectUuidPK(entryId));

          BigDecimal sourceAmountBalance = billEntryInfo.getSourceAmountBalance();

          BigDecimal budgetAmount = new BigDecimal("0.00");
          if (sourceAmountBalance.compareTo(new BigDecimal("0.00")) < 0) {
            billEntryInfo.setBudgetDo(new BigDecimal("0.00"));
            billEntryInfo.setBudgetUsed(new BigDecimal("0.00"));
            TravelAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(billEntryInfo, selectorItemColl);
          }
          else {
            if (sourceAmountBalance.compareTo(rootCenterAmount) < 0) {
              budgetAmount = rootCenterAmount.subtract(sourceAmountBalance);
            }
            else
            {
              BigDecimal templeBalance = sourceAmountBalance.subtract(rootCenterAmount);

              if (notRootCenterAmount.compareTo(templeBalance) > 0) {
                budgetAmount = rootCenterAmount.subtract(sourceAmountBalance);
              }
              else {
                budgetAmount = notRootCenterAmount.multiply(new BigDecimal("-1"));
              }

            }

            billEntryInfo.setBudgetDo(budgetAmount);
            billEntryInfo.setBudgetUsed(budgetAmount);
            TravelAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(billEntryInfo, selectorItemColl);
          }

        }

      }

    }
    else if ((billInfo instanceof DailyPurchaseAccountBillInfo)) {
      SelectorItemCollection selectorItemColl = new SelectorItemCollection();
      selectorItemColl.add(new SelectorItemInfo("budgetDo"));
      selectorItemColl.add(new SelectorItemInfo("budgetUsed"));
      HashMap sourceTypeMap = new HashMap();
      DailyPurchaseAccountBillInfo info = (DailyPurchaseAccountBillInfo)billInfo;
      DailyPurchaseAccountBillEntryInfo entryInfo = null;
      DailyPurchaseAccountBillEntryCollection billEntry = info.getEntries();

      int length = billEntry.size();
      if (sourceBillId == null) {
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          entryInfo.setBudgetDo(entryInfo.getAmount());
          entryInfo.setBudgetUsed(entryInfo.getAmount());
          DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
        }
      }
      else {
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          String sourceTypeID = null;
          if (entryInfo.getBudgetAmount() != null) {
            if (isBgForOperationType) {
              sourceTypeID = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }
            else
            {
              sourceTypeID = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }

            sourceTypeMap.put(sourceTypeID, entryInfo.getId().toString());
          }
        }

        Iterator typeIterator = sourceTypeMap.keySet().iterator();

        while (typeIterator.hasNext()) {
          String sourceTypeID = (String)typeIterator.next();
          String expenseTypeID = null;
          BigDecimal rootCenterAmount = new BigDecimal("0.00");
          BigDecimal notRootCenterAmount = new BigDecimal("0.00");
          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            String typeid = null;
            if (isBgForOperationType) {
              typeid = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getOperationType().getId().toString();
            }
            else {
              typeid = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getExpenseType().getId().toString();
            }

            if (sourceTypeID.equals(typeid)) {
              rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());
            }
            else {
              if (sourceTypeID.indexOf(expenseTypeID) == -1)
                continue;
              notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());

              entryInfo.setBudgetDo(entryInfo.getAmount());
              entryInfo.setBudgetUsed(entryInfo.getAmount());
              DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
            }

          }

          String entryId = (String)sourceTypeMap.get(sourceTypeID);
          DailyPurchaseAccountBillEntryInfo billEntryInfo = DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).getDailyPurchaseAccountBillEntryInfo(new ObjectUuidPK(entryId));

          BigDecimal sourceAmountBalance = billEntryInfo.getSourceAmountBalance();

          BigDecimal budgetAmount = new BigDecimal("0.00");
          if (sourceAmountBalance.compareTo(new BigDecimal("0.00")) < 0) {
            billEntryInfo.setBudgetDo(new BigDecimal("0.00"));
            billEntryInfo.setBudgetUsed(new BigDecimal("0.00"));
            DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(billEntryInfo, selectorItemColl);
          } else {
            if (sourceAmountBalance.compareTo(rootCenterAmount) < 0) {
              budgetAmount = rootCenterAmount.subtract(sourceAmountBalance);
            }
            else
            {
              BigDecimal templeBalance = sourceAmountBalance.subtract(rootCenterAmount);

              if (notRootCenterAmount.compareTo(templeBalance) > 0) {
                budgetAmount = rootCenterAmount.subtract(sourceAmountBalance);
              }
              else {
                budgetAmount = notRootCenterAmount.multiply(new BigDecimal("-1"));
              }

            }

            billEntryInfo.setBudgetDo(budgetAmount);
            billEntryInfo.setBudgetUsed(budgetAmount);
            DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(billEntryInfo, selectorItemColl);
          }
        }
      }
    }
  }

  public static void updateBudgetDoForReturnBudget(Context ctx, BizCollBillBaseInfo billInfo, boolean approved)
    throws EASBizException, BOSException
  {
    String sourceBillId = billInfo.getSourceBillId();

    boolean isBgForOperationType = false;

    Object param = BizBillUtil.getParamByNumber(ctx, "CP015");

    if (param == null)
      isBgForOperationType = false;
    else {
      isBgForOperationType = new Boolean(param.toString()).booleanValue();
    }
    if ((billInfo instanceof OtherExpenseBillInfo)) {
      OtherExpenseBillInfo info = (OtherExpenseBillInfo)billInfo;
      OtherExpenseBillEntryInfo entryInfo = null;
      OtherExpenseBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (approved) {
          entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

          entryInfo.setBudgetUsed(entryInfo.getAmountApproved());
        } else {
          entryInfo.setBudgetDo(entryInfo.getBudgetUsed().multiply(new BigDecimal("-1")));

          entryInfo.setBudgetUsed(new BigDecimal("0.00"));
        }
        OtherExpenseBillEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(entryInfo.getId()), entryInfo);
      }
    }
    else if ((billInfo instanceof DailyLoanBillInfo)) {
      DailyLoanBillInfo info = (DailyLoanBillInfo)billInfo;
      DailyLoanBillEntryInfo entryInfo = null;
      DailyLoanBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if ((entryInfo.getSourceAmountBalance() != null) && (entryInfo.getSourceAmountBalance().compareTo(new BigDecimal("0.00")) < 0))
        {
          entryInfo.setBudgetDo(new BigDecimal("0.00"));
          entryInfo.setBudgetUsed(new BigDecimal("0.00"));
        } else if (approved) {
          if (sourceBillId == null) {
            entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

            entryInfo.setBudgetUsed(entryInfo.getAmountApproved());
          }
          else if (entryInfo.getAmount().compareTo(entryInfo.getSourceAmountBalance()) > 0)
          {
            if (entryInfo.getAmountApproved().compareTo(entryInfo.getSourceAmountBalance()) > 0)
            {
              entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

              entryInfo.setBudgetUsed(entryInfo.getAmountApproved().subtract(entryInfo.getSourceAmountBalance()));
            }
            else
            {
              entryInfo.setBudgetDo(entryInfo.getSourceAmountBalance().subtract(entryInfo.getAmount()));

              entryInfo.setBudgetUsed(new BigDecimal("0.00"));
            }
          }
        }
        else {
          entryInfo.setBudgetDo(entryInfo.getBudgetUsed().multiply(new BigDecimal("-1")));

          entryInfo.setBudgetUsed(new BigDecimal("0.00"));
        }
        DailyLoanBillEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(entryInfo.getId()), entryInfo);
      }

    }
    else if ((billInfo instanceof EvectionLoanBillInfo)) {
      EvectionLoanBillInfo info = (EvectionLoanBillInfo)billInfo;
      EvectionLoanBillEntryInfo entryInfo = null;
      EvectionLoanBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if ((entryInfo.getSourceAmountBalance() != null) && (entryInfo.getSourceAmountBalance().compareTo(new BigDecimal("0.00")) < 0))
        {
          entryInfo.setBudgetDo(new BigDecimal("0.00"));
          entryInfo.setBudgetUsed(new BigDecimal("0.00"));
        } else if (approved) {
          if (sourceBillId == null) {
            entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

            entryInfo.setBudgetUsed(entryInfo.getAmountApproved());
          }
          else if (entryInfo.getAmount().compareTo(entryInfo.getSourceAmountBalance()) > 0)
          {
            if (entryInfo.getAmountApproved().compareTo(entryInfo.getSourceAmountBalance()) > 0)
            {
              entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

              entryInfo.setBudgetUsed(entryInfo.getAmountApproved().subtract(entryInfo.getSourceAmountBalance()));
            }
            else
            {
              entryInfo.setBudgetDo(entryInfo.getSourceAmountBalance().subtract(entryInfo.getAmount()));

              entryInfo.setBudgetUsed(new BigDecimal("0.00"));
            }
          }
        }
        else
        {
          entryInfo.setBudgetDo(entryInfo.getBudgetUsed().multiply(new BigDecimal("-1")));

          entryInfo.setBudgetUsed(new BigDecimal("0.00"));
        }
        EvectionLoanBillEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(entryInfo.getId()), entryInfo);
      }

    }
    else if ((billInfo instanceof BizAccountBillInfo)) {
      SelectorItemCollection selectorItemColl = new SelectorItemCollection();
      selectorItemColl.add(new SelectorItemInfo("budgetDo"));
      selectorItemColl.add(new SelectorItemInfo("budgetUsed"));
      HashMap sourceTypeMap = new HashMap();
      BizAccountBillInfo info = (BizAccountBillInfo)billInfo;
      BizAccountBillEntryInfo entryInfo = null;
      BizAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      if (sourceBillId == null) {
        if (approved) {
          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

            entryInfo.setBudgetUsed(entryInfo.getAmountApproved());
            BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
          }
        }
        else {
          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            entryInfo.setBudgetDo(entryInfo.getBudgetUsed().multiply(new BigDecimal("-1")));

            entryInfo.setBudgetUsed(new BigDecimal("0.00"));
            BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
          }
        }

      }
      else if (approved) {
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          String sourceTypeID = null;
          if (entryInfo.getBudgetAmount() != null) {
            if (isBgForOperationType) {
              sourceTypeID = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }
            else
            {
              sourceTypeID = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }

            sourceTypeMap.put(sourceTypeID, entryInfo.getId().toString());
          }
        }

        Iterator typeIterator = sourceTypeMap.keySet().iterator();

        while (typeIterator.hasNext()) {
          String sourceTypeID = (String)typeIterator.next();
          String expenseTypeID = null;
          BigDecimal rootCenterAmount = new BigDecimal("0.00");

          BigDecimal rootCenterAmountApproved = new BigDecimal("0.00");

          BigDecimal notRootCenterAmount = new BigDecimal("0.00");

          BigDecimal notRootCenterAmountApproved = new BigDecimal("0.00");

          BigDecimal allAmount = new BigDecimal("0.00");

          BigDecimal allAmountApproved = new BigDecimal("0.00");

          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            String typeid = null;
            if (isBgForOperationType) {
              typeid = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getOperationType().getId().toString();
            }
            else {
              typeid = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getExpenseType().getId().toString();
            }

            if (sourceTypeID.equals(typeid))
            {
              rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());

              rootCenterAmountApproved = rootCenterAmountApproved.add(entryInfo.getAmountApproved());
            }
            else if (sourceTypeID.indexOf(expenseTypeID) != -1) {
              notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());

              notRootCenterAmountApproved = notRootCenterAmountApproved.add(entryInfo.getAmountApproved());

              entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

              entryInfo.setBudgetUsed(entryInfo.getAmountApproved());

              BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
            }

          }

          allAmount = rootCenterAmount.add(notRootCenterAmount);

          allAmountApproved = rootCenterAmountApproved.add(notRootCenterAmountApproved);

          String entryId = (String)sourceTypeMap.get(sourceTypeID);

          BizAccountBillEntryInfo billEntryInfo = BizAccountBillEntryFactory.getLocalInstance(ctx).getBizAccountBillEntryInfo(new ObjectUuidPK(entryId));

          BigDecimal sourceAmountBalance = billEntryInfo.getSourceAmountBalance();

          BigDecimal budgetAmount = new BigDecimal("0.00");

          if (sourceAmountBalance.compareTo(new BigDecimal("0.00")) < 0)
          {
            budgetAmount = new BigDecimal("0.00");
          } else if (sourceAmountBalance.subtract(allAmount).compareTo(new BigDecimal("0.00")) > 0)
          {
            budgetAmount = notRootCenterAmount.subtract(notRootCenterAmountApproved);
          }
          else if (sourceAmountBalance.subtract(allAmountApproved).compareTo(new BigDecimal("0.00")) > 0)
          {
            budgetAmount = sourceAmountBalance.subtract(allAmountApproved).subtract(rootCenterAmount.subtract(rootCenterAmountApproved));
          }
          else budgetAmount = rootCenterAmountApproved.subtract(rootCenterAmount);

          billEntryInfo.setBudgetDo(budgetAmount);
          BigDecimal budgetUsed = new BigDecimal("0.00");
          budgetUsed = billEntryInfo.getBudgetUsed();
          budgetUsed = budgetUsed.add(budgetAmount);
          billEntryInfo.setBudgetUsed(budgetUsed);
          BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(billEntryInfo, selectorItemColl);
        }

      }
      else
      {
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          entryInfo.setBudgetDo(entryInfo.getBudgetUsed().multiply(new BigDecimal("-1")));

          entryInfo.setBudgetUsed(new BigDecimal("0.00"));
          BizAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
        }

      }

    }
    else if ((billInfo instanceof TravelAccountBillInfo)) {
      SelectorItemCollection selectorItemColl = new SelectorItemCollection();
      selectorItemColl.add(new SelectorItemInfo("budgetDo"));
      selectorItemColl.add(new SelectorItemInfo("budgetUsed"));
      HashMap sourceTypeMap = new HashMap();
      TravelAccountBillInfo info = (TravelAccountBillInfo)billInfo;
      TravelAccountBillEntryInfo entryInfo = null;
      TravelAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      if (sourceBillId == null) {
        if (approved) {
          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

            entryInfo.setBudgetUsed(entryInfo.getAmountApproved());
            TravelAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
          }
        }
        else {
          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            entryInfo.setBudgetDo(entryInfo.getBudgetUsed().multiply(new BigDecimal("-1")));

            entryInfo.setBudgetUsed(new BigDecimal("0.00"));
            TravelAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
          }
        }

      }
      else if (approved)
      {
        for (int i = 0; i < length; i++)
        {
          entryInfo = billEntry.get(i);
          String sourceTypeID = null;

          if (entryInfo.getBudgetAmount() != null) {
            if (isBgForOperationType) {
              sourceTypeID = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }
            else
            {
              sourceTypeID = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }

            sourceTypeMap.put(sourceTypeID, entryInfo.getId().toString());
          }

        }

        Iterator typeIterator = sourceTypeMap.keySet().iterator();

        while (typeIterator.hasNext()) {
          String sourceTypeID = (String)typeIterator.next();
          String expenseTypeID = null;
          BigDecimal rootCenterAmount = new BigDecimal("0.00");
          BigDecimal rootCenterAmountApproved = new BigDecimal("0.00");

          BigDecimal notRootCenterAmountApproved = new BigDecimal("0.00");

          BigDecimal notRootCenterAmount = new BigDecimal("0.00");
          BigDecimal allAmountApproved = new BigDecimal("0.00");
          BigDecimal allAmount = new BigDecimal("0.00");
          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            String typeid = null;
            if (isBgForOperationType) {
              typeid = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getOperationType().getId().toString();
            }
            else {
              typeid = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getExpenseType().getId().toString();
            }

            if (sourceTypeID.equals(typeid))
            {
              rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());

              rootCenterAmountApproved = rootCenterAmountApproved.add(entryInfo.getAmountApproved());
            }
            else if (sourceTypeID.indexOf(expenseTypeID) != -1) {
              notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());

              notRootCenterAmountApproved = notRootCenterAmountApproved.add(entryInfo.getAmountApproved());

              entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

              entryInfo.setBudgetUsed(entryInfo.getAmountApproved());

              TravelAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
            }
          }

          allAmountApproved = rootCenterAmountApproved.add(notRootCenterAmountApproved);

          allAmount = rootCenterAmount.add(notRootCenterAmount);
          String entryId = (String)sourceTypeMap.get(sourceTypeID);

          TravelAccountBillEntryInfo billEntryInfo = TravelAccountBillEntryFactory.getLocalInstance(ctx).getTravelAccountBillEntryInfo(new ObjectUuidPK(entryId));

          BigDecimal sourceAmountBalance = billEntryInfo.getSourceAmountBalance();

          BigDecimal budgetAmount = new BigDecimal("0.00");
          if (sourceAmountBalance.compareTo(new BigDecimal("0.00")) < 0)
          {
            budgetAmount = new BigDecimal("0.00");
          } else if (sourceAmountBalance.subtract(allAmount).compareTo(new BigDecimal("0.00")) > 0)
          {
            budgetAmount = notRootCenterAmount.subtract(notRootCenterAmountApproved);
          }
          else if (sourceAmountBalance.subtract(allAmountApproved).compareTo(new BigDecimal("0.00")) > 0)
          {
            budgetAmount = sourceAmountBalance.subtract(allAmountApproved).subtract(rootCenterAmount.subtract(rootCenterAmountApproved));
          }
          else budgetAmount = rootCenterAmountApproved.subtract(rootCenterAmount);

          billEntryInfo.setBudgetDo(budgetAmount);
          BigDecimal budgetUsed = new BigDecimal("0.00");
          budgetUsed = billEntryInfo.getBudgetUsed();
          budgetUsed = budgetUsed.add(budgetAmount);
          billEntryInfo.setBudgetUsed(budgetUsed);
          TravelAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(billEntryInfo, selectorItemColl);
        }

      }
      else
      {
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          entryInfo.setBudgetDo(entryInfo.getBudgetUsed().multiply(new BigDecimal("-1")));

          entryInfo.setBudgetUsed(new BigDecimal("0.00"));
          TravelAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
        }

      }

    }
    else if ((billInfo instanceof DailyPurchaseAccountBillInfo)) {
      SelectorItemCollection selectorItemColl = new SelectorItemCollection();
      selectorItemColl.add(new SelectorItemInfo("budgetDo"));
      selectorItemColl.add(new SelectorItemInfo("budgetUsed"));
      HashMap sourceTypeMap = new HashMap();
      DailyPurchaseAccountBillInfo info = (DailyPurchaseAccountBillInfo)billInfo;
      DailyPurchaseAccountBillEntryInfo entryInfo = null;
      DailyPurchaseAccountBillEntryCollection billEntry = info.getEntries();

      int length = billEntry.size();
      if (sourceBillId == null) {
        if (approved) {
          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

            entryInfo.setBudgetUsed(entryInfo.getAmountApproved());
            DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
          }
        }
        else {
          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            entryInfo.setBudgetDo(entryInfo.getBudgetUsed().multiply(new BigDecimal("-1")));

            entryInfo.setBudgetUsed(new BigDecimal("0.00"));
            DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
          }
        }

      }
      else if (approved)
      {
        for (int i = 0; i < length; i++)
        {
          entryInfo = billEntry.get(i);
          String sourceTypeID = null;

          if (entryInfo.getBudgetAmount() != null) {
            if (isBgForOperationType) {
              sourceTypeID = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }
            else
            {
              sourceTypeID = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();
            }

            sourceTypeMap.put(sourceTypeID, entryInfo.getId().toString());
          }

        }

        Iterator typeIterator = sourceTypeMap.keySet().iterator();

        while (typeIterator.hasNext()) {
          String sourceTypeID = (String)typeIterator.next();
          String expenseTypeID = null;
          BigDecimal rootCenterAmount = new BigDecimal("0.00");

          BigDecimal rootCenterAmountApproved = new BigDecimal("0.00");

          BigDecimal notRootCenterAmount = new BigDecimal("0.00");

          BigDecimal notRootCenterAmountApproved = new BigDecimal("0.00");

          BigDecimal allAmount = new BigDecimal("0.00");

          BigDecimal allAmountApproved = new BigDecimal("0.00");

          for (int i = 0; i < length; i++) {
            entryInfo = billEntry.get(i);
            String typeid = null;
            if (isBgForOperationType) {
              typeid = billEntry.get(i).getOperationType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getOperationType().getId().toString();
            }
            else {
              typeid = billEntry.get(i).getExpenseType().getId().toString() + billEntry.get(i).getCostCenter().getId().toString();

              expenseTypeID = billEntry.get(i).getExpenseType().getId().toString();
            }

            if (sourceTypeID.equals(typeid))
            {
              rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());

              rootCenterAmountApproved = rootCenterAmountApproved.add(entryInfo.getAmountApproved());
            }
            else if (sourceTypeID.indexOf(expenseTypeID) != -1) {
              notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());

              notRootCenterAmountApproved = notRootCenterAmountApproved.add(entryInfo.getAmountApproved());

              entryInfo.setBudgetDo(entryInfo.getAmountApproved().subtract(entryInfo.getAmount()));

              entryInfo.setBudgetUsed(entryInfo.getAmountApproved());

              DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
            }

          }

          allAmount = rootCenterAmount.add(notRootCenterAmount);

          allAmountApproved = rootCenterAmountApproved.add(notRootCenterAmountApproved);

          String entryId = (String)sourceTypeMap.get(sourceTypeID);

          DailyPurchaseAccountBillEntryInfo billEntryInfo = DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).getDailyPurchaseAccountBillEntryInfo(new ObjectUuidPK(entryId));

          BigDecimal sourceAmountBalance = billEntryInfo.getSourceAmountBalance();

          BigDecimal budgetAmount = new BigDecimal("0.00");
          if (sourceAmountBalance.compareTo(new BigDecimal("0.00")) < 0)
          {
            budgetAmount = new BigDecimal("0.00");
          } else if (sourceAmountBalance.subtract(allAmount).compareTo(new BigDecimal("0.00")) > 0)
          {
            budgetAmount = notRootCenterAmount.subtract(notRootCenterAmountApproved);
          }
          else if (sourceAmountBalance.subtract(allAmountApproved).compareTo(new BigDecimal("0.00")) > 0)
          {
            budgetAmount = sourceAmountBalance.subtract(allAmountApproved).subtract(rootCenterAmount.subtract(rootCenterAmountApproved));
          }
          else budgetAmount = rootCenterAmountApproved.subtract(rootCenterAmount);

          billEntryInfo.setBudgetDo(budgetAmount);
          BigDecimal budgetUsed = new BigDecimal("0.00");
          budgetUsed = billEntryInfo.getBudgetUsed();
          budgetUsed = budgetUsed.add(budgetAmount);
          billEntryInfo.setBudgetUsed(budgetUsed);
          DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(billEntryInfo, selectorItemColl);
        }

      }
      else
      {
        for (int i = 0; i < length; i++) {
          entryInfo = billEntry.get(i);
          entryInfo.setBudgetDo(entryInfo.getBudgetUsed().multiply(new BigDecimal("-1")));

          entryInfo.setBudgetUsed(new BigDecimal("0.00"));
          DailyPurchaseAccountBillEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, selectorItemColl);
        }
      }
    }
  }

  public static BizCollBillBaseInfo sortEntry(BizCollBillBaseInfo billInfo)
  {
    BizCollBillBaseInfo retInfo = null;
    Map entryMap = new HashMap();
    if ((billInfo instanceof BizAccountBillInfo)) {
      retInfo = (BizAccountBillInfo)billInfo.clone();
      BizAccountBillEntryCollection entryCollection = ((BizAccountBillInfo)retInfo).getEntries();

      BizAccountBillEntryInfo entryInfo = null;
      int i = 0; for (int n = entryCollection.size(); i < n; i++) {
        entryInfo = entryCollection.get(i);
        String expenseTypeID = entryInfo.getExpenseType().getId().toString();

        String costCenterID = entryInfo.getCostCenter().getId().toString();

        if (entryMap.containsKey(expenseTypeID + costCenterID)) {
          BizAccountBillEntryInfo tmpInfo = (BizAccountBillEntryInfo)entryMap.get(expenseTypeID + costCenterID);

          tmpInfo.setBudgetAmount(addBigDecimal(tmpInfo.getBudgetAmount(), entryInfo.getBudgetAmount()));

          tmpInfo.setAmount(addBigDecimal(tmpInfo.getAmount(), entryInfo.getAmount()));

          tmpInfo.setAmountApproved(addBigDecimal(tmpInfo.getAmountApproved(), entryInfo.getAmountApproved()));
        }
        else
        {
          entryMap.put(expenseTypeID + costCenterID, entryInfo);
        }
      }
      entryCollection.clear();
      Collection coll = entryMap.values();
      Iterator ite = coll.iterator();
      while (ite.hasNext())
        entryCollection.add((BizAccountBillEntryInfo)ite.next());
    }
    else if ((billInfo instanceof DailyPurchaseAccountBillInfo)) {
      retInfo = (DailyPurchaseAccountBillInfo)billInfo.clone();
      DailyPurchaseAccountBillEntryCollection entryCollection = ((DailyPurchaseAccountBillInfo)retInfo).getEntries();

      DailyPurchaseAccountBillEntryInfo entryInfo = null;
      int i = 0; for (int n = entryCollection.size(); i < n; i++) {
        entryInfo = entryCollection.get(i);
        String expenseTypeID = entryInfo.getExpenseType().getId().toString();

        String costCenterID = entryInfo.getCostCenter().getId().toString();

        if (entryMap.containsKey(expenseTypeID + costCenterID)) {
          DailyPurchaseAccountBillEntryInfo tmpInfo = (DailyPurchaseAccountBillEntryInfo)entryMap.get(expenseTypeID + costCenterID);

          tmpInfo.setBudgetAmount(addBigDecimal(tmpInfo.getBudgetAmount(), entryInfo.getBudgetAmount()));

          tmpInfo.setAmount(addBigDecimal(tmpInfo.getAmount(), entryInfo.getAmount()));

          tmpInfo.setAmountApproved(addBigDecimal(tmpInfo.getAmountApproved(), entryInfo.getAmountApproved()));
        }
        else
        {
          entryMap.put(expenseTypeID + costCenterID, entryInfo);
        }
      }
      entryCollection.clear();
      Collection coll = entryMap.values();
      Iterator ite = coll.iterator();
      while (ite.hasNext()) {
        entryCollection.add((DailyPurchaseAccountBillEntryInfo)ite.next());
      }
    }
    else if ((billInfo instanceof TravelAccountBillInfo)) {
      retInfo = (TravelAccountBillInfo)billInfo.clone();
      TravelAccountBillEntryCollection entryCollection = ((TravelAccountBillInfo)retInfo).getEntries();

      TravelAccountBillEntryInfo entryInfo = null;
      int i = 0; for (int n = entryCollection.size(); i < n; i++) {
        entryInfo = entryCollection.get(i);
        String expenseTypeID = entryInfo.getExpenseType().getId().toString();

        String costCenterID = entryInfo.getCostCenter().getId().toString();

        if (entryMap.containsKey(expenseTypeID + costCenterID)) {
          TravelAccountBillEntryInfo tmpInfo = (TravelAccountBillEntryInfo)entryMap.get(expenseTypeID + costCenterID);

          tmpInfo.setBudgetAmount(addBigDecimal(tmpInfo.getBudgetAmount(), entryInfo.getBudgetAmount()));

          tmpInfo.setAmount(addBigDecimal(tmpInfo.getAmount(), entryInfo.getAmount()));

          tmpInfo.setAmountApproved(addBigDecimal(tmpInfo.getAmountApproved(), entryInfo.getAmountApproved()));
        }
        else
        {
          entryMap.put(expenseTypeID + costCenterID, entryInfo);
        }
      }
      entryCollection.clear();
      Collection coll = entryMap.values();
      Iterator ite = coll.iterator();
      while (ite.hasNext()) {
        entryCollection.add((TravelAccountBillEntryInfo)ite.next());
      }
    }
    return retInfo;
  }

  private static BigDecimal addBigDecimal(BigDecimal big1, BigDecimal big2)
  {
    BigDecimal retBig;
    if (null == big1) {
      retBig = big2;
    }
    else
    {
      if (null == big2)
        retBig = big1;
      else
        retBig = big1.add(big2);
    }
    return retBig;
  }

  private static Map getSourceTypeMap(Context ctx, String sourceBillId, boolean isBgForOperationType)
    throws BOSException
  {
    Map typeMap = new HashMap();
    ObjectUuidPK objpk = new ObjectUuidPK(sourceBillId);
    IDynamicObject iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);

    BizCollBillBaseInfo billInfo = (BizCollBillBaseInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk, getSelector());

    if ((billInfo instanceof OtherExpenseBillInfo)) {
      if (isBgForOperationType) {
        typeMap.put(((OtherExpenseBillInfo)billInfo).getOperationType().getId().toString(), ((OtherExpenseBillInfo)billInfo).getCostedDept().getId().toString());
      }
      else
      {
        OtherExpenseBillEntryCollection entryCollection = ((OtherExpenseBillInfo)billInfo).getEntries();

        OtherExpenseBillEntryInfo entryInfo = null;
        int i = 0; for (int n = entryCollection.size(); i < n; i++) {
          entryInfo = entryCollection.get(i);
          typeMap.put(entryInfo.getExpenseType().getId().toString(), ((OtherExpenseBillInfo)billInfo).getCostedDept().getId().toString());
        }

      }

    }
    else if ((billInfo instanceof DailyLoanBillInfo))
    {
      if (isBgForOperationType) {
        typeMap.put(((DailyLoanBillInfo)billInfo).getOperationType().getId().toString(), ((DailyLoanBillInfo)billInfo).getCostedDept().getId().toString());
      }
      else
      {
        DailyLoanBillEntryCollection entryCollection = ((DailyLoanBillInfo)billInfo).getEntries();

        DailyLoanBillEntryInfo entryInfo = null;
        int i = 0; for (int n = entryCollection.size(); i < n; i++) {
          entryInfo = entryCollection.get(i);
          typeMap.put(entryInfo.getExpenseType().getId().toString(), ((DailyLoanBillInfo)billInfo).getCostedDept().getId().toString());
        }

      }

    }
    else if ((billInfo instanceof EvectionLoanBillInfo)) {
      if (isBgForOperationType) {
        typeMap.put(((EvectionLoanBillInfo)billInfo).getOperationType().getId().toString(), ((EvectionLoanBillInfo)billInfo).getCostedDept().getId().toString());
      }
      else
      {
        EvectionLoanBillEntryCollection entryCollection = ((EvectionLoanBillInfo)billInfo).getEntries();

        EvectionLoanBillEntryInfo entryInfo = null;
        int i = 0; for (int n = entryCollection.size(); i < n; i++) {
          entryInfo = entryCollection.get(i);
          typeMap.put(entryInfo.getExpenseType().getId().toString(), ((EvectionLoanBillInfo)billInfo).getCostedDept().getId().toString());
        }
      }

    }

    return typeMap;
  }

  private static BigDecimal getRootCenterAmountBalance(Context ctx, IObjectValue billInfo, String typeId, String rootCenterId, boolean isBgForOperationType)
  {
    BigDecimal rootCenterAmountBalance = new BigDecimal("0.00");

    if ((billInfo instanceof BizAccountBillInfo)) {
      BizAccountBillInfo info = (BizAccountBillInfo)billInfo;
      BizAccountBillEntryInfo entryInfo = null;
      BizAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          if (entryInfo.getSourceAmountBalance() == null)
            continue;
          rootCenterAmountBalance = rootCenterAmountBalance.add(entryInfo.getSourceAmountBalance());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          if (entryInfo.getSourceAmountBalance() == null)
            continue;
          rootCenterAmountBalance = rootCenterAmountBalance.add(entryInfo.getSourceAmountBalance());
        }

      }

    }
    else if ((billInfo instanceof TravelAccountBillInfo)) {
      TravelAccountBillInfo info = (TravelAccountBillInfo)billInfo;
      TravelAccountBillEntryInfo entryInfo = null;
      TravelAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          if (entryInfo.getSourceAmountBalance() == null)
            continue;
          rootCenterAmountBalance = rootCenterAmountBalance.add(entryInfo.getSourceAmountBalance());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          if (entryInfo.getSourceAmountBalance() == null)
            continue;
          rootCenterAmountBalance = rootCenterAmountBalance.add(entryInfo.getSourceAmountBalance());
        }

      }

    }
    else if ((billInfo instanceof DailyPurchaseAccountBillInfo)) {
      DailyPurchaseAccountBillInfo info = (DailyPurchaseAccountBillInfo)billInfo;
      DailyPurchaseAccountBillEntryInfo entryInfo = null;
      DailyPurchaseAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          if (entryInfo.getSourceAmountBalance() == null)
            continue;
          rootCenterAmountBalance = rootCenterAmountBalance.add(entryInfo.getSourceAmountBalance());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          if (entryInfo.getSourceAmountBalance() == null)
            continue;
          rootCenterAmountBalance = rootCenterAmountBalance.add(entryInfo.getSourceAmountBalance());
        }

      }

    }

    return rootCenterAmountBalance;
  }

  private static BigDecimal getRootCenterAmount(Context ctx, IObjectValue billInfo, String typeId, String rootCenterId, boolean isBgForOperationType)
  {
    BigDecimal rootCenterAmount = new BigDecimal("0.00");

    if ((billInfo instanceof BizAccountBillInfo)) {
      BizAccountBillInfo info = (BizAccountBillInfo)billInfo;
      BizAccountBillEntryInfo entryInfo = null;
      BizAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());
        }

      }

    }
    else if ((billInfo instanceof TravelAccountBillInfo)) {
      TravelAccountBillInfo info = (TravelAccountBillInfo)billInfo;
      TravelAccountBillEntryInfo entryInfo = null;
      TravelAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());
        }

      }

    }
    else if ((billInfo instanceof DailyPurchaseAccountBillInfo)) {
      DailyPurchaseAccountBillInfo info = (DailyPurchaseAccountBillInfo)billInfo;
      DailyPurchaseAccountBillEntryInfo entryInfo = null;
      DailyPurchaseAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmount = rootCenterAmount.add(entryInfo.getAmount());
        }
      }

    }

    return rootCenterAmount;
  }

  private static BigDecimal getRootCenterAmountApproved(Context ctx, IObjectValue billInfo, String typeId, String rootCenterId, boolean isBgForOperationType)
  {
    BigDecimal rootCenterAmountApproved = new BigDecimal("0.00");

    if ((billInfo instanceof BizAccountBillInfo)) {
      BizAccountBillInfo info = (BizAccountBillInfo)billInfo;
      BizAccountBillEntryInfo entryInfo = null;
      BizAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmountApproved = rootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmountApproved = rootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }

      }

    }
    else if ((billInfo instanceof TravelAccountBillInfo)) {
      TravelAccountBillInfo info = (TravelAccountBillInfo)billInfo;
      TravelAccountBillEntryInfo entryInfo = null;
      TravelAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmountApproved = rootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmountApproved = rootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }

      }

    }
    else if ((billInfo instanceof DailyPurchaseAccountBillInfo)) {
      DailyPurchaseAccountBillInfo info = (DailyPurchaseAccountBillInfo)billInfo;
      DailyPurchaseAccountBillEntryInfo entryInfo = null;
      DailyPurchaseAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmountApproved = rootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (!rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          rootCenterAmountApproved = rootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }

      }

    }

    return rootCenterAmountApproved;
  }

  private static BigDecimal getNotRootCenterAmount(Context ctx, IObjectValue billInfo, String typeId, String rootCenterId, boolean isBgForOperationType)
  {
    BigDecimal notRootCenterAmount = new BigDecimal("0.00");

    if ((billInfo instanceof BizAccountBillInfo)) {
      BizAccountBillInfo info = (BizAccountBillInfo)billInfo;
      BizAccountBillEntryInfo entryInfo = null;
      BizAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());
        }

      }

    }
    else if ((billInfo instanceof TravelAccountBillInfo)) {
      TravelAccountBillInfo info = (TravelAccountBillInfo)billInfo;
      TravelAccountBillEntryInfo entryInfo = null;
      TravelAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());
        }

      }

    }
    else if ((billInfo instanceof DailyPurchaseAccountBillInfo)) {
      DailyPurchaseAccountBillInfo info = (DailyPurchaseAccountBillInfo)billInfo;
      DailyPurchaseAccountBillEntryInfo entryInfo = null;
      DailyPurchaseAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmount = notRootCenterAmount.add(entryInfo.getAmount());
        }
      }

    }

    return notRootCenterAmount;
  }

  private static BigDecimal getNotRootCenterAmountApproved(Context ctx, IObjectValue billInfo, String typeId, String rootCenterId, boolean isBgForOperationType)
  {
    BigDecimal notRootCenterAmountApproved = new BigDecimal("0.00");

    if ((billInfo instanceof BizAccountBillInfo)) {
      BizAccountBillInfo info = (BizAccountBillInfo)billInfo;
      BizAccountBillEntryInfo entryInfo = null;
      BizAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmountApproved = notRootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmountApproved = notRootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }

      }

    }
    else if ((billInfo instanceof TravelAccountBillInfo)) {
      TravelAccountBillInfo info = (TravelAccountBillInfo)billInfo;
      TravelAccountBillEntryInfo entryInfo = null;
      TravelAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmountApproved = notRootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmountApproved = notRootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }

      }

    }
    else if ((billInfo instanceof DailyPurchaseAccountBillInfo)) {
      DailyPurchaseAccountBillInfo info = (DailyPurchaseAccountBillInfo)billInfo;
      DailyPurchaseAccountBillEntryInfo entryInfo = null;
      DailyPurchaseAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if ((!typeId.equals(entryInfo.getOperationType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmountApproved = notRootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }
        else
        {
          if ((!typeId.equals(entryInfo.getExpenseType().getId().toString())) || (rootCenterId.equals(entryInfo.getCostCenter().getId().toString())))
          {
            continue;
          }
          notRootCenterAmountApproved = notRootCenterAmountApproved.add(entryInfo.getAmountApproved());
        }
      }

    }

    return notRootCenterAmountApproved;
  }

  private static BigDecimal getAllAmount(Context ctx, IObjectValue billInfo, String typeId, String rootCenterId, boolean isBgForOperationType)
  {
    BigDecimal allAmount = new BigDecimal("0.00");

    if ((billInfo instanceof BizAccountBillInfo)) {
      BizAccountBillInfo info = (BizAccountBillInfo)billInfo;
      BizAccountBillEntryInfo entryInfo = null;
      BizAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if (!typeId.equals(entryInfo.getOperationType().getId().toString()))
            continue;
          allAmount = allAmount.add(entryInfo.getAmount());
        }
        else
        {
          if (!typeId.equals(entryInfo.getExpenseType().getId().toString()))
            continue;
          allAmount = allAmount.add(entryInfo.getAmount());
        }

      }

    }
    else if ((billInfo instanceof TravelAccountBillInfo)) {
      TravelAccountBillInfo info = (TravelAccountBillInfo)billInfo;
      TravelAccountBillEntryInfo entryInfo = null;
      TravelAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if (!typeId.equals(entryInfo.getOperationType().getId().toString()))
            continue;
          allAmount = allAmount.add(entryInfo.getAmount());
        }
        else
        {
          if (!typeId.equals(entryInfo.getExpenseType().getId().toString()))
            continue;
          allAmount = allAmount.add(entryInfo.getAmount());
        }

      }

    }
    else if ((billInfo instanceof DailyPurchaseAccountBillInfo)) {
      DailyPurchaseAccountBillInfo info = (DailyPurchaseAccountBillInfo)billInfo;
      DailyPurchaseAccountBillEntryInfo entryInfo = null;
      DailyPurchaseAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if (!typeId.equals(entryInfo.getOperationType().getId().toString()))
            continue;
          allAmount = allAmount.add(entryInfo.getAmount());
        }
        else
        {
          if (!typeId.equals(entryInfo.getExpenseType().getId().toString()))
            continue;
          allAmount = allAmount.add(entryInfo.getAmount());
        }
      }
    }

    return allAmount;
  }

  private static BigDecimal getAllAmountApproved(Context ctx, IObjectValue billInfo, String typeId, String rootCenterId, boolean isBgForOperationType)
  {
    BigDecimal allAmountApproved = new BigDecimal("0.00");

    if ((billInfo instanceof BizAccountBillInfo)) {
      BizAccountBillInfo info = (BizAccountBillInfo)billInfo;
      BizAccountBillEntryInfo entryInfo = null;
      BizAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if (!typeId.equals(entryInfo.getOperationType().getId().toString()))
            continue;
          allAmountApproved = allAmountApproved.add(entryInfo.getAmountApproved());
        }
        else
        {
          if (!typeId.equals(entryInfo.getExpenseType().getId().toString()))
            continue;
          allAmountApproved = allAmountApproved.add(entryInfo.getAmountApproved());
        }

      }

    }
    else if ((billInfo instanceof TravelAccountBillInfo)) {
      TravelAccountBillInfo info = (TravelAccountBillInfo)billInfo;
      TravelAccountBillEntryInfo entryInfo = null;
      TravelAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if (!typeId.equals(entryInfo.getOperationType().getId().toString()))
            continue;
          allAmountApproved = allAmountApproved.add(entryInfo.getAmountApproved());
        }
        else
        {
          if (!typeId.equals(entryInfo.getExpenseType().getId().toString()))
            continue;
          allAmountApproved = allAmountApproved.add(entryInfo.getAmountApproved());
        }

      }

    }
    else if ((billInfo instanceof DailyPurchaseAccountBillInfo)) {
      DailyPurchaseAccountBillInfo info = (DailyPurchaseAccountBillInfo)billInfo;
      DailyPurchaseAccountBillEntryInfo entryInfo = null;
      DailyPurchaseAccountBillEntryCollection billEntry = info.getEntries();
      int length = billEntry.size();
      for (int i = 0; i < length; i++) {
        entryInfo = billEntry.get(i);
        if (isBgForOperationType) {
          if (!typeId.equals(entryInfo.getOperationType().getId().toString()))
            continue;
          allAmountApproved = allAmountApproved.add(entryInfo.getAmountApproved());
        }
        else
        {
          if (!typeId.equals(entryInfo.getExpenseType().getId().toString()))
            continue;
          allAmountApproved = allAmountApproved.add(entryInfo.getAmountApproved());
        }
      }

    }

    return allAmountApproved;
  }

  private static SelectorItemCollection getSelector() {
    SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("number"));
    sic.add(new SelectorItemInfo("amountControlType"));
    sic.add(new SelectorItemInfo("costedDept.id"));
    sic.add(new SelectorItemInfo("costedDept.name"));
    sic.add(new SelectorItemInfo("operationType.id"));
    sic.add(new SelectorItemInfo("operationType.name"));
    sic.add(new SelectorItemInfo("entries.*"));
    sic.add(new SelectorItemInfo("entries.expenseType.id"));
    sic.add(new SelectorItemInfo("entries.expenseType.name"));
    return sic;
  }

  public static void checkForSubmit(Context ctx, BizCollBillBaseInfo billInfo)
    throws BOSException, EASBizException
  {
    String msg = "pass";
    Map map = null;
    String sourceBillId = billInfo.getSourceBillId();
    boolean isBgForOperationType = false;
    boolean isNeedBudget = false;

    boolean isCheckBudgetForSubmit = true;
    try {
      Object param = BizBillUtil.getParamByNumber(ctx, "CP015");
      if (param != null) {
        isBgForOperationType = new Boolean(param.toString()).booleanValue();
      }

      param = BizBillUtil.getParamByNumber(ctx, "CP001");
      if (param != null) {
        isNeedBudget = new Boolean(param.toString()).booleanValue();
      }

      param = BizBillUtil.getParamByNumber(ctx, "CP020");
      if (param != null)
        isCheckBudgetForSubmit = new Boolean(param.toString()).booleanValue();
    }
    catch (Exception e)
    {
      isBgForOperationType = false;
      e.printStackTrace();
    }
    if (sourceBillId == null) {
      if ((isNeedBudget) && (isCheckBudgetForSubmit))
        ExpenseCommenFacadeFactory.getLocalInstance(ctx).isPassBgAudit(billInfo, billInfo.getId(), null);
    }
    else
    {
      String orgName = null;
      String itemName = null;
      if (StateEnum.SUBMIT.equals(billInfo.getState())) {
        ObjectUuidPK objpk = new ObjectUuidPK(sourceBillId);
        IDynamicObject iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);

        BizCollBillBaseInfo sourceInfo = (BizCollBillBaseInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk, getSelector());

        if ((sourceInfo instanceof OtherExpenseBillInfo)) {
          OtherExpenseBillInfo info = (OtherExpenseBillInfo)sourceInfo;
          OtherExpenseEntryCoreBaseInfo entryInfo = null;
          OtherExpenseBillEntryCollection billEntry = null;
          BigDecimal amountBalance = new BigDecimal("0.00");
          billEntry = info.getEntries();
          int length = billEntry.size();
          if (sourceInfo.getAmountControlType().equals(AmountControlTypeEnum.oneTOone))
          {
            for (int i = 0; i < length; i++) {
              entryInfo = billEntry.get(i);
              map = getUpdateAmountMap(ctx, billInfo, entryInfo, isBgForOperationType);

              if (map.size() > 0) {
                amountBalance = (BigDecimal)map.get("amountBalance");

                if (amountBalance.doubleValue() < 0.0D) {
                 // msg = "NoBalance";
                  orgName = sourceInfo.getCostedDept().getName();

                  if (isBgForOperationType) {
                    itemName = sourceInfo.getOperationType().getName(); break;
                  }

                  itemName = entryInfo.getExpenseType().getName();

                  break;
                }
              }
            }
          }
        }
        else if ((sourceInfo instanceof DailyLoanBillInfo)) {
          DailyLoanBillInfo info = (DailyLoanBillInfo)sourceInfo;
          DailyLoanBillEntryInfo entryInfo = null;
          DailyLoanBillEntryCollection billEntry = info.getEntries();
          int length = billEntry.size();
          BigDecimal amountBalance = new BigDecimal("0.00");

          if (sourceInfo.getAmountControlType().equals(AmountControlTypeEnum.oneTOone))
          {
            for (int i = 0; i < length; i++) {
              entryInfo = billEntry.get(i);
              map = getUpdateAmountMap(ctx, billInfo, entryInfo, isBgForOperationType);

              if (map.size() > 0) {
                amountBalance = (BigDecimal)map.get("amountBalance");

                if (amountBalance.doubleValue() < 0.0D) {
                 // msg = "NoBalance";
                  orgName = sourceInfo.getCostedDept().getName();

                  if (isBgForOperationType) {
                    itemName = sourceInfo.getOperationType().getName(); break;
                  }

                  itemName = entryInfo.getExpenseType().getName();

                  break;
                }
              }
            }
          }
        }
        else if ((sourceInfo instanceof EvectionLoanBillInfo)) {
          EvectionLoanBillInfo info = (EvectionLoanBillInfo)sourceInfo;
          EvectionExpBillEntryBaseInfo entryInfo = null;
          EvectionLoanBillEntryCollection billEntry = info.getEntries();
          int length = billEntry.size();
          BigDecimal amountBalance = new BigDecimal("0.00");

          if (sourceInfo.getAmountControlType().equals(AmountControlTypeEnum.oneTOone))
          {
            for (int i = 0; i < length; i++) {
              entryInfo = billEntry.get(i);
              map = getUpdateAmountMap(ctx, billInfo, entryInfo, isBgForOperationType);

              if (map.size() > 0) {
                amountBalance = (BigDecimal)map.get("amountBalance");

                if (amountBalance.doubleValue() < 0.0D) {
                 // msg = "NoBalance";
                  orgName = sourceInfo.getCostedDept().getName();

                  if (isBgForOperationType) {
                    itemName = sourceInfo.getOperationType().getName(); break;
                  }

                  itemName = entryInfo.getExpenseType().getName();

                  break;
                }
              }
            }
          }
        }

      }

      if (msg.equals("pass")) {
        if ((isNeedBudget) && (isCheckBudgetForSubmit)) {
          ExpenseCommenFacadeFactory.getLocalInstance(ctx).isPassBgAudit(billInfo, billInfo.getId(), null);
        }
      }
      else if (msg.equals("NoBalance"))
        throw new BizCollException(BizCollException.NOBALANCE, new Object[] { orgName, itemName });
    }
  }
}