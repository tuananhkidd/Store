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
import com.kidd.store.models.response.SaveClothesPreview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaveClothesAdapter extends EndlessLoadingRecyclerViewAdapter implements View.OnClickListener{
    OnButtonSaveClick onButtonSaveClick;
    public SaveClothesAdapter(Context context, boolean enableSelectedMode,OnButtonSaveClick onButtonSaveClick) {
        super(context, enableSelectedMode);
        this.onButtonSaveClick = onButtonSaveClick;
    }

    @Override
    protected RecyclerView.ViewHolder initLoadingViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_load_more,parent,false);
        return new SaveClothesPreviewHolder(view);
    }

    @Override
    protected void bindLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_save_clothes,parent,false);
        return new SaveClothesPreviewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder normalViewHolder, int position) {
        SaveClothesPreviewHolder holder = (SaveClothesPreviewHolder) normalViewHolder;
        SaveClothesPreview saveClothesPreview = getItem(position,SaveClothesPreview.class);
        holder.tvCreatedDate.setText(Utils.getDateFromMilliseconds(saveClothesPreview.getSaveDate()));
        holder.tvName.setText(saveClothesPreview.getName());
        holder.tvprice.setText(Utils.formatNumberMoney(saveClothesPreview.getPrice())+" đ");

        GlideApp.with(getContext())
                .load(saveClothesPreview.getLogoUrl())
                .placeholder(R.drawable.book_logo)
                .into(holder.imgAvatar);
    }

    @Override
    public void onClick(View view) {

    }

    class SaveClothesPreviewHolder extends NormalViewHolder{
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_full_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvprice;
        @BindView(R.id.tv_created_date)
        TextView tvCreatedDate;
        @BindView(R.id.img_save)
        ImageView img_save;

        public SaveClothesPreviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            img_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonSaveClick.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnButtonSaveClick{
        void onClick(int pos);
    }


}
