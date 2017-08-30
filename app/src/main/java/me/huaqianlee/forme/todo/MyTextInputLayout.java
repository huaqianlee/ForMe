package me.huaqianlee.forme.todo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MyTextInputLayout extends TextInputLayout {
    private boolean isHintSet;
    private CharSequence hint;
    public MyTextInputLayout(Context context) {
        super(context);
    }

    public MyTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isHintSet && ViewCompat.isLaidOut(this)) {
            setHint(null);//清空hint以便检查通过

            CharSequence currentHint = getEditText().getHint();
            if (currentHint != null && currentHint.length()>0) {
                hint = currentHint;
            }
            setHint(hint);
            isHintSet = true;

        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof EditText) {
            hint = ((EditText) child).getHint();
        }
        super.addView(child, index, params);
    }
}
