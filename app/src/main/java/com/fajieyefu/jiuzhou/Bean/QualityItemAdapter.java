package com.fajieyefu.jiuzhou.Bean;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fajieyefu.jiuzhou.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Fajieyefu on 2016/8/11.
 */
public class QualityItemAdapter extends ArrayAdapter{
    private  int resourceId;
    private List<QualityInfo> mData;
    public Map<String, String> editorValue = new HashMap<String, String>();
    public QualityItemAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        mData=objects;
        resourceId=resource;
        init();
    }

    private void init() {
        editorValue.clear();
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
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final QualityInfo qualityInfo = (QualityInfo) getItem(position);
        final ViewHolder viewHolder;
        View view;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder= new ViewHolder();
            viewHolder.xuhaoText= (TextView) view.findViewById(R.id.xuhao);
            viewHolder.item_desc_Text= (TextView) view.findViewById(R.id.item_desc);
            viewHolder.item_ok_Text=(TextView) view.findViewById(R.id.item_ok);
            viewHolder.item_result_Group= (RadioGroup) view.findViewById(R.id.item_result);
            viewHolder.item_a_Button=(RadioButton) view.findViewById(R.id.item_a);
            viewHolder.item_b_Button=(RadioButton) view.findViewById(R.id.item_b);
            viewHolder.item_remark_Edit= (EditText) view.findViewById(R.id.item_remark);
            viewHolder.item_remark_Edit.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                        QualityInfo qualityInfo2= (QualityInfo) viewHolder.item_remark_Edit.getTag();
                        qualityInfo2.setEditString(editable.toString());
                }
            });

            view.setTag(viewHolder);//将viewholder存储在View中
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.xuhaoText.setText(position+1+"");
        viewHolder.item_desc_Text.setText(qualityInfo.getItem_desc());
        viewHolder.item_ok_Text.setText(qualityInfo.getItem_ok());
        viewHolder.item_a_Button.setText(qualityInfo.getItem_a());
        viewHolder.item_b_Button.setText(qualityInfo.getItem_b());


        viewHolder.item_remark_Edit.setTag(qualityInfo);
        viewHolder.item_remark_Edit.clearFocus();//清除焦点
        viewHolder.item_result_Group.setId(position);
        viewHolder.item_result_Group.setOnCheckedChangeListener(null);
        if(qualityInfo.getItem_a().equals(qualityInfo.getItem_result())){
            viewHolder.item_result_Group.check(viewHolder.item_a_Button.getId());
        }else if (qualityInfo.getItem_b().equals(qualityInfo.getItem_result())){
            viewHolder.item_result_Group.check(viewHolder.item_b_Button.getId());
        }else{
            viewHolder.item_result_Group.clearCheck();
        }
        if (!TextUtils.isEmpty(qualityInfo.getEditString())){
            viewHolder.item_remark_Edit.setText(qualityInfo.getEditString());
        }else{
            viewHolder.item_remark_Edit.setText("");
        }
        viewHolder.item_result_Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                QualityInfo qualityInfo1 = mData.get(radioGroup.getId());
                if (qualityInfo1 == null) return;
                switch(checkId){
                    case R.id.item_a:
                        qualityInfo1.setItem_result(qualityInfo1.getItem_a());
                        break;
                    case R.id.item_b:
                        qualityInfo1.setItem_result(qualityInfo1.getItem_b());
                        break;
                }
            }
        });
        viewHolder.item_result_Group.check(R.id.item_a);

        return  view;
    }

    class ViewHolder {
        TextView xuhaoText;
        TextView item_desc_Text;
        TextView item_ok_Text;
        RadioGroup item_result_Group;
        RadioButton item_a_Button;
        RadioButton item_b_Button;
        EditText item_remark_Edit;
    }
}
