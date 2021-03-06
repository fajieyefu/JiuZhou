package com.fajieyefu.jiuzhou.Bean;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fajieyefu on 2016/7/27.
 */
public class ActivityCollector {
    public static List<Activity> activities= new ArrayList<Activity>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static  void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
