package com.kidd.store.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidd.store.GlideApp;
import com.kidd.store.R;
import com.kidd.store.common.Utils;
import com.kidd.store.models.response.OrderViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderClothesAdapter extends EndlessLoadingRecyclerViewAdapter {
    Context context;

    public OrderClothesAdapter(Context context) {
        super(context, false);
        this.context = context;
    }

    @Override
    protected RecyclerView.ViewHolder initLoadingViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false);
        return new OrderClothesViewHolder(view);
    }

    @Override
    protected void bindLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_clothes, parent, false);
        return new OrderClothesViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        OrderClothesViewHolder viewHolder = (OrderClothesViewHolder) holder;
        OrderViewModel orderViewModel = getItem(position,OrderViewModel.class);
        GlideApp.with(context)
                .load(orderViewModel.getLogoUrl())
                .placeholder(R.drawable.avatar_placeholder)
                .into(viewHolder.imgAvatar);

        viewHolder.tvName.setText(orderViewModel.getName());
        viewHolder.tvprice.setText("Giá : "+Utils.formatNumberMoney(orderViewModel.getPrice())+" vnđ/chiếc");
        viewHolder.txt_amount.setText(orderViewModel.getAmount()+"");
        viewHolder.txt_order_date.setText(Utils.getDateFromMilliseconds(orderViewModel.getCreatedDate()));
    }

    class OrderClothesViewHolder extends NormalViewHolder {
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.txt_title)
        TextView tvName;
        @BindView(R.id.txt_price)
        TextView tvprice;
        @BindView(R.id.txt_amount)
        TextView txt_amount;
        @BindView(R.id.txt_order_date)
        TextView txt_order_date;


        public OrderClothesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
