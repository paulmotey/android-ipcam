package cz.janchvala.android.ipcamera.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.widget.Toast;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspServer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import cz.janchvala.android.ipcamera.R;


/**
 * A straightforward example of how to use the RTSP server included in libstreaming.
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends ToolbarBaseActivity implements Session.Callback, Camera.PreviewCallback, SurfaceHolder.Callback {

    @ViewById(R.id.surface)
    protected SurfaceView mSurfaceView;

    @OptionsItem(R.id.action_settings)
    public void onShowSettingClick() {
        // Handle action bar setting item clicks here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @AfterViews
    protected void initializeSession() {
        Log.d("RTSP-callback", "initializeSession");

        mToolbarMain.setLogo(R.drawable.ic_launcher);

        // Configures the SessionBuilder
        SessionBuilder.getInstance()
                .setSurfaceView(mSurfaceView)
                .setPreviewOrientation(90)
                .setContext(getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_NONE)
                .setVideoEncoder(SessionBuilder.VIDEO_H264);

        mSurfaceView.getHolder().addCallback(this);

        // Starts the RTSP server
        startService(new Intent(this, RtspServer.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(this, RtspServer.class));
    }

    @Override
    public void onBitrateUpdate(long bitrate) {
        Log.d("RTSP-callback", "onBitrateUpdate: " + bitrate);
    }

    @Override
    public void onSessionError(int reason, int streamType, Exception e) {
        Log.d("RTSP-callback", String.format("onSessionError: reason(%s) streamType(%s)", reason, streamType));
        e.printStackTrace();
    }

    @Override
    public void onPreviewStarted() {
        Log.d("RTSP-callback", "onPreviewStarted");
    }

    @Override
    public void onSessionConfigured() {
        Log.d("RTSP-callback", "onSessionConfigured");
    }

    @Override
    public void onSessionStarted() {
        Log.d("RTSP-callback", "onSessionStarted");
    }

    @Override
    public void onSessionStopped() {
        Log.d("RTSP-callback", "onSessionStopped");
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.d("RTSP-callback", "onPreviewFrame");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("RTSP-callback", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("RTSP-callback", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("RTSP-callback", "surfaceDestroyed");
    }
}
