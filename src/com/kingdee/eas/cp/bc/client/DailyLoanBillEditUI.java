package com.kingdee.eas.cp.bc.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDCheckBoxMenuItem;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.RuleStatus;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.AmountControlTypeEnum;
import com.kingdee.eas.cp.bc.BcParamInfo;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollBillTypeEnum;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.CommonUtilFacadeFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryCollection;
import com.kingdee.eas.cp.bc.DailyLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.DailyLoanBillFactory;
import com.kingdee.eas.cp.bc.DailyLoanBillInfo;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.cp.bc.ExpenseCommenFacadeFactory;
import com.kingdee.eas.cp.bc.ExpenseReqException;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.ICommonUtilFacade;
import com.kingdee.eas.cp.bc.IDailyLoanBill;
import com.kingdee.eas.cp.bc.IExpenseCommenFacade;
import com.kingdee.eas.cp.bc.LoanTypeEnum;
import com.kingdee.eas.cp.bc.MakeControl;
import com.kingdee.eas.cp.bc.OperationTypeInfo;
import com.kingdee.eas.cp.bc.PriorEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.cp.bc.util.AssistantAccountUtil;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ObjectValueUtil;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.print.MultiDataSourceProviderProxy;
import com.kingdee.eas.framework.report.util.PeriodEntity;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class DailyLoanBillEditUI extends AbstractDailyLoanBillEditUI
{
  private String TB_PURPOSE = "purpose";

  private String TB_AMOUNT = "amount";

  private String TB_COMMENT = "comment";

  private String TB_EXPENSETYPE = "expensetype";

  private String TB_AMOUNTAPPROVED = "approvedamount";

  private String TB_BUDGETAMOUNT = "budgetamount";
  private static final String RESOURCE = "com.kingdee.eas.cp.bc.ExpenseReqException";
  Map paramMap = new HashMap();

  private boolean isAmountApprovedCaculated = false;

  private String RES = "com.kingdee.eas.cp.bc.client.LoanReqResource";
  private static final String RESBIZCOLL = "com.kingdee.eas.cp.bc.BizCollResource";
  private int DEFAULTLINES = 1;

  public static final BigDecimal ZERO = new BigDecimal("0.00");

  private Color backGroundcolor = Color.decode("#FCFBDF");

  private boolean isShowedParam = false;

  private boolean initFlag = false;

  JButton btnShowBal = new KDWorkButton();

  JButton btnSpace = new KDWorkButton();

  private KDBizPromptBox bizPromptExpenseTypeEntry = new KDBizPromptBox();

  private boolean isEntrustedByCostCenter = false;

  public DailyLoanBillEditUI()
    throws Exception
  {
    this.bizPromptCompany.addDataChangeListener(new DataChangeListener()
    {
      public void dataChanged(DataChangeEvent e) {
        try {
          boolean isChanged = true;
          isChanged = BizCollUtil.isF7ValueChanged(e);
          if (!isChanged)
            return;
          if ((DailyLoanBillEditUI.this.isEntrustedByCostCenter) && (e.getOldValue() != null)) {
            DailyLoanBillEditUI.this.bizPromptCostedDept.setData(null);
          }
          bizPromptCompany_dataChange(e);
        }
        catch (Exception exc) {
          DailyLoanBillEditUI.this.handUIException(exc); } finally {
        }
      }

      private void bizPromptCompany_dataChange(DataChangeEvent e) throws Exception {
        if ((DailyLoanBillEditUI.this.isLoadFieldAction) && (DailyLoanBillEditUI.this.getUIContext().get("CP_BC_ADDNEW_CONTINOUSLY") == null)) {
          return;
        }
        if (("ADDNEW".equals(DailyLoanBillEditUI.this.getOprtState())) || ("EDIT".equals(DailyLoanBillEditUI.this.getOprtState())))
        {
          MakeControl.makeCostTarget(DailyLoanBillEditUI.this.bizPromptSupportedObj, (CompanyOrgUnitInfo)DailyLoanBillEditUI.this.bizPromptCompany.getData());

          isEntrustedByCostCenter = MakeControl.makeCostCenterF7(bizPromptCostedDept, (CompanyOrgUnitInfo)bizPromptCompany.getData(), paramMap);
        }
      }
    });
    this.FormattedTextFieldAmount.setHorizontalAlignment(4);

    this.bizPromptApplier.setRequired(true);
    this.dateReqDate.setRequired(true);
    this.bizPromptOrgUnit.setRequired(true);

    this.bizPromptCompany.setRequired(true);
    this.bizPromptCostedDept.setRequired(true);
    this.bizPromptCurrencyType.setRequired(true);
    this.bizPromptBiller.setRequired(true);
    this.dateBillDate.setRequired(true);

    this.kdtEntries.getSelectManager().addKDTActiveCellListener(new KDTActiveCellListener()
    {
      public void activeCellChanged(KDTActiveCellEvent e)
      {
        if ((e.getRowIndex() == DailyLoanBillEditUI.this.kdtEntries.getRowCount() - 1) && 
          (e.getPrevRowIndex() == DailyLoanBillEditUI.this.kdtEntries.getRowCount() - 2) && (e.getPrevColumnIndex() == DailyLoanBillEditUI.this.kdtEntries.getColumnCount() - 1) && (e.getColumnIndex() == 0))
        {
          DailyLoanBillEditUI.this.addLine(DailyLoanBillEditUI.this.kdtEntries);
        }
      }
    });
    this.bizPromptOrgUnit.addDataChangeListener(new DataChangeListener()
    {
      public void dataChanged(DataChangeEvent e)
      {
        try {
          DailyLoanBillEditUI.this.bizPromptOrgUnit_dataChanged(e);
        } catch (Exception exc) {
          DailyLoanBillEditUI.this.handUIException(exc);
        }
        finally
        {
        }
      }
    });
    this.bizPromptCostedDept.addDataChangeListener(new DataChangeListener()
    {
      public void dataChanged(DataChangeEvent e)
      {
        try {
          DailyLoanBillEditUI.this.bizPromptCostedDept_dataChanged(e);
        } catch (Exception exc) {
          DailyLoanBillEditUI.this.handUIException(exc);
        }
        finally
        {
        }
      }
    });
    this.bizPromptCurrencyType.addDataChangeListener(new DataChangeListener()
    {
      public void dataChanged(DataChangeEvent e)
      {
        try {
          DailyLoanBillEditUI.this.bizPromptCurrencyType_dataChanged(e);
        } catch (Exception exc) {
          DailyLoanBillEditUI.this.handUIException(exc);
        }
        finally
        {
        }
      }
    });
    this.kdtEntries.addKDTPropertyChangeListener(new KDTPropertyChangeListener()
    {
      public void propertyChange(KDTPropertyChangeEvent evt)
      {
        try {
          DailyLoanBillEditUI.this.kdtEntries_propertyChange(evt);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    this.kDCheckAmountControl.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0) {
        DailyLoanBillEditUI.this.setAmountControlType();
      }
    });
  }

  private void setAmountControlType() {
    if (this.kDCheckAmountControl.getSelectState() == 32)
      this.editData.setAmountControlType(AmountControlTypeEnum.oneTOmore);
    else
      this.editData.setAmountControlType(AmountControlTypeEnum.oneTOone);
  }

  public void loadFields()
  {
    if (this.editData == null) {
      return;
    }

    this.isLoadFieldAction = true;
    if (OprtState.ADDNEW.equals(getOprtState())) {
      Object obj = getUIContext().get("InitDataObject");
      if (obj != null)
      {
        java.util.Date now = new java.util.Date();

        this.editData.setBizReqDate(DateTimeUtils.truncateDate(now));

        this.editData.setLoanType(LoanTypeEnum.DAILY_PURCHASE);

        this.editData.setState(StateEnum.NEW);

        this.editData.setBillTypeCode(BizCollBillTypeEnum.DAILY_LOAN);

        this.editData.setBizReqDate(DateTimeUtils.truncateDate(now));

        this.editData.setBiller(SysContext.getSysContext().getCurrentUserInfo());

        this.editData.setCreator(SysContext.getSysContext().getCurrentUserInfo());

        this.editData.setBillDate(DateTimeUtils.truncateDate(now));
        this.editData.setLoanType(LoanTypeEnum.DAILY_PURCHASE);

        if (getUIContext().get("srcBillID") != null) {
          this.editData.setSourceBillId(getUIContext().get("srcBillID").toString());
        }
      }

    }

    if (this.editData == null) {
      return;
    }
    setAutoNumberByOrg("Company");
    this.dataBinder.loadHeader();

    if ((OprtState.ADDNEW.equals(getOprtState())) || (OprtState.EDIT.equals(getOprtState())))
    {
      if (this.editData.getSourceBillId() == null) {
        if (this.editData.getAmountControlType() == null) {
          this.editData.setAmountControlType(AmountControlTypeEnum.oneTOone);
        }

      }
      else if (AmountControlTypeEnum.oneTOmore.equals(BizCollUtil.getParentAmountConttrolType(this.editData.getSourceBillId())))
      {
        this.editData.setAmountControlType(AmountControlTypeEnum.oneTOmore);
      }
      else {
        this.editData.setAmountControlType(AmountControlTypeEnum.oneTOone);
      }

    }

    bindDataToTable(this.kdtEntries, this.editData.getEntries());

    initDefaultLines(this.kdtEntries);

    recountAmount();
    this.oldData.setInt("amountControlType", this.editData.getAmountControlType().getValue());

    this.oldData.setString("number", this.editData.getNumber());
    this.isLoadFieldAction = false;
  }
  protected void setAutoNumberByOrg(String orgType) {
    if (this.editData.getNumber() == null) {
      try {
        String companyID = null;
        if ((!StringUtils.isEmpty(orgType)) && (!"NONE".equalsIgnoreCase(orgType)) && (SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum(orgType)) != null)) {
          companyID = SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum(orgType)).getString("id");
        }
        else if (SysContext.getSysContext().getCurrentOrgUnit() != null) {
          companyID = SysContext.getSysContext().getCurrentOrgUnit().getString("id");
        }
        ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
        if (iCodingRuleManager.isExist(this.editData, companyID)) {
          if (iCodingRuleManager.isAddView(this.editData, companyID)) {
            this.editData.setNumber(iCodingRuleManager.getNumber(this.editData, companyID));
          }
          this.txtNumber.setEnabled(false);
          RuleStatus rs = iCodingRuleManager.getRuleStatus(this.editData, companyID);
          if ((rs != null) && (rs.isModifiable())) {
            this.txtNumber.setEnabled(true);
          }
        }
      }
      catch (Exception e)
      {
        handUIException(e);
        this.oldData = this.editData;
        SysUtil.abort();
      }

    }
    else if (this.editData.getNumber().trim().length() > 0)
      this.txtNumber.setText(this.editData.getNumber());
  }

  public void setDataObject(IObjectValue dataObject)
  {
    super.setDataObject(dataObject);

    this.oldData = ((AbstractObjectValue)this.editData.clone());
  }

  protected void prepareNumber(IObjectValue caller, String number)
  {
    super.prepareNumber(caller, number);
    ((BizCollBillBaseInfo)caller).setNumber(number);

    this.txtNumber.setEnabled(false);
  }

  protected void setNumberTextEnabled()
  {
    super.setNumberTextEnabled();
    this.txtNumber.setEditable(true);
    this.txtNumber.setEnabled(true);
  }

  public void bindDataToTable(KDTable table, IObjectCollection detailCollection)
  {
    if (detailCollection == null) {
      return;
    }
    table.removeRows();

    if (detailCollection != null) {
      table.setUserObject(detailCollection);
    }

    int detailCount = detailCollection.size();
    Object detailData = null;
    IRow row = null;
    for (int i = 0; i < detailCount; i++) {
      detailData = detailCollection.getObject(i);
      row = table.addRow();
      row.setUserObject(detailData);
      loadLineFields(table, row, (IObjectValue)detailData);
    }
  }

  protected void loadLineFields(KDTable table, IRow row, IObjectValue obj)
  {
    super.loadLineFields(table, row, obj);
    this.dataBinder.loadLineFields(table, row, obj);
  }

  public void storeFields()
  {
    this.isLoadFieldAction = true;
    if (this.editData == null)
      return;
    if (this.kDCheckAmountControl.isSelected()) {
      this.editData.setAmountControlType(AmountControlTypeEnum.oneTOmore);
    }
    else {
      this.editData.setAmountControlType(AmountControlTypeEnum.oneTOone);
    }

    this.dataBinder.storeHeader();

    bindTableToData(this.kdtEntries, this.editData.getEntries());
    this.isLoadFieldAction = false;
  }

  public void bindTableToData(KDTable table, IObjectCollection detailCollection)
  {
    if (detailCollection == null)
      return;
    detailCollection.clear();

    int i = 0; for (int n = table.getRowCount(); i < n; i++) {
      IRow row = table.getRow(i);

      if (checkIsCountLine(row)) {
        continue;
      }
      IObjectValue obj = (IObjectValue)row.getUserObject();

      storeLineFields2(table, row, obj);
      if (checkIsBlankLine((AbstractObjectValue)obj)) {
        continue;
      }
      if (detailCollection.addObject(obj))
        storeLineFields(table, row, obj);
    }
  }

  public void storeLineFields(KDTable table, IRow row, IObjectValue obj)
  {
    super.storeLineFields(table, row, obj);
    this.dataBinder.storeLineFields(table, row, obj);
  }

  private void storeLineFields2(KDTable table, IRow row, IObjectValue obj)
  {
    table.checkParsed();

    int i = 0; for (int n = table.getColumnCount(); i < n; i++) {
      String bindField = table.getColumn(i).getFieldName();
      if (!StringUtils.isEmpty(bindField))
        if (bindField.indexOf(".") >= 0) {
          String[] subItems = StringUtils.split(bindField, ".");

          IObjectValue subObj = obj;
          int k = 0; for (int l = subItems.length - 1; k < l; k++) {
            subObj = (IObjectValue)subObj.get(subItems[k]);
            if (subObj == null)
              break;
          }
          if (subObj != null)
            subObj.put(subItems[(subItems.length - 1)], row.getCell(i).getValue());
        }
        else {
          Class type = obj.getClass();
          try {
            String setMethodName = "set" + StringUtils.headCharUpperCase(bindField);

            Method[] methods = type.getMethods();
            Method method = null;
            int k = 0; for (int l = methods.length; k < l; k++) {
              if (!setMethodName.equalsIgnoreCase(methods[k].getName()))
                continue;
              method = methods[k];
              break;
            }

            if (method != null) {
              method.invoke(obj, new Object[] { row.getCell(i).getValue() });
            }
            else
              obj.put(bindField, row.getCell(i).getValue());
          } catch (Exception e) {
            obj.put(bindField, row.getCell(i).getValue());
          }
        }
    }
  }

  protected IObjectValue createNewDetailData(KDTable table)
  {
    DailyLoanBillEntryInfo entryInfo = new DailyLoanBillEntryInfo();
    entryInfo.setAmount(new BigDecimal("0.00"));

    if (this.isInitAmountApproved) {
      entryInfo.setAmountApproved(entryInfo.getAmount());
    }

    entryInfo.setBudgetDo(new BigDecimal("0.00"));

    return entryInfo;
  }

  protected KDTable getDetailTable()
  {
    return this.kdtEntries;
  }

  protected IObjectValue createNewData()
  {
    DailyLoanBillInfo info = new DailyLoanBillInfo();
    try
    {
      BizCollBillBaseInfo baseInfo = CommonUtilFacadeFactory.getRemoteInstance().forLoanBillCreateNewData();

      info.setCurrencyType(baseInfo.getCurrencyType());

      info.setName(BizCollBillTypeEnum.DAILY_LOAN.toString());

      info.setState(StateEnum.NEW);

      info.setBillTypeCode(BizCollBillTypeEnum.DAILY_LOAN);

      info.setPrior(PriorEnum.LOW);

      info.setLoanType(LoanTypeEnum.DAILY_PURCHASE);

      info.setPayMode(((DailyLoanBillInfo)baseInfo).getPayMode());

      java.util.Date now = new java.util.Date();

      info.setBizReqDate(DateTimeUtils.truncateDate(baseInfo.getBizReqDate()));

      info.setBiller(SysContext.getSysContext().getCurrentUserInfo());

      info.setBillDate(DateTimeUtils.truncateDate(baseInfo.getBillDate()));

      info.setAmount(new BigDecimal("0.0"));

      info.setApplier(baseInfo.getApplier());

      info.setPosition(baseInfo.getPosition());

      info.setOrgUnit(baseInfo.getOrgUnit());

      info.setApplierCompany(baseInfo.getApplierCompany());

      info.setCostedDept(baseInfo.getCostedDept());

      info.setCompany(baseInfo.getCompany());

      info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
    } catch (Exception e) {
      handUIException(e);
    }

    return info;
  }

  protected ICoreBase getBizInterface()
    throws Exception
  {
    return DailyLoanBillFactory.getRemoteInstance();
  }

  public void onLoad()
    throws Exception
  {
    super.onLoad();

    initEntryEditor();

    setBtnViewRrcdsOfLendAndRepayState();

    this.mark.setHorizontalAlignment(0);
    this.mark.setForeground(Color.RED);

    this.btnWFViewSubmitProccess.setVisible(false);
    this.menuItemViewSubmitProccess.setVisible(false);
    this.menuItemViewBudget.setIcon(EASResource.getIcon("imgTbtn_linkviewlist"));

    this.actionSubmitOption.setVisible(true);
    this.actionSubmitOption.setEnabled(true);
    this.chkMenuItemSubmitAndAddNew.setVisible(true);
    this.chkMenuItemSubmitAndAddNew.setEnabled(true);
    this.chkMenuItemSubmitAndPrint.setVisible(true);
    this.chkMenuItemSubmitAndPrint.setEnabled(true);

    if ((AMOUNTAPPROVED.equals(this.oprtState)) && (isFromWorkFlow())) {
      this.actionCopy.setEnabled(false);
    }

    this.kDLabelContainer26.setVisible(false);
    this.kDLabelContainer27.setVisible(false);
    this.kdtEntries.getColumn("amountUsed").getStyleAttributes().setHided(true);

    this.kdtEntries.getColumn("amountBalance").getStyleAttributes().setHided(true);

    if (AmountControlTypeEnum.oneTOmore.equals(this.editData.getAmountControlType()))
    {
      this.kDCheckAmountControl.setSelected(true);
    }
    else this.kDCheckAmountControl.setSelected(false);

    Boolean isRelationCreated = new Boolean(false);
    if (getUIContext().get("isRelationCreated") != null) {
      isRelationCreated = (Boolean)getUIContext().get("isRelationCreated");
    }
    String sourceId = this.editData.getSourceBillId();
    if (((OprtState.ADDNEW.equals(getOprtState())) && (getBOTPViewStatus() == 0) && (!isRelationCreated.booleanValue())) || ((sourceId != null) && (BizCollUtil.isEvectionReq(sourceId))))
    {
      if (this.isAllowOverApply)
        this.kDCheckAmountControl.setSelected(true);
      else {
        this.kDCheckAmountControl.setSelected(false);
      }
    }
    if ((sourceId != null) && (!BizCollUtil.isEvectionReq(sourceId))) {
      this.kDCheckAmountControl.setEnabled(false);
    }

    this.bizPromptApplierCompany.setEnabled(false);
    if ((this.isRelaFi) && (this.isShowLoanBalance)) {
      this.mark.setText(getAccount());
    }
    if (this.isNeedBudget) {
      if (!BizCollUtil.hasBgBalanceViewPermission(null)) {
        this.actionViewBudgetBalance.setVisible(false);
      } else {
        this.actionViewBudgetBalance.setVisible(true);
        this.actionViewBudgetBalance.setEnabled(true);
      }

      if (this.isShowBgAudit) {
        this.bizPromptCostedDept.addDataChangeListener(new DataChangeListener()
        {
          public void dataChanged(DataChangeEvent e)
          {
            try {
              DailyLoanBillEditUI.this.showBgAuditInLabel(e);
            } catch (Exception exc) {
              DailyLoanBillEditUI.this.handUIException(exc);
            }
            finally
            {
            }
          }
        });
        this.dateReqDate.addDataChangeListener(new DataChangeListener()
        {
          public void dataChanged(DataChangeEvent e)
          {
            try {
              DailyLoanBillEditUI.this.showBgAuditInLabel(e);
            } catch (Exception exc) {
              DailyLoanBillEditUI.this.handUIException(exc);
            } finally {
            }
          } } );
      }
    }
    else {
      this.actionViewBudgetBalance.setVisible(false);
    }
  }

  protected void initUIData()
  {
    this.FormattedTextFieldAmount.setDataType(1);
    this.FormattedTextFieldAmount.setPrecision(2);
    this.FormattedTextFieldAmount.setRemoveingZeroInDispaly(false);
    this.FormattedTextFieldAmountApproved.setDataType(1);

    this.FormattedTextFieldAmountApproved.setPrecision(2);
    this.FormattedTextFieldAmountApproved.setRemoveingZeroInDispaly(false);
    this.FormattedTextFieldAmountApproved.setHorizontalAlignment(4);

    this.txtAmountUsed.setDataType(1);
    this.txtAmountUsed.setPrecision(2);
    this.txtAmountUsed.setRemoveingZeroInDispaly(false);
    this.txtAmountUsed.setHorizontalAlignment(4);

    this.txtAmountBalance.setDataType(1);
    this.txtAmountBalance.setPrecision(2);
    this.txtAmountBalance.setRemoveingZeroInDispaly(false);
    this.txtAmountBalance.setHorizontalAlignment(4);

    if (AmountControlTypeEnum.oneTOmore.equals(this.editData.getAmountControlType()))
    {
      this.kdtEntries.getColumn("budgetamount").getStyleAttributes().setHided(true);
    }

    if ((this.editData.getState().getValue() >= 60) || (StateEnum.ALREADYVOUCHER.equals(this.editData.getState())))
    {
      this.kDLabelContainer26.setVisible(true);
      this.kDLabelContainer27.setVisible(true);
      this.kdtEntries.getColumn("amountUsed").getStyleAttributes().setHided(false);

      this.kdtEntries.getColumn("amountBalance").getStyleAttributes().setHided(false);
    }

    Boolean isRelationCreated = (Boolean)getUIContext().get("isRelationCreated");

    if (isRelationCreated == null) {
      try {
        prepareInitUIData().callHandler();
      }
      catch (Exception e1) {
        e1.printStackTrace();
      }
    }

    MakeControl.makeReqDepF7(this.bizPromptOrgUnit);

    MakeControl.makeApplierF7ByOrgUnit(this.bizPromptApplier, this.editData.getOrgUnit(), true);

    MakeControl.makeOperationTypeF7(this.bizPromptExpenseType, this);
    if (this.isShowBgAudit) {
      this.kdtEntries.getSelectManager().addKDTSelectListener(new KDTSelectListener()
      {
        public void tableSelectChanged(KDTSelectEvent e) {
          try {
            if (DailyLoanBillEditUI.this.isRowNoEqual(e))
              DailyLoanBillEditUI.this.showBgAuditInLabel(null);
          }
          catch (Exception exc) {
            DailyLoanBillEditUI.this.handUIException(exc);
          }
        }
      });
    }

    MakeControl.makeAccountF7(this.bizPromptExpenseTypeEntry, this);
    this.bizPromptExpenseTypeEntry.setEditable(true);
    ObjectValueRender avr = new ObjectValueRender();
    avr.setFormat(new BizDataFormat("$name$-$number$"));
    this.kdtEntries.getColumn(this.TB_EXPENSETYPE).setRenderer(avr);
    this.kdtEntries.getColumn(this.TB_EXPENSETYPE).setEditor(new KDTDefaultCellEditor(this.bizPromptExpenseTypeEntry));

    this.paramMap.put("CP019", new Boolean(this.isDelimitOrgScope));
    this.isEntrustedByCostCenter = MakeControl.makeCostCenterF7(this.bizPromptCostedDept, (CompanyOrgUnitInfo)this.bizPromptCompany.getData(), this.paramMap);

    MakeControl.makeCostTarget(this.bizPromptSupportedObj);

    MakeControl.makePayMode(this.bizPromptPayType);

    MakeControl.makeCompanyF7(this.bizPromptCompany, this, this.paramMap);

    MakeControl.makeCompanyF7(this.bizPromptApplierCompany, this, this.paramMap);

    String personId = null;
    if (this.editData.getApplier() != null) {
      personId = this.editData.getApplier().getId().toString();
    }
    MakeControl.makePosition(this.bizPromptPosition, this, personId);

    lockExpenseTypeByState();

    if (this.editData.getSourceBillId() != null) {
      this.bizPromptCompany.setEnabled(false);
      this.bizPromptCostedDept.setEnabled(false);
    }

    this.kdtEntries.addKDTEditListener(new KDTEditAdapter()
    {
      public void editStarting(KDTEditEvent e)
      {
        if (e.getColIndex() == DailyLoanBillEditUI.this.kdtEntries.getColumnIndex("expensetype")) {
          ExpenseTypePromptBox selector = (ExpenseTypePromptBox)DailyLoanBillEditUI.this.bizPromptExpenseTypeEntry.getSelector();

          if (DailyLoanBillEditUI.this.bizPromptExpenseType.getValue() != null) {
            selector.getUiContext().put("operationTypeId", ((OperationTypeInfo)DailyLoanBillEditUI.this.bizPromptExpenseType.getValue()).getId().toString());
          }
          else
          {
            selector.getUiContext().put("operationTypeId", null);
          }

          if (DailyLoanBillEditUI.this.bizPromptCompany.getData() != null) {
            String ln = ((CompanyOrgUnitInfo)DailyLoanBillEditUI.this.bizPromptCompany.getData()).getLongNumber();

            String[] lnSecs = ln.split("!");
            int size = lnSecs.length;
            HashSet lnUps = new HashSet();
            for (int i = 0; i < size; i++) {
              lnUps.add(lnSecs[i]);
            }
            selector.getUiContext().put("companyId", ((CompanyOrgUnitInfo)DailyLoanBillEditUI.this.bizPromptCompany.getData()).getId().toString());

            selector.getUiContext().put("companyLongNumber", ((CompanyOrgUnitInfo)DailyLoanBillEditUI.this.bizPromptCompany.getData()).getLongNumber());
          }
          else
          {
            selector.getUiContext().put("companyId", null);
          }
        }
      }
    });
  }

  private boolean getInitFlag() {
    return this.initFlag;
  }

  private void setInitFlag(boolean flag)
  {
    this.initFlag = flag;
  }

  private void initEntryEditor()
  {
    if (!getInitFlag())
    {
      if (this.isNeedBudget) {
        this.ctnEntry.addButton(this.btnShowBal);
        this.ctnEntry.addButton(this.btnSpace);
        this.btnShowBal.setVisible(false);
        this.btnSpace.setVisible(false);
      }

      JButton btnAddRuleNew = this.ctnEntry.add(this.actionAddLine);
      JButton btnDelRuleNew = this.ctnEntry.add(this.actionRemoveLine);

      btnAddRuleNew.setIcon(EASResource.getIcon("imgTbtn_addline"));
      btnAddRuleNew.setToolTipText(this.btnAddLine.getToolTipText());
      btnAddRuleNew.setSize(22, 19);
      btnDelRuleNew.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
      btnDelRuleNew.setToolTipText(this.btnRemoveLine.getToolTipText());
      btnDelRuleNew.setSize(22, 19);

      setInitFlag(true);
    }

    KDDatePicker date = new KDDatePicker();
    date.setEditable(true);

    KDTDefaultCellEditor numberCellEditor = new KDTDefaultCellEditor(BizCollUtil.getMoneyEditor());

    getDetailTable().getColumn(this.TB_AMOUNT).setEditor(numberCellEditor);
    getDetailTable().getColumn(this.TB_AMOUNT).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);

    getDetailTable().getColumn(this.TB_AMOUNTAPPROVED).setEditor(numberCellEditor);

    getDetailTable().getColumn(this.TB_AMOUNTAPPROVED).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);

    this.kdtEntries.getColumn(this.TB_AMOUNT).getStyleAttributes().setBackground(this.backGroundcolor);

    this.kdtEntries.getColumn(this.TB_PURPOSE).getStyleAttributes().setBackground(this.backGroundcolor);

    this.kdtEntries.getColumn(this.TB_BUDGETAMOUNT).getStyleAttributes().setLocked(true);
  }

  private void lockExpenseTypeByState()
  {
    for (int i = 0; i < getDetailTable().getRowCount(); i++)
      if (AmountControlTypeEnum.oneTOone.equals(this.editData.getAmountControlType()))
      {
        if (getDetailTable().getRow(i).getCell(this.TB_BUDGETAMOUNT).getValue() == null)
          continue;
        if (getDetailTable().getRow(i).getCell(this.TB_EXPENSETYPE).getValue() == null)
          continue;
        getDetailTable().getRow(i).getCell(this.TB_EXPENSETYPE).getStyleAttributes().setLocked(true);
      }
      else
      {
        if (!AmountControlTypeEnum.oneTOmore.equals(this.editData.getAmountControlType()))
          continue;
        if ((this.editData.getSourceBillId() == null) || 
          (getDetailTable().getRow(i).getCell(this.TB_EXPENSETYPE).getValue() == null))
          continue;
        getDetailTable().getRow(i).getCell(this.TB_EXPENSETYPE).getStyleAttributes().setLocked(true);
      }
  }

  protected void dateYjBack_dataChanged(DataChangeEvent e)
    throws Exception
  {
    if ((this.dateYjBack.getValue() != null) && (this.dateYjBack.getSqlDate().before(this.dateReqDate.getSqlDate())))
    {
      this.dateYjBack.setValue(this.dateReqDate.getValue());
    }
  }

  protected void beforeStoreFields(ActionEvent e)
    throws Exception
  {
    if ((e != null) && ((this.btnSubmit.equals(e.getSource())) || (this.menuItemSubmit.equals(e.getSource()))))
    {
      this.FormattedTextFieldAmount.setValue(calculateTotalAmount(getTotalRow(this.kdtEntries)));

      if (this.txtName.getText().trim().length() > 80) {
        this.txtName.requestFocus();
        showErrorMessage("dailyLoanNameTooLong");
      }

      if (BizCollUtil.objectIsNull(this.bizPromptApplier.getData())) {
        this.bizPromptApplier.requestFocus();
        showErrorMessage("dailyLoanApplierNotNull");
      }

      if (!StringUtils.isEmpty(this.txtNumber.getText()))
      {
        if (this.txtNumber.getText().trim().length() > 80) {
          this.txtNumber.requestFocus();
          showErrorMessage("dailyLoanNumberTooLong");
        }
      }

      if (BizCollUtil.objectIsNull(this.dateReqDate.getValue())) {
        this.dateReqDate.requestFocus();
        showErrorMessage("dailyLoanReqDateNotNull");
      }

      if (BizCollUtil.objectIsNull(this.bizPromptOrgUnit.getData())) {
        this.bizPromptOrgUnit.requestFocus();
        showErrorMessage("dailyLoanOrgUnitNotNull");
      }

      if (BizCollUtil.objectIsNull(this.bizPromptCompany.getData())) {
        this.bizPromptCompany.requestFocus();
        showErrorMessage("dailyLoanCompanyNotNull");
      }

      if (!BizCollUtil.isBizUnitCompany((CompanyOrgUnitInfo)this.bizPromptCompany.getData()))
      {
        this.bizPromptCompany.requestFocus();
        throw new ExpAccException(ExpAccException.ISBIZUNIT);
      }

      if (BizCollUtil.objectIsNull(this.bizPromptApplierCompany.getData()))
      {
        this.bizPromptApplierCompany.requestFocus();
        throw new ExpAccException(ExpAccException.A_COMPANY_NOTNULL);
      }

      if (!BizCollUtil.isBizUnitCompany((CompanyOrgUnitInfo)this.bizPromptApplierCompany.getData()))
      {
        this.bizPromptApplierCompany.requestFocus();
        throw new ExpAccException(ExpAccException.A_COMPANY_NOTBIZUNIT);
      }

      if (BizCollUtil.objectIsNull(this.bizPromptCostedDept.getData())) {
        this.bizPromptCostedDept.requestFocus();
        showErrorMessage("dailyLoanCostedDeptNotNull");
      }

      if (this.bizPromptPayType.getData() == null) {
        this.bizPromptPayType.requestFocus();
        showErrorMessage("payModeNotNull");
      }

      if ((this.bizPromptCostedDept.getData() instanceof FullOrgUnitInfo)) {
        ICostCenterOrgUnit iCost = CostCenterOrgUnitFactory.getRemoteInstance();

        BOSUuid ids = ((FullOrgUnitInfo)this.bizPromptCostedDept.getData()).getId();

        CostCenterOrgUnitInfo cost = (CostCenterOrgUnitInfo)iCost.getValue(new ObjectUuidPK(ids));

        this.bizPromptCostedDept.setData(cost);
      }

      if (BizCollUtil.objectIsNull(this.comboPrior.getSelectedItem())) {
        this.comboPrior.requestFocus();
        showErrorMessage("dailyLoanPriorNotNull");
      }

      if (BizCollUtil.objectIsNull(this.bizPromptCurrencyType.getData())) {
        this.bizPromptCurrencyType.requestFocus();
        showErrorMessage("dailyLoanCurrencyTypeNotNull");
      }

      if (BizCollUtil.objectIsNull(this.dateBillDate.getValue())) {
        this.dateBillDate.requestFocus();
        showErrorMessage("dailyLoanBillDateNotNull");
      }

      if (BizCollUtil.objectIsNull(this.bizPromptBiller.getData())) {
        this.bizPromptBiller.requestFocus();
        showErrorMessage("dailyLoanBillerNotNull");
      }

      if (StringUtils.isEmpty(this.FormattedTextFieldAmount.getText())) {
        this.FormattedTextFieldAmount.requestFocus();
        showErrorMessage("dailyLoanAmountNotNull");
      }

      if (BizCollUtil.bigDecimalObjectLessEqual(BizCollUtil.MAX, this.FormattedTextFieldAmount.getBigDecimalValue()))
      {
        throw new BizCollException(BizCollException.AMOUNT_TOO_LG);
      }

      if (BizCollUtil.bigDecimalObjectLessEqual(BizCollUtil.MAX, this.FormattedTextFieldAmountApproved.getBigDecimalValue()))
      {
        throw new BizCollException(BizCollException.AMOUNT_TOO_LG);
      }

      if ((!StringUtils.isEmpty(this.kDTextAreaCause.getText())) && (this.kDTextAreaCause.getText().trim().length() > 200))
      {
        this.kDTextAreaCause.requestFocus();
        showErrorMessage("dailyLoanCauseTooLong");
      }

      if ((!StringUtils.isEmpty(this.kDTextAreaDescription.getText())) && (this.kDTextAreaDescription.getText().trim().length() > 200))
      {
        this.kDTextAreaDescription.requestFocus();
        showErrorMessage("dailyLoanDescriptionTooLong");
      }

      checkEntry();
    } else if ((AMOUNTAPPROVED.equals(this.oprtState)) && (isFromWorkFlow()))
    {
      checkEntry();
    }
    super.beforeStoreFields(e);
  }

  protected void addLine(KDTable table)
  {
    IObjectValue detailData = createNewDetailData(table);
    IRow row = null;

    if (table.getRowCount() > 0)
    {
      if (checkIsCountLine(table.getRow(table.getRowCount() - 1)))
        row = table.addRow(table.getRowCount() - 1);
      else
        row = table.addRow();
    }
    else {
      row = table.addRow();
    }
    loadLineFields(table, row, detailData);
    afterAddLine(table, detailData);
  }

  protected void afterRemoveLine(KDTable table, IObjectValue lineData)
  {
    recountAmount();
  }

  public void actionRemoveLine_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (getDetailTable().getSelectManager().size() == 0) {
      MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));

      return;
    }

    int top = getDetailTable().getSelectManager().get().getTop();

    if (getDetailTable().getRow(top) == null) {
      MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));

      return;
    }

    if (checkIsCountLine(getDetailTable().getRow(top))) {
      return;
    }

    int bottom = getDetailTable().getSelectManager().get().getEndRow();
    for (int i = top; i <= bottom; i++) {
      BigDecimal budgetAmount = (BigDecimal)getDetailTable().getRow(i).getCell(this.TB_BUDGETAMOUNT).getValue();

      if ((budgetAmount != null) && (budgetAmount.intValue() > 0)) {
        String s = EASResource.getString("com.kingdee.eas.cp.bc.BizCollResource", "RELATIONSHIPBILL_CANNOT_REMOVE");

        MsgBox.showError(this, s);
        setCursorOfDefault();
        SysUtil.abort();
      }
    }
    super.actionRemoveLine_actionPerformed(e);
  }

  protected void removeLine(KDTable table)
  {
    if (table == null) {
      return;
    }

    if (table.getSelectManager().size() == 0) {
      MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));

      return;
    }

    if (confirmRemove()) {
      int top = table.getSelectManager().get().getBeginRow();
      int bottom = table.getSelectManager().get().getEndRow();
      for (int i = top; i <= bottom; i++) {
        if (table.getRow(top) == null) {
          MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));

          return;
        }

        IObjectValue detailData = (IObjectValue)table.getRow(top).getUserObject();

        if (checkIsCountLine(table.getRow(top))) {
          continue;
        }
        table.removeRow(top);
        IObjectCollection collection = (IObjectCollection)table.getUserObject();

        if (collection != null)
        {
          if (detailData != null) {
            collection.removeObject(detailData);
          }
        }
        afterRemoveLine(table, detailData);
      }
    }
  }

  public void actionCopy_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionCopy_actionPerformed(e);

    this.bizPromptApplierCompany.setEnabled(false);
    this.bizPromptCostedDept.setEnabled(true);
    this.bizPromptCompany.setEnabled(true);
  }

  protected void setFieldsNull(AbstractObjectValue newData)
  {
    super.setFieldsNull(newData);
    DailyLoanBillInfo data = null;
    DailyLoanBillEntryInfo entryInfo = null;
    if ((newData != null) && ((newData instanceof DailyLoanBillInfo)))
      data = (DailyLoanBillInfo)newData;
    else {
      return;
    }

    data.setBizReqDate(this.serverDate);
    data.setAuditor(null);
    data.setBiller(SysContext.getSysContext().getCurrentUserInfo());

    data.setBillDate(this.serverDate);
    data.setAuditDate(null);
    data.setLastUpdateTime(null);
    data.setLastUpdateUser(null);
    data.setAmountBalance(null);
    data.setAmountUsed(null);
    data.setLoanState("N");

    data.setSourceBillId(null);

    data.setState(StateEnum.NEW);
    data.setName(data.getName() + "(" + EASResource.getString("com.kingdee.eas.cp.bc.BizCollResource", "copy") + ")");

    DailyLoanBillEntryCollection entries = data.getEntries();
    if (entries == null) {
      return;
    }
    for (int i = 0; i < entries.size(); i++) {
      entryInfo = entries.get(i);
      entryInfo.setBudgetAmount(null);
      entryInfo.setAmountBalance(null);
      entryInfo.setAmountUsed(null);
    }

    setFaceForAmountControl();
  }

  private void setFaceForAmountControl() {
    this.kDCheckAmountControl.setEnabled(true);
    if ((StateEnum.CHECKED.equals(this.editData.getState())) || (StateEnum.CLOSED.equals(this.editData.getState())))
    {
      this.kDLabelContainer26.setVisible(true);
      this.kDLabelContainer27.setVisible(true);
      this.kdtEntries.getColumn("amountUsed").getStyleAttributes().setHided(false);

      this.kdtEntries.getColumn("amountBalance").getStyleAttributes().setHided(false);
    }
    else {
      this.kDLabelContainer26.setVisible(false);
      this.kDLabelContainer27.setVisible(false);
      this.kdtEntries.getColumn("amountUsed").getStyleAttributes().setHided(true);

      this.kdtEntries.getColumn("amountBalance").getStyleAttributes().setHided(true);
    }
  }

  public void setBeforeCopy()
  {
    if (isExistsCodingRule(this.editData)) {
      getNumberByCodingRule(this.editData, this.editData.getCompany().getId().toString());

      this.txtNumber.setEditable(false);
    } else {
      this.txtNumber.setText(null);
      this.txtNumber.setEditable(true);
    }
    java.util.Date date = new java.util.Date();
    this.dateReqDate.setValue(date);
    this.bizPromptUpdator.setText(null);
    this.bizPromptBiller.setText(null);
    this.dateUpdateDate.setValue(null);

    storeFields();
  }

  public void setAfterCopy() {
    if (!isExistsCodingRule(this.editData)) {
      this.txtNumber.setEditable(true);
      this.txtNumber.setPreferredSize(new Dimension(168, 19));
      this.txtNumber.setRequired(true);
      this.txtNumber.setMaxLength(60);
    }
  }

  private void addTotalLine(KDTable table)
  {
    addLine(table);
    IRow row = table.getRow(table.getRowCount() - 1);
    row.getCell(this.TB_EXPENSETYPE).setValue(EASResource.getString(this.RES, "count"));

    row.getCell("flag").setValue("count");
    row.getStyleAttributes().setLocked(true);
    row.getStyleAttributes().setBackground(new Color(249, 247, 242));
  }

  private void checkEntry()
    throws Exception
  {
    int rows = -1;

    if (getDetailTable() == null)
      rows = -1;
    else {
      rows = getDetailTable().getRowCount();
    }
    if (rows <= 0) {
      showErrorMessage("entryNotNull");
    }
    KDTEditManager kdtManager = getDetailTable().getEditManager();
    IRow row = null;
    Set expenseTypeSet = new HashSet();
    DailyLoanBillEntryInfo entryInfo = null;
    int count = 0;

    for (int i = 0; i < rows; i++) {
      row = getDetailTable().getRow(i);

      if (checkIsCountLine(row))
      {
        continue;
      }
      entryInfo = new DailyLoanBillEntryInfo();
      storeLineFields2(getDetailTable(), row, entryInfo);
      if (checkIsBlankLine(entryInfo))
      {
        continue;
      }
      ExpenseTypeInfo expenseTypeInfo = (ExpenseTypeInfo)row.getCell(this.TB_EXPENSETYPE).getValue();

      if (expenseTypeInfo == null) {
        kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_EXPENSETYPE));

        showErrorMessage("dailyLoanExpenseTypeNotNull");
      }

      if ((expenseTypeInfo != null) && (expenseTypeInfo.getId() != null)) {
        String key = expenseTypeInfo.getId().toString();
        if (expenseTypeSet.contains(key)) {
          throw new ExpAccException(ExpAccException.EXPENSETYPE_IS_DUPLICATE);
        }

        expenseTypeSet.add(key);
      }

      if (BizCollUtil.objectIsNull(row.getCell(this.TB_PURPOSE).getValue())) {
        kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_PURPOSE));

        showErrorMessage("dailyLoanPurposeNotNull");
      } else if (row.getCell(this.TB_PURPOSE).getValue().toString().trim().length() > 200)
      {
        kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_PURPOSE));

        showErrorMessage("dailyLoanPurposeTooLong");
      }

      if (!BizCollUtil.objectIsNull(row.getCell("particiants").getValue()))
      {
        if (row.getCell("particiants").getValue().toString().trim().length() > 80)
        {
          kdtManager.editCellAt(i, getDetailTable().getColumnIndex("particiants"));

          showErrorMessage("participants_tooLong");
        }
      }

      BigDecimal amount = (BigDecimal)row.getCell(this.TB_AMOUNT).getValue();

      String sourceId = this.editData.getSourceBillId();
      if ((sourceId != null) && (!BizCollUtil.isEvectionReq(sourceId))) {
        if ((amount == null) || (amount.compareTo(new BigDecimal("0.00")) < 0)) {
          kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_AMOUNT));

          throw new ExpAccException(ExpAccException.AMOUNTNOTLESSTHENZERO);
        }
      }
      else if ((amount == null) || (amount.compareTo(new BigDecimal("0.00")) <= 0)) {
        kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_AMOUNT));

        throw new ExpenseReqException(ExpenseReqException.NEGTIVE_ENTRY_AMOUNT_ERROR);
      }

      BigDecimal amountApproved = (BigDecimal)row.getCell(this.TB_AMOUNTAPPROVED).getValue();

      if ((amountApproved != null) && (amountApproved.compareTo(new BigDecimal("0.00")) < 0)) {
        kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_AMOUNTAPPROVED));

        throw new ExpAccException(ExpAccException.APPROVEDACOUNT_NOTNULL);
      }

      if ((AMOUNTAPPROVED.equals(this.oprtState)) && (isFromWorkFlow()) && (
        (amountApproved == null) || (amountApproved.intValue() < 0))) {
        kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_AMOUNTAPPROVED));

        throw new ExpAccException(ExpAccException.APPROVEDACOUNT_NOTNULL);
      }

      if (!BizCollUtil.bigDecimalObjectLessThan(row.getCell(this.TB_AMOUNTAPPROVED).getValue(), amount))
      {
        kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_AMOUNTAPPROVED));

        throw new ExpAccException(ExpAccException.APPROVED_GT_AMOUNT);
      }

      if ((!BizCollUtil.objectIsNull(row.getCell(this.TB_COMMENT).getValue())) && (row.getCell(this.TB_COMMENT).getValue().toString().trim().length() > 1000))
      {
        kdtManager.editCellAt(i, getDetailTable().getColumnIndex(this.TB_COMMENT));

        showErrorMessage("dailyLoanCommentTooLong");
      }

      count++;
    }

    if (count <= 0)
      showErrorMessage("entryNotNull");
  }

  private boolean checkIsBlankLine(AbstractObjectValue objValue)
  {
    return ObjectValueUtil.objectValueEquals((AbstractObjectValue)createNewDetailData(this.kdtEntries), objValue);
  }

  private boolean checkIsCountLine(IRow row)
  {
    if (row == null) {
      return false;
    }
    if (row.getCell(this.TB_EXPENSETYPE).getValue() == null) {
      return false;
    }

    Object value = row.getCell("flag").getValue();
    if (BizCollUtil.objectIsNull(value)) {
      return false;
    }
    return "count".equals(value.toString());
  }

  private void initDefaultLines(KDTable table) {
    if (table == null) {
      return;
    }
    if (!hasTotalLine(this.kdtEntries)) {
      addTotalLine(this.kdtEntries);
    }

    for (int i = table.getRowCount(); i <= this.DEFAULTLINES; i++) {
      addLine(table);
    }

    IRow row = getTotalRow(this.kdtEntries);
    calculateColumnAmount(row, this.kdtEntries.getColumnIndex(this.TB_BUDGETAMOUNT));
  }

  public SelectorItemCollection getSelectors()
  {
    SelectorItemCollection sic = new SelectorItemCollection();
    sic = super.getSelectors();
    sic.add(new SelectorItemInfo("name"));
    sic.add(new SelectorItemInfo("number"));
    sic.add(new SelectorItemInfo("applier.id"));
    sic.add(new SelectorItemInfo("applier.number"));
    sic.add(new SelectorItemInfo("applier.name"));
    sic.add(new SelectorItemInfo("orgUnit.number"));
    sic.add(new SelectorItemInfo("orgUnit.id"));
    sic.add(new SelectorItemInfo("orgUnit.name"));
    sic.add(new SelectorItemInfo("position.id"));
    sic.add(new SelectorItemInfo("position.number"));
    sic.add(new SelectorItemInfo("position.name"));
    sic.add(new SelectorItemInfo("company.id"));
    sic.add(new SelectorItemInfo("company.number"));
    sic.add(new SelectorItemInfo("company.name"));
    sic.add(new SelectorItemInfo("company.longNumber"));
    sic.add(new SelectorItemInfo("prior"));
    sic.add(new SelectorItemInfo("currencyType.id"));
    sic.add(new SelectorItemInfo("currencyType.number"));
    sic.add(new SelectorItemInfo("currencyType.name"));
    sic.add(new SelectorItemInfo("costedDept.id"));
    sic.add(new SelectorItemInfo("costedDept.number"));
    sic.add(new SelectorItemInfo("costedDept.name"));
    sic.add(new SelectorItemInfo("amount"));
    sic.add(new SelectorItemInfo("amountApproved"));
    sic.add(new SelectorItemInfo("amountUsed"));
    sic.add(new SelectorItemInfo("amountBalance"));
    sic.add(new SelectorItemInfo("supportedObj.id"));
    sic.add(new SelectorItemInfo("supportedObj.number"));
    sic.add(new SelectorItemInfo("supportedObj.name"));
    sic.add(new SelectorItemInfo("expenseType.id"));
    sic.add(new SelectorItemInfo("expenseType.number"));
    sic.add(new SelectorItemInfo("expenseType.typeName"));
    sic.add(new SelectorItemInfo("billDate"));
    sic.add(new SelectorItemInfo("biller.id"));
    sic.add(new SelectorItemInfo("biller.number"));
    sic.add(new SelectorItemInfo("biller.name"));
    sic.add(new SelectorItemInfo("loanType"));
    sic.add(new SelectorItemInfo("bizReqDate"));
    sic.add(new SelectorItemInfo("tel"));
    sic.add(new SelectorItemInfo("auditor.id"));
    sic.add(new SelectorItemInfo("auditor.number"));
    sic.add(new SelectorItemInfo("auditor.name"));
    sic.add(new SelectorItemInfo("auditDate"));
    sic.add(new SelectorItemInfo("lastUpdateUser.id"));
    sic.add(new SelectorItemInfo("lastUpdateUser.number"));
    sic.add(new SelectorItemInfo("lastUpdateUser.name"));
    sic.add(new SelectorItemInfo("payMode.number"));
    sic.add(new SelectorItemInfo("payMode.name"));
    sic.add(new SelectorItemInfo("lastUpdateTime"));

    sic.add(new SelectorItemInfo("entries.*"));
    sic.add(new SelectorItemInfo("entries.expenseType.id"));
    sic.add(new SelectorItemInfo("entries.expenseType.number"));
    sic.add(new SelectorItemInfo("entries.expenseType.typeName"));

    sic.add(new SelectorItemInfo("cause"));
    sic.add(new SelectorItemInfo("description"));
    sic.add(new SelectorItemInfo("foreseeDate"));
    sic.add(new SelectorItemInfo("state"));
    sic.add(new SelectorItemInfo("loanState"));
    sic.add(new SelectorItemInfo("sourceBillId"));
    sic.add(new SelectorItemInfo("creator"));
    sic.add(new SelectorItemInfo("applierCompany.*"));
    sic.add(new SelectorItemInfo("amountControlType"));
    return sic;
  }

  protected void bizPromptApplier_dataChanged(DataChangeEvent e)
    throws Exception
  {
    if ((this.isLoadFieldAction) && (getUIContext().get("CP_BC_ADDNEW_CONTINOUSLY") == null)) {
      return;
    }
    if (BizCollUtil.objectIsNull(this.bizPromptApplier.getValue())) {
      return;
    }

    if (e.getNewValue() != e.getOldValue()) {
      PersonInfo applier = (PersonInfo)e.getNewValue();
      if (this.bizPromptOrgUnit.getData() != null) {
        if ((applier != null) && (applier.getId() != null) && (((OrgUnitInfo)this.bizPromptOrgUnit.getData()).getId() != null)) {
          String orgUnitId = ((OrgUnitInfo)this.bizPromptOrgUnit.getData()).getId().toString();
          boolean isIn = MakeControl.isPersonInOrgUnit(applier.getId().toString(), orgUnitId);
          if (isIn) {
            BizCollBillBaseInfo baseInfo = CommonUtilFacadeFactory.getRemoteInstance().forLoanBillApplierActionByOrgUnitId(applier, orgUnitId);
            this.bizPromptPosition.setValue(baseInfo.getPosition());
          }
        }
      }
      else {
        PersonInfo pInfo = (PersonInfo)this.bizPromptApplier.getValue();
        BizCollBillBaseInfo baseInfo = CommonUtilFacadeFactory.getRemoteInstance().forLoanBillApplierAction(pInfo);

        this.bizPromptPosition.setValue(baseInfo.getPosition());

        this.bizPromptOrgUnit.setValue(baseInfo.getOrgUnit());

        String personId = null;
        if (this.bizPromptApplier.getData() != null) {
          PersonInfo personInfo = (PersonInfo)this.bizPromptApplier.getData();
          personId = personInfo.getId().toString();
        }
        MakeControl.makePosition(this.bizPromptPosition, this, personId);
      }
    }

    if ((this.isRelaFi) && (this.isShowLoanBalance))
      this.mark.setText(getAccount());
  }

  protected void bizPromptCostedDept_dataChanged(DataChangeEvent e)
    throws Exception
  {
    if ((this.isLoadFieldAction) && (getUIContext().get("CP_BC_ADDNEW_CONTINOUSLY") == null)) {
      return;
    }

    if (this.isEntrustedByCostCenter) {
      return;
    }

    if ((this.bizPromptCompany.getData() == null) && (this.bizPromptCostedDept.getData() == null)) {
      return;
    }

    if (("ADDNEW".equals(getOprtState())) && (this.editData.getSourceBillId() == null))
    {
      CostCenterOrgUnitInfo costInfo = (CostCenterOrgUnitInfo)this.bizPromptCostedDept.getValue();
      BizCollBillBaseInfo baseInfo;
      try
      {
        baseInfo = CommonUtilFacadeFactory.getRemoteInstance().forLoanBillCostCenterAction(costInfo);
      }
      catch (EASBizException e1)
      {
        
        this.bizPromptCompany.setValue(null);
        this.bizPromptCostedDept.setValue(null);
        this.bizPromptCostedDept.requestFocus();
        throw e1;
      }
      if (baseInfo != null) {
        this.bizPromptCompany.setValue(baseInfo.getCompany());
      }
    }

    if ((this.isRelaFi) && (this.isShowLoanBalance))
      this.mark.setText(getAccount());
  }

  public void bizPromptOrgUnit_dataChanged(DataChangeEvent e)
    throws Exception
  {
    if ((this.isLoadFieldAction) && (getUIContext().get("CP_BC_ADDNEW_CONTINOUSLY") == null)) {
      return;
    }
    if (("ADDNEW".equals(getOprtState())) || ("EDIT".equals(getOprtState())))
    {
      AdminOrgUnitInfo adminInfo = (AdminOrgUnitInfo)this.bizPromptOrgUnit.getValue();

      BizCollBillBaseInfo baseInfo = CommonUtilFacadeFactory.getRemoteInstance().forBillAdminOrgUnitAction(adminInfo);

      this.bizPromptCostedDept.setValue(baseInfo.getCostedDept());
      this.bizPromptCompany.setValue(baseInfo.getCompany());

      MakeControl.makeApplierF7ByOrgUnit(this.bizPromptApplier, (OrgUnitInfo)e.getNewValue());
    }

    if ((this.isRelaFi) && (this.isShowLoanBalance))
      this.mark.setText(getAccount());
  }

  private void bizPromptCurrencyType_dataChanged(DataChangeEvent e)
    throws Exception
  {
    if ((this.isLoadFieldAction) && (getUIContext().get("CP_BC_ADDNEW_CONTINOUSLY") == null)) {
      return;
    }

    if ((this.isRelaFi) && (this.isShowLoanBalance))
      this.mark.setText(getAccount());
  }

  protected void kdtEntries_propertyChange(KDTPropertyChangeEvent e)
    throws Exception
  {
    if (checkIsCountLine(this.kdtEntries.getRow(e.getRowIndex()))) {
      return;
    }

    if (e.getColIndex() == this.kdtEntries.getColumnIndex(this.TB_AMOUNT))
    {
      if (this.isInitAmountApproved) {
        Object amount = this.kdtEntries.getRow(e.getRowIndex()).getCell(this.TB_AMOUNT).getValue();

        Object amountApproved = this.kdtEntries.getRow(e.getRowIndex()).getCell(this.TB_AMOUNTAPPROVED).getValue();

        if (((amount == null) && (amountApproved != null)) || ((amount != null) && (amountApproved == null)))
        {
          this.isAmountApprovedCaculated = true;
        } else if ((amount != null) && (amountApproved != null) && (!amount.equals(amountApproved)))
        {
          this.isAmountApprovedCaculated = true;
        }
        if (this.isAmountApprovedCaculated) {
          this.kdtEntries.getRow(e.getRowIndex()).getCell(this.TB_AMOUNTAPPROVED).setValue(e.getNewValue());
        }

      }
      else if (e.getNewValue() == null) {
        Object amountApproved = this.kdtEntries.getRow(e.getRowIndex()).getCell(this.TB_AMOUNTAPPROVED).getValue();

        if (amountApproved != null)
        {
          this.isAmountApprovedCaculated = true;
          this.kdtEntries.getRow(e.getRowIndex()).getCell(this.TB_AMOUNTAPPROVED).setValue(e.getNewValue());
        }

      }

      calculateColumnAmount(getTotalRow(this.kdtEntries), e.getColIndex());

      this.FormattedTextFieldAmount.setValue(calculateTotalAmount(getTotalRow(this.kdtEntries)));

      if (getTotalRow(this.kdtEntries) != null) {
        calculateColumnAmount(getTotalRow(this.kdtEntries), this.kdtEntries.getColumnIndex(this.TB_AMOUNTAPPROVED));

        this.FormattedTextFieldAmountApproved.setValue(getTotalRow(this.kdtEntries).getCell(this.TB_AMOUNTAPPROVED).getValue());
      }

    }
    else if ((!this.isAmountApprovedCaculated) && (e.getColIndex() == this.kdtEntries.getColumnIndex(this.TB_AMOUNTAPPROVED)))
    {
      if (getTotalRow(this.kdtEntries) != null) {
        calculateColumnAmount(getTotalRow(this.kdtEntries), e.getColIndex());
        this.FormattedTextFieldAmountApproved.setValue(getTotalRow(this.kdtEntries).getCell(this.TB_AMOUNTAPPROVED).getValue());
      }
    }
    else {
      this.isAmountApprovedCaculated = false;
    }
    if (e.getColIndex() == this.kdtEntries.getColumnIndex(this.TB_EXPENSETYPE))
      showBgAuditInLabel(null);
  }

  private void recountAmount()
  {
    IRow row = getTotalRow(this.kdtEntries);
    calculateColumnAmount(row, this.kdtEntries.getColumnIndex(this.TB_AMOUNT));
    calculateColumnAmount(row, this.kdtEntries.getColumnIndex(this.TB_AMOUNTAPPROVED));

    this.FormattedTextFieldAmount.setValue(calculateTotalAmount(getTotalRow(this.kdtEntries)));

    if ((getTotalRow(this.kdtEntries) != null) && (getTotalRow(this.kdtEntries).getCell(this.TB_AMOUNTAPPROVED) != null))
    {
      this.FormattedTextFieldAmountApproved.setValue(getTotalRow(this.kdtEntries).getCell(this.TB_AMOUNTAPPROVED).getValue());
    }
  }

  private IRow getTotalRow(KDTable table)
  {
    int n = table.getRowCount(); for (int i = n - 1; i > -1; i--) {
      IRow row = table.getRow(i);
      if (checkIsCountLine(row)) {
        return row;
      }
    }
    return null;
  }

  private boolean hasTotalLine(KDTable table)
  {
    if (table == null) {
      return false;
    }

    return getTotalRow(table) != null;
  }

  private void calculateColumnAmount(IRow countRow, int columnIndex)
  {
    if (countRow == null) {
      return;
    }
    KDTable table = getDetailTable();

    IRow row = null;
    ICell cell = null;
    BigDecimal columnAmount = new BigDecimal("0.00");
    BigDecimal amount = null;
    int i = 0; for (int n = table.getRowCount(); i < n; i++) {
      row = table.getRow(i);

      if (checkIsCountLine(row)) {
        continue;
      }
      cell = row.getCell(columnIndex);
      Object value = cell.getValue();

      amount = BizCollUtil.toBigDecimal(value);
      if (amount != null) {
        columnAmount = columnAmount.add(amount);
      }
    }

    countRow.getCell(columnIndex).setValue(columnAmount);
  }

  private BigDecimal calculateTotalAmount(IRow countRow)
  {
    BigDecimal totalAmount = new BigDecimal("0.0");
    if (countRow == null) {
      return totalAmount;
    }
    BigDecimal amount = BizCollUtil.toBigDecimal(countRow.getCell(this.TB_AMOUNT).getValue());

    if (amount != null) {
      return amount;
    }

    return totalAmount;
  }

  public void afterActionPerformed(ActionEvent e) {
    super.afterActionPerformed(e);
    initDefaultLines(this.kdtEntries);
  }

  protected void beforeSubmit()
    throws Exception
  {
    BigDecimal amountApproved = this.editData.getAmountApproved() == null ? ZERO : this.editData.getAmountApproved();

    BigDecimal amountTotal = this.editData.getAmount() == null ? ZERO : this.editData.getAmount();

    if ((AMOUNTAPPROVED.equals(this.oprtState)) && (isFromWorkFlow()) && (amountApproved.compareTo(new BigDecimal(0.0D)) < 0))
    {
      this.FormattedTextFieldAmountApproved.requestFocus();
      throw new ExpAccException(ExpAccException.APPROVEDACOUNT_NOTNULL);
    }
    if (amountApproved.compareTo(amountTotal) > 0) {
      this.FormattedTextFieldAmountApproved.requestFocus();
      throw new ExpAccException(ExpAccException.APPROVED_GT_AMOUNT);
    }

    if (BizCollUtil.bigDecimalObjectLessEqual(this.editData.getAmount(), null))
      throw new ExpAccException(ExpAccException.AMOUNT_GT_ZERO);
  }

  public void checkPassBgAudit()
    throws EASBizException, BOSException
  {
    int rows = -1;

    if (getDetailTable() == null)
      rows = -1;
    else {
      rows = getDetailTable().getRowCount();
    }
    if (rows <= 0)
      showErrorMessage("entryNotNull");
    IRow row = null;
    DailyLoanBillEntryInfo entryInfo = null;
    int count = 0;
    List entryList = new ArrayList();
    List list = null;
    for (int i = 0; i < rows; i++) {
      row = getDetailTable().getRow(i);

      if (checkIsCountLine(row))
      {
        continue;
      }
      entryInfo = new DailyLoanBillEntryInfo();
      storeLineFields2(getDetailTable(), row, entryInfo);
      if (checkIsBlankLine(entryInfo))
      {
        continue;
      }
      ExpenseTypeInfo expenseTypeInfo = (ExpenseTypeInfo)row.getCell(this.TB_EXPENSETYPE).getValue();

      if (this.isNeedBudget)
      {
        if ((this.editData.getState().getValue() <= 25) && 
          (!BizCollUtil.objectIsNull(this.dateReqDate.getValue())) && (this.bizPromptCostedDept.getData() != null) && (this.bizPromptCurrencyType.getData() != null) && (this.bizPromptCompany.getData() != null))
        {
          BizCollBillBaseInfo baseInfo = (BizCollBillBaseInfo)this.editData.clone();

          baseInfo.setBizReqDate(this.dateReqDate.getSqlDate());

          baseInfo.setCostedDept((CostCenterOrgUnitInfo)this.bizPromptCostedDept.getData());

          baseInfo.setCurrencyType((CurrencyInfo)this.bizPromptCurrencyType.getData());

          baseInfo.setCompany((CompanyOrgUnitInfo)this.bizPromptCompany.getData());

          BigDecimal entryAmount = (BigDecimal)row.getCell(this.TB_AMOUNT).getValue();

          BigDecimal entryBugdetAmount = (BigDecimal)row.getCell(this.TB_BUDGETAMOUNT).getValue();

          if (entryBugdetAmount != null) {
            entryAmount = entryAmount.subtract(entryBugdetAmount);
          }

          baseInfo.setAmount(entryAmount);
          list = new ArrayList();
          list.add(baseInfo);
          list.add(expenseTypeInfo);
          entryList.add(list);
        }

      }

      count++;
    }
    ExpenseTypeInfo expInfo = CommonUtilFacadeFactory.getRemoteInstance().isPassBgAudit(entryList);

    if (expInfo != null) {
      String s = EASResource.getString("com.kingdee.eas.cp.bc.BizCollResource", "NOBUDGETAMOUNT");
      s = expInfo.getTypeName() + s;
      MsgBox.showError(this, s);
      setCursorOfDefault();
      SysUtil.abort();
    }
  }

  public void onShow()
    throws Exception
  {
    super.onShow();
    if ((OprtState.ADDNEW.equals(getOprtState())) || (OprtState.EDIT.equals(getOprtState())))
    {
      this.txtName.requestFocus();
    }
  }

  private void showCreateFromUI(List billData) throws Exception {
    Map uiContext = new UIContext(getUIContext().get("OwnerWindow"));
    uiContext.put("data", this.editData);
    uiContext.put("billTypes", billData);

    IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);

    IUIWindow window = uiFactory.create(CreateFromUI.class.getName(), uiContext, null, null);

    window.getUIObject().getUIContext().put("billEdit", this);
    window.getUIObject().getUIContext().put("srcBillBOSTypeString", getBillInterface().getType());

    if (((CreateFromUI)window.getUIObject()).needClose())
      window.close();
    else
      window.show();
  }

  public void actionViewBudgetBalance_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (("ADDNEW".equals(this.oprtState)) || ("EDIT".equals(this.oprtState))) {
      storeFields();
    }
    BizCollUtil.showBgForm(this, this.editData);
  }

  public void actionCreateFrom_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionCreateFrom_actionPerformed(e);
  }

  private void showErrorMessage(String message)
  {
    String s = EASResource.getString(this.RES, message);
    MsgBox.showError(this, s);
    setCursorOfDefault();
    SysUtil.abort();
  }

  protected BizCollBillTypeEnum[] getCountBillType() {
    return new BizCollBillTypeEnum[] { BizCollBillTypeEnum.OTHER_EXPENSE, BizCollBillTypeEnum.EVECTION_REQ };
  }

  protected void lockSomeUIForWorkFlow()
  {
    KDTable table = getDetailTable();
    ArrayList arrayList = new ArrayList();
    arrayList.add("amountApproved");
    BizCollUtil.lockTableColumn(table, arrayList);
    this.kDTextAreaDescription.setAccessAuthority(0);

    this.kDCheckAmountControl.setEnabled(false);
    this.bizPromptPayType.setAccessAuthority(0);
  }

  protected boolean isRowNoEqual(KDTSelectEvent rowE)
  {
    boolean flag = false;
    Map ctx = getUIContext();
    int col = rowE.getSelectBlock().getEndCol();
    int row = rowE.getSelectBlock().getEndRow();

    if (ctx.get("oldRowId") == null) {
      ctx.put("oldRowId", row + "");
      flag = true;
    } else {
      int oldId = Integer.parseInt(ctx.get("oldRowId") + "");
      ctx.put("oldRowId", row + "");
      if (oldId != row) {
        flag = true;
      }

    }

    return flag;
  }

  protected void showBgAuditInLabel(DataChangeEvent e) throws Exception
  {
    if (this.isLoadFieldAction) {
      return;
    }
    storeFields();
    if (this.editData == null)
      return;
    IExpenseCommenFacade iExpenseCommenFacade = ExpenseCommenFacadeFactory.getRemoteInstance();

    int[] rows = KDTableUtil.getSelectedRows(getDetailTable());
    if ((!BizCollUtil.objectIsNull(this.dateReqDate.getValue())) && (this.bizPromptCurrencyType.getData() != null) && (this.bizPromptCostedDept.getData() != null))
    {
      Map map = new HashMap();
      if (rows.length > 0) {
        if (rows[0] < 0) {
          return;
        }
        IRow row = getDetailTable().getRow(rows[0]);
        if (checkIsCountLine(row)) {
          return;
        }
        if (row.getCell("expensetype").getValue() != null) {
          map.put("expenseType", (ExpenseTypeInfo)row.getCell("expensetype").getValue());
        }
      }

      String labelText = iExpenseCommenFacade.getBudget(this.editData, this.editData.getId(), map);

      if (labelText == null) {
        this.btnShowBal.setVisible(false);
        return;
      }
      int txtLength = labelText.length();
      StringBuffer space = new StringBuffer();
      for (int i = 0; i < 80 - txtLength; i++) {
        space = space.append(" ");
      }
      this.btnSpace.setText(space.toString());
      space.delete(0, space.length());
      space.append("        ");
      this.btnShowBal.setVisible(true);
      this.btnShowBal.setText(labelText);
      this.btnShowBal.setEnabled(false);
    }
    else {
      this.btnShowBal.setVisible(false);
    }
  }

  private String getAccount() throws Exception
  {
    Map map = new HashMap();
    String tempStr = null;
    BcParamInfo bcParamInfo = null;
    try {
      map = CommonUtilFacadeFactory.getRemoteInstance().getAccount((PersonInfo)this.bizPromptApplier.getValue(), (CostCenterOrgUnitInfo)this.bizPromptCostedDept.getValue(), (CurrencyInfo)this.bizPromptCurrencyType.getValue(), (AdminOrgUnitInfo)this.bizPromptOrgUnit.getValue(), this.loanBalanceAccountNumber);

      tempStr = (String)map.get("tempStr");
      bcParamInfo = (BcParamInfo)map.get("bcParamInfo");

      if (bcParamInfo == null) {
        if (!this.isShowedParam) {
          this.isShowedParam = true;
          MsgBox.showError(EASResource.getString(this.RES, "nullBcParamInfo"));
        }

        SysUtil.abort();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if ((tempStr == null) || (tempStr.equals("")))
      tempStr = "0.00";
    StringBuffer sb = new StringBuffer();
    if (bcParamInfo != null) {
      if ((bcParamInfo.isOrgUnitParam()) && (this.bizPromptOrgUnit.getValue() != null))
      {
        sb.append(((AdminOrgUnitInfo)this.bizPromptOrgUnit.getValue()).getName(getCurrentLocale()));

        sb.append(EASResource.getString(this.RES, "department"));
      }
      if ((bcParamInfo.isCostCenterParam()) && (this.bizPromptCostedDept.getValue() != null))
      {
        sb.append(((CostCenterOrgUnitInfo)this.bizPromptCostedDept.getValue()).getName(getCurrentLocale()));

        sb.append(EASResource.getString(this.RES, "department"));
      }

      if ((bcParamInfo.isPersonParam()) && (this.bizPromptApplier.getValue() != null))
      {
        sb.append(((PersonInfo)this.bizPromptApplier.getValue()).getName(getCurrentLocale()));

        sb.append(EASResource.getString(this.RES, "person"));
      }
      String mark = EASResource.getString(this.RES, "person_assistantacc");
      String showName = this.loanBalanceShowName;
      String str = MessageFormat.format(mark, new Object[] { showName, parseString(tempStr, "0.00") });

      return str;
    }
    return null;
  }

  private Object[] getInitParams(BcParamInfo bcParamInfo) throws Exception
  {
    CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();

    PeriodEntity pe = null;
    try {
      pe = AssistantAccountUtil.getPeriod(company);
    } catch (Exception e2) {
      handleException(e2);
    }
    String periodYear = String.valueOf(pe.getCurrentYear());
    String periodNumber = String.valueOf(pe.getCurrentPeriod());
    String periodYear1 = String.valueOf(pe.getCurrentYear());
    String periodNumber1 = String.valueOf(pe.getCurrentPeriod());

    String orgunitID = "";
    if (company != null) {
      orgunitID = company.getId().toString();
    }
    CurrencyInfo currencyInfo = null;
    String currency = "";
    try {
      currencyInfo = AssistantAccountUtil.getCurrencyInfo("BB01");
    } catch (BOSException e1) {
      handleException(e1);
    }
    if (currencyInfo != null) {
      currency = currencyInfo.getId().toString();
    }

    String number = this.loanBalanceAccountNumber;
    String balType = AssistantAccountUtil.BOLTYPE;

    AdminOrgUnitInfo orgInfo = null;
    try
    {
      orgInfo = AssistantAccountUtil.getOrgUnitReferToApplier((PersonInfo)this.bizPromptApplier.getValue());
    }
    catch (Exception e)
    {
      handleException(e);
    }

    String longNumber = orgInfo.getLongNumber();

    ArrayList array = new ArrayList();
    array.add(periodYear);
    array.add(periodNumber);
    array.add(currency);
    array.add(number);
    array.add(balType);
    array.add(periodYear1);
    array.add(periodNumber1);
    array.add(longNumber);
    if (bcParamInfo.isOrgUnitParam()) {
      array.add(orgunitID);
    }
    if (bcParamInfo.isCostCenterParam()) {
      if (this.bizPromptCostedDept.getValue() != null) {
        String costCenterId = ((CostCenterOrgUnitInfo)this.bizPromptCostedDept.getValue()).getId().toString();

        array.add(costCenterId);
      } else {
        array.add("!~eas~!");
      }
    }

    if (bcParamInfo.isPersonParam()) {
      if (this.bizPromptApplier.getValue() != null) {
        String personId = ((PersonInfo)this.bizPromptApplier.getValue()).getId().toString();

        array.add(personId);
      } else {
        array.add("!~eas~!");
      }
    }

    return array.toArray();
  }

  private void setBtnViewRrcdsOfLendAndRepayState()
  {
    this.btnViewRcrdsOfLendAndRepay.setVisible(true);
    this.btnViewRcrdsOfLendAndRepay.setEnabled(true);
    this.btnViewRcrdsOfLendAndRepay.setIcon(EASResource.getIcon("imgTbtn_requite"));

    this.menuItemViewRcds.setIcon(EASResource.getIcon("imgTbtn_requite"));
  }

  public void updateReqDateAction_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.updateReqDateAction_actionPerformed(e);

    DailyLoanBillInfo info = DailyLoanBillFactory.getRemoteInstance().getDailyLoanBillInfo(new ObjectUuidPK(this.editData.getId().toString()));

    this.dateReqDate.setValue(info.getBizReqDate());
  }

  public void actionViewRrcdsOfLendAndRepay_actionPerformed(ActionEvent e)
    throws Exception
  {
    Map ctx = perpareForUIContext();

    if (ctx.get("applier") == null) {
      String s = EASResource.getString("com.kingdee.eas.cp.bc.BizCollResource", "apllierCanNotNull");
      MsgBox.showError(this, s);
      SysUtil.abort();
    }
    IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);

    IUIWindow messageDialog = uiFactory.create(RecordsOfLendAndRepayUI.class.getName(), ctx);

    messageDialog.show();
  }

  public void actionAddNew_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionAddNew_actionPerformed(e);

    this.bizPromptCostedDept.setEnabled(true);
    this.bizPromptCompany.setEnabled(true);
  }

  private Map perpareForUIContext()
  {
    Map ctx = new HashMap();
    ctx.put("applier", this.bizPromptApplier.getValue());
    java.util.Date templeDate = (java.util.Date)this.dateBillDate.getValue();
    Timestamp createTime = new Timestamp(templeDate.getTime());
    ctx.put("createTime", createTime);
    ctx.put("Owner", this);
    return ctx;
  }

  public static String parseString(String str, String back)
  {
    if ((str == null) || (str.trim().length() == 0)) {
      return back;
    }
    if (str.indexOf('.') > 0) {
      String tempStr = str.substring(str.indexOf('.') + 1);
      if (tempStr.length() < 2) {
        for (int i = 0; i < 2 - tempStr.length(); i++) {
          str = str + "0";
        }
        return str;
      }

      if (tempStr.length() == 2) {
        return str;
      }
      if (tempStr.length() > 2)
        return str.substring(0, str.lastIndexOf('.') + 3);
    }
    try
    {
      Integer d = new Integer(str);
      return str + ".00"; } catch (NumberFormatException e) {
    }
    return back;
  }

  public SelectorItemCollection getBOTPSelectors()
  {
    SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("id"));
    sic.add(new SelectorItemInfo("name"));
    sic.add(new SelectorItemInfo("number"));
    sic.add(new SelectorItemInfo("applier.name"));
    sic.add(new SelectorItemInfo("orgUnit.name"));
    sic.add(new SelectorItemInfo("orgUnit.number"));

    sic.add(new SelectorItemInfo("position.name"));

    sic.add(new SelectorItemInfo("company.name"));
    sic.add(new SelectorItemInfo("company.number"));
    sic.add(new SelectorItemInfo("prior"));

    sic.add(new SelectorItemInfo("costedDept.number"));
    sic.add(new SelectorItemInfo("costedDept.name"));
    sic.add(new SelectorItemInfo("amount"));

    sic.add(new SelectorItemInfo("currencyType.name"));
    sic.add(new SelectorItemInfo("currencyType.number"));
    sic.add(new SelectorItemInfo("supportedObj.number"));
    sic.add(new SelectorItemInfo("supportedObj.name"));

    sic.add(new SelectorItemInfo("expenseType.typeName"));

    sic.add(new SelectorItemInfo("billDate"));

    sic.add(new SelectorItemInfo("cause"));
    sic.add(new SelectorItemInfo("description"));
    sic.add(new SelectorItemInfo("loanType"));
    sic.add(new SelectorItemInfo("bizReqDate"));
    sic.add(new SelectorItemInfo("tel"));
    sic.add(new SelectorItemInfo("payMode.number"));
    sic.add(new SelectorItemInfo("payMode.name"));
    sic.add(new SelectorItemInfo("entries.id"));

    sic.add(new SelectorItemInfo("entries.purpose"));
    sic.add(new SelectorItemInfo("entries.amount"));
    sic.add(new SelectorItemInfo("entries.comment"));
    sic.add(new SelectorItemInfo("entries.participants"));

    sic.add(new SelectorItemInfo("entries.expenseType.id"));
    sic.add(new SelectorItemInfo("entries.expenseType.number"));
    sic.add(new SelectorItemInfo("entries.expenseType.typeName"));
    sic.add(new SelectorItemInfo("entries.expenseType.name"));

    sic.add(new SelectorItemInfo("state"));
    sic.add(new SelectorItemInfo("loanState"));
    sic.add(new SelectorItemInfo("operationType.number"));
    sic.add(new SelectorItemInfo("operationType.name"));

    sic.add(new SelectorItemInfo("applierCompany.number"));
    sic.add(new SelectorItemInfo("applierCompany.name"));
    sic.add(new SelectorItemInfo("sourceBillId"));
    sic.add(new SelectorItemInfo("actionFlag"));

    return sic;
  }

  public List getPassBgParam() throws EASBizException, BOSException {
    int rows = -1;
    if (getDetailTable() == null)
      rows = -1;
    else {
      rows = getDetailTable().getRowCount();
    }
    if (rows <= 0)
      showErrorMessage("entryNotNull");
    IRow row = null;
    DailyLoanBillEntryInfo entryInfo = null;
    List entryList = new ArrayList();
    List list = null;
    for (int i = 0; i < rows; i++) {
      row = getDetailTable().getRow(i);

      if (checkIsCountLine(row)) {
        continue;
      }
      entryInfo = new DailyLoanBillEntryInfo();
      storeLineFields2(getDetailTable(), row, entryInfo);
      if (checkIsBlankLine(entryInfo)) {
        continue;
      }
      ExpenseTypeInfo expenseTypeInfo = (ExpenseTypeInfo)row.getCell(this.TB_EXPENSETYPE).getValue();

      if (!this.isNeedBudget)
        continue;
      if ((this.editData.getState().getValue() > 25) || 
        (BizCollUtil.objectIsNull(this.dateReqDate.getValue())) || (this.bizPromptCostedDept.getData() == null) || (this.bizPromptCurrencyType.getData() == null) || (this.bizPromptCompany.getData() == null))
      {
        continue;
      }
      BizCollBillBaseInfo baseInfo = (BizCollBillBaseInfo)this.editData.clone();

      baseInfo.setBizReqDate(this.dateReqDate.getSqlDate());

      baseInfo.setCostedDept((CostCenterOrgUnitInfo)this.bizPromptCostedDept.getData());

      baseInfo.setCurrencyType((CurrencyInfo)this.bizPromptCurrencyType.getData());

      baseInfo.setCompany((CompanyOrgUnitInfo)this.bizPromptCompany.getData());

      BigDecimal entryAmount = (BigDecimal)row.getCell(this.TB_AMOUNT).getValue();

      BigDecimal entryBugdetAmount = (BigDecimal)row.getCell(this.TB_BUDGETAMOUNT).getValue();

      if (entryBugdetAmount != null) {
        entryAmount = entryAmount.subtract(entryBugdetAmount);
      }

      baseInfo.setAmount(entryAmount);
      list = new ArrayList();
      list.add(baseInfo);
      list.add(expenseTypeInfo);
      entryList.add(list);
    }

    return entryList;
  }

  public boolean isNeedShowBOTPRule()
  {
    return true;
  }

  public IUIActionPostman prepareInit() {
    IUIActionPostman clientHanlder = super.prepareInit();
    if (clientHanlder != null) {
      RequestContext request = (RequestContext)clientHanlder.getRequestContext();

      EntityViewInfo viewInfo = new EntityViewInfo();
      FilterInfo filter = new FilterInfo();
      filter.getFilterItems().add(new FilterItemInfo("isDefault", Boolean.valueOf(true)));

      viewInfo.setFilter(filter);
      request.put("viewInfo", viewInfo);
      request.put("billTypes", getCountBillType());
      clientHanlder.setRequestContext(request);
    }
    return clientHanlder;
  }

  public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception
  {
    RequestContext request = super.prepareActionSubmit(itemAction);
    if (request != null) {
      request.setClassName(getUIHandlerClassName());
      List entryList = getPassBgParam();
      request.put("entryList", entryList);
    }
    return request;
  }

  public RequestContext prepareActionViewBudgetBalance(IItemAction itemAction) throws Exception
  {
    RequestContext request = super.prepareActionViewBudgetBalance(itemAction);

    if (request != null) {
      request.setClassName(getUIHandlerClassName());
      if (("ADDNEW".equals(this.oprtState)) || ("EDIT".equals(this.oprtState)))
      {
        storeFields();
      }
      request.put("info", this.editData);
    }
    return request;
  }

  public IUIActionPostman prepareInitUIData()
  {
    return super.prepareInitUIData();
  }

  public boolean isPrepareActionSubmit()
  {
    return true;
  }

  public boolean isPrepareActionViewBudgetBalance()
  {
    return true;
  }

  public boolean isPrepareActionViewRrcdsOfLendAndRepay()
  {
    return true;
  }

  public boolean isPrepareInit()
  {
    return true;
  }

  public boolean isPrepareActionMultiapprove()
  {
    return true;
  }

  public boolean isPrepareActionSave()
  {
    return true;
  }

  public boolean isPrepareActionViewSubmitProccess()
  {
    return true;
  }

  public boolean isPrepareActionTraceUp() {
    return true;
  }

  public boolean isPrepareActionTraceDown() {
    return true;
  }

  public boolean isPrepareActionWorkFlowG() {
    return true;
  }

  public boolean isPrepareInitUIData()
  {
    return super.isPrepareInitUIData();
  }

  public boolean isPrepareActionCopy() {
    return super.isPrepareActionCopy();
  }

  public void actionPrintPreview_actionPerformed(ActionEvent e)
    throws Exception
  {
    methodForPrint(e, false);
  }

  public void actionPrint_actionPerformed(ActionEvent e)
    throws Exception
  {
    methodForPrint(e, true);
  }

  private void methodForPrint(ActionEvent e, boolean noPreview)
  {
    BOSUuid id = this.editData.getId();
    KDNoteHelper appHlp = new KDNoteHelper();
    if (id != null) {
      BizCollCoreBillEditUI.DataProvider data = new BizCollCoreBillEditUI.DataProvider();
      MultiDataSourceProviderProxy multiDataSourceProviderProxy = getMultiQueryDelegate();

      multiDataSourceProviderProxy.put("MainQuery", data);
      if (noPreview)
      {
        appHlp.print("/cp/bc/dailyloan", multiDataSourceProviderProxy, SwingUtilities.getWindowAncestor(this));
      }
      else {
        appHlp.printPreview("/cp/bc/dailyloan", multiDataSourceProviderProxy, SwingUtilities.getWindowAncestor(this));
      }
    }
    else
    {
      MsgBox.showInfo(EASResource.getString("com.kingdee.eas.cp.bc.ExpenseReqException", "Null_Number_Exception"));
    }
  }

  protected String getReportQueryName() {
    return "com.kingdee.eas.cp.bc.app.DialyLoadBillQueryForPrintQuery";
  }

  protected String getR1ReportBillQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.DailyLoanBillForR1PrintQuery";
  }

  protected String getR1ReportEntryQueryName()
  {
    return "com.kingdee.eas.cp.bc.app.DailyLoanBillEntryForPrintQuery";
  }

  protected IRowSet updateBillReprotRowSet(IRowSet rowSet)
    throws SQLException
  {
    rowSet = super.updateBillReprotRowSet(rowSet);
    rowSet = parseEnumValue(rowSet, "amountControlType", "amountControlTypeAlias", AmountControlTypeEnum.getEnumList());

    return rowSet;
  }

  protected IRowSet parsePrintValue(IRowSet rowSet)
    throws SQLException
  {
    rowSet = super.parsePrintValue(rowSet);
    rowSet = parseEnumValue(rowSet, "amountControlType", "amountControlTypeAlias", AmountControlTypeEnum.getEnumList());

    return rowSet;
  }
}