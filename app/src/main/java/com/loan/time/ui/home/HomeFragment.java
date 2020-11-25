package com.loan.time.ui.home;


import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loan.time.R;
import com.loan.time.adaperts.AuthorityRv;
import com.loan.time.adaperts.HomeRvAdapert;
import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.MVPBaseFragment;
import com.loan.time.ui.first.FirstActivity;
import com.loan.time.utils.SingleClick;
import com.scwang.smart.refresh.layout.footer.ClassicsFooter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends MVPBaseFragment<HomeContract.View, HomePresenter> implements HomeContract.View {

    @BindView(R.id.home_high)
    View homeHigh;
    @BindView(R.id.home_large)
    View homeLarge;
    @BindView(R.id.home_quick)
    View homeQuick;
    @BindView(R.id.home_rv)
    RecyclerView homeRv;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.class_Footer)
    ClassicsFooter classFooter;
    private ArrayList<ResponseBean> mList=new ArrayList<>();
    private FirstActivity activity;

    @Override
    public int getLayoutId() {
        activity= (FirstActivity) getActivity();
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        homeRv.setNestedScrollingEnabled(false);
        homeRv.setLayoutManager(manager);
        HomeRvAdapert adapert = new HomeRvAdapert(activity, mList);
        homeRv.setAdapter(adapert);
    }

    @SingleClick
    @OnClick({R.id.home_high, R.id.home_large, R.id.home_quick,R.id.home_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_img:
                break;
            case R.id.home_high:
                break;
            case R.id.home_large:
                break;
            case R.id.home_quick:
                break;
        }
    }
}