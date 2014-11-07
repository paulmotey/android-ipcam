package cz.janchvala.android.ipcamera.preferences;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import cz.janchvala.android.ipcamera.R;

/**
 * @author Jan
 *         Created on 22. 10. 2014.
 */
@SharedPref
public interface Preferences {

    @DefaultString(keyRes = R.string.rtsp_port, value = "1234")
    String port();

}
