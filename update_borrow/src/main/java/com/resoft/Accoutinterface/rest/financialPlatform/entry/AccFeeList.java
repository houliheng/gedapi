package com.resoft.Accoutinterface.rest.financialPlatform.entry;
import java.io.Serializable;
import java.math.BigDecimal;


	public class AccFeeList  implements Serializable{

		
		private static final long serialVersionUID = 1L;
		
		
		private String fee_type;
		private BigDecimal fee_amt;
		protected String getFeeType() {
			return fee_type;
		}
		public String getFee_type() {
			return fee_type;
		}
		public void setFee_type(String fee_type) {
			this.fee_type = fee_type;
		}
		public BigDecimal getFee_amt() {
			return fee_amt;
		}
		public void setFee_amt(BigDecimal fee_amt) {
			this.fee_amt = fee_amt;
		}
		protected void setFeeType(String fee_type) {
			this.fee_type = fee_type;
		}
		protected BigDecimal getFeeAmt() {
			return fee_amt;
		}
		protected void setFeeAmt(BigDecimal fee_amt) {
			this.fee_amt = fee_amt;
		}
	}