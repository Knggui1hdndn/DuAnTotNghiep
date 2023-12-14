package com.knd.duantotnghiep.duantotnghiep.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static int COUNT_SHOPPING_BAG = 0;
    private final ArrayList<String> strings = new ArrayList<>(Arrays.asList("Chờ xác nhận", "Đã xác nhận", "Đang giao hàng", "Đã giao hàng", "Hủy", "Trả hàng"));

    public static void copyTextToCLipBoard(Context context, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("lablel", text));
        Toast.makeText(context, "Copy to clipboard", Toast.LENGTH_SHORT).show();
    }

    public static void loadImage(ImageView imageView, String path) {
        Picasso.get().load(path).into(imageView);
    }

    public static void showKeyboard(EditText mEtSearch, Context context) {
        mEtSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


    }

    public static String getImageProduct(ProductResponse productResponse) {
        try {
            return productResponse.getProductDetails().get(0).getImageProducts().get(0).getImageProduct().getImage();
        } catch (Exception e) {
            return "";
        }
    }

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat detailsDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static final DecimalFormat simplePriceFormat = new DecimalFormat("###,###,###.##");

    public static String formatDate(Long date) {
        return simpleDateFormat.format(date);
    }

    public static String formatDateDetails(Long date) {
        return detailsDateFormat.format(date);
    }

    public static String formatPrice(Double price) {
        return simplePriceFormat.format(price);
    }

    public static String checkInfoNull(String info) {
        if (info == null) return "config";
        return info;
    }

    @SuppressLint("UnsafeOptInUsageError")
    public static void setUpBadge(Context context, Toolbar toolbar, int number, int itemIds) {
        BadgeDrawable badge = BadgeDrawable.create(context);
        badge.setBadgeGravity(BadgeDrawable.TOP_END);
        badge.setMaxNumber(99);
        badge.setNumber(number);
        badge.setAutoAdjustToWithinGrandparentBounds(true);
        if (number == 0) badge.setVisible(false);
        BadgeUtils.attachBadgeDrawable(badge, toolbar, itemIds);
    }

}
