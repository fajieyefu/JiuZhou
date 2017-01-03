package com.fajieyefu.jiuzhou.Bean;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.Audit.ContractInputActivity2;
import com.fajieyefu.jiuzhou.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiancheng on 2016/10/27.
 */
public class ClassInfoAdapter extends BaseAdapter {
	private Context mContext;
	private List<ClassInfo> mData;
	private int measure_height;
	private int measure_width;

	public ClassInfoAdapter(Context context, List<ClassInfo> data) {
		mContext = context;
		mData = data;
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		measure_height=dm.heightPixels;
		measure_width=dm.widthPixels;


	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ClassInfo classInfo = mData.get(position);

		convertView = LayoutInflater.from(mContext).inflate(R.layout.classinfo_item, null);
		TextView className_Text = (TextView) convertView.findViewById(R.id.classInfo_name);
		final TextView modelInfo_View = (TextView) convertView.findViewById(R.id.modelInfo_view);
		className_Text.setText(classInfo.getClass_name());
		String model_code = ContractInputActivity2.map.get(classInfo.getClass_code());
		if (!TextUtils.isEmpty(model_code)) {
			String model_name = ContractInputActivity2.map_model.get(model_code);
			modelInfo_View.setText(model_name);
		}

		modelInfo_View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(mContext);
				View view = LayoutInflater.from(mContext).inflate(R.layout.model_dialog, null);
				final List<ModelInfo> modelInfo_lv = classInfo.getModelInfo();
				Log.i("size", modelInfo_lv.size() + "");
				ModelAdapter modelAdapter = new ModelAdapter(mContext, modelInfo_lv);
				ListView listView = (ListView) view.findViewById(R.id.model_lv);
				listView.setAdapter(modelAdapter);
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						if (dialog != null) {
							dialog.dismiss();
						}
						int i = 0;
						while (i < modelInfo_lv.size()) {
							modelInfo_lv.get(i).setChoose_flag(0);
							i++;
						}
						ContractInputActivity2.map.put(classInfo.getClass_code(), modelInfo_lv.get(position).getModel_code());
						modelInfo_lv.get(position).setChoose_flag(1);
						modelInfo_View.setText(modelInfo_lv.get(position).getModel_name());
					}
				});
				dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				dialog.show();
			}
		});
		return convertView;
	}


}
