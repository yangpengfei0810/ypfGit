package com.kingdee.eas.cp.bc.web;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.waf.annotation.IBOSBizCtrl;
import com.kingdee.bos.waf.ctx.WafContext;
import com.kingdee.bos.waf.resource.Resources;
import com.kingdee.bos.waf.util.BeanUtil;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.CommonUtilFacadeFactory;
import com.kingdee.eas.cp.bc.EvectionReqBillEntryCollection;
import com.kingdee.eas.cp.bc.EvectionReqBillEntryInfo;
import com.kingdee.eas.cp.bc.EvectionReqBillInfo;
import com.kingdee.eas.cp.bc.ICommonUtilFacade;
import com.kingdee.eas.cp.bc.IEvectionReqBill;
import com.kingdee.eas.cp.bc.PriorEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.cp.bc.VehicleEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.operamasks.faces.annotation.Inject;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.component.grid.impl.UIEditDataGrid;

@ManagedBean(name="cp.bc.EvectionReqBillEditBean", scope=ManagedBeanScope.SESSION)
public class EvectionReqBillEditBean extends BCBaseEditBean
{

  @IBOSBizCtrl
  private IEvectionReqBill service;

  @Inject("cp.bc.EvectionReqBillEntryBean")
  private EvectionReqBillEntryBean entryBean;

  public void createNewModel()
  {
    try
    {
      this.model = new EvectionReqBillInfo();
      Context ctx = WafContext.getInstance().getContext();
      BizCollBillBaseInfo baseInfo = CommonUtilFacadeFactory.getLocalInstance(ctx).forLoanBillCreateNewData();

      ((EvectionReqBillInfo)this.model).setAmount(new BigDecimal("0.00"));
      ((EvectionReqBillInfo)this.model).setBizReqDate(new Date());
      ((EvectionReqBillInfo)this.model).setState(StateEnum.NEW);
      ((EvectionReqBillInfo)this.model).setPrior(PriorEnum.LOW);
      UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
      ((EvectionReqBillInfo)this.model).setBiller(userInfo);
      ((EvectionReqBillInfo)this.model).setBillDate(new Date());
      ((EvectionReqBillInfo)this.model).setApplier(baseInfo.getApplier());
      ((EvectionReqBillInfo)this.model).setPosition(baseInfo.getPosition());
      ((EvectionReqBillInfo)this.model).setOrgUnit(baseInfo.getOrgUnit());
      ((EvectionReqBillInfo)this.model).setApplierCompany(baseInfo.getApplierCompany());

      ((EvectionReqBillInfo)this.model).setCostedDept(baseInfo.getCostedDept());

      ((EvectionReqBillInfo)this.model).setCompany(baseInfo.getCompany());

      ((EvectionReqBillInfo)this.model).setCurrencyType(baseInfo.getCurrencyType());
    }
    catch (BOSException ex)
    {
      System.out.println(ex.getMessage());
      ex.printStackTrace();
      return;
    } catch (EASBizException ex) {
      return;
    }
  }

  public void pageOnload()
  {
    super.pageOnload();
    this.pageTitle = Resources.getText(ResourceUtils.CP_BC_WEB_RES, "EvectionReqBill");

    EvectionReqBillEntryCollection entryCol = ((EvectionReqBillInfo)this.model).getEntries();

    List listModel = new LinkedList();
    if (entryCol == null)
      return;
    if (entryCol.size() == 0) {
      EvectionReqBillEntryInfo entryInfo = new EvectionReqBillEntryInfo();
      entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
      entryInfo.setVehicle(VehicleEnum.AIRPLANE);
      entryCol.add(entryInfo);
    }
    int i = 0; for (int n = entryCol.size(); i < n; i++) {
      listModel.add(entryCol.get(i));
    }
    if (this.entryBean.getEdiGrid() != null) {
      this.entryBean.getEdiGrid().setDataProvider(null);
    }
    this.entryBean.setListModel(listModel);
    this.entryBean.getEdiGrid().setClientRows(Integer.valueOf(listModel.size()));
    this.entryBean.getEdiGrid().reload();
  }

