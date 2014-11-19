# android-ipcam

> Please **bear in mind** that this project is developed as part of my diploma thesis so It is changing constantly. I cannot accept or merge pull requests and I will provide no support.

Android IP camera based on libstreaming library is project created for my diploma thesis. Among others IP cameras for Android this project should in the end behave as client for Content Delivery Network (CDN).


# Usage
MainActivity contains only black (SurfaceView for camera) screen with green Toolbar. On the Toolbar there are two buttons *cast* (which starts/stops the RTSP server- streaming) and *settings* which leads to PreferenceActivity. 

You need to specify server url when first running the application. By default there is an IP address of my local server (http://10.0.0.12:8080) set. Go to settings and change the only setting available (Server settings). So far there is no extra option.

Once you have this done go back to MainActivity and start the server by pressing *cast* button on Toolbar.

You can now go to *http://server.ip.address:port/sessions* to see if there is new active session for you RTSP server instance. There should be one session listed on a card. In the upper right corner of the card there is a button which will show VLC plugin at the bottom of the card and requests the session to be streamed.

When the client application receives the streaming request it starts the camera and starts the streaming. You should be able to see the stream in couple of seconds.

# Download APK
The APK files are provided as part of release process so you can find them in  [releases](https://github.com/JanChvala/android-ipcam-server/releases).
