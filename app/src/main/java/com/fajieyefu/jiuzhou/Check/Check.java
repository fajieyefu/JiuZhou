package com.fajieyefu.jiuzhou.Check;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.ApplyInfo;
import com.fajieyefu.jiuzhou.Bean.ApplyInfoAdapter;
import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.CommonData;
import com.fajieyefu.jiuzhou.Bean.PopupAdapter;
import com.fajieyefu.jiuzhou.Bean.ResponseBean;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.Query.QueryDetail;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.util.RespnseCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Fajieyefu on 2016/8/2.
 */
public class Check extends BaseActivity {
	private PopupWindow popupWindow;
	private ListView lv_group;
	private ListView listView;
	private View view;
	private TextView titleText;
	private List<String> groups;
	private ImageView menuView;
	private String type;
	private int count = 1;
	private String account, departcode;
	private SharedPreferences pref;
	private ApplyInfoAdapter applyInfoAdapter;
	private ArrayList<ApplyInfo> infoArrayList = new ArrayList<ApplyInfo>();
	private Handler handler = new Handler();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.query_list);
		listView = (ListView) findViewById(R.id.list_view_query);
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("业务审批");
		type = "全部";

		applyInfoAdapter = new ApplyInfoAdapter(this, R.layout.news_item, infoArrayList);
		pref = Check.this.getSharedPreferences("userInfo", MODE_PRIVATE);
		account = pref.getString("account", "");
		departcode = pref.getString("depart_code", "");
		listView.setEmptyView(findViewById(R.id.empty_view));
		menuView = (ImageView) findViewById(R.id.group_menu);
		menuView.setVisibility(View.VISIBLE);
		menuView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showWindow(view);
			}
		});
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				ApplyInfo applyInfo = infoArrayList.get(i);//通过位置获取实例；
				Intent intent = new Intent(Check.this, CheckDetail.class);
				intent.putExtra("loan_code", applyInfo.getLoan_code());
				intent.putExtra("audit_code", applyInfo.getAudit_code());
				startActivity(intent);
			}
		});
		listView.setAdapter(applyInfoAdapter);
		initInfo();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		initInfo();
	}

	private void initInfo() {
//		OkHttpUtils.get()
//				.url(CommonData.CHECK_INFO)
//				.addParams("account", account)
//				.addParams("departcode", departcode)
//				.addParams("type", type)
//				.addParams("count", count + "")
//				.build()
//				.execute(new MyCallBack());


		new Thread(new Runnable() {
			@Override
			public void run() {
				String info = WebService.getCheckInfo(account, departcode, type, count);
				JSONArray jsonArray;
				JSONObject jsonObject;
				infoArrayList.clear();
				if (!TextUtils.isEmpty(info) && !info.equals("fail")) {
					try {
						jsonArray = new JSONArray(info);
						for (int i = 0; i < jsonArray.length(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							String loan_date = jsonObject.getString("loan_date");
							loan_date = loan_date.substring(0, loan_date.length() - 2);
							String title = jsonObject.getString("loan_name");
							String loan_code = jsonObject.getString("loan_code");
							String bill_flag = jsonObject.getString("bill_flag");
							String audit_code = jsonObject.getString("audit_code");
							ApplyInfo applyInfo = new ApplyInfo();
							applyInfo.setTitle(title);
							applyInfo.setLoan_date(loan_date);
							applyInfo.setLoan_code(loan_code);
							applyInfo.setBill_flag(bill_flag);
							applyInfo.setAudit_code(audit_code);
							infoArrayList.add(applyInfo);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

				handler.post(new Runnable() {
					@Override
					public void run() {
						applyInfoAdapter.notifyDataSetChanged();
					}
				});


			}
		}).start();
	}

	private void showWindow(View parent) {
		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.group_list, null);
			lv_group = (ListView) view.findViewById(R.id.lvGroup);
			groups = new ArrayList<String>();

			groups.add("全部");
			groups.add("合同");
			groups.add("用款");
			groups.add("出差");
			groups.add("报销");
			PopupAdapter adapter = new PopupAdapter(this, groups);
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Check.this,android.R.layout.simple_expandable_list_item_1,groups);
			lv_group.setAdapter(adapter);
			popupWindow = new PopupWindow(view, 120, 200);
		}
		//使其聚焦
		popupWindow.setFocusable(true);
		//设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		//设置点击返回键，同样会消失，不影响背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		//设置显示位置,
		int xoff = popupWindow.getWidth() / 2 - parent.getWidth() / 2;
		popupWindow.update();
		popupWindow.showAsDropDown(parent, -xoff, 0);
		lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Toast.makeText(Check.this, groups.get(i), Toast.LENGTH_SHORT).show();
				type = groups.get(i);
				count = 1;
				initInfo();
				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}

		});

	}

	private class MyCallBack extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {

		}

		@Override
		public void onResponse(ResponseBean response, int id) {

		}
	}
}
