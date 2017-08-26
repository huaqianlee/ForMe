package me.huaqianlee.forme.todo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddToDoActivity {


    public static String formatDate(String format, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
