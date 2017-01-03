package com.fajieyefu.jiuzhou.Bean;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fajieyefu on 2016/7/11.
 */
public class WebService {
	private static String IP = "139.196.191.217:9891";
	private static String JiuZhouPath = "http://" + IP + "/JiuZhouWeb/";
	private static String JIUZHOUPATH_ORIGINAL = "http://" + IP + "/JiuZhou_original/";
//	private static String Jiuzhou="http://" + IP + "/Jiuzhou/";
	/**
	 * 登录
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public static String login(String username, String password) {
		String servlet_path = JiuZhouPath + "LogLet?username=" + username + "&password=" + password;
		String info = getFromServer(servlet_path);
		return info;
	}

	/**
	 * 获取通知公告、消息
	 *
	 * @param pages
	 * @return
	 */
	public static String executeHttpGetNews(int pages) {
		String servlet_path = JiuZhouPath + "NewsServlet?pages=" + pages;
		String info = getFromServer(servlet_path);
		return info;
	}

	/**
	 * 获取操作权限
	 *
	 * @param account
	 * @return
	 */
	public static String executeHttpGetAuthority(String account) {
		String servlet_path = JiuZhouPath + "AuthorityServlet?account=" + account;
		String info = getFromServer(servlet_path);
		return info;
	}

	/**
	 * 获取审批权限
	 *
	 * @param account
	 * @return
	 */
	public static String executeHttpGetAuthority_shenpi(String account) {
		String servlet_path = JiuZhouPath + "ShenPiAuthorityServlet?account=" + account;
		String info = getFromServer(servlet_path);
		return info;

	}

	/**
	 * 获取用款类型
	 *
	 * @param account
	 * @return
	 */
	public static String getApplyFeeType(String account) {
		String servlet_path = JiuZhouPath + "ApplyFeeTypeServlet?account=" + account;
		String info = getFromServer(servlet_path);
		return info;

	}

