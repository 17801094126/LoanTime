package com.loan.time.ui.login;

import android.content.Context;

import com.loan.time.mvp.BasePresenter;
import com.loan.time.mvp.BaseView;

import java.net.ConnectException;

public class LoginContract {

    interface View extends BaseView {

        //获取图形验证码
        void getLoginImg();

    }

    interface  Presenter extends BasePresenter<View> {

        //获取图形验证码
        void getLoginImg(Context context,String mobilePhone);
        //升级
        void getUpdate(Context context);
    }
}
