package com.release.simplex.ui.act;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.release.easybasex.base.BaseActivity;
import com.release.simplex.R;
import com.release.simplex.widget.WaveDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by corleone on 2018/1/18.
 */

public class TimerAnimationActivity extends BaseActivity {
    private final String TAG = TimerAnimationActivity.class.getSimpleName();
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.iv_back_bg)
    ImageView mIvBackBg;
    @BindView(R.id.iv_front_bg)
    ImageView mIvFrontBg;

    private WaveDrawable mWaveDrawable;
    private CountDownTimer mSdt;


    @Override
    public int getLayoutId() {
        return R.layout.activity_timer_animation;
    }

    @Override
    public void initView() {
        mTopBar.setTitle("送礼物倒计时按钮");
        countDown();
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
//        loadAni();
        mSdt.cancel();
        mSdt.start();
    }

    private void countDown() {

        mSdt = new CountDownTimer(3000, 10) {

            @Override
            public void onTick(long millisUntilFinished) {
                mWaveDrawable.setLevel((int) ((millisUntilFinished / 3000.0) * 10000));
            }

            @Override
            public void onFinish() {

                Toast.makeText(TimerAnimationActivity.this, "完成", Toast.LENGTH_SHORT).show();
            }
        };

        mWaveDrawable = new WaveDrawable(this, R.mipmap.gift_count_down_back_bg);
        mIvBackBg.setImageDrawable(mWaveDrawable);
        //是否自动播放
        mWaveDrawable.setIndeterminate(false);
        //上下波动幅度30
        mWaveDrawable.setWaveAmplitude(10);
        //水平波动幅度500
        mWaveDrawable.setWaveLength(500);
        //10
        mWaveDrawable.setWaveSpeed(10);
    }

    private void loadAni() {
        ScaleAnimation anim1 = new ScaleAnimation(
                Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF,
                Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF);
        anim1.setDuration(3000);
        anim1.setRepeatMode(Animation.REVERSE);
        mIvBackBg.startAnimation(anim1);
    }
}
