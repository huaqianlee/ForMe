package me.huaqianlee.forme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import me.huaqianlee.forme.adapter.NavFuncAdapter;
import me.huaqianlee.forme.bean.Func;
import me.huaqianlee.forme.util.LogUtil;

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

        mlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();//获得ToolBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

/*        NavigationView  navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });*/

        initFunc();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.nav_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        NavFuncAdapter adapter = new NavFuncAdapter(funcList);
        recyclerView.setAdapter(adapter);



        LogUtil.i(TAG, getString(R.string.test_log));
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

    private Func [] funcs = {new Func ("Call",R.drawable.nav_call),new Func("Friends",R.drawable.nav_friends),new Func("Location",R.drawable.nav_location),
    new Func("Mail", R.drawable.nav_mail), new Func("Tasks",R.drawable.nav_task)};
    private void initFunc() {
        funcList.clear();
        Func toDoFunc = new Func();
        toDoFunc.setImageId(R.drawable.todo_icon);
        toDoFunc.setName("ToDO");
        funcList.add(toDoFunc);

        Func toDoFunc2 = new Func("Lee", R.drawable.lee);
        funcList.add(toDoFunc2);

        for (int i = 0; i<funcs.length;i++) {
            funcList.add(funcs[i]);
        }
    }
}
