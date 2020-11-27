package com.loan.time.ui.list;

import android.content.Context;

import com.google.gson.Gson;
import com.loan.time.App;
import com.loan.time.BuildConfig;
import com.loan.time.banner.LoanDialog;
import com.loan.time.bean.HeaderBean;
import com.loan.time.bean.RequestBean;
import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.BasePresenterImpl;
import com.loan.time.network.HttpCode;
import com.loan.time.network.HttpUtils;
import com.loan.time.utils.PreferenceUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListPresenter  extends BasePresenterImpl<ListContract.View> implements ListContract.Presenter {

    private LoanDialog loanDialog;
    private Gson gson = new Gson();

    @Override
    public void getListData(Context context, String plateKey) {
        loanDialog=LoanDialog.show(context,false);
        Observable.create((ObservableOnSubscribe<ResponseBean>) emitter -> {
            RequestBean requestBean = new RequestBean();
            requestBean.setDeviceToken(PreferenceUtil.getString(App.DeviceToken,""));
            requestBean.setDeviceId(PreferenceUtil.getString(App.DeviceId,""));
            requestBean.setUid(PreferenceUtil.getString(App.Uid,""));
            requestBean.setCooperateWay("h5");
            requestBean.setPlateKey(plateKey);
            String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_l_2", gson.toJson(requestBean),gson.toJson(new HeaderBean(PreferenceUtil.getString(App.Token,""))));
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
                                       if (isAttarchView())
                                           getView().getListData(responseBean.getData().getProductList());
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
}
