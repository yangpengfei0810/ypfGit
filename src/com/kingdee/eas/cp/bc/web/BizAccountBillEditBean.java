package com.kingdee.eas.cp.bc.web;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.waf.annotation.IBOSBizCtrl;
import com.kingdee.bos.waf.ctx.WafContext;
import com.kingdee.bos.waf.resource.Resources;
import com.kingdee.bos.waf.util.BeanUtil;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizAccountBillEntryCollection;
import com.kingdee.eas.cp.bc.BizAccountBillEntryInfo;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollBillTypeEnum;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.CommonUtilFacadeFactory;
import com.kingdee.eas.cp.bc.EntryStateEnum;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.cp.bc.ExpenseReqException;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.IBizAccountBill;
import com.kingdee.eas.cp.bc.ICommonUtilFacade;
import com.kingdee.eas.cp.bc.IOperationType;
import com.kingdee.eas.cp.bc.OperationTypeFactory;
import com.kingdee.eas.cp.bc.OperationTypeInfo;
import com.kingdee.eas.cp.bc.PriorEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.DateTimeUtils;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.operamasks.faces.annotation.Inject;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.component.form.impl.UITextField;
import org.operamasks.faces.component.grid.impl.UIEditDataGrid;

@ManagedBean(name = "cp.bc.BizAccountBillEditBean", scope = ManagedBeanScope.SESSION)
public class BizAccountBillEditBean extends BCBaseEditBean {

	@IBOSBizCtrl
	private IBizAccountBill service;

	@Inject("cp.bc.BizAccountBillEntryBean")
	private BizAccountBillEntryBean entryBean;
	public static final BigDecimal ZERO = new BigDecimal("0");

	public void createNewModel() {
		this.model = new BizAccountBillInfo();

		// 主题默认为空 2011-06-23 haibin_ye
		// ((BizAccountBillInfo)this.model).setName(BizCollBillTypeEnum.BIZ_ACCOUNT.toString());

		Context ctx = WafContext.getInstance().getContext();
		buildNewData(ctx, (BizAccountBillInfo) this.model);
	}

	private IObjectValue buildNewData(Context ctx, BizAccountBillInfo info) {
		try {
			BizCollBillBaseInfo baseInfo = CommonUtilFacadeFactory
					.getLocalInstance(ctx).forLoanBillCreateNewData();

			info.setOrgUnit(ContextUtil.getCurrentAdminUnit(ctx));
			Date date = DateTimeUtils.truncateDate(new Timestamp(System
					.currentTimeMillis()));

			info.setBizReqDate(date);

			info.setBillDate(date);
			UserInfo user = ContextUtil.getCurrentUserInfo(ctx);

			info.setBiller(user);
			info.setCreator(user);
			info.setCreateTime(new Timestamp(date.getTime()));

			((BizAccountBillInfo) this.model).setCurrencyType(baseInfo
					.getCurrencyType());

			info.setPayMode(TravelAccountBillEditBean.getDefaultPayMode(ctx));

			info.setAmount(new BigDecimal("0.00"));

			info.setPrior(PriorEnum.LOW);

			info.setBudgetAmount(new BigDecimal("0.00"));

			info.setBudgetBalance(new BigDecimal("0.00"));

			info.setAmountApproved(new BigDecimal("0.00"));

			info.setAmountStriked(new BigDecimal("0.00"));

			info.setAmountEncashed(new BigDecimal("0.00"));

			info.setAmountRefunded(new BigDecimal("0.00"));
			info.setState(StateEnum.NEW);

			info.setApplier(baseInfo.getApplier());

			info.setPosition(baseInfo.getPosition());

			info.setOrgUnit(baseInfo.getOrgUnit());

			info.setApplierCompany(baseInfo.getApplierCompany());

			info.setCostedDept(baseInfo.getCostedDept());

			info.setCompany(baseInfo.getCompany());
		} catch (BOSException ex) {
			return null;
		} catch (EASBizException ex) {
			return null;
		}
		return info;
	}

