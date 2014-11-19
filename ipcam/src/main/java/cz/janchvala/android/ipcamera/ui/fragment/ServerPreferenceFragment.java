package cz.janchvala.android.ipcamera.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import org.androidannotations.annotations.EFragment;

import cz.janchvala.android.ipcamera.R;

/**
 * Created by jan on 19.11.2014.
 */
@EFragment
public class ServerPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_server);
    }
}
