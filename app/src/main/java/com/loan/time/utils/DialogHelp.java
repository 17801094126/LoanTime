package com.loan.time.utils;

import android.app.Activity;

import com.loan.time.permission.AndPermission;

/**
 *
 * @author renji
 * @date 2018/1/16
 */

public class DialogHelp {

    public static void showNormalDialog(Activity context) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        android.app.AlertDialog.Builder normalDialog =
                new android.app.AlertDialog.Builder(context);
        normalDialog.setTitle("No permission to continue borrowing");
        normalDialog.setMessage("Go to system settings to open permissions");
        normalDialog.setPositiveButton("To authorize", (dialog, which) -> AndPermission.with(context)
                .runtime().setting().start(1));
        normalDialog.setNegativeButton("Close",
                (dialog, which) -> {
                    ToastUtils.showToast(context,"You give up access and cannot proceed to the next step！");
                });
        // 显示
        normalDialog.show();
    }
}