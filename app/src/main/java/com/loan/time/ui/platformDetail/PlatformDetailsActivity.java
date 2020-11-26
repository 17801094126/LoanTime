package com.loan.time.ui.platformDetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loan.time.R;
import com.loan.time.adaperts.AuthorityRv;
import com.loan.time.adaperts.PlatRvAdapert;
import com.loan.time.mvp.MVPBaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 平台详情页面
 */
public class PlatformDetailsActivity extends MVPBaseActivity<PlatformInf.PlatformViewInf, PlatformPresenter> {

    @BindView(R.id.finish)
    ImageView finish;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tool_Bar)
    Toolbar toolBar;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.tv_rp)
    TextView tvRp;
    @BindView(R.id.home_view)
    View homeView;
    @BindView(R.id.home_rililv)
    View homeRililv;
    @BindView(R.id.tv_rililv)
    TextView tvRililv;
    @BindView(R.id.tv_rililv_number)
    TextView tvRililvNumber;
    @BindView(R.id.home_jisu)
    View homeJisu;
    @BindView(R.id.tv_jisu)
    TextView tvJisu;
    @BindView(R.id.tv_jisu_number)
    TextView tvJisuNumber;
    @BindView(R.id.home_daikuan)
    View homeDaikuan;
    @BindView(R.id.tv_cankao)
    TextView tvCankao;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_permission)
    TextView tvPermission;
    @BindView(R.id.platform_rv)
    RecyclerView platformRv;
    @BindView(R.id.tv_fangkuan)
    TextView tvFangkuan;
    @BindView(R.id.img_fang)
    ImageView imgFang;
    @BindView(R.id.bt_commit)
    TextView btCommit;
    private ArrayList<String> titleList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_platform_details;
    }

    @Override
    protected void initView() {
        super.initView();
        toolBar.setPadding(0, getHeight(), 0, 0);
        title.setText("产品名称");
        titleList.clear();
        titleList.add("年龄20～55周岁");
        titleList.add("无网贷逾期");
        titleList.add("手机实名6个月以上，负债2万以内");
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        platformRv.setNestedScrollingEnabled(false);
        platformRv.setLayoutManager(manager);
        PlatRvAdapert adapert = new PlatRvAdapert(titleList, this);
        platformRv.setAdapter(adapert);

    }

    @OnClick({R.id.finish, R.id.bt_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finish:
                finish();
                break;
            case R.id.bt_commit:
                break;
        }
    }
}
