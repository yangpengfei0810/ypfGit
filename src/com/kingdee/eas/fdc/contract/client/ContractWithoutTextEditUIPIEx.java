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
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
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

public class ContractWithoutTextEditUIPIEx extends ContractWithoutTextEditUI {
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
	private static FDCUtil util;
	private static ContractWithoutTextListUIPIEx listUI=null;
	
	private static String CALLTYPE_SEND ="发送";//写log用的
	
	public ContractWithoutTextEditUIPIEx() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();

		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("VIEW")||oprtState.equals("EDIT"))
			{
			  listUI = (ContractWithoutTextListUIPIEx) this.getUIContext().get("Owner");
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
			   listUI = (ContractWithoutTextListUIPIEx) this.getUIContext().get("Owner");
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
			boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractWithoutText WHERE fid='"+fid+"'");
			if(!isEnableSubmit)
			{
				MsgBox.showInfo("单据已经提交OA走审批流程，不能删除");
				SysUtil.abort();
			}
	    }
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		
		String id = (!editData.getId().equals("") && editData.getId() !=null)?editData.getId().toString():"";
    	if(oprtState.equals("VIEW"))
    	{
    		fid = id;
    	}
    	
		//检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
		 if(!fid.equals("")){
			boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractWithoutText WHERE fid='"+fid+"'");
			if(!isEnableSubmit)
			{
				MsgBox.showInfo("单据已经提交OA走审批流程，不能编辑");
				SysUtil.abort();
			}
		 }
		super.actionEdit_actionPerformed(arg0);
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
			ContractWithoutTextCollection col = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(view);
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
			ContractWithoutTextCollection col = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(view);
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
			ContractWithoutTextCollection col = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(view);
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
	
	// modify by ll 2012-10-11 单据已走OA审批流程，但单据在查看下可以审批
	  public void actionAudit_actionPerformed(ActionEvent e){
		  
		  ContractWithoutTextInfo contractInfo=null;
		try {
			contractInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(editData.getId()));
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  String state = contractInfo.getState().toString();
		  Boolean isOAAudit =contractInfo.isIsOAAudit();
			String oaAudit=isOAAudit.toString();
			System.out.println("state:" + state + "   isOAAudit:" + oaAudit);

			if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
					&& oaAudit.equals("true")) {
				MsgBox.showInfo("单据已经走OA流程审批，不能在EAS审批");
				SysUtil.abort();
			} else {
				try {
					super.actionAudit_actionPerformed(e);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	  // modify by ll 2012-10-11 单据已走OA审批流程，但单据在查看下可以反审批
		public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
			 ContractWithoutTextInfo contractInfo=null;
				try {
					contractInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(editData.getId()));
				} catch (EASBizException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BOSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  String state = contractInfo.getState().toString();
				  Boolean isOAAudit =contractInfo.isIsOAAudit();
					String oaAudit=isOAAudit.toString();
					System.out.println("state:" + state + "   isOAAudit:" + oaAudit);

			if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
					&& oaAudit.equals("true")) {
				MsgBox.showInfo("单据已经通过OA审批，不能进行反审批");
				SysUtil.abort();
			} else {
				super.actionUnAudit_actionPerformed(e);
			}
		}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
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
		//重置静态变量
		resetParam();
		
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
		
		boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractWithoutText WHERE fid='"+fid+"'");
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
			String sql = "SELECT t1.fid billID,t3.fid attachmentID FROM T_CON_ContractWithoutText t1 left join T_BAS_BoAttchAsso t2 on t1.fid=t2.fboid left join  T_BAS_Attachment t3 on t2.fattachmentid =t3.fid WHERE t1.fid='"+fid+"'";
			 util = new FDCUtil();
			if(!util.checkExistAttachment(sql))
			{
				actionSubmit_no_attachment(e);
				SysUtil.abort();
			}
			
			bl_ok = true;
			String jsonForm = getJsonForm((String) fid);
			String formHTML="";
			try {
				formHTML = getHTML(fid);
			} catch (BOSException e4) {
				e4.printStackTrace();
			}
		    System.out.println("------actionSubmit_actionPerformed-----fid:"+fid);
	         
		    //如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
		    String prjMappingName = PROJECT_INFO.getPrjMappingName();
		    
		    //工程项目+单据名称
