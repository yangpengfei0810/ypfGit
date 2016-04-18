package com.kingdee.eas.fdc.contract;

import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;

public class WriteLog {
	
	/**
	 * **************************************************
	 * 方法说明: setLog(这里用一句话描述这个方法的作用)  
	 *  
	 * 参数：@param ctx
	 * 参数：@param info   填写 title、details、isSuc、type
	 *          title：标题
	 *          details：明细
	 *          isSuc：bool值
	 *          type：合同、付款申请单、变更申请单、无文本合同、结算单                                    
	 * 返回值：void
	 * 
	 * 修改人：yangpengfei
	 * 修改时间：2013-6-27 下午04:37:09
	 * ***************************************************
	 */
	public  static void setLog(Context ctx,LogInfo info)
	{
		ContractBillInfo billInfo = new ContractBillInfo();
		String ftitle = (info.getTitle()!=null && !info.getTitle().equals(""))?info.getTitle():"";
		String fdetails = (info.getDetails()!=null && !info.getDetails().equals(""))?info.getDetails():"";
		int fisSuc = (info.isSuc()==true)?1:0;
		String ftype = (info.getType()!=null && !info.getType().equals(""))?info.getType():"";
		Date fcreateTime = new Date();
		BOSUuid fid = BOSUuid.create(billInfo.getBOSType());
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("insert into t_fdc_ContractLogs (fid,ftitle,fdetails,fisSuc,ftype,fcreateTime) values(?,?,?,?,?,?)");
		builder.addParam(fid);
		builder.addParam(ftitle);
		builder.addParam(fdetails);
		builder.addParam(fisSuc);
		builder.addParam(ftype);
		builder.addParam(fcreateTime);
		try {
			builder.execute();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	public  static void setLog(Context ctx,boolean isSuc,String type,String title,String details)
	{
		ContractBillInfo billInfo = new ContractBillInfo();
		String ftitle = (title!=null && !title.equals(""))?title:"";
		String fdetails = (details!=null && !details.equals(""))?details:"";
		int fisSuc = (isSuc==true)?1:0;
		String ftype = (type!=null && !type.equals(""))?type:"";
		Date fcreateTime = new Date();
		BOSUuid fid = BOSUuid.create(billInfo.getBOSType());
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("insert into t_fdc_ContractLogs (fid,ftitle,fdetails,fisSuc,ftype,fcreateTime) values(?,?,?,?,?,?)");
		builder.addParam(fid);
		builder.addParam(ftitle);
		builder.addParam(fdetails);
		builder.addParam(fisSuc);
		builder.addParam(ftype);
		builder.addParam(fcreateTime);
		try {
			builder.execute();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

}
