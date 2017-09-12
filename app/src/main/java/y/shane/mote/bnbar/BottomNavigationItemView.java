package y.shane.mote.bnbar;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.RelativeLayout;

import y.shane.mote.model.BottomNavigationItem;

/**
 * Created by Sieun on 16. 4. 25..
 */
public abstract class BottomNavigationItemView extends RelativeLayout {

    public BottomNavigationItemView(Context context) {
        super(context);
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

    public abstract void setItem(BottomNavigationItem item);
    public abstract void active();
    public abstract void inactive();

}
