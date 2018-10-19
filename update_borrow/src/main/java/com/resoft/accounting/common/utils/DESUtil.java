package com.resoft.accounting.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * @reqno:H1510130134
 * @date-designer:2015年10月16日-songmin
 * @date-author:2015年10月16日-songmin:加密、解密工具类
 */
public class DESUtil {
	private static Logger logger = Logger.getLogger(DESUtil.class);
	public static void main(String[] args) {
		String param = "密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试密文测试";
		String encrypt = DESUtil.encryptDES(param, "$%^&ks21");
		System.out.println(encrypt);
		System.out.println(DESUtil.decryptDES(encrypt, "$%^&ks21"));
	}
	
    private static byte[] iv = {1,2,3,4,5,6,7,8};
    
    public static String encryptDES(String encryptString, String encryptKey) {
    	try{
//          IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
          IvParameterSpec zeroIv = new IvParameterSpec(iv);
          SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("UTF-8"), "DES");
          Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
          cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
          byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
    
          return Base64Util.encode(encryptedData);
    	}
    	catch(Exception ex){
    		return "";
    	}
    }
    
	public static String decryptDES(String decryptString, String decryptKey) {
    	try{
            byte[] byteMi = Base64Util.decode(decryptString);
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
//            IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("UTF-8"), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);
      
            return new String(decryptedData, "UTF-8");
    	}
    	catch(Exception ex){
    		//ex.printStackTrace();
    		logger.error("decryptDES erro:"+ex.getMessage());
    		return "";
    	}
    }
}
