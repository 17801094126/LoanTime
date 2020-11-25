package com.scwang.smart.refresh.layout.api;

import androidx.annotation.NonNull;

import com.scwang.smart.refresh.layout.constant.SpinnerStyle;


/**
 * 刷新内部组件
 * Created by scwang on 2017/5/26.
 */
public interface RefreshInternal extends RefreshComponent {

    /**
     * @return 变换方式
     */
    @NonNull
    SpinnerStyle getSpinnerStyle();
}
