package me.justwork.videobackgrounddemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private MediaPlayer mp;
    private final static String TAG = "MainActivity_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        ((SurfaceView) findViewById(R.id.surfaceView)).getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        try {
            initPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPlayer() throws IOException {
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.video_bg);
        mp = new MediaPlayer();
        mp.setDataSource(this, video);
        mp.setDisplay(((SurfaceView) findViewById(R.id.surfaceView)).getHolder());
        mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        mp.prepare();
        mp.setLooping(true);
        mp.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        mp.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp!=null){
            mp.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp!=null){
            mp.stop();
        }
    }
}
