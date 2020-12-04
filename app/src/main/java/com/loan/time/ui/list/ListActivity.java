package com.loan.time.ui.list;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.gyf.immersionbar.ImmersionBar;
import com.loan.time.R;
import com.loan.time.adaperts.HomeRvAdapert;
import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.MVPBaseActivity;
import com.loan.time.ui.web.WebActivity;
import com.loan.time.utils.SingleClick;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends MVPBaseActivity<ListContract.View, ListPresenter> implements ListContract.View, HomeRvAdapert.RvItemClickListener {

    @BindView(R.id.finish)
    ImageView finish;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tool_Bar)
    Toolbar toolBar;
    @BindView(R.id.lv_Rv)
    RecyclerView lvRv;
    @BindView(R.id.list_smart)
    SmartRefreshLayout listSmart;
    public static String List_Value = "ListValue";
    public static String PlateKey = "PlateKey";
    public static String IN_HIGHQUOTA = "IN_HIGHQUOTA";
    public static String IN_FASTLENDING = "IN_FASTLENDING";
    public static String IN_RECOMMEND = "IN_RECOMMEND";
    public static String IN_Home = "IN_Home";
    private ArrayList<ResponseBean.DataBean.ProductListBean> proList = new ArrayList<>();
    private HomeRvAdapert adapert;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lvRv.setNestedScrollingEnabled(false);
        lvRv.setLayoutManager(manager);
        adapert = new HomeRvAdapert(this, proList, 1);
        lvRv.setAdapter(adapert);
        adapert.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        String intExtra = intent.getStringExtra(List_Value);
        String plateKey = intent.getStringExtra(PlateKey);
        toolBar.setPadding(0, getHeight(), 0, 0);
        if (!TextUtils.isEmpty(intExtra)) {
            title.setText(intExtra);
        } else {
            title.setText("High pass rate");
        }
        if (plateKey.equals(IN_Home)){
            mPresenter.getHomeData(this);
        }else{
            mPresenter.getListData(this, plateKey);
        }
        listSmart.setOnRefreshListener(refreshLayout -> {
            if (plateKey.equals(IN_Home)){
                mPresenter.getHomeData(this);
            }else{
                mPresenter.getListData(this, plateKey);
            }
        });
    }

    @SingleClick
    @OnClick(R.id.finish)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getListData(List<ResponseBean.DataBean.ProductListBean> mList) {
        if (listSmart.isRefreshing()){
            listSmart.finishRefresh();
        }
        proList.clear();
        proList.addAll(mList);
        adapert.notifyDataSetChanged();

    }

    @Override
    public void getHomeData(List<ResponseBean.DataBean.ProductListBean> mList) {
        if (listSmart.isRefreshing()){
            listSmart.finishRefresh();
        }
        proList.clear();
        proList.addAll(mList);
        adapert.notifyDataSetChanged();
    }

    @Override
    public void onClickerListener(int position) {
        String innerProductUrl = proList.get(position).getInnerProductUrl();
        try {
            URL url = new URL(innerProductUrl);
            if (url.getHost().equals("play.google.com")) {
                Uri uri = Uri.parse(innerProductUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else if (proList.get(position).getInnerProductUrl().endsWith("apk")) {
                Uri uri = Uri.parse(innerProductUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra(WebActivity.WebUrl, innerProductUrl);
                startActivity(intent);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}