package me.huaqianlee.forme.todo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class TodoItem implements Serializable{
    private String mToDoContent;
    private boolean mHasReminder;
    private Date mToDoDate;
    private int mToDoColor;
    private UUID mToDoIdentifier;
    private static final String TODOCONTENT = "todocontent";
    private static final String TODOREMINDER = "todoreminder";
    private static final String TODODATE = "tododate";
    private static final String TODOCOLOR = "todocolor";
    private static final String TODOIDENTIFIER = "todoidentifier";


    public TodoItem(String mToDoContent, boolean mHasReminder, Date mToDoDate) {
        this.mToDoContent = mToDoContent;
        this.mHasReminder = mHasReminder;
        this.mToDoDate = mToDoDate;
        mToDoColor = 1677725;
        mToDoIdentifier = UUID.randomUUID();
    }

    public TodoItem(JSONObject object) throws JSONException {
        mToDoContent = object.getString(TODOCONTENT);
        mHasReminder = object.getBoolean(TODOREMINDER);
        mToDoColor = object.getInt(TODOCOLOR);
        mToDoIdentifier = UUID.fromString(object.getString(TODOIDENTIFIER));
        if (object.has(TODODATE)) {
            mToDoDate = new Date(object.getLong(TODODATE));
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(TODOCONTENT,mToDoContent);
        object.put(TODOREMINDER,mHasReminder);
        object.put(TODOCOLOR,mToDoColor);
        object.put(TODOIDENTIFIER,mToDoIdentifier);
        if (mToDoDate != null) {
            object.put(TODODATE,mToDoDate.getTime());
        }

        return object;
    }

    public TodoItem () {
        this("Go to the morning run!", true, new Date());
    }

    public String getmToDoContent() {
        return mToDoContent;
    }

    public void setmToDoContent(String mToDoContent) {
        this.mToDoContent = mToDoContent;
    }

    public boolean hasReminder() {
        return mHasReminder;
    }

    public void setmHasReminder(boolean mHasReminder) {
        this.mHasReminder = mHasReminder;
    }

    public Date getmToDoDate() {
        return mToDoDate;
    }

    public void setmToDoDate(Date mToDoDate) {
        this.mToDoDate = mToDoDate;
    }

    public int getmToDoColor() {
        return mToDoColor;
    }

    public void setmToDoColor(int mToDoColor) {
        this.mToDoColor = mToDoColor;
    }

    public UUID getmToDoIdentifier() {
        return mToDoIdentifier;
    }

    public void setmToDoIdentifier(UUID mToDoIdentifier) {
        this.mToDoIdentifier = mToDoIdentifier;
    }
}
