package com.kidd.store.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.kidd.store.R;

import org.jetbrains.annotations.NotNull;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RatingDialog extends AlertDialog {
    MaterialRatingBar ratingBar;
    EditText edt_msg;
    Button btn_submit;
    onClickRateButton clickRateButton;
    int rate = -1;

    public onClickRateButton getClickRateButton() {
        return clickRateButton;
    }

    public void setClickRateButton(onClickRateButton clickRateButton) {
        this.clickRateButton = clickRateButton;
    }

    public RatingDialog(@NotNull Context context) {
        super(context);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        Window window = getWindow();
//        if (window != null) {
//            window.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
//        }
        View contentView = LayoutInflater.from(context)
                .inflate(R.layout.rating_dialog, null, false);
        setView(contentView);

        ratingBar = contentView.findViewById(R.id.rating_product);
        edt_msg = contentView.findViewById(R.id.edt_cmt);
        btn_submit = contentView.findViewById(R.id.btn_rate);
        ratingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                rate = (int) rating;
            }
        });
        btn_submit.setOnClickListener(v -> {
            clickRateButton.onClickButton((int) ratingBar.getRating(), edt_msg.getText().toString());

        });
    }


    public interface onClickRateButton {
        void onClickButton(int rate, String msg);
    }
}
