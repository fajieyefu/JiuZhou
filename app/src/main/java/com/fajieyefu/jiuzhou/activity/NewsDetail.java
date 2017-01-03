package com.fajieyefu.jiuzhou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.R;

/**
 * Created by Fajieyefu on 2016/7/13.
 */
public class NewsDetail extends BaseActivity {
    private TextView titleText,contentText,fb_personText,fb_dateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.newsdetail);
        Intent intent= getIntent();
        String title = intent.getStringExtra("title");
        String content = "     "+intent.getStringExtra("content");
        String fb_person = intent.getStringExtra("fb_person");
        String fb_date = intent.getStringExtra("fb_date");
        titleText= (TextView) findViewById(R.id.title_text);
        contentText= (TextView) findViewById(R.id.detail_content);
        fb_personText= (TextView) findViewById(R.id.detail_fb_person);
        fb_dateText= (TextView) findViewById(R.id.detail_fb_time);
        titleText.setText(title);
        contentText.setText(content);
        fb_personText.setText(fb_person);
        fb_dateText.setText(fb_date);
    }
}
