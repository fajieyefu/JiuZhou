package com.fajieyefu.jiuzhou.Audit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.fajieyefu.jiuzhou.activity.YewuActivity;
import com.fajieyefu.jiuzhou.util.RespnseCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;

/**
 * Created by Fajieyefu on 2016/7/23.
 */
public class ReimbursementActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
	private TextView titleText;
	private TextView reimbursementManTextView, reimbursementDepartmentTextView;
	private Spinner reimbursementSpinner;
	private EditText reimbursementAmountText, title, content;
	private String account, user_man_name, depart_name, depart_code, time, date, apply_bill, applyTypeString;
	private String titleString, contentString, amount_string;
	private String num;
	private SharedPreferences pref;
	private ArrayAdapter<String> type_arrayAdapter;
	private ArrayList<String> typeArr = new ArrayList<String>();
	private Button button;
	private Handler handler = new Handler();
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reimbursement);
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("报销申请");
		reimbursementManTextView = (TextView) findViewById(R.id.reimbursement_man);
		reimbursementDepartmentTextView = (TextView) findViewById(R.id.department);
		reimbursementSpinner = (Spinner) findViewById(R.id.reimbursement_type);
		reimbursementAmountText = (EditText) findViewById(R.id.reimbursement_amount);

		title = (EditText) findViewById(R.id.reimbursement_title);
		content = (EditText) findViewById(R.id.reimbursement_content);
		button = (Button) findViewById(R.id.submit_reimbursement);
		type_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeArr);

		filesRv = (RecyclerView) findViewById(R.id.files_rv);
		pref = ReimbursementActivity.this.getSharedPreferences("userInfo", MODE_PRIVATE);
		account = pref.getString("account", "");
		depart_code = pref.getString("depart_code", "");
		depart_name = pref.getString("depart_name", "");
		user_man_name = pref.getString("username", "");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

		Long times = System.currentTimeMillis();
		date = simpleDateFormat.format(times);
		reimbursementManTextView.setText(user_man_name);
		reimbursementDepartmentTextView.setText(depart_name);
		reimbursementSpinner.setAdapter(type_arrayAdapter);
		reimbursementSpinner.setOnItemSelectedListener(this);
		initReimbursementType();
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
														 MultiImageSelector.create(ReimbursementActivity.this)
																 .showCamera(true)
																 .count(10)
																 .multi()//设置为多选模式，.single为单选模式
																 .origin(mPhotoList).start(ReimbursementActivity.this, REQUEST_IMAGE);
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

	private void initReimbursementType() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String info = WebService.getReimbursement(depart_code);

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

						handler.post(new Runnable() {
							@Override
							public void run() {
								type_arrayAdapter.notifyDataSetChanged();
							}
						});

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
			case R.id.reimbursement_type:
				applyTypeString = typeArr.get(position);
				break;

		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}


	@Override
	public void onClick(View v) {

		titleString = title.getText().toString();
		contentString = content.getText().toString();
		amount_string = reimbursementAmountText.getText().toString();

		if (titleString.equals("") || contentString.equals("")) {
			Toast.makeText(ReimbursementActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(applyTypeString)) {
			Toast.makeText(ReimbursementActivity.this, "请选择报销申请类别", Toast.LENGTH_SHORT).show();
			return;
		}

		if (amount_string.equals("")) {
			Toast.makeText(ReimbursementActivity.this, "请填写报销数额！", Toast.LENGTH_SHORT).show();
			return;
		}
		beanUtil.showProgressDialog(this, "正在提交数据", "请稍后...");
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
//
//				String info = WebService.makeReimbursement(account, depart_code, depart_name, applyTypeString, time, amount_string, titleString
//						, contentString);
//				System.out.println(info);
//				if (!TextUtils.isEmpty(info) && !info.equals("fail")) {
//					try {
//						JSONObject jsonObject = new JSONObject(info);
//						num = jsonObject.getString("num");
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				} else {
//					handler.post(new Runnable() {
//						@Override
//						public void run() {
//							Toast.makeText(ReimbursementActivity.this, "操作异常，请重试！", Toast.LENGTH_SHORT).show();
//							return;
//						}
//					});
//				}
//
//				handler.post(new Runnable() {
//					@Override
//					public void run() {
//						final AlertDialog.Builder dialog = new AlertDialog.Builder(ReimbursementActivity.this);
//						num = "0" + num;
//						num = num.substring(num.length() - 2, num.length());
//						apply_bill = "BXSQ" + account + date + num;
//
//						dialog.setTitle("申请成功！");
//						dialog.setMessage("申请单号:" + apply_bill + ",请等待审核。" + "\r\n" + "申请时间：" + time + "\n" + "申请人：" + account);
//						dialog.setCancelable(false);
//						dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialogInterface, int i) {
//								ReimbursementActivity.this.finish();
//							}
//						});
//						dialog.show();
//					}
//				});
//
//			}
//		}).start();
	}

	private void postToServer() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Long times = System.currentTimeMillis();
		time = dateFormat.format(times);
		OkHttpUtils.post()
				.url(CommonData.REIMBURSEMENT_SUBMIT)
				.files("files", map)
				.addParams("account", account)
				.addParams("departCode", depart_code)
				.addParams("departname", depart_name)
				.addParams("applyFeeTypeString", applyTypeString)
				.addParams("time", time)
				.addParams("amount_string", amount_string)
				.addParams("title", titleString)
				.addParams("content", contentString)
				.build()
				.execute(new MyCallBack());
	}

	private class MyCallBack extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {
			beanUtil.dismissProgressDialog();
			Toast.makeText(ReimbursementActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			beanUtil.dismissProgressDialog();
			if (response.getCode() == 0) {

				num = response.getNum() + "";
				final AlertDialog.Builder dialog = new AlertDialog.Builder(ReimbursementActivity.this);
				num = "0" + num;
				num = num.substring(num.length() - 2, num.length());
				apply_bill = "BXSQ" + account + date + num;

				dialog.setTitle("申请成功！");
				dialog.setMessage("申请单号:" + apply_bill + ",请等待审核。" + "\r\n" + "申请时间：" + time + "\n" + "申请人：" + account);
				dialog.setCancelable(false);
				dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						ReimbursementActivity.this.finish();
					}
				});
				dialog.show();
			} else {

				Toast.makeText(ReimbursementActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
