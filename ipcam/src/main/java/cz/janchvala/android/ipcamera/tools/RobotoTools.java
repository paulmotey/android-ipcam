package cz.janchvala.android.ipcamera.tools;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cz.janchvala.android.ipcamera.Application_;

/**
 * helper class used to provide Roboto Type face specific paths in asset folder
 * <p/>
 * Created by jan on 7.11.2014.
 */
public class RobotoTools {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ROBOTO_CONDENSE_BOLD, ROBOTO_CONDENSE_BOLD_ITALIC, ROBOTO_CONDENSE_ITALIC, ROBOTO_CONDENSE_LIGHT, ROBOTO_CONDENSE_LIGHT_ITALIC, ROBOTO_CONDENSE_REGULAR})
    public @interface RobotoPath {
    }

    public static final String ROBOTO_CONDENSE_BOLD = "fonts/RobotoCondense-Bold.ttf";
    public static final String ROBOTO_CONDENSE_BOLD_ITALIC = "fonts/RobotoCondense-BoldItalic.ttf";
    public static final String ROBOTO_CONDENSE_ITALIC = "fonts/RobotoCondense-Italic.ttf";
    public static final String ROBOTO_CONDENSE_LIGHT = "fonts/RobotoCondense-Light.ttf";
    public static final String ROBOTO_CONDENSE_LIGHT_ITALIC = "fonts/RobotoCondense-LightItalic.ttf";
    public static final String ROBOTO_CONDENSE_REGULAR = "fonts/RobotoCondense-Regular.ttf";

    public static Typeface getRobotoTypeFace(@NonNull @RobotoPath String path) {
        return TypeFaceTools_.getInstance_(Application_.getInstance()).getTypeFace(path);
    }
}
