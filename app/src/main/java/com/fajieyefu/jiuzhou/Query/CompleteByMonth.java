package com.fajieyefu.jiuzhou.Query;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.CommonData;
import com.fajieyefu.jiuzhou.Bean.CompleteAdapter;
import com.fajieyefu.jiuzhou.Bean.CompleteCarBean;
import com.fajieyefu.jiuzhou.Bean.MyDatePickerDialog;
import com.fajieyefu.jiuzhou.Bean.ResponseBean;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.util.RespnseCallBack;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by qiancheng on 2016/12/15.
 */
public class CompleteByMonth extends BaseActivity {
	@BindView(R.id.back_ib)
	ImageButton backIb;
	@BindView(R.id.title_middle_edit)
	EditText titleMiddleEdit;
	@BindView(R.id.complete_lv)
	ListView completeLv;
	private String chooseMonth;
	private CompleteAdapter completeAdapter;
	private List<CompleteCarBean> list = new ArrayList<>();
	private DatePickerDialog dialog;
	private int mYear, mMonth, mDay;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_bymoth);
		ButterKnife.bind(this);
		init();
		loadingView(chooseMonth);


	}

	private void init() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		long times = System.currentTimeMillis();
		chooseMonth = dateFormat.format(times);
		titleMiddleEdit.setText(chooseMonth);
		completeAdapter = new CompleteAdapter(this, list);
		completeLv.setAdapter(completeAdapter);
		Calendar calendar = Calendar.getInstance();
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);
		dialog = new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);

		titleMiddleEdit.setInputType(InputType.TYPE_NULL);
	}

	@OnClick({R.id.back_ib, R.id.title_middle_edit})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.back_ib:
				finish();
				break;
			case R.id.title_middle_edit:
				dialog.show();
				DatePicker datePicker = findDatePicker((ViewGroup) dialog.getWindow().getDecorView());
				if (datePicker!=null){
					((ViewGroup)((ViewGroup)datePicker.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
				}
				break;
		}
	}

	private void loadingView(String date) {
		progressDialog= new ProgressDialog(this);
		progressDialog.setTitle("请稍后");
		progressDialog.setMessage("正在获取信息....");
		progressDialog.show();
		OkHttpUtils
				.post()
				.url(CommonData.COMPLETEBYMONTH)
				.addParams("date", date)
				.build()
				.execute(new MyCallback());
	}




	private class MyCallback extends RespnseCallBack {
		@Override
		public void onError(Call call, Exception e, int id) {

		}

		@Override
		public void onResponse(ResponseBean response, int id) {
			if (progressDialog!=null){
				progressDialog.dismiss();
			}
			if (response.getCode() == 0) {
				list.clear();
				list.addAll(response.getCompleteCar());
				String total ="总计";
				int chuche_total=0;
				int penqi_total=0;
				int fache_total=0;
				int contract_total=0;
				for (int i=0;i<list.size();i++){
					chuche_total=chuche_total+Integer.parseInt(list.get(i).getChuche_num());
					penqi_total=penqi_total+Integer.parseInt(list.get(i).getPenqi_num());
					fache_total=fache_total+Integer.parseInt(list.get(i).getFache_num());
					contract_total=contract_total+Integer.parseInt(list.get(i).getContract_car_num());
				}
				CompleteCarBean completeCarBean = new CompleteCarBean();
				completeCarBean.setChuche_num(chuche_total+"");
				completeCarBean.setDate(total);
				completeCarBean.setContract_car_num(contract_total+"");
				completeCarBean.setFache_num(fache_total+"");
				completeCarBean.setPenqi_num(penqi_total+"");
				list.add(completeCarBean);
				completeAdapter.notifyDataSetChanged();

			} else {
				Toast.makeText(CompleteByMonth.this, response.getMsg(), Toast.LENGTH_SHORT).show();
			}
		}
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
		String expect_time = new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.toString();
		titleMiddleEdit.setText(expect_time);
		loadingView(expect_time);


	}
	private DatePicker findDatePicker(ViewGroup group) {
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				View child = group.getChildAt(i);
				if (child instanceof DatePicker) {
					return (DatePicker) child;
				} else if (child instanceof ViewGroup) {
					DatePicker result = findDatePicker((ViewGroup) child);
					if (result != null)
						return result;
				}
			}
		}
		return null;
	}
}
