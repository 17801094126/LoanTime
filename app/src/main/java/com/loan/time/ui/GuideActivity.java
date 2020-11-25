package com.loan.time.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.loan.time.R;
import com.loan.time.banner.MZBannerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    private ArrayList<Integer> mList=new ArrayList<>();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                .statusBarAlpha(0.0f)  //状态栏透明度，不写默认0.0f
                .init();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


    }

}
