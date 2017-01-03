package com.fajieyefu.jiuzhou.Audit;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.ContractBrowseBean;
import com.fajieyefu.jiuzhou.Bean.HandleInfoUtil;
import com.fajieyefu.jiuzhou.Bean.ModelAdapter;
import com.fajieyefu.jiuzhou.Bean.ModelInfo;
import com.fajieyefu.jiuzhou.Bean.ProcessAdapter;
import com.fajieyefu.jiuzhou.Bean.ProcessInfo;
import com.fajieyefu.jiuzhou.Bean.TitleLayout;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by qiancheng on 2016/10/26.
 */
public class PaichanInputActivity extends Activity {
	private List<ProcessInfo> list = new ArrayList<>();
	private TitleLayout titleLayout;
	private ListView listView;
	private ProcessAdapter adapter;
	public static Map<String, String> map_model = new HashMap<>();
	public static Map<String, String> map_class = new HashMap<>();
	public static Map<String, String> map_process = new HashMap<>();
	public static Map<String, String> process_class = new HashMap<>();
	public static Map<String, String> map_basic = new HashMap<>();
	public Map<String, String> submit_info = new HashMap<>();
	private Button basic_info;
	private int basic_visiable_flag = 0;
	private LinearLayout mainLayout;
	private Button submit;
	private JSONObject jsonObject_info;
	private Button basic_product_details;
	private View footerView;
	private EditText buyer;//需方
	private EditText sign_date;//签订日期
	private EditText sign_address;//签订地址
	private EditText tractor;//牵引头
	private RadioGroup power_type;//动力类型
	private EditText traction_pin;//牵引销
	private EditText traction_seat;//牵引座
	private EditText heightfromground;//离地高度
	private EditText model;//型号
	private EditText tonnage;//吨位
	private EditText plate_num;//钢板数量
	private EditText axis_number;//轴数
	private EditText color;//颜色
	private EditText steel;//轮胎
	private EditText sum;//数量
	private EditText price;//单价
	private EditText total_price;//总金额
	private EditText shoufu;//首付金额
	private EditText extra_days;//生效时间
	private EditText delivery_model;//提货方式
	private EditText wai_chang, wai_kuan, wai_gao, nei_chang, nei_kuan, nei_gao, cexiangban, zhouju;
	private EditText anthor_quest;
	private SharedPreferences pref;
	private String account;
	private String user_man_name;
	private int mYear, mMonth, mDay;
	private Calendar c;
	private ArrayList<String> dialog_list = new ArrayList<>();
	private ArrayList<ModelInfo> basic_model_list = new ArrayList<>();

