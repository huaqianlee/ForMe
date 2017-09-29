package me.huaqianlee.forme.todo;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import me.huaqianlee.forme.MainActivity;
import me.huaqianlee.forme.R;
import me.huaqianlee.forme.base.BaseActivity;
import me.huaqianlee.forme.util.LogUtil;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static me.huaqianlee.forme.todo.ToDoFragment.DARKTHEME;
import static me.huaqianlee.forme.todo.ToDoFragment.TODOITEM;

public class AddToDoActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private String theme;
    private TodoItem item;
    private String itemText;
    private boolean hasReminder;
    private Date reminderDate;
    private int itemColor;
    private LinearLayout reminderDateLayout;
    private LinearLayout dateInputLayout;
    private EditText todoEdit;
    private EditText timeEdit;
    private EditText dateEdit;
    private SwitchCompat reminderSwitch;
    private FloatingActionButton todoFAB;
    private TextView newTodoText;


    @Override
    public void onBackPressed() {
        if(reminderDate.before(new Date())){
            item.setmToDoDate(null);
        }
        makeResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(this)!=null){
                    makeResult(RESULT_CANCELED);
                    NavUtils.navigateUpFromSameTask(this);
                }
                hideKeyboard(todoEdit);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

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
        item = (TodoItem) intent.getSerializableExtra(TODOITEM);
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

        timeEdit = (EditText) findViewById(R.id.add_todo_time_edit);
        dateEdit = (EditText) findViewById(R.id.add_todo_date_edit);

        /*Title*/
        todoEdit.requestFocus();
        todoEdit.setText(itemText);
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        todoEdit.setSelection(itemText.length());

        todoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoEdit.setFocusable(true);
                todoEdit.setFocusableInTouchMode(true);
                todoEdit.requestFocus();
            }
        });

        todoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             itemText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /*Finish FAB button*/
        todoFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todoEdit.length() <= 0) {
                    todoEdit.setError(getString(R.string.todo_error));
                } else if(reminderDate!=null && reminderDate.before(new Date())){
                    makeResult(RESULT_CANCELED);
                }
                else{
                    makeResult(RESULT_OK);
                    finish();
                }
                hideKeyboard(todoEdit);
            }
        });


        /*Reminder switch*/
        //reminderSwitch.setChecked(hasReminder&&(reminderDate!=null));
        if (hasReminder && (reminderDate != null)) {
            //setReminderText();
            reminderSwitch.setChecked(true);
            setDateInputLayoutVisibleWithAnimations(true);
        }
        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    reminderDate = null;
                }
                hasReminder = isChecked;
                setDateAndTime();
                setDateInputLayoutVisibleWithAnimations(isChecked);
                hideKeyboard(todoEdit);
            }
        });
        if (reminderDate == null) {
            reminderSwitch.setChecked(false);
            setDateInputLayoutVisibleWithAnimations(false);
        }

        reminderDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(todoEdit);
                todoEdit.setFocusable(false);
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

        /*DateInputLayout*/
        setDateInputLayoutVisible(reminderSwitch.isChecked());
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date ;
                hideKeyboard(todoEdit);
                if (item.getmToDoDate()!=null) {
                    date = reminderDate;
                } else {
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddToDoActivity.this, year, month, day);
                if(theme.equals(DARKTHEME)){
                    datePickerDialog.setThemeDark(true);
                }
                datePickerDialog.show(getFragmentManager(), "DateFragment");
            }
        });
        timeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                hideKeyboard(todoEdit);
                if(item.getmToDoDate()!=null){
                    date = reminderDate;
                }
                else{
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddToDoActivity.this, hour, minute, DateFormat.is24HourFormat(AddToDoActivity.this));
                if(theme.equals(DARKTHEME)){
                    timePickerDialog.setThemeDark(true);
                }
                timePickerDialog.show(getFragmentManager(), "TimeFragment");
            }
        });
        setDateAndTime();
    }

    private void makeResult(int result) {
        Intent i = new Intent();
        if(itemText.length()>0){

            String capitalizedString = Character.toUpperCase(itemText.charAt(0))+itemText.substring(1);
            item.setmToDoContent(capitalizedString);
        }
        else{
            item.setmToDoContent(itemText);
        }

        if(reminderDate!=null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reminderDate);
            calendar.set(Calendar.SECOND, 0);
            reminderDate = calendar.getTime();
        }
        item.setmHasReminder(hasReminder);
        item.setmToDoDate(reminderDate);
        item.setmToDoColor(itemColor);
        i.putExtra(TODOITEM, item);
        setResult(result, i);
    }

    private void setDateAndTime() {
        if (item.hasReminder() && reminderDate != null) {
            String date = formatDate("d MMM, yyyy",reminderDate);
            String formateToUse;
            if (DateFormat.is24HourFormat(this)) {
                formateToUse = "h:mm";
            } else {
                formateToUse = "h:mm a";
            }
            String time = formatDate(formateToUse, reminderDate);
            timeEdit.setText(time);
            dateEdit.setText(date);

        } else {
            dateEdit.setText(getString(R.string.date_reminder_default));
            boolean isHour24 = DateFormat.is24HourFormat(this);
            Calendar calendar = Calendar.getInstance();
            if (isHour24) {
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+1);
            } else {
                calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)+1);
            }
            calendar.set(Calendar.MINUTE, 0);
            reminderDate = calendar.getTime();
            String timeText;
            if (isHour24) {
                timeText = formatDate("h:mm", reminderDate);
            } else {
                timeText = formatDate("h:mm a", reminderDate);
            }
            timeEdit.setText(timeText);
        }
    }

   /*     mTimeEditText.setText(userTime);

        String timeString;
        if(time24){
            timeString = formatDate("k:mm", mUserReminderDate);
        }
        else{
            timeString = formatDate("h:mm a", mUserReminderDate);
        }
        mTimeEditText.setText(timeString);*/
    public void setDateInputLayoutVisible(boolean checked){
        if(checked){
            dateInputLayout.setVisibility(View.VISIBLE);
        }
        else{
            dateInputLayout.setVisibility(View.INVISIBLE);
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

    @Override
    public void onTimeSet(RadialPickerLayout view, int hour, int minute) {
        setTime(hour, minute);
    }

    private void setTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        if(reminderDate!=null){
            calendar.setTime(reminderDate);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("OskarSchindler", "Time set: "+hour);
        calendar.set(year, month, day, hour, minute, 0);
        reminderDate = calendar.getTime();

        setReminderText();
        setTimeEditText();
    }
    public void  setTimeEditText(){
        String dateFormat;
        if(DateFormat.is24HourFormat(this)){
            dateFormat = "k:mm";
        }
        else{
            dateFormat = "h:mm a";

        }
        timeEdit.setText(formatDate(dateFormat, reminderDate));
    }
    public void  setDateEditText(){
        String dateFormat = "d MMM, yyyy";
        dateEdit.setText(formatDate(dateFormat, reminderDate));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        setDate(year, month, day);
    }

    private void setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        int hour, minute;

        Calendar reminderCalendar = Calendar.getInstance();
        reminderCalendar.set(year, month, day);

        if(reminderCalendar.before(calendar)){
            Toast.makeText(this, "My time-machine is a bit rusty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(reminderDate!=null){
            calendar.setTime(reminderDate);
        }

        if(DateFormat.is24HourFormat(this)){
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        }
        else{

            hour = calendar.get(Calendar.HOUR);
        }
        minute = calendar.get(Calendar.MINUTE);

        calendar.set(year, month, day, hour, minute);
        reminderDate = calendar.getTime();
        setReminderText();
        setDateEditText();
    }
}
