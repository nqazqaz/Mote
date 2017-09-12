package y.shane.mote.bnbar;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Sieun on 16. 4. 25..
 */
public class BottomNavigationItemViewFactory {

    public static BottomNavigationItemView createItemView(Context context, int animationMode) {

        switch (animationMode) {
            case BottomNavigationStyle.MODE_ANIMATION_FIXED:
                return new BottomNavigationFixedItemView(context);

            case BottomNavigationStyle.MODE_ANIMATION_SHIFTING:
                return new BottomNavigationShiftingItemView(context);
        }

        return null;
    }

    public static ArrayList<BottomNavigationItemView> createItemViewList(Context context, int mode, int size) throws RuntimeException {

        if(size < 2 || size > 5)
            throw new RuntimeException("itemView size is size < 2 or size > 5");

        ArrayList<BottomNavigationItemView> itemViews = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            switch (mode) {
                case BottomNavigationStyle.MODE_ANIMATION_FIXED:
                    itemViews.add(new BottomNavigationFixedItemView(context));

                case BottomNavigationStyle.MODE_ANIMATION_SHIFTING:
                    itemViews.add(new BottomNavigationShiftingItemView(context));
            }
        }

        return itemViews;
    }
}
