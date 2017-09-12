package y.shane.mote.bnbar;

import android.content.Context;

import y.shane.mote.R;

/**
 * Created by Sieun on 16. 5. 3..
 */
public class ValuesHelper {

    private static ValuesHelper mInstance;
    private Context mContext;

    // layout size

    protected float mWeight = 1.0f;

    // Shifting size
    protected int mShiftingActiveMinWidth;
    protected int mShiftingInactiveMinWidth;
    protected int mShiftingActiveMaxWidth;
    protected int mShiftingInactiveMaxWidth;
    protected int mShiftingActiveWidth;
    protected int mShiftingInactiveWidth;

    // Fixed size
    protected int mFixedMaxWidth;
    protected int mFixedWidth;

    protected int mHeight;

    // layout Padding

    protected int mShiftingInactiveTop;
    protected int mShiftingActiveTop;

    protected int mLeft;
    protected int mFixedInactiveTop;
    protected int mFixedActiveTop;
    protected int mRight;
    protected int mBottom;

    // layout textSize
    protected float mActiveTextSize;
    protected float mInactiveTextSize;

    protected int mActiveColor;
    protected int mInactiveColor;

    public static ValuesHelper getInstance(Context context) {

        if (mInstance == null)
            mInstance = new ValuesHelper(context.getApplicationContext());
        return mInstance;
    }

    private ValuesHelper(Context context) {
        mContext = context;

        // layout size

        mFixedMaxWidth = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_max_width_fixed);


        mShiftingActiveMinWidth = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_min_width_shifting_active);
        mShiftingInactiveMinWidth = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_min_width_shifting_inactive);
        mShiftingActiveMaxWidth = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_max_width_shifting_active);
        mShiftingInactiveMaxWidth = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_max_width_shifting_inactive);

        mHeight = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_height);

        // layout Padding
        mLeft = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_padding_left);

        mShiftingInactiveTop = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_padding_top_shifting_inactive);
        mShiftingActiveTop = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_padding_top_shifting_active);

        mFixedInactiveTop = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_padding_top_fixed_inactive);
        mFixedActiveTop = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_padding_top_fixed_active);
        mRight = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_padding_right);
        mBottom = (int) mContext.getResources().getDimension(R.dimen.bottom_navigation_padding_bottom);

        // layout textSize
        mActiveTextSize = mContext.getResources().getDimensionPixelSize(R.dimen.bottom_navigation_text_size_active);
        mInactiveTextSize = mContext.getResources().getDimensionPixelSize(R.dimen.bottom_navigation_text_size_inactive);

        mActiveColor = mContext.getResources().getColor(R.color.colorPrimary);
        mInactiveColor = mContext.getResources().getColor(R.color.colorNomalInactive);
    }
}
