package com.fajieyefu.jiuzhou.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fajieyefu.jiuzhou.Bean.ActivityCollector;
import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.Bean.News;
import com.fajieyefu.jiuzhou.Bean.NewsAdapter;
import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.Check.Check;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.service.UpdateCheckNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements View.OnClickListener {
	private LinearLayout newsLayout;
	private ImageView baobiaoLayout, yewuLayout, shenheLayout;
	private ListView news_listview, baobiao_listview, yewu_listview, shenpi_listview;
	private List<News> newsList = new ArrayList<News>();
	private String info;
	private NewsAdapter newsAdapter;
	private Handler handler = new Handler();
	private Button signOut;
	private TextView accountTextView;
	private SharedPreferences pref;
	private String account, username;
	private boolean doubleBackToExitPressedOnce = false;
	private DrawerLayout drawerLayout;
	private ImageView emptyView;
	private ImageButton mail;
	private Button modifyPsw;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		newsAdapter = new NewsAdapter(MainActivity.this, R.layout.news_item_first, newsList);
		initNews();//初始化数据。
		pref = MainActivity.this.getSharedPreferences("userInfo", MODE_PRIVATE);
		account = pref.getString("account", "");
		username = pref.getString("username", "");
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_Layout);
		drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {

			}

			@Override
			public void onDrawerOpened(View drawerView) {
				drawerView.setClickable(true);
			}

			@Override
			public void onDrawerClosed(View drawerView) {

			}

			@Override
			public void onDrawerStateChanged(int newState) {

			}
		});
		modifyPsw = (Button) findViewById(R.id.modify_psw);
		emptyView = (ImageView) findViewById(R.id.empty);
		newsLayout = (LinearLayout) findViewById(R.id.news);
		baobiaoLayout = (ImageView) findViewById(R.id.baobiao);
		yewuLayout = (ImageView) findViewById(R.id.yewu_manage);
		shenheLayout = (ImageView) findViewById(R.id.shenpi_manage);
		news_listview = (ListView) findViewById(R.id.news_listview);
		news_listview.setEmptyView(emptyView);
		accountTextView = (TextView) findViewById(R.id.account_text);
		accountTextView.setText(username);
		signOut = (Button) findViewById(R.id.sign_out);
		mail = (ImageButton) findViewById(R.id.mail);
		signOut.setOnClickListener(this);

		mail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MailActivity.class);
				startActivity(intent);
			}
		});
		news_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				News news = newsList.get(i);//通过位置获取；
				Intent intent = new Intent(MainActivity.this, NewsDetail.class);
				intent.putExtra("title", news.getTitle());
				intent.putExtra("content", news.getContent());
				intent.putExtra("fb_person", news.getFb_person());
				intent.putExtra("fb_date", news.getNewsTime());
				startActivity(intent);
			}
		});
		newsLayout.setOnClickListener(this);
		baobiaoLayout.setOnClickListener(this);
		yewuLayout.setOnClickListener(this);
		shenheLayout.setOnClickListener(this);
		modifyPsw.setOnClickListener(this);
		TimerTask task = new TimerTask() {
			public void run() {
				Intent intent = new Intent(MainActivity.this, UpdateCheckNews.class);
				startService(intent);
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 3000);
	}

	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			finish();
			System.exit(0);
			return;
		}
		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(MainActivity.this, "再点击一次退出程序", Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);

	}

	private void initNews() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				info = WebService.executeHttpGetNews(1);
				if (!TextUtils.isEmpty(info)) {
					try {
						String title, fb_date, fb_person, content;
						JSONArray jsonArray = new JSONArray(info);
						JSONObject jsonObject;
						int len = jsonArray.length();
						for (int i = 1; i < len; i++) {
							jsonObject = jsonArray.getJSONObject(i - 1);
							title = jsonObject.getString("title");
							fb_date = jsonObject.getString("fb_date");
							fb_date = fb_date.split(" ")[0];
							fb_person = jsonObject.getString("fb_man");
							content = jsonObject.getString("content");
							News news = new News(title, content, fb_date, fb_person, null);
							newsList.add(news);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						news_listview.setAdapter(newsAdapter);
					}
				});


			}
		}).start();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.news:
				Intent intent_news = new Intent(MainActivity.this, NewsActivity.class);
				startActivity(intent_news);
				break;
			case R.id.baobiao:
				Intent intent_baobiao = new Intent(MainActivity.this, BaobiaoActivity.class);
				startActivity(intent_baobiao);
				break;
			case R.id.yewu_manage:
				Intent intent_yewu = new Intent(MainActivity.this, YewuActivity.class);
				startActivity(intent_yewu);
				break;
			case R.id.shenpi_manage:
				Intent intent_shenpi = new Intent(MainActivity.this, Check.class);
				startActivity(intent_shenpi);
				break;
			case R.id.sign_out:
				Intent intent_sign_out = new Intent(MainActivity.this, LoginActivity.class);
				ActivityCollector.finishAll();
				startActivity(intent_sign_out);
				break;
			case R.id.modify_psw:
				Intent intent = new Intent(this, ModifyPswActivity.class);
				startActivity(intent);
				break;

		}
	}
}
