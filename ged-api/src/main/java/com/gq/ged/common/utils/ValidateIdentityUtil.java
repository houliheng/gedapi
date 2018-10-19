package com.gq.ged.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateIdentityUtil {
    private final static Pattern PARTTERN_CARD_NO = Pattern.compile("\\d{15}|\\d{17}[0-9X]");
    private final static Pattern PARTTERN_DATE = Pattern.compile(
            "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)");
    // 1-17位相乘因子数组
    private final static int[] FACTOR = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    // 18位随机码数组
    private final static char[] RANDOM = "10X98765432".toCharArray();

    /**
     * 验证身份证号是否合法
     * @param idCardNo
     * @return
     */
    public static boolean validate(String idCardNo) {
        // 对身份证号进行长度等简单判断
        if (idCardNo == null || !PARTTERN_CARD_NO.matcher(idCardNo).matches()) {
            return false;
        }
        int len = idCardNo.length();
        // 一代身份证
        if (len == 15) {
            return PARTTERN_DATE.matcher("19" + idCardNo.substring(6, 12)).matches();
        }
        // 二代身份证
        if (len == 18 && PARTTERN_DATE.matcher(idCardNo.substring(6, 14)).matches()) {
            // 判断随机码是否相等
            return calculateRandom(idCardNo) == idCardNo.charAt(17);
        } else {
            return false;
        }

    }

    /**
     * 计算最后一位随机码
     *
     * @param idCardNo
     * @return
     */
    private static char calculateRandom(String idCardNo) {
        // 计算1-17位与相应因子乘积之和
        int total = 0;
        for (int i = 0; i < 17; i++) {
            total += Character.getNumericValue(idCardNo.charAt(i)) * FACTOR[i];
        }
        // 判断随机码是否相等
        return RANDOM[total % 11];
    }


    /**
     * @param mobile
     * @return
     *  验证手机号是否合法
     */
    public static boolean isMobileNO(String mobile) {
        if (mobile.length() != 11) {
            return false;
        } else {
            /**
             * 移动号段正则表达式
             */
            String pat1 = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
            /**
             * 联通号段正则表达式
             */
            String pat2 = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";
            /**
             * 电信号段正则表达式
             */
            String pat3 = "^((133)|(153)|(177)|(18[0,1,9])|(149)|(199))\\d{8}$";
            /**
             * 虚拟运营商正则表达式
             */
            String pat4 = "^((170))\\d{8}|(1718)|(1719)\\d{7}$";

            Pattern pattern1 = Pattern.compile(pat1);
            Matcher match1 = pattern1.matcher(mobile);
            boolean isMatch1 = match1.matches();
            if (isMatch1) {
                return true;
            }
            Pattern pattern2 = Pattern.compile(pat2);
            Matcher match2 = pattern2.matcher(mobile);
            boolean isMatch2 = match2.matches();
            if (isMatch2) {
                return true;
            }
            Pattern pattern3 = Pattern.compile(pat3);
            Matcher match3 = pattern3.matcher(mobile);
            boolean isMatch3 = match3.matches();
            if (isMatch3) {
                return true;
            }
            Pattern pattern4 = Pattern.compile(pat4);
            Matcher match4 = pattern4.matcher(mobile);
            boolean isMatch4 = match4.matches();
            if (isMatch4) {
                return true;
            }
            return false;
        }
    }
    public static void main(String[] args) {
        System.out.println(validate("23232619951002384X"));
    }
}
