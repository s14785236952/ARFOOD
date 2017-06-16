package com.example.peter.arfood;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import com.example.peter.arfood.fragment.ExploreFragment;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private List<App> mApps;
    private boolean mHorizontal;
    private boolean mPager;

    public Adapter(Context context) {
        this.context = context;
    }

    public Adapter(boolean horizontal, boolean pager, List<App> apps) {
        mHorizontal = horizontal;
        mApps = apps;
        mPager = pager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mPager) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_pager, parent, false));
        } else {
            return  new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter, parent, false)) ;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        App app = mApps.get(position);
        holder.imageView.setImageResource(app.getDrawable());

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView nameTextView;
        public TextView ratingTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {
            Log.d("App", mApps.get(getAdapterPosition()).getName());

            Intent i = new Intent(v.getContext(),RestaurantActivity.class);
            i.putExtra("restaurant",mApps.get(getAdapterPosition()).getName());
            v.getContext().startActivity(i);
        }
    }

}

