package com.fajieyefu.jiuzhou.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.fajieyefu.jiuzhou.Bean.WebService;
import com.fajieyefu.jiuzhou.Check.Check;
import com.fajieyefu.jiuzhou.R;
import com.fajieyefu.jiuzhou.receiver.AutoUpdateReceiver;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Fajieyefu on 2016/8/29.
 */
public class UpdateCheckNews extends Service {
	private String account;
	private String departcode;
	private String type="全部";
	private int count=1;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private int last_check_id;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		pref=UpdateCheckNews.this.getSharedPreferences("userInfo",MODE_PRIVATE);
		editor= pref.edit();
		account=pref.getString("account","");
		departcode=pref.getString("depart_code","");
		last_check_id=pref.getInt("last_check_id",0);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				updatePic();
			}
		}).start();
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = 2 * 60 * 1000;//这是2分钟的时间
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this, AutoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 更新照片信息
	 */
	private void updatePic() {
		String info = WebService.getCheckInfo(account, departcode, type, count);
		try {
			JSONArray jsonArray = new JSONArray(info);
			int current_id =jsonArray.getJSONObject(0).getInt("id");
			Log.i("current_id:", current_id + "");
			Log.i("last_id:", last_check_id + "");
			if (current_id > last_check_id) {
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				Intent intent = new Intent(UpdateCheckNews.this, Check.class);
				PendingIntent contentIntent = PendingIntent.getActivity(UpdateCheckNews.this, 1, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
				builder.setTicker("消息提示");
				builder.setContentTitle("九州汽车");
				builder.setContentText("新增消息需要审批");
				builder.setSmallIcon(R.drawable.logo);
				builder.setContentIntent(contentIntent);
				Notification notification = builder.build();
				notification.flags = Notification.FLAG_AUTO_CANCEL;
				notification.ledARGB = Color.BLUE;
				notification.ledOnMS = 1000;
				notification.ledOffMS = 1000;
				notification.flags |= Notification.FLAG_SHOW_LIGHTS;
				notification.defaults = Notification.DEFAULT_SOUND;
				long[] vibrates = {0, 500, 500, 500};
				notification.vibrate = vibrates;
				notificationManager.notify(1, notification);
				editor.putInt("last_check_id",current_id);
				editor.apply();
			}
		} catch (Exception e) {

		}


	}
}
