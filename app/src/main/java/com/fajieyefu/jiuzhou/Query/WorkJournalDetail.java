package com.fajieyefu.jiuzhou.Query;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.R;

/**
 * Created by Fajieyefu on 2016/8/15.
 */
public class WorkJournalDetail extends BaseActivity {
    private TextView use_man_text,depart_name_text,post_name_text,record_date_text,title_text,content_text;
    private SharedPreferences pref;
    private TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.work_journal_detail);
        pref=WorkJournalDetail.this.getSharedPreferences("userInfo",MODE_PRIVATE);
        String username=pref.getString("username","");
        String depart_name=pref.getString("depart_name","");
        Intent intent =getIntent();
        String journal_title=intent.getStringExtra("journal_title");
        String journal_content=intent.getStringExtra("journal_content");
        String record_date=intent.getStringExtra("record_date");
        String post_name=intent.getStringExtra("post_name");
        initWeight();//初始化控件
        titleText.setText("日志查询详情");
        use_man_text.setText(username);
        depart_name_text.setText(depart_name);
        post_name_text.setText(post_name);
        record_date_text.setText(record_date);
        title_text.setText(journal_title);
        content_text.setText(journal_content);
    }

    private void initWeight() {
        titleText= (TextView) findViewById(R.id.title_text);
        use_man_text= (TextView) findViewById(R.id.use_man_name);
        depart_name_text= (TextView) findViewById(R.id.depart_name);
        post_name_text= (TextView) findViewById(R.id.post_name);
        record_date_text= (TextView) findViewById(R.id.record_date);
        title_text= (TextView) findViewById(R.id.title);
        content_text= (TextView) findViewById(R.id.content);

    }
}
