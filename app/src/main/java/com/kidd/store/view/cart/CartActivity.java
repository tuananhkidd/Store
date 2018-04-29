package com.kidd.store.view.cart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.kidd.store.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {
    @BindView(R.id.rc_item)
    RecyclerView rcItem;
    @BindView(R.id.tv_cost_cart)
    TextView tvCostCart;
    @BindView(R.id.bt_pay_cart)
    Button btPayCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
    }

}
