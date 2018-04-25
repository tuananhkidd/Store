package com.kidd.store.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Until;
import com.kidd.store.R;
import com.kidd.store.common.Utils;
import com.kidd.store.models.ClothesPreview;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KingIT on 4/22/2018.
 */

public class ClothesPreviewAdapter extends EndlessLoadingRecyclerViewAdapter{
    public ClothesPreviewAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initLoadingViewHolder(ViewGroup parent) {
        View loadingView = getInflater().inflate(R.layout.item_load_more, parent, false);
        return new LoadingViewHolder(loadingView);
    }

    @Override
    protected void bindLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_clothes, parent, false);
        return new ClothesPreviewHodel(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        ClothesPreviewHodel clothesPreviewHodel= (ClothesPreviewHodel) holder;
        ClothesPreview clothesPreview= getItem(position, ClothesPreview.class);
        Glide.with(getContext()).load(clothesPreview.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.book_logo)).into(clothesPreviewHodel.imgAvatar);
        clothesPreviewHodel.tvAcountSave.setText("("+clothesPreview.getNumberSave()+")");
        clothesPreviewHodel.tvName.setText(clothesPreview.getName());
        clothesPreviewHodel.tvType.setText(clothesPreview.getCategory());
//        DecimalFormat decimalFormat= new DecimalFormat("###.###.###");
        clothesPreviewHodel.tvCost.setText(Utils.formatNumberMoney(clothesPreview.getPrice())+" đ");
    }
    class ClothesPreviewHodel extends NormalViewHolder{
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_acount_save)
        TextView tvAcountSave;
        @BindView(R.id.tv_full_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_cost)
        TextView tvCost;
        public ClothesPreviewHodel(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
