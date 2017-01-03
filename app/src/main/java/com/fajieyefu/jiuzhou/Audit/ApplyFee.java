package com.fajieyefu.jiuzhou.Audit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.AddFilesAdapter;
import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.BeanUtil;
import com.fajieyefu.jiuzhou.Bean.CommonData;
import com.fajieyefu.jiuzhou.Bean.FilesInfo;
import com.fajieyefu.jiuzhou.Bean.ImageFactory;
import com.fajieyefu.jiuzhou.Bean.ResponseBean;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.util.RespnseCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;

/**
 * Created by Fajieyefu on 2016/7/18.
 */
public class ApplyFee extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
	private TextView titleText;
	private TextView applyFeeMan, applyManDepartment;
	private Spinner applyFeeType, receiveType;
	private EditText useTime;
	private EditText amount, title, content, receiveMan, bankCardCode;
	private String account, user_man_name, depart_code, depart_name, date, time, expect_time, applyFee_bill, applyFeeTypeString, receiveTypeString;
	private String titleString, contentString, amount_string, receiveMan_string, bankCardCode_string;
	private String num;
	private SharedPreferences pref;
	private ArrayAdapter<String> type_arrayAdapter, receive_typeAdapter;
	private String[] receiveTypeArr;
	private ArrayList<String> typeArr = new ArrayList<String>();
	private Button button;
	private Calendar c;
	private int mYear;
	private int mMonth;
	private int mDay;
	private RecyclerView filesRv;
	private List<FilesInfo> list;
	private AddFilesAdapter addFilesAdapter;
	private ArrayList<String> mPhotoList = new ArrayList<>();
	private final static int REQUEST_IMAGE = 1;
	private final static int TYPECOMPLETE = 20;
	private Map<String, File> map;
	private static final int FILESCOMPLETE = 10;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case FILESCOMPLETE:
					postToServer();
					break;
				case TYPECOMPLETE:
					type_arrayAdapter.notifyDataSetChanged();
					break;
			}
		}
	};
	private BeanUtil beanUtil = new BeanUtil();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_fee);
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("用款申请");
		applyFeeMan = (TextView) findViewById(R.id.applyfee_man);
		applyManDepartment = (TextView) findViewById(R.id.department);
		applyFeeType = (Spinner) findViewById(R.id.applyfee_type);
		receiveType = (Spinner) findViewById(R.id.receive_type);
		useTime = (EditText) findViewById(R.id.use_time);
		amount = (EditText) findViewById(R.id.amount);
		title = (EditText) findViewById(R.id.applyfee_title);
		receiveMan = (EditText) findViewById(R.id.receive_man);
		bankCardCode = (EditText) findViewById(R.id.bank_cardcode);
		content = (EditText) findViewById(R.id.applyfee_content);
		button = (Button) findViewById(R.id.submit_aply_fee);
		filesRv = (RecyclerView) findViewById(R.id.files_rv);
		receiveTypeArr = new String[]{"现金", "支票", "转账"};
		receive_typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, receiveTypeArr);
		type_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeArr);

		pref = ApplyFee.this.getSharedPreferences("userInfo", MODE_PRIVATE);
		account = pref.getString("account", "");
		depart_name = pref.getString("depart_name", "");
		user_man_name = pref.getString("username", "");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

		Long times = System.currentTimeMillis();
		date = simpleDateFormat.format(times);

		c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		applyFeeMan.setText(user_man_name);
		applyManDepartment.setText(depart_name);
		applyFeeType.setAdapter(type_arrayAdapter);
		receiveType.setAdapter(receive_typeAdapter);
		applyFeeType.setOnItemSelectedListener(this);
		receiveType.setOnItemSelectedListener(this);
		useTime.setInputType(InputType.TYPE_NULL);
		useTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Dialog dialog = new DatePickerDialog(ApplyFee.this, mDateSetListener, mYear, mMonth, mDay);
				dialog.show();
			}
		});
		initApplyFeeType();
		initFiles();

		button.setOnClickListener(this);


	}


	private void initFiles() {
		File file = new File(CommonData.PIC_TEMP);
		if (!file.exists()) {
			file.mkdirs();
		}
		for (int i = 1; i < 11; i++) {
			file = new File(CommonData.PIC_TEMP + i + ".jpg");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		list = new ArrayList<>();
		FilesInfo filesInfo = new FilesInfo();
		list.add(filesInfo);
		addFilesAdapter = new AddFilesAdapter(list, this);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		//默认为vertical，可以不设置
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		filesRv.setLayoutManager(layoutManager);
		filesRv.setAdapter(addFilesAdapter);
		addFilesAdapter.setClickListener(new AddFilesAdapter.OnItemClickListener() {
											 @Override
											 public void onClick(View view, int position) {
												 switch (position) {
													 case 0:
														 initPhotoList();
														 MultiImageSelector.create(ApplyFee.this)
																 .showCamera(true)
																 .count(10)
																 .multi()//设置为多选模式，.single为单选模式
																 .origin(mPhotoList).start(ApplyFee.this, REQUEST_IMAGE);
														 break;

												 }
											 }
										 }

		);

	}

	private void initPhotoList() {
		int size = list.size();
		if (size == 1) {
			return;
		}
		mPhotoList.clear();
		for (int i = 1; i < size; i++) {
			mPhotoList.add(list.get(i).getAdd_file_path());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE) {
			if (resultCode == RESULT_OK) {
				list.clear();
				list.add(new FilesInfo());
				// 获取返回的图片列表
				List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
				// 处理你自己的逻辑 ....
				if (path.size() != 0) {
					for (int i = 0; i < path.size(); i++) {
						FilesInfo fileInfo = new FilesInfo();
						fileInfo.setAdd_file_path(path.get(i));
						list.add(fileInfo);
					}

				}
				addFilesAdapter.notifyDataSetChanged();
			}
		}
	}

	private void initApplyFeeType() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String info = WebService.getApplyFeeType(account);
				Log.i("出差类型", info);
				if (TextUtils.isEmpty(info) || info.equals("fail")) {
					return;
				} else {
					try {
						System.out.println(info);
						JSONArray jsonArray = new JSONArray(info);
						int len = jsonArray.length();
						String type;
						JSONObject jsonObject;

						for (int i = 0; i < len; i++) {
							jsonObject = jsonArray.getJSONObject(i);
							type = jsonObject.getString("type");
							typeArr.add(type);
						}
						Message message = new Message();
						message.what = TYPECOMPLETE;
						mHandler.sendMessage(message);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}


			}
		}).start();


	}


	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
		switch (adapterView.getId()) {
			case R.id.applyfee_type:
				applyFeeTypeString = typeArr.get(position);
				System.out.println(applyFeeTypeString);
				break;
			case R.id.receive_type:
				receiveTypeString = receiveTypeArr[position];
				break;

		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDateDisplay();
		}
	};

	private void updateDateDisplay() {
		expect_time = new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
				.append((mDay < 10) ? "0" + mDay : mDay).toString();
		useTime.setText(expect_time);
	}

	@Override
	public void onClick(View v) {
		titleString = title.getText().toString();
		contentString = content.getText().toString();
		amount_string = amount.getText().toString();
		receiveMan_string = receiveMan.getText().toString();
		bankCardCode_string = bankCardCode.getText().toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Long times = System.currentTimeMillis();
		time = dateFormat.format(times);

		if (titleString.equals("") || contentString.equals("") || receiveMan_string.equals("") || bankCardCode_string.equals("")) {
			Toast.makeText(ApplyFee.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(applyFeeTypeString)) {
			Toast.makeText(ApplyFee.this, "请选择用款类别", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(receiveTypeString)) {
			Toast.makeText(ApplyFee.this, "请选择付款类型！", Toast.LENGTH_SHORT).show();
			return;
		}

		if (amount_string.equals("")) {
			Toast.makeText(ApplyFee.this, "请填写用款数额！", Toast.LENGTH_SHORT).show();
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				map = new HashMap<>();
				Bitmap bitmap = null;
				ImageFactory imageFactory = new ImageFactory();
				if (list.size() > 1) {
					for (int i = 1; i < list.size(); i++) {
						File file = new File(CommonData.PIC_TEMP + i + ".jpg");
						bitmap = imageFactory.getBitmap(list.get(i).getAdd_file_path());
						ImageFactory.compressBmpToFile(bitmap, file);
						map.put(i + ".jpg", file);
					}
				}
				Message message = new Message();
				message.what = FILESCOMPLETE;
				mHandler.sendMessage(message);

			}
		}).start();


//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//				Long times = System.currentTimeMillis();
//				time=dateFormat.format(times);
//				String info=WebService.makeApplyFee(account,depart_name,applyFeeTypeString,time,expect_time,amount_string,titleString
//						,contentString,receiveMan_string,bankCardCode_string,receiveTypeString);
//				System.out.println(info);
//				if(!TextUtils.isEmpty(info)&&!info.equals("fail")){
//					try {
//						JSONObject jsonObject= new JSONObject(info);
//						num=jsonObject.getString("num");
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//					handler.post(new Runnable() {
//						@Override
//						public void run() {
//							final AlertDialog.Builder dialog= new AlertDialog.Builder(ApplyFee.this);
//							num="0"+num;
//							num=num.substring(num.length()-2,num.length());
//							applyFee_bill="YKSQ"+account+date+num;
//							dialog.setTitle("申请成功！");
//							dialog.setMessage("申请单号:"+applyFee_bill+",请等待审核。"+"\r\n"+"申请时间："+time+"\n"+"申请人："+account);
//							dialog.setCancelable(false);
//							dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialogInterface, int i) {
//									ApplyFee.this.finish();
//								}
//							});
//							dialog.show();
//						}
//					});
//				}
//				else {
//					handler.post(new Runnable() {
//						@Override
//						public void run() {
//							Toast.makeText(ApplyFee.this, "操作异常，请重试！", Toast.LENGTH_SHORT).show();
//						}
//					});
//
//				}
//
//
//
//
//			}
//		}).start();
	}

	private void postToServer() {
		beanUtil.showProgressDialog(this,"请稍后","正在提交数据");
		OkHttpUtils.post()
				.url(CommonData.APPLYFEE_SUBMIT)
				.files("files", map)
				.addParams("account", account)
				.addParams("depart_name", depart_name)
				.addParams("applyFeeTypeString", applyFeeTypeString)
				.addParams("time", time)
				.addParams("expect_time", expect_time)
				.addParams("amount_string", amount_string)
				.addParams("title", titleString)
				.addParams("content", contentString)
				.addParams("receiveMan_string", receiveMan_string)
				.addParams("bankCardCode_String", bankCardCode_string)
				.addParams("receiveTypeString", receiveTypeString)
				.build()
				.execute(new MyCallBack());
	}

	private class MyCallBack extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {
			beanUtil.dismissProgressDialog();
			Toast.makeText(ApplyFee.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			beanUtil.dismissProgressDialog();
			if (response.getCode() == 0) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(ApplyFee.this);
				num = "0" + num;
				num = num.substring(num.length() - 2, num.length());
				applyFee_bill = "YKSQ" + account + date + num;
				dialog.setTitle("申请成功！");
				dialog.setMessage("申请单号:" + applyFee_bill + ",请等待审核。" + "\r\n" + "申请时间：" + time + "\n" + "申请人：" + account);
				dialog.setCancelable(false);
				dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						ApplyFee.this.finish();
					}
				});
				dialog.show();
			} else {
				Toast.makeText(ApplyFee.this, "操作异常，请重试！", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
