package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.query.server.SQLDataAccessFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCUtil {

	public boolean checkExistAttachment(String sql) {
		boolean bl = false;
		IRowSet rs = null;
		try {
			rs = SQLDataAccessFactory.getRemoteInstance().getRowSet(sql);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				try {
					if (rs.next()) {
						bl = (rs.getString("attachmentID") == null || rs
								.getString("attachmentID") == "") ? false
								: true;
						if (bl) {
							break;
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return bl;
	}

	public static boolean checkExistChinese(String str) {
		boolean bl = false;
		Pattern p = Pattern.compile("[\u4E00-\u9FB0]");
		Matcher m = p.matcher(str);
		while (m.find()) {
			System.out.println("-------中文：" + m.group());
			bl = true;
			break;
		}

		return bl;
	}

	public static String cutComma(String str) {
		if(str!=null && !str.equals(""))
		{
			str = str.replace(",", "");
		}
			
		return str;
	}
	
	// bl "true":可以提交；"false":不能提交
	public static boolean isEnableSubmit(String sql) {
		boolean bl = true;
		IRowSet rs = null;
		try {
			rs = SQLDataAccessFactory.getRemoteInstance().getRowSet(sql);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				try {
					if (rs.next()) {
						String state = (rs.getString("state") != null && rs.getString("state") != "") ? rs.getString("state").toString() : "";
						String isOAAudit = (rs.getString("isOAAudit") != null && rs.getString("isOAAudit") != "") ? rs.getString("isOAAudit").toString() : "";
						if ((FDCBillStateEnum.SUBMITTED.getValue().equals(state) || FDCBillStateEnum.AUDITTED.getValue().equals(state))&& isOAAudit.equals("1")) {
							bl = false;
							break;
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return bl;
	}

	public static String getMoney(String sql) {
		IRowSet rs = null;
		try {
			rs = SQLDataAccessFactory.getRemoteInstance().getRowSet(sql);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (rs != null && rs.size() > 0) {
			try {
				if(rs.next())
				{
					try {
						return rs.getString("originalMoney");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public static boolean isDate(String str) {
		String pstr = "\\d\\d\\d\\d/\\d\\d/\\d\\d ";
		Pattern p = Pattern.compile(pstr);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	/*
	 * 将金额保留2位小数并添加千位符
	 */
	public  String getDecimal(String num) {
		if (num != null && !num.equals("")) {
			try {
				BigDecimal number = new BigDecimal(num);
				int index = number.toString().indexOf(".");
				if (index != -1 && number.toString().substring(0, index).equals("0")) {
					return "0.00";
				}
				if(num.equals("0")){
					return "0.00";
				}
				DecimalFormat dformat = new DecimalFormat(".##");
				String pattern = "###,###.00";
				dformat.applyPattern(pattern);
				return dformat.format(number);
			} catch (Exception ex) {
				return num;
			}
		} else {
			return "";
		}
	}
	
	public String createColumn(String value)
	{
		boolean isNumber = true;

		try {
			BigDecimal bd = new BigDecimal(value);
			isNumber = true;
		} catch (Exception e) {
			isNumber = false;
		}
		
		//如果是数值
		if (isNumber) {
			try {
				BigDecimal number = new BigDecimal(value);
				int index = number.toString().indexOf(".");
				if (index != -1 && number.toString().substring(0, index).equals("0")) {
					return "<td class='td_normal_title' align='right'>0.00</td> \r\n";
				}
				if(value.equals("0")){
					return "<td class='td_normal_title' align='right'>0.00</td> \r\n";
				}
				DecimalFormat dformat = new DecimalFormat(".##");
				String pattern = "###,###.00";
				dformat.applyPattern(pattern);
				return "<td class='td_normal_title' align='right'>"+dformat.format(number)+"</td> \r\n";
			} catch (Exception ex) {
				System.out.println("-----出问题了");
				return value;
			}
		} else {
			return "<td class='td_normal_title'>"+value+"</td> \r\n";
		}
	}
	
	public String filterStringNull(String string)
	{
		
		
		return "";
	}
	
	public static boolean isAuthorization(String string)
	{
		boolean bl = false;
		
		return true;
	}

}
