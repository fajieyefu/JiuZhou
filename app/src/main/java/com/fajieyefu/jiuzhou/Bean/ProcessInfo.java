package com.fajieyefu.jiuzhou.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiancheng on 2016/10/27.
 */
public class ProcessInfo implements Serializable {
	private String process_code;
	private String process_name;
	private List<ClassInfo> modelInfoList;

	public ProcessInfo(String process_code, String process_name,List<ClassInfo> list) {
		this.process_code = process_code;
		this.process_name = process_name;
		this.modelInfoList=list;
	}

	public List<ClassInfo> getClassInfoList() {
		return modelInfoList;
	}

	public void setClassInfoList(List<ClassInfo> modelInfoList) {
		this.modelInfoList = modelInfoList;
	}

	public String getProcess_code() {
		return process_code;
	}

	public void setProcess_code(String process_code) {
		this.process_code = process_code;
	}

	public String getProcess_name() {
		return process_name;
	}

	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
}
