package com.release.simplex.ui.corleone_player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.release.simplex.R;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnSeekCompleteListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by corleone on 2018/11/17.
 */
public class CorleonePlayerView extends SurfaceView implements MediaPlayerListener {
    private static final String TAG = CorleonePlayerView.class.getName();
    public static final int VIDEO_LAYOUT_ORIGIN = 0;
    public static final int VIDEO_LAYOUT_SCALE = 1;
    public static final int VIDEO_LAYOUT_STRETCH = 2;
    public static final int VIDEO_LAYOUT_ZOOM = 3;
    public static final int PLAYER_RATION_LAND_FULL = 0;
    public static final int PLAYER_RATION_PORTRAIT_FULL = 1;
    public static final int PLAYER_RATION_4_3 = 2;
    public static final int PLAYER_RATION_16_9 = 3;
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_SUSPEND = 6;
    private static final int STATE_RESUME = 7;
    private static final int STATE_SUSPEND_UNSUPPORTED = 8;
    private static final int STATE_DESTROY = 9;
    private Uri mUri;
    private long mDuration;
    private int mCurrentState = STATE_IDLE;
    private int mTargetState = STATE_IDLE;
    public int mVideoLayout;
    private SurfaceHolder mSurfaceHolder = null;
    private IMediaPlayer mMediaPlayer = null;
    private int mVideoWidth;
    private int mVideoHeight;
    private int mVideoSarNum;
    private int mVideoSarDen;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private View mMediaBufferingIndicator;
    private OnCompletionListener mOnCompletionListener;
    private OnPreparedListener mOnPreparedListener;
    private OnErrorListener mOnErrorListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnInfoListener mOnInfoListener;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnControllerEventsListener mOnControllerEventsListener;
    private int mCurrentBufferPercentage;
    private long mSeekWhenPrepared;
    private boolean mCanPause = true;
    private boolean mCanSeekBack = true;
    private boolean mCanSeekForward = true;
    private Context mContext;


