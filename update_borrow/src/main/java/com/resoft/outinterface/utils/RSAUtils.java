package com.resoft.outinterface.utils;

import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Filename:    com.gqhmt.asset.util
 * Copyright:   Copyright (c)2017
 * Company:     冠群驰骋
 * RSA公钥/私钥/签名工具包
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 *
 * @author: L.P.F.
 * @version: 1.0
 * @since: JDK 1.8
 * Create at:  2017-10-12 14:12
 * Description:
 * <p/>
 * Modification History:
 * DateTime    Author      Version     Description
 * -----------------------------------------------------------------
 * 2017-10-12  LPF      1.0     1.0 Version
 */


public class RSAUtils {

    /** */
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** */
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /** */
    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /** */
    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return URLEncoder.encode(Base64Utils.encodeToString(signature.sign()), "UTF-8");
    }
    /** */
    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String signCallBack(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encodeToString(signature.sign());
    }

    /** */
    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        if(sign.contains("%") || sign.contains(" ")){
            sign= URLDecoder.decode(sign,"utf-8");
        }
        byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        boolean verify = false;
        try {
            verify = signature.verify(Base64Utils.decodeFromString(sign));
        }catch (Exception e){
            System.out.println(e);
        }
        return verify;
    }

    /** */
    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
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

    /** */
    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
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

    /** */
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
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

    /** */
    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
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
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encodeToString(key.getEncoded());
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encodeToString(key.getEncoded());
    }

    /**
     * java端公钥加密
     */
    public static String encryptedDataOnJava(String data, String PUBLICKEY) {
        try {
            data = Base64Utils.encodeToString(encryptByPublicKey(data.getBytes("UTF-8"), PUBLICKEY));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    /**
     * java端私钥解密
     */
    public static String decryptDataOnJava(String data, String PRIVATEKEY) throws Exception {
        String temp = "";
        byte[] rs = Base64Utils.decodeFromString(data);
        temp = new String(decryptByPrivateKey(rs, PRIVATEKEY)); //以utf-8的方式生成字符串
        return temp;
    }


    public static String md5Encode(String message) {
        if (StringUtils.isEmpty(message)) message = "888888";
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位
            // 转换为16进制字符串
            StringBuffer hexStr = new StringBuffer();
            int num;
            for (int i = 0; i < md5Byte.length; i++) {
                num = md5Byte[i];
                if (num < 0) {
                    num += 256;
                }
                if (num < 16) {
                    hexStr.append("0");
                }
                hexStr.append(Integer.toHexString(num));
            }
            md5 = hexStr.toString().toUpperCase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }


    public static void main(String[] args) throws Exception {
        String ss = "88721657SUKQ|201807101511532182883|11090004|{\"bidId\":10673,\"contractAmount\":2000000.00,\"contractNo\":\"HG-GJZ00180710AD\",\"custId\":\"23088\",\"custType\":\"1\",\"productName\":\"常规优企贷\",\"tenderList\":{\"amount\":2000000.0,\"bidId\":10673,\"bonusAmount\":0.0,\"custType\":2,\"customerId\":\"20308\",\"investTime\":1531205587000,\"realAmount\":2000000.0,\"tenderNo\":\"0e7e4f94-581c-4df8-a075-c6937d1c1946\"}}||http://10.100.200.119:81/api/fssCallBack/commonBidFullNotice";//        String data="a2RnVMYvbbMW4typQjiOUrOwKvAgijxxb0RQbAgjGN8+oHphcWGNDjAUsjJcoOO2x/fbMGTEqlYSBJGbQ76mc68W+f0JQ7KomXFfJy15JoKUqNh9I+pCMnlCitq57Vb1Bt0R/JC9YxHDlLtwHMx5ZDh/33PIxwRlYS50FGIndfc=";
        //String ss = "88721657SUKQ|201807101511532182883|11090004|{\"bidId\":10673,\"contractAmount\":2000000.00,\"contractNo\":\"HG-GJZ00180710AD\",\"custId\":\"23088\",\"custType\":\"1\",\"productName\":\"常规优企贷\",\"tenderList\":[{\"amount\":2000000.0,\"bidId\":10673,\"bonusAmount\":0.0,\"custType\":2,\"customerId\":\"20308\",\"investTime\":1531205587000,\"realAmount\":2000000.0,\"tenderNo\":\"0e7e4f94-581c-4df8-a075-c6937d1c1946\"}]}||http://10.100.200.119:81/api/fssCallBack/commonBidFullNotice";//        String data="a2RnVMYvbbMW4typQjiOUrOwKvAgijxxb0RQbAgjGN8+oHphcWGNDjAUsjJcoOO2x/fbMGTEqlYSBJGbQ76mc68W+f0JQ7KomXFfJy15JoKUqNh9I+pCMnlCitq57Vb1Bt0R/JC9YxHDlLtwHMx5ZDh/33PIxwRlYS50FGIndfc=";
        String sysprkey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMOkk7RjC9D8WQFWduf2urAlXSI0A4QqpshlH0G6jLlWHg0bzVEhPiajEkfnREcRX9lbjvWwZofP6AjapBKhaer59GSmZmaMpi2tP0UsMnUI8Z0tz4Kfa6OnIdFQ0VCjY2N4vm4YvhUxDJjmw27jzv3coDan85Avn3akUnZXurHxAgMBAAECgYB1wVs92dfE4/5Wdcp/99uKJhMWgSQUqu9T8rmLgFMvQow366H60P1tl/1q1eIcvHJqi1dSvegYmqTYn3Dd3dW3j8MM2OJJbFA5Gn5SXu2ytd+amn2X1IhMU+vxOjyByUZXhkb24hBS1E4S3r6MR2klDZEcDMYv/Q5qgTd7YhD6GQJBAPoqbNagD+3GlzlCmzR8YvAqBN0xOg9EDNDpUAmgvu2NUYaaae1kMNnY0Dz3mgrb3IcyCaBZQLOkNA3UOeIMe7cCQQDINKG+Qff7MBQ8QWzWeDYhpI86fIwEpbHv5+tLsgd9eisLcrSBfXNi+BUOj9u2ymwyR7TTJmVszjXOH96N0g+XAkBsgb5XkXGXRs4M7hD3wQQA+5jEgDeYq4GLk8c9hdWKoAM+iPdtGkn+E4avCw5rq1WJ5nA6drwTryNM5EJ/A78dAkEAmlZIof0FCHVGBDWDi8pcII9VgwbkgeiiRYmKEG6L66U0bbzdfj2RqCgLQ5CPFljyE8jHk0c0RS6qt95FmRrovQJBAOl1eQwszgpFdZ4Mpga5sCO7U7NOZf9FLXs4AR0uGsnyX6l0aquO6ftF+n8FfBd737QDKGVFcLOoB7jTPN1ogUs=";
        String syspbkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDpJO0YwvQ/FkBVnbn9rqwJV0iNAOEKqbIZR9Buoy5Vh4NG81RIT4moxJH50RHEV/ZW471sGaHz+gI2qQSoWnq+fRkpmZmjKYtrT9FLDJ1CPGdLc+Cn2ujpyHRUNFQo2NjeL5uGL4VMQyY5sNu48793KA2p/OQL592pFJ2V7qx8QIDAQAB";
//        verify(ss.getBytes(),syspbkey,data);

//        String data = "{\"amount\":10000.00,\"contractNo\":\"GJX00190625AD-B-B\",\"custId\":\"17302\",\"custType\":\"1\",\"feeList\":[{\"feeAmount\":10000.00,\"feeType\":11085002}],\"platform\":\"10040001\"}";
//
//        String mchn = "88721657SUKQQ";
//        String seqNo = "LS201711271002";
//        String tradeType = "11041109";
////        String str = mchn+"|"+seqNo+"|"+tradeType+"|"+data+"|"+""+"|"+"";
////        String str = "04543666UDHF|20180424172031502|11022001|{\"custId\":\"622675\",\"mobile\":\"13515157534\"}|http://10.100.32.203:9000/yqh-gedh5-0314/finish.html|http://10.100.161.215:9090/v2/api/account/personAccountCallBack";
////        System.out.println("数据:" + URLEncoder.encode(data, "UTF-8"));
//
        String signature = signCallBack(ss.getBytes(), sysprkey);
        System.out.println(signature);
//
//        System.out.println("签名:" + signature);
//        signature= URLDecoder.decode(signature,"utf-8");
        boolean a = verify(ss.getBytes(), syspbkey, signature);
        System.out.println(a);
    }
}