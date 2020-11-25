package com.loan.time.ui.platformDetail;

import com.loan.time.mvp.BaseView;

public interface PlatformInf {

    interface PlatformPreInf{
        /**
         * 查询会员是否过期
         * data:0表示过期，1表示没过期，2表示不是会员
         */
        void checkOverdue(PlatformDetailsActivity context, String mobile, String url);
    }

    interface PlatformViewInf extends BaseView {

    }
}
