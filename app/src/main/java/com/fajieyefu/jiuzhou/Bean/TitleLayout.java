package com.fajieyefu.jiuzhou.Bean;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.R;

/**
 * Created by Fajieyefu on 2016/7/26.
 */
public class TitleLayout extends RelativeLayout {
	private TextView titleText;

	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.backtitle, this);
		ImageButton back = (ImageButton) findViewById(R.id.back);
		titleText = (TextView) findViewById(R.id.title_text);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				((Activity) getContext()).finish();
			}
		});
	}

	public void setTitleText(String text) {
		titleText.setText(text);

	}

}
