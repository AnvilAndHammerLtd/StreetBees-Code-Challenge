package com.alexandroukyriakos.streetbeescodechallenge;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Useful generic UI re-usable methods
 */
public class UiUtil {
    /**
     * displays a toast message in the center of the screen
     *
     * @param context the context
     * @param message the string to show
     */
    public static final void showToastMessageCentered(Context context, String message) {
        if (context != null) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * displays a toast message in the center of the screen
     *
     * @param context      the context
     * @param messageResId the string resource id to show
     */
    public static final void showToastMessageCentered(Context context, int messageResId) {
        if (context != null) {
            Toast toast = Toast.makeText(context, messageResId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * load an image from a url into an ImageView using picasso
     *
     * @param context          the context
     * @param imageUrl         the full path of the image
     * @param placeholderResId a placeholder image to use in case the real image is not found
     * @param into             the ImageView to load the image into
     */
    public static void loadImageInto(
            Context context, String imageUrl,
            int placeholderResId, ImageView into) {
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(placeholderResId)
                .into(into);
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