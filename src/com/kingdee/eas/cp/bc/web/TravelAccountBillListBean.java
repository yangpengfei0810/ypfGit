package com.kingdee.eas.cp.bc.web;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.waf.annotation.IBOSBizCtrl;
import com.kingdee.bos.waf.ctx.WafContext;
import com.kingdee.bos.waf.resource.Resources;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.ExpenseAccountFacadeFactory;
import com.kingdee.eas.cp.bc.ExpenseCommenFacadeFactory;
import com.kingdee.eas.cp.bc.IExpenseAccountFacade;
import com.kingdee.eas.cp.bc.IExpenseCommenFacade;
import com.kingdee.eas.cp.bc.ITravelAccountBill;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.cp.bc.VehicleEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.component.grid.impl.UIDataGrid;

@ManagedBean(name="cp.bc.TravelAccountBillListBean", scope=ManagedBeanScope.SESSION)
public class TravelAccountBillListBean extends BCBaseListBean
{

  @IBOSBizCtrl
  private ITravelAccountBill service;

  protected String getEditBeanName()
  {
    return "cp.bc.TravelAccountBillEditBean";
  }

  protected String getEditUrl()
  {
    return "/cp/bc/travelAccountBillEdit.jsf";
  }

  protected ICoreBase getBizInterface()
  {
    return this.service;
  }

  protected IMetaDataPK getQueryPK()
  {
    return new MetaDataPK("com.kingdee.eas.cp.bc.app.TravelAccountQuery");
  }

  protected String getUserQueryView()
  {
    return "/cp/bc/bizAccountBillCommQuery.xhtml";
  }

  public String getBindActionName()
  {
    return "#{cp.bc.TravelAccountBillListBean.viewLoanReturnAction}";
  }

  protected void enbledButtonByState(BizEnumValueDTO state)
  {
    super.enbledButtonByState(state);
    if (state == null)
      return;
    boolean isCurrentFIUnit = BillUtil.isCurrentFIUnit(this.listModel, this.grid);
    //非关闭状态可以删除
    //boolean isDisabled = ((getState(20).equals(state.getValue())) || (getState(40).equals(state.getValue())) || (getState(27).equals(state.getValue()))) && (isCurrentFIUnit);
    boolean isDisabled = (!getState(80).equals(state.getValue())) && (isCurrentFIUnit);
    setButtonDisabled("btn-delete", !isDisabled);
  }

  public void unAuditActionProcess()
  {
    int[] rows = this.grid.getSelections();
    if ((rows == null) || (rows.length == 0)) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "no_selected"));

      return;
    }if (showErrorForNoWorkFlow()) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "unAuditNoWorkFlow"));

      return;
    }
    Map rowData = (Map)this.listModel.get(rows[0] % this.grid.getRows());
    String billId = (String)rowData.get("id");
    try {
      ((ITravelAccountBill)getBizInterface()).antiAudit(BOSUuid.read(billId));

      addMessage(FacesMessage.SEVERITY_INFO, Resources.getText(ResourceUtils.CP_BC_WEB_RES, "unAudit_success"));

      refresh();
    }
    catch (EASBizException e) {
      e.printStackTrace();
    }
    catch (BOSException e) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "unAuditNoWorkFlow"));

      e.printStackTrace();
    }
    catch (UuidException e) {
      e.printStackTrace();
    }
  }

  public void abandonActionProcess()
  {
    int[] rows = this.grid.getSelections();
    if ((rows == null) || (rows.length == 0)) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "no_selected"));

      return;
    }
    Map rowData = (Map)this.listModel.get(rows[0] % this.grid.getRows());
    String billId = (String)rowData.get("id");
    try {
      BizCollBillBaseInfo billInfo = (BizCollBillBaseInfo)getBizInterface().getValue(new ObjectUuidPK(billId));

      if (billInfo.getState().getValue() == 60) {
        Context ctx = WafContext.getInstance().getContext();
        UserInfo currentUserInfo = ContextUtil.getCurrentUserInfo(ctx);
        UserInfo biller = billInfo.getBiller();
        if (!currentUserInfo.getId().equals(biller.getId())) {
          addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "abandon_not_creator"));

          return;
        }if (BillUtil.checkBillHasNextBill(billInfo)) {
          addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "abandon_have_relative"));

          return;
        }
        ExpenseCommenFacadeFactory.getLocalInstance(ctx).returnBudget(BOSUuid.read(billId), billInfo, false);

        IExpenseAccountFacade facade = ExpenseAccountFacadeFactory.getLocalInstance(ctx);

        facade.setState(BOSUuid.read(billId), StateEnum.ALREADYABANDON);
      }
      else
      {
        if (showErrorForNoWorkFlow()) {
          addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "unAuditNoWorkFlow"));

          return;
        }
        ((ITravelAccountBill)getBizInterface()).abandon(BOSUuid.read(billId));

        this.outerScript = "OM.ajax.submit('listForm',null,{'listForm:btn-refresh':''},true);";
      }
      addMessage(FacesMessage.SEVERITY_INFO, Resources.getText(ResourceUtils.CP_BC_WEB_RES, "abandon_success"));

      refresh();
    }
    catch (Exception e) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "alreadyhaveworkflowinstance"));

      e.printStackTrace();
    }
  }

  protected IRowSet parsePrintValue(IRowSet rowSet) throws SQLException {
    rowSet = super.parsePrintValue(rowSet);

    rowSet = parseEnumValue(rowSet, "entries.vehicle", "vehalias", VehicleEnum.getEnumList());

    return rowSet;
  }

  public String getReportTemplateUrl()
  {
    return "/cp/bc/tra";
  }

  public String getReportQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.TravelAccountForPrintQuery";
  }

  protected String getR1ReportBillQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.TravelAccountBillForR1PrintQuery";
  }

  protected String getR1ReportEntryQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.TravelAccountBillEntryForPrintQuery";
  }

  protected IRowSet updateEntryReprotRowSet(IRowSet rowSet)
    throws SQLException
  {
    return parseEnumValue(rowSet, "vehicle", "vehalias", VehicleEnum.getEnumList());
  }

  protected String getR1ReportEntrySumQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.TravelAccountBillEntrySumForPrintQuery";
  }
}