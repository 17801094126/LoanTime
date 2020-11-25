package com.loan.time.banner;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;

import com.loan.time.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

@Deprecated
public class LoanDialog extends AlertDialog {


    @BindView(R.id.aviLoading)
    AVLoadingIndicatorView aviLoading;

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

    public static ProgressDialog show(Context context) {
        return show(context, false);
    }


    public static ProgressDialog show(Context context,  boolean indeterminate) {
        return show(context,indeterminate, false, null);
    }

    public static ProgressDialog show(Context context, boolean indeterminate, boolean cancelable) {
        return show(context, indeterminate, cancelable, null);
    }

    public static ProgressDialog show(Context context,  boolean indeterminate,
                                      boolean cancelable, OnCancelListener cancelListener) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_dialog);
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
