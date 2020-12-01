package com.loan.time.ui.home;


import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.loan.time.R;
import com.loan.time.adaperts.HomeRvAdapert;
import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.MVPBaseFragment;
import com.loan.time.ui.first.FirstActivity;
import com.loan.time.ui.list.ListActivity;
import com.loan.time.ui.web.WebActivity;
import com.loan.time.utils.SingleClick;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends MVPBaseFragment<HomeContract.View, HomePresenter> implements HomeContract.View, HomeRvAdapert.RvItemClickListener {

    @BindView(R.id.home_high)
    View homeHigh;
    @BindView(R.id.home_large)
    View homeLarge;
    @BindView(R.id.home_quick)
    View homeQuick;
    @BindView(R.id.home_rv)
    RecyclerView homeRv;
    @BindView(R.id.tv_high)
    TextView tvHigh;
    @BindView(R.id.tv_large)
    TextView tvLarge;
    @BindView(R.id.tv_quick)
    TextView tvQuick;
    @BindView(R.id.smart)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.home_img)
    ImageView homeImg;
    private FirstActivity activity;
    private List<ResponseBean.DataBean.ProductListBean> mList;

    @Override
    public int getLayoutId() {
        activity = (FirstActivity) getActivity();
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected void initView() {
        super.initView();

        mPresenter.getHomeData(activity);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.getHomeData(activity);

        });

    }

    @SingleClick
    @OnClick({R.id.home_high, R.id.home_large, R.id.home_quick, R.id.home_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_img:
                startToList(getResources().getString(R.string.app_name),ListActivity.IN_Home);
                break;
            case R.id.home_high:
                startToList(tvHigh.getText().toString().trim(),ListActivity.IN_HIGHQUOTA);
                break;
            case R.id.home_large:
                startToList(tvLarge.getText().toString().trim(),ListActivity.IN_FASTLENDING);
                break;
            case R.id.home_quick:
                startToList(tvQuick.getText().toString().trim(),ListActivity.IN_RECOMMEND);
                break;
        }
    }

    private void startToList(String value,String plateKey) {
        Intent intent = new Intent(activity, ListActivity.class);
        intent.putExtra(ListActivity.List_Value, value);
        intent.putExtra(ListActivity.PlateKey,plateKey);
        startActivity(intent);
    }

    @Override
    public void getHomeData(List<ResponseBean.DataBean.ProductListBean> mList) {
        smartRefreshLayout.finishRefresh();
        this.mList=mList;
        LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        homeRv.setNestedScrollingEnabled(false);
        homeRv.setLayoutManager(manager);
        HomeRvAdapert adapert = new HomeRvAdapert(activity, mList,0);
        homeRv.setAdapter(adapert);
        adapert.setOnClickListener(this);
    }

    @Override
    public void onClickerListener(int position) {
        String innerProductUrl = mList.get(position).getInnerProductUrl();
        try {
            URL url=new URL(innerProductUrl);
            if (url.getHost().equals("play.google.com")){
                Uri uri = Uri.parse(mList.get(position).getInnerProductUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }else if (mList.get(position).getInnerProductUrl().endsWith("apk")){
                Uri uri = Uri.parse(mList.get(position).getInnerProductUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }else{
                Intent intent=new Intent(activity, WebActivity.class);
                intent.putExtra(WebActivity.WebUrl,mList.get(position).getInnerProductUrl());
                startActivity(intent);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }
}