package y.shane.mote.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sieun on 16. 4. 20..
 */
public class AnimatorUtil {


    private static final AnimatorUtil mInstance = new AnimatorUtil();

    public static AnimatorUtil getInstance() {
        return mInstance;
    }

    public void startAnimator(Animator... animators) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animators);
        animatorSet.start();
    }

    public void startAnimator(ArrayList<Animator> animators) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animators);
        animatorSet.start();
    }

    public ValueAnimator topPaddingAnimator(final View view, int fromPadding, int toPadding) {

        ValueAnimator animator = ValueAnimator.ofFloat(fromPadding, toPadding);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                view.setPadding(view.getPaddingLeft(), (int) animatedValue, view
                        .getPaddingRight(), view.getPaddingBottom());
                view.requestLayout();
            }
        });

        return animator;
    }

    public ValueAnimator textSizeAnimator(final TextView textView, float fromSize, float toSize) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromSize, toSize);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, animatedValue);
            }
        });

        return animator;
    }

    public ValueAnimator textColorAnimator(final TextView textView, @ColorInt int fromColor, @ColorInt int toColor) {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((int) animator.getAnimatedValue());
            }
        });

        return animator;
    }

    public ValueAnimator drawableColorAnimator(final Drawable drawable, @ColorInt int fromColor, @ColorInt int toColor) {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                drawable.setColorFilter((int) valueAnimator.getAnimatedValue(), PorterDuff.Mode.SRC_IN);
                //                imageView.setImageDrawable(drawable);
            }
        });

        return animator;
    }

    public ValueAnimator viewAlphaAnimator(final View view, int fromAlpha, int toAlpha) {
        ValueAnimator animator = ValueAnimator.ofInt(fromAlpha, toAlpha);
        animator.setDuration(100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                view.setAlpha((int) valueAnimator.getAnimatedValue());

            }
        });

        return animator;
    }

    public Animator backgroundColorAnimator(View backgroundView, View itemView, Animator
            .AnimatorListener listener) {

        // get the center for the clipping circle
        int cx = (itemView.getLeft() + itemView.getRight()) / 2;
        int cy = (itemView.getTop() + itemView.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(backgroundView.getWidth(), backgroundView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(backgroundView, cx, cy, 0, finalRadius);
        anim.setDuration(400);
        anim.addListener(listener);
        // make the view visible and start the animation
        backgroundView.setVisibility(View.VISIBLE);
        return anim;
    }

    public ValueAnimator viewWidthAnimator(final View view, int fromWidth, int toWidth) {
        ValueAnimator animator = ValueAnimator.ofInt(fromWidth, toWidth);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                view.getLayoutParams().width = animatedValue;
                view.requestLayout();
            }
        });

        return animator;
    }
}
