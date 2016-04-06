package com.alexandroukyriakos.streetbeescodechallenge.ui.fragments;

import android.support.v4.app.Fragment;

import com.alexandroukyriakos.streetbeescodechallenge.helpers.progressbar.BaseProgressBarHelper;

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getName();
    private BaseProgressBarHelper mProgressBarHelper;

    protected BaseProgressBarHelper getProgressBarHelper() {
        return mProgressBarHelper;
    }

    protected void setProgressBarHelper(BaseProgressBarHelper baseProgressBarHelper) {
        mProgressBarHelper = baseProgressBarHelper;
    }
}
