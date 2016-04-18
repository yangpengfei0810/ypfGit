package com.kingdee.eas.fdc.contract.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.JLabel;
import javax.xml.rpc.ServiceException;

import org.json.JSONArray;
import org.json.JSONObject;


import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractExecFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractMoveHistoryCollection;
import com.kingdee.eas.fdc.contract.ContractMoveHistoryFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.JsDataTools;
import com.kingdee.eas.fdc.contract.ModelFactory;
import com.kingdee.eas.fdc.contract.ModelInfo;
import com.kingdee.eas.fdc.finance.DeductBillCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.wslog.WSLogFactory;
import com.kingdee.eas.fdc.wslog.WSLogInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.landray.kmss.km.importfile.webservice.AttachmentForm;
import com.landray.kmss.km.importfile.webservice.KmReviewParamterForm;
import com.landray.kmss.km.importfile.webservice.KmReviewParamterNoattForm;
import com.landray.kmss.km.importfile.webservice.ZHIKmReviewWebserviceService;
import com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpService;
import com.landray.kmss.km.importfile.webservice.ZHKmReviewWebserviceServiceImpServiceLocator;

public class ContractSettlementBillEditUIPIEx extends ContractSettlementBillEditUI {
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
	private static ContractSettlementBillListUIPIEx listUI=null;//add by ypf on 20121023
	private static FDCUtil util;
	
	private static String CALLTYPE_SEND ="发送";//写log用的
	
