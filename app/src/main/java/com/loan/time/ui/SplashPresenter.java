package com.loan.time.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loan.time.App;
import com.loan.time.BuildConfig;
import com.loan.time.bean.HeaderBean;
import com.loan.time.bean.RequestBean;
import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.BasePresenterImpl;
import com.loan.time.network.HttpCode;
import com.loan.time.network.HttpUtils;
import com.loan.time.utils.AppUtils;
import com.loan.time.utils.PreferenceUtil;
import com.loan.time.utils.ToastUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter  extends BasePresenterImpl<SplashContract.View> implements SplashContract.Presenter {

    private Gson gson = new Gson();

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

    @Override
    public void initModel(Context context) {
        Observable.create((ObservableOnSubscribe<ResponseBean>) emitter -> {
            RequestBean requestBean = new RequestBean();
            requestBean.setDeviceToken("");
            requestBean.setDeviceId("");
            requestBean.setUid("0");
            //设置App基本信息
            RequestBean.DataBean dataBean = initDeviceInfo(context);
            requestBean.setDeviceInfo(new Gson().toJson(dataBean));
            String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_a_0", gson.toJson(requestBean), "{}");
            ResponseBean responseBean = gson.fromJson(respone, ResponseBean.class);
            emitter.onNext(responseBean);
            Log.e("LoginPresenter","RequqestBean:"+responseBean.toString());
            Log.e("LoginPresenter","ResponseBean:"+responseBean.toString());
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 运算 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        //主线程执行的方法
                        if (HttpCode.CODE_SUCCESS.equals(responseBean.getCode())){
                            PreferenceUtil.commitString(App.DeviceId,responseBean.getData().getDeviceId());
                            PreferenceUtil.commitString(App.DeviceToken,responseBean.getData().getDeviceToken());
                            initIsLogin();
                        }else{
                            if (isAttarchView())
                                getView().initView(null);
                            ToastUtils.showToast(context,responseBean.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isAttarchView())
                            getView().initView(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initIsLogin() {
        Observable.create((ObservableOnSubscribe<ResponseBean>) subscriber -> {
            RequestBean requestBean = new RequestBean();
            requestBean.setDeviceToken(PreferenceUtil.getString(App.DeviceToken,""));
            requestBean.setDeviceId(PreferenceUtil.getString(App.DeviceId,""));
            requestBean.setUid(PreferenceUtil.getString(App.Uid,""));
            requestBean.setCooperateWay("h5");
            String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_l_2", gson.toJson(requestBean),gson.toJson(new HeaderBean(PreferenceUtil.getString(App.Token,""))));
            ResponseBean responseBean = gson.fromJson(respone, ResponseBean.class);
            //以上内容均在子线程执行
            //现在开始触发主线程的回调
            subscriber.onNext(responseBean);

        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 运算 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        //主线程执行的方法
                        if (HttpCode.CODE_SUCCESS.equals(responseBean.getCode())){
                            PreferenceUtil.commitInt(SplashActivity.NUM,2);
                            List<ResponseBean.DataBean.ProductListBean> productList = responseBean.getData().getProductList();
                            if (isAttarchView())
                                getView().initView(productList);
                        }else if (HttpCode.CODE_120002.equals(responseBean.getCode())){
                            PreferenceUtil.commitInt(SplashActivity.NUM,1);
                            if (isAttarchView())
                                getView().initView(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isAttarchView())
                            getView().initView(null);
                        Log.i("ALex", "出现了异常", e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
