package com.loan.time;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class App extends Application {

    //全局上下文
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    /**
     * 获取APP当前版本号
     * @param context
     * @return
     */
    public static String getAppVersion(Context context){
        PackageManager packageManager=context.getPackageManager();
        String version=null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
