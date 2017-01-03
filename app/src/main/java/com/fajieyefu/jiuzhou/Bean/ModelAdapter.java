package com.fajieyefu.jiuzhou.Bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.R;

import java.util.List;

/**
 * Created by qiancheng on 2016/10/27.
 */
public class ModelAdapter extends BaseAdapter {
	private Context mContext;
	private List<ModelInfo> mData;

	public ModelAdapter(Context context, List<ModelInfo> data) {
		mContext = context;
		mData = data;
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
		ModelInfo modelInfo = mData.get(position);
		convertView = LayoutInflater.from(mContext).inflate(R.layout.model_item, null);
		TextView textView = (TextView) convertView.findViewById(R.id.model_text);
		textView.setText(modelInfo.getModel_name());
		if (modelInfo.getChoose_flag() == 1) {
			textView.setBackgroundColor(mContext.getResources().getColor(R.color.headgreen));
		} else {
			textView.setBackgroundColor(mContext.getResources().getColor(R.color.azure));
		}
 		return convertView;
	}

}
