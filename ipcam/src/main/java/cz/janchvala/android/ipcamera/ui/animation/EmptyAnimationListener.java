package cz.janchvala.android.ipcamera.ui.animation;

import android.animation.Animator;

/**
 * Dummy listener which does nothing. It can be used when we do not need to implement all of the
 * Animator.AnimatorListener interface methods.
 * <p/>
 * Created by jan on 12.11.2014.
 */
public abstract class EmptyAnimationListener implements Animator.AnimatorListener {
    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }
}
