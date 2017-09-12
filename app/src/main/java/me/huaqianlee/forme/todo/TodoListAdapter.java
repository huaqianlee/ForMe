package me.huaqianlee.forme.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import me.huaqianlee.forme.R;
import me.huaqianlee.forme.base.MyApplication;
import me.huaqianlee.forme.util.LogUtil;

import java.util.ArrayList;
import java.util.Collections;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter{
    private static final String THEME_PREF = "me.huaqianlee.forme.todo.prefs";
    private static final String THEME_SAVED = "me.huaqianlee.forme.todo.savedtheme";
    private static final String LIGHTTHEME = "me.huaqianlee.forme.todo.lighttheme";
    public static final String DATE_TIME_FORMAT_12_HOUR = "MMM d, yyyy  h:mm a";
    public static final String DATE_TIME_FORMAT_24_HOUR = "MMM d, yyyy  k:mm";
    //private static final String TODOITEM = "me.huaqianlee.forme.todo.todoitem";
    private static final int REQUEST_ID_TODOITEM = 100;
    private ArrayList<TodoItem> items;
    private TodoItem mDeletedItem;
    private int mIndexOfDeletedItem;
    private ToDoAlarm alarm;

    public TodoListAdapter(ArrayList<TodoItem> items) {
        this.items = items;
        alarm = new ToDoAlarm();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TodoItem item = items.get(position);

        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(item.getmToDoContent().substring(0,1),item.getmToDoColor());
        holder.imageView.setImageDrawable(myDrawable);

        SharedPreferences pref = MyApplication.getContext().getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        holder.textView.setText(item.getmToDoContent());
        if (pref.getString(THEME_SAVED, LIGHTTHEME).equals(LIGHTTHEME)) {
            holder.cardView.setBackgroundColor(Color.WHITE);
            holder.textView.setTextColor(MyApplication.getContext().getResources().getColor(R.color.secondary_text));
        } else {
            holder.cardView.setBackgroundColor(Color.DKGRAY);
            holder.textView.setTextColor(Color.WHITE);
        }

        if (item.hasReminder() && item.getmToDoDate() != null) {
            holder.textView.setMaxLines(1);
            holder.timeView.setVisibility(View.VISIBLE);
        } else {
            holder.textView.setMaxLines(2);
            holder.timeView.setVisibility(View.GONE);
        }

        if (null != item.getmToDoDate()) {
            String timeText ;
            //timeText = String.valueOf(item.getmToDoDate());
            if (android.text.format.DateFormat.is24HourFormat(ToDoFragment.funcMainActivity)) {
                timeText = AddToDoActivity.formatDate(DATE_TIME_FORMAT_24_HOUR,item.getmToDoDate());
            } else {
                timeText = AddToDoActivity.formatDate(DATE_TIME_FORMAT_12_HOUR,item.getmToDoDate());
            }
            LogUtil.d("TodoListAdapter", "Test"+ timeText);
            holder.timeView.setText(timeText);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition;i < toPosition; i++) {
                Collections.swap(items, i , i+1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i-1);
            }
        }
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemRemoved(int position) {
        mDeletedItem = items.remove(position);
        mIndexOfDeletedItem = position;

        Intent intent = new Intent(ToDoFragment.funcMainActivity, ToDoNotifyService.class);
        alarm.deleteAlarm(intent,mDeletedItem.getmToDoIdentifier().hashCode());
        notifyItemRemoved(position);

        Snackbar.make(ToDoFragment.funcMainActivity.findViewById(R.id.main_cdlayout),"Deleted Todo", Snackbar.LENGTH_SHORT)
        .setAction("UNDO0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(mIndexOfDeletedItem,mDeletedItem);
                if (mDeletedItem.getmToDoDate() != null && mDeletedItem.hasReminder()) {
                    Intent i = new Intent(ToDoFragment.funcMainActivity, ToDoNotifyService.class);
                    i.putExtra(ToDoNotifyService.TODOTEXT, mDeletedItem.getmToDoContent());
                    i.putExtra(ToDoNotifyService.TODOUUID, mDeletedItem.getmToDoIdentifier());
                    alarm.createAlarm(i, mDeletedItem.getmToDoIdentifier().hashCode(), mDeletedItem.getmToDoDate().getTime());
                }
                notifyItemInserted(mIndexOfDeletedItem);
            }
        }).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView ;
        TextView textView;
        TextView timeView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.todo_item_cardview);
            imageView = (ImageView) itemView.findViewById(R.id.todo_item_image);
            textView = (TextView) itemView.findViewById(R.id.todo_item_text);
            timeView = (TextView) itemView.findViewById(R.id.todo_item_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoItem item = items.get(getAdapterPosition());
                    LogUtil.e("TOdoListAdapter ITEM CONTENT" ,""+item.getmToDoContent());
                    Intent intent = new Intent(ToDoFragment.funcMainActivity, AddToDoActivity.class);
                    intent.putExtra(ToDoFragment.TODOITEM,item);
                    ToDoFragment.funcMainActivity.startActivityForResult(intent, REQUEST_ID_TODOITEM);
                }
            });
        }
    }
}
