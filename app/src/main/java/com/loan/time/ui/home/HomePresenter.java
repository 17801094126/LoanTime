package com.loan.time.ui.home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import com.loan.time.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter  extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter {

    private LoanDialog loanDialog;
    private Gson gson = new Gson();

    @Override
    public void getHomeData(Context context) {
        loanDialog=LoanDialog.show(context,false);
        Observable.create((ObservableOnSubscribe<ResponseBean>) subscriber -> {
            RequestBean requestBean = new RequestBean();
            requestBean.setDeviceToken(PreferenceUtil.getString(App.DeviceToken,""));
            requestBean.setDeviceId(PreferenceUtil.getString(App.DeviceId,""));
            requestBean.setUid(PreferenceUtil.getString(App.Uid,""));
            requestBean.setCooperateWay("h5");
            String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_l_2", gson.toJson(requestBean),gson.toJson(new HeaderBean(PreferenceUtil.getString(App.Token,""))));
            Log.e("QQQ",respone);
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
                loanDialog.dismiss();
                if (HttpCode.CODE_SUCCESS.equals(responseBean.getCode())){
                    if (isAttarchView())
                        getView().getHomeData(responseBean.getData().getProductList());
                }else{
                    ToastUtils.showToast(context,responseBean.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                loanDialog.dismiss();
                Log.i("ALex", "出现了异常", e);
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        });


    }
}
