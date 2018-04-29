package com.kidd.store.adapter;

import android.annotation.SuppressLint;
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

public class SpinnerColorAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> lsColor;


    public SpinnerColorAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.lsColor = objects;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_size_color, parent, false);
        }
        TextView textView = (TextView) convertView;
        switch (position) {
            case 0: {
                textView.setBackgroundResource(android.R.color.holo_red_light);
                break;
            }
            case 1: {
                textView.setBackgroundResource(android.R.color.holo_blue_light);
                break;
            }
            case 2: {
                textView.setBackgroundResource(android.R.color.holo_orange_light);
                break;
            }
        }
        textView.setText(getItem(position));
//        textView.setTextColor(R.color.colorAccent);
//        textView.setBackgroundResource(R.color.colorPrimary);
        return textView;
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
