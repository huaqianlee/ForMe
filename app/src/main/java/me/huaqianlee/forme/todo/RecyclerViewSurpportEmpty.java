package me.huaqianlee.forme.todo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class RecyclerViewSurpportEmpty extends RecyclerView {
    private View emptyView;

    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            showEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
            observer.onChanged();
        }
    }

    private void showEmptyView() {
        Adapter <?> adapter = getAdapter();
        if (adapter != null && emptyView != null) {
            if (adapter.getItemCount() == 0) {
                emptyView.setVisibility(VISIBLE);
                RecyclerViewSurpportEmpty.this.setVisibility(GONE);
            }  else {
                emptyView.setVisibility(GONE);
                RecyclerViewSurpportEmpty.this.setVisibility(VISIBLE);
            }
        }
    }

    public void setEmptyView(View view) {
        emptyView = view;
    }

    public RecyclerViewSurpportEmpty(Context context) {
        super(context);
    }

    public RecyclerViewSurpportEmpty(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewSurpportEmpty(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
