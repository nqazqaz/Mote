package y.shane.mote.bnbar;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import y.shane.mote.R;
import y.shane.mote.model.BottomNavigationItem;
import y.shane.mote.utils.AnimatorUtil;

/**
 * Created by Sieun on 16. 4. 18..
 */

public class BottomNavigationShiftingItemView extends BottomNavigationItemView {

    private Resources mResources;

    private ImageView mIvIcon;
    private TextView mTvTitle;

    // layout size
    private int mActiveWidth;
    private int mInactiveWidth;

    private int mHeight;

    // layout Padding
    private int mLeft;
    private int mInactiveTop;
    private int mActiveTop;
    private int mRight;
    private int mBottom;

    // layout textSize
    private float mActiveTextSize;
    private float mInactiveTextSize;

    private int mActiveColor;
    private int mInactiveColor;

    public BottomNavigationShiftingItemView(Context context) {
        super(context);
        initUI(context);
    }

    private void initUI(Context context) {
        mResources = context.getResources();

        mActiveWidth = ValuesHelper.getInstance(getContext()).mShiftingActiveWidth;
        mInactiveWidth = ValuesHelper.getInstance(getContext()).mShiftingInactiveWidth;
        mHeight = ValuesHelper.getInstance(getContext()).mHeight;

        mLeft = 0;
        mInactiveTop = ValuesHelper.getInstance(getContext()).mShiftingInactiveTop;
        mActiveTop = ValuesHelper.getInstance(getContext()).mShiftingActiveTop;
        mRight = 0;
        mBottom = ValuesHelper.getInstance(getContext()).mBottom;

        mActiveTextSize = ValuesHelper.getInstance(getContext()).mActiveTextSize;
        mInactiveTextSize = ValuesHelper.getInstance(getContext()).mInactiveTextSize;

        mActiveColor = ValuesHelper.getInstance(getContext()).mActiveColor;
        mInactiveColor = ValuesHelper.getInstance(getContext()).mInactiveColor;

        LinearLayout.LayoutParams rlParams = new LinearLayout.LayoutParams(mInactiveWidth, mHeight);
        rlParams.weight = ValuesHelper.getInstance(getContext()).mWeight;
        setLayoutParams(rlParams);

        setPadding(mLeft, mInactiveTop, mRight, mBottom);

        setMinimumWidth(mInactiveWidth);

        // addViews
        mIvIcon = new ImageView(context);
        LayoutParams ivParams = new LayoutParams(
                (int) mResources.getDimension(R.dimen.bottom_navigation_icon),
                (int) mResources.getDimension(R.dimen.bottom_navigation_icon));
        ivParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        ivParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mIvIcon.setLayoutParams(ivParams);
        addView(mIvIcon);

        mTvTitle = new TextView(context);
        mTvTitle.setSingleLine(true);
        mTvTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mInactiveTextSize);
        LayoutParams tvParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        tvParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tvParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mTvTitle.setLayoutParams(tvParams);
        mTvTitle.setAlpha(0);
        addView(mTvTitle);
    }

    @Override
    public void setItem(BottomNavigationItem item) {
        mTvTitle.setText(item.getTitle(getContext()) == null ? "" : item.getTitle(getContext()));
        mTvTitle.setTextColor(mInactiveColor);

        mIvIcon.setImageDrawable(item.getIcon(getContext()));
        mIvIcon.getDrawable().setColorFilter(mInactiveColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void active() {
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(AnimatorUtil.getInstance().viewWidthAnimator(this, mInactiveWidth, mActiveWidth));
        animators.add(AnimatorUtil.getInstance().topPaddingAnimator(this, mInactiveTop, mActiveTop));
        animators.add(AnimatorUtil.getInstance().drawableColorAnimator(mIvIcon.getDrawable(), mInactiveColor, mActiveColor));
        animators.add(AnimatorUtil.getInstance().textSizeAnimator(mTvTitle, mInactiveTextSize, mActiveTextSize));
        animators.add(AnimatorUtil.getInstance().textColorAnimator(mTvTitle, mInactiveColor, mActiveColor));
        animators.add(AnimatorUtil.getInstance().viewAlphaAnimator(mTvTitle, 0, 255));

        AnimatorUtil.getInstance().startAnimator(animators);
    }

    @Override
    public void inactive() {
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(AnimatorUtil.getInstance().viewWidthAnimator(this, mActiveWidth, mInactiveWidth));
        animators.add(AnimatorUtil.getInstance().topPaddingAnimator(this, mActiveTop, mInactiveTop));
        animators.add(AnimatorUtil.getInstance().drawableColorAnimator(mIvIcon.getDrawable(), mActiveColor, mInactiveColor));
        animators.add(AnimatorUtil.getInstance().textSizeAnimator(mTvTitle, mActiveTextSize, mInactiveTextSize));
        animators.add(AnimatorUtil.getInstance().textColorAnimator(mTvTitle, mActiveColor, mInactiveColor));
        animators.add(AnimatorUtil.getInstance().viewAlphaAnimator(mTvTitle, 255, 0));

        AnimatorUtil.getInstance().startAnimator(animators);
    }
}
