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
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loan.time.App;
import com.loan.time.R;
import com.loan.time.bean.RequestBean;
import com.loan.time.utils.ActivityCollector;
import com.loan.time.utils.AppUtils;
import com.loan.time.utils.PreferenceUtil;
import com.loan.time.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        String activityurl = intent.getStringExtra(WebUrl);

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
                Uri parse = Uri.parse(url);
                String scheme = parse.getScheme();
                String host = parse.getHost();
                if (url.endsWith("apk")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, parse);
                    startActivity(intent);
                    WebActivity.this.finish();
                }else if ("play.google.com".equals(host)){
                    Intent intent = new Intent(Intent.ACTION_VIEW, parse);
                    startActivity(intent);
                    WebActivity.this.finish();
                }

                //拦截H5URL   判断Schema
                if ("wode-schema".equals(scheme)){
                    //判断不同Host
                    if ("closeNewWebview".equals(host)){
                        ToastUtils.showToast(WebActivity.this,"Close WebView!");
                        WebActivity.this.finish();
                    }else if ("jumpUrlOuter".equals(host)){
                            try {
                                String data = parse.getQueryParameter("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String decoderString = URLDecoder.decode(jsonObject.getString("url"),"UTF-8");
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(decoderString));
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }catch (NullPointerException e){
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                    }else if ("appInfo".equals(host)){
                        String callback = parse.getQueryParameter("callback");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Log.e(TAG, getJson());
                            // 只需要将第一种方法的loadUrl()换成下面该方法即可
                            web.evaluateJavascript("javascript:"+callback+"(" + getJson() + ")", value -> {
                                //此处为 js 返回的结果
                                Log.e(TAG, value);
                            });
                        } else {
                            web.loadUrl("javascript:"+callback+"(" +getJson() + ")");
                        }
                    }

                }
                return true;

            }
        });
        web.loadUrl(activityurl);
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
        requestBean.setTerminal_name("LoanTime");
        requestBean.setToken(PreferenceUtil.getString(App.Token,""));
        requestBean.setDeviceId(PreferenceUtil.getString(App.DeviceId,""));
        RequestBean.DataBean dataBean = initDeviceInfo(this);
        requestBean.setDeviceInfo(new Gson().toJson(dataBean));
        String s = new Gson().toJson(requestBean);
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
