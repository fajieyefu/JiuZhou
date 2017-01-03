package com.fajieyefu.jiuzhou.Audit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.BeanUtil;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.activity.YewuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Fajieyefu on 2016/7/18.
 */
public class ApplyToLeaveActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private TextView titleText;
    private TextView travelMan, department;
    private EditText title, content, travelDays, travelDate;
    private Spinner travelType;
    private String account, user_man_name,departcode, depart_name, travel_date_string, titleString, contentString, travelDayString, travelTypeString;
    private Handler handler = new Handler();
    private SharedPreferences pref;
    private ArrayList<String> typeArr = new ArrayList<String>();
    private ArrayAdapter<String> typeAdapter;
    private int mYear, mMonth, mDay;
    private Calendar c;
    private String date, num;
    private Button submit;
    private String type = "出差";
    private BeanUtil beanUtil = new BeanUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.applytoleave);
        titleText = (TextView) findViewById(R.id.title_text);
        titleText.setText("出差申请");
        pref = ApplyToLeaveActivity.this.getSharedPreferences("userInfo", MODE_PRIVATE);
        account = pref.getString("account", "");
        departcode=pref.getString("depart_code","");
        depart_name= pref.getString("depart_name","");
        user_man_name=pref.getString("username","");

        //初始化控件
        travelMan = (TextView) findViewById(R.id.travel_man);
        department = (TextView) findViewById(R.id.department);
        travelDate = (EditText) findViewById(R.id.travel_date);
        title = (EditText) findViewById(R.id.travel_title);
        content = (EditText) findViewById(R.id.travel_content);
        travelDays = (EditText) findViewById(R.id.travel_days);
        travelType = (Spinner) findViewById(R.id.travel_type);
        submit = (Button) findViewById(R.id.submit_aply_leave);
        //获取控件的取值
        travelMan.setText(user_man_name);
        department.setText(depart_name);
        typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeArr);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Long times = System.currentTimeMillis();
        date = simpleDateFormat.format(times);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        travelDate.setInputType(InputType.TYPE_NULL);
        travelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new DatePickerDialog(ApplyToLeaveActivity.this, mDateSetListener, mYear, mMonth, mDay);
                dialog.show();
            }
        });

        travelType.setAdapter(typeAdapter);
        travelType.setOnItemSelectedListener(this);
        initTravelType();

        submit.setOnClickListener(this);

    }

    private void initTravelType() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String info = WebService.getType(departcode, type);
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
                                typeAdapter.notifyDataSetChanged();
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
            case R.id.travel_type:
                travelTypeString = typeArr.get(position);
                System.out.println(travelTypeString);
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
        travel_date_string = new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay).toString();
        travelDate.setText(travel_date_string);
    }

    @Override
    public void onClick(View view) {
        titleString = title.getText().toString();
        contentString = content.getText().toString();
        travel_date_string = travelDate.getText().toString();
        travelDayString = travelDays.getText().toString();

        switch (view.getId()) {
            case R.id.submit_aply_leave:
                if (titleString.equals("") || contentString.equals("")) {
                    Toast.makeText(ApplyToLeaveActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(travelTypeString)) {
                    Toast.makeText(ApplyToLeaveActivity.this, "请选择出差类别", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(travel_date_string)) {
                    Toast.makeText(ApplyToLeaveActivity.this, "请选择出差日期！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (travelDayString.equals("")) {
                    Toast.makeText(ApplyToLeaveActivity.this, "请填写出差时间！", Toast.LENGTH_SHORT).show();
                    return;
                }
                beanUtil.showProgressDialog(this,"请稍等","正在提交数据");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                        Long times = System.currentTimeMillis();
                        final String time = dateFormat.format(times);
                        String info = WebService.makeTravelInfo(account,depart_name, departcode, time, travelTypeString, titleString
                                , contentString, travel_date_string, travelDayString);

                        System.out.println(info);
                        if (!TextUtils.isEmpty(info) && !info.equals("fail")) {
                            try {
                                JSONObject jsonObject = new JSONObject(info);
                                num = jsonObject.optInt("num")+"";
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    beanUtil.dismissProgressDialog();
                                    Toast.makeText(ApplyToLeaveActivity.this, "操作异常，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }


                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                beanUtil.dismissProgressDialog();
                                final AlertDialog.Builder dialog = new AlertDialog.Builder(ApplyToLeaveActivity.this);
                                num = "0" + num;
                                num = num.substring(num.length() - 2, num.length());
                                String travel_bill = "YKSQ" + account + date + num;
                                dialog.setTitle("申请成功！");
                                dialog.setMessage("申请单号:" + travel_bill + ",请等待审核。" + "\r\n" + "申请时间：" + time + "\n" + "申请人：" + account);
                                dialog.setCancelable(false);
                                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ApplyToLeaveActivity.this.finish();
                                    }
                                });
                                dialog.show();
                            }
                        });

                    }
                }).start();
        }
    }
}
