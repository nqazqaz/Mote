package y.shane.mote.bnbar;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import y.shane.mote.R;
import y.shane.mote.behavior.BottomNavigationBehavior;
import y.shane.mote.model.BottomNavigationItem;
import y.shane.mote.utils.AnimatorUtil;
import y.shane.mote.utils.Debug;

/**
 * Created by Sieun on 16. 4. 18..
 */

public class BottomNavigationBar extends CardView implements View.OnClickListener {

    private static final int[] SHIFTING_BG_RES = new int[]{R.color.colorOne2, R.color.colorTwo2, R
            .color.colorThree2, R.color.colorFour2, R.color.colorFive2};

    private static final int MIN_ITEMS = 2;
    private static final int MAX_ITEMS = 5;

    private int mBackgroundMode = BottomNavigationStyle.MODE_BACKGROUND_NOMAL;
    private int mAnimationMode = BottomNavigationStyle.MODE_ANIMATION_FIXED;


    private ViewPager mViewPager;

    private View mBackroundView;
    private BottomNavigationItemView mSelectedItemView;

    private ArrayList<BottomNavigationItem> mItems = new ArrayList<>();
    private ArrayList<BottomNavigationItemView> mItemViews = new ArrayList<>();

    private ArrayList<OnItemTouchListener> mListeners = new ArrayList<>();

    private Context mContext;
    private Resources mResources;

    private int mCurPosition;

    private boolean mIsScrollingHide = true;

    public BottomNavigationBar(Context context) {
        this(context, null);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mResources = context.getResources();

        ValuesHelper.getInstance(getContext()).mActiveColor = mResources.getColor(R.color
                .colorNomalActive);
        ValuesHelper.getInstance(getContext()).mInactiveColor = mResources.getColor(R.color
                .colorNomalInactive);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setLayoutParams(layoutParams);

        setClipToPadding(false);
        ViewCompat.setElevation(this, mResources.getDimension(R.dimen.bottom_navigation_elevation));
        setRadius(0f);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mAnimationMode == BottomNavigationStyle.MODE_ANIMATION_FIXED) {
            if (w > ValuesHelper.getInstance(getContext()).mFixedMaxWidth * mItems.size()) {
                ValuesHelper.getInstance(getContext()).mFixedWidth = ValuesHelper.getInstance(getContext()).mFixedMaxWidth;
            } else {
                ValuesHelper.getInstance(getContext()).mFixedWidth = w / mItems.size();
            }
        } else if (mAnimationMode == BottomNavigationStyle.MODE_ANIMATION_SHIFTING) {
            if (w > ValuesHelper.getInstance(getContext()).mShiftingActiveMaxWidth +
                    (ValuesHelper.getInstance(getContext()).mShiftingInactiveMaxWidth * (mItems.size() - 1))) {

                ValuesHelper.getInstance(getContext()).mShiftingActiveWidth = ValuesHelper.getInstance(getContext()).mShiftingActiveMaxWidth;
                ValuesHelper.getInstance(getContext()).mShiftingInactiveWidth = ValuesHelper.getInstance(getContext()).mShiftingInactiveMaxWidth;
                ValuesHelper.getInstance(getContext()).mWeight = 0.0f;

            } else {
                ValuesHelper.getInstance(getContext()).mShiftingActiveWidth = ValuesHelper.getInstance(getContext()).mShiftingActiveMinWidth;
                ValuesHelper.getInstance(getContext()).mShiftingInactiveWidth = ValuesHelper.getInstance(getContext()).mShiftingInactiveMinWidth;
                ValuesHelper.getInstance(getContext()).mWeight = 1.0f;
            }
        }

        apply();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void apply() {

        clearView();

        if (mItems.size() == 0)
            return;

        if (getLayoutParams() instanceof CoordinatorLayout.LayoutParams && mIsScrollingHide)
            ((CoordinatorLayout.LayoutParams) getLayoutParams()).setBehavior(new BottomNavigationBehavior());

        mBackroundView = new View(getContext());
        addView(mBackroundView, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, (int) mResources
                .getDimension(R.dimen.bottom_navigation_height)));

        LinearLayout itemViewContainer = new LinearLayout(getContext());
        itemViewContainer.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams
                .MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        itemViewContainer.setGravity(Gravity.CENTER);

        for (BottomNavigationItem item : mItems) {

            BottomNavigationItemView itemView = BottomNavigationItemViewFactory.createItemView(getContext(), mAnimationMode);
            itemView.setOnClickListener(this);
            itemView.setItem(item);

            if (mBackgroundMode == BottomNavigationStyle.MODE_BACKGROUND_NOMAL) {
                TypedValue outValue = new TypedValue();
                getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true);
                itemView.setBackgroundResource(outValue.resourceId);
            }

