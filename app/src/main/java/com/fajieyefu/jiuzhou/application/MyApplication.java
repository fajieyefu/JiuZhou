package com.fajieyefu.jiuzhou.application;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by qiancheng on 2016/12/2.
 */
public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//设置Cookie
		CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
		//设置可以访问所有https的网站
		HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
				.connectTimeout(10000L, TimeUnit.MILLISECONDS)
				.readTimeout(10000L, TimeUnit.MILLISECONDS)
				.cookieJar(cookieJar)
				.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
				//其他配置
				.build();

		OkHttpUtils.initClient(okHttpClient);
		//可以设置具体的证书
//		HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(证书的inputstream, null, null);
//		OkHttpClient okHttpClient = new OkHttpClient.Builder()
//				.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager))
//		//其他配置
//		.build();
//		OkHttpUtils.initClient(okHttpClient);

	}

	@Override
	public Context getBaseContext() {
		return super.getBaseContext();
	}
}
