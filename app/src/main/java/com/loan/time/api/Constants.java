package com.loan.time.api;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.loan.time.App;
import com.loan.time.BuildConfig;


/**
 * 接口
 *
 * @author renji
 * @date 2017/11/23
 */

public class Constants {
    //"https://openapi.juxinda360.cn/gateway/gateway/"
    public static final String HOST_URL = BuildConfig.BASE_URL;
    //包名
    public static final String PaackageName=BuildConfig.APPLICATION_ID;
    //渠道
    public static final String ChannelKey="16";
    //平台
    public static final String Platform="android";
    //版本名称
    public static final String VersionKey= App.getAppVersion();
    //版本号
    public static final String VersionCode=App.getVersionCode();


}