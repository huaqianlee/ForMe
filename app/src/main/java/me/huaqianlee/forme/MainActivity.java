package me.huaqianlee.forme;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;
import me.huaqianlee.forme.adapter.NavFuncAdapter;
import me.huaqianlee.forme.base.BaseActivity;
import me.huaqianlee.forme.bean.Func;
import me.huaqianlee.forme.util.LogUtil;
import me.huaqianlee.forme.util.SelectedNavItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private final static String TAG = "ForMe.MainActivity";
    private DrawerLayout mlayout;
    private List<Func> funcList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            toolbar.setContentInsetsRelative(50, 10);
            //getWindow().setStatusBarColor(Color.TRANSPARENT);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
 /*       ActionBar actionBar = getSupportActionBar();//获得ToolBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }*/

        initFunc();  // 初始化侧边导航栏
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.nav_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        final NavFuncAdapter adapter = new NavFuncAdapter(funcList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NavFuncAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SelectedNavItem.setSlectedNavItem(position); // 更新选中的位置
                new MainViewSwitch().switchMainView(MainActivity.this); //切换主视图
                adapter.notifyDataSetChanged();
                LogUtil.d(TAG, "You clicked the nav button!");
            }
        });

        LogUtil.i(TAG, getString(R.string.test_log));
    }

    @Override
    public void onBackPressed() {
        if (mlayout.isDrawerOpen(findViewById(R.id.nav_left_layout)))
            mlayout.closeDrawers();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                break;
            case android.R.id.home:
                mlayout.openDrawer(GravityCompat.START);
                break;

            default:
                break;

        }
        return true;
    }

/*    private void switchTitle(int position) {
        ActionBar toolbar = getSupportActionBar();
        switch (position) {
            case SelectedNavItem.TODO:
                toolbar.setTitle(R.string.todo_item);
                break;
            case SelectedNavItem.DATEEVENT:
                toolbar.setTitle(R.string.date_event_item);
                break;
            default:
                toolbar.setTitle(R.string.app_name);

        }

    }*/

    private Func[] funcs = {new Func("Call", R.drawable.nav_call), new Func("Friends", R.drawable.nav_friends), new Func("Location", R.drawable.nav_location),
            new Func("Mail", R.drawable.nav_mail), new Func("Tasks", R.drawable.nav_task)};

    private void initFunc() {
        funcList.clear();
        Func toDoFunc = new Func();
        toDoFunc.setImageId(R.drawable.todo_icon);
        toDoFunc.setName("ToDO");
        funcList.add(toDoFunc);
        SelectedNavItem.setSlectedNavItem(SelectedNavItem.TODO);
        new MainViewSwitch().switchMainView(this);

        Func toDoFunc2 = new Func("Lee", R.drawable.lee);
        funcList.add(toDoFunc2);

        for (int i = 0; i < funcs.length; i++) {
            funcList.add(funcs[i]);
        }
    }
}
