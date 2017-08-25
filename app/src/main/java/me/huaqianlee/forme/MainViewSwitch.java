package me.huaqianlee.forme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import me.huaqianlee.forme.*;
import me.huaqianlee.forme.base.BaseActivity;
import me.huaqianlee.forme.dateevent.DateEventFragment;
import me.huaqianlee.forme.todo.RecyclerViewSurpportEmpty;
import me.huaqianlee.forme.todo.ToDoFragment;
import me.huaqianlee.forme.util.LogUtil;
import me.huaqianlee.forme.util.SelectedNavItem;

/*
 * 切换主界面视图过工具类
 */
public class MainViewSwitch {

    public void switchMainView(MainActivity activity) {
        ActionBar toolbar = activity.getSupportActionBar();
        switch (SelectedNavItem.getSlectedNavItem()) {
            case SelectedNavItem.TODO:
                ToDoFragment toDoFragment = new ToDoFragment();
                replaceFragment(toDoFragment,activity);

                toolbar.setTitle(R.string.todo_item);
                break;
            case SelectedNavItem.DATEEVENT:
                replaceFragment(new DateEventFragment(), activity);

                toolbar.setTitle(R.string.date_event_item);
                break;

            default:
                toolbar.setTitle(R.string.app_name);
                break;

        }
    }

    private void replaceFragment(Fragment fragment, BaseActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_view_layout, fragment);
        transaction.commit();
    }

}
