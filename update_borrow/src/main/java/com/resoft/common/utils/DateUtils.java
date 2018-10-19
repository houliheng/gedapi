package com.resoft.common.utils;

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
    
   	public static String getLastSunday(String str) {
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
 
   	/**
   	 * 获取上月最后一天的日期
   	 * @param date   格式为：yyyy-MM-dd
   	 * @return    sunday   格式为：yyyy-MM-dd
   	 */
   	public static String getLastDayOfMonth(String dateString) {
   		Calendar cal = Calendar.getInstance();
   		String[] strs = dateString.split("-");
   		if(strs.length != 3){
   			return null;
   		}
   		int year = new Integer(strs[0]);
   		int month = new Integer(strs[1]);
   		int date = new Integer(strs[2]);
   		cal.set(year, month-1, date);
   		cal.set(Calendar.DAY_OF_MONTH,1);
   		cal.add(Calendar.DAY_OF_MONTH, -1);
   		String lastDayOfMonth =DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd");
   		return lastDayOfMonth;
   	}
   	
   	public static String getLastDayOfLastSeveralMonth(String dateString,int num) {
   		Calendar cal = Calendar.getInstance();
   		String[] strs = dateString.split("-");
   		if(strs.length != 3){
   			return null;
   		}
   		int year = new Integer(strs[0]);
   		int month = new Integer(strs[1]);
   		int date = new Integer(strs[2]);
   		cal.set(year, month-num, date);
   		cal.set(Calendar.DAY_OF_MONTH,1);
   		cal.add(Calendar.DAY_OF_MONTH, -1);
   		String lastDayOfMonth =DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd");
   		return lastDayOfMonth;
   	}
   	/**
   	 * 获取下个月的前一天
   	 * */
   	public static Date getNextMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		cal.add(Calendar.MONTH, 1);
		return cal.getTime();
	}
  
}
