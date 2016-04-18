package com.kingdee.eas.fdc.contract.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.xml.rpc.ServiceException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.client.OrgInnerUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.JsDataTools;
import com.kingdee.eas.fdc.contract.ModelFactory;
import com.kingdee.eas.fdc.contract.ModelInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.wslog.WSLogFactory;
import com.kingdee.eas.fdc.wslog.WSLogInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.landray.kmss.km.importfile.webservice.AttachmentForm;
import com.landray.kmss.km.importfile.webservice.KmReviewParamterForm;
import com.landray.kmss.km.importfile.webservice.KmReviewParamterNoattForm;
import com.landray.kmss.km.importfile.webservice.ZHIKmReviewWebserviceService;
import com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpService;
import com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpServiceLocator;

public class ContractBillEditUIPIEx extends ContractBillEditUI {
	private static KmReviewParamterForm form = null;
	private static KmReviewParamterNoattForm noAttForm = null;
	private static ZHKmReviewWebserviceServiceImpService service = new ZHKmReviewWebserviceServiceImpServiceLocator();
	private static ZHIKmReviewWebserviceService proxy = null;
	private static String oaId = "";
	private static String templateId = "";// oa单据模板id
	private static String billState = "";
	private static boolean bl_ok = true;
	private static String fid = "";// eas单据id
	private static int num = 100;
	private static String oaBillID = "";
	private static CurProjectInfo PROJECT_INFO = null;// 工程项目对象
	private static FDCUtil util;
	private static ContractBillListUIPIEx listUI = null;//用来反刷新listui
	
	private static String CALLTYPE_SEND ="发送";//写log用的

	private BigDecimal splitAmount = null;// 杨人的

	public ContractBillEditUIPIEx() throws Exception {
		super();
	}

