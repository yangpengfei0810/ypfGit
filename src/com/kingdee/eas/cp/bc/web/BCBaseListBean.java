package com.kingdee.eas.cp.bc.web;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.waf.action.WebActionFactory;
import com.kingdee.bos.waf.ctx.WafContext;
import com.kingdee.bos.waf.resource.Resources;
import com.kingdee.bos.waf.rpt.IReportBean;
import com.kingdee.bos.waf.util.BeanUtil;
import com.kingdee.bos.waf.util.Utils;
import com.kingdee.bos.waf.winlet.UIWinStyle;
import com.kingdee.bos.waf.winlet.list.ListBean;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.AmountControlTypeEnum;
import com.kingdee.eas.cp.bc.BillPrintFacadeFactory;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionLoanBillInfo;
import com.kingdee.eas.cp.bc.EvectionReqBillInfo;
import com.kingdee.eas.cp.bc.ExpenseAccountBillInfo;
import com.kingdee.eas.cp.bc.ExpenseCommenFacadeFactory;
import com.kingdee.eas.cp.bc.ForPrintFacadeFactory;
import com.kingdee.eas.cp.bc.IBillPrintFacade;
import com.kingdee.eas.cp.bc.IExpenseCommenFacade;
import com.kingdee.eas.cp.bc.IForPrintFacade;
import com.kingdee.eas.cp.bc.PriorEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.cp.bc.TravelAccountBillInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.print.MultiDataSourceProviderProxy;
import com.kingdee.eas.framework.print.MultiapprovePrintUtility;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.IntEnum;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.operamasks.faces.annotation.Accessible;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.component.ComponentContainer;
import org.operamasks.faces.component.ComponentFactory;
import org.operamasks.faces.component.grid.impl.UIDataGrid;
import org.operamasks.faces.component.widget.UIButton;
import org.operamasks.faces.component.widget.UIToolBar;

@ManagedBean(name="BCBaseListBean", scope=ManagedBeanScope.SESSION)
public abstract class BCBaseListBean extends ListBean
{

  @Accessible
  private UIInput number;

  @Accessible
  private UIInput reqBeginDate;

  @Accessible
  private UIInput reqEndDate;

  @Accessible
  private UIInput applier;

  @Accessible
  private UIInput costedDept;

  @Accessible
  private ViewBudgetAction viewBudgetAction;

  @Accessible
  private ViewLoanReturnAction viewLoanReturnAction;

  @Accessible
  private UnAuditAction unAuditAction;

  @Accessible
  private AbandonAction abandonAction;

  @Accessible
  private SubmitAction submitAction;

  @Accessible
  private ViewWorkFlowAction viewWorkFlowAction;

  @Accessible
  private ViewAuditResultAction viewAuditResultAction;

  @Accessible
  String showHeight;

  @Accessible
  int displayRows = 10;

  protected Map paramMap = null;

  protected String _state = null;

  public void pageOnload()
  {
    setParamMap();
    clearQureyUI();
    super.pageOnload();
    verifyCurrentInfo();
    setEditStyle();
    setButtonDisabled("btn-edit", false);
    setButtonDisabled("btn-submit", false);
    setButtonDisabled("btn-delete", false);
    setButtonDisabled("btn-createTo", false);
    setButtonDisabled("btn-unAudit", false);
    setButtonDisabled("btn-abandon", false);

    clearQureyUI();
    initShowHeight();
  }

  public void setEntityViewInfoOnload() {
    refreshList("pageOnload");
  }

  private void verifyCurrentInfo() {
    Context ctx = WafContext.getInstance().getContext();
    CompanyOrgUnitInfo currentCompany = ContextUtil.getCurrentFIUnit(ctx);
    if ((currentCompany == null) || (currentCompany.isIsOnlyUnion()))
      throw new FacesException(new BizCollException(BizCollException.COMPANY_IS_UNION));
  }

  private void setParamMap()
  {
    Context ctx = WafContext.getInstance().getContext();
    List paramList = new ArrayList();
    paramList.add("CP001");
    paramList.add("CP002");
    try {
      this.paramMap = ExpenseCommenFacadeFactory.getLocalInstance(ctx).getParams(paramList);
    }
    catch (EASBizException e) {
      e.printStackTrace();
    } catch (BOSException e) {
      e.printStackTrace();
    }
  }

  public void setEditStyle() {
    getWinStyle().setHeight(Integer.valueOf(688));
    getWinStyle().setWidth(Integer.valueOf(894));
    getWinStyle().setScroll("yes");
  }

