package com.fajieyefu.jiuzhou.Query;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Audit.ContractInputActivity2;
import com.fajieyefu.jiuzhou.Audit.PaichanInputActivity;
import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.ContractBrowseAdapter;
import com.fajieyefu.jiuzhou.Bean.ContractBrowseBean;
import com.fajieyefu.jiuzhou.Bean.TitleLayout;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * Created by qiancheng on 2016/11/1.
 */
public class ContractQuery extends BaseActivity {
	private ListView listView;
	private List<ContractBrowseBean> list = new ArrayList<>();
	private ContractBrowseAdapter adapter;
	private String loan_code;
	private String intentFlag;
	private String bill_code;
	private Button confrim, modify;
	private ImageButton more;
	private ArrayList<String> dialog_list = new ArrayList<>();
	private String basic_product_code;
	private String basic_item_name;
	String extra_days;
	private int mYear, mMonth, mDay;
	private EditText applyDeliveryDate;
	private Calendar c;
	private String paichan_flag="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contract_browse_layout);
		TitleLayout titlelayout = (TitleLayout) findViewById(R.id.title_contract_browse);
		titlelayout.setTitleText("合同详情");
		confrim = (Button) titlelayout.findViewById(R.id.confrim);
		modify = (Button) titlelayout.findViewById(R.id.modify);
		more = (ImageButton) titlelayout.findViewById(R.id.more);
		c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		dialog_list.add("修改");
		dialog_list.add("删除");
		dialog_list.add("提交");
		dialog_list.add("确认");
		more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(ContractQuery.this);
				View dialog_view = LayoutInflater.from(ContractQuery.this).inflate(R.layout.more_dialog, null);
				ListView lv = (ListView) dialog_view.findViewById(R.id.more_lv);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(ContractQuery.this, android.R.layout.simple_list_item_1, dialog_list);
				lv.setAdapter(adapter);
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {
							case 0:
								if (bill_code.equals("C") || bill_code.equals("E") || bill_code.equals("A")) {
									Intent intent = new Intent(ContractQuery.this, ContractInputActivity2.class);
									intent.putExtra("info", (Serializable) list);
									intent.putExtra("contract_no", loan_code);
									intent.putExtra("basic_product_code", basic_product_code);
									intent.putExtra("basic_item_name", basic_item_name);
									ContractQuery.this.finish();
									startActivity(intent);
								} else {
									Toast.makeText(ContractQuery.this, "正在审核不能修改", Toast.LENGTH_SHORT).show();
								}
								break;
							case 1:
								Toast.makeText(ContractQuery.this, "整改中...", Toast.LENGTH_SHORT).show();
								break;
							case 2:
								if (bill_code.equals("A")) {
									changeContractFlag("B");
								} else {
									Toast.makeText(ContractQuery.this, "该合同已经提交过了", Toast.LENGTH_SHORT).show();
								}
								break;
							case 3:
								if (bill_code.equals("F")) {
									/**
									 *弹出含有是否插单的选择窗口
									 */
									final String[] temp_flag = {"Y"};
									final Dialog dialog1 = new Dialog(ContractQuery.this);
									dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
									View view1 = LayoutInflater.from(ContractQuery.this).inflate(R.layout.confirm_layout, null);
									TextView contractText = (TextView) view1.findViewById(R.id.contract_no);
									contractText.setText(loan_code);
									TextView extraDays = (TextView) view1.findViewById(R.id.extra_days);
									extraDays.setText(extra_days);
									applyDeliveryDate = (EditText) view1.findViewById(R.id.apply_delivery_date);
									applyDeliveryDate.setInputType(InputType.TYPE_NULL);
									applyDeliveryDate.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											Dialog dialog = new DatePickerDialog(ContractQuery.this, mDateSetListener2, mYear, mMonth, mDay);
											dialog.show();
										}
									});
									final Spinner spinner = (Spinner) view1.findViewById(R.id.chadanOrNot);
									ArrayList spinnerList = new ArrayList();
									spinnerList.add("确认订单");
									spinnerList.add("申请插单");
									ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ContractQuery.this, android.R.layout.simple_spinner_item, spinnerList);
									spinner.setAdapter(spinnerAdapter);
									spinner.setSelection(0);
									final EditText confirmIllustrate = (EditText) view1.findViewById(R.id.confirm_illustrate);
									final Button confirm = (Button) view1.findViewById(R.id.confirm_btn);
									Button cancel = (Button) view1.findViewById(R.id.cancel_btn);
									cancel.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											dialog1.dismiss();
										}
									});
									confirm.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											String confirm_text = confirmIllustrate.getText().toString();
											String applyDelivery_text = applyDeliveryDate.getText().toString();
											switch (spinner.getSelectedItemPosition()) {
												case 0:
													temp_flag[0] = "Y";
													break;
												case 1:
													temp_flag[0] = "G";
													break;

											}
											String flag = temp_flag[0];
											if (flag.equals("G") && applyDelivery_text.equals("")) {
												Toast.makeText(ContractQuery.this, "请设置申请交期", Toast.LENGTH_SHORT).show();
											} else {
												dialog1.dismiss();
												changeContractFlag(flag, confirm_text);

												ContractQuery.this.finish();
											}

										}
									});
									dialog1.setContentView(view1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
									dialog1.setCanceledOnTouchOutside(false);
									dialog1.show();


//
								} else {
									Toast.makeText(ContractQuery.this, "审核通过后才能确认", Toast.LENGTH_SHORT).show();
								}

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
		Intent intent = getIntent();
		loan_code = intent.getStringExtra("loan_code");
		bill_code = intent.getStringExtra("bill_code");
		String num = intent.getStringExtra("more");
		paichan_flag=intent.getStringExtra("paichan_flag");
		if (num != null && num.equals("1")) {
			more.setVisibility(View.VISIBLE);
		}

