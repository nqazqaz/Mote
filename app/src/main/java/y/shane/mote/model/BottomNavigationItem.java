package y.shane.mote.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Created by Sieun on 16. 4. 18..
 */
public class BottomNavigationItem {

    private CharSequence title;
    private Drawable icon;
    private int color = Color.GRAY;

    private
    @StringRes
    int titleRes = 0;
    private
    @DrawableRes
    int iconRes = 0;

    /**
     * Constructor
     */
    public BottomNavigationItem() {
    }

    /**
     * Constructor
     *
     * @param title Title
     * @param icon Drawable
     */
    public BottomNavigationItem(CharSequence title, Drawable icon) {
        this.title = title;
        this.icon = icon;
    }

    /**
     * @param titleRes String resource
     * @param iconRes Drawable resource
     */
    public BottomNavigationItem(int titleRes, int iconRes) {
        this.titleRes = titleRes;
        this.iconRes = iconRes;
    }

    /**
     * Constructor
     *
     * @param title Title
     * @param icon Drawable
     * @param color Color
     */
    public BottomNavigationItem(String title, Drawable icon, int color) {
        this.title = title;
        this.icon = icon;
        this.color = color;
    }

    public CharSequence getTitle(Context context) {
        if(titleRes != 0)
            return context.getString(titleRes);
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.titleRes = 0;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
        this.title = "";
    }

    public Drawable getIcon(Context context) {
        if(iconRes != 0)
            return context.getResources().getDrawable(iconRes);
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
        this.iconRes = 0;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
        this.icon = null;
    }

    public int getColor(Context context) {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