	/**
	 * 提交报销申请单
	 *
	 * @param account
	 * @param departCode
	 * @param departname
	 * @param applyTypeString
	 * @param time
	 * @param amount_string
	 * @param titleString
	 * @param contentString
	 * @return
	 */
	public static String makeReimbursement(String account, String departCode, String departname, String applyTypeString, String time, String amount_string, String titleString, String contentString) {
		String applyTypeString_encode = null, titleString_encode = null, contentString_encode = null,
				time_encode = null, departname_encode = null;
		try {
			applyTypeString_encode = URLEncoder.encode(applyTypeString, "UTF-8");
			titleString_encode = URLEncoder.encode(titleString, "UTF-8");
			contentString_encode = URLEncoder.encode(contentString, "UTF-8");
			time_encode = URLEncoder.encode(time, "UTF-8");
			departname_encode = URLEncoder.encode(departname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String servlet_path = JiuZhouPath + "ReimbursementServlet";
		servlet_path = servlet_path + "?account=" + account + "&departCode=" + departCode + "&departname=" + departname_encode + "&applyFeeTypeString=" + applyTypeString_encode + "&time=" + time_encode +
				"&amount_string=" + amount_string + "&title=" + titleString_encode + "&content=" + contentString_encode;
		String info = getFromServer(servlet_path);
		return info;
	}

	/**
	 * 获取报销类型
	 *
	 * @param departcode
	 * @return
	 */
	public static String getReimbursement(String departcode) {
		String servlet_path = JiuZhouPath + "ReimbursementTypeServlet?departcode=" + departcode;
		String info = getFromServer(servlet_path);
		return info;

	}



	private static String parseInfo(InputStream in) throws IOException {
		byte[] data = read(in);

		return new String(data, "UTF-8");

	}

	//将输入流转化为byte数组
	private static byte[] read(InputStream in) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];

		int len = 0;

		while ((len = in.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		in.close();
		return outputStream.toByteArray();

	}


	public static String makeApplyFee(String account, String depart_name, String applyFeeTypeString, String time, String expect_time,
									  String amount_string, String titleString, String contentString,
									  String receiveMan_string, String bankCardCode_string, String receiveTypeString) {
		String applyFeeTypeString_encode = null, titleString_encode = null, contentString_encode = null, depart_name_encode = null,
				receiveMan_string_encode = null, receiveTypeString_encode = null, time_encode = null, expect_time_encode = null;
		try {
			applyFeeTypeString_encode = URLEncoder.encode(applyFeeTypeString, "UTF-8");
			titleString_encode = URLEncoder.encode(titleString, "UTF-8");
			contentString_encode = URLEncoder.encode(contentString, "UTF-8");
			receiveMan_string_encode = URLEncoder.encode(receiveMan_string, "UTF-8");
			receiveTypeString_encode = URLEncoder.encode(receiveTypeString, "UTF-8");
			time_encode = URLEncoder.encode(time, "UTF-8");
			expect_time_encode = URLEncoder.encode(expect_time, "UTF-8");
			depart_name_encode = URLEncoder.encode(depart_name, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			String path = "http://" + IP + "/JiuZhouWeb/ApplyFeeServlet";//URl路径
			path = path + "?account=" + account + "&departname=" + depart_name_encode + "&applyFeeTypeString=" + applyFeeTypeString_encode + "&time=" + time_encode + "&expect_time=" +
					expect_time_encode + "&amount_string=" + amount_string + "&title=" + titleString_encode + "&content=" + contentString_encode
					+ "&receiveMan_string=" + receiveMan_string_encode + "&bankCardCode_string=" + bankCardCode_string + "&receiveTypeString=" + receiveTypeString_encode;
			conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(6000);//设置连接超时时间
			conn.setReadTimeout(6000);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");//设置获取信息方式
			conn.setRequestProperty("Charset", "UTF-8");//设置接收数据编码格式
			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				return parseInfo(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//意外退出时关闭连接
			if (conn != null) {
				conn.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}



	public static String getType(String departcode, String type) {
		HttpURLConnection conn = null;
		InputStream in = null;
		String type_encode = null;
		try {
			type_encode = URLEncoder.encode(type, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			String path = JiuZhouPath+"TypeServlet";//URl路径
			path = path + "?departcode=" + departcode + "&type=" + type_encode;
			conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(6000);//设置连接超时时间
			conn.setReadTimeout(6000);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");//设置获取信息方式
			conn.setRequestProperty("Charset", "UTF-8");//设置接收数据编码格式
			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				return parseInfo(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//意外退出时关闭连接
			if (conn != null) {
				conn.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	public static String makeTravelInfo(String account, String departname, String departcode, String time, String travelTypeString,
										String titleString, String contentString, String travel_date_string, String travelDayString) {
		String time_encode = null, travelTypeString_encode = null, title_encode = null, content_encode = null;
		String travel_date_encode = null, travelDay_encode = null, departname_encode = null;
		try {
			time_encode = URLEncoder.encode(time, "UTF-8");
			travelTypeString_encode = URLEncoder.encode(travelTypeString, "UTF-8");
			title_encode = URLEncoder.encode(titleString, "UTF-8");
			content_encode = URLEncoder.encode(contentString, "UTF-8");
			travel_date_encode = URLEncoder.encode(travel_date_string, "UTF-8");
			travelDay_encode = URLEncoder.encode(travelDayString, "UTF-8");
			departname_encode = URLEncoder.encode(departname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			String path = "http://" + IP + "/JiuZhouWeb/TravelServlet";//URl路径
			path = path + "?account=" + account + "&departname=" + departname_encode + "&departcode=" + departcode + "&time=" + time_encode +
					"&travelType=" + travelTypeString_encode + "&title=" + title_encode +
					"&content=" + content_encode + "&travel_date=" + travel_date_encode + "&travelDays=" + travelDay_encode;
			conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(6000);//设置连接超时时间
			conn.setReadTimeout(6000);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");//设置获取信息方式
			conn.setRequestProperty("Charset", "UTF-8");//设置接收数据编码格式
			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				return parseInfo(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			//意外退出时关闭连接
			if (conn != null) {
				conn.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	/**
	 * 获取申请信息的详情
	 *
	 * @param account
	 * @param type
	 * @return
	 */
	public static String getApplyInfo(String account, String type) {
		String type_encode = null;
		try {
			type_encode = URLEncoder.encode(type, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String servlet_path = JiuZhouPath + "ApplyInfoServlet?account=" + account + "&type=" + type_encode;
		String info = getFromServer(servlet_path);
		return info;

	}

	/**
	 * 获取用户查询的权限
	 *
	 * @param account
	 * @param departcode
	 * @return
	 */

	public static String getQueryAuthority(String account, String departcode) {
		String servlet_path = JiuZhouPath + "QueryAuthority?account=" + account + "&departcode=" + departcode;
		String info = getFromServer(servlet_path);
		return info;

	}

	/**
	 * 获取用户查询的详细信息
	 *
	 * @param account
	 * @param departcode
	 * @param type
	 * @param count
	 * @return
	 */
	public static String getQueryInfo(String account, String departcode, String type, int count) {
		String type_encode = "";
		try {
			type_encode = URLEncoder.encode(type, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String servlet_path = JiuZhouPath + "QueryInfoServlet?account=" + account + "&departcode=" + departcode
				+ "&count=" + count + "&type=" + type_encode;
		String info = getFromServer(servlet_path);
		return info;

	}


	public static String getQueryDetail(String loan_code) {
		String servlet_path = JiuZhouPath + "QueryDetailServlet?loan_code=" + loan_code;
		String info = getFromServer(servlet_path);
		return info;

	}

	/**
	 * 获取带审核的项目
	 *
	 * @param account
	 * @param departcode
	 * @param type
	 * @param count
	 * @return
	 */
	public static String getCheckInfo(String account, String departcode, String type, int count) {
		String type_encode = "";
		try {
			type_encode = URLEncoder.encode(type, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String servlet_path = JiuZhouPath + "CheckInfoServlet?account=" + account + "&departcode=" + departcode + "&count=" + count
				+ "&type=" + type_encode;
		String info = getFromServer(servlet_path);
		return info;
	}

	public static String getCheckDetailInfo(String loan_code, String audit_code) {
		String servlet_path = JiuZhouPath + "CheckDetailServlet?loan_code=" + loan_code + "&audit_code=" + audit_code;
		String info = getFromServer(servlet_path);
		return info;
	}

	public static String sendFlag(String loan_code, String audit_code, int i) {

		String servlet_path = JiuZhouPath + "AuditServlet?loan_code=" + loan_code + "&audit_code=" + audit_code + "&flag_num=" + i;
		String info = getFromServer(servlet_path);
		return info;

	}

	public static String getSpinnerInfo() {

		String servlet_path = JiuZhouPath + "SpinnerInfoServlet";
		String info = getFromServer(servlet_path);
		return info;

	}

	public static String makeContractInput(Map<String, String> params) {
		String servlet_path = JiuZhouPath + "ContractServlet";
		String info = postToServer(servlet_path, params);
		return info;

//		return "-1";
	}


	public static String getContractDetailInfo(String loan_code) {
		String servlet_path = JiuZhouPath + "ContractInfoServlet?loan_code=" + loan_code;
		String info = getFromServer(servlet_path);
		return info;

	}

	public static String getQueryContractInfo(String account, int count) {
		String servlet_path = JiuZhouPath + "ContractListServlet?account=" + account + "&count=" + count;
		String info = getFromServer(servlet_path);
		return info;

	}


	public static String getQualitySpinner() {
		String servlet_path = JIUZHOUPATH_ORIGINAL + "QualitySpinnerServlet";
		String info = getFromServer(servlet_path);
		return info;
	}

	public static String makeQualityInfo(Map<String, String> params) {
		String servlet_path = JIUZHOUPATH_ORIGINAL + "QualityServlet";
		String info = postToServer(servlet_path, params);
		return info;


	}

	public static String makeQualityDetailInfo(Map<String, String> params) {
		String servlet_path = JIUZHOUPATH_ORIGINAL + "QualityDetailServlet";
		String info = postToServer(servlet_path, params);
		return info;
//		byte[] data = getRequestData(params, "UTF-8").toString().getBytes();//获得请求体
	}

	public static String getQualityProcessInfo(Map<String, String> params) {
		String servlet_path = JIUZHOUPATH_ORIGINAL + "QualitProcessInfo";
		String info = postToServer(servlet_path, params);
		return info;
	}

	public static String makeWorkJournal(Map<String, String> params) {
		String servlet_path = JiuZhouPath + "WorkJournalServlet";
		String info = postToServer(servlet_path, params);
		return info;

	}

	public static String getQueryInfo(String account, int count) {

		String servlet_path = JiuZhouPath + "WorkJournalQueryServlet?account=" + account + "&count=" + count;
		String info = getFromServer(servlet_path);
		return info;


	}

	public static String getContractCheckInfo() {

		String servlet_path = JiuZhouPath + "ContractApply";
		String info = getFromServer(servlet_path);
		return info;

	}

	public static String submitInfo(String paramStr) {
		Map<String, String> map = new HashMap<>();
		map.put("data", paramStr);
		String servlet_path = JiuZhouPath + "ContractSubmitServlet";
		String info = postToServer(servlet_path, map);
		return info;

	}

	/**
	 * 以json格式来提交数据到服务器
	 *
	 * @param
	 * @return
	 */
	public static String getContractInfo(String loan_code) {
		String servlet_path = JiuZhouPath + "ContractBrowseServlet?loan_code=" + loan_code;
		String info = getFromServer(servlet_path);
		return info;


	}

	public static String changeFlag(String loan_code, String y, String confirm_text) {
		String servlet_path = JiuZhouPath + "ContractConfirm?loan_code=" + loan_code + "&flag=" + y + "&confirm_text=" + confirm_text;
		String info = getFromServer(servlet_path);
		return info;

	}


	public static String getQueryContractInfo2(String account, int count) {
		String servlet_path = JiuZhouPath + "ContractListServlet2?account=" + account + "&count=" + count;
		String info = getFromServer(servlet_path);
		return info;
	}

	public static String getPaiChanList(String account, int count) {
		String servlet_path = JiuZhouPath + "PaiChanServlet?account=" + account + "&count=" + count;
		String info = getFromServer(servlet_path);
		return info;

	}

	public static String getPaiChanInfo(String loan_code) {
		String servlet_path = JiuZhouPath + "PaichanInfoServlet?loan_code=" + loan_code;
		String info = getFromServer(servlet_path);
		return info;
	}

	public static String changePaiChanFlag(String loan_code, String flag) {
		String servlet_path = JiuZhouPath + "PaichanConfirm?loan_code=" + loan_code + "&flag=" + flag;
		String info = getFromServer(servlet_path);
		return info;
	}

	/**
	 * 获取APK信息
	 *
	 * @return
	 */
	public static String updateApk() {
		String path = JiuZhouPath + "ApkInfoServlet";
		String info = getFromServer(path);
		return info;

	}

	/**
	 * 以get方式提交到服务器
	 *
	 * @param servlet_path
	 * @return
	 */
	public static String getFromServer(String servlet_path) {
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			String path = servlet_path;//URl路径
			conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(6000);//设置连接超时时间
			conn.setReadTimeout(3000);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");//设置获取信息方式
			conn.setRequestProperty("Charset", "UTF-8");//设置接收数据编码格式

			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				return parseInfo(in);
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//意外退出时关闭连接
			if (conn != null) {
				conn.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "fail";
	}

	/**
	 * 以post的提交到服务器
	 *
	 * @param serv_path
	 * @param params
	 * @return
	 */
	public static String postToServer(String serv_path, Map<String, String> params) {
		byte[] data = getRequestData(params, "UTF-8").toString().getBytes();//获得请求体
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			String path = serv_path;//URl路径
			conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(6000);//设置连接超时时间
			conn.setReadTimeout(6000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");//设置获取信息方式
			conn.setUseCaches(false);//使用post方式不能使用缓存
			byte[] bytes = params.toString().getBytes();
			//设置请求体的类型是文本类型
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//设置请求体的长度
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));
			conn.setRequestProperty("Charset", "UTF-8");//设置接收数据编码格式
			//获得输出流，向服务器写入数据
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(data);
			int response = conn.getResponseCode();            //获得服务器的响应码
			if (response == HttpURLConnection.HTTP_OK) {        //HTTP_OK=200
				InputStream inputStream = conn.getInputStream();
				return parseInfo(inputStream);                  //处理服务器的响应结果
			}
		} catch (Exception e) {
			return "err: " + e.getMessage();
		}
		return "-1";

	}

	/**
	 * 封装post请求内容
	 *
	 * @param params
	 * @param encode
	 * @return
	 */
	public static StringBuffer getRequestData(Map<String, String> params, String encode) {
		StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey())
						.append("=")
						.append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}


	public static String submitPaichanInfo(String ParamStr) {
		String path = "http://" + IP + "/JiuZhouWeb/ContractPaichanSubmit";
		Log.v("zd", "send json");
		try {
			// 转成指定格式
			byte[] requestData = ParamStr.getBytes("UTF-8");
			HttpURLConnection conn = null;
			DataOutputStream outStream = null;
			String MULTIPART_FORM_DATA = "multipart/form-data";

			// 构造一个post请求的http头
			URL url = new URL(path); // 服务器地址
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); // 允许输入
			conn.setDoOutput(true); // 允许输出
			conn.setUseCaches(false); // 不使用caches
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA);
			conn.setRequestProperty("Content-Length", Long.toString(requestData.length));
			conn.setRequestProperty("data", ParamStr);
			// 请求参数内容, 获取输出到网络的连接流对象
			outStream = new DataOutputStream(conn.getOutputStream());
			outStream.write(requestData, 0, requestData.length);
			outStream.flush();
			outStream.close();
			ByteArrayOutputStream outStream2 = new ByteArrayOutputStream();

			int cah = conn.getResponseCode();
			if (cah != 200) {
				Log.v("data", "服务器响应错误代码:" + cah);
				return "-1";
			} else if (cah == 200) {
				Log.v("data", "服务器响应成功:" + cah);
			}
			InputStream inputStream = conn.getInputStream();
			int len = 0;
			byte[] data = new byte[1024];

			while ((len = inputStream.read(data)) != -1) {
				outStream2.write(data, 0, len);
			}
			outStream2.close();
			inputStream.close();
			String responseStr = new String(outStream2.toByteArray());
			Log.v("data", "data = " + responseStr);

			return responseStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
	}


}
