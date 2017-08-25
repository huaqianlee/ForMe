package me.huaqianlee.forme.util;

import android.content.Context;
import android.content.res.TypedArray;
import me.huaqianlee.forme.R;

public class ToolbarUtils {
    public static int getToolbarHeight(Context context) {
        final TypedArray styleAttr = context.getTheme().obtainStyledAttributes(new  int[] {R.attr.actionBarSize});
        int toolbarHeight = (int) styleAttr.getDimension(0,0);
        styleAttr.recycle();
        return toolbarHeight;
    }
}
