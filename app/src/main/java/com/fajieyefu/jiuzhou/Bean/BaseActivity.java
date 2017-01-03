package com.fajieyefu.jiuzhou.Bean;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Fajieyefu on 2016/7/27.
 */
public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ActivityCollector.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
}
