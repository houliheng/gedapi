package com.gq.ged.common.utils;

import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public class RSAEncryptUtils {



  public static String DEFAULT_PUBLIC_KEY ="";




  public static String DEFAULT_PRIVATE_KEY ="";



  /**
   * 加密算法RSA
   */
  public static final String KEY_ALGORITHM = "RSA";

  /**
   * 签名算法
   */
  public static final String SIGNATURE_ALGORITHM = "MD5withRSA";



  /**
   * RSA最大加密明文大小
   */
  private static final int MAX_ENCRYPT_BLOCK = 117;

  /**
   * RSA最大解密密文大小
   */
  private static final int MAX_DECRYPT_BLOCK = 128;


  /**
   * 私钥
   */
  private static RSAPrivateKey privateKey;

  /**
   * 公钥
   */
  private static RSAPublicKey publicKey;

  /**
   * 字节数据转字符串专用集合
   */
  private static final char[] HEX_CHAR =
      {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


  /**
   * 获取私钥
   * 
   * @return 当前的私钥对象
   */
  public static RSAPrivateKey getPrivateKey() {
    return privateKey;
  }

  /**
   * 获取公钥
   * 
   * @return 当前的公钥对象
   */
  public static RSAPublicKey getPublicKey() {
    return publicKey;
  }

  /**
   * 随机生成密钥对
   */
  public static void genKeyPair() {
    KeyPairGenerator keyPairGen = null;
    try {
      keyPairGen = KeyPairGenerator.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    keyPairGen.initialize(1024, new SecureRandom());
    KeyPair keyPair = keyPairGen.generateKeyPair();
    privateKey = (RSAPrivateKey) keyPair.getPrivate();
    publicKey = (RSAPublicKey) keyPair.getPublic();
  }

  /**
   * 从文件中输入流中加载公钥
   * 
   * @param in 公钥输入流
   * @throws Exception 加载公钥时产生的异常
   */
  public static void loadPublicKey(InputStream in) throws Exception {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String readLine = null;
      StringBuilder sb = new StringBuilder();
      while ((readLine = br.readLine()) != null) {
        if (readLine.charAt(0) == '-') {
          continue;
        } else {
          sb.append(readLine);
          sb.append('\r');
        }
      }
      loadPublicKey(sb.toString());
    } catch (IOException e) {
      throw new Exception("公钥数据流读取错误");
    } catch (NullPointerException e) {
      throw new Exception("公钥输入流为空");
    }
  }


  /**
   * 从字符串中加载公钥
   * 
   * @param publicKeyStr 公钥数据字符串
   * @throws Exception 加载公钥时产生的异常
   */
  public static void loadPublicKey(String publicKeyStr) throws Exception {
    try {


      // byte[] buffer= org.apache.commons.codec.binary.Base64.decodeBase64(publicKeyStr);
      byte[] buffer = Base64.decode(publicKeyStr);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
      publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
    } catch (NoSuchAlgorithmException e) {
      throw new Exception("无此算法");
    } catch (InvalidKeySpecException e) {
      throw new Exception("公钥非法");
    } catch (NullPointerException e) {
      throw new Exception("公钥数据为空");
    }
  }

  /**
   * 从文件中加载私钥
   * 
   * @return 是否成功
   * @throws Exception
   */
  public static void loadPrivateKey(InputStream in) throws Exception {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String readLine = null;
      StringBuilder sb = new StringBuilder();
      while ((readLine = br.readLine()) != null) {
        if (readLine.charAt(0) == '-') {
          continue;
        } else {
          sb.append(readLine);
          sb.append('\r');
        }
      }
      loadPrivateKey(sb.toString());
    } catch (IOException e) {
      throw new Exception("私钥数据读取错误");
    } catch (NullPointerException e) {
      throw new Exception("私钥输入流为空");
    }
  }

  public static void loadPrivateKey(String privateKeyStr) throws Exception {
    try {

      // byte[] buffer= org.apache.commons.codec.binary.Base64.decodeBase64(privateKeyStr);
      byte[] buffer = Base64.decode(privateKeyStr);
      // byte[] buffer= base64Decoder.decodeBuffer(privateKeyStr);
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    } catch (NoSuchAlgorithmException e) {
      throw new Exception("无此算法");
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
      throw new Exception("私钥非法");
    } catch (NullPointerException e) {
      throw new Exception("私钥数据为空");
    }
  }

  /*  *//**
         * 加密过程
         * 
         * @param publicKey 公钥
         * @param plainTextData 明文数据
         * @return
         * @throws Exception 加密过程中的异常信息
         */
  /*
   * public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{
   * if(publicKey== null){ throw new Exception("加密公钥为空, 请设置"); } Cipher cipher= null; try { //
   * cipher= Cipher.getInstance("RSA",new BouncyCastleProvider());//, ); //
   * cipher.init(Cipher.ENCRYPT_MODE, publicKey); // byte[] output= cipher.doFinal(plainTextData);
   * 
   * 
   * 
   * 
   * 
   * cipher = Cipher.getInstance("RSA"); cipher.init(Cipher.DECRYPT_MODE, publicKey); int inputLen =
   * plainTextData.length; ByteArrayOutputStream out = new ByteArrayOutputStream(); int offSet = 0;
   * byte[] cache; int i = 0; // 对数据分段解密 while (inputLen - offSet > 0) { if (inputLen - offSet >
   * MAX_DECRYPT_BLOCK) { cache = cipher.doFinal(plainTextData, offSet, MAX_DECRYPT_BLOCK); } else {
   * cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet); } out.write(cache, 0,
   * cache.length); i++; offSet = i * MAX_DECRYPT_BLOCK; } byte[] decryptedData = out.toByteArray();
   * out.close(); return decryptedData;
   * 
   * 
   * 
   * 
   * } catch (NoSuchAlgorithmException e) { throw new Exception("无此加密算法"); } catch
   * (NoSuchPaddingException e) { e.printStackTrace(); return null; }catch (InvalidKeyException e) {
   * throw new Exception("加密公钥非法,请检查"); } catch (IllegalBlockSizeException e) { throw new
   * Exception("明文长度非法"); } catch (BadPaddingException e) { throw new Exception("明文数据已损坏"); } }
   * 
   *//**
     * 解密过程
     * 
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     *//*
       * public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{ if
       * (privateKey== null){ throw new Exception("解密私钥为空, 请设置"); } Cipher cipher= null; try {
       * cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
       * cipher.init(Cipher.DECRYPT_MODE, privateKey); byte[] output= cipher.doFinal(cipherData);
       * return output; } catch (NoSuchAlgorithmException e) { throw new Exception("无此解密算法"); }
       * catch (NoSuchPaddingException e) { e.printStackTrace(); return null; }catch
       * (InvalidKeyException e) { throw new Exception("解密私钥非法,请检查"); } catch
       * (IllegalBlockSizeException e) { throw new Exception("密文长度非法"); } catch (BadPaddingException
       * e) { throw new Exception("密文数据已损坏"); } }
       */



  /**
   * <P>
   * 私钥解密
   * </p>
   * 
   * @param encryptedData 已加密数据
   * @param privateKey 私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] decryptByPrivateKey(byte[] encryptedData, RSAPrivateKey privateKey)
      throws Exception {

    // Cipher cipher = Cipher.getInstance("RSA",new BouncyCastleProvider());
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    int inputLen = encryptedData.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段解密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
        cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
      } else {
        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * MAX_DECRYPT_BLOCK;
    }
    byte[] decryptedData = out.toByteArray();
    out.close();
    return decryptedData;
  }

  /**
   * <p>
   * 公钥解密
   * </p>
   * 
   * @param encryptedData 已加密数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] decryptByPublicKey(byte[] encryptedData, RSAPublicKey publicKey)
      throws Exception {

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, publicKey);
    int inputLen = encryptedData.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段解密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
        cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
      } else {
        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * MAX_DECRYPT_BLOCK;
    }
    byte[] decryptedData = out.toByteArray();
    out.close();
    return decryptedData;
  }

  /**
   * 使用私钥解密 入参为字符串， 出参为字符串 先base64， 在解密
   * 
   * @param content
   * @return
   */
  public static String decryptByPublicKey(String content) throws Exception {

    try {

      loadPublicKey(RSAEncryptUtils.DEFAULT_PUBLIC_KEY);
      byte[] returnByte = decryptByPublicKey(Base64.decode(content), getPublicKey());
      return new String(returnByte);
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "";
  }

  /**
   * <p>
   * 公钥加密
   * </p>
   * 
   * @param data 源数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] encryptByPublicKey(byte[] data, RSAPublicKey publicKey) throws Exception {

    // 对数据加密
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    int inputLen = data.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段加密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
        cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
      } else {
        cache = cipher.doFinal(data, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * MAX_ENCRYPT_BLOCK;
    }
    byte[] encryptedData = out.toByteArray();
    out.close();
    return encryptedData;
  }

  /**
   * <p>
   * 私钥加密
   * </p>
   * 
   * @param data 源数据
   * @param privateKey 私钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] encryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) throws Exception {

    // Cipher cipher = Cipher.getInstance("RSA",new BouncyCastleProvider());
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");


    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
    int inputLen = data.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段加密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
        cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
      } else {
        cache = cipher.doFinal(data, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * MAX_ENCRYPT_BLOCK;
    }
    byte[] encryptedData = out.toByteArray();
    out.close();
    return encryptedData;
  }



  /**
   * 字节数据转十六进制字符串
   * 
   * @param data 输入数据
   * @return 十六进制内容
   */
  public static String byteArrayToString(byte[] data) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      // 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
      stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
      // 取出字节的低四位 作为索引得到相应的十六进制标识符
      stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
      if (i < data.length - 1) {
        stringBuilder.append(' ');
      }
    }
    return stringBuilder.toString();
  }



  /**
   * 使用私钥加密 入参为字符串， 出参为字符串
   * 
   * 先加密， 在base64
   * 
   * @param content
   * @return
   */
  public static String encryptByPrivateKey(String content) throws Exception {

    try {

      loadPrivateKey(RSAEncryptUtils.DEFAULT_PRIVATE_KEY);
      byte[] returnByte = encryptByPrivateKey(content.getBytes(), getPrivateKey());
      return Base64.encode(returnByte);
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "";
  }

  /**
   * 使用公钥加密 入参为字符串， 出参为字符串
   * 
   * 先加密， 在base64
   * 
   * @param content
   * @return
   */
  public static String encryptByPublicKey(String content) throws Exception {
    try {

      loadPublicKey(RSAEncryptUtils.DEFAULT_PUBLIC_KEY);
      byte[] returnByte = encryptByPublicKey(content.getBytes(), getPublicKey());
      return Base64.encode(returnByte);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * 使用私钥解密 入参为字符串， 出参为字符串 先base64， 在解密
   * 
   * @param content
   * @return
   */
  public static String decryptByPrivateKey(String content) throws Exception {

    try {

      loadPrivateKey(RSAEncryptUtils.DEFAULT_PRIVATE_KEY);
      byte[] returnByte = decryptByPrivateKey(Base64.decode(content), getPrivateKey());
      return new String(returnByte);
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "";
  }



  public static void main(String[] args) throws Exception {


    long time1 = System.currentTimeMillis();

    RSAEncryptUtils rsaEncrypt = new RSAEncryptUtils();



    // 加载公钥
    try {
      loadPublicKey(RSAEncryptUtils.DEFAULT_PUBLIC_KEY);
      System.out.println("加载公钥成功");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.err.println("加载公钥失败");
    }

    // 加载私钥
    try {
      loadPrivateKey(RSAEncryptUtils.DEFAULT_PRIVATE_KEY);
      System.out.println("加载私钥成功");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.err.println("加载私钥失败");
    }


    // 测试字符串
    String encryptStr =
        "{'d':{'attrs':{'测试值53':'测试参数53','测试值32':'测试参数32','测试值82':'测试参数82'},'domains':{}},'v':'1','r':'000006'}";
    String test =
        "{\"trade_type\":\"11020040\",\"page_notify_url\":\"http://10.100.200.63:8080/h5/1.0.0/views/signUp/index.html?userId=3\",\"mchn\":\"88721657SUKQ\",\"cert_mobile\":\"13691271945\",\"cert_name\":\"大王\",\"cust_id\":\"3\",\"cert_no\":\"610326199407131817\",\"page_notify_url_fuiou\":\"\"}";
    System.out.println("私钥长度：" + getPrivateKey().toString().length());
    System.out.println("公钥长度：" + getPublicKey().toString().length());
    try {
      byte[] cipherPrivate =
          encryptByPrivateKey(test.getBytes(), getPrivateKey());
      byte[] cipher = encryptByPublicKey(test.getBytes(), getPublicKey());



      // 加密
      // byte[] cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), encryptStr.getBytes());
      System.out.println("密文长度:" + cipher.length);
      System.out.println("密文长度:" + cipherPrivate.length);
      String param = Base64.encode(cipher);
      String paramPrivate = Base64.encode(cipherPrivate);
      System.out.println(param);
      System.out.println(paramPrivate);
      // 解密
      byte[] plainText =
          decryptByPrivateKey(Base64.decode(param), getPrivateKey());


      System.out.println("明文长度:" + plainText.length);
      System.out.println("====");
      System.out.println(new String(plainText));
      long time2 = System.currentTimeMillis();
      System.out.println(time2 - time1);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getMessage());
    }
  }
}
