package com.example.peter.arfood;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> {

    private List<App> mApps;
    private boolean mHorizontal;
    private boolean mPager;

    public AdapterFavorite(boolean horizontal, boolean pager, List<App> apps) {
        mHorizontal = horizontal;
        mApps = apps;
        mPager = pager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mPager) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_pager_favorite, parent, false));
        } else {
            return  new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_favorite, parent, false)) ;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        App app = mApps.get(position);
        holder.imageView.setImageResource(app.getDrawable());
        holder.imageView2.setImageResource(app.getDrawable2());
        holder.imageView3.setImageResource(app.getDrawable3());
        holder.nameTextView.setText(app.getName());
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
        public ImageView imageView2;
        public ImageView imageView3;
        public TextView nameTextView;
        public TextView ratingTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageView2);
            imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);

        }

        @Override
        public void onClick(View v) {
            Log.d("App", mApps.get(getAdapterPosition()).getName());
        }
    }

}