    OnVideoSizeChangedListener mSizeChangedListener = new OnVideoSizeChangedListener() {
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height,
                                       int sarNum, int sarDen) {
            LogUtils.i(TAG, "onVideoSizeChanged: ---width:" + width + "----height:" + height);
            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();
            mVideoSarNum = sarNum;
            mVideoSarDen = sarDen;
            if (mVideoWidth != 0 && mVideoHeight != 0) {
                setVideoLayout(mVideoLayout);
            }
        }
    };

    OnPreparedListener mPreparedListener = new OnPreparedListener() {
        public void onPrepared(IMediaPlayer mp) {
            LogUtils.d(TAG, "onPrepared");
            mCurrentState = STATE_PREPARED;
            mTargetState = STATE_PLAYING;
            if (mOnPreparedListener != null) {
                mOnPreparedListener.onPrepared(mMediaPlayer);
            }
            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();
            long seekToPosition = mSeekWhenPrepared;
            if (seekToPosition != 0) {
                seekTo(seekToPosition);
            }
            if (mVideoWidth != 0 && mVideoHeight != 0) {
                setVideoLayout(mVideoLayout);
                if (mSurfaceWidth == mVideoWidth
                        && mSurfaceHeight == mVideoHeight) {
                    if (mTargetState == STATE_PLAYING) {
                        start();
                    } else if (!isPlaying()
                            && (seekToPosition != 0 || getCurrentPosition() > 0)) {
                    }
                }
            } else if (mTargetState == STATE_PLAYING) {
                start();
            }
        }
    };

    private OnCompletionListener mCompletionListener = new OnCompletionListener() {
        public void onCompletion(IMediaPlayer mp) {
            LogUtils.d(TAG, "onCompletion");
            mCurrentState = STATE_PLAYBACK_COMPLETED;
            mTargetState = STATE_PLAYBACK_COMPLETED;
            if (mOnCompletionListener != null) {
                mOnCompletionListener.onCompletion(mMediaPlayer);
            }
        }
    };

    private OnErrorListener mErrorListener = new OnErrorListener() {
        public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
            LogUtils.i(TAG, "Error:" + framework_err + "   impl_err---:" + impl_err);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            if (mOnErrorListener != null) {
                if (mOnErrorListener.onError(mMediaPlayer, framework_err, impl_err)) {
                    return true;
                }
            }

            return true;
        }
    };

    private OnBufferingUpdateListener mBufferingUpdateListener = new OnBufferingUpdateListener() {
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            mCurrentBufferPercentage = percent;
            if (mOnBufferingUpdateListener != null) {
                mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
            }
        }
    };

    private OnInfoListener mInfoListener = new OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
            LogUtils.i(TAG, "onInfo--what: " + what + "\nextra:" + extra);
            if (mOnInfoListener != null) {
                mOnInfoListener.onInfo(mp, what, extra);
            } else if (mMediaPlayer != null) {
                if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    LogUtils.i(TAG, "onInfo: (MEDIA_INFO_BUFFERING_START)");
                    if (mMediaBufferingIndicator != null) {
                        mMediaBufferingIndicator.setVisibility(View.VISIBLE);
                    }
                } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    LogUtils.i(TAG, "onInfo: (MEDIA_INFO_BUFFERING_END)");
                    if (mMediaBufferingIndicator != null) {
                        mMediaBufferingIndicator.setVisibility(View.GONE);
                    }
                }
            }
            return true;
        }
    };

    private OnSeekCompleteListener mSeekCompleteListener = new OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            LogUtils.d(TAG, "onSeekComplete");
            if (mOnSeekCompleteListener != null) {
                mOnSeekCompleteListener.onSeekComplete(mp);
            }
        }
    };

    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//            LogUtils.i(TAG, "surfaceChanged: ");
            mSurfaceHolder = holder;
            if (mMediaPlayer != null) {
                mMediaPlayer.setDisplay(mSurfaceHolder);
            }
            mSurfaceWidth = w;
            mSurfaceHeight = h;
            boolean isValidState = (mTargetState == STATE_PLAYING);
            boolean hasValidSize = (mVideoWidth == w && mVideoHeight == h);
            if (mMediaPlayer != null && isValidState && hasValidSize) {
                if (mSeekWhenPrepared != 0) {
                    seekTo(mSeekWhenPrepared);
                }
                start();
            }
        }


        public void surfaceCreated(SurfaceHolder holder) {
//            LogUtils.i(TAG, "surfaceCreated: ");
            mSurfaceHolder = holder;
            if (mMediaPlayer != null && mCurrentState == STATE_SUSPEND
                    && mTargetState == STATE_RESUME) {
                mMediaPlayer.setDisplay(mSurfaceHolder);
                resume();
            } else {
                openVideo();
            }
        }


        public void surfaceDestroyed(SurfaceHolder holder) {
//            LogUtils.i(TAG, "surfaceDestroyed: ");
//            if (mCurrentState == STATE_IDLE) {
//                mSurfaceHolder = null;
//                release(true);
//            }
        }
    };


    public CorleonePlayerView(Context context) {
        super(context);
        initVideoView(context);
    }


    public CorleonePlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CorleonePlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initVideoView(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    public void setVideoLayout(int layout) {
        LayoutParams lp = getLayoutParams();
        Pair<Integer, Integer> res = ScreenResolution.getResolution(mContext);
        int windowWidth = res.first, windowHeight = res.second;
        float windowRatio = windowWidth / (float) windowHeight;//屏幕
        int sarNum = mVideoSarNum;
        int sarDen = mVideoSarDen;
        if (mVideoHeight > 0 && mVideoWidth > 0) {
            float videoRatio = ((float) (mVideoWidth)) / mVideoHeight;
            if (sarNum > 0 && sarDen > 0) {
                videoRatio = videoRatio * sarNum / sarDen;
            }
            mSurfaceHeight = mVideoHeight;
            mSurfaceWidth = mVideoWidth;
            if (VIDEO_LAYOUT_ORIGIN == layout && mSurfaceWidth < windowWidth
                    && mSurfaceHeight < windowHeight) {
                lp.width = (int) (mSurfaceHeight * videoRatio);
                lp.height = mSurfaceHeight;
            } else if (layout == VIDEO_LAYOUT_ZOOM) {
                lp.width = windowRatio > videoRatio ? windowWidth
                        : (int) (videoRatio * windowHeight);
                lp.height = windowRatio < videoRatio ? windowHeight
                        : (int) (windowWidth / videoRatio);
            } else if (layout == VIDEO_LAYOUT_SCALE) {
                lp.height = (int) (3.f / 4.f * getScreenWidth());
                lp.width = getScreenWidth();
            } else {
                boolean full = layout == VIDEO_LAYOUT_STRETCH;
                lp.width = (full || windowRatio < videoRatio) ? windowWidth
                        : (int) (videoRatio * windowHeight);
                lp.height = (full || windowRatio > videoRatio) ? windowHeight
                        : (int) (windowWidth / videoRatio);
            }
            setLayoutParams(lp);
            getHolder().setFixedSize(mSurfaceWidth, mSurfaceHeight);
//            LogUtils.i(TAG, "mVideoWidth:" + mVideoWidth + "\nmVideoHeight:" + mVideoHeight + "\nvideoRatio:" + videoRatio
//                    + "\nmVideoSarNum:" + mVideoSarNum + "\nmVideoSarDen:" + mVideoSarDen + "\nmSurfaceWidth:" + mSurfaceWidth + "\nmSurfaceHeight:" + mSurfaceHeight
//                    + "\nlp.width:" + lp.width + "\nlp.height:" + lp.height + "\nwindowWidth:" + windowWidth + "\nwindowHeight：" + windowHeight + "\nwindowRatio:" + windowRatio);
        }
        mVideoLayout = layout;
    }

    public void setPlayRatio(int playerRatio) {

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        switch (playerRatio) {
            case PLAYER_RATION_LAND_FULL:
                Log.i(TAG, "PLAYER_RATION_LAND_FULL----------------------: ");
                layoutParams.topMargin = 0;
                layoutParams.height = (int) (3.f / 4.f * getScreenWidth());
                layoutParams.width = getScreenWidth();
                layoutParams.gravity = Gravity.CENTER;
                setLayoutParams(layoutParams);
                break;
            case PLAYER_RATION_PORTRAIT_FULL:
                Log.i(TAG, "PLAYER_RATION_PORTRAIT_FULL----------------------: ");
                layoutParams.topMargin = 0;
                layoutParams.height = getScreenHeight();
                layoutParams.width = getScreenWidth();
                setLayoutParams(layoutParams);
                break;
            case PLAYER_RATION_4_3:
                layoutParams.topMargin = mContext.getResources().getDimensionPixelSize(R.dimen.view_play_screen_top);
                layoutParams.height = (int) (3.f / 4.f * getScreenWidth());
                layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
                layoutParams.gravity = Gravity.NO_GRAVITY;
                setLayoutParams(layoutParams);
                break;
            case PLAYER_RATION_16_9:
                Log.i(TAG, "PLAYER_RATION_16_9----------------------: ");
                layoutParams.topMargin = mContext.getResources().getDimensionPixelSize(R.dimen.view_play_screen_top);
                layoutParams.height = (int) (9.f / 16.f * getScreenWidth());
                layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
                setLayoutParams(layoutParams);
                break;
        }
    }


    public int getScreenWidth() {
        WindowManager manager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }

    public int getScreenHeight() {
        WindowManager manager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
    }

    private void initVideoView(Context ctx) {
        mContext = ctx;
        mVideoWidth = 0;
        mVideoHeight = 0;
        mVideoSarNum = 0;
        mVideoSarDen = 0;
        getHolder().addCallback(mSHCallback);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        mCurrentState = STATE_IDLE;
        mTargetState = STATE_IDLE;
        if (ctx instanceof Activity) {
            ((Activity) ctx).setVolumeControlStream(AudioManager.STREAM_MUSIC);
        }
    }


    public boolean isValid() {
        return (mSurfaceHolder != null && mSurfaceHolder.getSurface().isValid());
    }


    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }


    public void setVideoURI(Uri uri) {
        mUri = uri;
        mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
    }

    public void stopPlayback() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
            mTargetState = STATE_IDLE;
        }
    }


    public void openVideo() {
        if (mUri == null || mSurfaceHolder == null) {
            return;
        }
        Intent i = new Intent("com.android.music.musicservicecommand");
        i.putExtra("command", "pause");
        mContext.sendBroadcast(i);
        release(false);
        try {
            mDuration = -1;
            mCurrentBufferPercentage = 0;
            // mMediaPlayer = new AndroidMediaPlayer();
            IjkMediaPlayer ijkMediaPlayer = null;
            if (mUri != null) {
                ijkMediaPlayer = new IjkMediaPlayer();
                ijkMediaPlayer.setLogEnabled(false);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec_mpeg4", 1);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "videotoolbox", 1);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 8);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "probesize", 10240L);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzemaxduration", 100L);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "flush_packets", 1L);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0L);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1L);
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-fps", 30);
            }
            mMediaPlayer = ijkMediaPlayer;
            assert mMediaPlayer != null;

