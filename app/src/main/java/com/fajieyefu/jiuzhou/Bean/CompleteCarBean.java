package com.fajieyefu.jiuzhou.Bean;

/**
 * Created by qiancheng on 2016/12/15.
 */
public class CompleteCarBean {
	private String date;
	private String chuche_num;//出车数量
	private String penqi_num;//喷漆
	private String fache_num;//发车
	private String contract_car_num;//签订合同

	public String getChuche_num() {
		return chuche_num;
	}

	public String getContract_car_num() {
		return contract_car_num;
	}

	public void setContract_car_num(String contract_car_num) {
		this.contract_car_num = contract_car_num;
	}

	public void setChuche_num(String chuche_num) {
		this.chuche_num = chuche_num;
	}



	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFache_num() {
		return fache_num;
	}

	public void setFache_num(String fache_num) {
		this.fache_num = fache_num;
	}

	public String getPenqi_num() {
		return penqi_num;
	}

	public void setPenqi_num(String penqi_num) {
		this.penqi_num = penqi_num;
	}
}
