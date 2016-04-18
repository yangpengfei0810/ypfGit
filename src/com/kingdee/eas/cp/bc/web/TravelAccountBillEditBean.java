package com.kingdee.eas.cp.bc.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.waf.annotation.IBOSBizCtrl;
import com.kingdee.bos.waf.ctx.WafContext;
import com.kingdee.bos.waf.resource.Resources;
import com.kingdee.bos.waf.util.BeanUtil;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.ISettlementType;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizAccountBillInfo;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.CommonUtilFacadeFactory;
import com.kingdee.eas.cp.bc.EducateEnum;
import com.kingdee.eas.cp.bc.EntryStateEnum;
import com.kingdee.eas.cp.bc.ExpAccException;
import com.kingdee.eas.cp.bc.ExpenseAccountBillInfo;
import com.kingdee.eas.cp.bc.ExpenseReqException;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.IOperationType;
import com.kingdee.eas.cp.bc.ITravelAccountBill;
import com.kingdee.eas.cp.bc.OperationTypeFactory;
import com.kingdee.eas.cp.bc.OperationTypeInfo;
import com.kingdee.eas.cp.bc.PriorEnum;
import com.kingdee.eas.cp.bc.StateEnum;
import com.kingdee.eas.cp.bc.TravelAccountBillEntryCollection;
import com.kingdee.eas.cp.bc.TravelAccountBillEntryInfo;
import com.kingdee.eas.cp.bc.TravelAccountBillInfo;
import com.kingdee.eas.cp.bc.VehicleEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

@ManagedBean(name = "cp.bc.TravelAccountBillEditBean", scope = ManagedBeanScope.SESSION)
public class TravelAccountBillEditBean extends BCBaseEditBean {

	@IBOSBizCtrl
	private ITravelAccountBill service;

	@Inject("cp.bc.TravelAccountBillEntryBean")
	private TravelAccountBillEntryBean entryBean;
	public static String AMOUNTAPPROVED = "amountApproved";

	public static SettlementTypeInfo getDefaultPayMode(Context ctx) {
		SettlementTypeInfo settle = null;
		try {
			ISettlementType iSettle = SettlementTypeFactory
					.getLocalInstance(ctx);
			EntityViewInfo viewInfo = new EntityViewInfo();

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("isDefault", Boolean.valueOf(true)));

			viewInfo.setFilter(filter);
			SettlementTypeCollection collPay = iSettle
					.getSettlementTypeCollection(viewInfo);

			if ((collPay != null) && (collPay.size() > 0))
				settle = collPay.get(0);
		} catch (BOSException e) {
			settle = null;
		}

