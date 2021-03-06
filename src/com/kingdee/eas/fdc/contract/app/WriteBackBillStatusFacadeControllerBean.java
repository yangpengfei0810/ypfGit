package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.server.SQLDataAccessFactory;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.master.cssp.ISupplierCompanyBank;
import com.kingdee.eas.basedata.master.cssp.ISupplierCompanyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeEntryCollection;
import com.kingdee.eas.fdc.contract.ContractChangeEntryFactory;
import com.kingdee.eas.fdc.contract.ContractChangeEntryInfo;
import com.kingdee.eas.fdc.contract.ContractChangeVisaFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillException;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.SupplierContentEntryCollection;
import com.kingdee.eas.fdc.contract.SupplierContentEntryFactory;
import com.kingdee.eas.fdc.contract.SupplierContentEntryInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.FDCPaymentBillHelper;
import com.kingdee.eas.fdc.finance.FDCPaymentBillInfo;
import com.kingdee.eas.fdc.finance.OtherPaymentCollection;
import com.kingdee.eas.fdc.finance.OtherPaymentFactory;
import com.kingdee.eas.fdc.finance.OtherPaymentInfo;
import com.kingdee.eas.fdc.wslog.WSLogFactory;
import com.kingdee.eas.fdc.wslog.WSLogInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.UrgentDegreeEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

public class WriteBackBillStatusFacadeControllerBean extends AbstractWriteBackBillStatusFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.app.WriteBackBillStatusFacadeControllerBean");
//	private static String BILLID="";//eas单据id
	private static String bill_type_contract ="合同";//写log用的
	private static String bill_type_without ="无文本合同";//写log用的
	private static String bill_type_change ="变更签证单";//写log用的
	private static String bill_type_settle ="结算单";//写log用的
	private static String bill_type_paybill ="付款申请单";//写log用的
//	private static JSONObject JSONPARAM = null;//原先的state参数修改成json 用来传递结算单、变更签证单的金额
	private static Timestamp CURRENT_TIME = new Timestamp(System.currentTimeMillis());//获取当前时间
	private static String CALLTYPE_WRITEBACK ="回写";//写log用的
	private BOSUuid payBillId;
	
