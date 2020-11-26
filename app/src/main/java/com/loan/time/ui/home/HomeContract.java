package com.loan.time.ui.home;

import android.content.Context;

import com.loan.time.mvp.BasePresenter;
import com.loan.time.mvp.BaseView;

public class HomeContract {

    interface View extends BaseView {

        void getHomeData();

    }

    interface  Presenter extends BasePresenter<View> {
        void getHomeData(Context context);
    }
}
