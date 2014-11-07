package cz.janchvala.android.ipcamera.tools;

import android.graphics.Typeface;
import android.support.v4.util.LruCache;

import org.androidannotations.annotations.EBean;

import cz.janchvala.android.ipcamera.Application_;

/**
 * Class provides way of getting type face from assets and cache them.
 * <p/>
 * Created by jan on 7.11.2014.
 */
@EBean(scope = EBean.Scope.Singleton)
public class TypeFaceTools {

    /**
     * An <code>LruCache</code> for previously loaded typefaces.
     */
    private static LruCache<String, Typeface> sTypefaceCache = new LruCache<>(6);

    public Typeface getTypeFace(String typefaceName) {
        Typeface typeface = sTypefaceCache.get(typefaceName);

        if (typeface == null) {
            typeface = Typeface.createFromAsset(Application_.getInstance().getAssets(), typefaceName);

            // Cache the loaded Typeface
            sTypefaceCache.put(typefaceName, typeface);
        }

        return typeface;
    }
}
