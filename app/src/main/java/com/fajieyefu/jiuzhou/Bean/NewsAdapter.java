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
 * Created by Fajieyefu on 2016/7/11.
 */
public class NewsAdapter  extends ArrayAdapter{
    private int resourceId;

    public NewsAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news=(News)getItem(position);
        ViewHolder viewHolder;
        View view;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder= new ViewHolder();
            viewHolder.news_title= (TextView) view.findViewById(R.id.news_title);
            viewHolder.news_time= (TextView) view.findViewById(R.id.news_time);
            view.setTag(viewHolder);//将viewholder存储在View中
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();//重新获取viewholder
        }

        viewHolder.news_title.setText(news.getTitle());
        viewHolder.news_time.setText(news.getNewsTime());
        return  view;

    }
    class ViewHolder{
        TextView news_title;
        TextView news_time;
    }

}
