package com.fajieyefu.jiuzhou.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.ActivityCollector;
import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.CommonData;
import com.fajieyefu.jiuzhou.Bean.My_MD5;
import com.fajieyefu.jiuzhou.Bean.ResponseBean;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.util.RespnseCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by qiancheng on 2016/12/14.
 */
public class ModifyPswActivity extends BaseActivity {
	@BindView(R.id.back_ib)
	ImageButton backIb;
	@BindView(R.id.title_middle_text)
	TextView titleMiddleText;
	@BindView(R.id.original_password)
	EditText originalPassword;
	@BindView(R.id.new_password)
	EditText newPassword;
	@BindView(R.id.confirm_password)
	EditText confirmPassword;
	@BindView(R.id.confirm)
	Button confirm;
	private String account;
	private String originalPsw;
	private String newPsw;
	private String confirmPsw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_password);
		ButterKnife.bind(this);
		initView();
	}
	private void initView() {
		titleMiddleText.setText("修改密码");
		SharedPreferences pref = ModifyPswActivity.this.getSharedPreferences("userInfo", MODE_PRIVATE);
		account = pref.getString("account", "");
	}

	@OnClick({R.id.back_ib, R.id.confirm})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.back_ib:
				finish();
				break;
			case R.id.confirm:
				modifyPsw();
				break;
		}
	}
	private void modifyPsw() {
		originalPsw = originalPassword.getText().toString();
		newPsw = newPassword.getText().toString();
		confirmPsw = confirmPassword.getText().toString();
		if (TextUtils.isEmpty(originalPsw) || TextUtils.isEmpty(newPsw) || TextUtils.isEmpty(confirmPsw)) {
			Toast.makeText(ModifyPswActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!confirmPsw.equals(newPsw)) {
			Toast.makeText(ModifyPswActivity.this, "两次新密码输入不相同", Toast.LENGTH_SHORT).show();
			return;
		}
		OkHttpUtils.post()
				.addParams("account", account)
				.addParams("original_psw", My_MD5.get_MD5Code(originalPsw))
				.addParams("new_psw", My_MD5.get_MD5Code(newPsw))
				.url(CommonData.MODIFY_PSW)
				.build()
				.execute(new MyResponseCall());

	}
	private class MyResponseCall extends RespnseCallBack {

		@Override
		public void onError(Call call, Exception e, int id) {

		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode() == 0) {
				Toast.makeText(ModifyPswActivity.this, "密码修改成功，请重新登录", Toast.LENGTH_SHORT).show();
				ActivityCollector.finishAll();
				Intent intent = new Intent(ModifyPswActivity.this, LoginActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(ModifyPswActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
