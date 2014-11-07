package cz.janchvala.android.ipcamera.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cz.janchvala.android.ipcamera.R;

/**
 * Base activity class to handle actionbar as toolbar.
 * <p/>
 * Created by jan on 7.11.2014.
 */
@EActivity
public abstract class ToolbarBaseActivity extends ActionBarActivity {

    @ViewById(R.id.toolbar_main)
    Toolbar mToolbarMain;

    @AfterViews
    protected void setupToolbar() {
        if (mToolbarMain == null) {
            throw new IllegalStateException("Activity extends ToolbarBaseActivity so the content view has to include toolbar_main.xml.");
        }
        setSupportActionBar(mToolbarMain);
    }
}
