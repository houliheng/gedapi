package com.resoft.accounting.common.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtils extends com.thinkgem.jeesite.common.utils.DateUtils{
	//时间处理
    public static String formatDateTime(long timeSecond){
		long day = timeSecond/(24*60*60);
		long hour = (timeSecond/(60*60)-day*24);
		long min = ((timeSecond/(60))-day*24*60-hour*60);
		long s = (timeSecond-day*24*60*60-hour*60*60-min*60);
		return (day>0?day+"天":"")+(hour>0?hour+"时":"")+(min>0?min+"分":"")+(s>0?s+"秒":"");
    }
    
   	public static String getLsatSunday(String str) {
   		Calendar cal =Calendar.getInstance();
   		String[] strs=str.split("-");
   		if(strs.length!=3){
   			return null;
   		}
   		Integer year =new Integer(strs[0]);
   		Integer month =new Integer(strs[1]);
   		Integer date =new Integer(strs[2]);
   		cal.set(year.intValue(), (month.intValue()-1), date.intValue());
   		cal.add(Calendar.DATE, -1);
   		cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
   		String sunday =DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd");
   		return sunday;
   		
   	}
   	
   	public static String getLastDayOfMonth(String str) {
   		Calendar cal = Calendar.getInstance();
   		String[] strs = str.split("-");
   		if(strs.length!= 3){
   			return null;
   		}
   		int year = new Integer(strs[0]);
   		int month = new Integer(strs[1]);
   		int date = new Integer(strs[2]);
   		cal.set(year, month-1, date);
   		cal.set(Calendar.DAY_OF_MONTH,1);
   		cal.add(Calendar.DAY_OF_MONTH, -1);
   		String lastDayOfMonth = DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd");
   		return lastDayOfMonth;
   	}
   
   	public static String getLastDayOfLastSeveralMonth(String str,int num) {
   		Calendar cal = Calendar.getInstance();
   		String[] strs= str.split("-");
   		if(strs.length!= 3){
   			return null;
   		}
   		int year = new Integer(strs[0]);
   		int month = new Integer(strs[1]);
   		int date = new Integer(strs[2]);
   		cal.set(year, month-num, date);
   		cal.set(Calendar.DAY_OF_MONTH,1);
   		cal.add(Calendar.DAY_OF_MONTH, -1);
   		String lastDayOfMonth = DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd");
   		return lastDayOfMonth;
   	}

   	public static String getLastDay(int day){
		Calendar calendar = Calendar.getInstance();//得到日历
		calendar.setTime(new Date());//把时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -day);
		String lastDay = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");//获得前一天时间
		return lastDay;
   	}
}
