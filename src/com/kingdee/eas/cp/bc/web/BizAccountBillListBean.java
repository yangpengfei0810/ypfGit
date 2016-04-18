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
import com.kingdee.eas.cp.bc.IBizAccountBill;
import com.kingdee.eas.cp.bc.IExpenseAccountFacade;
import com.kingdee.eas.cp.bc.IExpenseCommenFacade;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.UuidException;
import java.util.List;
import java.util.Map;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.component.grid.impl.UIDataGrid;

@ManagedBean(name="cp.bc.BizAccountBillListBean", scope=ManagedBeanScope.SESSION)
public class BizAccountBillListBean extends BCBaseListBean
{

  @IBOSBizCtrl
  private IBizAccountBill service;

  protected String getEditBeanName()
  {
    return "cp.bc.BizAccountBillEditBean";
  }

  protected String getEditUrl()
  {
    return "/cp/bc/bizAccountBillEdit.jsf";
  }

  protected ICoreBase getBizInterface()
  {
    return this.service;
  }

  protected IMetaDataPK getQueryPK()
  {
    return new MetaDataPK("com.kingdee.eas.cp.bc.app.BizAccountQuery");
  }

  protected String getUserQueryView()
  {
    return "/cp/bc/bizAccountBillCommQuery.xhtml";
  }

  public String getBindActionName()
  {
    return "#{cp.bc.BizAccountBillListBean.viewLoanReturnAction}";
  }

  protected void enbledButtonByState(BizEnumValueDTO state)
  {
    super.enbledButtonByState(state);
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
      ((IBizAccountBill)getBizInterface()).antiAudit(BOSUuid.read(billId));

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
        /*if (showErrorForNoWorkFlow()) {
          addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "unAuditNoWorkFlow"));

          return;
        }*/
        ((IBizAccountBill)getBizInterface()).abandon(BOSUuid.read(billId));
      }

      addMessage(FacesMessage.SEVERITY_INFO, Resources.getText(ResourceUtils.CP_BC_WEB_RES, "abandon_success"));

      refresh();
    }
    catch (Exception e) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "alreadyhaveworkflowinstance"));

      e.printStackTrace();
    }
  }

  public void attachment() {
    FacesContext context = FacesContext.getCurrentInstance();
    String navUrl = context.getApplication().getViewHandler().getResourceURL(context, "/cp/bc/FileUploadProgress.xhtml");

    this.outerScript = ("KDWin.open('" + navUrl + "')");
  }

  public String getReportTemplateUrl()
  {
    return "/cp/bc/bizaccount";
  }

  protected String getReportQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.BizAccountForPrintQuery";
  }

  protected String getR1ReportBillQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.BizAccountBillForR1PrintQuery";
  }

  protected String getR1ReportEntryQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.BizAccountBillEntryForPrintQuery";
  }
}