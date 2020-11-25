package com.loan.time.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.loan.time.R;
import com.loan.time.mvp.MVPBaseActivity;
import com.loan.time.ui.authority.AuthorityActivity;
import com.loan.time.utils.EmptyUtils;
import com.loan.time.utils.PhoneNumberCheck;
import com.loan.time.utils.SingleClick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @BindView(R.id.finish)
    ImageView finish;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tool_Bar)
    Toolbar toolBar;
    @BindView(R.id.tv_login1)
    TextView tvLogin1;
    @BindView(R.id.tv_login2)
    TextView tvLogin2;
    @BindView(R.id.tv_login3)
    TextView tvLogin3;
    @BindView(R.id.login_phone_back)
    View loginPhoneBack;
    @BindView(R.id.login_bt)
    TextView loginBt;
    @BindView(R.id.isAgree)
    CheckBox isAgree;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.login_ed_phone)
    EditText loginEdPhone;
    private String phone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        toolBar.setPadding(0, getHeight(), 0, 0);
        finish.setVisibility(View.GONE);
        title.setText("登录");
        mPresenter.getUpdate(this);

    }

    @SingleClick
    @OnClick({R.id.login_bt, R.id.tv1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                phone = loginEdPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (!PhoneNumberCheck.checkCellphone(phone)) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }else{
                    mPresenter.getLoginImg(this,phone);
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                    initYzmDialog();
                }
                break;
            case R.id.tv1:
                break;
        }
    }

    private EditText et_code;
    private ImageView login_img_code;
    private View  view1_ind;
    private TextView ed1;
    private View  view2_ind;
    private TextView ed2;
    private View  view3_ind;
    private TextView ed3;
    private View  view4_ind;
    private TextView ed4;
    private List<String> codes = new ArrayList<>();




    private void initYzmDialog() {
        AlertDialog dialog=new AlertDialog.Builder(this).create();
        //自定义布局
        View view = getLayoutInflater().inflate(R.layout.dialog_login_yzm, null);
        //把自定义的布局设置到dialog中，注意，布局设置一定要在show之前。从第二个参数分别填充内容与边框之间左、上、右、下、的像素
        dialog.setView(view, 0, 0, 0, 0);
        //一定要先show出来再设置dialog的参数，不然就不会改变dialog的大小了
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        login_img_code=view.findViewById(R.id.login_img_code);
        view1_ind=view.findViewById(R.id.view1_ind);
        ed1=view.findViewById(R.id.ed1);
        view2_ind=view.findViewById(R.id.view2_ind);
        ed2=view.findViewById(R.id.ed2);
        view3_ind=view.findViewById(R.id.view3_ind);
        ed3=view.findViewById(R.id.ed3);
        view4_ind=view.findViewById(R.id.view4_ind);
        ed4=view.findViewById(R.id.ed4);
        et_code=view.findViewById(R.id.et_code);
        et_code.requestFocus();
        et_code.setLongClickable(true);//支持长按
        initEvent();
    }

    private void initEvent() {
        //验证码输入
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0) {
                    et_code.setText("");
                    if (codes.size() < 4) {
                        String data = editable.toString().trim();
                        if (data.length() >= 4) {
                            //将string转换成List
                            List<String> list = Arrays.asList(data.split(""));
                            //Arrays.asList没有实现add和remove方法，继承的AbstractList，需要将list放进java.util.ArrayList中
                            codes = new ArrayList<>(list);
                            if (EmptyUtils.isNotEmpty(codes) && codes.size() > 6) {
                                //使用data.split("")方法会将""放进第一下标里需要去掉
                                codes.remove(0);
                            }
                        } else {
                            codes.add(data);
                        }
                        showCode();
                    }
                }
            }
        });
        // 监听验证码删除按键
        et_code.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (EmptyUtils.isNotEmpty(codes)) {
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && codes.size() > 0) {
                    codes.remove(codes.size() - 1);
                    showCode();
                    return true;
                }
            }
            return false;
        });
    }

    /**
     * 显示输入的验证码
     */
    private void showCode() {
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        if (codes.size()<=0){
            view1_ind.setVisibility(View.VISIBLE);
            view2_ind.setVisibility(View.GONE);
            view3_ind.setVisibility(View.GONE);
            view4_ind.setVisibility(View.GONE);
        }
        if (codes.size() >= 1) {
            code1 = codes.get(0);
            view1_ind.setVisibility(View.GONE);
            view2_ind.setVisibility(View.VISIBLE);
            view3_ind.setVisibility(View.GONE);
            view4_ind.setVisibility(View.GONE);
        }
        if (codes.size() >= 2) {
            code2 = codes.get(1);
            view1_ind.setVisibility(View.GONE);
            view2_ind.setVisibility(View.GONE);
            view3_ind.setVisibility(View.VISIBLE);
            view4_ind.setVisibility(View.GONE);
        }
        if (codes.size() >= 3) {
            code3 = codes.get(2);
            view1_ind.setVisibility(View.GONE);
            view2_ind.setVisibility(View.GONE);
            view3_ind.setVisibility(View.GONE);
            view4_ind.setVisibility(View.VISIBLE);
        }
        if (codes.size() >= 4) {
            code4 = codes.get(3);
            view1_ind.setVisibility(View.GONE);
            view2_ind.setVisibility(View.GONE);
            view3_ind.setVisibility(View.GONE);
            view4_ind.setVisibility(View.GONE);
        }
        ed1.setText(code1);
        ed2.setText(code2);
        ed3.setText(code3);
        ed4.setText(code4);
        if (codes.size() == 4) {
            //已经输入完成
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_code.getWindowToken(), 0);
            startActivity(new Intent(LoginActivity.this, AuthorityActivity.class));
            finish();
        } else {
            //正在输入
        }
    }

    /**
     * 显示输入的验证码
     */
    public void showEmptyCode() {
        ed1.setText("");
        ed2.setText("");
        ed3.setText("");
        ed4.setText("");
        codes.clear();
        et_code.setText("");
    }

    @Override
    public void getLoginImg() {

    }
}