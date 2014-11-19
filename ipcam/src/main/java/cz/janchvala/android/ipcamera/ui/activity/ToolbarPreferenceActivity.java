package cz.janchvala.android.ipcamera.ui.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EActivity;

import java.util.List;

import cz.janchvala.android.ipcamera.R;

/**
 * PreferenceActivity with toolbar available.
 * <p/>
 * Created by jan on 19.11.2014.
 */
@EActivity
public class ToolbarPreferenceActivity extends PreferenceActivity {
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup root = ((ViewGroup) findViewById(android.R.id.content));
        LinearLayout content = (LinearLayout) root.getChildAt(0);
        LinearLayout toolbarContainer = (LinearLayout) View.inflate(this, R.layout.activity_toolbar_preference_activity, null);

        root.removeAllViews();
        toolbarContainer.addView(content);
        root.addView(toolbarContainer);

        mToolBar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar_main);

        mToolBar.setTitle("Application preferences");
        mToolBar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }
}
