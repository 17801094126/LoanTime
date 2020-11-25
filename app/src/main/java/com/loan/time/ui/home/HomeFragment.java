package com.loan.time.ui.home;


import com.loan.time.R;
import com.loan.time.mvp.MVPBaseFragment;


public class HomeFragment extends MVPBaseFragment<HomeContract.View, HomePresenter> implements HomeContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}