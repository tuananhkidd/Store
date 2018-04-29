package com.kidd.store.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kidd.store.R;

import java.util.List;

public class SpinnerSizeAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> lsSize;
    public SpinnerSizeAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.lsSize = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_size_color, parent, false);
        }
        ((TextView) convertView).setText(getItem(position));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_size_color, parent, false);
        }
        ((TextView) convertView).setText(getItem(position));
        return convertView;
    }
}
