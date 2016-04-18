package com.kingdee.eas.cp.bc.web;

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
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.operamasks.faces.annotation.Accessible;
import org.operamasks.faces.component.form.impl.UIField;
import org.operamasks.faces.component.form.impl.UITextField;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.waf.action.ActionManager;
import com.kingdee.bos.waf.action.WebAction;
import com.kingdee.bos.waf.action.WebActionFactory;
import com.kingdee.bos.waf.ctx.WafContext;
import com.kingdee.bos.waf.resource.Resources;
import com.kingdee.bos.waf.rpt.IReportBean;
import com.kingdee.bos.waf.util.BeanUtil;
import com.kingdee.bos.waf.util.OperateState;
import com.kingdee.bos.waf.util.Utils;
import com.kingdee.bos.waf.winlet.UIWinStyle;
import com.kingdee.bos.waf.winlet.edit.EditBean;
import com.kingdee.bos.web.components.widget.impl.UIPromptBox;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.permission.OrgRangeIncludeSubOrgFactory;
import com.kingdee.eas.base.permission.OrgRangeType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.AmountControlTypeEnum;
import com.kingdee.eas.cp.bc.BillPrintFacadeFactory;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollCoreBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.CommonUtilFacadeFactory;
import com.kingdee.eas.cp.bc.ExpenseCommenFacadeFactory;
import com.kingdee.eas.cp.bc.ForPrintFacadeFactory;
import com.kingdee.eas.cp.bc.IBillPrintFacade;
import com.kingdee.eas.cp.bc.IForPrintFacade;
import com.kingdee.eas.cp.bc.PriorEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.print.MultiDataSourceProviderProxy;
import com.kingdee.eas.framework.print.MultiapprovePrintUtility;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.IntEnum;

public abstract class BCBaseEditBean extends EditBean
{
  protected Map paramMap = null;
  private String autoSetAmountApproved = "false";

  @Accessible
  private ViewLoanReturnAction viewLoanReturnAction;

  @Accessible
  private ViewBudgetAction viewBudgetAction;

  @Accessible
  private ViewWorkFlowAction viewWorkFlowAction;

  @Accessible
  private ViewAuditResultAction viewAuditResultAction;

  @Accessible
  protected TraceUpAction traceUpAction;

  @Accessible
  protected TraceDownAction traceDownAction;
  protected static final String STATUS_FINDVIEW = "FINDVIEW";
  protected static final String STATUS_AMOUNTAPPROVED = "amountApproved";

  @Accessible
  public UITextField ExpenseTypeField;
  protected String costDeptDisable = "false";
  private boolean isDelimitOrgScope = false;
  private int count = 0;
  private int countEx = 0;

  private boolean isFromCostedDeptChange = false;
  private boolean isFirstLoad = false;

  public void pageOnload()
  {
    this.isFirstLoad = true;
    super.pageOnload();
    setParamMap();
    verifyCurrentInfo();

    BizCollCoreBillBaseInfo info = (BizCollCoreBillBaseInfo)this.model;
    setState(info.getState());
    lockComponent();
  }

  private void setParamMap()
  {
    Context ctx = WafContext.getInstance().getContext();
    List paramList = new ArrayList();
    paramList.add("CP001");
    paramList.add("CP002");
    paramList.add("CP016");
    paramList.add("CP019");
    try {
      this.paramMap = ExpenseCommenFacadeFactory.getLocalInstance(ctx).getParams(paramList);

      this.isDelimitOrgScope = (this.paramMap.get("CP019") == null ? false : new Boolean(this.paramMap.get("CP019").toString()).booleanValue());

      ctx.put("cp019", Boolean.valueOf(this.isDelimitOrgScope));
    }
    catch (EASBizException e) {
      e.printStackTrace();
    } catch (BOSException e) {
      e.printStackTrace();
    }
  }

  protected void setCodingNumber()
  {
    if (OperateState.ADDNEW.equals(getOperateState()))
    {
      boolean isCUAttributeExist = false;
      if (((BizCollCoreBillBaseInfo)this.model).getCU() != null) {
        isCUAttributeExist = true;
      }
      else {
        Context ctx = WafContext.getInstance().getContext();
        ((BizCollCoreBillBaseInfo)this.model).setCU(ContextUtil.getCurrentCtrlUnit(ctx));
      }
      super.setCodingNumber();
      if (!isCUAttributeExist)
        ((BizCollCoreBillBaseInfo)this.model).setCU(null);
    }
  }

  private void verifyCurrentInfo()
  {
    Context ctx = WafContext.getInstance().getContext();
    UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
    if ((user != null) && 
      (user.getType() != null) && (user.getType() != UserType.PERSON)) {
      throw new FacesException(new BizCollException(BizCollException.USERTYPE_IS_WRONG));
    }

    CompanyOrgUnitInfo currentCompany = ContextUtil.getCurrentFIUnit(ctx);

    if ((OperateState.ADDNEW.equals(this.operateState)) && (
      (currentCompany == null) || (currentCompany.isIsOnlyUnion())))
      throw new FacesException(new BizCollException(BizCollException.COMPANY_IS_UNION));
  }

  public void submitModel()
  {
    boolean state = (StateEnum.NEW.equals(((BizCollBillBaseInfo)this.model).getState())) || (StateEnum.DRAFT.equals(((BizCollBillBaseInfo)this.model).getState())) || (StateEnum.CHECKFAILD.equals(((BizCollBillBaseInfo)this.model).getState())) || (StateEnum.ALREADYABANDON.equals(((BizCollBillBaseInfo)this.model).getState()));

    if (state) {
      ((BizCollBillBaseInfo)this.model).setState(StateEnum.SUBMIT);
    }
    super.submitModel();
  }

