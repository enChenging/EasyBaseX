package com.release.simplex.ui.act;

import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.release.easybasex.base.BaseActivity;
import com.release.simplex.R;
import com.release.simplex.ui.corleone_player.CircleProgressView;
import com.release.simplex.ui.corleone_player.CorleonePlayerView;
import com.release.simplex.utils.Constants;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;


/**
 * @author Mr.release
 * @create 2021/3/05
 * @Describe
 */
public class IjkPlayerActivity extends BaseActivity {
    private final String TAG = IjkPlayerActivity.class.getSimpleName();
    @BindView(R.id.video_view)
    CorleonePlayerView mVideoView;
    @BindView(R.id.bg_layout)
    FrameLayout mBgLayout;
    @BindView(R.id.et_url)
    EditText mEtUrl;
    @BindView(R.id.btn_play)
    Button mBtnPlay;
    @BindView(R.id.video_loading_progress)
    CircleProgressView mVideoLoadingProgress;
    @BindView(R.id.buffering_indicator)
    RelativeLayout mBufferingIndicator;
    @BindView(R.id.root_view)
    ConstraintLayout mRootView;
    private int mPlayScreen = Constants.NO_FULL_SCREEN_PLAY;
    private int mCurrentOrientation;
    private boolean autoRefresh;
    private boolean isStartLive;
    boolean playing;
    private String pullUrl = "rtmp://58.200.131.2:1935/livetv/cctv1";

    @Override
    public int getLayoutId() {
        return R.layout.activity_ijkplayer;
    }

    @Override
    public void initView() {
        mTopBar.setTitle("ijk内核播放器");

        mCurrentOrientation = this.getResources().getConfiguration().orientation;
        initParam();

    }

    private void initParam() {
        if (mVideoView != null) {
            mVideoView.setVisibility(View.VISIBLE);
            mVideoView.setMediaBufferingIndicator(mRootView);
            mVideoView.requestFocus();
            if (mPlayScreen == Constants.NO_FULL_SCREEN_PLAY && mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                LogUtils.i(TAG, "播放状态--------4_3--------------: ");
                mVideoView.mVideoLayout = CorleonePlayerView.VIDEO_LAYOUT_SCALE;
                mVideoView.setPlayRatio(CorleonePlayerView.PLAYER_RATION_4_3);//不全屏播放
            } else if (mPlayScreen == Constants.FULL_SCREEN_PLAY && mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                LogUtils.i(TAG, "播放状态--------全屏播放--------------: ");
                mVideoView.mVideoLayout = CorleonePlayerView.VIDEO_LAYOUT_STRETCH;
                mVideoView.setPlayRatio(CorleonePlayerView.PLAYER_RATION_PORTRAIT_FULL);//全屏播放
            } else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                LogUtils.i(TAG, "播放状态--------全屏播放--------------: ");
                mVideoView.setPlayRatio(CorleonePlayerView.PLAYER_RATION_LAND_FULL);//全屏播放
            }
            mVideoView.setVideoPath(pullUrl);
        }

    }

    @Override
    public void initListener() {
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                LogUtils.i(TAG, "Prepare: " + iMediaPlayer);
                autoRefresh = false;

                isStartLive = true;
                mVideoView.setVisibility(View.VISIBLE);
            }
        });

        mVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
//                LogUtils.i(TAG, "onInfo: " + what);
                if (what == IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED) {
                    if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                        mVideoView.setPlayRatio(CorleonePlayerView.PLAYER_RATION_LAND_FULL);
                    } else if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT && mPlayScreen == Constants.NO_FULL_SCREEN_PLAY) {
                        mVideoView.mVideoLayout = CorleonePlayerView.VIDEO_LAYOUT_SCALE;
                        mVideoView.setPlayRatio(CorleonePlayerView.PLAYER_RATION_4_3);
                    } else if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT && mPlayScreen == Constants.FULL_SCREEN_PLAY) {
                        mVideoView.mVideoLayout = CorleonePlayerView.VIDEO_LAYOUT_STRETCH;
                        mVideoView.setPlayRatio(CorleonePlayerView.PLAYER_RATION_PORTRAIT_FULL);//全屏播放
                    }
                }
                return true;
            }
        });
        mVideoView.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(IMediaPlayer mp) {
                LogUtils.i(TAG, "onSeekComplete: " + mp);
            }
        });
        mVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                LogUtils.w(TAG, "VideoPlay--onCompletion: ");
                LogUtils.d(TAG, "onCompletion%%%%%%unLine%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            }
        });
        mVideoView.setOnControllerEventsListener(new CorleonePlayerView.OnControllerEventsListener() {
            @Override
            public void onVideoPause() {
                LogUtils.i(TAG, "onVideoPause: ");
            }

            @Override
            public void OnVideoResume() {
                LogUtils.i(TAG, "OnVideoResume: ");
            }
        });
        mVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                LogUtils.e(TAG, "onError: " + i);
                autoRefresh = true;
                return false;
            }
        });
        mVideoView.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

            }
        });


        if (mVideoView != null)
            playing = mVideoView.isPlaying();
        LogUtils.i(TAG, "successChatRoom:----------startLiveUrl----------------");
    }


    @OnClick(R.id.btn_play)
    public void onClick() {

        if (TextUtils.isEmpty(mEtUrl.getText())) return;


        if (!TextUtils.isEmpty(pullUrl))
            initParam();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mVideoView.release(true);
    }


}
