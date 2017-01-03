package com.fajieyefu.jiuzhou.Audit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.BeanUtil;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fajieyefu on 2016/7/18.
 */
public class WorkJournalActivity extends Activity {
    private TextView textView;
    private EditText main_work_Edit,work_detail_Edit;
    private Button submit;
    private SharedPreferences pref;
    private String account,user_man_name,depart_code,depart_name,main_work,work_detail;
    private Handler handler = new Handler();
    BeanUtil beanUtil =  new BeanUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.work_journal);
        textView= (TextView) findViewById(R.id.title_text);
        textView.setText("工作日志录入");
        pref=WorkJournalActivity.this.getSharedPreferences("userInfo",MODE_PRIVATE);

        account=pref.getString("account","");
        user_man_name=pref.getString("username","");
        depart_code=pref.getString("depart_code","");
        depart_name=pref.getString("depart_name","");

        main_work_Edit= (EditText) findViewById(R.id.main_work);
        work_detail_Edit= (EditText) findViewById(R.id.work_detail);
        submit= (Button) findViewById(R.id.submit_journal);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               main_work=main_work_Edit.getText().toString();
                work_detail=work_detail_Edit.getText().toString();
                if(TextUtils.isEmpty(main_work)){
                    Toast.makeText(WorkJournalActivity.this, "请输入标题！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(work_detail)){
                    Toast.makeText(WorkJournalActivity.this, "请输入内容！", Toast.LENGTH_SHORT).show();
                }else{
                    beanUtil.showProgressDialog(WorkJournalActivity.this,"正在提交数据","请稍等");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Map<String,String> params= new HashMap<String, String>();
                            params.put("account",account);
                            params.put("user_man_name",user_man_name);
                            params.put("depart_code",depart_code);
                            params.put("depart_name",depart_name);
                            params.put("main_work",main_work);
                            params.put("work_detail",work_detail);
                            String info =WebService.makeWorkJournal(params);
                            if (info.equals("1")){
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        beanUtil.dismissProgressDialog();
                                        AlertDialog.Builder dialog= new AlertDialog.Builder(WorkJournalActivity.this);
                                        dialog.setTitle("提示：");
                                        dialog.setMessage("日志录入成功！");
                                        dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        });
                                        dialog.show();
                                    }
                                });

                            }else if(info.equals("-1")){
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        beanUtil.dismissProgressDialog();
                                        AlertDialog.Builder dialog= new AlertDialog.Builder(WorkJournalActivity.this);
                                        dialog.setTitle("提示：");
                                        dialog.setMessage("网络出现异常，请检查网络！");
                                        dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }

                                        });
                                        dialog.show();
                                    }
                                });
                            }else{
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder dialog= new AlertDialog.Builder(WorkJournalActivity.this);
                                        dialog.setTitle("提示：");
                                        dialog.setMessage("服务端出现错误，请稍后重试！");
                                        dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }

                                        });
                                        dialog.show();
                                    }
                                });
                            }
                        }
                    }).start();


                }
            }
        });
    }
}