//		    String subject = prmtcurProject.getText()+txtbillName.getText();//mod by ypf on 20121106
		    String subject = editData.getCurProject().getName()+txtbillName.getText();//mod by ypf on 20141118 空间的文本值显示的是项目长名称，业务上只要项目名称
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
			String keyForModel = "EAS5("+prjMappingName+")";
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
						logInfo.setSourceBillType("EAS5");
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
						logInfo.setSourceBillType("EAS5");
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
							logInfo.setSourceBillType("EAS5");
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
				logInfo.setLogTitle("调用OA接口传送单据数据失败. ");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS5");
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
				if(!updateContractWithoutText(fid,true,templateId,oaId)){
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
							logInfo.setSourceBillType("EAS5");
							WSLogFactory.getRemoteInstance().addnew(logInfo);
							
							//mod by ypf on 20121111 刷新listui
							rebackRefreshListUI(e);
							
							//先保存再关闭，否则会弹出“数据已修改，是否需要保存”提示
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
						logInfo.setSourceBillType("EAS5");
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
				logInfo.setSourceBillType("EAS5");
				WSLogFactory.getRemoteInstance().addnew(logInfo);
				
				SysUtil.abort();
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", fid));
			view.setFilter(filter);
			ContractWithoutTextCollection col;
			try {
				col = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(view);
				ContractWithoutTextInfo info = new ContractWithoutTextInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//更新附件内容
					   ContractWithoutTextFactory.getRemoteInstance().updatePartial(info, selector);
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
	
	public void actionSubmit_no_attachment(ActionEvent e) {
		bl_ok = true;
		String jsonForm = getJsonForm((String) fid);
		String formHTML="";
		try {
			formHTML = getHTML(fid);
		} catch (BOSException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
	    System.out.println("------actionSubmit_actionPerformed-----fid:"+fid);
         
	    //如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
	    String prjMappingName = PROJECT_INFO.getPrjMappingName();
		
	    //工程项目+单据名称
//	    String subject = prmtcurProject.getText()+txtbillName.getText();//mod by ypf on 20121106
	    String subject = editData.getCurProject().getName()+txtbillName.getText();//mod by ypf on 20141118 空间的文本值显示的是项目长名称，业务上只要项目名称
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
			String keyForModel = "EAS5("+prjMappingName+")";
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
			System.out.println("-------驳回重新提交时根据单据id查找到的oa单据id:"+oaBillID);
			
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
						logInfo.setSourceBillType("EAS5");
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
						logInfo.setSourceBillType("EAS5");
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
							MsgBox.showDetailAndOK(null, "调用OA接口传送单据数据失败. \r\n点击详细信息查看详情.", e2.toString(), 1);
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("失败");
							logInfo.setLogTitle("调用OA接口传送单据数据时失败");
							logInfo.setLogDetail(e2.toString());
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS5");
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
				logInfo.setSourceBillType("EAS5");
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
				if(!updateContractWithoutText(fid,true,templateId,oaId)){
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
							logInfo.setSourceBillType("EAS5");
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
						logInfo.setSourceBillType("EAS5");
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
				logInfo.setSourceBillType("EAS5");
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
			ContractWithoutTextCollection col;
			try {
				col = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(view);
				ContractWithoutTextInfo info = new ContractWithoutTextInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//更新附件内容
					   ContractWithoutTextFactory.getRemoteInstance().updatePartial(info, selector);
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
		
		ContractWithoutTextInfo info = null;
		try {
			ContractWithoutTextCollection col = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(view);
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
	 * 修改时间：2012-11-27 
	 * ***************************************************
	 */
	private String getJsonForm(String fid)
	{
		String json = "";
		String longName= PROJECT_INFO.getDisplayName();//项目长名称
		String longNumber= PROJECT_INFO.getLongNumber();//项目长编码
		String contractNumber = (txtNumber.getText()==null)?"":txtNumber.getText();// 单据编码
		String contractName = (txtbillName.getText() ==null)?"":txtbillName.getText();// 单据名称
		String isContractAudit= "0";//是否合同审批
		AdminOrgUnitInfo adminOrgUnitInfo = (AdminOrgUnitInfo) prmtuseDepartment.getData();
		String useDepartment = (adminOrgUnitInfo != null && adminOrgUnitInfo.getName()!=null)?adminOrgUnitInfo.getName():"";//用款部门  add by ypf on 20121127
		CurrencyInfo currencyInfo = (CurrencyInfo) prmtcurrency.getData();
		String currency = (currencyInfo!=null && currencyInfo.getName()!=null)?currencyInfo.getName():"";//币别  add by ypf on 20121127
		String currencyAmount = (txtBcAmount.getText()==null)?"":txtBcAmount.getText();//原币金额  mod by ypf on 20121127
		SupplierInfo recvUnit = (SupplierInfo) prmtreceiveUnit.getData();//收款单位全称  add by ypf on 20121127
		String recv_unit = (recvUnit !=null && recvUnit.getName()!=null)?recvUnit.getName():"";
		SupplierInfo realRecvUnit = (SupplierInfo) prmtrealSupplier.getData();//实际收款单位  add by ypf on 20121127
		String real_recv_unit =  (realRecvUnit!=null && realRecvUnit.getName()!=null)?realRecvUnit.getName():"";
		SettlementTypeInfo settlementTypeInfo = (SettlementTypeInfo) prmtsettlementType.getData();//结算方式  add by ypf on 20121127
		String settle_type = (settlementTypeInfo !=null &&settlementTypeInfo.getName()!=null)?settlementTypeInfo.getName():"";
		String bankName = (txtBank.getText()!=null && !txtBank.getText().equals(""))?txtBank.getText():"";//收款银行  add by ypf on 20121127
		String bankNumber = (txtBankAcct.getText()!=null && !txtBankAcct.getText().equals(""))?txtBankAcct.getText():"";//收款帐号  add by ypf on 20121127
		String moneyDes = (txtMoneyDesc.getText()!=null && !txtMoneyDesc.getText().equals(""))?txtMoneyDesc.getText():"";//款项说明  add by ypf on 20121127
		String requestMoney = (txtBcAmount.getText()==null)?"":txtBcAmount.getText();//原币金额  mod by ypf on 20121129
		
		Map map = new HashMap();
		map.put("prj_long_number", longNumber);
		map.put("prj_long_name", longName);
        map.put("bill_number", contractNumber);
        map.put("bill_name", contractName);
        map.put("no_contract_audit", isContractAudit);
        map.put("use_deparment", useDepartment);//add by ypf on 20121127
        map.put("currency", currency);//add by ypf on 20121127
        map.put("currency_money", util.cutComma(currencyAmount));//原币金额  mod by ypf on 20121127
        map.put("rev_full_name", recv_unit);//add by ypf on 20121127
        map.put("real_rev_deparment",real_recv_unit);//add by ypf on 20121127
        map.put("settle_type", settle_type);//add by ypf on 20121127
        map.put("rev_bank", bankName);//add by ypf on 20121127
        map.put("bank_number", bankNumber);//add by ypf on 20121127
        map.put("content_description", moneyDes);//add by ypf on 20121127
        
        map.put("req_money", util.cutComma(requestMoney));//原币金额  mod by ypf on 20121127
		
		JsDataTools js = new JsDataTools();
	    json = js.createJsData(map);
		System.out.println("----无文本合同单据-----json:"+json);
		
		return json;
	}

	//是否走OA审批
	private int isOAAudit()
	{
		return MsgBox.showConfirm3("请选择是否走OA审批流程？");
	}
	
	/**
	 * **************************************************
	 * 方法说明: updateContractWithoutText(在走提交后，更新‘是否走oa审批’字段)  
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
	public boolean updateContractWithoutText(String id,boolean isOAAudit,String billTempletID,String oaBillID)
	{
		System.out.println("------发起流程成功后更新合同信息："+"  eas单据id:"+id +"  是否走oa审批："+isOAAudit+"  模板id："+billTempletID +"  oa单据id："+oaBillID);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		ContractWithoutTextCollection col;
		try {
			col = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(view);
			ContractWithoutTextInfo info = new ContractWithoutTextInfo();
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
				   ContractWithoutTextFactory.getRemoteInstance().updatePartial(info, selector);
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
		filter.getFilterItems().add(new FilterItemInfo("description", keyForModel.trim()));
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
	
	public String getHTML(String fid) throws BOSException {
		String html = "";
		String org = "";// 组织
		String curProject="";//项目；名称
		String number="";//单据编号
		String billName="";//单据名称
		String bookedDate="";//业务日期
		String period="";//订立期间
		String payment="";//付款类型
		String useDepartment="";//用款部门
		String contractType="";//合同类型
		String signDate="";//付款日期
		String paymentRequestBillNumber="";//付款申请单编码
		String currency="";//币别
		String receiveUnit="";//收款单位全称
		String exchangeRate="";//汇率
		String realSupplier="";//实际收款单位
		String amount="";//原币金额
		String invoiceNumber="";//发票号
		String invoiceAmt="";//发票金额
		String invoiceDate="";//开票日期
		String allInvoiceAmt="";//累计发票金额
		String settlementType="";//结算方式
		String bcAmount="";//本币金额
		String bank="";//收款银行
		String capitalAmount="";//大写金额
		String bankAcct="";//收款帐号
		String completePrjAmt="";//本期完工工程量
		String paymentProportion="";//进度付款比例%
		String attachment="";//附件
		String description="";//计划项目
		String needPaid="";//无需付款
		String moneyDesc="";//款项说明
		String noPaidReason="";//无需付款原因
		String urgency="";//加急
		String conCharge="";//合同费用项目
		String attachments="";//附件列表
		String creator="";//制单人
		String dateCreateTime="";//制单日期
		String auditor="";//审批人
		String auditTime="";//审批日期
		
		org = (txtOrg.getText()==null)?"":txtOrg.getText();
		curProject=(prmtcurProject.getText()==null)?"":prmtcurProject.getText();
		number=(txtNumber.getText()==null)?"":txtNumber.getText();
		billName=(txtbillName.getText()==null)?"":txtbillName.getText();
		bookedDate=(pkbookedDate.getText()==null)?"":pkbookedDate.getText();
		period=(cbPeriod.getText()==null)?"":cbPeriod.getText();
		payment=(prmtPayment.getText()==null)?"":prmtPayment.getText();
		useDepartment=(prmtuseDepartment.getText()==null)?"":prmtuseDepartment.getText();
		contractType=(prmtContractType.getText()==null)?"":prmtContractType.getText();
		signDate=(pksignDate.getText()==null)?"":pksignDate.getText();
		paymentRequestBillNumber=(txtPaymentRequestBillNumber.getText()==null)?"":txtPaymentRequestBillNumber.getText();
		currency=(prmtcurrency.getText()==null)?"":prmtcurrency.getText();
		receiveUnit=(prmtreceiveUnit.getText()==null)?"":prmtreceiveUnit.getText();
		exchangeRate=(txtexchangeRate.getText()==null)?"":txtexchangeRate.getText();
		realSupplier=(prmtrealSupplier.getText()==null)?"":prmtrealSupplier.getText();
		amount=(txtamount.getText()==null)?"":txtamount.getText();
		invoiceNumber=(txtInvoiceNumber.getText()==null)?"":txtInvoiceNumber.getText();
		invoiceAmt=(txtInvoiceAmt.getText()==null)?"":txtInvoiceAmt.getText();
		invoiceDate=(pkInvoiceDate.getText()==null)?"":pkInvoiceDate.getText();
		allInvoiceAmt=(txtAllInvoiceAmt.getText()==null)?"":txtAllInvoiceAmt.getText();
		settlementType=(prmtsettlementType.getText()==null)?"":prmtsettlementType.getText();
		bcAmount=(txtBcAmount.getText()==null)?"":txtBcAmount.getText();
		bank=(txtBank.getText()==null)?"":txtBank.getText();
		capitalAmount=(txtcapitalAmount.getText()==null)?"":txtcapitalAmount.getText();
		bankAcct=(txtBankAcct.getText()==null)?"":txtBankAcct.getText();
		completePrjAmt=(txtcompletePrjAmt.getText()==null)?"":txtcompletePrjAmt.getText();
		paymentProportion=(txtPaymentProportion.getText()==null)?"":txtPaymentProportion.getText();
		attachment=(txtattachment.getText()==null)?"":txtattachment.getText();
		description=(prmtPlanProject.getText()==null)?"":prmtPlanProject.getText(); // mod by ypf on 20121105
//		description="";
		needPaid=(chkNeedPaid.isSelected()==true)?"是":"否";
		moneyDesc=(txtMoneyDesc.getText()==null)?"":txtMoneyDesc.getText();
		noPaidReason=(txtNoPaidReason.getText()==null)?"":txtNoPaidReason.getText();
		urgency=(chkUrgency.isSelected()==true)?"是":"否";
		conCharge=(prmtConCharge.getText()==null)?"":prmtConCharge.getText();
		if(cmbAttachment.getItemCount()>0){
		String atts="";
		  for(int i=0;i<cmbAttachment.getItemCount();i++){
			  if(cmbAttachment.getItemAt(i)!=null){
				  atts=atts+cmbAttachment.getItemAt(i)+",";
			  }
		  }
	    int num= atts.lastIndexOf(",");
		attachments=atts.substring(0, num);
		}
		creator=(prmtCreator.getText()==null)?"":prmtCreator.getText();
		dateCreateTime=(kDDateCreateTime.getText()==null)?"":kDDateCreateTime.getText();
		auditor=(prmtAuditor.getText()==null)?"":prmtAuditor.getText();
		auditTime=(pkauditTime.getText()==null)?"":pkauditTime.getText();
		
		html=
		"\r\n <table class='tb_normal' width=100%>		  \r\n"
		+"<tr><td colspan=6 class='td_normal_title_head'>无文本合同表头</td></tr> \r\n"
		+"  <tr>              \r\n"
		+"	<td width=16% class='td_normal_title'>组织</td>	  \r\n"
		+"	<td width=16% >"+org+"</td> \r\n"
		+"	<td   class='td_normal_title'>项目名称</td>	  \r\n"
		+"	<td width=16% >"+curProject+"</td> \r\n"
		+"	<td width=16%  class='td_normal_title'>单据编号</td>	  \r\n"
		+"	<td width=16% >"+number+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>单据名称</td>	  \r\n"
		+"	<td >"+billName+"</td> \r\n"
		+"	<td   class='td_normal_title'>业务日期</td>	  \r\n"
		+"	<td >"+bookedDate+"</td> \r\n"
		+"	<td   class='td_normal_title'>订立期间</td>	  \r\n"
		+"	<td >"+period+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>付款类型</td>	  \r\n"
		+"	<td >"+payment+"</td> \r\n"
		+"	<td   class='td_normal_title'>用款部门</td>	  \r\n"
		+"	<td >"+useDepartment+"</td> \r\n"
		+"	<td   class='td_normal_title'>合同类型</td>	  \r\n"
		+"	<td >"+contractType+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>付款日期</td>	  \r\n"
		+"	<td >"+signDate+"</td> \r\n"
		+"	<td   class='td_normal_title'>付款申请单编码</td>	  \r\n"
		+"	<td >"+paymentRequestBillNumber+"</td> \r\n"
		+"	<td   class='td_normal_title'>币别</td>	  \r\n"
		+"	<td >"+currency+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>收款单位全全称</td>	  \r\n"
		+"	<td >"+receiveUnit+"</td> \r\n"
		+"	<td   class='td_normal_title'>汇率</td>	  \r\n"
		+"	<td >"+exchangeRate+"</td> \r\n"
		+"	<td   class='td_normal_title'>实际收款单位</td>	  \r\n"
		+"	<td >"+realSupplier+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>原币金额</td>	  \r\n"
		+"	<td >"+amount+"</td> \r\n"
		+"	<td   class='td_normal_title'>发票号</td>	  \r\n"
		+"	<td >"+invoiceNumber+"</td> \r\n"
		+"	<td   class='td_normal_title'>发票金额</td>	  \r\n"
		+"	<td >"+invoiceAmt+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>开票日期</td>	  \r\n"
		+"	<td >"+invoiceDate+"</td> \r\n"
		+"	<td   class='td_normal_title'>累计发票金额</td>	  \r\n"
		+"	<td >"+allInvoiceAmt+"</td> \r\n"
		+"	<td   class='td_normal_title'>结算方式</td>	  \r\n"
		+"	<td >"+settlementType+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>本币金额</td>	  \r\n"
		+"	<td >"+bcAmount+"</td> \r\n"
		+"	<td   class='td_normal_title'>收款银行</td>	  \r\n"
		+"	<td >"+bank+"</td> \r\n"
		+"	<td   class='td_normal_title'>大写金额</td>	  \r\n"
		+"	<td >"+capitalAmount+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>收款帐号</td>	  \r\n"
		+"	<td >"+bankAcct+"</td> \r\n"
		+"	<td   class='td_normal_title'>本期完成工程量</td>	  \r\n"
		+"	<td >"+completePrjAmt+"</td> \r\n"
		+"	<td   class='td_normal_title'>进度付款比例</td>	  \r\n"
		+"	<td >"+paymentProportion+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>附件</td>	  \r\n"
		+"	<td >"+attachment+"</td> \r\n"
		+"	<td   class='td_normal_title'>计划项目</td>	  \r\n"
		+"	<td >"+description+"</td> \r\n"
		+"	<td   class='td_normal_title'>无需付款</td>	  \r\n"
		+"	<td >"+needPaid+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>款项说明</td>	  \r\n"
		+"	<td >"+moneyDesc+"</td> \r\n"
		+"	<td   class='td_normal_title'>无需付款原因</td>	  \r\n"
		+"	<td >"+noPaidReason+"</td> \r\n"
		+"	<td   class='td_normal_title'>加急</td>	  \r\n"
		+"	<td >"+urgency+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>合同费用项目</td>	  \r\n"
		+"	<td >"+conCharge+"</td> \r\n"
		+"	<td   class='td_normal_title'>附件列表</td>	  \r\n"
		+"	<td >"+attachments+"</td> \r\n"
		+"	<td   class='td_normal_title'>制单人</td>	  \r\n"
		+"	<td >"+creator+"</td> \r\n"
		+"  </tr>             \r\n"
		+"  <tr>              \r\n"
		+"	<td   class='td_normal_title'>制单日期</td>	  \r\n"
		+"	<td >"+dateCreateTime+"</td> \r\n"
		+"	<td   class='td_normal_title'>审批人</td>	  \r\n"
		+"	<td >"+auditor+"</td> \r\n"
		+"	<td   class='td_normal_title'>审批日期</td>	  \r\n"
		+"	<td >"+auditTime+"</td> \r\n"
		+"  </tr>             \r\n"
		+"</table>            \r\n";
	
		//html=html.replaceAll("<", "&lt;");
	   // html=html.replaceAll(">", "&gt;");
		System.out.println("---------html:"+html);
		return html;
	}
}
