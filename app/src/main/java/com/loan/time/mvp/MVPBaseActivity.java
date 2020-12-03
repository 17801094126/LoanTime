package com.loan.time.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.loan.time.App;
import com.loan.time.utils.ActivityCollector;
import com.loan.time.utils.AppUtils;
import com.loan.time.utils.PreferenceUtil;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;


public abstract class MVPBaseActivity<V extends BaseView,T extends BasePresenterImpl<V>> extends AppCompatActivity implements BaseView{
    public T mPresenter;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceUtil.init(this);

        setContentView(getLayoutId());
        if (Build.VERSION.SDK_INT>=23){
            ImmersionBar.with(this)
                    .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                    .init();
        }else{
            ImmersionBar.with(this)
                    .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                    .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
                    .init();
        }
        AppUtils.setStatus(this);
        App.context=this;
        mPresenter= getInstance(this,1);
        mPresenter.attachView((V) this);
        ScreenAdapterTools.getInstance().reset(this);//如果希望android7.0分屏也适配的话,加上这句
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        //绑定ButterKnifet
        ButterKnife.bind(this);
        initView();
        initData();


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityCollector.addActivity(this);
    }

    protected abstract int getLayoutId();

    protected void initView(){}
    protected void initData(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null)
        mPresenter.detachView();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public Context getContext(){
        return this;
    }

    public  <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }

    //获取状态栏高度
    protected int getHeight(){
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }
}