//	private static String OABILLID="";//OA单据id  add by ypf on 20130822
	
	protected boolean _alterAttachmentByID(Context ctx, String fid,String fileName, byte[] file) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", fid));
		view.setFilter(filter);
		logger.info("---------select * from T_BAS_Attachment where fid='"+ fid + "'");
		AttachmentCollection col;
		try {
			col = AttachmentFactory.getLocalInstance(ctx).getAttachmentCollection(view);
			AttachmentInfo info = new AttachmentInfo();
			// 取到需要更新附件的对象
			if (col != null && col.size() > 0) {
				info = col.get(0);
				info.setFile(file);// 附件
				info.setName(fileName);
				BigDecimal big=new BigDecimal(file.length);
				int b=big.ROUND_HALF_UP;
				info.setSize(big+"KB");
				info.setSizeInByte(file.length);
				
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("file");
				selector.add("name");
				selector.add("size");
				selector.add("sizeInByte");
				logger.info("附件："+fileName+"  字节数："+file.length +"  大小："+b+"KB");
				try {
					// 更新附件内容
					AttachmentFactory.getLocalInstance(ctx).updatePartial(info,selector);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("----根据附件ID'" + fid + "'更新附件表[T_BAS_Attachment]的'附件'字段时异常。");
					return false;
				}
			} else {
				logger.info("----在附件表[T_BAS_Attachment]中没有找到ID为'" + fid + "'的记录。");
				return false;
			}
		} catch (BOSException e1) {
			logger.info("----根据附件ID'" + fid + "'查询附件表[T_BAS_Attachment]时异常。");
			e1.printStackTrace();
			return false;
		}
	}

	protected boolean getContractBillID(Context ctx, String fid,String status) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("OABillID", fid));
		view.setFilter(filter);
		ContractBillCollection col;
		try {
			col = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
			ContractBillInfo info = new ContractBillInfo();
			// 取到需要更新单据的对象
			if (col != null && col.size() > 0) {
				info = col.get(0);
				logger.info("---回写合同单据的状态为："+status+"   系统设置为："+FDCBillStateEnum.getEnum(status));
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("state");
				info.setState(FDCBillStateEnum.getEnum(status));// 状态
				try {
					// 更新单据状态
					ContractBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
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
	
	public void updateOriContractBill (Context ctx,String contractBillId)throws BOSException, SQLException, EASBizException
	{
		//根据补充合同id查询是否是‘是否单独计算’，如果是‘否’，说明要将补充合同的金额累加到源合同的本币金额和原币金额里面
		String sql_supply = "select t2.fcontent as fcontent,t1.FAMOUNT as famount,t1.ForiginalAmount as foriginalAmount from T_CON_ContractBill t1 left join T_CON_ContractBillentry t2 on t1.fid=t2.fparentid  where t1.fid='"+contractBillId+"' and t1.FCONTRACTPROPERT='SUPPLY' and t2.fdetail='是否单独计算' ";
		logger.info("------是否单独计算-------sql_supply:"+sql_supply);
		IRowSet rs = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(sql_supply);
		if(rs!=null && rs.size()>0)
		{
			while(rs.next())
			{
				String fcontent = rs.getString("fcontent");
				BigDecimal amount_new = rs.getBigDecimal("famount");//补充合同     本币金额
				BigDecimal originalAmount_new = rs.getBigDecimal("foriginalAmount");//补充合同    原币金额
				//补充合同‘是否单独计算’
				if(fcontent!=null && fcontent.equals("否"))
				{
					String sqlGetOriContractID = "select t2.fcontent fid from T_CON_ContractBill t1 left join T_CON_ContractBillentry t2 on t1.fid=t2.fparentid  where t1.fid='"+contractBillId+"' and t1.FCONTRACTPROPERT='SUPPLY' and t2.fdetail='对应主合同编码'";
					IRowSet rsGetOriContractID = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(sqlGetOriContractID);
					
					if(rsGetOriContractID!=null && rsGetOriContractID.size()>0)
					{
						while(rsGetOriContractID.next())
						{
							String oriContractBillId = rsGetOriContractID.getString("fid");
						
							EntityViewInfo view = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("id", oriContractBillId));
							view.setFilter(filter);
							ContractBillCollection col = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
							ContractBillInfo info = col.get(0);
							
							info.setAmount(info.getAmount().add(amount_new));// 本币金额
							info.setOriginalAmount(info.getOriginalAmount().add(originalAmount_new));  //原币金额
							
							SelectorItemCollection selector = new SelectorItemCollection();
							selector.add("amount");
							selector.add("originalAmount");
							logger.info("补充合同更新原合同的本币和原币："+info.getAmount()+" "+info.getOriginalAmount() + "  补充合同金额："+amount_new);
							ContractBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
						}
					}
				}
			}
		}
	}
	
	//add by ypf on 20140617  附件回写单独列出来
	public String alterAttechment(Context ctx,String msg,String easId,boolean isExistAttachment,String state,IObjectCollection attachmentInfoAry)
	{
		//如果有附件
		if(msg.equals("1") && !easId.equals("") && isExistAttachment && !attachmentInfoAry.isEmpty() && state.equals("4AUDITTED"))
		{
			AttachmentInfo [] infos = new AttachmentInfo[attachmentInfoAry.size()];
			AttachmentInfo info = null;
		    for (int i = 0; i < attachmentInfoAry.size(); i++) {
			   info = new AttachmentInfo();
			   info = (AttachmentInfo) attachmentInfoAry.getObject(i);
			   info.setId(BOSUuid.create(info.getBOSType()));
			   infos[i] = info;
		   }
		    
		   String msgtemp = bakAttachmentByBillId(ctx, easId);
		   if(msgtemp.equals("1"))
		   {
			  logger.info("-----回写的附件列表长度："+infos.length); 
			  msg = addAttachmentByBillId(ctx, easId, infos);
		   }
		   /*else
		   {
			   msg = bakAttachmentByBillId(ctx, BILLID);
		   }*/
		}
		
		return msg;	
	}
	
	//更新合同单据状态
	protected String alterContractBillStatus(Context ctx, String fid,String status,String easId,String OAWorkFlowNumber) throws BOSException {
		logger.info("------进入了更新合同单据状态方法     参数=>   OA单据id："+ fid +"  单据状态："+status );
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("OABillID", fid));
		view.setFilter(filter);
		ContractBillCollection col;
		try {
			col = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
			ContractBillInfo info = new ContractBillInfo();
			// 取到需要更新单据的对象
			if (col != null && col.size() > 0) {
				logger.info("------跟据条件"+view+"找到了 OABillID：'"+ fid +"'的记录 ");
				info = col.get(0);
			    easId = info.getId().toString();
				logger.info("---回写合同单据的状态为："+status+"   系统设置为："+FDCBillStateEnum.getEnum(status) +"  BILLID(合同id):"+easId);
				info.setState(FDCBillStateEnum.getEnum(status));// 状态
				info.setAuditTime(new Date());  //审批时间
				
				//add by ypf on 20141214 
				if(OAWorkFlowNumber!=null && !OAWorkFlowNumber.equals(""))
				{
					info.setOAWorkFlowNumber(OAWorkFlowNumber);//oa流程编号
				}
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("state");
				selector.add("auditTime");
				
				//add by ypf on 20141214 
				if(OAWorkFlowNumber!=null && !OAWorkFlowNumber.equals(""))
				{
					selector.add("OAWorkFlowNumber");
				}
				try {
					//处理补充合同的金额情况  add by ypf on 20130311
					//mod by ypf on 20130506 加入是“已审批”状态条件控制，否则像驳回的补充合同也会累加合同金额
					if(!easId.equals("") && FDCBillStateEnum.getEnum(status).equals(FDCBillStateEnum.AUDITTED)){
						updateOriContractBill(ctx,easId);
					}
					
					// 更新单据状态
					ContractBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
					
					//-----------杨人代码  begin-------------------------------------------
					ProgrammingContractInfo pcInfo=info.getProgrammingContract();
					if(pcInfo!=null && FDCBillStateEnum.getEnum(status).equals(FDCBillStateEnum.AUDITTED)){
						//累计分配=累计分配+本次分配；
						BigDecimal totalSplitAmount = ContractBillFacadeFactory.getLocalInstance(ctx).getTotalSplitAmount(pcInfo.getId().toString());
						BigDecimal calTotalSplitAmount = FDCHelper.add(totalSplitAmount,info.getSplitAmt());
						ContractBillFacadeFactory.getLocalInstance(ctx).updateTotalSplitAmount(pcInfo.getId().toString(), calTotalSplitAmount);
					}
					//------end-----------------------------------------------------------
					
					
					return "1";
				} catch (Exception e) {
					e.printStackTrace();
					
					WSLogInfo logInfo = new WSLogInfo();
					logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
					logInfo.setSourceBillID(fid);
					logInfo.setSourceBillType("EAS1");
					logInfo.setState("失败");
					logInfo.setLogTitle("回写合同时发生异常："+status);
					logInfo.setLogDetail(e.getMessage());
					logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
					logInfo.setCreateTime(CURRENT_TIME);
					logInfo.setCallType(CALLTYPE_WRITEBACK);
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
					return "回写合同时发生异常"+e.getMessage();
				}
			} else {
				logger.info("------跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(fid);
				logInfo.setSourceBillType("EAS1");
				logInfo.setState("失败");
				logInfo.setLogTitle("回写合同时发生异常："+status);
				logInfo.setLogDetail("跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				
				return "跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ";
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			return e1.getMessage();
		}
	}
	
	//更新付款申请单据状态
	protected String alterPayRequestBillStatus(Context ctx, String fid,String status,String easId,String OABILLID) {
		logger.info("------进入了更新付款申请单据状态方法     参数=>   OA单据id："+ fid +"  单据状态："+status );
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("OABillID", fid));
		view.setFilter(filter);
		PayRequestBillCollection col = null;
		try {
			col = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
			PayRequestBillInfo info = new PayRequestBillInfo();
			// 取到需要更新单据的对象
			if (col != null && col.size() > 0) {
				logger.info("------跟据条件"+view+"找到了 OABillID：'"+ fid +"'的记录 ");
				
				info = col.get(0);
				easId = info.getId().toString();
				logger.info("---回写付款申请单据的状态为："+status+"   系统设置为："+FDCBillStateEnum.getEnum(status) +"  BILLID(付款申请单id):"+easId);
				info.setState(FDCBillStateEnum.getEnum(status));// 状态
				info.setAuditTime(new Date());  //审批时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("state");
				selector.add("auditTime");
				try {
					//mod by ypf on 20130724  判断分支调整一下
					//如果在OA是'已审批'，则EAS要生成付款单  mod by ypf on 20121217
					if(status.equals("4AUDITTED"))
					{
						//生成付款单
						payReqBillaudAudit(ctx,info.getId(),OABILLID);
						
						//更新银行帐号
						upSupplierBank(ctx,info);
						
						logger.info("---付款申请单在OA审批通过生成付款单：select * from T_CAS_PaymentBill where ffdcpayreqid='"+ easId+"'");
					}else if(status.equals("10REJECT"))//mod by ypf on 20130105  oa 驳回时处理单据状态
					{
						// 更新单据状态
						//mod by ypf on 20130129 驳回时需要恢复请款单中的部分字段数值
						selector.add("amount");
						selector.add("projectPriceInContract");
						selector.add("curPaid"); 
						selector.add("prjAllReqAmt");
						selector.add("completePrjAmt");
						selector.add("originalAmount");
						selector.add("actPaiedAmount");
						selector.add("projectPriceInContractOri");
						selector.add("invoiceAmt");
						selector.add("allInvoiceAmt");
						selector.add("invoiceOriAmt");
						//selector.add("allInvoiceOriAmt");
						selector.add("curReqPercent");
						selector.add("paymentProportion");
						
						info.setAmount(new BigDecimal(0));
						info.setProjectPriceInContract(new BigDecimal(0));
						info.setCurPaid(new BigDecimal(0));
//						info.setPrjAllReqAmt(info.getPrjAllReqAmt().subtract(info.getAmount()));
						//mod by ypf on 20130712  info.getPrjAllReqAmt() 可能会为空
						if(info.getPrjAllReqAmt()!=null && info.getAmount()!=null)
						{
							info.setPrjAllReqAmt(info.getPrjAllReqAmt().subtract(info.getAmount()));
						}
						info.setCompletePrjAmt(new BigDecimal(0));
						info.setOriginalAmount(new BigDecimal(0));
						info.setActPaiedAmount(new BigDecimal(0));
						info.setProjectPriceInContractOri(new BigDecimal(0));
						info.setInvoiceAmt(new BigDecimal(0));
//						info.setAllInvoiceAmt(info.getAllInvoiceAmt().subtract(info.getAmount()));
						//mod by ypf on 20130712  info.getAllInvoiceAmt() 可能会为空
						if(info.getAllInvoiceAmt()!=null && info.getAmount()!=null)
						{
							info.setAllInvoiceAmt(info.getAllInvoiceAmt().subtract(info.getAmount()));
						}
						info.setInvoiceOriAmt(new BigDecimal(0));
						info.setCurReqPercent(new BigDecimal(0));
						info.setPaymentProportion(new BigDecimal(0));
						
						PayRequestBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
						logger.info("-----------更新驳回状态成功！  单据id："+easId);
					}else
					{
						WSLogInfo logInfo = new WSLogInfo();
						logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
						logInfo.setSourceBillID(fid);
						logInfo.setSourceBillType("EAS2");
						logInfo.setState("失败");
						logInfo.setLogTitle("单据状态不符合要求");
						logInfo.setLogDetail("传入的单据状态："+status);
						logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
						logInfo.setCreateTime(CURRENT_TIME);
						logInfo.setCallType(CALLTYPE_WRITEBACK);
						try {
							WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
						} catch (BOSException e) {
							e.printStackTrace();
							return e.getMessage();
						}
						
						return "单据状态不符合要求"+status;
					}
					
					return "1";
				} catch (Exception e) {
					e.printStackTrace();
					
					WSLogInfo logInfo = new WSLogInfo();
					logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
					logInfo.setSourceBillID(fid);
					logInfo.setSourceBillType("EAS2");
					logInfo.setState("失败");
					logInfo.setLogTitle("回写付款申请单时发生异常："+status);
					logInfo.setLogDetail(e.getMessage());
					logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
					logInfo.setCreateTime(CURRENT_TIME);
					logInfo.setCallType(CALLTYPE_WRITEBACK);
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
					
					return "回写付款申请单时发生异常"+e.getMessage();
				}
			} else {
				logger.info("------跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(fid);
				logInfo.setSourceBillType("EAS2");
				logInfo.setState("失败");
				logInfo.setLogTitle("回写付款申请单时发生异常："+status);
				logInfo.setLogDetail("跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				
				return "跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ";
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(fid);
			logInfo.setSourceBillType("EAS2");
			logInfo.setState("失败");
			logInfo.setLogTitle("根据OABillID查询时异常："+status+"     "+view);
			logInfo.setLogDetail(e1.getMessage());
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			try {
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
			return e1.getMessage();
		}
	}
	
	//更新结算单据状态
	protected String alterContractSettlementBillStatus(Context ctx, String fid,String status,String easId,net.sf.json.JSONObject jsonparam) throws BOSException {
		logger.info("------进入了更新结算单据状态方法     参数=>   OA单据id："+ fid +"  单据状态："+status );

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("OABillID", fid));
		view.setFilter(filter);
		ContractSettlementBillCollection col;
		String msg = "";
		
		String contractBillId="";
		try {
			col = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(view);
			ContractSettlementBillInfo info = new ContractSettlementBillInfo();
			// 取到需要更新单据的对象
			if (col != null && col.size() > 0) {
				logger.info("------跟据条件"+view+"找到了 OABillID：'"+ fid +"'的记录 ");
				info = col.get(0);
				easId = info.getId().toString();
				contractBillId = info.getContractBill().getId()+"";//add by ypf on 20130819 获取合同id，用来更新合同的“是否已结算”标志
				logger.info("---回写结算单据的状态为："+status+"   系统设置为："+FDCBillStateEnum.getEnum(status) +"  BILLID(结算单据id):"+easId);
				info.setState(FDCBillStateEnum.getEnum(status));// 状态
				info.setAuditTime(new Date());  //审批时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("state");
				selector.add("auditTime");
				try {
					// 更新结算单状态
					if(status.equals("4AUDITTED"))
					{
						Object object = jsonparam.get("EASBak_1");
						BigDecimal amount = null;
						if(object!=null && !object.equals("") && !object.equals("null"))
						{
						   amount = new BigDecimal(object+"");//oa的结算金额
						   
						   //add by ypf on 20130731 如果oa对金额做了调整  那么就要更新eas对应的金额字段
						   if(info.getOriginalAmount()!=null && info.getOriginalAmount().compareTo(amount)!=0)
						   {
							   BigDecimal oriAmount = info.getOriginalAmount();
							   //add by ypf on 20130731 如果在oa结算金额做了修改，那么 累计结算金额也要相应调整 ;方式： 累计金额-原本币金额+调整金额
							   try {
								   info.setTotalSettlePrice(info.getTotalSettlePrice().subtract(info.getOriginalAmount()).add(amount));
								   info.setTotalOriginalAmount(info.getTotalSettlePrice().subtract(info.getOriginalAmount()).add(amount));
							   } catch (Exception e) {
								   
								    WSLogInfo logInfo = new WSLogInfo();
									logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
									logInfo.setSourceBillID(fid);
									logInfo.setSourceBillType("EAS3");
									logInfo.setState("失败");
									logInfo.setLogTitle("回写结算单时更新结算金额异常");
									logInfo.setLogDetail(jsonparam+" 异常消息："+e.getMessage());
									logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
									logInfo.setCreateTime(CURRENT_TIME);
									logInfo.setCallType(CALLTYPE_WRITEBACK);
									WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
									
									return "回写结算单时更新结算金额异常.\r\n"+e.getMessage();
							   }
							   
							   //记录结算金额与发起时不一致的情况
							   WSLogInfo logInfo = new WSLogInfo();
							   logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							   logInfo.setSourceBillID(fid);
							   logInfo.setSourceBillType("EAS3");
							   logInfo.setState("提示");
							   logInfo.setLogTitle("回写的结算金额与原先发起的结算金额不一致");
							   logInfo.setLogDetail(jsonparam+"原结算金额："+oriAmount+"  新的结算金额："+amount);
							   logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
							   logInfo.setCreateTime(CURRENT_TIME);
							   logInfo.setCallType(CALLTYPE_WRITEBACK);
							   WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
								
							   
							   //本币、原币要放后面
//							   info.setAmount(amount);//本币
							   info.setOriginalAmount(amount);//原币
							   info.setSettlePrice(amount);//结算造价
							   info.setCurSettlePrice(amount);//最新结算造价本币
							   info.setCurOriginalAmount(amount);//最新结算造价原币
							   
							   selector.add("totalSettlePrice");
							   selector.add("totalOriginalAmount");
//							   selector.add("amount");
							   selector.add("originalAmount");
							   selector.add("settlePrice");
							   selector.add("curSettlePrice");
							   selector.add("curOriginalAmount");
						   }

						   ContractSettlementBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
						   msg = "1";
						}else if(object==null || object.equals("") || object.equals("null")){
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(fid);
							logInfo.setSourceBillType("EAS3");
							logInfo.setState("失败");
							logInfo.setLogTitle("回写结算单时参数不全："+status);
							logInfo.setLogDetail("回写结算单时，OA的结算金额要回写，不能为空！"+jsonparam);
							logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
							logInfo.setCreateTime(CURRENT_TIME);
							logInfo.setCallType(CALLTYPE_WRITEBACK);
							WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
							
							msg = "回写结算单时，OA的结算金额要回写，不能为空！  JSONPARAM:"+jsonparam;
						}
						
						//mod by ypf on 20130731  重写 反写结算单时，如果状态是“已审批”，那么就要设置成“已结算”
						if(msg.equals("1"))
						{
							if(!contractBillId.equals(""))
							{
								msg = updateContractIsSettlement(ctx, contractBillId,amount);
								if(msg.equals("1"))
								{
									logger.info("---结算单审批通过后，更新合同的是否结算字段成功");
									return "1";
								}else
								{
									logger.info("---结算单审批通过后，更新合同的是否结算字段失败");
									return "结算单审批通过后，更新合同的是否结算字段失败.详细信息:"+msg;
								}
							}else{
								WSLogInfo logInfo = new WSLogInfo();
								logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
								logInfo.setSourceBillID(fid);
								logInfo.setSourceBillType("EAS3");
								logInfo.setState("失败");
								logInfo.setLogTitle("回写结算单时更新合同是否已结算标志失败："+status);
								logInfo.setLogDetail("回写结算单时更新合同是否已结算标志失败。合同id不能为空！"+jsonparam);
								logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
								logInfo.setCreateTime(CURRENT_TIME);
								logInfo.setCallType(CALLTYPE_WRITEBACK);
								WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
								
								return "回写结算单时更新合同是否已结算标志失败。合同id不能为空！";
							}
						}
					}else if(status.equals("10REJECT"))//mod by ypf on 20130105  oa 驳回时处理单据状态
					{
						ContractSettlementBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
						return "1";
					}else
					{
						//其他状态已在入口进行了拦截  参考方法：verfiyParam
					}
				} catch (Exception e) {
					e.printStackTrace();
					
					WSLogInfo logInfo = new WSLogInfo();
					logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
					logInfo.setSourceBillID(fid);
					logInfo.setSourceBillType("EAS3");
					logInfo.setState("失败");
					logInfo.setLogTitle("回写结算单时发生异常："+status);
					logInfo.setLogDetail(e.getMessage());
					logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
					logInfo.setCreateTime(CURRENT_TIME);
					logInfo.setCallType(CALLTYPE_WRITEBACK);
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
					
					return "回写结算单时发生异常:"+e.getMessage();
				}
			} else {
				logger.info("------跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(fid);
				logInfo.setSourceBillType("EAS3");
				logInfo.setState("失败");
				logInfo.setLogTitle("回写结算单时发生异常："+status);
				logInfo.setLogDetail("跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				
				return "跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ";
			}
		} catch (BOSException e1) {
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(fid);
			logInfo.setSourceBillType("EAS3");
			logInfo.setState("失败");
			logInfo.setLogTitle("回写结算单,根据OABillID查询时异常："+status+"   "+view);
			logInfo.setLogDetail(e1.getMessage());
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			
			e1.printStackTrace();
			return e1.getMessage();
		}
		
		return msg;
	}
	
	//mod by ypf on 20120921  反写结算单时，如果状态是“已审批”，那么就要设置成“已结算”
	private String updateContractIsSettlement(Context ctx,String id,BigDecimal amount)
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		ContractBillCollection col;
		
		try {
			col = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
			ContractBillInfo info = new ContractBillInfo();
			// 取到需要更新单据的对象
			if (col != null && col.size() > 0) {
				info = col.get(0);
				info.setHasSettled(true);
				info.setSettleAmt(amount);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("hasSettled");
				selector.add("settleAmt");
				try {
					// 更新合同单据“是否结算”字段
					ContractBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
					logger.info("---更新合同的是否结算标志位成功");
					return "1";
				} catch (Exception e) {
					e.printStackTrace();
					return e.getMessage();
				}
			} else {
				logger.info("----根据合同id："+id+"没有找到记录");
				return "根据合同id："+id+"没有找到记录";
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			return e1.getMessage();
		}
	}
	
	//更新变更签证确认单据状态
	protected String alterContractChangeBillStatus(Context ctx, String fid,String status,String easId,net.sf.json.JSONObject jsonparam) throws BOSException {
		logger.info("------进入了更新变更签证单据状态方法     参数=>   OA单据id："+ fid +"  单据状态："+status );
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("OABillID", fid));
		view.setFilter(filter);
		ContractChangeBillCollection col;
		String msg = "1";
		try {
			col = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
			ContractChangeBillInfo info = new ContractChangeBillInfo();
			// 取到需要更新单据的对象
			if (col != null && col.size() > 0) {
				logger.info("------跟据条件"+view+"找到了 OABillID：'"+ fid +"'的记录 ");
				info = col.get(0);
				easId = info.getId().toString();
				logger.info("---回写变更签证单据的状态为："+status+"   系统设置为："+FDCBillStateEnum.getEnum(status) +"  BILLID(变更签证单id):"+easId);
				info.setState(FDCBillStateEnum.getEnum(status));// 状态
				info.setAuditTime(new Date());  //审批时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("state");
				selector.add("auditTime");
				
				try {
					// 更新变更签证单状态
					if(status.equals("4AUDITTED"))
					{
						//mod by ypf on 2015年7月15日23:14:51 状态改为“已签证”
//						info.setState(FDCBillStateEnum.getEnum(status));
						info.setState(FDCBillStateEnum.getEnum("8VISA"));
						
						Object object = jsonparam.get("EASBak_1");
						BigDecimal amount = null;
						if(object!=null && !object.equals("") && !object.equals("null"))
						{
						   amount = new BigDecimal(object+"");//oa的变更签证单金额
						   
						   //add by ypf on 20130731 如果oa对金额做了调整  那么就要更新eas对应的金额字段
						   if(info.getOriginalAmount()!=null && info.getOriginalAmount().compareTo(amount)!=0)
						   {
							   BigDecimal oriAmount = info.getOriginalAmount();
							   
							   info.setAmount(amount);//本币
							   info.setOriginalAmount(amount);//原币
							   
							   selector.add("amount");
							   selector.add("originalAmount");
							   
							   //记录变更金额与发起时不一致的情况
							   WSLogInfo logInfo = new WSLogInfo();
							   logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							   logInfo.setSourceBillID(fid);
							   logInfo.setSourceBillType("EAS4");
							   logInfo.setState("提示");
							   logInfo.setLogTitle("回写的变更金额与原先发起的变更金额不一致");
							   logInfo.setLogDetail(jsonparam+"   原变更金额："+oriAmount+"  新的变更金额："+amount);
							   logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
							   logInfo.setCreateTime(CURRENT_TIME);
							   logInfo.setCallType(CALLTYPE_WRITEBACK);
							   WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
						   }

						   ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
						   
						    //add by ypf on 20150723 变更签证确认单审批时，将签证金额更新到变更后金额字段
							ContractChangeVisaFacadeFactory.getLocalInstance(ctx).saveVisaAmountByChngAuditBillID(easId);
							
						   return "1";
						}else if(object==null || object.equals("") || object.equals("null")){
							WSLogInfo logInfo = new WSLogInfo();
							logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
							logInfo.setSourceBillID(fid);
							logInfo.setSourceBillType("EAS4");
							logInfo.setState("失败");
							logInfo.setLogTitle("回写变更签证单时参数不全:"+status);
							logInfo.setLogDetail("回写变更签证单时，OA的变更签证单金额要回写，不能为空！"+jsonparam);
							logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
							logInfo.setCreateTime(CURRENT_TIME);
							logInfo.setCallType(CALLTYPE_WRITEBACK);
							WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
							
							return "回写变更签证单时，OA的变更金额要回写，不能为空！ \r\n JSONPARAM:"+jsonparam;
						}
					}else if(status.equals("10REJECT"))//mod by ypf on 20130105  oa 驳回时处理单据状态
					{
						ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
						return "1";
					}else
					{
						//其他状态已在入口进行了拦截  参考方法：verfiyParam
					}
				} catch (Exception e) {
					WSLogInfo logInfo = new WSLogInfo();
					logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
					logInfo.setSourceBillID(fid);
					logInfo.setSourceBillType("EAS4");
					logInfo.setState("失败");
					logInfo.setLogTitle("回写变更签证单时发生异常:"+status);
					logInfo.setLogDetail(e.getMessage());
					logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
					logInfo.setCreateTime(CURRENT_TIME);
					logInfo.setCallType(CALLTYPE_WRITEBACK);
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
					
					e.printStackTrace();
					return "回写变更签证单时发生异常"+e.getMessage();
				}
			} else {
				logger.info("------跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(fid);
				logInfo.setSourceBillType("EAS4");
				logInfo.setState("失败");
				logInfo.setLogTitle("回写变更签证单时发生异常："+status);
				logInfo.setLogDetail("跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				return "跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ";
			}
		} catch (BOSException e1) {
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(fid);
			logInfo.setSourceBillType("EAS4");
			logInfo.setState("失败");
			logInfo.setLogTitle("查询变更签证单时发生异常："+status+"    "+view);
			logInfo.setLogDetail(e1.getMessage());
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			e1.printStackTrace();
			return "查询变更签证单时发生异常:"+e1.getMessage();
		}
		return msg;
	}
	
	//更新无文本合同单据状态
	protected String alterContractWithoutTextStatus(Context ctx, String fid,String status,String easId,String OABILLID,String OAWorkFlowNumber) throws BOSException {
		logger.info("------进入了更新无文本合同单据状态方法     参数=>   OA单据id："+ fid +"  单据状态："+status );
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("OABillID", fid));
		view.setFilter(filter);
		ContractWithoutTextCollection col;
		String msg = "";
		try {
			logger.info("-----view:"+view);
			col = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(view);
			ContractWithoutTextInfo info = new ContractWithoutTextInfo();
			// 取到需要更新单据的对象
			if (col != null && col.size() > 0) {
				logger.info("------跟据条件"+view+"找到了 OABillID：'"+ fid +"'的记录 ");
				info = col.get(0);
				easId = info.getId().toString();
				logger.info("---回写无文本合同单据的状态为："+status+"   系统设置为："+FDCBillStateEnum.getEnum(status) +"  BILLID(无文本合同单id):"+easId);
				info.setState(FDCBillStateEnum.getEnum(status));// 状态
				info.setAuditTime(new Date());  //审批时间
				//add by ypf on 20141214 
				if(OAWorkFlowNumber!=null && !OAWorkFlowNumber.equals(""))
				{
					info.setOAWorkFlowNumber(OAWorkFlowNumber);//oa流程编号
				}
				
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("state");
				selector.add("auditTime");
				//add by ypf on 20141214 
				if(OAWorkFlowNumber!=null && !OAWorkFlowNumber.equals(""))
				{
					selector.add("OAWorkFlowNumber");
				}
				
				try {
					//mod by ypf on 20121217 条件分支调整
					//如果在OA是'已审批'，则EAS要生成付款单  mod by ypf on 20121217
					if(status.equals("4AUDITTED"))
					{
						//生成付款单
						contractWithOutTextAudit(ctx,BOSUuid.read(easId),OABILLID);
						logger.info("---无文本合同在OA审批通过生成付款单：select * from T_CAS_PaymentBill where ffdcpayreqid='"+ easId+"'");
						return "1";
					}else if(status.equals("10REJECT"))
					{
						// 更新单据状态
						ContractWithoutTextFactory.getLocalInstance(ctx).updatePartial(info, selector);
						logger.info("----更新无文本合同驳回状态成功！ 单据id："+easId);
						return "1";
					}else
					{
						//其他状态已在入口进行了拦截  参考方法：verfiyParam
					}
				} catch (Exception e) {
					e.printStackTrace();
					WSLogInfo logInfo = new WSLogInfo();
					logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
					logInfo.setSourceBillID(fid);
					logInfo.setSourceBillType("EAS5");
					logInfo.setState("警告/失败");
					logInfo.setLogTitle("回写无文本合同时抛出异常，请检查'日志明细'字段，如果显示的是'1'则说明正常，不影响业务；否则 联系管理员分析。");
					logInfo.setLogDetail(e.getMessage());
					logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
					logInfo.setCreateTime(CURRENT_TIME);
					logInfo.setCallType(CALLTYPE_WRITEBACK);
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
//					return "回写无文本合同时发生异常:"+e.getMessage();
					
					return "1";//mod by ypf on 20130822系统一个异常，不影响正常业务走下去，如果不关掉异常，oa那边就会捕捉到这个消息 执行不下去
				}
			} else {
				logger.info("------跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(fid);
				logInfo.setSourceBillType("EAS5");
				logInfo.setState("失败");
				logInfo.setLogTitle("回写无文本合同时发生异常："+status);
				logInfo.setLogDetail("跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				return "跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ";
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(fid);
			logInfo.setSourceBillType("EAS5");
			logInfo.setState("失败");
			logInfo.setLogTitle("查询无文本合时发生异常："+status+"   "+view);
			logInfo.setLogDetail(e1.getMessage());
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			return "查询无文本合时发生异常："+e1.getMessage();
		}
		return msg;
	}
	
	//更新变更审批单据状态
	public String alterChangeAuditBillStatus(Context ctx, String fid,String status,String easId,net.sf.json.JSONObject JSONPARAM) throws BOSException {
		logger.info("------进入了更新变更审批单据状态方法     参数=>   OA单据id："+ fid +"  单据状态："+status );
		
		String sql = "select fid from t_con_changeauditbill where cfoabillid='"+fid+"'";
		String id ="";//t_con_changeauditbill 单据id
		IRowSet rs = DbUtil.executeQuery(ctx, sql);
		try {
			while(rs.next())
			{
			   id = rs.getString("fid").trim();
			   break;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		//判断根据oaid是否可以找到对应的变更审批单
		if(id.equals("") || id == "")
		{
			logger.info("------跟据条件没有找到 OABillID：'"+ fid +"'的记录 ");
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(fid);
			logInfo.setSourceBillType("EAS6");
			logInfo.setState("失败");
			logInfo.setLogTitle("回写变更审批时发生异常："+status);
			logInfo.setLogDetail("跟据条件没有找到 OABillID：'"+ fid +"'的记录 ");
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			return "跟据条件没有找到 OABillID：'"+ fid +"'的记录 ";
		}
			
		try {
//			ChangeAuditBillInfo info = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(id));
		    
			logger.info("------跟据条件找到了 OABillID：'"+ fid +"'的记录 ");
			easId = id;
			logger.info("---回写变更审批单据的状态为："+status+"   系统设置为："+FDCBillStateEnum.getEnum(status) +"  BILLID(变更审批单据id):"+easId);
//			info.setState(FDCBillStateEnum.getEnum(status));// 状态
//			info.setAuditTime(new Date());  //审批时间
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("state");
//			selector.add("auditTime");
			try {
				//mod by ypf on 20121217 条件分支调整
				//如果在OA是'已审批'，则EAS要生成付款单  mod by ypf on 20121217
				if(status.equals("4AUDITTED"))
				{
					//调用变更审批单的审核方法
					String changeAuditBillAuditString = changeAuditBillAudit(ctx,BOSUuid.read(easId),JSONPARAM);
					
					//add by ypf on 20150723 合同变更发起单审批时，将测试金额更新到预估金额字段
					ContractChangeVisaFacadeFactory.getLocalInstance(ctx).saveCostAmountByChngAuditBillID(easId);
					
					return changeAuditBillAuditString;
				}else if(status.equals("10REJECT"))
				{
					//add by ypf on 20140305  变更审批单状态（界面显示用的）
//					info.setChangeState(ChangeBillStateEnum.getEnum(status));
//					selector.add("changeState");
					
					// 更新单据状态
//					ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					
					
					String updateSql = "update T_CON_ChangeAuditBill set fstate='10REJECT',faudittime=getdate(),fchangestate='10REJECT' where fid='"+id+"' ";
					logger.info("----更新变更审批驳回状态sql："+updateSql);
					DbUtil.execute(ctx, updateSql);
					
					logger.info("----更新变更审批驳回状态成功！ 单据id："+easId);
					return "1";
				}else
				{
					//其他状态已在入口进行了拦截  参考方法：verfiyParam
					return "回写的状态不对，只能是：4AUDITTED、10REJECT";
				}
			} catch (Exception e) {
				e.printStackTrace();
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(fid);
				logInfo.setSourceBillType("EAS6");
				logInfo.setState("警告/失败");
				logInfo.setLogTitle("回写变更审批时抛出异常，请检查'日志明细'字段，如果显示的是'1'则说明正常，不影响业务；否则 联系管理员分析。");
				logInfo.setLogDetail(e.getMessage());
				logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				
				return "1";//mod by ypf on 20130822系统一个异常，不影响正常业务走下去，如果不关掉异常，oa那边就会捕捉到这个消息 执行不下去
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(fid);
			logInfo.setSourceBillType("EAS6");
			logInfo.setState("失败");
			logInfo.setLogTitle("查询变更审批时发生异常："+status+"   "+fid);
			logInfo.setLogDetail(e1.getMessage());
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			
			return "查询变更审批时发生异常："+e1.getMessage();
		}
	}
	
	//更新变更审批单据状态
	public String alterChangeAuditBillStatusForEAS8(Context ctx, String fid,String status,String easId,net.sf.json.JSONObject JSONPARAM) throws BOSException {
		logger.info("------进入了更新变更审批单据状态方法     参数=>   OA单据id："+ fid +"  单据状态："+status );
		
		String sql = "select fid from t_con_changeauditbill where cfoabillid='"+fid+"'";
		String id ="";//t_con_changeauditbill 单据id
		IRowSet rs = DbUtil.executeQuery(ctx, sql);
		try {
			while(rs.next())
			{
			   id = rs.getString("fid").trim();
			   break;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		//判断根据oaid是否可以找到对应的变更审批单
		if(id.equals("") || id == "")
		{
			logger.info("------跟据条件没有找到 OABillID：'"+ fid +"'的记录 ");
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(fid);
			logInfo.setSourceBillType("EAS8");
			logInfo.setState("失败");
			logInfo.setLogTitle("回写变更审批时发生异常："+status);
			logInfo.setLogDetail("跟据条件没有找到 OABillID：'"+ fid +"'的记录 ");
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			return "跟据条件没有找到 OABillID：'"+ fid +"'的记录 ";
		}
			
		try {
//			ChangeAuditBillInfo info = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(id));
		    
			logger.info("------跟据条件找到了 OABillID：'"+ fid +"'的记录 ");
			easId = id;
			logger.info("---回写变更审批单据的状态为："+status+"   系统设置为："+FDCBillStateEnum.getEnum(status) +"  BILLID(变更审批单据id):"+easId);
//			info.setState(FDCBillStateEnum.getEnum(status));// 状态
//			info.setAuditTime(new Date());  //审批时间
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("state");
//			selector.add("auditTime");
			try {
				//mod by ypf on 20121217 条件分支调整
				//如果在OA是'已审批'，则EAS要生成付款单  mod by ypf on 20121217
				if(status.equals("4AUDITTED"))
				{
					//调用变更审批单的审核方法
					String changeAuditBillAuditString = changeAuditBillAuditForEAS8(ctx,BOSUuid.read(easId),JSONPARAM);
					
					return changeAuditBillAuditString;
				}else if(status.equals("10REJECT"))
				{
					//add by ypf on 20140305  变更审批单状态（界面显示用的）
//					info.setChangeState(ChangeBillStateEnum.getEnum(status));
//					selector.add("changeState");
					
					// 更新单据状态
//					ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					
					
					String updateSql = "update T_CON_ChangeAuditBill set fstate='10REJECT',faudittime=getdate(),fchangestate='10REJECT' where fid='"+id+"' ";
					logger.info("----更新变更审批驳回状态sql："+updateSql);
					DbUtil.execute(ctx, updateSql);
					
					logger.info("----更新变更审批驳回状态成功！ 单据id："+easId);
					return "1";
				}else
				{
					//其他状态已在入口进行了拦截  参考方法：verfiyParam
					return "回写的状态不对，只能是：4AUDITTED、10REJECT";
				}
			} catch (Exception e) {
				e.printStackTrace();
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(fid);
				logInfo.setSourceBillType("EAS8");
				logInfo.setState("警告/失败");
				logInfo.setLogTitle("回写变更审批时抛出异常，请检查'日志明细'字段，如果显示的是'1'则说明正常，不影响业务；否则 联系管理员分析。");
				logInfo.setLogDetail(e.getMessage());
				logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				
				return "1";//mod by ypf on 20130822系统一个异常，不影响正常业务走下去，如果不关掉异常，oa那边就会捕捉到这个消息 执行不下去
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(fid);
			logInfo.setSourceBillType("EAS8");
			logInfo.setState("失败");
			logInfo.setLogTitle("查询变更审批时发生异常："+status+"   "+fid);
			logInfo.setLogDetail(e1.getMessage());
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			
			return "查询变更审批时发生异常："+e1.getMessage();
		}
	}
	
	//更新其他付款单据状态
	protected String alterOtherPaymentStatus(Context ctx, String fid,String status,String easId) throws BOSException {
		logger.info("------进入了更新其他付款单据状态方法     参数=>   OA单据id："+ fid +"  单据状态："+status );
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("OABillID", fid));
		view.setFilter(filter);
		OtherPaymentCollection col;
		String msg = "";
		try {
			logger.info("-----view:"+view);
			col = OtherPaymentFactory.getLocalInstance(ctx).getOtherPaymentCollection(view);
			OtherPaymentInfo info = new OtherPaymentInfo();
			// 取到需要更新单据的对象
			if (col != null && col.size() > 0) {
				logger.info("------跟据条件"+view+"找到了 OABillID：'"+ fid +"'的记录 ");
				info = col.get(0);
				easId = info.getId().toString();
				logger.info("---回写其他付款单据的状态为："+status+"   系统设置为："+FDCBillStateEnum.getEnum(status) +"  BILLID(其他付款单id):"+easId);
				info.setBillstates(FDCBillStateEnum.getEnum(status));// 状态
				info.setAuditDate(new Date());  //审批时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("billstates");
				selector.add("auditDate");
				try {
					if(status.equals("4AUDITTED") || status.equals("10REJECT")){
						OtherPaymentFactory.getLocalInstance(ctx).updatePartial(info, selector);
						return "1";
					}
				} catch (Exception e) {
					e.printStackTrace();
					WSLogInfo logInfo = new WSLogInfo();
					logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
					logInfo.setSourceBillID(fid);
					logInfo.setSourceBillType("EAS7");
					logInfo.setState("警告/失败");
					logInfo.setLogTitle("回写其他付款单据时抛出异常，请检查'日志明细'字段，如果显示的是'1'则说明正常，不影响业务；否则 联系管理员分析。");
					logInfo.setLogDetail(e.getMessage());
					logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
					logInfo.setCreateTime(CURRENT_TIME);
					logInfo.setCallType(CALLTYPE_WRITEBACK);
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
					
					return "1";//mod by ypf on 20130822系统一个异常，不影响正常业务走下去，如果不关掉异常，oa那边就会捕捉到这个消息 执行不下去
				}
			} else {
				logger.info("------跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(fid);
				logInfo.setSourceBillType("EAS7");
				logInfo.setState("失败");
				logInfo.setLogTitle("回写其他付款单据时发生异常："+status);
				logInfo.setLogDetail("跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ");
				logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				return "跟据条件"+view+"没有找到 OABillID：'"+ fid +"'的记录 ";
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(fid);
			logInfo.setSourceBillType("EAS7");
			logInfo.setState("失败");
			logInfo.setLogTitle("查询其他付款单据时发生异常："+status+"   "+view);
			logInfo.setLogDetail(e1.getMessage());
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fid);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			return "查询其他付款单据时发生异常："+e1.getMessage();
		}
		return msg;
	}
	
	//add by ypf on 20130731 增加校验规则
	protected String verfiyParam(Context ctx,String billId,String billType,net.sf.json.JSONObject jsonparam) throws JSONException
	{
		if(billId == null || billId.equals("") || billId.equals("null"))
		{
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(billId);
			logInfo.setSourceBillType(billType);
			logInfo.setState("失败：检查billId是否为空时不通过");
			logInfo.setLogTitle("失败：检查billId是否为空时不通过");
			logInfo.setLogDetail("失败：检查billId是否为空时不通过");
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			try {
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			} catch (BOSException e) {
				e.printStackTrace();
				return e.getMessage();
			}
			
			return "失败：检查billId是否为空时不通过";
		}
		
		//校验单据类型
		String [] type_ = new String []{"EAS1","EAS2","EAS3","EAS4","EAS5","EAS6","EAS7","EAS8"};
		if(billType == null || billType.equals("") || billType.equals("null"))
		{
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(billId);
			logInfo.setSourceBillType(billType);
			logInfo.setState("失败：检查billtype是否为空时不通过");
			logInfo.setLogTitle("失败：检查billtype是否为空时不通过");
			logInfo.setLogDetail("失败：检查billtype是否为空时不通过");
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			try {
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			} catch (BOSException e) {
				e.printStackTrace();
				return e.getMessage();
			}
			
			return "失败：检查billtype是否为空时不通过";
		}
		
		//校验更新状态
		String state = jsonparam.get("status")+"";
		String [] state_ = new String []{"4AUDITTED","10REJECT"};
		if(state == null || state.equals("") || state.equals("null"))
		{
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(billId);
			logInfo.setSourceBillType(billType);
			logInfo.setState("失败：检查state是否为空时不通过");
			logInfo.setLogTitle("失败：检查state是否为空时不通过");
			logInfo.setLogDetail("失败：检查state是否为空时不通过");
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			try {
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			} catch (BOSException e) {
				e.printStackTrace();
				return e.getMessage();
			}
			
			return "失败：检查state是否为空时不通过";
		}
		
		if(state != null && !state.equals("") && !state.equals("null"))
		{
			boolean bl_isExist = false;
			for (int i = 0; i < state_.length; i++) {
				if(state.trim().equals(state_[i]))
				{
					bl_isExist = true;
					break;
				}
			}
			
			//如果没有匹配在类型，则返回
			if(!bl_isExist)
			{
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(billId);
				logInfo.setSourceBillType(billType);
				logInfo.setState("失败：检查state是否匹配");
				logInfo.setLogTitle("失败：检查state是否匹配时不通过:"+state);
				logInfo.setLogDetail("失败：检查state是否匹配不通过");
				logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				try {
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				} catch (BOSException e) {
					e.printStackTrace();
					return e.getMessage();
				}
				
				return "失败：检查state是否匹配时不通过:"+state;
			}
		}
		
		if(billType != null && !billType.equals("") && !billType.equals("null"))
		{
			boolean bl_isExist = false;
			for (int i = 0; i < type_.length; i++) {
				if(billType.contains(type_[i]))
				{
					bl_isExist = true;
					break;
				}
			}
			
			//如果没有匹配在类型，则返回
			if(!bl_isExist)
			{
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(billId);
				logInfo.setSourceBillType(billType);
				logInfo.setState("失败：检查billtype是否匹配");
				logInfo.setLogTitle("失败：检查billtype是否匹配时不通过:"+billType);
				logInfo.setLogDetail("失败：检查billtype是否匹配不通过");
				logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				try {
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				} catch (BOSException e) {
					e.printStackTrace();
					return e.getMessage();
				}
				
				return "失败：检查billtype是否匹配时不通过:"+billType;
			}
			
			if(billType.contains("EAS3") && state.equals("4AUDITTED"))
			{
				Object object = jsonparam.get("EASBak_1");
				if(object==null || object.equals("") || object.equals("null")){
					WSLogInfo logInfo = new WSLogInfo();
					logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
					logInfo.setSourceBillID(billId);
					logInfo.setSourceBillType("EAS3");
					logInfo.setState("失败");
					logInfo.setLogTitle("回写结算单时必填字段为空");
					logInfo.setLogDetail("回写已审批的结算单时，OA的结算金额要回写，但是没有回写结算金额！  JSONPARAM:"+jsonparam);
					logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
					logInfo.setCreateTime(CURRENT_TIME);
					logInfo.setCallType(CALLTYPE_WRITEBACK);
					try {
						WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
					} catch (BOSException e) {
						e.printStackTrace();
					}
					
					return "回写已审批的结算单时，OA的结算金额要回写，不能为空！ \r\n JSONPARAM:"+jsonparam;
				}
			}
			
			if(billType.contains("EAS4") && state.equals("4AUDITTED"))
			{
				Object object = jsonparam.get("EASBak_1");
				if(object==null || object.equals("") || object.equals("null")){
					WSLogInfo logInfo = new WSLogInfo();
					logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
					logInfo.setSourceBillID(billId);
					logInfo.setSourceBillType("EAS4");
					logInfo.setState("失败");
					logInfo.setLogTitle("回写变更签证单时必填字段为空");
					logInfo.setLogDetail("回写已审批的变更签证单时，OA的变更金额要回写，但是没有回写变更金额！JSONPARAM:"+jsonparam);
					logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
					logInfo.setCreateTime(CURRENT_TIME);
					logInfo.setCallType(CALLTYPE_WRITEBACK);
					try {
						WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
					} catch (BOSException e) {
						e.printStackTrace();
					}
					
					return "回写已审批的变更签证单时，OA的变更金额要回写，不能为空！ \r\n JSONPARAM:"+jsonparam;
				}
			}
		}
		
		return "";
	}
	
	protected String _modBillInfo(Context ctx, String billType,String billId,String stateJson, boolean isExistAttachment,IObjectCollection attachmentInfoAry) {
		String inParam = "------OA开始调用了,传入参数=>  类型："+billType +"  OA单据id："+billId +"  json："+stateJson +"  是否有附件："+isExistAttachment;
		logger.info(inParam);
		CURRENT_TIME = new Timestamp(System.currentTimeMillis());//加载调用时更新一个
//		BILLID = "";
		net.sf.json.JSONObject JSONPARAM = null;
		
		String OABILLID = billId;
		
		if(ctx==null)
		{
			logger.info("上下文会话是空的");
			return "ctx 上下文会话是空的";
		}
		
		//调用时记录参数信息
		WSLogInfo logInfo1 = new WSLogInfo();
		logInfo1.setId(BOSUuid.create(logInfo1.getBOSType()));
		logInfo1.setSourceBillID(billId);
		logInfo1.setSourceBillType(billType);
		logInfo1.setState("信息");
		logInfo1.setLogTitle("回写EAS的信息");
		logInfo1.setLogDetail(inParam);
		logInfo1.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
		logInfo1.setCreateTime(CURRENT_TIME);
		logInfo1.setCallType(CALLTYPE_WRITEBACK);
		logInfo1.setInParam("billType:"+billType +"  billId:"+billId +"  stateJson:"+stateJson +"  isExistAttachment:"+isExistAttachment);
		try {
			WSLogFactory.getLocalInstance(ctx).addnew(logInfo1);
		} catch (BOSException e2) {
			e2.printStackTrace();
			
			WSLogInfo logInfo2 = new WSLogInfo();
			logInfo2.setId(BOSUuid.create(logInfo1.getBOSType()));
			logInfo2.setSourceBillID(billId);
			logInfo2.setSourceBillType(billType);
			logInfo2.setState("异常");
			logInfo2.setLogTitle("进入接口modbillinfo方法时，保存原始来源数据异常");
			logInfo2.setLogDetail(inParam);
			logInfo2.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
			logInfo2.setCreateTime(CURRENT_TIME);
			logInfo2.setCallType(CALLTYPE_WRITEBACK);
			logInfo2.setInParam("billType:"+billType +"  billId:"+billId +"  stateJson:"+stateJson +"  isExistAttachment:"+isExistAttachment);
		}
		
		
		//mod by ypf on 20130731  state 参数修改成json，用来解决传结算单、变更签证单的金额字段
		try {
			JSONPARAM = net.sf.json.JSONObject.fromObject(stateJson);
		} catch (Exception e) {
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(billId);
			logInfo.setSourceBillType(billType);
			logInfo.setState("失败");
			logInfo.setLogTitle("回写EAS时参数不符合Json格式要求："+stateJson);
			logInfo.setLogDetail(stateJson);
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			try {
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			} catch (BOSException e1) {
				e1.printStackTrace();
				return e1.getMessage();
			}
			
			return "回写EAS时参数不符合Json格式要求："+stateJson;
		}
		
		String state = "";
		state = (JSONPARAM.get("status")!=null && !JSONPARAM.get("status").equals(""))?JSONPARAM.get("status").toString():"";
		
		//add by ypf on 20130731 增加校验规则
		try {
			String verfiyMsg;
			verfiyMsg = verfiyParam(ctx,billId,billType,JSONPARAM);
			if(!verfiyMsg.equals("") && verfiyMsg!=null)
			{
				logger.info(verfiyMsg);
				return verfiyMsg;//有异常消息 则返回
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
			return e1.getMessage();
		}
		
		
		String msg = "1";
		//更新合同单据状态
		if(billType != null && billType.trim().contains("EAS1"))
		{
		    try {
		    	//add by ypf on 20141214 通过“写回EAS”流程节点把OA流程编号一起回写到EAS OAWorkFlowNumber字段
				String OAWorkFlowNumber = "";
				OAWorkFlowNumber = (JSONPARAM.get("OAWorkFlowNumber")!=null && !JSONPARAM.get("OAWorkFlowNumber").equals(""))?JSONPARAM.get("OAWorkFlowNumber").toString():"";
				
		    	String easId = "";
		    	msg = alterContractBillStatus(ctx,billId,state,easId,OAWorkFlowNumber);
		    	//有异常时，直接返回接口调用了
		    	if(!msg.equals("1"))
		    	{
		    		return msg;
		    	}
		    	easId = getEASID(ctx,"T_CON_ContractBill",billId);
		    	alterAttechment(ctx,msg,easId,isExistAttachment,state,attachmentInfoAry);
		    	
			} catch (BOSException e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
		
		//更新付款申请单据状态
		if(billType != null && billType.trim().contains("EAS2"))
		{
//		    msg = alterPayRequestBillStatus(ctx,billId,state);
		    
		    String easId = "";
	    	msg = alterPayRequestBillStatus(ctx,billId,state,easId, OABILLID);
	    	//有异常时，直接返回接口调用了
	    	if(!msg.equals("1"))
	    	{
	    		return msg;
	    	}
	    	easId = getEASID(ctx,"T_CON_PayRequestBill",billId);
	    	alterAttechment(ctx,msg,easId,isExistAttachment,state,attachmentInfoAry);
		}
		
		//更新结算单据状态
		if(billType != null && billType.trim().contains("EAS3"))
		{
		    try {
//				msg = alterContractSettlementBillStatus(ctx,billId,state);
				
				String easId = "";
		    	msg = alterContractSettlementBillStatus(ctx,billId,state,easId,JSONPARAM);
		    	//有异常时，直接返回接口调用了
		    	if(!msg.equals("1"))
		    	{
		    		return msg;
		    	}
		    	easId = getEASID(ctx,"T_CON_ContractChangeBill",billId);
		    	alterAttechment(ctx,msg,easId,isExistAttachment,state,attachmentInfoAry);
			} catch (BOSException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		}
		
		//更新变更签证确认单据状态
		if(billType != null && billType.trim().contains("EAS4"))
		{
		    try {
//				msg = alterContractChangeBillStatus(ctx,billId,state);
		    	
		    	String easId = "";
		    	msg = alterContractChangeBillStatus(ctx,billId,state,easId,JSONPARAM);
		    	//有异常时，直接返回接口调用了
		    	if(!msg.equals("1"))
		    	{
		    		return msg;
		    	}
		    	easId = getEASID(ctx,"T_CON_ContractSettlementBill",billId);
		    	alterAttechment(ctx,msg,easId,isExistAttachment,state,attachmentInfoAry);
		    	
			} catch (BOSException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		}
		
		//更新无文本合同单据状态
		if(billType != null && billType.trim().contains("EAS5"))
		{
		    try {
//				msg = alterContractWithoutTextStatus(ctx,billId,state);
		    	//add by ypf on 20141214 通过“写回EAS”流程节点把OA流程编号一起回写到EAS OAWorkFlowNumber字段
				String OAWorkFlowNumber = "";
				OAWorkFlowNumber = (JSONPARAM.get("OAWorkFlowNumber")!=null && !JSONPARAM.get("OAWorkFlowNumber").equals(""))?JSONPARAM.get("OAWorkFlowNumber").toString():"";
				
				String easId = "";
		    	msg = alterContractWithoutTextStatus(ctx,billId,state,easId,OABILLID,OAWorkFlowNumber);
		    	//有异常时，直接返回接口调用了
		    	if(!msg.equals("1"))
		    	{
		    		return msg;
		    	}
		    	easId = getEASID(ctx,"T_CON_ContractWithoutText",billId);
		    	alterAttechment(ctx,msg,easId,isExistAttachment,state,attachmentInfoAry);
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		//更新变更审批单据状态
		if(billType != null && billType.trim().contains("EAS6"))
		{
		    try {
//				msg = alterChangeAuditBillStatus(ctx,billId,state);
				
				String easId = "";
		    	msg = alterChangeAuditBillStatus(ctx,billId,state,easId,JSONPARAM);
		    	//有异常时，直接返回接口调用了
		    	if(!msg.equals("1"))
		    	{
		    		return msg;
		    	}
		    	easId = getEASID(ctx,"t_con_changeauditbill",billId);
		    	alterAttechment(ctx,msg,easId,isExistAttachment,state,attachmentInfoAry);
		    	
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		//更新其他付款单据状态
		if(billType != null && billType.trim().contains("EAS7"))
		{
		    try {
//				msg = alterOtherPaymentStatus(ctx,billId,state);
				
				String easId = "";
		    	msg = alterOtherPaymentStatus(ctx,billId,state,easId);
		    	//有异常时，直接返回接口调用了
		    	if(!msg.equals("1"))
		    	{
		    		return msg;
		    	}
		    	easId = getEASID(ctx,"CT_FNC_OtherPayment",billId);
		    	alterAttechment(ctx,msg,easId,isExistAttachment,state,attachmentInfoAry);
		    	
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		//更新变更审批单据状态
		if(billType != null && billType.trim().contains("EAS8"))
		{
		    try {
//				msg = alterChangeAuditBillStatus(ctx,billId,state);
				
				String easId = "";
		    	msg = alterChangeAuditBillStatusForEAS8(ctx,billId,state,easId,JSONPARAM);
		    	//有异常时，直接返回接口调用了
		    	if(!msg.equals("1"))
		    	{
		    		return msg;
		    	}
		    	easId = getEASID(ctx,"t_con_changeauditbill",billId);
		    	alterAttechment(ctx,msg,easId,isExistAttachment,state,attachmentInfoAry);
		    	
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		//mod by ypf on 20140617 附件回写放到单个的流程中处理
		/*//如果有附件
		if(msg.equals("1") && isExistAttachment && !attachmentInfoAry.isEmpty() && state.equals("4AUDITTED"))
		{
			AttachmentInfo [] infos = new AttachmentInfo[attachmentInfoAry.size()];
			AttachmentInfo info = null;
		    for (int i = 0; i < attachmentInfoAry.size(); i++) {
			   info = new AttachmentInfo();
			   info = (AttachmentInfo) attachmentInfoAry.getObject(i);
			   info.setId(BOSUuid.create(info.getBOSType()));
			   infos[i] = info;
		   }
		    
		   String msgtemp = bakAttachmentByBillId(ctx, BILLID);
		   if(msgtemp.equals("1"))
		   {
			  logger.info("-----回写的附件列表长度："+infos.length); 
			  msg = addAttachmentByBillId(ctx, BILLID, infos);
		   }
		   else
		   {
			   msg = bakAttachmentByBillId(ctx, BILLID);
		   }
		}*/
		
		//如果没有附件,并且   mod by ypf on 20130822 没有附件则不处理
		/*if(msg.equals("1") && !isExistAttachment && state.equals("4AUDITTED"))
		{
		   logger.info("-----没有附件");
		   if(attachmentInfoAry == null || null == attachmentInfoAry)
		   {
			   logger.info("-----没有附件,准备备份附件");
			   String msgtemp = bakAttachmentByBillId(ctx, BILLID);
			   logger.info("-----备份原附件消息："+msgtemp); 
			   
			   if(msgtemp.equals("1"))
			   {
				  msg="1";
				  logger.info("-----没有附件回写，备份原附件成功"); 
			   }else
			   {
				   msg="备份附件失败";
				   logger.info("-----没有附件回写，备份原附件失败"); 
			   }
		   }
		}*/
		
		//add by ypf on 20130801 oa回写eas成功后记录日志（可以关闭）
		if(msg.equals("1"))
		{
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(billId);
			logInfo.setSourceBillType(billType);
			logInfo.setState("成功");
			logInfo.setLogTitle("回写单据成功");
			logInfo.setLogDetail(JSONPARAM+"");
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			logInfo.setInParam(inParam);
			try {
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}else
		{
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(billId);
			logInfo.setSourceBillType(billType);
			logInfo.setState("失败");
			logInfo.setLogTitle("回写单据失败");
			logInfo.setLogDetail(JSONPARAM+"   异常消息："+msg);
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+billId);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			logInfo.setInParam(inParam);
			try {
				WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		return msg;
	}
	
	//add by ypf on 20140617
	public String getEASID(Context ctx,String tableNameString,String oaBillId)
	{
		logger.info("---------tableNameString:"+tableNameString);
		String easId = "";
    	String sql = "select fid from "+tableNameString+" where cfoabillid='"+oaBillId+"'";
		
		try {
			IRowSet rs = DbUtil.executeQuery(ctx, sql);
			while(rs.next())
			{
			   easId = rs.getString("fid").trim();
			   break;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		logger.info("---getEASID---easId:"+easId);
		
		return easId;
	}
	
	protected String bakAttachmentByBillId(Context ctx,String billID)
	{
		String msg = "1";
		if(billID != null && !billID.equals("")){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", billID));
			view.setFilter(filter);
			BoAttchAssoCollection col = null;
			try {
				logger.info("---附件--view:"+view);
				col = BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(view);
				logger.info("------1111------备份附件的个数："+col.size());
			} catch (BOSException e) {
				e.printStackTrace();
				return e.getMessage();
			}
			
			logger.info("------------备份附件的个数："+col.size());
			if(col != null && col.size() > 0)
			{
				for (int j = 0; j < col.size(); j++) {
					BoAttchAssoInfo info = col.get(j);
					String fid = info.getId().toString();
					logger.info("------第"+(j+1)+"个------备份附件的id："+fid);
					info.setBoID(null);
					info.setSourceBillID(billID);
					info.setSaveTime(new Timestamp(new Date().getTime()));
					 
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("boID");
					selector.add("sourceBillID");
					selector.add("saveTime");
					 
					try {
						BoAttchAssoFactory.getLocalInstance(ctx).updatePartial(info, selector);
					} catch (EASBizException e) {
						logger.info("-----------附件备份异常，请查看表T_BAS_BoAttchAsso-------id："+fid);
						e.printStackTrace();
						return "-----------附件备份异常，请查看表T_BAS_BoAttchAsso-------id："+fid+"   "+e.getMessage();
					} catch (BOSException e) {
						e.printStackTrace();
						return e.getMessage();
					}
				}
			}
		}
		return msg;
	}
	
	protected boolean deleteAttachmentByBillId(Context ctx,String id)
	{
		boolean bl = false;
		String sql_select ="select * from T_BAS_BoAttchAsso where fboid='"+id+"'";

		try {
			IRowSet set = DbUtil.executeQuery(ctx, sql_select);
			try {
				while(set.next())
				{
					String attachmentId = set.getString("FAttachmentID");
					
					//删除附件表记录
					String sql = "delete T_BAS_Attachment where fid='"+attachmentId+"'";
					try {
						DbUtil.execute(ctx, sql);
						bl = true;
					} catch (BOSException e) {
						e.printStackTrace();
						bl = false;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		//单据与附件的中间表
		String sql = "delete T_BAS_BoAttchAsso where fboid='"+id+"'";
		try {
			DbUtil.execute(ctx, sql);
			bl = true;
		} catch (BOSException e) {
			e.printStackTrace();
			bl = false;
		}
		
		return bl;
	}
	
	protected String addAttachmentByBillId(Context ctx,String billId,AttachmentInfo [] AttachmentInfoList)
	{
		String msg = "1";
		BoAttchAssoInfo info_sso = null;
		for (int i = 0; i < AttachmentInfoList.length; i++) {
		    info_sso = new BoAttchAssoInfo();
		    BOSUuid bosid=BOSUuid.create(info_sso.getBOSType());
		    logger.info("----bosid:"+bosid);
//			info_sso.setId(BOSUuid.create(info_sso.getBOSType()));
		    info_sso.setId(bosid);
			info_sso.setBoID(billId);
			info_sso.setAssoType("OA回写新增附件");
			info_sso.setAttachment(AttachmentInfoList[i]);
			info_sso.setAssoBusObjType(info_sso.getBOSType().toString());
			
			logger.info("---billId:"+billId+"  info_sso.getBOSType().toString():"+info_sso.getBOSType().toString()+"  AttachmentInfoList[i]:"+AttachmentInfoList[i].getId()+"  fileLenth:"+AttachmentInfoList[i].getFile().length);
			
			try {
				logger.info("-1111---bosid:"+info_sso.getId());
				BoAttchAssoFactory.getLocalInstance(ctx).addnew(info_sso);
//				BoAttchAssoFactory.getLocalInstance(ctx).save(info_sso);
				logger.info("----add relation --第"+(i+1)+"条---单据与附件关系表："+"  id:"+info_sso.getId());
			} catch (EASBizException e1) {
				logger.info("--fail----add relation:"+(i+1)+"  id:"+info_sso.getId());
				e1.printStackTrace();
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(billId);
				logInfo.setState("失败：添加附件失败");
				logInfo.setLogTitle("添加附件发生异常");
				logInfo.setLogDetail(e1.getMessage());
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				try {
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				} catch (BOSException e) {
					e.printStackTrace();
					return e1.getMessage();
				}
				
				return e1.getMessage();
			} catch (BOSException e1) {
				logger.info("--fail----add relation:"+(i+1)+"  id:"+info_sso.getId());
				
				e1.printStackTrace();
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(billId);
				logInfo.setState("失败：添加附件失败");
				logInfo.setLogTitle("添加附件发生异常");
				logInfo.setLogDetail(e1.getMessage());
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				try {
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				} catch (BOSException e) {
					e.printStackTrace();
					return e.getMessage();
				}
				
				return e1.getMessage();
			}
			
			//附件
			AttachmentInfo info_attachment = AttachmentInfoList[i];
			String name = (info_attachment.getName()==null || info_attachment.getName()=="")?"OA没有传附件的名称.txt":info_attachment.getName();
			
			logger.info("---第"+(i+1)+"条---附件名称："+name);
			if(name!=null && name !="")
			{
				String fileName=name.substring(0,name.indexOf("."));
				String fileExtendName=name.substring(name.indexOf(".")+1, name.length());
				info_attachment.setName(fileName);//文件名
				info_attachment.setSimpleName(fileExtendName);//扩展名
				
				String type = "未知文件类型（."+fileExtendName+"）";
				if(fileExtendName.toLowerCase().equals("xls") || fileExtendName.toLowerCase().equals("xlsx"))
				{
					type = "EXCEL文件";
				}
				if(fileExtendName.toLowerCase().equals("doc") || fileExtendName.toLowerCase().equals("docx"))
				{
					type = "WORD文件";
				}
				if(fileExtendName.toLowerCase().equals("ppt"))
				{
					type = "PPT文件";
				}
				if(fileExtendName.toLowerCase().equals("pdf"))
				{
					type = "PDF文件";
				}
				if(fileExtendName.toLowerCase().equals("txt"))
				{
					type = "TEXT文件";
				}
				if(fileExtendName.toLowerCase().equals("rar")||fileExtendName.toLowerCase().equals("zip"))
				{
					type = "RAR(ZIP)文件";
				}
				if(fileExtendName.toLowerCase().equals("jpg")||fileExtendName.toLowerCase().equals("jpeg")||fileExtendName.toLowerCase().equals("bmp")||fileExtendName.toLowerCase().equals("gif"))
				{
					type = "JPG(JPEG/BMP/GIF)文件";
				}
				info_attachment.setType(type);//文件类型
			}
			
//			info_attachment.setCreateTime(new Timestamp(new Date().getTime()));
			info_attachment.setNumber(new SimpleDateFormat("yyyyMMddHHmmssms").format(new Timestamp(new Date().getTime())));
			info_attachment.setIsShared(false);
			BigDecimal sizeByte=new BigDecimal(info_attachment.getFile().length);
			BigDecimal size = sizeByte.divide(new BigDecimal(1024), BigDecimal.ROUND_UP);
			info_attachment.setSize(size+"KB");
			info_attachment.setSizeInByte(info_attachment.getFile().length);
			
			try {
				AttachmentFactory.getLocalInstance(ctx).addnew(info_attachment);
				logger.info("---第"+(i+1)+"条---附件表："+"  id:"+info_attachment.getId());
			} catch (EASBizException e) {
				e.printStackTrace();
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(billId);
				logInfo.setState("失败：添加附件失败");
				logInfo.setLogTitle("添加附件发生异常");
				logInfo.setLogDetail(e.getMessage());
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				try {
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				} catch (BOSException e1) {
					e.printStackTrace();
				}
				return e.getMessage();
			} catch (BOSException e) {
				e.printStackTrace();
				
				WSLogInfo logInfo = new WSLogInfo();
				logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
				logInfo.setSourceBillID(billId);
				logInfo.setState("失败：添加附件失败");
				logInfo.setLogTitle("添加附件发生异常");
				logInfo.setLogDetail(e.getMessage());
				logInfo.setCreateTime(CURRENT_TIME);
				logInfo.setCallType(CALLTYPE_WRITEBACK);
				try {
					WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
				} catch (BOSException e1) {
					e.printStackTrace();
				}
				
				return e.getMessage();
			}
		}
		return msg;
	}
	
	protected byte[] _getAttachment(Context ctx, String id) throws BOSException {
		byte [] bt = null;
					
		EntityViewInfo view1 = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(new FilterItemInfo("id", id));
		view1.setFilter(filter1);
		
		AttachmentInfo info = null;
		AttachmentCollection aCol = AttachmentFactory.getLocalInstance(ctx).getAttachmentCollection(view1);
		if(aCol != null && aCol.size() >0)
		{
			info = new AttachmentInfo();
			info = aCol.get(0);
			bt = info.getFile();
		}
		
		return bt;
	}
	
	protected String _getBillState(Context ctx, String id) throws BOSException {
		String state = "";
		
		EntityViewInfo view1 = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(new FilterItemInfo("id", id));
		view1.setFilter(filter1);
		
		ContractBillInfo info = null;
		ContractBillCollection aCol = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view1);
		if(aCol != null && aCol.size() >0)
		{
			info = new ContractBillInfo();
			info = aCol.get(0);
			state = (info.getState() != null)?info.getState().toString():null;
		}
		
		return state;
	}
	
//-----------------------------------
	//生成付款单
	private PaymentBillInfo createPayment(Context ctx,PayRequestBillInfo payReqBill)throws Exception{
		//当付款申请单生成付款单后，默认会关闭
		//如果把生成的付款单删除后 需要再次生成付款单 则需要反关闭此付款申请单
//		if(payReqBill.isHasClosed()){
//			return null;
//		}
		BOSObjectType bosType = new PaymentBillInfo().getBOSType();

		IBTPManager iBTPManager = BTPManagerFactory.getLocalInstance(ctx);
		BTPTransformResult result = iBTPManager.transform(payReqBill, bosType.toString());

		IObjectCollection destBillColl = result.getBills();
		BOTRelationCollection botRelateColl = result.getBOTRelationCollection();

		PaymentBillInfo destBillInfo = null;

		//2009-1-12 取财务组织  
		//其实实体财务组织可以用工程项目上的fullorgunit　modify  by sxhong 2009-06-04 18:31:24
		FullOrgUnitInfo org = payReqBill.getOrgUnit();
		CompanyOrgUnitInfo company=null;	
		if(payReqBill.getCurProject().getFullOrgUnit()!=null){
			String companyId=payReqBill.getCurProject().getFullOrgUnit().getId().toString();
			company=new CompanyOrgUnitInfo();
			company.setId(BOSUuid.read(companyId));
		}else{
			company=FDCHelper.getFIOrgUnit(ctx, org);
		}
		
		BOSObjectType  contractType=new ContractBillInfo().getBOSType();
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("isNeedPaid");
		selectors.add("account.id");
		
		//付款单生成之后，部分属性处理
		//此处处理BOTP未设置的属性 
		for (int i = 0, size = destBillColl.size(); i < size; i++) {

			destBillInfo = (PaymentBillInfo) destBillColl.getObject(i);
			if(destBillInfo.getCU()==null){
				destBillInfo.setCU(payReqBill.getCU());
			}
			if(destBillInfo.getCompany()==null){
				destBillInfo.setCompany(company);
			}
			
//			if(destBillInfo.getBizDate()==null){
//				destBillInfo.setBizDate(payReqBill.getBizDate());
				destBillInfo.setBizDate(payReqBill.getBookedDate());//房地产维护bookeddate，默认与申请单相同
//			}
		  //需求要求把付款日期改成审批时的日期
//			if(destBillInfo.getPayDate()==null){//botp未设置付款时间时取申请单付款日期,付款时取付款时的日期
//				destBillInfo.setPayDate(payReqBill.getPayDate());
//			}
			if(destBillInfo.getCurProject()==null){
				destBillInfo.setCurProject(payReqBill.getCurProject());
			}
			if(destBillInfo.getBillStatus()==null){
				destBillInfo.setBillStatus(BillStatusEnum.SAVE);
			}
			//by tim_gao 币别，汇率不管如何都要和合同的统一，即跟合同统一
			//因为付款单对付款申请单，可以多对1 ，如果每个币别，汇率不同，在计算累计发票，以及其他累计问题就头大了
			//如果日后像做成灵活的，请分析好再修改
				destBillInfo.setCurrency(payReqBill.getCurrency());
				destBillInfo.setExchangeRate(payReqBill.getExchangeRate());
			
			if(destBillInfo.getAmount()==null){
				destBillInfo.setAmount(payReqBill.getOriginalAmount());
			}
			if(destBillInfo.getLocalAmt()==null){
				if (payReqBill.getAmount()==null && payReqBill.getOriginalAmount() != null && payReqBill.getExchangeRate() != null){
					destBillInfo.setLocalAmt(payReqBill.getOriginalAmount().multiply(payReqBill.getExchangeRate()));
				}else{
					destBillInfo.setLocalAmt(payReqBill.getAmount());
				}
			}
			if(destBillInfo.getFdcPayReqID()==null){
				destBillInfo.setFdcPayReqID(payReqBill.getId().toString());
			}
			if(destBillInfo.getFdcPayReqNumber()==null){
				destBillInfo.setFdcPayReqNumber(payReqBill.getNumber());
			}
			if(destBillInfo.getContractNo()==null){
				destBillInfo.setContractNo(payReqBill.getContractNo());
			}
			if(destBillInfo.getContractBillId()==null){
				destBillInfo.setContractBillId(payReqBill.getContractId());
			}
			if(destBillInfo.getActFdcPayeeName()==null){
				destBillInfo.setActFdcPayeeName(payReqBill.getRealSupplier());
			}
			if(destBillInfo.getFdcPayeeName()==null){
				destBillInfo.setFdcPayeeName(payReqBill.getSupplier());
			}
			if(destBillInfo.getCapitalAmount()==null){
				destBillInfo.setCapitalAmount(payReqBill.getCapitalAmount());
			}
			if(destBillInfo.getAddProjectAmt()==null){
				destBillInfo.setAddProjectAmt(payReqBill.getProjectPriceInContractOri());
			}
			if(destBillInfo.getLstPrjAllPaidAmt()==null){
				destBillInfo.setLstPrjAllPaidAmt(payReqBill.getLstPrjAllPaidAmt());
			}
			//是否提交付款
			destBillInfo.setIsNeedPay(payReqBill.isIsPay());
			//备注
			if(destBillInfo.getDescription()==null){
				destBillInfo.setDescription(payReqBill.getDescription());
			}
			//款项说明
			if(destBillInfo.getSummary()==null){
				destBillInfo.setSummary(payReqBill.getMoneyDesc());
			}
			//紧急程度
			if(destBillInfo.getUrgentDegree()==null){
				destBillInfo.setUrgentDegree(UrgentDegreeEnum.getEnum(String.valueOf(payReqBill.getUrgentDegree().getValue())));
			}
			//destBillInfo.setIsEmergency(IsMergencyEnum.getEnum(String.valueOf(payReqBill.getUrgentDegree().getValue())));
			//紧急程度
			if(destBillInfo.getIsEmergency()==null){
				if(destBillInfo.getUrgentDegree()!=null&&destBillInfo.getUrgentDegree().equals(UrgentDegreeEnum.URGENT)){
					destBillInfo.put("isEmergency", new Integer(1));
				}else{
					destBillInfo.put("isEmergency", new Integer(0));
				}
			}
			//收款银行账户
			if(destBillInfo.getPayeeBank()==null){
				destBillInfo.setPayeeBank(payReqBill.getRecBank());
			}
			if(destBillInfo.getPayeeAccountBank()==null){
				destBillInfo.setPayeeAccountBank(payReqBill.getRecAccount());
			}
			
			//同城异地以及用途
			destBillInfo.setIsDifferPlace(payReqBill.getIsDifferPlace());
			if(destBillInfo.getUsage()==null){
				destBillInfo.setUsage(payReqBill.getUsage());
			}
			destBillInfo.setAccessoryAmt(payReqBill.getAttachment());
			
			//收款人
			if(payReqBill.getSupplier()!=null){
				destBillInfo.setPayeeID(payReqBill.getSupplier().getId().toString());
				destBillInfo.setPayeeNumber(payReqBill.getSupplier().getNumber());
				destBillInfo.setPayeeName(payReqBill.getSupplier().getName());
			}
			//付款类型
			if(destBillInfo.getFdcPayType()==null){
				destBillInfo.setFdcPayType(payReqBill.getPaymentType());
			}
			
			/**增加参数，根据参数控制付款申请单审批结束以后生成的付款单的制单人的取值（R090520-176）——by neo **/
			/*boolean isCreator = FDCUtils.getDefaultFDCParamByKey(ctx,ContextUtil.getCurrentFIUnit(ctx).getId().toString(),FDCConstants.FDC_PARAM_PAYMENTCREATOR);
			if(isCreator){
				destBillInfo.setCreator(payReqBill.getCreator());
			} */ //mod by ypf on 20121218001
			
			boolean isCreator = true;
			destBillInfo.setCreator(payReqBill.getCreator());
			
			
			
//			boolean isCreator = true;
			/**如果是无文本合同：
			 * 在启用财务成本一体化参数时，若勾选“无需付款”，则出来s“贷方科目”字段，
			 * 审批后，该字段金额自动填入“付款科目”，相应的付款单亦自动“已付款”状态；
			 * 若不启用一体化参数，若勾选“无需付款”，无文本合同审批后，对应的付款单自动变为“已付款”。*/
			
			ContractWithoutTextInfo model = null;
			if(!BOSUuid.read(payReqBill.getContractId()).getType().equals(contractType)){
				model = (ContractWithoutTextInfo)ContractWithoutTextFactory.getLocalInstance(ctx).
						getContractWithoutTextInfo(new ObjectUuidPK(payReqBill.getContractId()),selectors);
				if(destBillInfo.getPayerAccount()==null){
					destBillInfo.setPayerAccount(model.getAccount());
				}
			}	
			
			//提交状态
			final IObjectPK pk = iBTPManager.saveRelations(destBillInfo, botRelateColl);
			destBillInfo.setId(BOSUuid.read(pk.toString()));			
			
			boolean is = FDCUtils.IsFinacial(ctx,company.getId().toString());
			if(model!=null && model.isIsNeedPaid() ){
				List list = new ArrayList();
				list.add(pk.toString());
				if((is && model.getAccount()!=null ) || !is ){
					PaymentBillFactory.getLocalInstance(ctx).audit4FDC(list);
				}
			}else{
				if(!isCreator){
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("createTime");
					selector.add("creator");
					destBillInfo.setCreateTime(null);
					destBillInfo.setCreator(null);
					PaymentBillFactory.getLocalInstance(ctx).updatePartial(destBillInfo, selector);
				}
			}
			
			//收款人名称
			//add by ypf on 20121220
			//mod by ypf on 20130815  发现前面有重复，故注释
			/*SupplierInfo supplierInfo = new SupplierInfo();
			if(destBillInfo.getPayeeNumber()==null && destBillInfo.getPayeeName()==null){
				IObjectPK pk1= new ObjectUuidPK();
				pk1.setKeyValue("id", payReqBill.getSupplier().getId());
				supplierInfo = SupplierFactory.getLocalInstance(ctx).getSupplierInfo(pk1);
				
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("payeeName");
				selector.add("payeeNumber");
				destBillInfo.setPayeeName(supplierInfo.getName());
				destBillInfo.setPayeeNumber(supplierInfo.getNumber());
				PaymentBillFactory.getLocalInstance(ctx).updatePartial(destBillInfo, selector);
			}*/ 
			
			
		}
		
		return destBillInfo;
	}
	
	protected void updatePartial(Context ctx,PayRequestBillInfo billInfo,SelectorItemCollection selector)
	{
		try {
			PayRequestBillFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	protected void payReqBillaudAudit(Context ctx, BOSUuid billId, String OABILLID) throws BOSException, EASBizException
	{
//		checkBillForAudit( ctx,billId,null);
		PayRequestBillInfo billInfo = new PayRequestBillInfo();
		billInfo.setId(billId);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		// 审核日期
		billInfo.setAuditTime(DateTimeUtils.truncateDate(new Date()));
		// 状态
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		updatePartial(ctx, billInfo, selector);
		//处理扣款项,改为调整款项选择的时候就处理
		selector = new SelectorItemCollection();
		selector.add("contractId");
		selector.add("*");
		selector.add("curProject.id");
		//取出工程项目的财务组织供生成付款单时用　 by sxhong 2009-06-04 18:28:23
		selector.add("curProject.fullOrgUnit.id");
		selector.add("orgUnit.id");
		selector.add("realSupplier.number");
		selector.add("realSupplier.name");
		selector.add("supplier.number");
		selector.add("supplier.name");
		selector.add("CU.name");
		selector.add("CU.number");
		//期间
		selector.add("period.number");
		selector.add("period.beginDate");
		selector.add("period.endDate");
		// by tim_gao 币种 
		selector.add("currency.*");
		
		PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getLocalInstance(ctx)
			.getPayRequestBillInfo(new ObjectUuidPK(billId),selector);
		//payRequestBillInfo.getRealSupplier().getName();
		
		//截止上期累计实付在付款申请单保存或者是提交的时候取得是合同上的prjPriceInConPaid，并且只有保存或者提交的时候才会将合同上的prjPriceInConPaid
		//同步保存到付款申请单上的lstPrjAllPaidAmt字段上去.系统如此处理在以下场景会存在问题:B付款申请单在A付款申请单没有付款之前就提交了，因为A还没有付款
		//合同上的 prjPriceInConPaid为空，那么B在保存提交的时候同步给B付款申请单的值就为空，此时将A付款，然后审批B发现截止上期累计实付还是为空。如果
		//这时候将B付款申请单再重新提交一下就好了.
		//为了防止这种问题产生，我们可以在审批的时候按照如下方式显式保存一下                  by cassiel_peng 2010-1-5
		
		FDCSQLBuilder _builder=new FDCSQLBuilder(ctx);
		_builder.appendSql("select fprjPriceInConPaid as amount from T_CON_ContractBill where fid=?");
		_builder.addParam(payRequestBillInfo.getContractId());
		IRowSet rowSet = _builder.executeQuery();
		if(rowSet!=null&&rowSet.size()==1){
			try {
				rowSet.next();
				billInfo.setLstPrjAllPaidAmt(rowSet.getBigDecimal("amount"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		SelectorItemCollection _selector = new  SelectorItemCollection();
		_selector.add("lstPrjAllPaidAmt");
		updatePartial(ctx, billInfo, _selector);
		

		//判断参数是否启用
		HashMap param = FDCUtils.getDefaultFDCParam(ctx,payRequestBillInfo.getOrgUnit().getId().toString());
		if(param.get(FDCConstants.FDC_PARAM_CONPAYPLAN)!=null){
			boolean hasConPlan = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_CONPAYPLAN).toString()).booleanValue();
			// 无文本没有付款计划
			if(hasConPlan&&FDCUtils.isContractBill(ctx, payRequestBillInfo.getContractId())){
				if (payRequestBillInfo.getCurPlannedPayment()==null || 
						FDCConstants.ZERO.compareTo(payRequestBillInfo.getCurPlannedPayment())==0) {
					throw new PayRequestBillException(PayRequestBillException.MUSTCONPAYPLAN);
				}
			}
		}
		
		//鑫苑要求 根据当前时间设置对应期间
		String companyID = payRequestBillInfo.getCurProject().getFullOrgUnit().getId().toString();
		Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyID);
		boolean isInCore = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INCORPORATION);
		if(isInCore){//去掉参数，启用月结统一按以下逻辑处理
			String prjId = payRequestBillInfo.getCurProject().getId().toString();
			// 财务期间
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, false);
			PeriodInfo billPeriod = payRequestBillInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//最终所在期间
			Date bookedDate = DateTimeUtils.truncateDate(payRequestBillInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","单据所对应的组织没有当前时间的期间，请先设置！"));
			}
			/***************
			 * （1）当付款申请单上的“业务日期”和“业务期间”大于“审批日期”（工程项目成本财务“当前期间”）时，付款单的“申请期间”和“申请日期”取付款申请单的“业务期间”和“业务日期”
			 * （2）当付款申请单上的“业务日期”和“业务期间”小于等于“审批日期”（工程项目成本财务“当前期间”）时，付款单的“申请期间”和“申请日期”取（工程项目成本财务“当前期间”）和审批日期，且将审批日期返写回付款申请单上的“业务日期”和“业务期间”。
			 *	
			 *	原理与拆分保存时相同，期间老出问题
			 */
			if(billPeriod!=null&&billPeriod.getNumber()>finPeriod.getNumber()){
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			}else if(finPeriod!=null){
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_CON_PayRequestBill set FPeriodId = ?,FBookedDate = ? where fId=? ");
			builder.addParam(shouldPeriod.getId().toString());
			builder.addParam(bookedDate);
			builder.addParam(billId.toString());
			builder.execute();
			//设置期间后新增
			payRequestBillInfo.setBookedDate(bookedDate);
		}
		PaymentBillInfo payBillInfo = null;
		try {
			payBillInfo = createPayment(ctx,payRequestBillInfo);
		} catch (Exception e) {
			WSLogInfo logInfo = new WSLogInfo();
			logInfo.setId(BOSUuid.create(logInfo.getBOSType()));
			logInfo.setSourceBillID(billId.toString());
			logInfo.setSourceBillType("EAS5");
			logInfo.setState("警告");
			logInfo.setLogTitle("回写无文本合同时生成付款单时出错，但是该异常不影响业务正常进行");
			logInfo.setLogDetail("生成付款单时出错:"+e.getMessage());
			logInfo.setUrl("http://oa.carec.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+OABILLID);
			logInfo.setCreateTime(CURRENT_TIME);
			logInfo.setCallType(CALLTYPE_WRITEBACK);
			WSLogFactory.getLocalInstance(ctx).addnew(logInfo);
			
			e.printStackTrace();
			logger.info("------生成付款单时出错:"+e);
			
			//mod by ypf on 20130815 系统一个异常，不影响正常业务走下去，如果不关掉异常，oa那边就会捕捉到这个消息 执行不下去
			throw new EASBizException(new NumericExceptionSubItem("100","1"),e);
			
			//mod by ypf on 20130815 系统一个异常，不影响正常业务走下去，如果不关掉异常，oa那边就会捕捉到这个消息 执行不下去
            //throw new EASBizException(new NumericExceptionSubItem("100","生成付款单时出错:"+e.getMessage()),e);//new BOSException(e.getMessage());//new PayRequestBillException(PayRequestBillException.CHECKTEXTLENGTH1);
		}
			
		if(payBillInfo != null){
			payBillId = payBillInfo.getId();
			
			try {
				updateFDCPaymentBillinvoice(ctx, payRequestBillInfo, payBillInfo);
			} catch (Exception e) {
				throw new EASBizException(new NumericExceptionSubItem("100","更新房地产付款单中间表时出错:"+e.getMessage()),e);
			}
		}
		
	}
	
	//by tim_gao 这个方法做的有问题,按理说,付款申请单与付款单应该去同时写一个fdcpaymentbill 同时显示也调用一个,
	//而不是回来再由付款单去回写这样真的很麻烦,引出一堆关于累计的问题updateFDCPaymentBillinvoice()
	
	private void updateFDCPaymentBillinvoice(Context ctx, PayRequestBillInfo payReqBill, PaymentBillInfo paymentBill) throws EASBizException, BOSException{
		//审批生成付款单中间表时，带出付款申请单的发票的字段
		FDCPaymentBillInfo fdcPayment = new FDCPaymentBillInfo();
		fdcPayment.setInvoiceNumber(payReqBill.getInvoiceNumber());
		fdcPayment.setInvoiceAmt(payReqBill.getInvoiceAmt());
		fdcPayment.setAllInvoiceAmt(payReqBill.getAllInvoiceAmt());
		fdcPayment.setInvoiceOriAmt(payReqBill.getInvoiceOriAmt());
		fdcPayment.setAllInvoiceOriAmt(payReqBill.getAllInvoiceOriAmt());
		fdcPayment.setInvoiceDate(payReqBill.getInvoiceDate());
		FDCPaymentBillHelper.handleFDCPaymentBillInvoice(ctx, fdcPayment, paymentBill);
	}
	
	// 审核
	protected void contractWithOutTextAudit(Context ctx, BOSUuid billId,String OABILLID) throws BOSException,
			EASBizException {

//		checkBillForAudit(ctx, billId, null);
//		super._audit(ctx, billId);
		
		ContractWithoutTextInfo billInfo = new ContractWithoutTextInfo();
		billInfo.setId(billId);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		// 审核日期
		billInfo.setAuditTime(DateTimeUtils.truncateDate(new Date()));
		// 状态
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		//更新无文本合同的审批信息
		ContractWithoutTextFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
		
		// 同步标记付款申请单为审批状态
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("contractId", billId.toString()));
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.setFilter(filterInfo);

		IPayRequestBill iPayReq = PayRequestBillFactory.getLocalInstance(ctx);
		PayRequestBillCollection prbc = iPayReq.getPayRequestBillCollection(evi);

		if (prbc.size() > 0) {
//			iPayReq.audit(prbc.get(0).getId());
			//调用付款申请单的方法生成付款单
			payReqBillaudAudit(ctx,prbc.get(0).getId(),OABILLID);
		}
		ContractExecInfosFactory.getLocalInstance(ctx).updateContract(ContractExecInfosInfo.EXECINFO_AUDIT, billId.toString());
	}
	
	//OA审批通过后反写eas的变更审批单和变更签证单:审核时间、审批状态、测算金额
	protected String changeAuditBillAudit(Context ctx, BOSUuid billId,net.sf.json.JSONObject JSONPARAM){
		try {
			//1、调用标准审批方法，可以更新变更审批单 和 变更签证单的审核时间、状态
//			ChangeAuditBillFactory.getLocalInstance(ctx).audit(billId);// 奶奶的，一个劲的报错
			try {
				audit(ctx,billId);//add by ypf on 20140623 重写bean里面的审核方法  
			} catch (Exception e) {
				e.printStackTrace();
			    logger.info("---------调用变更审批单audit方法异常："+e);
			    return e.getMessage();
			}
			
			logger.info("-----4---变更审批单，changeAuditBillAudit进来了----");
			//2、更新变更审批单分录中的测试金额原币和本币 
			ChangeSupplierEntryCollection changeSupplierEntryCollection = ChangeSupplierEntryFactory.getLocalInstance(ctx).getChangeSupplierEntryCollection(" where parent='"+billId+"'");
			logger.info("------5--变更审批单，changeAuditBillAudit进来了----");
			for (int i = 0; i < changeSupplierEntryCollection.size(); i++) {
				ChangeSupplierEntryInfo changeSupplierEntryInfo = changeSupplierEntryCollection.get(i);
				
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("costAmount");
				selector.add("oriCostAmount");
				
				//json里面传了EASBak_*和contract_number_* 的值  add by ypf on 20140515 增加注释说明，oa回写的json有合同编号、测算金额、状态
				BigDecimal amount = new BigDecimal(0);
				String contract_number = JSONPARAM.getString("contract_number_"+(i+1)).trim();
				
				// 循环查找匹配的合同
				for (int j = 0; j < changeSupplierEntryCollection.size(); j++) {
					ContractBillInfo contractBill = changeSupplierEntryInfo.getContractBill();
//					ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractBill.getId()));
					//mod by ypf on 20150721 上面那种写法抛异常，很奇怪
					ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(" where id='"+contractBill.getId()+"'");
					
					if(contract_number.equals(contractBillInfo.getNumber().trim()))
					{
						amount = new BigDecimal(JSONPARAM.getString("EASBak_"+(i+1)));
						break;
					}
				}
				
				//mod by ypf on 20140820  去掉测试金额大于0的控制，因为实际场景中会有为0，甚至为负数的情况
//				if(amount.compareTo(new BigDecimal(0))>0)
//				{
					//2.1、更新变更审批单分录中的测试金额原币和本币 
					changeSupplierEntryInfo.setCostAmount(amount.multiply(changeSupplierEntryInfo.getExRate()));
					changeSupplierEntryInfo.setOriCostAmount(amount);
					ChangeSupplierEntryFactory.getLocalInstance(ctx).updatePartial(changeSupplierEntryInfo, selector);
					
					//mod by ypf on 20150623-todo
					DbUtil.execute(ctx, "update T_CON_ChangeSupplierEntry set cfChgAftAmt = "+amount+" where fid = '"+changeSupplierEntryInfo.getId()+"'");
					
					//3、更新变更签证单的测算金额
					ContractChangeBillInfo contractChange = changeSupplierEntryInfo.getContractChange();
					ContractChangeBillInfo contractChangeBillInfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(new ObjectUuidPK(contractChange.getId()));
					
					SelectorItemCollection selector1 = new SelectorItemCollection();
					selector1.add("amount");
					selector1.add("originalAmount");
					selector1.add("state");
					contractChangeBillInfo.setAmount(amount.multiply(contractChangeBillInfo.getExRate()));
					contractChangeBillInfo.setOriginalAmount(amount);
					contractChangeBillInfo.setState(FDCBillStateEnum.AUDITTED);
					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(contractChangeBillInfo, selector1);
//				}
				logger.info("------6--变更审批单，changeAuditBillAudit进来了----");
			}
		} catch (EASBizException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (BOSException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "1";
	}
	
	//OA审批通过后反写eas的变更审批单和变更签证单:审核时间、审批状态、测算金额
	protected String changeAuditBillAuditForEAS8(Context ctx, BOSUuid billId,net.sf.json.JSONObject JSONPARAM){
		try {
			//1、调用标准审批方法，可以更新变更审批单 和 变更签证单的审核时间、状态
//			ChangeAuditBillFactory.getLocalInstance(ctx).audit(billId);// 奶奶的，一个劲的报错
			try {
				audit(ctx,billId);//add by ypf on 20140623 重写bean里面的审核方法  
			} catch (Exception e) {
				e.printStackTrace();
			    logger.info("---------调用变更审批单audit方法异常："+e);
			    return e.getMessage();
			}
			
			logger.info("-----4---变更审批单，changeAuditBillAudit进来了----");
			//2、更新变更审批单分录中的测试金额原币和本币 
			ChangeSupplierEntryCollection changeSupplierEntryCollection = ChangeSupplierEntryFactory.getLocalInstance(ctx).getChangeSupplierEntryCollection(" where parent='"+billId+"'");
			logger.info("------5--变更审批单，changeAuditBillAudit进来了----");
			for (int i = 0; i < changeSupplierEntryCollection.size(); i++) {
				ChangeSupplierEntryInfo changeSupplierEntryInfo = changeSupplierEntryCollection.get(i);
				
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("costAmount");
				selector.add("oriCostAmount");
				
				//json里面传了EASBak_*和contract_number_* 的值  add by ypf on 20140515 增加注释说明，oa回写的json有合同编号、测算金额、状态
				BigDecimal amount = new BigDecimal(0);
				String contract_number = JSONPARAM.getString("contract_number_"+(i+1)).trim();
				
				// 循环查找匹配的合同
				for (int j = 0; j < changeSupplierEntryCollection.size(); j++) {
					ContractBillInfo contractBill = changeSupplierEntryInfo.getContractBill();
					ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractBill.getId()));
					
					if(contract_number.equals(contractBillInfo.getNumber().trim()))
					{
						amount = new BigDecimal(JSONPARAM.getString("EASBak_"+(i+1)));
						break;
					}
				}
				
				//mod by ypf on 20140820  去掉测试金额大于0的控制，因为实际场景中会有为0，甚至为负数的情况
//				if(amount.compareTo(new BigDecimal(0))>0)
//				{
					//2.1、更新变更审批单分录中的测试金额原币和本币 
					changeSupplierEntryInfo.setCostAmount(amount.multiply(changeSupplierEntryInfo.getExRate()));
					changeSupplierEntryInfo.setOriCostAmount(amount);
					ChangeSupplierEntryFactory.getLocalInstance(ctx).updatePartial(changeSupplierEntryInfo, selector);
					
					//mod by ypf on 20150623-todo
					DbUtil.execute(ctx, "update T_CON_ChangeSupplierEntry set cfChgBefAmt = "+amount+" where fid = '"+changeSupplierEntryInfo.getId()+"'");
					
					//3、更新变更签证单的测算金额
					ContractChangeBillInfo contractChange = changeSupplierEntryInfo.getContractChange();
					ContractChangeBillInfo contractChangeBillInfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(new ObjectUuidPK(contractChange.getId()));
					
					SelectorItemCollection selector1 = new SelectorItemCollection();
					selector1.add("amount");
					selector1.add("originalAmount");
					selector1.add("state");
					contractChangeBillInfo.setAmount(amount.multiply(contractChangeBillInfo.getExRate()));
					contractChangeBillInfo.setOriginalAmount(amount);
//					contractChangeBillInfo.setState(FDCBillStateEnum.AUDITTED);
					contractChangeBillInfo.setState(FDCBillStateEnum.SAVED);//mod by ypf on 2015年7月15日22:54:21,调整为已保存
					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(contractChangeBillInfo, selector1);
//				}
				logger.info("------6--变更审批单，changeAuditBillAudit进来了----");
			}
		} catch (EASBizException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (BOSException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "1";
	}
	
	//变更审批单（EAS6）用的，重写了controlerbean里面的audit方法
	protected void audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		logger.info("---1-----变更审批单，audit进来了----");
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId));
//		checkBillForAudit( ctx,billId,billInfo);//mod by ypf on 20140623  不控制校验
		logger.info("---2-----变更审批单，audit进来了----");
		
		billInfo.setChangeState(ChangeBillStateEnum.Audit);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("changeState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
		//之前系统中如果变更审批单下发单位分录为空是不允许提交的,现在中渝模式下是没有此限制,故必须处理为空的情况  by Cassiel_peng 2009-9-25
		if(c!=null){
			logger.info("---21-----变更审批单，audit进来了----");
			int num = billInfo.getSuppEntry().size();
			if(num>0){
				Set set=new HashSet();
				for(int i=0;i<num;i++){
					ChangeSupplierEntryInfo entry = c.get(i);
					set.add(entry.getContractBill().getId().toString());
				}
				logger.info("---22-----变更审批单，audit进来了----");
				EntityViewInfo view=new EntityViewInfo();
				view.setFilter(new FilterInfo());
				view.getFilter().getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
				view.getSelector().add("hasSettled");
				view.getSelector().add("number");
				logger.info("---23-----变更审批单，audit进来了----");
				ContractBillCollection cons=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
				logger.info("---24-----变更审批单，audit进来了----");
				boolean hasSettled=false;
				String conNumber="";
				for(int i=0;i<cons.size();i++){
					if(cons.get(i).isHasSettled()){
						hasSettled=true;
						logger.info("---25-----变更审批单，audit进来了----");
						conNumber+=",'"+cons.get(i).getNumber()+"'";
					}
				}
				if(hasSettled){
					logger.info("---26-----变更审批单，audit进来了----\r\n"+"审批不通过：变更审批单登记下发单位内存在已结算的合同，合同编码："+conNumber.substring(1));
					
					//mod by ypf on 2015年6月21日18:22:18，客户要求去掉，不控制
//					throw new EASBizException(new NumericExceptionSubItem("111","审批不通过：变更审批单登记下发单位内存在已结算的合同，合同编码："+conNumber.substring(1)));
				}
				List list = new ArrayList();
				for(int i=0; i<num; i++){
					ChangeSupplierEntryInfo entry = c.get(i);
					if(entry.getContractChange()!=null){
						ContractChangeBillInfo change = entry.getContractChange();
						list.add(change.getId().toString());
					}				
				}
				logger.info("---27-----变更审批单，audit进来了----");
				Map initParam = FDCUtils.getDefaultFDCParam(ctx,null);//批量取参	
				boolean isGenerateAfterAuidt = true;
				if(initParam.get(FDCConstants.FDC_PARAM_AUTOCHANGETOPROJECTVISA)!=null){
					isGenerateAfterAuidt = Boolean.valueOf(initParam.get(FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT).toString()).booleanValue();
				}
				if(c!=null&&c.size()>0){
					if(isGenerateAfterAuidt){
						logger.info("---28-----变更审批单，audit进来了----");
						
						ChangeBill(ctx, billInfo, FDCBillStateEnum.SAVED);
						logger.info("---29-----变更审批单，audit进来了----");
					}
				}
				//mod by ypf 20140623 不注释掉，oa回写eas时会抛异常
				//2008-11-19 改为ContractChangeBill的审批方法
				/*if(list!=null && list.size()>0){
					if(!isGenerateAfterAuidt){
						logger.info("---30-----变更审批单，audit进来了----");
						ContractChangeBillFactory.getLocalInstance(ctx).audit(list);
						logger.info("---2-----变更审批单，audit进来了----");
					}
				}*/
			}
		}
		
//		_updatePartial(ctx, billInfo, selector);
//		updatePeriod(ctx, billId);
		logger.info("-----3---变更审批单，audit进来了----");
		ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
		logger.info("-----4---变更审批单，audit进来了----");
	}
	
	
	/**
	 * 根据当前项目成本期间更新暂缓单据的业务日期和订立期间
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void updatePeriod(Context ctx, BOSUuid billId) throws EASBizException, BOSException {
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("isRespite");
		selectors.add("curProject.id");
		selectors.add("curProject.fullOrgUnit.id");
		selectors.add("bookedDate");
		selectors.add("period.*");
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId), selectors);
		
		String companyID = billInfo.getCurProject().getFullOrgUnit().getId().toString();
		Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyID);
		boolean isInCore = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INCORPORATION);
		if(isInCore){
			//启用月结统一按以下逻辑处理
			String prjId = billInfo.getCurProject().getId().toString();
			//成本期间
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
			PeriodInfo billPeriod = billInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//最终所在期间
			Date bookedDate = DateTimeUtils.truncateDate(billInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","单据所对应的组织没有当前时间的期间，请先设置！"));
			}
			/***************
			 * （1）当工程量确认单上的“业务日期”和“业务期间”大于工程项目成本财务“当前期间”时，“业务期间”不变
			 * （2）当工程量确认单上的“业务日期”和“业务期间”小于等于工程项目成本财务“当前期间”时，“业务期间”更新为工程项目成本财务“当前期间”
			 *	
			 *	原理与拆分保存时相同，期间老出问题
			 */
			if (billPeriod != null && billPeriod.getNumber() > finPeriod.getNumber()) {
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			} else if (finPeriod != null) {
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			
			//更新变更审批单的业务日期，变更期间和暂缓状态
			
			selectors = new SelectorItemCollection();
			selectors.add("period");
			selectors.add("bookedDate");
			selectors.add("isRespite");
			billInfo.setBookedDate(bookedDate);
			billInfo.setPeriod(shouldPeriod);
			billInfo.setIsRespite(false);
//			_updatePartial(ctx, billInfo, selectors);
			//mod by ypf on 20140623
			ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(billInfo, selectors);
		}
	}
	
	private void ChangeBill(Context ctx, IObjectValue model, FDCBillStateEnum state) throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo) model;
		
		ChangeSupplierEntryCollection c = info.getSuppEntry();
		//之前系统中如果变更审批单下发单位分录为空是不允许提交的,现在中渝模式下是没有此限制的,故必须处理为空的情况 by Cassiel_peng 2009-9-25
		if(c!=null){
			for(int i=0;i<c.size();i++){			
				ChangeSupplierEntryInfo entry = c.get(i);
				if(entry.getContractChange()==null){
					ContractChangeBillInfo change = new ContractChangeBillInfo();
					change.setConductTime(FDCHelper.getCurrentDate());
					change.setSettleTime(FDCHelper.getCurrentDate());	    	
					change.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
					change.setSourceType(info.getSourceType());
//					change.setOrgUnit(ContextUtil.getCurrentCostUnit(ctx).castToFullOrgUnitInfo());
					change.setOrgUnit(info.getOrgUnit());
					change.setChangeAudit(info);
					if(info.getNumber()!=null)
						change.setChangeAuditNumber(info.getNumber());
					change.setState(state);
					change.setChangeType(info.getAuditType());
					if(info.getAuditTypeName()!=null)
						change.setChangeTypeName(info.getAuditTypeName());
					//效成本金额只登记在第1个变更指令单上，并且在变更指令单上增加“无效成本金额”、“无效成本原因”  20080324
					if(i==0){
						change.setInvalidCostReason(info.getInvalidCostReason());
						change.setCostNouse(info.getCostNouse());
					}else{
						change.setInvalidCostReason(null);
						change.setCostNouse(GlUtils.zero);
					}
					//期间
					change.setBookedDate(info.getBookedDate());
					change.setPeriod(info.getPeriod());
					
					change.setChangeReason(info.getChangeReason());
					change.setChangeSubject(info.getChangeSubject());
					change.setConductDept(info.getConductDept());
					change.setUrgentDegree(info.getUrgentDegree());
					change.setCurProject(info.getCurProject());
					if(info.getCurProjectName()!=null)
						change.setCurProjectName(info.getCurProjectName());
					change.setJobType(info.getJobType());
					if(info.getJobTypeName()!=null)
						change.setJobTypeName(info.getJobTypeName());
					change.setSpecialtyType(info.getSpecialtyType());
					if(info.getSpecialtyTypeName()!=null)
						change.setSpecialtyTypeName(info.getSpecialtyTypeName());
					change.setGraphCount(info.getGraphCount());
					change.setConductTime(info.getCreateTime());
					change.setMainSupp(entry.getMainSupp());
					change.setContractBill(entry.getContractBill());
					if(entry.getContractBill()!=null&&entry.getContractBill().getNumber()!=null){
						change.setContractBillNumber(entry.getContractBill().getNumber());
						change.setIsConSetted(entry.getContractBill().isHasSettled());
						change.setIsCostSplit(entry.getContractBill().isIsCoseSplit());
					}
					change.setBalanceType(entry.getBalanceType());
					change.setIsDeduct(entry.isIsDeduct());
					change.setDeductAmount(entry.getDeductAmount());
					change.setDeductReason(entry.getDeductReason());
					//原币金额
					change.setAmount(entry.getCostAmount());
					change.setOriginalAmount(entry.getOriCostAmount());
					//汇率
					change.setCurrency(entry.getCurrency());
					change.setExRate(entry.getExRate());
					//增加原始联系单号 eric_wang 2010.05.31
					change.setOriginalContactNum(entry.getOriginalContactNum());
					change.setIsSureChangeAmt(entry.isIsSureChangeAmt());
					change.setIsImportChange(info.isIsImportChange());
					change.setConstructPrice(entry.getConstructPrice());
					
					SupplierContentEntryCollection coll = new SupplierContentEntryCollection();
					EntityViewInfo vit = new EntityViewInfo();
					FilterInfo fit = new FilterInfo();
					FilterItemCollection itt = fit.getFilterItems();	
					if(info.getId()!=null&&entry.getId()!=null){
						itt.add(new FilterItemInfo("parent.id", entry.getId().toString(),CompareType.EQUALS));
						vit.setFilter(fit);
						vit.getSelector().add("content.*");
						SorterItemInfo sortName = new SorterItemInfo("seq");
		                sortName.setSortType(SortType.ASCEND);
						vit.getSorter().add(sortName);
						coll = SupplierContentEntryFactory.getLocalInstance(ctx).getSupplierContentEntryCollection(vit);
					}else{
						coll = entry.getEntrys();
					}
					ContractChangeEntryCollection entrycoll = change.getEntrys();
					for(int j=0;j<coll.size();j++){
						SupplierContentEntryInfo con = coll.get(j);
						ContractChangeEntryInfo test = new ContractChangeEntryInfo();
						test.setNumber(con.getContent().getNumber());
						test.setChangeContent(con.getContent().getChangeContent());
						test.setIsBack(con.getContent().isIsBack());
						test.setSeq(con.getContent().getSeq());
						entrycoll.add(test);
					}
					change.setName(info.getName()+"_"+(i+1));
					String orgId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
					String billNumber = null;
					/***
					 * 变更指令单生成时，调用编码规则报错，请检查编码规则是否有问题,例如：顺序号是否已经使用完
					 * 异常被吃掉，使不应该的，这里输出后台日至，备查
					 */
					try {
						billNumber = FDCHelper.getNumberByCodingRule(ctx, change, orgId);
					} catch (Exception e1){
						logger.error("变更指令单生成时，调用编码规则报错，请检查编码规则是否有问题,例如：顺序号是否已经使用完！", e1);
						if(e1 instanceof BOSException)
							throw (BOSException)e1;
						if(e1 instanceof EASBizException)
							throw (EASBizException)e1;
					}
					if(billNumber == null) {			
						billNumber = info.getNumber()+"_"+(i+1);
					}			
					change.setNumber(billNumber);
					//中渝增加字段
					if(info.getConductUnit()!=null){
						change.setConductUnit(info.getConductUnit());
					}
					if(info.getOffer()!=null){
						change.setOffer(info.getOffer());
					}
					if(info.getConstrUnit()!=null){
						change.setConstrUnit(info.getConstrUnit());
					}
					if(info.getConstrSite()!=null){
						change.setConstrSite(info.getConstrSite());
					}
					ContractChangeBillFactory.getLocalInstance(ctx).addnew(change);
					entry.setContractChange(change);
					//审批的变更审批单生成指令单之后不会将指令单和下发单位分录关联起来，所以在此处下发单位分录自己维护。
					//否则会审批一次生成一 次     by Cassiel_peng  2009-9-26
					if(entry!=null&&entry.getId()!=null){
						SelectorItemCollection _selector=new SelectorItemCollection();
						_selector.add("contractChange");
						ChangeSupplierEntryFactory.getLocalInstance(ctx).updatePartial(entry, _selector);
					}
				}
				else{
					ContractChangeBillInfo change = entry.getContractChange();
//					for(i=0;i<change.getEntrys().size();i++){
//						change.getEntrys().removeObject(i);
//						i--;
//					}
					FilterInfo fi = new FilterInfo();
					FilterItemCollection it = fi.getFilterItems();	
					if(change.getId()!=null){
						it.add(new FilterItemInfo("parent.id", change.getId().toString(),CompareType.EQUALS));
						ContractChangeEntryFactory.getLocalInstance(ctx).delete(fi);
					}
					if(info.getNumber()!=null)
						change.setChangeAuditNumber(info.getNumber());
					//效成本金额只登记在第1个变更指令单上，并且在变更指令单上增加“无效成本金额”、“无效成本原因”  20080324
					if(i==0){
						change.setInvalidCostReason(info.getInvalidCostReason());
						change.setCostNouse(info.getCostNouse());
					}else{
						change.setInvalidCostReason(null);
						change.setCostNouse(GlUtils.zero);
					}
					
					//期间
					change.setBookedDate(info.getBookedDate());
					change.setPeriod(info.getPeriod());
					change.setSourceType(info.getSourceType());
					
					change.setChangeType(info.getAuditType());
					if(info.getAuditTypeName()!=null)
						change.setChangeTypeName(info.getAuditTypeName());
					change.setChangeReason(info.getChangeReason());
					change.setChangeSubject(info.getChangeSubject());
					change.setConductDept(info.getConductDept());
					change.setUrgentDegree(info.getUrgentDegree());
					change.setCurProject(info.getCurProject());
					if(info.getCurProjectName()!=null)
						change.setCurProjectName(info.getCurProjectName());
					change.setJobType(info.getJobType());
					if(info.getJobTypeName()!=null)
						change.setJobTypeName(info.getJobTypeName());
					change.setState(state);
					change.setSpecialtyType(info.getSpecialtyType());
					if(info.getSpecialtyTypeName()!=null)
						change.setSpecialtyTypeName(info.getSpecialtyTypeName());
					change.setGraphCount(info.getGraphCount());
					change.setConductTime(info.getCreateTime());
					change.setMainSupp(entry.getMainSupp());
					change.setContractBill(entry.getContractBill());
					if(entry.getContractBill()!=null&&entry.getContractBill().getNumber()!=null){
						change.setContractBillNumber(entry.getContractBill().getNumber());
						change.setIsConSetted(entry.getContractBill().isHasSettled());
						change.setIsCostSplit(entry.getContractBill().isIsCoseSplit());
					}
					change.setBalanceType(entry.getBalanceType());
					change.setIsDeduct(entry.isIsDeduct());
					change.setDeductAmount(entry.getDeductAmount());
					change.setDeductReason(entry.getDeductReason());
					//原币金额
					change.setAmount(entry.getCostAmount());
					change.setOriginalAmount(entry.getOriCostAmount());
					//汇率
					change.setCurrency(entry.getCurrency());
					change.setExRate(entry.getExRate());
					//原始联系单号 eric_wang 2010.05.31
					change.setOriginalContactNum(entry.getOriginalContactNum());
					change.setIsSureChangeAmt(entry.isIsSureChangeAmt());
					change.setIsImportChange(info.isIsImportChange());
					change.setConstructPrice(entry.getConstructPrice());
					
					EntityViewInfo vit = new EntityViewInfo();
					FilterInfo fit = new FilterInfo();
					FilterItemCollection itt = fit.getFilterItems();	
					if(entry.getId()!=null)
						itt.add(new FilterItemInfo("parent.id", entry.getId().toString(),CompareType.EQUALS));
					vit.setFilter(fit);
					vit.getSelector().add("content.*");
					SorterItemInfo sortName = new SorterItemInfo("seq");
	                sortName.setSortType(SortType.ASCEND);
					vit.getSorter().add(sortName);
					SupplierContentEntryCollection coll = SupplierContentEntryFactory.getLocalInstance(ctx).getSupplierContentEntryCollection(vit);
					for(int j=0;j<coll.size();j++){
						SupplierContentEntryInfo con = coll.get(j);
						ContractChangeEntryInfo test = new ContractChangeEntryInfo();
						test.setNumber(con.getContent().getNumber());
						test.setChangeContent(con.getContent().getChangeContent());
						test.setIsBack(con.getContent().isIsBack());
						test.setSeq(con.getContent().getSeq());
						test.setParent(change);
						ContractChangeEntryFactory.getLocalInstance(ctx).addnew(test);
					}
					//中渝增加字段
					if(info.getConductUnit()!=null){
						change.setConductUnit(info.getConductUnit());
					}
					if(info.getOffer()!=null){
						change.setOffer(info.getOffer());
					}
					if(info.getConstrUnit()!=null){
						change.setConstrUnit(info.getConstrUnit());
					}
					if(info.getConstrSite()!=null){
						change.setConstrSite(info.getConstrSite());
					}
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("changeType");
					selector.add("changeReason");
					selector.add("changeSubject");
					selector.add("conductDept");
					selector.add("urgentDegree");
					selector.add("curProject");
					selector.add("jobType");
					selector.add("specialtyType");
					selector.add("graphCount");
					selector.add("mainSupp");
					selector.add("contractBill");
					selector.add("balanceType");
					selector.add("isDeduct");
					selector.add("deductAmount");
					selector.add("deductReason");
//					selector.add("entrys.*");
					selector.add("amount");
					selector.add("contractBillNumber");
					selector.add("conductTime");
					selector.add("state");
					selector.add("changeAuditNumber");
					selector.add("changeTypeName");
					selector.add("curProjectName");
					selector.add("jobTypeName");
					selector.add("specialtyTypeName");
					selector.add("costNouse");
					selector.add("invalidCostReason");
					selector.add("originalAmount");
					selector.add("bookedDate");
					selector.add("period");
					selector.add("isConSetted");
					selector.add("isCostSplit");
					selector.add("conductUnit");
					selector.add("offer");
					selector.add("constrUnit");
					selector.add("constrSite");
					selector.add("originalContactNum");
					selector.add("isSureChangeAmt");
					selector.add("isImportChange");
					selector.add("constructPrice");
					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, selector);
				}
			}
		}
	}
	
	//审核校验
	/*private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
    
		ChangeAuditBillInfo model = (ChangeAuditBillInfo)billInfo;

        if(model==null|| model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
        	model= this.getChangeAuditBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }

		//检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
			
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();	
			//没有结束初始化
			if(!ProjectPeriodStatusUtil._isClosed(ctx,curProject)){
				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.ISNOT_INIT,new Object[]{model.getCurProject().getDisplayName()});
			}	
			//成本已经月结
			PeriodInfo costPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			
			if (costPeriod == null) {
				// 工程项目月结状态的期间不能为空。
				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.PERIOD_CNT_EMPTY, new Object[] { model.getCurProject()
						.getDisplayName() });
			}
			
			if(model.getPeriod().getBeginDate().after(costPeriod.getEndDate())){
				throw new  ContractException(ContractException.AUD_AFTERPERIOD,new Object[]{model.getNumber()});
			}
		}
	}*/
	
	private SelectorItemCollection getSic(){
		// 此过滤为详细信息定义
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("period.id"));
        sic.add(new SelectorItemInfo("period.beginDate"));	
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));	
        
        return sic;
    }
	
	//更新银行帐号
	private void upSupplierBank(Context ctx,PayRequestBillInfo info) throws Exception {
		String account = info.getRecAccount();
		SupplierInfo realSupplier = info.getRealSupplier();//实收款单位
		if(account != null && realSupplier != null && realSupplier instanceof SupplierInfo && account instanceof String){
			String bankNum = account.toString();
			SupplierInfo supplierInfo = (SupplierInfo) realSupplier;
			ISupplierCompanyInfo remoteInstance = SupplierCompanyInfoFactory.getLocalInstance(ctx);//供应商财务信息
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("supplier",supplierInfo.getId()));
			viewInfo.setFilter(filterInfo);
			//查询供应商财务信息
			SupplierCompanyInfoCollection companyInfoCollection = remoteInstance.getSupplierCompanyInfoCollection(viewInfo);
			for (int i = 0; i < companyInfoCollection.size(); i++) {
				SupplierCompanyInfoInfo companyInfoInfo = companyInfoCollection.get(i);
				
				//财务信息的银行信息
				ISupplierCompanyBank companyBank = SupplierCompanyBankFactory.getLocalInstance(ctx);//供应商财务银行
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("SupplierCompanyInfo",companyInfoInfo.getId()));
				filter.getFilterItems().add(new FilterItemInfo("bankAccount",bankNum));
				view.setFilter(filter);
				SupplierCompanyBankCollection companyBankCollection = companyBank.getSupplierCompanyBankCollection(view);
				if(!(companyBankCollection.size() > 0)){
					SupplierCompanyBankInfo companyBankInfo =  new SupplierCompanyBankInfo();
					companyBankInfo.setBankAccount(bankNum);
					companyBankInfo.setSupplierCompanyInfo(companyInfoInfo);
					companyBank.addnew(companyBankInfo);
				}
			}
			
		}
	}
	
}