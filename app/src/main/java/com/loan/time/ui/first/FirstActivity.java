package com.loan.time.ui.first;

import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.FragmentManager;

import com.gyf.immersionbar.ImmersionBar;
import com.loan.time.R;
import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.MVPBaseActivity;
import com.loan.time.ui.home.HomeFragment;
import com.loan.time.ui.my.MyFragment;
import com.loan.time.utils.FragmentUtils;
import com.loan.time.utils.SingleClick;
import com.loan.time.utils.ToastUtils;
import com.loan.time.utils.VirturlUtil;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;

import butterknife.BindView;

public class FirstActivity extends MVPBaseActivity<FirstContract.View, FirstPresenter> implements FirstContract.View ,RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.first_Frame)
    FrameLayout firstFrame;
    @BindView(R.id.rb_Home)
    RadioButton rbHome;
    @BindView(R.id.rb_My)
    RadioButton rbMy;
    @BindView(R.id.rg)
    RadioGroup rg;
    //返回键监听
    private boolean isQuit;
    //读写权限
    public static String TAG = FirstActivity.class.getSimpleName();
    private ArrayList<ResponseBean.DataBean.ProductListBean> mList;
    public static final String HomeResult="HomeResult";
    FragmentManager manager;
    private HomeFragment homeFragment;
    private MyFragment myFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first;
    }

    @Override
    protected void initView() {
        super.initView();
        mList = getIntent().getParcelableArrayListExtra(HomeResult);
        manager = getSupportFragmentManager();
        RadioButton childAt = (RadioButton) rg.getChildAt(0);
        childAt.setChecked(true);
        ImmersionBar.with(FirstActivity.this)
                .transparentBar()
                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为 true)
                .navigationBarColor(R.color.black) //导航栏颜色，不写默认黑色
                .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认 0.0F
                .fullScreen(false)
                .statusBarDarkFont(false)
                .reset()
                .init();
        if (mList==null){
            if (homeFragment == null) {
                homeFragment = HomeFragment.newInstance();
                FragmentUtils.addFragment(manager, homeFragment, R.id.first_Frame, false);
            } else {
                FragmentUtils.showFragment(homeFragment);
            }
        }else{
            if (homeFragment == null) {
                homeFragment = HomeFragment.newInstance(mList);
                FragmentUtils.addFragment(manager, homeFragment, R.id.first_Frame, false);
            } else {
                FragmentUtils.showFragment(homeFragment);
            }
        }

        //底部导航栏点击事件
        rg.setOnCheckedChangeListener(this);
    }

    private void initHideAllFragment() {

        if (homeFragment != null)
            FragmentUtils.hideFragment(homeFragment);
        if (myFragment != null)
            FragmentUtils.hideFragment(myFragment);

    }

    //返回Home键监听
    @Override
    public void onBackPressed() {
        if (!isQuit) {
            ToastUtils.showToast(this, "Click again to exit!");
            isQuit = true;
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    isQuit = false;
                }
            }).start();
        } else {
            System.exit(0);
            finish();
        }
    }

    @SingleClick
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        initHideAllFragment();
        switch (checkedId) {
            case R.id.rb_Home:
                ImmersionBar.with(FirstActivity.this)
                        .transparentBar()
                        .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为 true)
                        .navigationBarColor(R.color.black) //导航栏颜色，不写默认黑色
                        .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认 0.0F
                        .fullScreen(false)
                        .statusBarDarkFont(false)
                        .init();
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                    FragmentUtils.addFragment(manager, homeFragment, R.id.first_Frame, false);
                } else {
                    FragmentUtils.showFragment(homeFragment);
                }
                break;
            case R.id.rb_My:
                if (Build.VERSION.SDK_INT>=23){
                    ImmersionBar.with(FirstActivity.this)
                            .transparentBar()
                            .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为 true)
                            .navigationBarColor(R.color.black) //导航栏颜色，不写默认黑色
                            .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认 0.0F
                            .fullScreen(false)
                            .statusBarDarkFont(true)
                            .init();
                }else{
                    ImmersionBar.with(this)
                            .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                            .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
                            .fullScreen(false)
                            .init();
                }
                if (myFragment == null) {
                    myFragment = MyFragment.newInstance();
                    FragmentUtils.addFragment(manager, myFragment, R.id.first_Frame, false);
                } else {
                    FragmentUtils.showFragment(myFragment);
                }
                break;
        }
    }


}