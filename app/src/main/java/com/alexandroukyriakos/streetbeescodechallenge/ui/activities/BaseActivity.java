package com.alexandroukyriakos.streetbeescodechallenge.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alexandroukyriakos.streetbeescodechallenge.Util;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.BaseProgressBarHelper;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.FactoryProgressBarHelper;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.SimpleProgressBarHelper;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getName();

    public static final FactoryProgressBarHelper PROGRESS_BAR_HELPER_FACTORY = new FactoryProgressBarHelper();
    private BaseProgressBarHelper mProgressBarHelper;

    private static final String TIMESTAMP_QUERY = "ts";
    private static final String API_QUERY = "apikey";
    private static final String HASH_QUERY = "hash";

    private static final String BASE_URL = "http://gateway.marvel.com";
    private static final String PUBLIC_API_KEY = "251f13da2290ab732011685df04c0849";
    private static final String PRIVATE_API_KEY = "569f1d30370eecc7d007fd78ade9826d1bff4507";

    private static final RequestInterceptor sRequestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestInterceptor.RequestFacade request) {
            String timestamp = Util.getCurrentTimestamp();
            String hash = Util.md5(timestamp + PRIVATE_API_KEY + PUBLIC_API_KEY);

            request.addQueryParam(TIMESTAMP_QUERY, timestamp);
            request.addQueryParam(API_QUERY, PUBLIC_API_KEY);
            request.addQueryParam(HASH_QUERY, hash);
        }
    };

    public static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(BASE_URL)
            .setRequestInterceptor(sRequestInterceptor)
            .build();

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