//		intentFlag=intent.getStringExtra("flag");
//		switch(intentFlag){
//			case "C":
//			case "E":
//				modify.setVisibility(View.VISIBLE);
//				break;
//			case "F":
//				confrim.setVisibility(View.VISIBLE);
//				break;
//
//		}
//		modify.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(ContractQuery.this, ContractInputActivity2.class);
//				intent.putExtra("info", (Serializable) list);
//				intent.putExtra("contract_no", loan_code);
//				startActivity(intent);
//
//			}
//		});
//		confrim.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				changeContractFlag("Y");
//			}
//		});
		listView = (ListView) findViewById(R.id.contract_lv);
		adapter = new ContractBrowseAdapter(this, list);
		listView.setAdapter(adapter);
		initData();
	}

	private void changeContractFlag(final String flag) {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				String info = WebService.changeFlag(loan_code, flag, "");
				System.out.println(info);
				try {
					JSONObject jsonObject = new JSONObject(info);
					int code = jsonObject.getInt("code");
					if (code == 0) {
						return true;
					}
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				super.onPostExecute(aBoolean);
				if (aBoolean) {
					Toast.makeText(ContractQuery.this, "合同订单处理成功！", Toast.LENGTH_SHORT).show();
					ContractQuery.this.finish();
				} else {
					Toast.makeText(ContractQuery.this, "处理数据出现异常", Toast.LENGTH_SHORT).show();
					ContractQuery.this.finish();
				}

			}
		}.execute();
	}

	private void changeContractFlag(final String flag, final String confirm_text) {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				String info = WebService.changeFlag(loan_code, flag, confirm_text);
				System.out.println(info);
				try {
					JSONObject jsonObject = new JSONObject(info);
					int code = jsonObject.getInt("code");
					if (code == 0) {
						return true;
					}
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				super.onPostExecute(aBoolean);
				if (aBoolean) {
					Toast.makeText(ContractQuery.this, "合同订单处理成功！", Toast.LENGTH_SHORT).show();
					ContractQuery.this.finish();
				} else {
					Toast.makeText(ContractQuery.this, "处理数据出现异常", Toast.LENGTH_SHORT).show();
					ContractQuery.this.finish();
				}

			}
		}.execute();
	}

	private void initData() {
		new MyAsyncTask().execute();
	}

	class MyAsyncTask extends AsyncTask<Void, Void, Integer> {
		String msg;

		@Override
		protected Integer doInBackground(Void... params) {

			String info = WebService.getContractInfo(loan_code);
			try {
				JSONObject jsonObject = new JSONObject(info);
				int code = jsonObject.getInt("code");
				if (code != 0) {
					msg = jsonObject.getString("msg");
					return 1;
				}
				JSONObject jsonObject_data = jsonObject.getJSONObject("data");
				JSONObject basicInfo = jsonObject_data.getJSONObject("basic_info");
				JSONArray listInfo = jsonObject_data.getJSONArray("list_info");
				Log.i("listInfo",listInfo.toString());
				String pingshen_txt = basicInfo.optString("pingshen_txt");
				ContractBrowseBean bean = new ContractBrowseBean("生产评审意见", pingshen_txt);
				list.add(bean);

				String audit_txt = basicInfo.optString("audit_txt");
				bean = new ContractBrowseBean("后勤评审意见", audit_txt);
				list.add(bean);

				String buyer_name = basicInfo.optString("buyer_name");//
				bean = new ContractBrowseBean("需方", buyer_name);
				bean.setModel_code("buyer_name");
				list.add(bean);

				String sign_date = basicInfo.optString("sign_date");//签订日期
				bean = new ContractBrowseBean("签订日期", sign_date);
				bean.setModel_code("sign_date");
				list.add(bean);

				String sign_address = basicInfo.optString("sign_address");//签订地址
				bean = new ContractBrowseBean("签订地点", sign_address);
				bean.setModel_code("sign_address");
				list.add(bean);

				basic_product_code = basicInfo.optString("basic_product_code");
				basic_item_name = basicInfo.optString("basic_item_name");
				bean = new ContractBrowseBean("基本机型", basic_item_name);
				bean.setModel_code("basic_product_code");
				list.add(bean);

				String power_type =basicInfo.optString("power_type");
				bean = new ContractBrowseBean("动力类型",power_type);
				bean.setModel_code("power_type");
				list.add(bean);

				String tractor = basicInfo.optString("tractor");
				bean = new ContractBrowseBean("牵引车头", tractor);
				bean.setModel_code("tractor");
				list.add(bean);

				String traction_pin = basicInfo.optString("traction_pin");
				bean = new ContractBrowseBean("牵引销", traction_pin);
				bean.setModel_code("traction_pin");
				list.add(bean);

				String traction_seat = basicInfo.optString("traction_seat");
				bean = new ContractBrowseBean("牵引座", traction_seat);
				bean.setModel_code("traction_seat");
				list.add(bean);

				String heightfromground = basicInfo.optString("heightfromground");
				bean = new ContractBrowseBean("离地高度", heightfromground);
				bean.setModel_code("heightfromground");
				list.add(bean);

				String wai_chang = basicInfo.optString("wai_chang");
				bean = new ContractBrowseBean("外部尺寸及测量范围(长)：", wai_chang);
				bean.setModel_code("wai_chang");
				list.add(bean);

				String wai_kuan = basicInfo.optString("wai_kuan");
				bean = new ContractBrowseBean("外部尺寸及测量范围(宽)：", wai_kuan);
				bean.setModel_code("wai_kuan");
				list.add(bean);

				String wai_gao = basicInfo.optString("wai_gao");
				bean = new ContractBrowseBean("外部尺寸及测量范围(高)：", wai_gao);
				bean.setModel_code("wai_gao");
				list.add(bean);

				String nei_chang = basicInfo.optString("nei_chang");
				bean = new ContractBrowseBean("内部尺寸及测量范围(长)", nei_chang);
				bean.setModel_code("nei_chang");
				list.add(bean);

				String nei_kuan = basicInfo.optString("nei_kuan");
				bean = new ContractBrowseBean("内部尺寸及测量范围(宽)", nei_kuan);
				bean.setModel_code("nei_kuan");
				list.add(bean);

				String nei_gao = basicInfo.optString("nei_gao");
				bean = new ContractBrowseBean("内部尺寸及测量范围(高)", nei_gao);
				bean.setModel_code("nei_gao");
				list.add(bean);

				String cexiangban = basicInfo.optString("cexiangban");
				bean = new ContractBrowseBean("侧厢板尺寸", cexiangban);
				bean.setModel_code("cexiangban");
				list.add(bean);

				String tonnage =basicInfo.optString("tonnage");
				bean = new ContractBrowseBean("吨位",tonnage);
				bean.setModel_code("tonnage");
				list.add(bean);

				String zhouju = basicInfo.optString("zhouju");
				bean = new ContractBrowseBean("轴距", zhouju);
				bean.setModel_code("zhouju");
				list.add(bean);

				String model = basicInfo.optString("model");
				bean = new ContractBrowseBean("型号", model);
				bean.setModel_code("model");
				list.add(bean);


				String plate_num = basicInfo.optString("plate_num");
				bean = new ContractBrowseBean("钢板数量", plate_num);
				bean.setModel_code("plate_num");
				list.add(bean);

				String axis_number = basicInfo.optString("axis_number");
				bean = new ContractBrowseBean("轴数", axis_number);
				bean.setModel_code("axis_number");
				list.add(bean);

				String color = basicInfo.optString("color");
				bean = new ContractBrowseBean("车身颜色", color);
				bean.setModel_code("color");
				list.add(bean);

				String steel = basicInfo.optString("steel");
				bean = new ContractBrowseBean("轮胎", steel);
				bean.setModel_code("steel");
				list.add(bean);

				String sum = basicInfo.optString("sum");
				bean = new ContractBrowseBean("数量（辆）", sum);
				bean.setModel_code("sum");
				list.add(bean);

				String price = basicInfo.optString("price");
				bean = new ContractBrowseBean("单价（元）", price);
				bean.setModel_code("price");
				list.add(bean);

				String total_price = basicInfo.optString("total_price");
				bean = new ContractBrowseBean("总价（元）", total_price);
				bean.setModel_code("total_price");
				list.add(bean);

				String shoufu = basicInfo.optString("shoufu");
				bean = new ContractBrowseBean("首付（元）", shoufu);
				bean.setModel_code("shoufu");
				list.add(bean);

				extra_days = basicInfo.optString("extra_days");
				bean = new ContractBrowseBean("提车时间（天）", extra_days);
				bean.setModel_code("extra_days");
				list.add(bean);

				String delivery_model = basicInfo.optString("delivery_model");
				bean = new ContractBrowseBean("提车方式", delivery_model);
				bean.setModel_code("delivery_model");
				list.add(bean);
				String audit_flag = basicInfo.optString("audit_flag");
				switch (audit_flag) {
					case "A":
						audit_flag = "待提交";
						break;
					case "B":
						audit_flag = "生产审核";
						break;
					case "C":
						audit_flag = "生产审核退回";
						break;
					case "D":
						audit_flag = "后勤审核";
						break;
					case "E":
						audit_flag = "后勤审核退回";
						break;
					case "F":
						audit_flag = "等待业务员确认";
						break;
					case "Y":
						audit_flag = "业务员确认";
						break;
					default:
						break;

				}
				bean = new ContractBrowseBean("审核状态", audit_flag);
				list.add(bean);

				String record_man = basicInfo.optString("record_man");
				bean = new ContractBrowseBean("提交人", record_man);
				list.add(bean);

				String record_man_name = basicInfo.optString("record_man_name");
				bean = new ContractBrowseBean("提交人姓名", record_man_name);
				list.add(bean);

				String record_date = basicInfo.optString("record_date");
				bean = new ContractBrowseBean("提交日期", record_date);
				list.add(bean);

				String remark = basicInfo.optString("remark");
				bean = new ContractBrowseBean("其他要求", remark);
				bean.setModel_code("remark");
				list.add(bean);
				List<ContractBrowseBean> list_temp = new ArrayList<>();
				if (listInfo.length() > 0) {
					for (int i = 0; i < listInfo.length(); i++) {
						JSONObject jsonObject_temp = listInfo.getJSONObject(i);
						String parent_name = jsonObject_temp.optString("process_name");
						String parent_code = jsonObject_temp.optString("process_code");
						String class_name = jsonObject_temp.optString("class_name");
						String class_code = jsonObject_temp.optString("class_code");
						String model_name = jsonObject_temp.optString("model_name");
						String model_code = jsonObject_temp.optString("model_code");
						ContractBrowseBean bean1 = new ContractBrowseBean(parent_code, parent_name, class_code, class_name, model_code, model_name);
						if (!TextUtils.isEmpty(class_name)) {
							list_temp.add(bean1);
						}

					}
					Collections.sort(list_temp);
				}

				list.addAll(list_temp);


			} catch (JSONException e) {
				e.printStackTrace();
				return -1;
			}
			return 0;
		}

		@Override
		protected void onPostExecute(Integer integer) {
			super.onPostExecute(integer);
			switch (integer) {
				case -1:
					Toast.makeText(ContractQuery.this, "数据解析出现错误", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(ContractQuery.this, msg, Toast.LENGTH_SHORT).show();
					break;
				case 0:
					adapter.notifyDataSetChanged();
					break;


			}
		}
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDateDisplay(applyDeliveryDate);
		}
	};

	private void updateDateDisplay(EditText editText) {
		String sign_date_string = new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
				.append((mDay < 10) ? "0" + mDay : mDay).toString();
		editText.setText(sign_date_string);
	}
}