	private String sign_date_string;
	private String contract_no = "";
	private ImageButton more;
	private String basic_product_code;
	private String basic_item_name;
	private TextView basic_model_name;
	private EditText anthorQuest2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contract_input_layout);
		ContractInputActivity2.map.clear();
		map_basic.clear();
		map_process.clear();
		map_model.clear();
		map_class.clear();
		Intent intent = getIntent();
		System.out.println("intent:" + intent);
		if (intent != null) {
			List<ContractBrowseBean> list = (List<ContractBrowseBean>) intent.getSerializableExtra("info");
			basic_product_code = intent.getStringExtra("basic_product_code");
			basic_item_name = intent.getStringExtra("basic_item_name");
			System.out.println("list:" + list.size());

			if (list != null) {
				for (ContractBrowseBean bean : list) {
					String class_code = bean.getClass_code();
					String model_code = bean.getModel_code();
					if (TextUtils.isEmpty(class_code)) {
						String text_name = bean.getModel_code();
						String text_content = bean.getTextContent();
						ContractInputActivity2.map.put(text_name, text_content);
					} else {
						ContractInputActivity2.map.put(class_code, model_code);
					}

				}
				contract_no = intent.getStringExtra("contract_no");
			} else {
				ContractInputActivity2.map.clear();
			}

		} else {
			ContractInputActivity2.map.clear();
		}


		findBI();
		initData();

	}

	private void initData() {
		map_model.clear();
		new AsyncTask<Void, Void, Boolean>() {
			ProgressDialog dialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				dialog = new ProgressDialog(PaichanInputActivity.this);
				dialog.setMessage("正在加载数据...");
				dialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				String info = WebService.getContractCheckInfo();
				HandleInfoUtil infoUtil = new HandleInfoUtil(info);
				JSONArray jsonArray_basic_model = infoUtil.getBasicModelJsonArray();
				int len_basic_model = jsonArray_basic_model.length();
				int num = 0;
				while (num < len_basic_model) {
					try {
						JSONObject jsonObject = jsonArray_basic_model.getJSONObject(num);
						String product_code = jsonObject.getString("product_code");
						String item_name = jsonObject.getString("item_name");
						ModelInfo basicModel = new ModelInfo(product_code, item_name);
						basic_model_list.add(basicModel);
						map_basic.put(product_code, item_name);

					} catch (JSONException e) {
						e.printStackTrace();
					}
					num++;
				}

				list.addAll(infoUtil.getProcessInfoList());
				if (list == null) {
					return false;
				}
				return true;
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				if (dialog != null) {
					dialog.dismiss();
				}
				if (aBoolean) {
					footerView.setVisibility(View.VISIBLE);
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(PaichanInputActivity.this, "加载数据失败", Toast.LENGTH_SHORT).show();
				}

			}
		}.execute();
	}

	private void findBI() {

		LinearLayout basicLayout = (LinearLayout) findViewById(R.id.basic_layout);
		basicLayout.setVisibility(View.GONE);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long times = System.currentTimeMillis();
		final String date = simpleDateFormat.format(times);
		c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		pref = PaichanInputActivity.this.getSharedPreferences("userInfo", MODE_PRIVATE);
		account = pref.getString("account", "");
		user_man_name = pref.getString("username", "");
		basic_model_name = (TextView) findViewById(R.id.basic_model_name);
		if (!TextUtils.isEmpty(basic_item_name)) {
			basic_model_name.setText(basic_item_name);
		}
		basic_product_details = (Button) findViewById(R.id.basic_product_details);
		basic_product_details.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(PaichanInputActivity.this);
				View view = LayoutInflater.from(PaichanInputActivity.this).inflate(R.layout.model_dialog, null);
				ListView lv = (ListView) view.findViewById(R.id.model_lv);
				ModelAdapter modelAdapter = new ModelAdapter(PaichanInputActivity.this, basic_model_list);
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						for (int i = 0; i < basic_model_list.size(); i++) {
							basic_model_list.get(i).setChoose_flag(0);
						}
						ModelInfo modelInfo = basic_model_list.get(position);
						modelInfo.setChoose_flag(1);
						basic_product_code = modelInfo.getModel_code();
						basic_item_name = modelInfo.getModel_name();

						if (dialog != null) {
							dialog.dismiss();
						}
						basic_model_name.setText(basic_item_name);
					}
				});
				lv.setAdapter(modelAdapter);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				dialog.show();
			}
		});
		wai_chang = (EditText) findViewById(R.id.wai_chang);
		wai_chang.setText(ContractInputActivity2.map.get("wai_chang"));

		wai_kuan = (EditText) findViewById(R.id.wai_kuan);
		wai_kuan.setText(ContractInputActivity2.map.get("wai_kuan"));

		wai_gao = (EditText) findViewById(R.id.wai_gao);
		wai_gao.setText(ContractInputActivity2.map.get("wai_gao"));

		nei_chang = (EditText) findViewById(R.id.nei_chang);
		nei_chang.setText(ContractInputActivity2.map.get("nei_chang"));

		nei_kuan = (EditText) findViewById(R.id.nei_kuan);
		nei_kuan.setText(ContractInputActivity2.map.get("nei_kuan"));

		nei_gao = (EditText) findViewById(R.id.nei_gao);
		nei_gao.setText(ContractInputActivity2.map.get("nei_gao"));

		cexiangban = (EditText) findViewById(R.id.cexiangban);
		cexiangban.setText(ContractInputActivity2.map.get("cexiangban"));

		zhouju = (EditText) findViewById(R.id.cexiangban);
		zhouju.setText(ContractInputActivity2.map.get("zhouju"));

		sign_date = (EditText) findViewById(R.id.sign_date);
		String date_string = ContractInputActivity2.map.get("sign_date");
		if (!TextUtils.isEmpty(date_string)) {
			sign_date.setText(date);
		} else {
			sign_date.setText(date_string);
		}

		sign_date.setInputType(InputType.TYPE_NULL);
		sign_date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Dialog dialog = new DatePickerDialog(PaichanInputActivity.this, mDateSetListener, mYear, mMonth, mDay);
				dialog.show();
			}
		});
		sign_address = (EditText) findViewById(R.id.sign_address);
		sign_address.setText(ContractInputActivity2.map.get("sign_address"));

		tractor = (EditText) findViewById(R.id.tractor);
		tractor.setText(ContractInputActivity2.map.get("tractor"));

		power_type = (RadioGroup) findViewById(R.id.power_type);

