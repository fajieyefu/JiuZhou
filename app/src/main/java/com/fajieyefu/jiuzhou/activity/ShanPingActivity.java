package com.fajieyefu.jiuzhou.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.My_MD5;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.util.UpdateApk;

/**
 * Created by Fajieyefu on 2016/7/11.
 */


public class ShanPingActivity extends BaseActivity {
    private String info ;
    private String account = null;
    private String password = null;
    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags()&Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)!=0){
            finish();
            return;
        }
        if (!isTaskRoot()) {
            finish();
            return;
        }
        setContentView(R.layout.shanping);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        account = pref.getString("account", "");
        password = pref.getString("pass", "");
        checkApkVersion();
    }

    private void checkApkVersion() {
        new AsyncTask<Void,Void,Boolean>(){
            Boolean vsIsNew;
            @Override
            protected Boolean doInBackground(Void... voids) {
                vsIsNew = UpdateApk.isNewVersion(ShanPingActivity.this);
                return vsIsNew;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean){
                    new Thread(new myThread()).start();
                }else{
                    Dialog dialog =new UpdateApk(ShanPingActivity.this).updateDialog(ShanPingActivity.this);
                    dialog.show();
                }
            }
        }.execute();
    }


    private class myThread implements Runnable {
        @Override
        public void run() {
            if(!TextUtils.isEmpty(account))
                info = WebService.login(account, My_MD5.get_MD5Code(password));
            if (info!=null&&!info.equals("fail")) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(ShanPingActivity.this, MainActivity.class);
                        ShanPingActivity.this.finish();
                        startActivity(i);
                    }
                });

            }else{
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(ShanPingActivity.this,
                                LoginActivity.class); // 从启动动画ui跳转到主ui
                        startActivity(intent);
                        ShanPingActivity.this.finish(); // 结束启动动画界面
                    }
                }, 3000); // 启动动画持续3秒钟
            }

        }
    }
}