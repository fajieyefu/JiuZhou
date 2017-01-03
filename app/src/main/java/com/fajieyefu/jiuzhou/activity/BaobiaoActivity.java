package com.fajieyefu.jiuzhou.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.Query.ApplyFeeQueryActivity;
import com.fajieyefu.jiuzhou.Query.CompleteByMonth;
import com.fajieyefu.jiuzhou.Query.ContractListActivity;
import com.fajieyefu.jiuzhou.Query.ReimbursementQueryActivity;
import com.fajieyefu.jiuzhou.Query.TravelQueryActivity;
import com.fajieyefu.jiuzhou.Query.WorkJournalQueryActivity;
import com.fajieyefu.jiuzhou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fajieyefu on 2016/7/11.
 */
public class BaobiaoActivity extends BaseActivity implements View.OnClickListener {
	private SharedPreferences pref;
	private String account, departcode;
	private TextView titleText;
	private LinearLayout contractQuery, testResultQuery, applyFeeQuery, travelQuery, reimbursementQuery,
			workJournalQuery, completeByMonth;
	private Handler handler = new Handler();
	private String temp1 = "", temp2 = "", temp3 = "", temp4 = "", temp5 = "", temp6 = "", temp7 = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.querytable);
		pref = BaobiaoActivity.this.getSharedPreferences("userInfo", MODE_PRIVATE);
		account = pref.getString("account", "");
		departcode = pref.getString("departcode", "");
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("综合查询");

		//实例化控件
		contractQuery = (LinearLayout) findViewById(R.id.contract_query);
		testResultQuery = (LinearLayout) findViewById(R.id.testReslut_query);
		applyFeeQuery = (LinearLayout) findViewById(R.id.applyFee_query);
		travelQuery = (LinearLayout) findViewById(R.id.travel_query);
		reimbursementQuery = (LinearLayout) findViewById(R.id.reimbursement_query);
		workJournalQuery = (LinearLayout) findViewById(R.id.workJournal_query);
		completeByMonth = (LinearLayout) findViewById(R.id.completeByMonth);
		init();
		contractQuery.setOnClickListener(this);
		testResultQuery.setOnClickListener(this);
		applyFeeQuery.setOnClickListener(this);
		travelQuery.setOnClickListener(this);
		reimbursementQuery.setOnClickListener(this);
		workJournalQuery.setOnClickListener(this);
		completeByMonth.setOnClickListener(this);
	}

	private void init() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String info = WebService.getQueryAuthority(account, departcode);
				JSONArray jsonArray;
				JSONObject jsonObject;

				if (!TextUtils.isEmpty(info) && !info.equals("fail")) {
					try {
						jsonArray = new JSONArray(info);
						jsonObject = jsonArray.getJSONObject(0);
						temp1 = jsonObject.optString("temp1");
						temp2 = jsonObject.optString("temp2");
						temp3 = jsonObject.optString("temp3");
						temp4 = jsonObject.optString("temp4");
						temp5 = jsonObject.optString("temp5");
						temp6 = jsonObject.optString("temp6");
						temp7 = jsonObject.optString("temp7");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						@Override
						public void run() {
							if (temp1.equals("success")) {
								contractQuery.setVisibility(View.VISIBLE);
							}
							if (temp2.equals("success")) {
								testResultQuery.setVisibility(View.VISIBLE);
							}
							if (temp3.equals("success")) {
								applyFeeQuery.setVisibility(View.VISIBLE);
							}
							if (temp4.equals("success")) {
								travelQuery.setVisibility(View.VISIBLE);
							}
							if (temp5.equals("success")) {
								reimbursementQuery.setVisibility(View.VISIBLE);
							}

							if (temp6.equals("success")) {
								workJournalQuery.setVisibility(View.VISIBLE);
							}
							if (temp7.equals("success")) {
								completeByMonth.setVisibility(View.VISIBLE);
							}
						}
					});

				}
			}
		}).start();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.contract_query:
				Intent intent_contract_query = new Intent(BaobiaoActivity.this, ContractListActivity.class);
				startActivity(intent_contract_query);
				break;
			case R.id.testReslut_query:
				Toast.makeText(BaobiaoActivity.this, "该模块正在开发中...", Toast.LENGTH_SHORT).show();
				// Intent intent_testReslut_query= new Intent(BaobiaoActivity.this,TestResultQueryActivity.class);
				//startActivity(intent_testReslut_query);
				break;
			case R.id.applyFee_query:
				Intent intent_applyFee_query = new Intent(BaobiaoActivity.this, ApplyFeeQueryActivity.class);
				startActivity(intent_applyFee_query);
				break;
			case R.id.travel_query:
				Intent intent_travel_query = new Intent(BaobiaoActivity.this, TravelQueryActivity.class);
				startActivity(intent_travel_query);
				break;
			case R.id.reimbursement_query:
				Intent intent_reimbursement_query = new Intent(BaobiaoActivity.this, ReimbursementQueryActivity.class);
				startActivity(intent_reimbursement_query);
				break;
			case R.id.workJournal_query:
//                Toast.makeText(BaobiaoActivity.this, "该模块正在开发中...", Toast.LENGTH_SHORT).show();
				Intent intent_workJournal_query = new Intent(BaobiaoActivity.this, WorkJournalQueryActivity.class);
				startActivity(intent_workJournal_query);
				break;
			case R.id.completeByMonth:
				Intent intent_completeByMonth = new Intent(BaobiaoActivity.this, CompleteByMonth.class);
				startActivity(intent_completeByMonth);
				break;
		}
	}
}
