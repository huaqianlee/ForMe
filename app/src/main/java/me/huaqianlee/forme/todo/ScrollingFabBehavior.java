package me.huaqianlee.forme.todo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import me.huaqianlee.forme.util.ToolbarUtils;

public class ScrollingFabBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private int toolbarHeight;
    private static boolean scrollUp = false;
    private static boolean scrollDown = false;

    public ScrollingFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        toolbarHeight = ToolbarUtils.getToolbarHeight(context);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            float finalVal = parent.getHeight() - dependency.getY();
            child.setTranslationY(-finalVal);
        } else if(dependency instanceof Toolbar){
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)child.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = child.getHeight() + fabBottomMargin;
            float finalVal = dependency.getY()/(float)toolbarHeight;
            float distFinal = -distanceToScroll * finalVal;
            child.setTranslationY(distFinal);
        }
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        //return super.layoutDependsOn(parent, child, dependency);
        return (dependency instanceof Snackbar.SnackbarLayout) || (dependency instanceof Toolbar);
    }
}
