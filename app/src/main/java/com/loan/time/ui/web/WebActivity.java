package com.loan.time.ui.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.loan.time.App;
import com.loan.time.R;
import com.loan.time.api.Constants;
import com.loan.time.bean.RequestBean;
import com.loan.time.mvp.MVPBaseActivity;
import com.loan.time.utils.ActivityCollector;
import com.loan.time.utils.AppUtils;
import com.loan.time.utils.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends AppCompatActivity {

    @BindView(R.id.web)
    WebView web;
    public static String WebUrl = "WebUrl";
    public static final String TAG="WebActivity";

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceUtil.init(this);
        setContentView(R.layout.activity_web);

        App.context=this;
        //绑定ButterKnifet
        ButterKnife.bind(this);
        initData();


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityCollector.addActivity(this);
    }

    private void initData() {

        Intent intent = getIntent();
        String url = intent.getStringExtra(WebUrl);

        WebSettings webSettings = web.getSettings();
        //设置支持javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //web.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG,url);
                String[] substring = url.split("callback=");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e(TAG, concat(getJson()));
                    // 只需要将第一种方法的loadUrl()换成下面该方法即可
                    web.evaluateJavascript("javascript:"+substring[1]+"(" + concat(getJson()) + ")", value -> {
                        //此处为 js 返回的结果
                        Log.e(TAG, value);
                    });
                } else {
                    web.loadUrl("javascript:"+substring[1]+"(" + concat(getJson()) + ")");
                }
                return false;

            }
        });
        web.loadUrl(url);
        web.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

            }
        });
    }


    private String concat(String... params) {
        StringBuilder mStringBuilder = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            String param = params[i];
            if (!isJson(param)) {
                mStringBuilder.append("\"").append(param).append("\"");
            } else {
                mStringBuilder.append(param);
            }
            if (i != params.length - 1) {
                mStringBuilder.append(" , ");
            }
        }
        return mStringBuilder.toString();
    }

    static boolean isJson(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        }
        boolean tag = false;
        try {
            if (target.startsWith("[")) {
                new JSONArray(target);
            } else {
                new JSONObject(target);
            }
            tag = true;
        } catch (JSONException ignore) {
//            ignore.printStackTrace();
            tag = false;
        }
        return tag;
    }

    /**
     * $ 代表{
     * % 代表"
     * * 代表}
     */
    private String getJson() {
        RequestBean requestBean = new RequestBean();
        requestBean.setDeviceToken(PreferenceUtil.getString(App.DeviceToken,""));
        requestBean.setUid(PreferenceUtil.getString(App.Uid,""));
        requestBean.setTerminal_name(String.valueOf(R.string.app_name));
        requestBean.setToken(PreferenceUtil.getString(App.Token,""));
        requestBean.setDeviceId(PreferenceUtil.getString(App.DeviceId,""));
        requestBean.setDeviceInfo(initDeviceInfo(this));
        String s = new Gson().toJson(requestBean);
        s.replaceAll("\\{","$");
        s.replaceAll("\\}","*");
        s.replaceAll("\\\"","%");
        /*String user = App.getUserPhone();//手机号
        String applicantName = PreferenceUtil.getString("applicantName", null);//姓名
        String cardId = PreferenceUtil.getString("cardId", null);//身份证号
        StringBuffer sb = new StringBuffer();
        sb.append("$");
        sb.append("%name%:%").append(applicantName).append("%,%card%:%")
                .append(cardId).append("%,%phone%:%").append(user)
                .append("%,%institutionCode%:%").append("369571").append("%,%password%:%")
                .append("sus3rrzie2dut6d").append("%,%className%:%").append(App.context.getPackageName())
                .append("%,%channelId%:%01%*");*/
        return s;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
            web.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置App基本信息
     * @param context
     */
    private RequestBean.DataBean initDeviceInfo(Context context) {
        RequestBean.DataBean dataBean = new RequestBean.DataBean();
        //设备的软件版本号
        dataBean.setDeviceSoftwareVersion(AppUtils.getDeviceSoftwareVersion(context));
        //设置IMEI号
        dataBean.setImei(AppUtils.getIMEI(context));
        //设置IMSI号
        dataBean.setImsi(AppUtils.getIMSI(context));
        //设置系统语言
        dataBean.setLanguage(AppUtils.getLanguage(context));
        dataBean.setDisplay(AppUtils.getDisplay(context));
        dataBean.setNetworkOperator(AppUtils.getNetworkOperator(context));
        dataBean.setNetworkOperatorName(AppUtils.getNetworkOperatorName(context));
        dataBean.setNetworkType(AppUtils.getNetworkType(context));
        dataBean.setTotalMemory(AppUtils.getAllMemory());
        dataBean.setGsmCellLocation(AppUtils.getGsmCellLocation(context));
        if (TextUtils.isEmpty(AppUtils.getUUID(context))){
            dataBean.setUuid(AppUtils.getAndroidId(context));
        }else{
            dataBean.setUuid(AppUtils.getUUID(context));
        }
        return dataBean;
    }

}
