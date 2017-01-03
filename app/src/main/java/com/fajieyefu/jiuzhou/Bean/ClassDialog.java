package com.fajieyefu.jiuzhou.Bean;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.fajieyefu.jiuzhou.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiancheng on 2016/10/27.
 */
public class ClassDialog extends Dialog {
	ProcessInfo processInfo;
	Context mContext;
	private ListView listView;
	private List<ClassInfo>  classInfo_list = new ArrayList<>();
	int measure_height;
	int measure_width;
	public ClassDialog(Context context,ProcessInfo processInfo){
		super(context);
		this.processInfo=processInfo;
		mContext=context;
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		measure_height=dm.heightPixels;
		measure_width=dm.widthPixels;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(mContext).inflate(R.layout.classinfo_dialog,null);
		setContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		setTitle(processInfo.getProcess_name());
		classInfo_list.addAll(processInfo.getClassInfoList());
		ClassInfoAdapter classInfoAdapter =new ClassInfoAdapter(mContext,classInfo_list);
		listView= (ListView) findViewById(R.id.classInfo_lv);
		listView.setAdapter(classInfoAdapter);


	}
}