	public ContractSettlementBillEditUIPIEx() throws Exception {
		super();
	}
	
	
	//杨人代码--------------start----------------------------------
	public void onLoad() throws Exception {
		super.onLoad();
		
		txtunitPrice.setEnabled(false);
		
		setDefData();
		
		txtbuildArea.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				calUnitPrice();
		}});
		
		txtSettlePrice.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				calUnitPrice();
		}});
		
		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("EDIT"))
			{
			  listUI = (ContractSettlementBillListUIPIEx) this.getUIContext().get("Owner");
			}
		}
	}
	
	public void rebackRefreshListUI(ActionEvent e)
	{
		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("EDIT"))
			{
			   listUI = (ContractSettlementBillListUIPIEx) this.getUIContext().get("Owner");
			   try {
				    listUI.actionRefresh_actionPerformed(e);
			   } catch (Exception e1) {
					e1.printStackTrace();
			   }
			}
		}
	}
	
	//获取当前工程项目的建筑面积
	public BigDecimal getCurBuildArea(){
		CurProjectInfo curProjectInfo = editData.getCurProject();
        String proOrOrgID=curProjectInfo.getId().toString();
        String projStage=ProjectStageEnum.DYNCOST_VALUE;//项目阶段
        StringBuffer sb = new StringBuffer();
        sb.append("select type.fname_l2,entry.findexvalue from T_FDC_ProjectIndexData parent \n");
        sb.append(" left join T_FDC_ProjectIndexDataEntry entry on entry.fparentid = parent.fid \n");
        sb.append(" left join T_FDC_ApportionType type on entry.fapportionTypeID = type.fid \n");
        sb.append(" where parent.fprojOrOrgID ='"+proOrOrgID+"' \n");
        sb.append(" and type.fname_l2 = '建筑面积' \n");
        sb.append(" and parent.fprojectstage ='"+projStage+"' \n");
        sb.append(" and parent.fislatestver ='1' \n");
        sb.append(" and parent.fproductTypeid is null \n");
        ISQLExecutor executor=SQLExecutorFactory.getRemoteInstance(sb.toString());
        IRowSet rowSet;
        BigDecimal buildArea=BigDecimal.ZERO;
		try {
			rowSet = executor.executeSQL();
			if(rowSet.next()){
				buildArea=rowSet.getBigDecimal("findexvalue");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buildArea;
	}
	
	private void setDefData() {
		
		//结算单新增时，建筑面积默认取当前工程项目的建筑面积
		BigDecimal buildArea=getCurBuildArea();
		txtbuildArea.setValue(buildArea);
		
		calUnitPrice();
	}
	
	//单位造价默认取值：单位造价=结算造价本位币/建筑面积
	private void calUnitPrice() {
		BigDecimal unitPrice=BigDecimal.ZERO;//单位造价
		BigDecimal settlePrice=txtSettlePrice.getBigDecimalValue();//结算造价本位币
		BigDecimal buildArea=getCurBuildArea();
		if(buildArea.compareTo(BigDecimal.ZERO)==0){
			unitPrice=BigDecimal.ZERO;
		}else{
			unitPrice=FDCHelper.divide(settlePrice, buildArea);
		}
		txtunitPrice.setValue(unitPrice);
	}
	//杨人代码--------------end---------------------------------------------------------
	
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
	
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	String id = (!editData.getId().equals("") && editData.getId() !=null)?editData.getId().toString():"";
    	if(oprtState.equals("VIEW"))
    	{
    		fid = id;
    	}
    	
		//检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
	    if(!fid.equals("")){
			boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractSettlementBill WHERE fid='"+fid+"'");
			if(!isEnableSubmit)
			{
				MsgBox.showInfo("单据已经提交OA走审批流程，不能删除");
				SysUtil.abort();
			}
	    }
		super.actionRemove_actionPerformed(e);
		
		//mod by ypf on 20121111 刷新listui
		rebackRefreshListUI(e);
	}
    
    // modify by ll 2012-10-10 单据已走OA审批流程，但单据在查看下可以审批
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		ContractSettlementBillInfo contractbill = ContractSettlementBillFactory.getRemoteInstance()
				.getContractSettlementBillInfo(new ObjectUuidPK(editData.getId()));

		String state = contractbill.getState().toString();
		Boolean isOAAudit = contractbill.isIsOAAudit();
		String oaAudit = isOAAudit.toString();

		System.out.println("state:" + state + "   isOAAudit:" + oaAudit);

		if (state.equals(FDCBillStateEnum.SUBMITTED.getAlias())
				&& oaAudit.equals("true")) {
			MsgBox.showInfo("单据已经走OA流程审批，不能在EAS审批");
			SysUtil.abort();
		} else {
			super.actionAudit_actionPerformed(e);
			
			//mod by ypf on 20121111 刷新listui
			rebackRefreshListUI(e);
		}
	}

	// modify by ll 2012-10-10 单据已走OA审批流程，但单据在查看下可以反审批
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {

		ContractSettlementBillInfo contractbill = ContractSettlementBillFactory.getRemoteInstance()
		.getContractSettlementBillInfo(new ObjectUuidPK(editData.getId()));

		String state = contractbill.getState().toString();
		Boolean isOAAudit = contractbill.isIsOAAudit();
		String oaAudit = isOAAudit.toString();
		System.out.println("state:" + state + "   isOAAudit:" + oaAudit);

		if (state.equals(FDCBillStateEnum.AUDITTED.getAlias())
				&& oaAudit.equals("true")) {
			MsgBox.showInfo("单据已经通过OA审批，不能进行反审批");
			SysUtil.abort();
		} else {
			super.actionUnAudit_actionPerformed(e);
			
			//mod by ypf on 20121111 刷新listui
			rebackRefreshListUI(e);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
//		String formHTML = getHTML("EEFt3r3rQoy5vZldHXvY3Q1t0fQ=");
		String id = (!editData.getId().equals("") && editData.getId() !=null)?editData.getId().toString():"";
    	if(oprtState.equals("VIEW"))
    	{
    		fid = id;
    	}
    	
		//检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
		 if(!fid.equals("")){
			boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractSettlementBill WHERE fid='"+fid+"'");
			if(!isEnableSubmit)
			{
				MsgBox.showInfo("单据已经提交OA走审批流程，不能编辑");
				SysUtil.abort();
			}
		 }
		super.actionEdit_actionPerformed(arg0);
	}
	
	private String getBillState(String id)
	{
		String state = "";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		
		try {
			ContractSettlementBillCollection col = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(view);
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
			ContractSettlementBillCollection col = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(view);
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
			ContractSettlementBillCollection col = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(view);
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
		
		boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ContractSettlementBill WHERE fid='"+fid+"'");
		if(!isEnableSubmit)
		{
			MsgBox.showInfo("单据已经提交OA走审批流程，不能重复提交");
			SysUtil.abort();
		}
		
	    num = isOAAudit();
	    
		//1、选择是否走OA审批
		if(num == 0)
		{
			//1.1、准备初始数据
			PROJECT_INFO=getPrjInfo(fid);//初始化项目工程对象
			
			//判断是否存在附件
			String sql = "SELECT t1.fid billID,t3.fid attachmentID FROM T_CON_ContractSettlementBill t1 left join T_BAS_BoAttchAsso t2 on t1.fid=t2.fboid left join  T_BAS_Attachment t3 on t2.fattachmentid =t3.fid WHERE t1.fid='"+fid+"'";
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
//		    String subject = "结算单";
//		    String subject = (txtcontractName.getText()!=null && txtcontractName.getText()!="") ? "-"+txtcontractName.getText():"";
		    
		    //结算单名称
		    String subject = txtbillName.getText();// mod by ypf on 20121106
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
			String keyForModel = "EAS3("+prjMappingName+")";
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
						logInfo.setSourceBillType("EAS3");
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
						logInfo.setSourceBillType("EAS3");
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
							logInfo.setSourceBillType("EAS3");
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
				logInfo.setLogTitle("调用OA接口传送单据数据失败.");
				logInfo.setLogDetail(e1.toString());
				logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	logInfo.setCallType(CALLTYPE_SEND);
				logInfo.setSourceBillType("EAS3");
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
				if(!updateContractSettlementBill(fid,true,templateId,oaId)){
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
							logInfo.setSourceBillType("EAS3");
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
						logInfo.setSourceBillType("EAS3");
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
				logInfo.setSourceBillType("EAS3");
				WSLogFactory.getRemoteInstance().addnew(logInfo);
				
				SysUtil.abort();
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", fid));
			view.setFilter(filter);
			ContractSettlementBillCollection col;
			try {
				col = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(view);
				ContractSettlementBillInfo info = new ContractSettlementBillInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//更新附件内容
					   ContractSettlementBillFactory.getRemoteInstance().updatePartial(info, selector);
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
			
		}
		else
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
			e4.printStackTrace();
		}
	    System.out.println("------actionSubmit_actionPerformed-----fid:"+fid);
         
	    //如果是新增状态，则会从uicontext里面取（prepareUIContext这里在新增时有压值），否则手动取
	    String prjMappingName = PROJECT_INFO.getPrjMappingName();
	    
	    //结算单名称
	    String subject = txtbillName.getText();// mod by ypf on 20121106
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
			String keyForModel = "EAS3("+prjMappingName+")";
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
						logInfo.setSourceBillType("EAS3");
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
						logInfo.setSourceBillType("EAS3");
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
							logInfo.setSourceBillType("EAS3");
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
				logInfo.setSourceBillType("EAS3");
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
				if(!updateContractSettlementBill(fid,true,templateId,oaId)){
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
							logInfo.setSourceBillType("EAS3");
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
						logInfo.setSourceBillType("EAS3");
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
				logInfo.setSourceBillType("EAS3");
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
			ContractSettlementBillCollection col;
			try {
				col = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(view);
				ContractSettlementBillInfo info = new ContractSettlementBillInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//更新附件内容
					   ContractSettlementBillFactory.getRemoteInstance().updatePartial(info, selector);
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
		
		ContractSettlementBillInfo info = null;
		try {
			ContractSettlementBillCollection col = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(view);
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
		String contractId = (fid == null || fid == "")?"":fid;// 合同id
		String contractNumber = (txtcontractNumber.getText()==null)?"":txtcontractNumber.getText();// 合同编号
		String billNumber =(txtNumber.getText()==null)?"":txtNumber.getText(); //结算单编码
		String partB=(contractBill.getPartB().getName()==null)?"":contractBill.getPartB().getName().toString(); //乙方
		String settlePrice=(txtSettlePrice.getText()==null)?"":txtSettlePrice.getText();//结算造价本位币
		String contractName = (txtcontractName.getText() ==null)?"":txtcontractName.getText();// 合同名称
		String longNumber= PROJECT_INFO.getLongNumber();//长编码
		String longName= PROJECT_INFO.getDisplayName();//长名称
		String amount=(contractBill.getAmount()==null)?"":util.getDecimal(contractBill.getAmount().toString());//合同金额
		
		Map map = new HashMap();
		map.put("contract_number", contractNumber);
        map.put("cost_bill_number", billNumber);
        map.put("bill_B", partB);
        
        //map.put("cost_amount", util.cutComma(settlePrice));
        //mod by ypf on 20130731 使用EASBak_1
        map.put("EASBak_1", util.cutComma(settlePrice));
        map.put("contract_name", contractName);
        map.put("prj_long_number", longNumber);
        map.put("prj_long_name", longName);
        map.put("contract_amount", util.cutComma(amount));
        
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
	 * 方法说明: updateContractSettlementBill(在走提交后，更新‘是否走oa审批’字段)  
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
	public boolean updateContractSettlementBill(String id,boolean isOAAudit,String billTempletID,String oaBillID)
	{
		System.out.println("------发起流程成功后更新合同信息："+"  eas单据id:"+id +"  是否走oa审批："+isOAAudit+"  模板id："+billTempletID +"  oa单据id："+oaBillID);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		ContractSettlementBillCollection col;
		try {
			col = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(view);
			ContractSettlementBillInfo info = new ContractSettlementBillInfo();
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
				   ContractSettlementBillFactory.getRemoteInstance().updatePartial(info, selector);
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
//		util = new FDCUtil();
	    String html = "";
		String org = "";// 组织
		String proj = "";// 工程项目
		String contractNumber = "";// 合同编码
		String contractName = "";// 合同名称
		String number = "";// 结算单编码
		String billName = "";// 结算单名称
		String bookedDate = "";// 业务日期
		String Currency = "";// 币别
		String exchangeRate = "";// 汇率
		String period = "";// 结算期间
		String originalAmount = "";// 结算造价原币
		String constructPrice = "";// 施工方报审价
		String getFeeCriteria = "";// 取费标准
		String settlePrice = "";// 结算造价本位币
		String unitPrice = "";// 单位造价（元）
		String infoPrice = "";// 信息价
		String buildArea = "";// 建筑面积
		String guaranceAmt = "";// 保修金
		String qualityTime = "";// 保修期限
		String qualityGuaranteRate = "";// 保修金比例%
		String qualityGuarante = "";// 累计保修金
		String description = "";// 备注
		String finalSettle = "";// 最终结算
		String attchment = "";// 附件列表
		String promptCreator = "";// 制单人
		String createTime = "";// 制单日期
		String promptAuditor = "";// 审批人
		String auditTime = "";// 审批日期

		org = (txtOrgUnit.getText() == null) ? "" : txtOrgUnit.getText();
		proj = (txtProj.getText() == null) ? "" : txtProj.getText();
		contractNumber = (txtcontractNumber.getText() == null) ? ""
				: txtcontractNumber.getText();
		contractName = (txtcontractName.getText() == null) ? ""
				: txtcontractName.getText();
		number = (txtNumber.getText() == null) ? "" : txtNumber.getText();
		billName = (txtbillName.getText() == null) ? "" : txtbillName.getText();
		bookedDate = (pkbookedDate.getText() == null) ? "" : pkbookedDate
				.getText();
		Currency = (prmtCurrency.getText() == null ? "" : prmtCurrency
				.getText());
		exchangeRate = (txtExchangeRate.getText() == null) ? ""
				: txtExchangeRate.getText();
		period = (cbPeriod.getText() == null) ? "" : cbPeriod.getText();
		originalAmount = (txtOriginalAmount.getText() == null) ? ""
				: txtOriginalAmount.getText();
		constructPrice = (txtConstructPrice.getText() == null) ? ""
				: txtConstructPrice.getText();
		getFeeCriteria = (txtgetFeeCriteria.getText() == null) ? ""
				: txtgetFeeCriteria.getText();
		settlePrice = (txtSettlePrice.getText() == null) ? "" : txtSettlePrice
				.getText();
		unitPrice = (txtunitPrice.getText() == null) ? "" : txtunitPrice
				.getText();
		infoPrice = (txtinfoPrice.getText() == null) ? "" : txtinfoPrice
				.getText();
		buildArea = (txtbuildArea.getText() == null) ? "" : txtbuildArea
				.getText();
		guaranceAmt = (txtGuaranceAmt.getText() == null) ? "" : txtGuaranceAmt
				.getText();
		qualityTime = (tetQualityTime.getText() == null) ? "" : tetQualityTime
				.getText();
		qualityGuaranteRate = (txtqualityGuaranteRate.getText() == null) ? ""
				: txtqualityGuaranteRate.getText();
		qualityGuarante = (txtqualityGuarante.getText() == null) ? ""
				: txtqualityGuarante.getText();
		description = (txtDescription.getSelectedItem().toString() == null) ? "" : txtDescription.getSelectedItem().toString();//mod by ypf on 20121105
//		description = "";
		finalSettle = (cbFinalSettle.getSelectedItem() == null) ? ""
				: cbFinalSettle.getSelectedItem().toString();
		if (cmbAttchment.getItemCount() > 0) {

			String atts = "";
			for (int i = 0; i < cmbAttchment.getItemCount(); i++) {
				if (cmbAttchment.getItemAt(i) != null) {
					atts = atts + cmbAttchment.getItemAt(i) + ",";
				}
			}
			int num = atts.lastIndexOf(",");
			attchment = atts.substring(0, num);

		}
		promptCreator = (bizPromptCreator.getText() == null) ? ""
				: bizPromptCreator.getText();
		createTime = (dateCreateTime.getText() == null) ? "" : dateCreateTime
				.getText();
		promptAuditor = (bizPromptAuditor.getText() == null) ? ""
				: bizPromptAuditor.getText();
		auditTime = (pkauditTime.getText() == null) ? "" : pkauditTime
				.getText();

		ContractBillInfo contractBill = editData.getContractBill();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("orgUnit.name"));
		selector.add(new SelectorItemInfo("curProject.displayName"));
		selector.add(new SelectorItemInfo("contractType.displayName"));
		selector.add(new SelectorItemInfo("codingNumber"));
		selector.add(new SelectorItemInfo("landDeveloper.name"));
		selector.add(new SelectorItemInfo("partB.name"));
		selector.add(new SelectorItemInfo("partC.*"));
		selector.add(new SelectorItemInfo("partC.name"));
		selector.add(new SelectorItemInfo("contractPropert"));
		selector.add(new SelectorItemInfo("signDate"));
		selector.add(new SelectorItemInfo("currency.name"));
		selector.add(new SelectorItemInfo("exRate"));
		selector.add(new SelectorItemInfo("respDept.name"));
		selector.add(new SelectorItemInfo("originalAmount"));
		selector.add(new SelectorItemInfo("amount"));
		selector.add(new SelectorItemInfo("respPerson.name"));
		selector.add(new SelectorItemInfo("grtRate"));
		selector.add(new SelectorItemInfo("bookedDate"));
		selector.add(new SelectorItemInfo("contractSource"));
		selector.add(new SelectorItemInfo("payPercForWarn"));
		selector.add(new SelectorItemInfo("period.periodYear"));
		selector.add(new SelectorItemInfo("period.periodNumber"));
		selector.add(new SelectorItemInfo("contractBill"));
		selector.add(new SelectorItemInfo("chgPercForWarn"));
		selector.add(new SelectorItemInfo("creator.name"));
		selector.add(new SelectorItemInfo("payScale"));
		selector.add(new SelectorItemInfo("createTime"));
		selector.add(new SelectorItemInfo("stampTaxAmt"));
		selector.add(new SelectorItemInfo("overRate"));
		selector.add(new SelectorItemInfo("entrys.*"));

		try {
			contractBill = ContractBillFactory.getRemoteInstance()
					.getContractBillInfo(
							new ObjectUuidPK(contractBill.getId().toString()),
							selector);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// EconItem

		html = "\r\n<table class='tb_normal' width=100%>		  \r\n"
			+"<tr><td colspan=6 class='td_normal_title_head'>结算单表头</td></tr>\r\n"
				+ "  <tr>              \r\n" 
				+ "	<td width=16%  class='td_normal_title'>组织</td>	  \r\n"
				+ "	<td width=16%>"
				+ org
				+ "</td> \r\n"
				+ "	<td width=16% class='td_normal_title'>工程项目</td>	  \r\n"
				+ "	<td width=16%>"
				+ proj
				+ "</td> \r\n"
				+ "	<td width=16% class='td_normal_title'>合同编码</td>	  \r\n"
				+ "	<td width=16%>"
				+ contractNumber
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>合同名称</td>	  \r\n"
				+ "	<td>"
				+ contractName
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>结算单编码</td>	  \r\n"
				+ "	<td>"
				+ number
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>结算单名称</td>	  \r\n"
				+ "	<td>"
				+ billName
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>业务日期</td>	  \r\n"
				+ "	<td  >"
				+ bookedDate
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>币别</td>	  \r\n"
				+ "	<td >"
				+ Currency
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>汇率</td>	  \r\n"
				+ "	<td>"
				+util.getDecimal(exchangeRate+"")
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>结算期间</td>	  \r\n"
				+ "	<td>"
				+ period
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>结算造价原币</td>	  \r\n"
				+ "	<td>"
				+util.getDecimal(originalAmount)
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>施工方报审价</td>	  \r\n"
				+ "	<td>"
				+ util.getDecimal(constructPrice)
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>取费标准</td>	  \r\n"
				+ "	<td>"
				+ getFeeCriteria
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>结算造价本位币</td>	  \r\n"
				+ "	<td>"
				+ util.getDecimal(settlePrice)
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>单位造价(元)</td>	  \r\n"
				+ "	<td>"
				+util.getDecimal( unitPrice)
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>信息价</td>	  \r\n"
				+ "	<td>"
				+ util.getDecimal(infoPrice)
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>建筑面积</td>	  \r\n"
				+ "	<td>"
				+ util.getDecimal(buildArea)
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>保修金</td>	  \r\n"
				+ "	<td>"
				+ util.getDecimal(guaranceAmt)
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>保修期限</td>	  \r\n"
				+ "	<td>"
				+ qualityTime
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>保修金比例%</td>	  \r\n"
				+ "	<td>"
				+ util.getDecimal(qualityGuaranteRate)
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>累计保修金</td>	  \r\n"
				+ "	<td>"
				+ util.getDecimal(qualityGuarante)
				+ "</td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>备注</td>	  \r\n"
				+ "	<td>"
				+ description
				+ "</td> \r\n"
				+ "	<td class='td_normal_title'>最终结算</td>	  \r\n"
				+ "	<td>"
				+ finalSettle
				+ "</td> \r\n"
				+ "	<td colspan=2></td> \r\n"
				+ "  </tr>             \r\n"
				+ "  <tr>              \r\n"
				+ "  <tr>              \r\n"
				+ "	<td class='td_normal_title'>附件列表</td>	  \r\n"
				+ "	<td colspan=5>"
				+ attchment
				+ "</td> \r\n"
				+ "<tr>              \r\n"
				+ "<td width=100% colspan=6>              \r\n"
				+ "<table class='tb_normal' width=100%>"
				+ "<tr>              \r\n"
				+ "<td colspan=6 class='td_normal_title_head'>汇总-合同信息-合同基本信息</td>"
				+ "</tr>              \r\n"
				+ "<tr>              \r\n"
				+ "<td class='td_test' >组织</td>"
				+ "<td colspan=2>"
				+ contractBill.getOrgUnit().getName()
				+ "</td>"
				+ "<td class='td_test'>工程项目</td>"
				+ "<td colspan=2>"
				+ contractBill.getCurProject().getDisplayName()
				+ "</td>"
				+ "</tr>              \r\n"
				+ "<tr>              \r\n"
				+ "<td class='td_test'>合同类型</td>"
				+ "<td colspan=2>"
				+ contractBill.getContractType().getDisplayName()
				+ "</td>"
				+ "<td class='td_test'>合同编号</td>"
				+ "<td colspan=2>"
				+ contractBill.getCodingNumber()
				+ "</td>"
				+ "</tr>              \r\n"
				+ "<tr>              \r\n"
				+ "<td class='td_test'>合同名称</td>"
				+ "<td colspan=5>"
				+ contractBill.getName()
				+ "</td>"
				+ "</tr>              \r\n"
				+ "<tr>              \r\n"
				+ "<td class='td_test'>甲方</td>"
				+ "<td colspan=2>"
				+ contractBill.getLandDeveloper().getName()
				+ "</td>"
				+ "<td class='td_test'>乙方</td>"
				+ "<td colspan=2>"
				+ contractBill.getPartB().getName()
				+ "</td>"
				+ "</tr>              \r\n"
				+ "<tr>              \r\n"
				+ "<td class='td_test'>丙方</td>" + "<td colspan=2>";
				SupplierInfo partc = contractBill.getPartC();
				String parcName = "";
				if (partc != null) {
					parcName = partc.getName();
				}
				html = html + parcName + "</td>" + "<td class='td_test'>合同性质</td>" + "<td colspan=2>"
				+ contractBill.getContractPropert() + "</td>"
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td class='td_test'>签约日期</td>" + "<td>" + contractBill.getSignDate()
				+ "</td>" + "<td class='td_test'>币别</td>" + "<td>"
				+ contractBill.getCurrency().getName() + "</td>"
				+ "<td class='td_test'>汇率</td>";
				if(contractBill.getExRate()==null){
					html+="<td ></td>";
				}else{
					html+="<td >"+util.getDecimal(contractBill.getExRate().toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td class='td_test'>责任部门</td>" + "<td>"
				+ contractBill.getRespDept().getName() + "</td>"
				+ "<td class='td_test'>原币金额</td>";
				if(contractBill.getOriginalAmount()==null){
					html+="<td></td>";
				}else{
					html+="<td>"+util.getDecimal(contractBill.getOriginalAmount().toString())+"</td>";
				}
				html=html
				+ "<td class='td_test'>本币金额</td>" ;
				if(contractBill.getAmount()==null){
					html+="<td></td>";
				}else{
					html+="<td>"+util.getDecimal(contractBill.getAmount().toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n"
				+ "<tr>              \r\n" + "<td class='td_test'>负责人</td>"
				+ "<td>"+ contractBill.getRespPerson().getName() + "</td>"
				+ "<td class='td_test'>保修金比例%</td>";
				if(contractBill.getGrtRate()==null){
					html+="<td ></td>";
				}else{
					html+="<td >"+util.getDecimal(contractBill.getGrtRate().toString())+"</td>";
				}
				html=html
				+ "<td class='td_test'>保修金额</td>";
				if(contractBill.getGrtAmount()==null){
					html+="<td ></td>";
				}else{
					html+="<td >"+util.getDecimal(contractBill.getGrtAmount().toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td class='td_test'>业务日期</td>" 
				+ "<td>" + contractBill.getBookedDate()+ "</td>" 
				+ "<td class='td_test'>形成方式</td>" + "<td>"
				+ contractBill.getContractSource() + "</td>"
				+ "<td class='td_test'>付款提示比例%</td>" ;
				if(contractBill.getPayPercForWarn()==null){
					html+="<td ></td>";
				}else{
					html+="<td >"+util.getDecimal(contractBill.getPayPercForWarn().toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td class='td_test'>订立期间</td>" + "<td>"
				+ contractBill.getPeriod().getPeriodYear() + "年"
				+ contractBill.getPeriod().getPeriodNumber() + "期" + "</td>"
				+ "<td class='td_test'>造价性质</td>" 
				+ "<td>" + contractBill.getCostProperty()+ "</td>" 
				+ "<td class='td_test'>变更提示比例%</td>" ;
				if(contractBill.getChgPercForWarn()==null){
					html+="<td ></td>";
				}else{
					html+="<td >"+util.getDecimal(contractBill.getChgPercForWarn().toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td class='td_test'>制单人</td>" 
				+ "<td>" + contractBill.getCreator().getName()+ "</td>" 
				+ "<td class='td_test'>是否进入动态成本"+"</td>" 
				+ "<td>"+contractBill.isIsCoseSplit() + "</td>" 
				+ "<td>"+ "是否甲供材料合同"+ "</td>" 
				+"<td>"+ contractBill.isIsPartAMaterialCon() + "</td>"
				 + "</tr>              \r\n"
				+ "<tr>              \r\n" 
				+ "<td class='td_test'>进度款付款比例%</td>";
				if(contractBill.getPayScale()==null){
					html+="<td ></td>";
				}else{
					html+="<td >"+util.getDecimal(contractBill.getPayScale().toString())+"</td>";
				}
				html=html
				+ "<td class='td_test'>制单时间</td>" + "<td>"
				+ contractBill.getCreateTime() + "</td>" + "<td>印花税率%</td>";
				if(contractBill.getStampTaxRate()==null){
					html+="<td ></td>";
				}else{
					html+="<td >"+util.getDecimal(contractBill.getStampTaxRate().toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n"
				+ "<tr>              \r\n" 
				+ "<td class='td_test'>印花税金额</td>";
				if(contractBill.getStampTaxAmt()==null){
					html+="<td ></td>";
				}else{
					html+="<td >"+util.getDecimal(contractBill.getStampTaxAmt().toString())+"</td>";
				}
				html=html
				+ "<td class='td_test'>结算提示比例%</td>";
				html+="<td  colspan=5>"+util.getDecimal(Double.toString(contractBill.getOverRate()))+"</td>";
				html=html
				+ "</tr>              \r\n"
				+ "<tr>              \r\n"
				+ "<td class='td_test'>附件列表</td>" 
				+ "<td colspan=5>" + attchment + "</td>" + "</tr>"
				+ "<tr>             \r\n"
				+ "<td colspan=6 width=100%>              \r\n";
		int rowCount = contractBill.getEntrys().size();

		if (rowCount==0) {
			html = html + "<table class='tb_normal'  width=100%>              \r\n"
					+ "<tr>             \r\n"
					+ "<td colspan=3 class='td_normal_title_head'>             \r\n"
					+ "信息一览表-汇总-合同信息-合同基本信息-合同详细信息" + "</td>             \r\n"
					+ "</tr>             \r\n"
					+ "<tr>             \r\n"
					+ "<td width=33%>详细信息</td>             \r\n"
					+ "<td  width=33%>内容</td>             \r\n"
					+ "<td  width=33%>描述</td>             \r\n"
					+ "</tr>             \r\n"
					;
			html = html + "</table>              \r\n"
					+ "</td>              \r\n" + "</tr>              \r\n"
					+ "</table>              \r\n" + "</td>              \r\n"
					+ "</tr>              \r\n";
		}
		
		if (rowCount>0 && contractBill.getEntrys().get(0).getId() != null) {
			html = html + "<table class='tb_normal'  width=100%>              \r\n"
					+ "<tr>             \r\n"
					+ "<td colspan=3 class='td_normal_title_head'>             \r\n"
					+ "信息一览表-汇总-合同信息-合同基本信息-合同详细信息" + "</td>             \r\n"
					+ "</tr>             \r\n"
					+ "<tr>             \r\n"
					+ "<td width=33%>详细信息</td>             \r\n"
					+ "<td  width=33%>内容</td>             \r\n"
					+ "<td  width=33%>描述</td>             \r\n"
					+ "</tr>             \r\n"
					;
			for (int i = 0; i < rowCount; i++) {
				html = html + "<tr>             \r\n";
				ContractBillEntryInfo rowInfo = contractBill.getEntrys().get(i);
				html = html + "<td class='td_test'>" + rowInfo.getDetail() + "</td>" 
				+ "<td>"+ util.getDecimal(rowInfo.getContent()) + "</td>" 
				+ "<td>"+ rowInfo.getDesc() + "</td>";
				html = html + "</tr>             \r\n";
			}
			html = html + "</table>              \r\n"
					+ "</td>              \r\n" + "</tr>              \r\n"
					+ "</table>              \r\n" + "</td>              \r\n"
					+ "</tr>              \r\n";
		}
		
		ContractChangeBillCollection contractChangeBill = null;
		contractChangeBill = ContractChangeBillFactory.getRemoteInstance()
				.getContractChangeBillCollection(
						"where contractBill.id='" + contractBill.getId() + "'");

		if (contractChangeBill.size() > 0) {
			html = html + "  <tr>              \r\n"
					+ "<td colspan=6 width=100%>              \r\n"
					+ "<table class='tb_normal' width=100%  >              \r\n"
					+ "<tr>              \r\n" + "<td colspan=6 class='td_normal_title_head'>信息一览表-总汇-变更信息</td>"
					+ "</tr>              \r\n" + "<tr>              \r\n"
					+ "<td>变更单编号</td>" + "<td>变更名称</td>" + "<td>变更金额</td>"
					+ "<td>变更类型</td>" + "<td>变更原因</td>" + "<td>提出部门</td>"
					+ "</tr>              \r\n";

			for (int i = 0; i < contractChangeBill.size(); i++) {
				html = html + "<tr>              \r\n" + "<td>"
						+ contractChangeBill.get(i).getNumber() + "</td>"
						+ "<td>" + contractChangeBill.get(i).getName()+ "</td>";
				if(contractChangeBill.get(i).getOriginalAmount()==null){
					html+="<td ></td>";
				}else{
					html+="<td >"+util.getDecimal(contractChangeBill.get(i).getOriginalAmount().toString())+"</td>";
				}
						html=html
						+ "<td>"
						+ contractChangeBill.get(i).getChangeTypeName()
						+ "</td>" + "<td>"
						+ contractChangeBill.get(i).getChangeReason() + "</td>"
						+ "<td>" + contractChangeBill.get(i).getConductDept()
						+ "</td>" + "</tr>              \r\n";
			}

			html = html + "</table>              \r\n"
					+ "  </td>              \r\n" + "</tr>              \r\n";
		}

		/**
		 * 成本信息
		 */
		BigDecimal changeTotal = FDCHelper.ZERO;
		Map changeMap = null;
		EntityViewInfo view = null;
		FilterInfo filter = null;
		BigDecimal total = FDCHelper.ZERO;
		view = new EntityViewInfo();
		view.getSelector().add("amount");
		view.getSelector().add("balanceAmount");
		view.getSelector().add("hasSettled");
		view.getSelector().add("changeType.id");
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.Id", contractBill.getId()
						.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", "'7ANNOUNCE','8VISA','4AUDITTED'",
						CompareType.INCLUDE));
		view.setFilter(filter);
		ContractChangeBillCollection changes = ContractChangeBillFactory
				.getRemoteInstance().getContractChangeBillCollection(view);
		total = FDCHelper.toBigDecimal(contractBill.getAmount(), 2);
		if (total == null)
			total = FDCHelper.ZERO;
		changeMap = new HashMap();
		for (int i = 0; i < changes.size(); i++) {
			ContractChangeBillInfo change = changes.get(i);
			if (change.getChangeType() == null)
				continue;
			String typeId = change.getChangeType().getId().toString();
			BigDecimal changeAmount = FDCHelper.toBigDecimal(
					change.getAmount(), 2);
			if (change.isHasSettled())
				changeAmount = FDCHelper.toBigDecimal(
						change.getBalanceAmount(), 2);
			if (changeAmount == null)
				changeAmount = FDCHelper.ZERO;
			if (changeMap.containsKey(typeId)) {
				BigDecimal sum = (BigDecimal) changeMap.get(typeId);
				changeMap.put(typeId, FDCHelper.toBigDecimal(sum
						.add(changeAmount), 2));
			} else {
				changeMap.put(typeId, changeAmount);
			}
			changeTotal = FDCHelper.toBigDecimal(changeTotal.add(changeAmount),
					2);
		}
		BigDecimal settleAmount = null;
		if (contractBill.isHasSettled()) {
			view = new EntityViewInfo();
			view.getSelector().add("totalSettlePrice");
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.Id", contractBill.getId()
							.toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("isSettled", Boolean.TRUE));
			filter.getFilterItems().add(
					new FilterItemInfo("isFinalSettle", Boolean.TRUE));
			ContractSettlementBillCollection settles = ContractSettlementBillFactory
					.getRemoteInstance().getContractSettlementBillCollection(
							view);
			if (settles.size() > 0) {
				ContractSettlementBillInfo info = settles.get(0);
				settleAmount = info.getTotalSettlePrice();
			}
		}

		FDCSQLBuilder builder = new FDCSQLBuilder();
		BigDecimal hasPayAmt = FDCHelper.ZERO;
		BigDecimal settleAmt = FDCHelper.ZERO;
		BigDecimal finalSettAmt = FDCHelper.ZERO;
		BigDecimal notAllPay = FDCHelper.ZERO;
		if (contractBill.isHasSettled()) {
			builder.clear();
			builder.appendSql("select  FSettlePrice \n ");
			builder
					.appendSql(" from t_con_contractSettlementbill where FContractBillId=?  and FIsSettled=1 and FIsFinalSettle=1 and fstate='4AUDITTED' \n ");
			builder.addParam(contractBill.getId().toString());
			IRowSet rowSet1 = builder.executeQuery();
			try {
				if (rowSet1.next()) {
					settleAmt = FDCHelper.toBigDecimal(rowSet1
							.getBigDecimal("FSettlePrice"), 2);
					finalSettAmt = settleAmt;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			builder.clear();
			builder
					.appendSql("select sum(FCurSettlePrice) FSettlePrice  from t_con_contractSettlementbill \n ");
			builder
					.appendSql(" where FContractBillId=? and FIsSettled=0 and FIsFinalSettle=0 and fstate='4AUDITTED' \n ");
			builder.addParam(contractBill.getId().toString());
			IRowSet rowSet2 = builder.executeQuery();
			try {
				if (rowSet2.next())
					settleAmt = FDCHelper.toBigDecimal(rowSet2
							.getBigDecimal("FSettlePrice"), 2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		builder.clear();
		builder
				.appendSql("select sum(FActPayLocAmt) hasPayed from T_CAS_PaymentBill \n ");
		builder.appendSql("where  FContractBillId=?  \n ");
		builder.appendSql("and FBillStatus =15 \n");
		builder.addParam(contractBill.getId().toString());
		IRowSet rowSet3 = builder.executeQuery();
		try {
			if (rowSet3.next())
				hasPayAmt = FDCHelper.toBigDecimal(rowSet3
						.getBigDecimal("hasPayed"), 2);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean isMoreSett = true;
		try {
			isMoreSett = FDCUtils.getDefaultFDCParamByKey(null, null,
					"FDC004_MORESETTER");
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BigDecimal totalworkamount = FDCHelper.ZERO;
		Set set = new HashSet();
		set.add(contractBill.getId().toString());
		if (contractBill.getCurProject() != null
				&& contractBill.getCurProject().getFullOrgUnit() != null) {
			Map workAmtMap = null;
			try {
				workAmtMap = ContractExecFacadeFactory.getRemoteInstance()
						._getCompleteAmt(
								contractBill.getCurProject().getFullOrgUnit()
										.getId().toString(), set);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (workAmtMap.get(contractBill.getId().toString()) != null)
				totalworkamount = (BigDecimal) workAmtMap.get(contractBill
						.getId().toString());
		}
		if (hasPayAmt == null)
			hasPayAmt = FDCHelper.ZERO;
		if (finalSettAmt == null)
			finalSettAmt = FDCHelper.ZERO;
		if (settleAmt == null)
			settleAmt = FDCHelper.ZERO;
		if (isMoreSett) {
			if (contractBill.isHasSettled()) {
				if (finalSettAmt != null && hasPayAmt != null)
					notAllPay = FDCHelper.subtract(finalSettAmt, hasPayAmt);
			} else {
				notAllPay = FDCHelper.subtract(settleAmt, hasPayAmt);
			}
		} else if (contractBill.isHasSettled() && settleAmt != null
				&& hasPayAmt != null)
			notAllPay = FDCHelper
					.toBigDecimal(settleAmt.subtract(hasPayAmt), 2);
		else
			notAllPay = FDCHelper.subtract(totalworkamount, hasPayAmt);
		html = html + "  <tr>              \r\n"
				+ "<td colspan=6 width=100%>              \r\n"
				+ "<table class='tb_normal' width=100%  >              \r\n"
				+ "<tr>              \r\n" + "<td colspan=4 class='td_normal_title_head'>信息一览表-总汇-成本信息</td>"
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td colspan=2></td>" + "<td colspan=2>金额</td>"
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td colspan=2  class='td_test'>合同金额</td>";
				if(contractBill.getAmount()==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(contractBill.getAmount().toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td colspan=2 class='td_test'>合同变更</td>";
				if(changeTotal==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(changeTotal.toString())+"</td>";
				}
				html=html
				 + "</tr>              \r\n"
				+ "<tr>              \r\n" + "<td colspan=2 class='td_test'>设计变更</td>";
				if(changeMap.get("001")==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(changeMap.get("001").toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td colspan=2 class='td_test'>现场签证</td>" ;
				if(changeMap.get("002")==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(changeMap.get("002").toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n"
				+ "<tr>              \r\n" + "<td colspan=2 class='td_test'>其他调整</td>";
				if(changeMap.get("004")==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(changeMap.get("004").toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td colspan=2 class='td_test'>结算调整</td>";
				if(settleAmount==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(settleAmount.toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n"
				+ "<tr>              \r\n" + "<td colspan=2 class='td_test'>合同最新造价</td>";
				if(contractBill.getAmount()==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(contractBill.getAmount().toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td colspan=2 class='td_test'>已实现产值</td>" ;
				if(settleAmt==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(settleAmt.toString())+"</td>";
				}
				html=html
				 + "</tr>              \r\n"
				+ "<tr>              \r\n" + "<td colspan=2 class='td_test'>累计完工工程量</td>";
				if(totalworkamount==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(totalworkamount.toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n" + "<tr>              \r\n"
				+ "<td colspan=2 class='td_test'>最终结算金额</td>";
				if(finalSettAmt==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(finalSettAmt.toString())+"</td>";
				}
				html=html
				 + "</tr>              \r\n"
				+ "<tr>              \r\n" + "<td colspan=2 class='td_test'>已付款</td>";
				if(hasPayAmt==null){
					html+="<td colspan=2></td>";
				}else{
					html+="<td colspan=2>"+util.getDecimal(hasPayAmt.toString())+"</td>";
				}
				html=html
				+ "</tr>              \r\n";
		try {
			for (RowSet rs = builder.executeQuery(); rs.next(); html = html
					+ "<tr>              \r\n" + "<td colspan=2 class='td_test'>累计发票金额</td>"
					+ "<td colspan=2>" + rs.getBigDecimal(1) + "</td>"
					+ "</tr>              \r\n")
				;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		html = html + "<tr>              \r\n"
			+ "<td colspan=2 class='td_test'>完工未付款</td>";
	if (notAllPay == null) {
		html += "<td colspan=2></td>";
	} else {
		html += "<td colspan=2>" + util.getDecimal(notAllPay.toString())
				+ "</td>";
	}
				html=html
				+ "</tr>              \r\n";
		html = html + "</table>              \r\n"
				+ "  </td>              \r\n" + "</tr>              \r\n";

		
		ContractMoveHistoryCollection contractMoveHistory = null;
		contractMoveHistory = ContractMoveHistoryFactory.getRemoteInstance()
				.getContractMoveHistoryCollection(
						"where contractBillId='" + contractBill.getId() + "'");
		if (contractMoveHistory.size() > 0) {
			html = html + "  <tr>              \r\n"
					+ "<td colspan=6 width=100%>              \r\n"
					+ "<table class='tb_normal' width=100%>              \r\n"
					+ "<tr>              \r\n" + "<td colspan=4 class='td_normal_title_head'>信息一览表-总汇-交底信息</td>"
					+ "</tr>              \r\n" + "<tr>              \r\n"
					+ "<td>移交日期</td>" + "<td>责任部门</td>" + "<td>责任人</td>"
					+ "<td>备注</td>" + "</tr>              \r\n";
			for (int i = 0; i < contractMoveHistory.size(); i++) {
				html = html + "<tr>              \r\n" + "<td>"
						+ contractMoveHistory.get(i).getMoveDate() + "</td>"
						+ "<td>"
						+ contractMoveHistory.get(i).getRespDept().getName()
						+ "</td>" + "<td>"
						+ contractMoveHistory.get(i).getRespPerson().getName()
						+ "</td>" + "<td>"
						+ contractMoveHistory.get(i).getRemark() + "</td>"
						+ "</tr>              \r\n";
			}
			html = html + "</table>              \r\n"
					+ "  </td>              \r\n" + "</tr>              \r\n";
			html.replace("null", "");

		}
		CompensationBillCollection compensationBill = null;
		compensationBill = CompensationBillFactory.getRemoteInstance()
				.getCompensationBillCollection(
						"where contract.id='" + contractBill.getId() + "'");
//		if (compensationBill.size() > 0) {
			html = html + "  <tr>              \r\n"
					+ "<td colspan=6 width=100%>              \r\n"
					+ "<table class='tb_normal' width=100%  >              \r\n"
					+ "<tr>              \r\n" + "<td colspan=10 class='td_normal_title_head'>信息一览表-总汇-违约金</td>"
					+ "</tr>              \r\n" + "<tr>              \r\n"
					+ "<td>违约金编号</td>" + "<td>违约金名称</td>" + "<td>状态</td>"
					+ "<td>币别</td>" + "<td>违约类型</td>" + "<td>金额</td>"
					+ "<td>是否违约</td>" + "<td>扣款方式</td>" + "<td>制单人</td>"
					+ "<td>制单日期</td>" + "</tr>              \r\n";

			if(compensationBill.size()>0){
				for (int i = 0; i < compensationBill.size(); i++) {
					html = html + "<tr>              \r\n" + "<td>"
							+ compensationBill.get(i).getNumber() + "</td>"
							+ "<td>" + compensationBill.get(i).getName() + "</td>"
							+ "<td>" + compensationBill.get(i).getState().getName()
							+ "</td>" + "<td>"
							+ compensationBill.get(i).getCurrency() + "</td>"
							+ "<td>"
							+ compensationBill.get(i).getCompensationType()
							+ "</td>" ;
							if (compensationBill.get(i).getOriginalAmount() == null) {
								html += "<td ></td>";
							} else {
								html += "<td >" + util.getDecimal(compensationBill.get(i).getOriginalAmount().toString())
										+ "</td>";
							}
							html=html
							+ "<td>" + compensationBill.get(i).isIsCompensated()
							+ "</td>" + "<td>"
							+ compensationBill.get(i).getDeductMode().getName()
							+ "</td>" + "<td>"
							+ compensationBill.get(i).getCreator().getName()
							+ "</td>" + "<td>"
							+ compensationBill.get(i).getCreateTime() + "</td>"
							+ "</tr>              \r\n";
				}
				
			}else
			{
				html = html + "<tr><td colspan=10>没有数据</td></tr> \r\n";
			}
			
			html = html + "</table>              \r\n"
			+ "  </td>              \r\n" + "</tr>              \r\n";
//		}

		selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("creator.name"));
		selector.add(new SelectorItemInfo("auditor.name"));
		selector.add(new SelectorItemInfo("entrys.*"));
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", contractBill.getCurProject().getId()));
//		filter.getFilterItems().add(new FilterItemInfo("entrys.contractId", contractBill.getId()));
		DeductBillCollection deductBill = null;
		view = new EntityViewInfo();
		view.setSelector(selector);
		view.setFilter(filter);
		deductBill = DeductBillFactory.getRemoteInstance().getDeductBillCollection(view);
//		if (deductBill.size() > 0) {
			html = html + "  <tr>              \r\n"
					+ "<td colspan=6 width=100%>              \r\n"
					+ "<table class='tb_normal' width=100%  >              \r\n"
					+ "<tr>              \r\n" + "<td colspan=10 class='td_normal_title_head'>信息一览表-总汇-扣款单</td>"
					+ "</tr>              \r\n" + "<tr>              \r\n"
					+ "<td>扣款单编码</td>" + "<td>状态</td>" + "<td>扣款单位</td>"
					+ "<td>扣款类型</td>" + "<td>扣款金额</td>" + "<td>扣款日期</td>"
					+ "<td>审批人</td>" + "<td>审批日期</td>" + "<td>制单人</td>"
					+ "<td>制单日期</td>" + "</tr>              \r\n";

			if(deductBill.size()>0){
				for (int i = 0; i < deductBill.size(); i++) {
					String state = deductBill.get(i).getState().getAlias();
					for (int j = 0; j < deductBill.get(i).getEntrys().size(); j++) {
						//mod by ypf on 20130924 扣罚记录没有根据合同过滤，扣罚单位、扣罚类型 bug修改
						if(deductBill.get(i).getEntrys().get(j).getContractId().toString().trim().equals(contractBill.getId().toString().trim()))
						{
							String supplierName ="";
							try {
								supplierName = SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(deductBill.get(i).getEntrys().get(j).getDeductUnit().getId())).getName();
							} catch (EASBizException e) {
								e.printStackTrace();
							}
							String deductTypeName ="";
							try {
								 deductTypeName = DeductTypeFactory.getRemoteInstance().getDeductTypeInfo(new ObjectUuidPK(deductBill.get(i).getEntrys().get(j).getDeductType().getId())).getName();
							} catch (EASBizException e) {
								e.printStackTrace();
							}
							html = html
							+ "<tr>              \r\n"
							+ "<td>"
							+ deductBill.get(i).getNumber()
							+ "</td>"
							+ "<td>"
							+ state
							+ "</td>"
							+ "<td>"
							+ supplierName
							+ "</td>"
							+ "<td>"
							+ deductTypeName
							+ "</td>";
					if(deductBill.get(i).getEntrys().get(j).getDeductAmt()==null){
						html+="<td></td>";
					}else{
						html+="<td>"+util.getDecimal(deductBill.get(i).getEntrys().get(j)
								.getDeductAmt().toString())+"</td>";
					}
							html=html
							+ "<td>"
							+ deductBill.get(i).getEntrys().get(j)
									.getDeductDate() + "</td>";
					if (deductBill.get(i).getAuditor() == null)
						html = html + "<td></td>";
					else
						html = html + "<td>"
								+ deductBill.get(i).getAuditor().getName()
								+ "</td>";
					html = html + "<td>" + deductBill.get(i).getAuditTime()
							+ "</td>" + "<td>"
							+ deductBill.get(i).getCreator().getName()
							+ "</td>" + "<td>"
							+ deductBill.get(i).getCreateTime() + "</td>"
							+ "</tr>              \r\n";
						}
					}
				}
			}else
			{
				html = html + "<tr><td colspan=10>没有数据</td></tr> \r\n";
			}
			
			html = html + "</table>              \r\n"
					+ "  </td>              \r\n" + "</tr>              \r\n";
//		}

		selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("Creator.name"));
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contract.id", contractBill.getId()
						.toString()));
		view = new EntityViewInfo();
		view.setSelector(selector);
		view.setFilter(filter);
		GuerdonBillCollection guerdonBill = null;
		guerdonBill = GuerdonBillFactory.getRemoteInstance()
				.getGuerdonBillCollection(view);
//		if (deductBill.size() > 0) {
			html = html + "  <tr>              \r\n"
					+ "<td colspan=6 width=100%>              \r\n"
					+ "<table class='tb_normal' width=100%  >              \r\n"
					+ "<tr>              \r\n" + "<td colspan=10 class='td_normal_title_head'>信息一览表-总汇-奖励单</td>"
					+ "</tr>              \r\n" + "<tr>              \r\n"
					+ "<td>奖励单编号</td>" + "<td>奖励单名称</td>" + "<td>币别</td>"
					+ "<td>金额</td>" + "<td>状态</td>" + "<td>是否以奖励</td>"
					+ "<td>发放方式</td>" + "<td>奖励事项</td>" + "<td>制单人</td>"
					+ "<td>制单日期</td>" + "</tr>              \r\n";

			if(guerdonBill.size()>0){
				for (int i = 0; i < guerdonBill.size(); i++) {
					html = html + "<tr>              \r\n" + "<td>"
							+ guerdonBill.get(i).getNumber() + "</td>" + "<td>"
							+ guerdonBill.get(i).getName() + "</td>" + "<td>"
							+ guerdonBill.get(i).getCurrency().getName() + "</td>";
							if( guerdonBill.get(i).getOriginalAmount()==null){
								html+="<td colspan=2></td>";
							}else{
								html+="<td colspan=2>"+util.getDecimal( guerdonBill.get(i).getOriginalAmount().toString())+"</td>";
							}
							html=html+ "<td>" + guerdonBill.get(i).getOriginalAmount()
							+ "</td>" + "<td>"
							+ guerdonBill.get(i).getState().getName() + "</td>"
							+ "<td>" + guerdonBill.get(i).isIsGuerdoned() + "</td>"
							+ "<td>" + guerdonBill.get(i).getPutOutType().getName()
							+ "</td>" + "<td>"
							+ guerdonBill.get(i).getGuerdonThings() + "</td>"
							+ "<td>" + guerdonBill.get(i).getCreator().getName()
							+ "</td>" + "<td>" + guerdonBill.get(i).getCreateTime()
							+ "</td>" + "</tr>              \r\n";
				}
			}else
			{
				html = html + "<tr><td colspan=10>没有数据</td></tr> \r\n";
			}
			
			

			html = html + "</table>              \r\n"
					+ "  </td>              \r\n" + "</tr>              \r\n";
//		}

		html = html + "  <tr>              \r\n" 
				+ "	<td class='td_normal_title'>制单人</td>	  \r\n"
				+ "	<td colspan=2>" + promptCreator + "</td> \r\n"
				+ "	<td class='td_normal_title'>制单日期</td>	  \r\n" + "	<td colspan=2>" + createTime
				+ "</td> \r\n" + "  </tr>             \r\n"
				+ "  </tr>             \r\n" + "  <tr>              \r\n"
				+ "  <tr>              \r\n" 
				+ "	<td class='td_normal_title'>审批人</td>	  \r\n"
				+ "	<td colspan=2>" + promptAuditor + "</td> \r\n"
				+ "	<td class='td_normal_title'>审批日期</td>	  \r\n" + "	<td colspan=2>" + auditTime
				+ "</td> \r\n"
				+ "</tr> \r\n";

		html = html + "</table>            \r\n";
		html = html.replace("null", "");
		System.out.println("---------html:" + html);
		return html;
	}
}
