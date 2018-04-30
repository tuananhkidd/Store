package com.kidd.store.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidd.store.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderClothesAdapter extends EndlessLoadingRecyclerViewAdapter {
    public OrderClothesAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initLoadingViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected void bindLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {

    }

    class OrderClothesViewHolder extends NormalViewHolder{
//        @BindView(R.id.img_avatar)
//        ImageView imgAvatar;
//        @BindView(R.id.tv_full_name)
//        TextView tvName;
//        @BindView(R.id.tv_price)
//        TextView tvprice;
//        @BindView(R.id.tv_created_date)
//        TextView tvCreatedDate;


        public OrderClothesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
