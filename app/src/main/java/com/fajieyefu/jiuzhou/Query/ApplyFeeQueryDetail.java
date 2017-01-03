package com.fajieyefu.jiuzhou.Query;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.CommonData;
import com.fajieyefu.jiuzhou.Bean.FileAdapter;
import com.fajieyefu.jiuzhou.Bean.FileInfo;
import com.fajieyefu.jiuzhou.Bean.LoanDetail;
import com.fajieyefu.jiuzhou.Bean.ResponseBean;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.activity.ImageBrowseActivity;
import com.fajieyefu.jiuzhou.util.RespnseCallBack;
import com.zhy.http.okhttp.OkHttpUtils;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Fajieyefu on 2016/7/27.
 */
public class ApplyFeeQueryDetail extends BaseActivity {
	private TextView loan_code, flower_type, loan_use_date, loan_sum,
			title, content, department, loan_name, loan_date, bill_flag;
	private String loan_code_s;
	private Handler handler = new Handler();
	private String flower_type_s, loan_use_date_s, loan_sum_s, title_s, content_s, department_s, loan_name_s, loan_date_s, bill_flag_s;
	private TextView titleText;
	private TextView files_tv;
	private RecyclerView files_rv;
	private String[] files;
	private List<FileInfo> list = new ArrayList<>();
	private FileAdapter fileAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reimbursement_query_detail);
		Intent intent = getIntent();
		loan_code_s = intent.getStringExtra("loan_code");
		//实例化控件
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("用款申请查询");
		loan_code = (TextView) findViewById(R.id.loan_code);
		flower_type = (TextView) findViewById(R.id.flower_type);
		loan_use_date = (TextView) findViewById(R.id.loan_use_date);
		loan_sum = (TextView) findViewById(R.id.loan_sum);
		title = (TextView) findViewById(R.id.title);
		content = (TextView) findViewById(R.id.content);
		department = (TextView) findViewById(R.id.department);
		loan_name = (TextView) findViewById(R.id.loan_name);
		loan_date = (TextView) findViewById(R.id.loan_date);
		bill_flag = (TextView) findViewById(R.id.bill_flag);
		files_tv = (TextView) findViewById(R.id.files_tv);
		files_rv = (RecyclerView) findViewById(R.id.recyclerview_horizontal);
		initInfo();
		initFiles();


	}

	private void initFiles() {
		// 创建一个线性布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		// 默认是Vertical，可以不写
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		files_rv.setLayoutManager(layoutManager);
		fileAdapter = new FileAdapter(list, this);
		files_rv.setAdapter(fileAdapter);
		fileAdapter.setClickListener(new FileAdapter.OnItemClickListener() {
			@Override
			public void onClick(View view, int position) {
				ImageBrowseActivity.startActivity(ApplyFeeQueryDetail.this, list, position);

			}
		});


	}

	private void initInfo() {
		OkHttpUtils.get()
				.url(CommonData.QUERYDEATILS)
				.addParams("loan_code", loan_code_s)
				.build()
				.execute(new MyResponse());
	}

	private class MyResponse extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {
			Toast.makeText(ApplyFeeQueryDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (response.getCode() == 0) {
				LoanDetail loanDetail = response.getLoanDetail();
				loan_code.setText(loanDetail.getLoan_code());
				flower_type.setText(loanDetail.getFlower_type());
				loan_use_date.setText(loanDetail.getLoan_use_date());
				loan_sum.setText(loanDetail.getLoan_sum());
				title.setText(loanDetail.getLoan_name());
				content.setText(loanDetail.getLoan_desc());
				department.setText(loanDetail.getDepart_name());
				loan_name.setText(loanDetail.getLoan_man_name());
				loan_date.setText(loanDetail.getLoan_date());
				bill_flag.setText(loanDetail.getBill_flag());
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
				}


			} else {
				Toast.makeText(ApplyFeeQueryDetail.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}