//		switch (ContractInputActivity2.map.get("power_type")){
//			case "燃油":
//				power_type.setId(R.id.fuel);
//				break;
//			case "燃气":
//				power_type.setId(R.id.gas);
//				break;
//		}

		traction_pin = (EditText) findViewById(R.id.traction_pin);
		traction_pin.setText(ContractInputActivity2.map.get("traction_pin"));

		traction_seat = (EditText) findViewById(R.id.traction_seat);
		traction_seat.setText(ContractInputActivity2.map.get("traction_seat"));

		heightfromground = (EditText) findViewById(R.id.heightfromground);
		heightfromground.setText(ContractInputActivity2.map.get("heightfromground"));

		model = (EditText) findViewById(R.id.model);
		model.setText(ContractInputActivity2.map.get("model"));

		tonnage = (EditText) findViewById(R.id.tonnage);//总重
		tonnage.setText(ContractInputActivity2.map.get("tonnage"));

		plate_num = (EditText) findViewById(R.id.plate_num);
		plate_num.setText(ContractInputActivity2.map.get("plate_num"));

		axis_number = (EditText) findViewById(R.id.axis_number);
		axis_number.setText(ContractInputActivity2.map.get("axis_number"));

		color = (EditText) findViewById(R.id.color);
		color.setText(ContractInputActivity2.map.get("color"));

		steel = (EditText) findViewById(R.id.steel);
		steel.setText(ContractInputActivity2.map.get("steel"));

		sum = (EditText) findViewById(R.id.num);
		sum.setText(ContractInputActivity2.map.get("sum"));

		price = (EditText) findViewById(R.id.price);
		price.setText(ContractInputActivity2.map.get("price"));

		total_price = (EditText) findViewById(R.id.total_amount);
		total_price.setText(ContractInputActivity2.map.get("total_price"));

		shoufu = (EditText) findViewById(R.id.shoufu);
		shoufu.setText(ContractInputActivity2.map.get("shoufu"));

		extra_days = (EditText) findViewById(R.id.extra_days);
		extra_days.setText(ContractInputActivity2.map.get("extra_days"));
		extra_days.setInputType(InputType.TYPE_NULL);
		extra_days.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog dialog = new DatePickerDialog(PaichanInputActivity.this, mDateSetListener2, mYear, mMonth, mDay);
				dialog.show();
			}
		});

		delivery_model = (EditText) findViewById(R.id.delivery_mode);
		delivery_model.setText(ContractInputActivity2.map.get("delivery_model"));

		buyer = (EditText) findViewById(R.id.buyer);
		buyer.setText(ContractInputActivity2.map.get("buyer_name"));

		basic_info = (Button) findViewById(R.id.basic_info);
		mainLayout = (LinearLayout) findViewById(R.id.main_content);
		titleLayout = (TitleLayout) findViewById(R.id.title);
		more = (ImageButton) findViewById(R.id.more);
		more.setVisibility(View.VISIBLE);
		more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_list.clear();
				final Dialog dialog = new Dialog(PaichanInputActivity.this);
				View dialog_view = LayoutInflater.from(PaichanInputActivity.this).inflate(R.layout.more_dialog, null);
				ListView lv = (ListView) dialog_view.findViewById(R.id.more_lv);
				dialog_list.add("提交");
//				dialog_list.add("保存");
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(PaichanInputActivity.this, android.R.layout.simple_list_item_1, dialog_list);
				lv.setAdapter(adapter);
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {
							case 0:
								submitInfoToServer("C");
								break;
//							case 1:
//								submitInfoToServer("A");
//								break;

						}
						if (dialog != null) {
							dialog.dismiss();
						}
					}
				});
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(dialog_view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				dialog.show();
			}
		});
		submit = (Button) titleLayout.findViewById(R.id.submit);
		anthor_quest = (EditText) findViewById(R.id.another_quest);
		anthor_quest.setText(ContractInputActivity2.map.get("remark"));
