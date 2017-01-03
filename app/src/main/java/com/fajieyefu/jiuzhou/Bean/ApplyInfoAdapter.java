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
public class ApplyInfoAdapter extends ArrayAdapter {
    private  int resourceId;
    public ApplyInfoAdapter(Context context, int resource, List objects) {
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
            viewHolder.infoTitle= (TextView) view.findViewById(R.id.news_title);
            viewHolder.infoNewsTime= (TextView) view.findViewById(R.id.news_time);
            view.setTag(viewHolder);//将viewholder存储在View中
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();//重新获取viewholder
        }

        viewHolder.infoTitle.setText(applyInfo.getTitle());
        viewHolder.infoNewsTime.setText(applyInfo.getLoan_date());
        return  view;
    }

     class ViewHolder {
        TextView infoTitle;
        TextView infoNewsTime;
    }
}
