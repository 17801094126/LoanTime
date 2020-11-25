package com.loan.time.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.loan.time.R;
import com.loan.time.ui.login.LoginActivity;

import butterknife.ButterKnife;

public class SplashActivity extends Activity {
    /**
     * 用Handler做定时器，2秒后跳转。
     */
    private Handler handler = new Handler();
    private int timer = 2;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (timer == 0) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                timer--;
                handler.postDelayed(runnable, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置竖屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        handler.postDelayed(runnable, 2000);

    }

}
