package cz.janchvala.android.ipcamera.tools;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.widget.TextView;

/**
 * Class provides utilities or tools for view manipulations.
 * <p/>
 * Created by jan on 7.11.2014.
 */
public class ViewTools {

    /**
     * Gets the TTF TypeFace font and applies it to all the given TextViews.
     *
     * @param tf        - true type font
     * @param textViews - all textViews to be set the TypeFace to
     */
    public static void setTypeface(@NonNull Typeface tf, @NonNull TextView... textViews) {
        if (textViews.length > 1) {
            for (TextView textView : textViews) {
                textView.setTypeface(tf);
            }
        }
    }
}
