package com.alexandroukyriakos.streetbeescodechallenge;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Useful generic re-usable methods
 */
public class Util {
    public static void showToastMessageCentered(Context context, int message) {
        if (context != null) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * gets the root view from the current context
     *
     * @param context the current context
     * @return the root view
     */
    public static ViewGroup getRootView(Context context) {
        ViewGroup layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();
        return layout;
    }
}
