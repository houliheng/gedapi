package com.gq.ged.common.utils;


import java.nio.ByteBuffer;
import java.util.Random;

import com.gq.ged.common.utils.crypto.DESCoding;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;



/**
 * User: Cougar Date: 14-5-11 Time: 下午6:16
 */
public class TokenUtil {

    //游客
    public static final byte GUEST_FLAG = (byte) 0x09;
    //登录用户
    public static final byte SOURCE_LOGIN_USER = (byte) 0x01;

    public static final String guestId = "00000000000000000000000000000000";

    private static final Random random = new Random();
    private static DESCoding des = null;

    static {
        try {
            des = new DESCoding("CASHGQ(<(*l!#j%a*H*a$>@(".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(TokenUtil.decodeToken("AWxI9R61zHtsgU56cM89v8eU1I7-alu3SXWienaJOZZzLEHRB4EWnZZRGQkqTmyYGJE3c_ZXNtTyRsoaeAcizawff7eKO3GQNULYlyydnAmIEBUtkWtWvcXgQBmTwl5b22-gwAgcR70KgIcIdvfy9Y9ia9KOoY3uxw9QuRmuvazsYWHNYNQ-kJ9V"));


    }


    /**
     * 生成guest用户token
     *
     * @param deviceId
     * @return
     */
    public static String encodeTokenForGuest(String deviceId) {
        return encodeToken(GUEST_FLAG, guestId, deviceId);
    }

    /**
     * 生成登录用户token
     *
     * @param uid
     * @param deviceId
     * @return
     */
    public static String encodeTokenForUser(String uid, String deviceId) {
        return encodeToken(SOURCE_LOGIN_USER, uid, deviceId);

    }


    /**
     * 加密字符串
     *
     * @param source
     * @param userId
     * @param deviceId
     * @return
     */
    public static String encodeToken(byte source, String userId, String deviceId) {
        String token = null;
        /*if (userId.length() != 32 || deviceId.length() != 32) {
            return token;
        }*/
        int crtTime = (int) (System.currentTimeMillis() / 1000); // 精确到秒的crtTime
        //初始化byte
        ByteBuffer buf = ByteBuffer.allocate(133);
        buf.put(source);
        //写入userid
        for (char a : userId.toCharArray()) {
            buf.putChar(a);
        }
        //写入deviceId
        for (char a : deviceId.toCharArray()) {
            buf.putChar(a);
        }
        //记录时间戳
        buf.putInt(crtTime);
        buf.flip();

        //des encode
        byte[] encodeBytes = des.encode(buf.array());
        if (encodeBytes == null) {
            return token;
        }

        //混入随机数
        byte[] realBytes = new byte[138];
        byte[] plus = new byte[2];
        plus[0] = 1;
        plus[1] = (byte) random.nextInt(1234567890);
        // 中间16byte为实际数据，前缀加1byte sid版本，后缀加1byte 随机数
        System.arraycopy(plus, 0, realBytes, 0, 1);
        System.arraycopy(reverse(encodeBytes), 0, realBytes, 1, 136);
        System.arraycopy(plus, 1, realBytes, 137, 1);
        //生成base64字符串
        token = StringUtils.newStringUtf8(Base64.encodeBase64URLSafe(realBytes));
        return token;
    }

    /**
     * 解密token串
     */
    public static TokenInfo decodeToken(String token) {
        if (StringUtil.isEmpty(token)) {
            return null;
        }
        TokenInfo tokenInfo = new TokenInfo();
        // super base64 decode
        byte[] base64Bytes = Base64.decodeBase64(token.getBytes(Charsets.UTF_8));
        if (base64Bytes == null) {
            return null;
        }

        // 获取第一byte，得到sid版本，倒数第一byte，随机数，忽略
        if (base64Bytes == null || base64Bytes.length != 138) {
            return null;
        }
        //排除随机数
        byte version = base64Bytes[0];
        if (version != 1) {
            return null;
        }
        byte randNum = base64Bytes[137];

        byte[] realBytes = new byte[136];
        // 中间136byte为实际数据
        System.arraycopy(base64Bytes, 1, realBytes, 0, 136);
        // des decode
        byte[] decodeBytes = des.decode(reverse(realBytes));
        if (decodeBytes == null || decodeBytes.length != 133) {
            return null;
        }

        // convert data to int array
        ByteBuffer buf = ByteBuffer.wrap(decodeBytes);
        byte source = buf.get();
        StringBuilder userId = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            userId.append(buf.getChar());
        }

        StringBuilder deviceId = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            deviceId.append(buf.getChar());
        }

        int crtTime = buf.getInt();
        tokenInfo.setFlag(source);
        tokenInfo.setUid(userId.toString());
        tokenInfo.setDid(deviceId.toString());
        tokenInfo.setCrtTime(crtTime);
        return tokenInfo;
    }

    /**
     * 固定字节倒序   后续可改为 奇偶位互换
     */
    private static byte[] reverse(byte[] myByte) {
        byte[] newByte = new byte[myByte.length];
        for (int i = 0; i < myByte.length; i++) {
            newByte[i] = myByte[myByte.length - 1 - i];
        }
        return newByte;
    }

}
