package me.huaqianlee.forme.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.huaqianlee.forme.MainActivity;
import me.huaqianlee.forme.R;
import me.huaqianlee.forme.util.LogUtil;

import java.util.ArrayList;


public class ToDoFragment extends Fragment {
    ArrayList<TodoItem> items = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_todo, null);
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        todoUi(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    private void todoUi (final View view) {
        final MainActivity activity = (MainActivity) getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerViewSurpportEmpty todoView = (RecyclerViewSurpportEmpty) view.findViewById(R.id.todo_recyclerview);
                if (todoView == null){
                    LogUtil.e("ToDoFragment","todoView is null!");
                }
                todoView.setEmptyView(view.findViewById(R.id.todo_empty));
                /*LinearLayoutManager manager = new LinearLayoutManager(activity);
                todoView.setLayoutManager(manager);*/
                TodoListAdapter adapter = new TodoListAdapter(items);
                todoView.setAdapter(adapter);
            }
        });

    }
}
