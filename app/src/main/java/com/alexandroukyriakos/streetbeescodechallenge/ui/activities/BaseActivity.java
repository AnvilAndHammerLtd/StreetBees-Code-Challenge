package com.alexandroukyriakos.streetbeescodechallenge.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alexandroukyriakos.streetbeescodechallenge.helpers.BaseProgressBarHelper;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.FactoryProgressBarHelper;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.SimpleProgressBarHelper;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getName();

    public static final FactoryProgressBarHelper PROGRESS_BAR_HELPER_FACTORY = new FactoryProgressBarHelper();
    private BaseProgressBarHelper mProgressBarHelper;

    public BaseProgressBarHelper getProgressBarHelper() {
        return mProgressBarHelper;
    }

    public void setProgressBarHelper(BaseProgressBarHelper baseProgressBarHelper) {
        mProgressBarHelper = baseProgressBarHelper;
    }

    protected void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        setProgressBarHelper(
                PROGRESS_BAR_HELPER_FACTORY.getProgressBar(
                        this,
                        SimpleProgressBarHelper.ProgressBarSize.FULL_SCREEN
                )
        );
    }
}