package com.loan.time;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.android.tony.defenselib.DefenseCrash;
import com.android.tony.defenselib.handler.IExceptionHandler;
import com.loan.time.utils.PreferenceUtil;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import goutil.Goutil;

public class App extends Application /*implements IExceptionHandler*/ {

    //全局上下文
    public static Context context;
    public static String DeviceId="deviceId";
    public static String DeviceToken="DeviceToken";
    public static String Uid="UID";
    public static String Token="Token";
    public static String Phone="Phone";
    public static String IsAuthority="IsAuthority";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        PreferenceUtil.init(this);
        ScreenAdapterTools.init(this);

        /* // step1: 初始化库
        DefenseCrash.initialize();
        // setp2: 安装防火墙
        DefenseCrash.install(this);*/
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
     * 判断App是否更新
     *0代表相等，1代表version1大于version2，-1代表version1小于version2
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

/*    @Override
    public void onCaughtException(Thread thread, Throwable throwable, boolean b) {
        // step3: 打印错误堆栈
        throwable.printStackTrace();
        // step4: 上报该错误到错误收集的平台,例如国内的Bugly,友盟等
    }

    @Override
    public void onEnterSafeMode() {
        // 框架使程序运行进入了安全模式,这种情况是在程序运行过程中发生了崩溃,
        // 但是不要慌,已经被框架拦截并且进入了安全模式,这里你可以什么都不用做.
    }

    @Override
    public void onMayBeBlackScreen(Throwable throwable) {
        // 这个回调说明在onLayout(),onMeasure() 和 onDraw()中出现了崩溃
        // 这种崩溃会导致绘图异常和编舞者类崩溃
        // 我们建议你在这时候,重启当前的activity或者应用
    }*/
}