	public String f7FilterInfo(String param) {
		String filter = super.f7FilterInfo(param);
		String companyId = ((BizAccountBillInfo) this.model)
				.getApplierCompany().getId().toString();
		Map paramMap = new HashMap();
		paramMap.put("companyId", companyId);
		Context ctx = WafContext.getInstance().getContext();
		return MakeControlUtil.makeExpenseTypeFilterInfo(ctx, paramMap);
	}

	public void pageOnload() {
		super.pageOnload();
		this.pageTitle = Resources.getText(ResourceUtils.CP_BC_WEB_RES,
				"BizAccountBill");

		BizAccountBillEntryCollection entryCol = ((BizAccountBillInfo) this.model)
				.getEntries();

		List listModel = new LinkedList();
		if (entryCol == null) {
			return;
		}
		if (entryCol.size() == 0) {
			BizAccountBillEntryInfo entryInfo = new BizAccountBillEntryInfo();
			entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
			entryInfo.setHappenTime(new Date());
			Context ctx = WafContext.getInstance().getContext();
			BizCollBillBaseInfo baseInfo = null;
			try {
				baseInfo = CommonUtilFacadeFactory.getLocalInstance(ctx)
						.forLoanBillCreateNewData();

				entryInfo.setCostCenter(baseInfo.getCostedDept());
				entryInfo.setCompany(baseInfo.getCompany());
				entryInfo.setPayState(EntryStateEnum.UNDONE);
				entryInfo.setReceiveState(EntryStateEnum.UNDONE);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}

			entryCol.add(entryInfo);
		}
		int i = 0;
		for (int n = entryCol.size(); i < n; i++) {
			listModel.add(entryCol.get(i));
		}
		if (this.entryBean.getEdiGrid() != null) {
			this.entryBean.getEdiGrid().setDataProvider(null);
		}
		this.entryBean.setListModel(listModel);
		this.entryBean.getEdiGrid().setClientRows(
				Integer.valueOf(listModel.size()));
		this.entryBean.getEdiGrid().reload();
	}

	public void saveModel() {
		updateModel();
		super.saveModel();
		BizAccountBillEntryCollection entryCol = ((BizAccountBillInfo) this.model)
				.getEntries();

		List listModel = new LinkedList();
		int i = 0;
		for (int n = entryCol.size(); i < n; i++) {
			listModel.add(entryCol.get(i));
		}
		this.entryBean.setListModel(listModel);
	}

