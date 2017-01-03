package com.fajieyefu.jiuzhou.util;

import android.util.Log;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.ResponseBean;
import com.fajieyefu.jiuzhou.application.MyApplication;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by qiancheng on 2016/12/2.
 */
public abstract class RespnseCallBack extends Callback<ResponseBean> {
	@Override
	public ResponseBean parseNetworkResponse(Response response, int id) throws Exception {
		String string = response.body().string();
		Log.i("返回数据",string);
		ResponseBean responseBean = new Gson().fromJson(string, ResponseBean.class);
		return responseBean;
	}


}