  protected boolean verifyModel() {
    boolean isPass = true;
    isPass = super.verifyModel();
    if (isPass) {
      if ((((BizCollBillBaseInfo)this.model).getNumber() == null) || (((BizCollBillBaseInfo)this.model).getNumber().equals("")))
      {
        if (isRequiredByState("number-required")) {
          addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "number_not_null"));

          return false;
        }
      }
      if (((BizCollBillBaseInfo)this.model).getApplierCompany() == null) {
        addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "applier_com_null"));

        return false;
      }
      if (!BillUtil.isBizUnitCompany(((BizCollBillBaseInfo)this.model).getApplierCompany().getId().toString()))
      {
        addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "applier_com_not_biz"));

        return false;
      }
      if (((BizCollBillBaseInfo)this.model).getApplier() == null) {
        addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "applier_not_null"));

        return false;
      }
      if (((BizCollBillBaseInfo)this.model).getCostedDept() == null) {
        addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "costdept_not_null"));

        return false;
      }
      if (!BillUtil.isBizUnitCostCenter(((BizCollBillBaseInfo)this.model).getCostedDept().getId().toString()))
      {
        addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "costdept_not_biz"));

        return false;
      }
      if (((BizCollBillBaseInfo)this.model).getCompany() == null) {
        addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "cost_com_not_null"));

        return false;
      }
      if (!BillUtil.isBizUnitCompany(((BizCollBillBaseInfo)this.model).getCompany().getId().toString()))
      {
        addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "cost_com_not_biz"));

        return false;
      }
      if (((BizCollBillBaseInfo)this.model).getCurrencyType() == null) {
        addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "currency_not_null"));

        return false;
      }
    }
    return isPass;
  }

  public OrgUnitInfo getMainOrgInfo()
  {
    Context ctx = WafContext.getInstance().getContext();
    return ContextUtil.getCurrentFIUnit(ctx);
  }

  public boolean isRenderByState(String param)
  {
    boolean isRender = true;
    Map componentRenderMap = new HashMap();
    boolean notShowAmountBalance = (StateEnum.NEW.equals(((BizCollBillBaseInfo)this.model).getState())) || (StateEnum.DRAFT.equals(((BizCollBillBaseInfo)this.model).getState())) || (StateEnum.SUBMIT.equals(((BizCollBillBaseInfo)this.model).getState())) || (StateEnum.CHECKING.equals(((BizCollBillBaseInfo)this.model).getState()));

    if (notShowAmountBalance) {
      componentRenderMap.put("amountBalance", Boolean.valueOf(false));
      componentRenderMap.put("amountUsed", Boolean.valueOf(false));
    }
    boolean isNeedBudget = this.paramMap.get("CP001") == null ? false : new Boolean(this.paramMap.get("CP001").toString()).booleanValue();

    if (!isNeedBudget) {
      componentRenderMap.put("btn-viewBudget", Boolean.valueOf(false));
    }
    isRender = componentRenderMap.get(param) == null ? true : ((Boolean)componentRenderMap.get(param)).booleanValue();

    return isRender;
  }

  public boolean isHiddenByState(String param) {
    boolean isRender = false;
    Map componentRenderMap = new HashMap();
    boolean notShowAmountBalance = (this.model == null) || (((BizCollBillBaseInfo)this.model).getState() == null) || (StateEnum.NEW.equals(((BizCollBillBaseInfo)this.model).getState())) || (StateEnum.DRAFT.equals(((BizCollBillBaseInfo)this.model).getState())) || (StateEnum.SUBMIT.equals(((BizCollBillBaseInfo)this.model).getState())) || (StateEnum.CHECKING.equals(((BizCollBillBaseInfo)this.model).getState()));

    if (notShowAmountBalance) {
      componentRenderMap.put("amountBalance", Boolean.valueOf(true));
      componentRenderMap.put("amountUsed", Boolean.valueOf(true));
    }
    isRender = componentRenderMap.get(param) == null ? false : ((Boolean)componentRenderMap.get(param)).booleanValue();

    return isRender;
  }

  public boolean isDisabledByState(String param) {
    boolean isDisabled = true;
    Map componentRenderMap = new HashMap();
    try {
      if ((this.ruleStatus != null) && (!this.ruleStatus.isAddView())) {
        componentRenderMap.put("number", Boolean.valueOf(true));
      } else {
        componentRenderMap.put("number", Boolean.valueOf(false));
        if ((this.ruleStatus != null) && (!this.ruleStatus.isModifiable())) {
          componentRenderMap.put("number", Boolean.valueOf(true));
        }
      }
      if ((this.ruleStatus != null) && (this.ruleStatus.isUseIntermitNumber()))
        componentRenderMap.put("number-required", Boolean.valueOf(false));
      else {
        componentRenderMap.put("number-required", Boolean.valueOf(true));
      }
      if ((this.operateState == null) || (this.operateState.equals(OperateState.VIEW)) || (this.operateState.equals("amountApproved")))
      {
        componentRenderMap.put("entryRowButton", Boolean.valueOf(true));
      }
      else componentRenderMap.put("entryRowButton", Boolean.valueOf(false));
    }
    catch (CodingRuleException e)
    {
      e.printStackTrace();
    }
    isDisabled = componentRenderMap.get(param) == null ? true : ((Boolean)componentRenderMap.get(param)).booleanValue();

    return isDisabled;
  }

  public boolean isRequiredByState(String param) {
    boolean isDisabled = true;
    Map componentRenderMap = new HashMap();

    if (this.ruleStatus != null)
    {
      componentRenderMap.put("number-required", Boolean.valueOf(false));
    }
    else componentRenderMap.put("number-required", Boolean.valueOf(true));

    isDisabled = componentRenderMap.get(param) == null ? true : ((Boolean)componentRenderMap.get(param)).booleanValue();

    return isDisabled;
  }

  protected void setActionState() {
    ActionManager am = getActionManager();
    if (this.operateState == null) {
      Iterator iter = am.getActionSet().values().iterator();
      while (iter.hasNext()) {
        WebAction action = (WebAction)iter.next();
        if (!isInEnabledList(getEnabledActionName(), action.getName()))
        {
          action.setEnabled(false);
        }

      }

    }
    else if ((this.operateState.equals(OperateState.ADDNEW)) || (this.operateState.equals(OperateState.EDIT)) || (this.operateState.equals(OperateState.COPYADDNEW)) || (this.operateState.equals("amountApproved")))
    {
      Iterator iter = am.getActionSet().values().iterator();
      while (iter.hasNext()) {
        WebAction action = (WebAction)iter.next();
        if (!isInEnabledList(getEnabledActionName(), action.getName()))
        {
          action.setEnabled(true);
        }
      }
    }
    else {
      Iterator iter = am.getActionSet().values().iterator();
      while (iter.hasNext()) {
        WebAction action = (WebAction)iter.next();
        if (!isInEnabledList(getEnabledActionName(), action.getName()))
        {
          action.setEnabled(false);
        }
      }
    }

    this.attachmentMgrAction.setEnabled(true);
    if ((this.operateState.equals(OperateState.ADDNEW)) || (this.operateState.equals(OperateState.EDIT)) || (this.operateState.equals(OperateState.COPYADDNEW)))
    {
      this.reportAction.setEnabled(false);
    }
    else this.reportAction.setEnabled(true);
  }

  protected void setState(StateEnum state)
  {
    if (state == null) {
      this.saveAction.setEnabled(false);
      this.submitAction.setEnabled(false);
      this.deleteAction.setEnabled(false);
      this.copyNewAction.setEnabled(false);
    }
    if (OperateState.VIEW.equals(this.operateState)) {
      this.reportAction.setEnabled(true);
    }
    else if (OperateState.ADDNEW.equals(this.operateState)) {
      this.reportAction.setEnabled(false);
    } else if (OperateState.EDIT.equals(this.operateState)) {
      this.reportAction.setEnabled(false);
      this.saveAction.setEnabled(canModify(state));
    }
    else if (OperateState.COPYADDNEW.equals(this.operateState)) {
      this.reportAction.setEnabled(false);
    }
    else if ("amountApproved".equals(this.operateState)) {
      this.saveAction.setEnabled(false);
    }
  }

  protected boolean canModify(StateEnum state)
  {
    if (state == null) {
      return false;
    }
    boolean b=(StateEnum.DRAFT.equals(state)) || (StateEnum.NEW.equals(state)) || (state.getValue() == 27);
    logger.info(WafContext.getSessionContext());
    if(StateEnum.SUBMIT.equals(state)){
    	return true;
    }
    return b;
  }

  protected String getReportURL()
  {
    return "loanReturnRecordReport.jsf";
  }

  protected void updateModel()
  {
    if (((BizCollBillBaseInfo)this.model).getCreator() == null)
      ((BizCollBillBaseInfo)this.model).setCreator(ContextUtil.getCurrentUserInfo(WafContext.getInstance().getContext()));
  }

  public void saveModel()
  {
    BOSUuid id = null;

    if (this.model.getId() != null) {
      id = this.model.getId();
      BizCollBillBaseInfo billInfo = null;
      boolean isExisted = false;
      Context ctx = WafContext.getInstance().getContext();
      IDynamicObject iDynamicObject = null;
      iDynamicObject = DynamicObjectFactory.getLocalInstance(ctx);
      try {
        isExisted = iDynamicObject.exists(id.getType(), new ObjectUuidPK(id));
      }
      catch (BOSException e)
      {
        this.logger.error("exist BizCollBillBaseInfo Error at method \"saveModle\" of class BCBaseEditBean ", e);
      }

      if (isExisted) {
        try {
          billInfo = (BizCollBillBaseInfo)iDynamicObject.getValue(id.getType(), new ObjectUuidPK(id), getSateSelector());

          StateEnum state = billInfo.getState();
          if (state != null) {
            ((BizCollBillBaseInfo)this.model).setState(state);
          }

        }
        catch (BOSException e)
        {
          this.logger.error("Getting BizCollBillBaseInfo Error at method \"saveModle\" of class BCBaseEditBean ", e);
        }

      }
      else
      {
        ((BizCollBillBaseInfo)this.model).setState(StateEnum.DRAFT);
      }
    } else {
      ((BizCollBillBaseInfo)this.model).setState(StateEnum.DRAFT);
    }
    super.saveModel();
  }

  private SelectorItemCollection getSateSelector() {
    SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("id"));
    sic.add(new SelectorItemInfo("state"));
    return sic;
  }

  public void lockComponent() {
    if (this.operateState.equals("amountApproved")) {
      List list = WafContext.getInstance().getFacesContext().getViewRoot().getChildren();

      lockComponentApprovedAmount(list);
    }
    if (((BizCollBillBaseInfo)this.model).getSourceBillId() != null) {
      UIField component = (UIField)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:amountControlType");

      if (component != null) {
        component.setDisabled(Boolean.valueOf(true));
      }
    }
    if (this.isDelimitOrgScope)
      if ((((BizCollBillBaseInfo)this.model).getCostedDept() == null) && (((BizCollBillBaseInfo)this.model).getCompany() == null))
      {
        this.costDeptDisable = "true";
      }
      else this.costDeptDisable = "false";
  }

  public void lockComponentApprovedAmount(List componentList)
  {
    Iterator ite = componentList.iterator();
    UIComponent component = null;
    while (ite.hasNext()) {
      component = (UIComponent)ite.next();
      if ((component instanceof UIField)) {
        if (unLockCompontList().contains(((UIField)component).getId()))
          continue;
        ((UIField)component).setDisabled(Boolean.valueOf(true));
        continue;
      }if (component.getChildCount() > 0)
        lockComponentApprovedAmount(component.getChildren());
    }
  }

  public List<String> unLockCompontList()
  {
    List list = new ArrayList();
    list.add("payMode");
    list.add("description");
    list.add("amountApprovedEditor");
    return list;
  }

  protected void viewBudget() throws Exception {
    updateModel();
    BgBalanceViewBean viewBean = (BgBalanceViewBean)BeanUtil.getBean("cp.bc.BgBalanceViewBean");

    if (viewBean != null) {
      viewBean.setBillInfo((BizCollBillBaseInfo)this.model);
    }
    UIWinStyle winStyle = new UIWinStyle();
    winStyle.setScroll("yes");
    winStyle.setWidth(Integer.valueOf(890));
    winStyle.setTitle(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "viewBudget"));

    FacesContext context = FacesContext.getCurrentInstance();
    String navUrl = context.getApplication().getViewHandler().getResourceURL(context, "bgBalanceView.jsf");

    this.outerScript = ("KDWin.open('" + navUrl + "', " + Utils.getEditorOptions(winStyle) + ")");
  }

  protected void viewLoanAndReturnRecord() throws Exception
  {
    Context ctx = WafContext.getInstance().getContext();
    String applierId = null;
    Date currentTime = null;
    if (this.model != null) {
      if (this.model.get("createTime") != null)
        currentTime = (Date)this.model.get("createTime");
      else {
        currentTime = new Date();
      }
      if (this.model.get("applier") != null) {
        applierId = ((PersonInfo)this.model.get("applier")).getId().toString();
      }
      else
        applierId = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
    }
    else
    {
      currentTime = new Date();
    }
    LoanReturnRecordReportBean viewBean = (LoanReturnRecordReportBean)BeanUtil.getBean("cp.bc.LoanReturnRecordReportBean");

    if (viewBean != null) {
      viewBean.setCurrentTime(currentTime);
      viewBean.setApplierId(applierId);
    }

    UIWinStyle winStyle = new UIWinStyle();
    winStyle.setWidth(Integer.valueOf(890));
    winStyle.setScroll("yes");
    winStyle.setTitle(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "viewLoanReturn"));

    FacesContext context = FacesContext.getCurrentInstance();
    String navUrl = context.getApplication().getViewHandler().getResourceURL(context, getReportURL());

    this.outerScript = ("KDWin.open('" + navUrl + "', " + Utils.getEditorOptions(winStyle) + ")");
  }

  public BCBaseEditBean()
  {
    this.viewLoanReturnAction = ((ViewLoanReturnAction)WebActionFactory.createWebAction("viewLoanReturnAction", ViewLoanReturnAction.class, getServiceContext()));

    this.viewLoanReturnAction.setBaseBean(this);
    this.viewBudgetAction = ((ViewBudgetAction)WebActionFactory.createWebAction("viewBudgetAction", ViewBudgetAction.class, getServiceContext()));

    this.viewBudgetAction.setBaseBean(this);
    this.viewWorkFlowAction = ((ViewWorkFlowAction)WebActionFactory.createWebAction("viewWorkFlowAction", ViewWorkFlowAction.class, getServiceContext()));

    this.viewWorkFlowAction.setBaseBean(this);
    this.viewAuditResultAction = ((ViewAuditResultAction)WebActionFactory.createWebAction("viewAuditResultAction", ViewAuditResultAction.class, getServiceContext()));

    this.viewAuditResultAction.setBaseBean(this);
    this.traceUpAction = ((TraceUpAction)WebActionFactory.createWebAction("traceUpAction", TraceUpAction.class, getServiceContext()));

    this.traceUpAction.setBaseBean(this);
    this.traceDownAction = ((TraceDownAction)WebActionFactory.createWebAction("traceDownAction", TraceDownAction.class, getServiceContext()));

    this.traceDownAction.setBaseBean(this);
  }

  protected boolean isInEnabledList(List list, String name)
  {
    if (list == null) {
      return false;
    }
    String tmp = null;
    int i = 0; for (int size = list.size(); i < size; i++) {
      tmp = list.get(i).toString();
      if (tmp.equals(name)) {
        return true;
      }
    }

    return false;
  }

  protected List getEnabledActionName()
  {
    ArrayList list = new ArrayList();
    list.add("viewLoanReturnAction");
    list.add("viewBudgetAction");
    list.add("viewWorkFlowAction");
    list.add("viewAuditResultAction");
    list.add("traceUpAction");
    list.add("traceDownAction");
    list.add("reportAction");
    return list;
  }

  public boolean isAmountControlType() {
    return (this.model == null) || (AmountControlTypeEnum.oneTOmore.equals(((BizCollBillBaseInfo)this.model).getAmountControlType()));
  }

  public void setAmountControlType(boolean amountControlType)
  {
    if (amountControlType) {
      ((BizCollBillBaseInfo)this.model).setAmountControlType(AmountControlTypeEnum.oneTOmore);
    }
    else
      ((BizCollBillBaseInfo)this.model).setAmountControlType(AmountControlTypeEnum.oneTOone);
  }

  public void f7DataChanged(String param)
    throws Exception
  {
    Context ctx = WafContext.getInstance().getContext();
    try {
      if ((param != null) && ("applier".equals(param))) {
        String[] id = (String[])(String[])WafContext.getInstance().getRequest().getParameterMap().get("editForm:applier_value");

        if ((id != null) && (id[0] != null) && (!"".equals(id[0]))) {
          UIPromptBox f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:position");

          f7.setSubmittedValue(null);
          String[] orgId = (String[])(String[])WafContext.getInstance().getRequest().getParameterMap().get("editForm:orgUnit_value");

          PersonInfo personInfo = PersonFactory.getLocalInstance(ctx).getPersonInfo(new ObjectUuidPK(id[0]));

          if ((orgId != null) && (orgId[0] != null) && (!"".equals(orgId[0])))
          {
            BizCollBillBaseInfo info = CommonUtilFacadeFactory.getLocalInstance(ctx).forLoanBillApplierActionByOrgUnitId(personInfo, orgId[0]);

            ((BizCollBillBaseInfo)this.model).setPosition(info.getPosition());

            f7.setFilterInfo(getPositionFilter(id[0]));
            f7.setValue(info.getPosition());
            f7.repaint();

            return;
          }
          BizCollBillBaseInfo info = CommonUtilFacadeFactory.getLocalInstance(ctx).forLoanBillApplierAction(personInfo);

          f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:orgUnit");

          f7.setSubmittedValue(null);
          f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:costedDept");

          f7.setSubmittedValue(null);
          f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:company");

          f7.setSubmittedValue(null);

          ((BizCollBillBaseInfo)this.model).setPosition(info.getPosition());

          ((BizCollBillBaseInfo)this.model).setOrgUnit(info.getOrgUnit());
          ((BizCollBillBaseInfo)this.model).setCostedDept(info.getCostedDept());

          ((BizCollBillBaseInfo)this.model).setCompany(info.getCompany());

          f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:position");

          f7.setFilterInfo(getPositionFilter(id[0]));
          f7.setValue(info.getPosition());
          f7.repaint();
        }
      }
      else if ((param != null) && ("orgUnit".equals(param))) {
        String[] id = (String[])(String[])WafContext.getInstance().getRequest().getParameterMap().get("editForm:orgUnit_value");

        if ((id != null) && (id[0] != null) && (!"".equals(id[0])))
        {
          UIPromptBox f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:costedDept");

          f7.setSubmittedValue(null);
          f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:company");

          f7.setSubmittedValue(null);
          AdminOrgUnitInfo adminInfo = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(id[0]));

          BizCollBillBaseInfo info = CommonUtilFacadeFactory.getLocalInstance(ctx).forBillAdminOrgUnitAction(adminInfo);

          ((BizCollBillBaseInfo)this.model).setCostedDept(info.getCostedDept());

          ((BizCollBillBaseInfo)this.model).setCompany(info.getCompany());
          String[] personId = (String[])(String[])WafContext.getInstance().getRequest().getParameterMap().get("editForm:applier_value");

          UIPromptBox fApplier = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:applier");

          UIPromptBox positionf7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:position");

          if (BillUtil.isPersonInOrgUnit(personId[0], id[0])) {
            PersonInfo personInfo = PersonFactory.getLocalInstance(ctx).getPersonInfo(new ObjectUuidPK(personId[0]));

            fApplier.setValue(personInfo);
            fApplier.repaint();
            info = CommonUtilFacadeFactory.getLocalInstance(ctx).forLoanBillApplierActionByOrgUnitId(personInfo, id[0]);

            ((BizCollBillBaseInfo)this.model).setPosition(info.getPosition());

            positionf7.setFilterInfo(getPositionFilter(personId[0]));
            positionf7.setValue(info.getPosition());
            positionf7.repaint();
          } else {
            fApplier.setSubmittedValue(null);
            fApplier.setValue(null);
            ((BizCollBillBaseInfo)this.model).setApplier(null);
            fApplier.repaint();

            positionf7.setSubmittedValue(null);
            positionf7.setFilterInfo(null);
            positionf7.setValue(null);
            ((BizCollBillBaseInfo)this.model).setPosition(null);
            positionf7.repaint();
          }
        }
        else if ((id == null) || ("".equals(id[0]))) {
          ((BizCollBillBaseInfo)this.model).setOrgUnit(null);
          UIPromptBox fApplier = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:applier");

          fApplier.setFilterInfo(getApplierFilter());
          fApplier.repaint();
        }
      } else if ((param != null) && ("costedDept".equals(param))) {
        String[] id = (String[])(String[])WafContext.getInstance().getRequest().getParameterMap().get("editForm:costedDept_value");

        if ((id != null) && (id[0] != null) && (!"".equals(id[0]))) {
          UIPromptBox f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:company");

          f7.setSubmittedValue(null);
          CostCenterOrgUnitInfo costCenterInfo = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitInfo(new ObjectUuidPK(id[0]));

          BizCollBillBaseInfo info = CommonUtilFacadeFactory.getLocalInstance(ctx).forLoanBillCostCenterAction(costCenterInfo);

          ((BizCollBillBaseInfo)this.model).setCostedDept(costCenterInfo);
          ((BizCollBillBaseInfo)this.model).setCompany(info.getCompany());
          this.isFromCostedDeptChange = true;
        }
      } else if ((param != null) && ("operationType".equals(param))) {
        String[] id = (String[])(String[])WafContext.getInstance().getRequest().getParameterMap().get("editForm:operationType_value");

        if ((id != null) && (id[0] != null) && (!"".equals(id[0]))) {
          UIPromptBox f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:editGrid:expenseTypeEditor");

          f7.setFilterInfo(getExpenseTypeFilterByOpertionType(id[0]));
        }
      }
      else if ((param != null) && ("company".equals(param))) {
        String[] id = (String[])(String[])WafContext.getInstance().getRequest().getParameterMap().get("editForm:company_value");

        if ((id != null) && (id[0] != null) && (!"".equals(id[0]))) {
          UIPromptBox f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:costedDept");

          ((BizCollBillBaseInfo)this.model).setApplier(null);
          f7.setSubmittedValue(null);
          f7.setFilterInfo(getCostCenterByCompanyFilter(id[0]));
          if (this.isFromCostedDeptChange) {
            String[] costedDeptId = (String[])(String[])WafContext.getInstance().getRequest().getParameterMap().get("editForm:costedDept_value");

            if ((costedDeptId != null) && (costedDeptId[0] != null) && (!"".equals(costedDeptId[0])))
            {
              CostCenterOrgUnitInfo costCenterInfo = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitInfo(new ObjectUuidPK(costedDeptId[0]));

              f7.setValue(costCenterInfo);
            }
            this.isFromCostedDeptChange = false;
          } else {
            ((BizCollBillBaseInfo)this.model).setCostedDept(null);
          }
          f7.repaint();
        } else if (((id == null) || ("".equals(id[0]))) && 
          (this.isDelimitOrgScope)) {
          UIPromptBox f7 = (UIPromptBox)WafContext.getInstance().getFacesContext().getViewRoot().findComponent("editForm:costedDept");

          f7.setSubmittedValue(null);
          ((BizCollBillBaseInfo)this.model).setCostedDept(null);
          this.costDeptDisable = "true";
          f7.repaint();
        }
      }
    }
    catch (EASBizException e) {
      e.printStackTrace();
    } catch (BOSException e) {
      e.printStackTrace();
    }
  }

  public String f7FilterInfo(String param) {
    Context ctx = WafContext.getInstance().getContext();
    String filter = "";
    Map paramMap = new HashMap();
    if ((param != null) && ("expenseType".equals(param)))
    {
      String[] id = (String[])(String[])WafContext.getInstance().getRequest().getParameterMap().get("editForm:company_value");

      String companyId = "";
      if ((this.isFirstLoad) && (((BizCollBillBaseInfo)this.model).getCompany() != null))
      {
        companyId = ((BizCollBillBaseInfo)this.model).getCompany().getId().toString();

        this.isFirstLoad = false;
      } else if ((id != null) && (id[0] != null) && (!"".equals(id[0]))) {
        companyId = id[0];
      }
      paramMap.put("companyId", companyId);
      return MakeControlUtil.makeExpenseTypeFilterInfo(ctx, paramMap);
    }

    return filter;
  }

  public String getCostCenterByCompanyFilter(String companyId)
  {
    String filterString = null;
    Context ctx = WafContext.getInstance().getContext();
    ctx.put("cp019", Boolean.valueOf(this.isDelimitOrgScope));
    if ((companyId != null) && (!"".equals(companyId))) {
      setCostDeptDisable("false");
    }
    else if ((this.model != null) && (((BizCollBillBaseInfo)this.model).getCostedDept() == null)) {
      setCostDeptDisable("true");
    }
    filterString = MakeControlUtil.makeCostCenterF7FilterInfo(ctx, companyId);
    return filterString;
  }

  public String getApplierFilter() {
    if ((this.model != null) && (((BizCollBillBaseInfo)this.model).getOrgUnit() != null))
    {
      String OrgUnitId = ((BizCollBillBaseInfo)this.model).getOrgUnit().getId().toString();

      return getApplierByOrgUnitFilter(OrgUnitId);
    }

    OrgUnitCollection unitColl = null;
    FilterInfo filter = new FilterInfo();
    FilterItemCollection fic = filter.getFilterItems();
    try {
      Context ctx = WafContext.getInstance().getContext();
      unitColl = OrgUnitRelationFactory.getLocalInstance(ctx).getFromUnit(ContextUtil.getCurrentFIUnit(ctx).getId().toString(), 1, 0);

      int size = 0;
      if (unitColl != null) {
        size = unitColl.size();
      }

      if (size > 0) {
        Set idSet = new HashSet();
        for (int i = 0; i < size; i++) {
          if (!((AdminOrgUnitInfo)unitColl.get(i)).isIsSealUp())
            idSet.add(unitColl.get(i).getId().toString());
        }
        fic.add(new FilterItemInfo("AdminOrgUnit.id", idSet, CompareType.INCLUDE));

        return fic.toString();
      }
    } catch (EASBizException e) {
      e.printStackTrace();
    } catch (BOSException e) {
      e.printStackTrace();
    }
    return "";
  }

  public String getPositionFilter(String personId)
  {
    String strFilter = "";
    if ((null != personId) && (!"".equals(personId))) {
      FilterInfo filter = new FilterInfo();
      filter.getFilterItems().add(new FilterItemInfo("Person.id", personId, CompareType.EQUALS));
      strFilter = filter.toString();
    }
    return strFilter;
  }

  public String getApplierByOrgUnitFilter(String OrgUnitId)
  {
    String filterString = null;
    if ((OrgUnitId != null) && (!"".equals(OrgUnitId))) {
      FilterInfo filter = new FilterInfo();
      FilterItemCollection fic = filter.getFilterItems();
      fic.add(new FilterItemInfo("AdminOrgUnit.id", OrgUnitId, CompareType.EQUALS));

      filterString = filter.toString();
    }
    return filterString;
  }

  public String f7CostCenterFilterInfo() {
    if ((this.model != null) && (((BizCollBillBaseInfo)this.model).getCompany() != null)) {
      String companyId = ((BizCollBillBaseInfo)this.model).getCompany().getId().toString();

      return getCostCenterByCompanyFilter(companyId);
    }
    return getCostCenterByCompanyFilter(null);
  }

  public String f7ViewUrl(String param)
  {
    String viewUrl = "";
    if (param != null) {
      if ("company".equals(param)) {
        if (this.isDelimitOrgScope)
          viewUrl = "bCKDCommonPromptDialog.jsf?var={f7ViewType:company,isCP019:true}";
        else
          viewUrl = "bCKDCommonPromptDialog.jsf?var={f7ViewType:company}";
      }
      else if ("orgUnit".equals(param)) {
        if (this.isDelimitOrgScope)
          viewUrl = "bCKDCommonPromptDialog.jsf?var={f7ViewType:orgUnit,isCP019:true}";
        else
          viewUrl = "bCKDCommonPromptDialog.jsf?var={f7ViewType:orgUnit}";
      }
      else if ("applier".equals(param))
      {
        String orgUnitId = getComponentValue("editForm:orgUnit_value");

        if ((orgUnitId == null) || ("".equals(orgUnitId)))
        {
          this.count += 1;
          if (this.count == 2) {
            if (this.model == null) {
              return "";
            }
            OrgUnitInfo orgUnitInfo = ((BizCollBillBaseInfo)this.model).getOrgUnit();

            if ((orgUnitInfo != null) && (orgUnitInfo.getId() != null)) {
              orgUnitId = orgUnitInfo.getId().toString();
              this.count = 0;
            }
          }
        }
        if (!"".equals(orgUnitId)) {
          viewUrl = "bCKDCommonPromptDialog.jsf?var={f7ViewType:applier}&orgUnitId=" + BillUtil.formatIdUrl(orgUnitId);
        }
        else {
          viewUrl = "bCKDCommonPromptDialog.jsf?var={f7ViewType:applier}";
        }
      }
      else if ("costedDept".equals(param)) {
        if (this.isDelimitOrgScope)
          viewUrl = "bCKDCommonPromptDialog.jsf?var={f7ViewType:costedDept,isCP019:true}";
        else
          viewUrl = "bCKDCommonPromptDialog.jsf?var={f7ViewType:costedDept}";
      }
      else if ("position".equals(param)) {
        String personId = getComponentValue("editForm:applier_value");
        if ((personId == null) || ("".equals(personId)))
        {
          this.countEx += 1;
          if (this.countEx == 2) {
            if (this.model == null) {
              return "";
            }
            PersonInfo personInfo = ((BizCollBillBaseInfo)this.model).getApplier();
            if ((personInfo != null) && (personInfo.getId() != null)) {
              personId = personInfo.getId().toString();
              this.countEx = 0;
            }
          }
        }

        if (!"".equals(personId)) {
          viewUrl = "bCKDCommonPromptDialog.jsf?var={f7ViewType:position}&personId=" + BillUtil.formatIdUrl(personId);
        }
        else {
          viewUrl = "bCKDCommonPromptDialog.jsf?var={f7ViewType:position}";
        }
      }
    }
    return viewUrl;
  }

  public String f7CompanyFilterInfo()
  {
    String filterString = null;
    FilterInfo filter = new FilterInfo();
    FilterItemCollection fic = filter.getFilterItems();
    fic.add(new FilterItemInfo("isBizUnit", Boolean.valueOf(true)));
    fic.add(new FilterItemInfo("isSealUp", Boolean.valueOf(false)));
    filter.setMaskString("#0 and #1");
    if (this.isDelimitOrgScope) {
      Context ctx = WafContext.getInstance().getContext();
      String userId = ContextUtil.getCurrentUserInfo(ctx).getId().toString();

      IObjectPK userPK = new ObjectStringPK(userId);
      try {
        Set setIds = OrgRangeIncludeSubOrgFactory.getLocalInstance(ctx).getOrgIdsByUserAndType(userPK, OrgRangeType.BIZ_ORG_TYPE);

        fic.add(new FilterItemInfo("id", setIds, CompareType.INCLUDE));
        filter.setMaskString("#0 and #1 and #2");
      }
      catch (EASBizException e) {
        e.printStackTrace();
      }
      catch (BOSException e) {
        e.printStackTrace();
      }
    }
    filterString = filter.toString();
    return filterString;
  }

  public String f7OrgAdminFilterInfo() {
    String filterStr = "";
    try
    {
      Context ctx = WafContext.getInstance().getContext();
      OrgUnitCollection unitColl = OrgUnitRelationFactory.getLocalInstance(ctx).getFromUnit(ContextUtil.getCurrentFIUnit(ctx).getId().toString(), 1, 0);

      int size = unitColl.size();
      FilterInfo filter = new FilterInfo();
      if (size > 0) {
        FilterItemCollection fic = filter.getFilterItems();
        Set idSet = new HashSet();
        for (int i = 0; i < size; i++) {
          idSet.add(unitColl.get(i).getId().toString());
        }
        fic.add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
        return fic.toString();
      }
    } catch (BOSException e) {
      e.printStackTrace();
    } catch (EASBizException e) {
      e.printStackTrace();
    }
    return filterStr;
  }

  public String getExpenseTypeFilterByOpertionType(String id) {
    String filter = null;
    Context ctx = WafContext.getInstance().getContext();
    FilterInfo fi = new FilterInfo();
    FilterItemCollection fic = fi.getFilterItems();
    fic.add(new FilterItemInfo("operation.id", id));

    fic.add(new FilterItemInfo("company.id", ContextUtil.getCurrentFIUnit(ctx).getId().toString()));

    CompanyOrgUnitInfo cui = ContextUtil.getCurrentFIUnit(ctx);
    String ln = cui.getLongNumber();
    String[] lnSecs = ln.split("!");
    int size = lnSecs.length;
    HashSet lnUps = new HashSet();
    for (int i = 0; i < size; i++) {
      lnUps.add(lnSecs[i]);
    }
    if (lnUps.size() != 0) {
      fic.add(new FilterItemInfo("company.number", lnUps, CompareType.INCLUDE));

      fi.setMaskString("#0 and #1 or #2");
    }
    filter = fi.toString();

    return filter;
  }

  public void afterBillTransform(IObjectCollection billInfos)
  {
    Context ctx = WafContext.getInstance().getContext();
    UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
    BizCollBillBaseInfo info = null;
    int i = 0; for (int n = billInfos.size(); i < n; i++) {
      info = (BizCollBillBaseInfo)billInfos.getObject(i);
      info.setBiller(userInfo);
      info.setLastUpdateUser(userInfo);
      info.setState(StateEnum.NEW);
    }
  }

  public String getRequiredMessage() {
    return Resources.getText(ResourceUtils.CP_BC_WEB_RES, "requiredMessage");
  }

  protected void viewWorkFlow()
    throws Exception
  {
    BOSUuid bosBillId = this.model.getId();
    if (bosBillId == null)
    {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "unAuditNoWorkFlow"));
    }
    else {
      String billId = bosBillId.toString();
      UIWinStyle winStyle = new UIWinStyle();
      winStyle.setWidth(Integer.valueOf(800));
      winStyle.setHeight(Integer.valueOf(600));
      winStyle.setScroll("yes");
      FacesContext context = FacesContext.getCurrentInstance();
      String navUrl = context.getApplication().getViewHandler().getResourceURL(context, "/workflow/viewProcessDiagram.jsf?billId=" + billId);

      this.outerScript = ("KDWin.open('" + navUrl + "', " + Utils.getEditorOptions(winStyle) + ")");
    }
  }

  protected void viewAuditResult()
    throws Exception
  {
    BOSUuid bosBillId = this.model.getId();
    if (bosBillId == null)
    {
      addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES, "operation_failure"), Resources.getText(ResourceUtils.CP_BC_WEB_RES, "unAuditNoWorkFlow"));
    }
    else {
      String billId = bosBillId.toString();
      UIWinStyle winStyle = new UIWinStyle();
      winStyle.setWidth(Integer.valueOf(950));
      winStyle.setHeight(Integer.valueOf(335));
      winStyle.setScroll("yes");
      FacesContext context = FacesContext.getCurrentInstance();
      String navUrl = context.getApplication().getViewHandler().getResourceURL(context, "/workflow/approveHistory.jsf?billId=" + billId);

      this.outerScript = ("KDWin.open('" + navUrl + "', " + Utils.getEditorOptions(winStyle) + ")");
    }
  }

  public void traceUp()
  {
    trace(0);
  }

  public void traceDown()
  {
    trace(1);
  }

  private void trace(int traceType) {
    BOSUuid bosBillId = this.model.getId();
    if (bosBillId == null) {
      return;
    }
    Context ctx = WafContext.getInstance().getContext();

    BizCollCoreBillBaseInfo bill = (BizCollCoreBillBaseInfo)this.model;
    String billID = bill.getId().toString();

    IObjectCollection entryCol = (IObjectCollection)bill.get("entries");
    ctx.put("billInfoID", billID);
    ctx.put("findType", new Integer(traceType));
    ctx.put("selectedEntries", entryCol);
    UIWinStyle winStyle = new UIWinStyle();
    FacesContext context = FacesContext.getCurrentInstance();
    String navUrl = context.getApplication().getViewHandler().getResourceURL(context, "/winlet/botp/navigation.jsf");

    this.outerScript = (winStyle.getOpenMethod() + "('" + navUrl + "', " + Utils.getEditorOptions(winStyle) + ")");
  }

  public IReportBean getReportBean()
  {
    return new IReportBean()
    {
      public Object getDataProvider() {
        BCBaseEditBean.DataProvider data = new BCBaseEditBean.DataProvider();
        MultiDataSourceProviderProxy multiDataSourceProviderProxy = BCBaseEditBean.this.getMultiQueryDelegate();
        multiDataSourceProviderProxy.put("MainQuery", data);
        return multiDataSourceProviderProxy;
      }

      public String getTemplateType()
      {
        return BCBaseEditBean.this.getReportTemplateUrl();
      }
    };
  }

  public String getReportTemplateUrl() {
    return null;
  }

  protected String getReportQueryName() {
    return null;
  }

  protected String getR1ReportBillQueryName() {
    return null;
  }

  protected String getR1ReportEntryQueryName() {
    return null;
  }

  protected String getR1ReportEntrySumQueryName() {
    return null;
  }

  protected IRowSet updateBillReprotRowSet(IRowSet rowSet) throws SQLException
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

  protected MultiDataSourceProviderProxy getMultiQueryDelegate() {
    Context ctx = WafContext.getInstance().getContext();
    return MultiapprovePrintUtility.getMultiQueryDelegate(getSelectedIdForPrint(), ctx);
  }

  protected List getSelectedIdForPrint()
  {
    List idList = new ArrayList();
    idList.add(this.model.getId().toString());
    return idList;
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

  public String getAutoSetAmountApproved() {
    return this.paramMap.get("CP016") == null ? "false" : this.paramMap.get("CP016").toString();
  }

  public void setAutoSetAmountApproved(String autoSetAmountApproved)
  {
    this.autoSetAmountApproved = autoSetAmountApproved;
  }

  public String getCostDeptDisable() {
    return this.costDeptDisable;
  }

  public void setCostDeptDisable(String costDeptDisable) {
    this.costDeptDisable = costDeptDisable;
  }

  public String getComponentValue(String param) {
    String retId = "";
    if ((param != null) && (!"".equals(param))) {
      String[] orgId = (String[])(String[])WafContext.getInstance().getRequest().getParameterMap().get(param);

      if ((orgId != null) && (orgId[0] != null) && (!"".equals(orgId[0]))) {
        retId = orgId[0];
      }
    }
    return retId;
  }

  public static String formatUrl(String id) {
    String newId = "";
    newId = id.replace("+", "%2B");
    return newId;
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
      String billId = BCBaseEditBean.this.model.getId().toString();
      try {
        if ("bill".equals(str)) {
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getLocalInstance(ctx);

          rowSet = ibizForPrint.getRowset(billId, BCBaseEditBean.this.getReportQueryName());

          rowSet = BCBaseEditBean.this.parsePrintValue(rowSet);
          return rowSet;
        }if ("r1bill".equals(str))
        {
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getLocalInstance(ctx);

          rowSet = ibizForPrint.getRowset(billId, BCBaseEditBean.this.getR1ReportBillQueryName());

          rowSet = BCBaseEditBean.this.updateBillReprotRowSet(rowSet);
          return rowSet;
        }
        if ("approveInfo".equals(str)) {
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
          rowSet = ibizForPrint.getEntryRowSet(v.toString(), BCBaseEditBean.this.getR1ReportEntryQueryName());

          rowSet = BCBaseEditBean.this.updateEntryReprotRowSet(rowSet);
          return rowSet;
        }
        if ("entrysum".equals(str))
        {
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getLocalInstance(ctx);

          Variant v = ((DSParam)ds.getParams().get(0)).getValue();
          rowSet = ibizForPrint.getEntryRowSet(v.toString(), BCBaseEditBean.this.getR1ReportEntrySumQueryName());

          return rowSet;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
        BCBaseEditBean.this.logger.error("error occur in the innerClass \"DataProvider\" Of the class \"BCBaseEditBean\" : \n " + e.getCause());
      }
      return null;
    }
  }
}