package com.alexandroukyriakos.streetbeescodechallenge.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.alexandroukyriakos.streetbeescodechallenge.R;
import com.alexandroukyriakos.streetbeescodechallenge.ui.fragments.ComicsFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        goToComicsFragment();
    }

    private void goToComicsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ComicsFragment companyFragment = ComicsFragment.newInstance(getProgressBarHelper());
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, companyFragment, ComicsFragment.TAG);
        ft.commit();
        fm.executePendingTransactions();
    }
}