package com.loan.time.ui.home;

import android.content.Context;

import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.BasePresenter;
import com.loan.time.mvp.BaseView;

import java.util.List;

public class HomeContract {

    interface View extends BaseView {

        void getHomeData(List<ResponseBean.DataBean.ProductListBean> mList);

    }

    interface  Presenter extends BasePresenter<View> {
        void getHomeData(Context context);
    }
}
