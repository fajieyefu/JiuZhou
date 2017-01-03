package com.fajieyefu.jiuzhou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.News;
import com.fajieyefu.jiuzhou.Bean.NewsAdapter;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fajieyefu on 2016/7/11.
 */
public class NewsActivity extends BaseActivity {
    private Handler handler = new Handler();
    private ListView listView;
    private Button leadMore;
    private List<News> list = new ArrayList();
    private NewsAdapter newsAdapter;
    private int count = 1;
    private String info;
    private TextView title;
    private ImageView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.news);
        title= (TextView) findViewById(R.id.title_text);
        title.setText("消息通知");
        View bottomView = getLayoutInflater().inflate(R.layout.bottom, null);
        leadMore = (Button) bottomView.findViewById(R.id.leadMore);
        emptyView= (ImageView) findViewById(R.id.empty);
        newsAdapter = new NewsAdapter(NewsActivity.this, R.layout.news_item_first, list);
        listView = (ListView) findViewById(R.id.total_news_listview);
        listView.setEmptyView(emptyView);
        listView.addFooterView(bottomView);
        listView.setAdapter(newsAdapter);


        leadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initNews();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news = list.get(i);//通过位置获取；
                Intent intent = new Intent(NewsActivity.this, NewsDetail.class);
                intent.putExtra("title", news.getTitle());
                intent.putExtra("content", news.getContent());
                intent.putExtra("fb_person", news.getFb_person());
                intent.putExtra("fb_date", news.getNewsTime());
                startActivity(intent);

            }
        });
        initNews();

    }

    private void initNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                info = WebService.executeHttpGetNews(count);
                if (!TextUtils.isEmpty(info) && !info.equals("fail")) {
                    try {
                        String title, fb_date, fb_person, content;
                        JSONArray jsonArray = new JSONArray(info);
                        JSONObject jsonObject;
                        int len = jsonArray.length();
                        for (int i = 1; i < len; i++) {
                            jsonObject = jsonArray.getJSONObject(i - 1);
                            title = jsonObject.getString("title");
                            content = jsonObject.getString("content");
                            fb_date = jsonObject.getString("fb_date");
                            fb_date=fb_date.split(" ")[0];
                            fb_person = jsonObject.getString("fb_man");
                            News news = new News(title, content, fb_date, fb_person, null);
                            list.add(news);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            newsAdapter.notifyDataSetChanged();
                            listView.setSelection(list.size() - 1);
                        }
                    });
                    count++;
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            leadMore.setText("往下没有数据了");
                            leadMore.setEnabled(false);
                            Toast.makeText(NewsActivity.this, "已经到最后了！", Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        }).start();

    }
}
