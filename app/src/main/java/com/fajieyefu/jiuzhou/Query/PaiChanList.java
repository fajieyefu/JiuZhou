package com.fajieyefu.jiuzhou.Query;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.ApplyInfo;
import com.fajieyefu.jiuzhou.Bean.ApplyInfoAdapter2;
import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.TitleLayout;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by qiancheng on 2016/11/3.
 */
public class PaiChanList extends BaseActivity {
	private ListView listView;
	private ArrayList<ApplyInfo> infoArrayList = new ArrayList<ApplyInfo>();
	private ApplyInfoAdapter2 adapter;
	private SharedPreferences pref;
	private String account, departcode, type;
	private int count = 1;
	private String intentFlag;
	private Button leadMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.query_list);
		pref = this.getSharedPreferences("userInfo", MODE_PRIVATE);
		account = pref.getString("account", "");
		departcode = pref.getString("depart_code", "");
		type = "全部";
		TitleLayout titleLayout = (TitleLayout) findViewById(R.id.title);
		titleLayout.setTitleText("待确认排产");
		listView = (ListView) findViewById(R.id.list_view_query);
		adapter = new ApplyInfoAdapter2(this, R.layout.news_item_2, infoArrayList);
		View bottomView = getLayoutInflater().inflate(R.layout.bottom, null);
		leadMore = (Button) bottomView.findViewById(R.id.leadMore);
		listView.addFooterView(bottomView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ApplyInfo applyInfo = infoArrayList.get(position);
				if (applyInfo.getBill_code().equals("D")) {
					Toast.makeText(PaiChanList.this, "请等待二次确认", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(PaiChanList.this, PaichanQuery.class);
				intent.putExtra("loan_code", applyInfo.getLoan_code());
				intent.putExtra("flag", intentFlag);
				intent.putExtra("bill_code", applyInfo.getBill_code());
				intent.putExtra("more", "1");
				intent.putExtra("paichan_flag","1");
				startActivity(intent);
			}
		});
		initData();
		leadMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				initData();
			}
		});


	}

	private void initData() {
		new MyAsyncTask().execute();
	}


	@Override
	protected void onRestart() {
		super.onRestart();
		infoArrayList.clear();
		count = 1;
		new MyAsyncTask().execute();
	}

	class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			String info = WebService.getPaiChanList(account, count);
			System.out.println(info);
			JSONArray jsonArray;
			JSONObject jsonObject;
			if (!TextUtils.isEmpty(info) && !info.equals("fail")) {
				try {
					jsonArray = new JSONArray(info);
					int len = jsonArray.length();
					if (len == 0) {
						return false;
					}
					for (int i = 0; i < len; i++) {
						jsonObject = jsonArray.getJSONObject(i);
						String loan_date = jsonObject.optString("loan_date");
						String title = jsonObject.optString("loan_name");
						String loan_code = jsonObject.optString("loan_code");
						String bill_flag = jsonObject.optString("bill_flag");
						String cusm_name = jsonObject.optString("loan_name");
						ApplyInfo applyInfo = new ApplyInfo();
						applyInfo.setTitle(title);
						applyInfo.setLoan_date(loan_date);
						applyInfo.setLoan_code(loan_code);
						applyInfo.setCusm_name(cusm_name);
						applyInfo.setBill_code(bill_flag);
						switch (bill_flag) {
							case "B":
								bill_flag = "等待一次评审";
								intentFlag = "B";
								applyInfo.setBill_flag(bill_flag);
								infoArrayList.add(applyInfo);
								break;

							case "C":
								bill_flag = "销售审核退回";
								intentFlag = "C";
								applyInfo.setBill_flag(bill_flag);
								infoArrayList.add(applyInfo);
								break;
							case "D":
								bill_flag = "等待二次评审";
								intentFlag = "D";
								applyInfo.setBill_flag(bill_flag);
								infoArrayList.add(applyInfo);
								break;
							case "E":
								bill_flag = "生产审核退回";
								intentFlag = "E";
								applyInfo.setBill_flag(bill_flag);
								infoArrayList.add(applyInfo);
								break;
							case "F":
								bill_flag = "等待业务员确认";
								intentFlag = "F";
								applyInfo.setBill_flag(bill_flag);
								infoArrayList.add(applyInfo);
								break;
							case "A":
								bill_flag = "待提交";
								intentFlag = "A";
								applyInfo.setBill_flag(bill_flag);
								infoArrayList.add(applyInfo);
								break;
							case "Y":
								bill_flag = "待确认排产";
								intentFlag = "Y";
								applyInfo.setBill_flag(bill_flag);
								infoArrayList.add(applyInfo);
								break;
						}

					}

				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}

				count++;

			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean aBoolean) {
			if (aBoolean) {
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(PaiChanList.this, "没有可用数据", Toast.LENGTH_SHORT).show();
			}

		}
	}
}

