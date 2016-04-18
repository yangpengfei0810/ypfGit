package com.kingdee.eas.cp.bc.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.uistatemanage.ActionManager;
import com.kingdee.bos.appframework.uistatemanage.ActionState;
import com.kingdee.bos.appframework.uistatemanage.ActionStateProvider;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.function.OperationInfo;
import com.kingdee.bos.metadata.function.UIActionRefInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.AmountControlTypeEnum;
import com.kingdee.eas.cp.bc.BillPrintFacadeFactory;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollBillTypeEnum;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.EvectionReqBillInfo;
import com.kingdee.eas.cp.bc.ExpenseAccountBillInfo;
import com.kingdee.eas.cp.bc.ExpenseCommenFacadeFactory;
import com.kingdee.eas.cp.bc.ForPrintFacadeFactory;
import com.kingdee.eas.cp.bc.IBillPrintFacade;
import com.kingdee.eas.cp.bc.IExpenseCommenFacade;
import com.kingdee.eas.cp.bc.IForPrintFacade;
import com.kingdee.eas.cp.bc.PriorEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.CoreBillEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.enums.IntEnum;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public abstract class BizCollCoreBillListUI extends AbstractBizCollCoreBillListUI
{
  private BizCollBillTypeEnum billType = null;

  private CompanyOrgUnitInfo currentCompany = null;

  public BigDecimal ZERO = new BigDecimal("0.00");

  private OperationInfo oper = null;

  private KDPanel filterDailog = null;

  boolean isRelaFi = true;

  boolean isNeedBudget = false;

  boolean isToVoucher = false;

  protected IRow totalRow = null;
  public static final String RES_BIZCOLL = "com.kingdee.eas.cp.bc.client.ExpAccResource";
  private boolean isFirstDefaultQuery = true;

  private CommonQueryDialog initQueryDialog = null;

  private boolean isRpcInvoke = true;

  private Date storeDateTo = null;

  public BizCollCoreBillListUI()
    throws Exception
  {
    UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
    if ((user != null) && 
      (user.getType() != null) && (user.getType() != UserType.PERSON)) {
      MsgBox.showError(this, EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "userTypeIsWrong"));

      SysUtil.abort();
    }

    this.currentCompany = SysContext.getSysContext().getCurrentFIUnit();

    if ((this.currentCompany == null) || (this.currentCompany.isIsOnlyUnion()))
      throw new BizCollException(BizCollException.COMPANY_IS_UNION);
  }

  protected void setTotalLine()
  {
    this.totalRow = this.tblMain.getGroupManager().getStatRowTemplate(-1);
    this.totalRow.getStyleAttributes().setBackground(new Color(240, 238, 217));
    this.totalRow.getCell(this.tblMain.getColumnIndex("number")).setValue(EASResource.getString("com.kingdee.eas.cp.bc.client.ExpAccResource", "total"));

    this.totalRow.getCell(getKeyFieldName()).setValue("0");
    this.totalRow.getCell("amount").setExpressions("SUM");

    this.totalRow.getCell("amount").getStyleAttributes().setNumberFormat("%r-[=]{#,##0.00}.2f");

    this.totalRow.getCell("amount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);

    this.totalRow.getStyleAttributes().setLocked(true);
    this.tblMain.getSelectManager().remove(-1);
    this.tblMain.getGroupManager().setTotalize(true);
    this.tblMain.getGroupManager().setGroup(true);
    this.tblMain.getGroupManager().setOrientation(1);
    this.tblMain.getGroupManager().group();
  }

  public IRow getTatalRow() {
    return this.totalRow;
  }

  public void onLoad() throws Exception {
    List paramList = new ArrayList();
    paramList.add("CP001");
    paramList.add("CP002");
    paramList.add("CP008");
    Map paramMap = ExpenseCommenFacadeFactory.getRemoteInstance().getParams(paramList);

    this.isRelaFi = new Boolean(paramMap.get("CP002").toString()).booleanValue();

    this.isNeedBudget = new Boolean(paramMap.get("CP001").toString()).booleanValue();

    this.isToVoucher = (paramMap.get("CP008") == null ? false : new Boolean(paramMap.get("CP008").toString()).booleanValue());

    super.onLoad();
    if (!isShowGroup()) {
      setTotalLine();
    }
    if (this.tblMain.getColumn("billTypeCode") != null) {
      this.tblMain.getColumn("billTypeCode").getStyleAttributes().setHided(true);
    }
    this.tblMain.setColumnMoveable(true);
    this.btnCopyAndAddNew.setEnabled(true);
    this.btnCopyAndAddNew.setIcon(EASResource.getIcon("imgTbtn_copy"));
    this.menuItemCopyAndAddNew.setEnabled(true);
    this.menuItemCopyAndAddNew.setIcon(EASResource.getIcon("imgTbtn_copy"));
  }

  protected boolean isNotRelaFiAndVoucher()
  {
    return (!this.isRelaFi) && (!this.isToVoucher);
  }

  protected boolean initDefaultFilter()
  {
    return true;
  }

  protected void wfInit()
  {
    super.wfInit();
    if (isFromWorkFlow())
      getActionManager().registerActionStateProvider(new wfActionStateProvider());
  }

  public boolean isShowGroup()
  {
    return getUIContext().get("IDList") != null;
  }

  public boolean isFromWorkFlow() {
    this.oper = ((OperationInfo)getUIContext().get("OperationInfo"));
    Boolean isFromWF = (Boolean)getUIContext().get("isFromWorkflow");

    return (isFromWF != null) && (isFromWF.booleanValue()) && (!BizCollUtil.objectIsNull(this.oper.getName()));
  }

  public void loadFields()
  {
    super.loadFields();
  }

  public void storeFields()
  {
    super.storeFields();
  }

  public BizCollBillTypeEnum getBillType() {
    return this.billType;
  }

  public void setBillType(BizCollBillTypeEnum type) {
    this.billType = type;
  }

  protected CommonQueryDialog initCommonQueryDialog() {
    if (this.initQueryDialog != null) {
      return this.initQueryDialog;
    }
    this.initQueryDialog = super.initCommonQueryDialog();
    KDPanel userPanel = getOldUserPanel();

    if (userPanel != null) {
      this.initQueryDialog.addUserPanel(userPanel);
    }
    Dimension dim = userPanel.getPreferredSize();
    if (dim.getHeight() != 10.0D) {
      this.initQueryDialog.setWidth((int)dim.getWidth() + 80);
      this.initQueryDialog.setHeight((int)dim.getHeight() + 27);
    }
    this.initQueryDialog.setShowFilter(true);
    this.initQueryDialog.setShowSorter(true);
    this.initQueryDialog.setUiObject(this);

    return this.initQueryDialog;
  }

  protected EntityViewInfo getInitDefaultSolution()
  {
    EntityViewInfo view = new EntityViewInfo();
    FilterInfo filter = new FilterInfo();
    Date dtFrom = null;
    Date dtTo = new Date();

    if (this.isRpcInvoke) {
      this.storeDateTo = dtTo;
      this.isRpcInvoke = false;
    } else {
      dtTo = this.storeDateTo;
      this.isRpcInvoke = true;
    }

    Calendar cal = Calendar.getInstance();
    cal.setTime(dtTo);
    cal.set(5, 1);
    dtFrom = cal.getTime();

    dtTo = DateTimeUtils.addDay(dtTo, 1L);

    FilterItemCollection fic = filter.getFilterItems();
    fic.add(new FilterItemInfo("bizReqDate", DateTimeUtils.truncateDate(dtFrom), CompareType.GREATER_EQUALS));

    fic.add(new FilterItemInfo("bizReqDate", dtTo, CompareType.LESS_EQUALS));

    view.setFilter(filter);
    return view;
  }

  protected FilterInfo getDefaultFilterForQuery()
  {
    FilterInfo filterInfo = super.getDefaultCUFilter(isIgnoreCUFilter());

    FilterInfo tempFilter = new FilterInfo();
    FilterItemInfo item = null;
    String data = null;
    if ((getBOTPViewStatus() == 0) && 
      (isFirstDefaultQuery())) {
      data = SysContext.getSysContext().getCurrentFIUnit().getId().toString();

      if (isAccountBill()) {
        item = new FilterItemInfo("entries.company.id", data, CompareType.EQUALS);

        tempFilter.getFilterItems().add(item);

        item = new FilterItemInfo("applierCompany.id", data, CompareType.EQUALS);

        tempFilter.getFilterItems().add(item);
        tempFilter.setMaskString("#0 or #1");
      }
      else {
        item = new FilterItemInfo("applierCompany.id", data, CompareType.EQUALS);

        tempFilter.getFilterItems().add(item);
        tempFilter.setMaskString("#0");
      }
      try {
        filterInfo.mergeFilter(tempFilter, " and ");
      }
      catch (BOSException e) {
        e.printStackTrace();
      }
    }

    return filterInfo;
  }

  private boolean isAccountBill()
  {
    return (getMetaDataPK().getFullName().equals("com.kingdee.eas.cp.bc.client.BizExpenseAccountListUI")) || (getClass().getName().equals("com.kingdee.eas.cp.bc.client.TravelExpenseAccountListUI")) || (getClass().getName().equals("com.kingdee.eas.cp.bc.client.DailyExpenseAccountListUI"));
  }

  protected boolean isIgnoreCUFilter()
  {
    return true;
  }

  public void actionQuery_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionQuery_actionPerformed(e);
  }

  protected KDPanel getUserPanel()
  {
    return this.filterDailog;
  }

  private KDPanel getOldUserPanel()
  {
    if (this.filterDailog == null) {
      this.filterDailog = getUserPanel();
    }
    return this.filterDailog;
  }

  private boolean showChoice(IUIWindow window)
  {
    if (fillBillType() == null)
      return false;
    boolean actionType = false;
    try {
      UIContext uiContext = new UIContext(this);
      if (window == null) {
        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);

        window = uiFactory.create(CollBillSelectUI.class.getName(), uiContext, null, null);
      }

      ((CollBillSelectUI)window.getUIObject()).addBillTypes(fillBillType());

      window.show();
      this.billType = ((CollBillSelectUI)window.getUIObject()).getCurBillType();

      actionType = ((CollBillSelectUI)window.getUIObject()).isConfirm();
    } catch (UIException e) {
      ExceptionHandler.handle(e);
    }
    return actionType;
  }

  protected BizCollBillTypeEnum[] fillBillType() {
    return null;
  }

  public void beforeActionPerformed(ActionEvent e) {
    super.beforeActionPerformed(e);

    this.billType = null;
  }

  public void actionView_actionPerformed(ActionEvent e) throws Exception
  {
    if (isFromWorkFlow()) {
      return;
    }
    super.actionView_actionPerformed(e);
  }

  public void actionAddNew_actionPerformed(ActionEvent e)
    throws Exception
  {
    if ((showChoice(null)) || (fillBillType() == null))
      super.actionAddNew_actionPerformed(e);
  }

  public void actionRemove_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionRemove_actionPerformed(e);

    super.initDapButtons();
  }

  public void actionCreateTo_actionPerformed(ActionEvent e)
    throws Exception
  {
    ArrayList billIdlist = getSelectedIdValues();
    String tempId = null;
    Iterator ite = billIdlist.iterator();
    while (ite.hasNext()) {
      tempId = (String)ite.next();
      ObjectUuidPK objpk = new ObjectUuidPK(tempId);
      BizCollBillBaseInfo billInfo = (BizCollBillBaseInfo)DynamicObjectFactory.getRemoteInstance().getValue(objpk.getObjectType(), objpk, getSelector());

      if ((!(billInfo instanceof ExpenseAccountBillInfo)) && (!(billInfo instanceof EvectionReqBillInfo)))
      {
        if ((billInfo.getAmountControlType() == null) || ((billInfo.getAmountControlType() != null) && (10 == billInfo.getAmountControlType().getValue())))
        {
          if ((billInfo.getAmountBalance() != null)&& (billInfo.getAmountBalance().doubleValue() <= 0.0D) )//
          {
           // throw new BizCollException(BizCollException.CANNTOTCREATETO);
          }
        }
      }

      if ((this.isRelaFi) && (!BizCollUtil.checkForBillCreateTo(null, tempId))) {
        throw new BizCollException(BizCollException.CANNOT_CREATETO);
      }
    }
    super.actionCreateTo_actionPerformed(e);
  }

  protected void setSortForQuery(SorterItemInfo sortItem, SorterItemInfo oldSortItem)
    throws Exception
  {
    this.mainQuery.getSorter().clear();

    if (sortItem.getPropertyName() != "id") {
      this.mainQuery.getSorter().add(sortItem);
    }

    if ((oldSortItem != null) && (!oldSortItem.getPropertyName().equals(sortItem.getPropertyName())))
    {
      this.mainQuery.getSorter().add(oldSortItem);
    }
    refreshListForOrder();
  }

  protected List getSelectIdList()
  {
    ArrayList list = new ArrayList();
    KDTSelectBlock sb = null;
    int size = this.tblMain.getSelectManager().size();
    if (size == 0) {
      return null;
    }
    int[] rows = KDTableUtil.getSelectedRows(this.tblMain);
    for (int i = 0; (i < rows.length) && 
      (rows[i] >= 0); i++)
    {
      list.add(this.tblMain.getRow(rows[i]).getCell("id").getValue().toString());
    }

    return list;
  }

  public void showErrorForNoWorkFlow()
    throws BOSException
  {
    IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();

    ProcessInstInfo instInfo = null;
    ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(getSelectedKeyValue());

    int i = 0; for (int n = procInsts.length; i < n; i++)
    {
      if ((!"open.running".equals(procInsts[i].getState())) && (!"open.not_running.suspended".equals(procInsts[i].getState()))) {
        continue;
      }
      instInfo = procInsts[i];
    }

    if (instInfo == null) {
      MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_WFHasNotInstance"));

      setCursorOfDefault();
      SysUtil.abort();
    }
  }

  public void actionVoucher_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionVoucher_actionPerformed(e);
    refresh(e);

    if (!this.isRelaFi) {
      this.actionVoucher.setVisible(false);
      this.actionDelVoucher.setVisible(false);
    }
  }

  public void actionDelVoucher_actionPerformed(ActionEvent e)
    throws Exception
  {
    checkSelected();
    ObjectUuidPK objpk = new ObjectUuidPK(getSelectedKeyValue());
    IDynamicObject iDynamicObject = DynamicObjectFactory.getRemoteInstance();

    BizCollBillBaseInfo sourceinfo = (BizCollBillBaseInfo)iDynamicObject.getValue(objpk.getObjectType(), objpk, getSelector());

    if (BizCollUtil.checkForDelVoucher(sourceinfo))
      super.actionDelVoucher_actionPerformed(e);
    else {
      throw new BizCollException(BizCollException.CANNOT_DELVOUCHER);
    }
    refresh(e);
  }

  private static SelectorItemCollection getSelector() {
    SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("id"));
    sic.add(new SelectorItemInfo("state"));
    sic.add(new SelectorItemInfo("amountBalance"));
    sic.add(new SelectorItemInfo("amountControlType"));
    return sic;
  }

  protected SystemEnum getSystemEnumForOA()
  {
    return SystemEnum.BUSINESS_COLLABORATION;
  }

  protected String getReportQueryName()
  {
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

  protected IRowSet updateEntryReprotRowSet(IRowSet rowSet)
    throws SQLException
  {
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
    if (!rowSet.next()) {
      return rowSet;
    }
    String mark = "";
    int pos = rowSet.findColumn(valueAlias);
    Iterator ite = null;
    IntEnum intenum = null;
    rowSet.previous();
    while (rowSet.next()) {
      ite = list.iterator();
      int tempPrior = rowSet.getInt(value);
      while (ite.hasNext()) {
        intenum = (IntEnum)ite.next();
        if (tempPrior == intenum.getValue()) {
          mark = intenum.getAlias();
        }
      }

      rowSet.updateString(pos, mark);
    }

    rowSet.beforeFirst();
    return rowSet;
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

  public void actionCopyAndAddNew_actionPerformed(ActionEvent e)
    throws Exception
  {
    checkSelected();

    checkObjectExists();

    UIContext uiContext = new UIContext(this);
    uiContext.put("ID", getSelectedKeyValue());

    uiContext.put("isListCopyAndAddNew", Boolean.TRUE);

    prepareUIContext(uiContext, e);

    IUIWindow uiWindow = null;
    if ((SwingUtilities.getWindowAncestor(this) != null) && ((SwingUtilities.getWindowAncestor(this) instanceof JDialog)))
    {
      uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getEditUIName(), uiContext, null, OprtState.VIEW);
    }
    else
    {
      uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null, OprtState.VIEW);
    }

    if ((uiWindow.getUIObject() instanceof BizCollCoreBillEditUI)) {
      ((BizCollCoreBillEditUI)uiWindow.getUIObject()).setNotActionCopyAddnew(false);
    }

    ((CoreBillEditUI)uiWindow.getUIObject()).actionCopy_actionPerformed(e);
    ((CoreBillEditUI)uiWindow.getUIObject()).afterActionPerformed(e);
    uiWindow.show();

    uiContext.put("isListCopyAndAddNew", Boolean.FALSE);

    if ((uiWindow.getUIObject() instanceof BizCollCoreBillEditUI)) {
      ((BizCollCoreBillEditUI)uiWindow.getUIObject()).setNotActionCopyAddnew(true);
    }

    if (isDoRefresh(uiWindow)) {
      this.isModify = true;
      setLocatePre(false);
      refresh(e);
      setLocatePre(true);
    }
  }

  private void checkObjectExists()
    throws BOSException, EASBizException, Exception
  {
    if (getSelectedKeyValue() == null) {
      return;
    }
    if (!getBizInterface().exists(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue()))))
    {
      refreshList();
      throw new EASBizException(EASBizException.CHECKEXIST);
    }
  }

  protected void initWorkButton()
  {
    super.initWorkButton();
    this.menuItemAntiAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
    this.menuItemAbandon.setIcon(EASResource.getIcon("imgTbtn_blankout"));
    this.menuItemCloseBill.setIcon(EASResource.getIcon("imgTbtn_close"));
    this.btnSuspenseAcc.setIcon(EASResource.getIcon("imgTbtn_suspendinstance"));

    this.MenuItemSuspenseAcc.setIcon(EASResource.getIcon("imgTbtn_suspendinstance"));
  }

  public void actionSuspenseAcc_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionVoucher_actionPerformed(e);
    refresh(e);
  }

  protected List getSelectedIdForPrint()
  {
    return getSelectIdList();
  }

  public SelectorItemCollection getSelectorForBg() {
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

  public void actionAttachment_actionPerformed(ActionEvent e)
    throws Exception
  {
    AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    String boID = getSelectedKeyValue();
    checkSelected();
    if (boID == null)
    {
      return;
    }
    AttachmentUIContextInfo info = new AttachmentUIContextInfo();
    info.setBoID(boID);
    info.setCode("");
    info.setEdit(false);
    info.setListener(createAttatchListener("HEAD"));
    acm.showAttachmentListUIByBoID(this, info);
  }

  public RequestContext prepareActionCopyAndAddNew(IItemAction itemAction) throws Exception
  {
    RequestContext requestContext = super.prepareActionCopyAndAddNew(itemAction);

    requestContext.put("ID", getSelectedKeyValue());
    return requestContext;
  }

  public RequestContext prepareActionQuery(IItemAction itemAction)
    throws Exception
  {
    RequestContext request = super.prepareActionQuery(itemAction);
    String id = SysContext.getSysContext().getCurrentUserInfo().getId().toString();

    String F7_CAPITAL_SENSITIVE_QUERY_PARAM_NAME = "f7CapitalSensitiveQuery";
    String F7_RETURN_ONLY_RECORD = "returnOlnyRecord";
    if ((id != null) || (!"".equals(id))) {
      String capitalSensitiveFilter = "where user.id='" + id + "' and paramName='" + F7_CAPITAL_SENSITIVE_QUERY_PARAM_NAME + "'";

      String returnOnlyRecordFilter = "where user.id='" + id + "' and paramName='" + F7_RETURN_ONLY_RECORD + "'";

      request.put("capitalSensitiveFilter", capitalSensitiveFilter);
      request.put("returnOnlyRecordFilter", returnOnlyRecordFilter);
    }
    return request;
  }

  public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception
  {
    RequestContext request = super.prepareActionAddNew(itemAction);
    request.put("ID", getSelectedKeyValue());
    return request;
  }

  public RequestContext prepareActionEdit(IItemAction itemAction) throws Exception
  {
    RequestContext request = super.prepareActionEdit(itemAction);
    request.put("ID", getSelectedKeyValue());
    return request;
  }

  public RequestContext prepareActionView(IItemAction itemAction) throws Exception
  {
    RequestContext request = super.prepareActionView(itemAction);
    request.put("ID", getSelectedKeyValue());
    return request;
  }

  public RequestContext prepareActionCreateTo(IItemAction itemAction) throws Exception
  {
    RequestContext request = super.prepareActionCreateTo(itemAction);
    request.put("billIdlist", getSelectedIdValues());
    return request;
  }

  public IUIActionPostman prepareInit() {
    IUIActionPostman clientHanlder = super.prepareInit();
    if (clientHanlder != null) {
      RequestContext request = (RequestContext)clientHanlder.getRequestContext();

      List paramList = new ArrayList();
      paramList.add("CP001");
      paramList.add("CP002");
      paramList.add("CP008");
      request.put("paramList", paramList);
      request.put("isAccountBill", new Boolean(isAccountBill()));
      clientHanlder.setRequestContext(request);
    }

    return clientHanlder;
  }

  public boolean isPrepareInit()
  {
    return true;
  }

  public boolean isPrepareActionQuery()
  {
    return true;
  }

  public boolean isPrepareActionRefresh()
  {
    return true;
  }

  public boolean isPrepareActionCopyAndAddNew() {
    return true;
  }

  public boolean isPrepareActionTraceUp() {
    return true;
  }

  public boolean isPrepareActionTraceDown() {
    return true;
  }

  public class DataProvider
    implements BOSQueryDelegate
  {
    public DataProvider()
    {
    }

    public IRowSet execute(BOSQueryDataSource ds)
    {
      try
      {
        IRowSet rowSet = null;
        String str = ds.getID();
        if ("bill".equals(str)) {
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getRemoteInstance();

          rowSet = ibizForPrint.getRowset(BizCollCoreBillListUI.this.getSelectIdList(), BizCollCoreBillListUI.this.getReportQueryName());

          rowSet = BizCollCoreBillListUI.this.parsePrintValue(rowSet);
          return rowSet;
        }if ("r1bill".equals(str))
        {
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getRemoteInstance();

          rowSet = ibizForPrint.getRowset(BizCollCoreBillListUI.this.getSelectIdList(), BizCollCoreBillListUI.this.getR1ReportBillQueryName());

          rowSet = BizCollCoreBillListUI.this.updateBillReprotRowSet(rowSet);
          return rowSet;
        }if ("approveInfo".equals(str)) {
          IBillPrintFacade billPrintFacade = BillPrintFacadeFactory.getRemoteInstance();

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
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getRemoteInstance();

          Variant v = ((DSParam)ds.getParams().get(0)).getValue();
          rowSet = ibizForPrint.getEntryRowSet(v.toString(), BizCollCoreBillListUI.this.getR1ReportEntryQueryName());

          rowSet = BizCollCoreBillListUI.this.updateEntryReprotRowSet(rowSet);
          return rowSet;
        }
        if ("entrysum".equals(str))
        {
          IForPrintFacade ibizForPrint = ForPrintFacadeFactory.getRemoteInstance();

          Variant v = ((DSParam)ds.getParams().get(0)).getValue();
          rowSet = ibizForPrint.getEntryRowSet(v.toString(), BizCollCoreBillListUI.this.getR1ReportEntrySumQueryName());

          return rowSet;
        }
      } catch (Exception e) {
        BizCollCoreBillListUI.this.handUIException(e);
      }
      return null;
    }
  }

  class wfActionStateProvider
    implements ActionStateProvider
  {
    wfActionStateProvider()
    {
    }

    public HashMap getActionState(IObjectValue vo, IObjectPK userInfoPk, String uiClass, HashMap actionSet)
    {
      Iterator itUIAction = actionSet.keySet().iterator();
      HashMap retDisableAction = new HashMap();
      String action = BizCollCoreBillListUI.this.oper.getUiActionRef().getActionRefName();
      if ((action == null) || (action.equals(""))) {
        return null;
      }
      while (itUIAction.hasNext()) {
        Object acStateTarget = itUIAction.next();
        if (!action.toUpperCase().equals(BizCollCoreBillListUI.this.splitClassName(acStateTarget.toString().toUpperCase())))
        {
          retDisableAction.put(acStateTarget, new ActionState(null, 0));
        }
      }

      return retDisableAction;
    }
  }
}