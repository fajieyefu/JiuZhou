package com.fajieyefu.jiuzhou.Bean;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.R;

import java.util.List;

/**
 * Created by qiancheng on 2016/11/1.
 */
public class ContractBrowseAdapter extends BaseAdapter {
	Context mContext;
	List<ContractBrowseBean> mData;

	public ContractBrowseAdapter(Context context, List<ContractBrowseBean> data) {
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
		ContractBrowseBean bean = mData.get(position);
		ContractBrowseBean bean_last;
		String last_parent = "";
		if (position != 0) {
			bean_last = mData.get(position - 1);
			last_parent = bean_last.getText_parent();
		}
		convertView = LayoutInflater.from(mContext).inflate(R.layout.contract_browse_item, null);
		TextView textName = (TextView) convertView.findViewById(R.id.textName);
		TextView textContent = (TextView) convertView.findViewById(R.id.textContent);
		TextView parentName = (TextView) convertView.findViewById(R.id.parent_name);
		textContent.setText(bean.getTextContent());
		textName.setText(bean.getTextName());
		String parent_name = bean.getText_parent();

		if (position!=0){
			parentName.setText(parent_name);
		}

		if (position != 0 && parent_name.equals(last_parent)) {
			parentName.setVisibility(View.GONE);
		}
		return convertView;
	}
}
