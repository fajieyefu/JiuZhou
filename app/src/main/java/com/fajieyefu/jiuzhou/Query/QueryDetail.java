package com.fajieyefu.jiuzhou.Query;

import android.os.Bundle;
import android.view.Window;

import com.fajieyefu.jiuzhou.Bean.BaseActivity;
import com.fajieyefu.jiuzhou.R;

/**
 * Created by Fajieyefu on 2016/7/30.
 */
public class QueryDetail extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reimbursement_query_detail);
    }
}