  public void refreshList(String param) {
    if ("page".equals(param)) {
      if ((this.reqBeginDate.getValue() != null) && (this.reqEndDate.getValue() != null)) {
        Date beginDate = (Date)this.reqBeginDate.getValue();
        Date endDate = (Date)this.reqEndDate.getValue();
        if (beginDate.compareTo(endDate) > 0) {
          addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "BEGINDATE_GREATER_ENDDATE"));

          return;
        }
      }
      this.entityViewInfo = new EntityViewInfo();
    }
    try {
      if (!this.botpView) {
        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if ((this._state == null) || ((params.get("state") != null) && (!this._state.equals(params.get("state")))))
        {
          this._state = ((String)params.get("state"));
        }
        String state = (String)params.get("state");
        if (state == null) {
          state = this._state;
        }
        EntityViewInfo entityViewInfo = new EntityViewInfo();
        if (state != null) {
          if ("draft".equals(state)) {
            entityViewInfo.setFilter("state=20 or state=27 or state=40");
          }
          else if ("checking".equals(state)) {
            entityViewInfo.setFilter("state=25 or state=30 or state=60");
          }
          else if ("close".equals(state)) {
            entityViewInfo.setFilter("state=45 or state=70 or state=75 or state=80");
          }

          mergeEntityViewInfo(entityViewInfo);
        }

        entityViewInfo = new EntityViewInfo();
        FilterInfo filterInfo = new FilterInfo();
        FilterItemCollection itemCollection = filterInfo.getFilterItems();

        FilterItemInfo item = null;
        if ((this.number.getValue() != null) && (!"".equals(this.number.getValue()))) {
          item = new FilterItemInfo("number", "%" + (String)this.number.getValue() + "%", CompareType.LIKE);

          itemCollection.add(item);
        }
        if (this.applier.getValue() != null) {
          item = new FilterItemInfo("applier.id", ((PersonInfo)this.applier.getValue()).getId().toString());

          itemCollection.add(item);
        }
        if (this.costedDept.getValue() != null) {
          item = new FilterItemInfo("costedDept.id", ((CostCenterOrgUnitInfo)this.costedDept.getValue()).getId().toString());

          itemCollection.add(item);
        }
        if (this.reqBeginDate.getValue() != null) {
          item = new FilterItemInfo("bizReqDate", BillUtil.getBeginDate((Date)this.reqBeginDate.getValue()), CompareType.GREATER_EQUALS);

          itemCollection.add(item);
        }
        if (this.reqEndDate.getValue() != null) {
          item = new FilterItemInfo("bizReqDate", BillUtil.getEndDate((Date)this.reqEndDate.getValue()), CompareType.LESS_EQUALS);

          itemCollection.add(item);
        }
        entityViewInfo.setFilter(filterInfo);
        mergeEntityViewInfo(entityViewInfo);

        entityViewInfo = new EntityViewInfo();
        filterInfo = new FilterInfo();
        itemCollection = filterInfo.getFilterItems();
        Context ctx = WafContext.getInstance().getContext();
        CompanyOrgUnitInfo curCompany = ContextUtil.getCurrentFIUnit(ctx);

        item = new FilterItemInfo("applierCompany.id", curCompany.getId().toString(), CompareType.LIKE);

        itemCollection.add(item);
        entityViewInfo.setFilter(filterInfo);
        mergeEntityViewInfo(entityViewInfo);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if ("page".equals(param))
      refresh();
    else
      clearQureyUI();
  }

  public boolean isTransWithAllEntries()
  {
    return true;
  }

  public SelectorItemCollection getBOTPSelectors()
  {
    SelectorItemCollection sic = super.getBOTPSelectors();
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

    sic.add(new SelectorItemInfo("supportedObj.id"));
    sic.add(new SelectorItemInfo("supportedObj.number"));
    sic.add(new SelectorItemInfo("supportedObj.name"));

    sic.add(new SelectorItemInfo("currencyType.id"));
    sic.add(new SelectorItemInfo("currencyType.number"));
    sic.add(new SelectorItemInfo("currencyType.name"));

    sic.add(new SelectorItemInfo("payMode.id"));
    sic.add(new SelectorItemInfo("payMode.number"));
    sic.add(new SelectorItemInfo("payMode.name"));

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
    sic.add(new SelectorItemInfo("entries.expenseType.id"));
    sic.add(new SelectorItemInfo("entries.expenseType.name"));
    sic.add(new SelectorItemInfo("entries.expenseType.number"));
    return sic;
  }

  public List<String> getDestBosTypeForBotp(CoreBillBaseCollection srcBillInfos, List<String> destBosTypeList)
  {
    List list = getBcBosTypeForBotp(srcBillInfos, destBosTypeList);
    Iterator ite = destBosTypeList.iterator();
    String bosType = null;
    while (ite.hasNext()) {
      bosType = (String)ite.next();
      if (!list.contains(bosType)) {
        destBosTypeList.remove(bosType);
        ite = destBosTypeList.iterator();
      }
    }
    return destBosTypeList;
  }

  public List<String> getBcBosTypeForBotp(CoreBillBaseCollection srcBillInfos, List<String> destBosTypeList)
  {
    List list = new ArrayList();
    BizCollBillBaseInfo info = new DailyLoanBillInfo();
    list.add(info.getBOSType().toString());
    info = new EvectionLoanBillInfo();
    list.add(info.getBOSType().toString());
    info = new BizAccountBillInfo();
    list.add(info.getBOSType().toString());
    info = new TravelAccountBillInfo();
    list.add(info.getBOSType().toString());
    return list;
  }

  protected void addToolBarButton()
  {
    List buttons = this.toolbar.getChildren();
    int i = 0; for (int size = buttons.size(); i < size; i++) {
      UIButton btn = (UIButton)buttons.get(i);
      if (btn.getId().equals("btn-viewLoanReturn")) {
        buttons.remove(btn);
        break;
      }
    }

    UIButton button = (UIButton)ComponentContainer.getInstance().getComponentFactory("org.operamasks.faces.widget.button").createComponent();

    button.setValue(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "viewLoanReturn"));

    button.setId("btn-viewLoanReturn");

    FacesContext context = FacesContext.getCurrentInstance();
    ExpressionFactory ef = context.getApplication().getExpressionFactory();

    ValueExpression ve = ef.createValueExpression(context.getELContext(), getBindActionName(), Object.class);

    button.setValueExpression("actionBinding", ve);

    int count = this.toolbar.getChildCount();
    this.toolbar.getChildren().add(count, button);
  }

  public String getBindActionName() {
    return "#{cp.bc.TravelAccountBillListBean.viewLoanReturnAction}";
  }

  public BCBaseListBean()
  {
    this.viewBudgetAction = ((ViewBudgetAction)WebActionFactory.createWebAction("viewBudgetAction", ViewBudgetAction.class, getServiceContext()));

    this.viewBudgetAction.setBaseBean(this);
    this.viewLoanReturnAction = ((ViewLoanReturnAction)WebActionFactory.createWebAction("viewLoanReturnAction", ViewLoanReturnAction.class, getServiceContext()));

    this.viewLoanReturnAction.setBaseBean(this);
    this.unAuditAction = ((UnAuditAction)WebActionFactory.createWebAction("unAudtiAction", UnAuditAction.class, getServiceContext()));

    this.unAuditAction.setListBean(this);
    this.abandonAction = ((AbandonAction)WebActionFactory.createWebAction("abandonAction", AbandonAction.class, getServiceContext()));

    this.abandonAction.setListBean(this);
    this.submitAction = ((SubmitAction)WebActionFactory.createWebAction("submitAction", SubmitAction.class, getServiceContext()));

    this.submitAction.setListBean(this);
    this.viewWorkFlowAction = ((ViewWorkFlowAction)WebActionFactory.createWebAction("viewWorkFlowAction", ViewWorkFlowAction.class, getServiceContext()));

    this.viewWorkFlowAction.setBaseBean(this);
    this.viewAuditResultAction = ((ViewAuditResultAction)WebActionFactory.createWebAction("viewAuditResultAction", ViewAuditResultAction.class, getServiceContext()));

    this.viewAuditResultAction.setBaseBean(this);
  }

  protected String getReportURL() {
    return "loanReturnRecordReport.jsf";
  }

  protected void viewBudget() throws Exception {
    int[] rows = this.grid.getSelections();
    if ((rows == null) || (rows.length == 0)) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "no_selected"));

      return;
    }
    String billId = getPrimaryKey(this.listModel.get(rows[0] % this.grid.getRows())).toString();

    BgBalanceViewBean viewBean = (BgBalanceViewBean)BeanUtil.getBean("cp.bc.BgBalanceViewBean");

    if (viewBean != null) {
      viewBean.setBillId(billId);
    }

    UIWinStyle winStyle = new UIWinStyle();
    winStyle.setWidth(Integer.valueOf(890));
    winStyle.setScroll("yes");
    winStyle.setTitle(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "viewBudget"));

    Context ctx = WafContext.getInstance().getContext();
    ctx.put("billId", billId);
    FacesContext context = FacesContext.getCurrentInstance();
    String navUrl = context.getApplication().getViewHandler().getResourceURL(context, "bgBalanceView.jsf");

    this.outerScript = ("KDWin.open('" + navUrl + "', " + Utils.getEditorOptions(winStyle) + ")");
  }

  protected void viewLoanAndReturnRecord() throws Exception
  {
    int[] rows = this.grid.getSelections();
    if ((rows == null) || (rows.length == 0)) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "no_selected"));

      return;
    }
    String billId = getPrimaryKey(this.listModel.get(rows[0] % this.grid.getRows())).toString();

    LoanReturnRecordReportBean viewBean = (LoanReturnRecordReportBean)BeanUtil.getBean("cp.bc.LoanReturnRecordReportBean");

    if (viewBean != null) {
      viewBean.setBillId(billId);
    }
    UIWinStyle winStyle = new UIWinStyle();
    winStyle.setWidth(Integer.valueOf(890));
    winStyle.setScroll("yes");
    winStyle.setTitle(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "viewLoanReturn"));

    Context ctx = WafContext.getInstance().getContext();
    ctx.put("billId", billId);

    FacesContext context = FacesContext.getCurrentInstance();
    String navUrl = context.getApplication().getViewHandler().getResourceURL(context, getReportURL()) + "?billId=" + billId;

    this.outerScript = ("KDWin.open('" + navUrl + "', " + Utils.getEditorOptions(winStyle) + ")");
  }

  public void clearQureyUI()
  {
    this.number.setValue(null);
    this.applier.setValue(null);
    this.reqBeginDate.setValue(null);
    this.reqEndDate.setValue(null);
    this.costedDept.setValue(null);
  }

  public void rowSelect() {
    try {
      BizEnumValueDTO state = null;
      Set stateSet = new HashSet();
      int[] rows = this.grid.getSelections();
      if (rows == null) {
        return;
      }
      boolean flag = false;
      int i = 0; for (int n = rows.length; i < n; i++) {
        Map map = (Map)this.listModel.get(rows[i] % this.grid.getRows());
        state = (BizEnumValueDTO)map.get("state");
        flag = (getState(20).equals(state.getValue())) || (getState(40).equals(state.getValue())) || (getState(27).equals(state.getValue()));

        if (flag)
          stateSet.add("0");
        else {
          stateSet.add(state.getValue().toString());
        }
      }
      if (stateSet.size() > 1) {
        state = null;
      }
      enbledButtonByState(state);
    } catch (IndexOutOfBoundsException e) {
      this.grid.reload();
    } catch (NullPointerException e2) {
      this.grid.reload();
      this.logger.info(e2.getMessage());
    }
  }

  protected void enbledButtonByState(BizEnumValueDTO state)
  {
    if (state == null) {
      setButtonDisabled("btn-edit", true);
      setButtonDisabled("btn-submit", true);
      setButtonDisabled("btn-delete", true);
      setButtonDisabled("btn-createTo", true);
      setButtonDisabled("btn-unAudit", true);
      setButtonDisabled("btn-abandon", true);
      return;
    }
    boolean isDisabled = false;
    boolean isCurrentFIUnit = BillUtil.isCurrentFIUnit(this.listModel, this.grid);
    isDisabled = ((getState(20).equals(state.getValue())) || (getState(25).equals(state.getValue())) || (getState(40).equals(state.getValue())) || (getState(27).equals(state.getValue()))) && (isCurrentFIUnit);

    setButtonDisabled("btn-edit", !isDisabled);
    isDisabled = ((getState(20).equals(state.getValue())) || (getState(40).equals(state.getValue())) || (getState(27).equals(state.getValue()))) && (isCurrentFIUnit);

    setButtonDisabled("btn-submit", !isDisabled);
   // isDisabled = ((getState(20).equals(state.getValue())) || (getState(40).equals(state.getValue())) || (getState(27).equals(state.getValue()))) && (isCurrentFIUnit);
    isDisabled = (!getState(80).equals(state.getValue()))&&(getState(27).equals(state.getValue())||(getState(20).equals(state.getValue())))&& (isCurrentFIUnit);
    
    setButtonDisabled("btn-delete", !isDisabled);
    isDisabled = (getState(30).equals(state.getValue())) && (isCurrentFIUnit);

    setButtonDisabled("btn-unAudit", !isDisabled);
    isDisabled = ((getState(25).equals(state.getValue())) || (getState(30).equals(state.getValue())) || (getState(60).equals(state.getValue()))) && (isCurrentFIUnit);

    setButtonDisabled("btn-abandon", !isDisabled);
  }

  protected String getState(int state) {
    return String.valueOf(state);
  }

  public boolean isRenderByState(String param) {
    boolean isRender = true;
    Map componentRenderMap = new HashMap();
    if (this.botpView) {
      componentRenderMap.put("queryPanel", Boolean.valueOf(false));
    }
    boolean isNeedBudget = this.paramMap.get("CP001") == null ? false : new Boolean(this.paramMap.get("CP001").toString()).booleanValue();

    if (!isNeedBudget) {
      componentRenderMap.put("btn-viewBudget", Boolean.valueOf(false));
    }
    isRender = componentRenderMap.get(param) == null ? true : ((Boolean)componentRenderMap.get(param)).booleanValue();

    return isRender;
  }

  protected void setButtonDisabled(String btnId, boolean isDisabled)
  {
    int i = 0; for (int size = this.toolbar.getChildren().size(); i < size; i++)
      if ((this.toolbar.getChildren().get(i) instanceof UIButton)) {
        UIButton btn = (UIButton)this.toolbar.getChildren().get(i);
        if (btn.getId().equals(btnId)) {
          btn.setDisabled(isDisabled);
          break;
        }
      }
  }

  public IReportBean getReportBean()
  {
    return new IReportBean()
    {
      public Object getDataProvider() {
        BCBaseListBean.DataProvider data = new BCBaseListBean.DataProvider();
        MultiDataSourceProviderProxy multiDataSourceProviderProxy = BCBaseListBean.this.getMultiQueryDelegate();
        multiDataSourceProviderProxy.put("MainQuery", data);
        return multiDataSourceProviderProxy;
      }

      public String getTemplateType()
      {
        return BCBaseListBean.this.getReportTemplateUrl();
      }
    };
  }

  public String getReportTemplateUrl() {
    return null;
  }

  protected String getReportQueryName() {
    return null;
  }

  protected String getR1ReportBillQueryName()
  {
    return null;
  }

  protected String getR1ReportEntryQueryName() {
    return null;
  }

  protected String getR1ReportEntrySumQueryName() {
    return null;
  }

  protected IRowSet updateBillReprotRowSet(IRowSet rowSet)
    throws SQLException
  {
    BigDecimal amount = null;
    while (rowSet.next()) {
      amount = rowSet.getBigDecimal("amountApproved");
      if ((amount != null) && (amount.compareTo(new BigDecimal("0.00")) == 0)) {
        amount = new BigDecimal("0.00");
      }
      rowSet.updateString("amountCapital", GlUtils.getChineseFormat(amount, false));
    }

    rowSet.beforeFirst();
    rowSet = parseEnumValue(rowSet, "prior", "prioralias", PriorEnum.getEnumList());

    rowSet = parseEnumValue(rowSet, "state", "stateAlias", StateEnum.getEnumList());
    return rowSet;
  }

  protected IRowSet updateEntryReprotRowSet(IRowSet rowSet) throws SQLException
  {
    return rowSet;
  }

  protected MultiDataSourceProviderProxy getMultiQueryDelegate()
  {
    Context ctx = WafContext.getInstance().getContext();
    return MultiapprovePrintUtility.getMultiQueryDelegate(getSelectedIdForPrint(), ctx);
  }

  protected List getSelectedIdForPrint()
  {
    int[] rows = this.grid.getSelections();
    if ((rows == null) || (rows.length == 0)) {
      return null;
    }
    Map rowData = (Map)this.listModel.get(rows[0] % this.grid.getRows());
    String billId = (String)rowData.get("id");
    List idList = new ArrayList();
    idList.add(billId);
    return idList;
  }

  protected List getSelectedBillIds()
  {
    List selectedIds = new ArrayList();
    int[] rows = this.grid.getSelections();

    int gridSize = this.grid.getRows();

    if ((rows == null) || (rows.length == 0)) {
      return selectedIds;
    }
    int length = rows.length;
    for (int i = 0; i < length; i++) {
      Map rowData = (Map)this.listModel.get(rows[i] % gridSize);
      String billId = (String)rowData.get("id");
      selectedIds.add(billId);
    }
    return selectedIds;
  }

  protected IRowSet parsePrintValue(IRowSet rowSet)
    throws SQLException
  {
    BigDecimal amount = null;
    rowSet.previous();
    while (rowSet.next()) {
      amount = rowSet.getBigDecimal("amountApproved");
      if ((amount != null) && (amount.compareTo(new BigDecimal("0.00")) == 0)) {
        amount = new BigDecimal("0.00");
      }
      rowSet.updateString("amountCapital", GlUtils.getChineseFormat(amount, false));
    }

    rowSet.beforeFirst();
    rowSet = parseEnumValue(rowSet, "prior", "prioralias", PriorEnum.getEnumList());

    rowSet = parseEnumValue(rowSet, "state", "stateAlias", StateEnum.getEnumList());

    return rowSet;
  }

  public IRowSet parseEnumValue(IRowSet rowSet, String value, String valueAlias, List list)
    throws SQLException
  {
    if (rowSet == null)
      return rowSet;
    if (list == null)
      return rowSet;
    if ((value == null) || (value.equals("")))
      return rowSet;
    if (!rowSet.next())
      return rowSet;
    String mark = "";
    int tempPrior = -1;
    int pos = rowSet.findColumn(valueAlias);
    Iterator ite = null;
    IntEnum enums = null;
    rowSet.previous();
    while (rowSet.next()) {
      ite = list.iterator();
      tempPrior = rowSet.getInt(value);
      while (ite.hasNext()) {
        enums = (IntEnum)ite.next();
        if (tempPrior == enums.getValue())
          mark = enums.getAlias();
      }
      rowSet.updateString(pos, mark);
    }
    rowSet.beforeFirst();
    return rowSet;
  }

  public void createToAssBill()
  {
    int[] rows = this.grid.getSelections();
    if ((rows == null) || (rows.length == 0)) {
      addMessage(Resources.getText("actionFailed"), Resources.getText("noRowSelected"));

      return;
    }
    Map rowData = (Map)this.listModel.get(rows[0] % this.grid.getRows());
    String billId = (String)rowData.get("id");
    ObjectUuidPK objpk = new ObjectUuidPK(billId);
    try
    {
      Context ctx = WafContext.getInstance().getContext();
      BizCollBillBaseInfo billInfo = (BizCollBillBaseInfo)DynamicObjectFactory.getLocalInstance(ctx).getValue(objpk.getObjectType(), objpk, getSelector());
      /*
      if ((!(billInfo instanceof ExpenseAccountBillInfo)) && (!(billInfo instanceof EvectionReqBillInfo)))
      {
        if (((billInfo.getAmountBalance() != null) && (billInfo.getAmountBalance().doubleValue() < 0.0D)) || ((AmountControlTypeEnum.oneTOone.equals(billInfo.getAmountControlType())) && (billInfo.getAmountBalance() != null) && (billInfo.getAmountBalance().doubleValue() == 0.0D)))
        {
          addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "cannot_createTo1"));

          return;
        }
      }*/
      boolean isRelaFi = this.paramMap.get("CP002") == null ? false : new Boolean(this.paramMap.get("CP002").toString()).booleanValue();

      if ((isRelaFi) && (!BizCollUtil.checkForBillCreateTo(ctx, billId))) {
        addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "cannot_createTo2"));

        return;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    super.createToAssBill();
  }

  private static SelectorItemCollection getSelector() {
    SelectorItemCollection sic = new SelectorItemCollection();
    sic.add("*");
    sic.add("entries.*");
    sic.add(new SelectorItemInfo("company.number"));
    sic.add(new SelectorItemInfo("company.name"));
    sic.add(new SelectorItemInfo("costedDept.number"));
    sic.add(new SelectorItemInfo("costedDept.name"));
    sic.add(new SelectorItemInfo("currencyType.name"));
    sic.add(new SelectorItemInfo("currencyType.number"));
    sic.add(new SelectorItemInfo("supportedObj.number"));
    sic.add(new SelectorItemInfo("supportedObj.name"));
    sic.add(new SelectorItemInfo("entries.expenseType.number"));
    sic.add(new SelectorItemInfo("entries.expenseType.typeName"));
    sic.add(new SelectorItemInfo("entries.expenseType.name"));
    sic.add(new SelectorItemInfo("entries.costCenter.number"));
    sic.add(new SelectorItemInfo("entries.costCenter.name"));
    sic.add(new SelectorItemInfo("entries.company.number"));
    sic.add(new SelectorItemInfo("entries.company.name"));
    return sic;
  }

  public void unAuditActionProcess()
  {
  }

  public void abandonActionProcess()
  {
  }

  public void submitActionProcess()
  {
    int[] rows = this.grid.getSelections();
    if ((rows == null) || (rows.length == 0)) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "no_selected"));

      return;
    }
    Context ctx = WafContext.getInstance().getContext();
    Map map = null;
    String id = null;
    BizCollBillBaseInfo info = null;
    ObjectUuidPK objpk = null;
    int successCount = 0;
    int faildCount = 0;
    int i = 0; for (int n = rows.length; i < n; i++) {
      map = (Map)this.listModel.get(rows[i] % this.grid.getRows());
      id = (String)map.get("id");
      try {
        objpk = new ObjectUuidPK(id);
        info = (BizCollBillBaseInfo)DynamicObjectFactory.getLocalInstance(ctx).getValue(objpk.getObjectType(), objpk, getSelector());

        info.setState(StateEnum.SUBMIT);
        getBizInterface().submit(info);
        successCount++;
      }
      catch (EASBizException e) {
        faildCount++;
        e.printStackTrace();
      }
      catch (BOSException e) {
        faildCount++;
        e.printStackTrace();
      }
    }
    StringBuffer msg = new StringBuffer();
    msg = msg.append(Resources.getText("submitSuccessful")).append(successCount).append(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "tiao")).append(",").append(Resources.getText("submitBillFailed")).append(faildCount).append(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "tiao")).append("!");

    if (faildCount > 0) {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "BatchSubmit"), msg.toString());
    }
    else {
      addMessage(FacesMessage.SEVERITY_INFO, Resources.getText(ResourceUtils.CP_BC_WEB_RES, "BatchSubmit"), msg.toString());
    }

    refresh();
  }

  public boolean showErrorForNoWorkFlow() {
    Context ctx = WafContext.getInstance().getContext();
    int[] rows = this.grid.getSelections();
    if ((rows == null) || (rows.length == 0)) {
      return false;
    }
    Map rowData = (Map)this.listModel.get(rows[0] % this.grid.getRows());
    String billId = (String)rowData.get("id");
    try
    {
      IEnactmentService service2 = EnactmentServiceFactory.createEnactService(ctx);
      ProcessInstInfo instInfo = null;
      ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(billId);

      int i = 0; for (int n = procInsts.length; i < n; i++) {
        if ((!"open.running".equals(procInsts[i].getState())) && (!"open.not_running.suspended".equals(procInsts[i].getState()))) {
          continue;
        }
        instInfo = procInsts[i];
      }

      return instInfo == null;
    }
    catch (BOSException e)
    {
      e.printStackTrace();
    }return false;
  }

  public boolean checkSelected(int[] rows)
  {
    if ((rows == null) || (rows.length == 0)) {
      addMessage(Resources.getText("actionFailed"), Resources.getText("noRowSelected"));

      return false;
    }

    return true;
  }

  public void viewWorkFlow()
    throws Exception
  {
    int[] rows = this.grid.getSelections();
    if (!checkSelected(rows)) {
      return;
    }
    Map rowData = (Map)this.listModel.get(rows[0] % this.grid.getRows());
    String billId = (String)rowData.get("id");
    UIWinStyle winStyle = new UIWinStyle();
    winStyle.setWidth(Integer.valueOf(800));
    winStyle.setHeight(Integer.valueOf(600));
    winStyle.setScroll("yes");
    FacesContext context = FacesContext.getCurrentInstance();
    String navUrl = context.getApplication().getViewHandler().getResourceURL(context, "/workflow/viewProcessDiagram.jsf?billId=" + billId);

    this.outerScript = ("KDWin.open('" + navUrl + "', " + Utils.getEditorOptions(winStyle) + ")");
  }

  public void viewAuditResult()
    throws Exception
  {
    int[] rows = this.grid.getSelections();
    if (!checkSelected(rows)) {
      return;
    }
    Map rowData = (Map)this.listModel.get(rows[0] % this.grid.getRows());
    String billId = (String)rowData.get("id");
    UIWinStyle winStyle = new UIWinStyle();
    winStyle.setWidth(Integer.valueOf(950));
    winStyle.setHeight(Integer.valueOf(350));
    winStyle.setScroll("yes");
    FacesContext context = FacesContext.getCurrentInstance();
    String navUrl = context.getApplication().getViewHandler().getResourceURL(context, "/workflow/approveHistory.jsf?billId=" + billId);

    this.outerScript = ("KDWin.open('" + navUrl + "', " + Utils.getEditorOptions(winStyle) + ")");
  }

  public String f7CostCenterFilterInfo()
  {
    String filterString = null;
    FilterInfo filter = new FilterInfo();
    FilterItemCollection fic = filter.getFilterItems();
    fic.add(new FilterItemInfo("isBizUnit", Boolean.valueOf(true)));
    filter.setMaskString("#0");
    filterString = filter.toString();
    return filterString;
  }

  public void refresh()
  {
    super.refresh();
    this.grid.setSelections(null);
  }

  protected void initShowHeight()
  {
    Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clientHeight");
    if (obj != null)
      this.showHeight = obj.toString();
    else {
      this.showHeight = "600";
    }

    Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String objMethod = (String)params.get("fromMethod");
    int tempHeight = Integer.parseInt(this.showHeight);
    if ("commonFunctionPortlet".equals(objMethod)) {
      this.showHeight = String.valueOf(tempHeight - 160);
      this.displayRows = ((tempHeight - 160 - 53) / 20);

      if (tempHeight - 160 - 53 - this.displayRows * 20 < 19)
        this.displayRows -= 1;
    }
    else if ("commonFunctionPortlet".equals(objMethod)) {
      this.showHeight = String.valueOf(tempHeight - 160);
      this.displayRows = ((tempHeight - 160 - 53) / 20);

      if (tempHeight - 160 - 53 - this.displayRows * 20 < 19)
        this.displayRows -= 1;
    }
    else if ("fastToolbar".equals(objMethod)) {
      this.showHeight = String.valueOf(tempHeight - 160);
      this.displayRows = ((tempHeight - 160 - 53) / 20);

      if (tempHeight - 160 - 53 - this.displayRows * 20 < 19)
        this.displayRows -= 1;
    }
    else {
      this.showHeight = String.valueOf(tempHeight - 170);
      this.displayRows = ((tempHeight - 170 - 53) / 20);

      if (tempHeight - 170 - 53 - this.displayRows * 20 < 19)
        this.displayRows -= 1;
    }
  }

  public class DataProvider
    implements BOSQueryDelegate
  {
    public DataProvider()
    {
    }

    public IRowSet execute(BOSQueryDataSource ds)
    {
      String str = ds.getID();
      IRowSet rowSet = null;
      Context ctx = WafContext.getInstance().getContext();
      int[] rows = BCBaseListBean.this.grid.getSelections();
      if ((rows == null) || (rows.length == 0)) {
        return null;
      }

      try
      {
        if ("bill".equals(str)) {
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getLocalInstance(ctx);

          rowSet = ibizForPrint.getRowset(BCBaseListBean.this.getSelectedBillIds(), BCBaseListBean.this.getReportQueryName());

          rowSet = BCBaseListBean.this.parsePrintValue(rowSet);
          return rowSet;
        }if ("r1bill".equals(str))
        {
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getLocalInstance(ctx);

          rowSet = ibizForPrint.getRowset(BCBaseListBean.this.getSelectedBillIds(), BCBaseListBean.this.getR1ReportBillQueryName());

          rowSet = BCBaseListBean.this.updateBillReprotRowSet(rowSet);
          return rowSet;
        }if ("approveInfo".equals(str)) {
          IBillPrintFacade billPrintFacade = BillPrintFacadeFactory.getLocalInstance(ctx);

          ArrayList params = ds.getParams();
          FilterInfo filterInfo = new FilterInfo();
          int size = params.size();

          for (int i = 0; i < size; i++) {
            DSParam dsParam = (DSParam)params.get(i);
            String paramName = dsParam.getColName();

            if ((paramName.equals("billId")) || (paramName.equals("billID"))) {
              Variant value = dsParam.getValue();
              if ((value != null) && (!"".equals(value.toString()))) {
                filterInfo.getFilterItems().add(new FilterItemInfo("billId", value.toString()));
              }
            }

            if (paramName.equals("isPass")) {
              Variant value = dsParam.getValue();
              if ((value != null) && (!"".equals(value.toString()))) {
                filterInfo.getFilterItems().add(new FilterItemInfo("isPass", value.toString().toLowerCase()));
              }
            }

          }

          int filterSize = filterInfo.getFilterItems().size();
          if (filterSize > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < filterSize - 1; i++) {
              sb.append("#" + i + " and ");
            }
            sb.append("#" + (filterSize - 1));
            filterInfo.setMaskString(sb.toString());
          }
          return billPrintFacade.queryApproveInfo(filterInfo);
        }
        if ("r1entry".equals(str))
        {
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getLocalInstance(ctx);

          Variant v = ((DSParam)ds.getParams().get(0)).getValue();
          rowSet = ibizForPrint.getEntryRowSet(v.toString(), BCBaseListBean.this.getR1ReportEntryQueryName());

          rowSet = BCBaseListBean.this.updateEntryReprotRowSet(rowSet);
          return rowSet;
        }
        if ("entrysum".equals(str))
        {
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getLocalInstance(ctx);

          Variant v = ((DSParam)ds.getParams().get(0)).getValue();
          rowSet = ibizForPrint.getEntryRowSet(v.toString(), BCBaseListBean.this.getR1ReportEntrySumQueryName());

          return rowSet;
        }
      } catch (Exception e) {
        e.printStackTrace();
        BCBaseListBean.this.logger.error("error occur in the innerClass \"DataProvider\" Of the class \"BCBaseListBean\" : \n " + e.getCause());
      }
      return null;
    }
  }
}