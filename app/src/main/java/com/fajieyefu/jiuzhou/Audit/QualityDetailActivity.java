package com.fajieyefu.jiuzhou.Audit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.BeanUtil;
import com.fajieyefu.jiuzhou.Bean.CommonData;
import com.fajieyefu.jiuzhou.Bean.QualityInfo;
import com.fajieyefu.jiuzhou.Bean.QualityItemAdapter;
import com.fajieyefu.jiuzhou.Bean.ResponseBean;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.util.RespnseCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by Fajieyefu on 2016/8/10.
 */
public class QualityDetailActivity extends BaseActivity implements View.OnClickListener {
	private QualityInfo qualityInfo;
	private QualityItemAdapter qualityItemAdapter;
	private ListView listView;
	private TextView titleView;
	private ArrayList<QualityInfo> list;
	private String info, product_no, process_code;
	private Button submit;
	private Handler handler = new Handler();
	private BeanUtil beanUtil= new BeanUtil();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.quality_process_check);
		Intent intent = getIntent();
		product_no = intent.getStringExtra("product_no");
		process_code = intent.getStringExtra("process_code");
		System.out.println(product_no + ":" + process_code);
		titleView = (TextView) findViewById(R.id.title_text);
		titleView.setText("检验工位清单");
		listView = (ListView) findViewById(R.id.list_view);
		View bottomView = getLayoutInflater().inflate(R.layout.bottom_2, null);
		submit = (Button) bottomView.findViewById(R.id.submit);
		listView.addFooterView(bottomView);
		list = new ArrayList<QualityInfo>();
		qualityItemAdapter = new QualityItemAdapter(this, R.layout.quality_item, list);
		initList();
		listView.setAdapter(qualityItemAdapter);
		submit.setOnClickListener(this);


	}

	private void initList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Map<String, String> params = new HashMap<String, String>();
					params.put("product_no", product_no);
					params.put("process_code", process_code);
					info = WebService.getQualityProcessInfo(params);
					System.out.println(info);
					JSONArray jsonArray = new JSONArray(info);
					int len = jsonArray.length();
					int num = 0;
					while (num < len) {
						JSONObject jsonObject = jsonArray.getJSONObject(num);
						String product_code = jsonObject.getString("product_code");
						String item_desc = jsonObject.getString("item_desc");
						String item_ok = jsonObject.getString("item_ok");
						String item_a = jsonObject.getString("item_a");
						String item_b = jsonObject.getString("item_b");
						String paixu = jsonObject.getString("paixu");
						qualityInfo = new QualityInfo(product_code, item_desc, item_ok, item_a, item_b, paixu);
						list.add(qualityInfo);
						num++;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						qualityItemAdapter.notifyDataSetChanged();
					}
				});
			}
		}).start();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.submit:
				int len = list.size();
				int num = 0;
				JSONArray jsonArray = new JSONArray();
				while (num < len) {
					Map<String, String> params = new HashMap<String, String>();
					QualityInfo qualityInfo = (QualityInfo) listView.getItemAtPosition(num);
					String remark = qualityInfo.getEditString();
					String item_result = qualityInfo.getItem_result();
					String paixu = qualityInfo.getPaixu();
					if (remark == null) {
						remark = "";
					}
					params.put("product_no", product_no);
					params.put("process_code", process_code);
					params.put("paixu", paixu);
					params.put("remark", remark);
					params.put("item_result", item_result);
					JSONObject jsonObject = new JSONObject(params);
					jsonArray.put(jsonObject);
					num++;
				}
				beanUtil.showProgressDialog(this,"请稍等","正在提交数据");
				OkHttpUtils
						.postString()
						.url(CommonData.QUALITYDETAILSERVLET)
						.content(jsonArray.toString())
						.mediaType(MediaType.parse("application/json; charset=utf-8"))
						.build()
						.execute(new MyCallBack());
				break;


		}
	}

	private class MyCallBack extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {
			beanUtil.dismissProgressDialog();
			Toast.makeText(QualityDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			beanUtil.dismissProgressDialog();
			String msg ;
			AlertDialog.Builder dialog = new AlertDialog.Builder(QualityDetailActivity.this);
			dialog.setTitle("提示：");
			if (response.getCode() == 0) {
				msg = "检测结果录入成功";
			} else {
				msg = response.getMsg();
			}
			dialog.setMessage(msg);
			dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					finish();
				}
			});
			dialog.show();
		}
	}
}
