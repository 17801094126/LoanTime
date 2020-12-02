package com.loan.time.ui.authority;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loan.time.App;
import com.loan.time.R;
import com.loan.time.adaperts.AuthorityRv;
import com.loan.time.bean.RvBean;
import com.loan.time.mvp.MVPBaseActivity;
import com.loan.time.ui.first.FirstActivity;
import com.loan.time.utils.PreferenceUtil;
import com.loan.time.utils.SingleClick;
import com.loan.time.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthorityActivity extends MVPBaseActivity<AuthorityContract.View, AuthorityPresenter> implements AuthorityContract.View {

    @BindView(R.id.finish)
    ImageView finish;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tool_Bar)
    Toolbar toolBar;
    @BindView(R.id.login_bt)
    TextView loginBt;
    @BindView(R.id.check_au)
    CheckBox checkAu;
    @BindView(R.id.tv_authority)
    TextView tvAuthority;
    @BindView(R.id.tv_hi)
    TextView tvHi;
    @BindView(R.id.tv_to)
    TextView tvTo;
    @BindView(R.id.auth_rv)
    RecyclerView authRv;
    private ArrayList<RvBean> mList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authority;
    }

    @Override
    protected void initView() {
        super.initView();
        toolBar.setPadding(0, getHeight(), 0, 0);
        finish.setVisibility(View.GONE);
        title.setText("Permission statement");
        checkAu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    loginBt.setBackground(ContextCompat.getDrawable(AuthorityActivity.this,R.drawable.login_bt_back));
                }else{
                    loginBt.setBackground(ContextCompat.getDrawable(AuthorityActivity.this,R.drawable.login_bt_back_no));
                }
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        mList.add(new RvBean(ContextCompat.getDrawable(this, R.drawable.sms), "SMS",
                "Collect and monitor only financial transaction SMS which includes name of transacting party, a description of" +
                        " the transaction and transaction amount for the purpose of performing a credit risk processFee. This credit" +
                        " risk processFee enables faster and quicker loan disbursal. No personal SMS are accessed, read or collected."));
        mList.add(new RvBean(ContextCompat.getDrawable(this, R.drawable.storage), "Storage", "This permission is required so that users loan account statements can " +
                "be securely downloaded and saved on users phone, and so that user can upload the" +
                " right documents for a faster approval and disbursal of the loan. Also, this helps" +
                " provide a very smooth seamless experience while using the app."));
        mList.add(new RvBean(ContextCompat.getDrawable(this, R.drawable.contacts), "Contacts", "The NBFC loan process and guidelines mandates us to verify \"references\". This feature enables us to do that & Why " +
                "type in contact number when you can just choose from your contact book"));
        mList.add(new RvBean(ContextCompat.getDrawable(this, R.drawable.location), "Location", "User location helps to locate and verify the address which helps in making better credit risk decisions and fasten the Know Your Customer (KYC) process."));
        mList.add(new RvBean(ContextCompat.getDrawable(this, R.drawable.camera), "Camera", "Camera access is required so that user can easily scan or capture identity documents, etc. and save time by allowing us to auto-fill the required data and helps in improving the user journey and experience."));
        mList.add(new RvBean(ContextCompat.getDrawable(this, R.drawable.phonestate), "Phone State", "Collect and monitor specific information about your phone like your phone's unique device identifier, user profile information as this information helps us in uniquely identifying a user, so that we can detect and prevent any unauthorized device from acting on your behalf. This enables us to detect fraud and manage risks better."));
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        authRv.setNestedScrollingEnabled(false);
        authRv.setLayoutManager(manager);
        AuthorityRv adapert = new AuthorityRv(mList, this);
        authRv.setAdapter(adapert);

    }

    @SingleClick
    @OnClick(R.id.login_bt)
    public void onViewClicked() {
        if (checkAu.isChecked()){
            PreferenceUtil.commitBoolean(App.IsAuthority,true);
            startActivity(new Intent(this, FirstActivity.class));
            finish();
        }else{
            //请您同意权限
            ToastUtils.showToast(this,"Please agree to the permissions！");
        }
    }
}