package com.kingdee.eas.fdc.contract.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.query.server.SQLDataAccessFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.fdc.contract.WriteBackBillStatusFacadeFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.landray.kmss.km.importfile.webservice.AttachmentForm;

/**
 * ************************************************** 类名：AlterAttachment
 * 修改日期：2012-8-19 下午07:52:20 修改版本：@version 1.0.0
 * **************************************************
 */
public class AlterAttachment {

	/**
	 * ************************************************** 方法说明:
	 * updateAttachmentByID(根据附件ID更新附件)
	 * 
	 * 参数@param id 附件id 参数@param bt 附件内容，字节流 返回值：boolean true：更新成功；false：更新失败
	 * 
	 * 修改人：yangpengfei 修改时间：2012-8-19 下午07:49:22
	 * ***************************************************
	 */
	public boolean updateAttachmentByID(String id, byte[] bt) {
		boolean bl = false;
		try {
			bl = WriteBackBillStatusFacadeFactory.getRemoteInstance().alterAttachmentByID(id, "", bt);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return bl;
	}

	/**
	 * ************************************************** 方法说明:
	 * getFile(获取文件的字节流，测试用)
	 * 
	 * 返回值：byte[]
	 * 
	 * 修改人：yangpengfei 修改时间：2012-8-19 下午07:54:39
	 * ***************************************************
	 */
	public String filePath = "";
	public byte[] getFile(String fid) {
		String sql = "select * from T_BAS_Attachment where fid='"+fid+"'";
		IRowSet rs;
		byte[] buffer = null;
		byte[] bs = null;
		try {
			rs = SQLDataAccessFactory.getRemoteInstance().getRowSet(sql);
			
			String fileName = "";
			while (rs.next()) {
				String fname = rs.getString("fname_l2");
				String extendName = rs.getString("fsimplename");
				InputStream word = rs.getBlob("ffile").getBinaryStream();

				String len = word.toString();
				long length = len.length();
				int ch = 0;
				buffer = new byte[(int) length / 7]; // 定义

				File file = new File("c:\\" + fname + "." + extendName);// 将数据写入文件
				filePath = "c:\\" + fname + "." + extendName;
				fileName = fname + "." + extendName;

				System.out.println("------------file:" + filePath);
				if (!file.exists()) {
					file.createNewFile();
				} else {
					if (file.delete()) {
						file.createNewFile();
					}
				}
				FileOutputStream newFile = new FileOutputStream(file, true);
				boolean go = true;
				while (go) {
					while ((ch = word.read(buffer)) != -1) {
						newFile.write(buffer);
					}
					go = false;
				}
			}

			File file = new File(filePath);
			FileInputStream in = new FileInputStream(file);
			bs = new byte[in.available()];
			in.read(bs);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bs;
	}
	
	/**
	 * **************************************************
	 * 方法说明: getAttacmentInfo(根据单据id获取附件对象)  
	 *  
	 * 参数：@param billId
	 * 参数：@return                                                 
	 * 返回值：AttachmentInfo
	 * 
	 * 修改人：yangpengfei
	 * 修改时间：2012-8-25 下午01:12:36
	 * ***************************************************
	 */
	public AttachmentForm[] getAttacmentInfo(String billId)
	{
		AttachmentForm  info = null;
		AttachmentForm [] infoList = null;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", billId));
		view.setFilter(filter);
		
		try {
			BoAttchAssoCollection boCol = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
			if(boCol != null && boCol.size()>0)
			{
				
				infoList = new AttachmentForm[boCol.size()];
				for (int i = 0; i < boCol.size(); i++) {
					info = new AttachmentForm();
					String attachmentID = boCol.get(i).getAttachment().getId().toString();
					
					EntityViewInfo view1 = new EntityViewInfo();
					FilterInfo filter1 = new FilterInfo();
					filter1.getFilterItems().add(new FilterItemInfo("id", attachmentID));
					view1.setFilter(filter1);
					
					AttachmentCollection aCol = AttachmentFactory.getRemoteInstance().getAttachmentCollection(view1);
					if(aCol != null && aCol.size() >0)
					{
						
						for (int j = 0; j < aCol.size(); j++) {
							info.setFdKey("zhdc_bill_attachment");
							info.setFdFileName(aCol.get(j).getName()+"."+aCol.get(j).getSimpleName());
							info.setFdAttachment(aCol.get(j).getFile());
							infoList[i] = info;
						}
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return infoList;
	}

}
