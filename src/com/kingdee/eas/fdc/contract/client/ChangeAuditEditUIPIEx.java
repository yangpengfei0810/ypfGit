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

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.server.SQLDataAccessFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.client.OrgInnerUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ChangeReasonInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeAuditBillCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeVisaFacadeFactory;
import com.kingdee.eas.fdc.contract.JsDataTools;
import com.kingdee.eas.fdc.contract.ModelFactory;
import com.kingdee.eas.fdc.contract.ModelInfo;
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

public class ChangeAuditEditUIPIEx extends ChangeAuditEditUI {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.client.ChangeAuditEditUIPIEx");
	
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
	private static ChangeAuditListUIPIEx listUI=null;
	
	//0-没有修改，1=有修改，add by ypf on 2015年6月28日22:10:10
	private boolean IS_CHNG_FLAG = false;
	private String ORG_ID = "";
	private String PRJ_ID = "";
	private String CHNG_TYP_ID = "";
	private String CHNG_CDE_NUM = "";
	
	private List OLD_CHNG_TYP_ID_LST = new ArrayList();
	private List OLD_HNG_CDE_NUM_LST = new ArrayList();
	
	private static String CALLTYPE_SEND ="发送";//写log用的
	
	public ChangeAuditEditUIPIEx() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("VIEW")||oprtState.equals("EDIT"))
			{
			    listUI = (ChangeAuditListUIPIEx) this.getUIContext().get("Owner");
			}
		}
		
		//mod by ypf on 2015年6月28日22:29:15
		getNumberCtrl().setEditable(false);//设置不可手工编辑
		String prjId = getUIContext().get("projectId")+"";//从listui获取传过来的参数
		String chngId = getUIContext().get("chngTypId")+"";//从listui获取传过来的参数
		String orgId = OrgInnerUtils.getCurCompany();//获取当前组织
		//新增时用设置单据编号、变更类型的值
		if(oprtState.equals("ADDNEW"))
		{
			
			if(!chngId.equals("") && !chngId.equals("所有类型")){
				IObjectPK pk= new ObjectUuidPK();
				pk.setKeyValue("id",chngId);
				ChangeTypeInfo changeTypeInfo = ChangeTypeFactory.getRemoteInstance().getChangeTypeInfo(pk);
				prmtAuditType.setData(changeTypeInfo);
			}
			
			if(!prjId.equals("") && !chngId.equals("") && !chngId.equals("所有类型") && !orgId.equals("")){
				getNumberCtrl().setText(getBillNumber(orgId,prjId,chngId));
			}
		}
		
		if(oprtState.equals("VIEW")||oprtState.equals("EDIT")){
			logger.info("-----oprtState:"+oprtState+"   chngId:"+chngId);
			if(null == chngId || chngId.equals("") || chngId.equals("null") || chngId.equals("所有类型"))
			{
				BOSUuid id = editData.getId();
				IObjectPK pk= new ObjectUuidPK();
				pk.setKeyValue("id",id);
				ChangeAuditBillInfo changeAuditBillInfo = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(pk);
				ChangeTypeInfo auditType = changeAuditBillInfo.getAuditType();
				if(auditType!=null)
				{
					chngId = auditType.getId()+"";
					logger.info("---------get chngId:"+chngId);
				}
			}
		}
		
		prmtChangeReason.addDataChangeListener(new DataChangeListener() {
	            public void dataChanged(DataChangeEvent eventObj)
	            {
	                if(prmtAuditType.getData() == null && prmtChangeReason.getData() != null)
	                {
	                    ChangeTypeInfo info = null;
	                    try
	                    {
	                        info = ChangeTypeFactory.getRemoteInstance().getChangeTypeInfo(new ObjectUuidPK(((ChangeReasonInfo)prmtChangeReason.getData()).getChangeType().getId().toString()));
	                    }
	                    catch(EASBizException e)
	                    {
	                        logger.debug(e.getMessage(), e.getCause());
	                        e.printStackTrace();
	                    }
	                    catch(BOSException e)
	                    {
	                        logger.debug(e.getMessage(), e.getCause());
	                        e.printStackTrace();
	                    }
	                    prmtAuditType.setDataNoNotify(info);
	                    
	                    //add by ypf on 2015年6月28日22:41:57
	                    CHNG_TYP_ID = info.getId().toString();
	                }
	            }

	        }
	    );
		 
		 prmtAuditType.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent arg0) {
				if(prmtAuditType.getData()!=null)
				{
					//如果有变更,将变更类型前的记录编号释放
					if(oprtState.equals("ADDNEW")){
						//先检查一下合同变更发起单中是否存在，如果是先打开查看一张已有的表单，在编辑界面新增，然后在选变更类型，这种情况就得先检查，否则会把单据里面已有的忽视掉
						try {
							boolean isExist = false;
							String sqlExist = "select fid,fnumber fnumber from t_con_changeAuditBill where FCONTROLUNITID='"
								+ ORG_ID
								+ "' and FCURPROJECTID='"
								+ PRJ_ID
								+ "' and FAUDITTYPEID='"
								+ CHNG_TYP_ID
								+ "' and fnumber = '"
								+ CHNG_CDE_NUM + "'";
							
							IRowSet rs = SQLDataAccessFactory.getRemoteInstance().getRowSet(sqlExist);
							if(rs!=null && rs.size()>0)
							{
								while(rs.next())
								{
									String fid = rs.getString("fid");
									String fnumber = rs.getString("fnumber");
									
									if(!fid.equals("") || !fnumber.equals(""))
									{
										isExist = true;
										break;
									}
								}
							}
							
							if(!isExist)
							{
								String sqlBef = "update CT_COM_CODE set cfisenable=0 where cfcde='"+CHNG_CDE_NUM+"' and CFORGID='"+ORG_ID+"' and CFCURPROJECTID='"+PRJ_ID+"' and cfchngtypid='"+CHNG_TYP_ID+"' and cfisenable=1";
								ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sqlBef);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					try {
						ChangeTypeInfo chngInfo = (ChangeTypeInfo) prmtAuditType.getData();
						CHNG_TYP_ID = chngInfo.getId().toString();//根据更新的变更类型,重新设置单据编号
						String billNumber = getBillNumber(ORG_ID,PRJ_ID,CHNG_TYP_ID);
						getNumberCtrl().setText(billNumber);
						CHNG_CDE_NUM = billNumber;
						
						//如果有变更,讲变更类型后的记录编号锁定
						String sql = "update CT_COM_CODE set cfisenable=1 where cfcde='"+CHNG_CDE_NUM+"' and CFORGID='"+ORG_ID+"' and CFCURPROJECTID='"+PRJ_ID+"' and cfchngtypid='"+CHNG_TYP_ID+"' and cfisenable=0";
						ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sql);
					} catch (Exception e) {
						e.printStackTrace();
					} 
					
					//在修改单据的变更类型后，临时存放变更类型Id，用于后续退出销毁窗体时进行释放编码表中的老编号
					if(editData.getAuditType()!=null){
						System.out.println("---OLD_CHNG_TYP_ID_LST:"+OLD_CHNG_TYP_ID_LST.size()+"  OLD_HNG_CDE_NUM_LST:"+OLD_HNG_CDE_NUM_LST.size());
						if(!OLD_CHNG_TYP_ID_LST.contains(editData.getAuditType().getId())){
							OLD_CHNG_TYP_ID_LST.add(editData.getAuditType().getId());
							OLD_HNG_CDE_NUM_LST.add(editData.getNumber());
						}
					}
					//当前的表单上修改的也存起来
					if(OLD_CHNG_TYP_ID_LST.contains(CHNG_TYP_ID)){
						OLD_CHNG_TYP_ID_LST.add(CHNG_TYP_ID);
						OLD_HNG_CDE_NUM_LST.add(CHNG_CDE_NUM);
					}
				}
			}
		  }
		 );
		
		ORG_ID = orgId;
		PRJ_ID = prjId;
		CHNG_TYP_ID = chngId;
		CHNG_CDE_NUM = getNumberCtrl().getText();
	}
	
	public void rebackRefreshListUI(ActionEvent e)
	{
		//mod by ypf on 20121113 刷新listui,其他页面不刷新
		if(this.getUIContext().get("isRefresh")!=null && this.getUIContext().get("isRefresh").equals("true"))
		{
			if(oprtState.equals("ADDNEW")||oprtState.equals("VIEW")||oprtState.equals("EDIT"))
			{
			   listUI = (ChangeAuditListUIPIEx) this.getUIContext().get("Owner");
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
			boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ChangeAuditBill WHERE fid='"+fid+"'");
			if(!isEnableSubmit)
			{
				MsgBox.showInfo("单据已经提交OA走审批流程，不能删除");
				SysUtil.abort();
			}
	    }
		super.actionRemove_actionPerformed(e);
		
		String sql = "update CT_COM_CODE set cfisenable=0 where cfcde='"+CHNG_CDE_NUM+"' and CFORGID='"+ORG_ID+"' and CFCURPROJECTID='"+PRJ_ID+"' and cfchngtypid='"+CHNG_TYP_ID+"' and cfisenable=1";
		try {
			ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sql);
		} catch (Exception e1) {
			logger.error("--exception client--sql:"+sql);
			e1.printStackTrace();
		}
	}
	
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAddNew_actionPerformed(e);
    	txtNumber.setEditable(false);
    }
    
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
	/*	util =new FDCUtil();
		String html11 = getHTML("dQeN+SNwSTKhFs/vO4dt1nARYRc=");
		PROJECT_INFO=getPrjInfo("dQeN+SNwSTKhFs/vO4dt1nARYRc=");//初始化项目工程对象
		getJsonForm("dQeN+SNwSTKhFs/vO4dt1nARYRc=");*/
		
		String id = (!editData.getId().equals("") && editData.getId() !=null)?editData.getId().toString():"";
    	if(oprtState.equals("VIEW"))
    	{
    		fid = id;
    	}
    	
		//检查当前单据是否是"已提交"或者"已审批"并且是走oa审批时，不让重复提交、删除、编辑
		 if(!fid.equals("")){
			boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ChangeAuditBill WHERE fid='"+fid+"'");
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
			ChangeAuditBillCollection col = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillCollection(view);
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
			ChangeAuditBillCollection col = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillCollection(view);
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
			ChangeAuditBillCollection col = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillCollection(view);
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
		  
		  ChangeAuditBillInfo contractInfo=null;
		try {
			contractInfo = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(editData.getId()));
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
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
					e1.printStackTrace();
				}
			}
			
			//add by ypf on 20150805
			resetContractChangeBillSts(true);
		}
	  
	  // modify by ll 2012-10-11 单据已走OA审批流程，但单据在查看下可以反审批
		public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
			 ChangeAuditBillInfo contractInfo=null;
				try {
					contractInfo = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(editData.getId()));
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e1) {
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
			
			//add by ypf on 20150723  合同变更发起单审批后，变更签证单状态设置为保存状态
			String id = editData.getId()+"";
			if(id != null && !id.equals("")){
				ContractChangeBillCollection chgBillCol = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(" where changeAudit='"+id+"'");
				if(chgBillCol!=null && chgBillCol.size()>0)
				{
					for (int i = 0; i < chgBillCol.size(); i++) {
						ContractChangeBillInfo contractChangeBillInfo = chgBillCol.get(i);
						String sql = "update t_con_contractchangebill set fstate='1SAVED' where fchangeauditid='"+id+"'";
						logger.info("---合同变更发起单审批后，变更签证单状态设置为保存状态:"+sql);
						ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sql);
					}
				}
				
				//add by ypf on 20150723 合同变更发起单审批时，将测试金额更新到预估金额字段
				ContractChangeVisaFacadeFactory.getRemoteInstance().unSaveCostAmountByChngAuditBillID(id);
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
		
		//add by ypf on 2015年6月28日22:16:01
		IS_CHNG_FLAG = true;
		
		//修改单据时，如果修改了变更类型，则在保存(提交)完单据后要释放掉原先的编号
		freeOldNumber();
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
		//add by ypf on 20150709 ,校验下发单位分录不能为空
		checkSuppEntry();
		
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
			
			//add by ypf on 2015年6月28日22:16:01
			IS_CHNG_FLAG = true;
				
			//初始化fid
			 fid = editData.getId().toString();
			
			//保存以后才能娶到单据状态
			 billState = getBillState(fid);
		}
		else if(oprtState.equals("EDIT"))
		{
			//add by ypf on 20141118
			 actionSave_actionPerformed(e);
			
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
		
		boolean isEnableSubmit = FDCUtil.isEnableSubmit("SELECT fstate state,cfisoaaudit isOAAudit FROM T_CON_ChangeAuditBill WHERE fid='"+fid+"'");
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
			String sql = "SELECT t1.fid billID,t3.fid attachmentID FROM T_CON_ChangeAuditBill t1 left join T_BAS_BoAttchAsso t2 on t1.fid=t2.fboid left join  T_BAS_Attachment t3 on t2.fattachmentid =t3.fid WHERE t1.fid='"+fid+"'";
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
		    
		  //工程项目+变更类型+单据名称
			String subject = prmtCurProject.getText()+prmtAuditType.getText()+txtName.getText();//mod by ypf on 20121106
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
			String keyForModel = "EAS8("+prjMappingName+")";
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
						logInfo.setSourceBillType("EAS8");
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
						logInfo.setSourceBillType("EAS8");
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
							logInfo.setSourceBillType("EAS8");
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
				logInfo.setSourceBillType("EAS8");
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
				if(!updateChangeAuditBill(fid,true,templateId,oaId)){
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
							
							//add by ypf on 20140624  提交成功后更新变更签证单的3个字段值
							if(!fid.equals("") && !templateId.equals("") && !oaId.equals(""))
							{
								//mod ypf on 20150723 合同变更发起单审批后，变更签证确认单不要关联oa
//								updateContractChangeBill(fid,templateId,oaId);
							}else
							{
								WSLogInfo logInfo = new WSLogInfo();
								logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
								logInfo.setSourceBillID(editData.getId()+"");
								logInfo.setState("成功");
								logInfo.setLogTitle("流程发起成功,但是变更签证单的3个字段没有反写");
								logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
								logInfo.setUrl(url);
								logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
								logInfo.setCallType(CALLTYPE_SEND);
								logInfo.setSourceBillType("EAS8");
								WSLogFactory.getRemoteInstance().addnew(logInfo);
							}
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("成功");
							logInfo.setLogTitle("流程发起成功");
							logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
							logInfo.setUrl(url);
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS8");
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
						logInfo.setSourceBillType("EAS8");
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
				logInfo.setSourceBillType("EAS8");
				WSLogFactory.getRemoteInstance().addnew(logInfo);
				
				SysUtil.abort();
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", fid));
			view.setFilter(filter);
			ChangeAuditBillCollection col;
			try {
				col = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillCollection(view);
				ChangeAuditBillInfo info = new ChangeAuditBillInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//更新附件内容
					   ChangeAuditBillFactory.getRemoteInstance().updatePartial(info, selector);
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
		
		//add by ypf on 20150805
		resetContractChangeBillSts(false);
	}
	
	//add by ypf on 2015年8月5日   合同变更发起单提交/审批后，变更签证单状态设置为保存状态
	public void resetContractChangeBillSts(boolean isAudit){
		try {
			//add by ypf on 20150723  合同变更发起单审批后，变更签证单状态设置为保存状态
			String id = editData.getId()+"";
			if(id != null && !id.equals("")){
				
				//mod by ypf on 20151007  处理提交后没有改成保存状态
				String sql1 = "select * from t_con_contractchangebill where fchangeauditid='+id+'";
				IRowSet rs = SQLDataAccessFactory.getRemoteInstance().getRowSet(sql1);
				if(rs!=null && rs.size()>0)
				{
					String sql = "update t_con_contractchangebill set fstate='1SAVED' where fchangeauditid='"+id+"'";
					logger.info("---合同变更发起单审批后，变更签证单状态设置为保存状态:"+sql);
					ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sql);
				}
				
				/*ContractChangeBillCollection chgBillCol = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(" where changeAudit='"+id+"'");
				if(chgBillCol!=null && chgBillCol.size()>0)
				{
					for (int i = 0; i < chgBillCol.size(); i++) {
						ContractChangeBillInfo contractChangeBillInfo = chgBillCol.get(i);
						String sql = "update t_con_contractchangebill set fstate='1SAVED' where fchangeauditid='"+id+"'";
						logger.info("---合同变更发起单审批后，变更签证单状态设置为保存状态:"+sql);
						ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sql);
					}
				}*/
				
				//add by ypf on 20150723 合同变更发起单审批时，将测试金额更新到预估金额字段
				if(isAudit){
					ContractChangeVisaFacadeFactory.getRemoteInstance().saveCostAmountByChngAuditBillID(id);
				}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	//add by ypf on 20150709 ,校验下发单位分录不能为空
	public void checkSuppEntry(){
		if(getSecondTable().getExpandedRowCount() <= 0)
        {
            if(isOfferAndConstrReq)
                return;
            MsgBox.showWarning(this, ChangeAuditUtil.getRes("suppNoNull"));
            SysUtil.abort();
        }
	}
	
	public void actionSubmit_no_attachment(ActionEvent e) {
		//add by ypf on 20150709 ,校验下发单位分录不能为空
		checkSuppEntry();
		
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
	    String subject = prmtCurProject.getText()+prmtAuditType.getText()+txtName.getText();//mod by ypf on 20140514
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
			String keyForModel = "EAS8("+prjMappingName+")";
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
						logInfo.setSourceBillType("EAS8");
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
						logInfo.setSourceBillType("EAS8");
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
							logInfo.setSourceBillType("EAS8");
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
				logInfo.setSourceBillType("EAS8");
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
				if(!updateChangeAuditBill(fid,true,templateId,oaId)){
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
							
							//add by ypf on 20140624  提交成功后更新变更签证单的3个字段值
							if(!fid.equals("") && !templateId.equals("") && !oaId.equals(""))
							{
								//mod ypf on 20150723 合同变更发起单审批后，变更签证确认单不要关联oa
//								updateContractChangeBill(fid,templateId,oaId);
							}else
							{
								WSLogInfo logInfo = new WSLogInfo();
								logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
								logInfo.setSourceBillID(editData.getId()+"");
								logInfo.setState("成功");
								logInfo.setLogTitle("流程发起成功,但是变更签证单的3个字段没有反写");
								logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
								logInfo.setUrl(url);
								logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
								logInfo.setCallType(CALLTYPE_SEND);
								logInfo.setSourceBillType("EAS8");
								WSLogFactory.getRemoteInstance().addnew(logInfo);
							}
							
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(editData.getId()+"");
							logInfo.setState("成功");
							logInfo.setLogTitle("流程发起成功");
							logInfo.setLogDetail("http://oa.avicred.com/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+oaId);
							logInfo.setUrl(url);
							logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							logInfo.setCallType(CALLTYPE_SEND);
							logInfo.setSourceBillType("EAS8");
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
						logInfo.setSourceBillType("EAS8");
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
				logInfo.setSourceBillType("EAS8");
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
			ChangeAuditBillCollection col;
			try {
				col = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillCollection(view);
				ChangeAuditBillInfo info = new ChangeAuditBillInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(0);
				  info.setIsOAAudit(false);
				  SelectorItemCollection selector = new SelectorItemCollection();
				  selector.add("isOAAudit");
				  try {
						//更新附件内容
					   ChangeAuditBillFactory.getRemoteInstance().updatePartial(info, selector);
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
		
		//add by ypf on 20150805
		resetContractChangeBillSts(false);
	}
	
	//销毁ui，不检查
	public boolean destroyWindow() {
		System.out.println("---------------销毁ui---不检查");

		//add by ypf on 2015年6月28日22:08:52，关闭窗口时，如果没有保存，则释放占用的编号
		if(!IS_CHNG_FLAG && oprtState.equals("ADDNEW")){
			if(!CHNG_CDE_NUM.equals("") && !ORG_ID.equals("") && !PRJ_ID.equals("") && !CHNG_TYP_ID.equals("")){
				String sql = "update CT_COM_CODE set cfisenable=0 where cfcde='"+CHNG_CDE_NUM+"' and CFORGID='"+ORG_ID+"' and CFCURPROJECTID='"+PRJ_ID+"' and cfchngtypid='"+CHNG_TYP_ID+"' and cfisenable=1";
				logger.info("--destroy-sql:"+sql);
				try {
					ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sql);
				} catch (BOSException e) {
					logger.error("--exception client--sql:"+sql);
					e.printStackTrace();
				}
			}
		}
		
		//修改单据时，如果修改了变更类型，则在保存(提交)完单据后要释放掉原先的编号
		freeOldNumber();
		
		return true;
	}
	
	//修改单据时，如果修改了变更类型，则在保存(提交)完单据后要释放掉原先的编号
	public void freeOldNumber(){
		if(prmtAuditType.getData() != null){
			ChangeTypeInfo chngInfo = (ChangeTypeInfo) prmtAuditType.getData();
			String newId = chngInfo.getId().toString();
			if(OLD_CHNG_TYP_ID_LST != null && OLD_HNG_CDE_NUM_LST != null){
				for (int i = 0; i < OLD_CHNG_TYP_ID_LST.size(); i++) {
					//剔除当前的变更类型ID，其他的变更类型在编码表中都释放
					if(!ORG_ID.equals("") && !PRJ_ID.equals("") && !newId.equals(OLD_CHNG_TYP_ID_LST.get(i))){
						String sql = "update CT_COM_CODE set cfisenable=0 where cfcde='"+OLD_HNG_CDE_NUM_LST.get(i)+"' and CFORGID='"+ORG_ID+"' and CFCURPROJECTID='"+PRJ_ID+"' and cfchngtypid='"+OLD_CHNG_TYP_ID_LST.get(i)+"' and cfisenable=1";
						logger.info("--destroy--释放修改前的编号-sql:"+sql);
						try {
							ContractChangeVisaFacadeFactory.getRemoteInstance().executeUpdate(sql);
						} catch (BOSException e) {
							logger.error("--exception client--sql:"+sql);
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	private String getOAID(String billID)
	{
		System.out.println("---getOAID---billID:"+billID);
		String oaID="";
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", billID));
		view.setFilter(filter);
		
		ChangeAuditBillInfo info = null;
		try {
			ChangeAuditBillCollection col = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillCollection(view);
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
	 * 修改时间：2014-01-08 
	 * ***************************************************
	 */
	private String getJsonForm(String fid)
	{
		String json = "";
		String longNumber= PROJECT_INFO.getLongNumber();//项目长编码
		String longName= PROJECT_INFO.getDisplayName();//项目长名称
		String changeAuditNumber = (txtNumber.getText()==null)?"":txtNumber.getText();// 单据编码,变更审批单编号
		String changeAuditName = (txtName.getText()==null)?"":txtName.getText();// 单据名称,变更审批单名称
		String constructionUnit = (prmtConstrUnit.getText()==null)?"":prmtConstrUnit.getText();// 施工单位
		
		//变更原因
		String changeReason = "";
		if(kdtEntrys.getRowCount()>0)
		{
			for (int m = 0; m < kdtEntrys.getRowCount(); m++) {
				String changeContentNumber = kdtEntrys.getCell(m, "number").getValue()!=null?kdtEntrys.getCell(m, "number").getValue().toString():"";
				String changeContent = kdtEntrys.getCell(m, "changeContent").getValue()!=null?kdtEntrys.getCell(m, "changeContent").getValue().toString():"";
				changeReason += changeContentNumber + "、 " + changeContent +";";
			}
		}
		
		Map map = new HashMap();
		map.put("prj_long_number", longNumber);
		map.put("prj_long_name", longName);
		map.put("bill_number", changeAuditNumber);
		map.put("bill_name", changeAuditName);
		map.put("construction_unit", constructionUnit);
		map.put("change_reason", changeReason);
		
		//------------------登记下发单位页签
		String json_entry_string = "";
		ChangeSupplierEntryCollection changeSupplierEntryCollection = null;
		if(fid!=null && !fid.equals("")){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent", fid));
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("contractBill.name");
			sic.add("contractBill.number");
			sic.add("contractBill.amount");
			sic.add("contractBill.partB");
			sic.add("currency.name");
			sic.add("entrys.content.changeContent");
			sic.add("mainSupp.*");
			sic.add("reckonor.*");
			sic.add("dutySupplier.*");
			sic.add("copySupp.*");
			sic.add("dutyOrg.*");
			view.setSelector(sic);
			view.setFilter(filter);
			try {
				changeSupplierEntryCollection = ChangeSupplierEntryFactory.getRemoteInstance().getChangeSupplierEntryCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < changeSupplierEntryCollection.size(); i++) {
				ChangeSupplierEntryInfo changeSupplierEntryInfo = changeSupplierEntryCollection.get(i);
				if(changeSupplierEntryInfo!=null){
					if(changeSupplierEntryInfo.getContractBill()==null)
					{
						//mod by ypf on 20141118 走流程审批前必须保证分录中的必填字段有值
						MsgBox.showWarning("登记下发单位分录中的【合同编号】提交OA流程前不能为空！");
						SysUtil.abort();
					}
					String contractNumber = changeSupplierEntryInfo.getContractBill().getNumber();//合同编码
					String contractName = changeSupplierEntryInfo.getContractBill().getName();//合同名称
					String partyB = (changeSupplierEntryInfo.getContractBill().getPartB()==null)?"":changeSupplierEntryInfo.getContractBill().getPartB().getName();//合同乙方
					String contractMoney = changeSupplierEntryInfo.getContractBill().getAmount() + "";//合同金额
					String costAmount = changeSupplierEntryInfo.getCostAmount() + "";//测试金额原币
					
					map.put("contract_number_"+(i+1), contractNumber);
					map.put("contract_name_"+(i+1), contractName);
					map.put("partyB_"+(i+1), partyB);
					map.put("contract_money_"+(i+1), util.cutComma(contractMoney));
					map.put("EASBak_"+(i+1), util.cutComma(costAmount));
				}
			}
		}
		
		JsDataTools js = new JsDataTools();
		json = js.createJsData(map);
		System.out.println("----变更审批单据'" + changeAuditName + "'-----json:" + json);
		
		return json;
	}

	//是否走OA审批
	private int isOAAudit()
	{
		return MsgBox.showConfirm3("请选择是否走OA审批流程？");
	}
	
	/**
	 * **************************************************
	 * 方法说明: updateChangeAuditBill(在走提交后，更新‘是否走oa审批’字段)  
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
	public boolean updateChangeAuditBill(String id,boolean isOAAudit,String billTempletID,String oaBillID)
	{
		System.out.println("------发起流程成功后更新合同信息："+"  eas单据id:"+id +"  是否走oa审批："+isOAAudit+"  模板id："+billTempletID +"  oa单据id："+oaBillID);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		ChangeAuditBillCollection col;
		try {
			col = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillCollection(view);
			for (int j = 0; j < col.size(); j++) {
				ChangeAuditBillInfo info = new ChangeAuditBillInfo();
				//取到需要更新附件的对象
				if(col != null && col.size() > 0)
				{
				  info = col.get(j);
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
						//更新变更审批单
					   ChangeAuditBillFactory.getRemoteInstance().updatePartial(info,selector);
					   
					   //add by ypf on 201401023  设置editData 中新加字段的值，否则在后面的supper.submit 方法执行后就会覆盖掉
					   ChangeAuditBillInfo auditBillInfo = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(info.getId().toString()));
					   boolean audit = auditBillInfo.isIsOAAudit();
					   String templetID = auditBillInfo.getBillTempletID();
					   String billID = auditBillInfo.getOABillID();
					   
					   editData.setIsOAAudit(audit);
					   editData.setBillTempletID(templetID);
					   editData.setOABillID(billID);
					   
					   System.out.println("_----------------"+audit+"==="+templetID+"===="+billID);
					   
					   //放在提交后再执行才有用  mod by ypf on 20140624
					   /*//获取到合同变更审批单的相关下发单位 并更新变更签证单的3个字段  add by ypf on 20140120
					   ChangeSupplierEntryCollection changeSupplierEntry = ChangeSupplierEntryFactory.getRemoteInstance().getChangeSupplierEntryCollection(" where parent='"+info.getId()+"'");
					   for (int i = 0; i < changeSupplierEntry.size(); i++) {
						    ChangeSupplierEntryInfo changeSupplierEntryInfo = changeSupplierEntry.get(i);
						    
						    //mod by ypf on 20140305 增加判断条件，如果新合同没有发生变更签证的情况 这里就会跑异常
						    if(changeSupplierEntryInfo.getContractChange()!=null){
								ContractChangeBillInfo contractChange = changeSupplierEntryInfo.getContractChange();
								contractChange.setIsOAAudit(true);
								contractChange.setBillTempletID(billTempletID);
								contractChange.setOABillID(oaBillID);
								
								SelectorItemCollection selector1 = new SelectorItemCollection();
								selector1.add("isOAAudit");
								selector1.add("billTempletID");
								selector1.add("OABillID");
								
								ContractChangeBillFactory.getRemoteInstance().updatePartial(contractChange, selector1);
						    }
						}*/
					   
					   return true;
				   } catch (Exception e) {
					   e.printStackTrace();
					   return false;
				  } 
				}else
				{
					return false;
				}
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			return false;
		}
		return false;
	}
	
	//add by ypf on 20140624  变更审批单提交后，要更新变更签证单的3个字段
	public void updateContractChangeBill(String changAuditBillId,String billTempletID,String oabillId) throws BOSException
	{
		//获取到合同变更审批单的相关下发单位 并更新变更签证单的3个字段  add by ypf on 20140120
		try {
			ChangeSupplierEntryCollection changeSupplierEntry = ChangeSupplierEntryFactory.getRemoteInstance().getChangeSupplierEntryCollection(" where parent='"+changAuditBillId+"'");
			   for (int i = 0; i < changeSupplierEntry.size(); i++) {
				    ChangeSupplierEntryInfo changeSupplierEntryInfo = changeSupplierEntry.get(i);
				    
				    //mod by ypf on 20140305 增加判断条件，如果新合同没有发生变更签证的情况 这里就会跑异常
				    if(changeSupplierEntryInfo.getContractChange()!=null){
						ContractChangeBillInfo contractChange = changeSupplierEntryInfo.getContractChange();
						contractChange.setIsOAAudit(true);
						contractChange.setBillTempletID(billTempletID);
						contractChange.setOABillID(oabillId);
						
						SelectorItemCollection selector1 = new SelectorItemCollection();
						selector1.add("isOAAudit");
						selector1.add("billTempletID");
						selector1.add("OABillID");
						
						ContractChangeBillFactory.getRemoteInstance().updatePartial(contractChange, selector1);
				    }
				}
		} catch (Exception e) {
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(editData.getId()+"");
			logInfo.setState("异常");
			logInfo.setLogTitle("对变更签证单的3个字段进行反写时出现异常");
			logInfo.setLogDetail(e.toString());
			logInfo.setUrl("oabillId:"+oabillId +"  fid:"+changAuditBillId);
			logInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
			logInfo.setCallType(CALLTYPE_SEND);
			logInfo.setSourceBillType("EAS8");
			WSLogFactory.getRemoteInstance().addnew(logInfo);
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
		//-----------表头基本信息------------
		String org = "";// 组织
		String curProject="";//项目名称
		String number="";//单据编号
		String billName="";//单据名称
		String changeReason="";//变更原因
		String bookedDate="";//业务日期
		String changeType="";//变更类型
		String changeTitle="";//变更主题
		String period="";//变更期间
		String jobType="";//承包类型
		String urgentDegree="";//紧急程度
		String submitDepartment="";//提出部门
		String specialType="";//专业类型
		String designDepartment="";//设计部门
		String submitUnitOrg="";//提出单位
		String workUnitOrg="";//施工单位
		String submitPartA="";//提出方
		String workPart="";//施工部位
		String reasonRemark="";//原因说明
		String attachmentList="";//附件列表
		String isBigChage="";//是否重大变更
		String billState="";//单据状态
		String attachmentrchiveDraft="";//归档稿
		String creator="";//制单人
		String dateCreateTime="";//制单日期
		String auditor="";//审批人
		String auditTime="";//审批日期
		
		org=(txtOrg.getText()==null)?"":txtOrg.getText();
		curProject=(prmtCurProject.getText()==null)?"":prmtCurProject.getText();
		
		number=(txtNumber.getText()==null)?"":txtNumber.getText();
		billName=(txtName.getText()==null)?"":txtName.getText();
		changeReason=(prmtChangeReason.getText()==null)?"":prmtChangeReason.getText();
		
		bookedDate=(pkbookedDate.getText()==null)?"":pkbookedDate.getText();
		changeType=(prmtAuditType.getText()==null)?"":prmtAuditType.getText();
		changeTitle=(txtChangeSubject.getText()==null)?"":txtChangeSubject.getText();
		
		period=(cbPeriod.getText()==null)?"":cbPeriod.getText();
		jobType=(prmtJobType.getText()==null)?"":prmtJobType.getText();
		urgentDegree=(comboUrgentDegree.getSelectedItem()==null)?"":comboUrgentDegree.getSelectedItem()+"";
		
		submitDepartment=(prmtConductDept.getText()==null)?"":prmtConductDept.getText();
		specialType=(prmtSpecialtyType.getText()==null)?"":prmtSpecialtyType.getText();
		designDepartment=(prmtDesignUnit.getText()==null)?"":prmtDesignUnit.getText();
		
		submitPartA=(prmtConductUnit.getText()==null)?"":prmtConductUnit.getText();
		workPart=(txtConstrSite.getText()==null)?"":txtConstrSite.getText();
		reasonRemark=(txtReaDesc.getSelectedItem()==null)?"":txtReaDesc.getSelectedItem()+"";
		
		attachmentList=(cmbAttachment.getSelectedItem()==null)?"":cmbAttachment.getSelectedItem()+"";
		isBigChage=(chkIsImportChange.getText()==null)?"":chkIsImportChange.getText();
		billState=(comboChangeState.getSelectedItem()==null)?"":comboChangeState.getSelectedItem()+"";
		
		attachmentrchiveDraft=(cmbAttenTwo.getSelectedItem()==null)?"":cmbAttenTwo.getSelectedItem()+"";
		creator=(prmtCreator.getText()==null)?"":prmtCreator.getText();
		dateCreateTime=(pkCreateTime.getText()==null)?"":pkCreateTime.getText();
		auditor=(prmtAuditor.getText()==null)?"":prmtAuditor.getText();
		auditTime=(pkAuditTime.getText()==null)?"":pkAuditTime.getText();
		
		html =  
		"\r\n<table class='tb_normal' width=100%>		                                               \r\n"+
		"	 <tr><td colspan=6  class='td_normal_title_head' width=15%>变更审批单表头</td></tr>  \r\n"+
		"	 <tr>                                                                                \r\n"+                                     
		"		<td class='td_normal_title'>组织</td>	                                             \r\n"+             
		"		<td  colspan=2>"+org+"</td>                                                       \r\n"+      
		"		<td class='td_normal_title'>项目名称</td>	                                         \r\n"+                              
		"		<td  colspan=2>"+curProject+"</td>                              \r\n"+                      
		"	 </tr>                                                                               \r\n"+                   
		"	 <tr>                                                                                \r\n"+                     
		"		<td width=16% class='td_normal_title'>单据编号</td>	                               \r\n"+                                   
		"		<td width=16%>"+number+"</td>                                                        \r\n"+                                  
		"		<td width=16% class='td_normal_title'>单据名称</td>	                               \r\n"+
		"		<td width=16%>"+billName+"</td>                               \r\n"+                
		"		<td width=16% class='td_normal_title'>变更原因</td>	                               \r\n"+
		"		<td width=16% >"+changeReason+"</td>                                                 \r\n"+
		"	 </tr>                                                                               \r\n"+
		"	 <tr>                                                                                \r\n"+
		"		<td width=16% class='td_normal_title'>业务日期</td>	                               \r\n"+
		"		<td width=16%>"+bookedDate+"</td>                                                        \r\n"+
		"		<td width=16% class='td_normal_title'>变更类型</td>	                               \r\n"+
		"		<td width=16%>"+changeType+"</td>                               \r\n"+
		"		<td width=16% class='td_normal_title'>变更主题</td>	                               \r\n"+
		"		<td width=16% >"+changeTitle+"</td>                                                 \r\n"+
		"	 </tr>                                                                               \r\n"+
		"	 <tr>                                                                                \r\n"+
		"		<td width=16% class='td_normal_title'>变更期间</td>	                               \r\n"+
		"		<td width=16%>"+period+"</td>                                                        \r\n"+
		"		<td width=16% class='td_normal_title'>承包类型</td>	                               \r\n"+
		"		<td width=16%>"+jobType+"</td>                               \r\n"+
		"		<td width=16% class='td_normal_title'>紧急程度</td>	                               \r\n"+
		"		<td width=16% >"+urgentDegree+"</td>                                                 \r\n"+
		"	 </tr>                                                                               \r\n"+
		"	 <tr>                                                                                \r\n"+
		"		<td width=16% class='td_normal_title'>提出部门</td>	                               \r\n"+
		"		<td width=16%>"+submitDepartment+"</td>                                                        \r\n"+
		"		<td width=16% class='td_normal_title'>专业类型</td>	                               \r\n"+
		"		<td width=16%>"+specialType+"</td>                               \r\n"+
		"		<td width=16% class='td_normal_title'>设计部门</td>	                               \r\n"+
		"		<td width=16% >"+designDepartment+"</td>                                                 \r\n"+
		"	 </tr>                                                                               \r\n"+
		"	 <tr>                                                                                \r\n"+
		"		<td width=16% class='td_normal_title'>提出单位</td>	                               \r\n"+
		"		<td width=16%>"+submitUnitOrg+"</td>                                                        \r\n"+
		"		<td width=16% class='td_normal_title'>施工单位</td>	                               \r\n"+
		"		<td width=16%>"+workUnitOrg+"</td>                               \r\n"+
		"		<td width=16% class='td_normal_title'>提出方</td>	                                 \r\n"+
		"		<td width=16% >"+submitPartA+"</td>                                                 \r\n"+
		"	 </tr>                                                                               \r\n"+
		"	 <tr>                                                                                \r\n"+
		"		<td width=16% class='td_normal_title'>施工部位</td>	                               \r\n"+
		"		<td width=16%>"+workPart+"</td>                                                        \r\n"+
		"		<td width=16% class='td_normal_title'>是否重大变更</td>	                           \r\n"+
		"		<td width=16%>"+isBigChage+"</td>                               \r\n"+
		"		<td width=16% class='td_normal_title'>单据状态</td>	                               \r\n"+
		"		<td width=16% >"+billState+"</td>                                                 \r\n"+
		"	 </tr>	                                                                             \r\n"+
		"	 <tr>                                                                                \r\n"+
		"	 	<td class='td_normal_title'>附件列表</td>	                                         \r\n"+
		"		<td  colspan=2>"+attachmentList+"</td>                                                       \r\n"+
		"		<td class='td_normal_title'>归档稿</td>	                                           \r\n"+
		"		<td  colspan=2>"+attachmentrchiveDraft+"</td>                                                       \r\n"+
		"	 </tr>                                                                               \r\n"+
		"	 <tr>                                                                                \r\n"+
		"		<td  class='td_normal_title'>原因说明</td>	                                       \r\n"+
		"		<td  colspan=5>"+reasonRemark+"</td>                              \r\n"+
		"	 </tr>                                                                               \r\n";
	
		//-----------变更内容页签信息------------
		BOSUuid id = editData.getId();
		ChangeAuditEntryCollection changeAuditEntryCollection = null;
		String changeContentString = "";//变更内容页签分录html
		if(id!=null && !id.equals("")){
			changeAuditEntryCollection = ChangeAuditEntryFactory.getRemoteInstance().getChangeAuditEntryCollection(" where parent='"+id+"'");
			for (int i = 0; i < changeAuditEntryCollection.size(); i++) {
				ChangeAuditEntryInfo changeAuditEntryInfo = changeAuditEntryCollection.get(i);
				changeContentString +=
					"			<tr>                                                                  \r\n"+
					"				<td >"+changeAuditEntryInfo.getNumber()+"</td>                                                      \r\n"+
					"				<td >"+changeAuditEntryInfo.getChangeContent()+"</td>                                    \r\n"+
					"				<td >"+(changeAuditEntryInfo.isIsBack()==true?"是":"否")+"</td>                                                        \r\n"+
					"			</tr>                                                                 \r\n";
			}
			
		}
		
		html += 
		" <tr>                                                                      \r\n"+         
		"	 <td colspan=6  width=100%>  			                                        \r\n"+                   
		"		<table class='tb_normal' width=100% >                                   \r\n"+                                           
		"			<tr><td colspan=6 class='td_normal_title_head'>变更内容信息</td></tr> \r\n"+                   			  
		"			<tr>                                                                  \r\n"+
		"				<td class='td_normal_title'>变更内容编码</td>                       \r\n"+
		"				<td class='td_normal_title'>变更内容</td>                           \r\n"+
		"				<td class='td_normal_title'>是否返工</td>                           \r\n"+
		"			</tr>                                                                 \r\n"+
		"			                                                                      \r\n"+
		changeContentString+
		"			                                                                      \r\n"+
		"		</table>                                                                \r\n"+
		" </td></tr>                                                                     \r\n";
		
		//------------------登记下发单位页签
		BigDecimal sumCostAmount = new BigDecimal(0);//测算总成本
		BigDecimal sumDutyAmount = new BigDecimal(0);//责任单位承担费用
		
		ChangeSupplierEntryCollection changeSupplierEntryCollection = null;
		String unitString = "";//登记下发单位页签分录html
		if(id!=null && !id.equals("")){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent", id));
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("contractBill.name");
			sic.add("contractBill.number");
			sic.add("currency.name");
			sic.add("entrys.content.changeContent");
			sic.add("mainSupp.*");
			sic.add("reckonor.*");
			sic.add("dutySupplier.*");
			sic.add("copySupp.*");
			sic.add("dutyOrg.*");
			view.setSelector(sic);
			view.setFilter(filter);
			changeSupplierEntryCollection = ChangeSupplierEntryFactory.getRemoteInstance().getChangeSupplierEntryCollection(view);
			
			for (int i = 0; i < changeSupplierEntryCollection.size(); i++) {
				ChangeSupplierEntryInfo changeSupplierEntryInfo = changeSupplierEntryCollection.get(i);
				
				//mod by ypf on 20141118 走流程审批前必须保证分录中的必填字段有值
				if(changeSupplierEntryInfo.getContractBill().getNumber()==null)
				{
					MsgBox.showWarning("登记下发单位分录中的【合同编号】提交OA流程前不能为空！");
					SysUtil.abort();
				}
				
				if(changeSupplierEntryInfo.getEntrys()==null || changeSupplierEntryInfo.getEntrys().get(0)==null)
				{
					MsgBox.showWarning("登记下发单位分录中的【执行内容】提交OA流程前不能为空！");
					SysUtil.abort();
				}
				
				if(changeSupplierEntryInfo.getOriCostAmount()==null)
				{
					MsgBox.showWarning("登记下发单位分录中的【测算金额原币】提交OA流程前不能为空！");
					SysUtil.abort();
				}
				if(changeSupplierEntryInfo.getCostAmount()==null)
				{
					MsgBox.showWarning("登记下发单位分录中的【测算金额】提交OA流程前不能为空！");
					SysUtil.abort();
				}
				
				if(changeSupplierEntryInfo.getDutyOrg()==null)
				{
					MsgBox.showWarning("登记下发单位分录中的【责任归属部门】提交OA流程前不能为空！");
					SysUtil.abort();
				}
				
				sumCostAmount = sumCostAmount.add(changeSupplierEntryInfo.getCostAmount());//测算总成本
				sumDutyAmount = sumDutyAmount.add(changeSupplierEntryInfo.getOriDeductAmount());//责任单位承担费用
				
				String contents= "";//执行内容
				if(changeSupplierEntryInfo.getEntrys()==null || changeSupplierEntryInfo.getEntrys().get(0)==null)
				{
					contents = "	<td colspan=3 width=60%></td>  \r\n";
				}else
				{
					contents = "	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getEntrys().get(0).getContent().getChangeContent()+"</td>  \r\n";
				}
				
				unitString +=     
				"<tr>                                    \r\n"+
				"	<td rowspan=20 width=5%>"+changeSupplierEntryInfo.getSeq()+"</td>         \r\n"+
				"	<td rowspan=6  width=15%>登记信息</td> \r\n"+
				"	<td width=15%>合同号</td>              \r\n"+
				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getContractBill().getNumber()+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>合同名称</td>            \r\n"+
				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getContractBill().getName()+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>主送单位</td>            \r\n"+
				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getMainSupp().getName()+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>抄送单位</td>            \r\n"+
				"	<td colspan=3 width=60%>"+((changeSupplierEntryInfo.getCopySupp()!=null && !changeSupplierEntryInfo.getCopySupp().equals("null") && !changeSupplierEntryInfo.getCopySupp().equals(""))?changeSupplierEntryInfo.getCopySupp():"")+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>原始联系单号</td>        \r\n"+
				"	<td colspan=3 width=60%>"+((changeSupplierEntryInfo.getOriginalContactNum()!=null && !changeSupplierEntryInfo.getOriginalContactNum().equals("null"))?changeSupplierEntryInfo.getOriginalContactNum():"")+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"                                        \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>执行内容</td>            \r\n";
				
				unitString += contents ;
				
				//责任归属部门
				String dutyOrg = changeSupplierEntryInfo.getDutyOrg()==null?"":changeSupplierEntryInfo.getDutyOrg().getName();
				unitString += 
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td rowspan=14 width=15%>测试信息</td> \r\n"+
				"	<td width=15%>币别</td>                \r\n"+
				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getCurrency().getName()+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>汇率</td>                \r\n"+
				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getExRate()+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>测算金额原币</td>        \r\n"+
				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getCostAmount()+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>测算金额</td>            \r\n"+
				"	<td colspan=3 width=60%>"+ (new BigDecimal(changeSupplierEntryInfo.getCostAmount()+"")).multiply(changeSupplierEntryInfo.getExRate()) +"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"                                        \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>是否确定变更金额</td>    \r\n"+
				"	<td colspan=3 width=60%>"+(changeSupplierEntryInfo.isIsSureChangeAmt()==true?"是":"否")+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>测算说明</td>            \r\n"+
				"	<td colspan=3 width=60%>"+((changeSupplierEntryInfo.getCostDescription()!=null && !changeSupplierEntryInfo.getCostDescription().equals("null"))?changeSupplierEntryInfo.getCostDescription():"")+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>施工方报审金额</td>      \r\n"+
				"	<td colspan=3 width=60%>"+((changeSupplierEntryInfo.getConstructPrice()!=null && !changeSupplierEntryInfo.getConstructPrice().equals("null"))?changeSupplierEntryInfo.getConstructPrice():"")+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>是否责任扣款单位</td>    \r\n"+
				"	<td colspan=3 width=60%>"+(changeSupplierEntryInfo.isIsDeduct()==true?"是":"否")+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>责任扣款金额原币</td>    \r\n"+
				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getDeductAmount()+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"                                        \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>责任扣款金额（备注）</td>\r\n"+ 
				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getOriDeductAmount()+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>扣款原因</td>            \r\n"+
				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getDeductReason()+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>结算方式</td>            \r\n"+
				"	<td colspan=3 width=60%>"+((changeSupplierEntryInfo.getBalanceType()!=null && !changeSupplierEntryInfo.getBalanceType().equals("null"))?changeSupplierEntryInfo.getBalanceType():"") +"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>测算人</td>              \r\n"+
				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getReckonor().getName()+"</td>  \r\n"+
				"</tr>                                   \r\n"+
				"<tr>                                    \r\n"+
				"	<td width=15%>责任归属部门</td>        \r\n"+
				"	<td colspan=3 width=60%>"+dutyOrg+"</td>  \r\n"+
//				"	<td colspan=3 width=60%>"+changeSupplierEntryInfo.getDutyOrg()==null?"":changeSupplierEntryInfo.getDutyOrg().getName()+"</td>  \r\n"+
				"</tr>                                   \r\n";
			}
		}
		
		System.out.println("---unitString:" + unitString);
		
		html +="<tr>                                                                          \r\n"+          
		"	 <td colspan=6  width=100%>  			                                           \r\n"+                                                   
		"		<table class='tb_normal' width=100% >                                      \r\n"+         
		"			<tr><td colspan=6 class='td_normal_title_head'>登记下发单位信息</td></tr>\r\n"+  			  
		"			<tr>                                                                     \r\n"+
		"				<td class='td_normal_title'>单位序号</td>                              \r\n"+
		"				<td class='td_normal_title'>事项</td>                                  \r\n"+
		"				<td class='td_normal_title'>名称</td>                                  \r\n"+
		"				<td colspan=3 class='td_normal_title'>内容</td>                        \r\n"+
		"			</tr>                                                                    \r\n"+
		"			                                                                         \r\n"+
		unitString+
		"			                                                                         \r\n"+
		"			<tr>                                                                     \r\n"+
		"			  <td width=16% class='td_normal_title'>测试总金额汇总</td>	             \r\n"+
		"				<td colspan=2 >"+sumCostAmount+"</td>                                              \r\n"+
		"				<td width=16% class='td_normal_title'>责任单位承担费用</td>	           \r\n"+
		"				<td colspan=2>"+txtDutyAmount.getText()+"</td>                   \r\n"+
		"			</tr>                                                                    \r\n"+
		"			<tr>                                                                     \r\n"+
		"			  <td width=16% class='td_normal_title'>是否存在无效成本</td>	           \r\n"+
		"				<td width=16% >"+(cbxNoUse.isSelected()==true?"是":"否")+"</td>                                                 \r\n"+
		"				<td width=16% class='td_normal_title'>无效成本金额</td>	               \r\n"+
		"				<td width=16%>"+txtNoUse.getText()+"</td>                                               \r\n"+
		"				<td width=16% class='td_normal_title'>无效成本原因</td>	               \r\n"+
		"				<td width=16% >"+txtAheadReason.getText()+"</td>                                           \r\n"+
		"			</tr>                                                                    \r\n"+
		"		</table>                                                                   \r\n"+
		"	 </td>                                                                       \r\n"+
		"	 </tr>                                                                       \r\n";
		
		//----------表单尾部
		html +=
		"<tr>                                         \r\n"+         
		"		<td class='td_normal_title'>制单人</td>	  \r\n"+
		"		<td colspan=2>"+creator+"</td>                 \r\n"+
		"		<td class='td_normal_title'>制单日期</td>	\r\n"+  
		"		<td colspan=2>"+dateCreateTime+"</td>             \r\n"+
		"	 </tr>                                      \r\n"+
		"	 <tr>                                       \r\n"+
		"		<td class='td_normal_title'>审核人</td>	  \r\n"+
		"		<td colspan=2>"+auditor+"</td>                       \r\n"+
		"		<td class='td_normal_title'>审核日期</td>	\r\n"+  
		"		<td colspan=2>"+auditTime+"</td>               \r\n"+
		"	 </tr>                                      \r\n"+
		"</table>                                     \r\n";
		
		//html=html.replaceAll("<", "&lt;");
	   // html=html.replaceAll(">", "&gt;");
		System.out.println("---------html:"+html);
		return html;
	}
	
	//add by ypf on 2015年6月28日17:48:35
	protected String getBillNumber(String orgId,String prjId,String chngId) throws EASBizException, BOSException
	{
		String billNumber = "";
		CurProjectInfo curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(" where id='"+prjId+"'");
		String longNumber = curProjectInfo.getLongNumber();
		
		ChangeTypeInfo changeTypeInfo = ChangeTypeFactory.getRemoteInstance().getChangeTypeInfo(" where id='"+chngId+"'");
		String changeTypeName = changeTypeInfo.getName();
		
		String prefix = longNumber+"."+changeTypeName;
		
	    billNumber = ContractChangeVisaFacadeFactory.getRemoteInstance().getChngAuditBillNumber(orgId, prjId, chngId, prefix);
		System.out.println("--------客户端取到的编码："+billNumber);
		
		this.CHNG_CDE_NUM = billNumber;
		return billNumber;
	}
}
