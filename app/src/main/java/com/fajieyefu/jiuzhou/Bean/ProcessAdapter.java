package com.fajieyefu.jiuzhou.Bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.R;

import java.util.List;

/**
 * Created by qiancheng on 2016/10/27.
 */
public class ProcessAdapter extends BaseAdapter {
	private Context mContext;
	private List<ProcessInfo> mData;

	public ProcessAdapter(Context context, List<ProcessInfo> data) {
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
		final ProcessInfo processInfo = mData.get(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder= new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.contract_process_item, null);
			viewHolder.processName= (TextView) convertView.findViewById(R.id.process_text);
			viewHolder.next= (Button) convertView.findViewById(R.id.process_next);
			convertView.setTag(viewHolder);
		}else{
			viewHolder= (ViewHolder) convertView.getTag();
		}
		viewHolder.processName.setText(processInfo.getProcess_name());
		viewHolder.next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ClassDialog classDialog = new ClassDialog(mContext,processInfo);
				classDialog.show();
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView processName;
		Button next;
	}
}
