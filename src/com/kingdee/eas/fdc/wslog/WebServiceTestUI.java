package com.kingdee.eas.fdc.wslog;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.dao.query.server.SQLDataAccessFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.contract.ContractChangeVisaFacadeFactory;
import com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade;
import com.kingdee.eas.fdc.contract.WriteBackBillStatusFacadeFactory;
import com.kingdee.eas.fdc.contract.client.BareBonesBrowserLaunch;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class WebServiceTestUI extends AbstractWebServiceTestUI
{
    private static final Logger logger = CoreUIObject.getLogger(WebServiceTestUI.class);
    
    private static Date startDate  = null;
    private static Date endDate  = null;
    
    public WebServiceTestUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    public void createWeekDay()
    {
    	Calendar c = new GregorianCalendar();
    	c.setFirstDayOfWeek(Calendar.MONDAY);
    	c.setTime(new Date());
    	
    	c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
    	startDate = c.getTime();
    	
    	c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday 
    	endDate = c.getTime();


    	System.out.println("---start:" + startDate);
    	System.out.println("---end:" + endDate);
    	
//    	BareBonesBrowserLaunch.openURL("www.baidu.com");
//    	JLabel lab = new JLabel("www.baidu.com");
//    	this.txtIsSuc.add(lab);
    	
    	try {
			kDEditorPaneBrowser.setPage("www.baidu.com");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	btnTest.setEnabled(true);
    	txtIsSuc.setEditable(false);
    	
    	btnSearch.setEnabled(true);
    	btnSave.setEnabled(true);
    	
    	cmbBillType.setSelectedItem("");
    	cmbCallType.setSelectedItem("");
    	cmbRSState.setSelectedItem("未处理");
    	cmbRecordState.setSelectedItem("失败");
    	cmbCallType.setSelectedItem("回写");
    	
    	//生成本周的第一天和最后一天
    	createWeekDay();
    	dtime1.setValue(startDate);
    	dtime2.setValue(endDate);
    	
    	/*String paramValue = ParamManager.getParamValue(new ObjectUuidPK("UJlmiV05QwOi8UsKKZRkj8znrtQ="), "FDC140311001");
    	System.out.println("---------paramValue:"+paramValue);*/
    }
    
    public void fillTBMain()
    {
    	tblMain.getStyleAttributes().setLocked(false);
    	
    	//执行前先删除
    	tblMain.removeRows();
    	
    	//设置排序
    	for (int i = 0; i < tblMain.getColumnCount(); i++) {
			tblMain.getColumn(i).setSortable(true);
		}
    	KDTSortManager manager = new KDTSortManager(tblMain);
    	manager.setSortAuto(true);
    	
    	// 冻结列
    	tblMain.getViewManager().freezeColumn(5);
    	
    	Object sTime = dtime1.getValue();
    	Object eTime = dtime2.getValue();
    	
    	Object billType = cmbBillType.getSelectedItem();
    	Object callType = cmbCallType.getSelectedItem();
    	Object recordState = cmbRecordState.getSelectedItem();
    	Object rsState = cmbRSState.getSelectedItem();
    	String oaBillId = txtOABillId.getText();
    	
    	String sqlWhere = "";
    	
    	if(sTime!=null && !sTime.toString().trim().equals("") )
    	{
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(sTime);
			sqlWhere += " and fcreatetime>={ts'"+time+"'}";
    	}
    	
    	if(eTime!=null && !eTime.toString().trim().equals(""))
    	{
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(eTime);
			sqlWhere += " and fcreatetime<={ts'"+time+" 23:59:59'}";
    	}
    	
    	if(billType!=null && !billType.toString().trim().equals("") )
    	{
			sqlWhere += " and fsourcebilltype like '"+billType+"%'";
    	}
    	
    	if(callType!=null && !callType.toString().trim().equals(""))
    	{
			sqlWhere += " and fcallType like '"+callType+"%'";
    	}
    	
    	if(recordState!=null && !recordState.toString().trim().equals(""))
    	{
			sqlWhere += " and fstate like '"+recordState+"'";
    	}
    	
    	if(rsState!=null && !rsState.toString().trim().equals(""))
    	{
    		if(rsState.equals("未处理"))
    		{
    			sqlWhere += " and (fisclosed like '"+rsState+"' or fisclosed is null)";
    		}else{
    			sqlWhere += " and fisclosed like '"+rsState+"'";
    		}
    	}
    	
    	if(oaBillId!=null && !oaBillId.toString().trim().equals(""))
    	{
			sqlWhere += " and (furl like '%"+oaBillId+"%' or fsourcebillid = '"+oaBillId+"' or finparam like '%"+oaBillId+"%' or flogdetail like '%"+oaBillId+"%')";
    	}
    	
    	String sql = 
	    	"select fid,fsourcebillid,fsourcebilltype,fstate,flogtitle,flogdetail,fcreatetime,fisclosed,finparam,fcalltype,furl "+
	    	"from T_WSL_WSLOG                                                  "+
	    	"  where 1=1 "+sqlWhere+"                                          "+
	    	" order by fcreatetime desc";
	    	
	    	IRowSet rs = null;
			try {
				rs = SQLDataAccessFactory.getRemoteInstance().getRowSet(sql);
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    	
			if(rs!=null && rs.size()>0)
			{
				 int i=0;
				 try {
					while(rs.next())
					{
						tblMain.addRow();
						tblMain.getCell(i, "isChecked").setValue(false);
						tblMain.getCell(i, "id").setValue(rs.getString("fid"));
						tblMain.getCell(i, "isClosed").setValue(rs.getString("fisclosed"));
						tblMain.getCell(i, "sourceBillId").setValue(rs.getString("fsourcebillid"));
						tblMain.getCell(i, "createTime").setValue(rs.getString("fcreatetime"));
						tblMain.getCell(i, "billType").setValue(rs.getString("fsourcebilltype"));
						tblMain.getCell(i, "state").setValue(rs.getString("fstate"));
						tblMain.getCell(i, "logTitle").setValue(rs.getString("flogtitle"));
						tblMain.getCell(i, "logDetail").setValue(rs.getString("flogdetail"));
						tblMain.getCell(i, "inParam").setValue(rs.getString("finparam"));
						tblMain.getCell(i, "callType").setValue(rs.getString("fcalltype"));
						tblMain.getCell(i, "url").setValue(rs.getString("furl"));
						
						i++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
    }
    
    public void actionTest_actionPerformed(ActionEvent e) throws Exception
    {
    	String billType = (txtBillType.getText()!=null && !txtBillType.getText().equals(""))?txtBillType.getText().trim():"";
    	String billState = (txtBillState.getText()!=null && !txtBillState.getText().equals(""))?txtBillState.getText().trim():"";
    	String OABillID = (txtOABillID.getText()!=null && !txtOABillID.getText().equals(""))?txtOABillID.getText().trim():"";
    	
//    	WSAttachment[] files = getFiles();
    	boolean verfiyMSG = verfiyMSG(billType);
    	if(!verfiyMSG)
    	{
    		MsgBox.showInfo("不符合单据类型要求");
    	}

    	if(!billType.equals("") && !billState.equals("") && !OABillID.equals(""))
    	{
    		IWriteBackBillStatusFacade remoteInstance = WriteBackBillStatusFacadeFactory.getRemoteInstance();
			String isSuc = remoteInstance.modBillInfo(billType, OABillID, billState, false,null);
    	    this.txtIsSuc.setText(isSuc.equals("1")?"成功":isSuc);
    	}else
    	{
    		MsgBox.showInfo("单据类型/单据状态/OA单据ID 字段不能为空");
    	}
    }
    
    /*public WSAttachment [] getFiles() throws IOException
    {
    	// 回写单据信息
		 File file = new File("c:\\VM_Setup.log");
		 FileInputStream in = new FileInputStream(file);
		 byte[] bs = new byte[in.available()];
		 in.read(bs);
		 in.close();
		
		 WSAttachment info=new WSAttachment();
		 info.setName("VM_Setup.log");
		 info.setFile(bs);
		 info.setSize(bs.length/1024+"KB");
		 info.setSizeInByte(bs.length);
		 
		 File file1 = new File("c:\\FaceProv.log");
		 FileInputStream in1 = new FileInputStream(file1);
		 byte[] bs1 = new byte[in1.available()];
		 in1.read(bs1);
		 in1.close();
		
		 WSAttachment info1=new WSAttachment();
		 info1.setName("FaceProv.log");
		 info1.setFile(bs1);
		 info1.setSize(bs1.length/1024+"KB");
		 info1.setSizeInByte(bs1.length);
		 
		 WSAttachment [] attachmentInfoList = new WSAttachment[2];
		 attachmentInfoList[0] = info;
		 attachmentInfoList[1] = info1;
		 
		 return attachmentInfoList;
    }*/
    
    public boolean verfiyMSG(String billType1)
    {
    	String [] type_ = new String []{"EAS1","EAS2","EAS3","EAS4","EAS5","EAS6","EAS7","EAS8"};
		if(billType1 == null || billType1.equals("") || billType1.equals("null"))
		{
			return false;
		}
		
		if(billType1 != null && !billType1.equals("") && !billType1.equals("null"))
		{
			boolean bl_isExist = false;
			for (int i = 0; i < type_.length; i++) {
				if(billType1.contains(type_[i]))
				{
					bl_isExist = true;
					break;
				}
				
			}
			
			//如果没有匹配在类型，则返回
			if(!bl_isExist)
			{
				return false;
			}
		}
		
		return true;
    }

    public void actionSearch_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSearch_actionPerformed(e);
    	
    	fillTBMain();
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    	
    	for (int i = 0; i < tblMain.getRowCount(); i++) {
    		if(tblMain.getCell(i, tblMain.getColumn("isChecked").getColumnIndex())!=null && tblMain.getCell(i, tblMain.getColumn("isChecked").getColumnIndex()).getValue().equals(true))
    		{
    			Object id = tblMain.getCell(i, tblMain.getColumn("id").getColumnIndex()).getValue();
    			if(id!=null)
    			{
    				WSLogCollection collection = WSLogFactory.getRemoteInstance().getCollection(" where id = '"+id+"'");
    				WSLogInfo logInfo = collection.get(0);
    				
    				SelectorItemCollection selector = new SelectorItemCollection();
    				selector.add("isClosed");
    				logInfo.setIsClosed("已处理");
    				WSLogFactory.getRemoteInstance().updatePartial(logInfo, selector);
    			}
    		}
		}
    	
    	//刷新
    	fillTBMain();
    }
    
    //add by ypf on 2015年8月1日19:11:28
    protected void kdBtnClear_actionPerformed(ActionEvent e) throws Exception {
    	String clearCodeRs = ContractChangeVisaFacadeFactory.getRemoteInstance().clearCode(null);
    	logger.info("---清理编码返回结果："+clearCodeRs);
    	kdTxtAreaClearRslt.setText(clearCodeRs);
    }
}