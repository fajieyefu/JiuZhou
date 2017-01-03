package com.fajieyefu.jiuzhou.Bean;

/**
 * Created by qiancheng on 2016/10/27.
 */
public class ModelInfo {
	private String model_name;
	private String model_code;
	private int choose_flag=0;

	public ModelInfo(String model_code, String model_name) {
		this.model_code = model_code;
		this.model_name = model_name;
	}

	public int getChoose_flag() {
		return choose_flag;
	}

	public void setChoose_flag(int choose_flag) {
		this.choose_flag = choose_flag;
	}

	public String getModel_code() {
		return model_code;
	}

	public void setModel_code(String model_code) {
		this.model_code = model_code;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}
}
