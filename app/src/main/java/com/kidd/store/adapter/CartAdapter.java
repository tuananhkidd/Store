package com.kidd.store.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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

public class CartAdapter extends RecyclerViewAdapter implements RecyclerViewAdapter.OnItemClickListener, RecyclerViewAdapter.OnItemSelectionChangedListener {
    private ClickChangeCount clickChangeCount;

    public CartAdapter(Context context, ClickChangeCount clickChangeCount) {
        super(context, false);
        setOnItemSelectionChangeListener(this);
        this.clickChangeCount=clickChangeCount;
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_cart_clothes, parent, false);
        return new CartAdapterHodel(view);
    }
    @Override
    public void setSelectedMode(boolean isSelected) {
        super.setSelectedMode(isSelected);
        if (isSelected) {
            backup();
            addOnItemClickListener(this);
            notifyItemRangeChanged(0, getItemCount());
        } else {
            removeOnItemClickListener(this);
        }
    }


    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        CartAdapterHodel cartAdapterHodel= (CartAdapterHodel) holder;
        Item item= getItem(position, Item.class);
        Glide.with(getContext()).load(item.getClothes().getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.book_logo)).into(cartAdapterHodel.imgAvatar);
        cartAdapterHodel.tvName.setText(item.getClothes().getName());
        cartAdapterHodel.tvSize.setText("Size: "+ item.getSize());
        switch (item.getColor()){
            case "Đỏ":{
                cartAdapterHodel.tvColorSelect.setBackgroundColor(getContext().getResources()
                        .getColor(R.color.md_red_500));
                break;
            }
            case "Xanh":{
                cartAdapterHodel.tvColorSelect.setBackgroundColor(getContext().getResources()
                        .getColor(R.color.md_green_500));
                break;
            }
            case "Cam":{
                cartAdapterHodel.tvColorSelect.setBackgroundColor(getContext().getResources()
                        .getColor(R.color.md_orange_500));
                break;
            }

        }
//        DecimalFormat decimalFormat= new DecimalFormat("###.###.###");
        cartAdapterHodel.tvCost.setText(Utils.formatNumberMoney(item.getClothes().getPrice())+" đ");
        cartAdapterHodel.tvCount.setText(item.getCount()+"");
        cartAdapterHodel.tvSubCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getCount()>1) {
                    clickChangeCount.clickSubCount(position);
                }
            }
        });
        cartAdapterHodel.tvAddCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickChangeCount.clickAddCount(position);
            }
        });
    }
    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        if (isInSelectedMode() && viewType == VIEW_TYPE_NORMAL) {
            setSelectedItem(position, !isItemSelected(position));
        }
    }

    @Override
    public void onItemSelectionChanged(RecyclerView.ViewHolder viewHolder, int viewType, boolean isSelected) {
        if (viewType == VIEW_TYPE_NORMAL) {
            CartAdapterHodel cartAdapterHodel = (CartAdapterHodel) viewHolder;
            if (isSelected) {
                cartAdapterHodel.itemView
                        .setBackgroundColor(getContext().getResources()
                                .getColor(R.color.light_gray));
                cartAdapterHodel.imgDeleteMark.setVisibility(View.VISIBLE);
            } else {
                TypedValue outValue = new TypedValue();
                getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                cartAdapterHodel.itemView.setBackgroundResource(outValue.resourceId);
                cartAdapterHodel.imgDeleteMark.setVisibility(View.GONE);
            }
        }
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
        @BindView(R.id.tv_total_count)
        TextView tvCount;
        @BindView(R.id.img_delete_mark)
        ImageView imgDeleteMark;
        public CartAdapterHodel(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
    public interface ClickChangeCount{
        public void clickAddCount(int possition);
        public void clickSubCount(int possition);
    }
}
