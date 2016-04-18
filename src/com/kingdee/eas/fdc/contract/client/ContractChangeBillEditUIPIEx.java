package com.kingdee.eas.fdc.contract.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.JsDataTools;
import com.kingdee.eas.fdc.contract.ModelFactory;
import com.kingdee.eas.fdc.contract.ModelInfo;
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

public class ContractChangeBillEditUIPIEx extends ContractChangeBillEditUI {
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
	private static ContractChangeBillListUIPIEx listUI=null;//add by ypf on 20121023 刷新listui
	private static FDCUtil util;
	private static String origalMoney="";//原币金额，及oa的合同总价
	
	private static String CALLTYPE_SEND ="发送";//写log用的

	public ContractChangeBillEditUIPIEx() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("VIEW")||oprtState.equals("EDIT"))
			{
			  listUI = (ContractChangeBillListUIPIEx) this.getUIContext().get("Owner");
			}
		}
		
		//add by ypf on 2015年7月20日
		if(oprtState.equals("ADDNEW")){
			setUITitle("现场签证单-新增");
		}
		if(oprtState.equals("VIEW")){
			setUITitle("现场签证单-查看");
		}
		if(oprtState.equals("EDIT")){
			setUITitle("现场签证单-修改");
		}
	}
	
	//xub  修改 mod 2013.11.28   3.变更指令单的变更原因和提出单位两个字段修改为可录字段（代码被覆盖了）。
	public void onShow() throws Exception {
	    super.onShow();

	    this.prmtChangeReason.setEnabled(true);
	    this.prmtConductUnit.setEnabled(true);
	  }
	
	public void rebackRefreshListUI(ActionEvent e)
	{
		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("VIEW")||oprtState.equals("EDIT"))
			{
			   listUI = (ContractChangeBillListUIPIEx) this.getUIContext().get("Owner");
			   try {
				    listUI.actionRefresh_actionPerformed(e);
			   } catch (Exception e1) {
					e1.printStackTrace();
			   }
			}
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = (!editData.getId().equals("") && editData.getId() != null) ? editData.getId().toString(): "";
		if (oprtState.equals("VIEW")) {
			fid = id;
		}

		// 检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
		if (!fid.equals("")) {
			boolean isEnableSubmit = FDCUtil
					.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractChangeBill WHERE fid='"
							+ fid + "'");
			if (!isEnableSubmit) {
				MsgBox.showInfo("单据已经提交OA走审批流程，不能删除");
				SysUtil.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		String id = (!editData.getId().equals("") && editData.getId() != null) ? editData
				.getId().toString()
				: "";
		if (oprtState.equals("VIEW")) {
			fid = id;
		}

		// 检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
		if (!fid.equals("")) {
			boolean isEnableSubmit = FDCUtil
					.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractChangeBill WHERE fid='"
							+ fid + "'");
			if (!isEnableSubmit) {
				MsgBox.showInfo("单据已经提交OA走审批流程，不能编辑");
				SysUtil.abort();
			}
		}
		super.actionEdit_actionPerformed(arg0);
	}

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

	private String getBillState(String id) {
		String state = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);

		try {
			ContractChangeBillCollection col = ContractChangeBillFactory
					.getRemoteInstance().getContractChangeBillCollection(view);
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
			ContractChangeBillCollection col = ContractChangeBillFactory
					.getRemoteInstance().getContractChangeBillCollection(view);
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
			ContractChangeBillCollection col = ContractChangeBillFactory
					.getRemoteInstance().getContractChangeBillCollection(view);
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
	
	//add by ypf on 2015年9月8日20:30:17
	public void chkTblVisaIsSelected(){
		boolean isSelected = false;
		for (int i = 0; i < tblVisa.getRowCount(); i++) {
			for (int j = 0; j < tblVisa.getColumnCount(); j++) {
				if (tblVisa.getColumn(j).getStyleAttributes().isHided() == false) {
					String strCol = "";
					if (tblVisa.getCell(i, j).getValue() == null) {
					} else {
						strCol = tblVisa.getCell(i, j).getValue().toString();
						if(strCol=="true" || strCol.equals("true"))
						{
							isSelected = true;
							break;
						}
						if(strCol=="false" || strCol.equals("false"))
						{
							isSelected = false;
						}
					}
				}
				
			}
			
			if(!isSelected){
				break;
			}
		}
		
		if(!isSelected){
			MsgBox.showWarning("执行情况分录必须勾选");
			SysUtil.abort();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// 重置静态变量
		resetParam();
		
		//add by ypf on 2015年9月8日20:30:17
		chkTblVisaIsSelected();
		
		try {
			super.verifyData();
		} catch (Exception e3) {
			e3.printStackTrace();
			SysUtil.abort();
		}
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

		boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractChangeBill WHERE fid='"+ fid + "'");
		if (!isEnableSubmit) {
			MsgBox.showInfo("单据已经提交OA走审批流程，不能重复提交");
			SysUtil.abort();
		}

		//合同原币金额，oa的合同总价   add by ypf on 20121129
		String sql_getMoney = "SELECT c.foriginalamount originalMoney FROM T_CON_ContractBill c LEFT JOIN  T_CON_ContractChangeBill b ON c.fid = b.fcontractbillid WHERE b.fid ='"+fid+"' ";
		origalMoney = FDCUtil.getMoney(sql_getMoney);
		
		num = isOAAudit();

		// 1、选择是否走OA审批
		if (num == 0) {
			// 1.1、准备初始数据
			PROJECT_INFO = getPrjInfo(fid);// 初始化项目工程对象

			// 判断是否存在附件
			String sql = "SELECT t1.fid billID,t3.fid attachmentID FROM T_CON_ContractChangeBill t1 left join T_BAS_BoAttchAsso t2 on t1.fid=t2.fboid left join  T_BAS_Attachment t3 on t2.fattachmentid =t3.fid WHERE t1.fid='"
					+ fid + "'";
			util = new FDCUtil();
			if (!util.checkExistAttachment(sql)) {
				actionSubmit_no_attachment(e);
				SysUtil.abort();
			}

			bl_ok = true;
			String jsonForm = getJsonForm((String) fid);
			String formHTML = "";
			try {
				formHTML = getHTML(fid);
			} catch (BOSException e4) {
				e4.printStackTrace();
			}
			System.out.println("------actionSubmit_actionPerformed-----fid:"
					+ fid);

			// 如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
			String prjMappingName = PROJECT_INFO.getPrjMappingName();

			//工程项目+变更类型+单据名称
			String subject = prmtCurProject.getText()+prmtChangeType.getText()+txtName.getText();//mod by ypf on 20121106
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
			String keyForModel = "EAS4(" + prjMappingName + ")";
			if (billState != null && billState != "" && !billState.equals("驳回")) {
				getOAModelAndSaveToEAS(keyForModel, fid);
				modelIDList = showFilterDialog(fid);
			}

			// 4、准备要发起流程的数据，并传递给oa审批
			AlterAttachment at = new AlterAttachment();
			String creator = SysContext.getSysContext().getCurrentUserInfo()
					.getNumber();

			if (billState != null && billState != "" && !billState.equals("驳回")) {
				templateId = (modelIDList.size() > 0) ? modelIDList.get(0)
						.toString() : "";
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
			System.out.println("---form----creator:" + creator + "  "
					+ (oprtState.equals("ADDNEW") ? "新增状态" : "编辑状态")
					+ "oaBillID:" + oaBillID + "  subject:" + subject
					+ "   templateId:" + templateId + "   jsonForm:" + jsonForm
					+ "  formHTML" + formHTML);

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
						logInfo.setSourceBillType("EAS4");
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
						logInfo.setSourceBillType("EAS4");
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
							logInfo.setSourceBillType("EAS4");
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
				logInfo.setLogTitle("调用OA接口传送单据数据失败. ");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS4");
				try {
					WSLogFactory.getRemoteInstance().addnew(logInfo);
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				SysUtil.abort();
			}

			// 7、更新eas合同单据的‘是否走OA审批’字段
			if (bl_ok) {
				if (!updateContractChangeBill(fid, true, templateId, oaId)) {
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
							logInfo.setSourceBillType("EAS4");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							//mod by ypf on 20121111 刷新listui
							rebackRefreshListUI(e);
							
							// 先保存再关闭，否则会弹出“数据已修改，是否需要保存”提示
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
						logInfo.setSourceBillType("EAS4");
						WSLogFactory.getRemoteInstance().addnew(logInfo);
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
				System.out.println("----------"+e1.toString());
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(editData.getId()+"");
				logInfo.setState("失败");
				logInfo.setLogTitle("直接在EAS提交时失败-不走OA工作流审批");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS4");
				WSLogFactory.getRemoteInstance().addnew(logInfo);
				
				SysUtil.abort();
			}

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", fid));
			view.setFilter(filter);
			ContractChangeBillCollection col;
			try {
				col = ContractChangeBillFactory.getRemoteInstance()
						.getContractChangeBillCollection(view);
				ContractChangeBillInfo info = new ContractChangeBillInfo();
				// 取到需要更新附件的对象
				if (col != null && col.size() > 0) {
					info = col.get(0);
					info.setIsOAAudit(false);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("isOAAudit");
					try {
						// 更新附件内容
						ContractChangeBillFactory.getRemoteInstance()
								.updatePartial(info, selector);
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

		deleteModel(fid);
	}

	public void actionSubmit_no_attachment(ActionEvent e) {
		bl_ok = true;
		String jsonForm = getJsonForm((String) fid);
		String formHTML = "";
		try {
			formHTML = getHTML(fid);
		} catch (BOSException e4) {
			e4.printStackTrace();
		}
		System.out.println("------actionSubmit_actionPerformed-----fid:" + fid);

		// 如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
		String prjMappingName = PROJECT_INFO.getPrjMappingName();
		
		//工程项目+变更类型+单据名称
		String subject = prmtCurProject.getText()+prmtChangeType.getText()+txtName.getText();//mod by ypf on 20121106
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
			String keyForModel = "EAS4(" + prjMappingName + ")";
			if (oprtState.equals("ADDNEW")
					|| (billState != null && billState != "" && !billState.equals("驳回"))) {
				getOAModelAndSaveToEAS(keyForModel, fid);
				modelIDList = showFilterDialog(fid);
			}

			// 4、准备要发起流程的数据，并传递给oa审批
			AlterAttachment at = new AlterAttachment();
			String creator = SysContext.getSysContext().getCurrentUserInfo()
					.getNumber();

			if (oprtState.equals("ADDNEW")|| (billState != null && billState != "" && !billState.equals("驳回"))) {
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
						logInfo.setSourceBillType("EAS4");
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
						logInfo.setSourceBillType("EAS4");
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
							logInfo.setSourceBillType("EAS4");
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
				logInfo.setSourceBillType("EAS4");
				try {
					WSLogFactory.getRemoteInstance().addnew(logInfo);
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				
				SysUtil.abort();
			}

			// 7、更新eas合同单据的‘是否走OA审批’字段
			if (bl_ok) {
				if (!updateContractChangeBill(fid, true, templateId, oaId)) {
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
							logInfo.setSourceBillType("EAS4");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							//mod by ypf on 20121111 刷新listui
							rebackRefreshListUI(e);
							
							// 先保存再关闭，否则会弹出“数据已修改，是否需要保存”提示
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
						logInfo.setSourceBillType("EAS4");
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
				logInfo.setSourceBillType("EAS4");
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
			ContractChangeBillCollection col;
			try {
				col = ContractChangeBillFactory.getRemoteInstance()
						.getContractChangeBillCollection(view);
				ContractChangeBillInfo info = new ContractChangeBillInfo();
				// 取到需要更新附件的对象
				if (col != null && col.size() > 0) {
					info = col.get(0);
					info.setIsOAAudit(false);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("isOAAudit");
					try {
						// 更新附件内容
						ContractChangeBillFactory.getRemoteInstance()
								.updatePartial(info, selector);
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

		deleteModel(fid);
	}

	private String getOAID(String billID) {
		System.out.println("---getOAID---billID:" + billID);
		String oaID = "";

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", billID));
		view.setFilter(filter);

		ContractChangeBillInfo info = null;
		try {
			ContractChangeBillCollection col = ContractChangeBillFactory
					.getRemoteInstance().getContractChangeBillCollection(view);
			if (col != null && col.size() > 0) {
				info = col.get(0);
				oaID = (info.getOABillID() == null || info.getOABillID() == "") ? ""
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
		String contractId = (fid == null || fid == "") ? "" : fid;// 合同id
		String billNumber = (txtNumber.getText() == null) ? "" : txtNumber.getText();// 单据编号
		String billName = (txtName.getText() == null) ? "" : txtName.getText(); // 单据名称
		String budgetOriAmount = (txtBudgetOriAmount.getText() == null) ? "": txtBudgetOriAmount.getText(); // 预算原币金额
		String unit = (prmtConstrUnit.getText() == null) ? "" : prmtConstrUnit.getText(); // 施工单位
		String contractName = (txtContractName.getText() == null) ? "": txtContractName.getText();// 合同名称
		
		String totalMoney = "";//合同总价   add by ypf on 20121129
		String addedMoney = "";//合同累计已付款    add by ypf on 20121129

		//mod by ypf on 20121121  变更原因
		String changeReason = "";
		if(kdtEntrys.getRowCount()>0)
		{
			for (int m = 0; m < kdtEntrys.getRowCount(); m++) {
				String changeContent = kdtEntrys.getCell(m, "changeContent").getValue()!=null?kdtEntrys.getCell(m, "changeContent").getValue().toString():"";
				changeReason += "  " + changeContent +";";
			}
		}
		
		String longNumber = PROJECT_INFO.getLongNumber();// 长编码
		String longName = PROJECT_INFO.getDisplayName();// 长名称
		String contractNumber = (prmtContractBill.getText() == null) ? "" : prmtContractBill.getText();// 合同编号
		Map map = new HashMap();
		// map.put("bill_id", contractId);////废弃不用

		map.put("bill_number", billNumber);
		map.put("bill_name", billName);
//		map.put("change_money", util.cutComma(budgetOriAmount));
		
		//mod by ypf on 20130731 使用EASBak_1
		map.put("EASBak_1", util.cutComma(budgetOriAmount));
		map.put("construction_unit", unit);
		map.put("contract_name_1", contractName);
		map.put("modify_reason", changeReason);
		map.put("prj_long_number", longNumber);
		map.put("prj_long_name", longName);
		map.put("contract_number_1", contractNumber);
		
//		map.put("total_money", util.cutComma(origalMoney));//合同的原币金额，oa的合同总价  add by ypf on 20121129  
//		map.put("added_money", "");//合同累计已付款  add by ypf on 20121129  
		
		//mod by ypf on 20150723,原先是用上面2个
		map.put("contract_money_1", util.cutComma(origalMoney));//合同的原币金额，oa的合同总价  add by ypf on 20121129  
//		map.put("added_money", "");//合同累计已付款  add by ypf on 20121129  

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
	 * updateContractChangeBill(在走提交后，更新‘是否走oa审批’字段)
	 * 
	 * 参数：@param id 参数：@param isOAAudit 参数：@return 返回值：boolean
	 * 
	 * 修改人：yangpengfei 修改时间：2012-8-21 下午08:43:04
	 * ***************************************************
	 */
	public boolean updateContractChangeBill(String id, boolean isOAAudit,
			String billTempletID, String oaBillID) {
		System.out.println("------发起流程成功后更新合同信息：" + "  eas单据id:" + id
				+ "  是否走oa审批：" + isOAAudit + "  模板id：" + billTempletID
				+ "  oa单据id：" + oaBillID);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		ContractChangeBillCollection col;
		try {
			col = ContractChangeBillFactory.getRemoteInstance()
					.getContractChangeBillCollection(view);
			ContractChangeBillInfo info = new ContractChangeBillInfo();
			// 取到需要更新附件的对象
			if (col != null && col.size() > 0) {
				info = col.get(0);
				info.setIsOAAudit(isOAAudit);

				String oabillid = (info.getOABillID() == null || info
						.getOABillID() == "") ? "" : info.getOABillID();
				// !oabillid.equals("")
				// 第一次进来，OABillID字段为空，则更新为oaBillID；第二次，显然OABillID字段不为空
				if (oaBillID != null && oaBillID != "" && oabillid.equals("")) {
					info.setOABillID(oaBillID);
				}
				// 如果要走oa审批流程，那么就把oa单据的模板id也保存
				if (isOAAudit) {
					info.setBillTempletID(billTempletID);
				}
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("isOAAudit");
				selector.add("billTempletID");
				if (oaBillID != null && oaBillID != "" && oabillid.equals("")) {
					selector.add("OABillID");
				}
				try {
					// 更新附件内容
					ContractChangeBillFactory.getRemoteInstance()
							.updatePartial(info, selector);
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
				new FilterItemInfo("description", keyForModel.trim()));
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

	public String getHTML(String fid) throws BOSException {
		String html = "";
		String number = "";// 单据编号
		String name = "";// 单据名称
		String curProject = "";// 工程项目
		String changeType = "";// 变更类型
		String changeReason = "";// 变更原因
		String jobType = "";// 承包类型
		String bookedDate = "";// 业务日期
		String urgentDegree = "";// 紧急程度
		String specialtyType = "";// 专业类型
		String period = "";// 变更期间
		String conductDept = "";// 提出部门
		String changeSubject = "";// 变更主题
		String graphCount = "";// 随图情况
		String conductUnit = "";// 提出单位
		String state = "";// 单据状态
		String offer = "";// 提出方
		String constrUnit = "";// 施工单位
		String constrSite = "";// 施工部位
		String visaType = "";// 签证类型
		String isImportChange = "";// 是否重大变更
		String attachment = "";// 附件列表
		String item[] = new String[tblVisa.getColumnCount()]; // 存放一行分录数据的数组
		String contractBill = "";// 合同号
		String contractName = "";// 合同名称
		String mainSupp = "";// 主送单位
		String isDeduct = "";// 是否责任扣款单位
		String balanceType = "";// 结算方式
		String deductAmount = "";// 扣款金额
		String deductReason = "";// 扣款原因
		String currency = "";// 币别
		String budgetOriAmount = "";// 预算原币金额
		String budgetAmount = "";// 预算本位币金额
		String exRate = "";// 汇率
		String isSureChangeAmt = "";// 是否确定变更金额
		String balanceAmount = "";// 结算本位币金额
		String auditNumber = "";// 审批单编码
		String oriBalanceAmount = "";// 结算原币金额
		String nouse = "";// 无效成本金额
		String originalContactNum = "";// 原始联系单号
		String validReason = "";// 无效成本原因
		String constructPrice = "";// 施工方报审金额
		String creator = "";// 制单人
		String createDate = "";// 制单日期
		String auditor = "";// 审核人
		String auditTime = "";// 审核日期

		number = (txtNumber.getText() == null) ? "" : txtNumber.getText();
		name = (txtName.getText() == null) ? "" : txtName.getText();
		curProject = (prmtCurProject.getText() == null) ? "" : prmtCurProject
				.getText();
		changeType = (prmtChangeType.getText() == null) ? "" : prmtChangeType
				.getText();
		changeReason = (prmtChangeReason.getText() == null) ? ""
				: prmtChangeReason.getText();
		jobType = (prmtJobType.getText() == null) ? "" : prmtJobType.getText();
		bookedDate = (pkbookedDate.getText() == null) ? "" : pkbookedDate
				.getText();
		urgentDegree = (comboUrgentDegree.getSelectedItem() == null) ? ""
				: comboUrgentDegree.getSelectedItem().toString();
		specialtyType = (prmtSpecialtyType.getText() == null) ? ""
				: prmtSpecialtyType.getText();
		period = (cbPeriod.getText() == null) ? "" : cbPeriod.getText();
		conductDept = (prmtConductDept.getText() == null) ? ""
				: prmtConductDept.getText();
		changeSubject = (txtChangeSubject.getText() == null) ? ""
				: txtChangeSubject.getText();
		graphCount = (comboGraphCount.getSelectedItem() == null) ? ""
				: comboGraphCount.getSelectedItem().toString();
		conductUnit = (prmtConductUnit.getText() == null) ? ""
				: prmtConductUnit.getText();
		state = (comboState.getSelectedItem() == null) ? "" : comboState
				.getSelectedItem().toString();
		offer = (comboOffer.getSelectedItem() == null) ? "" : comboOffer
				.getSelectedItem().toString();
		constrUnit = (prmtConstrUnit.getText() == null) ? "" : prmtConstrUnit
				.getText();
		constrSite = (txtConstrSite.getText() == null) ? "" : txtConstrSite
				.getText();
		visaType = (prmtVisaType.getText() == null) ? "" : prmtVisaType
				.getText();
		isImportChange = (chkIsImportChange.isSelected() == true) ? "是" : "";
		if (cmbAttachment.getItemCount() > 0) {

			String atts = "";
			for (int i = 0; i < cmbAttachment.getItemCount(); i++) {
				if (cmbAttachment.getItemAt(i) != null) {
					atts = atts + cmbAttachment.getItemAt(i) + ",";
				}
			}
			int num = atts.lastIndexOf(",");
			attachment = atts.substring(0, num);

		}
		contractBill = (prmtContractBill.getText() == null) ? ""
				: prmtContractBill.getText();
		contractName = (txtContractName.getText() == null) ? ""
				: txtContractName.getText();
		mainSupp = (prmtMainSupp.getText() == null) ? "" : prmtMainSupp
				.getText();
		isDeduct = (chkIsDeduct.isSelected() == true) ? "是" : "";
		balanceType = (txtBalanceType.getText() == null) ? "" : txtBalanceType
				.getText();
		deductAmount = (txtDeductAmount.getText() == null) ? ""
				: txtDeductAmount.getText();
		deductReason = (txtDeductReason.getText() == null) ? ""
				: txtDeductReason.getText();
		currency = (prmtCurrency.getText() == null) ? "" : prmtCurrency
				.getText();
		budgetOriAmount = (txtBudgetOriAmount.getText() == null) ? ""
				: txtBudgetOriAmount.getText();
		budgetAmount = (txtBudgetAmount.getText() == null) ? ""
				: txtBudgetAmount.getText();
		exRate = (txtExRate.getText() == null) ? "" : txtExRate.getText();
		isSureChangeAmt = (chkIsSureChangeAmt.isSelected() == true) ? "是" : "";
		balanceAmount = (txtBalanceAmount.getText() == null) ? ""
				: txtBalanceAmount.getText();
		auditNumber = (txtAuditNumber.getText() == null) ? "" : txtAuditNumber
				.getText();
		oriBalanceAmount = (txtOriBalanceAmount.getText() == null) ? ""
				: txtOriBalanceAmount.getText();
		nouse = (txtNouse.getText() == null) ? "" : txtNouse.getText();
		originalContactNum = (txtOriginalContactNum.getText() == null) ? ""
				: txtOriginalContactNum.getText();
		validReason = (prmtValidReason.getText() == null) ? ""
				: prmtValidReason.getText();
		constructPrice = (txtConstructPrice.getText() == null) ? ""
				: txtConstructPrice.getText();
		creator = (prmtCreator.getText() == null) ? "" : prmtCreator.getText();
		createDate = (pkCreateDate.getText() == null) ? "" : pkCreateDate
				.getText();
		auditor = (prmtAuditor.getText() == null) ? "" : prmtAuditor.getText();
		auditTime = (pkAuditTime.getText() == null) ? "" : pkAuditTime
				.getText();

		html = "\r\n<table class='tb_normal' width=100%>		  \r\n"
				+ "<tr><td colspan=6  class='td_normal_title_head'width=15%>变更签证单表头</td></tr>\r\n"
				+ "  <tr>              \r\n"
				+ "	<td width=16% class='td_normal_title'>单据编号</td>	  \r\n"
				+ "	<td width=16%>"
				+ number
				+ "</td> \r\n"
				+ "	<td width=16% class='td_normal_title'>单据名称</td>	  \r\n"
				+ "	<td width=16%>"
				+ name
				+ "</td> \r\n"
				+ "	<td width=16% class='td_normal_title'>工程项目</td>	  \r\n"
				+ "	<td width=16%>"
				+ curProject
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>变更类型</td>	  \r\n"
				+ "	<td  >"
				+ changeType
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>变更原因</td>	  \r\n"
				+ "	<td  >"
				+ changeReason
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>承包类型</td>	  \r\n"
				+ "	<td  >"
				+ jobType
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>业务日期</td>	  \r\n"
				+ "	<td  >"
				+ bookedDate
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>紧急程度</td>	  \r\n"
				+ "	<td  >"
				+ urgentDegree
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>专业类型</td>	  \r\n"
				+ "	<td  >"
				+ specialtyType
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>变更期间</td>	  \r\n"
				+ "	<td  >"
				+ period
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>提出部门</td>	  \r\n"
				+ "	<td  >"
				+ conductDept
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>变更主题</td>	  \r\n"
				+ "	<td  >"
				+ changeSubject
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>附图情况</td>	  \r\n"
				+ "	<td  >"
				+ graphCount
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>提出单位</td>	  \r\n"
				+ "	<td  >"
				+ conductUnit
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>单据状态</td>	  \r\n"
				+ "	<td  >"
				+ state
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>提出方</td>	  \r\n"
				+ "	<td  >"
				+ offer
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>施工单位</td>	  \r\n"
				+ "	<td  >"
				+ constrUnit
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>施工部位</td>	  \r\n"
				+ "	<td  >"
				+ constrSite
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>签证类型</td>	  \r\n"
				+ "	<td  >"
				+ visaType
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>是否重大变更</td>	  \r\n"
				+ "	<td  >"
				+ isImportChange
				+ "</td> \r\n"
				+ "	<td  colspan=2></td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>附件列表</td>	  \r\n"
				+ "	<td  >"
				+ attachment
				+ "</td> \r\n"
				+ "	<td  colspan=4></td> \r\n"
				+ "  </tr>             \r\n";
		
		//执行内容部分
		if (kdtEntrys.getRowCount() > 0) {
			html = html
					+ "  <tr> \r\n" 
					+ "   <td colspan=6> \r\n" 
					+ "    <table class='tb_normal' width=100%> \r\n"
					+ "     <tr><td colspan=3 class='td_normal_title_head'>签证信息-执行内容</td></tr> \r\n ";

			for (int i = 0; i < kdtEntrys.getHeadRowCount(); i++) {
				html += "     <tr>              \r\n";
				for (int j = 0; j < kdtEntrys.getColumnCount(); j++) {
					if (kdtEntrys.getColumn(j).getStyleAttributes().isHided() == false) {
						String strHeaderCol = kdtEntrys.getHeadRow(i).getCell(j).getValue().toString();
						html = html + "      <td>" + strHeaderCol + "</td>         \r\n";
					}
				}
				html += "     </tr>              \r\n";
			}

			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				html += "     <tr>              \r\n";
				for (int j = 0; j < kdtEntrys.getColumnCount(); j++) {
					if (kdtEntrys.getColumn(j).getStyleAttributes().isHided() == false) {
						String strCol = "";
						if (kdtEntrys.getCell(i, j).getValue() == null) {
							strCol = "";
						} else {
							strCol = kdtEntrys.getCell(i, j).getValue().toString();
							if(strCol=="true" || strCol.equals("true"))
							{
								strCol = "是";
							}
							if(strCol=="false" || strCol.equals("false"))
							{
								strCol = "";
							}
						}
						html = html + "      <td>" + strCol + "</td>         \r\n";
					}
				}
				html += "     </tr>              \r\n";
			}
		}
		html = html + "    </table> \r\n" 
				+ "     </td> \r\n" 
				+ "  </tr>             \r\n";
				
		//执行情况部分
		//-------start mod by ypf on 20150621
		if (tblVisa.getRowCount() > 0) {
			html = html
					+ "  <tr> \r\n" 
					+ "   <td colspan=6> \r\n" 
					+ "    <table class='tb_normal' width=100%> \r\n"
					+ "     <tr><td colspan=5 class='td_normal_title_head'>签证信息-执行情况</td></tr> \r\n ";

			for (int i = 0; i < tblVisa.getHeadRowCount(); i++) {
				html += "     <tr>              \r\n";
				for (int j = 0; j < tblVisa.getColumnCount(); j++) {
					if (tblVisa.getColumn(j).getStyleAttributes().isHided() == false) {
						String strHeaderCol = tblVisa.getHeadRow(i).getCell(j).getValue().toString();
						html = html + "      <td>" + strHeaderCol + "</td>         \r\n";
					}
				}
				html += "     </tr>              \r\n";
			}

			for (int i = 0; i < tblVisa.getRowCount(); i++) {
				html += "     <tr>              \r\n";
				for (int j = 0; j < tblVisa.getColumnCount(); j++) {
					if (tblVisa.getColumn(j).getStyleAttributes().isHided() == false) {
						String strCol = "";
						if (tblVisa.getCell(i, j).getValue() == null) {
							strCol = "";
						} else {
							strCol = tblVisa.getCell(i, j).getValue().toString();
							if(strCol=="true" || strCol.equals("true"))
							{
								strCol = "是";
							}
							if(strCol=="false" || strCol.equals("false"))
							{
								strCol = "";
							}
						}
						html = html + "      <td>" + strCol + "</td>         \r\n";
					}
				}
				html += "     </tr>              \r\n";
			}
		}
		html = html + "    </table> \r\n" 
				+ "     </td> \r\n" 
				+ "  </tr>             \r\n";
		
		
		//表格后面加两行		
		html = html + "\r\n  <tr><td class='td_normal_title'>本次执行说明</td><td colspan=4>"+txtThisTime.getText()+"</td></tr> \r\n " 
		            + "  <tr><td class='td_normal_title'>未执行情况</td><td colspan=4>"+txtCondition.getText()+"</td></tr> \r\n "
		//-------end mod by ypf on 20150621
				
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>合同号</td>	  \r\n" + "	<td  >"
				+ contractBill + "</td> \r\n"
				+ "	<td class='td_normal_title'>合同名称</td>	  \r\n" + "	<td  >"
				+ contractName + "</td> \r\n"
				+ "	<td class='td_normal_title'>主送单位</td>	  \r\n" + "	<td  >"
				+ mainSupp + "</td> \r\n" + "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>是否责任扣款单位</td>	  \r\n"
				+ "	<td  >" + isDeduct + "</td> \r\n"
				+ "	<td class='td_normal_title'>扣款金额</td>	  \r\n" + "	<td  >"
				+ deductAmount + "</td> \r\n"
				+ "	<td class='td_normal_title'>扣款原因</td>	  \r\n" + "	<td  >"
				+ deductReason + "</td> \r\n" + "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>币别</td>	  \r\n" + "	<td  >"
				+ currency + "</td> \r\n"
				+ "	<td class='td_normal_title'>预算原币金额</td>	  \r\n" + "	<td  >"
				+ util.getDecimal(budgetOriAmount) + "</td> \r\n"
				+ "	<td class='td_normal_title'>预算本位币金额</td>	  \r\n"
				+ "	<td  >" + util.getDecimal(budgetAmount) + "</td> \r\n"
				+ "  </tr>             \r\n" + "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>汇率</td>	  \r\n" + "	<td  >"
				+ util.getDecimal(exRate) + "</td> \r\n"
				+ "	<td class='td_normal_title'>是否确定变更金额</td>	  \r\n"
				+ "	<td  >" + isSureChangeAmt + "</td> \r\n"
				+ "	<td class='td_normal_title'>结算本位币金额</td>	  \r\n"
				+ "	<td  >" + util.getDecimal(balanceAmount) + "</td> \r\n"
				+ "  </tr>             \r\n" + "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>审批单编码</td>	  \r\n" + "	<td  >"
				+ auditNumber + "</td> \r\n"
				+ "	<td class='td_normal_title'>结算原币金额</td>	  \r\n" + "	<td  >"
				+ util.getDecimal(oriBalanceAmount) + "</td> \r\n"
				+ "	<td class='td_normal_title'>无效成本金额</td>	  \r\n" + "	<td  >"
				+ util.getDecimal(nouse) + "</td> \r\n"
				+ "  </tr>             \r\n" + "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>原始联系单号</td>	  \r\n" + "	<td  >"
				+ originalContactNum + "</td> \r\n"
				+ "	<td class='td_normal_title'>无效成本原因</td>	  \r\n" + "	<td  >"
				+ validReason + "</td> \r\n"
				+ "	<td class='td_normal_title'>施工方报审金额</td>	  \r\n"
				+ "	<td  >" + util.getDecimal(constructPrice) + "</td> \r\n"
				+ "  </tr>             \r\n" + "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>制单人</td>	  \r\n" + "	<td>"
				+ creator + "</td> \r\n"
				+ "	<td class='td_normal_title'>制单日期</td>	  \r\n"
				+ "	<td colspan=3>" + createDate + "</td> \r\n"
				+ "  </tr>             \r\n" + "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>审核人</td>	  \r\n" + "	<td >"
				+ auditor + "</td> \r\n"
				+ "	<td class='td_normal_title'>审核日期</td>	  \r\n"
				+ "	<td colspan=3>" + auditTime + "</td> \r\n"
				+ "  </tr>             \r\n" + "</table>            \r\n";
		// html=html.replaceAll("<", "&lt;");
		// html=html.replaceAll(">", "&gt;");
		System.out.println("---------html:" + html);
		return html;
	}
	
}
