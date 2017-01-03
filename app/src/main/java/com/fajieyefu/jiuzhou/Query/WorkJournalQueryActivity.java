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
 * Created by Fajieyefu on 2016/7/26.
 */
public class WorkJournalQueryActivity  extends BaseActivity{
    private TextView titleView;
    private SharedPreferences pref;
    private String account;
    private ArrayList<ApplyInfo> list;
    private ApplyInfoAdapter applyInfoAdapter;
    private Button leadMore;
    private  ListView listView;
    private Handler handler = new Handler();
    private int count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reimbursement_query);
        titleView= (TextView) findViewById(R.id.title_text);
        titleView.setText("工作日志查询");
        pref= this.getSharedPreferences("userInfo",MODE_PRIVATE);
        account=pref.getString("account","");
        list= new ArrayList<ApplyInfo>();
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
                Intent intent = new Intent(WorkJournalQueryActivity.this, WorkJournalDetail.class);
                intent.putExtra("journal_title", applyInfo.getTitle());
                intent.putExtra("journal_content", applyInfo.getContent());
                intent.putExtra("record_date", applyInfo.getLoan_date());
                intent.putExtra("post_name", applyInfo.getBill_flag());
                startActivity(intent);
            }
        });
        initInfo();
    }
        private void initInfo() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String info= WebService.getQueryInfo(account,count);
                    JSONArray jsonArray;
                    JSONObject jsonObject;
                    if(info.equals("0")){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                leadMore.setVisibility(View.INVISIBLE);
                                Toast.makeText(WorkJournalQueryActivity.this, "已经到最后了！", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }if(info.equals("-1")){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                leadMore.setVisibility(View.INVISIBLE);
                                Toast.makeText(WorkJournalQueryActivity.this, "请检查网络连接，稍后重试！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (info.startsWith("err")){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                leadMore.setVisibility(View.INVISIBLE);
                                Toast.makeText(WorkJournalQueryActivity.this, info, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (!TextUtils.isEmpty(info) && !info.equals("fail")) {
                        try {
                            jsonArray = new JSONArray(info);
                            int len = jsonArray.length();
                            for (int i = 0; i < len; i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String journal_title= jsonObject.getString("journal_title");
                                String journal_content = jsonObject.getString("journal_content");
                                String record_date = jsonObject.getString("record_date");
                                record_date=record_date.split(" ")[0];
                                String post_name= jsonObject.getString("post_name");
                                ApplyInfo applyInfo = new ApplyInfo();
                                applyInfo.setTitle(journal_title);
                                applyInfo.setContent(journal_content);
                                applyInfo.setLoan_date(record_date);
                                applyInfo.setBill_flag(post_name);
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

                }
            }).start();

        }

}
