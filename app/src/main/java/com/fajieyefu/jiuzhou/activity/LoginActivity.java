package com.fajieyefu.jiuzhou.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.My_MD5;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.Bean.WebService;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText userName, passWord;
    private Button sumbitButton;
    private CheckBox checkBox;
    private long exitTime;

    private ProgressDialog dialog;
    private String info;
    private SharedPreferences pref,pref2;
    private SharedPreferences.Editor editor,editor2;


    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        pref2= LoginActivity.this.getSharedPreferences("userInfo",MODE_PRIVATE);
        userName = (EditText) findViewById(R.id.account);
        passWord = (EditText) findViewById(R.id.password);
        sumbitButton = (Button) findViewById(R.id.submit);
        checkBox=(CheckBox)findViewById(R.id.remember_pass);
        sumbitButton.setOnClickListener(this);//设置登录按钮监听
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember){
            String account = pref.getString("account","");
            String pass=pref.getString("pass","");
            userName.setText(account);
            passWord.setText(pass);
            checkBox.setChecked(true);

        }


    }

    /*
    后续需要修改
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //  TODO 按两次返回键退出应用程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 判断间隔时间 大于2秒就退出应用
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                // 应用名
                String applicationName = getResources().getString(
                        R.string.app_name);
                String msg = "再按一次返回键退出" + applicationName;
                //String msg1 = "再按一次返回键回到桌面";
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                // 计算两次返回键按下的时间差
                exitTime =  System.currentTimeMillis();
            } else {
                // 关闭应用程序
                //finish();
                // 返回桌面操作
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.submit:

                if(userName.getText().toString().equals("")||passWord.getText().toString().equals("")){
                    Toast toast = Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                }
                //检测网络连接
                if (!checkNetwork()) {

                    Toast toast = Toast.makeText(LoginActivity.this, "网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                }
                //提示框
                dialog = new ProgressDialog(this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                //创建子线程，进行网络数据的传输
                new Thread(new myThread()).start();
                break;



        }
    }

    //检测网络
    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo()!= null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    //子线程接收数据，主线程修改数据
    private class myThread implements Runnable {
        Boolean isSuccess = false;
        public void run() {
            info = WebService.login(userName.getText().toString(),My_MD5.get_MD5Code(passWord.getText().toString()));
            System.out.print(info);
            if (!info.equals("fail")){
                isSuccess=true;
            }

            handler.post(new Runnable() {
                @Override
                public void run() {

                    if(isSuccess){
                        String user_man_name="",depart_code="",depart_name="";
                        try {
                            JSONObject jsonObject= new JSONObject(info);
                            user_man_name=jsonObject.getString("user_man_name");
                            depart_code=jsonObject.getString("depart_code");
                            depart_name=jsonObject.getString("depart_name");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String account =userName.getText().toString();
                        String pass=passWord.getText().toString();
                        editor=pref.edit();
                        editor2=pref2.edit();
                        editor2.putString("account",account);
                        editor2.putString("username",user_man_name);
                        editor2.putString("depart_code",depart_code);
                        editor2.putString("depart_name",depart_name);
                        editor2.commit();

                        if (checkBox.isChecked()){//檢查复选框是否选中
                            editor.putBoolean("remember_password",true);
                            editor.putString("account",account);
                            editor.putString("pass",pass);
                        }else{
                            editor.clear();
                        }
                        editor.commit();

                        Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast toast = Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                    if(dialog!=null)
                        dialog.dismiss();
                }
            });


        }
    }
}