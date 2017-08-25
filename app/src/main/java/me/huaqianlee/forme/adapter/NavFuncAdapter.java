package me.huaqianlee.forme.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import me.huaqianlee.forme.*;
import me.huaqianlee.forme.bean.Func;
import me.huaqianlee.forme.util.SelectedNavItem;

import java.util.List;

public class NavFuncAdapter extends RecyclerView.Adapter<NavFuncAdapter.ViewHolder> {
    private Context mContext;
    private List<Func> mLists;


    public NavFuncAdapter(List<Func> lists) {
        mLists = lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.func_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Func func = mLists.get(position);
        holder.funcName.setText(func.getName());
        Glide.with(mContext).load(func.getImageId()).into(holder.funcImage);

       // holder.itemView.setSelected(position == SelectedNavItem.getSlectedNavItem()); //设置选中状态
/*        if (position == SelectedNavItem.getSlectedNavItem()) {
              SelectedNavItem.setSlectedNavItem(position);
        }*/

        /*设置选中状态*/
        if (position == SelectedNavItem.getSlectedNavItem()) {
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.selected));
        } else {
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.un_select));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists.size();

    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView funcImage;
        TextView funcName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            funcImage = (ImageView) itemView.findViewById(R.id.nav_func_image);
            funcName = (TextView) itemView.findViewById(R.id.nav_func_name);

/*            cardView.setOnClickListener(NavFuncAdapter.this);
            funcName.setOnClickListener(NavFuncAdapter.this);
            funcImage.setOnClickListener(NavFuncAdapter.this);*/

        }

    }
}
