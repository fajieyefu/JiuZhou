package com.fajieyefu.jiuzhou.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.R;

/**
 * Created by qiancheng on 2016/11/8.
 */
public class MailActivity extends BaseActivity {
	private Button new_mail, inbox, sending_box;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mail_layout);
		new_mail = (Button) findViewById(R.id.new_mail);
		inbox = (Button) findViewById(R.id.inbox);
		sending_box = (Button) findViewById(R.id.sending_box);

	}
}