	public void submitModel() {
		updateModel();
		
		if (this.entryBean == null) {
			this.entryBean = ((BizAccountBillEntryBean) BeanUtil
					.getBean("cp.bc.BizAccountBillEntryBean"));
		}

		this.entryBean.updataModel();
		List<CoreBaseInfo> entryModel = this.entryBean.getListModel();
		if (entryModel != null) {
			for (CoreBaseInfo entryInfo : entryModel) {
				BizAccountBillEntryInfo info = (BizAccountBillEntryInfo) entryInfo;
				if ((info != null) && (info.getAmountApproved() != null)
						&& (info.getAmountApproved().intValue() < 0)) {
					addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
							"approvedacount_notnull"));

					return;
				}
			}
		}
		logger.info(this.model);
		super.submitModel();
	}

	protected void updateModel() {
		super.updateModel();
		if (this.entryBean == null) {
			this.entryBean = ((BizAccountBillEntryBean) BeanUtil
					.getBean("cp.bc.BizAccountBillEntryBean"));
		}

		this.entryBean.updataModel();
		List<CoreBaseInfo> entryModel = this.entryBean.getListModel();
		((BizAccountBillInfo) this.model).getEntries().clear();
		((BizAccountBillInfo) this.model).getEntries().clear();
		if (entryModel != null) {
			for (CoreBaseInfo entryInfo : entryModel) {
				BizAccountBillEntryInfo entry = (BizAccountBillEntryInfo) entryInfo;
				entry.setHappenTime(new Date());
				((BizAccountBillInfo) this.model).getEntries().add(entry);
			}
		}

		BizAccountBillInfo info = (BizAccountBillInfo) this.model;
		BizAccountBillEntryInfo entry = null;
		BigDecimal amount = ZERO;
		BigDecimal budgetAmount = ZERO;
		BigDecimal amountApprove = ZERO;
		int i = 0;
		for (int size = info.getEntries().size(); i < size; i++) {
			entry = info.getEntries().get(i);
			if (entry.getAmount() != null) {
				amount = amount.add(entry.getAmount());
			}
			if (entry.getBudgetAmount() != null) {
				budgetAmount = budgetAmount.add(entry.getBudgetAmount());
			}
			if (entry.getAmountApproved() != null) {
				amountApprove = amountApprove.add(entry.getAmountApproved());
			}
		}
		info.setAmount(amount);
		info.setAmountApproved(amountApprove);
		info.setBudgetAmount(budgetAmount);
		info.setAmountEncashed(amountApprove);
	}

	protected boolean verifyModel() {
		BizAccountBillInfo info = (BizAccountBillInfo) this.model;

		if (((info.getNumber() == null) || (info.getNumber().trim().length() == 0))
				&& (isRequiredByState("number-required"))) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.NUMBER_NOT_NULL).getMessage());

			return false;
		}

		if ((info.getName() == null) || (info.getName().trim().length() == 0)) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.NAME_NOT_NULL).getMessage());

			return false;
		}

		if (info.getBizReqDate() == null) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.EXPENSEDATE_NOT_NULL).getMessage());

			return false;
		}

		if (info.getApplier() == null) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.APPLIER_NOT_NULL).getMessage());

			return false;
		}
		if (info.getAccessoryCount() <= 0) {
			addMessage("操作失败", "附件数不能为空且必须大于0!");
			return false;
		}
		if (info.getOrgUnit() == null) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.ORGUNIT_NOT_NULL).getMessage());

			return false;
		}

		if (info.getCompany() == null) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.A_COMPANY_NOTNULL).getMessage());

			return false;
		}

		if (!TravelAccountBillEditBean.isBizUnitCompany(info.getCompany()
				.getId().toString())) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.ISBIZUNIT).getMessage());

			return false;
		}

		if (info.getCostedDept() == null) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.REQDEPARTMENT_NOT_NULL).getMessage());

			return false;
		}

		if (info.getCurrencyType() == null) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.CUR_NOT_NULL).getMessage());

			return false;
		}

		if (info.getPayMode() == null) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.PAYMODE_NOT_NULL).getMessage());

			return false;
		}

		if (info.getPrior() == null) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.PRIOR_NOT_NULL).getMessage());

			return false;
		}

		if (BizCollUtil.bigDecimalObjectLessEqual(BizCollUtil.MAX,
				info.getAmountApproved())) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new BizCollException(
					BizCollException.AMOUNT_TOO_LG).getMessage());

			return false;
		}

		BizAccountBillEntryCollection entries = info.getEntries();
		if ((entries == null) || (entries.size() == 0)) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.ENTRY_NOT_NULL).getMessage());

			return false;
		}

		BigDecimal amount = null;

		BizAccountBillEntryInfo entry = null;
		String sourceId = ((BizAccountBillInfo) this.model).getSourceBillId();
		int i = 0;
		for (int size = entries.size(); i < size; i++) {
			entry = entries.get(i);
			// 分录中发生时间改为非必录 2011-06-23 haibin_ye
			// if (entry.getHappenTime() == null) {
			// addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
			// "operation_failure"), new
			// ExpAccException(ExpAccException.HAPPENDATA_NOT_NULL).getMessage());
			//
			// return false;
			// }

			if (entry.getExpenseType() == null) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.EXPENSE_TYPE_NOT_NULL).getMessage());

				return false;
			}

			if (sourceId != null) {
				if (entry.getAmount().compareTo(new BigDecimal("0.00")) < 0) {
					addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
							"operation_failure"), new ExpAccException(
							ExpAccException.AMOUNTNOTLESSTHENZERO).getMessage());

					return false;
				}

			} else if (entry.getAmount().compareTo(new BigDecimal("0.00")) <= 0) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"),
						new ExpenseReqException(
								ExpenseReqException.NEGTIVE_ENTRY_AMOUNT_ERROR)
								.getMessage());

				return false;
			}

			amount = entry.getAmount();
			if (amount.compareTo(entry.getAmountApproved()) < 0) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.APPROVED_GT_AMOUNT).getMessage());

				return false;
			}

			if (entry.getCostCenter() == null) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.ENTRY_COSTCENTER_NOT_NULL).getMessage());

				return false;
			}

			if (entry.getCompany() == null) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.ENTRY_COMPANY_NOT_NULL).getMessage());

				return false;
			}
		}

		return true;
	}

	protected ICoreBase getBizInterface() {
		return this.service;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();

		sic = super.getSelectors();
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("applier.id"));
		sic.add(new SelectorItemInfo("applier.number"));
		sic.add(new SelectorItemInfo("applier.name"));
		sic.add(new SelectorItemInfo("position.number"));
		sic.add(new SelectorItemInfo("position.name"));
		sic.add(new SelectorItemInfo("tel"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("bizReqDate"));
		sic.add(new SelectorItemInfo("cause"));
		sic.add(new SelectorItemInfo("prior"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("billDate"));
		sic.add(new SelectorItemInfo("biller.id"));
		sic.add(new SelectorItemInfo("biller.name"));
		sic.add(new SelectorItemInfo("biller.number"));
		sic.add(new SelectorItemInfo("costedDept.number"));
		sic.add(new SelectorItemInfo("costedDept.name"));
		sic.add(new SelectorItemInfo("company.number"));
		sic.add(new SelectorItemInfo("company.name"));
		sic.add(new SelectorItemInfo("supportedObj.number"));
		sic.add(new SelectorItemInfo("supportedObj.name"));
		sic.add(new SelectorItemInfo("currencyType.name"));
		sic.add(new SelectorItemInfo("currencyType.number"));
		sic.add(new SelectorItemInfo("expenseType.typeName"));
		sic.add(new SelectorItemInfo("expenseType.number"));
		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("budgetAmount"));
		sic.add(new SelectorItemInfo("budgetBalance"));
		sic.add(new SelectorItemInfo("amountSendedBack"));
		sic.add(new SelectorItemInfo("amountApproved"));
		sic.add(new SelectorItemInfo("amountStriked"));
		sic.add(new SelectorItemInfo("amountEncashed"));
		sic.add(new SelectorItemInfo("amountRefunded"));
		sic.add(new SelectorItemInfo("sourceBillId"));
		sic.add(new SelectorItemInfo("payMode.number"));
		sic.add(new SelectorItemInfo("payMode.name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("lastUpdateUser.id"));
		sic.add(new SelectorItemInfo("lastUpdateUser.number"));
		sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("auditor.id"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("auditor.number"));
		sic.add(new SelectorItemInfo("auditDate"));
		sic.add(new SelectorItemInfo("sourceBillId"));
		sic.add(new SelectorItemInfo("sourceBillType"));

		sic.add(new SelectorItemInfo("accessoryCount"));

		sic.add(new SelectorItemInfo("entries.expenseType.id"));
		sic.add(new SelectorItemInfo("entries.expenseType.number"));
		sic.add(new SelectorItemInfo("entries.expenseType.name"));
		sic.add(new SelectorItemInfo("entries.operationType.id"));
		sic.add(new SelectorItemInfo("entries.operationType.number"));
		sic.add(new SelectorItemInfo("entries.operationType.name"));
		sic.add(new SelectorItemInfo("entries.company.id"));
		sic.add(new SelectorItemInfo("entries.company.number"));
		sic.add(new SelectorItemInfo("entries.company.name"));
		sic.add(new SelectorItemInfo("entries.costCenter.id"));
		sic.add(new SelectorItemInfo("entries.costCenter.number"));
		sic.add(new SelectorItemInfo("entries.costCenter.name"));
		sic.add(new SelectorItemInfo("entries.amountApproved"));
		sic.add(new SelectorItemInfo("entries.budgetAmount"));
		sic.add(new SelectorItemInfo("entries.amount"));
		sic.add(new SelectorItemInfo("entries.company.id"));
		sic.add(new SelectorItemInfo("entries.company.id"));
		sic.add(new SelectorItemInfo("entries.happenTime"));
		sic.add(new SelectorItemInfo("entries.participants"));
		sic.add(new SelectorItemInfo("entries.purpose"));
		sic.add(new SelectorItemInfo("entries.comment"));
		sic.add(new SelectorItemInfo("creator.id"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("creator.number"));
		sic.add(new SelectorItemInfo("applierCompany.id"));
		sic.add(new SelectorItemInfo("applierCompany.number"));
		sic.add(new SelectorItemInfo("applierCompany.name"));
		sic.add(new SelectorItemInfo("amountControlType"));

		sic.add(new SelectorItemInfo("BankNo"));
		sic.add(new SelectorItemInfo("PayUnitName"));
		sic.add(new SelectorItemInfo("AccountNo"));
		sic.add(new SelectorItemInfo("LeadPerson"));
		
		return sic;
	}

	public String getReportTemplateUrl() {
		return "/cp/bc/bizaccount";
	}

	protected String getReportQueryName() {
		return "com.kingdee.eas.cp.bc.app.BizAccountForPrintQuery";
	}

	protected String getR1ReportBillQueryName() {
		return "com.kingdee.eas.cp.bc.app.BizAccountBillForR1PrintQuery";
	}

	protected String getR1ReportEntryQueryName() {
		return "com.kingdee.eas.cp.bc.app.BizAccountBillEntryForPrintQuery";
	}

	public void onSelectExpenseType() {
		if (this.model == null) {
			return;
		}
		BizAccountBillInfo info = (BizAccountBillInfo) this.model;

		updateModel();
		Context ctx = WafContext.getInstance().getContext();
		BizAccountBillEntryCollection entries = info.getEntries();
		try {
			String[] ExpenseTypes = this.ExpenseTypeField.getValue().toString()
					.split(";");

			int length = ExpenseTypes.length;
			if (length < 1)
				return;
			length -= 1;

			int editrow = Integer.valueOf(ExpenseTypes[length].trim())
					.intValue();
			Set expenseTypeIdSet = new HashSet();
			Set unitIdSet = new HashSet();
			for (int i = 0; i < length; i++) {
				expenseTypeIdSet.add(ExpenseTypes[i]);
			}
			Map msMap = BillUtil.getExpenseTypeInfoMap(ctx, expenseTypeIdSet);

			for (int j = 0; j < length; j++) {
				BizAccountBillEntryInfo entryInfo = null;

				if ((j == 0) && (this.entryBean.getListModel() != null)
						&& (this.entryBean.getListModel().size() > 0)) {
					List listEntries = this.entryBean.getListModel();
					entryInfo = (BizAccountBillEntryInfo) listEntries
							.get(editrow);
				} else {
					entryInfo = (BizAccountBillEntryInfo) this.entryBean
							.createNewEntry();
					entries.add(entryInfo);
				}
				ExpenseTypeInfo etInfo = (ExpenseTypeInfo) msMap
						.get(ExpenseTypes[j]);

				if (etInfo != null) {
					entryInfo.setExpenseType(etInfo);
					if (etInfo.getOperationType() != null) {
						String operationId = etInfo.getOperationType().getId()
								.toString();
						OperationTypeInfo operationInfo = OperationTypeFactory
								.getLocalInstance(ctx).getOperationTypeInfo(
										new ObjectUuidPK(operationId));

						if (operationInfo != null) {
							entryInfo.setOperationType(operationInfo);
						}

					}

				}

			}

			updateEditGrid(entries);
			this.ExpenseTypeField.setValue(null);
			this.ExpenseTypeField.resetValue();
		} catch (Exception e) {
		} finally {
			this.outerScript = "ExpenseTypeField.hide();btnsubmit.enable();btnsave.enable();";
		}
	}

	private void updateEditGrid(BizAccountBillEntryCollection entries) {
		List listModel = new LinkedList();
		int n = entries.size();
		for (int j = 0; j < n; j++) {
			entries.get(j).setSeq(j);
			listModel.add(entries.get(j));
		}
		this.entryBean.setListModel(listModel);
		this.entryBean.getEdiGrid().setClientRows(Integer.valueOf(n));
		this.entryBean.getEdiGrid().reload();
	}
	
	public boolean isAutoGenalrate(){
		BizAccountBillInfo info = (BizAccountBillInfo) this.model;
		if(info.getSourceBillId()!=null&&info.getSourceBillType()!=null&&info.getSourceBillType().equals(BizCollBillTypeEnum.DAILY_LOAN))
		{	return true;}
		return false;
	}
}