//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
            mMediaPlayer.setOnErrorListener(mErrorListener);
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            mMediaPlayer.setOnInfoListener(mInfoListener);
            mMediaPlayer.setOnSeekCompleteListener(mSeekCompleteListener);
            if (mUri != null) {
                mMediaPlayer.setDataSource(mUri.toString());
            }
            mMediaPlayer.setDisplay(mSurfaceHolder);
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.prepareAsync();
            mCurrentState = STATE_PREPARING;
        } catch (IOException | IllegalArgumentException ex) {
            LogUtils.e(TAG, "Unable to open content: " + mUri);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            mErrorListener.onError(mMediaPlayer, IMediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
        }
    }

    public void setMediaBufferingIndicator(View mediaBufferingIndicator) {
        if (mMediaBufferingIndicator != null) {
            mMediaBufferingIndicator.setVisibility(View.GONE);
        }
        mMediaBufferingIndicator = mediaBufferingIndicator;
    }

    public void setOnPreparedListener(OnPreparedListener l) {
        mOnPreparedListener = l;
    }

    public void setOnCompletionListener(OnCompletionListener l) {
        mOnCompletionListener = l;
    }

    public void setOnErrorListener(OnErrorListener l) {
        mOnErrorListener = l;
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener l) {
        mOnBufferingUpdateListener = l;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener l) {
        mOnSeekCompleteListener = l;
    }

    public void setOnInfoListener(OnInfoListener l) {
        mOnInfoListener = l;
    }

    public void setOnControllerEventsListener(OnControllerEventsListener l) {
        mOnControllerEventsListener = l;
    }

    public void release(boolean cleartargetstate) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
            if (cleartargetstate) {
//                LogUtils.i(TAG, "release---------------------: ");
                mSurfaceHolder = null;
                mTargetState = STATE_IDLE;
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }


    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isKeyCodeSupported = keyCode != KeyEvent.KEYCODE_BACK
                && keyCode != KeyEvent.KEYCODE_VOLUME_UP
                && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN
                && keyCode != KeyEvent.KEYCODE_MENU
                && keyCode != KeyEvent.KEYCODE_CALL
                && keyCode != KeyEvent.KEYCODE_ENDCALL;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void start() {
        if (isInPlaybackState()) {
            mMediaPlayer.start();
            mCurrentState = STATE_PLAYING;
        }
        mTargetState = STATE_PLAYING;
        mOnControllerEventsListener.OnVideoResume();
    }

    @Override
    public void pause() {
        if (isInPlaybackState()) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mCurrentState = STATE_PAUSED;
            }
        }
        mTargetState = STATE_PAUSED;
        mOnControllerEventsListener.onVideoPause();
    }

    public void onDestroy() {
        mCurrentState = STATE_DESTROY;
    }


    public void resume() {
        if (mSurfaceHolder == null && mCurrentState == STATE_SUSPEND) {
            mTargetState = STATE_RESUME;
        } else if (mCurrentState == STATE_SUSPEND_UNSUPPORTED) {
            openVideo();
        }
    }


    @Override
    public int getDuration() {
        if (isInPlaybackState()) {
            if (mDuration > 0) {
                return (int) mDuration;
            }
            mDuration = mMediaPlayer.getDuration();
            return (int) mDuration;
        }
        mDuration = -1;
        return (int) mDuration;
    }

    @Override
    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            long position = mMediaPlayer.getCurrentPosition();
            return (int) position;
        }
        return 0;
    }


    @Override
    public void seekTo(long msec) {
        if (isInPlaybackState()) {
            mMediaPlayer.seekTo(msec);
            mSeekWhenPrepared = 0;
        } else {
            mSeekWhenPrepared = msec;
        }
    }


    @Override
    public boolean isPlaying() {
        return isInPlaybackState() && mMediaPlayer.isPlaying();
    }


    @Override
    public int getBufferPercentage() {
        if (mMediaPlayer != null) {
            return mCurrentBufferPercentage;
        }
        return 0;
    }

    public int getVideoWidth() {
        return mVideoWidth;
    }

    public int getVideoHeight() {
        return mVideoHeight;
    }

    protected boolean isInPlaybackState() {
        return (mMediaPlayer != null && mCurrentState != STATE_ERROR
                && mCurrentState != STATE_IDLE && mCurrentState != STATE_PREPARING);
    }

    public boolean canPause() {
        return mCanPause;
    }

    public boolean canSeekBackward() {
        return mCanSeekBack;
    }

    public boolean canSeekForward() {
        return mCanSeekForward;
    }

    public interface OnControllerEventsListener {
        void onVideoPause();

        void OnVideoResume();
    }
}