//		submit.setVisibility(View.VISIBLE);
		titleLayout.setTitleText("合同录入");
		listView = (ListView) findViewById(R.id.process_list);
		footerView = LayoutInflater.from(this).inflate(R.layout.another_quest_layout, null);
		footerView.setVisibility(View.INVISIBLE);
		anthorQuest2 = (EditText) footerView.findViewById(R.id.another_quest2);
		anthorQuest2.setText(ContractInputActivity2.map.get("remark"));
		listView.addFooterView(footerView);
		adapter = new ProcessAdapter(this, list);
		listView.setAdapter(adapter);
	}


	private void submitInfoToServer(String audit_flag) {

		submit_info.clear();
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray;
		try {
			jsonObject.put("contract_no", contract_no);
			jsonObject.put("audit_flag", audit_flag);
			String wai_chang_string = wai_chang.getText().toString();
			String wai_kuan_string = wai_kuan.getText().toString();
			String wai_gao_string = wai_gao.getText().toString();
			String nei_chang_string = nei_chang.getText().toString();
			String nei_kuan_string = nei_kuan.getText().toString();
			String nei_gao_string = nei_gao.getText().toString();
			String cexiangban_string = cexiangban.getText().toString();
			String zhouju_string = zhouju.getText().toString();
			jsonObject.put("wai_chang", wai_chang_string);
			jsonObject.put("wai_kuan", wai_kuan_string);
			jsonObject.put("wai_gao", wai_gao_string);
			jsonObject.put("nei_chang", nei_chang_string);
			jsonObject.put("nei_kuan", nei_kuan_string);
			jsonObject.put("nei_gao", nei_gao_string);
			jsonObject.put("cexiangban", cexiangban_string);
			jsonObject.put("zhouju", zhouju_string);
			RadioButton radioButton = (RadioButton) PaichanInputActivity.this.findViewById(power_type.getCheckedRadioButtonId());
			String power_string = radioButton.getText().toString();
			jsonObject.put("power_type", power_string);
			String buyer_string = buyer.getText().toString();
			jsonObject.put("buyer_name", buyer_string);
			sign_date_string = sign_date.getText().toString();
			jsonObject.put("sign_date", sign_date_string);
			String sign_address_string = sign_address.getText().toString();
			jsonObject.put("sign_address", sign_address_string);
			String tractor_string = tractor.getText().toString();
			jsonObject.put("tractor", tractor_string);
			String traction_pin_string = traction_pin.getText().toString();
			jsonObject.put("traction_pin", traction_pin_string);
			String traction_seat_string = traction_seat.getText().toString();
			jsonObject.put("traction_seat", traction_seat_string);
			String heightfromground_string = heightfromground.getText().toString();
			jsonObject.put("heightfromground", heightfromground_string);
			String model_string = model.getText().toString();
			jsonObject.put("model", model_string);
			String tonnage_string = tonnage.getText().toString();
			jsonObject.put("tonnage", tonnage_string);
			String plate_num_string = plate_num.getText().toString();
			jsonObject.put("plate_num", plate_num_string);
			String axis_number_string = axis_number.getText().toString();
			jsonObject.put("axis_number", axis_number_string);
			String color_string = color.getText().toString();
			jsonObject.put("color", color_string);

			String steel_string = steel.getText().toString();
			jsonObject.put("steel", steel_string);
			String sum_string = sum.getText().toString();
			jsonObject.put("sum", sum_string);
			String price_string = price.getText().toString();
			jsonObject.put("price", price_string);
			String total_price_string = total_price.getText().toString();
			jsonObject.put("total_price", total_price_string);

			String shoufu_string = shoufu.getText().toString();
			jsonObject.put("shoufu", shoufu_string);
			String extra_days_string = extra_days.getText().toString();
			jsonObject.put("extra_days", extra_days_string);
			String delivery_model_string = delivery_model.getText().toString();
			jsonObject.put("delivery_model", delivery_model_string);
			String anthor_quest_string = anthor_quest.getText().toString();
			jsonObject.put("remark", anthor_quest_string);
			for (Map.Entry<String, String> entry : process_class.entrySet()) {
				String process_code = entry.getKey();
				String process_name = entry.getValue();
				System.out.println(process_code + ":" + process_name);
			}

			jsonArray = new JSONArray();
			for (Map.Entry<String, String> entry : ContractInputActivity2.map.entrySet()) {
				JSONObject jsonObject_model = new JSONObject();
				String class_code = entry.getKey();
				String model_code = entry.getValue();
				String model_name = ContractInputActivity2.map_model.get(model_code);
				String class_name = ContractInputActivity2.map_class.get(class_code);
				jsonObject_model.put("model_code", model_code);
				jsonObject_model.put("model_name", model_name);
				jsonObject_model.put("class_code", class_code);
				jsonObject_model.put("class_name", class_name);
				System.out.println(class_code);
				String process_code = ContractInputActivity2.process_class.get(class_code);
				String process_name = ContractInputActivity2.map_process.get(process_code);
				System.out.println(process_code);
				System.out.println(process_name);
				jsonObject_model.put("process_code", process_code);
				jsonObject_model.put("process_name", process_name);
				jsonArray.put(jsonObject_model);
				Log.i("jsonArray", jsonArray.toString());
			}
			JSONObject jsonObject_basic_model = new JSONObject();
			jsonObject_basic_model.put("product_code", basic_product_code);
			jsonObject_basic_model.put("item_name", basic_item_name);
			jsonObject_info = new JSONObject();
			jsonObject_info.put("basic_info", jsonObject);
			jsonObject_info.put("list_info", jsonArray);
			jsonObject_info.put("userman_name", user_man_name);
			jsonObject_info.put("model_type", jsonObject_basic_model);
			jsonObject_info.put("account", account);
			jsonObject_info.put("remark", anthorQuest2.getText().toString());
			System.out.println(jsonObject_info.toString());
			Log.i("提交的数据:", jsonObject_info.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (TextUtils.isEmpty(contract_no)) {
			new SubmitAsyncTask().execute();
		} else {
			new AfterChangeSubmit().execute();
		}
	}

	class SubmitAsyncTask extends AsyncTask<Void, Void, Boolean> {
		ProgressDialog progressDialog;
		String contract_no;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(PaichanInputActivity.this);
			progressDialog.setMessage("提交数据中...");
			progressDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			System.out.println("运行到这里了");
			String info = WebService.submitInfo(jsonObject_info.toString());
			System.out.println(info);
			try {
				JSONObject jsonObject = new JSONObject(info);
				int code = jsonObject.getInt("code");
				if (code == 0) {
					contract_no = jsonObject.getString("contract_no");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (info.equals("-1")) {
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean aBoolean) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			if (aBoolean) {
				final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(PaichanInputActivity.this);
				dialog.setTitle("合同信息录入成功！");
				dialog.setMessage("合同单号:" + contract_no + "\n申请人：" + account);
				dialog.setCancelable(false);
				dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						PaichanInputActivity.this.finish();

					}
				});
				dialog.show();
			} else {
				Toast.makeText(PaichanInputActivity.this, "录入合同出现异常，请稍后重试！", Toast.LENGTH_SHORT).show();
			}

		}
	}


	class AfterChangeSubmit extends AsyncTask<Void, Void, Boolean> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(PaichanInputActivity.this);
			progressDialog.setMessage("提交数据中...");
			progressDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			String info = WebService.submitPaichanInfo(jsonObject_info.toString());
			try {
				JSONObject jsonObject = new JSONObject(info);
				int code = jsonObject.getInt("code");
				if (code == 0) {
					return true;
				}
			} catch (JSONException e) {
//				e.printStackTrace();
				return false;
			}


			return true;
		}

		@Override
		protected void onPostExecute(Boolean aBoolean) {
			super.onPostExecute(aBoolean);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			if (aBoolean) {
				final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(PaichanInputActivity.this);
				dialog.setTitle("提示！");
				dialog.setMessage("合同单号:" + contract_no + "更改成功");
				dialog.setCancelable(false);
				dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						PaichanInputActivity.this.finish();

					}
				});
				dialog.show();

			} else {
				Toast.makeText(PaichanInputActivity.this, "合同修改出现异常！", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		ContractInputActivity2.map.clear();
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDateDisplay(sign_date);
		}
	};
	private DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDateDisplay(extra_days);
		}
	};

	private void updateDateDisplay(EditText editText) {
		sign_date_string = new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
				.append((mDay < 10) ? "0" + mDay : mDay).toString();
		editText.setText(sign_date_string);
	}
}
