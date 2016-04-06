package com.alexandroukyriakos.streetbeescodechallenge.helpers.progressbar;

import android.content.Context;

/**
 * Factory class to easily create a specific progress bar
 */
public class FactoryProgressBarHelper {

    public BaseProgressBarHelper getProgressBar(Context context, BaseProgressBarHelper.ProgressBarSize progressBarSize) {
        BaseProgressBarHelper progressBarHelper;

        switch (progressBarSize) {
            case SMALL:
                progressBarHelper = new SimpleProgressBarHelper(context, SimpleProgressBarHelper.ProgressBarSize.SMALL);
                break;

            case LARGE:
                progressBarHelper = new SimpleProgressBarHelper(context, SimpleProgressBarHelper.ProgressBarSize.LARGE);
                break;

            case FULL_SCREEN:
                progressBarHelper = new SimpleProgressBarHelper(context, SimpleProgressBarHelper.ProgressBarSize.FULL_SCREEN);
                break;

            default:
                progressBarHelper = new SimpleProgressBarHelper(context, SimpleProgressBarHelper.ProgressBarSize.SMALL);
        }
        return progressBarHelper;
    }
}