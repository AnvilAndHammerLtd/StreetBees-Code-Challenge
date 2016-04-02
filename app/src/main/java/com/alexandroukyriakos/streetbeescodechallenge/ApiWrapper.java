package com.alexandroukyriakos.streetbeescodechallenge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

public class ApiWrapper {
    private final static String TAG = ApiWrapper.class.getSimpleName();

    public static int getColor(Context context, @ColorRes int id, @Nullable Resources.Theme theme) {
        int color;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            color = context.getResources().getColor(id, theme);
        } else {
            color = context.getResources().getColor(id);
        }
        return color;
    }

    public static Drawable getDrawable(Context context, @DrawableRes int id, @Nullable Resources.Theme theme) {
        Drawable drawable;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            drawable = context.getResources().getDrawable(id, theme);
        } else {
            drawable = context.getResources().getDrawable(id);
        }
        return drawable;
    }
}