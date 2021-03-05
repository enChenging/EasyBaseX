package com.release.simplex.ui.corleone_player;
/**
 * Created by corleone on 2018/11/17.
 */
public interface MediaPlayerListener {
    void start();

    void pause();

    int getDuration();

    int getCurrentPosition();

    void seekTo(long pos);

    boolean isPlaying();

    int getBufferPercentage();

    boolean canPause();
}
