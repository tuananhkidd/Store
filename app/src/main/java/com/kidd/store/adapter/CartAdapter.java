package com.kidd.store.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kidd.store.R;
import com.kidd.store.common.Utils;
import com.kidd.store.models.Item;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KingIT on 4/29/2018.
 */

public class CartAdapter extends RecyclerViewAdapter{


    public CartAdapter(Context context, boolean enableSelectedMode) {
        super(context, enableSelectedMode);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_cart_clothes, parent, false);
        return new CartAdapterHodel(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        CartAdapterHodel cartAdapterHodel= (CartAdapterHodel) holder;
        Item item= getItem(position, Item.class);
        Glide.with(getContext()).load(item.getClothes().getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.book_logo)).into(clothesPreviewHodel.imgAvatar);
        cartAdapterHodel.tvName.setText(item.getClothes().getName());
        cartAdapterHodel.tvSize.setText(cartAdapterHodel.tvSize.getText().toString()+ item.getSize());
        switch (item.getColor()){
            case 1:{
                cartAdapterHodel.tvColorSelect.setBackgroundColor(R.color.md_red_500);
                break;
            }
            case 2:{
                cartAdapterHodel.tvColorSelect.setBackgroundColor(R.color.md_green_500);
                break;
            }
            case 3:{
                cartAdapterHodel.tvColorSelect.setBackgroundColor(R.color.md_blue_500);
                break;
            }
            case 4:{
                cartAdapterHodel.tvColorSelect.setBackgroundColor(R.color.md_yellow_500);
                break;
            }
        }
//        DecimalFormat decimalFormat= new DecimalFormat("###.###.###");
        cartAdapterHodel.tvCost.setText(Utils.formatNumberMoney(item.getClothes().getPrice())+" đ");
        cartAdapterHodel.tvCount.setText(item.getCount());
        cartAdapterHodel.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    class CartAdapterHodel extends NormalViewHolder{
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.img_close)
        ImageView imgClose;
        @BindView(R.id.tv_full_name)
        TextView tvName;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_cost)
        TextView tvCost;
        @BindView(R.id.tv_color_select)
        TextView tvColorSelect;
        @BindView(R.id.tv_sub_count)
        TextView tvSubCount;
        @BindView(R.id.tv_add_count)
        TextView tvAddCount;
        @BindView(R.id.tv_count)
        TextView tvCount;
        public CartAdapterHodel(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
