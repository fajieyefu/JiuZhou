package com.fajieyefu.jiuzhou.Bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.R;

import java.util.List;

/**
 * Created by Fajieyefu on 2016/7/25.
 */
public class ApplyInfoAdapter2 extends ArrayAdapter {
	private  int resourceId;
	public ApplyInfoAdapter2(Context context, int resource, List objects) {
		super(context, resource, objects);
		resourceId=resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ApplyInfo applyInfo = (ApplyInfo) getItem(position);
		ViewHolder viewHolder;
		View view;
		if(convertView==null){
			view = LayoutInflater.from(getContext()).inflate(resourceId,null);
			viewHolder= new ViewHolder();
			viewHolder.contract_no= (TextView) view.findViewById(R.id.contract_no);
			viewHolder.cusm_name= (TextView) view.findViewById(R.id.cusm_name);
			viewHolder.audit_flag= (TextView) view.findViewById(R.id.audit_flag);
			viewHolder.order_num= (TextView) view.findViewById(R.id.order_num);
			view.setTag(viewHolder);//将viewholder存储在View中
		}else{
			view=convertView;
			viewHolder= (ViewHolder) view.getTag();//重新获取viewholder
		}

		viewHolder.contract_no.setText(applyInfo.getLoan_code());
		viewHolder.cusm_name.setText(applyInfo.getCusm_name());
		viewHolder.audit_flag.setText(applyInfo.getBill_flag());
		viewHolder.order_num.setText(position+1+"");
		return  view;
	}

	class ViewHolder {
		TextView order_num;
		TextView contract_no;
		TextView cusm_name;
		TextView audit_flag;
	}
}
