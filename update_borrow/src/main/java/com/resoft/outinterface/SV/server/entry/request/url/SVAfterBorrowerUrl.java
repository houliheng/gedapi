package com.resoft.outinterface.SV.server.entry.request.url;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVAfterBorrowerUrl {
	@XmlElement(required=true,name="DAY25_REAUDIT")
	private  List<SVRequestBaseData> reaudit;
	@XmlElement(required=true,name="DAY15_OVERDUE")
	private  List<SVRequestBaseData> overdue;
	@XmlElement(required=true,name="REMIND_731")
	private  List<SVRequestBaseData> remind;
	@XmlElement(required=true,name="DAILY_CHECK")
	private  List<SVRequestBaseData> dailyCheck;
	public List<SVRequestBaseData> getReaudit() {
		return reaudit;
	}
	public void setReaudit(List<SVRequestBaseData> reaudit) {
		this.reaudit = reaudit;
	}
	public List<SVRequestBaseData> getOverdue() {
		return overdue;
	}
	public void setOverdue(List<SVRequestBaseData> overdue) {
		this.overdue = overdue;
	}
	public List<SVRequestBaseData> getRemind() {
		return remind;
	}
	public void setRemind(List<SVRequestBaseData> remind) {
		this.remind = remind;
	}
	public List<SVRequestBaseData> getDailyCheck() {
		return dailyCheck;
	}
	public void setDailyCheck(List<SVRequestBaseData> dailyCheck) {
		this.dailyCheck = dailyCheck;
	}
}
