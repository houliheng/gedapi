package com.resoft.accounting.fdfs.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.resoft.accounting.fdfs.Constant;


public class StringUtils extends Constant {

	public static String getDateStringFromApplyNo(String applyNo) {
		String registerDate = applyNo.substring(dateStringStartIndexInJinjianNo, dateStringStartIndexInJinjianNo + dateStringLength);
		if (dateStringLength == 8) {
			registerDate = new StringBuffer(registerDate).insert(6, '-').insert(4, '-').toString();
		} else if (dateStringLength == 6) {
			registerDate = new StringBuffer(registerDate).insert(4, '-').insert(2, '-').insert(0, "20").toString();
		}
		return registerDate;
	}

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getCurrentTimeString() {
		return DATE_FORMAT.format(new Date());
	}
	
}
