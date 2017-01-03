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
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;


/**
 * Created by Fajieyefu on 2016/8/8.
 */
public class QualityInputActivity extends BaseActivity implements View.OnClickListener{
    private TextView titleText;
    private Spinner bom_model_spinner,check_gongxu_spinner,monitor_spinner,inspector_spinner;
    private ArrayList<String> bom_model_list,check_gongxu_list,monitor_list,inspector_list;
    private ArrayAdapter<String> bom_model_adapter,check_gongxu_adapter,monitor_adapter,inspector_adapter;
    private EditText produce_date_edit,off_date_edit,check_date_edit;
    private EditText production_no_edit;
    private Button submit;
    private SharedPreferences pref;
    private String account,userName;
    private JSONObject jsonObject,jsonObject_temp;
    private Handler handler = new Handler();
    private Calendar c;
    private int mYear;
    private int mMonth;
    private int mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.quality_input);
        titleText= (TextView) findViewById(R.id.title_text);
        titleText.setText("质量检验结果录入");
        pref=QualityInputActivity.this.getSharedPreferences("userInfo",MODE_PRIVATE);
        account=pref.getString("account","");
        userName=pref.getString("username","");
        c=Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        initWeight();
        submit.setOnClickListener(this);
        initAdapter();
        getSpinnerInfo();


    }

    private void initAdapter() {
        bom_model_list=new ArrayList<String>();
        bom_model_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bom_model_list);
        check_gongxu_list=new ArrayList<String>();
        check_gongxu_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,check_gongxu_list);
        monitor_list=new ArrayList<String>();
        monitor_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,monitor_list);
        inspector_list=new ArrayList<String>();
        inspector_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,inspector_list);
    }

    private void initWeight() {
        bom_model_spinner= (Spinner) findViewById(R.id.bom_model);
        check_gongxu_spinner= (Spinner) findViewById(R.id.check_gongxu);
        monitor_spinner= (Spinner) findViewById(R.id.monitor);
        inspector_spinner= (Spinner) findViewById(R.id.inspector);
        produce_date_edit= (EditText) findViewById(R.id.produce_date);
        off_date_edit= (EditText) findViewById(R.id.off_date);
        check_date_edit= (EditText) findViewById(R.id.check_date);
        production_no_edit= (EditText) findViewById(R.id.production_no);
        long times =System.currentTimeMillis();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        String date=dateFormat.format(times);
        produce_date_edit.setText(date);
        off_date_edit.setText(date);
        check_date_edit.setText(date);
        submit= (Button) findViewById(R.id.submit_quality_input);
        produce_date_edit.setInputType(InputType.TYPE_NULL);
        produce_date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new DatePickerDialog(QualityInputActivity.this,mDateSetListener,mYear,mMonth,mDay);
                dialog.show();
            }
        });
        off_date_edit.setInputType(InputType.TYPE_NULL);
        off_date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new DatePickerDialog(QualityInputActivity.this,mDateSetListener_1,mYear,mMonth,mDay);
                dialog.show();
            }
        });
        check_date_edit.setInputType(InputType.TYPE_NULL);
        check_date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new DatePickerDialog(QualityInputActivity.this,mDateSetListener_2,mYear,mMonth,mDay);
                dialog.show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(QualityInputActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("确认提交信息吗？");
        dialog.setCancelable(false);
        dialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                submitInfo();
            }
        });
        dialog.show();

    }

    private void submitInfo() {
        final String bom_model=getString(bom_model_spinner);
        final String check_gongxu=getString(check_gongxu_spinner);
        final String monitor=getString(monitor_spinner);
        final String inspector=getString(inspector_spinner);
        final String off_date=getString(off_date_edit);
        final String check_date=getString(check_date_edit);
        final String produce_date=getString(produce_date_edit);
        final String production_no=getString(production_no_edit);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("account",account);
                params.put("userName",userName);
                params.put("bom_model",bom_model);
                params.put("check_gongxu",check_gongxu.split(":")[0]);
                params.put("check_gongxu_name",check_gongxu.split(":")[1]);
                params.put("monitor",monitor.split(":")[0]);
                params.put("monitor_name",monitor.split(":")[1]);
                params.put("inspector",inspector.split(":")[0]);
                params.put("inspector_name",inspector.split(":")[1]);
                params.put("off_date",off_date);
                params.put("check_date",check_date);
                params.put("produce_date",produce_date);
                params.put("production_no",production_no);
                final String info =WebService.makeQualityInfo(params);
                if(info.equals("0")){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(QualityInputActivity.this, "生产号不存在！", Toast.LENGTH_SHORT).show();
                        }

                    });
                }
                else if(info.equals("1")){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(QualityInputActivity.this, "该工序检验结果已经录入！", Toast.LENGTH_SHORT).show();
                        }

                    });
                }else if (info.equals("3")){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(info);
                            Intent intent = new Intent(QualityInputActivity.this,QualityDetailActivity.class);
                            intent.putExtra("process_code",check_gongxu.split(":")[0]);
                            intent.putExtra("product_no",production_no);
                            startActivity(intent);
                            QualityInputActivity.this.finish();
                        }
                    });
                }else{
                    Toast.makeText(QualityInputActivity.this, "出现异常，请稍后重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }


    private String getString(Spinner bom_model_spinner) {
        return bom_model_spinner.getSelectedItem().toString();
    }
    private String getString(EditText editText){
        return editText.getText().toString();
    }

    public void getSpinnerInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String info = WebService.getQualitySpinner();
                try {
                    jsonObject=new JSONObject(info);
                    manageJsonInfo("bom",bom_model_list,bom_model_adapter,bom_model_spinner);
                    manageJsonInfo("gongXu",check_gongxu_list,check_gongxu_adapter,check_gongxu_spinner);
                    manageJsonInfo("monitor",monitor_list,monitor_adapter,monitor_spinner);
                    manageJsonInfo("inspector",inspector_list,inspector_adapter,inspector_spinner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }}).start();
    }
    private void manageJsonInfo(final String name, final ArrayList<String> list, final ArrayAdapter<String> adapter, final Spinner spinner) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jsonObject_temp= jsonObject.getJSONObject(name);
                    int len=jsonObject_temp.length();
                    int i=1;
                    while (i<=len){
                        list.add(jsonObject_temp.getString(i+""));
                        i++;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            spinner.setPrompt("请选择类型");
                            spinner.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            updateDateDisplay(produce_date_edit);
        }
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener_1 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            updateDateDisplay(off_date_edit);
        }
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener_2 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            updateDateDisplay(check_date_edit);
        }
    };
    private void updateDateDisplay(EditText editText){
        String expect_time=new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay).toString();
        editText.setText(expect_time);
    }
}
