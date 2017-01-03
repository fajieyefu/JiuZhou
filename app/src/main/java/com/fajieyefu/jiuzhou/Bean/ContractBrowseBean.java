package com.fajieyefu.jiuzhou.Bean;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by qiancheng on 2016/11/1.
 */
public class ContractBrowseBean implements Serializable, Comparable<ContractBrowseBean> {
	private String textName;
	private String model_code;
	private String textContent;
	private String text_parent="";
	private String parent_code;
	private String class_code;
	public ContractBrowseBean(String textName,String textContent){
		this.textName=textName;
		this.textContent=textContent;
	}
	public ContractBrowseBean(String parent_code, String text_parent, String textName, String textContent) {
		this.text_parent = text_parent;
		this.parent_code=parent_code;
		this.textName = textName;
		this.textContent = textContent;
	}

	public ContractBrowseBean(String parent_code, String text_parent, String class_code, String textName, String model_code, String textContent) {
		this.parent_code = parent_code;
		this.text_parent = text_parent;
		this.class_code = class_code;
		this.textName = textName;
		this.model_code = model_code;
		this.textContent = textContent;
	}
	public String getClass_code() {
		return class_code;
	}

	public void setClass_code(String class_code) {
		this.class_code = class_code;
	}

	public String getModel_code() {
		return model_code;
	}

	public void setModel_code(String model_code) {
		this.model_code = model_code;
	}

	public String getParent_code() {
		return parent_code;
	}

	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}

	public String getText_parent() {
		return text_parent;
	}

	public void setText_parent(String text_parent) {
		this.text_parent = text_parent;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}



	@Override
	public int compareTo(ContractBrowseBean o) {
		return this.getParent_code().compareTo(o.getParent_code());
	}
}
