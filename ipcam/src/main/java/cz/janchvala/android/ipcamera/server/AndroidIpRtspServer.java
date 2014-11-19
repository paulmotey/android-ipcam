package cz.janchvala.android.ipcamera.server;

import net.majorkernelpanic.streaming.rtsp.RtspServer;

/**
 * Custom server which is not explicitly enabled when the service was started.
 * <p/>
 * Created by jan on 12.11.2014.
 */
public class AndroidIpRtspServer extends RtspServer {
    public AndroidIpRtspServer() {
        super();
        // RTSP server disabled by default
        mEnabled = false;
    }

    public void enableAndStart() {
        mEnabled = true;
        start();
    }
}
