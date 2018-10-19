package com.resoft.accounting.holiday.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
public class Holiday extends DataEntity<Holiday> {
		private static final long serialVersionUID = -5506180523815127069L;
		private Date hldDate;			//节假日日期
		private Date hldStartDate;		//开始时间
		private Date hldEndDate;			//结束时间
		private String hldName;			//节假日名称
	
		public Date getHldDate() {
			return hldDate;
		}
		public void setHldDate(Date hldDate) {
			this.hldDate = hldDate;
		}
		
		public Date getHldStartDate() {
			return hldStartDate;
		}
		public void setHldStartDate(Date hldStartDate) {
			this.hldStartDate = hldStartDate;
		}
		public Date getHldEndDate() {
			return hldEndDate;
		}
		public void setHldEndDate(Date hldEndDate) {
			this.hldEndDate = hldEndDate;
		}
		public String getHldName() {
			return hldName;
		}
		public void setHldName(String hldName) {
			this.hldName = hldName;
		}
}