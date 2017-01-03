package com.fajieyefu.jiuzhou.Audit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.fajieyefu.jiuzhou.Query.PaiChanList;
import com.fajieyefu.jiuzhou.R;

/**
 * Created by qiancheng on 2016/10/28.
 */
public class ContractChooseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contract_choose_layout);
		Button query_confrim = (Button) findViewById(R.id.ready_confrim);
		Button add_contract= (Button) findViewById(R.id.add_contract);
		Button paichan = (Button) findViewById(R.id.paichan);
		paichan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ContractChooseActivity.this, PaiChanList.class);
				startActivity(intent);
			}
		});

		add_contract.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ContractChooseActivity.this,ContractInputActivity2.class);
				startActivity(intent);
			}
		});
		query_confrim.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent= new Intent(ContractChooseActivity.this,ContractList.class);
				startActivity(intent);
			}
		});
	}
}
