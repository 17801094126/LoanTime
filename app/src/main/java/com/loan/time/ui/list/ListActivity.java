package com.loan.time.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loan.time.R;
import com.loan.time.adaperts.HomeRvAdapert;
import com.loan.time.bean.ResponseBean;
import com.loan.time.mvp.MVPBaseActivity;
import com.loan.time.utils.SingleClick;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends MVPBaseActivity<ListContract.View, ListPresenter> implements ListContract.View {

    @BindView(R.id.finish)
    ImageView finish;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tool_Bar)
    Toolbar toolBar;
    @BindView(R.id.lv_Rv)
    RecyclerView lvRv;
    public static String List_Value = "ListValue";
    private ArrayList<ResponseBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        String intExtra = intent.getStringExtra(List_Value);
        toolBar.setPadding(0, getHeight(), 0, 0);
        if (!TextUtils.isEmpty(intExtra)) {
            title.setText(intExtra);
        } else {
            title.setText("High pass rate");
        }

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lvRv.setNestedScrollingEnabled(false);
        lvRv.setLayoutManager(manager);
        HomeRvAdapert adapert = new HomeRvAdapert(this, mList, 1);
        lvRv.setAdapter(adapert);
    }

    @SingleClick
    @OnClick(R.id.finish)
    public void onViewClicked() {
        finish();
    }

}