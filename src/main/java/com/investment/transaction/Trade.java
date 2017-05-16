package com.investment.transaction;

public class Trade implements java.io.Serializable {
	private String rowKey;
	private String trd_dt;
	private String trd_pst_dt;
	private String brn_i;
	private String acc_i;
	private String curr_ver;
	private String brn_stat_c;
	private String txn_rec_typ;
	private String pw_scty_i;
	private String pro_clasfn_c;
	private String rule_R001="N";
		
	public Trade(String rowKey, 
			String acc_i, 
			String brn_i, 
			String brn_stat_c, 
			String curr_ver, 
			String pro_clasfn_c, 
			String pw_scty_i,
			String trd_dt,
			String trd_pst_dt,
			String txn_rec_typ) {
		this.rowKey = rowKey;
		this.acc_i = acc_i;
		this.brn_i = brn_i;
		this.brn_stat_c = brn_stat_c;
		this.curr_ver = curr_ver;
		this.pro_clasfn_c = pro_clasfn_c;
		this.pw_scty_i = pw_scty_i;
		this.trd_dt = trd_dt;
		this.trd_pst_dt = trd_pst_dt;		
		this.txn_rec_typ = txn_rec_typ;
		
	}
	
	public String getRowKey() {
		return rowKey;
	}
	

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
		
	public String getTrd_dt() {
		return trd_dt;
	}
	

	public void setTrd_dt(String trd_dt) {
		this.trd_dt = trd_dt;
	}
	

	public String getTrd_pst_dt() {
		return trd_pst_dt;
	}
	

	public void setTrd_pst_dt(String trd_pst_dt) {
		this.trd_pst_dt = trd_pst_dt;
	}
	

	public String getBrn_i() {
		return brn_i;
	}
	

	public void setBrn_i(String brn_i) {
		this.brn_i = brn_i;
	}
	

	public String getAcc_i() {
		return acc_i;
	}
	

	public void setAcc_i(String acc_i) {
		this.acc_i = acc_i;
	}
	

	public String getCurr_ver() {
		return curr_ver;
	}
	

	public void setCurr_ver(String curr_ver) {
		this.curr_ver = curr_ver;
	}
	

	public String getBrn_stat_c() {
		return brn_stat_c;
	}
	

	public void setBrn_stat_c(String brn_stat_c) {
		this.brn_stat_c = brn_stat_c;
	}
	

	public String getTxn_rec_typ() {
		return txn_rec_typ;
	}
	

	public void setTxn_rec_typ(String txn_rec_typ) {
		this.txn_rec_typ = txn_rec_typ;
	}
	

	public String getPw_scty_i() {
		return pw_scty_i;
	}
	

	public void setPw_scty_i(String pw_scty_i) {
		this.pw_scty_i = pw_scty_i;
	}
	

	public String getPro_clasfn_c() {
		return pro_clasfn_c;
	}
	

	public void setPro_clasfn_c(String pro_clasfn_c) {
		this.pro_clasfn_c = pro_clasfn_c;
	}
	
	public String getRule_R001() {
		return rule_R001;
	}	

	public void setRule_R001(String rule_R001) {
		this.rule_R001 = rule_R001;
	}
	
	@Override
	public String toString() {
		return rule_R001; 
	}

}
