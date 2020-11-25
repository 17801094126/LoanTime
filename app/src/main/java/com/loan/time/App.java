package com.loan.time;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import goutil.Goutil;

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
     * @return
     */
    public static String getAppVersion(){
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

    /**
     * get App versionCode
     * @return
     */
    public static String getVersionCode(){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

}
