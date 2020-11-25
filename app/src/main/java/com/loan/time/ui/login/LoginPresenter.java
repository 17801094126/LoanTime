package com.loan.time.ui.login;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.loan.time.BuildConfig;
import com.loan.time.R;
import com.loan.time.banner.LoanDialog;
import com.loan.time.bean.RequestBean;
import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.BasePresenterImpl;
import com.loan.time.network.HttpUtils;

import goutil.Goutil;

public class LoginPresenter  extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private LoanDialog loanDialog;
    private Gson gson = new Gson();

    @Override
    public void getLoginImg(Context context,String mobilePhone) {
        RequestBean requestBean = new RequestBean();
        requestBean.setMobile(mobilePhone);
        requestBean.setDeviceToken("");
        requestBean.setDeviceId("");
        requestBean.setUid("0");
        requestBean.setDeviceToken("");
        requestBean.setDeviceId(null);
        String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_l_0", gson.toJson(requestBean), "{}");
        ResponseBean responseBean = gson.fromJson(respone, ResponseBean.class);
    }

    @Override
    public void getUpdate(Context context) {
        RequestBean requestBean = new RequestBean();
        requestBean.setDeviceToken("");
        requestBean.setDeviceId("");
        requestBean.setUid("0");
        requestBean.setDeviceToken("");
        requestBean.setDeviceId(null);
        Log.e("QQQQ",gson.toJson(requestBean));
        String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_a_0", gson.toJson(requestBean), "{}");
        ResponseBean responseBean = gson.fromJson(respone, ResponseBean.class);
        Log.e("QQQQ",responseBean.toString());
    }
}
