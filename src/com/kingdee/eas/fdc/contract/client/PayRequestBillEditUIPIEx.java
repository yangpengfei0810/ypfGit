package com.kingdee.eas.fdc.contract.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank;
import com.kingdee.eas.basedata.master.cssp.ISupplierCompanyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgFacade;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.IAdminOrgFacade;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.JsDataTools;
import com.kingdee.eas.fdc.contract.ModelFactory;
import com.kingdee.eas.fdc.contract.ModelInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.wslog.WSLogFactory;
import com.kingdee.eas.fdc.wslog.WSLogInfo;
import com.kingdee.eas.hr.emp.PersonPositionCollection;
import com.kingdee.eas.hr.emp.PersonPositionFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.landray.kmss.km.importfile.webservice.AttachmentForm;
import com.landray.kmss.km.importfile.webservice.KmReviewParamterForm;
import com.landray.kmss.km.importfile.webservice.KmReviewParamterNoattForm;
import com.landray.kmss.km.importfile.webservice.ZHIKmReviewWebserviceService;
import com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpService;
import com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpServiceLocator;

public class PayRequestBillEditUIPIEx extends PayRequestBillEditUI {
	private static KmReviewParamterForm form = null;
	private static KmReviewParamterNoattForm noAttForm = null;
	private static ZHKmReviewWebserviceServiceImpService service = new ZHKmReviewWebserviceServiceImpServiceLocator();
	private static ZHIKmReviewWebserviceService proxy = null;
	private static String oaId="";
	private static String templateId = "";//oa单据模板id
	private static String billState ="";
	private static boolean bl_ok = true;
	private static String fid="";//eas单据id
	private static int num = 100;
	private static String oaBillID ="";
	private static CurProjectInfo PROJECT_INFO = null;//工程项目对象
	private static FDCUtil util = null;
	private static PayRequestBillListUIPIEx listUI=null;
	
	private static String CALLTYPE_SEND ="发送";//写log用的
	
	public PayRequestBillEditUIPIEx() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();

		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("VIEW")||oprtState.equals("EDIT"))
			{
			  listUI = (PayRequestBillListUIPIEx) this.getUIContext().get("Owner");
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
			   listUI = (PayRequestBillListUIPIEx) this.getUIContext().get("Owner");
			   try {
				    listUI.actionRefresh_actionPerformed(e);
			   } catch (Exception e1) {
					e1.printStackTrace();
			   }
			}
		}
	}
	
   public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
	    String id = (!editData.getId().equals("") && editData.getId() !=null)?editData.getId().toString():"";
   	    if(oprtState.equals("VIEW"))
        {
   		   fid = id;
        }
   	
		//检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
	    if(!fid.equals("")){
			boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_PayRequestBill WHERE fid='"+fid+"'");
			if(!isEnableSubmit)
			{
				MsgBox.showInfo("单据已经提交OA走审批流程，不能删除");
				SysUtil.abort();
			}
	    }
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		/*util =new FDCUtil();
		String html11 = getHTML("RVeeD73jQyO+76lbUej14MmlqGk=");*/
		
		String id = (!editData.getId().equals("") && editData.getId() !=null)?editData.getId().toString():"";
    	if(oprtState.equals("VIEW"))
    	{
    		fid = id;
    	}
    	
		//检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
		 if(!fid.equals("")){
			boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_PayRequestBill WHERE fid='"+fid+"'");
			if(!isEnableSubmit)
			{
				MsgBox.showInfo("单据已经提交OA走审批流程，不能编辑");
				SysUtil.abort();
			}
		 }
		 //biaobiao
		 if(!fid.equals("")){
			 PayRequestBillInfo requestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(fid));
			 if(requestBillInfo.isIsOtherPay()){
				MsgBox.showInfo("单据为其它付款单关联生成，不能编辑");
				SysUtil.abort();
			 }
		 }
		 
		 
		 super.actionEdit_actionPerformed(arg0);
		