	// ------杨人代码----start-
	public void onLoad() throws Exception {
		super.onLoad();

		// 合同录入时，业务日期自动取签约日期，且不允许修改
		pkbookedDate.setEnabled(false);
		pkbookedDate.setValue(pksignDate.getValue());

		pksignDate.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent arg0) {
				pkbookedDate.setValue(pksignDate.getValue());
			}
		});

		if (null != editData) {
			splitAmount = editData.getSplitAmt();
		}
		
		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("VIEW")||oprtState.equals("EDIT"))
			{
			  listUI = (ContractBillListUIPIEx) this.getUIContext().get("Owner");
			}
		}
	}
	
	public void rebackRefreshListUI(ActionEvent e)
	{
		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("VIEW")||oprtState.equals("EDIT"))
			{
			   listUI = (ContractBillListUIPIEx) this.getUIContext().get("Owner");
			   try {
				    listUI.actionRefresh_actionPerformed(e);
			   } catch (Exception e1) {
					e1.printStackTrace();
			   }
			}
		}
	}

	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null) {
			ContractBillInfo info = ContractBillFactory.getRemoteInstance()
					.getContractBillInfo(
							new ObjectUuidPK(editData.getId().toString()));
			editData.put("splitAmt", info.getSplitAmt());
		}
		super.actionProgram_actionPerformed(e);
		splitAmount = editData.getSplitAmt();
	}

	// 杨人代码
	private void updateSplitAmt() {
		try {
			ContractBillFacadeFactory.getRemoteInstance().updateSplitAmt(
					editData.getId().toString(), splitAmount);
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	// /------------------end----------------------------------------------

	// 重装全局变量
	public void resetParam() {
		form = null;
		noAttForm = null;
		proxy = null;
		oaId = "";
		templateId = "";
		billState = "";
		bl_ok = true;
		fid = "";
		num = 100;
		oaBillID = "";
		PROJECT_INFO = null;
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = (!editData.getId().equals("") && editData.getId() != null) ? editData.getId().toString():"";
		if (oprtState.equals("VIEW")) {
			fid = id;
		}

		// 检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
		if (!fid.equals("")) {
			boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractBill WHERE fid='"+ fid + "'");
			if (!isEnableSubmit) {
				MsgBox.showInfo("单据已经提交OA走审批流程，不能删除");
				SysUtil.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	// modify by ll 2012-10-10 单据已走OA审批流程，但单据在查看下可以审批
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		ContractBillInfo contractbill = ContractBillFactory.getRemoteInstance()
				.getContractBillInfo(new ObjectUuidPK(editData.getId()));

		String state = contractbill.getState().toString();
		Boolean isOAAudit = contractbill.isIsOAAudit();
		String oaAudit = isOAAudit.toString();
		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& oaAudit.equals("true")) {
			MsgBox.showInfo("单据已经走OA流程审批，不能在EAS审批");
			SysUtil.abort();
		} else {
			// -----------杨人代码 begin-------------------------------------------
			ProgrammingContractInfo pcInfoTemp = editData
					.getProgrammingContract();
			if (pcInfoTemp != null) {
				BigDecimal totalSplitAmount = ContractBillFacadeFactory
						.getRemoteInstance().getTotalSplitAmount(
								pcInfoTemp.getId().toString());
				// 累计分配=累计分配+本次分配；
				BigDecimal calTotalSplitAmount = FDCHelper.add(
						totalSplitAmount, contractbill.getSplitAmt());
				ContractBillFacadeFactory.getRemoteInstance()
						.updateTotalSplitAmount(pcInfoTemp.getId().toString(),
								calTotalSplitAmount);
			}
			//------end----------------------------------------------------------
			// -

			super.actionAudit_actionPerformed(e);

			//mod by ypf on 20121111 刷新listui
			rebackRefreshListUI(e);
			
			// -----------杨人代码 begin-------------------------------------------
			// 审批后保存、提交、关联框架合约设置为不可用
			btnAddNew.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnProgram.setEnabled(false);
			//------end----------------------------------------------------------
			// -
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		ContractBillInfo contractbill = ContractBillFactory.getRemoteInstance()
				.getContractBillInfo(new ObjectUuidPK(editData.getId()));

		String state = contractbill.getState().toString();
		Boolean isOAAudit = contractbill.isIsOAAudit();
		String oaAudit = isOAAudit.toString();
		System.out.println("state:" + state + "   isOAAudit:" + oaAudit);

		if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
				&& oaAudit.equals("true")) {
			MsgBox.showInfo("单据已经通过OA审批，不能进行反审批");
			SysUtil.abort();
		} else {
			// -----------杨人代码 begin-------------------------------------------
			ProgrammingContractInfo pcInfoTemp = editData
					.getProgrammingContract();
			if (pcInfoTemp != null) {
				BigDecimal totalSplitAmount = ContractBillFacadeFactory
						.getRemoteInstance().getTotalSplitAmount(
								pcInfoTemp.getId().toString());
				// 累计分配=累计分配-本次分配；
				BigDecimal calTotalSplitAmount = FDCHelper.subtract(
						totalSplitAmount, contractbill.getSplitAmt());
				ContractBillFacadeFactory.getRemoteInstance()
						.updateTotalSplitAmount(pcInfoTemp.getId().toString(),
								calTotalSplitAmount);
			}
			//------end----------------------------------------------------------

			super.actionUnAudit_actionPerformed(e);
			
			//mod by ypf on 20121111 刷新listui
			rebackRefreshListUI(e);
			
			// -----------杨人代码 begin-------------------------------------------
			// 反审批后保存、提交、关联框架合约设置为不可用
			btnAddNew.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnProgram.setEnabled(false);
			//------end----------------------------------------------------------
		}
	}

	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		String id = (!editData.getId().equals("") && editData.getId() != null) ? editData.getId().toString(): "";
		if (oprtState.equals("VIEW")) {
			fid = id;
		}

		// 检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
		if (!fid.equals("")) {
			boolean isEnableSubmit = FDCUtil
					.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractBill WHERE fid='"
							+ fid + "'");
			if (!isEnableSubmit) {
				MsgBox.showInfo("单据已经提交OA走审批流程，不能编辑");
				SysUtil.abort();
			}
		}
		super.actionEdit_actionPerformed(arg0);
		
		/*TestWS ws = new TestWS();
		ws.calContractBill("EAS2", "13fe7e352e258d1caf7ca054abd923e8", "10REJECT", false,null);*/
	}

	private String getBillState(String id) {
		String state = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);

		try {
			ContractBillCollection col = ContractBillFactory
					.getRemoteInstance().getContractBillCollection(view);
			if (col != null && col.size() > 0) {
				state = (col.get(0).getState() == null) ? "" : col.get(0)
						.getState().toString();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return state;
	}

	private String getOABillTemplet(String id) {
		String billTempletID = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);

		try {
			ContractBillCollection col = ContractBillFactory
					.getRemoteInstance().getContractBillCollection(view);
			if (col != null && col.size() > 0) {
				billTempletID = (col.get(0).getBillTempletID() == null) ? ""
						: col.get(0).getBillTempletID().toString();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return billTempletID;
	}

	// 获取项目工程对象
	private CurProjectInfo getPrjInfo(String id) {
		CurProjectInfo info = null;
		String prjID = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);

		try {
			ContractBillCollection col = ContractBillFactory
					.getRemoteInstance().getContractBillCollection(view);
			if (col != null && col.size() > 0) {
				prjID = (col.get(0).getCurProject() == null) ? "" : col.get(0)
						.getCurProject().getId().toString();
				EntityViewInfo view1 = new EntityViewInfo();
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", prjID));
				view1.setFilter(filter1);
				CurProjectCollection prjCol = CurProjectFactory
						.getRemoteInstance().getCurProjectCollection(view1);
				if (prjCol != null && prjCol.size() > 0) {
					info = prjCol.get(0);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return info;
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// 校验editdata数据
		try {
			super.verifyData();
		} catch (Exception e3) {
			e3.printStackTrace();
			SysUtil.abort();
		}

		super.actionSave_actionPerformed(e);

		// 杨人代码----start
		if (null != editData && null != editData.getId()) {
			updateSplitAmt();
		}
		// 杨人代码----end

		// 回写单据信息
		// File file = new File("c:\\VM_Setup.log");
		// FileInputStream in = new FileInputStream(file);
		// byte[] bs = new byte[in.available()];
		// in.read(bs);
		// in.close();
		//		
		// AttachmentInfo info=new AttachmentInfo();
		// // info.setId("r9nSEp8uRjO7BIEf4o1rNvSvTwM=");
		// info.setName("VM_Setup.log");
		// info.setFile(bs);
		// info.setSize(bs.length/1024+"KB");
		// info.setSizeInByte(bs.length);
		//		 
		//		
		// AttachmentCollection attachmentInfoList = new AttachmentCollection();
		// attachmentInfoList.add(info);
		//
		// boolean bl= addAttachmentByBillId("1360d0baab3935f8443ed624ab8831e6",
		// attachmentInfoList);
		// System.out.println("----bl:"+bl);
	}

	protected boolean addAttachmentByBillId(String billId,
			AttachmentCollection AttachmentInfoList) {
		boolean bl = true;
		BoAttchAssoInfo info_sso = null;
		billId = getConId(billId);
		System.out.println("----billId:" + billId);
		for (int i = 0; i < AttachmentInfoList.size(); i++) {
			info_sso = new BoAttchAssoInfo();
			BOSUuid bosid = BOSUuid.create(info_sso.getBOSType());
			System.out.println("----bosid:" + bosid);
			// info_sso.setId(BOSUuid.create(info_sso.getBOSType()));
			info_sso.setId(bosid);
			info_sso.setBoID(billId);
			info_sso.setAssoType("OA回写新增附件");
			info_sso.setAttachment(AttachmentInfoList.get(i));
			info_sso.setAssoBusObjType(info_sso.getBOSType().toString());
			// System.out.println("---billId:"+billId+
			// "  info_sso.getBOSType().toString():"
			// +info_sso.getBOSType().toString
			// ()+"  AttachmentInfoList[i]:"+AttachmentInfoList
			// [i].getId()+"  fileLenth:"
			// +AttachmentInfoList[i].getFile().length);

			try {
				System.out.println("-1111---bosid:" + info_sso.getId());
				// BoAttchAssoFactory.getLocalInstance(ctx).addnew(info_sso);
				BoAttchAssoFactory.getRemoteInstance().save(info_sso);
				System.out.println("----add relation --第" + (i + 1)
						+ "条---单据与附件关系表：" + "  id:" + info_sso.getId());
			} catch (EASBizException e1) {
				System.out.println("--fail----add relation:" + (i + 1)
						+ "  id:" + info_sso.getId());
				bl = false;
				e1.printStackTrace();
			} catch (BOSException e1) {
				System.out.println("--fail----add relation:" + (i + 1)
						+ "  id:" + info_sso.getId());
				bl = false;
				e1.printStackTrace();
			}
		}
		return bl;
	}

	public String getConId(String fid) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("OABillID", fid));
		view.setFilter(filter);
		PayRequestBillCollection col;
		String BILLID = "";
		try {
			col = PayRequestBillFactory.getRemoteInstance()
					.getPayRequestBillCollection(view);
			PayRequestBillInfo info = new PayRequestBillInfo();
			// 取到需要更新单据的对象
			if (col != null && col.size() > 0) {
				info = col.get(0);
				BILLID = info.getId().toString();
			}
		} catch (Exception e) {

		}
		return BILLID;
	}

	private void deleteModel(String billFid) {
		try {
			ModelFactory.getRemoteInstance().delete(
					"where id is not null and description='" + billFid.trim()
							+ "'");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		/*Object[] obj = ContractBillListUIPIEx.class.getClass().getMethods();
		for (int i = 0; i < obj.length; i++) {
			System.out.println("----method:" + obj[i]);
		}*/

		this.destroyWindow();
		this.disposeUIWindow();
		// 重置静态变量
		resetParam();

		if (oprtState.equals("ADDNEW")) {
			actionSave_actionPerformed(e);

			// 初始化fid
			fid = editData.getId().toString();

			// 保存以后才能娶到单据状态
			billState = getBillState(fid);
		} else if (oprtState.equals("EDIT")) {
			fid = (editData.getId() == null) ? "" : editData.getId().toString();
			billState = getBillState(fid);
			if (billState.equals("驳回")) {
				templateId = getOABillTemplet(fid);
			}
		} else {
			SysUtil.abort();
		}

		// 第一步、检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除
		boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractBill WHERE fid='"+ fid + "'");
		if (!isEnableSubmit) {
			MsgBox.showInfo("单据已经提交OA走审批流程，不能重复提交");
			SysUtil.abort();
		}

		num = isOAAudit();

		// 1、选择是否走OA审批
		if (num == 0) {
			try {
				super.verifyData();
			} catch (Exception e3) {
				e3.printStackTrace();
				SysUtil.abort();
			}

			PROJECT_INFO = getPrjInfo(fid);// 初始化项目工程对象

			// 判断是否存在附件
			String sql = "SELECT t1.fid billID,t3.fid attachmentID FROM T_CON_ContractBill t1 left join T_BAS_BoAttchAsso t2 on t1.fid=t2.fboid left join  T_BAS_Attachment t3 on t2.fattachmentid =t3.fid WHERE t1.fid='"
					+ fid + "'";
			util = new FDCUtil();
			if (!util.checkExistAttachment(sql)) {
				actionSubmit_no_attachment(e);
				SysUtil.abort();
			}

			bl_ok = true;
			String jsonForm = getJsonForm((String) fid);
			String formHTML = getHTML(fid);
			System.out.println("------actionSubmit_actionPerformed-----fid:"
					+ fid);

			// 如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
			String prjMappingName = PROJECT_INFO.getPrjMappingName();
			
			//合同名称
			String subject = txtcontractName.getText();// mod by ypf on 20121106
			System.out.println("--------subject主题："+subject);

			if (prjMappingName == null || prjMappingName.equals("")) {
				if (MsgBox.showConfirm3("该工程项目没有配置映射名称有可能取不到模板，是否还要继续？") == MsgBox.OK) {

				} else {
					SysUtil.abort();
				}
			}

			// 2、选择模板
			// 获取蓝凌OA提供模板并保存到eas中
			List modelIDList = null;
			String keyForModel = "EAS1(" + prjMappingName + ")";
			if (billState != null && !billState.equals("") && !billState.equals("驳回")) {
				getOAModelAndSaveToEAS(keyForModel, fid);
				modelIDList = showFilterDialog(fid);
			}

			// 4、准备要发起流程的数据，并传递给oa审批
			AlterAttachment at = new AlterAttachment();
			String creator = SysContext.getSysContext().getCurrentUserInfo().getNumber();

			if (billState != null && !billState.equals("") && !billState.equals("驳回")) {
				templateId = (modelIDList.size() > 0) ? modelIDList.get(0).toString() : "";
			}
			oaBillID = getOAID(fid);
			System.out.println("-------驳回重新提交时根据单据id查找到的oa单据id:" + oaBillID);

			//mod by ypf on 20140827 发起账号要是json
			String creatorJsonString = "";
			try {
				JSONObject creatorJson = new JSONObject();
				creatorJsonString = creatorJson.put("LoginName", creator)+"";
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			AttachmentForm[] forms = at.getAttacmentInfo(fid);
			form = new KmReviewParamterForm();
			form.setDocCreator(creatorJsonString);
			form.setDocSubject(subject);
			form.setFdTemplateId(templateId);
			form.setFormValues(jsonForm);
			form.setEasHtml(formHTML);
			form.setAttachmentForms(forms);
			form.setDocId(oaBillID);// oa单据id oaBillID
			System.out.println("---form----creator:" + creatorJsonString + "  " + (oprtState.equals("ADDNEW") ? "新增状态" : "编辑状态") + "oaBillID:" + oaBillID + "  subject:" + subject
					+ "   templateId:" + templateId + "   jsonForm:" + jsonForm + "  formHTML" + formHTML);

			try {
				proxy = service.getZHKmReviewWebserviceServiceImpPort();
				bl_ok = true;
			} catch (ServiceException e2) {
				e2.printStackTrace();

				if (MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存提交？") == MsgBox.OK) {
					try {
						super.actionSubmit_actionPerformed(e);
					} catch (Exception e1) {
						e1.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("提交时失败");
						logInfo.setLogDetail(e1.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS1");
						WSLogFactory.getRemoteInstance().addnew(logInfo);
						
						SysUtil.abort();
					}
				}
				SysUtil.abort();
			}

			// 5、检查是否有待办记录
			try {
				proxy.delTasksByID(fid);
			} catch (Exception e1) {
				e1.printStackTrace();
				if (MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存？") == MsgBox.OK) {
					try {
						super.actionSave_actionPerformed(e);
					} catch (Exception e11) {
						e11.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("远程调用OA接口失败时对当前单据进行保存失败");
						logInfo.setLogDetail(e11.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS1");
						WSLogFactory.getRemoteInstance().addnew(logInfo);
						
						SysUtil.abort();
					}
				}
				SysUtil.abort();
			}

			// 6、调用oa的流程发起接口传值
			try {
				LongTimeDialog dialog = UITools.getDialog(this);
				dialog.setTitle("提示");// 提示框标题
				if (dialog == null)
					return;
				dialog.setLongTimeTask(new ILongTimeTask() {
					public Object exec() throws Exception {
						// 加入要等待的代码块
						try {
							oaId = proxy.addReview(form);
							if (!oprtState.equals("ADDNEW")
									&& !oaBillID.equals("")) {
								oaId = oaBillID;
							}
						} catch (Exception e2) {
							bl_ok = false;
							MsgBox.showDetailAndOK(null, "调用OA接口传送单据数据失败. \r\n点击详细信息查看详情.", e2.toString(), 1);
							System.out.println("-----------e2:" + e2);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("失败");
							logInfo.setLogTitle("调用OA接口传送单据数据时失败");
							logInfo.setLogDetail(e2.toString());
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS1");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							SysUtil.abort();
						}
						System.out.println("-----向oa传送数据后的----oaId:" + oaId);
						return oaId;
					}

					public void afterExec(Object result) throws Exception {

					}
				});
				Component[] cps = dialog.getContentPane().getComponents();
				for (Component cp : cps) {
					if (cp instanceof JLabel) {
						// 提示内容
						((JLabel) cp).setText("请等待，正在调用OA接口传送数据......");
					}
				}
				dialog.show();
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "调用OA接口传送单据数据失败. \r\n点击详细信息查看详情.", e1.toString(), 1);
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("失败");
				logInfo.setLogTitle("调用OA接口传送单据数据失败.");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS1");
				try {
					WSLogFactory.getRemoteInstance().addnew(logInfo);
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				SysUtil.abort();
			}

			// 7、更新eas合同单据的‘是否走OA审批’字段
			if (bl_ok) {
				if (!updateContractBill(fid, true, templateId, oaId)) {
					MsgBox.showInfo("回写EAS单据失败");
					SysUtil.abort();
				} else {
					MsgBox.showInfo("发起OA审批流程成功");
					try {
						String url = OASSOUtil.getSSOURL(creator, oaId);
						System.out.println("-----sso url:" + url);
						if (!url.equals("")) {
							super.actionSubmit_actionPerformed(e);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("成功");
							logInfo.setLogTitle("流程发起成功");
							logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
							logInfo.setUrl(url);
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS1");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							//mod by ypf on 20121111 刷新listui
							rebackRefreshListUI(e);
							
							uiWindow.close();
							BareBonesBrowserLaunch.openURL(url);
						} else {
							MsgBox.showInfo("没有获取到单点登录URL");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						MsgBox.showDetailAndOK(null, "单点登录OA失败,请直接登录OA查看流程. \r\n点击详细信息查看详情.", e1.toString(), 1);
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("单点登录OA失败,请直接登录OA查看流程");
						logInfo.setLogDetail(e1.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS1");
						WSLogFactory.getRemoteInstance().addnew(logInfo);
					}
				}
			} else {
				MsgBox.showWarning("发起OA审批流程失败");
				SysUtil.abort();
			}
			if (bl_ok) {
				System.out.println("-----复制这条链接登录oa查看（用户名："	+ creator+ "  密码：1）------http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="
								+ oaId);
			}
		} else if (num == 1) {
			// 直接保存
			try {
				super.actionSubmit_actionPerformed(e);
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "提交失败. \r\n点击详细信息查看详情.", e1.toString(), 1);
				
				System.out.println("----------"+e1.toString());
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("失败");
				logInfo.setLogTitle("直接在EAS提交时失败-不走OA工作流审批");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS2");
				WSLogFactory.getRemoteInstance().addnew(logInfo);
				
				SysUtil.abort();
			}

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", fid));
			view.setFilter(filter);
			ContractBillCollection col;
			try {
				col = ContractBillFactory.getRemoteInstance()
						.getContractBillCollection(view);
				ContractBillInfo info = new ContractBillInfo();
				// 取到需要更新附件的对象
				if (col != null && col.size() > 0) {
					info = col.get(0);
					info.setIsOAAudit(false);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("isOAAudit");
					try {
						// 更新附件内容
						ContractBillFactory.getRemoteInstance().updatePartial(
								info, selector);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} catch (BOSException e1) {
				e1.printStackTrace();

			}

			MsgBox.showInfo("提交保存成功");
			
			//mod by ypf on 20121111 刷新listui
			rebackRefreshListUI(e);
			
		} else {
			SysUtil.abort();
		}

		// 删除本单据引用的模板
		deleteModel(fid);
	}

	public void actionSubmit_no_attachment(ActionEvent e) {
		bl_ok = true;
		String jsonForm = getJsonForm((String) fid);
		String formHTML = getHTML(fid);
		System.out.println("------actionSubmit_actionPerformed-----fid:" + fid);

		// 如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
		String prjMappingName = PROJECT_INFO.getPrjMappingName();
//		String subject = "合同申请单";
		//合同名称
		String subject = txtcontractName.getText();// mod by ypf on 20121106
		System.out.println("--------subject主题："+subject);
		
		// 1、选择是否走OA审批
		if (num == 0) {
			if (prjMappingName == null || prjMappingName.equals("")) {
				if (MsgBox.showConfirm3("该工程项目没有配置映射名称有可能取不到模板，是否还要继续？") == MsgBox.OK) {

				} else {
					SysUtil.abort();
				}
			}

			// 2、选择模板
			// 获取蓝凌OA提供模板并保存到eas中
			List modelIDList = null;
			String keyForModel = "EAS1(" + prjMappingName + ")";
			if (oprtState.equals("ADDNEW")
					|| (billState != null && !billState.equals("") && !billState
							.equals("驳回"))) {
				getOAModelAndSaveToEAS(keyForModel, fid);
				modelIDList = showFilterDialog(fid);
			}

			// 4、准备要发起流程的数据，并传递给oa审批
			AlterAttachment at = new AlterAttachment();
			String creator = SysContext.getSysContext().getCurrentUserInfo()
					.getNumber();

			if (oprtState.equals("ADDNEW")|| (billState != null && !billState.equals("") && !billState.equals("驳回"))) {
				templateId = (modelIDList.size() > 0) ? modelIDList.get(0).toString() : "";
			}
			oaBillID = getOAID(fid);
			System.out.println("-------驳回重新提交时根据单据id查找到的oa单据id:" + oaBillID);

			//mod by ypf on 20140827 发起账号要是json
			String creatorJsonString = "";
			try {
				JSONObject creatorJson = new JSONObject();
				creatorJsonString = creatorJson.put("LoginName", creator)+"";
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			noAttForm = new KmReviewParamterNoattForm();
			noAttForm.setDocCreator(creatorJsonString);
			noAttForm.setDocSubject(subject);
			noAttForm.setFdTemplateId(templateId);
			noAttForm.setFormValues(jsonForm);
			noAttForm.setEasHtml(formHTML);
			noAttForm.setDocId(oaBillID);// oa单据id oaBillID
			System.out.println("---form----creator:" + creator + "  oaBillID:"
					+ oaBillID + "  subject:" + subject + "   templateId:"
					+ templateId + "   jsonForm:" + jsonForm + "  formHTML"
					+ formHTML);

			try {
				proxy = service.getZHKmReviewWebserviceServiceImpPort();
				bl_ok = true;
			} catch (ServiceException e2) {
				e2.printStackTrace();

				if (MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存提交？") == MsgBox.OK) {
					try {
						super.actionSubmit_actionPerformed(e);
					} catch (Exception e1) {
						e1.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("提交时失败");
						logInfo.setLogDetail(e1.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS1");
						try {
							WSLogFactory.getRemoteInstance().addnew(logInfo);
						} catch (BOSException e3) {
							e3.printStackTrace();
						}
						
						SysUtil.abort();
					}
				}
				SysUtil.abort();
			}

			// 5、检查是否有待办记录
			try {
				proxy.delTasksByID(fid);
			} catch (Exception e1) {
				e1.printStackTrace();
				if (MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存？") == MsgBox.OK) {
					try {
						super.actionSave_actionPerformed(e);
					} catch (Exception e11) {
						e11.printStackTrace();
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("远程调用OA接口失败时对当前单据进行保存失败");
						logInfo.setLogDetail(e11.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS1");
						try {
							WSLogFactory.getRemoteInstance().addnew(logInfo);
						} catch (BOSException e2) {
							e2.printStackTrace();
						}
						
						SysUtil.abort();
					}
				}
				SysUtil.abort();
			}

			// 6、调用oa的流程发起接口传值
			try {
				LongTimeDialog dialog = UITools.getDialog(this);
				dialog.setTitle("提示");// 提示框标题
				if (dialog == null)
					return;
					dialog.setLongTimeTask(new ILongTimeTask() {
					public Object exec() throws Exception {
						// 加入要等待的代码块
						try {
							oaId = proxy.addReviewNoatt(noAttForm);
							if (!oprtState.equals("ADDNEW")
									&& !oaBillID.equals("")) {
								oaId = oaBillID;
							}
						} catch (Exception e2) {
							bl_ok = false;
							MsgBox.showDetailAndOK(null, "调用OA接口传送单据数据失败. \r\n点击详细信息查看详情.", e2.toString(), 1);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("失败");
							logInfo.setLogTitle("调用OA接口传送单据数据时失败");
							logInfo.setLogDetail(e2.toString());
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS1");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							SysUtil.abort();
						}
						System.out.println("-----向oa传送数据后的----oaId:" + oaId);
						return oaId;
					}

					public void afterExec(Object result) throws Exception {

					}
				});
				Component[] cps = dialog.getContentPane().getComponents();
				for (Component cp : cps) {
					if (cp instanceof JLabel) {
						// 提示内容
						((JLabel) cp).setText("请等待，正在调用OA接口传送数据......");
					}
				}
				dialog.show();
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "远程调用OA接口传送数据失败. \r\n点击详细信息查看详情.", e1.toString(), 1);
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("失败");
				logInfo.setLogTitle("单点登录失败,请登录OA查看");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS1");
				try {
					WSLogFactory.getRemoteInstance().addnew(logInfo);
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				
				SysUtil.abort();
			}

			// 7、更新eas合同单据的‘是否走OA审批’字段
			if (bl_ok) {
				if (!updateContractBill(fid, true, templateId, oaId)) {
					MsgBox.showInfo("回写EAS单据失败");
					SysUtil.abort();
				} else {
					MsgBox.showInfo("发起OA审批流程成功");
					try {
						String url = OASSOUtil.getSSOURL(creator, oaId);
						System.out.println("-----sso url:" + url);
						if (!url.equals("")) {
							super.actionSubmit_actionPerformed(e);
							// 先保存再关闭，否则会弹出“数据已修改，是否需要保存”提示
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("成功");
							logInfo.setLogTitle("流程发起成功");
							logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
							logInfo.setUrl(url);
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS1");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							//mod by ypf on 20121111 刷新listui
							rebackRefreshListUI(e);
							
							uiWindow.close();
							BareBonesBrowserLaunch.openURL(url);
						} else {
							MsgBox.showInfo("没有获取到单点登录URL");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						MsgBox.showDetailAndOK(null, "单点登录OA失败,请直接登录OA查看流程. \r\n点击详细信息查看详情.", e1.toString(), 1);
						
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(editData.getId()+"");
						logInfo.setState("失败");
						logInfo.setLogTitle("单点登录OA失败,请直接登录OA查看流程");
						logInfo.setLogDetail(e1.toString());
						logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						logInfo.setCallType(CALLTYPE_SEND);
						logInfo.setSourceBillType("EAS1");
						try {
							WSLogFactory.getRemoteInstance().addnew(logInfo);
						} catch (BOSException e2) {
							e2.printStackTrace();
						}
					}
				}
			} else {
				MsgBox.showWarning("发起OA审批流程失败");
				SysUtil.abort();
			}
			if (bl_ok) {
				System.out.println("-----复制这条链接登录oa查看（用户名："+ creator+ "  密码：1）------http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="
								+ oaId);
			}
		} else if (num == 1) {
			// 直接保存
			try {
				super.actionSubmit_actionPerformed(e);
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showDetailAndOK(null, "提交失败. \r\n点击详细信息查看详情.", e1.toString(), 1);
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("失败");
				logInfo.setLogTitle("直接在EAS提交时失败-不走OA工作流审批");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS1");
				try {
					WSLogFactory.getRemoteInstance().addnew(logInfo);
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				
				SysUtil.abort();
			}

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", fid));
			view.setFilter(filter);
			ContractBillCollection col;
			try {
				col = ContractBillFactory.getRemoteInstance()
						.getContractBillCollection(view);
				ContractBillInfo info = new ContractBillInfo();
				// 取到需要更新附件的对象
				if (col != null && col.size() > 0) {
					info = col.get(0);
					info.setIsOAAudit(false);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("isOAAudit");
					try {
						// 更新附件内容
						ContractBillFactory.getRemoteInstance().updatePartial(info, selector);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} catch (BOSException e1) {
				e1.printStackTrace();

			}

			MsgBox.showInfo("提交保存成功");
			
			//mod by ypf on 20121111 刷新listui
			rebackRefreshListUI(e);
			
		} else {
			SysUtil.abort();
		}

		// 删除本单据引用的模板
		deleteModel(fid);
	}

	// 销毁ui，不检查
	public boolean destroyWindow() {
		System.out.println("---------------销毁ui---不检查");
		return true;
	}

	private String getOAID(String billID) {
		System.out.println("---getOAID---billID:" + billID);
		String oaID = "";

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", billID));
		view.setFilter(filter);

		ContractBillInfo info = null;
		try {
			ContractBillCollection col = ContractBillFactory
					.getRemoteInstance().getContractBillCollection(view);
			if (col != null && col.size() > 0) {
				info = col.get(0);
				oaID = (info.getOABillID() == null || info.getOABillID().equals("")) ? ""
						: info.getOABillID();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		System.out.println("-----getOAID----oaID:" + oaID);

		return oaID;
	}

	/**
	 * ************************************************** 方法说明:
	 * getJsonForm(获取单据的json)
	 * 
	 * 参数：@param fid 参数：@return 返回值：String
	 * 
	 * 修改人：yangpengfei 修改时间：2012-8-25 下午03:06:18
	 * ***************************************************
	 */
	private String getJsonForm(String fid) {
		String json = "";
		String contractNumber = (txtNumber.getText() == null) ? "" : txtNumber.getText();// 合同编号
		String contractName = (txtcontractName.getText() == null) ? "": txtcontractName.getText();// 合同名称
		//mod by ypf on 20121121
		LandDeveloperInfo landDeveloperInfo = (LandDeveloperInfo) prmtlandDeveloper.getData();
		SupplierInfo supplierB = (SupplierInfo) prmtpartB.getData();
		SupplierInfo supplierC = (SupplierInfo) prmtpartC.getData();
		String landDeveloper = landDeveloperInfo!=null?landDeveloperInfo.getName():"";// 甲方
		String partB = supplierB!=null?supplierB.getName():"";// 乙方
		String partC = supplierC!=null?supplierC.getName():"";// 丙方
		
		String localAmount = (txtLocalAmount.getText() == null) ? "": txtLocalAmount.getText();// 本币金额
		String grtAmount = (txtGrtAmount.getText() == null) ? "" : txtGrtAmount.getText();// 保修金额
		String longNumber = PROJECT_INFO.getLongNumber();// 长编码
		String longName = PROJECT_INFO.getDisplayName();// 长名称

		Map map = new HashMap();
		map.put("contract_number", contractNumber);
		map.put("bill_name", contractName);
		map.put("bill_A", landDeveloper);
		map.put("bill_B", partB);
		map.put("bill_C", partC);
		map.put("total_money", util.cutComma(localAmount));
		map.put("repair_money", util.cutComma(grtAmount));
		map.put("prj_long_number", longNumber);
		map.put("prj_long_name", longName);

		JsDataTools js = new JsDataTools();
		json = js.createJsData(map);
		System.out.println("----合同单据'" + contractName + "'-----json:" + json);

		return json;
	}

	// 是否走OA审批
	private int isOAAudit() {
		return MsgBox.showConfirm3("请选择是否走OA审批流程？");
	}

	/**
	 * ************************************************** 方法说明:
	 * updateContractBill(在走提交后，更新‘是否走oa审批’字段)
	 * 
	 * 参数：@param id 参数：@param isOAAudit 参数：@return 返回值：boolean
	 * 
	 * 修改人：yangpengfei 修改时间：2012-8-21 下午08:43:04
	 * ***************************************************
	 */
	public boolean updateContractBill(String id, boolean isOAAudit,String billTempletID, String oaBillID) {
		System.out.println("------发起流程成功后更新合同信息：" + "  eas单据id:" + id + "  是否走oa审批：" + isOAAudit + "  模板id：" + billTempletID + "  oa单据id：" + oaBillID);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		ContractBillCollection col;
		try {
			col = ContractBillFactory.getRemoteInstance()
					.getContractBillCollection(view);
			ContractBillInfo info = new ContractBillInfo();
			// 取到需要更新附件的对象
			if (col != null && col.size() > 0) {
				info = col.get(0);
				info.setIsOAAudit(isOAAudit);

				String oabillid = (info.getOABillID() == null || info
						.getOABillID().equals("")) ? "" : info.getOABillID();
				// !oabillid.equals("")
				// 第一次进来，OABillID字段为空，则更新为oaBillID；第二次，显然OABillID字段不为空
				if (oaBillID != null && !oaBillID.equals("") && oabillid.equals("")) {
					info.setOABillID(oaBillID);
				}
				// 如果要走oa审批流程，那么就把oa单据的模板id也保存
				if (isOAAudit) {
					info.setBillTempletID(billTempletID);
				}
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("isOAAudit");
				selector.add("billTempletID");
				if (oaBillID != null && !oaBillID.equals("") && oabillid.equals("")) {
					selector.add("OABillID");
				}
				try {
					// 更新附件内容
					ContractBillFactory.getRemoteInstance().updatePartial(info,
							selector);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			} else {
				return false;
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	/**
	 * ************************************************** 方法说明:
	 * getOAModelAndSaveToEAS(获取蓝凌OA提供模板并保存到eas中)
	 * 
	 * 参数：@throws Exception 返回值：void
	 * 
	 * 修改人：yangpengfei 修改时间：2012-8-21 下午04:34:11
	 * ***************************************************
	 */
	public void getOAModelAndSaveToEAS(String type, String user) {
		String obj = null;
		try {
			proxy = service.getZHKmReviewWebserviceServiceImpPort();
			obj = proxy.getTemplate(type);
		} catch (Exception e) {
			e.printStackTrace();
			MsgBox.showWarning("无法获取蓝凌OA接口提供的模板类型，请联系管理员");
			SysUtil.abort();
		}
		if (obj.length() > 0) {
			// 先清空原有模板
			try {
				ModelFactory.getRemoteInstance().delete("where id is not null and description='" + user.trim()+ "'");
			} catch (EASBizException e) {
				e.printStackTrace();
				MsgBox.showWarning("执行清空EAS系统原有OA模板时异常");
				SysUtil.abort();
			} catch (BOSException e) {
				e.printStackTrace();
				MsgBox.showWarning("执行清空EAS系统原有OA模板时异常");
				SysUtil.abort();
			}

			ModelInfo info = null;
			
			try {
				// 遍历键值对
				JSONArray jsonArray = new JSONArray(obj);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					for (int j = 0; j < object.length(); j++) {
						JSONArray list =   (JSONArray)object.get(j+1+"");
						String fid = (list.get(0) == null) ? "" : list.get(0).toString().trim();
						String fname = (list.get(1) == null) ? "" :list.get(1).toString().trim();
						
						System.out.println("fid='" + fid + "' -- fname='" + fname + "'");

						// 保存到eas数据表中
						info = new ModelInfo();
						info.setId(BOSUuid.create(info.getBOSType()));
						info.setSimpleName(fid);
						info.setName(fname);
						info.setNumber(new SimpleDateFormat("yyyyMMddHHmmssms").format(new Timestamp(new Date().getTime()))+ j + "");
						info.setDescription(user.trim());
						try {
							ModelFactory.getRemoteInstance().addnew(info);
						} catch (EASBizException e) {
							e.printStackTrace();
							MsgBox.showWarning("保存OA模板时异常111");
							SysUtil.abort();
						} catch (BOSException e) {
							e.printStackTrace();
							MsgBox.showWarning("保存OA模板时异常222");
							SysUtil.abort();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			MsgBox.showInfo("没有获取到OA接口提供的模板类型");
			SysUtil.abort();
		}
	}

	/**
	 * ************************************************** 方法说明:
	 * showFilterDialog(模板选择对话框)
	 * 
	 * 参数：@return 参数：@throws Exception 返回值：String
	 * 
	 * 修改人：yangpengfei 修改时间：2012-8-23 下午04:21:44
	 * ***************************************************
	 */
	protected List showFilterDialog(String keyForModel) {
		List fids = new ArrayList();
		KDBizPromptBox modelBox = new KDBizPromptBox();
		modelBox.setVisible(false);
		modelBox.setEnabledMultiSelection(true);
		modelBox.setQueryInfo("com.kingdee.eas.fdc.contract.app.ModelQuery");

		// 设置过滤
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("description", keyForModel));
		view.setFilter(filter);
		modelBox.setEntityViewInfo(view);

		modelBox.setDataBySelector();

		if (modelBox.getSelector().isCanceled() || modelBox.getData() == null) {
			SysUtil.abort();
		} else {
			Object infos[] = (Object[]) modelBox.getData();
			for (int i = 0; i < infos.length; i++) {
				fids.add(((ModelInfo) infos[i]).getSimpleName().toString());
				System.out.println("----选中的模板编码："
						+ ((ModelInfo) infos[i]).getSimpleName().toString());
			}
		}
		return fids;
	}

	public String getHTML(String fid) {
		String html = "";
		String org = "";// 组织
		String prjProgram = "";// 工程项目
		String contractTypeID = "";// 合同类型
		String contractNumber = "";// 合同编号
		String contractName = "";// 合同名称
		String contractRule = "";// 框架合约名称
		String landDeveloper="";//甲方
		String partB="";//乙方
		String partC="";//丙方
		String contractProper="";//合同性质
		String signDate="";//签约时间
		String currency="";//币别
		String exRate="";//汇率
		String respDept="";//负责部门
		String amount="";//原金额
		String localAmount="";//本币金额
		String respPerson="";//负责人
		String grtRate="";//保修金比例%
		String grtAmount="";//保修金额
		String bookedDate="";//业务日期
		String contractSourc="";//形成方式
		String payPercForWarn="";//提款提示比例%
		String period="";//订立期间
		String property="";//造价性质
		String chgPercForWarn="";//变更提示比例%
		String creator="";//制单人
		String costSplit="";//进入动态成本
		String isPartAMaterialCon="";//是否甲供材料合同
		String payScale="";//进度款付款比例%
		String createTime="";//制单时间
		String stampTaxRate="";//印花税率%
		String stampTaxAmt="";//印花税金额
		String overAmt="";//结算提示比例%
		String charge="";//合同费用项目
		String attachmentNameList="";//附件列表
		String bailOriAmount="";//履约保证金原币
		String bailRate="";//履约保证金比例（%）
		String item[]=new String[tblDetail.getColumnCount()];  //存放一行分录数据的数组
		
		
		org = (txtOrg.getText()==null)?"":txtOrg.getText();
		prjProgram = (txtProj.getText()==null)?"":txtProj.getText();
		contractTypeID = (prmtcontractType.getValue()==null)?"":prmtcontractType.getText();
		contractNumber = (txtNumber.getText()==null)?"":txtNumber.getText();
		contractName = (txtcontractName.getText() ==null)?"":txtcontractName.getText();
		contractRule = (textFwContract.getText()==null)?"":textFwContract.getText();
		
		// modify by ll 2012-09-21 甲乙方不需要显示编号
		/*landDeveloper=(prmtlandDeveloper.getValue()==null)?"":prmtlandDeveloper.getText().split(" ")[1];
		partB=(prmtpartB.getValue()==null)?"":prmtpartB.getText().split(" ")[1];	
		partC=(prmtpartC.getValue()==null)?"":prmtpartC.getText().split(" ")[1];*/	
		
		//mod by ypf on 20130731
		landDeveloper=(prmtlandDeveloper.getValue()==null)?"":editData.getLandDeveloper().getName();
		partB=(prmtpartB.getValue()==null)?"":editData.getPartB().getName();	
		partC=(prmtpartC.getValue()==null)?"":editData.getPartC().getName();
		
		contractProper=(contractPropert.getSelectedItem()==null)?"":contractPropert.getSelectedItem().toString();	
		signDate=(pksignDate.getText()==null)?"":pksignDate.getText();		
		currency=(comboCurrency.getSelectedItem()==null)?"":comboCurrency.getSelectedItem().toString();
		exRate=(txtExRate.getText()==null)?"":txtExRate.getText();
		respDept=(prmtRespDept.getText()==null)?"":prmtRespDept.getText();
		amount=(txtamount.getText()==null)?"":txtamount.getText();
		localAmount=(txtLocalAmount.getText()==null)?"":txtLocalAmount.getText();
		respPerson=(prmtRespPerson.getText()==null)?"":prmtRespPerson.getText();
		grtRate=(txtGrtRate.getText()==null)?"":txtGrtRate.getText();
		grtAmount=(txtGrtAmount.getText()==null)?"":txtGrtAmount.getText();
		bookedDate=(pkbookedDate.getText()==null)?"":pkbookedDate.getText();
		contractSourc=(contractSource.getText()==null)?"":contractSource.getText();
		payPercForWarn=(txtpayPercForWarn.getText()==null)?"":txtpayPercForWarn.getText();
		period=(cbPeriod.getText()==null)?"":cbPeriod.getText();
		property=(costProperty.getSelectedItem()==null)?"":costProperty.getSelectedItem().toString();
		chgPercForWarn=(txtchgPercForWarn.getText()==null)?"":txtchgPercForWarn.getText();
		creator=(txtCreator.getText()==null)?"":txtCreator.getText();
		costSplit=(chkCostSplit.isSelected()==true)?"是":"否";
		isPartAMaterialCon=(chkIsPartAMaterialCon.isSelected()==true)?"是":"否";
		payScale=(txtPayScale.getText()==null)?"":txtPayScale.getText();
		createTime=(kDDateCreateTime.getText()==null)?"":kDDateCreateTime.getText();
		stampTaxRate=(txtStampTaxRate.getText()==null)?"":txtStampTaxRate.getText();
		stampTaxAmt=(txtStampTaxAmt.getText()==null)?"":txtStampTaxAmt.getText();
		overAmt=(txtOverAmt.getText()==null)?"":txtOverAmt.getText();
		charge=(prmtCharge.getText()==null)?"":prmtCharge.getText();
		if(comboAttachmentNameList.getItemCount()>0){
			String atts="";
			  for(int i=0;i<comboAttachmentNameList.getItemCount();i++){
				  if(comboAttachmentNameList.getItemAt(i)!=null){
					  atts=atts+comboAttachmentNameList.getItemAt(i)+",";
				  }
			  }
		    int num= atts.lastIndexOf(",");
		   attachmentNameList=atts.substring(0, num);
			}
		bailOriAmount=(txtBailOriAmount.getText()==null)?"":txtBailOriAmount.getText();
		bailRate=(txtBailRate.getText()==null)?"":txtBailRate.getText();
		html=
		 "\r\n<table class='tb_normal' width=100%>		  \r\n"
		+"<tr><td colspan=6  class='td_normal_title_head' >合同基本信息</td></tr>\r\n"
		+"  <tr>              \r\n"
		+"	<td width=16% class='td_normal_title'>组织</td>	  \r\n"
		+"	<td width=16% >"+org+"</td> \r\n"
		+"	<td width=16% class='td_normal_title'>工程项目</td>      \r\n"
		+"	<td width=16% >"+prjProgram+"</td>        \r\n"
		+"	<td width=16% class='td_normal_title'>合同类型</td>         \r\n"
		+"	<td width=16% >"+contractTypeID+"</td>         \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td class='td_normal_title'>合同编号</td>         \r\n"
		+"	<td >"+contractNumber+"</td>         \r\n"
		+"	<td class='td_normal_title'>合同名称</td>         \r\n"
		+"	<td >"+contractName+"</td>         \r\n"
		+"	<td class='td_normal_title'>框架合约名称</td>         \r\n"
		+"	<td >"+contractRule+"</td>         \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td class='td_normal_title'>甲方</td>         \r\n"
		+"	<td >"+landDeveloper+"</td>         \r\n"
		+"	<td class='td_normal_title'>乙方</td>         \r\n"
		+"	<td >"+partB+"</td>         \r\n"
		+"	<td class='td_normal_title'>丙方</td>         \r\n"
		+"	<td >"+partC+"</td>         \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td class='td_normal_title'>合同性质</td>         \r\n"
		+"	<td >"+contractProper+"</td>         \r\n"
		+"	<td class='td_normal_title'>签约时间</td>         \r\n"
		+"	<td >"+signDate+"</td>         \r\n"
		+"	<td class='td_normal_title'>币别</td>         \r\n"
		+"	<td>"+currency+"</td>         \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td class='td_normal_title'>汇率</td>         \r\n"
		+"	<td>"+util.getDecimal(exRate)+"</td>         \r\n"
		+"	<td class='td_normal_title'>负责部门</td>         \r\n"
		+"	<td>"+respDept+"</td>         \r\n"
		+"	<td class='td_normal_title'>原金额</td>         \r\n"
		+"	<td >"+util.getDecimal(amount)+"</td>         \r\n"
		+"  <tr>              \r\n"
		+"	<td class='td_normal_title'>本币金额</td>         \r\n"
		+"	<td >"+util.getDecimal(localAmount)+"</td>         \r\n"
		+"	<td class='td_normal_title'>责任人</td>         \r\n"
		+"	<td >"+respPerson+"</td>         \r\n"
		+"	<td class='td_normal_title'>保修金比例%</td>         \r\n"
		+"	<td >"+util.getDecimal(grtRate)+"</td>         \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td class='td_normal_title'>保修金额</td>         \r\n"
		+"	<td >"+util.getDecimal(grtAmount)+"</td>         \r\n"
		+"	<td class='td_normal_title'>业务日期</td>         \r\n"
		+"	<td >"+bookedDate+"</td>         \r\n"
		+"	<td class='td_normal_title'>形成方式</td>         \r\n"
		+"	<td >"+contractSourc+"</td>         \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td class='td_normal_title'>提款提示比例%</td>         \r\n"
		+"	<td >"+util.getDecimal(payPercForWarn)+"</td>         \r\n"
		+"	<td class='td_normal_title'>订立期间</td>         \r\n"
		+"	<td >"+period+"</td>         \r\n"
		+"	<td class='td_normal_title'>造价性质</td>         \r\n"
		+"	<td >"+property+"</td>         \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td class='td_normal_title'>变更提示比例%</td>         \r\n"
		+"	<td >"+util.getDecimal(chgPercForWarn)+"</td>         \r\n"
		+"	<td class='td_normal_title'>制单人</td>         \r\n"
		+"	<td >"+creator+"</td>         \r\n"
		+"	<td class='td_normal_title'>进入动态成本</td>         \r\n"
		+"  <td >"+costSplit+"</td>         \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"  <td class='td_normal_title' >是否甲供材料合同"
		+"  <td>"+isPartAMaterialCon+"</td>         \r\n"
		+"	<td class='td_normal_title'>进度款付款比例%</td>         \r\n"
		+"	<td >"+util.getDecimal(payScale)+"</td>         \r\n"
		+"	<td class='td_normal_title'>制单时间</td>         \r\n"
		+"	<td >"+createTime+"</td>         \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td class='td_normal_title'>印花税率%</td>         \r\n"
		+"	<td >"+util.getDecimal(stampTaxRate)+"</td>         \r\n"
		+"	<td class='td_normal_title'>印花税金额</td>         \r\n"
		+"	<td >"+util.getDecimal(stampTaxAmt)+"</td>         \r\n"
		+"	<td class='td_normal_title'>结算提示比例%</td>         \r\n"
		+"	<td>"+util.getDecimal(overAmt)+"</td>         \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td class='td_normal_title'>附件列表</td>         \r\n"
		+"	<td colspan=5>"+attachmentNameList+"</td>         \r\n"
		+"  </tr>              \r\n";
		if(tblDetail.getRowCount()>0){
		html=html+"<tr><td colspan=6 width=100% ><table class='tb_normal'  width=100%>         \r\n"
		+"<tr><td colspan=3  class='td_normal_title_head'>合同详细信息</td>" 
		+"</tr>         \r\n";
		
		for(int i=0;i<tblDetail.getHeadRowCount();i++){
			html+="<tr>              \r\n";
			for(int j=0;j<tblDetail.getColumnCount();j++){
				if(tblDetail.getColumn(j).getStyleAttributes().isHided()==false){
				item[j]=tblDetail.getHeadRow(i).getCell(j).getValue().toString();
				html=html+"<td>"+item[j]+"</td>         \r\n";
				}
			}
			item=new String[tblDetail.getColumnCount()];
			html+="</tr>              \r\n";
			
		}
		
		for(int i=0;i<tblDetail.getRowCount();i++){
			html+="<tr>              \r\n";
			for(int j=0;j<tblDetail.getColumnCount();j++){
				if(tblDetail.getColumn(j).getStyleAttributes().isHided()==false){
					if(tblDetail.getCell(i, j).getValue()==null){
						item[j]="";
					}else{
						item[j]=  util.getDecimal(tblDetail.getCell(i, j).getValue().toString());
						
						if(tblDetail.getCell(i, j).getValue().toString().contains(":"))
						{
							//mod by ypf on 20120220 修改日期显示格式
							try
							{
							    item[j] = new SimpleDateFormat("yyyy-MM-dd").format(new Date(tblDetail.getCell(i, j).getValue().toString()));
							}catch(Exception ee)
							{
								item[j] =tblDetail.getCell(i, j).getValue().toString();
							}
						}
					}
					/*
					if(j==1){
						html=html+"<td class='td_test'>"+item[j]+"</td>         \r\n";
					}else{
						html=html+"<td>"+item[j]+"</td>         \r\n";
					}
					*/
					html=html+"<td>"+item[j]+"</td>         \r\n";
				}
			}
			item=new String[tblDetail.getColumnCount()];
			
			html+="</tr>              \r\n";
		}
		html=html+" </table> </td></tr>             \r\n";
		}
		html=html+"</table>            \r\n";
		
		System.out.println("---------html:"+html);
		return html;
	}
	
	private String numberString = "";
	protected void ceremonyb_dataChanged(DataChangeEvent e) {
		numberString = this.txtNumber.getText();
		super.ceremonyb_dataChanged(e);
		this.txtNumber.setText(numberString);
	}
	
}
