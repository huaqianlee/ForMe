package me.huaqianlee.forme.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import me.huaqianlee.forme.*;

/*
 * 切换主界面视图过工具类
 */
public class MainViewSwitch {

    public void switchMainView(BaseActivity activity) {
        switch (SelectedNavItem.getSlectedNavItem()) {
            case SelectedNavItem.TODO:
                replaceFragment(new ToDoFragment(),activity);
                break;
            case SelectedNavItem.DATEEVENT:
                replaceFragment(new DateEventFragment(), activity);
                break;

            default:
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
