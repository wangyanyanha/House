package com.fangdichan.house.views;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.fangdichan.house.R;

import java.io.IOException;

//import android.widget.MediaController;


/**
 * TODO: document your custom view class.
 */
public class MediaView extends RelativeLayout {

    Activity context;
    int height,width;

    ImageView play;


    MediaPlayer mPlayer;
    SurfaceView mSurfaceView;
    Controller mController;
    SurfaceHolder.Callback mCallback;
    MediaPlayer.OnPreparedListener mPreparedListener;
    Controller.ControlOper mPlayerControl;


    public MediaView(Activity context) {
        super(context);
        init(context,null, 0);
    }

    public MediaView(Activity context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs, 0);
    }

    public MediaView(Activity context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs, defStyle);
    }


    private void init(final Activity context,AttributeSet attrs, int defStyle) {
        LayoutInflater.from(context).inflate(R.layout.view_media, this);
        this.context=context;

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();

        height = (int) (d.getHeight());
        width = (int) (d.getWidth());

        FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.width=width;
        params.height=width*1080/1920;


        initListeners();

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mSurfaceView.setLayoutParams(params);
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(mCallback);
        mPlayer = new MediaPlayer();
        mController = new Controller(context,false);

        try {

            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(context, Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video));
            mPlayer.setOnPreparedListener(mPreparedListener);
            mPlayer.prepareAsync();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


}


    private void releaseMediaPlayer() {

        mController.removeHandlerCallback();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        mController.show();
        return false;
    }


    private void initListeners() {

        mCallback = new SurfaceHolder.Callback() {

            public void surfaceCreated(SurfaceHolder holder) {
                mPlayer.setDisplay(holder);
            }

            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {

            }

            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        };

        mPreparedListener = new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {

                mController.setMediaPlayer(mPlayerControl);
                mController.setAnchorView((FrameLayout)findViewById(R.id.surfacecontainer));
                mPlayer.start();
            }
        };

        mPlayerControl = new Controller.ControlOper() {

            public void start() {
                mPlayer.start();
            }

            public void pause() {
                mPlayer.pause();
            }

            public int getDuration() {
                return mPlayer.getDuration();
            }

            public int getCurPosition() {
                return mPlayer.getCurrentPosition();
            }

            public void seekTo(int pos) {
                mPlayer.seekTo(pos);
            }

            public boolean isPlaying() {
                return mPlayer.isPlaying();
            }

            public int getBufPercent() {
                return 0;
            }

            public boolean canPause() {
                return true;
            }

            public boolean canSeekBackward() {
                return true;
            }

            public boolean canSeekForward() {
                return true;
            }

            public boolean isFullScreen() {
                return false;
            }

            public void fullScreen() {

            }
        };
    }


    public void destroy() {
        releaseMediaPlayer();
    }

    public void pause() {
        mPlayer.pause();
    }


}
