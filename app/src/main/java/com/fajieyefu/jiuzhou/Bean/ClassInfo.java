package com.fajieyefu.jiuzhou.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiancheng on 2016/10/27.
 */
public class ClassInfo implements Serializable {
	private String class_code;
	private String class_name;
	private List<ModelInfo> modelInfo;
	public ClassInfo(String class_code,String class_name,List<ModelInfo> modelInfo){
		this.class_code=class_code;
		this.class_name=class_name;
		this.modelInfo=modelInfo;

	}

	public List<ModelInfo> getModelInfo() {
		return modelInfo;
	}

	public void setModelInfo(List<ModelInfo> modelInfo) {
		this.modelInfo = modelInfo;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getClass_code() {
		return class_code;
	}

	public void setClass_code(String class_code) {
		this.class_code = class_code;
	}
}
