package com.loan.time.ui.my;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.loan.time.App;
import com.loan.time.R;
import com.loan.time.mvp.MVPBaseFragment;
import com.loan.time.ui.first.FirstActivity;

import butterknife.BindView;

public class MyFragment extends MVPBaseFragment<MyContract.View, MyPresenter> implements MyContract.View {

    @BindView(R.id.finish)
    ImageView finish;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tool_Bar)
    Toolbar toolBar;
    @BindView(R.id.my_img)
    ImageView myImg;
    @BindView(R.id.my_top)
    TextView myTop;
    @BindView(R.id.my_version)
    TextView myVersion;
    @BindView(R.id.tv1)
    TextView tv1;
    FirstActivity activity;

    @Override
    public int getLayoutId() {
        activity= (FirstActivity) getActivity();
        return R.layout.fragment_my;
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        toolBar.setPadding(0, getHeight(), 0, 0);
        finish.setVisibility(View.GONE);
        title.setText("My");
        myVersion.setText("V"+ App.getAppVersion());
    }
}