            itemViewContainer.addView(itemView);
            mItemViews.add(itemView);
        }

        addView(itemViewContainer);

        select(0);
    }

    public void select(final int position) {

        if (mSelectedItemView != mItemViews.get(position)) {

            if (mSelectedItemView != null)
                mSelectedItemView.inactive();

            mSelectedItemView = mItemViews.get(position);
            mSelectedItemView.active();


            if (mBackgroundMode == BottomNavigationStyle.MODE_BACKGROUND_HIGH_LIGHT) {
                mBackroundView.setBackgroundResource(SHIFTING_BG_RES[position]);

                if (mBackroundView.isAttachedToWindow()) {
                    AnimatorUtil.getInstance().startAnimator(AnimatorUtil.getInstance()
                            .backgroundColorAnimator(mBackroundView, mSelectedItemView, new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    setCardBackgroundColor(mResources.getColor(SHIFTING_BG_RES[position]));
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            }));
                } else {
                    setCardBackgroundColor(mResources.getColor(SHIFTING_BG_RES[position]));
                }
            }

            if (mViewPager != null && mViewPager.getCurrentItem() != position) {
                mViewPager.setCurrentItem(position);
            }

        }
    }

    @Override
    public void onClick(View v) {

        mCurPosition = mItemViews.indexOf(v);

        for (OnItemTouchListener listener : mListeners) {
            listener.onSelectedItem(mCurPosition);
        }
        select(mCurPosition);
    }

    private void clearView() {
        removeAllViews();
        mItemViews.clear();
    }


    public int getSelectedItemPosition() {
        return mCurPosition;
    }

    public void addMenuItem(BottomNavigationItem bottomNavigationItem) {
        mItems.add(bottomNavigationItem);
    }

    public void addMenuItems(ArrayList<BottomNavigationItem> menuItems) {
        mItems.addAll(menuItems);
    }

    public void setActiveColor(int color) {
        ValuesHelper.getInstance(getContext()).mActiveColor = color;
    }

    public void setInactiveColor(int color) {
        ValuesHelper.getInstance(getContext()).mInactiveColor = color;
    }

    public void setStyle(int animationMode, int backgroundMode) {
        mBackgroundMode = backgroundMode;
        mAnimationMode = animationMode;

        switch (mBackgroundMode) {
            case BottomNavigationStyle.MODE_BACKGROUND_NOMAL:

                ValuesHelper.getInstance(mContext).mActiveColor = mResources.getColor(R.color.colorPrimary);
                ValuesHelper.getInstance(mContext).mInactiveColor = mResources.getColor(R.color.colorNomalInactive);

                break;
            case BottomNavigationStyle.MODE_BACKGROUND_HIGH_LIGHT:

                ValuesHelper.getInstance(getContext()).mActiveColor = mResources.getColor(R.color.colorHighlightActive);
                ValuesHelper.getInstance(getContext()).mInactiveColor = mResources.getColor(R.color.colorHighlightInactive);

                break;
        }
    }

    public void setScrollingHide(boolean isScrollingHide) {
        mIsScrollingHide = isScrollingHide;
    }

    public void setUpWithViewPager(ViewPager viewPager) {

        if (viewPager != null) {
            mViewPager = viewPager;
            mViewPager.addOnPageChangeListener(new BottomNavigationBarOnPageChangeListener(this));
            setPagerAdapter(viewPager.getAdapter());
        }

    }

    private class BottomNavigationBarOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private final BottomNavigationBar bottomNavigationBar;
        private int mScrollState;

        private BottomNavigationBarOnPageChangeListener(BottomNavigationBar mBottomNavigationBarRef) {
            this.bottomNavigationBar = mBottomNavigationBarRef;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Debug.log("onPageScrolled : " + position + ", positionOffset : " + positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            Debug.log("onPageSelected : " + position);
            bottomNavigationBar.select(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;
        }
    }

    public void addOnItemTouchListener(OnItemTouchListener listener) {
        if (listener != null)
            mListeners.add(listener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener listener) {
        if (mListeners.contains(listener))
            mListeners.remove(listener);
    }

    public interface OnItemTouchListener {
        void onSelectedItem(int position);
    }

    private void setPagerAdapter(PagerAdapter adapter) {

        Debug.log("size : " + mItems.size());

        BNFragmentPagerAdapter bnFragmentPagerAdapter;

        for (int i = 0; i < adapter.getCount(); i++) {

            BottomNavigationItem item = new BottomNavigationItem();

            if (adapter instanceof BNFragmentPagerAdapter) {
                bnFragmentPagerAdapter = (BNFragmentPagerAdapter) adapter;

                Drawable icon = bnFragmentPagerAdapter.getBottomNavigationBarIcon(i);
                String menuTitle = bnFragmentPagerAdapter.getBottomNavigationBarMenuTitle(i);

                Debug.log("position : " + i);
                Debug.log("icon : " + icon);
                Debug.log("menuTitle : " + menuTitle);

                if(icon == null)
                    icon = new ColorDrawable(Color.TRANSPARENT);
                item.setIcon(icon);

                if (menuTitle == null)
                    menuTitle = "";
                item.setTitle(menuTitle);

                mItems.add(item);
            }
        }
    }
}
