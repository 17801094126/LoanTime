package com.loan.time.ui.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.loan.time.App;
import com.loan.time.BuildConfig;
import com.loan.time.banner.LoanDialog;
import com.loan.time.bean.DecryptBean;
import com.loan.time.bean.RequestBean;
import com.loan.time.bean.ResponseBean;
import com.loan.time.bean.ResponseDecryptBean;
import com.loan.time.mvp.BasePresenterImpl;
import com.loan.time.network.HttpCode;
import com.loan.time.network.HttpUtils;
import com.loan.time.ui.authority.AuthorityActivity;
import com.loan.time.ui.first.FirstActivity;
import com.loan.time.utils.AppUtils;
import com.loan.time.utils.PreferenceUtil;
import com.loan.time.utils.ToastUtils;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter  extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private LoanDialog loanDialog;
    private Gson gson = new Gson();

    /**
     * 获取用户基本信息以及App版本更新
     * @param context
     */
    @Override
    public void getUpdate(Context context) {
        loanDialog=LoanDialog.show(context,false);
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            RequestBean requestBean = new RequestBean();
            requestBean.setDeviceToken("");
            requestBean.setDeviceId("");
            requestBean.setUid("0");
            //设置App基本信息
            RequestBean.DataBean dataBean = initDeviceInfo(context);
            requestBean.setDeviceInfo(new Gson().toJson(dataBean));
            String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_a_0", gson.toJson(requestBean), "{}");
            emitter.onNext(respone);
            Log.e("LoginPresenter","RequqestBean:"+requestBean.toString());
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 运算 线程
         .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String str) {
                //主线程执行的方法
                loanDialog.dismiss();
                try {
                    ResponseBean responseBean = gson.fromJson(str, ResponseBean.class);
                    Log.e("LoginPresenter","ResponseBean:"+responseBean.toString());
                    if (HttpCode.CODE_SUCCESS.equals(responseBean.getCode())){
                        PreferenceUtil.commitString(App.DeviceId,responseBean.getData().getDeviceId());
                        PreferenceUtil.commitString(App.DeviceToken,responseBean.getData().getDeviceToken());
                        try {
                            DecryptBean decryptBean = new DecryptBean();
                            byte[] decrypt = HttpUtils.getInstance().decrypt(gson.toJson(decryptBean), responseBean.getData().getConfig());
                            String s = new String(decrypt);
                            Log.e("LoginActivity",s);
                            ResponseDecryptBean bean = new Gson().fromJson(s, ResponseDecryptBean.class);
                            List<ResponseDecryptBean.ExtendListBean> extendList = bean.getExtendList();
                            String loginUrl = getLoginUrl(extendList);
                            if (isAttarchView()){
                                // getView().getUpdate(bean);
                                getView().getLoginXieYi(loginUrl);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        ToastUtils.showToast(context,responseBean.getMessage());
                    }
                }catch (Exception e){
                    ToastUtils.showToast(context,str);
                }


            }

            @Override
            public void onError(Throwable e) {
                loanDialog.dismiss();

            }

            @Override
            public void onComplete() {

            }
        });


    }

    private String getLoginUrl(List<ResponseDecryptBean.ExtendListBean> extendList){
        String extendValue="";
        for (int i = 0; i < extendList.size(); i++) {
            ResponseDecryptBean.ExtendListBean extendListBean = extendList.get(i);
            if (extendListBean.getExtendCode().equals("api_login_protocol_url")){
                extendValue = extendListBean.getExtendValue();
            }
        }
        return extendValue;

    }
    /**
     * 获取图片验证码
     * @param context
     * @param mobilePhone
     */
    @Override
    public void getLoginImg(Context context,String mobilePhone) {
        loanDialog=LoanDialog.show(context,false);
        Observable.create((ObservableOnSubscribe<ResponseBean>) emitter -> {
            if (TextUtils.isEmpty(PreferenceUtil.getString(App.DeviceToken,""))){
                RequestBean requestBean = new RequestBean();
                requestBean.setDeviceToken("");
                requestBean.setDeviceId("");
                requestBean.setUid("0");
                //设置App基本信息
                RequestBean.DataBean dataBean = initDeviceInfo(context);
                requestBean.setDeviceInfo(new Gson().toJson(dataBean));
                String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_a_0", gson.toJson(requestBean), "{}");
                ResponseBean responseBean = gson.fromJson(respone, ResponseBean.class);
                String deviceId = responseBean.getData().getDeviceId();
                String deviceToken = responseBean.getData().getDeviceToken();
                requestBean.setMobile(mobilePhone);
                requestBean.setDeviceToken(deviceToken);
                requestBean.setDeviceId(deviceId);
                requestBean.setUid("0");
                String respone1 = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_l_0", gson.toJson(requestBean), "{}");
                ResponseBean responseBean1 = gson.fromJson(respone1, ResponseBean.class);
                ResponseBean.DataBean data = responseBean1.getData();
                data.setDeviceId(deviceId);
                data.setDeviceToken(deviceToken);
                responseBean1.setData(data);
                emitter.onNext(responseBean1);
            }else{
                RequestBean requestBean = new RequestBean();
                requestBean.setMobile(mobilePhone);
                requestBean.setDeviceToken(PreferenceUtil.getString(App.DeviceToken, ""));
                requestBean.setDeviceId(PreferenceUtil.getString(App.DeviceId, ""));
                requestBean.setUid("0");
                String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_l_0", gson.toJson(requestBean), "{}");
                ResponseBean responseBean = gson.fromJson(respone, ResponseBean.class);
                emitter.onNext(responseBean);
            }

        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 运算 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
            .subscribe(new Observer<ResponseBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ResponseBean responseBean) {
                    loanDialog.dismiss();
                    if (HttpCode.CODE_SUCCESS.equals(responseBean.getCode())){
                        if (TextUtils.isEmpty(PreferenceUtil.getString(App.DeviceId,""))){
                            PreferenceUtil.commitString(App.DeviceId,responseBean.getData().getDeviceId());
                        }
                        if (TextUtils.isEmpty(PreferenceUtil.getString(App.DeviceToken,""))){
                            PreferenceUtil.commitString(App.DeviceToken,responseBean.getData().getDeviceToken());
                        }
                        if (isAttarchView())
                            getView().getLoginImg(responseBean.getData().getId(),responseBean.getData().getImage());
                    }else {
                        ToastUtils.showToast(context,responseBean.getMessage());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    loanDialog.dismiss();
                }

                @Override
                public void onComplete() {

                }
            });

    }

    /**
     * 登录
     * @param activity
     * @param id
     * @param imgCode
     */
    @Override
    public void login(LoginActivity activity, String id, String mobilePhone,String imgCode) {

        loanDialog=LoanDialog.show(activity,false);
        Observable.create((ObservableOnSubscribe<ResponseBean>) emitter -> {
            RequestBean requestBean = new RequestBean();
            requestBean.setMobile(mobilePhone);
            requestBean.setDeviceToken(PreferenceUtil.getString(App.DeviceToken,""));
            requestBean.setDeviceId(PreferenceUtil.getString(App.DeviceId,""));
            requestBean.setUid("0");
            requestBean.setCaptchaId(id);
            requestBean.setDigits(imgCode);
            requestBean.setDeviceInfo(new Gson().toJson(initDeviceInfo(activity)));
            String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_l_1", gson.toJson(requestBean), "{}");
            ResponseBean responseBean = gson.fromJson(respone, ResponseBean.class);
            emitter.onNext(responseBean);
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 运算 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
        .subscribe(new Observer<ResponseBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBean responseBean) {
                loanDialog.dismiss();
                if (HttpCode.CODE_SUCCESS.equals(responseBean.getCode())){
                    PreferenceUtil.commitString(App.Uid,responseBean.getData().getUid());
                    PreferenceUtil.commitString(App.Token,responseBean.getData().getToken());
                    PreferenceUtil.commitString(App.Phone,mobilePhone);
                    if (PreferenceUtil.getBoolean(App.IsAuthority,false)){
                        activity.startActivity(new Intent(activity, FirstActivity.class));
                        activity.finish();
                    }else{
                        activity.startActivity(new Intent(activity, AuthorityActivity.class));
                        activity.finish();
                    }
                }else if (HttpCode.CODE_121002.equals(responseBean.getCode())){
                    ToastUtils.showToast(activity,"Please enter the correct security code");
                    getLoginImg(activity,mobilePhone);
                }else {
                    ToastUtils.showToast(activity,responseBean.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                loanDialog.dismiss();
            }

            @Override
            public void onComplete() {

            }
        });

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
        dataBean.setUuid(AppUtils.getUUID(context));
        return dataBean;
    }
}