		return settle;
	}
	
	public String f7FilterInfo(String param) {
		String filter = super.f7FilterInfo(param);
		String companyId = ((TravelAccountBillInfo) this.model)
				.getApplierCompany().getId().toString();
		Map paramMap = new HashMap();
		paramMap.put("companyId", companyId);
		Context ctx = WafContext.getInstance().getContext();
		return MakeControlUtil.makeExpenseTypeFilterInfo(ctx, paramMap);
	}

	private IObjectValue buildNewData(Context ctx, ExpenseAccountBillInfo info) {
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
			info.setCurrencyType(baseInfo.getCurrencyType());

			info.setPayMode(getDefaultPayMode(ctx));

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

	public void createNewModel() {
		this.model = new TravelAccountBillInfo();
		// 涓婚榛樿涓虹┖
		// ((TravelAccountBillInfo)this.model).setName(BizCollBillTypeEnum.TRAVEL_ACCOUNT.toString());
		((TravelAccountBillInfo)this.model).setIsEducate(EducateEnum.UNEDUCATE);
		Context ctx = WafContext.getInstance().getContext();
		buildNewData(ctx, (TravelAccountBillInfo) this.model);
	}

	public void pageOnload() {
		super.pageOnload();
		this.pageTitle = Resources.getText(ResourceUtils.CP_BC_WEB_RES,
				"TravelAccountBill");

		TravelAccountBillEntryCollection entryCol = ((TravelAccountBillInfo) this.model)
				.getEntries();

		List listModel = new LinkedList();
		if (entryCol == null) {
			return;
		}
		if (entryCol.size() == 0) {
			TravelAccountBillEntryInfo entryInfo = new TravelAccountBillEntryInfo();
			entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));

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
			// 浜ら�氬伐鍏烽粯璁や负椋炴満 2011-06-23 haibin_ye
			entryInfo.setVehicle(VehicleEnum.AIRPLANE);

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

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
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
		sic.add(new SelectorItemInfo("amountApproved"));
		sic.add(new SelectorItemInfo("amountStriked"));
		sic.add(new SelectorItemInfo("amountEncashed"));
		sic.add(new SelectorItemInfo("amountRefunded"));
		sic.add(new SelectorItemInfo("amountSendedBack"));
		sic.add(new SelectorItemInfo("payMode"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("lastUpdateUser.id"));
		sic.add(new SelectorItemInfo("lastUpdateUser.number"));
		sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("sourceBillId"));

		sic.add(new SelectorItemInfo("auditor.id"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("auditor.number"));
		sic.add(new SelectorItemInfo("auditDate"));
		sic.add(new SelectorItemInfo("sourceBillId"));

		sic.add(new SelectorItemInfo("accessoryCount"));

		sic.add(new SelectorItemInfo("payMode.number"));
		sic.add(new SelectorItemInfo("payMode.name"));
		sic.add(new SelectorItemInfo("entries.id"));
		sic.add(new SelectorItemInfo("entries.from"));
		sic.add(new SelectorItemInfo("entries.to"));
		sic.add(new SelectorItemInfo("entries.startDate"));
		sic.add(new SelectorItemInfo("entries.endDate"));
		sic.add(new SelectorItemInfo("entries.vehicle"));
		sic.add(new SelectorItemInfo("entries.taxiExpense"));
		sic.add(new SelectorItemInfo("entries.bussesExpense"));
		sic.add(new SelectorItemInfo("entries.hotelExpense"));
		sic.add(new SelectorItemInfo("entries.otherExpense"));
		sic.add(new SelectorItemInfo("entries.amount"));
		sic.add(new SelectorItemInfo("entries.budgetAmount"));
		sic.add(new SelectorItemInfo("entries.amountApproved"));
		sic.add(new SelectorItemInfo("entries.eventionSubsidy"));
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
		// 鍒嗗綍澧炲姞澶╂暟
		sic.add(new SelectorItemInfo("entries.dayCount"));
		
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
		
		sic.add(new SelectorItemInfo("outAppBillNo"));
		sic.add(new SelectorItemInfo("isEducate"));
		return sic;
	}

	public void saveModel() {
		updateModel();
		super.saveModel();
		TravelAccountBillEntryCollection entryCol = ((TravelAccountBillInfo) this.model)
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
			this.entryBean = ((TravelAccountBillEntryBean) BeanUtil
					.getBean("cp.bc.TravelAccountBillEntryBean"));
		}

		this.entryBean.updataModel();
		List<CoreBaseInfo> entryModel = this.entryBean.getListModel();
		if (entryModel != null) {
			for (CoreBaseInfo entryInfo : entryModel) {
				TravelAccountBillEntryInfo info = (TravelAccountBillEntryInfo) entryInfo;
				if ((info != null) && (info.getAmountApproved() != null)
						&& (info.getAmountApproved().intValue() < 0)) {
					addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
							"approvedacount_notnull"));

					return;
				}
			}
		}

		super.submitModel();
	}

	protected void updateModel() {
		super.updateModel();
		if (this.entryBean == null) {
			this.entryBean = ((TravelAccountBillEntryBean) BeanUtil
					.getBean("cp.bc.TravelAccountBillEntryBean"));
		}

		this.entryBean.updataModel();
		List<CoreBaseInfo> entryModel = this.entryBean.getListModel();
		((TravelAccountBillInfo) this.model).getEntries().clear();
		TravelAccountBillEntryCollection entries;
		if (entryModel != null) {
			entries = ((TravelAccountBillInfo) this.model).getEntries();

			for (CoreBaseInfo entryInfo : entryModel) {
				entries.add((TravelAccountBillEntryInfo) entryInfo);
			}
		}
		TravelAccountBillInfo info = (TravelAccountBillInfo) this.model;
		TravelAccountBillEntryInfo entry = null;
		BigDecimal amount = BizAccountBillEditBean.ZERO;
		BigDecimal budgetAmount = BizAccountBillEditBean.ZERO;
		BigDecimal amountApprove = BizAccountBillEditBean.ZERO;
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

	protected ICoreBase getBizInterface() {
		return this.service;
	}

	protected boolean verifyModel() {
		TravelAccountBillInfo info = (TravelAccountBillInfo) this.model;

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

		if (info.getAccessoryCount() <= 0) {
			addMessage("操作失败", "附件数不能为空且必须大于0!");

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

		if (!isBizUnitCompany(info.getCompany().getId().toString())) {
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
		
		if(StringUtils.isEmpty(info.getCause())){
			addMessage("操作失败", "完成情况不能为空！");
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

		TravelAccountBillEntryCollection entries = info.getEntries();
		if ((entries == null) || (entries.size() == 0)) {
			addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
					"operation_failure"), new ExpAccException(
					ExpAccException.ENTRY_NOT_NULL).getMessage());

			return false;
		}

		BigDecimal amount = null;

		TravelAccountBillEntryInfo entry = null;
		String sourceId = ((TravelAccountBillInfo) this.model)
				.getSourceBillId();
		int i = 0;
		
		ICostCenterOrgUnit iCostCenter = null;
		IOperationType iOperationType = null;
		try {
			iCostCenter = CostCenterOrgUnitFactory.getRemoteInstance();
			iOperationType = OperationTypeFactory.getRemoteInstance();
		} catch (BOSException e1) {
			addMessage("操作失败", "获取远程接口错误:" + e1.getMessage());
			return false;
		}
		
		for (int size = entries.size(); i < size; i++) {
			entry = entries.get(i);
			if (entry.getExpenseType() == null) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.EXPENSE_TYPE_NOT_NULL).getMessage());

				return false;
			}
			
			if (entry.getStartDate() == null) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.BEGINDATE_NOT_NULL).getMessage());

				return false;
			}

			if (entry.getEndDate() == null) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.ENDDATE_NOT_NULL).getMessage());

				return false;
			}

			if ((entry.getFrom() == null)
					|| (entry.getFrom().toString().length() == 0)) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.BEGIN_NOT_NULL).getMessage());

				return false;
			}

			if (entry.getFrom().length() > 80) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), Resources.getText(
						ResourceUtils.CP_BC_WEB_LOANREQ_RES,
						"evectionLoanFromTooLong"));

				return false;
			}

			if ((entry.getTo() == null)
					|| (entry.getTo().toString().length() == 0)) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.END_NOT_NULL).getMessage());

				return false;
			}

			if (entry.getTo().length() > 80) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), Resources.getText(
						ResourceUtils.CP_BC_WEB_LOANREQ_RES,
						"evectionLoanToTooLong"));

				return false;
			}

			if (entry.getVehicle() == null) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.VEHICLE_NOT_NULL).getMessage());

				return false;
			}

			if (entry.getStartDate().compareTo(entry.getEndDate()) > 0) {
				addMessage(Resources.getText(ResourceUtils.CP_BC_WEB_RES,
						"operation_failure"), new ExpAccException(
						ExpAccException.BEGIN_LT_END).getMessage());

				return false;
			}

			if ((sourceId != null) && (!BizCollUtil.isEvectionReq(sourceId))) {
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
			
			if(entry.getOperationType() == null){
				addMessage("操作失败", "业务类别不能为空!");
				return false;
			}
			
			// 鍒ゆ柇涓氬姟绫诲埆鐨勬弿杩板拰棰勭畻璐ｄ换閮ㄩ棬鏄惁涓�鑷�
//			CostCenterOrgUnitInfo costCenter = entry.getCostCenter();
//			OperationTypeInfo operationType = entry.getOperationType();
//			try {
//				costCenter = iCostCenter.getCostCenterOrgUnitInfo(new ObjectUuidPK(costCenter.getId().toString()));
//				operationType = iOperationType.getOperationTypeInfo(new ObjectUuidPK(operationType.getId().toString()));
//				
//				if(StringUtils.isEmpty(operationType.getDescription()))
//					continue;
//				if(!costCenter.getName().equals(operationType.getDescription())){
//					addMessage("鎿嶄綔澶辫触", "绗�" + (i+1)+ "琛岀殑涓氬姟绫诲埆涓庨绠楄矗浠婚儴闂ㄤ笉涓�鑷达紝璇蜂慨鏀瑰悗閲嶈瘯锛�");
//					return false;
//				}
//			} catch (Exception e) {
//				addMessage("鎿嶄綔澶辫触", "鑾峰彇涓氬姟瀵硅薄閿欒:" + e.getMessage());
//				return false;
//			}
		}
		
		return true;
	}

	public static boolean isBizUnitCompany(String id) {
		Context ctx = WafContext.getInstance().getContext();
		try {
			CompanyOrgUnitInfo info = CompanyOrgUnitFactory.getLocalInstance(
					ctx).getCompanyOrgUnitInfo(
					"select isBizUnit where id = '" + id + "'");

			return info.isIsBizUnit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected IRowSet parsePrintValue(IRowSet rowSet) throws SQLException {
		rowSet = super.parsePrintValue(rowSet);

		rowSet = parseEnumValue(rowSet, "entries.vehicle", "vehalias",
				VehicleEnum.getEnumList());

		return rowSet;
	}

	public String getReportTemplateUrl() {
		return "/cp/bc/tra";
	}

	protected String getReportQueryName() {
		return "com.kingdee.eas.cp.bc.app.TravelAccountForPrintQuery";
	}

	protected String getR1ReportBillQueryName() {
		return "com.kingdee.eas.cp.bc.app.TravelAccountBillForR1PrintQuery";
	}

	protected String getR1ReportEntryQueryName() {
		return "com.kingdee.eas.cp.bc.app.TravelAccountBillEntryForPrintQuery";
	}

	protected String getR1ReportEntrySumQueryName() {
		return "com.kingdee.eas.cp.bc.app.TravelAccountBillEntrySumForPrintQuery";
	}

	protected IRowSet updateEntryReprotRowSet(IRowSet rowSet)
			throws SQLException {
		return parseEnumValue(rowSet, "vehicle", "vehalias",
				VehicleEnum.getEnumList());
	}

	protected void viewAuditResult() throws Exception {
		super.viewAuditResult();
	}

	public void onSelectExpenseType() {
		if (this.model == null) {
			return;
		}
		TravelAccountBillInfo info = (TravelAccountBillInfo) this.model;

		updateModel();
		Context ctx = WafContext.getInstance().getContext();
		TravelAccountBillEntryCollection entries = info.getEntries();
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

			List entriesTempList = (ArrayList) this.entryBean.getEdiGrid()
					.getModifiedData();
			if ((entriesTempList == null) || (entriesTempList.size() < 1)) {
				entriesTempList = (ArrayList) this.entryBean.getEdiGrid()
						.getAddedData();
			}
			for (int j = 0; j < length; j++) {
				TravelAccountBillEntryInfo entryInfo = null;

				if ((j == 0) && (this.entryBean.getListModel() != null)
						&& (this.entryBean.getListModel().size() > 0)) {
					List listEntries = this.entryBean.getListModel();
					entryInfo = (TravelAccountBillEntryInfo) listEntries
							.get(editrow);
				} else {
					entryInfo = (TravelAccountBillEntryInfo) this.entryBean
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
			System.out.print(e.toString());
		} finally {
			this.outerScript = "ExpenseTypeField.hide();btnsubmit.enable();btnsave.enable();";
		}
	}

	private void updateEditGrid(TravelAccountBillEntryCollection entries) {
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
}