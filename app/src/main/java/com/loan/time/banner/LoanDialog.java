package com.loan.time.banner;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

import com.loan.time.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoanDialog extends AlertDialog {


    @BindView(R.id.aviLoading)
    AVLoadingIndicatorView aviLoading;
    public static View view;

    /**
     * Creates a Progress dialog.
     *
     * @param context the parent context
     */
    public LoanDialog(Context context) {
        super(context);
    }

    /**
     * Creates a Progress dialog.
     *
     * @param context the parent context
     * @param theme   the resource ID of the theme against which to inflate
     *                this dialog, or {@code 0} to use the parent
     *                {@code context}'s default alert dialog theme
     */
    public LoanDialog(Context context, int theme) {
        super(context, theme);
    }



    public static LoanDialog show(Context context) {
        return show(context, false, null);
    }

    public static LoanDialog show(Context context, boolean cancelable) {
        return show(context, cancelable, null);
    }

    public static LoanDialog show(Context context,  boolean cancelable, OnCancelListener cancelListener) {
        LoanDialog dialog = new LoanDialog(context);

        //把自定义的布局设置到dialog中，注意，布局设置一定要在show之前。从第二个参数分别填充内容与边框之间左、上、右、下、的像素
        dialog.setView(view, 0, 0, 0, 0);
        //一定要先show出来再设置dialog的参数，不然就不会改变dialog的大小了
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_dialog);
        //自定义布局
        view = getLayoutInflater().inflate(R.layout.dialog_update, null);
        //绑定ButterKnifet
        ButterKnife.bind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        aviLoading.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        aviLoading.hide();
    }

}
