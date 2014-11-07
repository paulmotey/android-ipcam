package cz.janchvala.android.ipcamera.ui.activity;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * TypeFaceSpan is class providing spannable string using typeface
 * <p/>
 * Created by jan on 7.11.2014.
 */
public class TypeFaceSpan extends MetricAffectingSpan {

    private Typeface mTypeface;

    private TypeFaceSpan(@NonNull Typeface mTypeface) {
        this.mTypeface = mTypeface;
    }

    @Override
    public void updateMeasureState(TextPaint p) {
        p.setTypeface(mTypeface);

        // Note: This flag is required for proper typeface rendering
        p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setTypeface(mTypeface);

        // Note: This flag is required for proper typeface rendering
        tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    /**
     * factory method for creating spannable string using TypeFaceSpan
     *
     * @param text     - text to display
     * @param typeface - desired TypeFace
     * @return - created SpannableString
     */
    public static SpannableString createSpannableString(@NonNull String text, @NonNull Typeface typeface) {
        TypeFaceSpan tfs = new TypeFaceSpan(typeface);
        SpannableString result = new SpannableString(text);
        result.setSpan(tfs, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return result;
    }
}
