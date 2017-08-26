package me.huaqianlee.forme.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.huaqianlee.forme.R;
import me.huaqianlee.forme.base.BaseFragment;

import java.util.ArrayList;


public class ToDoFragment extends BaseFragment {
    ArrayList<TodoItem> items = new ArrayList<>();

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
                TodoItem item = new TodoItem() ;
                items.add(item);
                TodoItem item2 = new TodoItem() ;
                items.add(item2);
                RecyclerViewSurpportEmpty todoView = (RecyclerViewSurpportEmpty) view.findViewById(R.id.todo_recyclerview);
                todoView.setEmptyView(view.findViewById(R.id.todo_empty));
                /*LinearLayoutManager manager = new LinearLayoutManager(activity);
                todoView.setLayoutManager(manager);*/
                TodoListAdapter adapter = new TodoListAdapter(items);
                todoView.setAdapter(adapter);
            }
        });

    }
}
