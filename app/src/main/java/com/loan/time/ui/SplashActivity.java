package com.loan.time.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.loan.time.App;
import com.loan.time.R;
import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.MVPBaseActivity;
import com.loan.time.ui.authority.AuthorityActivity;
import com.loan.time.ui.first.FirstActivity;
import com.loan.time.ui.login.LoginActivity;
import com.loan.time.utils.DialogHelp;
import com.loan.time.utils.PreferenceUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class SplashActivity extends MVPBaseActivity<SplashContract.View, SplashPresenter> implements SplashContract.View {
    /**
     * 用Handler做定时器，2秒后跳转。
     */
    private ArrayList<ResponseBean.DataBean.ProductListBean> mList=new ArrayList<>();
    private Handler handler = new Handler();
    private int timer = 2;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (timer == 0) {
                int anInt = PreferenceUtil.getInt(NUM, 1);
                if (anInt==1){
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else if (anInt==2){
                    if (PreferenceUtil.getBoolean(App.IsAuthority,false)){
                        Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
                        intent.putParcelableArrayListExtra(FirstActivity.HomeResult,mList);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(SplashActivity.this, AuthorityActivity.class);
                        intent.putParcelableArrayListExtra(FirstActivity.HomeResult,mList);
                        startActivity(intent);
                        finish();
                    }

                }
            } else {
                timer--;
                handler.postDelayed(runnable, 1000);
            }
        }
    };
    public static  String  NUM="Num";//1 跳转登录   2跳转首页
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

        if (TextUtils.isEmpty(PreferenceUtil.getString(App.Token,""))){
            if (AndPermission.hasPermissions(this,Manifest.permission.READ_PHONE_STATE)){
                handler.postDelayed(runnable, 2000);
                PreferenceUtil.commitInt(NUM,1);
            }else{
                AndPermission.with(this)
                        .runtime()
                        .permission(Permission.READ_PHONE_STATE)
                        .onGranted(permissions -> {
                            if (AndPermission.hasPermissions(SplashActivity.this, Permission.READ_PHONE_STATE)) {
                                //获取到权限
                                //获取App基本信息以及升级接口
                                handler.postDelayed(runnable, 2000);
                                PreferenceUtil.commitInt(NUM,1);
                            } else {
                                //未获取到权限
                                DialogHelp.showNormalDialog(this);
                            }
                        })
                        .onDenied(permissions -> {
                            if (AndPermission.hasPermissions(SplashActivity.this, Permission.READ_PHONE_STATE)) {
                                //获取到权限
                                //获取App基本信息以及升级接口
                                handler.postDelayed(runnable, 2000);
                                PreferenceUtil.commitInt(NUM,1);
                            } else {
                                //未获取到权限
                                DialogHelp.showNormalDialog(this);
                            }
                        })
                        .start();
            }

        }else{
            if (AndPermission.hasPermissions(this,Permission.READ_PHONE_STATE)){
                mPresenter.initModel(this);
            }else{
                AndPermission.with(this)
                        .runtime()
                        .permission(Permission.READ_PHONE_STATE)
                        .onGranted(permissions -> {
                            if (AndPermission.hasPermissions(SplashActivity.this, Permission.READ_PHONE_STATE)) {
                                //获取到权限
                                //获取App基本信息以及升级接口
                                mPresenter.initModel(this);
                            } else {
                                //未获取到权限
                                DialogHelp.showNormalDialog(this);
                            }
                        })
                        .onDenied(permissions -> {
                            if (AndPermission.hasPermissions(SplashActivity.this,Permission.READ_PHONE_STATE)) {
                                //获取到权限
                                //获取App基本信息以及升级接口
                                mPresenter.initModel(this);
                            } else {
                                //未获取到权限
                                DialogHelp.showNormalDialog(this);
                            }
                        })
                        .start();
            }
        }

    }


    @Override
    public void initView(List<ResponseBean.DataBean.ProductListBean> productList) {
        handler.postDelayed(runnable, 2000);
        if (productList!=null&&productList.size()>0){
            mList.addAll(productList);
        }
    }
}
