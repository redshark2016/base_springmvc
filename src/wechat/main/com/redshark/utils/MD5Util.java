package com.redshark.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * <p>Title: 虚拟大厅系统</p>
 * <p>Description:用户登录密码后密码使用md5加密后存数据库</p>
 * <p>创建日期:2011-12-30</p>
 * @author wenshun.wang
 * @version 1.0
 * <p>科创</p>
 */
public class MD5Util {
	private static final Log LOG = LogFactory.getLog(MD5Util.class);
	/**  
     * MD5 加密  
     */   
    public String getMD5Str(String str) {   
        MessageDigest messageDigest = null;   
   
        try {   
            messageDigest = MessageDigest.getInstance("MD5");
   
            messageDigest.reset();   //重置MD5摘要以供再次使用。
   
            messageDigest.update(str.getBytes("GBK"));  //使用指定的字节更新MD5摘要。 
        } catch (NoSuchAlgorithmException e) {   
        	LOG.error(e);  
        } catch (UnsupportedEncodingException e) {   
        	LOG.error(e);  
        }   
   
        if(messageDigest != null){
        	byte[] byteArray = messageDigest.digest(); //通过执行诸如填充之类的最终操作完成哈希计算,在调用此方法之后，MD5摘要被重置。  
        	   
            StringBuffer md5StrBuff = new StringBuffer();   
       
            for (int i = 0; i < byteArray.length; i++) {               
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)   
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));   
                else   
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));   
            }   
       
            return md5StrBuff.toString(); 
        }else{
        	return null;
        }
          
    }  
    private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
