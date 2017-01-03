package com.fajieyefu.jiuzhou.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fajieyefu.jiuzhou.service.UpdateCheckNews;

/**
 * Created by Fajieyefu on 2016/8/29.
 */
public class AutoUpdateReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, UpdateCheckNews.class);
        context.startService(i);
    }
}
