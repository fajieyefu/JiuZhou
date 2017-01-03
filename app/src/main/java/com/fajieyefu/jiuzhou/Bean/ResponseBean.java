package com.fajieyefu.jiuzhou.Bean;

import java.util.List;

/**
 * Created by qiancheng on 2016/12/14.
 */
public class ResponseBean {
	private int code;
	private String msg;
	private List<CompleteCarBean> completeCar;
	private LoanDetail loanDetail;
	private int num;



	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public LoanDetail getLoanDetail() {
		return loanDetail;
	}

	public void setLoanDetail(LoanDetail loanDetail) {
		this.loanDetail = loanDetail;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<CompleteCarBean> getCompleteCar() {
		return completeCar;
	}

	public void setCompleteCar(List<CompleteCarBean> completeCar) {
		this.completeCar = completeCar;
	}
}
