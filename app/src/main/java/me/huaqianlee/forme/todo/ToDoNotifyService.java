package me.huaqianlee.forme.todo;

import android.app.IntentService;
import android.content.Intent;

public class ToDoNotifyService extends IntentService{
    public static final String TODOTEXT = "me.huaqianlee.forme.todo.notifyservicetext";
    public static final String TODOUUID = "me.huaqianlee.forme.todo.notifyserviceuuid";


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ToDoNotifyService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
