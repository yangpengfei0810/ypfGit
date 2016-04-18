package com.kingdee.eas.fdc.contract.client;

import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;

import com.kingdee.eas.util.client.EASResource;

public class OASSOUtil {
	public static String encrypt(String message, String key) throws Exception {
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		// IvParameterSpec iv = new IvParameterSpec("".getBytes("UTF-8"));

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] bytes = cipher.doFinal(message.getBytes("UTF-8"));
		return getBASE64(bytes);
	}

	public static String decrypt(String message, String key) throws Exception {
		byte[] bytesrc = getFromBASE64(message);

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		// IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte, "UTF-8");
	}

	public static String getBASE64(byte[] b) {
		String s = null;
		if (b != null) {
			s = new sun.misc.BASE64Encoder().encode(b);
		}
		return s;
	}

	public static byte[] getFromBASE64(String s) {
		byte[] b = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				return b;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	public static String getSSOURL(String userNumber,String oaID) throws Exception
	{
		String url="";
		if(!FDCUtil.checkExistChinese(oaID))
		{                 
			String key = "6E66F6CD"; // 密钥
			String tmpStr = userNumber+"+easlogin+"+oaID;
			System.out.println("原始时间串=" + tmpStr);
			String desStr = encrypt(tmpStr, key);
			System.out.println("加密后=" + desStr);
			desStr = desStr.replaceAll("\\+", "*");
			desStr = desStr.replaceAll("/", "-");
			desStr = desStr.replaceAll("=", "_");
			System.out.println("-----替换后=" + desStr);
			
			String path = EASResource.getString("com.kingdee.eas.fdc.contract.client.FdcContractConfig", "path");
			url = path +""+desStr;
			System.out.println("-----资源文件的url："+url);
//			url = "http://ekptest.carec.com.cn:8088/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+desStr+"";
//			url = "http://oa.carec.com.cn/km/importfile/sso/easLoginHelper.do?method=easLogin&key="+desStr+"";
//			System.out.println("-----写死的url："+url);
		}
		return url;
	}
	
	public static void main(String[] args) throws Exception {
		String key = "6E66F6CD"; // 密钥
		Date thisDate = new Date();
		System.out.println(thisDate);
		System.out.println(thisDate.getTime());

		// Date newDate = new Date();
		// newDate.setMinutes(53);
		// System.out.println(newDate);
		// // System.out.println(newDate.compareTo(thisDate));
		// System.out.println(newDate.getTime());

		String tmpStr = String.valueOf(thisDate.getTime());
		System.out.println("原始时间串=" + tmpStr);
		String desStr = encrypt(tmpStr, key);

		System.out.println("加密后=" + desStr);

		String desStr1 = decrypt(desStr, key);
		System.out.println("解密后=" + desStr1);
	}

}
