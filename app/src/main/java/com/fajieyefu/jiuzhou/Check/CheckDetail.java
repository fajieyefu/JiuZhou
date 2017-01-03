package com.fajieyefu.jiuzhou.Check;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.CommonData;
import com.fajieyefu.jiuzhou.Bean.FileAdapter;
import com.fajieyefu.jiuzhou.Bean.FileInfo;
import com.fajieyefu.jiuzhou.Bean.FilesInfo;
import com.fajieyefu.jiuzhou.Bean.LoanDetail;
import com.fajieyefu.jiuzhou.Bean.ResponseBean;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.activity.ImageBrowseActivity;
import com.fajieyefu.jiuzhou.util.RespnseCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Fajieyefu on 2016/8/2.
 */
public class CheckDetail extends BaseActivity implements View.OnClickListener {
	private static final String TAG = "CheckDetail";
	private TextView loan_code_Text, flower_type_Text, loan_use_date_Text, loan_sum_Text, loan_name_Text, department_Text;
	private TextView bill_flag_Text, loan_date_Text, title_Text, content_Text;
	private EditText suggest_Edit;
	private String loan_code, audit_code;
	private Handler handler = new Handler();
	private Button pass, noPass;
	private TextView titleText;
	private RecyclerView filesRv;
	private FileAdapter fileAdapter;
	private ArrayList<FileInfo> list = new ArrayList<>();
	private String[] files;
	private LinearLayout useDateLayout,filesLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.check_detail);
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("申请详情及审核");
		Intent intent = getIntent();
		loan_code = intent.getStringExtra("loan_code");
		audit_code = intent.getStringExtra("audit_code");
		suggest_Edit = (EditText) findViewById(R.id.suggest);
		loan_code_Text = (TextView) findViewById(R.id.loan_code);
		flower_type_Text = (TextView) findViewById(R.id.flower_type);
		loan_use_date_Text = (TextView) findViewById(R.id.loan_use_date);
		loan_sum_Text = (TextView) findViewById(R.id.loan_sum);
		loan_name_Text = (TextView) findViewById(R.id.loan_name);
		department_Text = (TextView) findViewById(R.id.department);
		bill_flag_Text = (TextView) findViewById(R.id.bill_flag);
		loan_date_Text = (TextView) findViewById(R.id.loan_date);
		title_Text = (TextView) findViewById(R.id.title);
		content_Text = (TextView) findViewById(R.id.content);
		filesRv = (RecyclerView) findViewById(R.id.files_rv);
		useDateLayout= (LinearLayout) findViewById(R.id.loan_use_date_layout);
		filesLayout= (LinearLayout) findViewById(R.id.files_ll);
		fileAdapter = new FileAdapter(list, this);
		pass = (Button) findViewById(R.id.pass);
		noPass = (Button) findViewById(R.id.nopass);

		initLoan();
		pass.setOnClickListener(this);
		noPass.setOnClickListener(this);
		initFiles();
	}

	private void initFiles() {
		// 创建一个线性布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		// 默认是Vertical，可以不写
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		filesRv.setLayoutManager(layoutManager);
		fileAdapter = new FileAdapter(list, this);
		filesRv.setAdapter(fileAdapter);
		fileAdapter.setClickListener(new FileAdapter.OnItemClickListener() {
			@Override
			public void onClick(View view, int position) {
				ImageBrowseActivity.startActivity(CheckDetail.this, list, position);

			}
		});


	}

	private void initLoan() {
		OkHttpUtils.get()
				.url(CommonData.CHECK_DETAILS)
				.addParams("loan_code", loan_code)
				.addParams("audit_code", audit_code)
				.build()
				.execute(new MyCallBack());


	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.pass:
				sendFlag(1);
				break;
			case R.id.nopass:
				if (suggest_Edit.getText().toString().isEmpty()) {
					Toast.makeText(CheckDetail.this, "请填写审批意见！", Toast.LENGTH_SHORT).show();
				} else {
					sendFlag(0);
				}
				break;

		}
	}

	private void sendFlag(final int i) {
		new AsyncTask<Void, Void, Boolean>() {
			ProgressDialog progressDialog;


			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(CheckDetail.this);
				progressDialog.setMessage("请稍等，正在处理数据...");
				progressDialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				String info = WebService.sendFlag(loan_code, audit_code, i);
				if (info.equals("success")) {
					return true;
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				super.onPostExecute(aBoolean);
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
				finish();
				if (aBoolean) {
					Toast.makeText(CheckDetail.this, "审核完成", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(CheckDetail.this, "审核出现异常", Toast.LENGTH_SHORT).show();
				}

			}
		}.execute();


	}

	private class MyCallBack extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {
			Toast.makeText(CheckDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode() == 0) {
				Log.i(TAG, response.toString());
				LoanDetail loanDetail = response.getLoanDetail();
				loan_code_Text.setText(loan_code);
				flower_type_Text.setText(loanDetail.getFlower_name());
				if (!TextUtils.isEmpty(loanDetail.getLoan_use_date())){
					loan_use_date_Text.setText(loanDetail.getLoan_use_date().split(" ")[0]);
				}else{
					useDateLayout.setVisibility(View.GONE);
				}
				loan_sum_Text.setText(loanDetail.getLoan_sum());
				loan_name_Text.setText(loanDetail.getLoan_man_name());
				department_Text.setText(loanDetail.getDepart_name());
				loan_date_Text.setText(loanDetail.getLoan_date().split(" ")[0]);
				title_Text.setText(loanDetail.getLoan_name());
				content_Text.setText(loanDetail.getLoan_desc());
				String bill_flag = "";
				switch (loanDetail.getBill_flag()){
					case "A":
						bill_flag="待审核";
						break;
				}
				bill_flag_Text.setText(bill_flag);
				if (!TextUtils.isEmpty(loanDetail.getFiles())) {
					files = loanDetail.getFiles().split(",");
					if (files.length > 0) {
						for (int i = 0; i < files.length; i++) {
							if (files.length > 0) {
								FileInfo fileInfo = new FileInfo();
								fileInfo.setFilePath(files[i]);
								list.add(fileInfo);
							}
						}
						fileAdapter.notifyDataSetChanged();
					}
				}else{
					filesLayout.setVisibility(View.GONE);
				}
			} else {
				Toast.makeText(CheckDetail.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
