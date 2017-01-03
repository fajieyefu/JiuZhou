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
 * Created by qiancheng on 2016/9/30.
 */
public class PopupAdapter extends BaseAdapter {
	private List<String> mData;
	private Context mCnontext;
	public PopupAdapter(Context context, List<String> list) {
		mData=list;
		mCnontext=context;
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
		String text=mData.get(position);
		LayoutInflater inflater=LayoutInflater.from(mCnontext);
		View view =inflater.inflate(R.layout.popup_item,null);
		TextView content= (TextView) view.findViewById(R.id.content);
		content.setText(text);
		return view;
	}
}
