package com.resoft.outinterface.SV.server.entry.response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Response")
@XmlType( propOrder={"msg","respCode","success"})
public class SVResponse {
	@XmlElement(name="RESP_MSG")
		private String msg;
	@XmlElement(name="RESP_CODE")
		private String respCode;
	@XmlElement(name="SUCCESS")
		private boolean success;
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getRespCode() {
			return respCode;
		}
		public void setRespCode(String respCode) {
			this.respCode = respCode;
		}
		
}
