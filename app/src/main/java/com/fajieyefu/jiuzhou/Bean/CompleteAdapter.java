package com.fajieyefu.jiuzhou.Bean;

import android.content.Context;
import android.icu.util.ValueIterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiancheng on 2016/12/15.
 */
public class CompleteAdapter extends BaseAdapter {
	private Context context;
	private List<CompleteCarBean> mData;

	public CompleteAdapter(Context context, List<CompleteCarBean> data) {
		this.context = context;
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
		CompleteCarBean completeCarBean = mData.get(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.completebymonth_item, null);
			viewHolder  = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else{
			viewHolder= (ViewHolder) convertView.getTag();
		}
		viewHolder.chuche.setText(completeCarBean.getChuche_num());
		viewHolder.contractCar.setText(completeCarBean.getContract_car_num());
		viewHolder.date.setText(completeCarBean.getDate());
		viewHolder.penqi.setText(completeCarBean.getPenqi_num());
		viewHolder.sendCar.setText(completeCarBean.getFache_num());
		return convertView;
	}

	static class ViewHolder {
		@BindView(R.id.date)
		TextView date;
		@BindView(R.id.chuche)
		TextView chuche;
		@BindView(R.id.penqi)
		TextView penqi;
		@BindView(R.id.send_car)
		TextView sendCar;
		@BindView(R.id.contract_car)
		TextView contractCar;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
