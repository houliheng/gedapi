package com.resoft.accounting.bankAccountStatement.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BankStatementDateUtil {
    public static String getTradeDate(String tradeDate) throws ParseException {
        SimpleDateFormat smf = new SimpleDateFormat("yyyyMMdd");
        Date date = smf.parse(tradeDate);

        SimpleDateFormat smf1 = new SimpleDateFormat("yyyy-MM-dd");
        tradeDate = smf1.format(date);
        return tradeDate;
    }
    public static String getTradeTime(String tradeTime) throws ParseException {
        SimpleDateFormat smf = new SimpleDateFormat("HHmmss");
        Date date = smf.parse(tradeTime);

        SimpleDateFormat smf1 = new SimpleDateFormat("HH:mm:ss");
        tradeTime = smf1.format(date);
        return tradeTime;
    }
}
