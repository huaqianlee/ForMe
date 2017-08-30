package me.huaqianlee.forme.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import me.huaqianlee.forme.R;
import me.huaqianlee.forme.base.BaseActivity;
import me.huaqianlee.forme.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddToDoActivity extends BaseActivity {
    private static final String TODOITEM = "me.huaqianlee.forme.todo.todoitem";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();//获得ToolBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
            actionBar.setTitle("");
        }


        Intent intent = getIntent();
        TodoItem item = (TodoItem) intent.getSerializableExtra(TODOITEM);
    }

    public static String formatDate(String format, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
