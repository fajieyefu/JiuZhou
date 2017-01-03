package com.fajieyefu.jiuzhou.Bean;

import android.os.Environment;

/**
 * Created by qiancheng on 2016/12/14.
 */
public class CommonData {
	public static String JiuZhouPath = "http://139.196.191.217:9891/JiuZhouWeb/";
	public static String ORIGINAL_PATH = "http://139.196.191.217:9891/JiuZhou_original/";
	public final static String MODIFY_PSW = JiuZhouPath + "ModifyPsw";//修改密码
	public final static String QUALITYDETAILSERVLET = ORIGINAL_PATH + "QualityDetailServlet2";//检验结果录入
	public final static String COMPLETEBYMONTH = JiuZhouPath + "CompleteCarServlet";//整车综合月度统计
	public final static String APPLYFEE_SUBMIT = JiuZhouPath + "ApplyFeeServlet";//用款申请
	public final static String REIMBURSEMENT_SUBMIT = JiuZhouPath + "ReimbursementServlet";//报销申请
	public static final String PIC_TEMP = Environment.getExternalStorageDirectory().getPath() + "/Jiuzhou/pic_temp/";
	public static final String QUERYDEATILS = JiuZhouPath + "QueryDetailServlet";
	public static final String LOADFILES = JiuZhouPath + "LoadFileServlet";
	public static final String CHECK_DETAILS =JiuZhouPath+"CheckDetailServlet";//审批详情
	public static final String CHECK_INFO = JiuZhouPath+"CheckInfoServlet";//审批列表接口
}
