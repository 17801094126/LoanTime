package com.loan.time.ui;

import android.content.Context;

import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.BasePresenter;
import com.loan.time.mvp.BaseView;
import com.loan.time.ui.login.LoginActivity;

import java.util.List;

public class SplashContract {

    interface View extends BaseView {

        //获取图形验证码
        void initView(List<ResponseBean.DataBean.ProductListBean> productList);

    }

    interface  Presenter extends BasePresenter<View> {

        //初始化接口
        void initModel(Context context);
    }
}