  public void saveModel()
  {
    updateModel();
    super.saveModel();
    EvectionReqBillEntryCollection entryCol = ((EvectionReqBillInfo)this.model).getEntries();

    List listModel = new LinkedList();
    int i = 0; for (int n = entryCol.size(); i < n; i++) {
      listModel.add(entryCol.get(i));
    }
    this.entryBean.setListModel(listModel);
  }

  public void submitModel()
  {
    updateModel();
    super.submitModel();
  }

  protected void updateModel()
  {
    super.updateModel();
    if (this.entryBean == null) {
      this.entryBean = ((EvectionReqBillEntryBean)BeanUtil.getBean("cp.bc.EvectionReqBillEntryBean"));
    }

    this.entryBean.updataModel();
    List<CoreBaseInfo> entryModel = this.entryBean.getListModel();
    ((EvectionReqBillInfo)this.model).getEntries().clear();
    if (entryModel != null) {
      for (CoreBaseInfo entryInfo : entryModel) {
        ((EvectionReqBillInfo)this.model).getEntries().add((EvectionReqBillEntryInfo)entryInfo);
      }

      countIntendingDays();
    }
  }

  protected ICoreBase getBizInterface()
  {
    return this.service;
  }

  protected boolean verifyModel() {
    boolean isPass = true;
    isPass = super.verifyModel();
    if (((EvectionReqBillInfo)this.model).getIntendingDays().intValue() <= 0) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "StartEndDate"));

      isPass = false;
    }
    
    if(StringUtils.isEmpty(((EvectionReqBillInfo)this.model).getCause())){
    	addMessage("操作失败","事由不能为空！");
    	isPass = false;
    }
    EvectionReqBillEntryCollection billEntryCollection = ((EvectionReqBillInfo)this.model).getEntries();

    EvectionReqBillEntryInfo billEntryInfo = null;
    if (isPass) {
      int i = 0; for (int n = billEntryCollection.size(); i < n; i++) {
        billEntryInfo = billEntryCollection.get(i);
        if ((billEntryInfo != null) && (billEntryInfo.getAmount() == null)) {
          addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "entry_est_amount_not_null"));

          isPass = false;
          break;
        }if (billEntryInfo.getAmount().compareTo(new BigDecimal("0.00")) >= 0)
          continue;
        addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "entry_est_amount_less_zero"));

        isPass = false;
        break;
      }
    }

    return isPass;
  }

  private void countIntendingDays() {
    Date start = null; Date end = null;

    EvectionReqBillEntryCollection collection = ((EvectionReqBillInfo)this.model).getEntries();

    EvectionReqBillEntryInfo entryInfo = null;
    int i = 0; for (int n = collection.size(); i < n; i++) {
      entryInfo = collection.get(i);
      Object tempObj = entryInfo.getStartDate();
      if (!BizCollUtil.objectIsNull(tempObj)) {
        if (start == null) {
          start = (Date)tempObj;
        }
        else if (start.after((Date)tempObj)) {
          start = (Date)tempObj;
        }
      }

      tempObj = entryInfo.getEndDate();
      if (!BizCollUtil.objectIsNull(tempObj)) {
        if (end == null) {
          end = (Date)tempObj;
        }
        else if (end.before((Date)tempObj)) {
          end = (Date)tempObj;
        }
      }
    }

    if ((start != null) && (end != null)) {
      long startMs = start.getTime();
      long endMs = end.getTime();
      long diff = endMs - startMs;
      int days = (int)(diff / 86400000L + 1L);
      ((EvectionReqBillInfo)this.model).setIntendingDays(new BigDecimal(days));
    }
    else {
      ((EvectionReqBillInfo)this.model).setIntendingDays(new BigDecimal("0"));
    }
  }

  public SelectorItemCollection getSelectors()
  {
    SelectorItemCollection sic = super.getSelectors();
    sic.add(new SelectorItemInfo("*"));

    sic.add(new SelectorItemInfo("applier.id"));
    sic.add(new SelectorItemInfo("applier.number"));
    sic.add(new SelectorItemInfo("applier.name"));

    sic.add(new SelectorItemInfo("orgUnit.id"));
    sic.add(new SelectorItemInfo("orgUnit.number"));
    sic.add(new SelectorItemInfo("orgUnit.name"));

    sic.add(new SelectorItemInfo("costedDept.id"));
    sic.add(new SelectorItemInfo("costedDept.number"));
    sic.add(new SelectorItemInfo("costedDept.name"));

    sic.add(new SelectorItemInfo("applierCompany.id"));
    sic.add(new SelectorItemInfo("applierCompany.number"));
    sic.add(new SelectorItemInfo("applierCompany.name"));

    sic.add(new SelectorItemInfo("position.id"));
    sic.add(new SelectorItemInfo("position.number"));
    sic.add(new SelectorItemInfo("position.name"));

    sic.add(new SelectorItemInfo("company.id"));
    sic.add(new SelectorItemInfo("company.number"));
    sic.add(new SelectorItemInfo("company.name"));

    sic.add(new SelectorItemInfo("currencyType.id"));
    sic.add(new SelectorItemInfo("currencyType.number"));
    sic.add(new SelectorItemInfo("currencyType.name"));

    sic.add(new SelectorItemInfo("operationType.id"));
    sic.add(new SelectorItemInfo("operationType.number"));
    sic.add(new SelectorItemInfo("operationType.name"));

    sic.add(new SelectorItemInfo("biller.id"));
    sic.add(new SelectorItemInfo("biller.number"));
    sic.add(new SelectorItemInfo("biller.name"));

    sic.add(new SelectorItemInfo("lastUpdateUser.id"));
    sic.add(new SelectorItemInfo("lastUpdateUser.number"));
    sic.add(new SelectorItemInfo("lastUpdateUser.name"));

    sic.add(new SelectorItemInfo("auditor.id"));
    sic.add(new SelectorItemInfo("auditor.number"));
    sic.add(new SelectorItemInfo("auditor.name"));

    sic.add(new SelectorItemInfo("entries.*"));
    return sic;
  }

  public String getReportTemplateUrl()
  {
    return "/cp/bc/evectionReq";
  }

  protected String getReportQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.EvectionReqForPrintQuery";
  }

  protected String getR1ReportBillQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.EvectionReqBillForR1PrintQuery";
  }

  protected String getR1ReportEntryQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.EvectionReqBillEntryForPrintQuery";
  }

  protected IRowSet updateBillReprotRowSet(IRowSet rowSet)
    throws SQLException
  {
    rowSet = super.updateBillReprotRowSet(rowSet);

    BigDecimal amount = null;
    while (rowSet.next()) {
      amount = rowSet.getBigDecimal("amount");
      if ((amount != null) && (amount.compareTo(new BigDecimal("0.00")) == 0)) {
        amount = new BigDecimal("0.00");
      }
      rowSet.updateString("amountCapital", GlUtils.getChineseFormat(amount, false));
    }

    rowSet.beforeFirst();
    return rowSet;
  }

  protected IRowSet updateEntryReprotRowSet(IRowSet rowSet)
    throws SQLException
  {
    return parseEnumValue(rowSet, "vehicle", "vehalias", VehicleEnum.getEnumList());
  }

  protected IRowSet parsePrintValue(IRowSet rowSet)
    throws SQLException
  {
    rowSet = super.parsePrintValue(rowSet);
    BigDecimal amount = null;
    while (rowSet.next()) {
      amount = rowSet.getBigDecimal("amount");
      if ((amount != null) && (amount.compareTo(new BigDecimal("0.00")) == 0)) {
        amount = new BigDecimal("0.00");
      }
      rowSet.updateString("amountCapital", GlUtils.getChineseFormat(amount, false));
    }

    rowSet.beforeFirst();

    rowSet = parseEnumValue(rowSet, "entries.vehicle", "vehalias", VehicleEnum.getEnumList());
    return rowSet;
  }
}