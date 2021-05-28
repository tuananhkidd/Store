package com.kidd.store.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidd.store.GlideApp;
import com.kidd.store.R;
import com.kidd.store.models.response.StoreBranchViewModel;

import java.util.List;

public class StoreBranchAdapter extends RecyclerView.Adapter<StoreBranchAdapter.StoreBranchViewHolder> {
    private Context context;
    private List<StoreBranchViewModel> storeBranchViewModels;
    private OnClickItem onClickItem;

    public StoreBranchAdapter(Context context, List<StoreBranchViewModel> storeBranchViewModels) {
        this.context = context;
        this.storeBranchViewModels = storeBranchViewModels;
//        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public StoreBranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store_branch,parent,false);
        return new StoreBranchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreBranchViewHolder holder, int position) {
        StoreBranchViewModel storeBranchViewModel = storeBranchViewModels.get(position);
        GlideApp.with(context)
                .load(storeBranchViewModel.getLogoUrl())
                .placeholder(R.drawable.avatar_placeholder)
                .into(holder.img_logo);

        holder.txt_branch_name.setText(storeBranchViewModel.getBranch_name());
        holder.txt_address.setText(storeBranchViewModel.getAddress());
        if(storeBranchViewModel.getDistance()==-1){
            holder.txt_distance.setVisibility(View.GONE);
        }else {
            if(storeBranchViewModel.getDistance() < 1){
                int distance = (int) (storeBranchViewModel.getDistance()*1000);
                holder.txt_distance.setVisibility(View.VISIBLE);
                holder.txt_distance.setText("Cách "+distance+" m");
            }else {
               // double distance = Double.parseDouble(String.format("%2s",storeBranchViewModel.getDistance()+""));
                holder.txt_distance.setVisibility(View.VISIBLE);
                holder.txt_distance.setText(String.format("Cách %.2f km",storeBranchViewModel.getDistance()));
            }

        }
    }

    @Override
    public int getItemCount() {
        return storeBranchViewModels.size();
    }


    class StoreBranchViewHolder extends RecyclerView.ViewHolder {
        ImageView img_logo;
        TextView txt_branch_name;
        TextView txt_address;
        TextView txt_distance;

        public StoreBranchViewHolder(View itemView) {
            super(itemView);
            img_logo = itemView.findViewById(R.id.img_logo);
            txt_address = itemView.findViewById(R.id.txt_address);
            txt_branch_name = itemView.findViewById(R.id.txt_branch_name);
            txt_distance = itemView.findViewById(R.id.txt_distance);
        }
    }

     interface OnClickItem{
        void clickItem(int positon);
    }

}
