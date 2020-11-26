package com.loan.time.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.loan.time.App;
import com.loan.time.BuildConfig;
import com.loan.time.R;
import com.loan.time.banner.LoanDialog;
import com.loan.time.bean.DecryptBean;
import com.loan.time.bean.RequestBean;
import com.loan.time.bean.ResponseBean;
import com.loan.time.bean.ResponseDecryptBean;
import com.loan.time.mvp.BasePresenterImpl;
import com.loan.time.network.HttpCode;
import com.loan.time.network.HttpUtils;
import com.loan.time.ui.authority.AuthorityActivity;
import com.loan.time.utils.AppUtils;
import com.loan.time.utils.PreferenceUtil;
import com.loan.time.utils.ToastUtils;

public class LoginPresenter  extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private LoanDialog loanDialog;
    private Gson gson = new Gson();

    /**
     * 获取用户基本信息以及App版本更新
     * @param context
     */
    @Override
    public void getUpdate(Context context) {
        loanDialog=LoanDialog.show(context,false);
        RequestBean requestBean = new RequestBean();
        requestBean.setDeviceToken("");
        requestBean.setDeviceId("");
        requestBean.setUid("0");
        //设置App基本信息
        RequestBean.DataBean dataBean = initDeviceInfo(context);
        requestBean.setDeviceInfo(dataBean);
        String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_a_0", gson.toJson(requestBean), "{}");
        ResponseBean responseBean = gson.fromJson(respone, ResponseBean.class);
        loanDialog.dismiss();
        if (HttpCode.CODE_SUCCESS.equals(responseBean.getCode())){
            PreferenceUtil.commitString(App.DeviceId,responseBean.getData().getDeviceId());
            PreferenceUtil.commitString(App.DeviceToken,responseBean.getData().getDeviceToken());
            try {
                DecryptBean decryptBean = new DecryptBean();
                byte[] decrypt = HttpUtils.getInstance().decrypt(gson.toJson(decryptBean), responseBean.getData().getConfig());
                String s = new String(decrypt);
                ResponseDecryptBean bean = new Gson().fromJson(s, ResponseDecryptBean.class);
                if (isAttarchView())
                    getView().getUpdate(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取图片验证码
     * @param context
     * @param mobilePhone
     */
    @Override
    public void getLoginImg(Context context,String mobilePhone) {
        loanDialog=LoanDialog.show(context,false);
        RequestBean requestBean = new RequestBean();
        requestBean.setMobile(mobilePhone);
        requestBean.setDeviceToken(PreferenceUtil.getString(App.DeviceToken,""));
        requestBean.setDeviceId(PreferenceUtil.getString(App.DeviceId,""));
        requestBean.setUid("0");
        String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_l_0", gson.toJson(requestBean), "{}");
        ResponseBean responseBean = gson.fromJson(respone, ResponseBean.class);
        loanDialog.dismiss();
        if (HttpCode.CODE_SUCCESS.equals(responseBean.getCode())){
            if (isAttarchView())
                getView().getLoginImg(responseBean.getData().getId(),responseBean.getData().getImage());
        }
    }

    /**
     * 登录
     * @param activity
     * @param id
     * @param imgCode
     */
    @Override
    public void login(LoginActivity activity, String id, String mobilePhone,String imgCode) {

        loanDialog=LoanDialog.show(activity,false);
        RequestBean requestBean = new RequestBean();
        requestBean.setMobile(mobilePhone);
        requestBean.setDeviceToken(PreferenceUtil.getString(App.DeviceToken,""));
        requestBean.setDeviceId(PreferenceUtil.getString(App.DeviceId,""));
        requestBean.setUid("0");
        requestBean.setCaptchaId(id);
        requestBean.setDigits(imgCode);
        requestBean.setDeviceInfo(initDeviceInfo(activity));
        String respone = HttpUtils.getInstance().sendRequest(BuildConfig.BASE_URL, "a_l_1", gson.toJson(requestBean), "{}");
        ResponseBean responseBean = gson.fromJson(respone, ResponseBean.class);
        loanDialog.dismiss();
        Log.e("QQQ",responseBean.toString());
        if (HttpCode.CODE_SUCCESS.equals(responseBean.getCode())){
            PreferenceUtil.commitString(App.Uid,responseBean.getData().getUid());
            PreferenceUtil.commitString(App.Token,responseBean.getData().getToken());
            PreferenceUtil.commitString(App.Phone,mobilePhone);
            activity.startActivity(new Intent(activity, AuthorityActivity.class));
            activity.finish();
        }else if (HttpCode.CODE_121002.equals(responseBean.getCode())){
            ToastUtils.showToast(activity,responseBean.getMessage());
            getLoginImg(activity,mobilePhone);
        }
    }


    /**
     * 设置App基本信息
     * @param context
     */
    private RequestBean.DataBean initDeviceInfo(Context context) {
        RequestBean.DataBean dataBean = new RequestBean.DataBean();
        //设备的软件版本号
        dataBean.setDeviceSoftwareVersion(AppUtils.getDeviceSoftwareVersion(context));
        //设置IMEI号
        dataBean.setImei(AppUtils.getIMEI(context));
        //设置IMSI号
        dataBean.setImsi(AppUtils.getIMSI(context));
        //设置系统语言
        dataBean.setLanguage(AppUtils.getLanguage(context));
        dataBean.setDisplay(AppUtils.getDisplay(context));
        dataBean.setNetworkOperator(AppUtils.getNetworkOperator(context));
        dataBean.setNetworkOperatorName(AppUtils.getNetworkOperatorName(context));
        dataBean.setNetworkType(AppUtils.getNetworkType(context));
        dataBean.setTotalMemory(AppUtils.getAllMemory());
        dataBean.setGsmCellLocation(AppUtils.getGsmCellLocation(context));
        if (TextUtils.isEmpty(AppUtils.getUUID(context))){
            dataBean.setUuid(AppUtils.getAndroidId(context));
        }else{
            dataBean.setUuid(AppUtils.getUUID(context));
        }
        return dataBean;
    }
}
