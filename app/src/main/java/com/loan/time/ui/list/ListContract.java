package com.loan.time.ui.list;

import android.content.Context;

import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.BasePresenter;
import com.loan.time.mvp.BaseView;

import java.util.List;

public class ListContract {

    interface View extends BaseView {
        void getListData(List<ResponseBean.DataBean.ProductListBean> mList);

    }

    interface  Presenter extends BasePresenter<View> {
        void getListData(Context context,String plateKey);
    }
}
