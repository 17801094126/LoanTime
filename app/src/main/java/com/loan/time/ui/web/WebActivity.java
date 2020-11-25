package com.loan.time.ui.web;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.loan.time.R;
import com.loan.time.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends MVPBaseActivity {

    @BindView(R.id.tool_Bar)
    Toolbar toolbarRealName;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.finish)
    ImageView finish;
    @BindView(R.id.title)
    TextView title;
    public static String WebUrl = "WebUrl";
    public static String WebFlag = "WebFlag";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= 23) {
            ImmersionBar.with(this)
                    .statusBarDarkFont(false).init();
        }
        toolbarRealName.setPadding(0, getHeight(), 0, 0);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        String flag = intent.getStringExtra(WebFlag);
        String url = intent.getStringExtra(WebUrl);
        WebSettings webSettings = web.getSettings();
        //设置支持javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        if ("1".equals(flag)) {
            web.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    //web.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });
            web.loadUrl(url);
        } else if ("2".equals(flag)) {
            web.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    //web.loadDataWithBaseURL(null,url,"text/html", "UTF-8", null);
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });
            web.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
            web.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.finish)
    public void onViewClicked() {
        finish();
    }
}
