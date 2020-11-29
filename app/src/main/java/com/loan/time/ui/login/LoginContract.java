package com.loan.time.ui.login;

import android.content.Context;

import com.loan.time.bean.ResponseDecryptBean;
import com.loan.time.mvp.BasePresenter;
import com.loan.time.mvp.BaseView;

import java.net.ConnectException;

public class LoginContract {

    interface View extends BaseView {

        //获取图形验证码
        void getLoginImg(String id,String image);
        //获取升级信息
        void getUpdate(ResponseDecryptBean bean);
        //获取注册协议地址
        void getLoginXieYi(String url);

    }

    interface  Presenter extends BasePresenter<View> {

        //获取图形验证码
        void getLoginImg(Context context,String mobilePhone);
        //升级
        void getUpdate(Context context);
        //登录
        void login(LoginActivity activity,String id,String mobilePhone,String imgCode);
    }
}
