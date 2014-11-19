package cz.janchvala.android.ipcamera.ui.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspServer;
import net.majorkernelpanic.streaming.video.VideoQuality;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.springframework.web.client.RestClientException;

import cz.janchvala.android.ipcamera.R;
import cz.janchvala.android.ipcamera.preferences.Preferences_;
import cz.janchvala.android.ipcamera.rest.AndroidIPCamRestClient;
import cz.janchvala.android.ipcamera.rest.AndroidIPCamRestClientFactory;
import cz.janchvala.android.ipcamera.rest.dto.SessionDTO;
import cz.janchvala.android.ipcamera.server.AndroidIpRtspServer;
import cz.janchvala.android.ipcamera.tools.NetworkingTools;


/**
 * A straightforward example of how to use the RTSP server included in libstreaming.
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends ToolbarBaseActivity implements Session.Callback, Camera.PreviewCallback, SurfaceHolder.Callback, RtspServer.CallbackListener {

    @OptionsMenuItem(R.id.mi_activity_main_server_start_stop)
    MenuItem miServerAction;

    @Bean
    AndroidIPCamRestClientFactory mRestServiceFactory;

    @Pref
    Preferences_ mSharedPrefs;

    @ViewById(R.id.camera_surface)
    protected SurfaceView mSurfaceView;

    Session mSession;
    SessionDTO mSessionDTO = null;
    AndroidIpRtspServer mService;

    boolean mBound = false;

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            RtspServer.LocalBinder binder = (RtspServer.LocalBinder) service;
            mService = (AndroidIpRtspServer) binder.getService();
            mService.addCallbackListener(MainActivity.this);
            mBound = true;
            Toast.makeText(MainActivity.this, "service connected", Toast.LENGTH_LONG).show();
            supportInvalidateOptionsMenu();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            mService = null;
            supportInvalidateOptionsMenu();
            Toast.makeText(MainActivity.this, "service DIS connected", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (isRtspServerRunning()) {
            miServerAction.setIcon(R.drawable.ic_clear_white_36dp);
        } else {
            miServerAction.setIcon(R.drawable.ic_cast_white_36dp);
        }
        return true;
    }

    @OptionsItem(R.id.mi_activity_main_settings)
    public void onShowSettingClick() {
        ToolbarPreferenceActivity_.intent(this).start();
    }

    @OptionsItem(R.id.mi_activity_main_server_start_stop)
    public void onServerActionClick() {
        if (isRtspServerRunning()) {
            stopRtspServer();
            Toast.makeText(this, "stopping server", Toast.LENGTH_SHORT).show();
        } else {
            startRtsServer();
        }
    }

    private void startRtsServer() {
        mService.enableAndStart();
    }

    private boolean isRtspServerRunning() {
        return mService != null && mService.isListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Starts the service of the RTSP server
        startService(new Intent(this, AndroidIpRtspServer.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRestServiceFactory.getClient().setRootUrl(mSharedPrefs.cdnServerUrl().get());
    }

    @AfterViews
    protected void initializeSession() {
        Log.d("RTSP-callback", "initializeSession");

        mToolbarMain.setLogo(R.drawable.ic_launcher);

        // Configures the SessionBuilder
        mSession = SessionBuilder.getInstance()
                .setSurfaceView(mSurfaceView)
                .setContext(getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_NONE)
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setVideoQuality(new VideoQuality(640, 480, 15))
                .setCallback(this)
                .build();

        mSurfaceView.getHolder().addCallback(this);

        bindService(new Intent(this, AndroidIpRtspServer.class), mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        serverListening(false);

        if (mBound) {
            mService.removeCallbackListener(MainActivity.this);
            unbindService(mConnection);
            mBound = false;
        }
        stopService(new Intent(this, AndroidIpRtspServer.class));
    }

    private void stopRtspServer() {
        if (isRtspServerRunning()) {
            serverListening(false);
            mService.stop();
        }
    }


    @Override
    public void onBitrateUpdate(Session session, long bitrate) {
        Log.d("RTSP-callback", "onBitrateUpdate: " + bitrate);
    }

    @Override
    public void onSessionError(Session session, int reason, int streamType, Exception e) {
        Log.d("RTSP-callback", String.format("onSessionError: reason(%s) streamType(%s)", reason, streamType));
    }

    @Override
    public void onPreviewStarted(Session session) {
        Log.d("RTSP-callback", "onPreviewStarted");
    }

    @Override
    public void onSessionConfigured(Session session) {
        Log.d("RTSP-callback", "onSessionConfigured");
    }

    @Override
    public void onSessionStarted(Session session) {
        Log.d("RTSP-callback", "onSessionStarted");
    }

    @Override
    public void onSessionStopped(Session session) {
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

    @Override
    public void onError(RtspServer server, Exception e, int error) {
        Log.e("RTSP-callback", "server error: " + error, e);
    }

    @Override
    public void onMessage(RtspServer server, int message) {
        Log.i("RTSP-callback", "server message: " + message);

    }

    @Override
    public void onServerStartListening(RtspServer server) {
        Log.i("RTSP-callback", "server started listening");
        serverListening(true);
    }

    @Override
    public void onServerSoppedListening(RtspServer server) {
        Log.i("RTSP-callback", "server stopped listening");
        serverListening(false);
    }

    private void serverListening(boolean listening) {
        registerSession(listening);
        invalidateOptionsMenu();
    }

    @Background
    protected void registerSession(boolean register) {
        if (mService == null) {
            return;
        }

        try {
            AndroidIPCamRestClient client = mRestServiceFactory.getClient();
            if (register) {
                mSessionDTO = new SessionDTO(null, "Android IP cam session", NetworkingTools.getLocalIpAddress(mService.getPort()), "This is sample content");
                mSessionDTO = client.registerSession(mSessionDTO.getName(), mSessionDTO.getUrls().get(0), mSessionDTO.getContent());
                Log.e("MainActivity", String.format("ID:%s,NAME:%s,URL:%s,CONTENT:%s", mSessionDTO.getId(), mSessionDTO.getName(), mSessionDTO.getUrls(), mSessionDTO.getContent()));
            } else if (mSessionDTO != null && mSessionDTO.getId() != null) {
                client.unregisterSession(mSessionDTO.getId());
            }
        } catch (RestClientException e) {
            String msg = "Exception during session registration: " + e.getMessage();
            Log.e("REST-client", msg);
            showAlertDialog(msg);
        }
    }

    @UiThread
    protected void showAlertDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
