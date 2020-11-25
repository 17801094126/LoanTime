package com.loan.time.ui.platformDetail;

import android.content.Intent;
import android.net.Uri;

import com.loan.time.mvp.BasePresenterImpl;


public class PlatformPresenter extends BasePresenterImpl<PlatformInf.PlatformViewInf> implements PlatformInf.PlatformPreInf {

    @Override
    public void checkOverdue(PlatformDetailsActivity context, String mobile,String url) {
        /*HomeHelper.checkOverdue(new HomeCallback.OverdueCallback() {

            @Override
            public void checkOverdueSuccess(ResponseBean responseBean) {
                ResponseBean.DataBean data = responseBean.getData();
                if (Code.Success.equals(responseBean.getCode())){
                    //data:0表示过期，1表示没过期，2表示不是会员
                    if ("0".equals(data.getCode())){
                        PreferenceUtil.commitString(GoSpendApp.OverdueUserName,data.getUserName());
                        PreferenceUtil.commitString(GoSpendApp.OverdueCode,data.getIdCard());
                        PreferenceUtil.commitString(GoSpendApp.OverduePhone,data.getMobile());
                        Intent intent = new Intent(context, RealNameActivity.class);
                        intent.putExtra(RealNameActivity.OverrdueCode,"0");
                        context.startActivity(intent);
                    }else if ("1".equals(data.getCode())){
                        HomeHelper.checkBlack(new HomeCallback.CheckBlackCallback() {
                            @Override
                            public void checkBlacSuccess(ResponseBean data) {
                                if ("0".equals(data.getData().getCheckBlacklist())){
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    context.startActivity(intent);
                                }else if ("1".equals(data.getData().getCheckBlacklist())){
                                    ResponseBean.DataBean data1 = data.getData();
                                    Intent intent = new Intent(context, CreditScoreActivity.class);
                                    intent.putExtra(CreditScoreActivity.BlackCode,data1.getCheckBlacklist());
                                    intent.putExtra(CreditScoreActivity.CreditGradle,data1.getGrade());
                                    intent.putExtra(CreditScoreActivity.CreditNews,data1.getCredit());
                                    intent.putExtra(CreditScoreActivity.CreditTime,data1.getTime());
                                    intent.putExtra(CreditScoreActivity.CreditCopy,data1.getCopywriting());
                                    context.startActivity(intent);
                                }
                            }

                            @Override
                            public void checkBlacError(String error) {
                                ToastUtils.showToast(context,error);
                            }
                        },mobile);
                    }else if ("2".equals(data.getCode())){
                        Intent intent = new Intent(context, RealNameActivity.class);
                        intent.putExtra(RealNameActivity.OverrdueCode,"2");
                        context.startActivity(intent);
                    }
                }else{
                    ToastUtils.showToast(context,responseBean.getMessage());
                }
            }

            @Override
            public void checkOverdueError(String error) {
                ToastUtils.showToast(context,error);
            }
        }, mobile);*/
    }
}