//		boolean modBillInfo = WriteBackBillStatusFacadeFactory.getRemoteInstance().modBillInfo("EAS2", "13ff6d64ddc93e475f348b849d0bcff3", "4AUDITTED", false, null) ;
	}
	
	public void resetParam()
	{
		form = null;
		noAttForm = null;
		proxy = null;
		oaId="";
		templateId = "";
		billState ="";
		bl_ok = true;
		fid="";
		num = 100;
		oaBillID ="";
		PROJECT_INFO = null;
	}
	
	private String getBillState(String id)
	{
		String state = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		
		try {
			PayRequestBillCollection col = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
			if(col != null && col.size() > 0)
			{
				state = (col.get(0).getState() == null)?"":col.get(0).getState().toString();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return state;
	}
	
	private String getOABillTemplet(String id)
	{
		String billTempletID = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		
		try {
			PayRequestBillCollection col = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
			if(col != null && col.size() > 0)
			{
				billTempletID = (col.get(0).getBillTempletID() == null)?"":col.get(0).getBillTempletID().toString();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return billTempletID;
	}
	
	//获取项目工程对象
	private CurProjectInfo getPrjInfo(String id)
	{
		CurProjectInfo info=null;
		String prjID = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		
		try {
			PayRequestBillCollection col = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
			if(col != null && col.size() > 0)
			{
				prjID = (col.get(0).getCurProject() == null)?"":col.get(0).getCurProject().getId().toString();
				EntityViewInfo view1 = new EntityViewInfo();
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", prjID));
				view1.setFilter(filter1);
				CurProjectCollection prjCol = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view1);
				if(prjCol !=null && prjCol.size()>0)
				{
				   info = prjCol.get(0);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	//获取公司
	private String getCompany(String personId)
	{
		String companyName = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", personId));
		view.setFilter(filter);
		
		try {
			PersonCollection col = PersonFactory.getRemoteInstance().getPersonCollection(view);
			if(col != null && col.size() > 0)
			{
				companyName = (col.get(0).getHrOrgUnit() == null)?"":col.get(0).getHrOrgUnit().getName();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return companyName;
	}
	
	//获取部门对象
	private AdminOrgUnitInfo getPersonDept(String personId)
	{
		AdminOrgUnitInfo info=null;
		String depID ="";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("person", personId));
		view.setFilter(filter);
		
		try {
			PersonPositionCollection col = PersonPositionFactory.getRemoteInstance().getPersonPositionCollection(view);
			if(col != null && col.size() > 0)
			{
				depID = (col.get(0).getPersonDep() == null)?"":col.get(0).getPersonDep().getId().toString();
				EntityViewInfo view1 = new EntityViewInfo();
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", depID));
				view1.setFilter(filter1);
				AdminOrgUnitCollection adminCol = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitCollection(view1);
				if(adminCol !=null && adminCol.size()>0)
				{
				   info = adminCol.get(0);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}
		
	public void actionSave_actionPerformed0000(ActionEvent e) throws Exception {
		//校验editdata数据
		try {
			super.verifyData();
		} catch (Exception e3) {
			e3.printStackTrace();
			SysUtil.abort();
		}
		
		super.actionSave_actionPerformed(e);
	}
	
	private void deleteModel(String billFid)
	{
		try {
			ModelFactory.getRemoteInstance().delete("where id is not null and description='"+billFid.trim()+"'");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception{
//		String formHTML1 = getHTML("kf3W2hXGR8C2f0NH9GjO88mlqGk=");
		
		//重置静态变量
		resetParam();
		
		//add by ypf on 20130715 
		String locAmonut = this.kdtEntrys.getCell(5, 6).getValue()+"";
		System.out.println("-----------付款申请单的原币金额locAmonut:"+locAmonut);		
		if(locAmonut == null || locAmonut.equals("") || locAmonut.equals("null"))
		{
			MsgBox.showWarning("工程付款情况表中的本次申请原币不能为空");
			SysUtil.abort();
		}
		
		//校验editdata数据
		try {
			super.verifyData();
		} catch (Exception e3) {
			e3.printStackTrace();
			SysUtil.abort();
		}
		if(oprtState.equals("ADDNEW"))
		{
			 actionSave_actionPerformed(e);
			
			//初始化fid
			 fid = editData.getId().toString();
			
			//保存以后才能娶到单据状态
			 billState = getBillState(fid);
		}
		else if(oprtState.equals("EDIT"))
		{
			 fid = (editData.getId()==null)?"":editData.getId().toString();
			 billState = getBillState(fid);
			 if(billState.equals("驳回"))
			 {
				 templateId = getOABillTemplet(fid);
			 }
		}else
		{
			SysUtil.abort();
		}
		
		boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_PayRequestBill WHERE fid='"+fid+"'");
		if(!isEnableSubmit)
		{
			MsgBox.showInfo("单据已经提交OA走审批流程，不能重复提交");
			SysUtil.abort();
		}
		
	    num = isOAAudit();
	    
		//1、选择是否走OA审批
		if(num == 0)
		{
			PROJECT_INFO=getPrjInfo(fid);//初始化项目工程对象
			
			//判断是否存在附件
			String sql = "SELECT t1.fid billID,t3.fid attachmentID FROM T_CON_PayRequestBill t1 left join T_BAS_BoAttchAsso t2 on t1.fid=t2.fboid left join  T_BAS_Attachment t3 on t2.fattachmentid =t3.fid WHERE t1.fid='"+fid+"'";
			util = new FDCUtil();
			if(!util.checkExistAttachment(sql))
			{
				actionSubmit_no_attachment(e);//执行无附件的提交方法
				SysUtil.abort();
			}
			
			bl_ok = true;
			String jsonForm = getJsonForm((String) fid);
			String formHTML = getHTML(fid);
		    System.out.println("------actionSubmit_actionPerformed-----fid:"+fid);
	         
		    //如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
		    String prjMappingName = PROJECT_INFO.getPrjMappingName();
		    
		    //合同名称+付款类型（eas付款类型字段）----------这里需要去掉“付款类型”的编码
		    String payType_ ="";
		    if(prmtPayment.getText()!=null && !prmtPayment.getText().equals(""))
		    {
		    	if(prmtPayment.getText().contains(" "))
		    	{
		    		String [] str_type =prmtPayment.getText().split(" ", 1);
		    		if(str_type.length>1)
		    		{
		    			payType_ = str_type[1].trim();
		    			for (int i = 0; i < str_type.length; i++) {
		    				System.out.println("-----------付款类型第"+i+"个："+str_type[i]);
						}
		    		}
		    	}
		    }
		    
		    PaymentTypeInfo paymentTypeInfo = (PaymentTypeInfo) prmtPayment.getData();
		    
		    System.out.println("-----------付款申请单-付款类型："+paymentTypeInfo.getName());
		    BOSUuid id = editData.getId();
//		    PayRequestBillInfo requestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id));
//		    String contractName2 = requestBillInfo.getContractName();
		    String subject = "";//mod by biaobbiao on 20140213
//		    String subject = this.getUIContext().get("contractName") + paymentTypeInfo.getName();//mod by ypf on 20121106
//		    if(contractName2== null || contractName2.equals(""))
//		    {
		    	//mod by ypf on 20121129
				String contractName = kdtEntrys.getCell(0, 1).getValue()!=null?kdtEntrys.getCell(0, 1).getValue().toString():"";
				subject = contractName + paymentTypeInfo.getName();
//		    }
		    System.out.println("--------subject主题："+subject);
		    
			if(prjMappingName ==null || prjMappingName.equals(""))
			{
				if(MsgBox.showConfirm3("该工程项目没有配置映射名称有可能取不到模板，是否还要继续？")==MsgBox.OK)
				{
					
				}else{
				   SysUtil.abort();
				}
			}
			
			//2、选择模板
		    //获取蓝凌OA提供模板并保存到eas中
			List modelIDList = null;
			String keyForModel = "EAS2("+prjMappingName+")";
			if(billState != null && billState != "" && !billState.equals("驳回"))
			{
				getOAModelAndSaveToEAS(keyForModel,fid);
			    modelIDList = showFilterDialog(fid);
			}
			
			//4、准备要发起流程的数据，并传递给oa审批
			AlterAttachment at = new AlterAttachment();
			String creator = SysContext.getSysContext().getCurrentUserInfo().getNumber();
			
			if(billState != null && billState != "" && !billState.equals("驳回"))
			{
				templateId = (modelIDList.size()>0) ? modelIDList.get(0).toString() : "";
			}
			oaBillID = getOAID(fid);
			System.out.println("-------驳回重新提交时根据单据id查找到的oa单据id:"+oaBillID);
			
			//mod by ypf on 20140827 发起账号要是json
			String creatorJsonString = "";
			try {
				JSONObject creatorJson = new JSONObject();
				creatorJsonString = creatorJson.put("LoginName", creator)+"";
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			AttachmentForm [] forms = at.getAttacmentInfo(fid);
			form = new KmReviewParamterForm();
			form.setDocCreator(creatorJsonString);
			form.setDocSubject(subject);
			form.setFdTemplateId(templateId);
			form.setFormValues(jsonForm);
			form.setEasHtml(formHTML);
			form.setAttachmentForms(forms);
			form.setDocId(oaBillID);//oa单据id   oaBillID
			System.out.println("---form----creator:"+creator+"  "+(oprtState.equals("ADDNEW")?"新增状态":"编辑状态")+"oaBillID:"+oaBillID+"  subject:"+subject+"   templateId:"+templateId+"   jsonForm:"+jsonForm+"  formHTML"+formHTML);
			
			try {
				proxy = service.getZHKmReviewWebserviceServiceImpPort();
				bl_ok = true ;
			} catch (ServiceException e2) {
				e2.printStackTrace();
				
				if(MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存提交？")==MsgBox.OK)
				{
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
						logInfo.setSourceBillType("EAS2");
						WSLogFactory.getRemoteInstance().addnew(logInfo);

						SysUtil.abort();
					}
				}
				SysUtil.abort();
			}
			
			//5、检查是否有待办记录
			try {
				proxy.delTasksByID(fid);
			} catch (Exception e1) {
				e1.printStackTrace();
				if(MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存？")==MsgBox.OK)
				{
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
						logInfo.setSourceBillType("EAS2");
						WSLogFactory.getRemoteInstance().addnew(logInfo);
						
						SysUtil.abort();
					}
				}
				SysUtil.abort();
			}
			
			//6、调用oa的流程发起接口传值
			try {
				LongTimeDialog dialog = UITools.getDialog(this);
				dialog.setTitle("提示");//提示框标题
				if(dialog==null)
					return;
		        dialog.setLongTimeTask(new ILongTimeTask() {
		            public Object exec() throws Exception {
		            	//加入要等待的代码块
		            	try {
		            		oaId = proxy.addReview(form);
		            		if(!oprtState.equals("ADDNEW") && !oaBillID.equals(""))
		            		{
		            		   oaId = oaBillID;
		            		}
						} catch (Exception e2) {
							bl_ok = false;
							MsgBox.showDetailAndOK(null, "调用OA接口传送单据数据失败. \r\n点击详细信息查看详情.", e2.toString(), 1);
							System.out.println("-----------e2:"+e2);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("失败");
							logInfo.setLogTitle("调用OA接口传送单据数据时失败");
							logInfo.setLogDetail(e2.toString());
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS2");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
		            		SysUtil.abort();
						}
						
		            	System.out.println("-----向oa传送数据后的----oaId:"+oaId);
		                return oaId;
		            }
		            public void afterExec(Object result) throws Exception {
		                
		            }
		        });
		        Component[] cps=dialog.getContentPane().getComponents();  
		        for(Component cp:cps){  
		            if(cp instanceof JLabel){  
		            	//提示内容
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
				logInfo.setLogTitle("调用OA接口传送单据数据失败");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS2");
				try {
					WSLogFactory.getRemoteInstance().addnew(logInfo);
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				
				SysUtil.abort();
			}
			
			//7、更新eas合同单据的‘是否走OA审批’字段
			if(bl_ok)
			{
				if(!updatePayRequestBill(fid,true,templateId,oaId)){
					MsgBox.showInfo("回写EAS单据失败");
					SysUtil.abort();
				}else
				{
					MsgBox.showInfo("发起OA审批流程成功");
					try {
						String url = OASSOUtil.getSSOURL(creator, oaId);
						System.out.println("-----sso url:"+url);
						if(!url.equals(""))
						{
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
							logInfo.setSourceBillType("EAS2");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							//mod by ypf on 20121111 刷新listui
							rebackRefreshListUI(e);
							
							//先保存再关闭，否则会弹出“数据已修改，是否需要保存”提示
							uiWindow.close();
							BareBonesBrowserLaunch.openURL(url);
						}else
						{
							MsgBox.showInfo("没有获取到单点登录URL,无法打开浏览器进行跳转");
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
						logInfo.setSourceBillType("EAS2");
						WSLogFactory.getRemoteInstance().addnew(logInfo);
					}
				}
			}else{
				MsgBox.showWarning("发起OA审批流程失败");
				SysUtil.abort();
			}
			if(bl_ok)
			{
				System.out.println("-----复制这条链接登录oa查看（用户名："+creator+"  密码：1）------http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
			}
		}else if(num == 1)
		{
			//直接保存
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
			PayRequestBillCollection col;
			try {
				col = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
				PayRequestBillInfo info = new PayRequestBillInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//更新附件内容
					   PayRequestBillFactory.getRemoteInstance().updatePartial(info, selector);
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
			
		}else
		{
			SysUtil.abort();
		}
		
		deleteModel(fid);
	}
	
	public void actionSubmit_no_attachment(ActionEvent e) throws Exception {
		bl_ok = true;
		String jsonForm = getJsonForm((String) fid);
		String formHTML = getHTML(fid);
	    System.out.println("------actionSubmit_actionPerformed-----fid:"+fid);
         
	    //如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
 	    String prjMappingName = PROJECT_INFO.getPrjMappingName();
		
 	    //合同名称+付款类型（eas付款类型字段）----------这里需要去掉“付款类型”的编码
	    String payType_ ="";
	    if(prmtPayment.getText()!=null && !prmtPayment.getText().equals(""))
	    {
	    	if(prmtPayment.getText().contains(" "))
	    	{
	    		String [] str_type =prmtPayment.getText().split(" ", 0);
	    		if(str_type.length>1)
	    		{
	    			payType_ = str_type[1].trim();
	    			for (int i = 0; i < str_type.length; i++) {
	    				System.out.println("-----------付款类型第"+i+"个："+str_type[i]);
					}
	    		}
	    	}
	    }
	    
	    //mod by ypf on 20121106
	    PaymentTypeInfo paymentTypeInfo = (PaymentTypeInfo) prmtPayment.getData();	    
	    System.out.println("-----------付款申请单-付款类型："+paymentTypeInfo.getName());
	    BOSUuid id = editData.getId();
//	    PayRequestBillInfo requestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id));
//	    String contractName2 = requestBillInfo.getContractName();
	    String subject = "";//mod by ypf on 20121106
////	    String subject = this.getUIContext().get("contractName") + paymentTypeInfo.getName();//mod by ypf on 20121106
//	    if(contractName2 == null || contractName2.equals(""))
//	    {
	    	//mod by ypf on 20121129
			String contractName = kdtEntrys.getCell(0, 1).getValue()!=null?kdtEntrys.getCell(0, 1).getValue().toString():"";
			subject = contractName + paymentTypeInfo.getName();
//	    }
	    System.out.println("--------subject主题："+subject);
	    
		//1、选择是否走OA审批
		if(num == 0)
		{
			if(prjMappingName ==null || prjMappingName.equals(""))
			{
				if(MsgBox.showConfirm3("该工程项目没有配置映射名称有可能取不到模板，是否还要继续？")==MsgBox.OK)
				{
					
				}else{
				   SysUtil.abort();
				}
			}
			
			//2、选择模板
		    //获取蓝凌OA提供模板并保存到eas中
			List modelIDList = null;
			String keyForModel = "EAS2("+prjMappingName+")";
			if(oprtState.equals("ADDNEW") || (billState != null && billState != "" && !billState.equals("驳回")))
			{
				getOAModelAndSaveToEAS(keyForModel,fid);
			    modelIDList = showFilterDialog(fid);
			}
			
			//4、准备要发起流程的数据，并传递给oa审批
			AlterAttachment at = new AlterAttachment();
			String creator = SysContext.getSysContext().getCurrentUserInfo().getNumber();
			
			if(oprtState.equals("ADDNEW") || (billState != null && billState != "" && !billState.equals("驳回")))
			{
				templateId = (modelIDList.size()>0) ? modelIDList.get(0).toString() : "";
			}
			oaBillID = getOAID(fid);
			System.out.println("-------驳回重新提交时根据单据id查找到的oa单据id:"+oaBillID);//可以删除
			
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
			noAttForm.setDocId(oaBillID);//oa单据id   oaBillID
			System.out.println("---form----creator:"+creator+"  oaBillID:"+oaBillID+"  subject:"+subject+"   templateId:"+templateId+"   jsonForm:"+jsonForm+"  formHTML"+formHTML);
			
			try {
				proxy = service.getZHKmReviewWebserviceServiceImpPort();
				bl_ok = true ;
			} catch (ServiceException e2) {
				e2.printStackTrace();
				
				if(MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存提交？")==MsgBox.OK)
				{
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
						logInfo.setSourceBillType("EAS2");
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
			
			//5、检查是否有待办记录
			try {
				proxy.delTasksByID(fid);
			} catch (Exception e1) {
				e1.printStackTrace();
				if(MsgBox.showConfirm3("远程调用OA接口失败，当前单据是否需要保存？")==MsgBox.OK)
				{
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
						logInfo.setSourceBillType("EAS2");
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
			
			//6、调用oa的流程发起接口传值
			try {
				LongTimeDialog dialog = UITools.getDialog(this);
				dialog.setTitle("提示");//提示框标题
				if(dialog==null)
					return;
		        dialog.setLongTimeTask(new ILongTimeTask() {
		            public Object exec() throws Exception {
		            	//加入要等待的代码块
		            	try {
		            		oaId = proxy.addReviewNoatt(noAttForm);
		            		if(!oprtState.equals("ADDNEW") && !oaBillID.equals(""))
		            		{
		            		   oaId = oaBillID;
		            		}
						} catch (Exception e2) {
							bl_ok = false;
							System.out.println("-----------e2:"+e2);
							MsgBox.showDetailAndOK(null, "调用OA接口传送单据数据失败. \r\n点击详细信息查看详情.", e2.toString(), 1);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("失败");
							logInfo.setLogTitle("调用OA接口传送单据数据时失败");
							logInfo.setLogDetail(e2.toString());
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS2");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
		            		SysUtil.abort();
						}
		            	System.out.println("-----向oa传送数据后的----oaId:"+oaId);
		                return oaId;
		            }
		            public void afterExec(Object result) throws Exception {
		                
		            }
		        });
		        Component[] cps=dialog.getContentPane().getComponents();  
		        for(Component cp:cps){  
		            if(cp instanceof JLabel){  
		            	//提示内容
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
				logInfo.setSourceBillType("EAS2");
				try {
					WSLogFactory.getRemoteInstance().addnew(logInfo);
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				
				SysUtil.abort();
			}
			
			//7、更新eas合同单据的‘是否走OA审批’字段
			if(bl_ok)
			{
				if(!updatePayRequestBill(fid,true,templateId,oaId)){
					MsgBox.showInfo("回写EAS单据失败");
					SysUtil.abort();
				}else
				{
					MsgBox.showInfo("发起OA审批流程成功");
					try {
						String url = OASSOUtil.getSSOURL(creator, oaId);
						System.out.println("-----sso url:"+url);
						if(!url.equals(""))
						{
							super.actionSubmit_actionPerformed(e);
							//先保存再关闭，否则会弹出“数据已修改，是否需要保存”提示
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("成功");
							logInfo.setLogTitle("流程发起成功");
							logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
							logInfo.setUrl(url);
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS2");
							WSLogFactory.getRemoteInstance().addnew(logInfo);

							//mod by ypf on 20121111 刷新listui
							rebackRefreshListUI(e);
							
							uiWindow.close();
							BareBonesBrowserLaunch.openURL(url);
						}else
						{
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
						logInfo.setSourceBillType("EAS2");
						try {
							WSLogFactory.getRemoteInstance().addnew(logInfo);
						} catch (BOSException e2) {
							e2.printStackTrace();
						}
					}
				}
			}else{
				MsgBox.showWarning("发起OA审批流程失败");
				SysUtil.abort();
			}
			if(bl_ok)
			{
				System.out.println("-----复制这条链接登录oa查看（用户名："+creator+"  密码：1）------http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
			}
		}else if(num == 1)
		{
			//直接保存
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
				logInfo.setSourceBillType("EAS2");
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
			PayRequestBillCollection col;
			try {
				col = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
				PayRequestBillInfo info = new PayRequestBillInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//更新附件内容
					   PayRequestBillFactory.getRemoteInstance().updatePartial(info, selector);
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
			
		}else
		{
			SysUtil.abort();
		}
		
		deleteModel(fid);
	}
	
	//销毁ui，不检查
	public boolean destroyWindow() {
		System.out.println("---------------销毁ui---不检查");
		return true;
	}
	
	private String getOAID(String billID)
	{
		System.out.println("---getOAID---billID:"+billID);
		String oaID="";
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", billID));
		view.setFilter(filter);
		
		PayRequestBillInfo info = null;
		try {
			PayRequestBillCollection col = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
			if(col != null && col.size()>0)
			{
				info = col.get(0);
				oaID = (info.getOABillID()==null || info.getOABillID()=="")?"":info.getOABillID();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		System.out.println("-----getOAID----oaID:"+oaID);
		
		return oaID;
	}
	
	
	/**
	 * **************************************************
	 * 方法说明: getJsonForm(获取单据的json)  
	 *  
	 * 参数：@param fid
	 * 参数：@return                                                 
	 * 返回值：String
	 * 
	 * 修改人：yangpengfei
	 * 修改时间：2012-8-25 下午03:06:18
	 * ***************************************************
	 */
	private String getJsonForm(String fid)
	{
		String json = "";
		String contractNumber = (txtcontractNo.getText()==null)?"":txtcontractNo.getText();// 合同编号
		//mod by ypf on 20121121
		String contractName = kdtEntrys.getCell(0, 1).getValue()!=null?kdtEntrys.getCell(0, 1).getValue().toString():"";
		//mod by ypf on 20121121
		SupplierInfo revSupplier = (SupplierInfo) prmtrealSupplier.getData();//实际收款单位
		String revUnit = revSupplier!=null?revSupplier.getName():"";
		
		//mod by ypf on 20121121
		SettlementTypeInfo settlementTypeInfo = (SettlementTypeInfo) prmtsettlementType.getData();
		String settlementName = settlementTypeInfo!=null?settlementTypeInfo.getName():"";
		
		String recBank=(txtrecBank.getText()==null)?"":txtrecBank.getText();//收款银行
		
		//收款单位全称  mod by ypf on 20121128  删除
//		String supplier=(prmtsupplier.getText()==null)?"":prmtsupplier.getText();//收款银行全称
		
		//mod by ypf on 20121121
		String localAmount = kdtEntrys.getCell(1, 10).getValue()!=null?kdtEntrys.getCell(1, 10).getValue().toString():"";//最新造价本币
		String reqMoney=(kdtEntrys.getCell(5, 6).getValue()==null)?"":kdtEntrys.getCell(5, 6).getValue()+"";//本次申请原币
		String costMoney=(kdtEntrys.getCell(2, 4).getValue()==null)?"":kdtEntrys.getCell(2, 4).getValue()+"";//结算金额
		String moneyDesc=(txtMoneyDesc.getItemAt(0)==null)?"":txtMoneyDesc.getItemAt(0).toString(); //备注
		
		String longNumber= PROJECT_INFO.getLongNumber();//长编码
		String longName= PROJECT_INFO.getDisplayName();//长名称
		String isContractAudit= "1";//是否合同审批
		
		//add by ypf on 20121121
		CurrencyInfo currencyInfo = (CurrencyInfo) prmtcurrency.getData();
		String currency = currencyInfo!=null?currencyInfo.getName():"";//币别
		//add by ypf on 20121121
		AdminOrgUnitInfo adminOrgUnitInfo = (AdminOrgUnitInfo) prmtuseDepartment.getData();
		String useDepartment = adminOrgUnitInfo!=null?adminOrgUnitInfo.getName():"";//用款部门
		
		
		IAdminOrgFacade  adminOrgFacade=new AdminOrgFacade();
		ContractBillInfo contractbill=null;
		try {
			String contractId=editData.getContractId();
			System.out.println("------contractId:"+contractId);
			contractbill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractId));
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("----责任人："+contractbill.getRespPerson().getId().toString());
		
		//20130328 mod by ypf 注释掉合同负责人公司和合同负责人部门
		//AdminOrgUnitInfo comInfo = null;
		//comInfo = getPersonDept(contractbill.getRespPerson().getId().toString());		
		//String company = getCompany(contractbill.getRespPerson().getId().toString());   //合同负责人公司
		//String department = (comInfo != null && comInfo.getName() != null)?comInfo.getName().toString():""; //合同负责人部门
		
		String billNum = (txtPaymentRequestBillNumber.getText()==null)?"":txtPaymentRequestBillNumber.getText();  //付款申请单编号
		String recAccount = (txtrecAccount.getText()==null)?"":txtrecAccount.getText();//收款帐号
		
		Map map = new HashMap();
        map.put("contract_number", contractNumber);
        map.put("contract_name", contractName);
        map.put("coperation_unit_name", revUnit);
        map.put("pay_type", settlementName);
        map.put("bank_name", recBank);
        //map.put("contactor", supplier);//收款单位全称  mod by ypf on 20121128  删除
        map.put("contract_total_amount", util.cutComma(localAmount));
        map.put("this_req_money", util.cutComma(reqMoney));
        map.put("cost_money", util.cutComma(costMoney));
        map.put("angent_opinion", moneyDesc);
        map.put("is_contract_audit", isContractAudit);
        map.put("prj_long_number", longNumber);
        map.put("currency", currency);//add by ypf on 20121121
        map.put("use_deparment", useDepartment);//add by ypf on 20121121
        map.put("prj_long_name", longName);
        map.put("payRequest_number", billNum);
        map.put("receive_account", recAccount);
        
        
        
        
		JsDataTools js = new JsDataTools();
	    json = js.createJsData(map);
		System.out.println("----合同单据'"+contractName+"'-----json:"+json);
		
		return json;
	}

	//是否走OA审批
	private int isOAAudit()
	{
		return MsgBox.showConfirm3("请选择是否走OA审批流程？");
	}
	
	/**
	 * **************************************************
	 * 方法说明: updatePayRequestBill(在走提交后，更新‘是否走oa审批’字段)  
	 *  
	 * 参数：@param id
	 * 参数：@param isOAAudit
	 * 参数：@return                                                 
	 * 返回值：boolean
	 * 
	 * 修改人：yangpengfei
	 * 修改时间：2012-8-21 下午08:43:04
	 * ***************************************************
	 */
	public boolean updatePayRequestBill(String id,boolean isOAAudit,String billTempletID,String oaBillID)
	{
		System.out.println("------发起流程成功后更新合同信息："+"  eas单据id:"+id +"  是否走oa审批："+isOAAudit+"  模板id："+billTempletID +"  oa单据id："+oaBillID);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		PayRequestBillCollection col;
		try {
			col = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
			PayRequestBillInfo info = new PayRequestBillInfo();
			//取到需要更新附件的对象
			if(col != null && col.size() > 0)
			{
			  info = col.get(0);
			  info.setIsOAAudit(isOAAudit);
			  
			  String oabillid = (info.getOABillID()==null || info.getOABillID()=="")?"":info.getOABillID();
			  //!oabillid.equals("") 第一次进来，OABillID字段为空，则更新为oaBillID；第二次，显然OABillID字段不为空
			  if(oaBillID!=null && oaBillID !="" && oabillid.equals(""))
			  {
			    info.setOABillID(oaBillID);
			  }
			  //如果要走oa审批流程，那么就把oa单据的模板id也保存
			  if(isOAAudit){
			      info.setBillTempletID(billTempletID);
			  }
			  SelectorItemCollection selector = new SelectorItemCollection();
			  selector.add("isOAAudit");
			  selector.add("billTempletID");
			  if(oaBillID!=null && oaBillID !="" && oabillid.equals(""))
			  {
				  selector.add("OABillID");
			  }
			  try {
					//更新附件内容
				   PayRequestBillFactory.getRemoteInstance().updatePartial(info, selector);
				   return true;
			   } catch (Exception e) {
				   e.printStackTrace();
				   return false;
			  } 
			}else
			{
				return false;
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	/**
	 * **************************************************
	 * 方法说明: getOAModelAndSaveToEAS(获取蓝凌OA提供模板并保存到eas中)  
	 *  
	 * 参数：@throws Exception                                                 
	 * 返回值：void
	 * 
	 * 修改人：yangpengfei
	 * 修改时间：2012-8-21 下午04:34:11
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
	 * **************************************************
	 * 方法说明: showFilterDialog(模板选择对话框)  
	 *  
	 * 参数：@return
	 * 参数：@throws Exception                                                 
	 * 返回值：String
	 * 
	 * 修改人：yangpengfei
	 * 修改时间：2012-8-23 下午04:21:44
	 * ***************************************************
	 */
	protected List showFilterDialog(String keyForModel) {
		List fids = new ArrayList();
		KDBizPromptBox modelBox = new KDBizPromptBox();
		modelBox.setVisible(false);
		modelBox.setEnabledMultiSelection(true);
		modelBox.setQueryInfo("com.kingdee.eas.fdc.contract.app.ModelQuery");
		
		//设置过滤
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("description", keyForModel));
		view.setFilter(filter);
		modelBox.setEntityViewInfo(view);
		
		modelBox.setDataBySelector();
		  
		if (modelBox.getSelector().isCanceled() || modelBox.getData() == null){
			SysUtil.abort();
		}else{
			Object infos[] = (Object[]) modelBox.getData();
			for (int i = 0; i < infos.length; i++) {
				fids.add(((ModelInfo)infos[i]).getSimpleName().toString());
				System.out.println("----选中的模板编码："+((ModelInfo)infos[i]).getSimpleName().toString());
			}
		}
        return fids;
	}
	
	public String getHTML(String fid) {
//		util = new FDCUtil();
		String html = "";
		String org = "";// 组织
		String proj="";//工程项目
		String contractNo="";//合同号
		String useDepartment="";//用款部门
		String bookedDate="";//业务日期
		String paymentRequestBillNumber="";//付款申请单编号
		String payDate="";//付款日期
		String period="";//申请日期
		String payment="";//付款类型
		String difPlac="";//同城异地
		String settlementType="";//结算方式
		String supplier="";//收款单位全称
		String recBank="";//收款银行
		String desc="";//摘要
		String realSupplier="";//实际收款单位
		String recAccount="";//收款账号
		String usage="";//用途
		String currency="";//币别
		String exchangeRate="";//汇率
		String paymentProportion="";//进度款付款比例
		String amount="";//原币金额
		String completePrjAmt="";//本期完工工程量金额
		String bcAmount="";//本币金额
		String grtAmount="";//保修金金额
		String totalSettlePrice="";//已实现产值
		String capitalAmount="";//大写金额
		String mergencyStat="";//是否加急
		String invoiceNumber="";//发票号
		String invoiceAmt="";//发票金额
		String invoiceDate="";//开票日期
		String allCompletePrjAmt="";//累计已完成工程量
		String allPaymentProportion="";//累计应付款比例
		String allInvoiceAmt="";//累计发票金额
		String attachment="";//附件列表
		String attachmentNum="";//附件数
		String process="";//形象进度描述
		String moneyDesc="";//备注
		String promptCreator="";//制单人
		String dateCreateTim="";//制单日期
		String promptAuditor="";//审核人
		String auditDate="";//审核日期
		
		org=(txtOrg.getText()==null)?"":txtOrg.getText();
		proj=(txtProj.getText()==null)?"":txtProj.getText();
		contractNo=(txtcontractNo.getText()==null)?"":txtcontractNo.getText();
		useDepartment=(prmtuseDepartment.getText()==null)?"":prmtuseDepartment.getText();
		bookedDate=(pkbookedDate.getText()==null)?"":pkbookedDate.getText();
		paymentRequestBillNumber=(txtPaymentRequestBillNumber.getText()==null)?"":txtPaymentRequestBillNumber.getText();
		payDate=(pkpayDate.getText()==null)?"":pkpayDate.getText();
		period=(cbPeriod.getText()==null)?"":cbPeriod.getText();
		payment=(prmtPayment.getText()==null)?"":prmtPayment.getText();
		difPlac=(difPlace.getSelectedItem()==null)?"":difPlace.getSelectedItem().toString();
		settlementType=(prmtsettlementType.getText()==null)?"":prmtsettlementType.getText();
		supplier=(prmtsupplier.getText()==null)?"":prmtsupplier.getText();
		recBank=(txtrecBank.getText()==null)?"":txtrecBank.getText();
		desc=(prmtDesc.getText()==null)?"":prmtDesc.getText();
		realSupplier=(prmtrealSupplier.getText()==null)?"":prmtrealSupplier.getText();
		recAccount=(txtrecAccount.getText()==null)?"":txtrecAccount.getText();
		usage=(txtUsage.getText()==null)?"":txtUsage.getText();
		currency=(prmtcurrency.getText()==null)?"":prmtcurrency.getText();
		exchangeRate=(txtexchangeRate.getText()==null)?"":txtexchangeRate.getText();
		paymentProportion=(txtpaymentProportion.getText()==null)?"":txtpaymentProportion.getText();
		amount=(txtAmount.getText()==null)?"":txtAmount.getText();
		completePrjAmt=(txtcompletePrjAmt.getText()==null)?"":txtcompletePrjAmt.getText();
		bcAmount=(txtBcAmount.getText()==null)?"":txtBcAmount.getText();
		grtAmount=(txtGrtAmount.getText()==null)?"":txtGrtAmount.getText();
		totalSettlePrice=(txtTotalSettlePrice.getText()==null)?"":txtTotalSettlePrice.getText();
		capitalAmount=(txtcapitalAmount.getText()==null)?"":txtcapitalAmount.getText();
		mergencyStat=(mergencyState.getSelectedItem()==null)?"":mergencyState.getSelectedItem().toString();
		invoiceNumber=(txtInvoiceNumber.getText()==null)?"":txtInvoiceNumber.getText();
		invoiceAmt=(txtInvoiceAmt.getText()==null)?"":txtInvoiceAmt.getText();
		invoiceDate=(pkInvoiceDate.getText()==null)?"":pkInvoiceDate.getText();
		allCompletePrjAmt=(txtAllCompletePrjAmt.getText()==null)?"":txtAllCompletePrjAmt.getText();
		allPaymentProportion=(txtAllPaymentProportion.getText()==null)?"":txtAllPaymentProportion.getText();
		allInvoiceAmt=(txtAllInvoiceAmt.getText()==null)?"":txtAllInvoiceAmt.getText();
		if(cmbAttachment.getItemCount()>0){
		String atts="";
		  for(int i=0;i<cmbAttachment.getItemCount();i++){
			  if(cmbAttachment.getItemAt(i)!=null){
				  atts=atts+cmbAttachment.getItemAt(i)+",";
			  }
		  }
	    int num= atts.lastIndexOf(",");
	    attachment=atts.substring(0, num);
		}
		attachmentNum=(txtattachment.getText()==null)?"":txtattachment.getText();
		process=(txtProcess.getText()==null)?"":txtProcess.getText();
		moneyDesc=txtMoneyDesc.getItemAt(0).toString();
		promptCreator=(bizPromptCreator.getText()==null)?"":bizPromptCreator.getText();
		dateCreateTim=(dateCreateTime.getText()==null)?"":dateCreateTime.getText();
		promptAuditor=(bizPromptAuditor.getText()==null)?"":bizPromptAuditor.getText();
		auditDate=(pkauditDate.getText()==null)?"":pkauditDate.getText();
		
//		for(int i=0;i<kdtEntrys.getRowCount();i++){
//			for(int j=0;j<kdtEntrys.getColumnCount();j++){
//				if(kdtEntrys.getCell(i, j).getValue()==null){
//					kdtEntrys.getCell(i, j).setValue("");
//				}
//			}
//		}
		html=
			 "\r\n<table class='tb_normal' width=100%>		  \r\n"
			+"<tr><td colspan=6  class='td_normal_title_head' width=15%>付款申请单表头</td></tr> \r\n"
			+"  <tr>              \r\n"
			+"	<td width=16% class='td_normal_title'>组织</td>	  \r\n"
			+"	<td  width=16%>"+org+"</td> \r\n"
			+"	<td width=16% class='td_normal_title'>工程项目</td>	  \r\n"
			+"	<td  width=16%>"+proj+"</td> \r\n"
			+"	<td width=16% class='td_normal_title'>合同号</td>	  \r\n"
			+"	<td width=16% >"+contractNo+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>用款部门</td>	  \r\n"
			+"	<td >"+useDepartment+"</td> \r\n"
			+"	<td class='td_normal_title'>业务日期</td>	  \r\n"
			+"	<td >"+bookedDate+"</td> \r\n"
			+"	<td class='td_normal_title'>付款申请单编号</td>	  \r\n"
			+"	<td >"+paymentRequestBillNumber+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>付款日期</td>	  \r\n"
			+"	<td >"+payDate+"</td> \r\n"
			+"	<td class='td_normal_title'>申请期间</td>	  \r\n"
			+"	<td >"+period+"</td> \r\n"
			+"	<td class='td_normal_title'>付款类型</td>	  \r\n"
			+"	<td >"+payment+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>同城异地</td>	  \r\n"
			+"	<td >"+difPlac+"</td> \r\n"
			+"	<td class='td_normal_title'>结算方式</td>	  \r\n"
			+"	<td >"+settlementType+"</td> \r\n"
			+"	<td class='td_normal_title'>收款单位全称</td>	  \r\n"
			+"	<td >"+supplier+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>收款银行</td>	  \r\n"
			+"	<td >"+recBank+"</td> \r\n"
			+"	<td class='td_normal_title'>摘要</td>	  \r\n"
			+"	<td >"+desc+"</td> \r\n"
			+"	<td class='td_normal_title'>实际收款单位</td>	  \r\n"
			+"	<td >"+realSupplier+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>收款账号</td>	  \r\n"
			+"	<td >"+recAccount+"</td> \r\n"
			+"	<td class='td_normal_title'>用途</td>	  \r\n"
			+"	<td >"+usage+"</td> \r\n"
			+"	<td class='td_normal_title'>币别</td>	  \r\n"
			+"	<td >"+currency+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>汇率</td>	  \r\n";
			html=html+"	<td >"+util.getDecimal(exchangeRate+"")+"</td> \r\n"
//			html=html+"	<td >1</td> \r\n"
			+"	<td class='td_normal_title'>进度款付款比例</td>	  \r\n"
			+"	<td >"+util.getDecimal(paymentProportion)+"</td> \r\n"
			+"	<td class='td_normal_title'>原币金额</td>	  \r\n"
			+"	<td >"+util.getDecimal(amount)+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>本期完工工程量金额</td>	  \r\n"
			+"	<td >"+util.getDecimal(amount)+"</td> \r\n"
			+"	<td class='td_normal_title'>本币金额</td>	  \r\n"
			+"	<td >"+util.getDecimal(bcAmount)+"</td> \r\n"
			+"	<td class='td_normal_title'>保修金金额</td>	  \r\n"
			+"	<td >"+util.getDecimal(grtAmount)+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>已实现产值</td>	  \r\n"
			+"	<td >"+util.getDecimal(totalSettlePrice)+"</td> \r\n"
			+"	<td class='td_normal_title'>大写金额</td>	  \r\n"
			+"	<td >"+capitalAmount+"</td> \r\n"
			+"	<td class='td_normal_title'>是否加急</td>	  \r\n"
			+"	<td >"+mergencyStat+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>发票号</td>	  \r\n"
			+"	<td >"+invoiceNumber+"</td> \r\n"
			+"	<td class='td_normal_title'>发票金额</td>	  \r\n"
			+"	<td >"+util.getDecimal(invoiceAmt)+"</td> \r\n"
			+"	<td class='td_normal_title'>开票日期</td>	  \r\n"
			+"	<td >"+invoiceDate+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>累计已完成工程量</td>	  \r\n"
			+"	<td >"+util.getDecimal(allCompletePrjAmt)+"</td> \r\n"
			+"	<td class='td_normal_title'>累计应付款比例</td>	  \r\n"
			+"	<td >"+util.getDecimal(allPaymentProportion)+"</td> \r\n"
			+"	<td class='td_normal_title'>累计发票金额</td>	  \r\n"
			+"	<td >"+util.getDecimal(allInvoiceAmt) +"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>附件列表</td>	  \r\n"
			+"	<td >"+attachment+"</td> \r\n"
			+"	<td class='td_normal_title'>附件数</td>	  \r\n"
			+"	<td >"+attachmentNum+"</td> \r\n"
			+"	<td class='td_normal_title'>形象进度描述</td>	  \r\n"
			+"	<td >"+process+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td class='td_normal_title'>备注</td>	  \r\n"
			+"	<td colspan=5>"+moneyDesc+"</td> \r\n"
			+"  </tr>             \r\n"
			+"  <tr>              \r\n"
			+"	<td colspan=6  width=100%>  			  \r\n"
			//------------------------------------工程付款情况--------------------------------------------------
			+"<table class='tb_normal' width=100% >"
			+"<tr>				  \r\n"
			+"<td colspan="+kdtEntrys.getColumnCount()+" class='td_normal_title_head'>工程付款情况</td>"
			+"</tr>  			  \r\n";
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				if(i != 6 && i != 7){
					html+="<tr>\r\n";
					for (int j = 0; j < kdtEntrys.getColumnCount(); j++) {
						html += util.createColumn(kdtEntrys.getCell(i, j).getValue()+"");
					}
					html+="</tr> \r\n";
				}
			}
			html+="</table>";
			
			html=html+"  <tr>              \r\n"
					+"	<td class='td_normal_title'>制单人</td>	  \r\n"
					+"	<td colspan=2>"+promptCreator+"</td> \r\n"
					+"	<td class='td_normal_title'>制单日期</td>	  \r\n"
					+"	<td colspan=2>"+dateCreateTim+"</td> \r\n"
					+"  </tr>             \r\n"
					+"  <tr>              \r\n"
					+"	<td class='td_normal_title'>审核人</td>	  \r\n"
					+"	<td colspan=2>"+promptAuditor+"</td> \r\n"
					+"	<td class='td_normal_title'>审核日期</td>	  \r\n"
					+"	<td colspan=2>"+auditDate+"</td> \r\n"
					+"  </tr>             \r\n";
			
			html=html+"</table>            \r\n";
			html=html.replaceAll("null", " ");
			System.out.println("------------html"+html);
		return html;
		
			
	}
	
	
	
	
}
