package com.fajieyefu.jiuzhou.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.Audit.ApplyToLeaveActivity;
import com.fajieyefu.jiuzhou.Audit.ContractChooseActivity;
import com.fajieyefu.jiuzhou.Audit.ApplyFee;
import com.fajieyefu.jiuzhou.Audit.QualityInputActivity;
import com.fajieyefu.jiuzhou.Audit.ReimbursementActivity;
import com.fajieyefu.jiuzhou.Audit.WorkJournalActivity;
import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fajieyefu on 2016/7/11.
 */
public class YewuActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleText;
    private LinearLayout contract_input, testReslut_input, apply_fee, applyToLeave, work_journal,reimbursement;
    private SharedPreferences pref;
    private String account;
    private Handler handler = new Handler();
    String work_journal_able,applyToLeave_able,testReslut_input_able,apply_fee_able,contract_input_able,reimbursement_able;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.yewu);
        pref = YewuActivity.this.getSharedPreferences("userInfo",MODE_PRIVATE);
        account = pref.getString("account", "");
        //实例化TextView
        titleText= (TextView) findViewById(R.id.title_text);
        titleText.setText("业务管理");
        contract_input = (LinearLayout) findViewById(R.id.contract_input);
        testReslut_input = (LinearLayout) findViewById(R.id.testReslut_input);
        apply_fee = (LinearLayout) findViewById(R.id.apply_fee);
        applyToLeave = (LinearLayout) findViewById(R.id.applyToLeave);
        work_journal = (LinearLayout) findViewById(R.id.work_journal);
        reimbursement= (LinearLayout) findViewById(R.id.reimbursement);
        //TextView设置监听
        contract_input.setOnClickListener(this);
        apply_fee.setOnClickListener(this);
        testReslut_input.setOnClickListener(this);
        applyToLeave.setOnClickListener(this);
        work_journal.setOnClickListener(this);
        reimbursement.setOnClickListener(this);
        new Thread(new MyThread()).start();

        contract_input.setVisibility(View.VISIBLE);//临时添加 完成后删掉


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.contract_input:
                Intent intent_contract_input= new Intent(YewuActivity.this,ContractChooseActivity.class);
                startActivity(intent_contract_input);
                break;
            case R.id.testReslut_input:
                Intent intent_testReslut_input= new Intent(YewuActivity.this,QualityInputActivity.class);
                startActivity(intent_testReslut_input);
                break;
            case R.id.apply_fee:
                Intent intent_apply_fee= new Intent(YewuActivity.this,ApplyFee.class);
                startActivity(intent_apply_fee);
                break;
            case R.id.applyToLeave:
                Intent intent_applyToLeave= new Intent(YewuActivity.this,ApplyToLeaveActivity.class);
                startActivity(intent_applyToLeave);
                break;
            case R.id.work_journal:
                Intent intent_work_journal= new Intent(YewuActivity.this,WorkJournalActivity.class);
                startActivity(intent_work_journal);
                break;
            case R.id.reimbursement:
                Intent intent_reimbursement=new Intent(YewuActivity.this,ReimbursementActivity.class);
                startActivity(intent_reimbursement);
                break;

        }
    }


    private class MyThread implements Runnable {
        @Override
        public void run() {
            String info;
            info = WebService.executeHttpGetAuthority(account);
            if (!TextUtils.isEmpty(info)) {
                try {
                    JSONArray jsonArray= new JSONArray(info);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    work_journal_able =jsonObject.getString("temp1");
                    applyToLeave_able=jsonObject.getString("temp2");
                    testReslut_input_able=jsonObject.getString("temp3");
                    apply_fee_able=jsonObject.getString("temp4");
                    contract_input_able=jsonObject.getString("temp5");
                    reimbursement_able=jsonObject.getString("temp6");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(contract_input_able.equals("success")){
                            contract_input.setVisibility(View.VISIBLE);
                        }
                        if(testReslut_input_able.equals("success")){
                            testReslut_input.setVisibility(View.VISIBLE);
                        }
                        if(apply_fee_able.equals("success")){
                            apply_fee.setVisibility(View.VISIBLE);
                        }
                        if(applyToLeave_able.equals("success")){
                            applyToLeave.setVisibility(View.VISIBLE);
                        }
                        if(work_journal_able.equals("success")){
                            work_journal.setVisibility(View.VISIBLE);
                        }
                        if(reimbursement_able.equals("success")){
                            reimbursement.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }
        }
    }
}


