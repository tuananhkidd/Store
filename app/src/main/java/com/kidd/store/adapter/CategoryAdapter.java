package com.kidd.store.adapter;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidd.store.R;
import com.kidd.store.models.Book;
import com.kidd.store.models.Category;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by TuanAnhKid on 4/1/2018.
 */

public class CategoryAdapter implements ExpandableListAdapter {
    private List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return categories.get(i).getLsBook().size();
    }

    @Override
    public Object getGroup(int i) {
        return categories.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return categories.get(i).getLsBook().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        }
        TextView txt_title = view.findViewById(R.id.txt_title);
        ImageView img = view.findViewById(R.id.img_expand);
        txt_title.setText(categories.get(i).getTitle());
        if (b) {
            img.setImageResource(R.drawable.ic_collapse);
        } else {
            img.setImageResource(R.drawable.ic_expand);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_book, viewGroup, false);
        TextView txt_title;
        TextView txt_author;
        TextView txt_price;
        txt_title = itemView.findViewById(R.id.txt_title);
        txt_author = itemView.findViewById(R.id.txt_author);
        txt_price = itemView.findViewById(R.id.txt_price);

        Book book = categories.get(i).getLsBook().get(i1);
        txt_title.setText(book.getTitle());
        txt_author.setText(book.getAuthor());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txt_price.setText(decimalFormat.format(book.getPrice()));
        return itemView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int i) {

    }

    @Override
    public void onGroupCollapsed(int i) {

    }

    @Override
    public long getCombinedChildId(long l, long l1) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return 0;
    }
}
