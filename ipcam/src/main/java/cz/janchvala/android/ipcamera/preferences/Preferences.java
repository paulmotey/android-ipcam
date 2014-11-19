package cz.janchvala.android.ipcamera.preferences;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import cz.janchvala.android.ipcamera.R;

/**
 * @author Jan.Chvala
 *         Created on 22. 10. 2014.
 */
@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface Preferences {

    @DefaultString(keyRes = R.string.rtsp_port, value = "8086")
    String port();

    @DefaultString(keyRes = R.string.cdn_server_url, value = "http://10.0.0.12:8080")
    String cdnServerUrl();

}
