package com.knd.duantotnghiep.duantotnghiep.utils;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

 public class AdapterBinding {
    @BindingAdapter({"strikethrough"})
    public static void strikethrough(TextView textView, @NonNull Boolean show) {
        if (show) {
            textView.setPaintFlags(STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags());
        }
    }
}
