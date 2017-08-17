package me.huaqianlee.forme.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import me.huaqianlee.forme.MyApplication;
import me.huaqianlee.forme.R;
import me.huaqianlee.forme.SelectedNavItem;
import me.huaqianlee.forme.bean.Func;
import me.huaqianlee.forme.util.LogUtil;

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

/*        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SelectedNavItem.setSlectedNavItem(holder.getAdapterPosition());
                //v.setBackgroundColor(11);
            }
        });*/

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Func func = mLists.get(position);
        holder.funcName.setText(func.getName());
        Glide.with(mContext).load(func.getImageId()).into(holder.funcImage);

        holder.itemView.setSelected(position == SelectedNavItem.getSlectedNavItem()); //

/*        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mLists.size();

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
        }
    }
}
