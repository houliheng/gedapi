package com.gq.ged.common.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
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
 * Date    Author      Version     Description
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
        byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decodeFromString(sign));
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
        if (StringUtils.isEmpty(message)) {
            message = "888888";
        }
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
        String prkey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOFQ2F1vkNVVlnB5bgTqIXvYyvrHtt5HRXcV/1qR4q4gqEQr4q/lfFjdslXsjhfe/Nf4xF3VB0UMOFcrhTQVnCNv8h10ahOBGtIjLQ8vU0aXW1g/BdbDuxH0LkKhV6CeerNT71eEvf8x7zquN9jXoyG2GDHk7D2CboejSRXsFv3PAgMBAAECgYEAmEBmBDVIXC/6eUREqMOBHsvu1SkxE+B7Oy49H/lmgJ2GtpIlGxDqRq6APHmdxtmiZ1otbu5tfCX2yEibOQfFOhPkfWNkRgjFcNrqhbUFUrZ8Rwr4wfASB8ev8nPMS+v4qcp5sVUo+JM1y70AgkRmGQ0o+m20muoK9VEXLh78g5ECQQD+Xg1C9RGjy7y8fNZlPYM53xEltR6rWaGYf97md9weBPHgCi0M+PR2CCxQeZZY8i953fMoXgm3i+n8i+moHz6HAkEA4sMPHOWdFTS2yp+jmk0e/DaJLVRSMAYmfNpIKzxaf7SByGmdgtx2E1KRC/Yv1FSqa27Py2CJY9OzDp8p88YQeQJBAKtVzrj7OhaKdWbA7+jhtjKU/ofXqADjLuX5u7qhobLWwdWXBSFnz8JxXugV/h+Lgk8kIVVfgkwQAlwKIikMYqECQQDCMiQ2LhnpeDx2qGJWmWdIvXea/1RnVk0NrW4SwNwL880VkgVXdXAVOieHo+zwHRXZbYu8806V1E3K3tnxr5SZAkAoIr3m/ID3tYSXe12l/6cCyuuC490eYrerqYAIoGO5UuF9+48XFtdf8C3+YTeXaaSgU/3q1grR3yQqMb7DYI9c";
//        String pbkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhUNhdb5DVVZZweW4E6iF72Mr6x7beR0V3Ff9akeKuIKhEK+Kv5XxY3bJV7I4X3vzX+MRd1QdFDDhXK4U0FZwjb/IddGoTgRrSIy0PL1NGl1tYPwXWw7sR9C5CoVegnnqzU+9XhL3/Me86rjfY16Mhthgx5Ow9gm6Ho0kV7Bb9zwIDAQAB";
        String data = "{\n" +
                "\"user_no\":\"QY_666\",\n" +
                "\"item_no\":\"10150001\",\n" +
                "\"payment_amt\":\"1000.00\"\n" +
                "}";

        String mchn = "88721657SUKQQ";
        String seqNo = "LS201711271002";
        String tradeType = "11041109";
        String page = "";
        String back = "";
        String str = mchn+"|"+seqNo+"|"+tradeType+"|"+data+"|"+page+"|"+back;
        String syspbkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDpJO0YwvQ/FkBVnbn9rqwJV0iNAOEKqbIZR9Buoy5Vh4NG81RIT4moxJH50RHEV/ZW471sGaHz+gI2qQSoWnq+fRkpmZmjKYtrT9FLDJ1CPGdLc+Cn2ujpyHRUNFQo2NjeL5uGL4VMQyY5sNu48793KA2p/OQL592pFJ2V7qx8QIDAQAB";
        String sysprkey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMOkk7RjC9D8WQFWduf2urAlXSI0A4QqpshlH0G6jLlWHg0bzVEhPiajEkfnREcRX9lbjvWwZofP6AjapBKhaer59GSmZmaMpi2tP0UsMnUI8Z0tz4Kfa6OnIdFQ0VCjY2N4vm4YvhUxDJjmw27jzv3coDan85Avn3akUnZXurHxAgMBAAECgYB1wVs92dfE4/5Wdcp/99uKJhMWgSQUqu9T8rmLgFMvQow366H60P1tl/1q1eIcvHJqi1dSvegYmqTYn3Dd3dW3j8MM2OJJbFA5Gn5SXu2ytd+amn2X1IhMU+vxOjyByUZXhkb24hBS1E4S3r6MR2klDZEcDMYv/Q5qgTd7YhD6GQJBAPoqbNagD+3GlzlCmzR8YvAqBN0xOg9EDNDpUAmgvu2NUYaaae1kMNnY0Dz3mgrb3IcyCaBZQLOkNA3UOeIMe7cCQQDINKG+Qff7MBQ8QWzWeDYhpI86fIwEpbHv5+tLsgd9eisLcrSBfXNi+BUOj9u2ymwyR7TTJmVszjXOH96N0g+XAkBsgb5XkXGXRs4M7hD3wQQA+5jEgDeYq4GLk8c9hdWKoAM+iPdtGkn+E4avCw5rq1WJ5nA6drwTryNM5EJ/A78dAkEAmlZIof0FCHVGBDWDi8pcII9VgwbkgeiiRYmKEG6L66U0bbzdfj2RqCgLQ5CPFljyE8jHk0c0RS6qt95FmRrovQJBAOl1eQwszgpFdZ4Mpga5sCO7U7NOZf9FLXs4AR0uGsnyX6l0aquO6ftF+n8FfBd737QDKGVFcLOoB7jTPN1ogUs=";

//        String RSAData = encryptedDataOnJava(data,sysprkey);
//        System.out.println("加密数据:"+RSAData);
        System.out.println("-----------------");
        String signature = sign(str.getBytes(),sysprkey);
        System.out.println("签名:"+signature);
        boolean a = verify(str.getBytes(),syspbkey,signature);
        System.out.println(a);
    }
}