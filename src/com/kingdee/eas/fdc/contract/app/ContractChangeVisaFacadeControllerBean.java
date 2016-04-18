package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.query.server.SQLDataAccessFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 保存变更测试金额
 * 
 * @author yangpengfei 2015年6月21日17:36:14
 */
public class ContractChangeVisaFacadeControllerBean extends
		AbstractContractChangeVisaFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ContractChangeVisaFacadeControllerBean");

	// 通过变更审批单ID保存测试成本金额
	protected String _saveCostAmountByChngAuditBillID(Context ctx, String pk) throws BOSException {
		try {
			String sqlChgSuppEnty = "select fid,foricostamount,fcostamount from T_CON_ChangeSupplierEntry where fparentid='"
					+ pk + "'";
			IRowSet rs = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(
					sqlChgSuppEnty);
			if (rs != null && rs.size() > 0) {
				while (rs.next()) {
					String suppEntyId = rs.getString("fid");
					String oriCostAmount = rs.getString("foricostamount");

					DbUtil.execute(ctx,
							"update T_CON_ChangeSupplierEntry set cfChgBefAmt = "
									+ oriCostAmount + " where fid = '"
									+ suppEntyId + "'");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "SC1111";
		}
		return "SC0000";
	}
	
	// 反审批合同变更审批单时，根据变更审批单ID保存测算成本金额为空
	protected String _unSaveCostAmountByChngAuditBillID(Context ctx, String pk) throws BOSException {
		try {
			String sqlChgSuppEnty = "select fid,foricostamount,fcostamount from T_CON_ChangeSupplierEntry where fparentid='"
					+ pk + "'";
			IRowSet rs = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(
					sqlChgSuppEnty);
			if (rs != null && rs.size() > 0) {
				while (rs.next()) {
					String suppEntyId = rs.getString("fid");
					String oriCostAmount = rs.getString("foricostamount");

					DbUtil.execute(ctx,
							"update T_CON_ChangeSupplierEntry set cfChgBefAmt = null where fid = '"
									+ suppEntyId + "'");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "SC1111";
		}
		return "SC0000";
	}
	
	//变更签证确认单审批后，保存签证金额
	protected String _saveVisaAmountByChngAuditBillID(Context ctx, String pk)
			throws BOSException {
		try {
			String sqlChgSuppEnty = "select fid,famount from T_CON_ContractChangeBill where fid='"
					+ pk + "'";
			IRowSet rs = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(
					sqlChgSuppEnty);
			if (rs != null && rs.size() > 0) {
				while (rs.next()) {
					String fid = rs.getString("fid");
					String chgAftAmt = rs.getString("famount");

					DbUtil.execute(ctx,
							"update T_CON_ChangeSupplierEntry set cfChgAftAmt = "
									+ chgAftAmt + " where fid = '"
									+ fid + "'");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "SC1111";
		}
		return "SC0000";
	}
	
	//反审批时，清掉签证金额
	protected String _unSaveVisaAmountByChngAuditBillID(Context ctx, String pk)
	throws BOSException {
		try {
			String sqlChgSuppEnty = "select fid,famount from T_CON_ContractChangeBill where fid='"
					+ pk + "'";
			IRowSet rs = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(
					sqlChgSuppEnty);
			if (rs != null && rs.size() > 0) {
				while (rs.next()) {
					String fid = rs.getString("fid");
					String chgAftAmt = rs.getString("famount");
		
					DbUtil.execute(ctx,
							"update T_CON_ChangeSupplierEntry set cfChgAftAmt = null where fid = '"
									+ fid + "'");
				}
		
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "SC1111";
		}
		return "SC0000";
	}

	// 通过变更审批单分录ID保存测试
	protected String _saveCostAmountByChngSupEntyID(Context ctx, String pk,
			BigDecimal amount) throws BOSException {
		try {
			DbUtil.execute(ctx,
					"update T_CON_ChangeSupplierEntry set cfChgBefAmt = "
							+ amount + " where fid = '" + pk + "'");
		} catch (Exception e) {
			e.printStackTrace();
			return "SC1111";
		}
		return "SC0000";
	}

	// 通过变更审批单分录ID获取预估金额、签证金额
	protected IRowSet _getCostAmountByChngAuditBillID(Context ctx, String pk,
			String rmk) throws BOSException {
		try {
			String sqlChgSuppEnty = "select sum(cfChgAftAmt) chgAftAmt,sum(cfChgBefAmt) chgBefAmt from T_CON_ChangeSupplierEntry where fparentid='"
					+ pk + "'";
			return SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(
					sqlChgSuppEnty);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * add by ypf on 2015年6月28日16:43:14
	 * 
	 * @param ctx
	 * @param orgId
	 * @param prjId
	 * @param chngId
	 * @param prefix
	 *            工程项目长编码.变更签证.
	 * @return
	 * @throws BOSException
	 */
	protected String _getChngAuditBillNumber(Context ctx, String orgId,
			String prjId, String chngId, String prefix) throws BOSException {
		String rtnNumberStr = "";
		String billNumber = "";// 单据中的
		String cdeNumber = "";// 回收表中的

		String sqlGetBillNum = "select max(fnumber) fnumber from t_con_changeauditbill where FCONTROLUNITID='"
				+ orgId
				+ "' and FCURPROJECTID='"
				+ prjId
				+ "' and FAUDITTYPEID='"
				+ chngId
				+ "' and fnumber like '"
				+ prefix + "%'";
		try {
			IRowSet rs = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(
					sqlGetBillNum);
			if (rs != null && rs.size() > 0) {
				while (rs.next()) {
					if(null!=rs.getString("fnumber")){
						billNumber = rs.getString("fnumber");
						//mod by ypf on 2015年9月6日 ，解决编码顺序号非4位的情况
						String[] splitStr = billNumber.split("\\.");
						String string = splitStr[splitStr.length-1];
						if(string.length()!=4){
							billNumber = "";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("---changeAuditBill-的number:" + billNumber);

			String sqlGetCde = "select min(cfcde) cfcde from CT_COM_CODE where CFORGID='"
					+ orgId
					+ "' and CFCURPROJECTID='"
					+ prjId
					+ "' and CFCHNGTYPID='"
					+ chngId
					+ "' and CFISENABLE=0 and cfcde like '"
					+ prefix + "%'";
			IRowSet rs = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(
					sqlGetCde);
			if (rs != null && rs.size() > 0) {
				while (rs.next()) {
					if(null!=rs.getString("cfcde")){
						cdeNumber = rs.getString("cfcde");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("------billNumber:" + billNumber + "     cdeNumber:" + cdeNumber);
		boolean flag = true;
		if (billNumber.equals("")) {
			billNumber = prefix + ".0001";
			rtnNumberStr = billNumber;
			flag = false;//如果是第一张表单，则直接使用即可
			
			//保存编码，防止其他客户端新增时出现编码冲突
			rtnNumberStr = insertCodeString(ctx,prefix,orgId,prjId,chngId,rtnNumberStr);
		}
		
		if(!billNumber.equals("") && flag==true && cdeNumber.equals("")) {
			String[] splitStr = billNumber.split("\\.");
			BigDecimal seq = new BigDecimal(splitStr[splitStr.length-1]);
			String numberSufixString = getNumberSufixString(seq.add(new BigDecimal(1)));
			rtnNumberStr = prefix + "." + numberSufixString;
			
			//保存编码，防止其他客户端新增时出现编码冲突
			rtnNumberStr = insertCodeString(ctx,prefix,orgId,prjId,chngId,rtnNumberStr);
		}
		
		if(!cdeNumber.equals("") && flag==true) {
			if(billNumber.compareTo(cdeNumber) > 0){
				rtnNumberStr = cdeNumber;
				
				//更新编码，防止其他客户端新增时出现编码冲突
				String sql = "update CT_COM_CODE set cfisenable=1 where cfcde='"+cdeNumber+"' and CFORGID='"+orgId+"' and CFCURPROJECTID='"+prjId+"' and cfchngtypid='"+chngId+"' and cfisenable=0";
				try {
					logger.info("----更新使用编码标识："+sql);
					DbUtil.execute(ctx, sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(billNumber.compareTo(cdeNumber) <= 0){
				String[] splitStr = billNumber.split("\\.");
				BigDecimal seq = new BigDecimal(splitStr[splitStr.length-1]);
				String numberSufixString = getNumberSufixString(seq.add(new BigDecimal(1)));
				rtnNumberStr = prefix + "." + numberSufixString;
				
				//保存编码，防止其他客户端新增时出现编码冲突
				if(!rtnNumberStr.equals(cdeNumber)){
					rtnNumberStr = insertCodeString(ctx,prefix,orgId,prjId,chngId,rtnNumberStr);
				}else{
					//更新编码，防止其他客户端新增时出现编码冲突
					String sql = "update CT_COM_CODE set cfisenable=1 where cfcde='"+cdeNumber+"' and CFORGID='"+orgId+"' and CFCURPROJECTID='"+prjId+"' and cfchngtypid='"+chngId+"' and cfisenable=0";
					try {
						logger.info("----更新使用编码标识："+sql);
						DbUtil.execute(ctx, sql);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		System.out.println("-----最终返回的编码:" + rtnNumberStr);
		return rtnNumberStr;
	}
	
	private String getNumberSufixString(BigDecimal seq){
		String sufixStr = "";
		int len1 = seq.toString().length();
		if(len1==1)
		{
			sufixStr = "000"+seq;
		}
		if(len1==2)
		{
			sufixStr = "00"+seq;
		}
		if(len1==3)
		{
			sufixStr = "0"+seq;
		}
		if(len1==4)
		{
			sufixStr = "9999";
		}
		
		return sufixStr;
	}
	
	private String insertCodeString(Context ctx,String prefix,String orgId,String prjId,String chngId,String rtnNumberStr) throws BOSException
	{
		boolean flg = true;// true-可以新增;false-有重复记录,不用新增
//		int isEnable = 0;//是否启用
		try {
			//检查该记录是否库中存在
			String chkSql = "select cfcde,cfisenable from CT_COM_CODE where cfcdetyp='06' and CFORGID='"+orgId+"' and CFCURPROJECTID='"+prjId+"' and CFCHNGTYPID='"+chngId+"' and cfcde = '"+rtnNumberStr+"' and cfisenable=0";
			IRowSet rs = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(
					chkSql);
			//如果找到了,就占用掉
			if (rs != null && rs.size() > 0) {
				while (rs.next()) {
					if(null!=rs.getString("cfcde")){
						flg = false;
						
						//更新编码，防止其他客户端新增时出现编码冲突
						String sql = "update CT_COM_CODE set cfisenable=1 where cfcdetyp='06' and cfcde='"+rtnNumberStr+"' and CFORGID='"+orgId+"' and CFCURPROJECTID='"+prjId+"' and cfchngtypid='"+chngId+"' and cfisenable=0";
						try {
							logger.info("--1--更新使用编码标识："+sql);
							DbUtil.execute(ctx, sql);
							return rtnNumberStr;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}//如果没有找到就提供一个最小的或者生成一个
					else{}
				}
			}else{
				String sqlGetCde = "select min(cfcde) cfcde from CT_COM_CODE where CFORGID='"
					+ orgId
					+ "' and CFCURPROJECTID='"
					+ prjId
					+ "' and CFCHNGTYPID='"
					+ chngId
					+ "' and CFISENABLE=0 and cfcde like '"
					+ prefix + "%'";
					IRowSet rs1 = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(
							sqlGetCde);
					if (rs1 != null && rs1.size() > 0) {
						while (rs1.next()) {
							if(null!=rs1.getString("cfcde")){
								//更新编码，防止其他客户端新增时出现编码冲突
								rtnNumberStr = rs1.getString("cfcde");
								String sql = "update CT_COM_CODE set cfisenable=1 where cfcdetyp='06' and cfcde='"+rtnNumberStr+"' and CFORGID='"+orgId+"' and CFCURPROJECTID='"+prjId+"' and cfchngtypid='"+chngId+"' and cfisenable=0";
								try {
									logger.info("--1--更新使用编码标识："+sql);
									DbUtil.execute(ctx, sql);
								} catch (Exception e) {
									e.printStackTrace();
								}
								return rtnNumberStr;
							}else{
								String sqlGetMaxCde = "select max(cfcde) cfcde from CT_COM_CODE where CFORGID='"
									+ orgId
									+ "' and CFCURPROJECTID='"
									+ prjId
									+ "' and CFCHNGTYPID='"
									+ chngId
									+ "' and CFISENABLE=1 and cfcde like '"
									+ prefix + "%'";
								IRowSet rs2 = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(
										sqlGetMaxCde);
								if (rs2 != null && rs2.size() > 0) {
									while (rs2.next()) {
										if(null!=rs2.getString("cfcde")){
											String[] splitStr = rs2.getString("cfcde").split("\\.");
											BigDecimal seq = new BigDecimal(splitStr[splitStr.length-1]);
											String numberSufixString = getNumberSufixString(seq.add(new BigDecimal(1)));
											rtnNumberStr = prefix + "." + numberSufixString;//重新赋值
											
											//新增一条记录
											BOSUuid id = BOSUuid.create("70116117");
											Timestamp createTime = new Timestamp(Calendar.getInstance().getTimeInMillis()); 
											
											String sql = "insert into CT_COM_CODE (cfid,cfcdetyp,CFCURPROJECTID,CFORGID,cfchngtypid,cfcde,cfisenable,CFCREATETIME) " +
											"values ('"+id+"','06','"+prjId+"','"+orgId+"','"+chngId+"','"+rtnNumberStr+"','1','"+createTime+"')";
											logger.info("---save code:"+sql);
											DbUtil.execute(ctx, sql);
											return rtnNumberStr;
										}else{
											//新增第一条记录,0001
											BOSUuid id = BOSUuid.create("70116117");
											Timestamp createTime = new Timestamp(Calendar.getInstance().getTimeInMillis()); 
											
											String sql = "insert into CT_COM_CODE (cfid,cfcdetyp,CFCURPROJECTID,CFORGID,cfchngtypid,cfcde,cfisenable,CFCREATETIME) " +
											"values ('"+id+"','06','"+prjId+"','"+orgId+"','"+chngId+"','"+rtnNumberStr+"','1','"+createTime+"')";
											logger.info("---save code:"+sql);
											DbUtil.execute(ctx, sql);
											rtnNumberStr = rtnNumberStr+"";
											return rtnNumberStr;
										}
									}
								}
								
							}
						}
					}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtnNumberStr;
	}
	
	protected void _executeUpdate(Context ctx, String sql) throws BOSException {
		try {
			DbUtil.execute(ctx, sql);
		} catch (Exception e) {
			logger.error("--exception-sql:"+sql);
			e.printStackTrace();
		}
	}
	
	//add by ypf on 2015年8月1日18:40:05  清理编码
	protected String _clearCode(Context ctx, String prmStr) throws BOSException {
		StringBuffer sb = new StringBuffer();
		int cnt = 0;
		String sql = "SELECT CFID,CFORGID,CFCURPROJECTID,CFCHNGTYPID,CFCDE,CFISENABLE FROM CT_COM_CODE WHERE CFISENABLE=1 OR CFISENABLE IS NULL OR CFISENABLE = ''";
		
		try {
			IRowSet rs = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(sql);
		    if (rs != null && rs.size() > 0) {
				while (rs.next()) {
					String id = rs.getString("CFID");
					String orgId = rs.getString("CFORGID");
					String curPrjId = rs.getString("CFCURPROJECTID");
					String chngTypId = rs.getString("CFCHNGTYPID");
					String cde = rs.getString("CFCDE");
					int isEnable = rs.getInt("CFISENABLE");
					
					boolean isExist = false;//true-存在，false-不存在
					String sqlForAuditChngNum = "select fid,fnumber from t_con_changeauditbill where FCONTROLUNITID='"+orgId+"' and FCURPROJECTID='"+curPrjId+"' and FAUDITTYPEID='"+chngTypId+"' and fnumber='"+cde+"'";
					IRowSet rs1 = SQLDataAccessFactory.getLocalInstance(ctx).getRowSet(sqlForAuditChngNum);
					if (rs1 != null && rs1.size() > 0) {
						while (rs1.next()) {
							isExist = true;
							break;
						}
					}
					
					//如果合同变更审批单中不存在该编号，但编码表中又标识是启用的编码，此情况下的编码需释放掉
					if(!isExist){
						String sqlUpdate = "UPDATE CT_COM_CODE SET CFISENABLE=0 WHERE CFID='"+id+"'";
						DbUtil.execute(ctx, sqlUpdate);
						logger.info("---编码清理sql："+sqlUpdate);
						sb.append("清理第"+(cnt+1)+"个编号：【" + cde + "，"+id+"，"+orgId+"，"+curPrjId+"，"+chngTypId+"】\r\n");
						cnt++;//累计清理数
					}
				}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String prefix = "清理完毕，清理编码总个数："+ cnt + "\n\r";
		if(sb != null && !sb.equals("") && sb.length()>0)
		{
			prefix += "\r\n【编码，编码ID，所属组织，工程项目，变更类型】\r\n";
		}
		logger.info(prefix + sb.toString()); 
		return prefix + sb.toString();
	}
}