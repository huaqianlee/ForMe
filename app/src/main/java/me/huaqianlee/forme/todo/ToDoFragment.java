package me.huaqianlee.forme.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import me.huaqianlee.forme.R;
import me.huaqianlee.forme.base.BaseFragment;
import me.huaqianlee.forme.util.LogUtil;

import java.util.ArrayList;
import java.util.Date;


public class ToDoFragment extends BaseFragment {
    private static final int REQUEST_ID_TODOITEM = 100;
    public static final String TODOITEM = "me.huaqianlee.forme.todo.todoitem";
    public  static final String DARKTHEME = "me.huaqianlee.forme.darktheme";
    public static final String LIGHTTHEME = "me.huaqianlee.forme.lighttheme";
    public static final String SAVETHEME = "me.huaqianlee.forme.savetheme";
    public static final String THEME_PREFERENCES = "me.huaqianlee.forme.thempre";
    ArrayList<TodoItem> items = new ArrayList<>();

    private String[] testStrings = {"Clean my room",
            "Water the plants",
            "Get car washed",
            "Get my dry cleaning"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_todo, null);
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        //view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        todoUi(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton addTodoFAB = (FloatingActionButton) funcMainActivity.findViewById(R.id.todo_add_item_FAB);
        addTodoFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(funcMainActivity, AddToDoActivity.class);
                TodoItem item = new TodoItem(" ",false,null);
                int color = ColorGenerator.MATERIAL.getRandomColor();
                item.setmToDoColor(color);
                intent.putExtra(TODOITEM,item);
                funcMainActivity.startActivityForResult(intent,REQUEST_ID_TODOITEM);
            }
        });
    }

    private void todoUi (final View view) {
        funcMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initItem();
                RecyclerViewSurpportEmpty todoView = (RecyclerViewSurpportEmpty) view.findViewById(R.id.todo_recyclerview);
                todoView.setEmptyView(view.findViewById(R.id.todo_empty));
                /*LinearLayoutManager manager = new LinearLayoutManager(activity);
                todoView.setLayoutManager(manager);*/
                TodoListAdapter adapter = new TodoListAdapter(items);
                todoView.setAdapter(adapter);
            }
        });
    }
    
    private void  initItem() {
        for (String string :
                testStrings) {
            TodoItem item = new TodoItem(string, true, new Date());
            LogUtil.d("ToDoFragment", String.valueOf(new Date()));
            item.setmToDoColor(getResources().getColor(R.color.primary_dark));
            items.add(item);
        }
    }
}
