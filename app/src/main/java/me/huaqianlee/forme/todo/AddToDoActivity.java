package me.huaqianlee.forme.todo;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import me.huaqianlee.forme.R;
import me.huaqianlee.forme.base.BaseActivity;
import me.huaqianlee.forme.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import static me.huaqianlee.forme.todo.ToDoFragment.DARKTHEME;

public class AddToDoActivity extends BaseActivity {
    private String theme;
    private TodoItem item;
    private String itemText;
    private boolean hasReminder;
    private Date reminderDate;
    private int itemColor;
    private LinearLayout reminderDateLayout;
    private LinearLayout dateInputLayout;
    private EditText todoEdit;
    private SwitchCompat reminderSwitch;
    private FloatingActionButton todoFAB;
    private TextView newTodoText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = getSharedPreferences(ToDoFragment.THEME_PREFERENCES, MODE_PRIVATE).getString(ToDoFragment.SAVETHEME, ToDoFragment.LIGHTTHEME);
        if (theme.equals(ToDoFragment.LIGHTTHEME)) {
            setTheme(R.style.CustomStyle_LightTheme);
            Log.d("AddToDoActivity", "Light Theme");
        } else {
            setTheme(R.style.CustomStyle_DarkTheme);
        }
        setContentView(R.layout.activity_addtodo);

        //Show an X in place of <-
/*        final Drawable cross = getResources().getDrawable(R.drawable.ic_clear_white_24dp);
        if(cross !=null){
            cross.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.SRC_ATOP);
        }*/
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();//获得ToolBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
            actionBar.setTitle("");
        }
        Intent intent = getIntent();
        item = (TodoItem) intent.getSerializableExtra(ToDoFragment.TODOITEM);
        itemText = item.getmToDoContent();
        hasReminder = item.hasReminder();
        reminderDate = item.getmToDoDate();
        itemColor = item.getmToDoColor();

        ImageButton reminderImage = (ImageButton)findViewById(R.id.add_todo_reminder_icon);
        TextView reminderText = (TextView)findViewById(R.id.add_todo_reminder_text);
        if(theme.equals(DARKTHEME)){
            reminderImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_alarm_add_white_24dp));
            reminderText.setTextColor(Color.WHITE);
        }

        reminderDateLayout = (LinearLayout)findViewById(R.id.add_todo_reminder_date);
        dateInputLayout = (LinearLayout)findViewById(R.id.add_todo_date_input_layout);
        todoEdit = (EditText)findViewById(R.id.add_todo_edit);
        reminderSwitch = (SwitchCompat)findViewById(R.id.add_todo_reminder_switch);
        todoFAB = (FloatingActionButton)findViewById(R.id.add_todo_FAB);
        newTodoText = (TextView)findViewById(R.id.new_todo_text);
        //setDateInputLayoutVisibleWithAnimations(false);

        reminderDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(todoEdit);
                todoEdit.setFocusable(false);
            }
        });
        todoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoEdit.setFocusable(true);
                todoEdit.setFocusableInTouchMode(true);
                todoEdit.requestFocus();
            }
        });
        reminderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reminderSwitch.isChecked()) {
                    setDateInputLayoutVisibleWithAnimations(true);
                } else {
                    setDateInputLayoutVisibleWithAnimations(false);
                }
            }
        });

        if (hasReminder && (reminderDate != null)) {
            //setReminderText();
            reminderSwitch.setChecked(true);
            setDateInputLayoutVisibleWithAnimations(true);
        }



    }

    private void setDateInputLayoutVisibleWithAnimations(boolean b) {
        if(b){
            setReminderText();
            dateInputLayout.animate().alpha(1.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            dateInputLayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    }
            );
        }  else{
            dateInputLayout.animate().alpha(0.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            dateInputLayout.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }
            );
        }
    }

    private void setReminderText() {
        if(reminderDate!=null){
            newTodoText.setVisibility(View.VISIBLE);
            if(reminderDate.before(new Date())){
                newTodoText.setText(getString(R.string.date_error_check_again));
                newTodoText.setTextColor(Color.RED);
                return;
            }
            Date date = reminderDate;
            String dateString = formatDate("d MMM, yyyy", date);
            String timeString;
            String amPmString = "";

            if(DateFormat.is24HourFormat(this)){
                timeString = formatDate("k:mm", date);
            }
            else{
                timeString = formatDate("h:mm", date);
                amPmString = formatDate("a", date);
            }
            String finalString = String.format(getResources().getString(R.string.remind_date_and_time), dateString, timeString, amPmString);
            newTodoText.setTextColor(getResources().getColor(R.color.secondary_text));
            newTodoText.setText(finalString);
        }
        else{
            newTodoText.setVisibility(View.INVISIBLE);

        }
    }

    public void hideKeyboard(EditText et){

        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public static String formatDate(String format, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
