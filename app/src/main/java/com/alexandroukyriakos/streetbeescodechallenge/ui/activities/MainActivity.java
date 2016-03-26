package com.alexandroukyriakos.streetbeescodechallenge.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.alexandroukyriakos.streetbeescodechallenge.R;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}