package me.huaqianlee.forme.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.huaqianlee.forme.R;
import me.huaqianlee.forme.base.BaseFragment;
import me.huaqianlee.forme.util.LogUtil;

import java.util.ArrayList;
import java.util.Date;


public class ToDoFragment extends BaseFragment {
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
