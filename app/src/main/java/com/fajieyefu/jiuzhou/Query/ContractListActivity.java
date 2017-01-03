package com.fajieyefu.jiuzhou.Query;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.ApplyInfo;
import com.fajieyefu.jiuzhou.Bean.ApplyInfoAdapter;
import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Fajieyefu on 2016/8/8.
 */
public class ContractListActivity extends BaseActivity {
    private TextView titleText;
    private String account;
    private SharedPreferences pref;
    private ArrayList<ApplyInfo> list;
    private ApplyInfoAdapter applyInfoAdapter;
    private Button leadMore;
    private ListView listView;
    private int count = 1;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reimbursement_query);
        pref= this.getSharedPreferences("userInfo",MODE_PRIVATE);
        account=pref.getString("account","");
        titleText= (TextView) findViewById(R.id.title_text);
        titleText.setText("合同信息查询");
        list = new ArrayList<ApplyInfo>();
        applyInfoAdapter= new ApplyInfoAdapter(this,R.layout.news_item_first,list);
        View bottomView = getLayoutInflater().inflate(R.layout.bottom, null);
        leadMore = (Button) bottomView.findViewById(R.id.leadMore);
        listView= (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty_view));
        listView.addFooterView(bottomView);
        listView.setAdapter(applyInfoAdapter);
        leadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initInfo();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ApplyInfo applyInfo = list.get(i);//通过位置获取实例；
                Intent intent = new Intent(ContractListActivity.this, ContractQuery.class);
                intent.putExtra("loan_code", applyInfo.getLoan_code());
                startActivity(intent);
            }
        });
        initInfo();
    }

    private void initInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String info= WebService.getQueryContractInfo(account,count);
                JSONArray jsonArray;
                JSONObject jsonObject;
                if (!TextUtils.isEmpty(info) && !info.equals("fail")) {
                    try {
                        jsonArray = new JSONArray(info);
                        int len = jsonArray.length();
                        for (int i = 0; i < len; i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            String loan_date= jsonObject.optString("loan_date");
                            String title = jsonObject.optString("loan_name");
                            String loan_code = jsonObject.optString("loan_code");
                            String bill_flag= jsonObject.optString("bill_flag");
                            loan_date=loan_date.split(" ")[0];
                            ApplyInfo applyInfo = new ApplyInfo();
                            applyInfo.setTitle("["+loan_code+"]"+title);
                            applyInfo.setLoan_date(loan_date);
                            applyInfo.setLoan_code(loan_code);
                            applyInfo.setBill_flag(bill_flag);
                            list.add(applyInfo);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            applyInfoAdapter.notifyDataSetChanged();
                            listView.setSelection(list.size() - 1);
                        }
                    });
                    count++;

                }
                else{
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ContractListActivity.this, "已经到最后了！", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        }).start();

    }
}
