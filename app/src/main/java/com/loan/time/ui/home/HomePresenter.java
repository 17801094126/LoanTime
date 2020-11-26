package com.loan.time.ui.home;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loan.time.App;
import com.loan.time.BuildConfig;
import com.loan.time.banner.LoanDialog;
import com.loan.time.bean.HeaderBean;
import com.loan.time.bean.RequestBean;
import com.loan.time.bean.ResponseBean;
import com.loan.time.bean.ResponseListBean;
import com.loan.time.mvp.BasePresenterImpl;
import com.loan.time.network.HttpUtils;
import com.loan.time.utils.PreferenceUtil;

public class HomePresenter  extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter {

    private LoanDialog loanDialog;
    private Gson gson = new Gson();

    @Override
    public void getHomeData(Context context) {

        loanDialog=LoanDialog.show(context,false);
        RequestBean requestBean = new RequestBean();
        requestBean.setDeviceToken(PreferenceUtil.getString(App.DeviceToken,""));
        requestBean.setDeviceId(PreferenceUtil.getString(App.DeviceId,""));
        requestBean.setUid(PreferenceUtil.getString(App.Uid,""));
        requestBean.setCooperateWay("h5");
        Log.e("QQQQQ","RequestBody----------》"+gson.toJson(requestBean));
        Log.e("QQQQQ","RequestHeader----------》"+gson.toJson(new HeaderBean(PreferenceUtil.getString(App.Token,""))));
        String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_l_2", gson.toJson(requestBean),gson.toJson(new HeaderBean(PreferenceUtil.getString(App.Token,""))));
        ResponseListBean responseBean = gson.fromJson(respone, ResponseListBean.class);
        Log.e("QQQQQ","ResponseBody--------------------》"+responseBean.toString());
    }
}
