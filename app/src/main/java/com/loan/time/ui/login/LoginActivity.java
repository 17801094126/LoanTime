package com.loan.time.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.internal.LinkedHashTreeMap;
import com.loan.time.App;
import com.loan.time.R;
import com.loan.time.api.NumberProgressBar;
import com.loan.time.bean.ResponseDecryptBean;
import com.loan.time.mvp.MVPBaseActivity;
import com.loan.time.ui.authority.AuthorityActivity;
import com.loan.time.ui.first.FirstActivity;
import com.loan.time.utils.ActivityCollector;
import com.loan.time.utils.AppUtils;
import com.loan.time.utils.BitmapUtils;
import com.loan.time.utils.DialogHelp;
import com.loan.time.utils.DownloadUtil;
import com.loan.time.utils.EmptyUtils;
import com.loan.time.utils.PhoneNumberCheck;
import com.loan.time.utils.PreferenceUtil;
import com.loan.time.utils.SingleClick;
import com.loan.time.utils.ToastUtils;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
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
    public String[] p = {
            //读取外部存储权限
            Manifest.permission.READ_EXTERNAL_STORAGE,
            //SD卡写入权限
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            //获取设备标识符权限
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        //设置App标题栏
        initToolBar();
        //获取权限
        initPermission();
    }

    /**
     * 设置App标题栏
     */
    private void initToolBar() {
        toolBar.setPadding(0, getHeight(), 0, 0);
        finish.setVisibility(View.GONE);
        title.setText("登录");
    }


    /**
     * 获取权限
     */
    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(p)
                .onGranted(permissions -> {
                    if (AndPermission.hasPermissions(LoginActivity.this, p)) {
                        //获取到权限
                        //获取App基本信息以及升级接口
                        mPresenter.getUpdate(this);
                    } else {
                        //未获取到权限
                        DialogHelp.showNormalDialog(this);
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasPermissions(LoginActivity.this, p)) {
                        //获取到权限
                        //获取App基本信息以及升级接口
                        mPresenter.getUpdate(this);
                    } else {
                        //未获取到权限
                        DialogHelp.showNormalDialog(this);
                    }
                })
                .start();


    }

    @SingleClick
    @OnClick({R.id.login_bt, R.id.tv1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                if (isAgree.isChecked()){
                    if (AndPermission.hasPermissions(LoginActivity.this, p)){
                        //获取到权限
                        initLogin();
                    }else{
                        //未获取到权限
                        initPermission();
                    }
                }else{
                    ToastUtils.showToast(this,"Please check 《Platform Service Agreement》");
                }
                break;
            case R.id.tv1:
                break;
        }
    }

    /**
     * 登录
     */
    private void initLogin() {
        phone = loginEdPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
        } else if (!PhoneNumberCheck.checkCellphone(phone)) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
        }else{
            mPresenter.getLoginImg(this,phone);
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
    private String loginId;
    private AlertDialog yzmDialog;
    private final int BOND = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BOND:
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    break;
            }
        }

    };

    private void initYzmDialog(String id,String image) {
        this.loginId=id;
        if (yzmDialog==null){
            yzmDialog=new AlertDialog.Builder(this).create();
            //自定义布局
            View view = getLayoutInflater().inflate(R.layout.dialog_login_yzm, null);
            //把自定义的布局设置到dialog中，注意，布局设置一定要在show之前。从第二个参数分别填充内容与边框之间左、上、右、下、的像素
            yzmDialog.setView(view, 0, 0, 0, 0);
            //一定要先show出来再设置dialog的参数，不然就不会改变dialog的大小了
            Window window = yzmDialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0));
            login_img_code=view.findViewById(R.id.login_img_code);
            login_img_code.setOnClickListener(v -> {
                mPresenter.getLoginImg(this,loginEdPhone.getText().toString().trim());
            });
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
        if (yzmDialog!=null&&!yzmDialog.isShowing()){
            yzmDialog.show();
            yzmDialog.setCanceledOnTouchOutside(false);
            handler.sendEmptyMessageDelayed(BOND,100);
        }
        if (!BitmapUtils.isBase64Img(image)){
            Bitmap bitmap = BitmapUtils.stringToBitmap(image);
            RequestOptions options=new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(this).load(bitmap).apply(options).into(login_img_code);
        }else{
            RequestOptions options=new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            //Glide 加载图片简单用法
            Glide.with(this).load(image).apply(options).into(login_img_code);
        }
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
                            if (EmptyUtils.isNotEmpty(codes) && codes.size() > 4) {
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
        if (codes.size() == 4) {
            //已经输入完成
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_code.getWindowToken(), 0);
            if (yzmDialog!=null)
                yzmDialog.dismiss();
            StringBuffer sb=new StringBuffer();
            for (int i = 0; i < codes.size(); i++) {
                sb.append(codes.get(i));
            }
            mPresenter.login(this,loginId,phone,sb.toString());
        }
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

    }
    /**
     * 显示输入的验证码
     */
    public void showEmptyCode() {
        view1_ind.setVisibility(View.VISIBLE);
        view2_ind.setVisibility(View.GONE);
        view3_ind.setVisibility(View.GONE);
        view4_ind.setVisibility(View.GONE);
        ed1.setText("");
        ed2.setText("");
        ed3.setText("");
        ed4.setText("");
        codes.clear();
        et_code.setText("");
    }

    @Override
    public void getLoginImg(String id,String image) {
        if (yzmDialog==null){
            initYzmDialog(id,image);
        }else{
            showEmptyCode();
            initYzmDialog(id,image);
        }
    }

    //初始化接口
    @Override
    public void getUpdate(ResponseDecryptBean dataBean) {
        ResponseDecryptBean.VersionInfoBean versionInfo = dataBean.getVersionInfo();
        //处理版本更新
        initVersion(versionInfo);
    }

    private String msg;
    //下载最新版本的地址
    private String downloadUrl;
    //版本
    private String version;
    //当前版本
    private String current;
    //是否强制更新
    private boolean state;
    //是否展示更新
    private boolean showUpgradeNotify;
    private File apkFile;

    private void initVersion(ResponseDecryptBean.VersionInfoBean versionInfo) {
        msg = versionInfo.getUpgradeMsg();
        String nextVersion = versionInfo.getNextVersion();
        if (nextVersion.contains("V") || nextVersion.contains("v")) {
            version = nextVersion.substring(1);
        } else {
            version = nextVersion;
        }
        current = App.getAppVersion();
        showUpgradeNotify = versionInfo.isShowUpgradeNotify();
        if (showUpgradeNotify){
            if (-1==App.compareVersion(current, version)) {
                Log.e("HomeActivity", "当前不是最新版本是否更新？" + current);
                state = versionInfo.isForceUpgrade();
                downloadUrl = versionInfo.getUpgradeUrl();
                //判断是否获取到权限
                if (!AndPermission.hasPermissions(this, p)) {
                    Boolean requestSD = PreferenceUtil.getBoolean("requestSD", false);
                    if (!requestSD) {
                        new AlertDialog.Builder(this)
                                .setTitle("提示：")
                                .setMessage("升级需要获取存储权限")
                                .setPositiveButton("ok", (dialog, which) -> {
                                    PreferenceUtil.commitBoolean("requestSD", true);
                                    initDialogPermission();
                                })
                                .create()
                                .show();
                    } else {
                        initDialogPermission();
                    }

                } else {
                    initDialog(msg);
                }
            }
        }
    }

    private void initDialogPermission(){
        AndPermission.with(this)
                .runtime()
                .permission(p)
                // .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (AndPermission.hasPermissions(LoginActivity.this, p)) {
                        //1、网络获取版本号，跟本地apk版本号比对,如果发现服务器版本号高于本地版本号即弹出对话框，提醒用户更新
                        //此处使用DownLoadManager开启下载任务
                        // 下载过程和下载完成后通知栏有通知消息。
                        //设置保存目录  /storage/emulated/0/Android/包名/files/Download
                        //注册内容观察者，实时显示进度
                        //广播监听下载完成
                        //弹出进度条，先隐藏前一个dialog
                        //显示进度的对话框
                        updateDialog(msg);
                    } else {
                        DialogHelp.showNormalDialog(this);
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasPermissions(LoginActivity.this, p)) {
                        //1、网络获取版本号，跟本地apk版本号比对,如果发现服务器版本号高于本地版本号即弹出对话框，提醒用户更新
                        //此处使用DownLoadManager开启下载任务
                        // 下载过程和下载完成后通知栏有通知消息。
                        //设置保存目录  /storage/emulated/0/Android/包名/files/Download
                        //注册内容观察者，实时显示进度
                        //广播监听下载完成
                        //弹出进度条，先隐藏前一个dialog
                        //显示进度的对话框
                        updateDialog(msg);
                    } else {
                        DialogHelp.showNormalDialog(this);
                    }
                })
                .start();
    }

    private void initDialog(String msg) {
        //1、网络获取版本号，跟本地apk版本号比对,如果发现服务器版本号高于本地版本号即弹出对话框，提醒用户更新
        //此处使用DownLoadManager开启下载任务
        // 下载过程和下载完成后通知栏有通知消息。
        //设置保存目录  /storage/emulated/0/Android/包名/files/Download
        //注册内容观察者，实时显示进度
        //广播监听下载完成
        //弹出进度条，先隐藏前一个dialog
        //显示进度的对话框
        if (TextUtils.isEmpty(msg)) {
            return;
        } else {
            updateDialog(msg);
        }
    }
    private void updateDialog(String message) {
        final AlertDialog build = new AlertDialog.Builder(this).create();
        //自定义布局
        View view = getLayoutInflater().inflate(R.layout.dialog_update, null);
        //把自定义的布局设置到dialog中，注意，布局设置一定要在show之前。从第二个参数分别填充内容与边框之间左、上、右、下、的像素
        build.setView(view, 0, 0, 0, 0);
        //一定要先show出来再设置dialog的参数，不然就不会改变dialog的大小了
        Window window = build.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        build.show();
        build.setCanceledOnTouchOutside(false);
        TextView tv_dialog_update_size = view.findViewById(R.id.tv_dialog_update_size);
        tv_dialog_update_size.setText("V" + version);
        TextView tv_dialog_update_content = view.findViewById(R.id.tv_dialog_update_content);
        TextView tv_cancel = view.findViewById(R.id.qiangzhi_cancel);
        TextView tv_updata = view.findViewById(R.id.qiangzhi_update);
        TextView tv_nowUpdata = view.findViewById(R.id.tv_dialog_update_update);
        NumberProgressBar progressBar = view.findViewById(R.id.pb_dialog_update_progress);
        TextView tv_xiazai = view.findViewById(R.id.tv_xiazai);
        LinearLayout linear_two_tip = view.findViewById(R.id.linear_qiangzhi);
        ImageView iv_dialog_update_close = view.findViewById(R.id.iv_dialog_update_close);
        LinearLayout ll_dialog_update_cancel = view.findViewById(R.id.ll_dialog_update_cancel);
        if (state) {
            //强制性更新
            tv_nowUpdata.setVisibility(View.VISIBLE);
            linear_two_tip.setVisibility(View.GONE);
            ll_dialog_update_cancel.setVisibility(View.GONE);
            build.setOnDismissListener(dialog -> {
                finish();
                ActivityCollector.finishAll();
                System.exit(0);
            });
        } else {
            //非强制性更新
            ll_dialog_update_cancel.setVisibility(View.VISIBLE);
            tv_nowUpdata.setVisibility(View.GONE);
            linear_two_tip.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(message)) {
            return;
        }
        tv_dialog_update_content.setText(message);
        tv_cancel.setOnClickListener(v -> {
            build.dismiss();
        });
        iv_dialog_update_close.setOnClickListener(v -> {
            build.dismiss();
        });
        tv_updata.setOnClickListener(v -> {
            initNewData(progressBar, tv_xiazai, linear_two_tip, tv_nowUpdata, downloadUrl);
        });
        tv_nowUpdata.setOnClickListener(v -> {
            initNewData(progressBar, tv_xiazai, linear_two_tip, tv_nowUpdata, downloadUrl);
        });
        tv_xiazai.setOnClickListener(v -> {
            if (apkFile != null)
                AppUtils.installApk(App.context, apkFile);
            else {
                ToastUtils.showToast(this, "解析包出现了问题请重新下载");
            }
        });
    }

    public void initNewData(NumberProgressBar progressBar, TextView tv_xiazai, LinearLayout linear_two_tip, TextView tv_nowUpdata, String downloadUrl) {
        progressBar.setVisibility(View.VISIBLE);
        linear_two_tip.setVisibility(View.GONE);
        tv_nowUpdata.setVisibility(View.GONE);
        tv_xiazai.setVisibility(View.VISIBLE);
        DownloadUtil.get().download(downloadUrl, "download", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(final String path) {
                runOnUiThread(() -> {
                    tv_xiazai.setText("下载完成");
                    PreferenceUtil.commitString(FirstActivity.TAG, path);
                    apkFile = new File(path);
                    AppUtils.installApk(App.context, apkFile);
                });
            }

            @Override
            public void onDownloading(int progress) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progress);
                    }
                });
            }

            @Override
            public void onDownloadFailed() {
                // proDialog.dismiss();
            }
        });
    }
}